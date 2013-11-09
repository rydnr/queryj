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
import org.acmsl.queryj.templates.DAOFactoryTemplateFactory;
import org.acmsl.queryj.templates.DAOFactoryTemplateGenerator;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.api.PerTableTemplate;
import org.acmsl.queryj.api.PerTableTemplateFactory;
import org.acmsl.queryj.api.PerTableTemplateGenerator;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

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
import java.util.List;
import java.util.Map;

/**
 * Cucumber tests for per-table templates.
 * @author <a href="chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/05/04
 */
public class PerTableTemplatesTest
    extends AbstractTemplatesTest<PerTableTemplateGenerator, PerTableTemplateFactory>
{
    /**
     * Creates an instance.
     */
    public PerTableTemplatesTest()
    {
        super();

        // dao
        GENERATOR_MAPPINGS.put("DAOFactory", new DAOFactoryTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("DAOFactory", DAOFactoryTemplateFactory.getInstance());
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
        defineInputTables(tableInfo, getTables(), TableTestHelper.getInstance());
    }

    /**
     * Defines the input tables based on the information provided by the
     * feature.
     * @param tableInfo the information about the tables.
     */
    protected void defineInputTables(
        @NotNull final DataTable tableInfo,
        @NotNull final Map<String, Table<String>> tables,
        @NotNull final TableTestHelper helper)
    {
        helper.defineInputTables(tableInfo, tables);
    }

    /**
     * Defines the columns from the feature.
     * @param columnInfo the column information.
     */
    @SuppressWarnings("unused")
    @Given("^the following columns:$")
    public void defineInputColumns(@NotNull final DataTable columnInfo)
    {
        defineInputColumns(columnInfo, getTables(), TableTestHelper.getInstance());
    }

    /**
     * Defines the columns from the feature.
     * @param columnInfo the column information.
     * @param tables the tables.
     */
    protected List<Table<String>> defineInputColumns(
        @NotNull final DataTable columnInfo,
        @NotNull final Map<String, Table<String>> tables,
        @NotNull final TableTestHelper helper)
    {
        return helper.defineInputColumns(columnInfo, tables);
    }

    /**
     * Defines the foreign keys via cucumber features.
     * @param fkInfo such information.
     */
    @SuppressWarnings("unused")
    @Given("^the following foreign keys:$")
    public void defineForeignKeys(@NotNull final DataTable fkInfo)
    {
        defineForeignKeys(fkInfo, getTables(), getForeignKeys(), TableTestHelper.getInstance());
    }

    /**
     * Defines the foreign keys via cucumber features.
     * @param fkInfo such information.
     * @param tables the table map.
     * @param foreignKeys the foreignKeys,
     * @param helper the {@link TableTestHelper} instance.
     */
    protected void defineForeignKeys(
        @NotNull final DataTable fkInfo,
        @NotNull final Map<String, Table<String>> tables,
        @NotNull final List<ForeignKey> foreignKeys,
        @NotNull final TableTestHelper helper)
    {
        helper.defineForeignKeys(fkInfo, tables, foreignKeys);
    }

    /**
     * Defines table values via cucumber features.
     * @param values such information.
     */
    @SuppressWarnings("unused")
    @And("^the following values:$")
    public void defineValues(@NotNull final DataTable values)
    {
        defineValues(values, getTables(), TableTestHelper.getInstance());
    }

    /**
     * Defines table values via cucumber features.
     * @param values such information.
     * @param tables the tables.
     * @param helper the {@link TableTestHelper} instance.
     */
    protected void defineValues(
        @NotNull final DataTable values,
        @NotNull final Map<String, Table<String>> tables,
        @NotNull final TableTestHelper helper)
    {
        helper.defineValues(values, tables);
    }

    /**
     * Defines SQL sentences via cucumber features.
     * @param values such information.
     */
    @SuppressWarnings("unused")
    @And("^the following queries:$")
    public void defineSql(@NotNull final DataTable values)
    {
        setSqlList(defineSql(values, TableTestHelper.getInstance()));
    }

    /**
     * Retrieves the list of {@link Sql} from a Cucumber table.\
     * @param values the Cucumber table.
     * @param helper the {@link TableTestHelper} instance.
     * @return the list of SQL items.
     */
    @NotNull
    protected List<Sql> defineSql(@NotNull final DataTable values, @NotNull final TableTestHelper helper)
    {
        return helper.defineSql(values);
    }

    /**
     * Defines SQL sentences via cucumber features.
     * @param values such information.
     */
    @SuppressWarnings("unused")
    @And("^the following query parameters:$")
    public void defineParameters(@NotNull final DataTable values)
    {
        setParameters(defineParameters(values, TableTestHelper.getInstance()));
    }

    /**
     * Defines SQL parameters via cucumber features.
     * @param values the SQL parameters in Cucumber table format.
     * @param helper the {@link TableTestHelper} instance.
     */
    @NotNull
    protected Map<String, List<Parameter>> defineParameters(
        @NotNull final DataTable values,
        @NotNull final TableTestHelper helper)
    {
        return helper.defineParameters(values);
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
    protected DecoratorFactory retrieveDecoratorFactory(@NotNull final PerTableTemplateGenerator generator)
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
        generateFile(
            template,
            engine,
            getTables(),
            getOutputFiles(),
            retrieveCustomSqlProvider(getSqlList(), getParameters()));
    }

    /**
     * Generates a file with the information from the feature.
     * @param templateName the template.
     * @param engine the engine name.
     * @param tables the tables.
     * @param outputFiles the output files.
     * @param sqlProvider the {@link CustomSqlProvider} instance.
     */
    @SuppressWarnings("unchecked")
    protected void generateFile(
        @NotNull final String templateName,
        @NotNull final String engine,
        @NotNull final Map<String, Table<String>> tables,
        @NotNull final Map<String, File> outputFiles,
        @NotNull final CustomSqlProvider sqlProvider)
    {
        @Nullable final PerTableTemplateGenerator generator =
            retrieveTemplateGenerator(templateName);

        Assert.assertNotNull("No template generator found for " + templateName, generator);

        for (@NotNull final Table<String> table : tables.values())
        {
            @Nullable final PerTableTemplateFactory templateFactory = retrieveTemplateFactory(templateName);

            Assert.assertNotNull("No template factory found for " + templateName, templateFactory);

            @Nullable final PerTableTemplate template =
                templateFactory.createTemplate(
                    retrieveMetadataManager(engine, table),
                    sqlProvider,
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
                new File(outputDir, template.getTemplateContext().getTemplateName()));
        }
    }
}
