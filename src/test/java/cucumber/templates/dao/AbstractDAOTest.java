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
 * Filename: AbstractDAOTest.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Abstract class with common methods for Cucumber tests for DAO templates.
 *
 * Date: 2013/05/04
 * Time: 9:33 AM
 *
 */
package cucumber.templates.dao;

/*
 * Importing project classes.
 */
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.xml.SqlXmlParserImpl;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataExtractionLogger;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.engines.JdbcMetadataManager;
import org.acmsl.queryj.metadata.engines.JdbcMetadataTypeManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.AttributeValueObject;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.ForeignKeyValueObject;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.metadata.vo.TableValueObject;
import org.acmsl.queryj.templates.BasePerTableTemplate;
import org.acmsl.queryj.templates.BasePerTableTemplateContext;
import org.acmsl.queryj.templates.BasePerTableTemplateGenerator;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.antlr.JavaLexer;
import org.acmsl.queryj.tools.antlr.JavaParser;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.utils.io.FileUtils;

/*
 * Importing Cucumber classes.
 */
import cucumber.api.DataTable;

/*
 * Importing ANTLR classes.
 */
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/*
 * Importing Jetbrains Annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JUnit classes.
 */
import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/*
 * Importing JDK classes.
 */
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class with common methods for Cucumber tests for DAO templates.
 * @author <a href="chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/05/04
 */
public abstract class AbstractDAOTest<N extends BasePerTableTemplate<BasePerTableTemplateContext>>
{
    @Rule
    public TemporaryFolder rootFolder = new TemporaryFolder();
    /**
     * The table.
     */
    private Map<String, Table> m__mTables;
    /**
     * The foreign keys.
     */
    private List<ForeignKey> m__lForeignKeys;
    /**
     * The output file.
     */
    private Map<String, File> m__mOutputFiles;

    /**
     * Creates an instance.
     */
    protected AbstractDAOTest()
    {
        immutableSetTables(new HashMap<String, Table>());
        immutableSetForeignKeys(new ArrayList<ForeignKey>());
        immutableSetOutputFiles(new HashMap<String, File>());
    }

    /**
     * Specifies the tables.
     * @param tables the tables.
     */
    protected final void immutableSetTables(@NotNull final Map<String, Table> tables)
    {
        m__mTables = tables;
    }

    /**
     * Specifies the tables.
     * @param tables the tables.
     */
    @SuppressWarnings("unused")
    protected void setTables(@NotNull final Map<String, Table> tables)
    {
        immutableSetTables(tables);
    }

