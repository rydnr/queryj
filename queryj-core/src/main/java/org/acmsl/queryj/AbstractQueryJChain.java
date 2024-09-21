//;-*- mode: java -*-
/*
                        QueryJ Core

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
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.exceptions.DevelopmentModeException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.ArrayListChainAdapter;
import org.acmsl.commons.patterns.Chain;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Manages a sequential chain of actions within QueryJ.
 * @param <C> the command type.
 * @param <CH> che command handler type.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public abstract class AbstractQueryJChain
    <C extends QueryJCommand, CH extends QueryJCommandHandler<C>>
{
    /**
     * The chain.
     */
    private Chain<C, QueryJBuildException, CH> m__Chain;

    /**
     * Constructs an {@link AbstractQueryJChain} instance.
     */
    public AbstractQueryJChain()
    {
        immutableSetChain(new ArrayListChainAdapter<C, QueryJBuildException, CH>());
    }

    /**
     * Specifies the chain.
     * @param chain the new chain.
     */
    protected final void immutableSetChain(@NotNull final Chain<C, QueryJBuildException, CH> chain)
    {
        m__Chain = chain;
    }

    /**
     * Specifies the chain.
     * @param chain the new chain.
     */
    @SuppressWarnings("unused")
    protected void setChain(@NotNull final Chain<C, QueryJBuildException, CH> chain)
    {
        immutableSetChain(chain);
    }

    /**
     * Retrieves the chain.
     * @return such chain.
     */
    @NotNull
    public Chain<C, QueryJBuildException, CH> getChain()
    {
        return m__Chain;
    }

    /**
     * Requests the chained logic to be performed.
     * @param settings the command.
     * @throws QueryJBuildException if the process fails.
    */
    public void process(@NotNull final C settings)
        throws QueryJBuildException
    {
        process(buildChain(getChain()), settings);
    }

    /**
     * Builds the chain.
     * @param chain the chain to be configured.
     * @return the updated chain.
     * @throws QueryJBuildException if the process fails.
     */
    protected abstract Chain<C, QueryJBuildException, CH> buildChain(
        @NotNull final Chain<C, QueryJBuildException, CH> chain)
        throws QueryJBuildException;

    /**
     * Retrieves the link of the chain just after the one given command
     * handler takes.
     * @param chain the concrete chain.
     * @param commandHandler the handler just before the desired link.
     * @return the next handler in the chain.
     */
    @Nullable
    public CH getNextChainLink(
        @Nullable final Chain<C, QueryJBuildException, CH> chain,
        @Nullable final CH commandHandler)
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
     * Retrieves the link of the chain just before the one given command
     * handler takes.
     * @param chain the concrete chain.
     * @param commandHandler the handler just after the desired link.
     * @return the previous handler in the chain.
     */
    @Nullable
    public CH getPreviousChainLink(
        @Nullable final Chain<C, QueryJBuildException, CH> chain,
        @Nullable final CH commandHandler)
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

                if  (   (t_iCurrentIndex > 0)
                     && (t_iCurrentIndex <= chain.size() - 1))
                {
                    result = chain.get(t_iCurrentIndex - 1);
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
     * @throws QueryJBuildException if the process fails.
     */
    protected boolean process(
        @NotNull final Chain<C, QueryJBuildException, CH> chain, @NotNull final C command)
      throws QueryJBuildException
    {
        boolean result = false;

        @Nullable final Log t_Log = command.getLog();

        final boolean t_bLoggingEnabled = (t_Log != null);

        boolean restart = false;

        do
        {
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
                        && (t_CurrentCommandHandler != null)
                        && (!restart));
            }
            catch  (@NotNull final DevelopmentModeException devMode)
            {
                restart = true;
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
        }
        while (restart);

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

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"chain\": " + getChain()
            + ", \"class\": \"AbstractQueryJChain\""
            + ", \"package\": \"org.acmsl.queryj\" }";
    }
}
