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
 * Filename: PerRepositoryTemplatesTest.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Maps steps in Cucumber scenarios for testing per-repository
 *              templates.
 *
 * Date: 2013/05/05
 * Time: 17:44
 *
 */
package cucumber.templates;

/*
 * Importing project classes.
 */
import cucumber.templates.xml.BeanElement;
import cucumber.templates.xml.DataAccessContextLocalTestHelper;
import org.acmsl.queryj.templates.dao.BasePreparedStatementCreatorTemplateFactory;
import org.acmsl.queryj.templates.dao.BasePreparedStatementCreatorTemplateGenerator;
import org.acmsl.queryj.templates.dao.BaseRepositoryDAOFactoryTemplateFactory;
import org.acmsl.queryj.templates.dao.BaseRepositoryDAOFactoryTemplateGenerator;
import org.acmsl.queryj.templates.dao.BaseRepositoryDAOTemplateFactory;
import org.acmsl.queryj.templates.dao.BaseRepositoryDAOTemplateGenerator;
import org.acmsl.queryj.templates.dao.BaseResultSetExtractorTemplateFactory;
import org.acmsl.queryj.templates.dao.BaseResultSetExtractorTemplateGenerator;
import org.acmsl.queryj.templates.dao.ConfigurationPropertiesTemplateFactory;
import org.acmsl.queryj.templates.dao.ConfigurationPropertiesTemplateGenerator;
import org.acmsl.queryj.templates.dao.DAOChooserTemplateFactory;
import org.acmsl.queryj.templates.dao.DAOChooserTemplateGenerator;
import org.acmsl.queryj.templates.dao.DAOListenerImplTemplateFactory;
import org.acmsl.queryj.templates.dao.DAOListenerImplTemplateGenerator;
import org.acmsl.queryj.templates.dao.DAOListenerTemplateFactory;
import org.acmsl.queryj.templates.dao.DAOListenerTemplateGenerator;
import org.acmsl.queryj.templates.dao.DataAccessContextLocalTemplateFactory;
import org.acmsl.queryj.templates.dao.DataAccessContextLocalTemplateGenerator;
import org.acmsl.queryj.templates.dao.DataAccessManagerTemplateFactory;
import org.acmsl.queryj.templates.dao.DataAccessManagerTemplateGenerator;
import org.acmsl.queryj.templates.dao.JdbcTemplateTemplateFactory;
import org.acmsl.queryj.templates.dao.JdbcTemplateTemplateGenerator;
import org.acmsl.queryj.templates.dao.JndiUtilsTemplateFactory;
import org.acmsl.queryj.templates.dao.JndiUtilsTemplateGenerator;
import org.acmsl.queryj.templates.dao.RepositoryDAOFactoryTemplateFactory;
import org.acmsl.queryj.templates.dao.RepositoryDAOFactoryTemplateGenerator;
import org.acmsl.queryj.templates.dao.RepositoryDAOTemplateFactory;
import org.acmsl.queryj.templates.dao.RepositoryDAOTemplateGenerator;
import org.acmsl.queryj.templates.dao.StatisticsProviderTemplateFactory;
import org.acmsl.queryj.templates.dao.StatisticsProviderTemplateGenerator;
import org.acmsl.queryj.templates.dao.ThreadLocalBagTemplateFactory;
import org.acmsl.queryj.templates.dao.ThreadLocalBagTemplateGenerator;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.api.PerRepositoryTemplateFactory;
import org.acmsl.queryj.api.PerRepositoryTemplateGenerator;

/*
 * Importing ACM S.L. Commons classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.digester.Digester;

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
 * Importing SAX classes.
 */
import org.xml.sax.SAXException;

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Maps steps in Cucumber scenarios for testing per-repository templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/05/05
 */