    /**
     * Retrieves the tables.
     * @return such information.
     */
    @NotNull
    protected Map<String, Table> getTables()
    {
        return m__mTables;
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
     * @param files such files.
     */
    @SuppressWarnings("unused")
    protected void immutableSetOutputFiles(@NotNull final Map<String, File> files)
    {
        m__mOutputFiles = files;
    }

    /**
     * Specifies the output files.
     * @param files such files.
     */
    @SuppressWarnings("unused")
    protected void setOutputFiles(@NotNull final Map<String, File> files)
    {
        immutableSetOutputFiles(files);
    }

    /**
     * Retrieves the output file.
     * @return such file.
     */
    public Map<String, File> getOutputFiles()
    {
        return m__mOutputFiles;
    }

    /**
     * Defines the input tables based on the information provided by the
     * feature.
     * @param tableInfo the information about the tables.
     */
    @SuppressWarnings("unused")
    @Given("the following tables:")
    public void defineInputTables(@NotNull final DataTable tableInfo)
    {
        defineInputTables(tableInfo, getTables());
    }

    /**
     * Defines the input tables based on the information provided by the
     * feature.
     * @param tableInfo the information about the tables.
     * @param tables the table collection.
     */
    protected void defineInputTables(
        @NotNull final DataTable tableInfo, @NotNull final Map<String, Table> tables)
    {
        @NotNull final List<Map<String, String>> tableEntries = tableInfo.asMaps();

        Table table;

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
     * Defines the columns from the feature.
     * @param columnInfo the column information.
     */
    @SuppressWarnings("unused")
    @And("the following columns:")
    public void defineInputColumns(@NotNull final DataTable columnInfo)
    {
        defineInputColumns(columnInfo, getTables());
    }

    /**
     * Defines the columns from the feature.
     * @param columnInfo the column information.
     * @param tables the tables.
     */
    protected void defineInputColumns(
        @NotNull final DataTable columnInfo, @NotNull final Map<String, Table> tables)
    {
        for (@NotNull final Table table : tables.values())
        {
            @NotNull final List<Attribute> attributes = table.getAttributes();

            @NotNull final List<Attribute> primaryKey = table.getPrimaryKey();

            Attribute attribute;

            int index = 1;

            int precision;
            String[] booleanInfo;

            for (@NotNull final Map<String, String> columnEntry: columnInfo.asMaps())
            {
                if (table.getName().equals(columnEntry.get("table")))
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
                            columnEntry.get("keyword"),
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
    }

    /**
     * Defines the foreign keys via cucumber features.
     * @param fkInfo such information.
     */
    @SuppressWarnings("unused")
    @And("the following foreign keys:")
    public void defineForeignKeys(@NotNull final DataTable fkInfo)
    {
        defineForeignKeys(fkInfo, getTables());
    }

    /**
     * Defines the foreign keys via cucumber features.
     * @param fkInfo such information.
     * @param tables the tables.
     */
    protected void defineForeignKeys(@NotNull final DataTable fkInfo, @NotNull final Map<String, Table> tables)
    {
        String sourceTable;
        String sourceColumnsField;
        String[] sourceColumns;
        String targetTable;

        ForeignKey foreignKey;

        for (@NotNull final Table table : tables.values())
        {
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
    }

    /**
     * Defines table values via cucumber features.
     * @param values such information.
     */
    @SuppressWarnings("unused")
    @And("the following values:")
    public void defineValues(@NotNull final DataTable values)
    {
        defineValues(values, getTables());
    }

    /**
     * Defines table values via cucumber features.
     * @param values such information.
     * @param tables the tables.
     */
    @SuppressWarnings("unused")
    protected void defineValues(@NotNull final DataTable values, @NotNull final Map<String, Table> tables)
    {
        for (@NotNull final Table table : tables.values())
        {
            @NotNull final List<Attribute> attributes = table.getAttributes();

            @NotNull final List<Attribute> primaryKey = table.getPrimaryKey();

            // TODO override MetadataManager#TableDAO#findAll with the contents of "values"
        }
    }

    /**
     * Generates a file with the information from the feature.
     */
    @SuppressWarnings("unused")
    public abstract void generateFile(@NotNull final String engine);

    /**
     * Retrieves the template generator from given template name.
     * @return such generator.
     */
    protected abstract BasePerTableTemplateGenerator<N, BasePerTableTemplateContext> retrieveTemplateGenerator();

    /**
     * Retrieves the template from given template name.
     * @param context the {@link BasePerTableTemplateContext} instance.
     * @return such template.
     */
    protected abstract N retrieveTemplate(@NotNull final BasePerTableTemplateContext context);

    /**
     * Retrieves the {@link DecoratorFactory} instance using given generator.
     * @param generator the generator to use.
     * @return the decorator factory.
     */
    protected abstract DecoratorFactory retrieveDecoratorFactory(
        BasePerTableTemplateGenerator<N, BasePerTableTemplateContext> generator);

    /**
     * Generates a file with the information from the feature.
     * @param tables the tables.
     * @param outputFiles the output files.
     */
    protected void generateFile(
        @NotNull final String engine,
        @NotNull final Map<String, Table> tables,
        @NotNull final Map<String, File> outputFiles)
    {
        BasePerTableTemplateGenerator<N, BasePerTableTemplateContext> generator =
            retrieveTemplateGenerator();

        for (@NotNull final Table table : tables.values())
        {
            BasePerTableTemplateContext context =
                new BasePerTableTemplateContext(
                    retrieveMetadataManager(engine, table),
                    retrieveCustomSqlProvider(),
                    "", // header
                    retrieveDecoratorFactory(generator),
                    "com.foo.bar.dao",
                    "com.foo.bar",
                    "acme", // repository
                    false, // marker
                    false, // jmx
                    "java:comp/env/db",
                    false, // disable generation timestamps
                    false, // disable NotNull annotations
                    true, // disable checkThread.org annotations
                    table.getName(),
                    new ArrayList<Row>(0));

            N template = retrieveTemplate(context);

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

//            Assert.assertTrue("Cannot create folder: " + outputDir.getAbsolutePath(), outputDir.mkdirs());

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

            outputFiles.put(table.getName(), new File(outputDir, generator.retrieveTemplateFileName(context)));
        }
    }

    /**
     * Checks the generated files compile.
     */
    @SuppressWarnings("unused")
    @Then("the generated files compile successfully")
    public void checkGeneratedFilesCompile()
    {
        checkGeneratedFilesCompile(getOutputFiles());
    }

    /**
     * Checks the generated files compile.
     * @param outputFiles the output files.
     */
    protected void checkGeneratedFilesCompile(@NotNull final Map<String, File> outputFiles)
    {
        for (@NotNull File outputFile : outputFiles.values())
        {
            FileUtils.getInstance().copyIfPossible(outputFile, new File(outputFile.getName()));

            @Nullable JavaLexer t_Lexer = null;

            try
            {
                t_Lexer =
                    new JavaLexer(new ANTLRFileStream(outputFile.getAbsolutePath()));
            }
            catch (final IOException missingFile)
            {
                Assert.fail(missingFile.getMessage());
            }

            @NotNull final CommonTokenStream t_Tokens =
                new CommonTokenStream(t_Lexer);

            @NotNull final JavaParser t_Parser = new JavaParser(t_Tokens);

            try
            {
                t_Parser.compilationUnit();
            }
            catch (@NotNull final Throwable invalidClass)
            {
                Assert.fail(invalidClass.getMessage());
            }

            Assert.assertNotNull("Missing package", t_Parser.getPackageName());
            Assert.assertEquals("Invalid package", "com.foo.bar.dao", t_Parser.getPackageName());

            Assert.assertNotNull("Missing class", t_Parser.getRootClass());
        }
    }

    /**
     * Retrieves a {@link org.acmsl.queryj.metadata.MetadataManager} instance.
     * @param engineName the name of the engine.
     * @param table the {@link org.acmsl.queryj.metadata.vo.Table}.
     * @return such instance.
     */
    @NotNull
    protected MetadataManager retrieveMetadataManager(@NotNull final String engineName, @NotNull final Table table)
    {
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
     * Retrieves an empty {@link org.acmsl.queryj.customsql.CustomSqlProvider} instance.
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
     * Converts given table information to a {@link org.acmsl.queryj.metadata.vo.Table}.
     * @param tableEntry the table information.
     * @return the {@link org.acmsl.queryj.metadata.vo.Table} instance.
     */
    @Nullable
    protected Table convertToTable(@NotNull final Map<String, String> tableEntry)
    {
        Table result = null;

        String table =  tableEntry.get("table");

        if (table != null)
        {
            result =
                new TableValueObject(
                    table,
                    tableEntry.get("comment"),
                    new ArrayList<Attribute>(),
                    new ArrayList<Attribute>(),
                    new ArrayList<ForeignKey>(),
                    // TODO: Decorate TableValueObject to retrieve the parent table via its name
                    // and the table collection.
                    null, //tableEntry.get("parent table"),
                    tableEntry.get("static") != null,
                    tableEntry.get("decorated") != null);
        }

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
     * @throws java.io.IOException if the folder cannot be created.
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
