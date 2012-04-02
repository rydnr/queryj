//;-*- mode: java -*-
/*
                        QueryJ

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
 * Filename: QueryResultSet.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Standard SQL query.
 *
 */
package org.acmsl.queryj;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.Query;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * Represents standard SQL queries.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class QueryResultSet
    implements  ResultSet
{
    /**
     * The query reference.
     */
    private Query m__Query;

    /**
     * The wrapped result set.
     */
    private ResultSet m__ResultSet;

    /**
     * The temporary fetch direction (in case wrapped result set is null).
     */
    private int m__iTempFetchDirection = -1; 

    /**
     * The temporary fetch size (in case wrapped result set is null).
     */
    private int m__iTempFetchSize = -1; 

    /**
     * Builds a query result set with given references.
     * @param query the query.
     * @param resultSet the wrapped result set.
     * @precondition query != null
     * @precondition resultSet != null
     */
    public QueryResultSet(final Query query, final ResultSet resultSet)
    {
        immutableSetQuery(query);
        immutableSetResultSet(resultSet);
    }

    /**
     * Specifies the query reference.
     * @param query the query.
     */
    private void immutableSetQuery(final Query query)
    {
        m__Query = query;
    }

    /**
     * Specifies the query reference.
     * @param query the query.
     */
    protected void setQuery(final Query query)
    {
        immutableSetQuery(query);
    }

    /**
     * Retrieves the query.
     * @return such reference.
     */
    protected Query getQuery()
    {
        return m__Query;
    }

    /**
     * Specifies the result set reference.
     * @param resultSet the result set.
     */
    private void immutableSetResultSet(final ResultSet resultSet)
    {
        m__ResultSet = resultSet;
    }

    /**
     * Specifies the result set reference.
     * @param resultSet the result set.
     */
    protected void setResultSet(final ResultSet resultSet)
    {
        immutableSetResultSet(resultSet);
    }

    /**
     * Retrieves the result set.
     * @return such reference.
     */
    protected ResultSet getResultSet()
    {
        return m__ResultSet;
    }

    // Implementation of java.sql.ResultSet

    /**
     * See ResultSet.getBytes(int)
     * @see java.sql.ResultSet#getBytes(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null
     * @exception SQLException if an error occurs.
     */
    @Nullable
    public byte[] getBytes(final int index)
        throws  SQLException
    {
        @Nullable byte[] result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getBytes(index);
        }
        
        return result;
    }

    /**
     * See ResultSet.getBytes(String)
     * @see java.sql.ResultSet#getBytes(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public byte[] getBytes(final String columnName)
        throws  SQLException
    {
        return getBytes(columnName, getResultSet());
    }

    /**
     * See ResultSet.getBytes(String)
     * @see java.sql.ResultSet#getBytes(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected byte[] getBytes(
        final String columnName, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getBytes(columnName);
    }

    /**
     * See ResultSet#next()
     * @see java.sql.ResultSet#next()
     * @return (Taken from Sun's Javadoc) <code>true</code> if the
     * new current row is valid; false if there are no more rows.
     * @exception SQLException if an error occurs.
     */
    public boolean next()
        throws  SQLException
    {
        return next(getResultSet());
    }

    /**
     * See ResultSet#next()
     * @see java.sql.ResultSet#next()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) <code>true</code> if the
     * new current row is valid; false if there are no more rows.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected boolean next(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.next();
    }

    /**
     * See ResultSet#previous()
     * @see java.sql.ResultSet#previous()
     * @return (Taken from Sun's Javadoc) <code>true</code> if the
     * cursor is on a valid row; false if it is off the result set.
     * @exception SQLException if an error occurs.
     */
    public boolean previous()
        throws  SQLException
    {
        return previous(getResultSet());
    }

    /**
     * See ResultSet#previous()
     * @see java.sql.ResultSet#previous()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) <code>true</code> if the
     * cursor is on a valid row; false if it is off the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected boolean previous(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.previous();
    }

    /**
     * See ResultSet#getBoolean(int)
     * @see java.sql.ResultSet#getBoolean(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is <code>false</code>.
     * @exception SQLException if an error occurs.
     */
    public boolean getBoolean(final int index)
        throws SQLException
    {
        return getBoolean(index, getResultSet());
    }

    /**
     * See ResultSet#getBoolean(int)
     * @see java.sql.ResultSet#getBoolean(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is <code>false</code>.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected boolean getBoolean(final int index, @NotNull final ResultSet resultSet)
        throws SQLException
    {
        return resultSet.getBoolean(index);
    }

    /**
     * See ResultSet#getBoolean(String)
     * @see java.sql.ResultSet#getBoolean(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is <code>false</code>.
     * @exception SQLException if an error occurs.
     */
    public boolean getBoolean(final String columnName)
        throws  SQLException
    {
        return getBoolean(columnName, getResultSet());
    }

    /**
     * See ResultSet#getBoolean(String)
     * @see java.sql.ResultSet#getBoolean(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is <code>false</code>.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected boolean getBoolean(final String columnName, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getBoolean(columnName);
    }

    /**
     * See ResultSet#getType()
     * @see java.sql.ResultSet#getType()
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     */
    public int getType()
        throws  SQLException
    {
        return getType(getResultSet());
    }

    /**
     * See ResultSet#getType()
     * @see java.sql.ResultSet#getType()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected int getType(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getType();
    }

    /**
     * See ResultSet#getLong(int)
     * @see java.sql.ResultSet#getLong(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     */
    public long getLong(final int index)
        throws  SQLException
    {
        return getLong(index, getResultSet());
    }

    /**
     * See ResultSet#getLong(int)
     * @see java.sql.ResultSet#getLong(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected long getLong(final int index, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getLong(index);
    }

    /**
     * See ResultSet#getLong(String)
     * @see java.sql.ResultSet#getLong(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     */
    public long getLong(final String columnName)
        throws  SQLException
    {
        return getLong(columnName, getResultSet());
    }

    /**
     * See ResultSet#getLong(String)
     * @see java.sql.ResultSet#getLong(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected long getLong(final String columnName, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getLong(columnName);
    }

    /**
     * See ResultSet#getObject(int)
     * @see java.sql.ResultSet#getObject(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Object getObject(final int index)
        throws  SQLException
    {
        return getObject(index, getResultSet());
    }

    /**
     * See ResultSet#getObject(int)
     * @see java.sql.ResultSet#getObject(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Object getObject(final int index, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getObject(index);
    }

    /**
     * See ResultSet#getObject(String)
     * @see java.sql.ResultSet#getObject(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public Object getObject(final String columnName)
        throws  SQLException
    {
        return getObject(columnName, getResultSet());
    }

    /**
     * See ResultSet#getObject(String)
     * @see java.sql.ResultSet#getObject(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Object getObject(
        final String columnName, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getObject(columnName);
    }

    /**
     * See ResultSet#getObject(int,Map)
     * @see java.sql.ResultSet#getObject(int,java.util.Map)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param map (Taken from Sun's Javadoc) a java.util.Map object that
     * contains the mapping from SQL type names to classes in the Java
     * programming language.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Object getObject(final int index, final Map map)
        throws  SQLException
    {
        return getObject(index, map, getResultSet());
    }

    /**
     * See ResultSet#getObject(int,Map)
     * @see java.sql.ResultSet#getObject(int,java.util.Map)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param map (Taken from Sun's Javadoc) a java.util.Map object that
     * contains the mapping from SQL type names to classes in the Java
     * programming language.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Object getObject(
        final int index, final Map map, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getObject(index, map);
    }

    /**
     * See ResultSet#getObject(String,Map)
     * @see java.sql.ResultSet#getObject(java.lang.String,java.util.Map)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param map (Taken from Sun's Javadoc) a java.util.Map object that
     * contains the mapping from SQL type names to classes in the Java
     * programming language.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Object getObject(final String columnName, final Map map)
        throws  SQLException
    {
        return getObject(columnName, map, getResultSet());
    }

    /**
     * See ResultSet#getObject(String,Map)
     * @see java.sql.ResultSet#getObject(java.lang.String,java.util.Map)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param map (Taken from Sun's Javadoc) a java.util.Map object that
     * contains the mapping from SQL type names to classes in the Java
     * programming language.
     * @param resultSet the result set.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Object getObject(
        final String columnName, final Map map, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getObject(columnName, map);
    }

    /**
     * See ResultSet#close()
     * @see java.sql.ResultSet#close()
     * @exception SQLException if an error occurs.
     */
    public void close()
        throws  SQLException
    {
        close(getResultSet());
    }

    /**
     * See ResultSet#close()
     * @see java.sql.ResultSet#close()
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void close(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        resultSet.close();
    }

    /**
     * See ResultSet#getRef(int)
     * @see java.sql.ResultSet#getRef(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Ref getRef(final int index)
        throws  SQLException
    {
        return getRef(index, getResultSet());
    }

    /**
     * See ResultSet#getRef(int)
     * @see java.sql.ResultSet#getRef(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Ref getRef(final int index, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getRef(index);
    }

    /**
     * See ResultSet#getRef(String)
     * @see java.sql.ResultSet#getRef(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Ref getRef(final String columnName)
        throws  SQLException
    {
        return getRef(columnName, getResultSet());
    }

    /**
     * See ResultSet#getRef(String)
     * @see java.sql.ResultSet#getRef(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Ref getRef(final String columnName, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getRef(columnName);
    }

    /**
     * See ResultSet#getTime(int)
     * @see java.sql.ResultSet#getTime(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Time getTime(final int index)
        throws  SQLException
    {
        return getTime(index, getResultSet());
    }

    /**
     * See ResultSet#getTime(int)
     * @see java.sql.ResultSet#getTime(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Time getTime(final int index, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getTime(index);
    }

    /**
     * See ResultSet#getTime(String)
     * @see java.sql.ResultSet#getTime(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Time getTime(final String columnName)
        throws  SQLException
    {
        return getTime(columnName, getResultSet());
    }

    /**
     * See ResultSet#getTime(String)
     * @see java.sql.ResultSet#getTime(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Time getTime(final String columnName, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getTime(columnName);
    }

    /**
     * See ResultSet#getTime(int,Calendar)
     * @see java.sql.ResultSet#getTime(int,java.util.Calendar)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Time getTime(final int index, final Calendar calendar)
        throws  SQLException
    {
        return getTime(index, calendar, getResultSet());
    }

    /**
     * See ResultSet#getTime(int,Calendar)
     * @see java.sql.ResultSet#getTime(int,java.util.Calendar)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Time getTime(
        final int index, final Calendar calendar, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getTime(index, calendar);
    }

    /**
     * See ResultSet#getTime(String,Calendar)
     * @see java.sql.ResultSet#getTime(java.lang.String,java.util.Calendar)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Time getTime(final String columnName, final Calendar calendar)
        throws  SQLException
    {
        return getTime(columnName, calendar, getResultSet());
    }

    /**
     * See ResultSet#getTime(String,Calendar)
     * @see java.sql.ResultSet#getTime(java.lang.String,java.util.Calendar)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Time getTime(
        final String columnName,
        final Calendar calendar,
        @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getTime(columnName, calendar);
    }


    /**
     * See ResultSet#getDate(int)
     * @see java.sql.ResultSet#getDate(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Date getDate(final int index)
        throws  SQLException
    {
        return getDate(index, getResultSet());
    }

    /**
     * See ResultSet#getDate(int)
     * @see java.sql.ResultSet#getDate(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Date getDate(final int index, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getDate(index);
    }

    /**
     * See ResultSet#getDate(String)
     * @see java.sql.ResultSet#getDate(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public Date getDate(final String columnName)
        throws  SQLException
    {
        return getDate(columnName, getResultSet());
    }

    /**
     * See ResultSet#getDate(String)
     * @see java.sql.ResultSet#getDate(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Date getDate(final String columnName, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getDate(columnName);
    }

    /**
     * See ResultSet#getDate(int,Calendar)
     * @see java.sql.ResultSet#getDate(int,java.util.Calendar)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Date getDate(final int index, final Calendar calendar)
        throws  SQLException
    {
        return getDate(index, calendar, getResultSet());
    }

    /**
     * See ResultSet#getDate(int,Calendar)
     * @see java.sql.ResultSet#getDate(int,java.util.Calendar)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Date getDate(
        final int index, final Calendar calendar, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getDate(index, calendar);
    }

    /**
     * See ResultSet#getDate(String,Calendar)
     * @see java.sql.ResultSet#getDate(java.lang.String,java.util.Calendar)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Date getDate(final String columnName, final Calendar calendar)
        throws  SQLException
    {
        return getDate(columnName, calendar, getResultSet());
    }

    /**
     * See ResultSet#getDate(String,Calendar)
     * @see java.sql.ResultSet#getDate(java.lang.String,java.util.Calendar)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Date getDate(
        final String columnName,
        final Calendar calendar,
        @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getDate(columnName, calendar);
    }

    /**
     * See ResultSet#first()
     * @see java.sql.ResultSet#first()
     * @return (Taken from Sun's Javadoc) 
     * @exception SQLException if an error occurs.
     */
    public boolean first()
        throws  SQLException
    {
        return first(getResultSet());
    }

    /**
     * See ResultSet#first()
     * @see java.sql.ResultSet#first()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) 
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected boolean first(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.first();
    }

    /**
     * See ResultSet#getByte(int)
     * @see java.sql.ResultSet#getByte(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     */
    public byte getByte(final int index)
        throws  SQLException
    {
        return getByte(index, getResultSet());
    }

    /**
     * See ResultSet#getByte(int)
     * @see java.sql.ResultSet#getByte(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @param resultSet the result set.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected byte getByte(final int index, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getByte(index);
    }

    /**
     * See ResultSet#getByte(String)
     * @see java.sql.ResultSet#getByte(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public byte getByte(final String columnName)
        throws  SQLException
    {
        return getByte(columnName, getResultSet());
    }

    /**
     * See ResultSet#getByte(String)
     * @see java.sql.ResultSet#getByte(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected byte getByte(final String columnName, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getByte(columnName);
    }

    /**
     * See ResultSet#getShort(int)
     * @see java.sql.ResultSet#getShort(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     */
    public short getShort(final int index)
        throws  SQLException
    {
        return getShort(index, getResultSet());
    }

    /**
     * See ResultSet#getShort(int)
     * @see java.sql.ResultSet#getShort(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected short getShort(final int index, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getShort(index);
    }

    /**
     * See ResultSet#getShort(String)
     * @see java.sql.ResultSet#getShort(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public short getShort(final String columnName)
        throws  SQLException
    {
        return getShort(columnName, getResultSet());
    }

    /**
     * See ResultSet#getShort(String)
     * @see java.sql.ResultSet#getShort(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected short getShort(
        final String columnName, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getShort(columnName);
    }

    /**
     * See ResultSet#getInt(int)
     * @see java.sql.ResultSet#getInt(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     */
    public int getInt(final int index)
        throws  SQLException
    {
        return getInt(index, getResultSet());
    }

    /**
     * See ResultSet#getInt(int)
     * @see java.sql.ResultSet#getInt(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected int getInt(final int index, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getInt(index);
    }

    /**
     * See ResultSet#getInt(String)
     * @see java.sql.ResultSet#getInt(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public int getInt(final String columnName)
        throws  SQLException
    {
        return getInt(columnName, getResultSet());
    }

    /**
     * See ResultSet#getInt(String)
     * @see java.sql.ResultSet#getInt(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected int getInt(final String columnName, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getInt(columnName);
    }

    /**
     * See ResultSet#getFloat(int)
     * @see java.sql.ResultSet#getFloat(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     */
    public float getFloat(final int index)
        throws  SQLException
    {
        return getFloat(index, getResultSet());
    }

    /**
     * See ResultSet#getFloat(int)
     * @see java.sql.ResultSet#getFloat(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected float getFloat(final int index, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getFloat(index);
    }

    /**
     * See ResultSet#getFloat(String)
     * @see java.sql.ResultSet#getFloat(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public float getFloat(final String columnName)
        throws  SQLException
    {
        return getFloat(columnName, getResultSet());
    }

    /**
     * See ResultSet#getFloat(String)
     * @see java.sql.ResultSet#getFloat(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected float getFloat(final String columnName, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getFloat(columnName);
    }

    /**
     * See ResultSet#getDouble(int)
     * @see java.sql.ResultSet#getDouble(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     */
    public double getDouble(final int index)
        throws  SQLException
    {
        return getDouble(index, getResultSet());
    }

    /**
     * See ResultSet#getDouble(int)
     * @see java.sql.ResultSet#getDouble(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected double getDouble(final int index, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getDouble(index);
    }

    /**
     * See ResultSet#getDouble(String)
     * @see java.sql.ResultSet#getDouble(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     */
    public double getDouble(final String columnName)
        throws  SQLException
    {
        return getDouble(columnName, getResultSet());
    }

    /**
     * See ResultSet#getDouble(String)
     * @see java.sql.ResultSet#getDouble(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected double getDouble(
        final String columnName, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getDouble(columnName);
    }

    /**
     * See ResultSet#getMetaData()
     * @see java.sql.ResultSet#getMetaData()
     * @return (Taken from Sun's Javadoc) the description of a ResultSet
     * object's columns or null if the driver cannot return a
     * ResultSetMetaData object.
     * @exception SQLException if an error occurs.
     */
    public ResultSetMetaData getMetaData()
        throws  SQLException
    {
        return getMetaData(getResultSet());
    }

    /**
     * See ResultSet#getMetaData()
     * @see java.sql.ResultSet#getMetaData()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the description of a ResultSet
     * object's columns or null if the driver cannot return a
     * ResultSetMetaData object.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected ResultSetMetaData getMetaData(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getMetaData();
    }

    /**
     * See ResultSet#getWarnings()
     * @see java.sql.ResultSet#getWarnings()
     * @return (Taken from Sun's Javadoc) the first SQLWarning object
     * reported or null if there are none.
     * @exception SQLException if an error occurs.
     */
    public SQLWarning getWarnings()
        throws  SQLException
    {
        return getWarnings(getResultSet());
    }

    /**
     * See ResultSet#getWarnings()
     * @see java.sql.ResultSet#getWarnings()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the first SQLWarning object
     * reported or null if there are none.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected SQLWarning getWarnings(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getWarnings();
    }

    /**
     * See ResultSet#clearWarnings()
     * @see java.sql.ResultSet#clearWarnings()
     * @exception SQLException if an error occurs.
     */
    public void clearWarnings()
        throws SQLException
    {
        clearWarnings(getResultSet());
    }

    /**
     * See ResultSet#clearWarnings()
     * @see java.sql.ResultSet#clearWarnings()
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void clearWarnings(@NotNull final ResultSet resultSet)
        throws SQLException
    {
        resultSet.clearWarnings();
    }

    /**
     * See ResultSet#setFetchDirection(int)
     * @see java.sql.ResultSet#setFetchDirection(int)
     * @param direction (Taken from Sun's Javadoc) an integer specifying the
     * suggested fetch direction; one of ResultSet.FETCH_FORWARD,
     * ResultSet.FETCH_REVERSE, or ResultSet.FETCH_UNKNOWN.
     * @exception SQLException if an error occurs.
     */
    public void setFetchDirection(final int direction)
        throws  SQLException
    {
        setFetchDirection(direction, getResultSet());
    }

    /**
     * See ResultSet#setFetchDirection(int)
     * @see java.sql.ResultSet#setFetchDirection(int)
     * @param direction (Taken from Sun's Javadoc) an integer specifying the
     * suggested fetch direction; one of ResultSet.FETCH_FORWARD,
     * ResultSet.FETCH_REVERSE, or ResultSet.FETCH_UNKNOWN.
     * @exception SQLException if an error occurs.
     */
    protected void setFetchDirection(
        final int direction, @Nullable final ResultSet resultSet)
      throws  SQLException
    {
        if  (resultSet != null) 
        {
            resultSet.setFetchDirection(direction);
        }
        else
        {
            m__iTempFetchDirection = direction;
        }
    }

    /**
     * See ResultSet#getFetchDirection()
     * @see java.sql.ResultSet#getFetchDirection()
     * @return (Taken from Sun's Javadoc) the current fetch direction for
     * this ResultSet object.
     * @exception SQLException if an error occurs.
     */
    public int getFetchDirection()
        throws  SQLException
    {
        return getFetchDirection(getResultSet());
    }

    /**
     * See ResultSet#getFetchDirection()
     * @see java.sql.ResultSet#getFetchDirection()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the current fetch direction for
     * this ResultSet object.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected int getFetchDirection(@Nullable final ResultSet resultSet)
        throws  SQLException
    {
        int result = m__iTempFetchDirection;

        if  (resultSet != null) 
        {
            result = resultSet.getFetchDirection();
        }
        
        return result;
    }

    /**
     * See ResultSet#setFetchSize(int)
     * @see java.sql.ResultSet#setFetchSize(int)
     * @param size (Taken from Sun's Javadoc) the number of rows to fetch.
     * @exception SQLException if an error occurs.
     */
    public void setFetchSize(final int size)
        throws  SQLException
    {
        setFetchSize(size, getResultSet());
    }

    /**
     * See ResultSet#setFetchSize(int)
     * @see java.sql.ResultSet#setFetchSize(int)
     * @param size (Taken from Sun's Javadoc) the number of rows to fetch.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     */
    protected void setFetchSize(final int size, @Nullable final ResultSet resultSet)
        throws  SQLException
    {
        if  (resultSet != null) 
        {
            resultSet.setFetchSize(size);
        }
        else
        {
            m__iTempFetchSize = size;
        }
    }

    /**
     * See ResultSet#getFetchSize()
     * @see java.sql.ResultSet#getFetchSize()
     * @return (Taken from Sun's Javadoc) the current fetch size for this
     * ResultSet object.
     * @exception SQLException if an error occurs.
     */
    public int getFetchSize()
        throws  SQLException
    {
        return getFetchSize(getResultSet());
    }

    /**
     * See ResultSet#getFetchSize()
     * @see java.sql.ResultSet#getFetchSize()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the current fetch size for this
     * ResultSet object.
     * @exception SQLException if an error occurs.
     */
    protected int getFetchSize(@Nullable final ResultSet resultSet)
        throws  SQLException
    {
        int result = m__iTempFetchSize;

        if  (resultSet != null) 
        {
            result = resultSet.getFetchSize();
        }
        
        return result;
    }

    /**
     * See ResultSet#getString(int)
     * @see java.sql.ResultSet#getString(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public String getString(final int index)
        throws  SQLException
    {
        return getString(index, getResultSet());
    }

    /**
     * See ResultSet#getString(int)
     * @see java.sql.ResultSet#getString(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected String getString(final int index, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getString(index);
    }

    /**
     * See ResultSet#getString(String)
     * @see java.sql.ResultSet#getString(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public String getString(final String columnName)
        throws  SQLException
    {
        return getString(columnName, getResultSet());
    }

    /**
     * See ResultSet#getString(String)
     * @see java.sql.ResultSet#getString(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected String getString(
        final String columnName, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getString(columnName);
    }

    /**
     * See ResultSet#getArray(int)
     * @see java.sql.ResultSet#getArray(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Array getArray(final int index)
        throws  SQLException
    {
        return getArray(index, getResultSet());
    }

    /**
     * See ResultSet#getArray(int)
     * @see java.sql.ResultSet#getArray(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Array getArray(final int index, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getArray(index);
    }

    /**
     * See ResultSet#getArray(String)
     * @see java.sql.ResultSet#getArray(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public Array getArray(final String columnName)
        throws  SQLException
    {
        return getArray(columnName, getResultSet());
    }

    /**
     * See ResultSet#getArray(String)
     * @see java.sql.ResultSet#getArray(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Array getArray(
        final String columnName, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getArray(columnName);
    }

    /**
     * See ResultSet#getAsciiStream(int)
     * @see java.sql.ResultSet#getAsciiStream(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public InputStream getAsciiStream(final int index)
        throws  SQLException
    {
        return getAsciiStream(index, getResultSet());
    }

    /**
     * See ResultSet#getAsciiStream(int)
     * @see java.sql.ResultSet#getAsciiStream(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected InputStream getAsciiStream(
        final int index, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getAsciiStream(index);
    }

    /**
     * See ResultSet#getAsciiStream(String)
     * @see java.sql.ResultSet#getAsciiStream(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public InputStream getAsciiStream(final String columnName)
        throws  SQLException
    {
        return getAsciiStream(columnName, getResultSet());
    }

    /**
     * See ResultSet#getAsciiStream(String)
     * @see java.sql.ResultSet#getAsciiStream(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected InputStream getAsciiStream(
        final String columnName, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getAsciiStream(columnName);
    }

    /**
     * See ResultSet#getBigDecimal(int,int)
     * @see java.sql.ResultSet#getBigDecimal(int,int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param scale (Taken from Sun's Javadoc) the number of digits to the
     * right of the decimal point.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     */
    public BigDecimal getBigDecimal(final int index, final int scale)
        throws  SQLException
    {
        return getBigDecimal(index, scale, getResultSet());
    }

    /**
     * See ResultSet#getBigDecimal(int,int)
     * @see java.sql.ResultSet#getBigDecimal(int,int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param scale (Taken from Sun's Javadoc) the number of digits to the
     * right of the decimal point.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     * @precondition resultSet != null
     */
    protected BigDecimal getBigDecimal(
        final int index, final int scale, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getBigDecimal(index, scale);
    }

    /**
     * See ResultSet#getBigDecimal(String,int)
     * @see java.sql.ResultSet#getBigDecimal(java.lang.String,int)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param scale (Taken from Sun's Javadoc) the number of digits to the
     * right of the decimal point.
     * @exception SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     */
    public BigDecimal getBigDecimal(final String columnName, final int scale)
        throws  SQLException
    {
        return getBigDecimal(columnName, scale, getResultSet());
    }

    /**
     * See ResultSet#getBigDecimal(String,int)
     * @see java.sql.ResultSet#getBigDecimal(java.lang.String,int)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param scale (Taken from Sun's Javadoc) the number of digits to the
     * right of the decimal point.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     * @precondition resultSet != null
     */
    protected BigDecimal getBigDecimal(
        final String columnName, final int scale, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getBigDecimal(columnName, scale);
    }

    /**
     * See ResultSet#getBigDecimal(int)
     * @see java.sql.ResultSet#getBigDecimal(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public BigDecimal getBigDecimal(final int index)
        throws  SQLException
    {
        return getBigDecimal(index, getResultSet());
    }

    /**
     * See ResultSet#getBigDecimal(int)
     * @see java.sql.ResultSet#getBigDecimal(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected BigDecimal getBigDecimal(
        final int index, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getBigDecimal(index);
    }

    /**
     * See ResultSet#getBigDecimal(String)
     * @see java.sql.ResultSet#getBigDecimal(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public BigDecimal getBigDecimal(final String columnName)
        throws  SQLException
    {
        return getBigDecimal(columnName, getResultSet());
    }

    /**
     * See ResultSet#getBigDecimal(String)
     * @see java.sql.ResultSet#getBigDecimal(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected BigDecimal getBigDecimal(
        final String columnName, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getBigDecimal(columnName);
    }

    /**
     * See ResultSet#getBinaryStream(int)
     * @see java.sql.ResultSet#getBinaryStream(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public InputStream getBinaryStream(final int index)
        throws  SQLException
    {
        return getBinaryStream(index, getResultSet());
    }

    /**
     * See ResultSet#getBinaryStream(int)
     * @see java.sql.ResultSet#getBinaryStream(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected InputStream getBinaryStream(
        final int index, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getBinaryStream(index);
    }

    /**
     * See ResultSet#getBinaryStream(String)
     * @see java.sql.ResultSet#getBinaryStream(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public InputStream getBinaryStream(final String columnName)
        throws  SQLException
    {
        return getBinaryStream(columnName, getResultSet());
    }

    /**
     * See ResultSet#getBinaryStream(String)
     * @see java.sql.ResultSet#getBinaryStream(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected InputStream getBinaryStream(
        final String columnName, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getBinaryStream(columnName);
    }

    /**
     * See ResultSet#getBlob(int)
     * @see java.sql.ResultSet#getBlob(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Blob getBlob(final int index)
        throws  SQLException
    {
        return getBlob(index, getResultSet());
    }

    /**
     * See ResultSet#getBlob(int)
     * @see java.sql.ResultSet#getBlob(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Blob getBlob(final int index, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getBlob(index);
    }

    /**
     * See ResultSet#getBlob(String)
     * @see java.sql.ResultSet#getBlob(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Blob getBlob(final String columnName)
        throws  SQLException
    {
        return getBlob(columnName, getResultSet());
    }

    /**
     * See ResultSet#getBlob(String)
     * @see java.sql.ResultSet#getBlob(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Blob getBlob(final String columnName, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getBlob(columnName);
    }

    /**
     * See ResultSet#getClob(int)
     * @see java.sql.ResultSet#getClob(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Clob getClob(final int index)
        throws  SQLException
    {
        return getClob(index, getResultSet());
    }

    /**
     * See ResultSet#getClob(int)
     * @see java.sql.ResultSet#getClob(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Clob getClob(final int index, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getClob(index);
    }

    /**
     * See ResultSet#getClob(String)
     * @see java.sql.ResultSet#getClob(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Clob getClob(final String columnName)
        throws  SQLException
    {
        return getClob(columnName, getResultSet());
    }

    /**
     * See ResultSet#getClob(String)
     * @see java.sql.ResultSet#getClob(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Clob getClob(final String columnName, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getClob(columnName);
    }

    /**
     * See ResultSet#getTimestamp(int)
     * @see java.sql.ResultSet#getTimestamp(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Timestamp getTimestamp(final int index)
        throws  SQLException
    {
        return getTimestamp(index, getResultSet());
    }

    /**
     * See ResultSet#getTimestamp(int)
     * @see java.sql.ResultSet#getTimestamp(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Timestamp getTimestamp(
        final int index, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getTimestamp(index);
    }

    /**
     * See ResultSet#getTimestamp(String)
     * @see java.sql.ResultSet#getTimestamp(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Timestamp getTimestamp(final String columnName)
        throws  SQLException
    {
        return getTimestamp(columnName, getResultSet());
    }

    /**
     * See ResultSet#getTimestamp(String)
     * @see java.sql.ResultSet#getTimestamp(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Timestamp getTimestamp(
        final String columnName, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getTimestamp(columnName);
    }

    /**
     * See ResultSet#getTimestamp(int,Calendar)
     * @see java.sql.ResultSet#getTimestamp(int,java.util.Calendar)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Timestamp getTimestamp(final int index, final Calendar calendar)
        throws  SQLException
    {
        return getTimestamp(index, calendar, getResultSet());
    }

    /**
     * See ResultSet#getTimestamp(int,Calendar)
     * @see java.sql.ResultSet#getTimestamp(int,java.util.Calendar)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Timestamp getTimestamp(
        final int index, final Calendar calendar, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getTimestamp(index, calendar);
    }

    /**
     * See ResultSet#getTimestamp(String,Calendar)
     * @see java.sql.ResultSet#getTimestamp(java.lang.String,java.util.Calendar)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Timestamp getTimestamp(final String columnName, final Calendar calendar)
        throws  SQLException
    {
        return getTimestamp(columnName, calendar, getResultSet());
    }

    /**
     * See ResultSet#getTimestamp(String,Calendar)
     * @see java.sql.ResultSet#getTimestamp(java.lang.String,java.util.Calendar)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Timestamp getTimestamp(
        final String columnName,
        final Calendar calendar,
        @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getTimestamp(columnName, calendar);
    }

    /**
     * See ResultSet#getUnicodeStream(int)
     * @see java.sql.ResultSet#getUnicodeStream(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     */
    public InputStream getUnicodeStream(final int index)
        throws  SQLException
    {
        return getUnicodeStream(index, getResultSet());
    }

    /**
     * See ResultSet#getUnicodeStream(int)
     * @see java.sql.ResultSet#getUnicodeStream(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     * @precondition resultSet != null
     */
    protected InputStream getUnicodeStream(
        final int index, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getUnicodeStream(index);
    }

    /**
     * See ResultSet#getUnicodeStream(String)
     * @see java.sql.ResultSet#getUnicodeStream(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     */
    public InputStream getUnicodeStream(final String columnName)
        throws  SQLException
    {
        return getUnicodeStream(columnName, getResultSet());
    }

    /**
     * See ResultSet#getUnicodeStream(String)
     * @see java.sql.ResultSet#getUnicodeStream(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     * @precondition resultSet != null
     */
    protected InputStream getUnicodeStream(
        final String columnName, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getUnicodeStream(columnName);
    }

    /**
     * See ResultSet#wasNull()
     * @see java.sql.ResultSet#wasNull()
     * @return (Taken from Sun's Javadoc) <code>true</code> if the last
     * column value read was SQL NULL and <code>false</code> otherwise.
     * @exception SQLException if an error occurs.
     */
    public boolean wasNull()
        throws  SQLException
    {
        return wasNull(getResultSet());
    }

    /**
     * See ResultSet#wasNull()
     * @see java.sql.ResultSet#wasNull()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) <code>true</code> if the last
     * column value read was SQL NULL and <code>false</code> otherwise.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected boolean wasNull(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.wasNull();
    }

    /**
     * See ResultSet#getCharacterStream(int)
     * @see java.sql.ResultSet#getCharacterStream(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Reader getCharacterStream(final int index)
        throws  SQLException
    {
        return getCharacterStream(index, getResultSet());
    }

    /**
     * See ResultSet#getCharacterStream(int)
     * @see java.sql.ResultSet#getCharacterStream(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Reader getCharacterStream(
        final int index, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getCharacterStream(index);
    }

    /**
     * See ResultSet#getCharacterStream(String)
     * @see java.sql.ResultSet#getCharacterStream(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Reader getCharacterStream(final String columnName)
        throws  SQLException
    {
        return getCharacterStream(columnName, getResultSet());
    }

    /**
     * See ResultSet#getCharacterStream(String)
     * @see java.sql.ResultSet#getCharacterStream(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Reader getCharacterStream(
        final String columnName, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getCharacterStream(columnName);
    }

    /**
     * See ResultSet#absolute(int)
     * @see java.sql.ResultSet#absolute(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) <code>true</code> if the cursor
     * is on the result set; <code>false</code> otherwise.
     * @exception SQLException if an error occurs.
     */
    public boolean absolute(final int index)
        throws  SQLException
    {
        return absolute(index, getResultSet());
    }

    /**
     * See ResultSet#absolute(int)
     * @see java.sql.ResultSet#absolute(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) <code>true</code> if the cursor
     * is on the result set; <code>false</code> otherwise.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected boolean absolute(final int index, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.absolute(index);
    }

    /**
     * See ResultSet#afterLast()
     * @see java.sql.ResultSet#afterLast()
     * @exception SQLException if an error occurs.
     */
    public void afterLast()
        throws  SQLException
    {
        afterLast(getResultSet());
    }

    /**
     * See ResultSet#afterLast()
     * @param resultSet the result set.
     * @see java.sql.ResultSet#afterLast()
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void afterLast(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        resultSet.afterLast();
    }

    /**
     * See ResultSet#beforeFirst()
     * @see java.sql.ResultSet#beforeFirst()
     * @exception SQLException if an error occurs.
     */
    public void beforeFirst()
        throws  SQLException
    {
        beforeFirst(getResultSet());
    }

    /**
     * See ResultSet#beforeFirst()
     * @see java.sql.ResultSet#beforeFirst()
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void beforeFirst(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        resultSet.beforeFirst();
    }

    /**
     * See ResultSet#cancelRowUpdates()
     * @see java.sql.ResultSet#cancelRowUpdates()
     * @exception SQLException if an error occurs.
     */
    public void cancelRowUpdates()
        throws  SQLException
    {
        cancelRowUpdates(getResultSet());
    }

    /**
     * See ResultSet#cancelRowUpdates()
     * @see java.sql.ResultSet#cancelRowUpdates()
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void cancelRowUpdates(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        resultSet.cancelRowUpdates();
    }

    /**
     * See ResultSet#deleteRow()
     * @see java.sql.ResultSet#deleteRow()
     * @exception SQLException if an error occurs.
     */
    public void deleteRow()
        throws  SQLException
    {
        deleteRow(getResultSet());
    }

    /**
     * See ResultSet#deleteRow()
     * @see java.sql.ResultSet#deleteRow()
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void deleteRow(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        resultSet.deleteRow();
    }

    /**
     * See ResultSet#findColumn(String)
     * @see java.sql.ResultSet#findColumn(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @return (Taken from Sun's Javadoc) the column index of the
     * given column name.
     * @exception SQLException if an error occurs.
     */
    public int findColumn(final String columnName)
        throws  SQLException
    {
        return findColumn(columnName, getResultSet());
    }

    /**
     * See ResultSet#findColumn(String)
     * @see java.sql.ResultSet#findColumn(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column index of the
     * given column name.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected int findColumn(final String columnName, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.findColumn(columnName);
    }

    /**
     * See ResultSet#getConcurrency()
     * @see java.sql.ResultSet#getConcurrency()
     * @return (Taken from Sun's Javadoc) the concurrency type, either
     * ResultSet.CONCUR_READ_ONLY or ResultSet.CONCUR_UPDATABLE.
     * @exception SQLException if an error occurs.
     */
    public int getConcurrency()
        throws  SQLException
    {
        return getConcurrency(getResultSet());
    }

    /**
     * See ResultSet#getConcurrency()
     * @see java.sql.ResultSet#getConcurrency()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the concurrency type, either
     * ResultSet.CONCUR_READ_ONLY or ResultSet.CONCUR_UPDATABLE.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected int getConcurrency(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getConcurrency();
    }

    /**
     * See ResultSet#getCursorName()
     * @see java.sql.ResultSet#getCursorName()
     * @return (Taken from Sun's Javadoc) the SQL name for this ResultSet
     * object's cursor.
     * @exception SQLException if an error occurs.
     */
    public String getCursorName()
        throws  SQLException
    {
        return getCursorName(getResultSet());
    }

    /**
     * See ResultSet#getCursorName()
     * @see java.sql.ResultSet#getCursorName()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the SQL name for this ResultSet
     * object's cursor.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected String getCursorName(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getCursorName();
    }

    /**
     * See ResultSet#getRow()
     * @see java.sql.ResultSet#getRow()
     * @return (Taken from Sun's Javadoc) the current row number;
     * 0 if there is no current row.
     * @exception SQLException if an error occurs.
     */
    public int getRow()
        throws  SQLException
    {
        return getRow(getResultSet());
    }

    /**
     * See ResultSet#getRow()
     * @see java.sql.ResultSet#getRow()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the current row number;
     * 0 if there is no current row.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected int getRow(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getRow();
    }

    /**
     * See ResultSet#getStatement()
     * @see java.sql.ResultSet#getStatement()
     * @return (Taken from Sun's Javadoc) the Statement object that produced
     * this ResultSet object or null if the result set was produced some
     * other way.
     * @exception SQLException if an error occurs.
     */
    public Statement getStatement()
        throws SQLException
    {
        return getStatement(getQuery(), getResultSet());
    }

    /**
     * See ResultSet#getStatement()
     * @see java.sql.ResultSet#getStatement()
     * @param query the query.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the Statement object that produced
     * this ResultSet object or null if the result set was produced some
     * other way.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    public Statement getStatement(
        final Statement query, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        Statement result = query;

        if  (result == null) 
        {
            result = resultSet.getStatement();
        }
        
        return result;
    }

    /**
     * See ResultSet#insertRow()
     * @see java.sql.ResultSet#insertRow()
     * @exception SQLException if an error occurs.
     */
    public void insertRow()
        throws  SQLException
    {
        insertRow(getResultSet());
    }

    /**
     * See ResultSet#insertRow()
     * @see java.sql.ResultSet#insertRow()
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void insertRow(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        resultSet.insertRow();
    }

    /**
     * See ResultSet#isAfterLast()
     * @see java.sql.ResultSet#isAfterLast()
     * @return (Taken from Sun's Javadoc) <code>true</code> if the cursor is
     * after the last row; <code>false</code> if the cursor is at any other
     * position or the result set contains no rows.
     * @exception SQLException if an error occurs.
     */
    public boolean isAfterLast()
        throws  SQLException
    {
        return isAfterLast(getResultSet());
    }

    /**
     * See ResultSet#isAfterLast()
     * @see java.sql.ResultSet#isAfterLast()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) <code>true</code> if the cursor is
     * after the last row; <code>false</code> if the cursor is at any other
     * position or the result set contains no rows.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected boolean isAfterLast(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.isAfterLast();
    }

    /**
     * See ResultSet#isBeforeFirst()
     * @see java.sql.ResultSet#isBeforeFirst()
     * @return (Taken from Sun's Javadoc) <code>true</code> if the cursor is
     * before the first row; <code>false</code> if the cursor is at any other
     * position or the result set contains no rows.
     * @exception SQLException if an error occurs.
     */
    public boolean isBeforeFirst()
        throws  SQLException
    {
        return isBeforeFirst(getResultSet());
    }

    /**
     * See ResultSet#isBeforeFirst()
     * @see java.sql.ResultSet#isBeforeFirst()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) <code>true</code> if the cursor is
     * before the first row; <code>false</code> if the cursor is at any other
     * position or the result set contains no rows.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected boolean isBeforeFirst(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.isBeforeFirst();
    }

    /**
     * See ResultSet#isFirst()
     * @see java.sql.ResultSet#isFirst()
     * @return (Taken from Sun's Javadoc) <code>true</code> if the cursor is
     * on the first row; <code>false</code> otherwise.
     * @exception SQLException if an error occurs.
     */
    public boolean isFirst()
        throws  SQLException
    {
        return isFirst(getResultSet());
    }

    /**
     * See ResultSet#isFirst()
     * @see java.sql.ResultSet#isFirst()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) <code>true</code> if the cursor is
     * on the first row; <code>false</code> otherwise.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected boolean isFirst(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.isFirst();
    }

    /**
     * See ResultSet#isLast()
     * @see java.sql.ResultSet#isLast()
     * @return (Taken from Sun's Javadoc) <code>true</code> if the cursor is
     * on the last row; <code>false</code> otherwise.
     * @exception SQLException if an error occurs.
     */
    public boolean isLast()
        throws  SQLException
    {
        return isLast(getResultSet());
    }

    /**
     * See ResultSet#isLast()
     * @see java.sql.ResultSet#isLast()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) <code>true</code> if the cursor is
     * on the last row; <code>false</code> otherwise.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected boolean isLast(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.isLast();
    }

    /**
     * See ResultSet#last()
     * @see java.sql.ResultSet#last()
     * @return (Taken from Sun's Javadoc) <code>true</code> if the cursor is
     * on a valid row; <code>false</code> if there are no rows in the result
     * set.
     * @exception SQLException if an error occurs.
     */
    public boolean last()
        throws  SQLException
    {
        return last(getResultSet());
    }

    /**
     * See ResultSet#last()
     * @see java.sql.ResultSet#last()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) <code>true</code> if the cursor is
     * on a valid row; <code>false</code> if there are no rows in the result
     * set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected boolean last(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.last();
    }

    /**
     * See ResultSet#moveToCurrentRow()
     * @see java.sql.ResultSet#moveToCurrentRow()
     * @exception SQLException if an error occurs.
     */
    public void moveToCurrentRow()
        throws  SQLException
    {
        moveToCurrentRow(getResultSet());
    }

    /**
     * See ResultSet#moveToCurrentRow()
     * @see java.sql.ResultSet#moveToCurrentRow()
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void moveToCurrentRow(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        resultSet.moveToCurrentRow();
    }

    /**
     * See ResultSet#moveToInsertRow()
     * @see java.sql.ResultSet#moveToInsertRow()
     * @exception SQLException if an error occurs.
     */
    public void moveToInsertRow()
        throws  SQLException
    {
        moveToInsertRow(getResultSet());
    }

    /**
     * See ResultSet#moveToInsertRow()
     * @see java.sql.ResultSet#moveToInsertRow()
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void moveToInsertRow(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        resultSet.moveToInsertRow();
    }

    /**
     * See ResultSet#refreshRow()
     * @see java.sql.ResultSet#refreshRow()
     * @exception SQLException if an error occurs.
     */
    public void refreshRow()
        throws  SQLException
    {
        refreshRow(getResultSet());
    }

    /**
     * See ResultSet#refreshRow()
     * @see java.sql.ResultSet#refreshRow()
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void refreshRow(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        resultSet.refreshRow();
    }

    /**
     * See ResultSet#relative(int)
     * @see java.sql.ResultSet#relative(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @return (Taken from Sun's Javadoc) <code>true</code> if the cursor is
     * on a row; <code>false</code> otherwise.
     * @exception SQLException if an error occurs.
     */
    public boolean relative(final int index)
        throws  SQLException
    {
        return relative(index, getResultSet());
    }

    /**
     * See ResultSet#relative(int)
     * @see java.sql.ResultSet#relative(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) <code>true</code> if the cursor is
     * on a row; <code>false</code> otherwise.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected boolean relative(final int index, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.relative(index);
    }

    /**
     * See ResultSet#rowDeleted()
     * @see java.sql.ResultSet#rowDeleted()
     * @return (Taken from Sun's Javadoc) <code>true</code> if a row was
     * deleted and deletions are detected; <code>false</code> otherwise.
     * @exception SQLException if an error occurs.
     */
    public boolean rowDeleted()
        throws  SQLException
    {
        return rowDeleted(getResultSet());
    }

    /**
     * See ResultSet#rowDeleted()
     * @see java.sql.ResultSet#rowDeleted()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) <code>true</code> if a row was
     * deleted and deletions are detected; <code>false</code> otherwise.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected boolean rowDeleted(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.rowDeleted();
    }

    /**
     * See ResultSet#rowInserted()
     * @see java.sql.ResultSet#rowInserted()
     * @return (Taken from Sun's Javadoc) <code>true</code> if a row has had
     * an insertion and insertions are detected; <code>false</code> otherwise.
     * @exception SQLException if an error occurs.
     */
    public boolean rowInserted()
        throws  SQLException
    {
        return rowInserted(getResultSet());
    }

    /**
     * See ResultSet#rowInserted()
     * @see java.sql.ResultSet#rowInserted()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) <code>true</code> if a row has had
     * an insertion and insertions are detected; <code>false</code> otherwise.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected boolean rowInserted(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.rowInserted();
    }

    /**
     * See ResultSet#rowUpdated()
     * @see java.sql.ResultSet#rowUpdated()
     * @return (Taken from Sun's Javadoc) <code>true</code> if both (1) the
     * row has been visibly updated by the owner or another and (2) updates
     * are detected.
     * @exception SQLException if an error occurs.
     */
    public boolean rowUpdated()
        throws  SQLException
    {
        return rowUpdated(getResultSet());
    }

    /**
     * See ResultSet#rowUpdated()
     * @see java.sql.ResultSet#rowUpdated()
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) <code>true</code> if both (1) the
     * row has been visibly updated by the owner or another and (2) updates
     * are detected.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected boolean rowUpdated(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.rowUpdated();
    }

    /**
     * See ResultSet#updateAsciiStream(int,InputStream,int)
     * @see java.sql.ResultSet#updateAsciiStream(int,java.io.InputStream,int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param length (Taken from Sun's Javadoc) the length of the stream.
     * @exception SQLException if an error occurs.
     */
    public void updateAsciiStream(
        final int index,
        final InputStream value,
        final int length)
      throws  SQLException
    {
        updateAsciiStream(index, value, length, getResultSet());
    }

    /**
     * See ResultSet#updateAsciiStream(int,InputStream,int)
     * @see java.sql.ResultSet#updateAsciiStream(int,java.io.InputStream,int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param length (Taken from Sun's Javadoc) the length of the stream.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateAsciiStream(
        final int index,
        final InputStream value,
        final int length,
        @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateAsciiStream(index, value, length);
    }

    /**
     * See ResultSet#updateAsciiStream(String,InputStream,int)
     * @see java.sql.ResultSet#updateAsciiStream(java.lang.String,java.io.InputStream,int)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param length (Taken from Sun's Javadoc) the length of the stream.
     * @exception SQLException if an error occurs.
     */
    public void updateAsciiStream(
        final String columnName,
        final InputStream value,
        final int length)
      throws  SQLException
    {
        updateAsciiStream(columnName, value, length, getResultSet());
    }

    /**
     * See ResultSet#updateAsciiStream(String,InputStream,int)
     * @see java.sql.ResultSet#updateAsciiStream(java.lang.String,java.io.InputStream,int)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param length (Taken from Sun's Javadoc) the length of the stream.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateAsciiStream(
        final String columnName,
        final InputStream value,
        final int length,
        @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateAsciiStream(columnName, value, length);
    }

    /**
     * See ResultSet#updateBigDecimal(int,BigDecimal)
     * @see java.sql.ResultSet#updateBigDecimal(int,java.math.BigDecimal)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value a <code>BigDecimal</code> value
     * @exception SQLException if an error occurs.
     */
    public void updateBigDecimal(final int index, final BigDecimal value)
        throws  SQLException
    {
        updateBigDecimal(index, value, getResultSet());
    }

    /**
     * See ResultSet#updateBigDecimal(int,BigDecimal)
     * @see java.sql.ResultSet#updateBigDecimal(int,java.math.BigDecimal)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value a <code>BigDecimal</code> value
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateBigDecimal(
        final int index, final BigDecimal value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateBigDecimal(index, value);
    }

    /**
     * See ResultSet#updateBigDecimal(String,BigDecimal)
     * @see java.sql.ResultSet#updateBigDecimal(java.lang.String,java.math.BigDecimal)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value a <code>BigDecimal</code> value
     * @exception SQLException if an error occurs.
     */
    public void updateBigDecimal(final String columnName, final BigDecimal value)
        throws  SQLException
    {
        updateBigDecimal(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateBigDecimal(String,BigDecimal)
     * @see java.sql.ResultSet#updateBigDecimal(java.lang.String,java.math.BigDecimal)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value a <code>BigDecimal</code> value
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateBigDecimal(
        final String columnName,
        final BigDecimal value,
        @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateBigDecimal(columnName, value);
    }

    /**
     * See ResultSet#updateBinaryStream(int,InputStream,int)
     * @see java.sql.ResultSet#updateBinaryStream(int,java.io.InputStream,int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param length (Taken from Sun's Javadoc) the length of the stream.
     * @exception SQLException if an error occurs.
     */
    public void updateBinaryStream(
        final int index,
        final InputStream value,
        final int length)
      throws  SQLException
    {
        updateBinaryStream(index, value, length, getResultSet());
    }

    /**
     * See ResultSet#updateBinaryStream(int,InputStream,int)
     * @see java.sql.ResultSet#updateBinaryStream(int,java.io.InputStream,int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param length (Taken from Sun's Javadoc) the length of the stream.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateBinaryStream(
        final int index,
        final InputStream value,
        final int length,
        @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateBinaryStream(index, value, length);
    }

    /**
     * See ResultSet#updateBinaryStream(String,InputStream,int)
     * @see java.sql.ResultSet#updateBinaryStream(java.lang.String,java.io.InputStream,int)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param length (Taken from Sun's Javadoc) the length of the stream.
     * @exception SQLException if an error occurs.
     */
    public void updateBinaryStream(
        final String columnName,
        final InputStream value,
        final int length)
      throws  SQLException
    {
        updateBinaryStream(columnName, value, length, getResultSet());
    }

    /**
     * See ResultSet#updateBinaryStream(String,InputStream,int)
     * @see java.sql.ResultSet#updateBinaryStream(java.lang.String,java.io.InputStream,int)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param length (Taken from Sun's Javadoc) the length of the stream.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateBinaryStream(
        final String columnName,
        final InputStream value,
        final int length,
        @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateBinaryStream(columnName, value, length);
    }

    /**
     * See ResultSet#updateBoolean(int,boolean)
     * @see java.sql.ResultSet#updateBoolean(int,boolean)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateBoolean(final int index, final boolean value)
        throws  SQLException
    {
        updateBoolean(index, value, getResultSet());
    }

    /**
     * See ResultSet#updateBoolean(int,boolean)
     * @see java.sql.ResultSet#updateBoolean(int,boolean)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateBoolean(
        final int index, final boolean value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateBoolean(index, value);
    }

    /**
     * See ResultSet#updateBoolean(String,boolean)
     * @see java.sql.ResultSet#updateBoolean(java.lang.String,boolean)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateBoolean(final String columnName, final boolean value)
        throws  SQLException
    {
        updateBoolean(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateBoolean(String,boolean)
     * @see java.sql.ResultSet#updateBoolean(java.lang.String,boolean)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateBoolean(
        final String columnName,
        final boolean value,
        @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateBoolean(columnName, value);
    }

    /**
     * See ResultSet#updateByte(int,byte)
     * @see java.sql.ResultSet#updateByte(int,byte)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateByte(final int index, final byte value)
        throws  SQLException
    {
        updateByte(index, value, getResultSet());
    }

    /**
     * See ResultSet#updateByte(int,byte)
     * @see java.sql.ResultSet#updateByte(int,byte)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateByte(
        final int index, final byte value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateByte(index, value);
    }

    /**
     * See ResultSet#updateByte(String,byte)
     * @see java.sql.ResultSet#updateByte(java.lang.String,byte)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateByte(final String columnName, final byte value)
        throws  SQLException
    {
        updateByte(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateByte(String,byte)
     * @see java.sql.ResultSet#updateByte(java.lang.String,byte)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateByte(
        final String columnName, final byte value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateByte(columnName, value);
    }

    /**
     * See ResultSet#updateBytes(int,byte[])
     * @see java.sql.ResultSet#updateBytes(int,byte[])
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateBytes(final int index, final byte[] value)
        throws  SQLException
    {
        updateBytes(index, value, getResultSet());
    }

    /**
     * See ResultSet#updateBytes(int,byte[])
     * @see java.sql.ResultSet#updateBytes(int,byte[])
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateBytes(
        final int index, final byte[] value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateBytes(index, value);
    }

    /**
     * See ResultSet#updateBytes(String,byte[])
     * @see java.sql.ResultSet#updateBytes(java.lang.String,byte[])
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateBytes(final String columnName, final byte[] value)
        throws  SQLException
    {
        updateBytes(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateBytes(String,byte[])
     * @see java.sql.ResultSet#updateBytes(java.lang.String,byte[])
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateBytes(
        final String columnName, final byte[] value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateBytes(columnName, value);
    }

    /**
     * See ResultSet#updateCharacterStream(int,Reader,int)
     * @see java.sql.ResultSet#updateCharacterStream(int,java.io.Reader,int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param length (Taken from Sun's Javadoc) the length of the stream.
     * @exception SQLException if an error occurs.
     */
    public void updateCharacterStream(final int index, Reader value, final int length)
        throws  SQLException
    {
        updateCharacterStream(index, value, length, getResultSet());
    }

    /**
     * See ResultSet#updateCharacterStream(int,Reader,int)
     * @see java.sql.ResultSet#updateCharacterStream(int,java.io.Reader,int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param length (Taken from Sun's Javadoc) the length of the stream.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateCharacterStream(
        final int index, Reader value, final int length, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateCharacterStream(index, value, length);
    }

    /**
     * See ResultSet#updateCharacterStream(String,Reader,int)
     * @see java.sql.ResultSet#updateCharacterStream(java.lang.String,java.io.Reader,int)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param length (Taken from Sun's Javadoc) the length of the stream.
     * @exception SQLException if an error occurs.
     */
    public void updateCharacterStream(
        final String columnName,
        final Reader value,
        final int length)
      throws  SQLException
    {
        updateCharacterStream(columnName, value, length, getResultSet());
    }

    /**
     * See ResultSet#updateCharacterStream(String,Reader,int)
     * @see java.sql.ResultSet#updateCharacterStream(java.lang.String,java.io.Reader,int)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param length (Taken from Sun's Javadoc) the length of the stream.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateCharacterStream(
        final String columnName,
        final Reader value,
        final int length,
        @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateCharacterStream(columnName, value, length);
    }

    /**
     * See ResultSet#updateDate(int,Date)
     * @see java.sql.ResultSet#updateDate(int,java.sql.Date)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateDate(final int index, final Date value)
        throws  SQLException
    {
        updateDate(index, value, getResultSet());
    }

    /**
     * See ResultSet#updateDate(int,Date)
     * @see java.sql.ResultSet#updateDate(int,java.sql.Date)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateDate(
        final int index, final Date value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateDate(index, value);
    }

    /**
     * See ResultSet#updateDate(String,Date)
     * @see java.sql.ResultSet#updateDate(java.lang.String,java.sql.Date)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateDate(final String columnName, final Date value)
        throws  SQLException
    {
        updateDate(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateDate(String,Date)
     * @see java.sql.ResultSet#updateDate(java.lang.String,java.sql.Date)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateDate(
        final String columnName, final Date value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateDate(columnName, value);
    }

    /**
     * See ResultSet#updateDouble(int,double)
     * @see java.sql.ResultSet#updateDouble(int,double)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateDouble(final int index, final double value)
        throws  SQLException
    {
        updateDouble(index, value, getResultSet());
    }

    /**
     * See ResultSet#updateDouble(int,double)
     * @see java.sql.ResultSet#updateDouble(int,double)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateDouble(
        final int index, final double value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateDouble(index, value);
    }

    /**
     * See ResultSet#updateDouble(String,double)
     * @see java.sql.ResultSet#updateDouble(String,double)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateDouble(final String columnName, final double value)
        throws  SQLException
    {
        updateDouble(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateDouble(String,double)
     * @see java.sql.ResultSet#updateDouble(String,double)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateDouble(
        final String columnName, final double value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateDouble(columnName, value);
    }

    /**
     * See ResultSet#updateFloat(int,float)
     * @see java.sql.ResultSet#updateFloat(int,float)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateFloat(final int index, final float value)
        throws  SQLException
    {
        updateFloat(index, value, getResultSet());
    }

    /**
     * See ResultSet#updateFloat(int,float)
     * @see java.sql.ResultSet#updateFloat(int,float)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateFloat(
        final int index, final float value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateFloat(index, value);
    }

    /**
     * See ResultSet#updateFloat(String,float)
     * @see java.sql.ResultSet#updateFloat(String,float)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs
     */
    public void updateFloat(final String columnName, final float value)
        throws  SQLException
    {
        updateFloat(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateFloat(String,float)
     * @see java.sql.ResultSet#updateFloat(String,float)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs
     * @precondition resultSet != null
     */
    protected void updateFloat(
        final String columnName, final float value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateFloat(columnName, value);
    }

    /**
     * See ResultSet#updateInt(int,int)
     * @see java.sql.ResultSet#updateInt(int,int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateInt(final int index, final int value)
        throws  SQLException
    {
        updateInt(index, value, getResultSet());
    }

    /**
     * See ResultSet#updateInt(int,int)
     * @see java.sql.ResultSet#updateInt(int,int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateInt(
        final int index, final int value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateInt(index, value);
    }

    /**
     * See ResultSet#updateInt(String,int)
     * @see java.sql.ResultSet#updateInt(java.lang.String,int)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateInt(final String columnName, final int value)
        throws  SQLException
    {
        updateInt(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateInt(String,int)
     * @see java.sql.ResultSet#updateInt(java.lang.String,int)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateInt(
        final String columnName, final int value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateInt(columnName, value);
    }

    /**
     * See ResultSet#updateLong(int,long)
     * @see java.sql.ResultSet#updateLong(int,long)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateLong(final int index, final long value)
        throws  SQLException
    {
        updateLong(index, value, getResultSet());
    }

    /**
     * See ResultSet#updateLong(int,long)
     * @see java.sql.ResultSet#updateLong(int,long)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateLong(
        final int index, final long value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateLong(index, value);
    }

    /**
     * See ResultSet#updateLong(String,long)
     * @see java.sql.ResultSet#updateLong(java.lang.String,long)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs
     */
    public void updateLong(final String columnName, final long value)
        throws  SQLException
    {
        updateLong(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateLong(String,long)
     * @see java.sql.ResultSet#updateLong(java.lang.String,long)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs
     * @precondition resultSet != null
     */
    protected void updateLong(
        final String columnName, final long value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateLong(columnName, value);
    }

    /**
     * See ResultSet#updateNull(int)
     * @see java.sql.ResultSet#updateNull(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @exception SQLException if an error occurs.
     */
    public void updateNull(final int index)
        throws  SQLException
    {
        updateNull(index, getResultSet());
    }

    /**
     * See ResultSet#updateNull(int)
     * @see java.sql.ResultSet#updateNull(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateNull(final int index, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        resultSet.updateNull(index);
    }

    /**
     * See ResultSet#updateNull(String)
     * @see java.sql.ResultSet#updateNull(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public void updateNull(final String columnName)
        throws  SQLException
    {
        updateNull(columnName, getResultSet());
    }

    /**
     * See ResultSet#updateNull(String)
     * @see java.sql.ResultSet#updateNull(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateNull(
        final String columnName, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateNull(columnName);
    }

    /**
     * See ResultSet#updateObject(int,Object,int)
     * @see java.sql.ResultSet#updateObject(int,java.lang.Object,int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param scale (Taken from Sun's Javadoc) for java.sql.Types.DECIMA
     * or java.sql.Types.NUMERIC types, this is the number of digits after
     * the decimal point. For all other types this value will be ignored.
     * @exception SQLException if an error occurs.
     */
    public void updateObject(
        final int index, final Object value, final int scale)
      throws  SQLException
    {
        updateObject(index, value, scale, getResultSet());
    }

    /**
     * See ResultSet#updateObject(int,Object,int)
     * @see java.sql.ResultSet#updateObject(int,java.lang.Object,int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param scale (Taken from Sun's Javadoc) for java.sql.Types.DECIMA
     * or java.sql.Types.NUMERIC types, this is the number of digits after
     * the decimal point. For all other types this value will be ignored.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateObject(
        final int index,
        final Object value,
        final int scale,
        @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateObject(index, value, scale);
    }

    /**
     * See ResultSet#updateObject(int,Object)
     * @see java.sql.ResultSet#updateObject(int,java.lang.Object)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateObject(final int index, final Object value)
        throws  SQLException
    {
        updateObject(index, value, getResultSet());
    }

    /**
     * See ResultSet#updateObject(int,Object)
     * @see java.sql.ResultSet#updateObject(int,java.lang.Object)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateObject(
        final int index, final Object value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateObject(index, value);
    }

    /**
     * See ResultSet#updateObject(String,Object,int)
     * @see java.sql.ResultSet#updateObject(java.lang.String,java.lang.Object,int)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param scale (Taken from Sun's Javadoc) for java.sql.Types.DECIMA
     * or java.sql.Types.NUMERIC types, this is the number of digits after
     * the decimal point. For all other types this value will be ignored.
     * @exception SQLException if an error occurs.
     */
    public void updateObject(
        final String columnName, final Object value, final int scale)
      throws  SQLException
    {
        updateObject(columnName, value, scale, getResultSet());
    }

    /**
     * See ResultSet#updateObject(String,Object,int)
     * @see java.sql.ResultSet#updateObject(java.lang.String,java.lang.Object,int)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param scale (Taken from Sun's Javadoc) for java.sql.Types.DECIMA
     * or java.sql.Types.NUMERIC types, this is the number of digits after
     * the decimal point. For all other types this value will be ignored.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateObject(
        final String columnName,
        final Object value,
        final int scale,
        @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateObject(columnName, value, scale);
    }

    /**
     * See ResultSet#updateObject(String,Object)
     * @see java.sql.ResultSet#updateObject(java.lang.String,java.lang.Object)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateObject(final String columnName, final Object value)
        throws  SQLException
    {
        updateObject(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateObject(String,Object)
     * @see java.sql.ResultSet#updateObject(java.lang.String,java.lang.Object)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateObject(
        final String columnName, final Object value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateObject(columnName, value);
    }

    /**
     * See ResultSet#updateRow()
     * @see java.sql.ResultSet#updateRow()
     * @exception SQLException if an error occurs.
     */
    public void updateRow()
        throws  SQLException
    {
        updateRow(getResultSet());
    }

    /**
     * See ResultSet#updateRow()
     * @param resultSet the result set.
     * @see java.sql.ResultSet#updateRow()
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateRow(@NotNull final ResultSet resultSet)
        throws  SQLException
    {
        resultSet.updateRow();
    }

    /**
     * See ResultSet#updateShort(int,short)
     * @see java.sql.ResultSet#updateShort(int,short)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateShort(final int index, final short value)
        throws  SQLException
    {
        updateShort(index, value, getResultSet());
    }

    /**
     * See ResultSet#updateShort(int,short)
     * @see java.sql.ResultSet#updateShort(int,short)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateShort(
        final int index, final short value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateShort(index, value);
    }

    /**
     * See ResultSet#updateShort(String,short)
     * @see java.sql.ResultSet#updateShort(java.lang.String,short)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateShort(final String columnName, final short value)
        throws  SQLException
    {
        updateShort(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateShort(String,short)
     * @see java.sql.ResultSet#updateShort(java.lang.String,short)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateShort(
        final String columnName, final short value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateShort(columnName, value);
    }

    /**
     * See ResultSet#updateString(int,String)
     * @see java.sql.ResultSet#updateString(int,java.lang.String)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateString(final int index, final String value)
        throws  SQLException
    {
        updateString(index, value, getResultSet());
    }

    /**
     * See ResultSet#updateString(int,String)
     * @see java.sql.ResultSet#updateString(int,java.lang.String)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateString(
        final int index, final String value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateString(index, value);
    }

    /**
     * See ResultSet#updateString(String,String)
     * @see java.sql.ResultSet#updateString(java.lang.String,java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateString(final String columnName, final String value)
        throws  SQLException
    {
        updateString(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateString(String,String)
     * @see java.sql.ResultSet#updateString(java.lang.String,java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateString(
        final String columnName, final String value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateString(columnName, value);
    }

    /**
     * See ResultSet#updateTime(int,Time)
     * @see java.sql.ResultSet#updateTime(int,java.sql.Time)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateTime(final int index, final Time value)
        throws  SQLException
    {
        updateTime(index, value, getResultSet());
    }

    /**
     * See ResultSet#updateTime(int,Time)
     * @see java.sql.ResultSet#updateTime(int,java.sql.Time)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateTime(
        final int index, final Time value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateTime(index, value);
    }

    /**
     * See ResultSet#updateTime(String,Time)
     * @see java.sql.ResultSet#updateTime(java.lang.String,java.sql.Time)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateTime(final String columnName, final Time value)
        throws  SQLException
    {
        updateTime(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateTime(String,Time)
     * @see java.sql.ResultSet#updateTime(java.lang.String,java.sql.Time)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateTime(
        final String columnName, final Time value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateTime(columnName, value);
    }

    /**
     * See ResultSet#updateTimestamp(int,Timestamp)
     * @see java.sql.ResultSet#updateTimestamp(int,java.sql.Timestamp)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateTimestamp(final int index, final Timestamp value)
        throws  SQLException
    {
        updateTimestamp(index, value, getResultSet());
    }

    /**
     * See ResultSet#updateTimestamp(int,Timestamp)
     * @see java.sql.ResultSet#updateTimestamp(int,java.sql.Timestamp)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateTimestamp(
        final int index, final Timestamp value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateTimestamp(index, value);
    }

    /**
     * See ResultSet#updateTimestamp(String,Timestamp)
     * @see java.sql.ResultSet#updateTimestamp(java.lang.String,java.sql.Timestamp)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateTimestamp(final String columnName, final Timestamp value)
        throws  SQLException
    {
        updateTimestamp(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateTimestamp(String,Timestamp)
     * @see java.sql.ResultSet#updateTimestamp(java.lang.String,java.sql.Timestamp)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateTimestamp(
        final String columnName, final Timestamp value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateTimestamp(columnName, value);
    }

    // New methods from JDK 1.4 //

    /**
     * See ResultSet#getURL(int)
     * @see java.sql.ResultSet#getURL(int)
     * @param columnIndex (Taken from Sun's Javadoc) the index of the column
     * 1 is the first, 2 is the second,...
     * @return (Taken from Sun's Javadoc) the column value as a java.net.URL
     * object; if the value is SQL NULL, the value returned is null in the
     * Java programming language.
     * @exception SQLException if an error occurs.
     */
    public URL getURL(final int columnIndex)
        throws  SQLException
    {
        return getURL(columnIndex, getResultSet());
    }

    /**
     * See ResultSet#getURL(int)
     * @see java.sql.ResultSet#getURL(int)
     * @param columnIndex (Taken from Sun's Javadoc) the index of the column
     * 1 is the first, 2 is the second,...
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value as a java.net.URL
     * object; if the value is SQL NULL, the value returned is null in the
     * Java programming language.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected URL getURL(final int columnIndex, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getURL(columnIndex);
    }

    /**
     * See ResultSet#getURL(String)
     * @see java.sql.ResultSet#getURL(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of the column.
     * @return (Taken from Sun's Javadoc) the column value as a java.net.URL
     * object; if the value is SQL NULL, the value returned is null in the
     * Java programming language.
     * @exception SQLException if an error occurs.
     */
    public URL getURL(final String columnName)
        throws  SQLException
    {
        return getURL(columnName, getResultSet());
    }

    /**
     * See ResultSet#getURL(String)
     * @see java.sql.ResultSet#getURL(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of the column.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value as a java.net.URL
     * object; if the value is SQL NULL, the value returned is null in the
     * Java programming language.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected URL getURL(final String columnName, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getURL(columnName);
    }

    /**
     * See ResultSet#updateRef(int,Ref)
     * @see java.sql.ResultSet#updateRef(int,java.sql.Ref)
     * @param columnIndex (Taken from Sun's Javadoc) the index of the column
     * 1 is the first, 2 is the second,...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateRef(final int columnIndex, final Ref value)
        throws  SQLException
    {
        updateRef(columnIndex, value, getResultSet());
    }

    /**
     * See ResultSet#updateRef(int,Ref)
     * @see java.sql.ResultSet#updateRef(int,java.sql.Ref)
     * @param columnIndex (Taken from Sun's Javadoc) the index of the column
     * 1 is the first, 2 is the second,...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateRef(
        final int columnIndex, final Ref value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateRef(columnIndex, value);
    }

    /**
     * See ResultSet#updateRef(String,Ref)
     * @see java.sql.ResultSet#updateRef(java.lang.String,java.sql.Ref)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateRef(final String columnName, final Ref value)
        throws  SQLException
    {
        updateRef(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateRef(String,Ref)
     * @see java.sql.ResultSet#updateRef(java.lang.String,java.sql.Ref)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateRef(
        final String columnName, final Ref value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateRef(columnName, value);
    }

    /**
     * See ResultSet#updateBlob(int,Blob)
     * @see java.sql.ResultSet#updateBlob(int,java.sql.Blob)
     * @param columnIndex (Taken from Sun's Javadoc) the index of the column
     * 1 is the first, 2 is the second,...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateBlob(final int columnIndex, final Blob value)
        throws  SQLException
    {
        updateBlob(columnIndex, value, getResultSet());
    }

    /**
     * See ResultSet#updateBlob(int,Blob)
     * @see java.sql.ResultSet#updateBlob(int,java.sql.Blob)
     * @param columnIndex (Taken from Sun's Javadoc) the index of the column
     * 1 is the first, 2 is the second,...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateBlob(
        final int columnIndex, final Blob value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateBlob(columnIndex, value);
    }

    /**
     * See ResultSet#updateBlob(String,Blob)
     * @see java.sql.ResultSet#updateBlob(java.lang.String,java.sql.Blob)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateBlob(final String columnName, final Blob value)
        throws  SQLException
    {
        updateBlob(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateBlob(String,Blob)
     * @see java.sql.ResultSet#updateBlob(java.lang.String,java.sql.Blob)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateBlob(
        final String columnName, final Blob value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateBlob(columnName, value);
    }

    /**
     * See ResultSet#updateClob(int,Clob)
     * @see java.sql.ResultSet#updateClob(int,java.sql.Clob)
     * @param columnIndex (Taken from Sun's Javadoc) the index of the column
     * 1 is the first, 2 is the second,...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateClob(final int columnIndex, final Clob value)
        throws  SQLException
    {
        updateClob(columnIndex, value, getResultSet());
    }

    /**
     * See ResultSet#updateClob(int,Clob)
     * @see java.sql.ResultSet#updateClob(int,java.sql.Clob)
     * @param columnIndex (Taken from Sun's Javadoc) the index of the column
     * 1 is the first, 2 is the second,...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateClob(
        final int columnIndex, final Clob value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateClob(columnIndex, value);
    }

    /**
     * See ResultSet#updateClob(String,Clob)
     * @see java.sql.ResultSet#updateClob(java.lang.String,java.sql.Clob)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateClob(final String columnName, final Clob value)
        throws  SQLException
    {
        updateClob(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateClob(String,Clob)
     * @see java.sql.ResultSet#updateClob(java.lang.String,java.sql.Clob)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateClob(
        final String columnName, final Clob value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateClob(columnName, value);
    }

    /**
     * See ResultSet#updateArray(int,Array)
     * @see java.sql.ResultSet#updateArray(int,java.sql.Array)
     * @param columnIndex (Taken from Sun's Javadoc) the index of the column
     * 1 is the first, 2 is the second,...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateArray(final int columnIndex, final Array value)
        throws  SQLException
    {
        updateArray(columnIndex, value, getResultSet());
    }

    /**
     * See ResultSet#updateArray(int,Array)
     * @see java.sql.ResultSet#updateArray(int,java.sql.Array)
     * @param columnIndex (Taken from Sun's Javadoc) the index of the column
     * 1 is the first, 2 is the second,...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateArray(
        final int columnIndex, final Array value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateArray(columnIndex, value);
    }

    /**
     * See ResultSet#updateArray(String,Array)
     * @see java.sql.ResultSet#updateArray(java.lang.String,java.sql.Array)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateArray(final String columnName, final Array value)
        throws  SQLException
    {
        updateArray(columnName, value, getResultSet());
    }

    /**
     * See ResultSet#updateArray(String,Array)
     * @see java.sql.ResultSet#updateArray(java.lang.String,java.sql.Array)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected void updateArray(
        final String columnName, final Array value, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        resultSet.updateArray(columnName, value);
    }

    // Helper ResultSet methods //

    /**
     * Retrieves a byte array value using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public byte[] getBytes(final Field field)
        throws  SQLException
    {
        return getBytes(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves a byte array value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected byte[] getBytes(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getBytes(query.getFieldIndex(field));
    }

    /**
     * Retrieves a boolean value using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is <code>false</code>.
     * @exception SQLException if an error occurs.
     */
    public boolean getBoolean(final Field field)
        throws  SQLException
    {
        return getBoolean(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves a boolean value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is <code>false</code>.
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected boolean getBoolean(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getBoolean(query.getFieldIndex(field));
    }

    /**
     * Retrieves a boolean value using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     */
    public long getLong(final Field field)
        throws  SQLException
    {
        return getLong(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves a boolean value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected long getLong(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getLong(query.getFieldIndex(field));
    }

    /**
     * Retrieves an object value using the field reference.
     * @param field the field.
     * @exception SQLException if an error occurs.
     */
    public Object getObject(final Field field)
        throws  SQLException
    {
        return getObject(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves an object value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected Object getObject(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getObject(query.getFieldIndex(field));
    }

    /**
     * Retrieves an object value using the field reference.
     * @param field the field.
     * @param map (Taken from Sun's Javadoc) a java.util.Map object that
     * contains the mapping from SQL type names to classes in the Java
     * programming language.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Object getObject(final Field field, final Map map)
        throws  SQLException
    {
        return getObject(field, map, getQuery(), getResultSet());
    }

    /**
     * Retrieves an object value using the field reference.
     * @param field the field.
     * @param map (Taken from Sun's Javadoc) a java.util.Map object that
     * contains the mapping from SQL type names to classes in the Java
     * programming language.
     * @param query the query.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected Object getObject(
        final Field field,
        final Map map,
        @NotNull final Query query,
        @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getObject(query.getFieldIndex(field), map);
    }

    /**
     * Retrieves a Ref value using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Ref getRef(final Field field)
        throws  SQLException
    {
        return getRef(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves a Ref value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected Ref getRef(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getRef(query.getFieldIndex(field));
    }

    /**
     * Retrieves a Time value using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Time getTime(final Field field)
        throws  SQLException
    {
        return getTime(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves a Time value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected Time getTime(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getTime(query.getFieldIndex(field));
    }

    /**
     * Retrieves a Time value using the field reference.
     * @param field the field.
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Time getTime(final Field field, final Calendar calendar)
        throws  SQLException
    {
        return getTime(field, calendar, getQuery(), getResultSet());
    }

    /**
     * Retrieves a Time value using the field reference.
     * @param field the field.
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @param query the query.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected Time getTime(
        final Field field,
        final Calendar calendar,
        @NotNull final Query query,
        @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getTime(query.getFieldIndex(field), calendar);
    }

    /**
     * Retrieves a Date value using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Date getDate(final Field field)
        throws  SQLException
    {
        return getDate(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves a Date value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     */
    protected Date getDate(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getDate(query.getFieldIndex(field));
    }

    /**
     * Retrieves a Date value using the field reference.
     * @param field the field.
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Date getDate(final Field field, final Calendar calendar)
        throws  SQLException
    {
        return getDate(field, calendar, getQuery(), getResultSet());
    }

    /**
     * Retrieves a Date value using the field reference.
     * @param field the field.
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @param query the query.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected Date getDate(
        final Field field,
        final Calendar calendar,
        @NotNull final Query query,
        @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getDate(query.getFieldIndex(field), calendar);
    }

    /**
     * Retrieves a byte value using the field reference.
     * @param field the field.
     * @return see getByte(int).
     * @exception SQLException if an error occurs.
     */
    public byte getByte(final Field field)
        throws  SQLException
    {
        return getByte(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves a byte value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return see getByte(int).
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected byte getByte(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getByte(query.getFieldIndex(field));
    }

    /**
     * Retrieves a short value using the field reference.
     * @param field the field.
     * @return see getShort(int).
     * @exception SQLException if an error occurs.
     */
    public short getShort(final Field field)
        throws  SQLException
    {
        return getShort(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves a short value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return see getShort(int).
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected short getShort(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getShort(query.getFieldIndex(field));
    }

    /**
     * Retrieves an integer value using the field reference.
     * @param field the field.
     * @return see getInt(int)
     * @exception SQLException if an error occurs.
     */
    public int getInt(final Field field)
        throws  SQLException
    {
        return getInt(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves an integer value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return see getInt(int)
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected int getInt(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getInt(query.getFieldIndex(field));
    }

    /**
     * Retrieves a float value using the field reference.
     * @param field the field.
     * @return see getFloat(int)
     * @exception SQLException if an error occurs.
     */
    public float getFloat(final Field field)
        throws  SQLException
    {
        return getFloat(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves a float value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return see getFloat(int)
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected float getFloat(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getFloat(query.getFieldIndex(field));
    }

    /**
     * Retrieves a double value using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     */
    public double getDouble(final Field field)
        throws  SQLException
    {
        return getDouble(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves a double value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected double getDouble(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getDouble(query.getFieldIndex(field));
    }

    /**
     * Retrieves a text value using the field reference.
     * @param field the field.
     * @return see getString(int)
     * @exception SQLException if an error occurs.
     */
    public String getString(final ClobField field)
        throws  SQLException
    {
        return
            getString(
                field, getQuery(), getResultSet(), QueryUtils.getInstance());
    }

    /**
     * Retrieves a text value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @param queryUtils the <code>QueryUtils</code> instance.
     * @return see getString(int)
     * @exception SQLException if an error occurs.
     * @precondition field != null
     * @precondition query != null
     * @precondition resultSet != null
     * @precondition queryUtils != null
     */
    protected String getString(
        final ClobField field,
        @NotNull final Query query,
        @NotNull final ResultSet resultSet,
        @NotNull final QueryUtils queryUtils)
      throws  SQLException
    {
        return
            queryUtils.clobToString(
                resultSet.getClob(query.getFieldIndex(field)));
    }

    /**
     * Retrieves a text value using the field reference.
     * @param field the field.
     * @return see getString(int)
     * @exception SQLException if an error occurs.
     */
    public String getString(final Field field)
        throws  SQLException
    {
        return getString(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves a text value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return see getString(int)
     * @exception SQLException if an error occurs.
     * @precondition resultSet != null
     * @precondition resultSet != null
     */
    protected String getString(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getString(query.getFieldIndex(field));
    }

    /**
     * Retrieves an array value using the field reference.
     * @param field the field.
     * @return see getArray(int)
     * @exception SQLException if an error occurs.
     */
    public Array getArray(final Field field)
        throws  SQLException
    {
        return getArray(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves an array value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return see getArray(int)
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected Array getArray(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getArray(query.getFieldIndex(field));
    }

    /**
     * Retrieves an ASCII stream using the field reference.
     * @param field the field.
     * @return see getAsciiStream(int)
     * @exception SQLException if an error occurs.
     */
    public InputStream getAsciiStream(final Field field)
        throws  SQLException
    {
        return getAsciiStream(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves an ASCII stream using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return see getAsciiStream(int)
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected InputStream getAsciiStream(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getAsciiStream(query.getFieldIndex(field));
    }

    /**
     * Retrieves a BigDecimal value using the field reference.
     * @see getBigDecimal(int, int)
     * @param field the field.
     * @param scale (Taken from Sun's Javadoc) the number of digits to the
     * right of the decimal point.
     * @return see getBigDecimal(int, int)
     * @exception SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     */
    public BigDecimal getBigDecimal(final Field field, final int scale)
        throws  SQLException
    {
        return getBigDecimal(field, scale, getQuery(), getResultSet());
    }

    /**
     * Retrieves a BigDecimal value using the field reference.
     * @see getBigDecimal(int, int)
     * @param field the field.
     * @param scale (Taken from Sun's Javadoc) the number of digits to the
     * right of the decimal point.
     * @param query the query.
     * @param resultSet the result set.
     * @exception SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected BigDecimal getBigDecimal(
        final Field field,
        final int scale,
        @NotNull final Query query,
        @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getBigDecimal(query.getFieldIndex(field), scale);
    }

    /**
     * Retrieves a BigDecimal value using the field reference.
     * @param field the field.
     * @return see getBigDecimal(field)
     * @exception SQLException if an error occurs.
     */
    public BigDecimal getBigDecimal(final Field field)
        throws  SQLException
    {
        return getBigDecimal(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves a BigDecimal value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return see getBigDecimal(field)
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected BigDecimal getBigDecimal(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getBigDecimal(query.getFieldIndex(field));
    }

    /**
     * Retrieves a binary stream using the field reference.
     * @param field the field.
     * @return see getBinaryStream(int)
     * @exception SQLException if an error occurs.
     */
    public InputStream getBinaryStream(final Field field)
        throws  SQLException
    {
        return getBinaryStream(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves a binary stream using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return see getBinaryStream(int)
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected InputStream getBinaryStream(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getBinaryStream(query.getFieldIndex(field));
    }

    /**
     * Retrieves a blob using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Blob getBlob(final Field field)
        throws  SQLException
    {
        return getBlob(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves a blob using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected Blob getBlob(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getBlob(query.getFieldIndex(field));
    }

    /**
     * Retrieves a clob value using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Clob getClob(final Field field)
        throws  SQLException
    {
        return getClob(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves a clob value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected Clob getClob(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getClob(query.getFieldIndex(field));
    }

    /**
     * Retrieves a timestamp value using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Timestamp getTimestamp(final Field field)
        throws  SQLException
    {
        return getTimestamp(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves a timestamp value using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected Timestamp getTimestamp(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getTimestamp(query.getFieldIndex(field));
    }

    /**
     * Retrieves a timestamp value using the field reference.
     * @param field the field.
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Timestamp getTimestamp(final Field field, final Calendar calendar)
        throws  SQLException
    {
        return getTimestamp(field, calendar, getQuery(), getResultSet());
    }

    /**
     * Retrieves a timestamp value using the field reference.
     * @param field the field.
     * @param calendar (Taken from Sun's Javadoc) the java.util.Calendar
     * object to use in constructing the time.
     * @param query the query.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected Timestamp getTimestamp(
        final Field field,
        final Calendar calendar,
        @NotNull final Query query,
        @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getTimestamp(query.getFieldIndex(field), calendar);
    }

    /**
     * Retrieves an Unicode stream using the field reference.
     * @param field the field.
     * @return see getUnicodeStream(int)
     * @exception SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     */
    public InputStream getUnicodeStream(final Field field)
        throws  SQLException
    {
        return getUnicodeStream(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves an Unicode stream using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return see getUnicodeStream(int)
     * @exception SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected InputStream getUnicodeStream(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getUnicodeStream(query.getFieldIndex(field));
    }

    /**
     * Retrieves a character stream using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Reader getCharacterStream(final Field field)
        throws  SQLException
    {
        return getCharacterStream(field, getQuery(), getResultSet());
    }

    /**
     * Retrieves a character stream using the field reference.
     * @param field the field.
     * @param query the query.
     * @param resultSet the result set.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     * @precondition query != null
     * @precondition resultSet != null
     */
    protected Reader getCharacterStream(
        final Field field, @NotNull final Query query, @NotNull final ResultSet resultSet)
      throws  SQLException
    {
        return resultSet.getCharacterStream(query.getFieldIndex(field));
    }

    // New methods from JDK 1.4 //

    /**
     * Retrieves an URL using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value as a java.net.URL
     * object; if the value is SQL NULL, the value returned is null in the
     * Java programming language.
     * @exception SQLException if an error occurs.
     */
    @Nullable
    public URL getURL(final Field field)
        throws  SQLException
    {
        @Nullable URL result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getURL(t_iFieldIndex);
            }
        }

        return result;
    }

    /**
     * Retrieves the column index associated to given field.
     * @param field the field.
     * @return the column index of the given field.
     * @exception SQLException if an error occurs.
     */
    public int findColumn(final Field field)
        throws  SQLException
    {
        int result = -1;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            result = t_Query.getFieldIndex(field);
        }
        
        return result;
    }

    /**
     * Updates an ASCII stream column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param length (Taken from Sun's Javadoc) the length of the stream.
     * @exception SQLException if an error occurs.
     */
    public void updateAsciiStream(
        final Field        field,
        final InputStream  value,
        final int          length)
      throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateAsciiStream(
                    t_iFieldIndex, value, length);
            }
        }
    }

    /**
     * See ResultSet#updateBigDecimal(String,BigDecimal)
     * @see java.sql.ResultSet#updateBigDecimal(java.lang.String,java.math.BigDecimal)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value a <code>BigDecimal</code> value
     * @exception SQLException if an error occurs.
     */
    public void updateBigDecimal(final Field field, final BigDecimal value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateBigDecimal(t_iFieldIndex, value);
            }
        }
    }

    /**
     * Updates a binary stream column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param length (Taken from Sun's Javadoc) the length of the stream.
     * @exception SQLException if an error occurs.
     */
    public void updateBinaryStream(
        final Field        field,
        final InputStream  value,
        final int          length)
      throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateBinaryStream(
                    t_iFieldIndex, value, length);
            }
        }
    }

    /**
     * Updates a boolean column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateBoolean(final Field field, final boolean value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateBoolean(t_iFieldIndex, value);
            }
        }
    }

    /**
     * Updates a byte column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateByte(final Field field, final byte value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateByte(t_iFieldIndex, value);
            }
        }
    }

    /**
     * Updates a byte array column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateBytes(final Field field, final byte[] value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateBytes(t_iFieldIndex, value);
            }
        }
    }

    /**
     * Updates a character stream column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param length (Taken from Sun's Javadoc) the length of the stream.
     * @exception SQLException if an error occurs.
     */
    public void updateCharacterStream(
        final Field   field,
        final Reader  value,
        final int     length)
      throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();
        
            if  (t_ResultSet != null) 
            {
                t_ResultSet
                    .updateCharacterStream(t_iFieldIndex, value, length);
            }
        }
    }

    /**
     * Updates a date column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateDate(final Field field, final Date value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateDate(t_iFieldIndex, value);
            }
        }
    }

    /**
     * Updates a double column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateDouble(final Field field, final double value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateDouble(t_iFieldIndex, value);
            }
        }
    }

    /**
     * Updates a float column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs
     */
    public void updateFloat(final Field field, final float value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateFloat(t_iFieldIndex, value);
            }
        }
    }

    /**
     * Updates an integer column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateInt(final Field field, final int value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateInt(t_iFieldIndex, value);
            }
        }
    }

    /**
     * Updates a long column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs
     */
    public void updateLong(final Field field, final long value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateLong(t_iFieldIndex, value);
            }
        }
    }

    /**
     * Sets a column to null using the field reference.
     * @param field the field.
     * @exception SQLException if an error occurs.
     */
    public void updateNull(final Field field)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateNull(t_iFieldIndex);
            }
        }
    }

    /**
     * Updates an object column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @param scale (Taken from Sun's Javadoc) for java.sql.Types.DECIMA
     * or java.sql.Types.NUMERIC types, this is the number of digits after
     * the decimal point. For all other types this value will be ignored.
     * @exception SQLException if an error occurs.
     */
    public void updateObject(
        final Field field, final Object value, final int scale)
      throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateObject(t_iFieldIndex, value, scale);
            }
        }
    }

    /**
     * Updates an object column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateObject(final Field field, final Object value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateObject(t_iFieldIndex, value);
            }
        }
    }

    /**
     * Updates a short column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateShort(final Field field, final short value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateShort(t_iFieldIndex, value);
            }
        }
    }

    /**
     * Updates a text column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateString(final Field field, final String value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateString(t_iFieldIndex, value);
            }
        }
    }

    /**
     * Updates a time column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateTime(final Field field, final Time value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateTime(t_iFieldIndex, value);
            }
        }
    }

    /**
     * Updates a timestamp column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateTimestamp(final Field field, final Timestamp value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateTimestamp(t_iFieldIndex, value);
            }
        }
    }

    // New methods from JDK 1.4 //

    /**
     * Updates a Ref column using the field reference.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateRef(final Field field, final Ref value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateRef(t_iFieldIndex, value);
            }
        }
    }

    /**
     * Updates a Blob column using the field bloberence.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateBlob(final Field field, final Blob value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateBlob(t_iFieldIndex, value);
            }
        }
    }

    /**
     * Updates a Clob column using the field cloberence.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateClob(final Field field, final Clob value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateClob(t_iFieldIndex, value);
            }
        }
    }

    /**
     * Updates a Array column using the field arrayerence.
     * @param field the field.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateArray(final Field field, final Array value)
        throws  SQLException
    {
        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                t_ResultSet.updateArray(t_iFieldIndex, value);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void updateNClob(final String name, final Reader reader)
        throws SQLException
    {
        updateNClob(name, reader, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateNClob(String, java.io.Reader)
     * @param name the parameter name.
     * @param reader the Clob reader.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateNClob(
        final String name, final Reader reader, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateNClob(name, reader);
    }

    /**
     * {@inheritDoc}
     */
    public void updateNClob(final String name, final Reader reader, final long length)
        throws SQLException
    {
        updateNClob(name, reader, length, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateNClob(String, java.io.Reader, long)
     * @param name the parameter name.
     * @param reader the Clob reader.
     * @param length the length.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateNClob(
        final String name, final Reader reader, final long length, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateNClob(name, reader, length);
    }

    /**
     * {@inheritDoc}
     */
    public void updateNClob(final int index, final Reader reader)
        throws SQLException
    {
        updateNClob(index, reader, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateNClob(int, java.io.Reader)
     * @param index the parameter index.
     * @param reader the Clob reader.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateNClob(
        final int index, final Reader reader, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateNClob(index, reader);
    }

    /**
     * {@inheritDoc}
     */
    public void updateNClob(final int index, final Reader reader, final long length)
        throws SQLException
    {
        updateNClob(index, reader, length, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateNClob(int, java.io.Reader, long)
     * @param index the parameter index.
     * @param reader the Clob reader.
     * @param length the length.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateNClob(
        final int index, final Reader reader, final long length, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateNClob(index, reader, length);
    }

    /**
     * {@inheritDoc}
     */
    public void updateClob(final String name, final Reader reader)
        throws SQLException
    {
        updateClob(name, reader, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateClob(String, java.io.Reader)
     * @param name the parameter name.
     * @param reader the Clob reader.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateClob(
        final String name, final Reader reader, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateClob(name, reader);
    }

    /**
     * {@inheritDoc}
     */
    public void updateClob(final String name, final Reader reader, final long length)
        throws SQLException
    {
        updateClob(name, reader, length, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateClob(String, java.io.Reader, long)
     * @param name the parameter name.
     * @param reader the Clob reader.
     * @param length the length.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateClob(
        final String name, final Reader reader, final long length, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateClob(name, reader, length);
    }

    /**
     * {@inheritDoc}
     */
    public void updateClob(final int index, final Reader reader)
        throws SQLException
    {
        updateClob(index, reader, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateClob(int, java.io.Reader)
     * @param index the parameter index.
     * @param reader the Clob reader.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateClob(
        final int index, final Reader reader, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateClob(index, reader);
    }

    /**
     * {@inheritDoc}
     */
    public void updateClob(final int index, final Reader reader, final long length)
        throws SQLException
    {
        updateClob(index, reader, length, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateClob(int, java.io.Reader, long)
     * @param index the parameter index.
     * @param reader the Clob reader.
     * @param length the length.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateClob(
        final int index, final Reader reader, final long length, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateClob(index, reader, length);
    }

    /**
     * {@inheritDoc}
     */
    public void updateBlob(final String name, final InputStream stream)
        throws SQLException
    {
        updateBlob(name, stream, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateBlob(String, java.io.InputStream)
     * @param name the parameter name.
     * @param stream the Blob stream.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateBlob(
        final String name, final InputStream stream, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateBlob(name, stream);
    }

    /**
     * {@inheritDoc}
     */
    public void updateBlob(final String name, final InputStream stream, final long length)
        throws SQLException
    {
        updateBlob(name, stream, length, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateBlob(String, java.io.InputStream, long)
     * @param name the parameter name.
     * @param stream the Blob stream.
     * @param length the length.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateBlob(
        final String name, final InputStream stream, final long length, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateBlob(name, stream, length);
    }

    /**
     * {@inheritDoc}
     */
    public void updateBlob(final int index, final InputStream stream)
        throws SQLException
    {
        updateBlob(index, stream, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateBlob(int, java.io.InputStream)
     * @param index the parameter index.
     * @param stream the Blob stream.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateBlob(
        final int index, final InputStream stream, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateBlob(index, stream);
    }

    /**
     * {@inheritDoc}
     */
    public void updateBlob(final int index, final InputStream stream, final long length)
        throws SQLException
    {
        updateBlob(index, stream, length, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateBlob(int, java.io.InputStream, long)
     * @param index the parameter index.
     * @param stream the Blob stream.
     * @param length the length.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateBlob(
        final int index, final InputStream stream, final long length, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateBlob(index, stream, length);
    }

    /**
     * {@inheritDoc}
     */
    public void updateCharacterStream(final String name, final Reader reader)
        throws SQLException
    {
        updateCharacterStream(name, reader, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateCharacterStream(String, java.io.Reader)
     * @param name the parameter name.
     * @param reader the CharacterStream reader.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateCharacterStream(
        final String name, final Reader reader, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateCharacterStream(name, reader);
    }

    /**
     * {@inheritDoc}
     */
    public void updateCharacterStream(final String name, final Reader reader, final long length)
        throws SQLException
    {
        updateCharacterStream(name, reader, length, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateCharacterStream(String, java.io.Reader, long)
     * @param name the parameter name.
     * @param reader the CharacterStream reader.
     * @param length the length.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateCharacterStream(
        final String name, final Reader reader, final long length, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateCharacterStream(name, reader, length);
    }

    /**
     * {@inheritDoc}
     */
    public void updateCharacterStream(final int index, final Reader reader)
        throws SQLException
    {
        updateCharacterStream(index, reader, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateCharacterStream(int, java.io.Reader)
     * @param index the parameter index.
     * @param reader the CharacterStream reader.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateCharacterStream(
        final int index, final Reader reader, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateCharacterStream(index, reader);
    }

    /**
     * {@inheritDoc}
     */
    public void updateCharacterStream(final int index, final Reader reader, final long length)
        throws SQLException
    {
        updateCharacterStream(index, reader, length, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateCharacterStream(int, java.io.Reader, long)
     * @param index the parameter index.
     * @param reader the CharacterStream reader.
     * @param length the length.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateCharacterStream(
        final int index, final Reader reader, final long length, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateCharacterStream(index, reader, length);
    }

    /**
     * {@inheritDoc}
     */
    public void updateNCharacterStream(final String name, final Reader reader)
        throws SQLException
    {
        updateNCharacterStream(name, reader, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateNCharacterStream(String, java.io.Reader)
     * @param name the parameter name.
     * @param reader the NCharacterStream reader.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateNCharacterStream(
        final String name, final Reader reader, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateNCharacterStream(name, reader);
    }

    /**
     * {@inheritDoc}
     */
    public void updateNCharacterStream(final String name, final Reader reader, final long length)
        throws SQLException
    {
        updateNCharacterStream(name, reader, length, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateNCharacterStream(String, java.io.Reader, long)
     * @param name the parameter name.
     * @param reader the NCharacterStream reader.
     * @param length the length.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateNCharacterStream(
        final String name, final Reader reader, final long length, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateNCharacterStream(name, reader, length);
    }

    /**
     * {@inheritDoc}
     */
    public void updateNCharacterStream(final int index, final Reader reader)
        throws SQLException
    {
        updateNCharacterStream(index, reader, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateNCharacterStream(int, java.io.Reader)
     * @param index the parameter index.
     * @param reader the NCharacterStream reader.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateNCharacterStream(
        final int index, final Reader reader, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateNCharacterStream(index, reader);
    }

    /**
     * {@inheritDoc}
     */
    public void updateNCharacterStream(final int index, final Reader reader, final long length)
        throws SQLException
    {
        updateNCharacterStream(index, reader, length, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateNCharacterStream(int, java.io.Reader, long)
     * @param index the parameter index.
     * @param reader the NCharacterStream reader.
     * @param length the length.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateNCharacterStream(
        final int index, final Reader reader, final long length, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateNCharacterStream(index, reader, length);
    }

    /**
     * {@inheritDoc}
     */
    public void updateBinaryStream(final String name, final InputStream stream)
        throws SQLException
    {
        updateBinaryStream(name, stream, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateBinaryStream(String, java.io.InputStream)
     * @param name the parameter name.
     * @param stream the BinaryStream stream.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateBinaryStream(
        final String name, final InputStream stream, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateBinaryStream(name, stream);
    }

    /**
     * {@inheritDoc}
     */
    public void updateBinaryStream(final String name, final InputStream stream, final long length)
        throws SQLException
    {
        updateBinaryStream(name, stream, length, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateBinaryStream(String, java.io.InputStream, long)
     * @param name the parameter name.
     * @param stream the BinaryStream stream.
     * @param length the length.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateBinaryStream(
        final String name, final InputStream stream, final long length, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateBinaryStream(name, stream, length);
    }

    /**
     * {@inheritDoc}
     */
    public void updateBinaryStream(final int index, final InputStream stream)
        throws SQLException
    {
        updateBinaryStream(index, stream, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateBinaryStream(int, java.io.InputStream)
     * @param index the parameter index.
     * @param stream the BinaryStream stream.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateBinaryStream(
        final int index, final InputStream stream, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateBinaryStream(index, stream);
    }

    /**
     * {@inheritDoc}
     */
    public void updateBinaryStream(final int index, final InputStream stream, final long length)
        throws SQLException
    {
        updateBinaryStream(index, stream, length, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateBinaryStream(int, java.io.InputStream, long)
     * @param index the parameter index.
     * @param stream the BinaryStream stream.
     * @param length the length.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateBinaryStream(
        final int index, final InputStream stream, final long length, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateBinaryStream(index, stream, length);
    }

    /**
     * {@inheritDoc}
     */
    public void updateAsciiStream(final String name, final InputStream stream)
        throws SQLException
    {
        updateAsciiStream(name, stream, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateAsciiStream(String, java.io.InputStream)
     * @param name the parameter name.
     * @param stream the AsciiStream stream.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateAsciiStream(
        final String name, final InputStream stream, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateAsciiStream(name, stream);
    }

    /**
     * {@inheritDoc}
     */
    public void updateAsciiStream(final String name, final InputStream stream, final long length)
        throws SQLException
    {
        updateAsciiStream(name, stream, length, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateAsciiStream(String, java.io.InputStream, long)
     * @param name the parameter name.
     * @param stream the AsciiStream stream.
     * @param length the length.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateAsciiStream(
        final String name, final InputStream stream, final long length, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateAsciiStream(name, stream, length);
    }

    /**
     * {@inheritDoc}
     */
    public void updateAsciiStream(final int index, final InputStream stream)
        throws SQLException
    {
        updateAsciiStream(index, stream, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateAsciiStream(int, java.io.InputStream)
     * @param index the parameter index.
     * @param stream the AsciiStream stream.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateAsciiStream(
        final int index, final InputStream stream, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateAsciiStream(index, stream);
    }

    /**
     * {@inheritDoc}
     */
    public void updateAsciiStream(final int index, final InputStream stream, final long length)
        throws SQLException
    {
        updateAsciiStream(index, stream, length, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateAsciiStream(int, java.io.InputStream, long)
     * @param index the parameter index.
     * @param stream the AsciiStream stream.
     * @param length the length.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateAsciiStream(
        final int index, final InputStream stream, final long length, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateAsciiStream(index, stream, length);
    }

    /**
     * {@inheritDoc}
     */
    public Reader getNCharacterStream(final String name)
        throws SQLException
    {
        return getNCharacterStream(name, getResultSet());
    }

    /**
     * @see java.sql.ResultSet#getNCharacterStream(String).
     * @param name the parameter name.
     * @param resultSet the result set.
     * @return the value.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected Reader getNCharacterStream(final String name, @NotNull final ResultSet resultSet)
        throws SQLException
    {
        return resultSet.getNCharacterStream(name);
    }

    /**
     * {@inheritDoc}
     */
    public Reader getNCharacterStream(final int index)
        throws SQLException
    {
        return getNCharacterStream(index, getResultSet());
    }

    /**
     * @see java.sql.ResultSet#getNCharacterStream(int).
     * @param index the parameter index.
     * @param resultSet the result set.
     * @return the value.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected Reader getNCharacterStream(final int index, @NotNull final ResultSet resultSet)
        throws SQLException
    {
        return resultSet.getNCharacterStream(index);
    }

    /**
     * {@inheritDoc}
     */
    public String getNString(final String name)
        throws SQLException
    {
        return getNString(name, getResultSet());
    }

    /**
     * @see java.sql.ResultSet#getNString(String).
     * @param name the parameter name.
     * @param resultSet the result set.
     * @return the value.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected String getNString(final String name, @NotNull final ResultSet resultSet)
        throws SQLException
    {
        return resultSet.getNString(name);
    }

    /**
     * {@inheritDoc}
     */
    public String getNString(final int index)
        throws SQLException
    {
        return getNString(index, getResultSet());
    }

    /**
     * @see java.sql.ResultSet#getNString(int).
     * @param index the parameter index.
     * @param resultSet the result set.
     * @return the value.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected String getNString(final int index, @NotNull final ResultSet resultSet)
        throws SQLException
    {
        return resultSet.getNString(index);
    }

    /**
     * {@inheritDoc}
     */
    public void updateSQLXML(final String name, final SQLXML sqlXml)
        throws SQLException
    {
        updateSQLXML(name, sqlXml, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateSQLXML(String, java.sql.SQLXML)
     * @param name the parameter name.
     * @param sqlXml the SQLXML value.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateSQLXML(
        final String name, final SQLXML sqlXml , @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateSQLXML(name, sqlXml);
    }

    /**
     * {@inheritDoc}
     */
    public void updateSQLXML(final int index, final SQLXML sqlXml)
        throws SQLException
    {
        updateSQLXML(index, sqlXml, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateSQLXML(int, java.sql.SQLXML)
     * @param index the parameter index.
     * @param sqlXml the SQLXML value.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateSQLXML(
        final int index, final SQLXML sqlXml , @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateSQLXML(index, sqlXml);
    }

    /**
     * {@inheritDoc}
     */
    public SQLXML getSQLXML(final String name)
        throws SQLException
    {
        return getSQLXML(name, getResultSet());
    }

    /**
     * @see java.sql.ResultSet#getSQLXML(String).
     * @param name the parameter name.
     * @param resultSet the result set.
     * @return the value.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected SQLXML getSQLXML(final String name, @NotNull final ResultSet resultSet)
        throws SQLException
    {
        return resultSet.getSQLXML(name);
    }

    /**
     * {@inheritDoc}
     */
    public SQLXML getSQLXML(final int index)
        throws SQLException
    {
        return getSQLXML(index, getResultSet());
    }

    /**
     * @see java.sql.ResultSet#getSQLXML(int).
     * @param index the parameter index.
     * @param resultSet the result set.
     * @return the value.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected SQLXML getSQLXML(final int index, @NotNull final ResultSet resultSet)
        throws SQLException
    {
        return resultSet.getSQLXML(index);
    }

    /**
     * {@inheritDoc}
     */
    public NClob getNClob(final String name)
        throws SQLException
    {
        return getNClob(name, getResultSet());
    }

    /**
     * @see java.sql.ResultSet#getNClob(String).
     * @param name the parameter name.
     * @param resultSet the result set.
     * @return the value.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected NClob getNClob(final String name, @NotNull final ResultSet resultSet)
        throws SQLException
    {
        return resultSet.getNClob(name);
    }

    /**
     * {@inheritDoc}
     */
    public NClob getNClob(final int index)
        throws SQLException
    {
        return getNClob(index, getResultSet());
    }

    /**
     * @see java.sql.ResultSet#getNClob(int).
     * @param index the parameter index.
     * @param resultSet the result set.
     * @return the value.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected NClob getNClob(final int index, @NotNull final ResultSet resultSet)
        throws SQLException
    {
        return resultSet.getNClob(index);
    }

    /**
     * {@inheritDoc}
     */
    public void updateNClob(final String name, final NClob nclob)
        throws SQLException
    {
        updateNClob(name, nclob, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateNClob(String, java.sql.NClob)
     * @param name the parameter name.
     * @param nclob the NClob value.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateNClob(
        final String name, final NClob nclob , @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateNClob(name, nclob);
    }

    /**
     * {@inheritDoc}
     */
    public void updateNClob(final int index, final NClob nclob)
        throws SQLException
    {
        updateNClob(index, nclob, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateNClob(int, java.sql.NClob)
     * @param index the parameter index.
     * @param nclob the NClob value.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateNClob(
        final int index, final NClob nclob , @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateNClob(index, nclob);
    }

    /**
     * {@inheritDoc}
     */
    public void updateNString(final String name, final String nstring)
        throws SQLException
    {
        updateNString(name, nstring, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateNString(String, String)
     * @param name the parameter name.
     * @param nstring the String value.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateNString(
        final String name, final String nstring, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateNString(name, nstring);
    }

    /**
     * {@inheritDoc}
     */
    public void updateNString(final int index, final String nstring)
        throws SQLException
    {
        updateNString(index, nstring, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateNString(int, String)
     * @param index the parameter index.
     * @param nstring the String value.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateNString(
        final int index, final String nstring, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateNString(index, nstring);
    }

    /**
     * {@inheritDoc}
     */
    public RowId getRowId(final String name)
        throws SQLException
    {
        return getRowId(name, getResultSet());
    }

    /**
     * @see java.sql.ResultSet#getRowId(String).
     * @param name the parameter name.
     * @param resultSet the result set.
     * @return the value.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected RowId getRowId(final String name, @NotNull final ResultSet resultSet)
        throws SQLException
    {
        return resultSet.getRowId(name);
    }

    /**
     * {@inheritDoc}
     */
    public RowId getRowId(final int index)
        throws SQLException
    {
        return getRowId(index, getResultSet());
    }

    /**
     * @see java.sql.ResultSet#getRowId(int).
     * @param index the parameter index.
     * @param resultSet the result set.
     * @return the value.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected RowId getRowId(final int index, @NotNull final ResultSet resultSet)
        throws SQLException
    {
        return resultSet.getRowId(index);
    }

    /**
     * {@inheritDoc}
     */
    public void updateRowId(final String name, final RowId rowId)
        throws SQLException
    {
        updateRowId(name, rowId, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateRowId(String, java.sql.RowId)
     * @param name the parameter name.
     * @param rowId the row id.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateRowId(
        final String name, final RowId rowId, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateRowId(name, rowId);
    }

    /**
     * {@inheritDoc}
     */
    public void updateRowId(final int index, final RowId rowId)
        throws SQLException
    {
        updateRowId(index, rowId, getResultSet());
    }

    /**
     * @see java.sql.PreparedStatement#updateRowId(int, java.sql.RowId)
     * @param index the parameter index.
     * @param rowId the row id.
     * @param resultSet the actual result set.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected void updateRowId(
        final int index, final RowId rowId, @NotNull final ResultSet resultSet)
      throws SQLException
    {
        resultSet.updateRowId(index, rowId);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isClosed()
        throws SQLException
    {
        return isClosed(getResultSet());
    }

    /**
     * @see java.sql.ResultSet#isClosed()
     * @param resultSet the result set.
     * @return such information.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected boolean isClosed(@NotNull final ResultSet resultSet)
        throws SQLException
    {
        return resultSet.isClosed();
    }

    /**
     * {@inheritDoc}
     */
    public int getHoldability()
        throws SQLException
    {
        return getHoldability(getResultSet());
    }

    /**
     * @see java.sql.ResultSet#getHoldability()
     * @param resultSet the result set.
     * @return such information.
     * @throws SQLException if the operation fails.
     * @precondition resultSet != null
     */
    protected int getHoldability(@NotNull final ResultSet resultSet)
        throws SQLException
    {
        return resultSet.getHoldability();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isWrapperFor(final Class wrapperClass)
    {
        return isWrapperFor(wrapperClass, getResultSet());
    }

    /**
     * Checks whether the wrapped result set is compatible with given class.
     * @param wrapperClass the wrapper class.
     * @param wrappedResultSet the wrapped result set.
     * @return <code>true</code> if the wrapped statement is compatible with given class.
     */
    protected boolean isWrapperFor(final Class wrapperClass, @Nullable final Object wrappedResultSet)
    {
        return
            (   (wrappedResultSet != null)
             && (wrappedResultSet.getClass().isAssignableFrom(wrapperClass)));
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    public Object unwrap(final Class wrapperClass)
    {
        return unwrap(wrapperClass, getResultSet());
    }

    /**
     * Unwraps the wrapped result set if it's compatible with given class.
     * @param wrapperClass the wrapper class.
     * @param wrappedResultSet the wrapped result set.
     * @return the wrapped statement if it's compatible.
     */
    @Nullable
    protected Object unwrap(final Class wrapperClass, final Object wrappedResultSet)
    {
        @Nullable Object result = null;

        if  (isWrapperFor(wrapperClass, wrappedResultSet))
        {
            result = wrappedResultSet;
        }

        return result;
    }
}
