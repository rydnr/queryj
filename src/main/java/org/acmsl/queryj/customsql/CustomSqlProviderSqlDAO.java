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
 * Filename: CustomSqlProviderSqlDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: CustomSqlProvider-backed SqlDAO implementation.
 *
 * Date: 6/6/12
 * Time: 8:24 AM
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.metadata.SqlDAO;

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
 * {@link CustomSqlProvider}-backed {@link SqlDAO} implementation.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
public class CustomSqlProviderSqlDAO
    implements SqlDAO
{
    /**
     * The {@link CustomSqlProvider} instance.
     */
    private CustomSqlProvider m__CustomSqlProvider;

    /**
     * Creates a new {@link CustomSqlProviderSqlDAO} with given {@link CustomSqlProvider}.
     * @param provider the {@link CustomSqlProvider} instance.
     */
    public CustomSqlProviderSqlDAO(@NotNull final CustomSqlProvider provider)
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
     * Finds a given {@link Sql} by its id.
     * @param id the id.
     * @return the associated {@link Sql}, or <code>null</code> if not found.
     */
    @Override
    @Nullable
    public Sql findByPrimaryKey(@NotNull final String id)
    {
        // TODO
        return null;
    }

    /**
     * Retrieves all {@link Sql} associated to a given DAO (table).
     *
     * @param table the table.
     * @return the associated {@link Sql} instances.
     */
    @NotNull
    @Override
    public List<Sql> findByDAO(@NotNull final String table)
    {
        // TODO
        return new ArrayList<Sql>(0);
    }

    /**
     * Retrieves all <i>selects-for-update</i> for a given DAO (table).
     * @param table the table.
     * @return all matching <i>selects-for-update</i> queries.
     */
    @NotNull
    @Override
    public List<Sql> findSelectsForUpdate(@NotNull final String table)
    {
        // TODO
        return new ArrayList<Sql>(0);
    }

    /**
     * Retrieves all <i>insert</i> {@link Sql sentences} for a given DAO (table).
     * @param table the table.
     * @return all matching <i>insert</i> sentences.
     */
    @NotNull
    @Override
    public List<Sql> findInserts(@NotNull final String table)
    {
        // TODO
        return new ArrayList<Sql>(0);
    }

    /**
     * Retrieves all <i>update</i> {@link Sql sentences} for a given DAO (table).
     * @param table the table.
     * @return all matching <i>update</i> sentences.
     */
    @NotNull
    @Override
    public List<Sql> findUpdates(@NotNull final String table)
    {
        // TODO
        return new ArrayList<Sql>(0);
    }

    /**
     * Retrieves all <i>delete</i> {@link Sql sentences} for a given DAO (table).
     *
     * @param table the table.
     * @return all matching <i>delete</i> sentences.
     */
    @NotNull
    @Override
    public List<Sql> findDeletes(@NotNull final String table)
    {
        // TODO
        return new ArrayList<Sql>(0);
    }
}
