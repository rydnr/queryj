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
 * Filename: PreparedStatementNotAvailableForValidationException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents a call to  RetrieveQueryHandler#retrieveCurrentSql(orgQueryJCommand)}
 * out of context, when no Sql is being validated.
 *
 * Date: 2014/03/15
 * Time: 10:23
 *
 */
package org.acmsl.queryj.customsql.exceptions;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.exceptions.QueryJNonCheckedException;
import org.acmsl.queryj.customsql.Sql;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents a call to
 * {@link org.acmsl.queryj.customsql.handlers.customsqlvalidation.RetrieveQueryHandler#retrieveCurrentSql(org.acmsl.queryj.QueryJCommand)}
 * out of context, when no {@link Sql} is being validated.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/15 10:23
 */
@ThreadSafe
public class PreparedStatementNotAvailableForValidationException
    extends QueryJNonCheckedException
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -421418527346539968L;

    /**
     * Creates a new instance with given {@link Sql} as context.
     * @param sql the current sql.
     */
    public PreparedStatementNotAvailableForValidationException(@NotNull final Sql<String> sql)
    {
        super("PreparedStatement.not.available.for.validation", new String[] { sql.getId()});
    }
}
