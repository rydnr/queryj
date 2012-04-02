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
    Lesser General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: TransactionManager.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents a transaction runtime environment. The one that
 *              created it manages the transaction.
 */
package org.acmsl.queryj.dao;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.dao.TransactionTokenFactory;
import org.acmsl.queryj.dao.TransactionToken;

/*
 * Importing Spring classes.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Manager;
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JDK classes.
 */
import java.sql.Connection;
import java.sql.SQLException;

/*
 * Importing Java extension classes.
 */
import javax.sql.DataSource;

/**
 * Simplifies transaction management.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TransactionManager
    implements Manager,
               Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class TransactionManagerSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final TransactionManager SINGLETON =
            new TransactionManager();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     * @param alias the table alias.
     */
    protected TransactionManager() {};

    /**
     * Retrieves a <code>TransactionManager</code> instance.
     * @return such instance.
     */
    @NotNull
    public static TransactionManager getInstance()
    {
        return TransactionManagerSingletonContainer.SINGLETON;
    }

    /**
     * Starts a transaction for given connection.
     * @param connection the connection.
     * @throws TransactionException if the transaction cannot be started for
     * some reason.
     * @precondition connection != null
     */
    @Nullable
    public TransactionToken begin(final Connection connection)
        throws  TransactionException
    {
        return
            begin(
                new SingleConnectionDataSource(connection, true));
    }

    /**
     * Starts a transaction for given data source.
     * @param dataSource the data source.
     * @param transactionDefinition the transaction definition.
     * @throws TransactionException if the transaction cannot be started for
     * some reason.
     * @precondition dataSource != null
     * @precondition transactionDefinition != null
     */
    @Nullable
    public TransactionToken begin(
        final Connection connection,
        final TransactionDefinition transactionDefinition)
      throws  TransactionException
    {
        return
            begin(
                new SingleConnectionDataSource(connection, true),
                transactionDefinition);
    }

    /**
     * Starts a transaction for given connection.
     * @param connection the connection.
     * @throws TransactionException if the transaction cannot be started for
     * some reason.
     * @precondition connection != null
     */
    @Nullable
    public TransactionToken begin(final DataSource dataSource)
        throws  TransactionException
    {
        return begin(dataSource, new DefaultTransactionDefinition());
    }

    /**
     * Starts a transaction for given data source.
     * @param dataSource the data source.
     * @param transactionDefinition the transaction definition.
     * @throws TransactionException if the transaction cannot be started for
     * some reason.
     * @precondition dataSource != null
     * @precondition transactionDefinition != null
     */
    @Nullable
    public TransactionToken begin(
        final DataSource dataSource,
        final TransactionDefinition transactionDefinition)
      throws  TransactionException
    {
        return
            begin(
                dataSource,
                transactionDefinition,
                TransactionTokenFactory.getInstance());
    }

    /**
     * Starts a transaction for given data source.
     * @param dataSource the data source.
     * @param transactionDefinition the transaction definition.
     * @param transactionTokenFactory the <code>TransactionTokenFactory</code>
     * instance.
     * @throws TransactionException if the transaction cannot be started for
     * some reason.
     * @precondition dataSource != null
     * @precondition transactionDefinition != null
     * @precondition transactionTokenFactory != null
     */
    @Nullable
    protected TransactionToken begin(
        final DataSource dataSource,
        final TransactionDefinition transactionDefinition,
        @NotNull final TransactionTokenFactory transactionTokenFactory)
      throws  TransactionException
    {
        @Nullable TransactionToken result = null;

        DataSource t_DataSource = dataSource;

        if  (!(t_DataSource instanceof ThreadAwareDataSourceWrapper))
        {
            t_DataSource =
                new ThreadAwareDataSourceWrapper(t_DataSource);
        }

        @Nullable PlatformTransactionManager t_TransactionManager =
            createTransactionManager(t_DataSource, true);

        if  (t_TransactionManager != null)
        {
            // Performs an implicit transaction start.
            TransactionStatus t_TransactionStatus =
                t_TransactionManager.getTransaction(
                    transactionDefinition);

            if  (   (t_TransactionStatus != null)
                 && (t_TransactionStatus instanceof DefaultTransactionStatus))
            {
                result =
                    transactionTokenFactory.createTransactionToken(
                        (DefaultTransactionStatus) t_TransactionStatus,
                        t_DataSource);
            }
        }

        return result;
    }

    /**
     * Creates a transaction manager correctly initialized.
     * @param connection the connection.
     * @return a <code>PlatformTransactionManager</code> instance.
     * @precondition connection != null
     */
    @Nullable
    protected PlatformTransactionManager createTransactionManager(
        final Connection connection)
    {
        return
            createTransactionManager(
                new SingleConnectionDataSource(connection, true),
                true);
    }

    /**
     * Creates a transaction manager correctly initialized.
     * @param dataSource the data source.
     * @return a <code>PlatformTransactionManager</code> instance.
     * @precondition dataSource != null
     */
    @Nullable
    protected PlatformTransactionManager createTransactionManager(
        final DataSource dataSource)
    {
        return createTransactionManager(dataSource, false);
    }

    /**
     * Creates a transaction manager correctly initialized.
     * @param dataSource the data source.
     * @return a <code>PlatformTransactionManager</code> instance.
     * @precondition dataSource != null
     */
    @Nullable
    protected PlatformTransactionManager createTransactionManager(
        final DataSource dataSource, final boolean initialize)
    {
        @Nullable PlatformTransactionManager result = null;

        if  (initialize)
        {
            // Enforcing connection is registered.
            DataSourceUtils.getConnection(dataSource, true);
        }

        result = new DataSourceTransactionManager(dataSource);

        return result;
    }

    /**
     * Commits a transaction identified by given transaction token.
     * @param trasactionToken the transaction token.
     * @param transactionManager the <code>PlatformTransactionManager</code>
     * instance.
     * @throws TransactionException if the transaction could not be committed
     * anyway.
     * @precondition transactionManager != null
     */
    public void commit(final TransactionStatus transactionToken)
      throws  TransactionException
    {
        if   (transactionToken instanceof DataSourceTransactionToken)
        {
            commitTransaction((DataSourceTransactionToken) transactionToken);
        }
        else
        {
            throw
                new TransactionManagerException(
                    "Invalid transaction token: " + transactionToken);
        }
    }

    /**
     * Commits a transaction identified by given transaction token.
     * @param trasactionToken the transaction token.
     * @param transactionManager the <code>PlatformTransactionManager</code>
     * instance.
     * @throws TransactionException if the transaction could not be committed
     * anyway.
     * @precondition transactionManager != null
     */
    protected void commitTransaction(
        @NotNull final DataSourceTransactionToken transactionToken)
      throws  TransactionException
    {
        commitTransaction(
            transactionToken,
            createTransactionManager(
                transactionToken.getDataSource(), false));
    }

    /**
     * Commits a transaction identified by given transaction token.
     * @param trasactionToken the transaction token.
     * @param transactionManager the <code>PlatformTransactionManager</code>
     * instance.
     * @throws TransactionException if the transaction could not be committed
     * anyway.
     * @precondition transactionManager != null
     */
    protected void commitTransaction(
        final TransactionToken transactionToken,
        @NotNull final PlatformTransactionManager transactionManager)
      throws  TransactionException
    {
        transactionManager.commit(transactionToken);
    }

    /**
     * Rollbacks a transaction identified by given transaction token.
     * @param trasactionToken the transaction token.
     * @param transactionManager the <code>PlatformTransactionManager</code>
     * instance.
     * @throws TransactionException if the transaction could not be rollbackted
     * anyway.
     * @precondition transactionManager != null
     */
    public void rollback(final TransactionStatus transactionToken)
      throws  TransactionException
    {
        if   (transactionToken instanceof DataSourceTransactionToken)
        {
            rollbackTransaction((DataSourceTransactionToken) transactionToken);
        }
        else
        {
            throw
                new TransactionManagerException(
                    "Invalid transaction token: " + transactionToken);
        }
    }

    /**
     * Rollbacks a transaction identified by given transaction token.
     * @param trasactionToken the transaction token.
     * @param transactionManager the <code>PlatformTransactionManager</code>
     * instance.
     * @throws TransactionException if the transaction could not be rollbackted
     * anyway.
     * @precondition transactionManager != null
     */
    protected void rollbackTransaction(
        @NotNull final DataSourceTransactionToken transactionToken)
      throws  TransactionException
    {
        rollbackTransaction(
            transactionToken,
            createTransactionManager(transactionToken.getDataSource(), false));
    }

    /**
     * Rollbacks a transaction identified by given transaction token.
     * @param trasactionToken the transaction token.
     * @param transactionManager the <code>PlatformTransactionManager</code>
     * instance.
     * @throws TransactionException if the transaction could not be rollbackted
     * anyway.
     * @precondition transactionManager != null
     */
    protected void rollbackTransaction(
        final TransactionToken transactionToken,
        @NotNull final PlatformTransactionManager transactionManager)
      throws  TransactionException
    {
        transactionManager.rollback(transactionToken);
    }

    /**
     * Inner transaction exception shortcut.
     * @author <a href="mailto:chous@acm-sl.org"
     *         >Jose San Leandro Armendariz</a>
     */
    public static class TransactionManagerException
        extends  TransactionException
    {
        /**
         * Creates a <code>TransactionManagerException</code>
         * with given message.
         * @param message the message.
         */
        public TransactionManagerException(final String message)
        {
            super(message);
        }
    }
}
