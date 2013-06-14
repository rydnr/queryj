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
 * Filename: UnsupportedCustomSqlParameterTypeException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents the error when the type of a parameter in a SQL
 *              sentence is not supported.
 *
 * Date: 6/12/13
 * Time: 8:02 PM
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing project classes.
 */
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
 * Represents the error when the type of a parameter in a SQL sentence is not supported.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/12
 */
@ThreadSafe
public class UnsupportedCustomSqlParameterTypeException
    extends QueryJBuildException
{
    private static final long serialVersionUID = 5391865024131873532L;

    /**
     * Creates an instance, indicating the index of the parameter whose type is not supported, and
     * the enclosing {@link Sql}.
     * @param type the parameter type.
     * @param parameterIndex the parameter index.
     * @param sql the Sql.
     */
    public UnsupportedCustomSqlParameterTypeException(
        @NotNull final String type,
        final int parameterIndex,
        @NotNull final Sql sql)
    {
        super(
            "unsupported.parameter.type.in.custom-sql",
            new Object[] { type, parameterIndex, sql.getId() });
    }

    /**
     * Creates an instance, indicating the index of the parameter whose type is not supported, and
     * the enclosing {@link Sql}.
     * @param type the parameter type.
     * @param parameterIndex the parameter index.
     * @param sql the Sql.
     * @param cause the cause.
     */
    public UnsupportedCustomSqlParameterTypeException(
        @NotNull final String type,
        final int parameterIndex,
        @NotNull final Sql sql,
        @NotNull final Throwable cause)
    {
        super(
            "unsupported.parameter.type.in.custom-sql",
            new Object[] { type, parameterIndex, sql.getId() },
            cause);
    }
}
