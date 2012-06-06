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
 * Filename: SqlResultDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Provides methods to access Result information.
 *
 * Date: 6/6/12
 * Time: 6:00 AM
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.customsql.Result;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.dao.DAO;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Provides methods to access {@link Result} information.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
public interface SqlResultDAO
    extends DAO
{
    /**
     * Retrieves the {@link Result} associated to given id.
     * @param id the result id.
     * @return the associated {@link Result result}.
     */
    @Nullable
    Result findByPrimaryKey(@NotNull final String id);

    /**
     * Retrieves the {@link Result} for single matches of a given VO (table).
     * @param table the table.
     * @return the single-match {@link Result result}.
     */
    @Nullable
    Result findSingleMatch(@NotNull final String table);

    /**
     * Retrieves the {@link Result} for multiple matches of a given VO (table).
     * @param table the table.
     * @return the multiple-match {@link Result result}.
     */
    @Nullable
    Result findMultipleMatch(@NotNull final String table);

    /**
     * Retrieves the {@link Result} for given {@link org.acmsl.queryj.tools.customsql.Sql sql} id.
     * @param sqlId the identifier.
     * @return the associated {@link Result}, or <code>null</code> if not found.
     */
    @Nullable
    Result findBySqlId(@NotNull final String sqlId);
}
