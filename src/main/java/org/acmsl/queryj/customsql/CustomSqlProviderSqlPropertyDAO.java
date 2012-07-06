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
 * Filename: CustomSqlProviderSqlPropertyDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: CustomSqlProvider-backed SqlPropertyDAO implementation.
 *
 * Date: 6/7/12
 * Time: 8:31 AM
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.metadata.SqlPropertyDAO;


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
 * {@link org.acmsl.queryj.customsql.CustomSqlProvider}-backed {@link org.acmsl.queryj.metadata.SqlPropertyDAO}
 * implementation.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/07/06
 */
public class CustomSqlProviderSqlPropertyDAO
    implements SqlPropertyDAO
{
    /**
     * The {@link org.acmsl.queryj.customsql.CustomSqlProvider} instance.
     */
    private CustomSqlProvider m__CustomSqlProvider;

    /**
     * Creates a new {@link CustomSqlProviderSqlPropertyDAO}
     * with given {@link CustomSqlProvider}.
     * @param provider the {@link CustomSqlProvider} instance.
     */
    public CustomSqlProviderSqlPropertyDAO(@NotNull final CustomSqlProvider provider)
    {
        immutableSetCustomSqlProvider(provider);
    }

    /**
     * Specifies the {@link org.acmsl.queryj.customsql.CustomSqlProvider} instance.
     * @param provider such {@link org.acmsl.queryj.customsql.CustomSqlProvider instance}.
     */
    protected final void immutableSetCustomSqlProvider(@NotNull final CustomSqlProvider provider)
    {
        m__CustomSqlProvider = provider;
    }

    /**
     * Specifies the {@link org.acmsl.queryj.customsql.CustomSqlProvider} instance.
     * @param provider such {@link org.acmsl.queryj.customsql.CustomSqlProvider instance}.
     */
    @SuppressWarnings("unused")
    protected void setCustomSqlProvider(@NotNull final CustomSqlProvider provider)
    {
        immutableSetCustomSqlProvider(provider);
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.customsql.CustomSqlProvider} instance.
     * @return the underlying {@link org.acmsl.queryj.customsql.CustomSqlProvider instance}.
     */
    @NotNull
    protected CustomSqlProvider getCustomSqlProvider()
    {
        return m__CustomSqlProvider;
    }

    /**
     * Retrieves the {@link Property} associated to given id.
     *
     * @param id the property id.
     * @return the {@link Property}, or <code>null</code> if not found.
     */
    @Override
    @Nullable
    public Property findByPrimaryKey(@NotNull final String id)
    {
        return findByPrimaryKey(id, getCustomSqlProvider());
    }

    /**
     * Retrieves the {@link Property} associated to given id.
     *
     * @param id the property id.
     * @return the {@link Property}, or <code>null</code> if not found.
     */
    @Nullable
    public Property findByPrimaryKey(@NotNull final String id, @NotNull final CustomSqlProvider customSqlProvider)
    {
        return customSqlProvider.resolvePropertyReference(id);
    }

    /**
     * Retrieves all {@link Property properties} used in given {@link Result}.
     *
     * @param resultId the {@link Result} identifier.
     * @return the list of properties associated to given {@link Result}.
     */
    @NotNull
    @Override
    public List<Property> findByResult(@NotNull final String resultId)
    {
        // TODO
        return new ArrayList<Property>(0);
    }
}
