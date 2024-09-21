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
 * Filename: InvalidPerCustomSqlTemplateException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents an error processing a custom sql template.
 *
 * Date: 6/15/13
 * Time: 7:33 AM
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
 * Represents an error processing a custom sql template.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/15
 */
@ThreadSafe
public class InvalidPerCustomSqlTemplateException
    extends InvalidTemplateException
{
    private static final long serialVersionUID = 5502277545457875468L;

    /**
     * Creates a new exception with given context.
     * @param name the template name.
     * @param sql the {@link Sql} instance.
     * @param repository the repository name.
     * @param cause the actual error.
     */
    public InvalidPerCustomSqlTemplateException(
        @NotNull final String name,
        @NotNull final Sql<?> sql,
        @NotNull final String repository,
        @NotNull final Throwable cause)
    {
        super(
            "invalid.per.custom-sql.template",
            new Object[] { name, repository, sql.getId() },
            cause);
    }
}
