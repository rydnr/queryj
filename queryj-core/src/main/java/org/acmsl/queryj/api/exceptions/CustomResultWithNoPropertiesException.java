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
 * Filename: CustomResultWithNoPropertiesException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents the error when the Result used by a Sql, contains no
 *              properties.
 *
 * Date: 6/12/13
 * Time: 9:56 PM
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.Sql;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents the error when the {@link Result} used by a {@link Sql}, contains no
 * {@link org.acmsl.queryj.customsql.Property properties}.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/12
 */
@ThreadSafe
public class CustomResultWithNoPropertiesException
    extends QueryJBuildException
{
    private static final long serialVersionUID = 5320900310750401486L;

    /**
     * Creates an instance, indicating the {@link Result}, used by given {@link Sql}, contains no
     * {@link org.acmsl.queryj.customsql.Property properties}.
     * @param result the custom result.
     * @param sql the Sql.
     */
    public CustomResultWithNoPropertiesException(@NotNull final Result result, @NotNull final Sql sql)
    {
        super("custom-result.with.no.properties", new Object[] { result.getId(), sql.getId() });
    }
}
