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
 * Filename: SqlDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Provides methods to access Sql information.
 *
 * Date: 6/6/12
 * Time: 5:54 AM
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.Sql;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.dao.DAO;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * Provides methods to access {@link Sql} information.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
public interface SqlDAO
    extends DAO
{
    /**
     * Finds a given {@link Sql} by its id.
     * @param id the id.
     * @return the associated {@link Sql}, or <code>null</code> if not found.
     */
    @Nullable
    Sql findByPrimaryKey(@NotNull final String id);

    /**
     * Retrieves all {@link Sql} associated to a given DAO (table).
     * @param table the table.
     * @return the associated {@link Sql} instances.
     */
    @NotNull
    List<Sql> findByDAO(@NotNull final String table);

    /**
     * Retrieves all <i>selects-for-update</i> for a given DAO (table).
     * @param table the table.
     * @return all matching <i>selects-for-update</i> queries.
     */
    @NotNull
    List<Sql> findSelectsForUpdate(@NotNull final String table);

    /**
     * Retrieves all <i>insert</i> {@link Sql sentences} for a given DAO (table).
     * @param table the table.
     * @return all matching <i>insert</i> sentences.
     */
    @NotNull
    List<Sql> findInserts(@NotNull final String table);

    /**
     * Retrieves all <i>update</i> {@link Sql sentences} for a given DAO (table).
     * @param table the table.
     * @return all matching <i>update</i> sentences.
     */
    @NotNull
    List<Sql> findUpdates(@NotNull final String table);

    /**
     * Retrieves all <i>delete</i> {@link Sql sentences} for a given DAO (table).
     * @param table the table.
     * @return all matching <i>delete</i> sentences.
     */
    @NotNull
    List<Sql> findDeletes(@NotNull final String table);

    /**
     * Retrieves all custom SQL.
     * @return such list.
     */
    @NotNull
    List<Sql> findAll();

    /**
     * Retrieves all SQL matching given result id.
     * @param resultId the result id.
     * @return the list of matching {@link Sql}.
     */
    @NotNull
    List<Sql> findByResultId(@NotNull final String resultId);

    /**
     * Retrieves all SQL matching given type.
     * @param type the type.
     * @return the list of matching {@link Sql}.
     */
    @NotNull
    List<Sql> findByType(@NotNull final String type);

    /**
     * Retrieves all repository-scoped SQL.
     * @return such list.
     */
    @NotNull
    List<Sql> findAllRepositoryScopedSql();

    /**
     * Checks whether it contains repository-scoped SQL or not.
     * @return <code>true</code> in such case.
     */
    boolean containsRepositoryScopedSql();

    /**
     * Retrieves all plain selects.
     * @param table the name of the table.
     * @return such list.
     */
    @NotNull
    List<Sql> findSelects(@NotNull final String table);

    /**
     * Retrieves all dynamic queries.
     * @param tableName the name of the table.
     * @return such list.
     */
    List<Sql> findDynamic(@NotNull final String tableName);
}