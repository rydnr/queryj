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
 * Description: Has the responsibility of creating transaction tokens.
 *              Right now, it follows the FactoryMethod pattern, but could be
 *              refactored as an abstract factory as new technologies are
 *              supported.
 *
 */
package org.acmsl.queryj.dao;

/*
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;

/*
 * Importing some extension classes.
 */
import javax.sql.DataSource;

/**
 * Has the responsibility of creating transaction tokens. Right now, it
 * follows the FactoryMethod pattern, but could be refactored as an abstract
 * factory as new technologies are supported.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro Armendariz</a>
 */
public class TransactionTokenFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected TransactionTokenFactory() {};

    /**
     * Specifies a new weak reference.
     * @param factory the factory instance to use.
     */
    protected static void setReference(TransactionTokenFactory factory)
    {
        singleton = new WeakReference(factory);
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
     * Retrieves a TransactionTokenFactory instance.
     * @return such instance.
     */
    public static TransactionTokenFactory getInstance()
    {
        TransactionTokenFactory result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (TransactionTokenFactory) reference.get();
        }

        if  (result == null) 
        {
            result = new TransactionTokenFactory();

            setReference(result);
        }

        return result;
    }

    /**
     * Creates a SingleConnectionTransactionToken using given connection.
     * @param dataSource the data source to use inside the same transaction.
     * @return the transaction token, or null if the connection is invalid.
     */
    public static DataSourceTransactionToken createTransactionToken(
        DataSource dataSource)
    {
        DataSourceTransactionToken result = null;

        if  (dataSource != null)
        {
            result = new DataSourceTransactionToken(dataSource) {};
        }

        return result;
    }
}
