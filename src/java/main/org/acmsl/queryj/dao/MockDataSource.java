/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jsanleandro@yahoo.es
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
 * Description: Implements DataSource interface and 
 *              just returns connections using
 *              DriverManager.getConnection().
 *
 */
package org.acmsl.queryj.dao;

/*
 * Importing some JDK1.3 classes.
 */
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Importing some J2EE classes.
 */
import javax.sql.DataSource;

/*
 * Importing commons-logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Implements DataSource interface and just returns
 * connections using DriverManager.getConnection()
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro Armendariz</a>
 */
public class MockDataSource
    implements  DataSource
{
    /**
     * Driver class name.
     */
    private String m__strDriverClassName;

    /**
     * URL connection.
     */
    private String m__strURL;

    /**
     * Username.
     */
    private String m__strUserName;

    /**
     * Password.
     */
    private String m__strPassword;

    /**
     * Auto commit.
     */
    private boolean m__bAutoCommit;

    /**
     * Open connections.
     */
    private int m__iOpenConnections = 0;

    /**
     * Call to connection.close() method count.
     */
    private int m__iCallsToConnectionClose = 0;

    /**
     * Call to connection.commit() method count.
     */
    private int m__iCallsToConnectionCommit = 0;

    /**
     * Call to connection.rollback() method count.
     */
    private int m__iCallsToConnectionRollback = 0;

    /**
     * Exception list.
     */
    private Collection m__cExceptions;

    /**
     * The log writer.
     */
    private PrintWriter m__pwLogWriter = null; 

    /**
     * The login timeout.
     */
    private int m__iLoginTimeout = 0;

    /**
     * Creates a MockDataSource using given URL for accessing the 
     * database.
     * @param driverClassName the driver's class name.
     * @param url the url that configures the access to the database.
     * @param username the user name.
     * @param password the password.
     */
    public MockDataSource(
        final String driverClassName,
        final String url,
        final String username,
        final String password)
    {
        immutableSetDriverClassName(driverClassName);
        immutableSetURL(url);
        immutableSetUserName(username);
        immutableSetPassword(password);
        setAutoCommit(false);
    }

    /**
     * Sets the driver's class name.
     * @param driverClassName the driver's class name.
     */
    private void immutableSetDriverClassName(String driverClassName)
    {
        m__strDriverClassName = driverClassName;
    }

    /**
     * Sets the driver's class name.
     * @param driverClassName the driver's class name.
     */
    protected void setDriverClassName(final String driverClassName)
    {
        immutableSetDriverClassName(driverClassName);
    }

    /**
     * Retrieves the driver's class name.
     * @return such class name.
     */
    public String getDriverClassName()
    {
        return m__strDriverClassName;
    }

    /**
     * Sets the URL connection.
     * @param url the URL connection.
     */
    private void immutableSetURL(final String url)
    {
        m__strURL = url;
    }

    /**
     * Sets the URL connection.
     * @param url the URL connection.
     */
    protected void setURL(final String url)
    {
        immutableSetURL(url);
    }

    /**
     * Retrieves the URL connection.
     * @return the URL connection.
     */
    public String getURL()
    {
        return m__strURL;
    }

    /**
     * Sets the user name.
     * @param username the user name.
     */
    private void immutableSetUserName(final String username)
    {
        m__strUserName = username;
    }

    /**
     * Sets the user name.
     * @param username the user name.
     */
    protected void setUserName(final String username)
    {
        immutableSetUserName(username);
    }

    /**
     * Retrieves the user name.
     * @return the user name.
     */
    public String getUserName()
    {
        return m__strUserName;
    }

    /**
     * Sets the password.
     * @param password the password.
     */
    private void immutableSetPassword(final String password)
    {
        m__strPassword = password;
    }

    /**
     * Sets the password.
     * @param password the password.
     */
    protected void setPassword(final String password)
    {
        immutableSetPassword(password);
    }

    /**
     * Retrieves the password.
     * @return the password.
     */
    public String getPassword()
    {
        return m__strPassword;
    }

    /**
     * Sets the auto commit.
     * @param autoCommit the flag value.
     */
    public void setAutoCommit(final boolean autoCommit)
    {
        m__bAutoCommit = autoCommit;
    }

    /**
     * Retrieves the auto commit.
     * @return such flag.
     */
    public boolean isAutoCommitEnabled()
    {
        return m__bAutoCommit;
    }

    /**
     * Specifies the exception collection.
     * @param collection the exceptions.
     */
    private void immutableSetExceptionCollection(final Collection collection)
    {
        m__cExceptions = collection;
    }

    /**
     * Specifies the exception collection.
     * @param collection the exceptions.
     */
    protected void setExceptionCollection(final Collection collection)
    {
        immutableSetExceptionCollection(collection);
    }

    /**
     * Retrieves the exception collection.
     * @return such collection.
     */
    protected Collection getExceptionCollection()
    {
        return m__cExceptions;
    }

    /**
     * Increases the number of opened connections.
     */
    public void increaseOpenedConnections()
    {
        m__iOpenConnections++;
    }

    public int getOpenedConnections()
    {
        return m__iOpenConnections;
    }

    /**
     * Increases the number of calls to Connection.close().
     */
    public void increaseCloseMethodCalls()
    {
        m__iCallsToConnectionClose++;
    }

    /**
     * Retrieves the number of calls to Connection.close();
     * @return the number of calls to such method.
     */
    public int getCloseMethodCalls()
    {
        return m__iCallsToConnectionClose;
    }

    /**
     * Increases the number of calls to Connection.commit().
     */
    void increaseCommitMethodCalls()
    {
        m__iCallsToConnectionCommit++;
    }

    /**
     * Retrieves the number of calls to Connection.commit();
     * @return the number of calls to such method.
     */
    public int getCommitMethodCalls()
    {
        return m__iCallsToConnectionCommit;
    }

    /**
     * Increases the number of calls to Connection.rollback().
     */
    public void increaseRollbackMethodCalls()
    {
        m__iCallsToConnectionCommit++;
    }

    /**
     * Retrieves the number of calls to Connection.rollback();
     * @return the number of calls to such method.
     */
    public int getRollbackMethodCalls()
    {
        return m__iCallsToConnectionRollback;
    }

    /**
     * Ensures the driver is registered.
     */
    protected void ensureDriverRegistered()
        throws  SQLException
    {
        Driver t_Driver = null;

        try 
        {
            t_Driver = DriverManager.getDriver(getURL());
        }
        catch  (SQLException sqlException)
        {
            LogFactory.getLog(getClass()).info(
                "Driver not yet registered. Performing registration.");
        }

        if  (t_Driver == null)
        {
            try
            {
                Class t_DriverClass = 
                    Class.forName(getDriverClassName());

                t_Driver = (Driver) t_DriverClass.newInstance();
            }
            catch  (Exception exception)
            {
                SQLException sqlException =
                    new SQLException(exception.getMessage());

                sqlException.initCause(exception);

                throw sqlException;
            }

            DriverManager.registerDriver(t_Driver);
        }
    }

    /**
     * Adds a new exception to the list.
     * @param exception the new exception to add.
     */
    public void addException(Exception exception)
    {
        if  (exception != null)
        {
            Collection t_cExceptions = getExceptionCollection();

            if  (t_cExceptions == null)
            {
                t_cExceptions = new ArrayList();

                setExceptionCollection(t_cExceptions);
            }

            t_cExceptions.add(exception);
        }
    }

    /**
     * Retrieves the list of exceptions.
     * @return the exception list.
     */
    public Iterator exceptionIterator()
    {
        Collection t_cResult = getExceptionCollection();

        if  (t_cResult == null)
        {
            t_cResult = new ArrayList();
            setExceptionCollection(t_cResult);
        }

        return t_cResult.iterator();
    }

    /**
     * Attempt to establish a database connection.
     * @return a Connection to the database
     * @throws java.sql.SQLException if a database-access error occurs.
     */
    public Connection getConnection()
        throws SQLException
    {
        MockConnection result = null;

        try
        {
            ensureDriverRegistered();

            if  (isAutoCommitEnabled())
            {
                result =
                    new MockConnection(
                        DriverManager.getConnection(
                            getURL(),
                            getUserName(),
                            getPassword()),
                        this);
            }
            else
            {
                result =
                    new AutoCommitDisabledMockConnection(
                        DriverManager.getConnection(
                            getURL(),
                            getUserName(),
                            getPassword()),
                        this);
            }

            increaseOpenedConnections();
        }
        catch  (SQLException sqlException)
        {
            addException(sqlException);

            throw sqlException;
        }

        return result;
    }

    /**
     * Attempts to establish a database connection.
     * @param username the database user on whose behalf the Connection
     * is being made.
     * @param password the user's password.
     * @return a Connection to the database
     * @throws SQLException if a database-access error occurs.
     */
    public Connection getConnection(String username, String password)
        throws SQLException
    {
        MockConnection result = null;

        try
        {
            ensureDriverRegistered();

            if  (isAutoCommitEnabled())
            {
                result =
                    new MockConnection(
                        DriverManager.getConnection(
                            getURL(),
                            username,
                            password),
                        this);
            }
            else
            {
                result =
                    new AutoCommitDisabledMockConnection(
                        DriverManager.getConnection(
                            getURL(),
                            username,
                            password),
                        this);
            }

            increaseOpenedConnections();
        }
        catch  (SQLException sqlException)
        {
            addException(sqlException);

            throw sqlException;
        }

        return result;
    }

    /**
     * Get the log writer for this data source.
     * The log writer is a character output stream to which all logging
     * and tracing messages for this data source object instance will be
     * printed.
     * This includes messages printed by the methods of this object,
     * messages printed by methods of other objects manufactured by this
     * object, and so on.
     * Messages printed to a data source specific log writer are not printed
     * to the log writer associated with the java.sql.Drivermanager class.
     * When a DataSource object is created the log writer is initially null,
     * in other words, logging is disabled.
     * @return the log writer for this data source, null if disabled.
     * @throws SQLException if a database-access error occurs.
     */
    public PrintWriter getLogWriter()
        throws SQLException
    {
        return m__pwLogWriter;
    }

    /**
     * Set the log writer for this data source.
     * The log writer is a character output stream to which all logging and
     * tracing messages for this data source object instance will be printed.
     * This includes messages printed by the methods of this object, messages
     * printed by methods of other objects manufactured by this object, and
     * so on.
     * Messages printed to a data source specific log writer are not printed
     * to the log writer associated with the java.sql.Drivermanager class.
     * When a DataSource object is created the log writer is initially null,
     * in other words, logging is disabled.
     * @param out the new log writer; to disable, set to null
     * @throws SQLException if a database-access error occurs.
     */
    public void setLogWriter(final PrintWriter out)
        throws SQLException
    {
        m__pwLogWriter = out;
    }

    /**
     * Sets the maximum time in seconds that this data source will wait
     * while attempting to connect to a database. A value of zero specifies
     * that the timeout is the default system timeout if there is one;
     * otherwise it specifies that there is no timeout. When a DataSource
     * object is created the login timeout is initially zero.
     * @param seconds the data source login time limit.
     * @throws SQLException if a database access error occurs.
     */
    public void setLoginTimeout(final int seconds)
        throws SQLException
    {
        m__iLoginTimeout = seconds;
    }

    /**
     * Gets the maximum time in seconds that this data source can wait while
     * attempting to connect to a database. A value of zero means that the
     * timeout is the default system timeout if there is one; otherwise it
     * means that there is no timeout. When a DataSource object is created
     * the login timeout is initially zero.
     * @return the data source login time limit.
     * @throws SQLException if a database access error occurs.
     */
    public int getLoginTimeout()
        throws SQLException
    {
        return m__iLoginTimeout;
    }
}
