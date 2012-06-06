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
 * Filename: CustomSqlProviderSqlParameterDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: CustomSqlProvider-backed SqlParameterDAO implementation.
 *
 * Date: 6/6/12
 * Time: 8:31 AM
 *
 */
package org.acmsl.queryj.tools.customsql;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.SqlParameterDAO;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * {@link CustomSqlProvider}-backed {@link SqlParameterDAO} implementation.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
public class CustomSqlProviderSqlParameterDAO
    implements SqlParameterDAO
{
    /**
     * The {@link CustomSqlProvider} instance.
     */
    private CustomSqlProvider m__CustomSqlProvider;

    /**
     * Creates a new {@link CustomSqlProviderSqlDAO} with given {@link CustomSqlProvider}.
     * @param provider the {@link CustomSqlProvider} instance.
     */
    public CustomSqlProviderSqlParameterDAO(@NotNull final CustomSqlProvider provider)
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
     * Retrieves the {@link org.acmsl.queryj.tools.customsql.Parameter} associated to given id.
     *
     * @param id the parameter id.
     * @return the {@link org.acmsl.queryj.tools.customsql.Parameter}, or <code>null</code> if not found.
     */
    @Override
    @Nullable
    public Parameter findByPrimaryKey(@NotNull final String id)
    {
        // TODO
        return null;
    }

    /**
     * Retrieves all {@link org.acmsl.queryj.tools.customsql.Parameter parameters} used in given {@link org.acmsl.queryj.tools.customsql.Sql}.
     *
     * @param sqlId the {@link org.acmsl.queryj.tools.customsql.Sql} identifier.
     * @return the list of parameters required by given {@link org.acmsl.queryj.tools.customsql.Sql}.
     */
    @NotNull
    @Override
    public List<Parameter> findBySql(@NotNull final String sqlId)
    {
        // TODO
        return new ArrayList<Parameter>(0);
    }
}
