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
 * Filename: QueryJMojo.java
 *
 * Author: Jose San Leandro Armendariz/Jose Juan.
 *
 * Description: Executes QueryJ.
 */
package org.acmsl.queryj.tools.maven;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.tools.ant.AntExternallyManagedFieldsElement;
import org.acmsl.queryj.tools.ant.AntFieldElement;
import org.acmsl.queryj.tools.ant.AntTableElement;
import org.acmsl.queryj.tools.ant.AntTablesElement;
import org.acmsl.queryj.tools.ant.QueryJTask;

/*
 * Importing some Maven classes.
 */
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;

/*
 * Importing some Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Executes QueryJ.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @goal queryj
 * @execute phase="generate-sources"
 * @threadSafe
 */
@SuppressWarnings("unused")
@ThreadSafe
public class QueryJMojo
    extends AbstractMojo
    implements Mojo
{
    /**
     * The location of pom.properties within the jar file.
     */
    protected static final String POM_PROPERTIES_LOCATION =
        "META-INF/maven/org.acmsl/queryj/pom.properties";

    /**
     * The driver.
     * @parameter property="driver" @required
     */
    private String m__strDriver;

    /**
     * The url.
     * @parameter property="url" @required
     */
    private String m__strUrl;

    /**
     * The user name.
     * @parameter property="username" @required
     */
    private String m__strUsername;

    /**
     * The password.
     * @parameter property="password" @required
     */
    private String m__strPassword;

    /**
     * The catalog.
     * @parameter property="catalog"
     */
    private String catalog;

    /**
     * The schema.
     * @parameter property="schema" @required
     */
    private String schema;

    /**
     * The repository.
     * @parameter property="repository" @required
     */
    private String m__strRepository;

    /**
     * The package name.
     * @parameter property="packageName" @required
     */
    private String m__strPackageName;

    /**
     * The output directory.
     * @parameter property="outputDir"
     */
    private File m__OutputDir;

    /**
     * The data source.
     * @parameter property="jndiDataSource"
     */
    private String m__strJndiDataSource;

    /**
     * The sql xml file.
     * @parameter property="sqlXmlFile"
     */
    private File m__SqlXmlFile;

    /**
     * The customized sql model.
     * @parameter property="customSqlModel"
     */
    private String m__strCustomSqlModel;

    /**
     * The xml dao implementation flag.
     * @parameter property="generateXmlDAOImplementation"
     */
    private Boolean m__bGenerateXmlDAOImplementation = Boolean.FALSE;

    /**
     * The mock dao implementation flag.
     * @parameter property="generateMockDAOImplementation"
     */
    private Boolean m__bGenerateMockDAOImplementation = Boolean.FALSE;

    /**
     * The test generation flag.
     * @parameter property="generateTests"
     */
    private Boolean m__bGenerateTests = Boolean.FALSE;

    /**
     * The header file.
     * @parameter property="headerFile"
     */
    private File m__HeaderFile;

    /**
     * The extract functions flag.
     * @parameter property="extractFunctions"
     */
    private Boolean m__bExtractFunctions = Boolean.FALSE;

    /**
     * The extract procedures flag.
     * @parameter property="extractProcedures"
     */
    private Boolean m__bExtractProcedures = Boolean.FALSE;

    /**
     * The list of external managed fields
     * @parameter
     */
    private ExternallyManagedField[] externallyManagedFields = new ExternallyManagedField[0];

    /**
     * The grammar folder.
     * @parameter property="grammarFolder"
     */
    private File m__GrammarFolder;

    /**
     * The grammar bundle.
     * @parameter property="grammarName"
     */
    private String m__strGrammarName;

    /**
     * The grammar suffix.
     * @parameter property="grammarSuffix"
     */
    private String m__strGrammarSuffix;

    /**
     * Whether to use <code>main</code> and <code>test</code> subfolders
     * within <code>outputDir</code>.
     */
    private Boolean m__bOutputSubfolders = Boolean.FALSE;
//     * @parameter property="useOutputSubfolders"

    /**
     * The list of tables.
     * @parameter
     */
    private Table[] m__aTables = new Table[0];
    // @*** parameter property="tables"

    /**
     * The file encoding.
     * @parameter property="encoding" default-value="${project.build.sourceEncoding}"
     */
    private String m__strEncoding;

    /**
 	* The current build session instance. This is used for toolchain manager API calls.
 	*
 	* @parameter default-value="${session}"
 	* @required
 	* @readonly
 	*/
 	private MavenSession session;

    /**
     * Whether to use template caching.
     * @parameter property="disableCaching"
     */
    private Boolean m__bDisableCaching = false;

    /**
     * Specifies the driver.
     * @param driver such value.
     */
    protected final void immutableSetDriver(@NotNull final String driver)
    {
        m__strDriver = driver;
    }

    /**
     * Specifies the driver.
     * @param driver such value.
     */
    public void setDriver(@NotNull final String driver)
    {
        immutableSetDriver(driver);
    }

    /**
     * Returns the driver.
     * @return such value.
     */
    @Nullable
    protected final String immutableGetDriver()
    {
        return m__strDriver;
    }

    /**
     * Returns the driver.
     * @return such value.
     */
    @Nullable
    protected String getDriver()
    {
        String result = System.getProperty("queryj.driver");

        if (result == null)
        {
            result = immutableGetDriver();
        }

        return result;
    }

    /**
     * Specifies the url.
     * @param url the url.
     */
    protected final void immutableSetUrl(@NotNull final String url)
    {
        m__strUrl = url;
    }

    /**
     * Specifies the url.
     * @param url the url.
     */
    public void setUrl(@NotNull final String url)
    {
        immutableSetUrl(url);
    }

    /**
     * Returns the url.
     * @return such value.
     */
    @Nullable
    protected final String immutableGetUrl()
    {
        return m__strUrl;
    }

    /**
     * Returns the url.
     * @return such value.
     */
    @Nullable
    protected String getUrl()
    {
        String result = System.getProperty("queryj.url");

        if (result == null)
        {
            result = immutableGetUrl();
        }

        return result;
    }

    /**
     * Specifies the username.
     * @param username the new value.
     */
    protected final void immutableSetUsername(@NotNull final String username)
    {
        m__strUsername = username;
    }

    /**
     * Specifies the username.
     * @param username the new value.
     */
    public void setUsername(@NotNull final String username)
    {
        immutableSetUsername(username);
    }

    /**
     * Returns the user name.
     * @return such value.
     */
    @Nullable
    protected final String immutableGetUsername()
    {
        return m__strUsername;
    }

    /**
     * Returns the user name.
     * @return such value.
     */
    @Nullable
    protected String getUsername()
    {
        String result = System.getProperty("queryj.username");

        if (result == null)
        {
            result = immutableGetUsername();
        }

        return result;
    }

    /**
     * Specifies the password.
     * @param password the password.
     */
    protected final void immutableSetPassword(@NotNull final String password)
    {
        m__strPassword = password;
    }

    /**
     * Specifies the password.
     * @param password the password.
     */
    public void setPassword(@NotNull final String password)
    {
        immutableSetPassword(password);
    }

    /**
     * Returns the password.
     * @return such value.
     */
    @Nullable
    protected final String immutableGetPassword()
    {
        return m__strPassword;
    }

    /**
     * Returns the password.
     * @return such value.
     */
    @Nullable
    protected String getPassword()
    {
        String result = System.getProperty("queryj.password");

        if (result == null)
        {
            result = immutableGetPassword();
        }

        return result;
    }

    /**
     * Specifies the catalog.
     * @param catalog the catalog.
     */
    protected final void immutableSetCatalog(@NotNull final String catalog)
    {
        this.catalog = catalog;
    }

    /**
     * Specifies the catalog.
     * @param catalog the catalog.
     */
    public void setCatalog(@NotNull final String catalog)
    {
        immutableSetCatalog(catalog);
    }

    /**
     * Returns the catalog.
     * @return such value.
     */
    @Nullable
    protected final String immutableGetCatalog()
    {
        return catalog;
    }

    /**
     * Returns the catalog.
     * @return such value.
     */
    @Nullable
    protected String getCatalog()
    {
        String result = System.getProperty("queryj.catalog");

        if (result == null)
        {
            result = immutableGetCatalog();
        }

        return result;
    }

    /**
     * Specifies the schema.
     * @param schema the schema.
     */
    protected final void immutableSetSchema(@NotNull final String schema)
    {
        this.schema = schema;
    }

    /**
     * Specifies the schema.
     * @param schema the schema.
     */
    public void setSchema(@NotNull final String schema)
    {
        immutableSetSchema(schema);
    }

    /**
     * Returns the schema.
     * @return such value.
     */
    @Nullable
    protected final String immutableGetSchema()
    {
        return schema;
    }

    /**
     * Returns the schema.
     * @return such value, or an empty string if not initialized.
     */
    @NotNull
    protected String getSchema()
    {
        String result = System.getProperty("queryj.schema");

        if (result == null)
        {
            result = immutableGetSchema();
        }

        if  (result == null)
        {
            result = "";
        }

        return result;
    }

    /**
     * Specifies the repository.
     * @param repository the repository.
     */
    protected final void immutableSetRepository(@NotNull final String repository)
    {
        m__strRepository = repository;
    }

    /**
     * Specifies the repository.
     * @param repository the repository.
     */
    public void setRepository(@NotNull final String repository)
    {
        immutableSetRepository(repository);
    }

    /**
     * Returns the repository.
     * @return such value.
     */
    @Nullable
    protected final String immutableGetRepository()
    {
        return m__strRepository;
    }

    /**
     * Returns the repository.
     * @return such value, or an empty string if not initialized.
     */
    @NotNull
    protected String getRepository()
    {
        String result = System.getProperty("queryj.repository");

        if (result == null)
        {
            result = immutableGetRepository();
        }

        if  (result == null)
        {
            result = "";
        }

        return result;
    }

    /**
     * Specifies the package name.
     * @param packageName the package name.
     */
    protected final void immutableSetPackageName(@NotNull final String packageName)
    {
        m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName the package name.
     */
    public void setPackageName(@NotNull final String packageName)
    {
        immutableSetPackageName(packageName);
    }

    /**
     * Returns the package name.
     * @return such value.
     */
    @Nullable
    protected final String immutableGetPackageName()
    {
        return m__strPackageName;
    }

    /**
     * Returns the package name.
     * @return such value.
     */
    @Nullable
    protected String getPackageName()
    {
        String result = System.getProperty("queryj.package");

        if (result == null)
        {
            result = immutableGetPackageName();
        }

        return result;
    }

    /**
     * Specifies the output directory.
     * @param outputDir such directory.
     */
    protected final void immutableSetOutputDir(@NotNull final File outputDir)
    {
        m__OutputDir = outputDir;
    }

    /**
     * Specifies the output directory.
     * @param outputDir such directory.
     */
    public void setOutputDir(@NotNull final File outputDir)
    {
        immutableSetOutputDir(outputDir);
    }

    /**
     * Returns the output directory.
     * @return such directory.
     */
    @Nullable
    protected final File immutableGetOutputDir()
    {
        return m__OutputDir;
    }

    /**
     * Returns the output directory.
     * @return such directory.
     */
    @Nullable
    protected File getOutputDir()
    {
        File result;

        String aux = System.getProperty("queryj.package");

        if (aux == null)
        {
            result = immutableGetOutputDir();
        }
        else
        {
            result = new File(aux);
        }

        return result;
    }

    /**
     * Specifies the JNDI path to the data source.
     * @param jndiPath such path.
     */
    protected final void immutableSetJndiDataSource(@NotNull final String jndiPath)
    {
        m__strJndiDataSource = jndiPath;
    }

    /**
     * Specifies the JNDI path to the data source.
     * @param jndiPath such path.
     */
    public void setJndiDataSource(@NotNull final String jndiPath)
    {
        immutableSetJndiDataSource(jndiPath);
    }

    /**
     * Returns the JNDI location of the data source.
     * @return such value.
     */
    @Nullable
    protected final String immutableGetJndiDataSource()
    {
        return m__strJndiDataSource;
    }

    /**
     * Returns the JNDI location of the data source.
     * @return such value.
     */
    @Nullable
    protected String getJndiDataSource()
    {
        String result = System.getProperty("queryj.jndi");

        if (result == null)
        {
            result = immutableGetJndiDataSource();
        }

        return result;
    }

    /**
     * Specifies the XML file where the SQL queries are defined.
     * @param sqlFile such file.
     */
    protected final void immutableSetSqlXmlFile(@NotNull final File sqlFile)
    {
        m__SqlXmlFile = sqlFile;
    }

    /**
     * Specifies the XML file where the SQL queries are defined.
     * @param sqlFile such file.
     */
    public void setSqlXmlFile(@NotNull final File sqlFile)
    {
        immutableSetSqlXmlFile(sqlFile);
    }

    /**
     * Return the sql xml file.
     * @return such file.
     */
    @Nullable
    protected final File immutableGetSqlXmlFile()
    {
        return m__SqlXmlFile;
    }

    /**
     * Return the sql xml file.
     * @return such file.
     */
    @Nullable
    protected File getSqlXmlFile()
    {
        File result;

        String aux = System.getProperty("queryj.sqlXmlFile");

        if (aux == null)
        {
            result = immutableGetSqlXmlFile();
        }
        else
        {
            result = new File(aux);
        }

        return result;
    }

    /**
     * Specifies the custom SQL model.
     * @param model the model.
     */
    protected final void immutableSetCustomSqlModel(@NotNull final String model)
    {
        m__strCustomSqlModel = model;
    }

    /**
     * Specifies the custom SQL model.
     * @param model the model.
     */
    public final void setCustomSqlModel(@NotNull final String model)
    {
        immutableSetCustomSqlModel(model);
    }

    /**
     * Returns the custom sql model.
     * @return such value.
     */
    @Nullable
    protected final String immutableGetCustomSqlModel()
    {
        return m__strCustomSqlModel;
    }

    /**
     * Returns the custom sql model.
     * @return such value.
     */
    @Nullable
    protected String getCustomSqlModel()
    {
        String result = System.getProperty("queryj.customSqlModel");

        if (result == null)
        {
            result = immutableGetCustomSqlModel();
        }

        return result;
    }

    /**
     * Specifies whether to generate a XML-based DAO implementation.
     * @param flag such condition.
     */
    protected final void immutableSetGenerateXmlDAOImplementation(@NotNull final Boolean flag)
    {
        m__bGenerateXmlDAOImplementation = flag;
    }

    /**
     * Specifies whether to generate a XML-based DAO implementation.
     * @param flag such condition.
     */
    public void setGenerateXmlDAOImplementation(@NotNull final Boolean flag)
    {
        immutableSetGenerateXmlDAOImplementation(flag);
    }

    /**
     * Indicates if xml dao must be generated.
     * @return such condition.
     */
    @Nullable
    protected final Boolean immutableGetGenerateXmlDAOImplementation()
    {
        return m__bGenerateXmlDAOImplementation;
    }

    /**
     * Indicates if xml dao must be generated.
     * @return such condition.
     */
    @Nullable
    protected Boolean getGenerateXmlDAOImplementation()
    {
        Boolean result = null;

        String aux = System.getProperty("queryj.generateXmlDAO");

        if (aux == null)
        {
            result = immutableGetGenerateXmlDAOImplementation();
        }

        if (result == null)
        {
            result = Boolean.FALSE;
        }

        return result;
    }

    /**
     * Specifies whether to generate a Mock-based DAO implementation.
     * @param flag such condition.
     */
    protected final void immutableSetGenerateMockDAOImplementation(@NotNull final Boolean flag)
    {
        m__bGenerateMockDAOImplementation = flag;
    }

    /**
     * Specifies whether to generate a Mock-based DAO implementation.
     * @param flag such condition.
     */
    public void setGenerateMockDAOImplementation(@NotNull final Boolean flag)
    {
        immutableSetGenerateMockDAOImplementation(flag);
    }

    /**
     * Indicates if mock dao must be generated.
     * @return such condition.
     */
    @Nullable
    protected final Boolean immutableGetGenerateMockDAOImplementation()
    {
        return m__bGenerateMockDAOImplementation;
    }

    /**
     * Indicates if mock dao must be generated.
     * @return such condition.
     */
    @Nullable
    protected Boolean getGenerateMockDAOImplementation()
    {
        Boolean result = null;

        String aux = System.getProperty("queryj.generateMockDAO");

        if (aux == null)
        {
            result = immutableGetGenerateMockDAOImplementation();
        }

        if (result == null)
        {
            result = Boolean.FALSE;
        }

        return result;
    }

    /**
     * Specifies whether the tests should be generated.
     * @param flag such condition.
     */
    protected final void immutableSetGenerateTests(@NotNull final Boolean flag)
    {
        m__bGenerateTests = flag;
    }

    /**
     * Specifies whether the tests should be generated.
     * @param flag such condition.
     */
    public void setGenerateTests(@NotNull final Boolean flag)
    {
        immutableSetGenerateTests(flag);
    }

    /**
     * Indicates if tests must be generated.
     * @return such condition.
     */
    @Nullable
    protected final Boolean immutableGetGenerateTests()
    {
        return m__bGenerateTests;
    }

    /**
     * Indicates if tests must be generated.
     * @return such condition.
     */
    @Nullable
    protected Boolean getGenerateTests()
    {
        Boolean result = null;

        String aux = System.getProperty("queryj.generateTests");

        if (aux == null)
        {
            result = immutableGetGenerateTests();
        }

        if (result == null)
        {
            result = Boolean.FALSE;
        }

        return result;
    }

    /**
     * Specifies the header file.
     * @param file such file.
     */
    protected final void immutableSetHeaderFile(@NotNull final File file)
    {
        m__HeaderFile = file;
    }

    /**
     * Specifies the header file.
     * @param file such file.
     */
    public void setHeaderFile(@NotNull final File file)
    {
        immutableSetHeaderFile(file);
    }

    /**
     * Returns the header file.
     * @return such file.
     */
    @Nullable
    protected final File immutableGetHeaderFile()
    {
        return m__HeaderFile;
    }

    /**
     * Returns the header file.
     * @return such file.
     */
    @Nullable
    protected File getHeaderFile()
    {
        File result;

        String aux = System.getProperty("queryj.headerFile");

        if (aux == null)
        {
            result = immutableGetHeaderFile();
        }
        else
        {
            result = new File(aux);
        }

        return result;
    }

    /**
     * Specifies whether database functions must be generated.
     * @param flag such condition.
     */
    protected final void immutableSetExtractFunctions(@NotNull final Boolean flag)
    {
        m__bExtractFunctions = flag;
    }

    /**
     * Specifies whether database functions must be generated.
     * @param flag such condition.
     */
    public void setExtractFunctions(@NotNull final Boolean flag)
    {
        immutableSetExtractFunctions(flag);
    }

    /**
     * Indicates if functions must be extracted.
     * @return such condition.
     */
    @Nullable
    protected final Boolean immutableGetExtractFunctions()
    {
        return m__bExtractFunctions;
    }

    /**
     * Indicates if functions must be extracted.
     * @return such condition.
     */
    @Nullable
    protected Boolean getExtractFunctions()
    {
        Boolean result = null;

        String aux = System.getProperty("queryj.extractFunctions");

        if (aux == null)
        {
            result = immutableGetExtractFunctions();
        }

        if (result == null)
        {
            result = Boolean.FALSE;
        }

        return result;
    }

    /**
     * Specifies whether database procedures must be generated.
     * @param flag such condition.
     */
    protected final void immutableSetExtractProcedures(@NotNull final Boolean flag)
    {
        m__bExtractProcedures = flag;
    }

    /**
     * Specifies whether database procedures must be generated.
     * @param flag such condition.
     */
    public void setExtractProcedures(@NotNull final Boolean flag)
    {
        immutableSetExtractProcedures(flag);
    }

    /**
     * Indicates if procedures must be extracted.
     * @return such condition.
     */
    @Nullable
    protected final Boolean immutableGetExtractProcedures()
    {
        return m__bExtractProcedures;
    }

    /**
     * Indicates if procedures must be extracted.
     * @return such condition.
     */
    @Nullable
    protected Boolean getExtractProcedures()
    {
        Boolean result = null;

        String aux = System.getProperty("queryj.extractProcedures");

        if (aux == null)
        {
            result = immutableGetExtractProcedures();
        }

        if (result == null)
        {
            result = Boolean.FALSE;
        }

        return result;
    }

    /**
     * Specifies the externally managed fields.
     * @param fields such fields.
     */
    protected final void immutableSetExternallyManagedFields(
        @NotNull final ExternallyManagedField[] fields)
    {
        externallyManagedFields = fields;
    }

    /**
     * Specifies the externally managed fields.
     * @param fields such fields.
     */
    public void setExternallyManagedFields(@NotNull final ExternallyManagedField[] fields)
    {
        immutableSetExternallyManagedFields(fields);
    }

    /**
     * Returns the externally managed fields.
     * @return such fields.
     */
    @NotNull
    protected final ExternallyManagedField[] immutableGetExternallyManagedFields()
    {
//        return m__aExternallyManagedFields;
        return externallyManagedFields;
    }

    /**
     * Returns the externally managed fields.
     * @return such fields.
     */
    @NotNull
    protected ExternallyManagedField[] getExternallyManagedFields()
    {
        return immutableGetExternallyManagedFields();
    }

    /**
     * Specifies the grammar folder.
     * @param folder such folder.
     */
    protected final void immutableSetGrammarFolder(@NotNull final File folder)
    {
        m__GrammarFolder = folder;
    }

    /**
     * Specifies the grammar folder.
     * @param folder such folder.
     */
    @SuppressWarnings("unused")
    public void setGrammarFolder(@NotNull final File folder)
    {
        immutableSetGrammarFolder(folder);
    }

    /**
     * Returns the grammar folder.
     * @return such folder.
     */
    @Nullable
    protected final File immutableGetGrammarFolder()
    {
        return m__GrammarFolder;
    }

    /**
     * Returns the grammar bundle.
     * @return such resource.
     */
    @Nullable
    protected File getGrammarFolder()
    {
        File result;

        String aux = System.getProperty("queryj.grammarFolder");

        if (aux == null)
        {
            result = immutableGetGrammarFolder();
        }
        else
        {
            result = new File(aux);
        }

        return result;
    }

    /**
     * Specifies the grammar bundle.
     * @param bundle such bundle.
     */
    protected final void immutableSetGrammarName(@NotNull final String bundle)
    {
        m__strGrammarName = bundle;
    }

    /**
     * Specifies the grammar bundle.
     * @param bundle such bundle.
     */
    @SuppressWarnings("unused")
    public void setGrammarName(@NotNull final String bundle)
    {
        immutableSetGrammarName(bundle);
    }

    /**
     * Returns the grammar bundle.
     * @return such resource.
     */
    @Nullable
    protected final String immutableGetGrammarName()
    {
        return m__strGrammarName;
    }

    /**
     * Returns the grammar bundle.
     * @return such resource.
     */
    @Nullable
    protected String getGrammarName()
    {
        String result = System.getProperty("queryj.grammarName");

        if (result == null)
        {
            result = immutableGetGrammarName();
        }

        return result;
    }

    /**
     * Specifies the grammar suffix.
     * @param suffix such suffix.
     */
    protected final void immutableSetGrammarSuffix(@NotNull final String suffix)
    {
        m__strGrammarSuffix = suffix;
    }

    /**
     * Specifies the grammar suffix.
     * @param suffix such suffix.
     */
    @SuppressWarnings("unused")
    public void setGrammarSuffix(@NotNull final String suffix)
    {
        immutableSetGrammarSuffix(suffix);
    }

    /**
     * Returns the grammar suffix.
     * @return such resource.
     */
    @Nullable
    protected final String immutableGetGrammarSuffix()
    {
        return m__strGrammarSuffix;
    }

    /**
     * Returns the grammar suffix.
     * @return such resource.
     */
    @Nullable
    protected String getGrammarSuffix()
    {
        String result = System.getProperty("queryj.grammarSuffix");

        if (result == null)
        {
            result = immutableGetGrammarSuffix();
        }

        return result;
    }

    /**
     * Specifies whether to create <code>main</code> and <code>test</code>
     * subfolders withing <code>outputDir</code>. Defaults to false.
     * @param flag such condition.
     */
    protected final void immutableSetUseOutputSubfolders(@NotNull final Boolean flag)
    {
        m__bOutputSubfolders = flag;
    }

    /**
     * Specifies whether to create <code>main</code> and <code>test</code>
     * subfolders withing <code>outputDir</code>. Defaults to false.
     * @param flag such condition.
     */
    public void setUseOutputSubfolders(@NotNull final Boolean flag)
    {
        immutableSetUseOutputSubfolders(flag);
    }

    /**
     * Retrieves whether to create <code>main</code> and <code>test</code>
     * subfolders withing <code>outputDir</code>. Defaults to false.
     * @return such condition.
     */
    @Nullable
    protected final Boolean immutableGetUseOutputSubfolders()
    {
        return m__bOutputSubfolders;
    }

    /**
     * Retrieves whether to create <code>main</code> and <code>test</code>
     * subfolders withing <code>outputDir</code>. Defaults to false.
     * @return such condition.
     */
    @Nullable
    public Boolean getUseOutputSubfolders()
    {
        Boolean result;

        String aux = System.getProperty("queryj.useOutputSubfolders");

        if (aux == null)
        {
            result = immutableGetUseOutputSubfolders();
        }
        else
        {
            result = Boolean.valueOf(aux);
        }

        return result;
    }

    /**
     * Specifies the tables.
     * @param tables such information.
     */
    protected final void immutableSetTables(@NotNull final Table[] tables)
    {
        m__aTables = tables;
    }

    /**
     * Specifies the tables.
     * @param tables such information.
     */
    public void setTables(@NotNull final Table[] tables)
    {
        immutableSetTables(tables);
    }

    /**
     * Returns the tables.
     * @return such information.
     */
    @NotNull
    protected final Table[] immutableGetTables()
    {
        return m__aTables;
    }

    /**
     * Returns the tables.
     * @return such information.
     */
    @NotNull
    protected Table[] getTables()
    {
        return immutableGetTables();
    }

    /**
     * Specifies the encoding.
     * @param encoding the encoding.
     */
    protected final void immutableSetEncoding(@NotNull final String encoding)
    {
        m__strEncoding = encoding;
    }

    /**
     * Specifies the encoding.
     * @param encoding the encoding.
     */
    public void setEncoding(@NotNull final String encoding)
    {
        immutableSetEncoding(encoding);
    }

    /**
     * Retrieves the encoding.
     * @return such information.
     */
    @Nullable
    protected final String immutableGetEncoding()
    {
        return m__strEncoding;
    }

    /**
     * Retrieves the encoding.
     * @return such information.
     */
    @Nullable
    public String getEncoding()
    {
        String result = System.getProperty("queryj.enconding");

        if (result == null)
        {
            result = immutableGetEncoding();
        }

        return result;
    }

    /**
     * Specifies whether to use template caching.
     * @param flag such condition.
     */
    protected final void immutableSetDisableCaching(@NotNull final Boolean flag)
    {
        m__bDisableCaching = flag;
    }

    /**
     * Specifies whether to use template caching.
     * @param flag such condition.
     */
    public void setDisableCaching(@NotNull final Boolean flag)
    {
        immutableSetDisableCaching(flag);
    }

    /**
     * Retrieves whether to use template caching.
     * @return such condition.
     */
    @Nullable
    protected final Boolean immutableGetDisableCaching()
    {
        return m__bDisableCaching;
    }

    /**
     * Retrieves whether to use template caching.
     * @return such condition.
     */
    @Nullable
    public Boolean isCaching()
    {
        Boolean result;

        String aux = System.getProperty("queryj.caching");

        if (aux == null)
        {
            result = immutableGetDisableCaching();
        }
        else
        {
            result = Boolean.valueOf(aux);
        }

        if (result != null)
        {
            result = !result;
        }

        return result;
    }

    /**
     * Executes QueryJ via Maven2.
     * @throws MojoExecutionException if something goes wrong.
     */
    public void execute()
        throws MojoExecutionException
    {
        execute(getLog());
    }

    /**
     * Executes QueryJ via Maven2.
     * @param log the Maven log.
     * @throws MojoExecutionException if something goes wrong.
     */
    protected void execute(@NotNull final Log log)
        throws MojoExecutionException
    {
        execute(log, retrieveVersion(retrievePomProperties(log)));
    }

    /**
     * Retrieves the version of QueryJ currently running.
     * @param properties the pom.properties information.
     * @return the version entry.
     */
    protected String retrieveVersion(@Nullable final Properties properties)
    {
        String result = "(unknown)";

        if (   (properties != null)
            && (properties.containsKey("version")))
        {
            result = properties.getProperty("version");
        }

        return result;
    }

    /**
     * Executes QueryJ via Maven2.
     * @param log the Maven log.
     * @param version the QueryJ version.
     * @throws MojoExecutionException if something goes wrong.
     */
    protected void execute(@NotNull final Log log, final String version)
        throws MojoExecutionException
    {
        boolean running = false;

        File outputDirPath = getOutputDir();

        @Nullable QueryJTask task;

        if  (outputDirPath != null)
        {
            //initialize directories
            File outputDir = outputDirPath.getAbsoluteFile();

            if (   (!outputDir.exists())
                && (!outputDir.mkdirs()))
            {
                log.warn("Cannot create output folder: " + outputDir);
            }

            //execute task
            task = buildTask(log);

            log.info("Running QueryJ " + version);

            task.execute();

            running = true;
        }
        else
        {
            log.error("outputDir is null");
        }

        if (!running)
        {
            log.error("NOT running QueryJ " + version);
            throw new MojoExecutionException("QueryJ could not start");
        }
    }

    /**
     * Retrieves the pom.properties bundled within the QueryJ jar.
     * @param log the Maven log.
     * @return such information.
     */
    @Nullable
    protected Properties retrievePomProperties(@NotNull final Log log)
    {
        @Nullable Properties result = null;

        try
        {
            InputStream pomProperties =
                getClass().getClassLoader().getResourceAsStream(POM_PROPERTIES_LOCATION);

            result = new Properties();

            result.load(pomProperties);
        }
        catch (@NotNull final IOException ioException)
        {
            log.warn(
                "Strange... Cannot read my own " + POM_PROPERTIES_LOCATION,
                ioException);
        }

        return result;
    }

    /**
     * Builds the QueryJ task.
     * @param log the Maven log.
     * @return such info.
     */
    @NotNull
    protected QueryJTask buildTask(@NotNull final Log log)
    {
        @NotNull QueryJTask result = new QueryJTask();

        @NotNull Project project = new AntProjectAdapter(new Project(), log);

        result.setProject(project);

        @NotNull Path path = new Path(project);
        result.setClasspath(path);

        log.debug("Catalog: " + getCatalog());
        result.setCatalog(getCatalog());

        log.debug("Driver: " + getDriver());
        result.setDriver(getDriver());

        log.debug("JNDI DataSource: " + getJndiDataSource());
        result.setJndiDataSource(getJndiDataSource());

        log.debug("Output dir: " + getOutputDir());
        result.setOutputdir(getOutputDir());

        log.debug("Package name: " + getPackageName());
        result.setPackage(getPackageName());

        log.debug("Repository: " + getRepository());
        result.setRepository(getRepository());

        log.debug("Schema: " + getSchema());
        result.setSchema(getSchema());

        log.debug("Url: " + getUrl());
        result.setUrl(getUrl());

        log.debug("Username: " + getUsername());
        result.setUsername(getUsername());

        log.debug("Password specified: " + (getPassword() != null));
        result.setPassword(getPassword());

        log.debug("Generate Mock DAO implementation? " + getGenerateMockDAOImplementation());
        result.setGenerateMockDAOImplementation(
            "" + getGenerateMockDAOImplementation());

        log.debug("Generate XML DAO implementation? " + getGenerateXmlDAOImplementation());
        result.setGenerateXMLDAOImplementation(
            "" + getGenerateXmlDAOImplementation());

        log.debug("Generate tests? " + getGenerateTests());
        result.setGenerateTests(
            "" + getGenerateTests());

        log.debug("Custom SQL model: " + getCustomSqlModel());
        result.setCustomSqlModel(getCustomSqlModel());

        log.debug("SQL XML file: " + getSqlXmlFile());
        result.setSqlXmlFile(getSqlXmlFile());

        log.debug("Header file: " + getHeaderFile());
        result.setHeaderfile(getHeaderFile());

        log.debug("Extract functions? " + getExtractFunctions());
        result.setExtractFunctions(
            "" + getExtractFunctions());

        log.debug("Extract procedures? " + getExtractProcedures());
        result.setExtractProcedures(
            "" + getExtractProcedures());

        log.debug(
            "Grammar bundle: " + getGrammarFolder() + File.separator
            + getGrammarName() + "(_" + Locale.US.getLanguage().toLowerCase(Locale.US)
            + ")" + getGrammarSuffix());

        result.setGrammarFolder(getGrammarFolder());
        result.setGrammarName(getGrammarName());
        result.setGrammarSuffix(getGrammarSuffix());

        log.debug("Output subfolders: " + getUseOutputSubfolders());
        result.setOutputdirsubfolders("" + getUseOutputSubfolders());

        buildExternallyManagedFields(result);
        buildTables(result);

        String encoding = getEncoding();

        if (encoding == null)
        {
            log.warn("Using default (platform-dependent) encoding to generate QueryJ sources");
        }
        else
        {
            log.info("Using encoding: '" + encoding + "' to generate QueryJ sources");
        }
        result.setEncoding(encoding);

        boolean caching = true;

        Boolean auxCaching = isCaching();
        if (auxCaching != null)
        {
            caching = auxCaching;
        }

        log.debug("Caching: " + caching);
        result.setCaching(caching);

        int threadCount = getRequestThreadCount();

        log.info("Using " + threadCount + " threads");
        result.setThreadCount(threadCount);

        return result;
    }

    /**
     * Builds the external managed fields list.
     * @param task the task.
     */
    @SuppressWarnings("unchecked")
    protected void buildExternallyManagedFields(
        @NotNull final QueryJTask task)
    {
        ExternallyManagedField[] array = getExternallyManagedFields();

        int count = array.length;
        @Nullable AntExternallyManagedFieldsElement element;
        ExternallyManagedField field;
        @Nullable AntFieldElement fieldElement;

        if  (count > 0)
        {
            element =
                (AntExternallyManagedFieldsElement) task.createDynamicElement(
                    "externally-managed-fields");

            if (element != null)
            {
                for (ExternallyManagedField anArray : array)
                {
                    field = anArray;

                    if (field != null)
                    {
                        fieldElement =
                            (AntFieldElement) element.createDynamicElement("field");

                        if (fieldElement != null)
                        {
                            fieldElement.setDynamicAttribute("name", field.getName());

                            fieldElement.setDynamicAttribute(
                                "table-name", field.getTableName());

                            fieldElement.setDynamicAttribute(
                                "keyword", field.getKeyword());

                            fieldElement.setDynamicAttribute(
                                "retrieval-query", field.getRetrievalQuery());
                        }
                    }
                }
            }
        }
    }

    /**
     * Builds the table list.
     * @param task the task.
     */
    protected void buildTables(@NotNull final QueryJTask task)
    {
        Table[] array = getTables();
        Table table;
        @Nullable AntTablesElement element;
        @Nullable AntTableElement tableElement;
        List<Field> fields;
        int fieldCount;
        Field field;
        @Nullable AntFieldElement fieldElement;

        int count =  array.length;

        if  (count > 0)
        {
            element =
                (AntTablesElement) task.createDynamicElement("tables");

            if (element != null)
            {
                for (Table anArray : array)
                {
                    table = anArray;

                    tableElement =
                        (AntTableElement) element.createDynamicElement("table");

                    if (tableElement != null)
                    {
                        tableElement.setDynamicAttribute("name", table.getName());

                        fields = table.getFields();

                        fieldCount = (fields == null) ? 0 : fields.size();

                        if (fields != null)
                        {
                            for (int fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++)
                            {
                                field = fields.get(fieldIndex);

                                if (field != null)
                                {
                                    fieldElement =
                                        (AntFieldElement) tableElement.createDynamicElement("field");

                                    if (fieldElement != null)
                                    {
                                        fieldElement.setDynamicAttribute(
                                            "name", field.getName());
                                        fieldElement.setDynamicAttribute(
                                            "type", field.getType());
                                        fieldElement.setDynamicAttribute(
                                            "pk", field.getPk());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Tries to get thread count if a Maven 3 build, using reflection as the plugin must not be maven3 api dependant
     *
     * @return number of thread for this build or 1 if not multi-thread build
     */
    protected int getRequestThreadCount()
    {
        int result = getRequestThreadCountFromSystemProperties();

        if (result < 1)
        {
            result = getRequestThreadCountFromMaven();
        }

        if (result < 1)
        {
            result = getRequestThreadCountFromRuntime();
        }

        if (result < 1)
        {
            result = 1;
        }

        return result;
    }

    /**
     * Tries to get thread count if a Maven 3 build, using reflection as the plugin must not be maven3 api dependant
     *
     * @return number of thread for this build or 1 if not multi-thread build
     */
    protected int getRequestThreadCountFromMaven()
    {
        int result = 0;

        try
        {
            Method getRequestMethod = this.session.getClass().getMethod( "getRequest" );
            Object mavenExecutionRequest = getRequestMethod.invoke( this.session );
            Method getThreadCountMethod = mavenExecutionRequest.getClass().getMethod( "getThreadCount" );
            String threadCount = (String) getThreadCountMethod.invoke( mavenExecutionRequest );
            result  = Integer.valueOf( threadCount );
        }
        catch (final Throwable unexpectedError)
        {
            getLog().debug( "unable to get thread count for the current build: " + unexpectedError.getMessage());
        }

//        if (   (result == 1)
//            && (isMultithreadEnabled()))
//        {
//            result = Runtime.getRuntime().availableProcessors();
//        }

        return result;
    }

    /**
     * Retrieves the thread count, from Runtime.
     * @return such information.
     */
    protected int getRequestThreadCountFromRuntime()
    {
        return Runtime.getRuntime().availableProcessors();
    }

    @SuppressWarnings("unchecked")
    protected boolean isMultithreadEnabled()
    {
        Set<Map.Entry> entrySet = (Set<Map.Entry>) super.getPluginContext().entrySet();

        for (Map.Entry item : entrySet)
        {
            getLog().info(item.getKey() + " -> " + item.getValue());
        }

        return true;
    }

    public int getRequestThreadCountFromSystemProperties()
    {
        int result = 0;

        String aux = System.getProperty("queryj.threadCount");

        if (aux != null)
        {
            result = Integer.parseInt(aux);
        }

        return result;
    }
}
