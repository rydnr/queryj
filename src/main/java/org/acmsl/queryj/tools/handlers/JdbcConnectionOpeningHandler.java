//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: JdbcConnectionOpeningHandler.java
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
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

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
                            ParameterValidationHandler.JDBC_PASSWORD),
                    parameters);
        }

        return result;
    }

    /**
     * Opens the connection.
     * @param driver the JDBC driver.
     * @param url the url.
     * @param username the username.
     * @param password the password.
     * @param attributes the attributes.
     * @return the JDBC connection.
     * @throws org.apache.tools.ant.BuildException whenever the required
     * parameters are not present or valid.
     * @precondition attributes != null
     */
    protected Connection openConnection(
        final String driver,
        final String url,
        final String username,
        final String password,
        final Map attributes)
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

                if  (result != null)
                {
                    fineTuneConnection(result, attributes);
                }
            }
            catch  (final RuntimeException exception)
            {
                throw exception;
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

    /**
     * Sets up additional settings for this connection.
     * @param connection the connection.
     * @param attributes the attributes.
     * @precondition connection != null
     * @precondition attributes != null
     */
    protected void fineTuneConnection(
        final Connection connection, final Map attributes)
    {
        fineTuneOracleConnection(connection, attributes);
    }
    
    /**
     * Sets up additional settings for this connection.
     * @param connection the connection.
     * @param attributes the attributes.
     * @precondition connection != null
     * @precondition attributes != null
     */
    protected void fineTuneOracleConnection(
        final Connection connection, final Map attributes)
    {
        try
        {
            Method t_Method =
                connection.getClass().getDeclaredMethod(
                    "setStatementCacheSize", new Class[] { Integer.TYPE });

            t_Method.invoke(connection, new Object[] { new Integer(100) });

            t_Method = 
                connection.getClass().getDeclaredMethod(
                    "setImplicitCachingEnabled", new Class[] { Boolean.TYPE });

            t_Method.invoke(connection, new Object[] { Boolean.TRUE });
        }
        catch  (final NoSuchMethodException noSuchMethod)
        {
            // Not Oracle. Better.
        }
        catch  (final SecurityException securityException)
        {
            // We cannot set up the statement cache.
        }
        catch  (final IllegalAccessException illegalAccessException)
        {
            // We cannot set up the statement cache.
        }
        catch  (final InvocationTargetException invocationTargetException)
        {
            // We cannot set up the statement cache.
        }
    }
    
    /**
     * Retrieves the relative weight of this handler.
     * @param parameters the parameters.
     * @return a value between <code>MIN_WEIGHT</code>
     * and <code>MAX_WEIGHT</code>.
     */
    public double getRelativeWeight(final Map parameters)
    {
        return DEFAULT_WEIGHT;
    }
}
