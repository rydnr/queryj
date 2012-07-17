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
import org.acmsl.commons.logging.UniqueLogFactory;
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
import java.util.List;

/**
 * {@link SqlXmlParser}-backed {@link SqlResultDAO} implementation.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
public class SqlXmlParserResultDAO
    extends AbstractSqlXmlParserDAO
    implements SqlResultDAO
{
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
    public Result findByPrimaryKey(@NotNull final String id)
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
    protected Result findByPrimaryKey(@NotNull final String id, @NotNull final SqlXmlParser parser)
    {
        return findById(id, Result.class, parser.getResults());
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.customsql.Result} for single matches of a given VO (table).
     * @param table the table.
     * @return the single-match {@link Result result}.
     */
    @Override
    @Nullable
    public Result findSingleMatch(@NotNull final String table)
    {
        return findMatch(table, Result.SINGLE, EnglishGrammarUtils.getInstance());
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
        return findMatch(table, Result.MULTIPLE, EnglishGrammarUtils.getInstance());
    }

    /**
     * Retrieves the {@link Result} for multiple matches of a given VO (table).
     * @param table the table.
     * @return the multiple-match {@link Result result}.
     */
    @Nullable
    public Result findMatch(
        @NotNull final String table,
        @NotNull final String type,
        @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        @Nullable Result result = null;

        @Nullable List<Sql> t_lSql;
        @Nullable String t_strDAO = null;

        boolean t_bBreak = false;

        for (@Nullable Result t_Result : findAll())
        {
            if (t_Result != null)
            {
                if (t_Result.getMatches().equals(type))
                {
                    t_lSql = findSqlByResultId(t_Result.getId());

                    for (@Nullable Sql t_Sql : t_lSql)
                    {
                        if (t_Sql != null)
                        {
                            t_strDAO = t_Sql.getDao();
                        }

                        if ("g_cycle_metadata".equals(t_strDAO))
                        {
                            UniqueLogFactory.getLog(SqlXmlParserResultDAO.class).debug("caught");
                        }
                        if (   (t_strDAO != null)
                            && (matches(table, t_strDAO, englishGrammarUtils)))
                        {
                            result = t_Result;
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

        return result;
    }

    /**
     * Finds the sql associated to given result id.
     * @param id the result id.
     * @return the associated {@link Sql}.
     */
    @NotNull
    protected List<Sql> findSqlByResultId(@NotNull final String id)
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
    protected List<Sql> findSqlByResultId(@NotNull final String id, @NotNull final SqlXmlParser sqlXmlParser)
    {
        @Nullable List<Sql> result = new ArrayList<Sql>(2);

        @Nullable Result t_CustomResult = findByPrimaryKey(id);
        @Nullable ResultRef t_ResultRef;

        if (t_CustomResult != null)
        {
            for (@Nullable Sql t_Sql : sqlXmlParser.getQueries())
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
    public Result findBySqlId(@NotNull final String sqlId)
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
    protected Result findBySqlId(@NotNull final String sqlId, @NotNull final SqlXmlParser sqlXmlParser)
    {
        @Nullable Result result = null;

        @Nullable ResultRef t_ResultRef;

        for (@Nullable Sql t_Sql : sqlXmlParser.getQueries())
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

        String t_strTableInLowerCase = tableName.trim().toLowerCase();

        result = daoId.equalsIgnoreCase(t_strTableInLowerCase);

        if  (!result)
        {
            String t_strSingularName =
                englishGrammarUtils.getSingular(t_strTableInLowerCase);

            result = daoId.equalsIgnoreCase(t_strSingularName);
        }

        if  (!result)
        {
            String t_strPluralName =
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
    public List<Result> findAll()
    {
        return findAll(getSqlXmlParser());

    }

    /**
     * Retrieves all {@link Result Results}
     * @param parser the {@link SqlXmlParser} instance.
     * @return such list.
     */
    @NotNull
    protected List<Result> findAll(@NotNull final SqlXmlParser parser)
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
    @Override
    public List<Result> findByType(@NotNull final String type)
    {
        return filterItems(findAll(), Result.class, type);
    }
}
