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
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.xml.AbstractSqlXmlParserDAO;
import org.acmsl.queryj.customsql.xml.SqlXmlParser;
import org.acmsl.queryj.metadata.MetadataUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * {@link SqlXmlParser}-backed {@link SqlDAO} implementation.
 * @author <a href="mailto:queryj@acm-sl.org">chous</a>
 * @since 3.0
 * Created: 2012/06/06
 */
@ThreadSafe
public class SqlXmlParserSqlDAO
    extends AbstractSqlXmlParserDAO
    implements SqlDAO
{
    /**
     * A cache of id -&gt; Sql
     */
    private static final Map<String, Sql<String>> FIND_BY_PRIMARY_KEY_CACHE = new HashMap<>();

    /**
     * The cache misses.
     */
    private static final Map<String, Boolean> PRIMARY_KEY_MISS_CACHE = new HashMap<>();

    /**
     * The cache of resultId -&gt; List&lt;Sql&lt;String&gt;&gt;
     */
    private static final Map<String, List<Sql<String>>> FIND_BY_RESULTID_CACHE = new HashMap<>();

    /**
     * The cache of type -&gt; List&lt;Sql&lt;String&gt;&gt;
     */
    private static final Map<String, List<Sql<String>>> FIND_BY_TYPE_CACHE = new HashMap<>();

    /**
     * The cached repository-scoped Sql.
     */
    private static List<Sql<String>> m__lCachedRepositoryScopedSql;

    /**
     * Creates a new {@link SqlXmlParser} with given {@link SqlXmlParser}.
     * @param parser the {@link SqlXmlParser} instance.
     */
    public SqlXmlParserSqlDAO(@NotNull final SqlXmlParser parser)
    {
        super(parser);
    }

    /**
     * Retrieves the {@link Sql} from the cache, if possible.
     * @param id the id.
     * @return the {@link Sql}, or <code>null</code> if not found in the cache.
     */
    protected synchronized Sql<String> getFromPrimaryKeyCache(@NotNull final String id)
    {
        return FIND_BY_PRIMARY_KEY_CACHE.get(id);
    }

    /**
     * Caches given {@link Sql} in the cache.
     * @param id the id.
     * @param sql the {@link Sql} to cache.
     */
    protected synchronized void cachePrimaryKey(@NotNull final String id, @NotNull final Sql<String> sql)
    {
        FIND_BY_PRIMARY_KEY_CACHE.put(id, sql);
    }

    /**
     * Specifies that given primary key was already retrieved, with no success.
     * @param id the id to annotate.
     */
    protected synchronized void setPrimaryKeyAlreadyRetrieved(@NotNull final String id)
    {
        PRIMARY_KEY_MISS_CACHE.put(id, Boolean.TRUE);
    }

    /**
     * Checks whether given primary key was already retrieved or not.
     * @param id the id to check.
     * @return {@code true} in such case.
     */
    protected synchronized boolean isPrimaryKeyAlreadyRetrieved(@NotNull final String id)
    {
        boolean result = false;

        final Boolean miss = PRIMARY_KEY_MISS_CACHE.get(id);

        if (miss != null)
        {
            result = miss;
        }

        return result;
    }
    /**
     * Finds a given {@link Sql} by its id.
     * @param id the id.
     * @return the associated {@link Sql}, or <code>null</code> if not found.
     */
    @Override
    @Nullable
    public Sql<String> findByPrimaryKey(@NotNull final String id)
    {
        Sql<String> result = getFromPrimaryKeyCache(id);

        if (   (result == null)
            && (!isPrimaryKeyAlreadyRetrieved(id)))
        {
            result = findByPrimaryKey(id, getSqlXmlParser());

            if (result != null)
            {
                cachePrimaryKey(id, result);
            }
            else
            {
                setPrimaryKeyAlreadyRetrieved(id);
            }
        }

        return result;
    }

    /**
     * Retrieves one specific {@link SqlElement SQL element}.
     * @param id the id to filter.
     * @param parser the parser.
     * @return such list.
     */
    @Nullable
    protected Sql<String> findByPrimaryKey(@NotNull final String id, @NotNull final SqlXmlParser parser)
    {
        return findById(id, Sql.class, parser.getQueries());
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
        return filterTable(table, findAll(), MetadataUtils.getInstance());
    }

    /**
     * Retrieves all <i>selects-for-update</i> for a given DAO (table).
     * @param table the table.                                                     I
     * @return all matching <i>selects-for-update</i> queries.
     */
    @NotNull
    @Override
    public List<Sql<String>> findSelects(@NotNull final String table)
    {
        return filterSelects(findByDAO(table));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sql<String>> findDynamic(@NotNull final String tableName)
    {
        return filterDynamic(findByDAO(tableName));
    }

    /**
     * Filters which queries are dynamic or not.
     * @param queries the queries.
     * @return the dynamic ones.
     */
    @NotNull
    protected List<Sql<String>> filterDynamic(@NotNull final List<Sql<String>> queries)
    {
        @NotNull final List<Sql<String>> result = new ArrayList<>();

        for (@Nullable final Sql<String> t_Sql : queries)
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
    public List<Sql<String>> findSelectsForUpdate(@NotNull final String table)
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
    public List<Sql<String>> findInserts(@NotNull final String table)
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
    public List<Sql<String>> findUpdates(@NotNull final String table)
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
    public List<Sql<String>> findDeletes(@NotNull final String table)
    {
        return filterDeletes(findByDAO(table));
    }

    /**
     * Retrieves all <i>delete</i> {@link Sql sentences} for a given DAO (table).
     * @param table the table.
     * @param queries the complete list of queries.
     * @param metadataUtils the {@link MetadataUtils} instance.
     * @return all matching <i>delete</i> sentences.
     */
    @NotNull
    protected List<Sql<String>> filterTable(
        @NotNull final String table,
        @NotNull final List<Sql<String>> queries,
        @NotNull final MetadataUtils metadataUtils)
    {
        @NotNull final List<Sql<String>> result = new ArrayList<>();

        String t_strDAO;

        for (@Nullable final Sql<String> t_Sql : queries)
        {
            if  (t_Sql != null)
            {
                t_strDAO = t_Sql.getDao();

                if (   (t_strDAO != null)
                    && (metadataUtils.matches(table, t_strDAO)))
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
    protected List<Sql<String>> filterDeletes(@NotNull final List<Sql<String>> list)
    {
        return filterByType(list, Sql.DELETE);
    }

    /**
     * Filters given {@link Sql} list to get only the <i>update</i> ones.
     * @param list the list of queries.
     * @return the update-only ones.
     */
    @NotNull
    protected List<Sql<String>> filterUpdates(@NotNull final List<Sql<String>> list)
    {
        return filterByType(list, Sql.UPDATE);
    }

    /**
     * Filters given {@link Sql} list to get only the <i>insert</i> ones.
     * @param list the list of queries.
     * @return the insert-only ones.
     */
    @NotNull
    protected List<Sql<String>> filterInserts(@NotNull final List<Sql<String>> list)
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
    protected List<Sql<String>> filterSelects(@NotNull final List<Sql<String>> list)
    {
        return filterByType(list, Sql.SELECT);
    }

    /**
     * Filters given {@link Sql} list to get only the <i>select-for-update</i> ones.
     * @param list the list of queries.
     * @return the select-for-update-only ones.
     */
    @NotNull
    protected List<Sql<String>> filterSelectsForUpdate(@NotNull final List<Sql<String>> list)
    {
        return filterByType(list, Sql.SELECT_FOR_UPDATE);
    }

    /**
     * Filters given {@link Sql} list to get only the ones matching given type.
     * @param list the list of queries.
     * @param type the type.
     * @return the matching ones.
     */
    @NotNull
    protected List<Sql<String>> filterByType(@NotNull final List<Sql<String>> list, @NotNull final String type)
    {
        @NotNull final List<Sql<String>> result = new ArrayList<>();

        for (@Nullable final Sql<String> t_Sql : list)
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
    public List<Sql<String>> findAll()
    {
        return findAll(getSqlXmlParser());
    }

    /**
     * Retrieves all {@link SqlElement SQL elements}.
     * @param parser the parser.
     * @return such list.
     */
    @NotNull
    protected List<Sql<String>> findAll(@NotNull final SqlXmlParser parser)
    {
        return parser.getQueries();
    }

    /**
     * Retrieves the list of matching {@link Sql} for given result id, from a local cache.
     * @param resultId the {@link Result} identifier.
     * @return the list of matching {@link Sql}, or <code>null</code> if not found in the cache.
     */
    @Nullable
    protected synchronized List<Sql<String>> getFromResultIdCache(@NotNull final String resultId)
    {
        return FIND_BY_RESULTID_CACHE.get(resultId);
    }

    /**
     * Annotates the matching {@link Sql}s for given result id in a local cache.
     * @param resultId the result id.
     * @param list the {@link Sql} list.
     */
    protected synchronized void cacheResultId(@NotNull final String resultId, @NotNull final List<Sql<String>> list)
    {
        FIND_BY_RESULTID_CACHE.put(resultId, list);
    }

    /**
     * Retrieves all SQL matching given result id.
     *
     * @param resultId the result id.
     * @return the list of matching {@link org.acmsl.queryj.customsql.Sql}.
     */
    @NotNull
    @Override
    public List<Sql<String>> findByResultId(@NotNull final String resultId)
    {
        List<Sql<String>> result = getFromResultIdCache(resultId);

        if (result == null)
        {
            result = findByResultId(resultId, findAll());
            cacheResultId(resultId, result);
        }

        return result;
    }

    /**
     * Retrieves all SQL matching given result id.
     * @param resultId the result id.
     * @param list the list of {@link Sql} to process.
     * @return the list of matching {@link org.acmsl.queryj.customsql.Sql}.
     */
    @NotNull
    public List<Sql<String>> findByResultId(@NotNull final String resultId, @NotNull final List<Sql<String>> list)
    {

        @NotNull final List<Sql<String>> result = new ArrayList<>();

        for (@Nullable final Sql<String> t_CurrentSql : list)
        {
            @Nullable final ResultRef t_ResultRef;

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
     * Retrieves the list of matching {@link Sql} for given type.
     * @param type the type.
     * @return the cached list of matching {@link Sql} items.
     */
    protected synchronized List<Sql<String>> getFromTypeCache(@NotNull final String type)
    {
        return FIND_BY_TYPE_CACHE.get(type);
    }

    /**
     * Annotates given list of {@link Sql} matching given type, in a local cache.
     * @param type the type.
     * @param list the matching {@link Sql} list.
     */
    protected synchronized void cacheType(@NotNull final String type, @NotNull final List<Sql<String>> list)
    {
        FIND_BY_TYPE_CACHE.put(type, list);
    }

    /**
     * Retrieves all SQL matching given type.
     *
     * @param type the type.
     * @return the list of matching {@link org.acmsl.queryj.customsql.Sql}.
     */
    @NotNull
    @Override
    public List<Sql<String>> findByType(@NotNull final String type)
    {
        List<Sql<String>> result = getFromTypeCache(type);

        if (result == null)
        {
            result = findByType(type, findAll());
            cacheType(type, result);
        }

        return result;
    }

    /**
     * Retrieves all SQL matching given type.
     * @param type the type.
     * @param list the list of {@link Sql} elements.
     * @return the list of matching {@link org.acmsl.queryj.customsql.Sql}.
     */
    @NotNull
    public List<Sql<String>> findByType(@NotNull final String type, @NotNull final List<Sql<String>> list)
    {
        return filterByType(list, type);
    }

    /**
     * Specifies the cached repository-scoped {@link Sql} items.
     * @param list such list.
     */
    protected synchronized static void setCachedRepositoryScopedSql(@NotNull final List<Sql<String>> list)
    {
        m__lCachedRepositoryScopedSql = list;
    }

    /**
     * Retrieves the cached repository-scoped {@link Sql} items.
     * @return such list.
     */
    @Nullable
    protected synchronized static List<Sql<String>> getCachedRepositoryScopedSql()
    {
        return m__lCachedRepositoryScopedSql;
    }

    /**
     * Retrieves all repository-scoped SQL.
     * @return such list.
     */
    @NotNull
    @Override
    public List<Sql<String>> findAllRepositoryScopedSql()
    {
        List<Sql<String>> result = getCachedRepositoryScopedSql();

        if (result == null)
        {
            result = findAllRepositoryScopedSql(false);
            setCachedRepositoryScopedSql(result);
        }

        return result;
    }

    /**
     * Retrieves all repository-scoped SQL.
     * @param breakAtFirstOccurrence whether to return just the first match.
     * @return such list.
     */
    @NotNull
    protected List<Sql<String>> findAllRepositoryScopedSql(final boolean breakAtFirstOccurrence)
    {
        @NotNull final List<Sql<String>> result = new ArrayList<>(0);

        for (@Nullable final Sql<String> t_Sql : findAll())
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
        @NotNull final List<Sql<String>> result = findAllRepositoryScopedSql();

        return result.size() > 0;
    }
}
