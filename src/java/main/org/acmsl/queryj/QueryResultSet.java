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
 * Description: Standard SQL query.
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
import org.acmsl.queryj.Query;

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
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * Represents standard SQL queries.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
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
     * Builds a query result set with given references.
     * @param query the query.
     * @param resultSet the wrapped result set.
     */
    public QueryResultSet(Query query, ResultSet resultSet)
    {
        unmodifiableSetQuery(query);
        unmodifiableSetResultSet(resultSet);
    }

    /**
     * Specifies the query reference.
     * @param query the query.
     */
    private void unmodifiableSetQuery(Query query)
    {
        m__Query = query;
    }

    /**
     * Specifies the query reference.
     * @param query the query.
     */
    protected void setQuery(Query query)
    {
        unmodifiableSetQuery(query);
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
    private void unmodifiableSetResultSet(ResultSet resultSet)
    {
        m__ResultSet = resultSet;
    }

    /**
     * Specifies the result set reference.
     * @param resultSet the result set.
     */
    protected void setResultSet(ResultSet resultSet)
    {
        unmodifiableSetResultSet(resultSet);
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
    public byte[] getBytes(int index)
        throws  SQLException
    {
        byte[] result = null;

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
    public byte[] getBytes(String columnName)
        throws  SQLException
    {
        byte[] result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null)
        {
            result = t_ResultSet.getBytes(columnName);
        }

        return result;
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
        boolean result = false;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.next();
        }
        
        return result;
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
        boolean result = false;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.previous();
        }
        
        return result;
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
    public boolean getBoolean(int index)
        throws SQLException
    {
        boolean result = false;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getBoolean(index);
        }
        
        return result;
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
    public boolean getBoolean(String columnName)
        throws  SQLException
    {
        boolean result = false;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getBoolean(columnName);
        }
        
        return result;
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
        int result = -1;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getType();
        }
        
        return result;
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
    public long getLong(int index)
        throws  SQLException
    {
        long result = 0;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getLong(index);
        }
        
        return result;
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
    public long getLong(String columnName)
        throws  SQLException
    {
        long result = 0;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getLong(columnName);
        }
        
        return result;
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
    public Object getObject(int index)
        throws  SQLException
    {
        Object result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getObject(index);
        }
        
        return result;
    }

    /**
     * See ResultSet#getObject(String)
     * @see java.sql.ResultSet#getObject(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public Object getObject(String columnName)
        throws  SQLException
    {
        Object result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getObject(columnName);
        }
        
        return result;
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
    public Object getObject(int index, Map map)
        throws  SQLException
    {
        Object result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getObject(index, map);
        }
        
        return result;
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
    public Object getObject(String columnName, Map map)
        throws  SQLException
    {
        Object result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getObject(columnName, map);
        }
        
        return result;
    }

    /**
     * See ResultSet#close()
     * @see java.sql.ResultSet#close()
     * @exception SQLException if an error occurs.
     */
    public void close()
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.close();
        }
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
    public Ref getRef(int index)
        throws  SQLException
    {
        Ref result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getRef(index);
        }
        
        return result;
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
    public Ref getRef(String columnName)
        throws  SQLException
    {
        Ref result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getRef(columnName);
        }
        
        return result;
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
    public Time getTime(int index)
        throws  SQLException
    {
        Time result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getTime(index);
        }
        
        return result;
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
    public Time getTime(String columnName)
        throws  SQLException
    {
        Time result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getTime(columnName);
        }
        
        return result;
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
    public Time getTime(int index, Calendar calendar)
        throws  SQLException
    {
        Time result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getTime(index, calendar);
        }
        
        return result;
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
    public Time getTime(String columnName, Calendar calendar)
        throws  SQLException
    {
        Time result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getTime(columnName, calendar);
        }
        
        return result;
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
    public Date getDate(int index)
        throws  SQLException
    {
        Date result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getDate(index);
        }
        
        return result;
    }

    /**
     * See ResultSet#getDate(String)
     * @see java.sql.ResultSet#getDate(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public Date getDate(String columnName)
        throws  SQLException
    {
        Date result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getDate(columnName);
        }
        
        return result;
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
    public Date getDate(int index, Calendar calendar)
        throws  SQLException
    {
        Date result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getDate(index, calendar);
        }
        
        return result;
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
    public Date getDate(String columnName, Calendar calendar)
        throws  SQLException
    {
        Date result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getDate(columnName, calendar);
        }
        
        return result;
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
        boolean result = false;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.first();
        }
        
        return result;
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
    public byte getByte(int index)
        throws  SQLException
    {
        byte result = 0;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getByte(index);
        }
        
        return result;
    }

    /**
     * See ResultSet#getByte(String)
     * @see java.sql.ResultSet#getByte(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public byte getByte(String columnName)
        throws  SQLException
    {
        byte result = 0;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getByte(columnName);
        }
        
        return result;
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
    public short getShort(int index)
        throws  SQLException
    {
        short result = 0;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getShort(index);
        }
        
        return result;
    }

    /**
     * See ResultSet#getShort(String)
     * @see java.sql.ResultSet#getShort(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public short getShort(String columnName)
        throws  SQLException
    {
        short result = 0;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getShort(columnName);
        }
        
        return result;
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
    public int getInt(int index)
        throws  SQLException
    {
        int result = 0;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getInt(index);
        }
        
        return result;
    }

    /**
     * See ResultSet#getInt(String)
     * @see java.sql.ResultSet#getInt(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public int getInt(String columnName)
        throws  SQLException
    {
        int result = 0;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getInt(columnName);
        }
        
        return result;
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
    public float getFloat(int index)
        throws  SQLException
    {
        float result = 0;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getFloat(index);
        }
        
        return result;
    }

    /**
     * See ResultSet#getFloat(String)
     * @see java.sql.ResultSet#getFloat(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public float getFloat(String columnName)
        throws  SQLException
    {
        float result = 0;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getFloat(columnName);
        }
        
        return result;
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
    public double getDouble(int index)
        throws  SQLException
    {
        double result = 0;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getDouble(index);
        }
        
        return result;
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
    public double getDouble(String columnName)
        throws  SQLException
    {
        double result = 0;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getDouble(columnName);
        }
        
        return result;
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
        ResultSetMetaData result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getMetaData();
        }
        
        return result;
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
        SQLWarning result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getWarnings();
        }
        
        return result;
    }

    /**
     * See ResultSet#clearWarnings()
     * @see java.sql.ResultSet#clearWarnings()
     * @exception SQLException if an error occurs.
     */
    public void clearWarnings()
        throws SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.clearWarnings();
        }
    }

    /**
     * See ResultSet#setFetchDirection(int)
     * @see java.sql.ResultSet#setFetchDirection(int)
     * @param direction (Taken from Sun's Javadoc) an integer specifying the
     * suggested fetch direction; one of ResultSet.FETCH_FORWARD,
     * ResultSet.FETCH_REVERSE, or ResultSet.FETCH_UNKNOWN.
     * @exception SQLException if an error occurs.
     */
    public void setFetchDirection(int direction)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.setFetchDirection(direction);
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
        int result = -1;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getFetchDirection();
        }
        
        return result;
    }

    /**
     * See ResultSet#setFetchSize(int)
     * @see java.sql.ResultSet#setFetchSize(int)
     * @param size (Taken from Sun's Javadoc) the number of rows to fetch.
     * @exception SQLException if an error occurs.
     */
    public void setFetchSize(int size)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.setFetchSize(size);
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
        int result = -1;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getFetchSize();
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
    public String getString(int index)
        throws  SQLException
    {
        String result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getString(index);
        }
        
        return result;
    }

    /**
     * See ResultSet#getString(String)
     * @see java.sql.ResultSet#getString(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public String getString(String columnName)
        throws  SQLException
    {
        String result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getString(columnName);
        }
        
        return result;
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
    public Array getArray(int index)
        throws  SQLException
    {
        Array result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getArray(index);
        }
        
        return result;
    }

    /**
     * See ResultSet#getArray(String)
     * @see java.sql.ResultSet#getArray(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public Array getArray(String columnName)
        throws  SQLException
    {
        Array result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getArray(columnName);
        }
        
        return result;
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
    public InputStream getAsciiStream(int index)
        throws  SQLException
    {
        InputStream result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getAsciiStream(index);
        }
        
        return result;
    }

    /**
     * See ResultSet#getAsciiStream(String)
     * @see java.sql.ResultSet#getAsciiStream(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public InputStream getAsciiStream(String columnName)
        throws  SQLException
    {
        InputStream result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getAsciiStream(columnName);
        }
        
        return result;
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
    public BigDecimal getBigDecimal(int index, int scale)
        throws  SQLException
    {
        BigDecimal result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getBigDecimal(index, scale);
        }
        
        return result;
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
    public BigDecimal getBigDecimal(String columnName, int scale)
        throws  SQLException
    {
        BigDecimal result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getBigDecimal(columnName, scale);
        }
        
        return result;
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
    public BigDecimal getBigDecimal(int index)
        throws  SQLException
    {
        BigDecimal result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getBigDecimal(index);
        }
        
        return result;
    }

    /**
     * See ResultSet#getBigDecimal(String)
     * @see java.sql.ResultSet#getBigDecimal(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public BigDecimal getBigDecimal(String columnName)
        throws  SQLException
    {
        BigDecimal result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getBigDecimal(columnName);
        }
        
        return result;
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
    public InputStream getBinaryStream(int index)
        throws  SQLException
    {
        InputStream result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getBinaryStream(index);
        }
        
        return result;
    }

    /**
     * See ResultSet#getBinaryStream(String)
     * @see java.sql.ResultSet#getBinaryStream(String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public InputStream getBinaryStream(String columnName)
        throws  SQLException
    {
        InputStream result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getBinaryStream(columnName);
        }
        
        return result;
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
    public Blob getBlob(int index)
        throws  SQLException
    {
        Blob result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getBlob(index);
        }
        
        return result;
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
    public Blob getBlob(String columnName)
        throws  SQLException
    {
        Blob result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getBlob(columnName);
        }
        
        return result;
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
    public Clob getClob(int index)
        throws  SQLException
    {
        Clob result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getClob(index);
        }
        
        return result;
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
    public Clob getClob(String columnName)
        throws  SQLException
    {
        Clob result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getClob(columnName);
        }
        
        return result;
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
    public Timestamp getTimestamp(int index)
        throws  SQLException
    {
        Timestamp result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getTimestamp(index);
        }
        
        return result;
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
    public Timestamp getTimestamp(String columnName)
        throws  SQLException
    {
        Timestamp result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getTimestamp(columnName);
        }
        
        return result;
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
    public Timestamp getTimestamp(int index, Calendar calendar)
        throws  SQLException
    {
        Timestamp result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getTimestamp(index, calendar);
        }
        
        return result;
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
    public Timestamp getTimestamp(String columnName, Calendar calendar)
        throws  SQLException
    {
        Timestamp result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getTimestamp(columnName, calendar);
        }
        
        return result;
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
    public InputStream getUnicodeStream(int index)
        throws  SQLException
    {
        InputStream result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getUnicodeStream(index);
        }
        
        return result;
    }

    /**
     * See ResultSet#getUnicodeStream(String)
     * @see java.sql.ResultSet#getUnicodeStream(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     */
    public InputStream getUnicodeStream(String columnName)
        throws  SQLException
    {
        InputStream result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getUnicodeStream(columnName);
        }
        
        return result;
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
        boolean result = false;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.wasNull();
        }
        
        return result;
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
    public Reader getCharacterStream(int index)
        throws  SQLException
    {
        Reader result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getCharacterStream(index);
        }
        
        return result;
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
    public Reader getCharacterStream(String columnName)
        throws  SQLException
    {
        Reader result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getCharacterStream(columnName);
        }
        
        return result;
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
    public boolean absolute(int index)
        throws  SQLException
    {
        boolean result = false;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.absolute(index);
        }
        
        return result;
    }

    /**
     * See ResultSet#afterLast()
     * @see java.sql.ResultSet#afterLast()
     * @exception SQLException if an error occurs.
     */
    public void afterLast()
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.afterLast();
        }
    }

    /**
     * See ResultSet#beforeFirst()
     * @see java.sql.ResultSet#beforeFirst()
     * @exception SQLException if an error occurs.
     */
    public void beforeFirst()
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.beforeFirst();
        }
    }

    /**
     * See ResultSet#cancelRowUpdates()
     * @see java.sql.ResultSet#cancelRowUpdates()
     * @exception SQLException if an error occurs.
     */
    public void cancelRowUpdates()
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.cancelRowUpdates();
        }
    }

    /**
     * See ResultSet#deleteRow()
     * @see java.sql.ResultSet#deleteRow()
     * @exception SQLException if an error occurs.
     */
    public void deleteRow()
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.deleteRow();
        }
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
    public int findColumn(String columnName)
        throws  SQLException
    {
        int result = -1;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.findColumn(columnName);
        }
        
        return result;
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
        int result = -1;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getConcurrency();
        }
        
        return result;
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
        String result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getCursorName();
        }
        
        return result;
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
        int result = -1;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getRow();
        }
        
        return result;
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
        Statement result = getQuery();

        if  (result == null) 
        {
            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getStatement();
            }
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
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
             t_ResultSet.insertRow();
        }
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
        boolean result = false;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.isAfterLast();
        }
        
        return result;
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
        boolean result = false;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.isBeforeFirst();
        }
        
        return result;
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
        boolean result = false;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.isFirst();
        }
        
        return result;
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
        boolean result = false;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.isLast();
        }
        
        return result;
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
        boolean result = false;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.last();
        }
        
        return result;
    }

    /**
     * See ResultSet#moveToCurrentRow()
     * @see java.sql.ResultSet#moveToCurrentRow()
     * @exception SQLException if an error occurs.
     */
    public void moveToCurrentRow()
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.moveToCurrentRow();
        }
    }

    /**
     * See ResultSet#moveToInsertRow()
     * @see java.sql.ResultSet#moveToInsertRow()
     * @exception SQLException if an error occurs.
     */
    public void moveToInsertRow()
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.moveToInsertRow();
        }
    }

    /**
     * See ResultSet#refreshRow()
     * @see java.sql.ResultSet#refreshRow()
     * @exception SQLException if an error occurs.
     */
    public void refreshRow()
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.refreshRow();
        }
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
    public boolean relative(int index)
        throws  SQLException
    {
        boolean result = false;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.relative(index);
        }
        
        return result;
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
        boolean result = false;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.rowDeleted();
        }
        
        return result;
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
        boolean result = false;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.rowInserted();
        }
        
        return result;
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
        boolean result = false;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.rowUpdated();
        }
        
        return result;
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
            int          index,
            InputStream  value,
            int          length)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateAsciiStream(index, value, length);
        }
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
            String       columnName,
            InputStream  value,
            int          length)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateAsciiStream(columnName, value, length);
        }
    }

    /**
     * See ResultSet#updateBigDecimal(int,BigDecimal)
     * @see java.sql.ResultSet#updateBigDecimal(int,java.math.BigDecimal)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value a <code>BigDecimal</code> value
     * @exception SQLException if an error occurs.
     */
    public void updateBigDecimal(int index, BigDecimal value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateBigDecimal(index, value);
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
    public void updateBigDecimal(String columnName, BigDecimal value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateBigDecimal(columnName, value);
        }
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
            int          index,
            InputStream  value,
            int          length)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateBinaryStream(index, value, length);
        }
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
            String       columnName,
            InputStream  value,
            int          length)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateBinaryStream(columnName, value, length);
        }
    }

    /**
     * See ResultSet#updateBoolean(int,boolean)
     * @see java.sql.ResultSet#updateBoolean(int,boolean)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateBoolean(int index, boolean value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateBoolean(index, value);
        }
    }

    /**
     * See ResultSet#updateBoolean(String,boolean)
     * @see java.sql.ResultSet#updateBoolean(java.lang.String,boolean)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateBoolean(String columnName, boolean value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateBoolean(columnName, value);
        }
    }

    /**
     * See ResultSet#updateByte(int,byte)
     * @see java.sql.ResultSet#updateByte(int,byte)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateByte(int index, byte value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateByte(index, value);
        }
    }

    /**
     * See ResultSet#updateByte(String,byte)
     * @see java.sql.ResultSet#updateByte(java.lang.String,byte)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateByte(String columnName, byte value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateByte(columnName, value);
        }
    }

    /**
     * See ResultSet#updateBytes(int,byte[])
     * @see java.sql.ResultSet#updateBytes(int,byte[])
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateBytes(int index, byte[] value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateBytes(index, value);
        }
    }

    /**
     * See ResultSet#updateBytes(String,byte[])
     * @see java.sql.ResultSet#updateBytes(java.lang.String,byte[])
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateBytes(String columnName, byte[] value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateBytes(columnName, value);
        }
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
    public void updateCharacterStream(int index, Reader value, int length)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateCharacterStream(index, value, length);
        }
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
            String  columnName,
            Reader  value,
            int     length)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateCharacterStream(columnName, value, length);
        }
    }

    /**
     * See ResultSet#updateDate(int,Date)
     * @see java.sql.ResultSet#updateDate(int,java.sql.Date)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateDate(int index, Date value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateDate(index, value);
        }
    }

    /**
     * See ResultSet#updateDate(String,Date)
     * @see java.sql.ResultSet#updateDate(java.lang.String,java.sql.Date)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateDate(String columnName, Date value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateDate(columnName, value);
        }
    }

    /**
     * See ResultSet#updateDouble(int,double)
     * @see java.sql.ResultSet#updateDouble(int,double)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateDouble(int index, double value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateDouble(index, value);
        }
    }

    /**
     * See ResultSet#updateDouble(String,double)
     * @see java.sql.ResultSet#updateDouble(String,double)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateDouble(String columnName, double value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateDouble(columnName, value);
        }
    }

    /**
     * See ResultSet#updateFloat(int,float)
     * @see java.sql.ResultSet#updateFloat(int,float)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateFloat(int index, float value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateFloat(index, value);
        }
    }

    /**
     * See ResultSet#updateFloat(String,float)
     * @see java.sql.ResultSet#updateFloat(String,float)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs
     */
    public void updateFloat(String columnName, float value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateFloat(columnName, value);
        }
    }

    /**
     * See ResultSet#updateInt(int,int)
     * @see java.sql.ResultSet#updateInt(int,int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateInt(int index, int value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateInt(index, value);
        }
    }

    /**
     * See ResultSet#updateInt(String,int)
     * @see java.sql.ResultSet#updateInt(java.lang.String,int)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateInt(String columnName, int value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateInt(columnName, value);
        }
    }

    /**
     * See ResultSet#updateLong(int,long)
     * @see java.sql.ResultSet#updateLong(int,long)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateLong(int index, long value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateLong(index, value);
        }
    }

    /**
     * See ResultSet#updateLong(String,long)
     * @see java.sql.ResultSet#updateLong(java.lang.String,long)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs
     */
    public void updateLong(String columnName, long value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateLong(columnName, value);
        }
    }

    /**
     * See ResultSet#updateNull(int)
     * @see java.sql.ResultSet#updateNull(int)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     *
     * @exception SQLException if an error occurs.
     */
    public void updateNull(int index)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateNull(index);
        }
    }

    /**
     * See ResultSet#updateNull(String)
     * @see java.sql.ResultSet#updateNull(java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @exception SQLException if an error occurs.
     */
    public void updateNull(String columnName)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateNull(columnName);
        }
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
    public void updateObject(int index, Object value, int scale)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateObject(index, value, scale);
        }
    }

    /**
     * See ResultSet#updateObject(int,Object)
     * @see java.sql.ResultSet#updateObject(int,java.lang.Object)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateObject(int index, Object value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateObject(index, value);
        }
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
    public void updateObject(String columnName, Object value, int scale)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateObject(columnName, value, scale);
        }
    }

    /**
     * See ResultSet#updateObject(String,Object)
     * @see java.sql.ResultSet#updateObject(java.lang.String,java.lang.Object)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateObject(String columnName, Object value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateObject(columnName, value);
        }
    }

    /**
     * See ResultSet#updateRow()
     * @see java.sql.ResultSet#updateRow()
     * @exception SQLException if an error occurs.
     */
    public void updateRow()
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateRow();
        }
    }

    /**
     * See ResultSet#updateShort(int,short)
     * @see java.sql.ResultSet#updateShort(int,short)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateShort(int index, short value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateShort(index, value);
        }
    }

    /**
     * See ResultSet#updateShort(String,short)
     * @see java.sql.ResultSet#updateShort(java.lang.String,short)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateShort(String columnName, short value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateShort(columnName, value);
        }
    }

    /**
     * See ResultSet#updateString(int,String)
     * @see java.sql.ResultSet#updateString(int,java.lang.String)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateString(int index, String value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateString(index, value);
        }
    }

    /**
     * See ResultSet#updateString(String,String)
     * @see java.sql.ResultSet#updateString(java.lang.String,java.lang.String)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateString(String columnName, String value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateString(columnName, value);
        }
    }

    /**
     * See ResultSet#updateTime(int,Time)
     * @see java.sql.ResultSet#updateTime(int,java.sql.Time)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateTime(int index, Time value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateTime(index, value);
        }
    }

    /**
     * See ResultSet#updateTime(String,Time)
     * @see java.sql.ResultSet#updateTime(java.lang.String,java.sql.Time)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateTime(String columnName, Time value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateTime(columnName, value);
        }
    }

    /**
     * See ResultSet#updateTimestamp(int,Timestamp)
     * @see java.sql.ResultSet#updateTimestamp(int,java.sql.Timestamp)
     * @param index (Taken from Sun's Javadoc) the first column
     * is 1, the second is 2, ...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateTimestamp(int index, Timestamp value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateTimestamp(index, value);
        }
    }

    /**
     * See ResultSet#updateTimestamp(String,Timestamp)
     * @see java.sql.ResultSet#updateTimestamp(java.lang.String,java.sql.Timestamp)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of
     * the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateTimestamp(String columnName, Timestamp value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateTimestamp(columnName, value);
        }
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
    public URL getURL(int columnIndex)
        throws  SQLException
    {
        URL result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getURL(columnIndex);
        }

        return result;
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
    public URL getURL(String columnName)
        throws  SQLException
    {
        URL result = null;

        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            result = t_ResultSet.getURL(columnName);
        }

        return result;
    }

    /**
     * See ResultSet#updateRef(int,Ref)
     * @see java.sql.ResultSet#updateRef(int,java.sql.Ref)
     * @param columnIndex (Taken from Sun's Javadoc) the index of the column
     * 1 is the first, 2 is the second,...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateRef(int columnIndex, Ref value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateRef(columnIndex, value);
        }
    }

    /**
     * See ResultSet#updateRef(String,Ref)
     * @see java.sql.ResultSet#updateRef(java.lang.String,java.sql.Ref)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateRef(String columnName, Ref value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateRef(columnName, value);
        }
    }

    /**
     * See ResultSet#updateBlob(int,Blob)
     * @see java.sql.ResultSet#updateBlob(int,java.sql.Blob)
     * @param columnIndex (Taken from Sun's Javadoc) the index of the column
     * 1 is the first, 2 is the second,...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateBlob(int columnIndex, Blob value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateBlob(columnIndex, value);
        }
    }

    /**
     * See ResultSet#updateBlob(String,Blob)
     * @see java.sql.ResultSet#updateBlob(java.lang.String,java.sql.Blob)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateBlob(String columnName, Blob value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateBlob(columnName, value);
        }
    }


    /**
     * See ResultSet#updateClob(int,Clob)
     * @see java.sql.ResultSet#updateClob(int,java.sql.Clob)
     * @param columnIndex (Taken from Sun's Javadoc) the index of the column
     * 1 is the first, 2 is the second,...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateClob(int columnIndex, Clob value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateClob(columnIndex, value);
        }
    }

    /**
     * See ResultSet#updateClob(String,Clob)
     * @see java.sql.ResultSet#updateClob(java.lang.String,java.sql.Clob)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateClob(String columnName, Clob value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateClob(columnName, value);
        }
    }


    /**
     * See ResultSet#updateArray(int,Array)
     * @see java.sql.ResultSet#updateArray(int,java.sql.Array)
     * @param columnIndex (Taken from Sun's Javadoc) the index of the column
     * 1 is the first, 2 is the second,...
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateArray(int columnIndex, Array value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateArray(columnIndex, value);
        }
    }

    /**
     * See ResultSet#updateArray(String,Array)
     * @see java.sql.ResultSet#updateArray(java.lang.String,java.sql.Array)
     * @param columnName (Taken from Sun's Javadoc) the SQL name of the column.
     * @param value (Taken from Sun's Javadoc) the new column value.
     * @exception SQLException if an error occurs.
     */
    public void updateArray(String columnName, Array value)
        throws  SQLException
    {
        ResultSet t_ResultSet = getResultSet();

        if  (t_ResultSet != null) 
        {
            t_ResultSet.updateArray(columnName, value);
        }
    }

    // Helper ResultSet methods //

    /**
     * Retrieves a byte array value using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public byte[] getBytes(Field field)
        throws  SQLException
    {
        byte[] result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null)
            {
                result = t_ResultSet.getBytes(t_iFieldIndex);
            }
        }

        return result;
    }

    /**
     * Retrieves a boolean value using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is <code>false</code>.
     * @exception SQLException if an error occurs.
     */
    public boolean getBoolean(Field field)
        throws  SQLException
    {
        boolean result = false;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getBoolean(t_iFieldIndex);
            }
        }
        
        return result;
    }

    /**
     * Retrieves a boolean value using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     */
    public long getLong(Field field)
        throws  SQLException
    {
        long result = 0;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getLong(t_iFieldIndex);
            }
        }
        
        return result;
    }

    /**
     * Retrieves an object value using the field reference.
     * @param field the field.
     * @exception SQLException if an error occurs.
     */
    public Object getObject(Field field)
        throws  SQLException
    {
        Object result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getObject(t_iFieldIndex);
            }
        }
        
        return result;
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
    public Object getObject(Field field, Map map)
        throws  SQLException
    {
        Object result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getObject(t_iFieldIndex, map);
            }
        }
        
        return result;
    }

    /**
     * Retrieves a Ref value using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Ref getRef(Field field)
        throws  SQLException
    {
        Ref result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getRef(t_iFieldIndex);
            }
        }
        
        return result;
    }

    /**
     * Retrieves a Time value using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Time getTime(Field field)
        throws  SQLException
    {
        Time result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getTime(t_iFieldIndex);
            }
        }
        
        return result;
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
    public Time getTime(Field field, Calendar calendar)
        throws  SQLException
    {
        Time result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getTime(t_iFieldIndex, calendar);
            }
        }
        
        return result;
    }

    /**
     * Retrieves a Date value using the field reference.
     * @param field the field.
     * @exception SQLException if an error occurs.
     */
    public Date getDate(Field field)
        throws  SQLException
    {
        Date result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getDate(t_iFieldIndex);
            }
        }
        
        return result;
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
    public Date getDate(Field field, Calendar calendar)
        throws  SQLException
    {
        Date result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getDate(t_iFieldIndex, calendar);
            }
        }
        
        return result;
    }

    /**
     * Retrieves a byte value using the field reference.
     * @param field the field.
     * @exception SQLException if an error occurs.
     */
    public byte getByte(Field field)
        throws  SQLException
    {
        byte result = 0;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getByte(t_iFieldIndex);
            }
        }
        
        return result;
    }

    /**
     * Retrieves a short value using the field reference.
     * @param field the field.
     * @exception SQLException if an error occurs.
     */
    public short getShort(Field field)
        throws  SQLException
    {
        short result = 0;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getShort(t_iFieldIndex);
            }
        }
        
        return result;
    }

    /**
     * Retrieves an integer value using the field reference.
     * @param field the field.
     * @exception SQLException if an error occurs.
     */
    public int getInt(Field field)
        throws  SQLException
    {
        int result = 0;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getInt(t_iFieldIndex);
            }
        }
        
        return result;
    }

    /**
     * Retrieves a float value using the field reference.
     * @param field the field.
     * @exception SQLException if an error occurs.
     */
    public float getFloat(Field field)
        throws  SQLException
    {
        float result = 0;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getFloat(t_iFieldIndex);
            }
        }
        
        return result;
    }

    /**
     * Retrieves a double value using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is 0.
     * @exception SQLException if an error occurs.
     */
    public double getDouble(Field field)
        throws  SQLException
    {
        double result = 0;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getDouble(t_iFieldIndex);
            }
        }
        
        return result;
    }

    /**
     * Retrieves a text value using the field reference.
     * @param field the field.
     * @exception SQLException if an error occurs.
     */
    public String getString(Field field)
        throws  SQLException
    {
        String result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getString(t_iFieldIndex);
            }
        }
        
        return result;
    }

    /**
     * Retrieves an array value using the field reference.
     * @param field the field.
     * @exception SQLException if an error occurs.
     */
    public Array getArray(Field field)
        throws  SQLException
    {
        Array result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getArray(t_iFieldIndex);
            }
        }
        
        return result;
    }

    /**
     * Retrieves an ASCII stream using the field reference.
     * @param field the field.
     * @exception SQLException if an error occurs.
     */
    public InputStream getAsciiStream(Field field)
        throws  SQLException
    {
        InputStream result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getAsciiStream(t_iFieldIndex);
            }
        }
        
        return result;
    }

    /**
     * Retrieves a BigDecimal value using the field reference.
     * @param field the field.
     * @param scale (Taken from Sun's Javadoc) the number of digits to the
     * right of the decimal point.
     * @exception SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     */
    public BigDecimal getBigDecimal(Field field, int scale)
        throws  SQLException
    {
        BigDecimal result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getBigDecimal(t_iFieldIndex, scale);
            }
        }
        
        return result;
    }

    /**
     * Retrieves a BigDecimal value using the field reference.
     * @param field the field.
     * @exception SQLException if an error occurs.
     */
    public BigDecimal getBigDecimal(Field field)
        throws  SQLException
    {
        BigDecimal result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getBigDecimal(t_iFieldIndex);
            }
        }
        
        return result;
    }

    /**
     * Retrieves a binary stream using the field reference.
     * @param field the field.
     * @exception SQLException if an error occurs.
     */
    public InputStream getBinaryStream(Field field)
        throws  SQLException
    {
        InputStream result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getBinaryStream(t_iFieldIndex);
            }
        }
        
        return result;
    }

    /**
     * Retrieves a blob using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Blob getBlob(Field field)
        throws  SQLException
    {
        Blob result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getBlob(t_iFieldIndex);
            }
        }
        
        return result;
    }

    /**
     * Retrieves a clob value using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Clob getClob(Field field)
        throws  SQLException
    {
        Clob result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getClob(t_iFieldIndex);
            }
        }
        
        return result;
    }

    /**
     * Retrieves a timestamp value using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Timestamp getTimestamp(Field field)
        throws  SQLException
    {
        Timestamp result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getTimestamp(t_iFieldIndex);
            }
        }
        
        return result;
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
    public Timestamp getTimestamp(Field field, Calendar calendar)
        throws  SQLException
    {
        Timestamp result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result =
                    t_ResultSet.getTimestamp(t_iFieldIndex, calendar);
            }
        }
        
        return result;
    }

    /**
     * Retrieves an Unicode stream using the field reference.
     * @param field the field.
     * @exception SQLException if an error occurs.
     * @deprecated since it's deprecated in JDK 1.4 as well.
     */
    public InputStream getUnicodeStream(Field field)
        throws  SQLException
    {
        InputStream result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getUnicodeStream(t_iFieldIndex);
            }
        }
        
        return result;
    }

    /**
     * Retrieves a character stream using the field reference.
     * @param field the field.
     * @return (Taken from Sun's Javadoc) the column value; if
     * the value is SQL NULL, the value returned is null.
     * @exception SQLException if an error occurs.
     */
    public Reader getCharacterStream(Field field)
        throws  SQLException
    {
        Reader result = null;

        Query t_Query = getQuery();

        if  (t_Query != null) 
        {
            int t_iFieldIndex = t_Query.getFieldIndex(field);

            ResultSet t_ResultSet = getResultSet();

            if  (t_ResultSet != null) 
            {
                result = t_ResultSet.getCharacterStream(t_iFieldIndex);
            }
        }
        
        return result;
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
    public URL getURL(Field field)
        throws  SQLException
    {
        URL result = null;

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
    public int findColumn(Field field)
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
            Field        field,
            InputStream  value,
            int          length)
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
    public void updateBigDecimal(Field field, BigDecimal value)
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
            Field        field,
            InputStream  value,
            int          length)
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
    public void updateBoolean(Field field, boolean value)
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
    public void updateByte(Field field, byte value)
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
    public void updateBytes(Field field, byte[] value)
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
            Field   field,
            Reader  value,
            int     length)
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
    public void updateDate(Field field, Date value)
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
    public void updateDouble(Field field, double value)
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
    public void updateFloat(Field field, float value)
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
    public void updateInt(Field field, int value)
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
    public void updateLong(Field field, long value)
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
    public void updateNull(Field field)
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
    public void updateObject(Field field, Object value, int scale)
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
    public void updateObject(Field field, Object value)
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
    public void updateShort(Field field, short value)
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
    public void updateString(Field field, String value)
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
    public void updateTime(Field field, Time value)
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
    public void updateTimestamp(Field field, Timestamp value)
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
    public void updateRef(Field field, Ref value)
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
    public void updateBlob(Field field, Blob value)
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
    public void updateClob(Field field, Clob value)
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
    public void updateArray(Field field, Array value)
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
}
