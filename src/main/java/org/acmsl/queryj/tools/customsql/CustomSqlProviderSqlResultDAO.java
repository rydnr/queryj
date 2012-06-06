package org.acmsl.queryj.tools.customsql;/*
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
 * Filename: CustomSqlProviderSqlResultDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 6/6/12
 * Time: 8:34 AM
 *
 */

import org.acmsl.queryj.tools.metadata.SqlParameterDAO;
import org.acmsl.queryj.tools.metadata.SqlResultDAO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * {@link CustomSqlProvider}-backed {@link SqlResultDAO} implementation.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
public class CustomSqlProviderSqlResultDAO
    implements SqlResultDAO
{
    /**
     * The {@link CustomSqlProvider} instance.
     */
    private CustomSqlProvider m__CustomSqlProvider;

    /**
     * Creates a new {@link CustomSqlProviderSqlResultDAO} with given {@link CustomSqlProvider}.
     * @param provider the {@link CustomSqlProvider} instance.
     */
    public CustomSqlProviderSqlResultDAO(@NotNull final CustomSqlProvider provider)
    {
        immutableSetCustomSqlProvider(provider);
    }

    /**
     * Specifies the {@link CustomSqlProvider} instance.
     * @param provider such {@link CustomSqlProvider instance}.
     */
    protected final void immutableSetCustomSqlProvider(@NotNull final CustomSqlProvider provider)
    {
        m__CustomSqlProvider = provider;
    }

    /**
     * Specifies the {@link CustomSqlProvider} instance.
     * @param provider such {@link CustomSqlProvider instance}.
     */
    @SuppressWarnings("unused")
    protected void setCustomSqlProvider(@NotNull final CustomSqlProvider provider)
    {
        immutableSetCustomSqlProvider(provider);
    }

    /**
     * Retrieves the {@link CustomSqlProvider} instance.
     * @return the underlying {@link CustomSqlProvider instance}.
     */
    @NotNull
    protected CustomSqlProvider getCustomSqlProvider()
    {
        return m__CustomSqlProvider;
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.tools.customsql.Result} associated to given id.
     * @param id the result id.
     * @return the associated {@link Result result}.
     */
    @Override
    @Nullable
    public Result findByPrimaryKey(@NotNull final String id)
    {
        // TODO
        return null;
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.tools.customsql.Result} for single matches of a given VO (table).
     * @param table the table.
     * @return the single-match {@link Result result}.
     */
    @Override
    @Nullable
    public Result findSingleMatch(@NotNull final String table)
    {
        // TODO
        return null;
    }

    /**
     * Retrieves the {@link Result} for multiple matches of a given VO (table).
     * @param table the table.
     * @return the multiple-match {@link Result result}.
     */
    @Override
    @Nullable
    public Result findMultipleMatch(@NotNull final String table)
    {
        // TODO
        return null;
    }

    /**
     * Retrieves the {@link Result} for given {@link Sql sql} id.
     * @param sqlId the identifier.
     * @return the associated {@link Result}, or <code>null</code> if not found.
     */
    @Override
    @Nullable
    public Result findBySqlId(@NotNull final String sqlId)
    {
        // TODO
        return null;
    }
}
