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
 * Filename: NoValidationValueForCustomSqlDateParameterException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents the error when a Date parameter in a SQL sentence
 *              does not provide a validation value.
 *
 * Date: 6/12/13
 * Time: 8:08 PM
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.customsql.Parameter;
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
 * Represents the error when a Date parameter in a SQL sentence does not provide a validation value.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/12
 */
@ThreadSafe
public class NoValidationValueForCustomSqlDateParameterException
    extends QueryJBuildException
{
    private static final long serialVersionUID = -5562245141819216883L;

    /**
     * Creates an instance, indicating the index of the invalid parameter and
     * the enclosing {@link org.acmsl.queryj.customsql.Sql}.
     * @param parameter the parameter.
     * @param sql the Sql.
     */
    public NoValidationValueForCustomSqlDateParameterException(
        @NotNull final Parameter<?, ?> parameter, @NotNull final Sql<?> sql)
    {
        super(
            "no.validation.value.specified.for.date.parameter.in.custom-sql",
            new Object[] { parameter.getId(), sql.getId() });
    }
}
