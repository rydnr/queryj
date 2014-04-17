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
 * Filename: CustomResultTestHelper.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Helper class for per-custom result Cucumber tests.
 *
 * Date: 2014/04/17
 * Time: 11:39
 *
 */
package org.acmsl.queryj.test;

/*
 * Importing Cucumber classes.
 */
import cucumber.api.DataTable;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyElement;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultElement;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JetBrains annotations.
 */
import java.util.List;
import java.util.Map;

/**
 * Helper class for per-custom result Cucumber tests.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/17 11:39
 */
@ThreadSafe
public class CustomResultTestHelper
{
    /**
     * Singleton implementation to avoid double-locking check.
     */
    protected static final class CustomResultTestHelperSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final CustomResultTestHelper SINGLETON = new CustomResultTestHelper();
    }

    /**
     * Retrieves the singleton instance.
     *
     * @return such instance.
     */
    @NotNull
    public static CustomResultTestHelper getInstance()
    {
        return CustomResultTestHelperSingletonContainer.SINGLETON;
    }

    /**
     * Defines the input results based on the information provided by the
     * feature.
     * @param tableInfo the information about the results.
     * @param tables the table collection.
     */
    @SuppressWarnings("unused")
    public void defineInputResults(
        @NotNull final DataTable tableInfo,
        @NotNull final Map<String, Result<String>> tables)
    {
        @NotNull final List<Map<String, String>> tableEntries = tableInfo.asMaps();

        @Nullable Result<String> result;

        for (@NotNull final Map<String, String> tableEntry: tableEntries)
        {
            result = convertToCustomResult(tableEntry);

            if (result != null)
            {
                tables.put(result.getId(), result);
            }
        }
    }

    /**
     * Converts given table information to a {@link Result}.
     * @param tableEntry the table information.
     * @return the {@link Result} instance.
     */
    @Nullable
    protected Result<String> convertToCustomResult(@NotNull final Map<String, String> tableEntry)
    {
        @Nullable Result<String> result = null;

        @Nullable final String id =  tableEntry.get("id");

        if (id != null)
        {
            result = convertToCustomResult(id, tableEntry.get("type"));
        }

        return result;
    }

    /**
     * Creates a new {@link Result}.
     * @param id the id.
     * @param type the type.
     * @return the {@link Result} instance.
     */
    @Nullable
    protected Result<String> convertToCustomResult(@NotNull final String id, @NotNull final String type)
    {
        return new ResultElement<>(id, type);
    }

    /**
     * Defines the input properties based on the information provided by the
     * feature.
     * @param tableInfo the information about the properties.
     * @param properties the properties collection.
     */
    @SuppressWarnings("unused")
    public void defineInputProperties(
        @NotNull final DataTable tableInfo,
        @NotNull final Map<String, Property<String>> properties)
    {
        @NotNull final List<Map<String, String>> tableEntries = tableInfo.asMaps();

        @Nullable Property<String> property;

        for (@NotNull final Map<String, String> tableEntry: tableEntries)
        {
            property = convertToProperty(tableEntry);

            if (property != null)
            {
                properties.put(property.getId(), property);
            }
        }
    }

    /**
     * Converts given table information to a {@link Result}.
     * @param tableEntry the table information.
     * @return the {@link Result} instance.
     */
    @Nullable
    protected Property<String> convertToProperty(@NotNull final Map<String, String> tableEntry)
    {
        @Nullable Property<String> result = null;

        @Nullable final String name =  tableEntry.get("name");

        if (name != null)
        {
            result =
                convertToProperty(
                    name,
                    tableEntry.get("columnName"),
                    tableEntry.get(Literals.INDEX),
                    tableEntry.get("type"),
                    Boolean.valueOf(tableEntry.get("nullable")));
        }

        return result;
    }

    /**
     * Creates a new {@link Property}.
     * @param id the id.
     * @param columnName the column name.
     * @param index the index.
     * @param type the type.
     * @param nullable whether its nullable.
     * @return the {@link Property} instance.
     */
    @Nullable
    protected Property<String> convertToProperty(
        @NotNull final String id,
        @NotNull final String columnName,
        @NotNull final String index,
        @NotNull final String type,
        final boolean nullable)
    {
        @Nullable final Property<String> result;

        int propertyIndex = -1;

        try
        {
            propertyIndex = Integer.parseInt(index);
        }
        catch (@NotNull final NumberFormatException invalidIndex)
        {
        }

        if (propertyIndex == -1)
        {
            result = null;
        }
        else
        {
            result = new PropertyElement<>(id, columnName, propertyIndex, type, nullable);
        }

        return result;
    }
}
