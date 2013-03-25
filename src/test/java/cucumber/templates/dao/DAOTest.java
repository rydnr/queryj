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
 * Filename: DAOTest.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Cucumber test for DAO.feature
 *
 * Date: 3/23/13
 * Time: 10:20 AM
 *
 */
package cucumber.templates.dao;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.DataTable;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.xml.SqlXmlParserImpl;
import org.acmsl.queryj.metadata.MetadataExtractionLogger;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.engines.JdbcMetadataManager;
import org.acmsl.queryj.metadata.vo.ForeignKeyValueObject;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.templates.BasePerTableTemplateContext;
import org.acmsl.queryj.templates.dao.DAOTemplate;
import org.acmsl.queryj.templates.dao.DAOTemplateGenerator;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;

import org.acmsl.queryj.metadata.engines.JdbcMetadataTypeManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.AttributeValueObject;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.metadata.vo.TableValueObject;
import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Cucumber test for DAO.feature.
 * @author <a href="chous@acm-sl.org">Jose San Leandro</a>
 * @since 03/2013
 */
public class DAOTest
{
    @Rule
    public TemporaryFolder rootFolder = new TemporaryFolder();

    /**
     * The table.
     */
    private Table m__Table;

    /**
     * The foreign keys.
     */
    private List<ForeignKey> m__lForeignKeys;

    /**
     * The output file.
     */
    private File m__OutputFile;

    /**
     * Creates a new test instance.
     */
    public DAOTest()
    {
        immutableSetForeignKeys(new ArrayList<ForeignKey>());
    }

    /**
     * Specifies the table.
     * @param table the table.
     */
    protected final void immutableSetTable(@NotNull final Table table)
    {
        m__Table = table;
    }

    /**
     * Specifies the table.
     * @param table the table.
     */
    @SuppressWarnings("unused")
    protected void setTable(@NotNull final Table table)
    {
        immutableSetTable(table);
    }

    /**
     * Retrieves the table.
     * @return such information.
     */
    @NotNull
    protected Table getTable()
    {
        return m__Table;
    }

    /**
     * Specifies the foreign keys.
     * @param foreignKeys the foreign keys.
     */
    protected final void immutableSetForeignKeys(@NotNull final List<ForeignKey> foreignKeys)
    {
        m__lForeignKeys = foreignKeys;
    }

    /**
     * Specifies the foreign keys.
     * @param foreignKeys the foreign keys.
     */
    @SuppressWarnings("unused")
    protected void setSetForeignKeys(@NotNull final List<ForeignKey> foreignKeys)
    {
        immutableSetForeignKeys(foreignKeys);
    }

    @NotNull
    protected List<ForeignKey> getForeignKeys()
    {
        return m__lForeignKeys;
    }

    /**
     * Specifies the output file.
     * @param file such file.
     */
    @SuppressWarnings("unused")
    protected void immutableSetOutputFile(@NotNull final File file)
    {
        m__OutputFile = file;
    }

    /**
     * Specifies the output file.
     * @param file such file.
     */
    protected void setOutputFile(@NotNull final File file)
    {
        immutableSetOutputFile(file);
    }

    /**
     * Retrieves the output file.
     * @return such file.
     */
    public File getOutputFile()
    {
        return m__OutputFile;
    }

    /**
     * Defines the input table based on the information provided by the
     * feature.
     * @param tableInfo the information about the table.
     */
    @Given("the following table:")
    public void defineInputTable(@NotNull final DataTable tableInfo)
    {
        @NotNull final List<Map<String, String>> tableEntries = tableInfo.asMaps();

        Table table = null;

        for (@NotNull final Map<String, String> tableEntry: tableEntries)
        {
            table = convertToTable(tableEntry);
            break;
        }

        if (table != null)
        {
            setTable(table);
        }
    }

