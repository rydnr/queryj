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
 * Filename: SqlXmlParserSqlDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: SqlXmlParser-backed SqlDAO implementation.
 *
 * Date: 6/6/12
 * Time: 8:24 AM
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.xml.AbstractSqlXmlParserDAO;
import org.acmsl.queryj.customsql.xml.SqlXmlParser;
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
 * {@link SqlXmlParser}-backed {@link SqlDAO} implementation.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
public class SqlXmlParserSqlDAO
    extends AbstractSqlXmlParserDAO
    implements SqlDAO
{
    /**
     * Creates a new {@link SqlXmlParser} with given {@link SqlXmlParser}.
     * @param parser the {@link SqlXmlParser} instance.
     */
    public SqlXmlParserSqlDAO(@NotNull final SqlXmlParser parser)
    {
        super(parser);
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
        return findByPrimaryKey(id, getSqlXmlParser());
    }

    /**
     * Retrieves all {@link SqlElement SQL elements}.
     * @param parser the parser.
     * @return such list.
     */
    @Nullable
    protected Sql findByPrimaryKey(@NotNull final String id, @NotNull final SqlXmlParser parser)
    {
        @Nullable Sql result = null;

        Sql t_Sql;

        for (@Nullable Object t_Item : parser.getCollection())
        {
            if  (t_Item instanceof Sql)
            {
                t_Sql = (Sql) t_Item;

                if (id.equals(t_Sql.getId()))
                {
                    result = t_Sql;
                    break;
                }
            }
        }

        return result;
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
        return filterTable(table, findAll(), CustomResultUtils.getInstance());
    }

    /**
     * Retrieves all <i>selects-for-update</i> for a given DAO (table).
     * @param table the table.
     * @return all matching <i>selects-for-update</i> queries.
     */
    @NotNull
    @Override
    public List<Sql> findSelects(@NotNull final String table)
    {
        return filterSelects(findByDAO(table));
    }

    @Override
    public List<Sql> findDynamic(@NotNull final String tableName)
    {
        return filterDynamic(findByDAO(tableName));
    }

    /**
     * Filters which queries are dynamic or not.
     * @param queries the queries.
     * @return the dynamic ones.
     */
    @NotNull
    protected List<Sql> filterDynamic(@NotNull final List<Sql> queries)
    {
        @NotNull List<Sql> result = new ArrayList<Sql>();

        for (@Nullable Sql t_Sql : queries)
        {
            if (   (t_Sql != null)
                && (t_Sql.isDynamic()))
            {
                result.add(t_Sql);
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
    public List<Sql> findSelectsForUpdate(@NotNull final String table)
    {
        return filterSelectsForUpdate(findByDAO(table));
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
        return filterInserts(findByDAO(table));
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
        return filterUpdates(findByDAO(table));
    }

    /**
     * Retrieves all <i>delete</i> {@link Sql sentences} for a given DAO (table).
     * @param table the table.
     * @return all matching <i>delete</i> sentences.
     */
    @NotNull
    @Override
    public List<Sql> findDeletes(@NotNull final String table)
    {
        return filterDeletes(findByDAO(table));
    }

    /**
     * Retrieves all <i>delete</i> {@link Sql sentences} for a given DAO (table).
     * @param table the table.
     * @param queries the complete list of queries.
     * @param customResultUtils the {@link CustomResultUtils} instance.
     * @return all matching <i>delete</i> sentences.
     */
    @NotNull
    protected List<Sql> filterTable(
        @NotNull final String table,
        @NotNull final List<Sql> queries,
        @NotNull final CustomResultUtils customResultUtils)
    {
        @NotNull List<Sql> result = new ArrayList<Sql>();

        String t_strDAO;

        for (@Nullable Sql t_Sql : queries)
        {
            if  (t_Sql != null)
            {
                t_strDAO = t_Sql.getDao();

                if (   (t_strDAO != null)
                    && (customResultUtils.matches(table, t_strDAO)))
                {
                    result.add(t_Sql);
                }
            }
        }

        return result;
    }

    /**
     * Filters given {@link Sql} list to get only the <i>delete</i> ones.
     * @param list the list of queries.
     * @return the delete-only ones.
     */
    @NotNull
    protected List<Sql> filterDeletes(@NotNull final List<Sql> list)
    {
        return filterByType(list, Sql.DELETE);
    }

    /**
     * Filters given {@link Sql} list to get only the <i>update</i> ones.
     * @param list the list of queries.
     * @return the update-only ones.
     */
    @NotNull
    protected List<Sql> filterUpdates(@NotNull final List<Sql> list)
    {
        return filterByType(list, Sql.UPDATE);
    }

    /**
     * Filters given {@link Sql} list to get only the <i>insert</i> ones.
     * @param list the list of queries.
     * @return the insert-only ones.
     */
    @NotNull
    protected List<Sql> filterInserts(@NotNull final List<Sql> list)
    {
        return filterByType(list, Sql.INSERT);
    }

    /**
     * Filters given {@link Sql} list to get only the <i>select</i> ones.
     * @param list the list of queries.
     * @return the select-only ones.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected List<Sql> filterSelects(@NotNull final List<Sql> list)
    {
        return filterByType(list, Sql.SELECT);
    }

    /**
     * Filters given {@link Sql} list to get only the <i>select-for-update</i> ones.
     * @param list the list of queries.
     * @return the select-for-update-only ones.
     */
    @NotNull
    protected List<Sql> filterSelectsForUpdate(@NotNull final List<Sql> list)
    {
        return filterByType(list, Sql.SELECT_FOR_UPDATE);
    }

    /**
     * Filters given {@link Sql} list to get only the ones matching given type.
     * @param list the list of queries.
     * @return the matching ones.
     */
    @NotNull
    protected List<Sql> filterByType(@NotNull final List<Sql> list, @NotNull final String type)
    {
        @NotNull List<Sql> result = new ArrayList<Sql>();

        for (@Nullable Sql t_Sql : list)
        {
            if  (   (t_Sql != null)
                 && (type.equalsIgnoreCase(t_Sql.getType())))
            {
                result.add(t_Sql);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public List<Sql> findAll()
    {
        return findAll(getSqlXmlParser());
    }

    /**
     * Retrieves all {@link SqlElement SQL elements}.
     * @param parser the parser.
     * @return such list.
     */
    @NotNull
    protected List<Sql> findAll(@NotNull final SqlXmlParser parser)
    {
        @NotNull List<Sql> result = new ArrayList<Sql>();

        for (@Nullable Object t_Item : parser.getCollection())
        {
            if  (t_Item instanceof Sql)
            {
                result.add((Sql) t_Item);
            }
        }

        return result;
    }

    /**
     * Retrieves all SQL matching given result id.
     *
     * @param resultId the result id.
     * @return the list of matching {@link org.acmsl.queryj.customsql.Sql}.
     */
    @NotNull
    @Override
    public List<Sql> findByResultId(@NotNull final String resultId)
    {
        @NotNull List<Sql> result = new ArrayList<Sql>();

        for (@Nullable Sql t_CurrentSql : findAll())
        {
            @Nullable ResultRef t_ResultRef;

            if  (t_CurrentSql != null)
            {
                t_ResultRef = t_CurrentSql.getResultRef();

                if (   (t_ResultRef != null)
                    && (resultId.equals(t_ResultRef.getId())))
                {
                    result.add(t_CurrentSql);
                }
            }
        }

        return result;
    }

    /**
     * Retrieves all SQL matching given type.
     *
     * @param type the type.
     * @return the list of matching {@link org.acmsl.queryj.customsql.Sql}.
     */
    @NotNull
    @Override
    public List<Sql> findByType(@NotNull final String type)
    {
        return filterByType(findAll(), type);
    }

    /**
     * Retrieves all repository-scoped SQL.
     * @return such list.
     */
    @NotNull
    @Override
    public List<Sql> findAllRepositoryScopedSql()
    {
        return findAllRepositoryScopedSql(false);
    }

    /**
     * Retrieves all repository-scoped SQL.
     * @return such list.
     */
    @NotNull
    protected List<Sql> findAllRepositoryScopedSql(final boolean breakAtFirstOccurrence)
    {
        @NotNull List<Sql> result = new ArrayList<Sql>(0);

        for (@Nullable Sql t_Sql : findAll())
        {
            if (t_Sql != null)
            {
                if (t_Sql.getRepositoryScope() != null)
                {
                    result.add(t_Sql);
                    if (breakAtFirstOccurrence)
                    {
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Retrieves all repository-scoped SQL.
     * @return such list.
     */
    @Override
    public boolean containsRepositoryScopedSql()
    {
        @NotNull List<Sql> result = findAllRepositoryScopedSql(true);

        return result.size() > 0;
    }
}
