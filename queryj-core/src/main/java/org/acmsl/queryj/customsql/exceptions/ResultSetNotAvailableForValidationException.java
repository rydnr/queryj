/*
                        QueryJ Core

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
 * Filename: ResultSetNotAvailableForValidationException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents a call to
 * ExecuteQueryHandler#retrieveCurrentResultSet(QueryJCommand)}
 * out of context, when no Sql is being validated.
 *
 * Date: 2014/03/15
 * Time: 11:40
 *
 */
package org.acmsl.queryj.customsql.exceptions;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.exceptions.QueryJNonCheckedException;
import org.acmsl.queryj.customsql.Sql;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents a call to
 * {@link org.acmsl.queryj.customsql.handlers.customsqlvalidation.ExecuteQueryHandler#retrieveCurrentResultSet(org.acmsl.queryj.QueryJCommand)}
 * out of context, when no {@link Sql} is being validated.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/15 11:40
 */
@ThreadSafe
public class ResultSetNotAvailableForValidationException
    extends QueryJNonCheckedException
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -6398569718658616193L;

    /**
     * Creates a new instance to use when the requested {@link java.sql.ResultSet} is not available.
     * @param sql the current {@link Sql}.
     */
    public ResultSetNotAvailableForValidationException(final Sql<String> sql)
    {
        super("ResultSet.not.available.for.validation", new String[] { sql.getId() });
    }
}
