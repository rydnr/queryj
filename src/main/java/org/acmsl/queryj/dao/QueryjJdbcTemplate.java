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
    Lesser General Public License for more details.

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
 * Description: Customization of JdbcTemplate to deal with proper connection
 *              handling.
 */
package org.acmsl.queryj.dao;

/*
 * Importing Spring classes.
 */
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.jdbc.SQLWarningException;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.nativejdbc.NativeJdbcExtractor;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.SqlProvider;

/*
 * Importing some JDK classes.
 */
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Importing some JDK extension classes
 */
import javax.sql.DataSource;

/*
 * Importing Jakarta Commons Logging classes
 */
import org.apache.commons.logging.LogFactory;

/**
 * Customization of JdbcTemplate to deal with proper connection handling.
 * @author <a href="http://maven.acm-sl.org/queryj">QueryJ</a>
 */
public class QueryjJdbcTemplate
    extends  JdbcTemplate
{
    /**
     * Construct a new JdbcTemplate for bean usage.
     * Note: The DataSource has to be set before using the instance.
     * This constructor can be used to prepare a JdbcTemplate via a BeanFactory,
     * typically setting the DataSource via setDataSource.
     * @see #setDataSource
     */
    public QueryjJdbcTemplate()
    {
        super();
    }

    /**
     * Construct a new <code>QueryjJdbcTemplate</code>, given a DataSource to obtain connections from.
     * Note: This will trigger eager initialization of the exception translator.
     * @param dataSource JDBC DataSource to obtain connections from
     */
    public QueryjJdbcTemplate(final DataSource dataSource)
    {
        super(dataSource);
    }

    /**
     * Executes a operation, taking into account a proper connection handling.
     * @param action the <code>ConnectionCallback</code> instance.
     * @return the result of the operation.
     * @precondition action != null
     */
    public Object execute(final ConnectionCallback action)
        throws DataAccessException
    {
        return
            execute(
                action,
                getDataSource(),
                getNativeJdbcExtractor(),
                getExceptionTranslator());
    }

    /**
     * Executes a operation, taking into account a proper connection handling.
     * @param action the <code>ConnectionCallback</code> instance.
     * @param dataSource the <code>DataSource</code> instance.
     * @param nativeJdbcExtractor the native JDBC extractor.
     * @param exceptionTranslator the exception translator.
     * @return the result of the operation.
     * @precondition action != null
     * @precondition dataSource != null
     * @precondition nativeJdbcExtractor != null
     * @precondition exceptionTranslator != null
     */
    protected Object execute(
        final ConnectionCallback action,
        final DataSource dataSource,
        final NativeJdbcExtractor nativeJdbcExtractor,
        final SQLExceptionTranslator exceptionTranslator)
      throws DataAccessException
    {
        Object result = null;

        Connection t_Connection = null;

        boolean t_bCloseConnection = false;

        DataSource t_DataSource = dataSource;

        if  (!(dataSource instanceof ThreadAwareDataSourceWrapper))
        {
            t_DataSource = new ThreadAwareDataSourceWrapper(dataSource);
        }

        try
        {
            ConnectionHolder t_ConnectionHolder =
                (ConnectionHolder)
                    TransactionSynchronizationManager.getResource(
                        t_DataSource);

            if  (t_ConnectionHolder != null)
            {
                t_Connection = t_ConnectionHolder.getConnection();
            }
            else
            {
                t_Connection = t_DataSource.getConnection();
                t_bCloseConnection = true;
            }

            if  (   (TransactionSynchronizationManager
                         .isSynchronizationActive())
                 && (t_bCloseConnection))
            {
                t_ConnectionHolder = new ConnectionHolder(t_Connection);
                TransactionSynchronizationManager.bindResource(
                    t_DataSource, t_ConnectionHolder);
                TransactionSynchronizationManager.registerSynchronization(
                    new ConnectionSynchronization(
                        t_ConnectionHolder, t_DataSource));
            }

            Connection t_ConnectionToUse = t_Connection;

            if  (nativeJdbcExtractor != null)
            {
                t_ConnectionToUse =
                    nativeJdbcExtractor.getNativeConnection(t_Connection);
            }

            result = action.doInConnection(t_ConnectionToUse);
        }
        catch  (final SQLException sqlException)
        {
            throw
                exceptionTranslator.translate(
                    "executing ConnectionCallback", getSql(action), sqlException);
        }
        finally
        {
            if  (   (t_bCloseConnection)
                 && (t_Connection != null))
            {
                try
                {
                    t_Connection.close();
                }
                catch  (final SQLException sqlException)
                {
                    LogFactory.getLog(QueryjJdbcTemplate.class).info(
                        "Could not close connection",
                        sqlException);
                }
            }
        }

        return result;
    }

    /**
     * Executes a operation, taking into account a proper connection handling.
     * @param action the <code>StatementCallback</code> instance.
     * @return the result of the operation.
     * @precondition action != null
     */
    public Object execute(final StatementCallback action)
        throws DataAccessException
    {
        return
            execute(
                action,
                getDataSource(),
                getNativeJdbcExtractor(),
                getExceptionTranslator());
    }

    /**
     * Executes a operation, taking into account a proper connection handling.
     * @param action the <code>StatementCallback</code> instance.
     * @param dataSource the <code>DataSource</code> instance.
     * @param nativeJdbcExtractor the native JDBC extractor.
     * @param exceptionTranslator the exception translator.
     * @return the result of the operation.
     * @precondition action != null
     * @precondition dataSource != null
     * @precondition nativeJdbcExtractor != null
     * @precondition exceptionTranslator != null
     */
    protected Object execute(
        final StatementCallback action,
        final DataSource dataSource,
        final NativeJdbcExtractor nativeJdbcExtractor,
        final SQLExceptionTranslator exceptionTranslator)
      throws DataAccessException
    {
        Object result = null;

        Connection t_Connection = null;

        boolean t_bCloseConnection = false;

        Statement t_Statement = null;

        DataSource t_DataSource = dataSource;

        if  (!(dataSource instanceof ThreadAwareDataSourceWrapper))
        {
            t_DataSource = new ThreadAwareDataSourceWrapper(dataSource);
        }

        try
        {
            ConnectionHolder t_ConnectionHolder =
                (ConnectionHolder)
                    TransactionSynchronizationManager.getResource(
                        t_DataSource);

            if  (t_ConnectionHolder != null)
            {
                t_Connection = t_ConnectionHolder.getConnection();
            }
            else
            {
                t_Connection = t_DataSource.getConnection();
                t_bCloseConnection = true;
            }

            if  (   (TransactionSynchronizationManager
                         .isSynchronizationActive())
                 && (t_bCloseConnection))
            {
                // use same Connection for further JDBC actions within the transaction
                // thread object will get removed by synchronization at transaction completion
                t_ConnectionHolder = new ConnectionHolder(t_Connection);
                TransactionSynchronizationManager.bindResource(
                    t_DataSource, t_ConnectionHolder);
                TransactionSynchronizationManager.registerSynchronization(
                    new ConnectionSynchronization(
                        t_ConnectionHolder, t_DataSource));
            }

            Connection t_ConnectionToUse = t_Connection;

            if  (   (nativeJdbcExtractor != null)
                 && (nativeJdbcExtractor
                         .isNativeConnectionNecessaryForNativeStatements()))
            {
                t_ConnectionToUse =
                    nativeJdbcExtractor.getNativeConnection(t_Connection);
            }

            t_Statement = t_ConnectionToUse.createStatement();

            DataSourceUtils.applyTransactionTimeout(
                t_Statement, t_DataSource);
            
            Statement t_StatementToUse = t_Statement;

            if  (nativeJdbcExtractor != null)
            {
                t_StatementToUse =
                    nativeJdbcExtractor.getNativeStatement(t_Statement);
            }

            result = action.doInStatement(t_StatementToUse);

            SQLWarning t_Warning = t_Statement.getWarnings();

            throwExceptionOnWarningIfNotIgnoringWarnings(t_Warning);
        }
        catch (final SQLException sqlException)
        {
            throw exceptionTranslator.translate(
                "executing StatementCallback", getSql(action), sqlException);
        }
        finally
        {
            JdbcUtils.closeStatement(t_Statement);

            if  (   (t_bCloseConnection)
                 && (t_Connection != null))
            {
                try
                {
                    t_Connection.close();
                }
                catch  (final SQLException sqlException)
                {
                    LogFactory.getLog(QueryjJdbcTemplate.class).info(
                        "Could not close connection",
                        sqlException);
                }
            }
        }

        return result;
    }

    /**
     * Executes a operation, taking into account a proper connection handling.
     * @param psc the <code>preparedStatementCreator</code> instance.
     * @param action the <code>PreparedStatementCallback</code> instance.
     * @return the result of the operation.
     * @precondition action != null
     */
    public Object execute(
        final PreparedStatementCreator preparedStatementCreator,
        final PreparedStatementCallback action)
      throws DataAccessException
    {
        return
            execute(
                preparedStatementCreator,
                action,
                getDataSource(),
                getNativeJdbcExtractor(),
                getExceptionTranslator());
    }

    /**
     * Executes a operation, taking into account a proper connection handling.
     * @param preparedStatementCreator the <code>PreparedStatementCreator</code>
     * instance.
     * @param action the <code>PreparedStatementCallback</code> instance.
     * @param dataSource the <code>DataSource</code> instance.
     * @param nativeJdbcExtractor the native JDBC extractor.
     * @param exceptionTranslator the exception translator.
     * @return the result of the operation.
     * @precondition preparedStatementCreator != null
     * @precondition action != null
     * @precondition dataSource != null
     * @precondition nativeJdbcExtractor != null
     * @precondition exceptionTranslator != null
     */
    protected Object execute(
        final PreparedStatementCreator preparedStatementCreator,
        final PreparedStatementCallback action,
        final DataSource dataSource,
        final NativeJdbcExtractor nativeJdbcExtractor,
        final SQLExceptionTranslator exceptionTranslator)
      throws DataAccessException
    {
        Object result = null;

        Connection t_Connection = null;

        boolean t_bCloseConnection = false;

        PreparedStatement t_PreparedStatement = null;

        DataSource t_DataSource = dataSource;

        if  (!(dataSource instanceof ThreadAwareDataSourceWrapper))
        {
            t_DataSource = new ThreadAwareDataSourceWrapper(dataSource);
        }

        try
        {
            ConnectionHolder t_ConnectionHolder =
                (ConnectionHolder)
                    TransactionSynchronizationManager.getResource(
                        t_DataSource);

            if  (t_ConnectionHolder != null)
            {
                t_Connection = t_ConnectionHolder.getConnection();
            }
            else
            {
                t_Connection = t_DataSource.getConnection();
                t_bCloseConnection = true;
            }

            if  (   (TransactionSynchronizationManager
                         .isSynchronizationActive())
                 && (t_bCloseConnection))
            {
                // use same Connection for further JDBC actions within the transaction
                // thread object will get removed by synchronization at transaction completion
                t_ConnectionHolder = new ConnectionHolder(t_Connection);
                TransactionSynchronizationManager.bindResource(
                    t_DataSource, t_ConnectionHolder);
                TransactionSynchronizationManager.registerSynchronization(
                    new ConnectionSynchronization(
                        t_ConnectionHolder, t_DataSource));
            }

            Connection t_ConnectionToUse = t_Connection;

            if  (   (nativeJdbcExtractor != null)
                 && (nativeJdbcExtractor
                         .isNativeConnectionNecessaryForNativePreparedStatements()))
            {
                t_ConnectionToUse =
                    nativeJdbcExtractor.getNativeConnection(t_Connection);
            }

            t_PreparedStatement =
                preparedStatementCreator.createPreparedStatement(
                    t_ConnectionToUse);

            DataSourceUtils.applyTransactionTimeout(
                t_PreparedStatement, t_DataSource);
            
            PreparedStatement t_PreparedStatementToUse =
                t_PreparedStatement;

            if  (nativeJdbcExtractor != null)
            {
                t_PreparedStatementToUse =
                    nativeJdbcExtractor.getNativePreparedStatement(
                        t_PreparedStatement);
            }

            result = action.doInPreparedStatement(t_PreparedStatementToUse);

            SQLWarning t_Warning = t_PreparedStatement.getWarnings();
            
            throwExceptionOnWarningIfNotIgnoringWarnings(t_Warning);
        }
        catch  (final SQLException sqlException)
        {
            throw exceptionTranslator.translate(
                  "executing PreparedStatementCallback ["
                + preparedStatementCreator + "]",
                getSql(preparedStatementCreator),
                sqlException);
        }
        finally
        {
            if  (preparedStatementCreator instanceof ParameterDisposer)
            {
                ((ParameterDisposer) preparedStatementCreator)
                    .cleanupParameters();
            }

            JdbcUtils.closeStatement(t_PreparedStatement);

            if  (   (t_bCloseConnection)
                 && (t_Connection != null))
            {
                try
                {
                    t_Connection.close();
                }
                catch  (final SQLException sqlException)
                {
                    LogFactory.getLog(QueryjJdbcTemplate.class).info(
                        "Could not close connection",
                        sqlException);
                }
            }
        }

        return result;
    }

    /**
     * Determine SQL from potential provider object.
     * @param sqlProvider object that's potentially a SqlProvider
     * @return the SQL string, or null
     * @see SqlProvider
     */
    protected String getSql(final Object sqlProvider)
    {
        String result = null;

        if  (sqlProvider instanceof SqlProvider)
        {
            result = ((SqlProvider) sqlProvider).getSql();
        }

        return result;
    }

    /**
     * Throw an SQLWarningException if we're not ignoring warnings.
     * @param warning warning from current statement. May be null,
     * in which case this method does nothing.
     */
    protected void throwExceptionOnWarningIfNotIgnoringWarnings(final SQLWarning warning)
        throws SQLWarningException
    {
        throwExceptionOnWarningIfNotIgnoringWarnings(warning, getIgnoreWarnings());
    }

    /**
     * Throw an SQLWarningException if we're not ignoring warnings.
     * @param warning warning from current statement. May be null,
     * in which case this method does nothing.
     * @param ignoreWarnings whether to ignore warnings.
     */
    protected void throwExceptionOnWarningIfNotIgnoringWarnings(
        final SQLWarning warning, final boolean ignoreWarnings)
      throws SQLWarningException
    {
        if  (   (warning != null)
             && (!ignoreWarnings))
        {
            throw new SQLWarningException("Warning not ignored", warning);
        }
    }

    /**
     * Callback for resource cleanup at the end of a non-native-JDBC transaction
     * (e.g. when participating in a JTA transaction).
     */
    private static class ConnectionSynchronization 
        extends TransactionSynchronizationAdapter
    {
        private final ConnectionHolder connectionHolder;

        private final DataSource dataSource;

        private ConnectionSynchronization(
            final ConnectionHolder connectionHolder, DataSource dataSource)
        {
            this.connectionHolder = connectionHolder;
            this.dataSource = dataSource;
        }

        public void suspend()
        {
            TransactionSynchronizationManager.unbindResource(
                this.dataSource);
        }

        public void resume()
        {
            TransactionSynchronizationManager.bindResource(
                this.dataSource, this.connectionHolder);
        }

        public void beforeCompletion()
        {
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            DataSourceUtils.closeConnectionIfNecessary(
                this.connectionHolder.getConnection(), this.dataSource);
        }
    }
}

