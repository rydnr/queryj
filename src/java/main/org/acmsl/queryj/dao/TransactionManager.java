/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile$
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
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.SQLException;

/*
 * Importing Java extension classes.
 */
import javax.sql.DataSource;

/**
 * Simplifies transaction management.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro Armendariz</a>
 */
public class TransactionManager
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     * @param alias the table alias.
     */
    protected TransactionManager() {};

    /**
     * Specifies a new weak reference.
     * @param manager the new manager instance.
     */
    protected static void setReference(final TransactionManager manager)
    {
        singleton = new WeakReference(manager);
    }

    /**
     * Retrieves the weak reference.
     * @return such reference.
     */
    protected static WeakReference getReference()
    {
        return singleton;
    }

    /**
     * Retrieves a TransactionManager instance.
     * @return such instance.
     */
    public static TransactionManager getInstance()
    {
        TransactionManager result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (TransactionManager) reference.get();
        }

        if  (result == null) 
        {
            result = new TransactionManager();

            setReference(result);
        }

        return result;
    }

    /**
     * Starts a transaction for given connection.
     * @param connection the connection.
     * @throws TransactionException if the transaction cannot be started for
     * some reason.
     * @precondition connection != null
     */
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
    protected TransactionToken begin(
        final DataSource dataSource,
        final TransactionDefinition transactionDefinition,
        final TransactionTokenFactory transactionTokenFactory)
      throws  TransactionException
    {
        TransactionToken result = null;

        DataSource t_DataSource = dataSource;

        if  (!(t_DataSource instanceof ThreadAwareDataSourceWrapper))
        {
            t_DataSource =
                new ThreadAwareDataSourceWrapper(t_DataSource);
        }

        PlatformTransactionManager t_TransactionManager =
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
    protected PlatformTransactionManager createTransactionManager(
        final DataSource dataSource, final boolean initialize)
    {
        PlatformTransactionManager result = null;

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
                new TransactionException(
                    "Invalid transaction token: " + transactionToken) {};
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
        final DataSourceTransactionToken transactionToken)
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
        final PlatformTransactionManager transactionManager)
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
                new TransactionException(
                    "Invalid transaction token: " + transactionToken) {};
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
        final DataSourceTransactionToken transactionToken)
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
        final PlatformTransactionManager transactionManager)
      throws  TransactionException
    {
        transactionManager.rollback(transactionToken);
    }
}
