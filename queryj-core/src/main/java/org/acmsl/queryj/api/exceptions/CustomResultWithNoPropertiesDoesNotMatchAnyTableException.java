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
 * Filename: CustomResultWithNoPropertiesDoesNotMatchAnyTableException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Exception to represent a custom result with no properties
 *              does not match any table either.
 *
 * Date: 2018/08/03
 * Time: 18:15
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.customsql.Result;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Exception to represent a custom result with no properties
 * does not match any table either.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/08/03
 */
@ThreadSafe
@SuppressWarnings("unused")
public class CustomResultWithNoPropertiesDoesNotMatchAnyTableException
    extends QueryJNonCheckedException
{
    private static final long serialVersionUID = 5008246553975491960L;

    /**
     * Creates the exception with given custom result.
     * @param customResult the custom result.
     */
    public CustomResultWithNoPropertiesDoesNotMatchAnyTableException(
        @NotNull final Result<?> customResult)
    {
        super(
            "custom-result.with.no.properties.does.not.match.any.table",
            new Object[] { customResult.getId() });
    }
}