    /**
     * Defines the columns from the feature.
     * @param columnInfo the column information.
     */
    @And("the following columns:")
    public void defineInputColumns(@NotNull final DataTable columnInfo)
    {
        @NotNull final Table table = getTable();

        List<Attribute> attributes = table.getAttributes();

        Attribute attribute;

        int index = 1;

        int precision;
        String[] booleanInfo;

        for (@NotNull final Map<String, String> columnEntry: columnInfo.asMaps())
        {
            precision = retrievePrecision(columnEntry);
            booleanInfo = retrieveBooleanInfo(columnEntry);

            attribute =
                new AttributeValueObject(
                    columnEntry.get("column"),
                    retrieveAttributeTypeId(columnEntry.get("type"), precision),
                    columnEntry.get("type"),
                    columnEntry.get("table"),
                    "test comment",
                    index++,
                    retrieveLength(columnEntry),
                    precision,
                    columnEntry.get("keyword"),
                    columnEntry.get("query"),
                    Boolean.valueOf(columnEntry.get("allows null")),
                    columnEntry.get("value"),
                    Boolean.valueOf(columnEntry.get("readonly")),
                    booleanInfo[0] != null,
                    booleanInfo[0],
                    booleanInfo[1],
                    booleanInfo[2]);

            attributes.add(attribute);
        }
    }

