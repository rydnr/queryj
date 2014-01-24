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
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.Result;
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
 * Represents the error when the type of a property in a custom result is invalid or not supported.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/12
 */
@ThreadSafe
public class UnsupportedCustomResultPropertyTypeException
    extends QueryJBuildException
{
    private static final long serialVersionUID = -7066638500035915220L;

    /**
     * Creates an instance, indicating the index of the property whose type is not supported, and
     * the enclosing {@link Result}.
     * @param property the property.
     * @param result the result.
     * @param sql the Sql.
     * @param cause the cause.
     */
    public UnsupportedCustomResultPropertyTypeException(
        @NotNull final Property<?> property,
        @NotNull final Result<?> result,
        @NotNull final Sql<?> sql,
        @NotNull final Throwable cause)
    {
        super(
            "unsupported.property.type.in.custom-result",
            new Object[] { property.getType(), property.getId(), result.getId(), sql.getId() },
            cause);
    }

}
