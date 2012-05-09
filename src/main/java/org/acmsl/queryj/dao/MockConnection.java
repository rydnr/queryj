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
 * Filename: MockConnection.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Wraps Connection objects and provides useful
 *              insight in unit testing.
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
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Wraps Connection objects and provides useful insight in unit testing.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro Armendariz</a>
 */
public class MockConnection
    implements  Connection
{
    /**
     * Constructs an object that implements the <code>Clob</code> interface. The object
     * returned initially contains no data.  The <code>setAsciiStream</code>,
     * <code>setCharacterStream</code> and <code>setString</code> methods of
     * the <code>Clob</code> interface may be used to add data to the <code>Clob</code>.
     *
     * @return An object that implements the <code>Clob</code> interface
     * @throws java.sql.SQLException if an object that implements the
     *                               <code>Clob</code> interface can not be constructed, this method is
     *                               called on a closed connection or a database access error occurs.
     * @throws java.sql.SQLFeatureNotSupportedException
     *                               if the JDBC driver does not support
     *                               this data type
     * @since 1.6
     */
    @Override
    public Clob createClob() throws SQLException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Constructs an object that implements the <code>Blob</code> interface. The object
     * returned initially contains no data.  The <code>setBinaryStream</code> and
     * <code>setBytes</code> methods of the <code>Blob</code> interface may be used to add data to
     * the <code>Blob</code>.
     *
     * @return An object that implements the <code>Blob</code> interface
     * @throws java.sql.SQLException if an object that implements the
     *                               <code>Blob</code> interface can not be constructed, this method is
     *                               called on a closed connection or a database access error occurs.
     * @throws java.sql.SQLFeatureNotSupportedException
     *                               if the JDBC driver does not support
     *                               this data type
     * @since 1.6
     */
    @Override
    public Blob createBlob() throws SQLException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Constructs an object that implements the <code>NClob</code> interface. The object
     * returned initially contains no data.  The <code>setAsciiStream</code>,
     * <code>setCharacterStream</code> and <code>setString</code> methods of the <code>NClob</code> interface may
     * be used to add data to the <code>NClob</code>.
     *
     * @return An object that implements the <code>NClob</code> interface
     * @throws java.sql.SQLException if an object that implements the
     *                               <code>NClob</code> interface can not be constructed, this method is
     *                               called on a closed connection or a database access error occurs.
     * @throws java.sql.SQLFeatureNotSupportedException
     *                               if the JDBC driver does not support
     *                               this data type
     * @since 1.6
     */
    @Override
    public NClob createNClob() throws SQLException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Constructs an object that implements the <code>SQLXML</code> interface. The object
     * returned initially contains no data. The <code>createXmlStreamWriter</code> object and
     * <code>setString</code> method of the <code>SQLXML</code> interface may be used to add data to the
     * <code>SQLXML</code>
     * object.
     *
     * @return An object that implements the <code>SQLXML</code> interface
     * @throws java.sql.SQLException if an object that implements the <code>SQLXML</code> interface can not
     *                               be constructed, this method is
     *                               called on a closed connection or a database access error occurs.
     * @throws java.sql.SQLFeatureNotSupportedException
     *                               if the JDBC driver does not support
     *                               this data type
     * @since 1.6
     */
    @Override
    public SQLXML createSQLXML() throws SQLException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns true if the connection has not been closed and is still valid.
     * The driver shall submit a query on the connection or use some other
     * mechanism that positively verifies the connection is still valid when
     * this method is called.
     * <p/>
     * The query submitted by the driver to validate the connection shall be
     * executed in the context of the current transaction.
     *
     * @param timeout -		The time in seconds to wait for the database operation
     *                used to validate the connection to complete.  If
     *                the timeout period expires before the operation
     *                completes, this method returns false.  A value of
     *                0 indicates a timeout is not applied to the
     *                database operation.
     *                <p/>
     * @return true if the connection is valid, false otherwise
     * @throws java.sql.SQLException if the value supplied for <code>timeout</code>
     *                               is less then 0
     * @see java.sql.DatabaseMetaData#getClientInfoProperties
     * @since 1.6
     *        <p/>
     */
    @Override
    public boolean isValid(final int timeout) throws SQLException
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Sets the value of the client info property specified by name to the
     * value specified by value.
     * <p/>
     * Applications may use the <code>DatabaseMetaData.getClientInfoProperties</code>
     * method to determine the client info properties supported by the driver
     * and the maximum length that may be specified for each property.
     * <p/>
     * The driver stores the value specified in a suitable location in the
     * database.  For example in a special register, session parameter, or
     * system table column.  For efficiency the driver may defer setting the
     * value in the database until the next time a statement is executed or
     * prepared.  Other than storing the client information in the appropriate
     * place in the database, these methods shall not alter the behavior of
     * the connection in anyway.  The values supplied to these methods are
     * used for accounting, diagnostics and debugging purposes only.
     * <p/>
     * The driver shall generate a warning if the client info name specified
     * is not recognized by the driver.
     * <p/>
     * If the value specified to this method is greater than the maximum
     * length for the property the driver may either truncate the value and
     * generate a warning or generate a <code>SQLClientInfoException</code>.  If the driver
     * generates a <code>SQLClientInfoException</code>, the value specified was not set on the
     * connection.
     * <p/>
     * The following are standard client info properties.  Drivers are not
     * required to support these properties however if the driver supports a
     * client info property that can be described by one of the standard
     * properties, the standard property name should be used.
     * <p/>
     * <ul>
     * <li>ApplicationName	-	The name of the application currently utilizing
     * the connection</li>
     * <li>ClientUser		-	The name of the user that the application using
     * the connection is performing work for.  This may
     * not be the same as the user name that was used
     * in establishing the connection.</li>
     * <li>ClientHostname	-	The hostname of the computer the application
     * using the connection is running on.</li>
     * </ul>
     * <p/>
     *
     * @param name  The name of the client info property to set
     * @param value The value to set the client info property to.  If the
     *              value is null, the current value of the specified
     *              property is cleared.
     *              <p/>
     * @throws java.sql.SQLClientInfoException if the database server returns an error while
     * setting the client info value on the database server or this method
     * is called on a closed connection
     * <p/>
     * @since 1.6
     */
    @Override
    public void setClientInfo(final String name, final String value) throws SQLClientInfoException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Sets the value of the connection's client info properties.  The
     * <code>Properties</code> object contains the names and values of the client info
     * properties to be set.  The set of client info properties contained in
     * the properties list replaces the current set of client info properties
     * on the connection.  If a property that is currently set on the
     * connection is not present in the properties list, that property is
     * cleared.  Specifying an empty properties list will clear all of the
     * properties on the connection.  See <code>setClientInfo (String, String)</code> for
     * more information.
     * <p/>
     * If an error occurs in setting any of the client info properties, a
     * <code>SQLClientInfoException</code> is thrown. The <code>SQLClientInfoException</code>
     * contains information indicating which client info properties were not set.
     * The state of the client information is unknown because
     * some databases do not allow multiple client info properties to be set
     * atomically.  For those databases, one or more properties may have been
     * set before the error occurred.
     * <p/>
     *
     * @param properties the list of client info properties to set
     *                   <p/>
     * @throws java.sql.SQLClientInfoException
     *          if the database server returns an error while
     *          setting the clientInfo values on the database server or this method
     *          is called on a closed connection
     *          <p/>
     * @see java.sql.Connection#setClientInfo(String, String) setClientInfo(String, String)
     * @since 1.6
     *        <p/>
     */
    @Override
    public void setClientInfo(final Properties properties) throws SQLClientInfoException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns the value of the client info property specified by name.  This
     * method may return null if the specified client info property has not
     * been set and does not have a default value.  This method will also
     * return null if the specified client info property name is not supported
     * by the driver.
     * <p/>
     * Applications may use the <code>DatabaseMetaData.getClientInfoProperties</code>
     * method to determine the client info properties supported by the driver.
     * <p/>
     *
     * @param name The name of the client info property to retrieve
     *             <p/>
     * @return The value of the client info property specified
     *         <p/>
     * @throws java.sql.SQLException if the database server returns an error when
     *                               fetching the client info value from the database
     *                               or this method is called on a closed connection
     *                               <p/>
     * @see java.sql.DatabaseMetaData#getClientInfoProperties
     * @since 1.6
     *        <p/>
     */
    @Override
    public String getClientInfo(final String name) throws SQLException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns a list containing the name and current value of each client info
     * property supported by the driver.  The value of a client info property
     * may be null if the property has not been set and does not have a
     * default value.
     * <p/>
     *
     * @throws java.sql.SQLException if the database server returns an error when
     *                               fetching the client info values from the database
     *                               or this method is called on a closed connection
     *                               <p/>
     * @return A <code>Properties</code> object that contains the name and current value of
     * each of the client info properties supported by the driver.
     * <p/>
     * @since 1.6
     */
    @Override
    public Properties getClientInfo() throws SQLException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Factory method for creating Array objects.
     * <p/>
     * <b>Note: </b>When <code>createArrayOf</code> is used to create an array object
     * that maps to a primitive data type, then it is implementation-defined
     * whether the <code>Array</code> object is an array of that primitive
     * data type or an array of <code>Object</code>.
     * <p/>
     * <b>Note: </b>The JDBC driver is responsible for mapping the elements
     * <code>Object</code> array to the default JDBC SQL type defined in
     * java.sql.Types for the given class of <code>Object</code>. The default
     * mapping is specified in Appendix B of the JDBC specification.  If the
     * resulting JDBC type is not the appropriate type for the given typeName then
     * it is implementation defined whether an <code>SQLException</code> is
     * thrown or the driver supports the resulting conversion.
     *
     * @param typeName the SQL name of the type the elements of the array map to. The typeName is a
     *                 database-specific name which may be the name of a built-in type,
     *                 a user-defined type or a standard  SQL type supported by this database. This
     *                 is the value returned by <code>Array.getBaseTypeName</code>
     * @param elements the elements that populate the returned object
     * @return an Array object whose elements map to the specified SQL type
     * @throws java.sql.SQLException if a database error occurs, the JDBC type is not
     *                               appropriate for the typeName and the conversion is not supported,
     *                               the typeName is null or this method is called on a closed connection
     * @throws java.sql.SQLFeatureNotSupportedException
     *                               if the JDBC driver does not support this data type
     * @since 1.6
     */
    @Override
    public Array createArrayOf(final String typeName, final Object[] elements) throws SQLException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Factory method for creating Struct objects.
     *
     * @param typeName   the SQL type name of the SQL structured type that this <code>Struct</code>
     *                   object maps to. The typeName is the name of  a user-defined type that
     *                   has been defined for this database. It is the value returned by
     *                   <code>Struct.getSQLTypeName</code>.
     * @param attributes the attributes that populate the returned object
     * @return a Struct object that maps to the given SQL type and is populated with the given attributes
     * @throws java.sql.SQLException if a database error occurs, the typeName is null or this method is called on a
     * closed connection
     * @throws java.sql.SQLFeatureNotSupportedException
     *                               if the JDBC driver does not support this data type
     * @since 1.6
     */
    @Override
    public Struct createStruct(final String typeName, final Object[] attributes) throws SQLException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns an object that implements the given interface to allow access to
     * non-standard methods, or standard methods not exposed by the proxy.
     * <p/>
     * If the receiver implements the interface then the result is the receiver
     * or a proxy for the receiver. If the receiver is a wrapper
     * and the wrapped object implements the interface then the result is the
     * wrapped object or a proxy for the wrapped object. Otherwise return the
     * the result of calling <code>unwrap</code> recursively on the wrapped object
     * or a proxy for that result. If the receiver is not a
     * wrapper and does not implement the interface, then an <code>SQLException</code> is thrown.
     *
     * @param iface A Class defining an interface that the result must implement.
     * @return an object that implements the interface. May be a proxy for the actual implementing object.
     * @throws java.sql.SQLException If no object found that implements the interface
     * @since 1.6
     */
    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns true if this either implements the interface argument or is directly or indirectly a wrapper
     * for an object that does. Returns false otherwise. If this implements the interface then return true,
     * else if this is a wrapper then return the result of recursively calling <code>isWrapperFor</code> on the wrapped
     * object. If this does not implement the interface and is not a wrapper, return false.
     * This method should be implemented as a low-cost operation compared to <code>unwrap</code> so that
     * callers can use this method to avoid expensive <code>unwrap</code> calls that may fail. If this method
     * returns true then calling <code>unwrap</code> with the same argument should succeed.
     *
     * @param iface a Class defining an interface.
     * @return true if this implements the interface or directly or indirectly wraps an object that does.
     * @throws java.sql.SQLException if an error occurs while determining whether this is a wrapper
     *                               for an object with the given interface.
     * @since 1.6
     */
    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
                try
                {
                    LogFactory.getLog(MockConnection.class).fatal(
                        "Wrapped connection null");
                }
                catch  (final Throwable throwable)
                {
                    // class-loading problem.
                }
            }
        }
        catch  (final SQLException sqlException)
        {
            MockDataSource t_MockDataSource = getMockDataSource();

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.addException(sqlException);
            }
            else 
            {
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
                    "Wrapped data source null");
            }

            Connection t_Connection = getConnection();

            if  (t_Connection != null)
            {
                t_Connection.close();
            }
            else 
            {
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
                    "Wrapped connection null");
            }

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.increaseCommitMethodCalls();
            }
            else 
            {
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
            LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
                    "Wrapped connection null");
            }

            if  (t_MockDataSource != null)
            {
                t_MockDataSource.increaseRollbackMethodCalls();
            }
            else 
            {
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
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
                LogFactory.getLog(MockConnection.class).fatal(
                    "Wrapped data source null");
            }

            throw sqlException;
        }

        return result;
    }
}
