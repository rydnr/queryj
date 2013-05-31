//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
                              chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: AbstractQueryJChain.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Manages a sequential chain of actions within QueryJ.
 *
 */
package org.acmsl.queryj;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;
import org.acmsl.queryj.tools.logging.QueryJLog;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.ArrayListChainAdapter;
import org.acmsl.commons.patterns.Chain;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Manages a sequential chain of actions within QueryJ.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractQueryJChain<CH extends QueryJCommandHandler<C>, C extends QueryJCommand>
{
    /**
     * The chain.
     */
    private Chain<CH> m__Chain;

    /**
     * Constructs an {@link AbstractQueryJChain} instance.
     */
    public AbstractQueryJChain()
    {
        super(); // redundant
        immutableSetChain(new ArrayListChainAdapter<CH>());
    }

    /**
     * Specifies the chain.
     * @param chain the new chain.
     */
    @SuppressWarnings("unchecked")
    protected final void immutableSetChain(@NotNull final Chain<CH> chain)
    {
        m__Chain = chain;
    }

    /**
     * Specifies the chain.
     * @param chain the new chain.
     */
    @SuppressWarnings("unused")
    protected void setChain(@NotNull final Chain<CH> chain)
    {
        immutableSetChain(chain);
    }

    /**
     * Retrieves the chain.
     * @return such chain.
     */
    @NotNull
    public Chain<CH> getChain()
    {
        return m__Chain;
    }

    /**
     * Requests the chained logic to be performed.
     * @throws QueryJBuildException whenever the required
     * parameters are not present or valid.
     */
    public void process()
        throws QueryJBuildException
    {
        process(buildChain(getChain()), buildCommand());
    }

    /**
     * Builds the chain.
     * @param chain the chain to be configured.
     * @return the updated chain.
     */
    protected abstract Chain<CH> buildChain(@NotNull final Chain<CH> chain);

    /**
     * Builds the command.
     * @return the initialized command.
     */
    @NotNull
    @SuppressWarnings("unchecked")
    protected C buildCommand()
    {
        return buildCommand(new QueryJCommand());
    }

    /**
     * Builds the command.
     * @param command the command to be initialized.
     * @return the initialized command.
     */
    @NotNull
    protected abstract C buildCommand(@NotNull final QueryJCommand command);

    /**
     * Retrieves the link of the chain just after the one given command
     * handler takes.
     * @param chain the concrete chain.
     * @param commandHandler the handler just before the desired link.
     * @return the next handler in the chain.
     */
    @Nullable
    public CH getNextChainLink(
        @Nullable final Chain<CH> chain, @Nullable final CH commandHandler)
    {
        @Nullable CH result = null;

        if  (   (chain != null)
             && (!chain.isEmpty()))
        {
            if  (   (commandHandler == null)
                 || (!chain.contains(commandHandler)))
            {
                result = chain.get(0);
            }
            else
            {
                final int t_iCurrentIndex = chain.indexOf(commandHandler);

                if  (   (t_iCurrentIndex >= 0)
                     && (t_iCurrentIndex < chain.size() - 1))
                {
                    result = chain.get(t_iCurrentIndex + 1);
                }
            }
        }

        return result;
    }

    /**
     * Sends given command to a concrete chain.
     * @param chain the concrete chain.
     * @param command the command that represents which actions should be done.
     * @return <code>true</code> if the command is processed by the chain.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected boolean process(
        @NotNull final Chain<CH> chain, @NotNull final C command)
      throws QueryJBuildException
    {
        boolean result = false;

        @Nullable final QueryJLog t_Log = command.getLog();

        final boolean t_bLoggingEnabled = (t_Log != null);

        try 
        {
            @Nullable CH t_CurrentCommandHandler = null;

            do
            {
                t_CurrentCommandHandler =
                    getNextChainLink(chain, t_CurrentCommandHandler);

                if (t_bLoggingEnabled)
                {
                    t_Log.debug("Next handler: " + t_CurrentCommandHandler);
                }

                if  (t_CurrentCommandHandler != null)
                {
                    result = t_CurrentCommandHandler.handle(command);

                    if (t_bLoggingEnabled)
                    {
                        t_Log.debug(
                              t_CurrentCommandHandler + "#handle(QueryJCommand) returned "
                            + result);
                    }
                }
            }
            while  (   (!result)
                    && (t_CurrentCommandHandler != null));
        }
        catch  (@NotNull final QueryJBuildException buildException)
        {
            cleanUpOnError(buildException, command);

            if (t_bLoggingEnabled)
            {
                t_Log.error(
                    "QueryJ could not generate sources correctly.",
                    buildException);
	        }

            throw buildException;
        }

        return result;
    }

    /**
     * Performs any clean up whenever an error occurs.
     * @param buildException the error that triggers this clean-up.
     * @param command the command.
     */
    protected abstract void cleanUpOnError(
        @NotNull final QueryJBuildException buildException,
        @NotNull final QueryJCommand command);

    @NotNull
    @Override
    public String toString()
    {
        return "AbstractQueryJChain{" + "chain=" + getChain() + '}';
    }
}
