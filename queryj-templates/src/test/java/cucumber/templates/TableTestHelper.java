/*
                        QueryJ Templates

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
import org.acmsl.queryj.customsql.ParameterRef;
import org.acmsl.queryj.customsql.ParameterRefElement;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.Sql.Cardinality;
import org.acmsl.queryj.customsql.SqlElement;
import org.acmsl.queryj.metadata.engines.JdbcMetadataTypeManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.AttributeIncompleteValueObject;
import org.acmsl.queryj.metadata.vo.AttributeValueObject;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.ForeignKeyValueObject;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.metadata.vo.RowValueObject;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.metadata.vo.TableValueObject;
import org.acmsl.queryj.tools.ant.AntFieldElement;
import org.acmsl.queryj.tools.ant.AntTablesElement;

/*
 * Importing JetBrains annotations.
 */
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;

/*
 * Importing JDK classes.
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Helper class for per-table Cucumber tests.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/05/26
 */
public class TableTestHelper
{
    private static final String TEST_COMMENT = "test comment";
    private static final String COLUMN = "column";
    private static final String PARENT_TABLE = "parent table";
    private static final String STATIC = "static";
    private static final String DECORATED = "decorated";
    private static final String PRECISION = "precision";
    private static final String QUERY = "query";
    private static final String ALLOWS_NULL = "allows null";
    private static final String SOURCE_TABLE = "source table";
    private static final String TARGET_TABLE = "target table";
    private static final String SOURCE_COLUMNS = "source columns";
    private static final String VALUE = "value";
    private static final String READONLY = "readonly";
    public static final String SYNTAX_ERROR_IN_STATIC_CONTENT = "Syntax error in static content ";
    private static final String DATE_FORMAT_ES = "DD/MM/yyyy";
    private static final String DATE_FORMAT_EN = "yyyy/MM/DD";

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
        @NotNull final DataTable tableInfo,
        @NotNull final Map<String, Table<String, Attribute<String>, List<Attribute<String>>>> tables)
    {
        @NotNull final List<Map<String, String>> tableEntries = tableInfo.asMaps();

        @Nullable Table<String, Attribute<String>, List<Attribute<String>>> table;

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
    protected Table<String, Attribute<String>, List<Attribute<String>>> convertToTable(
        @NotNull final Map<String, String> tableEntry)
    {
        @Nullable Table<String, Attribute<String>, List<Attribute<String>>> result = null;

        @Nullable final String table =  tableEntry.get(AntTablesElement.TABLE);

        if (table != null)
        {
            result =
                convertToTable(
                    table,
                    tableEntry.get(Literals.COMMENT),
                    tableEntry.get(PARENT_TABLE),
                    tableEntry.get(STATIC),
                    isNullOrBlank(tableEntry.get(DECORATED)));
        }

        return result;
    }

    /**
     * Checks whether given value is null or blank.
     * @param value the value to check.
     * @return {@code true} in such case.
     */
    protected boolean isNullOrBlank(@Nullable final String value)
    {
        return
            (   (value == null)
             || ("".equals(value.trim())));
    }

    /**
     * Defines the columns from the feature.
     * @param columnInfo the column information.
     * @param tables the tables.
     */
    public List<Table<String, Attribute<String>, List<Attribute<String>>>> defineInputColumns(
        @NotNull final DataTable columnInfo,
        @NotNull final Map<String, Table<String, Attribute<String>, List<Attribute<String>>>> tables)
    {
        @NotNull final List<Table<String, Attribute<String>, List<Attribute<String>>>> result =
            new ArrayList<>(tables.size());

        for (@NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table : tables.values())
        {
            result.add(table);

            @NotNull final List<Attribute<String>> attributes = table.getAttributes();

            @NotNull final List<Attribute<String>> primaryKey = table.getPrimaryKey();

            Attribute<String> attribute;

            int index = 1;

            int length;
            int precision;
            String[] booleanInfo;

            for (@NotNull final Map<String, String> columnEntry: columnInfo.asMaps())
            {
                if (table.getName().equals(columnEntry.get(AntTablesElement.TABLE)))
                {
                    length = retrieveLength(columnEntry);
                    precision = retrievePrecision(columnEntry);
                    booleanInfo = retrieveBooleanInfo(columnEntry);

                    attribute =
                        new AttributeValueObject(
                            columnEntry.get(COLUMN),
                            retrieveAttributeTypeId(columnEntry.get("type"), length, precision),
                            columnEntry.get("type"),
                            table.getName(),
                            TableTestHelper.TEST_COMMENT,
                            index++,
                            retrieveLength(columnEntry),
                            precision,
                            columnEntry.get(AntFieldElement.KEYWORD_LITERAL),
                            columnEntry.get(TableTestHelper.QUERY),
                            columnEntry.get("sequence"),
                            Boolean.valueOf(columnEntry.get(TableTestHelper.ALLOWS_NULL)),
                            columnEntry.get(VALUE),
                            Boolean.valueOf(columnEntry.get(READONLY)),
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
     * @param staticAttribute the attribute used to label static contents.
     * @param isDecorated whether the table is decorated.
     * @return the {@link Table} instance.
     */
    @Nullable
    public Table<String, Attribute<String>, List<Attribute<String>>> convertToTable(
        @NotNull final String tableName,
        @Nullable final String comment,
        @SuppressWarnings("unused") @Nullable final String parentTable,
        @Nullable final String staticAttribute,
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
                staticAttribute != null ? new _AttributeIncompleteValueObject(staticAttribute, tableName) : null,
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
        return retrieveInt(row, Literals.LENGTH);
    }

    /**
     * Retrieves the precision information from given row.
     * @param row the row.
     * @return the length.
     */
    public int retrievePrecision(@NotNull final Map<String, String> row)
    {
        return retrieveInt(row, PRECISION);
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
    public List<Attribute<String>> filterAttributes(
        final Table<String, Attribute<String>, List<Attribute<String>>> table, final String[] columns)
    {
        @NotNull final List<Attribute<String>> result = new ArrayList<>();

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
     * @param length the length.
     * @param precision the precision.
     * @return the concrete type in {@link java.sql.Types}.
     */
    protected int retrieveAttributeTypeId(@NotNull final String type, final int length, final int precision)
    {
        return new JdbcMetadataTypeManager().toJdbcType(type, length, precision);
    }

    /**
     * Defines the foreign keys via cucumber features.
     * @param fkInfo such information.
     * @param tables the tables.
     * @param foreignKeys the foreign keys.
     */
    public void defineForeignKeys(
        @NotNull final DataTable fkInfo,
        @NotNull final Map<String, Table<String, Attribute<String>, List<Attribute<String>>>> tables,
        @NotNull final List<ForeignKey<String>> foreignKeys)
    {
        ForeignKey<String> foreignKey;

        for (@NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table : tables.values())
        {
            for (@NotNull final Map<String, String> fkEntry: fkInfo.asMaps())
            {
                @Nullable final String sourceTable;
                @Nullable final String sourceColumnsField;
                @Nullable final String[] sourceColumns;
                @Nullable final String targetTable;

                sourceTable = fkEntry.get(SOURCE_TABLE);

                if (table.getName().equalsIgnoreCase(sourceTable.trim()))
                {
                    sourceColumnsField = fkEntry.get(SOURCE_COLUMNS);

                    if (sourceColumnsField != null)
                    {
                        sourceColumns = sourceColumnsField.split(",");

                        targetTable = fkEntry.get(TARGET_TABLE);

                        if (targetTable != null)
                        {
                            foreignKey =
                                new ForeignKeyValueObject(
                                    sourceTable,
                                    filterAttributes(table, sourceColumns),
                                    targetTable,
                                    Boolean.valueOf(fkEntry.get(ALLOWS_NULL)));

                            table.getForeignKeys().add(foreignKey);

                            foreignKeys.add(foreignKey);
                        }
                    }
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
    public void defineValues(
        @NotNull final DataTable values,
        @NotNull final Map<String, Table<String, Attribute<String>, List<Attribute<String>>>> tables)
    {
        for (@NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table : tables.values())
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
    public List<Sql<String>> defineSql(@NotNull final DataTable sqlInfo)
    {
        @NotNull final List<Sql<String>> result = new ArrayList<>();

        for (@NotNull final Map<String, String> sqlRow: sqlInfo.asMaps())
        {
            @NotNull final String id = sqlRow.get("id");
            @NotNull final String name = sqlRow.get("name");
            @NotNull final String dao = sqlRow.get("dao");
            @NotNull final String type = sqlRow.get("type");
            @NotNull final String matches = sqlRow.get("matches");
            @NotNull final String validate = sqlRow.get("validate");
            @NotNull final Cardinality cardinality = Cardinality.fromString(matches.toLowerCase(Locale.US));
            @NotNull final String value = sqlRow.get(VALUE);

            @NotNull final SqlElement<String> sql =
                new SqlElement<>(
                    id,
                    dao,
                    name,
                    type,
                    cardinality,
                    "all",
                    Boolean.valueOf(validate.trim().toLowerCase(Locale.US)),
                    false,
                    "Test " + id);

            sql.setValue(value);

            result.add(sql);
        }

        return result;
    }

    /**
     * Defines parameters in custom SQL sentences.
     * @param parameterInfo the SQL information in Cucumber table format.
     * @param sqlList the list of {@link Sql queries}.
     * @return a Map of DAO-name, Sql pairs.
     */
    @NotNull
    public Map<String, List<Parameter<String, ?>>> defineParameters(
        @NotNull final DataTable parameterInfo, @NotNull final List<Sql<String>> sqlList)
    {
        @NotNull final Map<String, List<Parameter<String, ?>>> result = new HashMap<>();

        for (@NotNull final Map<String, String> sqlRow: parameterInfo.asMaps())
        {
            @NotNull final String id = sqlRow.get("id");
            @NotNull final String sqlRef = sqlRow.get("sql");
            @NotNull final String index = sqlRow.get(Literals.INDEX);
            @NotNull final String name = sqlRow.get("name");
            @NotNull final String type = sqlRow.get("type");
            @Nullable final String validationValue = sqlRow.get("validation-value");

            @Nullable final Object parseValidationValue = (validationValue != null) ? toDate(validationValue) : null;

            @NotNull final ParameterElement<String, ?> parameter =
                new ParameterElement<>(
                    id,
                    Integer.parseInt(index),
                    name,
                    type,
                    (parseValidationValue != null) ? parseValidationValue : validationValue);

            @Nullable List<Parameter<String, ?>> parameters = result.get(sqlRef);

            if (parameters == null)
            {
                parameters = new ArrayList<>();
                result.put(sqlRef, parameters);
            }
            parameters.add(parameter);
            @Nullable final Sql<String> sql = retrieveSql(sqlList, sqlRef);
            if (sql != null)
            {
                @NotNull final List<ParameterRef> parameterRefs = sql.getParameterRefs();
                parameterRefs.add(new ParameterRefElement(parameter.getId()));
            }
            else
            {
                Assert.fail("SQL with id " + sqlRef + " not found");
            }
        }

        return result;
    }

    /**
     * Tries to convert given value to a date.
     * @param validationValue the value to convert.
     * @return the {@link Date}.
     */
    @Nullable
    protected Date toDate(@NotNull final String validationValue)
    {
        @Nullable Date result = null;

        try
        {
            result = new SimpleDateFormat(DATE_FORMAT_ES).parse(validationValue);
        }
        catch (@NotNull final ParseException invalidDate)
        {
            try
            {
                result = new SimpleDateFormat(DATE_FORMAT_EN).parse(validationValue);
            }
            catch (@NotNull final ParseException invalidEnglishDate)
            {
                LogFactory.getLog(TableTestHelper.class).debug(
                    "Cannot convert " + validationValue + " to a Date", invalidEnglishDate);
            }
        }

        return result;
    }

    /**
     * Retrieves the SQL matching given id.
     * @param sqlList the list of {@link Sql}.
     * @param sqlRef the SQL reference.
     * @return the {@link Sql} matching given reference.
     */
    @Nullable Sql<String> retrieveSql(@NotNull final List<Sql<String>> sqlList, @NotNull final String sqlRef)
    {
        @Nullable Sql<String> result = null;

        for (@Nullable final Sql<String> sql : sqlList)
        {
            if (   (sql != null)
                && (sql.getId().equalsIgnoreCase(sqlRef)))
            {
                result = sql;
                break;
            }
        }

        return result;
    }

    /**
     * Defines the static contents defined in a Cucumber test.
     * @param values the test values.
     */
    @NotNull
    public Map<String, List<Row<String>>> defineRows(
        @NotNull final DataTable values,
        @NotNull final Map<String, Table<String, Attribute<String>, List<Attribute<String>>>> tables)
    {
        @NotNull final Map<String, List<Row<String>>> result = new HashMap<>();

        for (@NotNull final Map<String, String> tableRow: values.asMaps())
        {
            @NotNull final String tableName = tableRow.get("table");
            @Nullable List<Row<String>> rows = result.get(tableName);
            if (rows == null)
            {
                rows = new ArrayList<>();
                result.put(tableName, rows);
            }
            @NotNull final String contents = tableRow.get("row");
            @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> table =
                tables.get(tableName);

            if (table == null)
            {
                Assert.fail("Unknown table for row: " + tableName);
            }
            else
            {
                @Nullable final String rowName = retrieveRowName(contents, table);

                Assert.assertNotNull("Cannot retrieve the row name.", rowName);

                @NotNull final Row<String> row =
                    new RowValueObject(rowName, tableName, fillValues(contents, table.getAttributes()));

                rows.add(row);
            }
        }

        return result;
    }

    /**
     * Fills the values from given contents, and returns a list of static attributes.
     * @param contents the contents.
     * @param attributes the attribute definitions.
     * @return a list of cloned attributes, with the correct values.
     */
    protected List<Attribute<String>> fillValues(
        @NotNull final String contents, @NotNull final List<Attribute<String>> attributes)
    {
        @NotNull final List<Attribute<String>> result = new ArrayList<>(attributes.size());

        @NotNull final String[] parts = contents.split(",");

        Assert.assertTrue(
            SYNTAX_ERROR_IN_STATIC_CONTENT + contents
            + ". Length of static columns does not match.",
            attributes.size() == parts.length);

        for (int index = 0; index < parts.length; index++)
        {
            @Nullable final Attribute<String> attribute = attributes.get(index);

            Assert.assertNotNull("Unexpected null attribute found.", attribute);

            @NotNull final Attribute<String> newAttribute =
                new AttributeValueObject(
                    attribute.getName(),
                    attribute.getTypeId(),
                    attribute.getType(),
                    attribute.getTableName(),
                    attribute.getComment(),
                    attribute.getOrdinalPosition(),
                    attribute.getLength(),
                    attribute.getPrecision(),
                    attribute.getKeyword(),
                    attribute.getRetrievalQuery(),
                    attribute.getSequence(),
                    attribute.isNullable(),
                    stripDoubleQuotes(parts[index].trim()),
                    attribute.isReadOnly(),
                    attribute.isBoolean(),
                    attribute.getBooleanTrue(),
                    attribute.getBooleanFalse(),
                    attribute.getBooleanNull());

            result.add(newAttribute);
        }

        return result;
    }

    /**
     * Strips the double quotes at the beginning and end.
     * @param value the value.
     * @return the stripped value.
     */
    @NotNull
    protected String stripDoubleQuotes(@NotNull final String value)
    {
        @NotNull final String result;

        result = value.replace("^\"", "").replace("\"$", "");

        return result;
    }

    /**
     * Retrieves the row for given contents.
     * @param contents the contents.
     * @param table the table.
     * @return the row.
     */
    @Nullable
    protected String retrieveRowName(
        @NotNull final String contents, @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table)
    {
        @NotNull final String result;

        @Nullable final Attribute<String> attribute = table.getStaticAttribute();

        Assert.assertNotNull(
            SYNTAX_ERROR_IN_STATIC_CONTENT + contents
            + ". Static column not found.",
            attribute);

        result = attribute.getName();

        return result;
    }

    protected static class _AttributeIncompleteValueObject
        extends AttributeIncompleteValueObject
    {
        public _AttributeIncompleteValueObject(final String staticAttribute, final String tableName)
        {
            super(
                staticAttribute,
                -1, // type
                "", // type
                tableName,
                "", // comment
                -1, // ordinalPosition
                -1, // length
                -1, // precision,
                false, // allows null,
                null); // value
        }
    }
}