@SuppressWarnings("unused")
public class PerRepositoryTemplatesTest
    extends AbstractPerRepositoryTemplatesTest<PerRepositoryTemplateGenerator, PerRepositoryTemplateFactory>
{
    /**
     * Creates an instance.
     */
    public PerRepositoryTemplatesTest()
    {
        GENERATOR_MAPPINGS.put("DataAccessManager", new DataAccessManagerTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("DataAccessManager", DataAccessManagerTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("BasePreparedStatementCreator", new BasePreparedStatementCreatorTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("BasePreparedStatementCreator", BasePreparedStatementCreatorTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("BaseRepositoryDAO", new BaseRepositoryDAOTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("BaseRepositoryDAO", BaseRepositoryDAOTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("BaseRepositoryDAOFactory", new BaseRepositoryDAOFactoryTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("BaseRepositoryDAOFactory", BaseRepositoryDAOFactoryTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("BaseResultSetExtractor", new BaseResultSetExtractorTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("BaseResultSetExtractor", BaseResultSetExtractorTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("DAOChooser", new DAOChooserTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("DAOChooser", DAOChooserTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("DAOListener", new DAOListenerTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("DAOListener", DAOListenerTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("DAOListenerImpl", new DAOListenerImplTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("DAOListenerImpl", DAOListenerImplTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("JdbcTemplate", new JdbcTemplateTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("JdbcTemplate", JdbcTemplateTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("JndiUtils", new JndiUtilsTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("JndiUtils", JndiUtilsTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("RepositoryDAO", new RepositoryDAOTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("RepositoryDAO", RepositoryDAOTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("RepositoryDAOFactory", new RepositoryDAOFactoryTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("RepositoryDAOFactory", RepositoryDAOFactoryTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("StatisticsProvider", new StatisticsProviderTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("StatisticsProvider", StatisticsProviderTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("ThreadLocalBag", new ThreadLocalBagTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("ThreadLocalBag", ThreadLocalBagTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("ConfigurationProperties", new ConfigurationPropertiesTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("ConfigurationProperties", ConfigurationPropertiesTemplateFactory.getInstance());
        GENERATOR_MAPPINGS.put("DataAccessContextLocal", new DataAccessContextLocalTemplateGenerator(false, 1));
        FACTORY_MAPPINGS.put("DataAccessContextLocal", DataAccessContextLocalTemplateFactory.getInstance());
    }

    /**
     * Retrieves the {@link DecoratorFactory} instance using given generator.
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
    @Given("^a repository with the following information:$")
    public void defineRepository(@NotNull final DataTable repositoryTable)
    {
        defineRepository(repositoryTable.asMaps());
    }

    /**
     * Gathers information about the tables defined in the repository.
     * @param dataTable the tabular data about the repository tables.
     */
    @Given("^a repository with the following tables:$")
    public void defineTables(@NotNull final DataTable dataTable)
    {
        defineTables(dataTable.asMaps(), immutableGetTableNames());
    }

    /**
     * Gathers information about the tables defined in the repository.
     * @param entries the tabular data about the repository tables.
     * @param tableList the table collection.
     */
    protected void defineTables(
        @NotNull final List<Map<String, String>> entries, @NotNull final List<String> tableList)
    {
        Assert.assertTrue("Missing table information", entries.size() > 0);

        for (@NotNull final Map<String, String> entry : entries)
        {
            @Nullable final String tableName = entry.get("table");
            Assert.assertNotNull("Missing table name", tableName);
            tableList.add(tableName);
        }
    }

    /**
     * Generates a file with the information from the feature.
     * @param template the template.
     */
    @SuppressWarnings("unused")
    @When("^I generate with per-repository (.*)\\.stg$")
    public void generateFile(@NotNull final String template)
    {
        generateFile(
            template,
            getRepositoryName(),
            getVendor(),
            getTableNames(),
            getOutputFiles(),
            retrieveCustomSqlProvider(getSqlList(), getParameters()));
    }

    /**
     * Checks the generated file compiles.
     * @param outputName the name of the output file.
     */
    @SuppressWarnings("unused")
    @Then("^the generated per-repository (.*) file compiles successfully")
    public void checkGeneratedFileCompiles(@NotNull final String outputName)
    {
        checkGeneratedFilesCompile(outputName, getOutputFiles());
    }

    /**
     * Checks whether the generated properties file is valid.
     * @param outputName the output name.
     */
    @Then("^the generated properties (.*)-queryj.properties is valid$")
    public void checkPropertiesIsValid(@NotNull final String outputName)
    {
        checkPropertiesIsValid(
            outputName,
            getOutputFiles(),
            getTableNames(),
            getVendor(),
            EnglishGrammarUtils.getInstance(),
            StringUtils.getInstance());
    }

    /**
     * Checks whether the generated properties file is valid.
     * @param outputName the output name.
     * @param outputFiles the output files.
     * @param tableNames the table names.
     * @param vendor the vendor.
     * @param grammarUtils the {@link EnglishGrammarUtils} instance.
     */
    protected void checkPropertiesIsValid(
        @NotNull final String outputName,
        @NotNull final Map<String,File> outputFiles,
        @NotNull final List<String> tableNames,
        @NotNull final String vendor,
        @NotNull final EnglishGrammarUtils grammarUtils,
        @NotNull final StringUtils stringUtils)
    {
        checkPropertiesFiles(outputName, outputFiles);
        @Nullable final File file = retrieveOutputFile(outputName.concat("-queryj.properties"));

        if (file != null)
        {
            @Nullable final Properties properties = readPropertiesFile(file);

            Assert.assertNotNull("Invalid properties file : " + file.getAbsolutePath(), properties);

            Assert.assertEquals(
                "Invalid number of entries in " + file.getAbsolutePath(),
                tableNames.size(),
                properties.size());

            for (@Nullable final String tableName : tableNames)
            {
                Assert.assertNotNull(tableName);

                @NotNull final String singularTableName = toSingular(tableName, grammarUtils);

                @NotNull final String key =
                    getRepositoryName() + "." + singularTableName + ".dao";

                Assert.assertTrue(
                    "Missing entry in " + file.getAbsolutePath(),
                    properties.containsKey(key));

                Assert.assertEquals(
                    "Invalid entry " + key + " in " + file.getAbsolutePath(),
                      DAO_PACKAGE_NAME
                    + ".rdb."
                    + vendor.toLowerCase(Locale.getDefault())
                    + "." + vendor
                    + capitalize(singularTableName, stringUtils)
                    + "DAOFactory",
                    properties.getProperty(key));
            }
        }
    }

    /**
     * Converts given word to singular.
     * @param value the word.
     * @param grammarUtils the {@link EnglishGrammarUtils} instance.
     * @return the singular word.
     */
    @NotNull
    protected String toSingular(
        @NotNull final String value, @NotNull final EnglishGrammarUtils grammarUtils)
    {
        return grammarUtils.getSingular(value);
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @param stringUtils the {@link StringUtils} instance.
     * @return the capitalized value.
     */
    @NotNull
    protected String capitalize(
        @NotNull final String value, @NotNull final StringUtils stringUtils)
    {
        return stringUtils.capitalize(value);
    }

    /**
     * Checks whether the generated properties file is valid.
     * @param outputName the output name.
     */
    @Then("^the generated Spring file (.*) is valid$")
    public void checkDataAccessContextLocalIsValid(@NotNull final String outputName)
    {
        @Nullable final File file = retrieveOutputFile(outputName);

        Assert.assertNotNull("Invalid file : " + outputName, file);

        @NotNull final List<BeanElement> beans = parseFile(file);

        Assert.assertEquals(
            "Wrong number of <bean> elements",
            1 /* jndi */ + 1 /* transaction manager */ + 2*(getTableNames().size()),
            beans.size());
    }

    /**
     * Parses given file.
     * @param file the file to parse.
     * @return the list of {@link BeanElement}s in the file.
     */
    @NotNull
    protected List<BeanElement> parseFile(@NotNull final File file)
    {
        return parseFile(file, buildDataAccessContextLocalDigester());
    }

    /**
     * Parses given file.
     * @param file the file to parse.
     * @param digester the {@link Digester} instance.
     * @return the list of {@link BeanElement}s in the file.
     */
    @NotNull
    protected List<BeanElement> parseFile(@NotNull final File file, @NotNull final Digester digester)
    {
        @NotNull final List<BeanElement> result =
            new ArrayList<BeanElement>(
                1 /* jndi */ + 1 /* transaction manager */ + 2*(getTableNames().size()));

        digester.push(result);

        try
        {
            digester.parse(file);
        }
        catch (@NotNull final IOException e)
        {
            Assert.fail(e.getMessage());
        }
        catch (@NotNull final SAXException e)
        {
            Assert.fail(e.getMessage());
        }

        return result;
    }

    /**
     * Creates and configures a new Digester instance.
     * @return such instance.
     */
    @NotNull
    protected Digester buildDataAccessContextLocalDigester()
    {
        return buildDataAccessContextLocalDigester(DataAccessContextLocalTestHelper.getInstance());
    }

    /**
     * Creates and configures a new Digester instance.
     * @param helper the {@link DataAccessContextLocalTestHelper} instance.
     * @return such instance.
     */
    @NotNull
    protected Digester buildDataAccessContextLocalDigester(@NotNull final DataAccessContextLocalTestHelper helper)
    {
        return helper.buildDataAccessContextLocalDigester();
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
