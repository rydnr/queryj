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
 * Filename: PerTableTemplatesTest.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Cucumber tests for per-table templates.
 *
 * Date: 2013/05/04
 * Time: 9:33 AM
 *
 */
package cucumber.templates;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.engines.JdbcMetadataTypeManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.AttributeValueObject;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.ForeignKeyValueObject;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.metadata.vo.TableValueObject;
import org.acmsl.queryj.templates.BasePerTableTemplate;
import org.acmsl.queryj.templates.BasePerTableTemplateFactory;
import org.acmsl.queryj.templates.BasePerTableTemplateGenerator;
import org.acmsl.queryj.templates.dao.BaseDAOTemplateFactory;
import org.acmsl.queryj.templates.dao.BaseDAOTemplateGenerator;
import org.acmsl.queryj.templates.dao.DAOFactoryTemplateFactory;
import org.acmsl.queryj.templates.dao.DAOFactoryTemplateGenerator;
import org.acmsl.queryj.templates.dao.DAOTemplateFactory;
import org.acmsl.queryj.templates.dao.DAOTemplateGenerator;
import org.acmsl.queryj.templates.dao.PkStatementSetterTemplateFactory;
import org.acmsl.queryj.templates.dao.PkStatementSetterTemplateGenerator;
import org.acmsl.queryj.templates.dao.ResultSetExtractorTemplateFactory;
import org.acmsl.queryj.templates.dao.ResultSetExtractorTemplateGenerator;
import org.acmsl.queryj.templates.valueobject.BaseValueObjectTemplateFactory;
import org.acmsl.queryj.templates.valueobject.BaseValueObjectTemplateGenerator;
import org.acmsl.queryj.templates.valueobject.ValueObjectFactoryTemplateFactory;
import org.acmsl.queryj.templates.valueobject.ValueObjectFactoryTemplateGenerator;
import org.acmsl.queryj.templates.valueobject.ValueObjectImplTemplateFactory;
import org.acmsl.queryj.templates.valueobject.ValueObjectImplTemplateGenerator;
import org.acmsl.queryj.templates.valueobject.ValueObjectTemplateGenerator;
import org.acmsl.queryj.tools.QueryJBuildException;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing Cucumber classes.
 */
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

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

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cucumber tests for per-table templates.
 * @author <a href="chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/05/04
 */
public class PerTableTemplatesTest
    extends AbstractTemplatesTest<BasePerTableTemplateGenerator, BasePerTableTemplateFactory>
{
    /**
     * Creates an instance.
     */
    public PerTableTemplatesTest()
    {
        immutableSetTables(new HashMap<String, Table>());
        immutableSetForeignKeys(new ArrayList<ForeignKey>());

        // dao
        GENERATOR_MAPPINGS.put("DAO", new DAOTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("DAO", DAOTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("BaseDAO", new BaseDAOTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("BaseDAO", BaseDAOTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("DAOFactory", new DAOFactoryTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("DAOFactory", DAOFactoryTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("PkStatementSetter", new PkStatementSetterTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("PkStatementSetter", PkStatementSetterTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("ResultSetExtractor", new ResultSetExtractorTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("ResultSetExtractor", ResultSetExtractorTemplateFactory.getInstance());
        // vo
        GENERATOR_MAPPINGS.put("BaseValueObject", new BaseValueObjectTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("BaseValueObject", BaseValueObjectTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("ValueObject", new ValueObjectTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("ValueObject", BaseValueObjectTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("ValueObjectFactory", new ValueObjectFactoryTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("ValueObjectFactory", ValueObjectFactoryTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("ValueObjectImpl", new ValueObjectImplTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("ValueObjectImpl", ValueObjectImplTemplateFactory.getInstance());
    }

    /**
     * Defines the input tables based on the information provided by the
     * feature.
     * @param tableInfo the information about the tables.
     */
    @SuppressWarnings("unused")
    @Given("^the following tables:$")
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
    @Given("^the following columns:$")
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
    @Given("^the following foreign keys:$")
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
    @And("^the following values:$")
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
     * Checks the generated file compiles.
     * @param outputName the name of the output file.
     */
    @SuppressWarnings("unused")
    @Then("^the generated per-table (.*) file compiles successfully")
    public void checkGeneratedFileCompiles(@NotNull final String outputName)
    {
        checkGeneratedFilesCompile(outputName, getOutputFiles());
    }

    /**
     * Retrieves the {@link DecoratorFactory} instance using given generator.
     * @param generator the generator to use.
     * @return the decorator factory.
     */
    @NotNull
    @Override
    protected DecoratorFactory retrieveDecoratorFactory(@NotNull final BasePerTableTemplateGenerator generator)
    {
        return generator.getDecoratorFactory();
    }

    /**
     * Generates a file with the information from the feature.
     * @param template the template.
     * @param engine the engine.
     */
    @SuppressWarnings("unused")
    @When("^I generate with per-table (.*)\\.stg for (.*)$")
    public void generateFile(@NotNull final String template, @NotNull final String engine)
    {
        generateFile(template, engine, getTables(), getOutputFiles());
    }

    /**
     * Generates a file with the information from the feature.
     * @param templateName the template.
     * @param tables the tables.
     * @param outputFiles the output files.
     */
    @SuppressWarnings("unchecked")
    protected void generateFile(
        @NotNull final String templateName,
        @NotNull final String engine,
        @NotNull final Map<String, Table> tables,
        @NotNull final Map<String, File> outputFiles)
    {
        BasePerTableTemplateGenerator generator =
            retrieveTemplateGenerator(templateName);

        Assert.assertNotNull("No template generator found for " + templateName, generator);

        for (@NotNull final Table table : tables.values())
        {
            BasePerTableTemplateFactory templateFactory = retrieveTemplateFactory(templateName);

            Assert.assertNotNull("No template factory found for " + templateName, templateFactory);

            BasePerTableTemplate template =
                templateFactory.createTemplate(
                    retrieveMetadataManager(engine, table),
                    retrieveCustomSqlProvider(),
                    retrieveDecoratorFactory(generator),
                    "com.foo.bar.dao",
                    "com.foo.bar",
                    "acme", // repository
                    "", // header
                    false, // marker
                    false, // jmx
                    "java:comp/env/db",
                    false, // disable generation timestamps
                    false, // disable NotNull annotations
                    true, // disable checkThread.org annotations
                    table.getName(),
                    new ArrayList<Row>(0));

            Assert.assertNotNull("No template found for " + templateName, template);

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

            UniqueLogFactory.initializeInstance(LogFactory.getLog(PerTableTemplatesTest.class));

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

            outputFiles.put(
                table.getName(),
                new File(outputDir, generator.retrieveTemplateFileName(template.getTemplateContext())));
        }
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

}
