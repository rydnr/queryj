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
 * Filename: TableTestHelper.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Helper class for per-table Cucumber tests.
 *
 * Date: 5/26/13
 * Time: 5:46 PM
 *
 */
package cucumber.templates;

/*
 * Importing Cucumber classes.
 */
import cucumber.api.DataTable;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.ParameterElement;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.SqlElement;
import org.acmsl.queryj.metadata.engines.JdbcMetadataTypeManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.AttributeValueObject;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.ForeignKeyValueObject;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.metadata.vo.TableValueObject;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.tools.ant.AntFieldElement;
import org.acmsl.queryj.tools.ant.AntTablesElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for per-table Cucumber tests.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/05/26
 */
public class TableTestHelper
{
    private static final String COMMENT = "comment";
    private static final String PARENT_TABLE = "parent table";
    private static final String STATIC = "static";
    private static final String DECORATED = "decorated";

    /**
     * Singleton implementation to avoid double-locking check.
     */
    protected static final class TableTestHelperSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final TableTestHelper SINGLETON = new TableTestHelper();
    }

    /**
     * Retrieves the singleton instance.
     *
     * @return such instance.
     */
    @NotNull
    public static TableTestHelper getInstance()
    {
        return TableTestHelperSingletonContainer.SINGLETON;
    }

    /**
     * Defines the input tables based on the information provided by the
     * feature.
     * @param tableInfo the information about the tables.
     * @param tables the table collection.
     */
    public void defineInputTables(
        @NotNull final DataTable tableInfo, @NotNull final Map<String, Table<String, Attribute<String>>> tables)
    {
        @NotNull final List<Map<String, String>> tableEntries = tableInfo.asMaps();

        @Nullable Table<String, Attribute<String>> table;

        for (@NotNull final Map<String, String> tableEntry: tableEntries)
        {
            table = convertToTable(tableEntry);

            if (table != null)
            {
                tables.put(table.getName(), table);
            }
        }
    }

    /**
     * Converts given table information to a {@link Table}.
     * @param tableEntry the table information.
     * @return the {@link Table} instance.
     */
    @Nullable
    protected Table<String, Attribute<String>> convertToTable(@NotNull final Map<String, String> tableEntry)
    {
        @Nullable Table<String, Attribute<String>> result = null;

        @Nullable final String table =  tableEntry.get(AntTablesElement.TABLE);

        if (table != null)
        {
            result =
                convertToTable(
                    table,
                    tableEntry.get(COMMENT),
                    tableEntry.get(PARENT_TABLE),
                    tableEntry.get(STATIC) != null,
                    tableEntry.get(DECORATED) != null);
        }

        return result;
    }

    /**
     * Defines the columns from the feature.
     * @param columnInfo the column information.
     * @param tables the tables.
     */
    public List<Table<String, Attribute<String>>> defineInputColumns(
        @NotNull final DataTable columnInfo, @NotNull final Map<String, Table<String, Attribute<String>>> tables)
    {
        @NotNull final List<Table<String, Attribute<String>>> result = new ArrayList<Table<String, Attribute<String>>>(tables.size());

        for (@NotNull final Table<String, Attribute<String>> table : tables.values())
        {
            result.add(table);

            @NotNull final List<Attribute<String>> attributes = table.getAttributes();

            @NotNull final List<Attribute<String>> primaryKey = table.getPrimaryKey();

            Attribute<String> attribute;

            int index = 1;

            int precision;
            String[] booleanInfo;

            for (@NotNull final Map<String, String> columnEntry: columnInfo.asMaps())
            {
                if (table.getName().equals(columnEntry.get(AntTablesElement.TABLE)))
                {
                    precision = retrievePrecision(columnEntry);
                    booleanInfo = retrieveBooleanInfo(columnEntry);

                    attribute =
                        new AttributeValueObject(
                            columnEntry.get("column"),
                            retrieveAttributeTypeId(columnEntry.get("type"), precision),
                            columnEntry.get("type"),
                            table.getName(),
                            "test comment",
                            index++,
                            retrieveLength(columnEntry),
                            precision,
                            columnEntry.get(AntFieldElement.KEYWORD_LITERAL),
                            columnEntry.get("query"),
                            Boolean.valueOf(columnEntry.get("allows null")),
                            columnEntry.get("value"),
                            Boolean.valueOf(columnEntry.get("readonly")),
                            booleanInfo[0] != null,
                            booleanInfo[0],
                            booleanInfo[1],
                            booleanInfo[2]);

                    if (Boolean.valueOf(columnEntry.get("pk")))
                    {
                        primaryKey.add(attribute);
                    }

                    attributes.add(attribute);
                }
            }
        }

        return result;
    }

    /**
     * Converts given table information to a {@link Table}.
     * @param tableName the table name.
     * @param comment the comment.
     * @param parentTable the name of the parent table, if any.
     * @param isStatic whether the table is static.
     * @param isDecorated whether the table is decorated.
     * @return the {@link Table} instance.
     */
    @Nullable
    public Table<String, Attribute<String>> convertToTable(
        @NotNull final String tableName,
        @Nullable final String comment,
        @SuppressWarnings("unused") @Nullable final String parentTable,
        final boolean isStatic,
        final boolean isDecorated)
    {
        return
            new TableValueObject(
                tableName,
                comment,
                new ArrayList<Attribute<String>>(),
                new ArrayList<Attribute<String>>(),
                new ArrayList<ForeignKey<String>>(),
                // TODO: Decorate TableValueObject to retrieve the parent table via its name
                // and the table collection.
                null, //parentTable,
                isStatic,
                isDecorated);
    }

    /**
     * Retrieves an int value from given row.
     * @param row the row.
     * @param columnName the column name.
     * @return the value.
     */
    public int retrieveInt(@NotNull final Map<String, String> row, @NotNull final String columnName)
    {
        int result = 0;
        @Nullable final String value = row.get(columnName);

        if (   (value != null)
               && (!"".equals(value.trim())))
        {
            result = Integer.parseInt(value);
        }

        return result;
    }

    /**
     * Retrieves the length information from given row.
     * @param row the row.
     * @return the length.
     */
    public int retrieveLength(@NotNull final Map<String, String> row)
    {
        return retrieveInt(row, "length");
    }

    /**
     * Retrieves the precision information from given row.
     * @param row the row.
     * @return the length.
     */
    public int retrievePrecision(@NotNull final Map<String, String> row)
    {
        return retrieveInt(row, "precision");
    }

    /**
     * Retrieves the boolean declaration of given row.
     * @param row the row.
     * @return an 3-element array in which the first element is the
     * value for <code>true</code> entries; the second element is
     * the value representing <code>false</code>, and
     * the last element is the value to be translated as <code>null</code>.
     */
    @NotNull
    public String[] retrieveBooleanInfo(@NotNull final Map<String, String> row)
    {
        @NotNull final String[] result = new String[3];

        @Nullable final String booleanInfo = row.get(Literals.BOOLEAN);

        if (   (booleanInfo != null)
               && (!"".equals(booleanInfo.trim())))
        {
            @NotNull final String[] fields = booleanInfo.split(",");
            result[0] = fields[0];

            if (fields.length > 1)
            {
                result[1] = fields[1];
            }

            if (fields.length > 2)
            {
                result[2] = fields[2];
            }
        }

        return result;
    }

    /**
     * Filters the attributes based on given column names.
     * @param table the table name.
     * @param columns the columns.
     * @return the list of attributes matching given column names.
     */
    @NotNull
    public List<Attribute<String>> filterAttributes(final Table<String, Attribute<String>> table, final String[] columns)
    {
        @NotNull final List<Attribute<String>> result = new ArrayList<Attribute<String>>();

        for (@NotNull final Attribute<String> attribute : table.getAttributes())
        {
            for (@Nullable final String column : columns)
            {
                if (attribute.getName().equalsIgnoreCase(column))
                {
                    result.add(attribute);
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the id of the attribute type.
     * @param type the type.
     * @param precision the precision.
     * @return the concrete type in {@link java.sql.Types}.
     */
    protected int retrieveAttributeTypeId(@NotNull final String type, final int precision)
    {
        return new JdbcMetadataTypeManager().getJavaType(type, precision);
    }

    /**
     * Defines the foreign keys via cucumber features.
     * @param fkInfo such information.
     * @param tables the tables.
     * @param foreignKeys the foreign keys.
     */
    public void defineForeignKeys(
        @NotNull final DataTable fkInfo,
        @NotNull final Map<String, Table<String, Attribute<String>>> tables,
        @NotNull final List<ForeignKey<String>> foreignKeys)
    {
        String sourceTable;
        String sourceColumnsField;
        String[] sourceColumns;
        String targetTable;

        ForeignKey<String> foreignKey;

        for (@NotNull final Table<String, Attribute<String>> table : tables.values())
        {
            for (@NotNull final Map<String, String> fkEntry: fkInfo.asMaps())
            {
                sourceTable = fkEntry.get("source table");

                if (table.getName().equalsIgnoreCase(sourceTable.trim()))
                {
                    sourceColumnsField = fkEntry.get("source columns");
                    sourceColumns = null;
                    if (sourceColumnsField != null)
                    {
                        sourceColumns = sourceColumnsField.split(",");
                    }

                    targetTable = fkEntry.get("target table");

                    foreignKey =
                        new ForeignKeyValueObject(
                            sourceTable,
                            filterAttributes(table, sourceColumns),
                            targetTable,
                            Boolean.valueOf(fkEntry.get("allows null")));

                    foreignKeys.add(foreignKey);
                }
            }
        }
    }

    /**
     * Defines table values via cucumber features.
     * @param values such information.
     * @param tables the tables.
     */
    @SuppressWarnings("unused")
    public void defineValues(@NotNull final DataTable values, @NotNull final Map<String, Table<String, Attribute<String>>> tables)
    {
        for (@NotNull final Table<String, Attribute<String>> table : tables.values())
        {
            @NotNull final List<Attribute<String>> attributes = table.getAttributes();

            @NotNull final List<Attribute<String>> primaryKey = table.getPrimaryKey();

            // TODO override MetadataManager#TableDAO#findAll with the contents of "values"
        }
    }

    /**
     * Defines custom SQL sentences.
     * @param sqlInfo the SQL information in Cucumber table format.
     * @return a Map of DAO-name, Sql pairs.
     */
    @NotNull
    public List<Sql> defineSql(@NotNull final DataTable sqlInfo)
    {
        @NotNull final List<Sql> result = new ArrayList<Sql>();

        for (@NotNull final Map<String, String> sqlRow: sqlInfo.asMaps())
        {
            @NotNull final String id = sqlRow.get("id");
            @NotNull final String name = sqlRow.get("name");
            @NotNull final String dao = sqlRow.get("dao");
            @NotNull final String type = sqlRow.get("type");
            @NotNull final String value = sqlRow.get("value");

            @NotNull final SqlElement sql = new SqlElement(id, dao, name, type, "all", false, false);
            sql.setValue(value);

            result.add(sql);
        }

        return result;
    }

    /**
     * Defines parameters in custom SQL sentences.
     * @param parameterInfo the SQL information in Cucumber table format.
     * @return a Map of DAO-name, Sql pairs.
     */
    @NotNull
    public Map<String, List<Parameter>> defineParameters(@NotNull final DataTable parameterInfo)
    {
        @NotNull final Map<String, List<Parameter>> result = new HashMap<String, List<Parameter>>();

        for (@NotNull final Map<String, String> sqlRow: parameterInfo.asMaps())
        {
            @NotNull final String id = sqlRow.get("id");
            @NotNull final String sql = sqlRow.get("sql");
            @NotNull final String index = sqlRow.get("index");
            @NotNull final String name = sqlRow.get("name");
            @NotNull final String type = sqlRow.get("type");

            @NotNull final ParameterElement parameter = new ParameterElement(id, Integer.parseInt(index), name, type, null);

            @Nullable List<Parameter> parameters = result.get(sql);
            if (parameters == null)
            {
                parameters = new ArrayList<Parameter>();
                result.put(sql, parameters);
            }
            parameters.add(parameter);
        }

        return result;
    }
}
