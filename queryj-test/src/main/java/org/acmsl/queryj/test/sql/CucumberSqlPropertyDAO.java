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
 * Filename: CucumberSqlPropertyDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Minimal SqlPropertyDAO implementation on top of a list of
 *              properties and a given Result.
 *
 * Date: 6/25/13
 * Time: 6:16 AM
 *
 */
package org.acmsl.queryj.test.sql;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyElement;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.SqlPropertyDAO;

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
 * Minimal {@link SqlPropertyDAO} implementation on top of a list of {@link Property properties}
 * and a given {@link Result}.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/06/25
 */
@SuppressWarnings("unused")
public class CucumberSqlPropertyDAO
    implements SqlPropertyDAO
{
    /**
     * The list of properties.
     */
    private List<Property<String>> m__Properties;

    /**
     * The custom result.
     */
    private Result<String> m__CustomResult;

    /**
     * Creates an instance with given properties and result.
     * @param properties the properties.
     * @param result the result.
     */
    @SuppressWarnings("unused")
    public CucumberSqlPropertyDAO(
        @NotNull final List<Property<String>> properties, @NotNull final Result<String> result)
    {
        immutableSetProperties(properties);
        immutableSetResult(result);
    }

    /**
     * Specifies the list of {@link Property properties}.
     * @param properties such list.
     */
    protected final void immutableSetProperties(@NotNull final List<Property<String>> properties)
    {
        this.m__Properties = properties;
    }

    /**
     * Specifies the list of {@link Property properties}.
     * @param properties such list.
     */
    @SuppressWarnings("unused")
    protected final void setProperties(@NotNull final List<Property<String>> properties)
    {
        immutableSetProperties(properties);
    }

    /**
     * Retrieves the list of {@link Property properties}.
     * @return such list.
     */
    @NotNull
    protected List<Property<String>> getProperties()
    {
        return this.m__Properties;
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
     * Retrieves the {@link Property} associated to given id.
     * @param id the parameter id.
     * @return the {@link Property}, or <code>null</code> if
     * not found.
     */
    @Nullable
    @Override
    public Property<String> findByPrimaryKey(@NotNull final String id)
    {
        return findByPrimaryKey(id, getProperties());
    }

    /**
     * Retrieves the {@link Property} associated to given id.
     * @param id the parameter id.
     * @param properties the wrapped properties.
     * @return the {@link Property}, or <code>null</code> if
     * not found.
     */
    @Nullable
    protected Property<String> findByPrimaryKey(
        @NotNull final String id, @NotNull final List<Property<String>> properties)
    {
        @Nullable Property<String> result = null;

        for (@NotNull final Property<String> property : properties)
        {
            if (id.equals(property.getId()))
            {
                result = property;
                break;
            }
        }

        return result;
    }

    /**
     * Retrieves all {@link Property properties} used in given
     * {@link Result}.
     * @param resultId the {@link Result} identifier.
     * @return the list of properties associated to given {@link Result}.
     */
    @NotNull
    @Override
    public List<Property<String>> findByResult(@NotNull final String resultId)
    {
        return findByResult(resultId, getProperties(), getResult());
    }

    /**
     * Retrieves all {@link Property properties} used in given
     * {@link Result}.
     * @param resultId the {@link Result} identifier.
     * @param properties the properties.
     * @param customResult the custom result.
     * @return the list of properties associated to given {@link Result}.
     */
    @NotNull
    protected List<Property<String>> findByResult(
        @NotNull final String resultId,
        @NotNull final List<Property<String>> properties,
        @NotNull final Result<String> customResult)
    {
        @NotNull List<Property<String>> result = properties;

        if (!resultId.equals(customResult.getId()))
        {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * Inserts a new property.
     * @param id the property id.
     * @param columnName the column name.
     * @param index the property index.
     * @param type the type.
     * @param nullable whether it allows null or not.
     */
    @Override
    public void insert(
        @NotNull final String id,
        @NotNull final String columnName,
        final int index,
        @NotNull final String type,
        final boolean nullable)
    {
        insert(id, columnName, index, type, nullable, getProperties());
    }

    /**
     * Inserts a new property.
     * @param id the property id.
     * @param columnName the column name.
     * @param index the property index.
     * @param type the type.
     * @param nullable whether it allows null or not.
     * @param properties the properties.
     */
    protected void insert(
        @NotNull final String id,
        @NotNull final String columnName,
        final int index,
        @NotNull final String type,
        final boolean nullable,
        @NotNull final List<Property<String>> properties)
    {
        properties.add(new PropertyElement<>(id, columnName, index, type, nullable));
    }

    @Override
    public String toString()
    {
        return "CucumberSqlPropertyDAO{" +
               "customResult=" + m__CustomResult +
               ", properties=" + m__Properties +
               '}';
    }
}
