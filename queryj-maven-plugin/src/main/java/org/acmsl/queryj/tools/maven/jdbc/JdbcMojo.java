/*
                        QueryJ Maven

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
 * Filename: JdbcMojo.java
 *
 * Author: Jose San Leandro Armendariz.
 *
 * Description: Executes QueryJ's JdbcMojo.
 */
package org.acmsl.queryj.tools.maven.jdbc;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.tools.ant.jdbc.AntExternallyManagedFieldsElement;
import org.acmsl.queryj.tools.ant.jdbc.AntFieldElement;
import org.acmsl.queryj.tools.ant.jdbc.AntTableElement;
import org.acmsl.queryj.tools.ant.jdbc.AntTablesElement;
import org.acmsl.queryj.tools.ant.jdbc.JdbcTask;
import org.acmsl.queryj.tools.ant.QueryJTask;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.maven.AntProjectAdapter;
import org.acmsl.queryj.tools.maven.CommonsLoggingMavenLogAdapter;
import org.acmsl.queryj.tools.maven.QueryJMojo;

/*
 * Importing some Maven classes.
 */
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Parameter;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;

/*
 * Importing NotNull annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.lang.reflect.Method;
import java.util.Arrays;
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
 */
@SuppressWarnings("unused")
@ThreadSafe
@Mojo( name = Literals.QUERYJ_L, defaultPhase = LifecyclePhase.GENERATE_SOURCES, threadSafe = true, executionStrategy = "once-per-session")
public class JdbcMojo
    extends QueryJMojo
{
    /**
     * The location of pom.properties within the jar file.
     */
    protected static final String POM_PROPERTIES_LOCATION =
        "META-INF/maven/org.acmsl.queryj/queryj-maven/pom.properties";

    /**
     * String literal: "(unknown)"
     */
    public static final String UNKNOWN_LITERAL = Literals.UNKNOWN_REMARK;

    /**
     * The driver.
     */
    @Parameter (name = Literals.DRIVER, property = JDBC_DRIVER, required = true)
    private String m__strDriver;

    /**
     * The url.
     */
    @Parameter (name = Literals.URL, property = JDBC_URL, required = true)
    private String m__strUrl;

    /**
     * The user name.
     */
    @Parameter (name = Literals.USERNAME, property = JDBC_USERNAME, required = true)
    private String m__strUsername;

    /**
     * The password.
     */
    @Parameter (name = Literals.PASSWORD, property = JDBC_PASSWORD, required = true)
    private String m__strPassword;

    /**
     * The catalog.
     */
    @Parameter (name = Literals.CATALOG, property = JDBC_CATALOG, required = false, defaultValue = "")
    private String m__strCatalog;

    /**
     * The schema.
     */
    @Parameter (name = Literals.SCHEMA, property = JDBC_SCHEMA, required = false, defaultValue = "")
    private String m__strSchema;

    /**
     * The repository.
     */
    @Parameter (name = Literals.REPOSITORY, property = REPOSITORY, required = true)
    private String m__strRepository;

    /**
     * The package name.
     */
    @Parameter (name = Literals.PACKAGE_NAME, property = PACKAGE_NAME, required = true)
    private String m__strPackageName;

    /**
     * The output directory.
     */
    @Parameter (name = Literals.OUTPUT_DIR, property = OUTPUT_DIR, required = false, defaultValue = "${project.build.dir}")
    private File m__OutputDir;

    /**
     * The data source.
     */
    @Parameter (name = Literals.JNDI_DATASOURCE, property = JNDI_DATASOURCE, required = false, defaultValue = "java:comp/env/jdbc/default")
    private String m__strJndiDataSource;

    /**
     * The sql xml file.
     */
    @Parameter (name = Literals.SQL_XML_FILE, property = SQL_XML_FILE, required = false, defaultValue = "sql.xml")
    private File m__SqlXmlFile;

    /**
     * The grammar folder.
     */
    @Parameter (name = Literals.GRAMMAR_FOLDER, property = GRAMMAR_FOLDER, required = false, defaultValue = "")
    private File m__GrammarFolder;

    /**
     * The grammar bundle.
     */
    @Parameter (name = Literals.GRAMMAR_NAME, property = GRAMMAR_NAME, required = false, defaultValue = "")
    private String m__strGrammarName;

    /**
     * The grammar suffix.
     */
    @Parameter (name = Literals.GRAMMAR_SUFFIX, property = GRAMMAR_SUFFIX, required = false, defaultValue = "")
    private String m__strGrammarSuffix;

    /**
     * The list of tables.
     */
    @Parameter (required = false)
    private Table[] m__aTables = new Table[0];
    // @*** parameter property="tables"

    /**
     * Whether to disable NotNull annotations.
     */
    @Parameter (name = Literals.DISABLE_NOTNULL_ANNOTATIONS, property = DISABLE_NOTNULL_ANNOTATIONS, required = false, defaultValue = "false")
    private Boolean m__bDisableNotNullAnnotations = false;

    /**
     * Whether to disable checkthread.org annotations.
     */
    @Parameter (name = Literals.DISABLE_CHECKTHREAD_ANNOTATIONS, property = DISABLE_CHECKTHREAD_ANNOTATIONS, required = false, defaultValue = "false")
    private Boolean m__bDisableCheckthreadAnnotations = false;

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
    public String getDriver()
    {
        String result = System.getProperty(JDBC_DRIVER);

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
    public String getUrl()
    {
        String result = System.getProperty(JDBC_URL);

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
    public String getUsername()
    {
        String result = System.getProperty(JDBC_USERNAME);

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
    public String getPassword()
    {
        String result = System.getProperty(JDBC_PASSWORD);

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
        m__strCatalog = catalog;
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
        return m__strCatalog;
    }

    /**
     * Returns the catalog.
     * @return such value.
     */
    @Nullable
    public String getCatalog()
    {
        String result = System.getProperty(JDBC_CATALOG);

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
        m__strSchema = schema;
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
        return m__strSchema;
    }

    /**
     * Returns the schema.
     * @return such value, or an empty string if not initialized.
     */
    @Nullable
    public String getSchema()
    {
        String result = System.getProperty(JDBC_SCHEMA);

        if (result == null)
        {
            result = immutableGetSchema();
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
    public String getRepository()
    {
        String result = System.getProperty(REPOSITORY);

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
    public String getPackageName()
    {
        String result = System.getProperty(PACKAGE_NAME);

        if (result == null)
        {
            result = immutableGetPackageName();
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
    public String getJndiDataSource()
    {
        String result = System.getProperty(JNDI_DATASOURCE);

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
    public File getSqlXmlFile()
    {
        final File result;

        @Nullable final String aux = System.getProperty(SQL_XML_FILE);

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
    public File getGrammarFolder()
    {
        final File result;

        @Nullable final String aux = System.getProperty(GRAMMAR_FOLDER);

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
    public String getGrammarName()
    {
        String result = System.getProperty(GRAMMAR_NAME);

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
    public String getGrammarSuffix()
    {
        String result = System.getProperty(GRAMMAR_SUFFIX);

        if (result == null)
        {
            result = immutableGetGrammarSuffix();
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
    public Table[] getTables()
    {
        return immutableGetTables();
    }

    /**
     * Specifies whether to use NotNull annotations in the generated code.
     * @param flag such choice.
     */
    protected final void immutableSetDisableNotNullAnnotations(@NotNull final Boolean flag)
    {
        m__bDisableNotNullAnnotations = flag;
    }

    /**
     * Specifies whether to use NotNull annotations in the generated code.
     * @param flag such choice.
     */
    public void setDisableNotNullAnnotations(@NotNull final Boolean flag)
    {
        immutableSetDisableNotNullAnnotations(flag);
    }

    /**
     * Retrieves whether to use NotNull annotations in the generated code.
     * @return the current setting.
     */
    @Nullable
    protected final Boolean immutableGetDisableNotNullAnnotations()
    {
        return m__bDisableNotNullAnnotations;
    }

    /**
     * Retrieves whether to use NotNull annotations in the generated code.
     * @return the current setting.
     */
    @NotNull
    public Boolean getDisableNotNullAnnotations()
    {
        Boolean result = null;

        @Nullable final String aux = System.getProperty(DISABLE_NOTNULL_ANNOTATIONS);

        if (aux == null)
        {
            result = immutableGetDisableNotNullAnnotations();
        }

        if (result == null)
        {
            result = Boolean.FALSE;
        }

        return result;
    }

    /**
     * Specifies whether to use Checkthread.org annotations in the generated code.
     * @param flag such choice.
     */
    protected final void immutableSetDisableCheckthreadAnnotations(@NotNull final Boolean flag)
    {
        m__bDisableCheckthreadAnnotations = flag;
    }

    /**
     * Specifies whether to use Checkthread.org annotations in the generated code.
     * @param flag such choice.
     */
    public void setDisableCheckthreadAnnotations(@NotNull final Boolean flag)
    {
        immutableSetDisableCheckthreadAnnotations(flag);
    }

    /**
     * Retrieves whether to use Checkthread annotations in the generated code.
     * @return the current setting.
     */
    @Nullable
    protected final Boolean immutableGetDisableCheckthreadAnnotations()
    {
        return m__bDisableCheckthreadAnnotations;
    }

    /**
     * Retrieves whether to use Checkthread annotations in the generated code.
     * @return the current setting.
     */
    @NotNull
    public Boolean getDisableCheckthreadAnnotations()
    {
        Boolean result = null;

        @Nullable final String aux = System.getProperty(DISABLE_CHECKTHREAD_ANNOTATIONS);

        if (aux == null)
        {
            result = immutableGetDisableCheckthreadAnnotations();
        }

        if (result == null)
        {
            result = Boolean.FALSE;
        }

        return result;
    }

    /**
     * Instantiates a new task.
     * @param version the version.
     * @param log the log.
     * @return a new {@link org.acmsl.queryj.tools.ant.QueryJTask}.
     */
    @NotNull
    @Override
    protected QueryJTask instantiateTask(
        @NotNull final String version, @NotNull final org.apache.commons.logging.Log log)
    {
        return new JdbcTask(log);
    }

    /**
     * Builds the QueryJ task.
     * @param version the version.
     * @param log the Maven log.
     * @return such info.
     */
    @NotNull
    protected QueryJTask buildTask(@NotNull final String version, @NotNull final Log log)
    {
        @NotNull final CommonsLoggingMavenLogAdapter t_Log = new CommonsLoggingMavenLogAdapter(log);

        @NotNull final QueryJTask result = instantiateTask(version, t_Log);

        initLogging(t_Log);

        @NotNull final Project project = new AntProjectAdapter(new Project(), log);

        result.setProject(project);

        @NotNull final Path path = new Path(project);
        result.setClasspath(path);

        result.setVersion(version);

        t_Log.debug("Catalog: " + getCatalog());
        result.setCatalog(getCatalog());

        t_Log.debug("Driver: " + getDriver());
        result.setDriver(getDriver());

        t_Log.debug("JNDI DataSource: " + getJndiDataSource());
        result.setJndiDataSource(getJndiDataSource());

        t_Log.debug("Output dir: " + getOutputDir());
        result.setOutputdir(getOutputDir());

        t_Log.debug("Package name: " + getPackageName());
        result.setPackage(getPackageName());

        t_Log.debug("Repository: " + getRepository());
        result.setRepository(getRepository());

        t_Log.debug("Schema: " + getSchema());
        result.setSchema(getSchema());

        t_Log.debug("Url: " + getUrl());
        result.setUrl(getUrl());

        t_Log.debug("Username: " + getUsername());
        result.setUsername(getUsername());

        t_Log.debug("Password specified: " + (getPassword() != null));
        result.setPassword(getPassword());

        t_Log.debug("SQL XML file: " + getSqlXmlFile());
        result.setSqlXmlFile(getSqlXmlFile());

        t_Log.debug("Header file: " + getHeaderFile());
        result.setHeaderfile(getHeaderFile());

        t_Log.debug(
            "Grammar bundle: " + getGrammarFolder() + File.separator
            + getGrammarName() + "(_" + Locale.US.getLanguage().toLowerCase(Locale.US)
            + ")" + getGrammarSuffix());

        result.setGrammarFolder(getGrammarFolder());
        result.setGrammarName(getGrammarName());
        result.setGrammarSuffix(getGrammarSuffix());

        buildTables(result);

        @Nullable final String encoding = getEncoding();

        if (encoding == null)
        {
            t_Log.warn("Using default (platform-dependent) encoding to generate QueryJ sources");
        }
        else
        {
            t_Log.info("Using encoding: \"" + encoding + "\" to generate QueryJ sources");
        }
        result.setEncoding(encoding);

        final boolean caching = true;

        final int threadCount = getRequestThreadCount();

        t_Log.info("Using " + threadCount + " threads");
        result.setThreadCount(threadCount);

        final boolean disableGenerationTimestamps = getDisableTimestamps();

        result.setDisableGenerationTimestamps(disableGenerationTimestamps);

        final boolean disableNotNullAnnotations = getDisableNotNullAnnotations();

        result.setDisableNotNullAnnotations(disableNotNullAnnotations);

        final boolean disableCheckthreadAnnotations = getDisableCheckthreadAnnotations();

        result.setDisableCheckthreadAnnotations(disableCheckthreadAnnotations);

        return result;
    }

    /**
     * Builds the table list.
     * @param task the task.
     */
    protected void buildTables(@NotNull final QueryJTask task)
    {
        @NotNull final Table[] array = getTables();
        Table table;
        @Nullable final AntTablesElement element;
        @Nullable AntTableElement tableElement;
        List<Field> fields;
        int fieldCount;
        Field field;
        @Nullable AntFieldElement fieldElement;

        final int count =  array.length;

        if  (count > 0)
        {
            element =
                (AntTablesElement) task.createDynamicElement(ParameterValidationHandler.TABLES);

            if (element != null)
            {
                for (@Nullable final Table anArray : array)
                {
                    table = anArray;

                    tableElement =
                        (AntTableElement) element.createDynamicElement(AntTablesElement.TABLE);

                    if (   (table != null)
                        && (tableElement != null))
                    {
                        @Nullable final String name = table.getName();

                        if (name != null)
                        {
                            tableElement.setDynamicAttribute("name", name);
                        }

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
                                        (AntFieldElement) tableElement.createDynamicElement(
                                            AntExternallyManagedFieldsElement.FIELD_LITERAL);

                                    if (fieldElement != null)
                                    {
                                        fieldElement.setDynamicAttribute(
                                            "name", field.getName());
                                        fieldElement.setDynamicAttribute(
                                            "type", field.getType());
                                        @Nullable final String t_strPk = field.getPk();
                                        fieldElement.setDynamicAttribute(
                                            "pk", String.valueOf(Boolean.valueOf(t_strPk == null)));
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
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"catalog\": \"" + m__strCatalog + '"'
            + ", \"driver\": \"" + m__strDriver + '"'
            + ", \"url\": \"" + m__strUrl + '"'
            + ", \"username\": \"" + m__strUsername + '"'
            + ", \"password\": \"" + m__strPassword + '"'
            + ", \"schema\": \"" + m__strSchema + '"'
            + ", \"repository\": \"" + m__strRepository + '"'
            + ", \"packageName\": \"" + m__strPackageName + '"'
            + ", \"outputDir\": \"" + getOutputDir() + '"'
            + ", \"jndiDataSource\": \"" + m__strJndiDataSource + '"'
            + ", \"sqlXmlFile\": \"" + m__SqlXmlFile + '"'
            + ", \"headerFile\": \"" + getHeaderFile() + '"'
            + ", \"grammarFolder\": \"" + m__GrammarFolder + '"'
            + ", \"grammarName\": \"" + m__strGrammarName + '"'
            + ", \"grammarSuffix\": \"" + m__strGrammarSuffix + '"'
            + ", \"tables\": " + Arrays.toString(m__aTables) + '"'
            + ", \"encoding\": \"" + getEncoding() + '"'
            + ", \"disableTimestamps\": \"" + getDisableTimestamps() + '"'
            + ", \"disableNotNullAnnotations\": \"" + m__bDisableNotNullAnnotations + '"'
            + ", \"disableCheckthreadAnnotations\": \"" + m__bDisableCheckthreadAnnotations + '"'
            + ", \"class\": \"JdbcMojo\""
            + ", \"package\": \"org.acmsl.queryj.tools.maven.jdbc\" }";
    }
}
