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
 * Description: Represents queries to access persistent data.
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
package org.acmsl.queryj;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryResultSet;

/*
 * Importing some JDK1.3 classes.
 */
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Calendar;
import java.util.List;

/*
 * Importing Jakarta Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Represents queries to access persistent data.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class Query
    implements  PreparedStatement
{
    /**
     * The wrapped statement.
     */
    private PreparedStatement m__PreparedStatement;

    /**
     * The fields.
     */
    private List m__lFields;

    /**
     * The tables.
     */
    private List m__lTables;

    /**
     * The conditions.
     */
    private List m__lConditions;

    /**
     * The variable conditions.
     */
    private List m__lVariableConditions;

    /**
     * Constructs a query.
     */
    public Query()
    {
        inmutableSetFields(new ArrayList());
        inmutableSetTables(new ArrayList());
        inmutableSetConditions(new ArrayList());
        inmutableSetVariableConditions(new ArrayList());
    }

    /**
     * Specifies the statement.
     * @param statement the prepared statement.
     */
    protected void setPreparedStatement(PreparedStatement statement)
    {
        m__PreparedStatement = statement;
    }

    /**
     * Retrieves the statement.
     * @return such statement.
     */
    protected PreparedStatement getPreparedStatement()
    {
        return m__PreparedStatement;
    }

    /**
     * Specifies new field collection.
     * @param list the new list.
     */
    private void inmutableSetFields(List list)
    {
        m__lFields = list;
    }

    /**
     * Specifies new field collection.
     * @param list the new list.
     */
    protected void setFields(List list)
    {
        inmutableSetFields(list);
    }

    /**
     * Retrieves the field collection.
     * @return such list.
     */
    protected List getFields()
    {
        return m__lFields;
    }

    /**
     * Adds a new field.
     * @param field the field to add.
     */
    protected void addField(Field field)
    {
        if  (field != null) 
        {
            List t_Fields = getFields();

            if  (t_Fields != null) 
            {
                t_Fields.add(field);
            }
        }
    }

    /**
     * Retrieves the position of given field on the query.
     * @param field the field to find.
     * @return its position, or -1 if such field doesn't belong to
     * this query.
     */
    protected int getFieldIndex(Field field)
    {
        return getIndex(getFields(), field);
    }

    /**
     * Specifies new table collection.
     * @param list the new list.
     */
    private void inmutableSetTables(List list)
    {
        m__lTables = list;
    }

    /**
     * Specifies new table collection.
     * @param list the new list.
     */
    protected void setTables(List list)
    {
        inmutableSetTables(list);
    }

    /**
     * Retrieves the table collection.
     * @return such list.
     */
    protected List getTables()
    {
        return m__lTables;
    }

    /**
     * Adds a new table.
     * @param table the table to add.
     */
    protected void addTable(Table table)
    {
        if  (table != null) 
        {
            List t_Tables = getTables();

            if  (t_Tables != null) 
            {
                t_Tables.add(table);
            }
        }
    }

    /**
     * Specifies new condition collection.
     * @param list the new list.
     */
    private void inmutableSetConditions(List list)
    {
        m__lConditions = list;
    }

    /**
     * Specifies new condition collection.
     * @param list the new list.
     */
    protected void setConditions(List list)
    {
        inmutableSetConditions(list);
    }

    /**
     * Retrieves the condition collection.
     * @return such list.
     */
    protected List getConditions()
    {
        return m__lConditions;
    }

    /**
     * Adds a new condition.
     * @param condition the condition to add.
     */
    protected void addCondition(Condition condition)
    {
        if  (condition != null) 
        {
            List t_Conditions = getConditions();

            if  (t_Conditions != null) 
            {
                t_Conditions.add(condition);

                Collection t_cNewVariableConditions =
                    condition.getVariableConditions();

                if  (t_cNewVariableConditions == null)
                {
                    t_cNewVariableConditions = new ArrayList();
                }

                List t_lVariableConditions = getVariableConditions();

                if  (t_lVariableConditions == null)
                {
                    t_lVariableConditions = new ArrayList();

                    setVariableConditions(t_lVariableConditions);
                }

                t_lVariableConditions.addAll(t_cNewVariableConditions);
            }
        }
    }

    /**
     * Specifies new variable condition collection.
     * @param list the new list.
     */
    private void inmutableSetVariableConditions(List list)
    {
        m__lVariableConditions = list;
    }

    /**
     * Specifies new variable condition collection.
     * @param list the new list.
     */
    protected void setVariableConditions(List list)
    {
        inmutableSetVariableConditions(list);
    }

    /**
     * Retrieves the variable condition collection.
     * @return such list.
     */
    protected List getVariableConditions()
    {
        return m__lVariableConditions;
    }

    /**
     * Adds a new variable condition.
     * @param variableCondition the variable condition to add.
     */
    protected void addVariableCondition(
        VariableCondition variableCondition)
    {
        if  (variableCondition != null) 
        {
            List t_lVariableConditions = getVariableConditions();

            if  (t_lVariableConditions == null)
            {
                t_lVariableConditions = new ArrayList();
                setVariableConditions(t_lVariableConditions);
            }

            t_lVariableConditions.add(variableCondition);

            Collection t_cNewVariableConditions =
                variableCondition.getVariableConditions();

            if  (   (t_cNewVariableConditions != null)
                 && (t_cNewVariableConditions.size() > 0))
            {
                t_lVariableConditions.addAll(t_cNewVariableConditions);
            }
        }
    }

    /**
     * Retrieves the position of given item on the query.
     * @param list the concrete list (fields, tables, conditions, etc.).
     * @param object the object to find.
     * @return its position, or -1 if such item doesn't belong to
     * this query.
     */
    protected int getIndex(List list, Object object)
    {
        int result = -1;

        if  (   (list   != null)
             && (object != null))
        {
            result = list.indexOf(object);

            if  (result != list.lastIndexOf(object))
            {
                list.set(result, list.indexOf(object) + "");
            }

            result++;
        }
        
        return result;
    }

    // Helper methods //

    /**
     * Prepares a statement.
     * @param connection the JDBC connection.
     * @return the statement.
     * @exception SQLException if an error occurs.
     */
    public PreparedStatement prepareStatement(Connection connection)
        throws  SQLException
    {
        PreparedStatement result = this;

        if  (connection != null)
        {
            setPreparedStatement(connection.prepareStatement(toString()));
        }

        return result;
    }

    // Implementation of java.sql.Statement //

    /**
     * See java.sql.Statement#close().
     * @see java.sql.Statement#close()
     * @exception SQLException if an error occurs.
     */
    public void close()
        throws  SQLException
    {
        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            t_Statement.close();
        }
    }

    /**
     * See java.sql.Statement#execute(String).
     * @see java.sql.Statement#execute(String)
     * @param sql (Taken from Sun's Javadoc) any SQL statement.
     * @return (Taken from Sun's Javadoc) <code>true</code> if the first
     * result is a ResultSet object; false if it is an update count
     * or there are no results.
     * @exception SQLException if an error occurs.
     */
    public boolean execute(String sql)
        throws  SQLException
    {
        boolean result = false;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.execute(sql);
        }

        return result;
    }

    /**
     * See java.sql.Statement#getConnection().
     * @see java.sql.Statement#getConnection()
     * @return (Taken from Sun's Javadoc) the connection that produced
     * this statement.
     * @exception SQLException if an error occurs
     */
    public Connection getConnection()
        throws  SQLException
    {
        Connection result = null;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.getConnection();
        }

        return result;
    }

    /**
     * See java.sql.Statement#getWarnings().
     * @see java.sql.Statement#getWarnings()
     * @return (Taken from Sun's Javadoc) the first SQLWarning object
     * or null if there are no warnings .
     * @exception SQLException if an error occurs.
     */
    public SQLWarning getWarnings()
        throws  SQLException
    {
        SQLWarning result = null;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.getWarnings();
        }

        return result;
    }

    /**
     * See java.sql.Statement#clearWarnings().
     * @see java.sql.Statement#clearWarnings()
     * @exception SQLException if an error occurs
     */
    public void clearWarnings()
        throws  SQLException
    {
        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            t_Statement.clearWarnings();
        }
    }

    /**
     * See java.sql.Statement#executeQuery(String).
     * @see java.sql.Statement#executeQuery(String)
     * @param sql (Taken from Sun's Javadoc) an SQL statement to be sent
     * to the database, typically a static SQL SELECT statement.
     * @return (Taken from Sun's Javadoc) a ResultSet object that
     * contains the data produced by the given query; never null. 
     * @exception SQLException if an error occurs.
     */
    public ResultSet executeQuery(String sql)
        throws  SQLException
    {
        ResultSet result = null;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.executeQuery(sql);
        }

        return result;
    }

    /**
     * See java.sql.Statement#executeUpdate(String).
     * @see java.sql.Statement#executeUpdate(String)
     * @param sql (Taken from Sun's Javadoc) an SQL INSERT, UPDATE or
     * DELETE statement or an SQL statement that returns nothing.
     * @return (Taken from Sun's Javadoc) a ResultSet object that
     * contains the data produced by the given query; never null.
     * @exception SQLException if an error occurs.
     */
    public int executeUpdate(String sql)
        throws  SQLException
    {
        int result = 0;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.executeUpdate(sql);
        }

        return result;
    }

    /**
     * See java.sql.Statement#getMaxFieldSize().
     * @see java.sql.Statement#getMaxFieldSize()
     * @return (Taken from Sun's Javadoc) the current
     * column size limit for columns storing character
     * and binary values; zero means there is no limit.
     * @exception SQLException if an error occurs.
     */
    public int getMaxFieldSize()
        throws  SQLException
    {
        int result = 0;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.getMaxFieldSize();
        }

        return result;
    }

    /**
     * See java.sql.Statement#setMaxFieldSize(int).
     * @see java.sql.Statement#setMaxFieldSize(int)
     * @param size (Taken from Sun's Javadoc) the new column size
     * limit in bytes; zero means there is no limit.
     * @exception SQLException if an error occurs
     */
    public void setMaxFieldSize(int size)
        throws  SQLException
    {
        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            t_Statement.setMaxFieldSize(size);
        }
    }

    /**
     * See java.sql.Statement#getMaxRows().
     * @see java.sql.Statement#getMaxRows()
     * @return (Taken from Sun's Javadoc) the current maximum
     * number of rows for a ResultSet object produced by this
     * Statement object; zero means there is no limit.
     * @exception SQLException if an error occurs
     */
    public int getMaxRows()
        throws  SQLException
    {
        int result = 0;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.getMaxRows();
        }

        return result;
    }

    /**
     * See java.sql.Statement#setMaxRows(int).
     * @see java.sql.Statement#setMaxRows(int)
     * @param max (Taken from Sun's Javadoc) the new max rows
     * limit; zero means there is no limit.
     * @exception SQLException if an error occurs
     */
    public void setMaxRows(int max)
        throws  SQLException
    {
        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            t_Statement.setMaxRows(max);
        }
    }

    /**
     * See java.sql.Statement#setEscapeProcessing(boolean).
     * @see java.sql.Statement#setEscapeProcessing(boolean)
     * @param flag (Taken from Sun's Javadoc) <code>true</code>
     * to enable escape processing; <code>false</code> to
     * disable it.
     * @exception SQLException if an error occurs
     */
    public void setEscapeProcessing(boolean flag)
        throws  SQLException
    {
        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            t_Statement.setEscapeProcessing(flag);
        }
    }

    /**
     * See java.sql.Statement#getQueryTimeout().
     * @see java.sql.Statement#getQueryTimeout()
     * @return (Taken from Sun's Javadoc) the current query
     * timeout limit in seconds; zero means there is no limit.
     * @exception SQLException if an error occurs.
     */
    public int getQueryTimeout()
        throws  SQLException
    {
        int result = 0;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.getQueryTimeout();
        }

        return result;
    }

    /**
     * See java.sql.Statement#setQueryTimeout(int).
     * @see java.sql.Statement#setQueryTimeout(int)
     * @param timeout (Taken from Sun's Javadoc) the new query
     * timeout limit in seconds; zero means there is no limit.
     * @exception SQLException if an error occurs
     */
    public void setQueryTimeout(int timeout)
        throws SQLException
    {
        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            t_Statement.setQueryTimeout(timeout);
        }
    }

    /**
     * See java.sql.Statement#cancel().
     * @see java.sql.Statement#cancel()
     * @exception SQLException if an error occurs.
     */
    public void cancel()
        throws  SQLException
    {
        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            t_Statement.cancel();
        }
    }

    /**
     * See java.sql.Statement#setCursorName(String).
     * @see java.sql.Statement#setCursorName(String)
     * @param name (Taken from Sun's Javadoc) the new
     * cursor name, which must be unique within a connection.
     * @exception SQLException if an error occurs.
     */
    public void setCursorName(String name)
        throws  SQLException
    {
        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            t_Statement.setCursorName(name);
        }
    }

    /**
     * See java.sql.Statement#getResultSet().
     * @see java.sql.Statement#getResultSet()
     * @return (Taken from Sun's Javadoc) the current result
     * as a ResultSet object or null if the result is an
     * update count or there are no more results.
     * @exception SQLException if an error occurs.
     */
    public ResultSet getResultSet()
        throws  SQLException
    {
        ResultSet result = null;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.getResultSet();
        }

        return result;
    }

    /**
     * See java.sql.Statement#getUpdateCount().
     * @see java.sql.Statement#getUpdateCount()
     * @return (Taken from Sun's Javadoc) the current
     * result as an update count; -1 if the current result
     * is a ResultSet object or there are no more results.
     * @exception SQLException if an error occurs.
     */
    public int getUpdateCount()
        throws  SQLException
    {
        int result = 0;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.getUpdateCount();
        }

        return result;
    }

    /**
     * See java.sql.Statement#getMoreResults().
     * @see java.sql.Statement#getMoreResults()
     * @return (Taken from Sun's Javadoc) <code>true</code>
     * if the next result is a ResultSet object;
     * <code>false</code> if it is an update count
     * or there are no more results.
     * @exception SQLException if an error occurs.
     */
    public boolean getMoreResults()
        throws  SQLException
    {
        boolean result = false;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.getMoreResults();
        }

        return result;
    }

    /**
     * See java.sql.Statement#setFetchDirection(int).
     * @see java.sql.Statement#setFetchDirection(int)
     * @param direction (Taken from Sun's Javadoc) the initial
     * direction for processing rows.
     * @exception SQLException if an error occurs.
     */
    public void setFetchDirection(int direction)
        throws  SQLException
    {
        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            t_Statement.setFetchDirection(direction);
        }
    }

    /**
     * See java.sql.Statement#getFetchDirection().
     * @see java.sql.Statement#getFetchDirection()
     * @return (Taken from Sun's Javadoc) the default
     * fetch direction for result sets generated
     * from this Statement object.
     * @exception SQLException if an error occurs.
     */
    public int getFetchDirection()
        throws  SQLException
    {
        int result = 0;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.getFetchDirection();
        }

        return result;
    }

    /**
     * See java.sql.Statement#setFetchSize(int).
     * @see java.sql.Statement#setFetchSize(int)
     * @param size (Taken from Sun's Javadoc) the number of rows to fetch.
     * @exception SQLException if an error occurs.
     */
    public void setFetchSize(int size)
        throws  SQLException
    {
        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            t_Statement.setFetchSize(size);
        }
    }

    /**
     * See java.sql.Statement#getFetchSize().
     * @see java.sql.Statement#getFetchSize()
     * @return (Taken from Sun's Javadoc) the default fetch
     * size for result sets generated from this Statement object.
     * @exception SQLException if an error occurs.
     */
    public int getFetchSize()
        throws  SQLException
    {
        int result = 0;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.getFetchSize();
        }

        return result;
    }

    /**
     * See java.sql.Statement#getResultSetConcurrency().
     * @see java.sql.Statement#getResultSetConcurrency()
     * @return (Taken from Sun's Javadoc) either
     * ResultSet.CONCUR_READ_ONLY or ResultSet.CONCUR_UPDATABLE.
     * @exception SQLException if an error occurs.
     */
    public int getResultSetConcurrency()
        throws  SQLException
    {
        int result = 0;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.getResultSetConcurrency();
        }

        return result;
    }

    /**
     * See java.sql.Statement#getResultSetType().
     * @see java.sql.Statement#getResultSetType()
     * @return (Taken from Sun's Javadoc) one of
     * ResultSet.TYPE_FORWARD_ONLY,
     * ResultSet.TYPE_SCROLL_INSENSITIVE, or
     * ResultSet.TYPE_SCROLL_SENSITIVE.
     * @exception SQLException if an error occurs.
     */
    public int getResultSetType()
        throws  SQLException
    {
        int result = 0;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.getResultSetType();
        }

        return result;
    }

    /**
     * See java.sql.Statement#addBatch(String).
     * @see java.sql.Statement#addBatch(String)
     * @param sql (Taken from Sun's Javadoc) typically this
     * is a static SQL INSERT or UPDATE statement.
     * @exception SQLException if an error occurs.
     */
    public void addBatch(String sql)
        throws  SQLException
    {
        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            t_Statement.addBatch(sql);
        }
    }

    /**
     * See java.sql.Statement#clearBatch().
     * @see java.sql.Statement#clearBatch()
     * @exception SQLException if an error occurs
     */
    public void clearBatch()
        throws  SQLException
    {
        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            t_Statement.clearBatch();
        }
    }

    /**
     * See java.sql.Statement#executeBatch().
     * @see java.sql.Statement#executeBatch()
     * @return (Taken from Sun's Javadoc) an array of update
     * counts containing one element for each command in the
     * batch. The elements of the array are ordered according
     * to the order in which commands were added to the batch.
     * @exception SQLException if an error occurs.
     */
    public int[] executeBatch()
        throws  SQLException
    {
        int[] result = new int[0];

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.executeBatch();
        }

        return result;
    }
    
    // Implementation of java.sql.PreparedStatement //

    /**
     * See java.sql.PreparedStatement#setTime(int,java.sql.Time).
     * @see java.sql.PreparedStatement#setTime(int,java.sql.Time)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param time (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setTime(int index, Time time)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setTime(index, time);
        }
    }

    /**
     * See java.sql.PreparedStatement#setTime(int,Time,Calendar).
     * @see java.sql.PreparedStatement#setTime(int,java.sql.Time,java.util.Calendar)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param time (Taken from Sun's Javadoc) the parameter value (!!).
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the time.
     * @exception SQLException if an error occurs.
     */
    public void setTime(int index, Time time, Calendar calendar)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setTime(index, time, calendar);
        }
    }

    /**
     * See java.sql.PreparedStatement#execute().
     * @see java.sql.PreparedStatement#execute()
     * @return (Taken from Sun's Javadoc) <code>true</code> if
     * the first result is a ResultSet object; <code>false</code>
     * if the first result is an update count or there is no result.
     * @exception SQLException if an error occurs.
     */
    public boolean execute()
        throws  SQLException
    {
        boolean result = false;

        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            result = t_PreparedStatement.execute();
        }

        return result;
    }

    /**
     * See java.sql.PreparedStatement#executeQuery().
     * @see java.sql.PreparedStatement#executeQuery()
     * @return (Taken from Sun's Javadoc) a ResultSet object
     * that contains the data produced by the query; never null.
     * @exception SQLException if an error occurs.
     */
    public ResultSet executeQuery()
        throws  SQLException
    {
        ResultSet result = null;

        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null)
        {
            result =
                new QueryResultSet(
                    this, t_PreparedStatement.executeQuery()) {};
        }

        return result;
    }

    /**
     * See java.sql.PreparedStatement#setBoolean(int,boolean).
     * @see java.sql.PreparedStatement#setBoolean(int,boolean)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param flag (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setBoolean(int index, boolean flag)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setBoolean(index, flag);
        }
    }

    /**
     * See java.sql.PreparedStatement#setByte(int,byte).
     * @see java.sql.PreparedStatement#setByte(int,byte)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param b (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setByte(int index, byte b)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null)
        {
            t_PreparedStatement.setByte(index, b);
        }
    }

    /**
     * See java.sql.PreparedStatement#setShort(int,short).
     * @see java.sql.PreparedStatement#setShort(int,short)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param s (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setShort(int index, short s)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setShort(index, s);
        }
    }

    /**
     * See java.sql.PreparedStatement#setInt(int,int).
     * @see java.sql.PreparedStatement#setInt(int,int)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setInt(int index, int value)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setInt(index, value);
        }
    }

    /**
     * See java.sql.PreparedStatement#setLong(int,long).
     * @see java.sql.PreparedStatement#setLong(int,long)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setLong(int index, long value)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setLong(index, value);
        }
    }

    /**
     * See java.sql.PreparedStatement#setFloat(int,float).
     * @see java.sql.PreparedStatement#setFloat(int,float)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setFloat(int index, float value)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setFloat(index, value);
        }
    }

    /**
     * See java.sql.PreparedStatement#setDouble(int,double).
     * @see java.sql.PreparedStatement#setDouble(int,double)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setDouble(int index, double value)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setDouble(index, value);
        }
    }

    /**
     * See java.sql.PreparedStatement#getMetaData().
     * @see java.sql.PreparedStatement#getMetaData()
     * @return (Taken from Sun's Javadoc) a ParameterMetaData
     * object that contains information about the number,
     * types and properties of this PreparedStatement
     * object's parameters.
     * @exception SQLException if an error occurs.
     */
    public ResultSetMetaData getMetaData()
        throws  SQLException
    {
        ResultSetMetaData result = null;

        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            result = t_PreparedStatement.getMetaData();
        }

        return result;
    }

    /**
     * See java.sql.PreparedStatement#executeUpdate().
     * @see java.sql.PreparedStatement#executeUpdate()
     * @return (Taken from Sun's Javadoc) either (1) the row
     * count for INSERT, UPDATE, or DELETE statements or
     * (2) 0 for SQL statements that return nothing.
     * @exception SQLException if an error occurs.
     */
    public int executeUpdate()
        throws  SQLException
    {
        int result = 0;

        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            result = t_PreparedStatement.executeUpdate();
        }

        return result;
    }

    /**
     * See java.sql.PreparedStatement#addBatch().
     * @see java.sql.PreparedStatement#addBatch()
     * @exception SQLException if an error occurs.
     */
    public void addBatch()
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.addBatch();
        }
    }

    /**
     * See java.sql.PreparedStatement#setNull(int,int).
     * @see java.sql.PreparedStatement#setNull(int,int)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param sqlType (Taken from Sun's Javadoc) the SQL type
     * code defined in java.sql.Types.
     * @exception SQLException if an error occurs.
     */
    public void setNull(int index, int sqlType)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setNull(index, sqlType);
        }
    }

    /**
     * See java.sql.PreparedStatement#setNull(int,int,String).
     * @see java.sql.PreparedStatement#setNull(int,int,String)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param sqlType (Taken from Sun's Javadoc) the SQL type
     * code defined in java.sql.Types.
     * @param typeName (Taken from Sun's Javadoc) the fully-qualified
     * name of an SQL user-defined type; ignored if the
     * parameter is not a user-defined type or REF.
     * @exception SQLException if an error occurs.
     */
    public void setNull(int index, int sqlType, String typeName)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setNull(index, sqlType, typeName);
        }
    }

    /**
     * See java.sql.PreparedStatement#setString(int,String).
     * @see java.sql.PreparedStatement#setString(int,String)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setString(int index, String value)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setString(index, value);
        }
    }

    /**
     * See java.sql.PreparedStatement#clearParameters().
     * @see java.sql.PreparedStatement#clearParameters()
     * @exception SQLException if an error occurs.
     */
    public void clearParameters()
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.clearParameters();
        }
    }

    /**
     * See java.sql.PreparedStatement#setArray(int,Array).
     * @see java.sql.PreparedStatement#setArray(int,java.sql.Array)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setArray(int index, Array value)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setArray(index, value);
        }
    }

    /**
     * See java.sql.PreparedStatement#setAsciiStream(int,InputStram,int).
     * @see java.sql.PreparedStatement#setAsciiStream(int,java.io.InputStream,int)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param inputStream (Taken from Sun's Javadoc) the Java input stream
     * that contains the ASCII parameter value.
     * @param length (Taken from Sun's Javadoc) the number of bytes
     * in the stream.
     * @exception SQLException if an error occurs.
     */
    public void setAsciiStream(
            int         index,
            InputStream inputStream,
            int         length)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setAsciiStream(index, inputStream, length);
        }
    }

    /**
     * See java.sql.PreparedStatement#setBigDecimal(int,BigDecimal).
     * @see java.sql.PreparedStatement#setBigDecimal(int,java.math.BigDecimal)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setBigDecimal(int index, BigDecimal value)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setBigDecimal(index, value);
        }
    }

    /**
     * See java.sql.PreparedStatement#setBinaryStream(int,InputStream).
     * @see java.sql.PreparedStatement#setBinaryStream(int,java.io.InputStream,int)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param inputStream (Taken from Sun's Javadoc) the Java input stream
     * that contains the binary parameter value.
     * @param length (Taken from Sun's Javadoc) the number of bytes
     * in the stream.
     * @exception SQLException if an error occurs.
     */
    public void setBinaryStream(
            int         index,
            InputStream inputStream,
            int         length)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement
                .setBinaryStream(index, inputStream, length);
        }
    }

    /**
     * See java.sql.PreparedStatement#setBlob(int,Blob).
     * @see java.sql.PreparedStatement#setBlob(int,java.sql.Blob)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setBlob(int index, Blob value)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setBlob(index, value);
        }
    }

    /**
     * See java.sql.PreparedStatement#setBytes(int,byte[]).
     * @see java.sql.PreparedStatement#setBytes(int,byte[])
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setBytes(int index, byte[] value)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setBytes(index, value);
        }
    }

    /**
     * See java.sql.PreparedStatement#setCharacterStream(int,Reader,int).
     * @see java.sql.PreparedStatement#setCharacterStream(int,java.io.Reader,int)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param reader (Taken from Sun's Javadoc) the java.io.Reader
     * object that contains the Unicode data.
     * @param length (Taken from Sun's Javadoc) the number of bytes
     * in the stream.
     * @exception SQLException if an error occurs.
     */
    public void setCharacterStream(
            int     index,
            Reader  reader,
            int     length)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setCharacterStream(index, reader, length);
        }
    }

    /**
     * See java.sql.PreparedStatement#setClob(int,Clob).
     * @see java.sql.PreparedStatement#setClob(int,java.sql.Clob)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setClob(int index, Clob value)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setClob(index, value);
        }
    }

    /**
     * See java.sql.PreparedStatement#setDate(int,Date).
     * @see java.sql.PreparedStatement#setDate(int,java.sql.Date)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setDate(int index, Date value)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setDate(index, value);
        }
    }

    /**
     * See java.sql.PreparedStatement#setDate(int,Date,Calendar).
     * @see java.sql.PreparedStatement#setDate(int,java.sql.Date,java.util.Calendar)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the time.
     * @exception SQLException if an error occurs.
     */
    public void setDate(int index, Date value, Calendar calendar)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setDate(index, value, calendar);
        }
    }

    /**
     * See java.sql.PreparedStatement#setObject(int,Object,int,int).
     * @see java.sql.PreparedStatement#setObject(int,Object,int,int)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @param sqlType (Taken from Sun's Javadoc) the SQL type
     * (as defined in java.sql.Types) to be sent to the database.
     * The scale argument may further qualify this type.
     * @param scale (Taken from Sun's Javadoc) for
     * java.sql.Types.DECIMAL or java.sql.Types.NUMERIC types,
     * this is the number of digits after the decimal point. For all
     * other types, this value will be ignored.
     * @exception SQLException if an error occurs.
     */
    public void setObject(int index, Object value, int sqlType, int scale)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setObject(index, value, sqlType, scale);
        }
    }

    /**
     * See java.sql.PreparedStatement#setObject(int,Object,int).
     * @see java.sql.PreparedStatement#setObject(int,Object,int)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @param sqlType (Taken from Sun's Javadoc) the SQL type
     * (as defined in java.sql.Types) to be sent to the database.
     * The scale argument may further qualify this type.
     * @exception SQLException if an error occurs.
     */
    public void setObject(int index, Object value, int sqlType)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setObject(index, value, sqlType);
        }
    }

    /**
     * See java.sql.PreparedStatement#setObject(int,Object).
     * @see java.sql.PreparedStatement#setObject(int,Object)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setObject(int index, Object value)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setObject(index, value);
        }
    }

    /**
     * See java.sql.PreparedStatement#setRef(int,Ref).
     * @see java.sql.PreparedStatement#setRef(int,java.sql.Ref)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setRef(int index, Ref value)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setRef(index, value);
        }
    }

    /**
     * See java.sql.PreparedStatement#setTimestamp(int,Timestamp).
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @exception SQLException if an error occurs.
     */
    public void setTimestamp(int index, Timestamp value)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setTimestamp(index, value);
        }
    }

    /**
     * See java.sql.PreparedStatement#setTimestamp(int,Timestamp,Calendar).
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp,java.util.Calendar)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the time.
     * @exception SQLException if an error occurs.
     */
    public void setTimestamp(int index, Timestamp value, Calendar calendar)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setTimestamp(index, value, calendar);
        }
    }

    /**
     * See java.sql.PreparedStatement#setUnicodeStream(int,InputStream,int).
     * @see java.sql.PreparedStatement#setUnicodeStream(int,java.io.InputStream,int)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param inputStream (Taken from Sun's Javadoc) the Java input stream
     * that contains the Unicode parameter value.
     * @param length (Taken from Sun's Javadoc) the number of bytes
     * in the stream.
     * @exception SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     */
    public void setUnicodeStream(
            int         index,
            InputStream inputStream,
            int         length)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement =
            getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setUnicodeStream(index, inputStream, length);
        }
    }

    // End of java.sql.PreparedStatement //

    /**
     * Specifies the value of a time parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the time value.
     * @see java.sql.PreparedStatement#setTime(int,java.sql.Time)
     * @exception SQLException if an error occurs.
     */
    public void setTime(VariableCondition condition, Time value)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setTime(t_iIndex, value);
            }
        }
    }

    /**
     * Specifies the value of a time parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the time value.
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the time.
     * @see java.sql.PreparedStatement#setTime(int,java.sql.Time)
     * @exception SQLException if an error occurs.
     */
    public void setTime(
            VariableCondition condition,
            Time              value,
            Calendar          calendar)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setTime(t_iIndex, value, calendar);
            }
        }
    }

    /**
     * Specifies the value of a boolean parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the boolean value.
     * @see java.sql.PreparedStatement#setBoolean(int,boolean)
     * @exception SQLException if an error occurs.
     */
    public void setBoolean(VariableCondition condition, boolean value)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setBoolean(t_iIndex, value);
            }
        }
    }

    /**
     * Specifies the value of a byte parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the byte value.
     * @see java.sql.PreparedStatement#setByte(int,byte)
     * @exception SQLException if an error occurs.
     */
    public void setByte(VariableCondition condition, byte value)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setByte(t_iIndex, value);
            }
        }
    }

    /**
     * Specifies the value of a short parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the short value.
     * @see java.sql.PreparedStatement#setShort(int,short)
     * @exception SQLException if an error occurs.
     */
    public void setShort(VariableCondition condition, short value)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setShort(t_iIndex, value);
            }
        }
    }

    /**
     * Specifies the value of an int parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the int value.
     * @see java.sql.PreparedStatement#setInt(int,int)
     * @exception SQLException if an error occurs.
     */
    public void setInt(VariableCondition condition, int value)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setInt(t_iIndex, value);
            }
        }
    }

    /**
     * Specifies the value of a long parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the long value.
     * @see java.sql.PreparedStatement#setLong(int,long)
     * @exception SQLException if an error occurs.
     */
    public void setLong(VariableCondition condition, long value)
        throws  SQLException
    {
        LogFactory.getLog(getClass()).debug(
            "Searching for condition " + condition + " in query " + this);

        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            LogFactory.getLog(getClass()).debug(
                "condition found at " + t_iIndex);

            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setLong(t_iIndex, value);
            }
        }
    }

    /**
     * Specifies the value of a float parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the float value.
     * @see java.sql.PreparedStatement#setFloat(int,float)
     * @exception SQLException if an error occurs.
     */
    public void setFloat(VariableCondition condition, float value)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setFloat(t_iIndex, value);
            }
        }
    }

    /**
     * Specifies the value of a double parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the double value.
     * @see java.sql.PreparedStatement#setDouble(int,double)
     * @exception SQLException if an error occurs.
     */
    public void setDouble(VariableCondition condition, double value)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setDouble(t_iIndex, value);
            }
        }
    }

    /**
     * Specifies as <code>null</code> the value of a parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param sqlType (Taken from Sun's Javadoc) the SQL type
     * code defined in java.sql.Types.
     * @see java.sql.PreparedStatement#setNull(int,int)
     * @exception SQLException if an error occurs.
     */
    public void setNull(VariableCondition condition, int sqlType)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setNull(t_iIndex, sqlType);
            }
        }
    }

    /**
     * Specifies as <code>null</code> the value of a parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param sqlType (Taken from Sun's Javadoc) the SQL type
     * code defined in java.sql.Types.
     * @param typeName (Taken from Sun's Javadoc) the fully-qualified
     * name of an SQL user-defined type; ignored if the
     * parameter is not a user-defined type or REF.
     * @see java.sql.PreparedStatement#setNull(int,int,String)
     * @exception SQLException if an error occurs.
     */
    public void setNull(
            VariableCondition  condition,
            int                sqlType,
            String             typeName)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setNull(t_iIndex, sqlType, typeName);
            }
        }
    }

    /**
     * Specifies the value of a String parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the String value.
     * @see java.sql.PreparedStatement#setString(int,String)
     * @exception SQLException if an error occurs.
     */
    public void setString(VariableCondition condition, String value)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0)
        {
            setString(t_iIndex, value);
        }
    }

    /**
     * Specifies the value of an Array parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the Array value.
     * @see java.sql.PreparedStatement#setArray(int,java.sql.Array)
     * @exception SQLException if an error occurs.
     */
    public void setArray(VariableCondition condition, Array value)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setArray(t_iIndex, value);
            }
        }
    }

    /**
     * Specifies the value of a parameter formatted as an ASCII stream,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param inputStream (Taken from Sun's Javadoc) the Java input stream
     * that contains the ASCII parameter value.
     * @param length (Taken from Sun's Javadoc) the number of bytes
     * in the stream.
     * @see java.sql.PreparedStatement#setAsciiStream(int,java.io.InputStream,int)
     * @exception SQLException if an error occurs.
     */
    public void setAsciiStream(
            VariableCondition condition,
            InputStream       inputStream,
            int               length)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement
                    .setAsciiStream(t_iIndex, inputStream, length);
            }
        }
    }

    /**
     * Specifies the value of a BigDecimal parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the big decimal value.
     * @see java.sql.PreparedStatement#setBigDecimal(int,java.math.BigDecimal)
     * @exception SQLException if an error occurs.
     */
    public void setBigDecimal(
            VariableCondition  condition,
            BigDecimal         value)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setBigDecimal(t_iIndex, value);
            }
        }
    }

    /**
     * Specifies the value of a parameter formatted as a binary stream,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param inputStream (Taken from Sun's Javadoc) the Java input stream
     * that contains the binary parameter value.
     * @param length (Taken from Sun's Javadoc) the number of bytes
     * in the stream.
     * @see java.sql.PreparedStatement#setBinaryStream(int,java.io.InputStream,int)
     * @exception SQLException if an error occurs.
     */
    public void setBinaryStream(
            VariableCondition condition,
            InputStream       inputStream,
            int               length)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement
                    .setBinaryStream(t_iIndex, inputStream, length);
            }
        }
    }

    /**
     * Specifies the value of a blob parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the blob value.
     * @see java.sql.PreparedStatement#setBlob(int,java.sql.Blob)
     * @exception SQLException if an error occurs.
     */
    public void setBlob(VariableCondition condition, Blob value)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setBlob(t_iIndex, value);
            }
        }
    }

    /**
     * Specifies the value of a byte array parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the byte array value.
     * @see java.sql.PreparedStatement#setBytes(int,byte[])
     * @exception SQLException if an error occurs.
     */
    public void setBytes(VariableCondition condition, byte[] value)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setBytes(t_iIndex, value);
            }
        }
    }

    /**
     * Specifies the value of a parameter formatted as a binary stream,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param reader (Taken from Sun's Javadoc) the java.io.Reader object
     * that contains the Unicode data.
     * @param length (Taken from Sun's Javadoc) the number of bytes
     * in the stream.
     * @see java.sql.PreparedStatement#setCharacterStream(int,java.io.Reader,int)
     * @exception SQLException if an error occurs.
     */
    public void setCharacterStream(
            VariableCondition condition,
            Reader            reader,
            int               length)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement
                    .setCharacterStream(t_iIndex, reader, length);
            }
        }
    }

    /**
     * Specifies the value of a clob parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the clob value.
     * @see java.sql.PreparedStatement#setClob(int,java.sql.Clob)
     * @exception SQLException if an error occurs.
     */
    public void setClob(VariableCondition condition, Clob value)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setClob(t_iIndex, value);
            }
        }
    }

    /**
     * Specifies the value of a date parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the date value.
     * @see java.sql.PreparedStatement#setDate(int,java.sql.Date)
     * @exception SQLException if an error occurs.
     */
    public void setDate(VariableCondition condition, Date value)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setDate(t_iIndex, value);
            }
        }
    }

    /**
     * Specifies the value of a date parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the date value.
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the date.
     * @see java.sql.PreparedStatement#setDate(int,java.sql.Date)
     * @exception SQLException if an error occurs.
     */
    public void setDate(
            VariableCondition condition,
            Date              value,
            Calendar          calendar)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setDate(t_iIndex, value, calendar);
            }
        }
    }

    /**
     * Specifies the value of an object parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the object value.
     * @param sqlType (Taken from Sun's Javadoc) the SQL type
     * (as defined in java.sql.Types) to be sent to the database.
     * The scale argument may further qualify this type.
     * @param scale (Taken from Sun's Javadoc) for
     * java.sql.Types.DECIMAL or java.sql.Types.NUMERIC types,
     * this is the number of digits after the decimal point. For all
     * other types, this value will be ignored.
     * @see java.sql.PreparedStatement#setObject(int,Object,int,int)
     * @exception SQLException if an error occurs.
     */
    public void setObject(
            VariableCondition  condition,
            Object             value,
            int                sqlType,
            int                scale)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement
                    .setObject(t_iIndex, value, sqlType, scale);
            }
        }
    }

    /**
     * Specifies the value of an object parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the object value.
     * @param sqlType (Taken from Sun's Javadoc) the SQL type
     * (as defined in java.sql.Types) to be sent to the database.
     * @see java.sql.PreparedStatement#setObject(int,Object,int)
     * @exception SQLException if an error occurs.
     */
    public void setObject(
            VariableCondition  condition,
            Object             value,
            int                sqlType)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setObject(t_iIndex, value, sqlType);
            }
        }
    }

    /**
     * Specifies the value of an object parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the object value.
     * @see java.sql.PreparedStatement#setObject(int,Object)
     * @exception SQLException if an error occurs.
     */
    public void setObject(VariableCondition condition, Object value)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setObject(t_iIndex, value);
            }
        }
    }

    /**
     * Specifies the value of a ref parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the ref value.
     * @see java.sql.PreparedStatement#setRef(int,java.sql.Ref)
     * @exception SQLException if an error occurs.
     */
    public void setRef(VariableCondition condition, Ref value)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setRef(t_iIndex, value);
            }
        }
    }

    /**
     * Specifies the value of a timestamp parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the timestamp value.
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp)
     * @exception SQLException if an error occurs.
     */
    public void setTimestamp(VariableCondition condition, Timestamp value)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement.setTimestamp(t_iIndex, value);
            }
        }
    }

    /**
     * Specifies the value of a timestamp parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the timestamp value.
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the timestamp.
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp)
     * @exception SQLException if an error occurs.
     */
    public void setTimestamp(
            VariableCondition  condition,
            Timestamp          value,
            Calendar           calendar)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement
                    .setTimestamp(t_iIndex, value, calendar);
            }
        }
    }
    /**
     * Specifies the value of a parameter formatted as an Unicode stream,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param inputStream (Taken from Sun's Javadoc) the Java input stream
     * that contains the Unicode parameter value.
     * @param length (Taken from Sun's Javadoc) the number of bytes
     * in the stream.
     * @see java.sql.PreparedStatement#setUnicodeStream(int,java.io.InputStream,int)
     * @exception SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     */
    public void setUnicodeStream(
            VariableCondition condition,
            InputStream       inputStream,
            int               length)
        throws  SQLException
    {
        int t_iIndex = getIndex(getVariableConditions(), condition);

        if  (t_iIndex > 0) 
        {
            PreparedStatement t_PreparedStatement =
                getPreparedStatement();

            if  (t_PreparedStatement != null) 
            {
                t_PreparedStatement
                    .setUnicodeStream(t_iIndex, inputStream, length);
            }
        }
    }

    // New from java.sql.Statement 1.4 //

    /**
     * See Statement#getMoreResults(int)
     * @see java.sql.Statement#getMoreResults(int)
     * @param current (Taken from Sun's Javadoc) one of the following
     * Statement constants indicating what should happen to current ResultSet
     * objects obtained using the method getResultSetCLOSE_CURRENT_RESULT,
     * KEEP_CURRENT_RESULT, or CLOSE_ALL_RESULTS.
     * @return (Taken from Sun's Javadoc) <code>true</code> if the next result
     * is a ResultSet object; <code>false</code> if it is an update count or
     * there are no more results.
     * @exception SQLException if an error occurs.
     */
    public boolean getMoreResults(int current)
        throws  SQLException
    {
        boolean result = false;

        PreparedStatement t_PreparedStatement = getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            result = t_PreparedStatement.getMoreResults(current);
        }

        return result;
    }

    /**
     * See Statement#getGeneratedKeys()
     * @see java.sql.Statement#getGeneratedKeys()
     * @return (Taken from Sun's Javadoc) a ResultSet object containing the
     * auto-generated key(s) generated by the execution of this Statement
     * object.
     * @exception SQLException if an error occurs.
     */
    public ResultSet getGeneratedKeys()
        throws  SQLException
    {
        ResultSet result = null;

        Statement t_Statement =
            getPreparedStatement();

        if  (t_Statement != null)
        {
            result =
                new QueryResultSet(
                    this, t_Statement.getGeneratedKeys()) {};
        }

        return result;
    }

    /**
     * See Statement#executeUpdate(String,int)
     * @see java.sql.Statement#executeUpdate(java.lang.String,int)
     * @param sql (Taken from Sun's Javadoc) must be an SQL INSERT, UPDATE
     * or DELETE statement or an SQL statement that returns nothing.
     * @param autogeneratedKeys (Taken from Sun's Javadoc) a flag indicating
     * whether auto-generated keys should be made available for retrieval;
     * one of the following constants: Statement.RETURN_GENERATED_KEYS
     * Statement.NO_GENERATED_KEYS.
     * @return (Taken from Sun's Javadoc) either the row count for INSERT,
     * UPDATE or DELETE statements, or 0 for SQL statements that return
     * nothing.
     * @exception SQLException if an error occurs.
     */
    public int executeUpdate(String sql, int autogeneratedKeys)
        throws  SQLException
    {
        int result = 0;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.executeUpdate(sql, autogeneratedKeys);
        }

        return result;
    }

    /**
     * See Statement#executeUpdate(String,int[])
     * @see java.sql.Statement#executeUpdate(java.lang.String,int[])
     * @param sql (Taken from Sun's Javadoc) must be an SQL INSERT, UPDATE
     * or DELETE statement or an SQL statement that returns nothing.
     * @param columnIndexes (Taken from Sun's Javadoc) an array of column
     * indexes indicating the columns that should be returned from the
     * inserted row.
     * @return (Taken from Sun's Javadoc) either the row count for INSERT,
     * UPDATE or DELETE statements, or 0 for SQL statements that return
     * nothing.
     * @exception SQLException if an error occurs.
     */
    public int executeUpdate(String sql, int[] columnIndexes)
        throws  SQLException
    {
        int result = 0;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.executeUpdate(sql, columnIndexes);
        }

        return result;
    }

    /**
     * See Statement#executeUpdate(String,String[])
     * @see java.sql.Statement#executeUpdate(java.lang.String,String[])
     * @param sql (Taken from Sun's Javadoc) must be an SQL INSERT, UPDATE
     * or DELETE statement or an SQL statement that returns nothing.
     * @param columnNames (Taken from Sun's Javadoc) an array of column
     * names indicating the columns that should be returned from the
     * inserted row.
     * @return (Taken from Sun's Javadoc) either the row count for INSERT,
     * UPDATE or DELETE statements, or 0 for SQL statements that return
     * nothing.
     * @exception SQLException if an error occurs.
     */
    public int executeUpdate(String sql, String[] columnNames)
        throws  SQLException
    {
        int result = 0;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.executeUpdate(sql, columnNames);
        }

        return result;
    }

    /**
     * See Statement#execute(String,int)
     * @see java.sql.Statement#execute(java.lang.String,int)
     * @param sql (Taken from Sun's Javadoc) must be an SQL INSERT, UPDATE
     * or DELETE statement or an SQL statement that returns nothing.
     * @param autogeneratedKeys (Taken from Sun's Javadoc) a flag indicating
     * whether auto-generated keys should be made available for retrieval;
     * one of the following constants: Statement.RETURN_GENERATED_KEYS
     * Statement.NO_GENERATED_KEYS.
     * @return (Taken from Sun's Javadoc) true if the first result is a
     * ResultSet object; false if it is an update count or there are no
     * results.
     * @exception SQLException if an error occurs.
     */
    public boolean execute(String sql, int autogeneratedKeys)
        throws  SQLException
    {
        boolean result = false;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.execute(sql, autogeneratedKeys);
        }

        return result;
    }

    /**
     * See Statement#execute(String,int[])
     * @see java.sql.Statement#execute(java.lang.String,int[])
     * @param sql (Taken from Sun's Javadoc) must be an SQL INSERT, UPDATE
     * or DELETE statement or an SQL statement that returns nothing.
     * @param columnIndexes (Taken from Sun's Javadoc) an array of column
     * indexes indicating the columns that should be returned from the
     * inserted row.
     * @return (Taken from Sun's Javadoc) true if the first result is a
     * ResultSet object; false if it is an update count or there are no
     * results.
     * @exception SQLException if an error occurs.
     */
    public boolean execute(String sql, int[] columnIndexes)
        throws  SQLException
    {
        boolean result = false;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.execute(sql, columnIndexes);
        }

        return result;
    }

    /**
     * See Statement#execute(String,String[])
     * @see java.sql.Statement#execute(java.lang.String,java.lang.String[])
     * @param sql (Taken from Sun's Javadoc) must be an SQL INSERT, UPDATE
     * or DELETE statement or an SQL statement that returns nothing.
     * @param columnNames (Taken from Sun's Javadoc) an array of column
     * names indicating the columns that should be returned from the
     * inserted row.
     * @return (Taken from Sun's Javadoc) true if the first result is a
     * ResultSet object; false if it is an update count or there are no
     * results.
     * @exception SQLException if an error occurs.
     */
    public boolean execute(String sql, String[] columnNames)
        throws  SQLException
    {
        boolean result = false;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.execute(sql, columnNames);
        }

        return result;
    }

    /**
     * See Statement#getResultSetHoldability()
     * @see java.sql.Statement#getResultSetHoldability()
     * @return (Taken from Sun's Javadoc) either
     * ResultSet.HOLD_CURSORS_OVER_COMMIT or
     * ResultSet.CLOSE_CURSORS_AT_COMMIT.
     * @exception SQLException if an error occurs.
     */
    public int getResultSetHoldability()
        throws  SQLException
    {
        int result = 0;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            result = t_Statement.getResultSetHoldability();
        }

        return result;
    }

    // New from java.sql.PreparedStatement 1.4 //

    /**
     * See PreparedStatement#setURL(int,URL)
     * @see java.sql.PreparedStatement#setURL(int,java.net.URL)
     * @param parameterIndex (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param url (Taken from Sun's Javadoc) the java.net.URL object to be set.
     * @exception SQLException if an error occurs.
     */
    public void setURL(int parameterIndex, URL url)
        throws  SQLException
    {
        PreparedStatement t_PreparedStatement = getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            t_PreparedStatement.setURL(parameterIndex, url);
        }
    }

    /**
     * See PreparedStatement#getParameterMetaData()
     * @see java.sql.PreparedStatement#getParameterMetaData()
     * @return (Taken from Sun's Javadoc) a ParameterMetaData object that
     * contains information about the number, types and properties of this
     * PreparedStatement object's parameters.
     * @exception SQLException if an error occurs.
     */
    public ParameterMetaData getParameterMetaData()
        throws  SQLException
    {
        ParameterMetaData result = null;

        PreparedStatement t_PreparedStatement = getPreparedStatement();

        if  (t_PreparedStatement != null) 
        {
            result = t_PreparedStatement.getParameterMetaData();
        }

        return result;
    }

    
}
