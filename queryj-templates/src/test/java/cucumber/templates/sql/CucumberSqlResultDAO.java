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
 * Filename: CucumberSqlResultDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: A SqlResultDAO implementation wrapping a single Result
 *              instance.
 *
 * Date: 6/25/13
 * Time: 6:02 AM
 *
 */
package cucumber.templates.sql;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.SqlResultDAO;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.templates.antlr.JavaParser.NonWildcardTypeArgumentsContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * A {@link SqlResultDAO} implementation wrapping a single {@link Result} instance.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/25
 */
public class CucumberSqlResultDAO
    implements SqlResultDAO
{
    /**
     * The wrapped result.
     */
    private Result<String> m__CustomResult;

    /**
     * Creates a DAO wrapping given result.
     * @param result such result.
     */
    public CucumberSqlResultDAO(@NotNull final Result<String> result)
    {
        immutableSetResult(result);
    }

    /**
     * Specifies the custom result.
     * @param result such result.
     */
    protected final void immutableSetResult(@NotNull final Result<String> result)
    {
        this.m__CustomResult = result;
    }

    /**
     * Specifies the custom result.
     * @param result such result.
     */
    @SuppressWarnings("unused")
    protected void setResult(@NotNull final Result<String> result)
    {
        immutableSetResult(result);
    }

    /**
     * Retrieves the custom result.
     * @return such result.
     */
    @NotNull
    protected Result<String> getResult()
    {
        return m__CustomResult;
    }

    /**
     * Retrieves the {@link Result} matching given id.
     * @param id the result id.
     * @return such result.
     */
    @Nullable
    @Override
    public Result<String> findByPrimaryKey(@NotNull final String id)
    {
        return findByPrimaryKey(id, getResult());
    }

    /**
     * Retrieves the {@link Result} matching given id.
     * @param id the result id.
     * @param customResult the custom result.
     * @return such result.
     */
    @Nullable
    protected Result<String> findByPrimaryKey(@NotNull final String id, @NotNull final Result<String> customResult)
    {
        @Nullable Result<String> result = null;

        if (id.equals(customResult.getId()))
        {
            result = customResult;
        }

        return result;
    }

    @Nullable
    @Override
    public Result<String> findBySqlId(@NotNull final String sqlId)
    {
        return null;
    }

    /**
     * Finds the {@link Result} of given table.
     * @param table the table.
     * @return the result.
     */
    @NotNull
    @Override
    public Result<String> findByTable(@NotNull final String table)
    {
        return findByTable(table, getResult());
    }

    /**
     * Finds nte {@link Result} of given type.
     * @param table the table name.
     * @param customResult the custom result.
     * @return the result.
     */
    @NotNull
    protected Result<String> findByTable(@NotNull final String table, @NotNull final Result<String> customResult)
    {
        @Nullable final Result<String> result;

        if (table.equals(customResult.getClassValue()))
        {
            result = customResult;
        }
        else
        {
            result = null;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<Result<String>> findAll()
    {
        return findAll(getResult());
    }

    /**
     * Retrieves all custom {@link Result results}.
     * @param customResult the wrapped result.
     * @return a list of just that element.
     */
    @NotNull
    protected List<Result<String>> findAll(@NotNull final Result<String> customResult)
    {
        @NotNull final List<Result<String>> result = new ArrayList<>(1);

        result.add(customResult);

        return result;
    }

    @Override
    public String toString()
    {
        return "CucumberSqlResultDAO{" +
               "customResult=" + m__CustomResult +
               '}';
    }
}
