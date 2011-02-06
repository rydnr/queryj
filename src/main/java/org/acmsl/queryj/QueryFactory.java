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
 * Filename: QueryFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Has the responsiblity of knowing how to create queries.
 *
 */
package org.acmsl.queryj;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.DeleteQuery;
import org.acmsl.queryj.InsertQuery;
import org.acmsl.queryj.Query;
import org.acmsl.queryj.SelectQuery;
import org.acmsl.queryj.UpdateQuery;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Factory;
import org.acmsl.commons.patterns.Singleton;

/**
 * Has the responsiblity of knowing how to create queries.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class QueryFactory
    implements  Factory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class QueryFactorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final QueryFactory SINGLETON = new QueryFactory();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected QueryFactory() {};

    /**
     * Retrieves a <code>QueryFactory</code> instance.
     * @return such instance.
     */
    public static QueryFactory getInstance()
    {
        return QueryFactorySingletonContainer.SINGLETON;
    }

    /**
     * Creates a select query.
     * @return such query.
     */
    public SelectQuery createSelectQuery()
    {
        return new SelectQuery();
    }

    /**
     * Creates an insert query.
     * @return such query.
     */
    public InsertQuery createInsertQuery()
    {
        return new InsertQuery();
    }

    /**
     * Creates an update query.
     * @return such query.
     */
    public UpdateQuery createUpdateQuery()
    {
        return new UpdateQuery();
    }

    /**
     * Creates a delete query.
     * @return such query.
     */
    public DeleteQuery createDeleteQuery()
    {
        return new DeleteQuery();
    }
}
