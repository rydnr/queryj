/*
                        QueryJ Test

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
 * Filename: CucumberSqlDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Cucumber-specific {@link SqlDAO} implementation.
 *
 * Date: 6/24/13
 * Time: 8:43 PM
 *
 */
package org.acmsl.queryj.test.sql;

/*
 * Importing QueryJ-core classes.
 */
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.customsql.ResultRef;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.metadata.SqlDAO;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Cucumber-specific {@link SqlDAO} implementation.
 * @author <a href="mailto:jose.sanleandro@ventura24.es">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/06/24
 */
public class CucumberSqlDAO
    implements SqlDAO
{
    /**
     * The data table.
     */
    private List<Sql<String>> m__lSql;

    /**
     * Creates an instance with given information.
     * @param sqlList such information.
     */
    public CucumberSqlDAO(@NotNull final List<Sql<String>> sqlList)
    {
        immutableSetSqlList(sqlList);
    }

    /**
     * Specifies the {@link Sql} list.
     * @param sqlList such list.
     */
    protected final void immutableSetSqlList(@NotNull final List<Sql<String>> sqlList)
    {
        this.m__lSql = sqlList;
    }

    /**
     * Specifies the {@link Sql} list.
     * @param sqlList such list.
     */
    @SuppressWarnings("unused")
    protected void setSqlList(@NotNull final List<Sql<String>> sqlList)
    {
        immutableSetSqlList(sqlList);
    }

    /**
     * Retrieves the list of {@link Sql}.
     * @return such table.
     */
    protected List<Sql<String>> getSqlList()
    {
        return this.m__lSql;
    }

    /**
     * Checks whether it contains repository-scoped SQL or not.
     * @return <code>true</code> in such case.
     */
    @Override
    public boolean containsRepositoryScopedSql()
    {
        return containsRepositoryScopedSql(getSqlList());
    }

    /**
     * Checks whether it contains repository-scoped SQL or not.
     * @param sqlList the list of {@link Sql} queries.
     * @return <code>true</code> in such case.
     */
    protected boolean containsRepositoryScopedSql(@NotNull final List<Sql<String>> sqlList)
    {
        boolean result = false;

        for (@NotNull final Sql<String> sql : sqlList)
        {
            if (sql.getDao() == null)
            {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Finds a given {@link Sql} by its id.
     * @param id the id.
     * @return the associated {@link Sql}, or <code>null</code> if not found.
     */
    @Nullable
    @Override
    public Sql<String> findByPrimaryKey(@NotNull final String id)
    {
        return findByPrimaryKey(id, getSqlList());
    }

    /**
     * Finds a given {@link Sql} by its id.
     * @param id the id.
     * @param sqlList the list of {@link Sql} queries.
     * @return the associated {@link Sql}, or <code>null</code> if not found.
     */
    @Nullable
    public Sql<String> findByPrimaryKey(@NotNull final String id, @NotNull final List<Sql<String>> sqlList)
    {
        @Nullable Sql<String> result = null;

        for (@NotNull final Sql<String> sql : sqlList)
        {
            if (id.equals(sql.getId()))
            {
                result = sql;
                break;
            }
        }

        return result;

    }

    /**
     * Retrieves all {@link Sql} associated to a given DAO (table).
     * @param table the table.
     * @return the associated {@link Sql} instances.
     */
    @NotNull
    @Override
    public List<Sql<String>> findByDAO(@NotNull final String table)
    {
        return findByDAO(table, getSqlList());
    }

    /**
     * Retrieves all {@link Sql} associated to a given DAO (table).
     * @param table the table.
     * @param sqlList the list of {@link Sql} queries.
     * @return the associated {@link Sql} instances.
     */
    @NotNull
    protected List<Sql<String>> findByDAO(@NotNull final String table, @NotNull final List<Sql<String>> sqlList)
    {
        @NotNull final List<Sql<String>> result = new ArrayList<>();

        for (@NotNull final Sql<String> sql : sqlList)
        {
            if (table.toUpperCase(QueryJSettings.DEFAULT_LOCALE)
                    .equals(sql.getDao().toUpperCase(QueryJSettings.DEFAULT_LOCALE)))
            {
                result.add(sql);
            }
        }

        return result;
    }

    /**
     * Retrieves all <i>selects-for-update</i> for a given DAO (table).
     * @param table the table.
     * @return all matching <i>selects-for-update</i> queries.
     */
    @NotNull
    @Override
    public List<Sql<String>> findSelectsForUpdate(@NotNull final String table)
    {
        return findByDAO(table, findByType(Sql.SELECT_FOR_UPDATE));
    }

    /**
     * Retrieves all <i>insert</i> {@link org.acmsl.queryj.customsql.Sql sentences} for a given DAO (table).
     *
     * @param table the table.
     * @return all matching <i>insert</i> sentences.
     */
    @NotNull
    @Override
    public List<Sql<String>> findInserts(@NotNull final String table)
    {
        return findByDAO(table, findByType(Sql.INSERT));
    }

    /**
     * Retrieves all <i>update</i> {@link org.acmsl.queryj.customsql.Sql sentences} for a given DAO (table).
     *
     * @param table the table.
     * @return all matching <i>update</i> sentences.
     */
    @NotNull
    @Override
    public List<Sql<String>> findUpdates(@NotNull final String table)
    {
        return findByDAO(table, findByType(Sql.UPDATE));
    }

    /**
     * Retrieves all <i>delete</i> {@link org.acmsl.queryj.customsql.Sql sentences} for a given DAO (table).
     *
     * @param table the table.
     * @return all matching <i>delete</i> sentences.
     */
    @NotNull
    @Override
    public List<Sql<String>> findDeletes(@NotNull final String table)
    {
        return findByDAO(table, findByType(Sql.DELETE));
    }

    /**
     * Retrieves all custom SQL.
     *
     * @return such list.
     */
    @NotNull
    @Override
    public List<Sql<String>> findAll()
    {
        return getSqlList();
    }

    /**
     * Retrieves all SQL matching given result id.
     * @param resultId the result id.
     * @return the list of matching {@link Sql}.
     */
    @NotNull
    @Override
    public List<Sql<String>> findByResultId(@NotNull final String resultId)
    {
        return findByResultId(resultId, getSqlList());
    }

    /**
     * Retrieves all SQL matching given result id.
     * @param resultId the result id.
     * @param sqlList the list of {@link Sql}.
     * @return the list of matching {@link Sql}.
     */
    @NotNull
    protected List<Sql<String>> findByResultId(
        @NotNull final String resultId, @NotNull final List<Sql<String>> sqlList)
    {
        @NotNull final List<Sql<String>> result = new ArrayList<>();

        for (@NotNull final Sql<String> sql : sqlList)
        {
            @Nullable final ResultRef resultRef = sql.getResultRef();

            if (   (resultRef != null)
                && (resultRef.getId().equals(resultId)))
            {
                result.add(sql);
            }
        }

        return result;
    }

    /**
     * Retrieves all SQL matching given type.
     * @param type the type.
     * @return the list of matching {@link Sql}.
     */
    @NotNull
    @Override
    public List<Sql<String>> findByType(@NotNull final String type)
    {
        return findByType(type, getSqlList());
    }

    /**
     * Retrieves all SQL matching given type.
     * @param type the type.
     * @param sqlList the list of {@link Sql} queries.
     * @return the list of matching {@link Sql}.
     */
    @NotNull
    protected List<Sql<String>> findByType(@NotNull final String type, @NotNull final List<Sql<String>> sqlList)
    {
        @NotNull final List<Sql<String>> result = new ArrayList<>();

        for (@NotNull final Sql<String> sql : sqlList)
        {
            if (type.equals(sql.getType()))
            {
                result.add(sql);
            }
        }

        return result;
    }

    /**
     * Retrieves all repository-scoped SQL.
     * @return such list.
     */
    @NotNull
    @Override
    public List<Sql<String>> findAllRepositoryScopedSql()
    {
        return findAllRepositoryScopedSql(getSqlList());
    }

    /**
     * Retrieves all repository-scoped SQL.
     * @param sqlList the list of {@link Sql} queries.
     * @return such list.
     */
    @NotNull
    protected List<Sql<String>> findAllRepositoryScopedSql(@NotNull final List<Sql<String>> sqlList)
    {
        @NotNull final List<Sql<String>> result = new ArrayList<>();

        for (@NotNull final Sql<String> sql : sqlList)
        {
            if (sql.getDao() == null)
            {
                result.add(sql);
            }
        }

        return result;
    }

    /**
     * Retrieves all plain selects.
     * @param table the name of the table.
     * @return such list.
     */
    @NotNull
    @Override
    public List<Sql<String>> findSelects(@NotNull final String table)
    {
        return findByDAO(table, findByType(Sql.SELECT));
    }

    /**
     * Retrieves all dynamic queries.
     * @param tableName the name of the table.
     * @return such list.
     */
    @Override
    @NotNull
    public List<Sql<String>> findDynamic(@NotNull final String tableName)
    {
        return findByDAO(tableName, findDynamic(getSqlList()));
    }

    /**
     * Retrieves all dynamic queries.
     * @param sqlList the list of {@link Sql} queries.
     * @return such list.
     */
    protected List<Sql<String>> findDynamic(@NotNull final List<Sql<String>> sqlList)
    {
        @NotNull final List<Sql<String>> result = new ArrayList<>();

        for (@NotNull final Sql<String> sql : sqlList)
        {
            if (sql.isDynamic())
            {
                result.add(sql);
            }
        }

        return result;
    }

    @Override
    public String toString()
    {
        return "CucumberSqlDAO{" +
               "sqlList=" + m__lSql +
               '}';
    }
}
