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
 * Description: Opens a JDBC connection.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/*
 * Importing some JDK classes.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

/*
 * Importing Jakarta Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Opens a JDBC connection.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class JdbcConnectionOpeningHandler
    extends  AbstractAntCommandHandler
{
    /**
     * The JDBC connection attribute name.
     */
    public static final String JDBC_CONNECTION = "jdbc.connection";

    /**
     * Creates a JdbcConnectionOpeningHandler.
     */
    public JdbcConnectionOpeningHandler() {};

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
            try 
            {
                result = handle((AntCommand) command);
            }
            catch  (final BuildException buildException)
            {
                ((AntCommand) command).getProject().log(
                    ((AntCommand) command).getTask(),
                    buildException.getMessage(),
                    Project.MSG_ERR);
            }
        }
        
        return result;
    }

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        boolean result = false;

        if  (command != null) 
        {
            storeConnection(
                openConnection(command.getAttributeMap()),
                command.getAttributeMap());
        }
        
        return result;
    }

    /**
     * Opens the JDBC connection using the information stored in the
     * attribute map.
     * @param parameters the parameter map.
     * @return the JDBC connection.
     * @throws BuildException if the connection cannot be opened.
     */
    protected Connection openConnection(final Map parameters)
        throws  BuildException
    {
        Connection result = null;

        if  (parameters != null) 
        {
            result =
                openConnection(
                    (String)
                        parameters.get(
                            ParameterValidationHandler.JDBC_DRIVER),
                    (String)
                        parameters.get(
                            ParameterValidationHandler.JDBC_URL),
                    (String)
                        parameters.get(
                            ParameterValidationHandler.JDBC_USERNAME),
                    (String)
                        parameters.get(
                            ParameterValidationHandler.JDBC_PASSWORD));
        }

        return result;
    }

    /**
     * Opens the connection.
     * @param driver the JDBC driver.
     * @param url the url.
     * @param username the username.
     * @param password the password.
     * @return the JDBC connection.
     * @throws org.apache.tools.ant.BuildException whenever the required
     * parameters are not present or valid.
     */
    protected Connection openConnection(
        final String driver,
        final String url,
        final String username,
        final String password)
      throws  BuildException
    {
        Connection result = null;

        if  (   (driver != null)
             && (url    != null))
        {
            try 
            {
                Class.forName(driver);

                result =
                    DriverManager.getConnection(
                        url, username, password);
            }
            catch  (final Exception exception)
            {
                throw new BuildException(exception);
            }
        }

        return result;
    }

    /**
     * Stores the JDBC connection in given attribute map.
     * @param connection the connection to store.
     * @param parameters the parameter map.
     * @throws BuildException if the connection cannot be stored for any reason.
     * @precondition connection != null
     * @precondition parameters != null
     */
    protected void storeConnection(
        final Connection connection,
        final Map parameters)
      throws  BuildException
    {
        parameters.put(JDBC_CONNECTION, connection);
    }
}
