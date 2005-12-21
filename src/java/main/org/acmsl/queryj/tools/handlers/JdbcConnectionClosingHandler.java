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
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Closes a JDBC connection.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.JdbcConnectionOpeningHandler;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Closes a JDBC connection.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class JdbcConnectionClosingHandler
    extends  AbstractAntCommandHandler
{
    /**
     * Creates a JdbcConnectionClosingHandler.
     */
    public JdbcConnectionClosingHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     */
    public boolean handle(final Command command)
    {
        boolean result = false;

        if  (command instanceof AntCommand) 
        {
            AntCommand t_AntCommand = (AntCommand) command;
            
            try 
            {
                result = handle(t_AntCommand);
            }
            catch  (final BuildException buildException)
            {
                Log t_Log = UniqueLogFactory.getLog(getClass());
                
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close JDBC connection.",
                        buildException);
                }
            }
        }
        
        return result;
    }

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition command != null
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        return handle(command.getAttributeMap());
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
        throws  BuildException
    {
        closeConnection(parameters);

        removeConnection(parameters);

        return false;
    }

    /**
     * Closes the JDBC connection stored in the attribute map.
     * @param parameters the parameter map.
     * @throws BuildException if the connection cannot be closed.
     * @precondition parameters != null
     */
    protected void closeConnection(final Map parameters)
        throws  BuildException
    {
        closeConnection(
            (Connection)
                parameters.get(
                    JdbcConnectionOpeningHandler.JDBC_CONNECTION));
    }

    /**
     * Closes the connection.
     * @param connection the JDBC connection.
     * @throws org.apache.tools.ant.BuildException whenever the required
     * connection is not present or valid.
     */
    protected void closeConnection(final Connection connection)
        throws  BuildException
    {
        if  (connection != null)
        {
            try
            {
                connection.close();
            }
            catch  (final SQLException sqlException)
            {
                throw new BuildException(sqlException);
            }
        }
    }

    /**
     * Removes the JDBC connection in given attribute map.
     * @param parameters the parameter map.
     * @throws BuildException if the connection cannot be removed for any reason.
     * @precondition parameters != null
     */
    protected void removeConnection(final Map parameters)
        throws  BuildException
    {
        parameters.remove(JdbcConnectionOpeningHandler.JDBC_CONNECTION);
    }
}
