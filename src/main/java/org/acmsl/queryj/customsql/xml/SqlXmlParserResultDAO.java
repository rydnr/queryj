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
        return this.findById(id, Result.class);
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

        Sql t_Sql;
        String t_strDAO = null;

        for (@Nullable Result t_Result : findAll())
        {
            if (t_Result != null)
            {
                if (t_Result.getMatches().equals(type))
                {
                    t_Sql = findSqlByResultId(t_Result.getId());

                    if (t_Sql != null)
                    {
                        t_strDAO = t_Sql.getDao();
                    }

                    if (   (t_strDAO != null)
                        && (matches(table, t_strDAO, englishGrammarUtils)))
                    {
                        result = t_Result;
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
    @Nullable
    protected Sql findSqlByResultId(@NotNull final String id)
    {
        // TODO
        return null;
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
        // TODO: need a way to retrieve a SqlDAO.
        return null;
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
        return this.findAll(getSqlXmlParser(), Result.class);

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
