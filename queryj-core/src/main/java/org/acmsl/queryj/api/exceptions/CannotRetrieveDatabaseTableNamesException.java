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
 * Filename: CannotRetrieveDatabaseTableNamesException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents the error when QueryJ cannot retrieve the table
 *              names.
 *
 * Date: 6/13/13
 * Time: 9:48 PM
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents the error when QueryJ cannot retrieve the table names.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/13
 */
@ThreadSafe
public class CannotRetrieveDatabaseTableNamesException
    extends QueryJBuildException
{
    private static final long serialVersionUID = 2214270355986726760L;

    /**
     * Creates an instance with given information.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param cause the underlying exception.
     */
    public CannotRetrieveDatabaseTableNamesException(
        @Nullable final String catalog, @Nullable final String schema, @NotNull final Throwable cause)
    {
        super("cannot.retrieve.database.table.names", new Object[] { catalog, schema }, cause);
    }
}
