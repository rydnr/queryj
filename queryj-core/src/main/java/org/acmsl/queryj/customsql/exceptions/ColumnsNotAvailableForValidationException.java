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
 * Filename: ColumnsNotAvailableForValidationException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents the error when trying to access the list of columns
 *              (represented as properties extracted from the ResultSet, before
 *              the handler flow has actually extracted them.
 *
 * Date: 2014/03/16
 * Time: 11:22
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
 * Represents the error when trying to access the list of columns (represented as
 * {@link org.acmsl.queryj.customsql.Property properties}) extracted from the {@link java.sql.ResultSet}, before
 * the handler flow has actually extracted them.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/16 11:22
 */
@ThreadSafe
public class ColumnsNotAvailableForValidationException
    extends QueryJNonCheckedException
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 5850876452607223594L;

    /**
     * Creates an instance using given {@link Sql} as context.
     * @param sql the SQL.
     */
    public ColumnsNotAvailableForValidationException(@NotNull final Sql<String> sql)
    {
        super("columns.not.available.for.validation", new String[] { sql.getId() });
    }
}
