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
 * Description: JDBC-related Transaction Token using DataSource class.
 *
 */
package org.acmsl.queryj.dao;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.dao.TransactionToken;

/*
 * Importing some JDK1.3 classes.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.io.PrintWriter;

/*
 * Importing some extension classes.
 */
import javax.sql.DataSource;

/*
 * Importing Jakarta Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * JDBC-related Transaction Token using DataSource class.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro Armendariz</a>
 */
public abstract class DataSourceTransactionToken
    implements  TransactionToken
{
    /**
     * Transaction aliveness.
     */
    private boolean m__bTransactionAlive;

    /**
     * Data source released.
     */
    private boolean m__bNotYetReleased;

    /**
     * Rollback pending.
     */
    private boolean m__bRollbackPending;

    /**
     * DataSource reference.
     */
    private _DataSourceWrapper m__DataSource;

    /**
     * Creates a DataSourceTransactionToken using given connection.
     * @param dataSource the data source to use inside the whole transaction.
     */
    public DataSourceTransactionToken(DataSource dataSource)
    {
        setDataSource(dataSource);
        setNotYetReleased(true);
        setRollbackPending(false);
    }

    /**
     * Specifies the data source.
     * @param dataSource the connection to use inside the whole transaction.
     */
    protected void setDataSource(DataSource dataSource)
    {
        DataSource t_DataSource = getDataSource();

        if  (t_DataSource != dataSource)
        {
            m__DataSource = new _DataSourceWrapper(dataSource);
        }
    }

    /**
     * Retrieves the data source.
     * @return the data source used inside the whole transaction.
     */
    public DataSource getDataSource()
    {
        return m__DataSource;
    }

    /**
     * Checks if this object is logically equal to given one.
     * @param object the object to compare to.
     * @return true if both objects are equal logically.
     */
    public boolean equals(Object object)
    {
        boolean result = false;

        DataSource t_DataSource = getDataSource();

        result =
            (   (t_DataSource != null) 
             && (t_DataSource.equals(object)));

        if  (!result) 
        {
            result = super.equals(object);
        }

        return result;
    }

    /**
     * Checks if the transaction is alive.
     * @return <code>true</code> if so.
     */
    public boolean isTransactionAlive()
    {
        return m__bTransactionAlive;
    }

    /**
     * Checks if the transaction is alive.
     * @return <code>true</code> if so.
     */
    protected void setTransactionAlive(boolean alive)
    {
        m__bTransactionAlive = alive;
    }

    /**
     * Checks if the transaction has been released.
     * @return <code>false</code> if so.
     */
    public boolean isNotYetReleased()
    {
        return m__bNotYetReleased;
    }

    /**
     * Specifies if the transaction has been released.
     * @param releaseState the release state.
     */
    protected void setNotYetReleased(boolean releaseState)
    {
        m__bNotYetReleased = releaseState;
    }

    /**
     * Gets notified whenever the transaction starts.
     */
    public void beginTransaction()
    {
        setTransactionAlive(true);
    }

    /**
     * Takes into account that the transaction is over.
     */
    public void endTransaction()
    {
        setTransactionAlive(false);
    }
    
    /**
     * Releases the transaction.
     */
    public void release()
    {
        if  (isNotYetReleased())
        {
            DataSource t_DataSource = getDataSource();

            if  (t_DataSource != null) 
            {
//                t_DataSource.release();
            }
            
            setNotYetReleased(false);
        }
    }

    /**
     * Sets the need for a rollback of the whole transaction.
     * @param flag to indicate to rollback the transaction.
     */
    public void setRollbackPending(boolean flag)
    {
        m__bRollbackPending = flag;
    }

    /**
     * Checks the need for a rollback of the whole transaction.
     * @return such information.
     */
    public boolean isRollbackPending()
    {
        return m__bRollbackPending;
    }

    /**
     * Private DataSource wrapper.
     * @author <a href="mailto:jsanleandro@yahoo.es"
               >Jose San Leandro Armendariz</a>
     * @version $Revision$
     */
    private static class _DataSourceWrapper
        implements  DataSource
    {
        /**
        * Concrete wrapped data source.
        */
        private DataSource m__DataSource;

        /**
        * The connection used in the transaction.
        */
        private Connection m__Connection;

        /**
        * Creates a _DataSourceWrapper using given data source.
        * @param dataSource the data source to wrap.
        */
        public _DataSourceWrapper(DataSource dataSource)
        {
            setDataSource(dataSource);
        }

        /**
         * Specifies the data source.
         * @param dataSource the data source.
         */
        private void unmodifiableSetDataSource(DataSource dataSource)
        {
            m__DataSource = dataSource;
        }

        /**
         * Specifies the data source.
         * @param dataSource the data source.
         */
        protected void setDataSource(DataSource dataSource)
        {
            unmodifiableSetDataSource(dataSource);
        }

        /**
         * Retrieves the data source.
         * @return the data source.
         */
        public DataSource getDataSource()
        {
            return m__DataSource;
        }

        /**
         * Specifies the JDBC connection.
         * @param connection the connection.
         */
        protected void setConnection(Connection connection)
        {
            m__Connection = connection;
        }

        /**
         * Retrieves the JDBC connection.
         * @return such connection.
         */
        protected Connection getInternalConnection()
        {
            return m__Connection;
        }

        /**
         * Attempts to establish a database connection.
         * @return the connection.
        */
        public Connection getConnection()
            throws  SQLException
        {
            Connection result = getInternalConnection();

            if  (result == null)
            {
                DataSource t_DataSource = getDataSource();

                if  (t_DataSource != null) 
                {
                    result = t_DataSource.getConnection();
                    setConnection(result);
                    result.setAutoCommit(false);
                }
            }

            return m__Connection;
        }

        /**
        * Attempts to establish a database connection.
        * @param userName the user name connection settings.
        * @param password the user password connection settings.
        * @return the connection.
        */
        public Connection getConnection(String userName, String password)
            throws  SQLException
        {
            Connection result = getConnection();

            if  (result == null)
            {
                DataSource t_DataSource = getDataSource();

                if  (t_DataSource != null) 
                {
                    result = t_DataSource.getConnection(userName, password);
                    setConnection(result);
                    result.setAutoCommit(false);
                }
            }

            return result;
        }

        /**
         * Retrieves the maximum time in seconds that this data source can
         * wait while attempting to connect to a database.
         * @return such timeout.
         */
        public int getLoginTimeout()
            throws  SQLException
        {
            int result = 0;

            DataSource t_DataSource = getDataSource();

            if  (t_DataSource != null) 
            {
                result = t_DataSource.getLoginTimeout();
            }
            
            return result;
        }

        /**
         * Retrieves the log writer for this data source.
         * @return such log writer.
         */
        public PrintWriter getLogWriter()
            throws  SQLException
        {
            PrintWriter result = null;

            DataSource t_DataSource = getDataSource();

            if  (t_DataSource != null) 
            {
                result = t_DataSource.getLogWriter();
            }

            return result;
        }

        /**
         * Specifies the maximum time in seconds that this data source will
         * wait while attempting to connect to a database.
         * @param seconds the timeout.
         */
        public void setLoginTimeout(int seconds)
            throws  SQLException
        {
            DataSource t_DataSource = getDataSource();

            if  (t_DataSource != null) 
            {
                t_DataSource.setLoginTimeout(seconds);
            }
        }

        /**
         * Specifies the log writer for this data source.
         * @param out the log writer.
         */
        public void setLogWriter(PrintWriter out)
            throws  SQLException
        {
            DataSource t_DataSource = getDataSource();

            if  (t_DataSource != null) 
            {
                t_DataSource.setLogWriter(out);
            }
        }

        /**
         * Releases the transaction.
         */
        public void release()
        {
            Connection t_Connection = getInternalConnection();

            if  (t_Connection != null)
            {
                try
                {                
                    t_Connection.close();
                } 
                catch  (SQLException sqlException)
                {
                    LogFactory.getLog(getClass()).fatal(
                        "Error releasing Connection.",
                        sqlException);
                }

                setConnection(null);
            }
        }

        /**
         * Checks if the data source is equal logically to given object.
         * @param object the object to compare to.
         * @return true if both data sources represents the same entity.
         */
        public boolean equals(Object object)
        {
            boolean result = false;

            if  (   (object != null)
                 && (object instanceof DataSource))
            {
                DataSource t_InternalDataSource = getDataSource();

                DataSource t_GivenDataSource = (DataSource) object;

                result =
                    (   (t_GivenDataSource == t_InternalDataSource)
                     || (t_GivenDataSource.equals(t_InternalDataSource)));
            }

            return result;
        }
    }
}
