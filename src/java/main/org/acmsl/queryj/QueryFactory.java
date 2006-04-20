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
 * Description: Has the responsiblity of knowing how to create queries.
 *
 */
package org.acmsl.queryj;

/*
 * Importing ACM-SL classes.
 */
import org.acmsl.queryj.DeleteQuery;
import org.acmsl.queryj.InsertQuery;
import org.acmsl.queryj.Query;
import org.acmsl.queryj.SelectQuery;
import org.acmsl.queryj.UpdateQuery;

/*
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;

/**
 * Has the responsiblity of knowing how to create queries.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 */
public abstract class QueryFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected QueryFactory() {};

    /**
     * Specifies a new weak reference.
     * @param factory the factory instance to use.
     */
    protected static void setReference(final QueryFactory factory)
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
     * Retrieves a QueryFactory instance.
     * @return such instance.
     */
    public static QueryFactory getInstance()
    {
        QueryFactory result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (QueryFactory) reference.get();
        }

        if  (result == null) 
        {
            result = new QueryFactory() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Creates a select query.
     * @return such query.
     */
    public SelectQuery createSelectQuery()
    {
        return new SelectQuery() {};
    }

    /**
     * Creates an insert query.
     * @return such query.
     */
    public InsertQuery createInsertQuery()
    {
        return new InsertQuery() {};
    }

    /**
     * Creates an update query.
     * @return such query.
     */
    public UpdateQuery createUpdateQuery()
    {
        return new UpdateQuery() {};
    }

    /**
     * Creates a delete query.
     * @return such query.
     */
    public DeleteQuery createDeleteQuery()
    {
        return new DeleteQuery() {};
    }
}
