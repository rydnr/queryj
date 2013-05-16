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
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.metadata.vo.TableIncompleteValueObject;
import org.acmsl.queryj.templates.BasePerRepositoryTemplate;
import org.acmsl.queryj.templates.BasePerRepositoryTemplateFactory;
import org.acmsl.queryj.templates.BasePerRepositoryTemplateGenerator;
import org.acmsl.queryj.templates.RepositoryDAOFactoryTemplateFactory;
import org.acmsl.queryj.templates.RepositoryDAOFactoryTemplateGenerator;
import org.acmsl.queryj.templates.RepositoryDAOTemplateFactory;
import org.acmsl.queryj.templates.RepositoryDAOTemplateGenerator;
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
import org.acmsl.queryj.templates.dao.StatisticsProviderTemplateFactory;
import org.acmsl.queryj.templates.dao.StatisticsProviderTemplateGenerator;
import org.acmsl.queryj.templates.dao.ThreadLocalBagTemplateFactory;
import org.acmsl.queryj.templates.dao.ThreadLocalBagTemplateGenerator;
import org.acmsl.queryj.tools.QueryJBuildException;

/*
 * Importing ACM S.L. Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

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
    extends AbstractTemplatesTest<BasePerRepositoryTemplateGenerator, BasePerRepositoryTemplateFactory>
{
    /**
     * The package name.
     */
    public static final String PACKAGE_NAME = "com.foo.bar";

    /**
     * The DAO package name.
     */
    public static final String DAO_PACKAGE_NAME = "com.foo.bar.dao";

    /**
     * The repository.
     */
    private String m__strRepository;

    /**
     * The db user.
     */
    private String m__strDbUser;

    /**
     * The vendor.
     */
    private String m__strVendor;

    /**
     * The table names.
     */
    private List<String> m__lTableNames;

    /**
     * Creates an instance.
     */
    public PerRepositoryTemplatesTest()
    {
        immutableSetTableNames(new ArrayList<String>());

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
     * Specifies the repository name.
     * @param name such name.
     */
    protected final void immutableSetRepositoryName(@NotNull final String name)
    {
        this.m__strRepository = name;
    }

    /**
     * Specifies the repository name.
     * @param name such name.
     */
    protected void setRepositoryName(@NotNull final String name)
    {
        immutableSetRepositoryName(name);
    }

    /**
     * Retrieves the repository name.
     * @return such name.
     */
    @NotNull
    public String getRepositoryName()
    {
        return m__strRepository;
    }

    /**
     * Specifies the db user.
     * @param user the database username.
     */
    protected final void immutableSetDbUser(@NotNull final String user)
    {
        this.m__strDbUser = user;
    }

    /**
     * Specifies the db user.
     * @param user the database username.
     */
    protected void setDbUser(@NotNull final String user)
    {
        immutableSetDbUser(user);
    }

    /**
     * Retrieves the db user.
     * @return the database username.
     */
    public String getDbUser()
    {
        return this.m__strDbUser;
    }

    /**
     * Specifies the vendor.
     * @param vendor the database vendor.
     */
    protected final void immutableSetVendor(@NotNull final String vendor)
    {
        this.m__strVendor = vendor;
    }

    /**
     * Specifies the vendor.
     * @param vendor the database vendor.
     */
    protected void setVendor(@NotNull final String vendor)
    {
        immutableSetVendor(vendor);
    }

    /**
     * Specifies the vendor.
     * @return the database vendor.
     */
    public String getVendor()
    {
        return this.m__strVendor;
    }

    /**
     * Specifies the table names.
     * @param tableNames such names.
     */
    protected final void immutableSetTableNames(@NotNull final List<String> tableNames)
    {
        m__lTableNames = tableNames;
    }

    /**
     * Specifies the table names.
     * @param tableNames such names.
     */
    protected void setTableNames(@NotNull final List<String> tableNames)
    {
        immutableSetTableNames(tableNames);
    }

    /**
     * Retrieves the table names.
     * @return such names.
     */
    protected final List<String> immutableGetTableNames()
    {
        return m__lTableNames;
    }

    /**
     * Retrieves the table names.
     * @return such names.
     */
    public List<String> getTableNames()
    {
        return new ArrayList<String>(immutableGetTableNames());
    }

    /**
     * Retrieves the {@link DecoratorFactory} instance using given generator.
     * @param generator the generator to use.
     * @return the decorator factory.
     */
    @NotNull
    @Override
    protected DecoratorFactory retrieveDecoratorFactory(@NotNull final BasePerRepositoryTemplateGenerator generator)
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
        @NotNull final List<Map<String,String>> entries = repositoryTable.asMaps();

        Assert.assertTrue("Missing repository information", entries.size() > 0);
        Assert.assertTrue("Too many repositories defined", entries.size() == 1);

        Map<String, String> repositoryInfo = entries.get(0);

        Assert.assertNotNull(repositoryInfo);

        String name = repositoryInfo.get("name");
        Assert.assertNotNull("Missing repository name", name);
        setRepositoryName(name);

        String user = repositoryInfo.get("user");
        Assert.assertNotNull("Missing repository user", user);
        setDbUser(user);

        String vendor = repositoryInfo.get("vendor");
        Assert.assertNotNull("Missing repository vendor", vendor);
        setVendor(vendor);
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
    protected void defineTables(@NotNull final List<Map<String, String>> entries, List<String> tableList)
    {
        Assert.assertTrue("Missing table information", entries.size() > 0);

        for (@NotNull Map<String, String> entry : entries)
        {
            String tableName = entry.get("table");
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
        generateFile(template, getRepositoryName(), getVendor(), getTableNames(), getOutputFiles());
    }

    /**
     * Generates a file with the information from the feature.
     * @param templateName the template.
     * @param repository the repository.
     * @param tables the tables.
     * @param outputFiles the output files.
     */
    @SuppressWarnings("unchecked")
    protected void generateFile(
        @NotNull final String templateName,
        @NotNull final String repository,
        @NotNull final String engine,
        @NotNull final List<String> tables,
        @NotNull final Map<String, File> outputFiles)
    {
        BasePerRepositoryTemplateGenerator generator =
            retrieveTemplateGenerator(templateName);

        Assert.assertNotNull("No template generator found for " + templateName, generator);

        BasePerRepositoryTemplateFactory templateFactory = retrieveTemplateFactory(templateName);

        Assert.assertNotNull("No template factory found for " + templateName, templateFactory);

        BasePerRepositoryTemplate template =
            templateFactory.createTemplate(
                retrieveMetadataManager(engine, tables, wrapTables(tables)),
                retrieveCustomSqlProvider(),
                retrieveDecoratorFactory(generator),
                DAO_PACKAGE_NAME,
                PACKAGE_NAME,
                repository,
                "", // header
                false, // marker
                false, // jmx
                tables,
                "java:comp/env/db",
                false, // disable generation timestamps
                false, // disable NotNull annotations
                true); // disable checkThread.org annotations

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
            repository,
            new File(outputDir, generator.retrieveTemplateFileName(template.getTemplateContext())));
    }

    /**
     * Wraps a list of {@link Table}s using given names.
     * @param tableNames the table names.
     * @return the table list.
     */
    protected List<Table> wrapTables(final List<String> tableNames)
    {
        @NotNull final List<Table> result = new ArrayList<Table>(tableNames.size());

        for (String tableName : tableNames)
        {
            result.add(
                new TableIncompleteValueObject(tableName, null));
        }

        return result;
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
        File file = retrieveOutputFile(outputName.concat("-queryj.properties"));

        if (file != null)
        {
            Properties properties = readPropertiesFile(file);

            Assert.assertEquals(
                "Invalid number of entries in " + file.getAbsolutePath(),
                tableNames.size(),
                properties.size());

            for (String tableName : tableNames)
            {
                String singularTableName = toSingular(tableName, grammarUtils);

                String key =
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
    protected String capitalize(
        @NotNull final String value, @NotNull final StringUtils stringUtils)
    {
        return stringUtils.capitalize(value);
    }
}