    /**
     * Defines the foreign keys via cucumber features.
     * @param fkInfo such information.
     */
    @SuppressWarnings("unused")
    @And("the following foreign keys:")
    public void defineForeignKeys(@NotNull final DataTable fkInfo)
    {
        String id;
        String sourceTable;
        String sourceColumnsField;
        String[] sourceColumns;
        String targetTable;

        ForeignKey foreignKey;

        @NotNull final Table table = getTable();

        @NotNull final List<ForeignKey> foreignKeys = getForeignKeys();

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

    /**
     * Generates a file with the information from the feature.
     * @param templateName the template.
     */
    @SuppressWarnings("unused")
    @When("I generate with (.*)\\.stg for (.*)")
    public void generateFile(@NotNull final String templateName, @NotNull final String engine)
    {
        DAOTemplateGenerator generator = new DAOTemplateGenerator(false, 1);

        BasePerTableTemplateContext context =
            new BasePerTableTemplateContext(
                retrieveMetadataManager(engine),
                retrieveCustomSqlProvider(),
                "", // header
                generator.getDecoratorFactory(),
                "dao",
                "com.foo.bar",
                "acme", // repository
                false, // marker
                false, // jmx
                "java:comp/env/db",
                true, // disable generation timestamps
                true, // disable NotNull annotations
                true, // disable checkThread.org annotations
                getTable().getName(),
                new ArrayList<Row>(0));

        DAOTemplate template = new DAOTemplate(context);

        File outputDir = null;
        try
        {
            rootFolder.create();
            outputDir = rootFolder.newFolder("dao");
        }
        catch (@NotNull final IOException ioException)
        {
            Assert.fail(ioException.getMessage());
        }

//        Assert.assertTrue("Cannot create folder: " + outputDir.getAbsolutePath(), outputDir.mkdirs());

        UniqueLogFactory.initializeInstance(LogFactory.getLog(DAOTest.class));

        try
        {
            generator.write(
                template,
                outputDir,
                rootFolder.getRoot(),
                Charset.defaultCharset());
        }
        catch (@NotNull final IOException ioException)
        {
            Assert.fail(ioException.getMessage());
        }
        catch (@NotNull final QueryJBuildException queryjBuildException)
        {
            Assert.fail(queryjBuildException.getMessage());
        }

        setOutputFile(new File(outputDir, generator.retrieveTemplateFileName(context)));
    }

    @SuppressWarnings("unused")
    @Then("the generated (.*) compiles successfully")
    public void checkGeneratedFileCompiles(@NotNull final String file)
    {
        File outputFile = getOutputFile();

        Assert.assertEquals("Wrong name of the generated file", file, outputFile.getName());
    }

    /**
     * Retrieves a {@link MetadataManager} instance.
     * @param engineName the name of the engine.
     * @return such instance.
     */
    @NotNull
    protected MetadataManager retrieveMetadataManager(@NotNull final String engineName)
    {
        @NotNull final Table table = getTable();
        @NotNull final List<String> tableNames = new ArrayList<String>(1);
        tableNames.add(table.getName());
        @NotNull final List<Table> tables = new ArrayList<Table>(1);
        tables.add(table);

        return
            new JdbcMetadataManager(
                "fake manager",
                null, // database metadata
                new MetadataExtractionLogger(), // extraction listener
                "catalog",
                "schema",
                tableNames,
                tables,
                true, // disable table extraction
                true, // lazy table extraction
                false, // case sensitive
                engineName, // engine
                "11", // engine version
                "'"); // quote

    }

    /**
     * Retrieves an empty {@link CustomSqlProvider} instance.
     * @return such instance.
     */
    @NotNull
    protected CustomSqlProvider retrieveCustomSqlProvider()
    {
        return new SqlXmlParserImpl(new ByteArrayInputStream("".getBytes()));
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
     * Converts given table information to a {@link Table}.
     * @param tableEntry the table information.
     * @return the {@link Table} instance.
     */
    @NotNull
    protected Table convertToTable(@NotNull final Map<String, String> tableEntry)
    {
        Table result;

        result =
            new TableValueObject(
                tableEntry.get("table"),
                tableEntry.get("comment"),
                new ArrayList<Attribute>(),
                new ArrayList<Attribute>(),
                new ArrayList<ForeignKey>(),
                null, //tableEntry.get("parent table"),
                tableEntry.get("static") != null,
                tableEntry.get("decorated") != null);

        return result;
    }

    /**
     * Retrieves an int value from given row.
     * @param row the row.
     * @param columnName the column name.
     * @return the value.
     */
    protected int retrieveInt(@NotNull final Map<String, String> row, @NotNull final String columnName)
    {
        int result = 0;
        String value = row.get(columnName);

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
    protected int retrieveLength(@NotNull final Map<String, String> row)
    {
        return retrieveInt(row, "length");
    }

    /**
     * Retrieves the precision information from given row.
     * @param row the row.
     * @return the length.
     */
    protected int retrievePrecision(@NotNull final Map<String, String> row)
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
    protected String[] retrieveBooleanInfo(@NotNull final Map<String, String> row)
    {
        String[] result = new String[3];

        String booleanInfo = row.get("boolean");

        if (   (booleanInfo != null)
            && (!"".equals(booleanInfo.trim())))
        {
            String[] fields = booleanInfo.split(",");
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
    protected List<Attribute> filterAttributes(final Table table, final String[] columns)
    {
        @NotNull final List<Attribute> result = new ArrayList<Attribute>();

        for (@NotNull final Attribute attribute : table.getAttributes())
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
     * Creates a temporary directory.
     * @param prefix the prefix to use (optional).
     * @return the temporary folder.
     * @throws IOException if the folder cannot be created.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected File createTempFolder(@Nullable final String prefix)
        throws IOException
    {
        @Nullable File result = null;

        @NotNull final String folderPrefix = (prefix != null) ? prefix : "cucumber";

        @Nullable IOException exceptionToThrow = null;

        try
        {
            result = File.createTempFile(folderPrefix, Long.toString(System.nanoTime()));
        }
        catch (@NotNull final IOException ioException)
        {
            exceptionToThrow = ioException;
        }

        if (   (exceptionToThrow == null)
            && (result != null)
            && (!result.delete()))
        {
            exceptionToThrow =
                new IOException("Could not delete temporary file: " + result.getAbsolutePath());
        }

        if (   (exceptionToThrow == null)
            && (result != null)
            && (!result.mkdirs()))
        {
            exceptionToThrow =
                new IOException("Could not create temporary file: " + result.getAbsolutePath());
        }

        if (exceptionToThrow != null)
        {
            throw exceptionToThrow;
        }

        return result;
    }
}
