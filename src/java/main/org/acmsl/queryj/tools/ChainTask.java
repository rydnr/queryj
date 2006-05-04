//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Manages a sequential chain of Ant actions.
 *
 */
package org.acmsl.queryj.tools;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AntCommandHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.ArrayListChainAdapter;
import org.acmsl.commons.patterns.Chain;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

/**
 * Manages a sequential chain of Ant actions.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class ChainTask
    extends  Task
{
    /**
     * The task chain.
     */
    private Chain m__Chain;

    /**
     * Constructs a Chain task.
     */
    public ChainTask()
    {
        super(); // redundant
        immutableSetChain(new ArrayListChainAdapter());
    }

    /**
     * Specifies the chain.
     * @param chain the new chain.
     */
    private void immutableSetChain(final Chain chain)
    {
        m__Chain = chain;
    }

    /**
     * Specifies the chain.
     * @param chain the new chain.
     */
    protected void setChain(final Chain chain)
    {
        immutableSetChain(chain);
    }

    /**
     * Retrieves the chain.
     * @return such chain.
     */
    public Chain getChain()
    {
        return m__Chain;
    }

    /**
     * Requests the chained logic to be performed.
     * @throws org.apache.tools.ant.BuildException whenever the required
     * parameters are not present or valid.
     */
    public void execute()
        throws  BuildException
    {
        process(
            buildChain(getChain()),
            buildCommand(new AntCommand(getProject())));
    }

    /**
     * Builds the chain.
     * @param chain the chain to be configured.
     * @return the updated chain.
     */
    protected abstract Chain buildChain(Chain chain);

    /**
     * Builds the command.
     * @param command the command to be initialized.
     * @return the initialized command.
     */
    protected abstract AntCommand buildCommand(final AntCommand command);

    /**
     * Retrieves the link of the chain just after the one given command
     * handlar takes.
     * @param chain the concrete chain.
     * @param commandHandler the handler just before the desired link.
     * @return the next hanlder in the chain.
     * @precondition chain != null
     */
    public AntCommandHandler getNextChainLink(
        final Chain chain,
        final AntCommandHandler commandHandler)
    {
        AntCommandHandler result = null;

        if  (   (chain != null)
             && (!chain.isEmpty()))
        {
            if  (   (commandHandler == null)
                 || (!chain.contains(commandHandler)))
            {
                result = (AntCommandHandler) chain.get(0);
            }
            else
            {
                int currentIndex = chain.indexOf(commandHandler);

                if  (   (currentIndex >= 0)
                     && (currentIndex < chain.size() - 1))
                {
                    result =
                        (AntCommandHandler) chain.get(currentIndex + 1);
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
     * @throws BuildException if the build process cannot be performed.
     */
    protected boolean process(
        final Chain chain, final AntCommand command)
      throws  BuildException
    {
        boolean result = false;

        try 
        {
            AntCommandHandler currentCommandHandler = null;

            do
            {
                currentCommandHandler =
                    getNextChainLink(chain, currentCommandHandler);

                if  (currentCommandHandler != null)
                {
                    result = currentCommandHandler.handle(command);
                }
            }
            while  (   (!result)
                    && (currentCommandHandler != null));
        }
        catch  (BuildException buildException)
        {
            cleanUpOnError(buildException, command);

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
        final BuildException buildException, final AntCommand command);
}
