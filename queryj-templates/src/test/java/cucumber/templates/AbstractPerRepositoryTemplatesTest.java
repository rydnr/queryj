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
 * Filename: AbstractPerRepositoryTemplatesTest.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Base test logic for per-repository tests.
 *
 * Date: 5/26/13
 * Time: 5:22 PM
 *
 */
package cucumber.templates;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.metadata.vo.TableIncompleteValueObject;
import org.acmsl.queryj.templates.BasePerRepositoryTemplate;
import org.acmsl.queryj.templates.BasePerRepositoryTemplateFactory;
import org.acmsl.queryj.templates.BasePerRepositoryTemplateGenerator;
import org.acmsl.queryj.tools.QueryJBuildException;

/*
 * Importing ACM S.L. classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/*
 * Importing JetBrains annotations.
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
 * Base test logic for per-repository tests.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/05/26
 */
public abstract class AbstractPerRepositoryTemplatesTest
    <G extends BasePerRepositoryTemplateGenerator, F extends BasePerRepositoryTemplateFactory>
    extends AbstractTemplatesTest<G, F>
{
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
     * Creates an empty instance.
     */
    public AbstractPerRepositoryTemplatesTest()
    {
        immutableSetTableNames(new ArrayList<String>());
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
    @SuppressWarnings("unused")
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
     * Gathers the repository information from the scenario.
     * @param entries the repository information.
     */
    protected void defineRepository(@NotNull final List<Map<String,String>> entries)
    {
        Assert.assertTrue("Missing repository information", entries.size() > 0);
        Assert.assertTrue("Too many repositories defined", entries.size() == 1);

        @Nullable final Map<String, String> repositoryInfo = entries.get(0);

        Assert.assertNotNull(repositoryInfo);

        @Nullable final String name = repositoryInfo.get("name");
        Assert.assertNotNull("Missing repository name", name);
        setRepositoryName(name);

        @Nullable final String user = repositoryInfo.get("user");
        Assert.assertNotNull("Missing repository user", user);
        setDbUser(user);

        @Nullable final String vendor = repositoryInfo.get("vendor");
        Assert.assertNotNull("Missing repository vendor", vendor);
        setVendor(vendor);
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
        final G generator =
            retrieveTemplateGenerator(templateName);

        Assert.assertNotNull("No template generator found for " + templateName, generator);

        final F templateFactory = retrieveTemplateFactory(templateName);

        Assert.assertNotNull("No template factory found for " + templateName, templateFactory);

        final BasePerRepositoryTemplate template =
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
     * Wraps a list of {@link Table tables} using given names.
     * @param tableNames the table names.
     * @return the table list.
     */
    protected List<Table> wrapTables(final List<String> tableNames)
    {
        @NotNull final List<Table> result = new ArrayList<Table>(tableNames.size());

        for (@Nullable final String tableName : tableNames)
        {
            if (tableName != null)
            {
                result.add(
                    new TableIncompleteValueObject(tableName, null));
            }
        }

        return result;
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
