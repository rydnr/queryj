//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Description: Represents queries to access persistent data.
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
import java.io.StringReader;
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
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Calendar;
import java.util.List;

/**
 * Represents queries to access persistent data.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
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
        immutableSetFields(new ArrayList());
        immutableSetTables(new ArrayList());
        immutableSetConditions(new ArrayList());
        immutableSetVariableConditions(new ArrayList());
    }

    /**
     * Specifies the statement.
     * @param statement the prepared statement.
     */
    protected final void immutableSetPreparedStatement(
        final PreparedStatement statement)
    {
        m__PreparedStatement = statement;
    }

    /**
     * Specifies the statement.
     * @param statement the prepared statement.
     */
    protected void setPreparedStatement(final PreparedStatement statement)
    {
        immutableSetPreparedStatement(statement);
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
     * Retrieves the statement, and throws an SQLException indicating
     * JDBC API has been incorrectly used since the statement wasn't
     * created before being used.
     * @return the statement.
     * @throws SQLException in such invalid API usage.
     */
    protected PreparedStatement retrievePreparedStatement()
        throws SQLException
    {
        PreparedStatement result = getPreparedStatement();

        if  (result == null)
        {
            throw new SQLException("No statement created yet!");
        }

        return result;
    }

    /**
     * Specifies new field collection.
     * @param list the new list.
     */
    private void immutableSetFields(final List list)
    {
        m__lFields = list;
    }

    /**
     * Specifies new field collection.
     * @param list the new list.
     */
    protected void setFields(final List list)
    {
        immutableSetFields(list);
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
     * @precondition field != null
     */
    protected void addField(final Field field)
    {
        addField(field, getFields());
    }

    /**
     * Adds a new field.
     * @param field the field to add.
     * @param fields the fields.
     * @precondition field != null
     * @precondition fields != null
     */
    protected final void addField(final Field field, final List fields)
    {
        fields.add(field);
    }

    /**
     * Retrieves the position of given field on the query.
     * @param field the field to find.
     * @return its position, or <code>-1</code> if such field doesn't belong to
     * this query.
     */
    protected int getFieldIndex(final Field field)
    {
        return getIndex(getFields(), field);
    }

    /**
     * Specifies new table collection.
     * @param list the new list.
     */
    private void immutableSetTables(final List list)
    {
        m__lTables = list;
    }

    /**
     * Specifies new table collection.
     * @param list the new list.
     */
    protected void setTables(final List list)
    {
        immutableSetTables(list);
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
     * @precondition table != null
     */
    protected void addTable(final Table table)
    {
        addTable(table, getTables());
    }

    /**
     * Adds a new table.
     * @param table the table to add.
     * @param tables the tables.
     * @precondition table != null
     * @precondition tables != null
     */
    protected final void addTable(
        final Table table, final List tables)
    {
        tables.add(table);
    }

    /**
     * Specifies new condition collection.
     * @param list the new list.
     */
    private void immutableSetConditions(final List list)
    {
        m__lConditions = list;
    }

    /**
     * Specifies new condition collection.
     * @param list the new list.
     */
    protected void setConditions(final List list)
    {
        immutableSetConditions(list);
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
     * @precondition condition != null
     */
    protected void addCondition(final Condition condition)
    {
        addCondition(condition, true);
    }

    /**
     * Adds a new condition.
     * @param condition the condition to add.
     * @param processNestedVariableConditions <code>true</code> to include
     * any nested variable conditions.
     * @precondition condition != null
     */
    protected void addCondition(
        final Condition condition,
        final boolean processNestedVariableConditions)
    {
        addCondition(
            condition,
            getConditions(),
            processNestedVariableConditions
            ?  getVariableConditions()
            :  null);
    }

    /**
     * Adds a new condition.
     * @param condition the condition to add.
     * @param conditions the conditions.
     * @param variableConditions the variable conditions, or <code>null</code> if
     * no nested conditions process is desired.
     * @precondition condition != null
     * @precondition conditions != null
     */
    protected final void addCondition(
        final Condition condition,
        final List conditions,
        final List variableConditions)
    {
        conditions.add(condition);

        if  (variableConditions != null)
        {
            addVariableConditions(
                condition.getVariableConditions(),
                variableConditions);
        }
    }

    /**
     * Specifies new variable condition collection.
     * @param list the new list.
     */
    private void immutableSetVariableConditions(final List list)
    {
        m__lVariableConditions = list;
    }

    /**
     * Specifies new variable condition collection.
     * @param list the new list.
     */
    protected void setVariableConditions(final List list)
    {
        immutableSetVariableConditions(list);
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
     * @precondition variableCondition != null
     */
    protected void addVariableCondition(
        final VariableCondition variableCondition)
    {
        addVariableCondition(variableCondition, getVariableConditions());
    }

    /**
     * Adds a new variable condition.
     * @param variableCondition the variable condition to add.
     * @param variableConditions the variable conditions.
     * @precondition variableCondition != null
     * @precondition variableConditions != null
     */
    protected void addVariableCondition(
        final VariableCondition variableCondition,
        final List variableConditions)
    {
        addVariableConditions(
            variableCondition,
            variableCondition.getVariableConditions(),
            variableConditions);
    }

    /**
     * Adds given new variable conditions.
     * @param newVariableConditions the variable conditions to add.
     * @param variableConditions the variable conditions.
     * @precondition variableConditions != null
     */
    protected final void addVariableConditions(
        final Collection newVariableConditions,
        final List variableConditions)
    {
        addVariableConditions(null, newVariableConditions, variableConditions);
    }

    /**
     * Adds given new variable conditions.
     * @param variableCondition the variable condition (optional).
     * @param newVariableConditions the variable conditions to add.
     * @param variableConditions the variable conditions.
     * @precondition variableConditions != null
     */
    protected final void addVariableConditions(
        final VariableCondition variableCondition,
        final Collection newVariableConditions,
        final List variableConditions)
    {
        int offset = 0;

        if  (variableCondition != null)
        {
            offset = 1;
        }

        if  (   (newVariableConditions != null)
             && (newVariableConditions.size() > offset))
        {
            variableConditions.addAll(newVariableConditions);
        }
        else if  (variableCondition != null)
        {
            variableConditions.add(variableCondition);
        }
    }

    /**
     * Retrieves the position of given item on the query.
     * @param list the concrete list (fields, tables, conditions, etc.).
     * @param object the object to find.
     * @return its position, or -1 if such item doesn't belong to
     * this query.
     * @precondition list != null
     * @precondition object != null
     */
    protected final int getIndex(final List list, final Object object)
    {
        int result = list.indexOf(object);

        if  (result != list.lastIndexOf(object))
        {
            list.set(result, list.indexOf(object) + "");
        }

        result++;
        
        return result;
    }

    /**
     * Retrieves the position of given item on the query.
     * @param list the concrete list (fields, tables, conditions, etc.).
     * @param object the object to find.
     * @return its position, or -1 if such item doesn't belong to
     * this query.
     * @throws SQLException if the element is not found.
     * @precondition list != null
     * @precondition object != null
     */
    protected int retrieveIndex(final List list, final Object object)
        throws  SQLException
    {
        int result = getIndex(list, object);

        if  (result < 1)
        {
            throw new SQLException("Field or Condition not found!");
        }

        return result;
    }

    // Helper methods //

    /**
     * Converts a clob to a string.
     * @param clob the clob to convert.
     * @return the clob contents.
     * @throws SQLException if the clob cannot be processed.
     * @precondition clob != null
     */
    protected String clobToString(final Clob clob)
      throws  SQLException
    {
        return clobToString(clob, QueryUtils.getInstance());
    }

    /**
     * Converts a clob to a string.
     * @param clob the clob to convert.
     * @param queryUtils the <code>QueryUtils</code> instance.
     * @return the clob contents.
     * @throws SQLException if the clob cannot be processed.
     * @precondition clob != null
     * @precondition queryUtils != null
     */
    protected String clobToString(
        final Clob clob, final QueryUtils queryUtils)
      throws  SQLException
    {
        return queryUtils.clobToString(clob);
    }

    /**
     * Prepares a statement.
     * @param connection the JDBC connection.
     * @return the statement.
     * @throws SQLException if an error occurs.
     * @precondition connection != null
     */
    public PreparedStatement prepareStatement(final Connection connection)
        throws  SQLException
    {
        setPreparedStatement(connection.prepareStatement(toString()));

        return this;
    }

    /**
     * Prepares a statement using given flags.
     * @param connection the JDBC connection.
     * @param resultSetType one of the ResultSet type flags.
     * @param resultSetConcurrency one of the ResultSet concurrency flags.
     * @return the statement.
     * @throws SQLException if an error occurs.
     * @precondition connection != null
     * @precondition (resultSetType == ResultSet.TYPE_FORWARD_ONLY) || (resultSetType == ResultSet.TYPE_SCROLL_INSENSITIVE) || (resultSetType == ResultSet.TYPE_SCROLL_SENSITIVE)
     * @precondition (resultSetConcurrency == ResultSet.CONCUR_READ_ONLY) || (resultSetConcurrency == ResultSet.CONCUR_UPDATABLE)
     */
    public PreparedStatement prepareStatement(
        final Connection connection,
        final int resultSetType,
        final int resultSetConcurrency)
      throws  SQLException
    {
        setPreparedStatement(
            connection.prepareStatement(
                toString(),
                resultSetType,
                resultSetConcurrency));

        return this;
    }

    /**
     * Prepares a statement using given flags.
     * @param connection the JDBC connection.
     * @param resultSetType one of the ResultSet type flags.
     * @param resultSetConcurrency one of the ResultSet concurrency flags.
     * @param resultSetHoldability one of the ResultSet holdability flags.
     * @return the statement.
     * @throws SQLException if an error occurs.
     * @precondition connection != null
     * @precondition (resultSetType == ResultSet.TYPE_FORWARD_ONLY) || (resultSetType == ResultSet.TYPE_SCROLL_INSENSITIVE) || (resultSetType == ResultSet.TYPE_SCROLL_SENSITIVE)
     * @precondition (resultSetConcurrency == ResultSet.CONCUR_READ_ONLY) || (resultSetConcurrency == ResultSet.CONCUR_UPDATABLE)
     * @precondition (resultSetHoldability == ResultSet.HOLD_CURSORS_OVER_COMMIT) || (resultSetHoldability == ResultSet.CLOSE_CURSORS_AT_COMMIT)
     */
    public PreparedStatement prepareStatement(
        final Connection connection,
        final int resultSetType,
        final int resultSetConcurrency,
        final int resultSetHoldability)
      throws  SQLException
    {
        setPreparedStatement(
            connection.prepareStatement(
                toString(),
                resultSetType,
                resultSetConcurrency,
                resultSetHoldability));

        return this;
    }

    // Implementation of java.sql.Statement //

    /**
     * See java.sql.Statement#close().
     * @see java.sql.Statement#close()
     * @throws SQLException if an error occurs.
     */
    public void close()
        throws  SQLException
    {
        retrievePreparedStatement().close();
    }

    /**
     * See java.sql.Statement#execute(String).
     * @see java.sql.Statement#execute(String)
     * @param sql (Taken from Sun's Javadoc) any SQL statement.
     * @return (Taken from Sun's Javadoc) <code>true</code> if the first
     * result is a ResultSet object; false if it is an update count
     * or there are no results.
     * @throws SQLException if an error occurs.
     */
    public boolean execute(final String sql)
        throws  SQLException
    {
        return retrievePreparedStatement().execute(sql);
    }

    /**
     * See java.sql.Statement#getConnection().
     * @see java.sql.Statement#getConnection()
     * @return (Taken from Sun's Javadoc) the connection that produced
     * this statement.
     * @throws SQLException if an error occurs
     */
    public Connection getConnection()
        throws  SQLException
    {
        return retrievePreparedStatement().getConnection();
    }

    /**
     * See java.sql.Statement#getWarnings().
     * @see java.sql.Statement#getWarnings()
     * @return (Taken from Sun's Javadoc) the first SQLWarning object
     * or null if there are no warnings .
     * @throws SQLException if an error occurs.
     */
    public SQLWarning getWarnings()
        throws  SQLException
    {
        return retrievePreparedStatement().getWarnings();
    }

    /**
     * See java.sql.Statement#clearWarnings().
     * @see java.sql.Statement#clearWarnings()
     * @throws SQLException if an error occurs
     */
    public void clearWarnings()
        throws  SQLException
    {
        retrievePreparedStatement().clearWarnings();
    }

    /**
     * See java.sql.Statement#executeQuery(String).
     * @see java.sql.Statement#executeQuery(String)
     * @param sql (Taken from Sun's Javadoc) an SQL statement to be sent
     * to the database, typically a static SQL SELECT statement.
     * @return (Taken from Sun's Javadoc) a ResultSet object that
     * contains the data produced by the given query; never null. 
     * @throws SQLException if an error occurs.
     * @precondition sql != null
     */
    public ResultSet executeQuery(final String sql)
        throws  SQLException
    {
        return
            new QueryResultSet(
                this,
                retrievePreparedStatement().executeQuery(sql));
    }

    /**
     * See java.sql.Statement#executeUpdate(String).
     * @see java.sql.Statement#executeUpdate(String)
     * @param sql (Taken from Sun's Javadoc) an SQL INSERT, UPDATE or
     * DELETE statement or an SQL statement that returns nothing.
     * @return (Taken from Sun's Javadoc) a ResultSet object that
     * contains the data produced by the given query; never null.
     * @throws SQLException if an error occurs.
     */
    public int executeUpdate(final String sql)
        throws  SQLException
    {
        return retrievePreparedStatement().executeUpdate(sql);
    }

    /**
     * See java.sql.Statement#getMaxFieldSize().
     * @see java.sql.Statement#getMaxFieldSize()
     * @return (Taken from Sun's Javadoc) the current
     * column size limit for columns storing character
     * and binary values; zero means there is no limit.
     * @throws SQLException if an error occurs.
     */
    public int getMaxFieldSize()
        throws  SQLException
    {
        return retrievePreparedStatement().getMaxFieldSize();
    }

    /**
     * See java.sql.Statement#setMaxFieldSize(int).
     * @see java.sql.Statement#setMaxFieldSize(int)
     * @param size (Taken from Sun's Javadoc) the new column size
     * limit in bytes; zero means there is no limit.
     * @throws SQLException if an error occurs
     */
    public void setMaxFieldSize(final int size)
        throws  SQLException
    {
        retrievePreparedStatement().setMaxFieldSize(size);
    }

    /**
     * See java.sql.Statement#getMaxRows().
     * @see java.sql.Statement#getMaxRows()
     * @return (Taken from Sun's Javadoc) the current maximum
     * number of rows for a ResultSet object produced by this
     * Statement object; zero means there is no limit.
     * @throws SQLException if an error occurs
     */
    public int getMaxRows()
        throws  SQLException
    {
        return retrievePreparedStatement().getMaxRows();
    }

    /**
     * See java.sql.Statement#setMaxRows(int).
     * @see java.sql.Statement#setMaxRows(int)
     * @param max (Taken from Sun's Javadoc) the new max rows
     * limit; zero means there is no limit.
     * @throws SQLException if an error occurs
     */
    public void setMaxRows(final int max)
        throws  SQLException
    {
        retrievePreparedStatement().setMaxRows(max);
    }

    /**
     * See java.sql.Statement#setEscapeProcessing(boolean).
     * @see java.sql.Statement#setEscapeProcessing(boolean)
     * @param flag (Taken from Sun's Javadoc) <code>true</code>
     * to enable escape processing; <code>false</code> to
     * disable it.
     * @throws SQLException if an error occurs
     */
    public void setEscapeProcessing(final boolean flag)
        throws  SQLException
    {
        retrievePreparedStatement().setEscapeProcessing(flag);
    }

    /**
     * See java.sql.Statement#getQueryTimeout().
     * @see java.sql.Statement#getQueryTimeout()
     * @return (Taken from Sun's Javadoc) the current query
     * timeout limit in seconds; zero means there is no limit.
     * @throws SQLException if an error occurs.
     */
    public int getQueryTimeout()
        throws  SQLException
    {
        return retrievePreparedStatement().getQueryTimeout();
    }

    /**
     * See java.sql.Statement#setQueryTimeout(int).
     * @see java.sql.Statement#setQueryTimeout(int)
     * @param timeout (Taken from Sun's Javadoc) the new query
     * timeout limit in seconds; zero means there is no limit.
     * @throws SQLException if an error occurs
     */
    public void setQueryTimeout(final int timeout)
        throws SQLException
    {
        retrievePreparedStatement().setQueryTimeout(timeout);
    }

    /**
     * See java.sql.Statement#cancel().
     * @see java.sql.Statement#cancel()
     * @throws SQLException if an error occurs.
     */
    public void cancel()
        throws  SQLException
    {
        retrievePreparedStatement().cancel();
    }

    /**
     * See java.sql.Statement#setCursorName(String).
     * @see java.sql.Statement#setCursorName(String)
     * @param name (Taken from Sun's Javadoc) the new
     * cursor name, which must be unique within a connection.
     * @throws SQLException if an error occurs.
     */
    public void setCursorName(final String name)
        throws  SQLException
    {
        retrievePreparedStatement().setCursorName(name);
    }

    /**
     * See java.sql.Statement#getResultSet().
     * @see java.sql.Statement#getResultSet()
     * @return (Taken from Sun's Javadoc) the current result
     * as a ResultSet object or null if the result is an
     * update count or there are no more results.
     * @throws SQLException if an error occurs.
     */
    public ResultSet getResultSet()
        throws  SQLException
    {
        return retrievePreparedStatement().getResultSet();
    }

    /**
     * See java.sql.Statement#getUpdateCount().
     * @see java.sql.Statement#getUpdateCount()
     * @return (Taken from Sun's Javadoc) the current
     * result as an update count; -1 if the current result
     * is a ResultSet object or there are no more results.
     * @throws SQLException if an error occurs.
     */
    public int getUpdateCount()
        throws  SQLException
    {
        return retrievePreparedStatement().getUpdateCount();
    }

    /**
     * See java.sql.Statement#getMoreResults().
     * @see java.sql.Statement#getMoreResults()
     * @return (Taken from Sun's Javadoc) <code>true</code>
     * if the next result is a ResultSet object;
     * <code>false</code> if it is an update count
     * or there are no more results.
     * @throws SQLException if an error occurs.
     */
    public boolean getMoreResults()
        throws  SQLException
    {
        return retrievePreparedStatement().getMoreResults();
    }

    /**
     * See java.sql.Statement#setFetchDirection(int).
     * @see java.sql.Statement#setFetchDirection(int)
     * @param direction (Taken from Sun's Javadoc) the initial
     * direction for processing rows.
     * @throws SQLException if an error occurs.
     */
    public void setFetchDirection(final int direction)
        throws  SQLException
    {
        retrievePreparedStatement().setFetchDirection(direction);
    }

    /**
     * See java.sql.Statement#getFetchDirection().
     * @see java.sql.Statement#getFetchDirection()
     * @return (Taken from Sun's Javadoc) the default
     * fetch direction for result sets generated
     * from this Statement object.
     * @throws SQLException if an error occurs.
     */
    public int getFetchDirection()
        throws  SQLException
    {
        return retrievePreparedStatement().getFetchDirection();
    }

    /**
     * See java.sql.Statement#setFetchSize(int).
     * @see java.sql.Statement#setFetchSize(int)
     * @param size (Taken from Sun's Javadoc) the number of rows to fetch.
     * @throws SQLException if an error occurs.
     */
    public void setFetchSize(final int size)
        throws  SQLException
    {
        retrievePreparedStatement().setFetchSize(size);
    }

    /**
     * See java.sql.Statement#getFetchSize().
     * @see java.sql.Statement#getFetchSize()
     * @return (Taken from Sun's Javadoc) the default fetch
     * size for result sets generated from this Statement object.
     * @throws SQLException if an error occurs.
     */
    public int getFetchSize()
        throws  SQLException
    {
        return retrievePreparedStatement().getFetchSize();
    }

    /**
     * See java.sql.Statement#getResultSetConcurrency().
     * @see java.sql.Statement#getResultSetConcurrency()
     * @return (Taken from Sun's Javadoc) either
     * ResultSet.CONCUR_READ_ONLY or ResultSet.CONCUR_UPDATABLE.
     * @throws SQLException if an error occurs.
     */
    public int getResultSetConcurrency()
        throws  SQLException
    {
        return retrievePreparedStatement().getResultSetConcurrency();
    }

    /**
     * See java.sql.Statement#getResultSetType().
     * @see java.sql.Statement#getResultSetType()
     * @return (Taken from Sun's Javadoc) one of
     * ResultSet.TYPE_FORWARD_ONLY,
     * ResultSet.TYPE_SCROLL_INSENSITIVE, or
     * ResultSet.TYPE_SCROLL_SENSITIVE.
     * @throws SQLException if an error occurs.
     */
    public int getResultSetType()
        throws  SQLException
    {
        return retrievePreparedStatement().getResultSetType();
    }

    /**
     * See java.sql.Statement#addBatch(String).
     * @see java.sql.Statement#addBatch(String)
     * @param sql (Taken from Sun's Javadoc) typically this
     * is a static SQL INSERT or UPDATE statement.
     * @throws SQLException if an error occurs.
     */
    public void addBatch(final String sql)
        throws  SQLException
    {
        retrievePreparedStatement().addBatch(sql);
    }

    /**
     * See java.sql.Statement#clearBatch().
     * @see java.sql.Statement#clearBatch()
     * @throws SQLException if an error occurs
     */
    public void clearBatch()
        throws  SQLException
    {
        retrievePreparedStatement().clearBatch();
    }

    /**
     * See java.sql.Statement#executeBatch().
     * @see java.sql.Statement#executeBatch()
     * @return (Taken from Sun's Javadoc) an array of update
     * counts containing one element for each command in the
     * batch. The elements of the array are ordered according
     * to the order in which commands were added to the batch.
     * @throws SQLException if an error occurs.
     */
    public int[] executeBatch()
        throws  SQLException
    {
        return retrievePreparedStatement().executeBatch();
    }
    
    // Implementation of java.sql.PreparedStatement //

    /**
     * See java.sql.PreparedStatement#setTime(int,java.sql.Time).
     * @see java.sql.PreparedStatement#setTime(int,java.sql.Time)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param time (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setTime(final int index, final Time time)
        throws  SQLException
    {
        retrievePreparedStatement().setTime(index, time);
    }

    /**
     * See java.sql.PreparedStatement#setTime(int,Time,Calendar).
     * @see java.sql.PreparedStatement#setTime(int,java.sql.Time,java.util.Calendar)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param time (Taken from Sun's Javadoc) the parameter value (!!).
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the time.
     * @throws SQLException if an error occurs.
     */
    public void setTime(
        final int index, final Time time, final Calendar calendar)
      throws  SQLException
    {
        retrievePreparedStatement().setTime(index, time, calendar);
    }

    /**
     * See java.sql.PreparedStatement#execute().
     * @see java.sql.PreparedStatement#execute()
     * @return (Taken from Sun's Javadoc) <code>true</code> if
     * the first result is a ResultSet object; <code>false</code>
     * if the first result is an update count or there is no result.
     * @throws SQLException if an error occurs.
     */
    public boolean execute()
        throws  SQLException
    {
        return retrievePreparedStatement().execute();
    }

    /**
     * See java.sql.PreparedStatement#executeQuery().
     * @see java.sql.PreparedStatement#executeQuery()
     * @return (Taken from Sun's Javadoc) a ResultSet object
     * that contains the data produced by the query; never null.
     * @throws SQLException if an error occurs.
     */
    public ResultSet executeQuery()
        throws  SQLException
    {
        return
            new QueryResultSet(
                this, retrievePreparedStatement().executeQuery());
    }

    /**
     * See java.sql.PreparedStatement#setBoolean(int,boolean).
     * @see java.sql.PreparedStatement#setBoolean(int,boolean)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param flag (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setBoolean(final int index, final boolean flag)
        throws  SQLException
    {
        retrievePreparedStatement().setBoolean(index, flag);
    }

    /**
     * See java.sql.PreparedStatement#setByte(int,byte).
     * @see java.sql.PreparedStatement#setByte(int,byte)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param b (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setByte(final int index, final byte b)
        throws  SQLException
    {
        retrievePreparedStatement().setByte(index, b);
    }

    /**
     * See java.sql.PreparedStatement#setShort(int,short).
     * @see java.sql.PreparedStatement#setShort(int,short)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param s (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setShort(final int index, final short s)
        throws  SQLException
    {
        retrievePreparedStatement().setShort(index, s);
    }

    /**
     * See java.sql.PreparedStatement#setInt(int,int).
     * @see java.sql.PreparedStatement#setInt(int,int)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setInt(final int index, final int value)
        throws  SQLException
    {
        retrievePreparedStatement().setInt(index, value);
    }

    /**
     * See java.sql.PreparedStatement#setLong(int,long).
     * @see java.sql.PreparedStatement#setLong(int,long)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setLong(final int index, final long value)
        throws  SQLException
    {
        retrievePreparedStatement().setLong(index, value);
    }

    /**
     * See java.sql.PreparedStatement#setFloat(int,float).
     * @see java.sql.PreparedStatement#setFloat(int,float)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setFloat(final int index, final float value)
        throws  SQLException
    {
        retrievePreparedStatement().setFloat(index, value);
    }

    /**
     * See java.sql.PreparedStatement#setDouble(int,double).
     * @see java.sql.PreparedStatement#setDouble(int,double)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setDouble(final int index, final double value)
        throws  SQLException
    {
        retrievePreparedStatement().setDouble(index, value);
    }

    /**
     * See java.sql.PreparedStatement#getMetaData().
     * @see java.sql.PreparedStatement#getMetaData()
     * @return (Taken from Sun's Javadoc) a ParameterMetaData
     * object that contains information about the number,
     * types and properties of this PreparedStatement
     * object's parameters.
     * @throws SQLException if an error occurs.
     */
    public ResultSetMetaData getMetaData()
        throws  SQLException
    {
        return retrievePreparedStatement().getMetaData();
    }

    /**
     * See java.sql.PreparedStatement#executeUpdate().
     * @see java.sql.PreparedStatement#executeUpdate()
     * @return (Taken from Sun's Javadoc) either (1) the row
     * count for INSERT, UPDATE, or DELETE statements or
     * (2) 0 for SQL statements that return nothing.
     * @throws SQLException if an error occurs.
     */
    public int executeUpdate()
        throws  SQLException
    {
        return retrievePreparedStatement().executeUpdate();
    }

    /**
     * See java.sql.PreparedStatement#addBatch().
     * @see java.sql.PreparedStatement#addBatch()
     * @throws SQLException if an error occurs.
     */
    public void addBatch()
        throws  SQLException
    {
        retrievePreparedStatement().addBatch();
    }

    /**
     * See java.sql.PreparedStatement#setNull(int,int).
     * @see java.sql.PreparedStatement#setNull(int,int)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param sqlType (Taken from Sun's Javadoc) the SQL type
     * code defined in java.sql.Types.
     * @throws SQLException if an error occurs.
     */
    public void setNull(final int index, final int sqlType)
        throws  SQLException
    {
        retrievePreparedStatement().setNull(index, sqlType);
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
     * @throws SQLException if an error occurs.
     */
    public void setNull(
        final int index, final int sqlType, final String typeName)
      throws  SQLException
    {
        retrievePreparedStatement().setNull(index, sqlType, typeName);
    }

    /**
     * See java.sql.PreparedStatement#setString(int,String).
     * @see java.sql.PreparedStatement#setString(int,String)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setString(final int index, final String value)
        throws  SQLException
    {
        retrievePreparedStatement().setString(index, value);
    }

    /**
     * See java.sql.PreparedStatement#clearParameters().
     * @see java.sql.PreparedStatement#clearParameters()
     * @throws SQLException if an error occurs.
     */
    public void clearParameters()
        throws  SQLException
    {
        retrievePreparedStatement().clearParameters();
    }

    /**
     * See java.sql.PreparedStatement#setArray(int,Array).
     * @see java.sql.PreparedStatement#setArray(int,java.sql.Array)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setArray(final int index, final Array value)
        throws  SQLException
    {
        retrievePreparedStatement().setArray(index, value);
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
     * @throws SQLException if an error occurs.
     */
    public void setAsciiStream(
        final int index,
        final InputStream inputStream,
        final int length)
      throws  SQLException
    {
        retrievePreparedStatement().setAsciiStream(index, inputStream, length);
    }

    /**
     * See java.sql.PreparedStatement#setBigDecimal(int,BigDecimal).
     * @see java.sql.PreparedStatement#setBigDecimal(int,java.math.BigDecimal)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setBigDecimal(final int index, final BigDecimal value)
        throws  SQLException
    {
        retrievePreparedStatement().setBigDecimal(index, value);
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
     * @throws SQLException if an error occurs.
     */
    public void setBinaryStream(
        final int index,
        final InputStream inputStream,
        final int length)
      throws  SQLException
    {
        retrievePreparedStatement()
            .setBinaryStream(index, inputStream, length);
    }

    /**
     * See java.sql.PreparedStatement#setBlob(int,Blob).
     * @see java.sql.PreparedStatement#setBlob(int,java.sql.Blob)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setBlob(final int index, final Blob value)
        throws  SQLException
    {
        retrievePreparedStatement().setBlob(index, value);
    }

    /**
     * See java.sql.PreparedStatement#setBytes(int,byte[]).
     * @see java.sql.PreparedStatement#setBytes(int,byte[])
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setBytes(final int index, final byte[] value)
        throws  SQLException
    {
        retrievePreparedStatement().setBytes(index, value);
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
     * @throws SQLException if an error occurs.
     */
    public void setCharacterStream(
        final int index,
        final Reader reader,
        final int length)
      throws  SQLException
    {
        retrievePreparedStatement().setCharacterStream(index, reader, length);
    }

    /**
     * See java.sql.PreparedStatement#setClob(int,Clob).
     * @see java.sql.PreparedStatement#setClob(int,java.sql.Clob)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setClob(final int index, final Clob value)
        throws  SQLException
    {
        retrievePreparedStatement().setClob(index, value);
    }

    /**
     * See java.sql.PreparedStatement#setClob(int,Clob).
     * @see java.sql.PreparedStatement#setClob(int,java.sql.Clob)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setClob(final int index, final String value)
        throws  SQLException
    {
        retrievePreparedStatement().setCharacterStream(
            index, new StringReader(value), value.length());
    }

    /**
     * See java.sql.PreparedStatement#setDate(int,Date).
     * @see java.sql.PreparedStatement#setDate(int,java.sql.Date)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setDate(final int index, final Date value)
        throws  SQLException
    {
        retrievePreparedStatement().setDate(index, value);
    }

    /**
     * Specifies the value of a time parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the time value.
     * @see java.sql.PreparedStatement#setDate(int,java.sql.Time)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setDate(final VariableCondition condition, final Date value)
        throws  SQLException
    {
        setDate(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * See java.sql.PreparedStatement#setDate(int,Date,Calendar).
     * @see java.sql.PreparedStatement#setDate(int,java.sql.Date,java.util.Calendar)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the time.
     * @throws SQLException if an error occurs.
     */
    public void setDate(
        final int index, final Date value, final Calendar calendar)
      throws  SQLException
    {
        retrievePreparedStatement().setDate(index, value, calendar);
    }

    /**
     * See java.sql.PreparedStatement#setDate(int,Date).
     * @see java.sql.PreparedStatement#setDate(int,java.util.Date)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     * @precondition value != null
     */
    public void setDate(final int index, final java.util.Date value)
        throws  SQLException
    {
        setDate(index, value, retrievePreparedStatement());
    }

    /**
     * See java.sql.PreparedStatement#setDate(int,Date).
     * @see java.sql.PreparedStatement#setDate(int,java.util.Date)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @param preparedStatement the prepared statement.
     * @throws SQLException if an error occurs.
     * @precondition value != null
     * @precondition preparedStatement != null
     */
    protected void setDate(
        final int index,
        final java.util.Date value,
        final PreparedStatement preparedStatement)
      throws  SQLException
    {
        if  (value != null)
        {
            preparedStatement.setDate(index, new Date(value.getTime()));
        }
        else
        {
            preparedStatement.setNull(index, Types.DATE);
        }
    }

    /**
     * Specifies the value of a time parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the time value.
     * @see java.sql.PreparedStatement#setTime(int,java.sql.Time)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setDate(final VariableCondition condition, final java.util.Date value)
        throws  SQLException
    {
        setDate(retrieveIndex(getVariableConditions(), condition), value);
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
     * @throws SQLException if an error occurs.
     */
    public void setObject(
        final int index,
        final Object value,
        final int sqlType,
        final int scale)
      throws  SQLException
    {
        retrievePreparedStatement().setObject(index, value, sqlType, scale);
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
     * @throws SQLException if an error occurs.
     */
    public void setObject(
        final int index, final Object value, final int sqlType)
      throws  SQLException
    {
        retrievePreparedStatement().setObject(index, value, sqlType);
    }

    /**
     * See java.sql.PreparedStatement#setObject(int,Object).
     * @see java.sql.PreparedStatement#setObject(int,Object)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setObject(final int index, final Object value)
        throws  SQLException
    {
        retrievePreparedStatement().setObject(index, value);
    }

    /**
     * See java.sql.PreparedStatement#setRef(int,Ref).
     * @see java.sql.PreparedStatement#setRef(int,java.sql.Ref)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setRef(final int index, final Ref value)
        throws  SQLException
    {
        retrievePreparedStatement().setRef(index, value);
    }

    /**
     * See java.sql.PreparedStatement#setTimestamp(int,Timestamp).
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setTimestamp(final int index, final Timestamp value)
        throws  SQLException
    {
        retrievePreparedStatement().setTimestamp(index, value);
    }

    /**
     * See java.sql.PreparedStatement#setTimestamp(int,Timestamp,Calendar).
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp,java.util.Calendar)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the time.
     * @throws SQLException if an error occurs.
     */
    public void setTimestamp(
        final int index, final Timestamp value, final Calendar calendar)
      throws  SQLException
    {
        retrievePreparedStatement().setTimestamp(index, value, calendar);
    }

    /**
     * See java.sql.PreparedStatement#setTimestamp(int,Timestamp).
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setTimestamp(final int index, final java.util.Date value)
        throws  SQLException
    {
        setTimestamp(index, value, retrievePreparedStatement());
    }

    /**
     * See java.sql.PreparedStatement#setTimestamp(int,Timestamp).
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @param preparedStatement the prepared statement.
     * @throws SQLException if an error occurs.
     * @precondition preparedStatement != null
     */
    protected void setTimestamp(
        final int index,
        final java.util.Date value,
        final PreparedStatement preparedStatement)
        throws  SQLException
    {
        if  (value != null)
        {
            preparedStatement.setTimestamp(
                index, new Timestamp(value.getTime()));
        }
        else
        {
            preparedStatement.setNull(index, Types.TIMESTAMP);
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
     * @throws SQLException if an error occurs.
     */
    public void setTimestamp(
        final int index, final java.util.Date value, final Calendar calendar)
      throws  SQLException
    {
        setTimestamp(index, value, calendar, retrievePreparedStatement());
    }
    
    /**
     * See java.sql.PreparedStatement#setTimestamp(int,Timestamp,Calendar).
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp,java.util.Calendar)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the time.
     * @throws SQLException if an error occurs.
     */
    protected void setTimestamp(
        final int index,
        final java.util.Date value,
        final Calendar calendar,
        final PreparedStatement preparedStatement)
      throws  SQLException
    {
        if  (value != null)
        {
            preparedStatement.setTimestamp(
                index, new Timestamp(value.getTime()), calendar);
        }
        else
        {
            preparedStatement.setNull(index, Types.DATE);
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
     * @throws SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     */
    public void setUnicodeStream(
        final int index,
        final InputStream inputStream,
        final int length)
      throws  SQLException
    {
        retrievePreparedStatement()
            .setUnicodeStream(index, inputStream, length);
    }

    // End of java.sql.PreparedStatement //

    /**
     * Specifies the value of a time parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the time value.
     * @see java.sql.PreparedStatement#setTime(int,java.sql.Time)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setTime(final Field field, final Time value)
        throws  SQLException
    {
        setTime(field.equals(), value);
    }

    /**
     * Specifies the value of a time parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the time value.
     * @see java.sql.PreparedStatement#setTime(int,java.sql.Time)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setTime(final VariableCondition condition, final Time value)
        throws  SQLException
    {
        setTime(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies the value of a time parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the time value.
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the time.
     * @see java.sql.PreparedStatement#setTime(int,java.sql.Time)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setTime(
        final Field field,
        final Time value,
        final Calendar calendar)
      throws  SQLException
    {
        setTime(field.equals(), value, calendar);
    }

    /**
     * Specifies the value of a time parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the time value.
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the time.
     * @see java.sql.PreparedStatement#setTime(int,java.sql.Time)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setTime(
        final VariableCondition condition,
        final Time value,
        final Calendar calendar)
      throws  SQLException
    {
        setTime(
            retrieveIndex(getVariableConditions(), condition),
            value,
            calendar);
    }

    /**
     * Specifies the value of a boolean parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the boolean value.
     * @see java.sql.PreparedStatement#setBoolean(int,boolean)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setBoolean(
        final Field field, final boolean value)
      throws  SQLException
    {
        setBoolean(field.equals(), value);
    }

    /**
     * Specifies the value of a boolean parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the boolean value.
     * @see java.sql.PreparedStatement#setBoolean(int,boolean)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setBoolean(
        final VariableCondition condition, final boolean value)
      throws  SQLException
    {
        setBoolean(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies the value of a byte parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the byte value.
     * @see java.sql.PreparedStatement#setByte(int,byte)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setByte(final Field field, final byte value)
        throws  SQLException
    {
        setByte(field.equals(), value);
    }

    /**
     * Specifies the value of a byte parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the byte value.
     * @see java.sql.PreparedStatement#setByte(int,byte)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setByte(final VariableCondition condition, final byte value)
        throws  SQLException
    {
        setByte(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies the value of a short parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the short value.
     * @see java.sql.PreparedStatement#setShort(int,short)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setShort(final Field field, final short value)
        throws  SQLException
    {
        setShort(field.equals(), value);
    }

    /**
     * Specifies the value of a short parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the short value.
     * @see java.sql.PreparedStatement#setShort(int,short)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setShort(final VariableCondition condition, final short value)
        throws  SQLException
    {
        setShort(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies an integer value, or <code>null</code>, depending
     * on given value.
     * @see java.sql.PreparedStatement#setInt(int,int)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setInt(final int index, final Integer value)
        throws  SQLException
    {
        setInt(index, value, retrievePreparedStatement());
    }
    
    /**
     * Specifies an integer value, or <code>null</code>, depending
     * on given value.
     * @see java.sql.PreparedStatement#setInt(int,int)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @param preparedStatement != null
     * @throws SQLException if an error occurs.
     * @precondition preparedStatement != null
     */
    protected void setInt(
        final int index,
        final Integer value,
        final PreparedStatement preparedStatement)
        throws  SQLException
    {
        if  (value != null)
        {
            preparedStatement.setInt(index, value.intValue());
        }
        else
        {
            preparedStatement.setNull(index, Types.INTEGER);
        }
    }

    /**
     * Specifies the value of an int parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the int value.
     * @see java.sql.PreparedStatement#setInt(int,int)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setInt(final Field field, final int value)
        throws  SQLException
    {
        setInt(field.equals(), value);
    }

    /**
     * Specifies the value of an integer parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the int value.
     * @see java.sql.PreparedStatement#setInt(int,int)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setInt(final Field field, final Integer value)
        throws  SQLException
    {
        setInt(field.equals(), value);
    }

    /**
     * Specifies the value of an int parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the int value.
     * @see java.sql.PreparedStatement#setInt(int,int)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setInt(final VariableCondition condition, final int value)
        throws  SQLException
    {
        setInt(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies the value of an Integer parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the int value.
     * @see java.sql.PreparedStatement#setInt(int,int)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setInt(final VariableCondition condition, final Integer value)
        throws  SQLException
    {
        setInt(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies a long value, or <code>null</code>, depending
     * on given value.
     * @see java.sql.PreparedStatement#setLong(int,long)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setLong(final int index, final Long value)
        throws  SQLException
    {
        setLong(index, value, retrievePreparedStatement());
    }
    
    /**
     * Specifies a long value, or <code>null</code>, depending
     * on given value.
     * @see java.sql.PreparedStatement#setLong(int,long)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @param preparedStatement the prepared statement.
     * @throws SQLException if an error occurs.
     * @precondition preparedStatement != null
     */
    protected void setLong(
        final int index,
        final Long value,
        final PreparedStatement preparedStatement)
      throws  SQLException
    {
        if  (value != null)
        {
            preparedStatement.setLong(index, value.longValue());
        }
        else
        {
            preparedStatement.setNull(index, Types.BIGINT);
        }
    }

    /**
     * Specifies the value of a long parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the long value.
     * @see java.sql.PreparedStatement#setLong(int,long)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setLong(final Field field, final long value)
        throws  SQLException
    {
        setLong(field.equals(), value);
    }

    /**
     * Specifies the value of a long parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the long value.
     * @see java.sql.PreparedStatement#setLong(int,long)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setLong(final Field field, final Long value)
        throws  SQLException
    {
        setLong(field.equals(), value);
    }

    /**
     * Specifies the value of a long parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the long value.
     * @see java.sql.PreparedStatement#setLong(int,long)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setLong(final VariableCondition condition, final long value)
        throws  SQLException
    {
        setLong(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies the value of a long parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the long value.
     * @see java.sql.PreparedStatement#setLong(int,long)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setLong(
        final VariableCondition condition, final Long value)
        throws  SQLException
    {
        setLong(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies the value of a float parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the float value.
     * @see java.sql.PreparedStatement#setFloat(int,float)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setFloat(final Field field, final float value)
        throws  SQLException
    {
        setFloat(field.equals(), value);
    }

    /**
     * Specifies the value of a float parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the float value.
     * @see java.sql.PreparedStatement#setFloat(int,float)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setFloat(final VariableCondition condition, final float value)
        throws  SQLException
    {
        setFloat(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies a double value, or <code>null</code>, depending
     * on given value.
     * @see java.sql.PreparedStatement#setDouble(int,double)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @throws SQLException if an error occurs.
     */
    public void setDouble(final int index, final Double value)
        throws  SQLException
    {
        setDouble(index, value, retrievePreparedStatement());
    }
    
    /**
     * Specifies a double value, or <code>null</code>, depending
     * on given value.
     * @see java.sql.PreparedStatement#setDouble(int,double)
     * @param index (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the parameter value (!!).
     * @param preparedStatement the prepared statement.
     * @throws SQLException if an error occurs.
     * @precondition preparedStatement != null
     */
    protected void setDouble(
        final int index,
        final Double value,
        final PreparedStatement preparedStatement)
      throws  SQLException
    {
        if  (value != null)
        {
            preparedStatement.setDouble(index, value.doubleValue());
        }
        else
        {
            preparedStatement.setNull(index, Types.REAL);
        }
    }

    /**
     * Specifies the value of a double parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the double value.
     * @see java.sql.PreparedStatement#setDouble(int,double)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setDouble(final Field field, final double value)
      throws  SQLException
    {
        setDouble(field.equals(), value);
    }

    /**
     * Specifies the value of a double parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the double value.
     * @see java.sql.PreparedStatement#setDouble(int,double)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setDouble(final Field field, final Double value)
      throws  SQLException
    {
        setDouble(field.equals(), value);
    }

    /**
     * Specifies the value of a double parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the double value.
     * @see java.sql.PreparedStatement#setDouble(int,double)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setDouble(
        final VariableCondition condition, final double value)
      throws  SQLException
    {
        setDouble(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies the value of a double parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the double value.
     * @see java.sql.PreparedStatement#setDouble(int,double)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setDouble(
        final VariableCondition condition, final Double value)
      throws  SQLException
    {
        setDouble(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies as <code>null</code> the value of a parameter,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param sqlType (Taken from Sun's Javadoc) the SQL type
     * code defined in java.sql.Types.
     * @see java.sql.PreparedStatement#setNull(int,int)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setNull(final Field field, final int sqlType)
        throws  SQLException
    {
        setNull(field.equals(), sqlType);
    }

    /**
     * Specifies as <code>null</code> the value of a parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param sqlType (Taken from Sun's Javadoc) the SQL type
     * code defined in java.sql.Types.
     * @see java.sql.PreparedStatement#setNull(int,int)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setNull(final VariableCondition condition, final int sqlType)
        throws  SQLException
    {
        setNull(retrieveIndex(getVariableConditions(), condition), sqlType);
    }

    /**
     * Specifies as <code>null</code> the value of a parameter,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param sqlType (Taken from Sun's Javadoc) the SQL type
     * code defined in java.sql.Types.
     * @param typeName (Taken from Sun's Javadoc) the fully-qualified
     * name of an SQL user-defined type; ignored if the
     * parameter is not a user-defined type or REF.
     * @see java.sql.PreparedStatement#setNull(int,int,String)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setNull(
        final Field field,
        final int sqlType,
        final String typeName)
      throws  SQLException
    {
        setNull(field.equals(), sqlType, typeName);
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
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setNull(
        final VariableCondition condition,
        final int sqlType,
        final String typeName)
      throws  SQLException
    {
        setNull(
            retrieveIndex(getVariableConditions(), condition),
            sqlType,
            typeName);
    }

    /**
     * Specifies the value of a String parameter,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param value the String value.
     * @see java.sql.PreparedStatement#setString(int,String)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setString(final ClobField field, final String value)
        throws  SQLException
    {
        setClob(field, value);
    }

    /**
     * Specifies the value of a String parameter,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param value the String value.
     * @see java.sql.PreparedStatement#setString(int,String)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setString(final Field field, final String value)
        throws  SQLException
    {
        setString(field.equals(), value);
    }

    /**
     * Specifies the value of a String parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the String value.
     * @see java.sql.PreparedStatement#setString(int,String)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setString(
        final VariableCondition condition, final String value)
      throws  SQLException
    {
        setString(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies the value of an Array parameter,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param value the Array value.
     * @see java.sql.PreparedStatement#setArray(int,java.sql.Array)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setArray(final Field field, final Array value)
        throws  SQLException
    {
        setArray(field.equals(), value);
    }

    /**
     * Specifies the value of an Array parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the Array value.
     * @see java.sql.PreparedStatement#setArray(int,java.sql.Array)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setArray(final VariableCondition condition, final Array value)
        throws  SQLException
    {
        setArray(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies the value of a parameter formatted as an ASCII stream,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param inputStream (Taken from Sun's Javadoc) the Java input stream
     * that contains the ASCII parameter value.
     * @param length (Taken from Sun's Javadoc) the number of bytes
     * in the stream.
     * @see java.sql.PreparedStatement#setAsciiStream(int,java.io.InputStream,int)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setAsciiStream(
        final Field field,
        final InputStream       inputStream,
        final int               length)
      throws  SQLException
    {
        setAsciiStream(field.equals(), inputStream, length);
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
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setAsciiStream(
        final VariableCondition condition,
        final InputStream       inputStream,
        final int               length)
      throws  SQLException
    {
        setAsciiStream(
            retrieveIndex(getVariableConditions(), condition),
            inputStream,
            length);
    }

    /**
     * Specifies the value of a BigDecimal parameter,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param value the big decimal value.
     * @see java.sql.PreparedStatement#setBigDecimal(int,java.math.BigDecimal)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setBigDecimal(
        final Field  field, final BigDecimal value)
      throws  SQLException
    {
        setBigDecimal(field.equals(), value);
    }

    /**
     * Specifies the value of a BigDecimal parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the big decimal value.
     * @see java.sql.PreparedStatement#setBigDecimal(int,java.math.BigDecimal)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setBigDecimal(
        final VariableCondition  condition,
        final BigDecimal         value)
      throws  SQLException
    {
        setBigDecimal(
            retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies the value of a parameter formatted as a binary stream,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param inputStream (Taken from Sun's Javadoc) the Java input stream
     * that contains the binary parameter value.
     * @param length (Taken from Sun's Javadoc) the number of bytes
     * in the stream.
     * @see java.sql.PreparedStatement#setBinaryStream(int,java.io.InputStream,int)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setBinaryStream(
        final Field field,
        final InputStream inputStream,
        final int length)
      throws  SQLException
    {
        setBinaryStream(field.equals(), inputStream, length);
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
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setBinaryStream(
        final VariableCondition condition,
        final InputStream inputStream,
        final int length)
      throws  SQLException
    {
        setBinaryStream(
            retrieveIndex(getVariableConditions(), condition),
            inputStream,
            length);
    }

    /**
     * Specifies the value of a blob parameter,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param value the blob value.
     * @see java.sql.PreparedStatement#setBlob(int,java.sql.Blob)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setBlob(final Field field, final Blob value)
        throws  SQLException
    {
        setBlob(field.equals(), value);
    }

    /**
     * Specifies the value of a blob parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the blob value.
     * @see java.sql.PreparedStatement#setBlob(int,java.sql.Blob)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setBlob(final VariableCondition condition, final Blob value)
        throws  SQLException
    {
        setBlob(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies the value of a byte array parameter,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param value the byte array value.
     * @see java.sql.PreparedStatement#setBytes(int,byte[])
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setBytes(final Field field, final byte[] value)
        throws  SQLException
    {
        setBytes(field.equals(), value);
    }

    /**
     * Specifies the value of a byte array parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the byte array value.
     * @see java.sql.PreparedStatement#setBytes(int,byte[])
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setBytes(final VariableCondition condition, final byte[] value)
        throws  SQLException
    {
        setBytes(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies the value of a parameter formatted as a binary stream,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param reader (Taken from Sun's Javadoc) the java.io.Reader object
     * that contains the Unicode data.
     * @param length (Taken from Sun's Javadoc) the number of bytes
     * in the stream.
     * @see java.sql.PreparedStatement#setCharacterStream(int,java.io.Reader,int)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setCharacterStream(
        final Field field,
        final Reader reader,
        final int length)
      throws  SQLException
    {
        setCharacterStream(field.equals(), reader, length);
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
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setCharacterStream(
        final VariableCondition condition,
        final Reader reader,
        final int length)
      throws  SQLException
    {
        setCharacterStream(
            retrieveIndex(getVariableConditions(), condition), reader, length);
    }

    /**
     * Specifies the value of a clob parameter,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param value the clob value.
     * @see java.sql.PreparedStatement#setClob(int,java.sql.Clob)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setClob(final Field field, final Clob value)
        throws  SQLException
    {
        setClob(field.equals(), value);
    }

    /**
     * Specifies the value of a clob parameter,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param value the clob value.
     * @see java.sql.PreparedStatement#setClob(int,java.sql.Clob)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setClob(final Field field, final String value)
        throws  SQLException
    {
        setClob(field.equals(), value);
    }

    /**
     * Specifies the value of a clob parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the clob value.
     * @see java.sql.PreparedStatement#setClob(int,java.sql.Clob)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setClob(final VariableCondition condition, final Clob value)
        throws  SQLException
    {
        setClob(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies the value of a clob parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the clob value.
     * @see java.sql.PreparedStatement#setClob(int,java.sql.Clob)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setClob(final VariableCondition condition, final String value)
        throws  SQLException
    {
        setClob(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies the value of a date parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the date value.
     * @see java.sql.PreparedStatement#setDate(int,java.sql.Date)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setDate(final Field field, final Date value)
        throws  SQLException
    {
        setDate(field.equals(), value);
    }

    /**
     * Specifies the value of a date parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the date value.
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the date.
     * @see java.sql.PreparedStatement#setDate(int,java.sql.Date)
     * @thows SQLException if an error occurs.
     * @precondition field != null
     */
    public void setDate(
        final Field field,
        final Date value,
        final Calendar calendar)
      throws  SQLException
    {
        setDate(field.equals(), value, calendar);
    }

    /**
     * Specifies the value of a date parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the date value.
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the date.
     * @see java.sql.PreparedStatement#setDate(int,java.sql.Date)
     * @thows SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setDate(
        final VariableCondition condition,
        final Date value,
        final Calendar calendar)
      throws  SQLException
    {
        setDate(
            retrieveIndex(
                getVariableConditions(), condition), value, calendar);
    }

    /**
     * Specifies the value of an object parameter,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param value the object value.
     * @param sqlType (Taken from Sun's Javadoc) the SQL type
     * (as defined in java.sql.Types) to be sent to the database.
     * The scale argument may further qualify this type.
     * @param scale (Taken from Sun's Javadoc) for
     * java.sql.Types.DECIMAL or java.sql.Types.NUMERIC types,
     * this is the number of digits after the decimal point. For all
     * other types, this value will be ignored.
     * @see java.sql.PreparedStatement#setObject(int,Object,int,int)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setObject(
        final Field field,
        final Object value,
        final int sqlType,
        final int scale)
      throws  SQLException
    {
        setObject(field.equals(), value, sqlType, scale);
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
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setObject(
        final VariableCondition condition,
        final Object value,
        final int sqlType,
        final int scale)
      throws  SQLException
    {
        setObject(
            retrieveIndex(getVariableConditions(), condition),
            value,
            sqlType,
            scale);
    }

    /**
     * Specifies the value of an object parameter,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param value the object value.
     * @param sqlType (Taken from Sun's Javadoc) the SQL type
     * (as defined in java.sql.Types) to be sent to the database.
     * @see java.sql.PreparedStatement#setObject(int,Object,int)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setObject(
        final Field field,
        final Object value,
        final int sqlType)
      throws  SQLException
    {
        setObject(field.equals(), value, sqlType);
    }

    /**
     * Specifies the value of an object parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the object value.
     * @param sqlType (Taken from Sun's Javadoc) the SQL type
     * (as defined in java.sql.Types) to be sent to the database.
     * @see java.sql.PreparedStatement#setObject(int,Object,int)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setObject(
        final VariableCondition condition,
        final Object value,
        final int sqlType)
      throws  SQLException
    {
        setObject(
            retrieveIndex(getVariableConditions(), condition), value, sqlType);
    }

    /**
     * Specifies the value of an object parameter,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param value the object value.
     * @see java.sql.PreparedStatement#setObject(int,Object)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setObject(
        final Field field, final Object value)
      throws  SQLException
    {
        setObject(field.equals(), value);
    }

    /**
     * Specifies the value of an object parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the object value.
     * @see java.sql.PreparedStatement#setObject(int,Object)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setObject(
        final VariableCondition condition, final Object value)
      throws  SQLException
    {
        setObject(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies the value of a ref parameter,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param value the ref value.
     * @see java.sql.PreparedStatement#setRef(int,java.sql.Ref)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setRef(final Field field, final Ref value)
        throws  SQLException
    {
        setRef(field.equals(), value);
    }

    /**
     * Specifies the value of a ref parameter,
     * associated with a previously specified variable condition.
     * @param condition the variable condition.
     * @param value the ref value.
     * @see java.sql.PreparedStatement#setRef(int,java.sql.Ref)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setRef(final VariableCondition condition, final Ref value)
        throws  SQLException
    {
        setRef(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies the value of a timestamp parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the timestamp value.
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setTimestamp(
        final Field field, final Timestamp value)
      throws  SQLException
    {
        setTimestamp(field.equals(), value);
    }

    /**
     * Specifies the value of a timestamp parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the timestamp value.
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setTimestamp(
        final VariableCondition condition, final Timestamp value)
      throws  SQLException
    {
        setTimestamp(retrieveIndex(getVariableConditions(), condition), value);
    }

    /**
     * Specifies the value of a timestamp parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the timestamp value.
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the timestamp.
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setTimestamp(
        final Field field,
        final Timestamp value,
        final Calendar calendar)
      throws  SQLException
    {
        setTimestamp(field.equals(), value, calendar);
    }

    /**
     * Specifies the value of a timestamp parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the timestamp value.
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the timestamp.
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     */
    public void setTimestamp(
        final VariableCondition condition,
        final Timestamp value,
        final Calendar calendar)
      throws  SQLException
    {
        setTimestamp(
            retrieveIndex(getVariableConditions(), condition),
            value,
            calendar);
    }

    /**
     * Specifies the value of a timestamp parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the date value.
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     */
    public void setTimestamp(
        final Field field, final java.util.Date value)
      throws  SQLException
    {
        setTimestamp(
            field.equals(),
            (value != null) ? new Timestamp(value.getTime()) : null);
    }

    /**
     * Specifies the value of a timestamp parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the date value.
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     * @precondition value != null
     */
    public void setTimestamp(
        final VariableCondition condition, final java.util.Date value)
      throws  SQLException
    {
        setTimestamp(
            retrieveIndex(
                getVariableConditions(), condition),
            (value != null) ? new java.util.Date(value.getTime()) : null);
    }

    /**
     * Specifies the value of a timestamp parameter, associated with a
     * previously specified variable condition for given field.
     * @param field the field.
     * @param value the timestamp value.
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the timestamp.
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp)
     * @throws SQLException if an error occurs.
     * @precondition field != null
     * @precondition value != null
     */
    public void setTimestamp(
        final Field field,
        final java.util.Date value,
        final Calendar calendar)
      throws  SQLException
    {
        setTimestamp(
            field.equals(),
            (value != null) ? new Timestamp(value.getTime()) : null,
            calendar);
    }

    /**
     * Specifies the value of a timestamp parameter, associated with a
     * previously specified variable condition.
     * @param condition the variable condition.
     * @param value the timestamp value.
     * @param calendar (Taken from Sun's Javadoc) the Calendar object
     * the driver will use to construct the timestamp.
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp)
     * @throws SQLException if an error occurs.
     * @precondition variableCondition != null
     * @precondition value != null
     */
    public void setTimestamp(
        final VariableCondition condition,
        final java.util.Date value,
        final Calendar calendar)
      throws  SQLException
    {
        setTimestamp(
            retrieveIndex(getVariableConditions(), condition),
            (value != null) ? new Timestamp(value.getTime()) : null,
            calendar);
    }

    /**
     * Specifies the value of a parameter formatted as an Unicode stream,
     * associated with a previously specified variable condition for given
     * field.
     * @param field the field.
     * @param inputStream (Taken from Sun's Javadoc) the Java input stream
     * that contains the Unicode parameter value.
     * @param length (Taken from Sun's Javadoc) the number of bytes
     * in the stream.
     * @see java.sql.PreparedStatement#setUnicodeStream(int,java.io.InputStream,int)
     * @throws SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     * @precondition field != null
     */
    public void setUnicodeStream(
        final Field field,
        final InputStream inputStream,
        final int length)
      throws  SQLException
    {
        setUnicodeStream(field.equals(), inputStream, length);
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
     * @throws SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     * @precondition variableCondition != null
     */
    public void setUnicodeStream(
        final VariableCondition condition,
        final InputStream inputStream,
        final int length)
      throws  SQLException
    {
        setUnicodeStream(
            retrieveIndex(getVariableConditions(), condition),
            inputStream,
            length);
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
     * @throws SQLException if an error occurs.
     */
    public boolean getMoreResults(final int current)
        throws  SQLException
    {
        return retrievePreparedStatement().getMoreResults(current);
    }

    /**
     * See Statement#getGeneratedKeys()
     * @see java.sql.Statement#getGeneratedKeys()
     * @return (Taken from Sun's Javadoc) a ResultSet object containing the
     * auto-generated key(s) generated by the execution of this Statement
     * object.
     * @throws SQLException if an error occurs.
     */
    public ResultSet getGeneratedKeys()
        throws SQLException
    {
        return
            new QueryResultSet(
                this, retrievePreparedStatement().getGeneratedKeys());
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
     * @throws SQLException if an error occurs.
     */
    public int executeUpdate(final String sql, final int autogeneratedKeys)
        throws  SQLException
    {
        return
            retrievePreparedStatement().executeUpdate(sql, autogeneratedKeys);
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
     * @throws SQLException if an error occurs.
     */
    public int executeUpdate(final String sql, final int[] columnIndexes)
        throws  SQLException
    {
        return retrievePreparedStatement().executeUpdate(sql, columnIndexes);
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
     * @throws SQLException if an error occurs.
     */
    public int executeUpdate(final String sql, final String[] columnNames)
        throws  SQLException
    {
        return retrievePreparedStatement().executeUpdate(sql, columnNames);
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
     * @throws SQLException if an error occurs.
     */
    public boolean execute(final String sql, final int autogeneratedKeys)
        throws  SQLException
    {
        return retrievePreparedStatement().execute(sql, autogeneratedKeys);
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
     * @throws SQLException if an error occurs.
     */
    public boolean execute(final String sql, final int[] columnIndexes)
        throws  SQLException
    {
        return retrievePreparedStatement().execute(sql, columnIndexes);
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
     * @throws SQLException if an error occurs.
     */
    public boolean execute(final String sql, final String[] columnNames)
        throws  SQLException
    {
        return retrievePreparedStatement().execute(sql, columnNames);
    }

    /**
     * See Statement#getResultSetHoldability()
     * @see java.sql.Statement#getResultSetHoldability()
     * @return (Taken from Sun's Javadoc) either
     * ResultSet.HOLD_CURSORS_OVER_COMMIT or
     * ResultSet.CLOSE_CURSORS_AT_COMMIT.
     * @throws SQLException if an error occurs.
     */
    public int getResultSetHoldability()
        throws  SQLException
    {
        return retrievePreparedStatement().getResultSetHoldability();
    }

    // New from java.sql.PreparedStatement 1.4 //

    /**
     * See PreparedStatement#setURL(int,URL)
     * @see java.sql.PreparedStatement#setURL(int,java.net.URL)
     * @param parameterIndex (Taken from Sun's Javadoc) the first parameter
     * is 1, the second is 2, ...
     * @param url (Taken from Sun's Javadoc) the java.net.URL object to be set.
     * @throws SQLException if an error occurs.
     */
    public void setURL(final int parameterIndex, final URL url)
        throws  SQLException
    {
        retrievePreparedStatement().setURL(parameterIndex, url);
    }

    /**
     * See PreparedStatement#getParameterMetaData()
     * @see java.sql.PreparedStatement#getParameterMetaData()
     * @return (Taken from Sun's Javadoc) a ParameterMetaData object that
     * contains information about the number, types and properties of this
     * PreparedStatement object's parameters.
     * @throws SQLException if an error occurs.
     */
    public ParameterMetaData getParameterMetaData()
        throws  SQLException
    {
        return retrievePreparedStatement().getParameterMetaData();
    }
}
