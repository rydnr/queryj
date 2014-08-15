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
package org.acmsl.queryj.customsql.xml;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultRef;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.metadata.SqlResultDAO;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;

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
import java.util.Locale;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * {@link SqlXmlParser}-backed {@link SqlResultDAO} implementation.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
@ThreadSafe
public class SqlXmlParserResultDAO
    extends AbstractSqlXmlParserDAO
    implements SqlResultDAO
{
    /**
     * The map of results by table/DAO.
     */
    private static final Map<String, Result<String>> CACHED_RESULT_BY_TABLE = new HashMap<>();
    private static final Map<String, Boolean> UNMATCHED_TABLES = new HashMap<>(1);

    /**
     * Creates a new {@link SqlXmlParserResultDAO} with given {@link SqlXmlParser}.
     * @param parser the {@link SqlXmlParser} instance.
     */
    public SqlXmlParserResultDAO(@NotNull final SqlXmlParser parser)
    {
        super(parser);
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.customsql.Result} associated to given id.
     * @param id the result id.
     * @return the associated {@link org.acmsl.queryj.customsql.Result result}.
     */
    @Override
    @Nullable
    public Result<String> findByPrimaryKey(@NotNull final String id)
    {
        return findByPrimaryKey(id, getSqlXmlParser());
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.customsql.Result} associated to given id.
     * @param id the result id.
     * @param parser the {@link SqlXmlParser} instance.
     * @return the associated {@link org.acmsl.queryj.customsql.Result result}.
     */
    @Nullable
    protected Result<String> findByPrimaryKey(@NotNull final String id, @NotNull final SqlXmlParser parser)
    {
        return findById(id, Result.class, parser.getResults());
    }

    /**
     * Retrieves the {@link Result} for multiple matches of a given VO (table).
     * @param table the table.
     * @return the multiple-match {@link Result result}.
     */
    @Override
    @Nullable
    public Result<String> findByTable(@NotNull final String table)
    {
        return findByTable(table, EnglishGrammarUtils.getInstance());
    }
    /**
     * Retrieves the {@link Result} for multiple matches of a given VO (table).
     * @param table the table.
     * @param englishGrammarUtils the {@link EnglishGrammarUtils} instance.
     * @return the multiple-match {@link Result result}.
     */
    @Nullable
    protected Result<String> findByTable(
        @NotNull final String table,
        @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        @Nullable final Result<String> result;

        @Nullable Result<String> aux = null;

        if (!knownToBeNotFound(table))
        {
            @Nullable final Result<String> t_CachedResult = getCachedResult(table);

            if (t_CachedResult == null)
            {
                @Nullable List<Sql<String>> t_lSql;
                @Nullable String t_strDAO = null;

                boolean t_bBreak = false;

                for (@Nullable final Result<String> t_Result : findAll())
                {
                    if (t_Result != null)
                    {
                        t_lSql = findSqlByResultId(t_Result.getId());

                        for (@Nullable final Sql<String> t_Sql : t_lSql)
                        {
                            if (t_Sql != null)
                            {
                                t_strDAO = t_Sql.getDao();
                            }

                            if (   (t_strDAO != null)
                                && (matches(table, t_strDAO, englishGrammarUtils)))
                            {
                                aux = t_Result;
                                t_bBreak = true;
                                break;
                            }
                        }

                        if (t_bBreak)
                        {
                            break;
                        }
                    }
                }
            }
            else
            {
                aux = t_CachedResult;
            }
        }

        result = aux;

        if (result != null)
        {
            cacheResult(table, result);
        }
        else
        {
            cacheNonMatch(table);
        }
        return result;
    }

    /**
     * Checks whether given table is known not to be associated to any {@link Result}.
     * @param table the table.
     * @return <code>true</code> in such case.
     */
    protected boolean knownToBeNotFound(@NotNull final String table)
    {
        boolean result = false;

        if (UNMATCHED_TABLES.get(table.toLowerCase(Locale.US)) != null)
        {
            result = true;
        }

        return result;
    }

    /**
     * Annotates given table doesn't have an associated {@link Result}.
     * @param table the table.
     */
    protected void cacheNonMatch(@NotNull final String table)
    {
        UNMATCHED_TABLES.put(table.toLowerCase(Locale.US), Boolean.TRUE);
    }

    /**
     * Retrieves the cached results for given table.
     * @param table the table.
     * @return the cached results.
     */
    @Nullable
    protected synchronized Result<String> getCachedResult(@NotNull final String table)
    {
        return CACHED_RESULT_BY_TABLE.get(table.toLowerCase(Locale.US));
    }

    /**
     * Caches given results, associated to given table.
     * @param table the table.
     * @param result the result.
     */
    protected synchronized void cacheResult(@NotNull final String table, @NotNull final Result<String> result)
    {
        @Nullable final Result<String> t_CurrentMatch = CACHED_RESULT_BY_TABLE.get(table.toLowerCase(Locale.US));

        if (t_CurrentMatch != null)
        {
            CACHED_RESULT_BY_TABLE.put(table.toLowerCase(Locale.US), t_CurrentMatch);
        }
        else
        {
            CACHED_RESULT_BY_TABLE.put(table.toLowerCase(Locale.US), result);
        }
    }

    /**
     * Finds the sql associated to given result id.
     * @param id the result id.
     * @return the associated {@link Sql}.
     */
    @NotNull
    protected List<Sql<String>> findSqlByResultId(@NotNull final String id)
    {
        return findSqlByResultId(id, getSqlXmlParser());
    }

    /**
     * Finds the sql associated to given result id.
     * @param id the result id.
     * @param sqlXmlParser the {@link SqlXmlParser} instance.
     * @return the associated {@link Sql}.
     */
    @NotNull
    protected List<Sql<String>> findSqlByResultId(@NotNull final String id, @NotNull final SqlXmlParser sqlXmlParser)
    {
        @Nullable final List<Sql<String>> result = new ArrayList<>(2);

        @Nullable final Result<String> t_CustomResult = findByPrimaryKey(id);
        @Nullable ResultRef t_ResultRef;

        if (t_CustomResult != null)
        {
            for (@Nullable final Sql<String> t_Sql : sqlXmlParser.getQueries())
            {
                if (t_Sql != null)
                {
                    t_ResultRef = t_Sql.getResultRef();

                    if (   (t_ResultRef != null)
                        && (id.equalsIgnoreCase(t_ResultRef.getId())))
                    {
                        result.add(t_Sql);
                    }
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the {@link Result} for given {@link org.acmsl.queryj.customsql.Sql sql} id.
     * @param sqlId the identifier.
     * @return the associated {@link Result}, or <code>null</code> if not found.
     */
    @Override
    @Nullable
    public Result<String> findBySqlId(@NotNull final String sqlId)
    {
        return findBySqlId(sqlId, getSqlXmlParser());
    }

    /**
     * Retrieves the {@link Result} for given {@link org.acmsl.queryj.customsql.Sql sql} id.
     * @param sqlId the identifier.
     * @param sqlXmlParser the {@link SqlXmlParser} instance.
     * @return the associated {@link Result}, or <code>null</code> if not found.
     */
    @Nullable
    protected Result<String> findBySqlId(@NotNull final String sqlId, @NotNull final SqlXmlParser sqlXmlParser)
    {
        @Nullable Result<String> result = null;

        @Nullable ResultRef t_ResultRef;

        for (@Nullable final Sql<String> t_Sql : sqlXmlParser.getQueries())
        {
            if (t_Sql != null)
            {
                if (sqlId.equalsIgnoreCase(t_Sql.getId()))
                {
                    t_ResultRef = t_Sql.getResultRef();

                    if (t_ResultRef != null)
                    {
                        result = findByPrimaryKey(t_ResultRef.getId());
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Checks whether given table name matches the DAO id.
     * @param tableName the table name.
     * @param daoId the DAO id.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @return <code>true</code> if they match.
     */
    protected boolean matches(
        @NotNull final String tableName,
        @NotNull final String daoId,
        @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        boolean result;

        @NotNull final String t_strTableInLowerCase = tableName.trim().toLowerCase();

        result = daoId.equalsIgnoreCase(t_strTableInLowerCase);

        if  (!result)
        {
            @NotNull final String t_strSingularName =
                englishGrammarUtils.getSingular(t_strTableInLowerCase);

            result = daoId.equalsIgnoreCase(t_strSingularName);
        }

        if  (!result)
        {
            @NotNull final String t_strPluralName =
                englishGrammarUtils.getPlural(t_strTableInLowerCase);

            result = daoId.equalsIgnoreCase(t_strPluralName);
        }

        return result;
    }

    /**
     * Retrieves all {@link Result Results}
     * @return such list.
     */
    @Override
    @NotNull
    public List<Result<String>> findAll()
    {
        return findAll(getSqlXmlParser());

    }

    /**
     * Retrieves all {@link Result Results}
     * @param parser the {@link SqlXmlParser} instance.
     * @return such list.
     */
    @NotNull
    protected List<Result<String>> findAll(@NotNull final SqlXmlParser parser)
    {
        return parser.getResults();

    }

    /**
     * Retrieves the {@link org.acmsl.queryj.customsql.Result results} matching given type.
     *
     * @param type the type.
     * @return the list of matching {@link Result} instances.
     */
    @NotNull
    public List<Result<String>> findByType(@NotNull final String type)
    {
        return filterItems(findAll(), Result.class, type);
    }
}
