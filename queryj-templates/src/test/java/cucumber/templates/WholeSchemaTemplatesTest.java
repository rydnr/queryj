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
 * Filename: WholeSchemaTemplatesTest.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Maps steps in Cucumber scenarios for testing schema-wide
 *              templates.
 * Date: 5/26/13
 * Time: 5:19 PM
 *
 */
package cucumber.templates;

/*
 * Importing project classes.
 */
import cucumber.api.java.en.And;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.api.PerRepositoryTemplateFactory;
import org.acmsl.queryj.api.PerRepositoryTemplateGenerator;
import org.acmsl.queryj.templates.other.CucumberFeatureTemplateFactory;
import org.acmsl.queryj.templates.other.CucumberFeatureTemplateGenerator;

/*
 * Importing Cucumber classes.
 */
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/*
 * Importing Jetbrains annotations.
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maps steps in Cucumber scenarios for testing schema-wide templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/05/26
 */
@SuppressWarnings("unused")
public class WholeSchemaTemplatesTest
    extends AbstractPerRepositoryTemplatesTest<PerRepositoryTemplateGenerator, PerRepositoryTemplateFactory>
{
    /**
     * Creates an instance.
     */
    public WholeSchemaTemplatesTest()
    {
        immutableSetTables(new HashMap<String, Table>());
        immutableSetForeignKeys(new ArrayList<ForeignKey>());
        GENERATOR_MAPPINGS.put("CucumberFeature", new CucumberFeatureTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("CucumberFeature", CucumberFeatureTemplateFactory.getInstance());
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.metadata.DecoratorFactory} instance using given generator.
     * @param generator the generator to use.
     * @return the decorator factory.
     */
    @NotNull
    @Override
    protected DecoratorFactory retrieveDecoratorFactory(@NotNull final PerRepositoryTemplateGenerator generator)
    {
        return generator.getDecoratorFactory();
    }

    /**
     * Gathers the repository information from the scenario.
     * @param repositoryTable the repository information in tabular format.
     */
    @Given("^a schema with the following information:$")
    public void defineSchema(@NotNull final DataTable repositoryTable)
    {
        defineRepository(repositoryTable.asMaps());
    }

    /**
     * Gathers information about the tables defined in the repository.
     * @param dataTable the tabular data about the repository tables.
     */
    @Given("^a schema with the following tables:$")
    public void defineTables(@NotNull final DataTable dataTable)
    {
        defineTables(dataTable, getTables(), TableTestHelper.getInstance());
    }

    /**
     * Gathers information about the tables defined in the repository.
     * @param dataTable the tabular data about the repository tables.
     */
    protected void defineTables(
        @NotNull final DataTable dataTable,
        @NotNull final Map<String, Table> tables,
        @NotNull final TableTestHelper helper)
    {
        helper.defineInputTables(dataTable, tables);
        setTableNames(new ArrayList<String>(tables.keySet()));
    }

    /**
     * Defines the columns from the feature.
     * @param columnInfo the column information.
     */
    @SuppressWarnings("unused")
    @Given("^such tables have the following columns:$")
    public void defineInputColumns(@NotNull final DataTable columnInfo)
    {
        defineInputColumns(columnInfo, getTables(), TableTestHelper.getInstance());
    }

    /**
     * Defines the columns from the feature.
     * @param columnInfo the column information.
     * @param tables the tables.
     * @param helper the {@link TableTestHelper} instance.
     */
    protected void defineInputColumns(
        @NotNull final DataTable columnInfo,
        @NotNull final Map<String, Table> tables,
        @NotNull final TableTestHelper helper)
    {
        @NotNull final List<Table> newTables = helper.defineInputColumns(columnInfo, tables);

        for (@Nullable final Table table: newTables)
        {
            if (table != null)
            {
                tables.put(table.getName(), table);
            }
        }
    }

    /**
     * Defines the foreign keys via cucumber features.
     * @param fkInfo such information.
     */
    @SuppressWarnings("unused")
    @Given("^the schema defines the following foreign keys:$")
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
        @NotNull final Map<String, Table> tables,
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
    @And("^the following table values:$")
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
        @NotNull final Map<String, Table> tables,
        @NotNull final TableTestHelper helper)
    {
        helper.defineValues(values, tables);
    }

    /**
     * Generates a file with the information from the feature.
     * @param template the template.
     */
    @SuppressWarnings("unused")
    @When("^I generate with schema-wide (.*)\\.stg$")
    public void generateFile(@NotNull final String template)
    {
        generateFile(template, getRepositoryName(), getVendor(), getTableNames(), getOutputFiles());
    }

    /**
     * Wraps a list of {@link org.acmsl.queryj.metadata.vo.Table}s using given names.
     * @param tableNames the table names.
     * @return the table list.
     */
    @Override
    @NotNull
    protected List<Table> wrapTables(final List<String> tableNames)
    {
        return wrapTables(tableNames, getTables());
    }

    /**
     * Wraps a list of {@link org.acmsl.queryj.metadata.vo.Table}s using given names.
     * @param tableNames the table names.
     * @param tables the tables.
     * @return the table list.
     */
    protected List<Table> wrapTables(final List<String> tableNames, @NotNull final Map<String, Table> tables)
    {
        return new ArrayList<Table>(tables.values());

    }

    /**
     * Checks whether the generated Cucumber file is valid.
     * @param outputName the output name.
     */
    @Then("^the generated Cucumber file (.*) is valid$")
    public void checkCucumberFeature(@NotNull final String outputName)
    {
        @Nullable final File file = retrieveOutputFile(outputName);

        Assert.assertNotNull("Invalid file : " + outputName, file);
    }

    @NotNull
    @Override
    public String toString()
    {
        @NotNull final StringBuilder result = new StringBuilder("PerRepositoryTemplatesTest{");

        result.append("tableNames=");
        result.append(immutableGetTableNames());
        result.append(", repository='");
        result.append(getRepositoryName());
        result.append("', dbUser='");
        result.append(getDbUser());
        result.append("', vendor='");
        result.append(getVendor());
        result.append("' }");

        return result.toString();
    }
}
