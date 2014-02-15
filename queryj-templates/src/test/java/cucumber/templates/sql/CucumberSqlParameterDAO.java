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
 * Filename: CucumberSqlParameterDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Cucumber-specific SqlParameterDAO implementation.
 *
 * Date: 6/26/13
 * Time: 5:21 AM
 *
 */
package cucumber.templates.sql;

/*
 * Importing QueryJ-core classes.
 */
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.metadata.SqlParameterDAO;

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
import java.util.Map;
import java.util.Map.Entry;

/**
 * Cucumber-specific {@link SqlParameterDAO} implementation.
 * @author <a href="mailto:jose.sanleandro@ventura24.es">Jose San Leandro</a>
 * @since 2013/06/26
 */
public class CucumberSqlParameterDAO
    implements SqlParameterDAO
{
    /**
     * The map of sqlId -> parameters.
     */
    private Map<String, List<Parameter<String, ?>>> m__mParameters;

    /**
     * Creates a DAO instance wrapping given map.
     * @param parameters such map.
     */
    public CucumberSqlParameterDAO(@NotNull final Map<String, List<Parameter<String, ?>>> parameters)
    {
        immutableSetParameters(parameters);
    }

    /**
     * Specifies the SQL queries' parameters.
     * @param parameters such map.
     */
    protected final void immutableSetParameters(@NotNull final Map<String, List<Parameter<String, ?>>> parameters)
    {
        this.m__mParameters = parameters;
    }

    /**
     * Specifies the SQL queries' parameters.
     * @param parameters such map.
     */
    @SuppressWarnings("unused")
    protected void setParameters(@NotNull final Map<String, List<Parameter<String, ?>>> parameters)
    {
        immutableSetParameters(parameters);
    }

    /**
     * Retrieves the SQL queries parameters.
     * @return such parameters.
     */
    protected final Map<String, List<Parameter<String, ?>>> getParameters()
    {
        return m__mParameters;
    }

    /**
     * Retrieves the {@link Parameter} associated to given id.
     * @param id the parameter id.
     * @return the {@link Parameter}, or <code>null</code> if not found.
     */
    @Nullable
    @Override
    public Parameter<String, ?> findByPrimaryKey(@NotNull final String id)
    {
        return findByPrimaryKey(id, getParameters());
    }

    /**
     * Retrieves the {@link Parameter} associated to given id.
     * @param id the parameter id.
     * @param parameters the parameter map.
     * @return the {@link Parameter}, or <code>null</code> if not found.
     */
    @Nullable
    protected Parameter<String, ?> findByPrimaryKey(
        @NotNull final String id, @NotNull final Map<String, List<Parameter<String, ?>>> parameters)
    {
        @Nullable Parameter<String, ?> result = null;

        for (@NotNull final Entry<String, List<Parameter<String, ?>>> entry : parameters.entrySet())
        {
            for (@Nullable final Parameter<String, ?> parameter : entry.getValue())
            {
                if (   (parameter != null)
                    && (parameter.getId().equals(id)))
                {
                    result = parameter;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Retrieves all {@link Parameter parameters} used in given {@link org.acmsl.queryj
     * .customsql.Sql}.
     * @param sqlId the {@link org.acmsl.queryj.customsql.Sql} identifier.
     * @return the list of parameters required by given {@link org.acmsl.queryj.customsql.Sql}.
     */
    @NotNull
    @Override
    public List<Parameter<String, ?>> findBySql(@NotNull final String sqlId)
    {
        return findBySql(sqlId, getParameters());
    }

    /**
     * Retrieves all {@link Parameter parameters} used in given {@link org.acmsl.queryj
     * .customsql.Sql}.
     * @param sqlId the {@link org.acmsl.queryj.customsql.Sql} identifier.
     * @param parameters the parameter map.
     * @return the list of parameters required by given {@link org.acmsl.queryj.customsql.Sql}.
     */
    @NotNull
    protected List<Parameter<String, ?>> findBySql(
        @NotNull final String sqlId, final Map<String, List<Parameter<String, ?>>> parameters)
    {
        @Nullable List<Parameter<String, ?>> result = null;

        for (@NotNull final Entry<String, List<Parameter<String, ?>>> entry : parameters.entrySet())
        {
            @NotNull final String key = entry.getKey();

            if (key.equals(sqlId))
            {
                result = entry.getValue();
                break;
            }
        }

        if (result == null)
        {
            result = new ArrayList<>(0);
        }

        return result;
    }

    @Override
    public String toString()
    {
        return "CucumberSqlParameterDAO{" +
               " parameters=" + m__mParameters +
               '}';
    }
}
