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
 * Filename: ReferencedResultNotFoundException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents the situation in which a referenced result in a custom SQL is not found.
 *
 * Date: 6/11/13
 * Time: 7:50 PM
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.customsql.ResultRef;
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
 * Represents the situation in which a referenced result in a custom SQL is not found.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/11
 */
@ThreadSafe
public class ReferencedResultNotFoundException
    extends QueryJBuildException
{
    private static final long serialVersionUID = -7480481268251696770L;

    /**
     * Creates an instance with given context.
     * @param resultRef the {@link ResultRef}.
     * @param sql the {@link Sql}.
     */
    public ReferencedResultNotFoundException(@NotNull final ResultRef resultRef, @NotNull final Sql sql)
    {
        super("referenced.result.not.found", new Object[] { resultRef.getId(), sql.getId() } );
    }
}
