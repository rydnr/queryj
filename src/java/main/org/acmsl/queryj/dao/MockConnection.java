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
    General Public License for more details.

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
 * Description: Wraps Connection objects and provides useful
 *              insight in unit testing.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package org.acmsl.queryj.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.dao.MockDataSource;

/*
 * Importing JDK classes.
 */
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.Savepoint;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Map;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Wraps Connection objects and provides useful insight in unit testing.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro Armendariz</a>
 * @version $Revision$
 */
public class MockConnection
    implements  Connection
{
    /**
     * Wrapped connection.
     */
    private Connection m__Connection;

    /**
     * Mock data source to send notifications of commits, closings and so on.
     */
    private MockDataSource m__MockDataSource;

    /**
     * The temporary auto-commit (in case connection set is null).
     */
    private boolean m__bTempAutoCommit = false; 

    /**
     * The temporary catalog (in case connection set is null).
     */
    private String m__strTempCatalog = null; 

    /**
     * The temporary read-only attribute (in case connection set is null).
     */
    private boolean m__bTempReadOnly = true; 

    /**
     * The temporary transaction-isolation level attribute (in case connection set is null).
     */
    private int m__iTempTransactionIsolation = -1; 

    /**
     * The temporary type map (in case connection set is null).
     */
    private Map m__mTempTypeMap = null; 

    /**
     * The temporary holdability attribute (in case connection set is null).
     */
    private int m__iTempHoldability = -1; 

    /**
     * Constructs a MockConnection using both objects.
     * @param connection the actual connection to wrap.
     * @param mockDataSource the data source to be notified of events.
     */
    public MockConnection(
        final Connection connection, final MockDataSource mockDataSource)
    {
        immutableSetConnection(connection);
        immutableSetMockDataSource(mockDataSource);
    }

    /**
     * Sets the wrapped connection.
     * @param connection the connection to be wrapped.
     */
    private void immutableSetConnection(final Connection connection)
    {
        m__Connection = connection;
    }

    /**
     * Sets the wrapped connection.
     * @param connection the connection to be wrapped.
     */
    protected void setConnection(final Connection connection)
    {
        immutableSetConnection(connection);
    }

    /**
     * Retrieves the wrapped connection.
     * @return the real connection.
     */
    public Connection getConnection()
    {
        return m__Connection;
    }

    /**
     * Sets the mock data source.
     * @param mockDataSource the mock data source.
     */
    private void immutableSetMockDataSource(final MockDataSource mockDataSource)
    {
        m__MockDataSource = mockDataSource;
    }

    /**
     * Sets the mock data source.
     * @param mockDataSource the mock data source.
     */
    protected void setMockDataSource(final MockDataSource mockDataSource)
    {
        immutableSetMockDataSource(mockDataSource);
    }

    /**
     * Retrieves the mock data source.
     * @return such mock object.
     */
    public MockDataSource getMockDataSource()
    {
        return m__MockDataSource;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Clears all warnings reported for this Connection object.
     * After a call to this method, the method getWarnings returns <code>null</code>
     * until a new warning is reported for this Connection object.
     * @exception SQLException if a database access error occurs.
     */
    public void clearWarnings()
        throws  SQLException
    {
        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                t_Connection.clearWarnings();
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Releases this Connection object's database and JDBC resources
     * immediately instead of waiting for them to be automatically released. 
     * Calling the method close on a Connection object that is already
     * closed is a no-op. 
     * Note: A Connection object is automatically closed when it is garbage
     * collected. Certain fatal errors also close a Connection object.
     * @exception SQLException if a database access error occurs.
     */
    public void close()
        throws  SQLException
    {
        MockDataSource t_MockDataSource = getMockDataSource();

        try
        {
            if  (t_MockDataSource != null)
            {
                t_MockDataSource.increaseCloseMethodCalls();
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                t_Connection.close();
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Makes all changes made since the previous commit/rollback
     * permanent and releases any database locks currently held by
     * this Connection object.
     * This method should be used only when auto-commit mode has
     * been disabled.
     * @exception SQLException if a database access error occurs
     * or this Connection object is in auto-commit mode.
     * @see #setAutoCommit(boolean)
     */
    public void commit()
        throws  SQLException
    {
        MockDataSource t_MockDataSource = getMockDataSource();

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                t_Connection.commit();
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.increaseCommitMethodCalls();
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }
        }
        catch  (SQLException sqlException)
        {
            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Creates a Statement object for sending SQL statements to the database.
     * SQL statements without parameters are normally executed using Statement
     * objects. If the same SQL statement is executed many times, it may be
     * more efficient to use a PreparedStatement object.
     * Result sets created using the returned Statement object will by default
     * be type TYPE_FORWARD_ONLY and have a concurrency level of CONCUR_READ_ONLY. 
     * @return a new default Statement object.
     * @exception SQLException if a database access error occurs.
     */
    public Statement createStatement()
        throws  SQLException
    {
        Statement result = null;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result = t_Connection.createStatement();
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Creates a Statement object that will generate ResultSet objects
     * with the given type and concurrency. This method is the same as
     * the createStatement method above, but it allows the default result
     * set type and concurrency to be overridden. 
     * @param resultSetType a result set type; one of
     * ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE,
     * or ResultSet.TYPE_SCROLL_SENSITIVE
     * @param resultSetConcurrency a concurrency type;
     * one of ResultSet.CONCUR_READ_ONLY or ResultSet.CONCUR_UPDATABLE
     * @return a new Statement object that will generate ResultSet
     * objects with the given type and concurrency.
     * @exception SQLException if a database access error occurs or the
     * given parameters are not ResultSet constants indicating type and
     * concurrency.
     * @since 1.2
     */
    public Statement createStatement(
            int resultSetType, int resultSetConcurrency)
        throws  SQLException
    {
        Statement result = null;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result =
                    t_Connection
                        .createStatement(
                            resultSetType,
                            resultSetConcurrency);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }


            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Creates a Statement object that will generate ResultSet objects
     * with the given type and concurrency. This method is the same as
     * the createStatement method above, but it allows the default result
     * set type and concurrency to be overridden. 
     * @param resultSetType a result set type; one of
     * ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE,
     * or ResultSet.TYPE_SCROLL_SENSITIVE.
     * @param resultSetConcurrency a concurrency type;
     * one of ResultSet.CONCUR_READ_ONLY or ResultSet.CONCUR_UPDATABLE.
     * @param resultSetHoldability one of the following ResultSet
     * constants: ResultSet.HOLD_CURSORS_OVER_COMMIT or
     * ResultSet.CLOSE_CURSORS_AT_COMMIT.
     * @return a new Statement object that will generate ResultSet
     * objects with the given type, concurrency and holdability.
     * @exception SQLException if a database access error occurs or the
     * given parameters are not ResultSet constants indicating type,
     * concurrency and holdability.
     * @since 1.4
     */
    public Statement createStatement(
            int resultSetType,
            int resultSetConcurrency,
            int resultSetHoldability)
        throws  SQLException
    {
        Statement result = null;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result =
                    t_Connection
                        .createStatement(
                            resultSetType,
                            resultSetConcurrency,
                            resultSetHoldability);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }


            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Retrieves the current auto-commit mode for this Connection object. 
     * @return the current state of this Connection object's auto-commit
     * mode.
     * @exception SQLException if a database access error occurs.
     * @see #setAutoCommit(boolean)
     */
    public boolean getAutoCommit()
        throws  SQLException
    {
        boolean result = m__bTempAutoCommit;

        Connection t_Connection = getConnection();

        if  (t_Connection != null)
        {
            result = t_Connection.getAutoCommit();
        }
        else 
        {
            LogFactory.getLog(getClass()).fatal(
                "Wrapped connection null");
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Retrieves this Connection object's current catalog name.
     * @return the current catalog name or <code>null</code> if there is none.
     * @exception SQLException if a database access error occurs.
     * @see #setCatalog(java.lang.String)
     */
    public String getCatalog()
        throws  SQLException
    {
        String result = m__strTempCatalog;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result = t_Connection.getCatalog();
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Retrieves a DatabaseMetaData object that contains metadata about
     * the database to which this Connection object represents a
     * connection. The metadata includes information about the
     * database's tables, its supported SQL grammar, its stored
     * procedures, the capabilities of this connection, and so on.
     * @return a DatabaseMetaData object for this Connection object.
     * @exception SQLException if a database access error occurs.
     */
    public DatabaseMetaData getMetaData()
        throws  SQLException
    {
        DatabaseMetaData result = null;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result = t_Connection.getMetaData();
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Retrieves this Connection object's current transaction isolation level.
     * @return the current transaction isolation level, which will be one of
     * the following constants: Connection.TRANSACTION_READ_UNCOMMITTED,
     * Connection.TRANSACTION_READ_COMMITTED,
     * Connection.TRANSACTION_REPEATABLE_READ,
     * Connection.TRANSACTION_SERIALIZABLE, or Connection.TRANSACTION_NONE.
     * @exception SQLException if a database access error occurs.
     * @see #setTransactionIsolation(int)
     */
    public int getTransactionIsolation()
        throws  SQLException
    {
        int result = m__iTempTransactionIsolation;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result = t_Connection.getTransactionIsolation();
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Retrieves the Map object associated with this Connection
     * object. Unless the application has added an entry, the
     * type map returned will be empty.
     * @return the java.util.Map object associated with this
     * Connection object.
     * @exception SQLException if a database access error occurs.
     * @since 1.2
     * @see #setTypeMap(java.util.Map)
     */
    public Map getTypeMap()
        throws  SQLException
    {
        Map result = m__mTempTypeMap;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result = t_Connection.getTypeMap();
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Retrieves the first warning reported by calls on
     * this Connection object. If there is more than one
     * warning, subsequent warnings will be chained to the
     * first one and can be retrieved by calling the
     * method SQLWarning.getNextWarning on the warning that
     * was retrieved previously.
     * This method may not be called on a closed connection;
     * doing so will cause an SQLException to be thrown. 
     * Note: Subsequent warnings will be chained to this SQLWarning.
     * @return the first SQLWarning object or <code>null</code>
     * if there are none.
     * @exception SQLException if a database access error occurs
     * or this method is called on a closed connection.
     * @see SQLWarning
     */
    public SQLWarning getWarnings()
        throws  SQLException
    {
        SQLWarning result = null;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result = t_Connection.getWarnings();
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Retrieves whether this Connection object has been
     * closed. A connection is closed if the method close
     * has been called on it or if certain fatal errors
     * have occurred. This method is guaranteed to return
     * <code>true</code> only when it is called after the
     * method Connection.close has been called.
     * This method generally cannot be called to determine
     * whether a connection to a database is valid or invalid.
     * A typical client can determine that a connection is
     * invalid by catching any exceptions that might be
     * thrown when an operation is attempted. 
     * @return <code>true</code> if this Connection object
     * is closed; <code>false</code> if it is still open 
     * @exception SQLException if a database access error occurs.
     */
    public boolean isClosed()
        throws  SQLException
    {
        boolean result = true;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result = t_Connection.isClosed();
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Retrieves whether this Connection object is in read-only mode. 
     * @return <code>true</code> if this Connection object is read-only;
     * <code>false</code> otherwise.
     * @exception SQLException if a database access error occurs.
     */
    public boolean isReadOnly()
        throws  SQLException
    {
        boolean result = m__bTempReadOnly;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result = t_Connection.isReadOnly();
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Converts the given SQL statement into the system's
     * native SQL grammar. A driver may convert the JDBC SQL
     * grammar into its system's native SQL grammar prior to
     * sending it. This method returns the native form of
     * the statement that the driver would have sent. 
     * @param sql an SQL statement that may contain one or
     * more '?' parameter placeholders.
     * @return the native form of this statement.
     * @exception SQLException if a database access error occurs.
    */
    public String nativeSQL(String sql)
        throws  SQLException
    {
        String result = null;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result = t_Connection.nativeSQL(sql);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Creates a CallableStatement object for calling database
     * stored procedures. The CallableStatement object provides
     * methods for setting up its IN and OUT parameters, and
     * methods for executing the call to a stored procedure. 
     * Note: This method is optimized for handling stored
     * procedure call statements. Some drivers may send the call
     * statement to the database when the method prepareCall is
     * done; others may wait until the CallableStatement object
     * is executed. This has no direct effect on users; however,
     * it does affect which method throws certain SQLExceptions.
     * Result sets created using the returned CallableStatement
     * object will by default be type TYPE_FORWARD_ONLY and have
     * a concurrency level of CONCUR_READ_ONLY. 
     * @param sql a SQL statement that may contain one or more
     * '?' parameter placeholders. Typically this statement is
     * a JDBC function call escape string. 
     * @return a new default CallableStatement object containing
     * the pre-compiled SQL statement.
     * @exception SQLException if a database access error occurs.
     */
    public CallableStatement prepareCall(String sql)
        throws  SQLException
    {
        CallableStatement result = null;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result = t_Connection.prepareCall(sql);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }


    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Creates a CallableStatement object that will generate ResultSet
     * objects with the given type and concurrency. This method is the
     * same as the prepareCall method above, but it allows the default
     * result set type and concurrency to be overridden. 
     * @param sql a String object that is the SQL statement to be sent
     * to the database; may contain on or more ? parameters.
     * @param resultSetType a result set type; one of
     * ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE,
     * or ResultSet.TYPE_SCROLL_SENSITIVE.
     * @param resultSetConcurrency a concurrency type; one of
     * ResultSet.CONCUR_READ_ONLY or ResultSet.CONCUR_UPDATABLE.
     * @return a new CallableStatement object containing the pre-compiled
     * SQL statement that will produce ResultSet objects with the given
     * type and concurrency.
     * @exception SQLException if a database access error occurs or the
     * given parameters are not ResultSet constants indicating type and
     * concurrency.
     * @since 1.2
     */
    public CallableStatement prepareCall(
            String sql, int resultSetType, int resultSetConcurrency)
        throws  SQLException
    {
        CallableStatement result = null;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result =
                    t_Connection
                        .prepareCall(
                            sql,
                            resultSetType,
                            resultSetConcurrency);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Creates a PreparedStatement object for sending parameterized
     * SQL statements to the database. 
     * A SQL statement with or without IN parameters can be pre-compiled
     * and stored in a PreparedStatement object. This object can then be
     * used to efficiently execute this statement multiple times. 
     * Note: This method is optimized for handling parametric SQL
     * statements that benefit from precompilation. If the driver supports
     * precompilation, the method prepareStatement will send the statement
     * to the database for precompilation. Some drivers may not support
     * precompilation. In this case, the statement may not be sent to the
     * database until the PreparedStatement object is executed. This has
     * no direct effect on users; however, it does affect which methods
     * throw certain SQLException objects. 
     * Result sets created using the returned PreparedStatement object will
     * by default be type TYPE_FORWARD_ONLY and have a concurrency level
     * of CONCUR_READ_ONLY. 
     * @param sql a SQL statement that may contain one or more '?' in
     * parameter placeholders.
     * @return a new default PreparedStatement object containing the
     * pre-compiled SQL statement.
     * @exception SQLException if a database access error occurs.
     */
    public PreparedStatement prepareStatement(String sql)
        throws  SQLException
    {
        PreparedStatement result = null;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result = t_Connection.prepareStatement(sql);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Creates a PreparedStatement object that will generate
     * ResultSet objects with the given type and concurrency.
     * This method is the same as the prepareStatement method
     * above, but it allows the default result set type and
     * concurrency to be overridden. 
     * @param sql a String object that is the SQL statement to be
     * sent to the database; may contain one or more ? IN parameters.
     * @param resultSetType a result set type; one of
     * ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE,
     * or ResultSet.TYPE_SCROLL_SENSITIVE.
     * @param resultSetConcurrency a concurrency type; one of
     * ResultSet.CONCUR_READ_ONLY or ResultSet.CONCUR_UPDATABLE.
     * @return a new PreparedStatement object containing the
     * pre-compiled SQL statement that will produce ResultSet
     * objects with the given type and concurrency.
     * @exception SQLException if a database access error occurs or
     * the given parameters are not ResultSet constants indicating
     * type and concurrency.
     * @since 1.2
     */
    public PreparedStatement prepareStatement(
            String sql, int resultSetType, int resultSetConcurrency)
        throws  SQLException
    {
        PreparedStatement result = null;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result =
                    t_Connection
                        .prepareStatement(
                            sql,
                            resultSetType,
                            resultSetConcurrency);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Undoes all changes made in the current transaction and
     * releases any database locks currently held by this Connection
     * object. This method should be used only when auto-commit mode
     * has been disabled. 
     * @exception SQLException if a database access error occurs or
     * this Connection object is in auto-commit mode.
     * @see #setAutoCommit(boolean)
     */
    public void rollback()
        throws  SQLException
    {
        MockDataSource t_MockDataSource = getMockDataSource();

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                t_Connection.rollback();
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.increaseRollbackMethodCalls();
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }
        }
        catch  (SQLException sqlException)
        {
            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Sets this connection's auto-commit mode to the given
     * state. If a connection is in auto-commit mode, then
     * all its SQL statements will be executed and committed
     * as individual transactions. Otherwise, its SQL statements
     * are grouped into transactions that are terminated by a
     * call to either the method commit or the method rollback.
     * By default, new connections are in auto-commit mode. 
     * The commit occurs when the statement completes or the
     * next execute occurs, whichever comes first. In the case
     * of statements returning a ResultSet object, the statement
     * completes when the last row of the ResultSet object has
     * been retrieved or the ResultSet object has been closed.
     * In advanced cases, a single statement may return multiple
     * results as well as output parameter values. In these cases,
     * the commit occurs when all results and output parameter
     * values have been retrieved. 
     * NOTE: If this method is called during a transaction,
     * the transaction is committed. 
     * @param autoCommit <code>true</code> to enable auto-commit
     * mode; <code>false</code> to disable it.
     * @exception SQLException if a database access error occurs
     * @see #getAutoCommit()
     */
    public void setAutoCommit(boolean autoCommit)
        throws  SQLException
    {
        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                t_Connection.setAutoCommit(autoCommit);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");

                m__bTempAutoCommit = autoCommit;
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Sets the given catalog name in order to select a subspace
     * of this Connection object's database in which to work. 
     * If the driver does not support catalogs, it will silently
     * ignore this request. 
     * @param catalog the name of a catalog (subspace in this
     * Connection object's database) in which to work.
     * @exception SQLException if a database access error occurs.
     * @see #getCatalog()
     */
    public void setCatalog(final String catalog)
        throws  SQLException
    {
        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                t_Connection.setCatalog(catalog);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");

                m__strTempCatalog = catalog;
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Puts this connection in read-only mode as a hint to the
     * driver to enable database optimizations. 
     * Note: This method cannot be called during a transaction. 
     * @param readOnly <code>true</code> enables read-only mode;
     * <code>false</code> disables it.
     * @exception SQLException if a database access error occurs
     * or this method is called during a transaction.
     */
    public void setReadOnly(final boolean readOnly)
        throws  SQLException
    {
        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                t_Connection.setReadOnly(readOnly);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");

                m__bTempReadOnly = readOnly;
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Attempts to change the transaction isolation level for
     * this Connection object to the one given. The constants
     * defined in the interface Connection are the possible
     * transaction isolation levels. 
     * Note: If this method is called during a transaction,
     * the result is implementation-defined. 
     * @param level one of the following Connection constants:
     * Connection.TRANSACTION_READ_UNCOMMITTED,
     * Connection.TRANSACTION_READ_COMMITTED,
     * Connection.TRANSACTION_REPEATABLE_READ,
     * or Connection.TRANSACTION_SERIALIZABLE.
     * (Note that Connection.TRANSACTION_NONE cannot be used
     * because it specifies that transactions are not supported.)
     * @exception SQLException if a database access error occurs
     * or the given parameter is not one of the Connection constants.
     * @see java.sql.DatabaseMetaData#supportsTransactionIsolationLevel(int)
     * @see #getTransactionIsolation()
     */
    public void setTransactionIsolation(final int level)
        throws  SQLException
    {
        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                t_Connection.setTransactionIsolation(level);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");

                m__iTempTransactionIsolation = level;
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Installs the given TypeMap object as the type map for this
     * Connection object. The type map will be used for the custom
     * mapping of SQL structured types and distinct types.
     * @param map the java.util.Map object to install as the
     * replacement for this Connection object's default type map.
     * @exception SQLException if a database access error occurs
     * or the given parameter is not a java.util.Map object.
     * @since 1.2
     * @see #getTypeMap()
     */
    public void setTypeMap(final Map map)
        throws  SQLException
    {
        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                t_Connection.setTypeMap(map);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");

                m__mTempTypeMap = map;
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Changes the holdability of ResultSet objects created
     * using this Connection object to the given holdability.
     * @param holdability a ResultSet holdability constant;
     * one of ResultSet.HOLD_CURSORS_OVER_COMMIT or
     * ResultSet.CLOSE_CURSORS_AT_COMMIT
     * @exception SQLException if a database access occurs,
     * the given parameter is not a ResultSet constant indicating
     * holdability, or the given holdability is not supported.
     * @since 1.4
     * @see #getHoldability()
     * @see java.sql.ResultSet
     */
    public void setHoldability(final int holdability)
        throws  SQLException
    {
        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                t_Connection.setHoldability(holdability);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");

                m__iTempHoldability = holdability;
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Retrieves the current holdability of ResultSet objects
     * created using this Connection object. 
     * @return the holdability, one of
     * ResultSet.HOLD_CURSORS_OVER_COMMIT or
     * ResultSet.CLOSE_CURSORS_AT_COMMIT
     * @exception SQLException if a database access occurs
     * @since 1.4
     * @see #setHoldability(int)
     * @see java.sql.ResultSet
     */
    public int getHoldability()
        throws  SQLException
    {
        int result = m__iTempHoldability;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result = t_Connection.getHoldability();
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Creates an unnamed savepoint in the current transaction
     * and returns the new Savepoint object that represents it.
     * @return the new Savepoint object.
     * @exception SQLException if a database access error occurs
     * or this Connection object is currently in auto-commit mode.
     * @since 1.4
     * @see Savepoint
     */
    public Savepoint setSavepoint()
        throws  SQLException
    {
        Savepoint result = null;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result = t_Connection.setSavepoint();
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Creates a savepoint with the given name in the current
     * transaction and returns the new Savepoint object that
     * represents it. 
     * @param savepoint a String containing the name of the savepoint.
     * @return the new Savepoint object.
     * @exception SQLException if a database access error occurs
     * or this Connection object is currently in auto-commit mode
     * @since 1.4
     * @see Savepoint
     */
    public Savepoint setSavepoint(final String savepoint)
        throws  SQLException
    {
        Savepoint result = null;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result = t_Connection.setSavepoint(savepoint);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Undoes all changes made after the given Savepoint object was set.
     * This method should be used only when auto-commit has been disabled.
     * @param savepoint the Savepoint object to roll back to.
     * @exception SQLException if a database access error occurs, the
     * Savepoint object is no longer valid, or this Connection object is
     * currently in auto-commit mode.
     * @since 1.4
     * @see Savepoint
     * @see #rollback()
     */
    public void rollback(final Savepoint savepoint)
        throws  SQLException
    {
        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                t_Connection.rollback(savepoint);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Removes the given Savepoint object from the current transaction.
     * Any reference to the savepoint after it have been removed will
     * cause an SQLException to be thrown.
     * @param savepoint the Savepoint object to be removed.
     * @exception SQLException  if a database access error occurs or
     * the given Savepoint object is not a valid savepoint in the
     * current transaction.
     * @since 1.4
     */
    public void releaseSavepoint(final Savepoint savepoint)
        throws  SQLException
    {
        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                t_Connection.releaseSavepoint(savepoint);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Creates a PreparedStatement object that will generate ResultSet
     * objects with the given type, concurrency, and holdability.
     * This method is the same as the prepareStatement method above,
     * but it allows the default result set type, concurrency, and
     * holdability to be overridden.
     * @param sql a String object that is the SQL statement to be sent
     * to the database; may contain one or more ? IN parameters.
     * @param resultSetType one of the following ResultSet constants:
     * ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE,
     * or ResultSet.TYPE_SCROLL_SENSITIVE
     * @param resultSetConcurrency one of the following ResultSet
     * constants: ResultSet.CONCUR_READ_ONLY or
     * ResultSet.CONCUR_UPDATABLE
     * @param resultSetHoldability one of the following ResultSet
     * constants: ResultSet.HOLD_CURSORS_OVER_COMMIT or
     * ResultSet.CLOSE_CURSORS_AT_COMMIT.
     * @return a new PreparedStatement object, containing the
     * pre-compiled SQL statement, that will generate ResultSet objects
     * with the given type, concurrency, and holdability.
     * @exception SQLException if a database access error occurs or the
     * given parameters are not ResultSet constants indicating type,
     * concurrency, and holdability.
     * @since 1.4
     * @see java.sql.ResultSet
     */
    public PreparedStatement prepareStatement(
        final String sql,
        final int    resultSetType,
        final int    resultSetConcurrency,
        final int    resultSetHoldability)
      throws  SQLException
    {
        PreparedStatement result = null;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result =
                    t_Connection
                        .prepareStatement(
                            sql,
                            resultSetType,
                            resultSetConcurrency, 
                            resultSetHoldability);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Creates a CallableStatement object that will generate ResultSet
     * objects with the given type and concurrency. This method is the
     * same as the prepareCall method above, but it allows the default
     * result set type, result set concurrency type and holdability to
     * be overridden. 
     * @param sql a String object that is the SQL statement to be sent
     * to the database; may contain on or more ? parameters.
     * @param resultSetType one of the following ResultSet constants:
     * ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE,
     * or ResultSet.TYPE_SCROLL_SENSITIVE.
     * @param resultSetConcurrency one of the following ResultSet
     * constants: ResultSet.CONCUR_READ_ONLY or
     * ResultSet.CONCUR_UPDATABLE.
     * @param resultSetHoldability one of the following ResultSet
     * constants: ResultSet.HOLD_CURSORS_OVER_COMMIT or
     * ResultSet.CLOSE_CURSORS_AT_COMMIT.
     * @return a new CallableStatement object, containing the
     * pre-compiled SQL statement, that will generate ResultSet
     * objects with the given type, concurrency, and holdability.
     * @exception SQLException if a database access error occurs or
     * the given parameters are not ResultSet constants indicating
     * type, concurrency, and holdability.
     * @since 1.4
     * @see java.sql.ResultSet
     */
    public CallableStatement prepareCall(
        final String sql,
        final int    resultSetType,
        final int    resultSetConcurrency,
        final int    resultSetHoldability)
      throws  SQLException
    {
        CallableStatement result = null;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result =
                    t_Connection
                        .prepareCall(
                            sql,
                            resultSetType,
                            resultSetConcurrency, 
                            resultSetHoldability);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Creates a default PreparedStatement object that has the
     * capability to retrieve auto-generated keys. The given
     * constant tells the driver whether it should make auto-generated
     * keys available for retrieval. This parameter is ignored if the
     * SQL statement is not an INSERT statement.
     * Note: This method is optimized for handling parametric SQL
     * statements that benefit from precompilation. If the driver
     * supports precompilation, the method prepareStatement will
     * send the statement to the database for precompilation.
     * Some drivers may not support precompilation. In this case,
     * the statement may not be sent to the database until the
     * PreparedStatement object is executed. This has no direct
     * effect on users; however, it does affect which methods throw
     * certain SQLExceptions. 
     * Result sets created using the returned PreparedStatement object
     * will by default be type TYPE_FORWARD_ONLY and have a concurrency
     * level of CONCUR_READ_ONLY. 
     * @param sql an SQL statement that may contain one or more '?'
     * IN parameter placeholders.
     * 2param autoGeneratedKeys a flag indicating whether auto-generated
     * keys should be returned; one of Statement.RETURN_GENERATED_KEYS
     * or Statement.NO_GENERATED_KEYS.
     * @return a new PreparedStatement object, containing the pre-compiled
     * SQL statement, that will have the capability of returning
     * auto-generated keys.
     * @exception SQLException if a database access error occurs or the
     * given parameter is not a Statement constant indicating whether
     * auto-generated keys should be returned.
     * @since 1.4
     */
    public PreparedStatement prepareStatement(
        final String sql, final int autoGeneratedKeys)
      throws  SQLException
    {
        PreparedStatement result = null;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result =
                    t_Connection
                        .prepareStatement(
                            sql, autoGeneratedKeys);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Creates a default PreparedStatement object capable of returning
     * the auto-generated keys designated by the given array. This array
     * contains the indexes of the columns in the target table that
     * contain the auto-generated keys that should be made available.
     * This array is ignored if the SQL statement is not an INSERT statement. 
     * An SQL statement with or without IN parameters can be pre-compiled
     * and stored in a PreparedStatement object. This object can then be
     * used to efficiently execute this statement multiple times.
     * Note: This method is optimized for handling parametric SQL statements
     * that benefit from precompilation. If the driver supports precompilation,
     * the method prepareStatement will send the statement to the database
     * for precompilation. Some drivers may not support precompilation.
     * In this case, the statement may not be sent to the database until
     * the PreparedStatement object is executed. This has no direct effect
     * on users; however, it does affect which methods throw certain
     * SQLExceptions. 
     * Result sets created using the returned PreparedStatement object will
     * by default be type TYPE_FORWARD_ONLY and have a concurrency level
     * of CONCUR_READ_ONLY. 
     * @param sql an SQL statement that may contain one or more '?' in
     * parameter placeholders.
     * @param columnIndexes an array of column indexes indicating the columns
     * that should be returned from the inserted row or rows.
     * @return a new PreparedStatement object, containing the pre-compiled
     * statement, that is capable of returning the auto-generated keys
     * designated by the given array of column indexes.
     * @exception SQLException if a database access error occurs.
     * @since 1.4
     */
    public PreparedStatement prepareStatement(
        final String sql, final int[] columnIndexes)
      throws  SQLException
    {
        PreparedStatement result = null;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result =
                    t_Connection
                        .prepareStatement(
                            sql, columnIndexes);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }

    /**
     * Taken from Sun's JDK 1.4 documentation:
     * Creates a default PreparedStatement object capable of
     * returning the auto-generated keys designated by the given
     * array. This array contains the names of the columns in the
     * target table that contain the auto-generated keys that should
     * be returned. This array is ignored if the SQL statement is
     * not an INSERT statement.
     * An SQL statement with or without IN parameters can be
     * pre-compiled and stored in a PreparedStatement object.
     * This object can then be used to efficiently execute this
     * statement multiple times. 
     * Note: This method is optimized for handling parametric SQL
     * statements that benefit from precompilation. If the driver
     * supports precompilation, the method prepareStatement will
     * send the statement to the database for precompilation. Some
     * drivers may not support precompilation. In this case, the
     * statement may not be sent to the database until the
     * PreparedStatement object is executed. This has no direct effect
     * on users; however, it does affect which methods throw certain
     * SQLExceptions. 
     * Result sets created using the returned PreparedStatement object
     * will by default be type TYPE_FORWARD_ONLY and have a concurrency
     * level of CONCUR_READ_ONLY. 
     * @param sql an SQL statement that may contain one or more '?'
     * IN parameter placeholders.
     * @param columnNames an array of column names indicating the
     * columns that should be returned from the inserted row or rows.
     * @return a new PreparedStatement object, containing the pre-compiled
     * statement, that is capable of returning the auto-generated keys
     * designated by the given array of column names.
     * @exception SQLException if a database access error occurs.
     * @since 1.4
     */
    public PreparedStatement prepareStatement(
        final String sql, final String[] columnNames)
      throws  SQLException
    {
        PreparedStatement result = null;

        try
        {
            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                result =
                    t_Connection
                        .prepareStatement(
                            sql, columnNames);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped connection null");
            }
        }
        catch  (SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(getClass()).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }
}
