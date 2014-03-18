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
 * Filename: InvalidColumnNameInCustomResultException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents the error of using different column names in custom
 *              queries than in the declared custom results, or the table names
 *              themselves.
 *
 * Date: 2014/03/18
 * Time: 10:36
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.Sql;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the error of using different column names in custom queries than in the
 * declared custom results, or the table names themselves.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/18 10:36
 */
@ThreadSafe
public class InvalidColumnNameInCustomResultException
    extends QueryJNonCheckedException
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 960685626915260054L;

    /**
     * Creates a new instance with given context.
     * @param property the {@link Property property}.
     * @param sql the {@link Sql}.
     * @param sqlResult the {@link Result result}.
     * @param cause the cause.
     */
    public InvalidColumnNameInCustomResultException(
        @NotNull final Property<String> property,
        @NotNull final Sql<String> sql,
        @Nullable final Result<String> sqlResult,
        @NotNull final Throwable cause)
    {
        super(
            (sqlResult != null) ? "invalid.column.name.in.custom.result" : "invalid.column.name.in.custom.query",
            new String[] { property.getColumnName(), sql.getId(), (sqlResult != null) ? sqlResult.getId() : "" },
            cause);
    }
}
