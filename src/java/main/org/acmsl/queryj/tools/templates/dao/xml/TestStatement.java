package org.acmsl.queryj.tools.templates.dao.xml;

import java.sql.Connection;
import java.sql.SQLWarning;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class TestStatement
    implements Statement {

// Implementation of java.sql.Statement

    /**
     * 
     * @throws SQLException if an error occurs.
     */
    public void close()
        throws SQLException
    {
        
    }

    /**
     * 
     * @param string 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public boolean execute(String string)
        throws SQLException
    {
        return false;
    }

    /**
     * 
     * @param string 
     * @param n 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public boolean execute(String string, int n)
        throws SQLException
    {
        return false;
    }

    /**
     * 
     * @param string 
     * @param intArray an <code>int[]</code> value
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public boolean execute(String string, int[] intArray)
        throws SQLException
    {
        return false;
    }

    /**
     * 
     * @param string 
     * @param stringArray a <code>String[]</code> value
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public boolean execute(String string, String[] stringArray)
        throws SQLException
    {
        return false;
    }

    /**
     * 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public Connection getConnection()
        throws SQLException
    {
        return null;
    }

    /**
     * 
     * @param string 
     * @throws SQLException if an error occurs.
     */
    public void addBatch(String string)
        throws SQLException
    {
        
    }

    /**
     * 
     * @throws SQLException if an error occurs.
     */
    public void cancel()
        throws SQLException
    {
        
    }

    /**
     * 
     * @throws SQLException if an error occurs.
     */
    public void clearBatch()
        throws SQLException
    {
        
    }

    /**
     * 
     * @throws SQLException if an error occurs.
     */
    public void clearWarnings()
        throws SQLException
    {
        
    }

    /**
     * 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public int[] executeBatch()
        throws SQLException
    {
        return null;
    }

    /**
     * 
     * @param string 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public ResultSet executeQuery(String string)
        throws SQLException
    {
        return null;
    }

    /**
     * 
     * @param string 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public int executeUpdate(String string)
        throws SQLException
    {
        return 0;
    }

    /**
     * 
     * @param string 
     * @param n 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public int executeUpdate(String string, int n)
        throws SQLException
    {
        return 0;
    }

    /**
     * 
     * @param string 
     * @param intArray an <code>int[]</code> value
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public int executeUpdate(String string, int[] intArray)
        throws SQLException
    {
        return 0;
    }

    /**
     * 
     * @param string 
     * @param stringArray a <code>String[]</code> value
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public int executeUpdate(String string, String[] stringArray)
        throws SQLException
    {
        return 0;
    }

    /**
     * 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public int getFetchDirection()
        throws SQLException
    {
        return 0;
    }

    /**
     * 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public int getFetchSize()
        throws SQLException
    {
        return 0;
    }

    /**
     * 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public ResultSet getGeneratedKeys()
        throws SQLException
    {
        return null;
    }

    /**
     * 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public int getMaxFieldSize()
        throws SQLException
    {
        return 0;
    }

    /**
     * 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public int getMaxRows()
        throws SQLException
    {
        return 0;
    }

    /**
     * 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public boolean getMoreResults()
        throws SQLException
    {
        return false;
    }

    /**
     * 
     * @param n 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public boolean getMoreResults(final int n)
        throws SQLException
    {
        return false;
    }

    /**
     * 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public int getQueryTimeout()
        throws SQLException
    {
        return 0;
    }

    /**
     * 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public ResultSet getResultSet()
        throws SQLException
    {
        return null;
    }

    /**
     * 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public int getResultSetConcurrency()
        throws SQLException
    {
        return 0;
    }

    /**
     * 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public int getResultSetHoldability()
        throws SQLException
    {
        return 0;
    }

    /**
     * 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public int getResultSetType()
        throws SQLException
    {
        return 0;
    }

    /**
     * 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public int getUpdateCount()
        throws SQLException
    {
        return 0;
    }

    /**
     * 
     * @return such information.
     * @throws SQLException if an error occurs.
     */
    public SQLWarning getWarnings()
        throws SQLException
    {
        return null;
    }

    /**
     * 
     * @param string 
     * @throws SQLException if an error occurs.
     */
    public void setCursorName(String string)
        throws SQLException
    {
        
    }

    /**
     * 
     * @param flag 
     * @throws SQLException if an error occurs.
     */
    public void setEscapeProcessing(boolean flag)
        throws SQLException
    {
        
    }

    /**
     * 
     * @param n 
     * @throws SQLException if an error occurs.
     */
    public void setFetchDirection(final int n)
        throws SQLException
    {
        
    }

    /**
     * 
     * @param n 
     * @throws SQLException if an error occurs.
     */
    public void setFetchSize(final int n)
        throws SQLException
    {
        
    }

    /**
     * 
     * @param n 
     * @throws SQLException if an error occurs.
     */
    public void setMaxFieldSize(final int n)
        throws SQLException
    {
        
    }

    /**
     * 
     * @param n 
     * @throws SQLException if an error occurs.
     */
    public void setMaxRows(final int n)
        throws SQLException
    {
        
    }

    /**
     * 
     * @param n 
     * @throws SQLException if an error occurs.
     */
    public void setQueryTimeout(final int n)
        throws SQLException
    {
        
    }
}
