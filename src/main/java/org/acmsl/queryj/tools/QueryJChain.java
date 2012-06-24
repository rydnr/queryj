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
 * Filename: QueryJChain.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the chain flow of QueryJ.
 *
 */
package org.acmsl.queryj.tools;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.customsql.handlers.CustomSqlProviderRetrievalHandler;
import org.acmsl.queryj.tools.customsql.handlers.CustomSqlValidationHandler;
import org.acmsl.queryj.tools.handlers.Log4JInitializerHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataCacheWritingHandler;
import org.acmsl.queryj.tools.handlers.JdbcMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataLoggingHandler;
import org.acmsl.queryj.tools.handlers.JdbcConnectionOpeningHandler;
import org.acmsl.queryj.tools.handlers.JdbcConnectionClosingHandler;
import org.acmsl.queryj.tools.handlers.ExternallyManagedFieldsRetrievalHandler;
import org.acmsl.queryj.tools.handlers.mysql.MySQL4xMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.oracle.OracleMetaDataRetrievalHandler;
import org.acmsl.queryj.templates.dao.DAOBundle;
import org.acmsl.queryj.templates.handlers.TableTemplateHandlerBundle;
import org.acmsl.queryj.templates.valueobject.VOBundle;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Chain;
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Defines the steps performed by QueryJ.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class QueryJChain
    extends org.acmsl.queryj.AbstractQueryJChain
    implements org.acmsl.queryj.QueryJSettings
{
    /**
     * The settings.
     */
    private Properties m__Settings;

    /**
     * The properties file (to include all other settings).
     */
    private File m__SettingsFile;

    /**
     * The driver.
     */
    private String m__strDriver;

    /**
     * The url.
     */
    private String m__strUrl;

    /**
     * The username.
     */
    private String m__strUsername;

    /**
     * The password.
     */
    private String m__strPassword;

    /**
     * The catalog.
     */
    private String m__strCatalog;

    /**
     * The schema.
     */
    private String m__strSchema;

    /**
     * The repository.
     */
    private String m__strRepository;

    /**
     * The package.
     */
    private String m__strPackage;

    /**
     * The output folder.
     */
    private File m__Outputdir;

    /**
     * The optional header.
     */
    private File m__Header;

    /**
     * The "outputdirsubfolders" value.
     */
    private String m__strOutputdirsubfolders;

    /**
     * Whether to use sub folders or not.
     */
    private boolean m__bOutputdirsubfolders = false;

    /**
     * The "extract-tables" value.
     */
    private String m__strExtractTables;

    /**
     * The "extract-tables" flag.
     */
    private boolean m__bExtractTables = true;

    /**
     * The "extract-procedures" value.
     */
    private String m__strExtractProcedures;

    /**
     * The "extract-procedures" flag.
     */
    private boolean m__bExtractProcedures = true;

    /**
     * The "extract-functions" value.
     */
    private String m__strExtractFunctions;

    /**
     * The "extract-functions" flag.
     */
    private boolean m__bExtractFunctions = true;

    /**
     * The JNDI location for the data sources.
     */
    private String m__strJNDIDataSources;

    /**
     * The "generate-mock-dao-implementation" value.
     */
    private String m__strGenerateMockDAOImplementation;

    /**
     * The "generate-mock-dao-implementation" flag.
     */
    private boolean m__bGenerateMockDAOImplementation = true;

    /**
     * The "generate-xml-dao-implementation" value.
     */
    private String m__strGenerateXMLDAOImplementation;

    /**
     * The "generate-xml-dao-implementation" flag.
     */
    private boolean m__bGenerateXMLDAOImplementation = true;

    /**
     * The "generate-tests" value.
     */
    private String m__strGenerateTests;

    /**
     * The "generate-tests" flag.
     */
    private boolean m__bGenerateTests = false;

    /**
     * The "custom-sql-model" type.
     */
    @Nullable
    private String m__strCustomSqlModel = null;

    /**
     * The "sql-xml-file" path.
     */
    private File m__SqlXmlFile;

    /**
     * The "grammar-bundle" property.
     */
    private String m__strGrammarBundleName;

    /**
     * The <i>allowEmptyRepositoryDAO</i> flag.
     */
    private boolean m__bAllowEmptyRepositoryDAO = false;

    /**
     * The <i>allowEmptyRepositoryDAO</i> property.
     */
    private String m__strAllowEmptyRepositoryDAO;

    /**
     * The <i>implementMarkerInterfaces</i> flag.
     */
    private boolean m__bImplementMarkerInterfaces = false;

    /**
     * The <i>implementMarkerInterfaces</i> property.
     */
    private String m__strImplementMarkerInterfaces;

    /**
     * The <i>disableCustomSqlValidation</i> flag.
     */
    private boolean m__bDisableCustomSqlValidation;

    /**
     * The <i>disableCustomSqlValidation</i> property.
     */
    private String m__strDisableCustomSqlValidation;

    /**
     * The <i>encoding</i> property.
     */
    private String m__strEncoding;

    /**
     * Creates a {@link QueryJChain} instance.
     */
    public QueryJChain() {}

    /**
     * Creates a {@link QueryJChain} with given information.
     * @param settings the configuration settings.
     */
    public QueryJChain(final Properties settings)
    {
        immutableSetSettings(settings);
    }

    /**
     * Creates a {@link QueryJChain} with given information.
     * @param settings the configuration settings.
     * @param driver the JDBC driver.
     * @param url the JDBC url.
     * @param username the JDBC username.
     * @param password the JDBC password.
     * @param catalog the JDBC catalog.
     * @param schema the JDBC schema.
     * @param repository the repository.
     * @param packageName the base package of the generated sources.
     * @param outputDir the output folder.
     * @param header the copyright header.
     * @param outputDirSubFolders whether to use main/ and test/ as
     * subfolders.
     * @param extractTables whether to extract tables or not.
     * @param extractProcedures whether to extract the procedures or not.
     * @param extractFunctions whether to extract the functions or not.
     * @param jndiDataSource the location in JNDI of the
     * {@link javax.sql.DataSource}.
     * @param generateMockDAOImplementation whether to generate Mock DAOs.
     * @param generateXmlDAOImplementation whether to generate XML DAOs.
     * @param generateTests whether to generate test cases or not.
     * @param allowEmptyRepositoryDAO whether to generate a repository
     * DAO even tough it'll contain no custom queries.
     * @param implementMarkerInterfaces whether to make some generated 
     * sources implement {@link org.acmsl.commons.patterns} Marker
     * interfaces.
     * @param customSqlModel the format of the custom SQL file.
     * @param disableCustomSqlValidation to disable validation of custom sql.
     * @param sqlXmlFile the file containing the custom SQL.
     * @param grammarBundle the grammar with irregular singular and plural
     * forms of the table names.
     * @param encoding the file encoding.
     */
    @SuppressWarnings("unused")
    public QueryJChain(
        final File settings,
        final String driver,
        final String url,
        final String username,
        final String password,
        final String catalog,
        final String schema,
        final String repository,
        final String packageName,
        final File outputDir,
        final File header,
        final boolean outputDirSubFolders,
        final boolean extractTables,
        final boolean extractProcedures,
        final boolean extractFunctions,
        final String jndiDataSource,
        final boolean generateMockDAOImplementation,
        final boolean generateXmlDAOImplementation,
        final boolean generateTests,
        final boolean allowEmptyRepositoryDAO,
        final boolean implementMarkerInterfaces,
        final String customSqlModel,
        final boolean disableCustomSqlValidation,
        final File sqlXmlFile,
        final String grammarBundle,
        final String encoding)
    {
        immutableSetSettingsFile(settings);
        immutableSetDriver(driver);
        immutableSetUrl(url);
        immutableSetUsername(username);
        immutableSetPassword(password);
        immutableSetCatalog(catalog);
        immutableSetSchema(schema);
        immutableSetRepository(repository);
        immutableSetPackage(packageName);
        immutableSetOutputdir(outputDir);
        immutableSetHeaderfile(header);
        immutableSetOutputdirsubfoldersFlag(outputDirSubFolders);
        immutableSetExtractTablesFlag(extractTables);
        immutableSetExtractProceduresFlag(extractProcedures);
        immutableSetExtractFunctionsFlag(extractFunctions);
        immutableSetJndiDataSource(jndiDataSource);
        immutableSetGenerateMockDAOImplementationFlag(
            generateMockDAOImplementation);
        immutableSetGenerateXMLDAOImplementationFlag(
            generateXmlDAOImplementation);
        immutableSetGenerateTestsFlag(generateTests);
        immutableSetAllowEmptyRepositoryDAOFlag(allowEmptyRepositoryDAO);
        immutableSetImplementMarkerInterfacesFlag(implementMarkerInterfaces);
        immutableSetCustomSqlModel(customSqlModel);
        immutableSetDisableCustomSqlValidationFlag(disableCustomSqlValidation);
        immutableSetSqlXmlFile(sqlXmlFile);
        immutableSetGrammarbundle(grammarBundle);
        immutableSetEncoding(encoding);
    }

    /**
     * Specifies the properties.
     * @param settings such settings.
     */
    protected final void immutableSetSettings(final Properties settings)
    {
        m__Settings = settings;
    }

    /**
     * Specifies the properties.
     * @param settings such settings.
     */
    @SuppressWarnings("unused")
    public void setSettings(final Properties settings)
    {
        immutableSetSettings(settings);
    }

    /**
     * Retrieves the properties file.
     * @return such file.
     */
    public Properties getSettings()
    {
        return m__Settings;
    }

    /**
     * Specifies the properties file.
     * @param file such file.
     */
    protected final void immutableSetSettingsFile(final File file)
    {
        m__SettingsFile = file;
    }

    /**
     * Specifies the properties file.
     * @param file such file.
     */
    public void setSettingsFile(final File file)
    {
        immutableSetSettingsFile(file);
    }

    /**
     * Retrieves the properties file.
     * @return such file.
     */
    public File getSettingsFile()
    {
        return m__SettingsFile;
    }

    /**
     * Specifies the driver.
     * @param driver the new driver.
     */
    protected final void immutableSetDriver(final String driver)
    {
        m__strDriver = driver;
    }

    /**
     * Specifies the driver.
     * @param driver the new driver.
     */
    public void setDriver(final String driver)
    {
        immutableSetDriver(driver);
    }

    /**
     * Retrieves the driver.
     * @return such information.
     */
    public String getDriver()
    {
        return m__strDriver;
    }

    /**
     * Retrieves the driver, using given properties if necessary.
     * @param properties the properties.
     * @return the JDBC driver.
     */
    protected String getDriver(@Nullable final Properties properties)
    {
        String result = getDriver();

        if  (   (result == null)
             && (properties != null))
        {
            result = properties.getProperty(JDBC_DRIVER);
            setDriver(result);
        }

        return result;
    }

    /**
     * Specifies the url.
     * @param url the new url.
     */
    protected final void immutableSetUrl(final String url)
    {
        m__strUrl = url;
    }

    /**
     * Specifies the url.
     * @param url the new url.
     */
    public void setUrl(final String url)
    {
        immutableSetUrl(url);
    }

    /**
     * Retrieves the url.
     * @return such information.
     */
    public String getUrl()
    {
        return m__strUrl;
    }

    /**
     * Retrieves the url, using given properties if necessary.
     * @param properties the properties.
     * @return the JDBC url.
     */
    protected String getUrl(@Nullable final Properties properties)
    {
        String result = getUrl();

        if  (   (result == null)
             && (properties != null))
        {
            result = properties.getProperty(JDBC_URL);
            setUrl(result);
        }

        return result;
    }

    /**
     * Specifies the username.
     * @param username the new username.
     */
    protected final void immutableSetUsername(final String username)
    {
        m__strUsername = username;
    }

    /**
     * Specifies the username.
     * @param username the new username.
     */
    public void setUsername(final String username)
    {
        immutableSetUsername(username);
    }

    /**
     * Retrieves the username.
     * @return such information.
     */
    public String getUsername()
    {
        return m__strUsername;
    }

    /**
     * Retrieves the username, using given properties if necessary.
     * @param properties the properties.
     * @return the JDBC username.
     */
    protected String getUsername(@Nullable final Properties properties)
    {
        String result = getUsername();

        if  (   (result == null)
             && (properties != null))
        {
            result = properties.getProperty(JDBC_USERNAME);
            setUsername(result);
        }

        return result;
    }

    /**
     * Specifies the password.
     * @param password the new password.
     */
    protected final void immutableSetPassword(final String password)
    {
        m__strPassword = password;
    }

    /**
     * Specifies the password.
     * @param password the new password.
     */
    public void setPassword(final String password)
    {
        immutableSetPassword(password);
    }

    /**
     * Retrieves the password.
     * @return such information.
     */
    public String getPassword()
    {
        return m__strPassword;
    }

    /**
     * Retrieves the password, using given properties if necessary.
     * @param properties the properties.
     * @return the JDBC password.
     */
    protected String getPassword(@Nullable final Properties properties)
    {
        String result = getPassword();

        if  (   (result == null)
             && (properties != null))
        {
            result = properties.getProperty(JDBC_PASSWORD);
            setPassword(result);
        }

        return result;
    }

    /**
     * Specifies the catalog.
     * @param catalog the new catalog.
     */
    protected final void immutableSetCatalog(final String catalog)
    {
        m__strCatalog = catalog;
    }

    /**
     * Specifies the catalog.
     * @param catalog the new catalog.
     */
    public void setCatalog(final String catalog)
    {
        immutableSetCatalog(catalog);
    }

    /**
     * Retrieves the catalog.
     * @return such information.
     */
    public String getCatalog()
    {
        return m__strCatalog;
    }

    /**
     * Retrieves the catalog, using given properties if necessary.
     * @param properties the properties.
     * @return the JDBC catalog.
     */
    protected String getCatalog(@Nullable final Properties properties)
    {
        String result = getCatalog();

        if  (   (result == null)
             && (properties != null))
        {
            result = properties.getProperty(JDBC_CATALOG);
            setCatalog(result);
        }

        return result;
    }

    /**
     * Specifies the schema.
     * @param schema the new schema.
     */
    protected final void immutableSetSchema(final String schema)
    {
        m__strSchema = schema;
    }

    /**
     * Specifies the schema.
     * @param schema the new schema.
     */
    public void setSchema(final String schema)
    {
        immutableSetSchema(schema);
    }

    /**
     * Retrieves the schema.
     * @return such information.
     */
    public String getSchema()
    {
        return m__strSchema;
    }

    /**
     * Retrieves the schema, using given properties if necessary.
     * @param properties the properties.
     * @return the JDBC schema.
     */
    protected String getSchema(@Nullable final Properties properties)
    {
        String result = getSchema();

        if  (   (result == null)
             && (properties != null))
        {
            result = properties.getProperty(JDBC_SCHEMA);
            setSchema(result);
        }

        return result;
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     */
    protected final void immutableSetRepository(final String repository)
    {
        m__strRepository = repository;
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     */
    public void setRepository(final String repository)
    {
        immutableSetRepository(repository);
    }

    /**
     * Retrieves the repository.
     * @return such information.
     */
    public String getRepository()
    {
        return m__strRepository;
    }

    /**
     * Retrieves the repository, using given properties if necessary.
     * @param properties the properties.
     * @return the repository.
     */
    protected String getRepository(@Nullable final Properties properties)
    {
        String result = getRepository();

        if  (   (result == null)
             && (properties != null))
        {
            result = properties.getProperty(REPOSITORY);
            setRepository(result);
        }

        return result;
    }

    /**
     * Specifies the package.
     * @param packageName the new package.
     */
    protected final void immutableSetPackage(final String packageName)
    {
        m__strPackage = packageName;
    }

    /**
     * Specifies the package.
     * @param packageName the new package.
     */
    public void setPackage(final String packageName)
    {
        immutableSetPackage(packageName);
    }

    /**
     * Retrieves the package.
     * @return such information.
     */
    public String getPackage()
    {
        return m__strPackage;
    }

    /**
     * Retrieves the package, using given properties if necessary.
     * @param properties the properties.
     * @return the package.
     */
    protected String getPackage(@Nullable final Properties properties)
    {
        String result = getPackage();

        if  (   (result == null)
             && (properties != null))
        {
            result = properties.getProperty(PACKAGE);
            setPackage(result);
        }

        return result;
    }

    /**
     * Specifies the outputdir.
     * @param outputdir the new outputdir.
     */
    protected final void immutableSetOutputdir(final File outputdir)
    {
        m__Outputdir = outputdir;
    }

    /**
     * Specifies the outputdir.
     * @param outputdir the new outputdir.
     */
    public void setOutputdir(final File outputdir)
    {
        immutableSetOutputdir(outputdir);
    }

    /**
     * Retrieves the outputdir.
     * @return such information.
     */
    public File getOutputdir()
    {
        return m__Outputdir;
    }

    /**
     * Retrieves the output folder, using given properties if necessary.
     * @param properties the properties.
     * @return the output folder.
     */
    protected File getOutputdir(@Nullable final Properties properties)
    {
        File result = getOutputdir();

        if  (   (result == null)
             && (properties != null))
        {
            String t_strOutputdir = properties.getProperty(OUTPUT_FOLDER);

            if  (t_strOutputdir != null)
            {
                result = new File(t_strOutputdir);
                setOutputdir(result);
            }
        }

        return result;
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    protected final void immutableSetHeaderfile(final File header)
    {
        m__Header = header;
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    public void setHeaderfile(final File header)
    {
        immutableSetHeaderfile(header);
    }

    /**
     * Retrieves the header.
     * @return such information.
     */
    public File getHeaderfile()
    {
        return m__Header;
    }

    /**
     * Retrieves the header file, using given properties if necessary.
     * @param properties the properties.
     * @return the header file.
     */
    protected File getHeaderfile(@Nullable final Properties properties)
    {
        File result = getHeaderfile();

        if  (   (result == null)
             && (properties != null))
        {
            String t_strHeader = properties.getProperty(HEADER_FILE);

            if  (t_strHeader != null)
            {
                result = new File(t_strHeader);
                setHeaderfile(result);
            }
        }

        return result;
    }

    /**
     * Specifies whether to use subfolders.
     * @param outputDirSubFolders such setting.
     */
    protected final void immutableSetOutputdirsubfolders(
        final String outputDirSubFolders)
    {
        m__strOutputdirsubfolders = outputDirSubFolders;
    }

    /**
     * Specifies whether to use subfolders.
     * @param outputDirSubFolders such setting.
     */
    public void setOutputdirsubfolders(final String outputDirSubFolders)
    {
        immutableSetOutputdirsubfolders(outputDirSubFolders);

        setOutputdirsubfoldersFlag(toBoolean(outputDirSubFolders));
    }

    /**
     * Retrieves whether to use subfolders.
     * @return such setting.
     */
    public String getOutputdirsubfolders()
    {
        return m__strOutputdirsubfolders;
    }

    /**
     * Specifies the "outputdirsubfolders" flag.
     * @param flag such flag.
     */
    protected final void immutableSetOutputdirsubfoldersFlag(
        final boolean flag)
    {
        m__bOutputdirsubfolders = flag;
    }

    /**
     * Specifies the "outputdirsubfolders" flag.
     * @param flag such flag.
     */
    protected void setOutputdirsubfoldersFlag(final boolean flag)
    {
        immutableSetOutputdirsubfoldersFlag(flag);
    }

    /**
     * Retrieves the "outputdirsubfolders" flag.
     * @return such flag.
     */
    @SuppressWarnings("unused")
    protected boolean getOutputdirsubfoldersFlag()
    {
        return m__bOutputdirsubfolders;
    }

    /**
     * Retrieves the output dir subfolders flag, using given properties if
     * necessary.
     * @param properties the properties.
     * @return such flag.
     */
    protected boolean getOutputdirsubfoldersFlag(@Nullable final Properties properties)
    {
        String t_strResult = getOutputdirsubfolders();

        if  (   (t_strResult == null)
             && (properties != null))
        {
            t_strResult = properties.getProperty(OUTPUT_DIR_SUBFOLDERS);
            setOutputdirsubfolders(t_strResult);
        }

        return toBoolean(t_strResult);
    }

    /**
     * Specifies whether to extract the tables.
     * @param extractTables the procedure extraction setting.
     */
    protected final void immutableSetExtractTables(final String extractTables)
    {
        m__strExtractTables = extractTables;
    }

    /**
     * Specifies whether to extract the tables.
     * @param extractTables the procedure extraction setting.
     */
    public void setExtractTables(final String extractTables)
    {
        immutableSetExtractTables(extractTables);

        setExtractTablesFlag(toBoolean(extractTables));
    }

    /**
     * Retrieves whether to extract the tables.
     * @return such setting.
     */
    public String getExtractTables()
    {
        return m__strExtractTables;
    }

    /**
     * Specifies the "extract-tables" flag.
     * @param flag such flag.
     */
    protected final void immutableSetExtractTablesFlag(final boolean flag)
    {
        m__bExtractTables = flag;
    }

    /**
     * Specifies the "extract-tables" flag.
     * @param flag such flag.
     */
    protected void setExtractTablesFlag(final boolean flag)
    {
        immutableSetExtractTablesFlag(flag);
    }

    /**
     * Retrieves the "extract-tables" flag.
     * @return such flag.
     */
    public boolean getExtractTablesFlag()
    {
        return m__bExtractTables;
    }

    /**
     * Retrieves the extract-tables flag, using given properties if necessary.
     * @param properties the properties.
     * @return such flag.
     */
    protected boolean getExtractTablesFlag(@Nullable final Properties properties)
    {
        String t_strResult = getExtractTables();

        if  (   (t_strResult == null)
             && (properties != null))
        {
            t_strResult = properties.getProperty(EXTRACT_TABLES);
            setExtractTables(t_strResult);
        }

        return toBoolean(t_strResult);
    }

    /**
     * Specifies whether to extract the procedures.
     * @param extractProcedures the procedure extraction setting.
     */
    protected final void immutableSetExtractProcedures(
        final String extractProcedures)
    {
        m__strExtractProcedures = extractProcedures;
    }

    /**
     * Specifies whether to extract the procedures.
     * @param extractProcedures the procedure extraction setting.
     */
    public void setExtractProcedures(final String extractProcedures)
    {
        immutableSetExtractProcedures(extractProcedures);

        setExtractProceduresFlag(toBoolean(extractProcedures));
    }

    /**
     * Retrieves whether to extract the procedures.
     * @return such setting.
     */
    public String getExtractProcedures()
    {
        return m__strExtractProcedures;
    }

    /**
     * Specifies the "extract-procedures" flag.
     * @param flag such flag.
     */
    protected final void immutableSetExtractProceduresFlag(final boolean flag)
    {
        m__bExtractProcedures = flag;
    }

    /**
     * Specifies the "extract-procedures" flag.
     * @param flag such flag.
     */
    protected void setExtractProceduresFlag(final boolean flag)
    {
        immutableSetExtractProceduresFlag(flag);
    }

    /**
     * Retrieves the "extract-procedures" flag.
     * @return such flag.
     */
    @SuppressWarnings("unused")
    protected boolean getExtractProceduresFlag()
    {
        return m__bExtractProcedures;
    }

    /**
     * Retrieves the extract-procedures flag, using given properties
     * if necessary.
     * @param properties the properties.
     * @return such flag.
     */
    protected boolean getExtractProceduresFlag(@Nullable final Properties properties)
    {
        String t_strResult = getExtractProcedures();

        if  (   (t_strResult == null)
             && (properties != null))
        {
            t_strResult = properties.getProperty(EXTRACT_PROCEDURES);
            setExtractProcedures(t_strResult);
        }

        return toBoolean(t_strResult);
    }

    /**
     * Specifies whether to extract the functions.
     * @param extractFunctions the function extraction setting.
     */
    protected final void immutableSetExtractFunctions(
        final String extractFunctions)
    {
        m__strExtractFunctions = extractFunctions;
    }

    /**
     * Specifies whether to extract the functions.
     * @param extractFunctions the function extraction setting.
     */
    public void setExtractFunctions(final String extractFunctions)
    {
        immutableSetExtractFunctions(extractFunctions);

        setExtractFunctionsFlag(toBoolean(extractFunctions));
    }

    /**
     * Retrieves whether to extract the functions.
     * @return such setting.
     */
    public String getExtractFunctions()
    {
        return m__strExtractFunctions;
    }

    /**
     * Specifies the "extract-functions" flag.
     * @param flag such flag.
     */
    protected final void immutableSetExtractFunctionsFlag(final boolean flag)
    {
        m__bExtractFunctions = flag;
    }

    /**
     * Specifies the "extract-functions" flag.
     * @param flag such flag.
     */
    protected void setExtractFunctionsFlag(final boolean flag)
    {
        immutableSetExtractFunctionsFlag(flag);
    }

    /**
     * Retrieves the "extract-functions" flag.
     * @return such flag.
     */
    @SuppressWarnings("unused")
    protected boolean getExtractFunctionsFlag()
    {
        return m__bExtractFunctions;
    }

    /**
     * Retrieves the extract-functions flag, using given properties if
     * necessary.
     * @param properties the properties.
     * @return such flag.
     */
    protected boolean getExtractFunctionsFlag(@Nullable final Properties properties)
    {
        String t_strResult = getExtractFunctions();

        if  (   (t_strResult == null)
             && (properties != null))
        {
            t_strResult = properties.getProperty(EXTRACT_FUNCTIONS);
            setExtractFunctions(t_strResult);
        }

        return toBoolean(t_strResult);
    }

    /**
     * Specifices the JNDI location for the data sources.
     * @param jndiLocation the JNDI location.
     */
    protected final void immutableSetJndiDataSource(final String jndiLocation)
    {
        m__strJNDIDataSources = jndiLocation;
    }

    /**
     * Specifices the JNDI location for the data sources.
     * @param jndiLocation the JNDI location.
     */
    public void setJndiDataSource(final String jndiLocation)
    {
        immutableSetJndiDataSource(jndiLocation);
    }

    /**
     * Retrieves the JNDI location for the data sources.
     * @return such information.
     */
    public String getJndiDataSource()
    {
        return m__strJNDIDataSources;
    }

    /**
     * Retrieves the JNDI path, using given properties if necessary.
     * @param properties the properties.
     * @return the JNDI location of the DataSource..
     */
    protected String getJndiDataSource(@Nullable final Properties properties)
    {
        String result = getJndiDataSource();

        if  (   (result == null)
             && (properties != null))
        {
            result = properties.getProperty(JNDI_DATASOURCE);
            setJndiDataSource(result);
        }

        return result;
    }

    /**
     * Specifies whether to generate Mock DAO implementations.
     * @param generate such setting.
     */
    protected final void immutableSetGenerateMockDAOImplementation(
        final String generate)
    {
        m__strGenerateMockDAOImplementation = generate;
    }

    /**
     * Specifies whether to generate Mock DAO implementations.
     * @param generate such setting.
     */
    public void setGenerateMockDAOImplementation(final String generate)
    {
        immutableSetGenerateMockDAOImplementation(generate);

        setGenerateMockDAOImplementationFlag(toBoolean(generate));
    }

    /**
     * Retrieves whether to generate Mock DAO implementations.
     * @return such setting.
     */
    public String getGenerateMockDAOImplementation()
    {
        return m__strGenerateMockDAOImplementation;
    }

    /**
     * Specifies the "generate-mock-dao-implementation" flag.
     * @param flag such flag.
     */
    protected final void immutableSetGenerateMockDAOImplementationFlag(
        final boolean flag)
    {
        m__bGenerateMockDAOImplementation = flag;
    }

    /**
     * Specifies the "generate-mock-dao-implementation" flag.
     * @param flag such flag.
     */
    protected void setGenerateMockDAOImplementationFlag(final boolean flag)
    {
        immutableSetGenerateMockDAOImplementationFlag(flag);
    }

    /**
     * Retrieves the "generate-mock-dao-implementation" flag.
     * @return such flag.
     */
    protected boolean getGenerateMockDAOImplementationFlag()
    {
        return m__bGenerateMockDAOImplementation;
    }

    /**
     * Retrieves the generate-mock-dao-implementation flag, using given
     * properties if necessary.
     * @param properties the properties.
     * @return such flag.
     */
    protected boolean getGenerateMockDAOImplementationFlag(
        @Nullable final Properties properties)
    {
        String t_strResult = getGenerateMockDAOImplementation();

        if  (   (t_strResult == null)
             && (properties != null))
        {
            t_strResult =
                properties.getProperty(GENERATE_MOCK_DAO_IMPLEMENTATION);
            setGenerateMockDAOImplementation(t_strResult);
        }

        return toBoolean(t_strResult);
    }

    /**
     * Specifies whether to generate XML DAO implementations.
     * @param generate such setting.
     */
    protected final void immutableSetGenerateXMLDAOImplementation(
        final String generate)
    {
        m__strGenerateXMLDAOImplementation = generate;
    }

    /**
     * Specifies whether to generate XML DAO implementations.
     * @param generate such setting.
     */
    public void setGenerateXMLDAOImplementation(final String generate)
    {
        immutableSetGenerateXMLDAOImplementation(generate);

        setGenerateXMLDAOImplementationFlag(toBoolean(generate));
    }

    /**
     * Retrieves whether to generate XML DAO implementations.
     * @return such setting.
     */
    public String getGenerateXMLDAOImplementation()
    {
        return m__strGenerateXMLDAOImplementation;
    }

    /**
     * Specifies the "generate-xml-dao-implementation" flag.
     * @param flag such flag.
     */
    protected final void immutableSetGenerateXMLDAOImplementationFlag(
        final boolean flag)
    {
        m__bGenerateXMLDAOImplementation = flag;
    }

    /**
     * Specifies the "generate-xml-dao-implementation" flag.
     * @param flag such flag.
     */
    protected void setGenerateXMLDAOImplementationFlag(final boolean flag)
    {
        immutableSetGenerateXMLDAOImplementationFlag(flag);
    }

    /**
     * Retrieves the "generate-xml-dao-implementation" flag.
     * @return such flag.
     */
    protected boolean getGenerateXMLDAOImplementationFlag()
    {
        return m__bGenerateXMLDAOImplementation;
    }

    /**
     * Retrieves the generate-xml-dao-implementation flag, using given
     * properties if necessary.
     * @param properties the properties.
     * @return such flag.
     */
    protected boolean getGenerateXMLDAOImplementationFlag(
        @Nullable final Properties properties)
    {
        String t_strResult = getGenerateXMLDAOImplementation();

        if  (   (t_strResult == null)
             && (properties != null))
        {
            t_strResult =
                properties.getProperty(GENERATE_XML_DAO_IMPLEMENTATION);
            setGenerateXMLDAOImplementation(t_strResult);
        }

        return toBoolean(t_strResult);
    }

    /**
     * Specifies whether to generate test cases.
     * @param generate such setting.
     */
    protected final void immutableSetGenerateTests(
        final String generate)
    {
        m__strGenerateTests = generate;
    }

    /**
     * Specifies whether to generate test cases.
     * @param generate such setting.
     */
    public void setGenerateTests(final String generate)
    {
        immutableSetGenerateTests(generate);

        setGenerateTestsFlag(toBoolean(generate));
    }

    /**
     * Retrieves whether to generate test cases.
     * @return such setting.
     */
    public String getGenerateTests()
    {
        return m__strGenerateTests;
    }

    /**
     * Specifies the "generate-tests" flag.
     * @param flag such flag.
     */
    protected final void immutableSetGenerateTestsFlag(
        final boolean flag)
    {
        m__bGenerateTests = flag;
    }

    /**
     * Specifies the "generate-tests" flag.
     * @param flag such flag.
     */
    protected void setGenerateTestsFlag(final boolean flag)
    {
        immutableSetGenerateTestsFlag(flag);
    }

    /**
     * Retrieves the "generate-tests" flag.
     * @return such flag.
     */
    @SuppressWarnings("unused")
    protected boolean getGenerateTestsFlag()
    {
        return m__bGenerateTests;
    }

    /**
     * Retrieves the generate-tests flag, using given
     * properties if necessary.
     * @param properties the properties.
     * @return such flag.
     */
    protected boolean getGenerateTestsFlag(
        @Nullable final Properties properties)
    {
        String t_strResult = getGenerateTests();

        if  (   (t_strResult == null)
             && (properties != null))
        {
            t_strResult =
                properties.getProperty(GENERATE_TESTS);
            setGenerateTests(t_strResult);
        }

        return toBoolean(t_strResult);
    }

    /**
     * Specifies whether to allow empty repository DAO.
     * @param allow such setting.
     */
    protected final void immutableSetAllowEmptyRepositoryDAO(
        final String allow)
    {
        m__strAllowEmptyRepositoryDAO = allow;
    }

    /**
     * Specifies whether to allow empty repository DAO.
     * @param allow such setting.
     */
    public void setAllowEmptyRepositoryDAO(final String allow)
    {
        immutableSetAllowEmptyRepositoryDAO(allow);

        setAllowEmptyRepositoryDAOFlag(toBoolean(allow));
    }

    /**
     * Retrieves whether to allow empty repository DAO.
     * @return such setting.
     */
    public String getAllowEmptyRepositoryDAO()
    {
        return m__strAllowEmptyRepositoryDAO;
    }

    /**
     * Specifies the "allow-empty-repository-dao" flag.
     * @param flag such flag.
     */
    protected final void immutableSetAllowEmptyRepositoryDAOFlag(
        final boolean flag)
    {
        m__bAllowEmptyRepositoryDAO = flag;
    }

    /**
     * Specifies the "allow-empty-repository-dao" flag.
     * @param flag such flag.
     */
    protected void setAllowEmptyRepositoryDAOFlag(final boolean flag)
    {
        immutableSetAllowEmptyRepositoryDAOFlag(flag);
    }

    /**
     * Retrieves the "allow-empty-repository-dao" flag.
     * @return such flag.
     */
    @SuppressWarnings("unused")
    protected boolean getAllowEmptyRepositoryDAOFlag()
    {
        return m__bAllowEmptyRepositoryDAO;
    }

    /**
     * Retrieves the allow-empty-repository-dao flag, using given
     * properties if necessary.
     * @param properties the properties.
     * @return such flag.
     */
    protected boolean getAllowEmptyRepositoryDAOFlag(
        @Nullable final Properties properties)
    {
        String t_strResult = getAllowEmptyRepositoryDAO();

        if  (   (t_strResult == null)
             && (properties != null))
        {
            t_strResult = properties.getProperty(ALLOW_EMPTY_REPOSITORY_DAO);
            setAllowEmptyRepositoryDAO(t_strResult);
        }

        return toBoolean(t_strResult, false);
    }

    /**
     * Specifies whether to implement marker interfaces.
     * @param allow such setting.
     */
    protected final void immutableSetImplementMarkerInterfaces(
        final String allow)
    {
        m__strImplementMarkerInterfaces = allow;
    }

    /**
     * Specifies whether to implement marker interfaces.
     * @param implement such setting.
     */
    public void setImplementMarkerInterfaces(final String implement)
    {
        immutableSetImplementMarkerInterfaces(implement);

        setImplementMarkerInterfacesFlag(toBoolean(implement));
    }

    /**
     * Retrieves whether to implement marker interfaces.
     * @return such setting.
     */
    public String getImplementMarkerInterfaces()
    {
        return m__strImplementMarkerInterfaces;
    }

    /**
     * Specifies the "implement-marker-interfaces" flag.
     * @param flag such flag.
     */
    protected final void immutableSetImplementMarkerInterfacesFlag(
        final boolean flag)
    {
        m__bImplementMarkerInterfaces = flag;
    }

    /**
     * Specifies the "implement-marker-interfaces" flag.
     * @param flag such flag.
     */
    protected void setImplementMarkerInterfacesFlag(final boolean flag)
    {
        immutableSetImplementMarkerInterfacesFlag(flag);
    }

    /**
     * Retrieves the "implement-marker-interfaces" flag.
     * @return such flag.
     */
    @SuppressWarnings("unused")
    protected boolean getImplementMarkerInterfacesFlag()
    {
        return m__bImplementMarkerInterfaces;
    }

    /**
     * Retrieves the implement-marker-interfaces flag, using given
     * properties if necessary.
     * @param properties the properties.
     * @return such flag.
     */
    protected boolean getImplementMarkerInterfacesFlag(
        @Nullable final Properties properties)
    {
        String t_strResult = getImplementMarkerInterfaces();

        if  (   (t_strResult == null)
             && (properties != null))
        {
            t_strResult = properties.getProperty(IMPLEMENT_MARKER_INTERFACES);
            setImplementMarkerInterfaces(t_strResult);
        }

        return toBoolean(t_strResult);
    }

    /**
     * Specifies the custom-sql model.
     * @param model the model.
     */
    protected final void immutableSetCustomSqlModel(final String model)
    {
        m__strCustomSqlModel = model;
    }

    /**
     * Specifies the custom-sql model.
     * @param model the model.
     */
    public void setCustomSqlModel(final String model)
    {
        immutableSetCustomSqlModel(model);
    }

    /**
     * Retrieves the custom-sql model.
     * @return such model.
     */
    @Nullable
    public String getCustomSqlModel()
    {
        return m__strCustomSqlModel;
    }

    /**
     * Retrieves the custom-sql model, using given
     * properties if necessary.
     * @param properties the properties.
     * @return such model.
     */
    @Nullable
    protected String getCustomSqlModel(@Nullable final Properties properties)
    {
        @Nullable String result = getCustomSqlModel();

        if  (   (result == null)
             && (properties != null))
        {
            result = properties.getProperty(CUSTOM_SQL_MODEL);
            setCustomSqlModel(result);
        }

        return result;
    }

    /**
     * Specifies whether to disable custom sql validation.
     * @param allow such setting.
     */
    protected final void immutableSetDisableCustomSqlValidation(
        final String allow)
    {
        m__strDisableCustomSqlValidation = allow;
    }

    /**
     * Specifies whether to disable custom sql validation.
     * @param disable such setting.
     */
    public void setDisableCustomSqlValidation(final String disable)
    {
        immutableSetDisableCustomSqlValidation(disable);

        setDisableCustomSqlValidationFlag(toBoolean(disable));
    }

    /**
     * Retrieves whether to disable custom sql validation.
     * @return such setting.
     */
    public String getDisableCustomSqlValidation()
    {
        return m__strDisableCustomSqlValidation;
    }

    /**
     * Specifies the "disable-custom-sql-validation" flag.
     * @param flag such flag.
     */
    protected final void immutableSetDisableCustomSqlValidationFlag(
        final boolean flag)
    {
        m__bDisableCustomSqlValidation = flag;
    }

    /**
     * Specifies the "disable-custom-sql-validation" flag.
     * @param flag such flag.
     */
    protected void setDisableCustomSqlValidationFlag(final boolean flag)
    {
        immutableSetDisableCustomSqlValidationFlag(flag);
    }

    /**
     * Retrieves the "disable-custom-sql-validation" flag.
     * @return such flag.
     */
    @SuppressWarnings("unused")
    protected boolean getDisableCustomSqlValidationFlag()
    {
        return m__bDisableCustomSqlValidation;
    }

    /**
     * Retrieves the disable-custom-sql-validation flag, using given
     * properties if necessary.
     * @param properties the properties.
     * @return such flag.
     */
    protected boolean getDisableCustomSqlValidationFlag(
        @Nullable final Properties properties)
    {
        String t_strResult = getDisableCustomSqlValidation();

        if  (   (t_strResult == null)
             && (properties != null))
        {
            t_strResult =
                properties.getProperty(DISABLE_CUSTOM_SQL_VALIDATION);
            setDisableCustomSqlValidation(t_strResult);
        }

        return toBoolean(t_strResult);
    }

    /**
     * Specifies the sql.xml file.
     * @param file the new file.
     */
    protected final void immutableSetSqlXmlFile(final File file)
    {
        m__SqlXmlFile = file;
    }

    /**
     * Specifies the sql.xml file.
     * @param file the new file.
     */
    public void setSqlXmlFile(final File file)
    {
        immutableSetSqlXmlFile(file);
    }

    /**
     * Retrieves the sql.xml file.
     * @return such information.
     */
    public File getSqlXmlFile()
    {
        return m__SqlXmlFile;
    }

    /**
     * Retrieves the sql.xml file, using given
     * properties if necessary.
     * @param properties the properties.
     * @return such information.
     */
    protected File getSqlXmlFile(@Nullable final Properties properties)
    {
        File result = getSqlXmlFile();

        if  (   (result == null)
             && (properties != null))
        {
            String t_strSqlXml = properties.getProperty(SQL_XML_FILE);

            if  (t_strSqlXml != null)
            {
                result = new File(t_strSqlXml);
                setSqlXmlFile(result);
            }
        }

        return result;
    }

    /**
     * Specifies the grammar bundle.
     * @param grammarBundle the new grammar bundle.
     */
    protected final void immutableSetGrammarbundle(
        final String grammarBundle)
    {
        m__strGrammarBundleName = grammarBundle;
    }

    /**
     * Specifies the grammar bundle.
     * @param grammarBundle the new grammar bundle.
     */
    public void setGrammarbundle(final String grammarBundle)
    {
        immutableSetGrammarbundle(grammarBundle);
    }

    /**
     * Retrieves the grammar bundle.
     * @return such information.
     */
    public String getGrammarbundle()
    {
        return m__strGrammarBundleName;
    }

    /**
     * Retrieves the grammar bundle, using given
     * properties if necessary.
     * @param properties the properties.
     * @return such information.
     */
    protected String getGrammarbundle(@Nullable final Properties properties)
    {
        String result = getGrammarbundle();

        if  (   (result == null)
             && (properties != null))
        {
            result = properties.getProperty(GRAMMAR_BUNDLE);
            setGrammarbundle(result);
        }

        return result;
    }

    /**
     * Specifies the file encoding.
     * @param encoding the encoding.
     */
    protected final void immutableSetEncoding(final String encoding)
    {
        m__strEncoding = encoding;
    }

    /**
     * Specifies the file encoding.
     * @param encoding the encoding.
     */
    public void setEncoding(final String encoding)
    {
        immutableSetEncoding(encoding);
    }

    /**
     * Retrieves the file encoding.
     * @return such encoding.
     */
    public String getEncoding()
    {
        return m__strEncoding;
    }

    /**
     * Retrieves the encoding, using given
     * properties if necessary.
     * @param properties the properties.
     * @return such information.
     */
    protected String getEncoding(@Nullable final Properties properties)
    {
        String result = getEncoding();

        if  (   (result == null)
             && (properties != null))
        {
            result = properties.getProperty(ENCODING);
            setEncoding(result);
        }

        return result;
    }

    /**
     * Builds the chain.
     * @param chain the chain to be configured.
     * @return the updated chain.
     */
    protected Chain buildChain(@NotNull final Chain chain)
    {
        return
            buildChain(
                chain,
                getGenerateMockDAOImplementationFlag(),
                getGenerateXMLDAOImplementationFlag());
    }

    /**
     * Builds the chain.
     * @param chain the chain to be configured.
     * @param generateMock whether to include Mock implementations.
     * @param generateXML whether to include XML implementations.
     * @return the updated chain.
     */
    @SuppressWarnings("unused")
    protected Chain buildChain(
        final Chain chain,
        final boolean generateMock,
        final boolean generateXML)
    {
        if  (chain != null)
        {
            chain.add(new ParameterValidationHandler());

            chain.add(new Log4JInitializerHandler());

            chain.add(new JdbcConnectionOpeningHandler());
            chain.add(new CustomSqlProviderRetrievalHandler());

            // chain.add(new DatabaseMetaDataCacheReadingHandler());

            chain.add(new MySQL4xMetaDataRetrievalHandler());
            chain.add(new OracleMetaDataRetrievalHandler());
            chain.add(new JdbcMetaDataRetrievalHandler());
            chain.add(new CustomSqlValidationHandler());

            chain.add(new DatabaseMetaDataCacheWritingHandler());

            chain.add(new DatabaseMetaDataLoggingHandler());

            chain.add(new ExternallyManagedFieldsRetrievalHandler());

            chain.add(new TableTemplateHandlerBundle());

            // TODO: Enable all templates
//            chain.add(new BaseRepositoryDAOTemplateHandlerBundle());
//            chain.add(new BaseRepositoryDAOFactoryTemplateHandlerBundle());

//            chain.add(new RepositoryDAOTemplateHandlerBundle());
//            chain.add(new RepositoryDAOFactoryTemplateHandlerBundle());


            //chain.add(new TableRepositoryTemplateHandlerBundle());

            //chain.add(new FunctionsBundle());

            //chain.add(new KeywordRepositoryTemplateHandlerBundle());

            chain.add(new DAOBundle(generateMock, generateXML));

            chain.add(new VOBundle(generateMock, generateXML));

            chain.add(new JdbcConnectionClosingHandler());
        }

        return chain;
    }

    /**
     * Performs any clean up whenever an error occurs.
     * @param buildException the error that triggers this clean-up.
     * @param command the command.
     */
    protected void cleanUpOnError(
        final QueryJBuildException buildException, @NotNull final org.acmsl.queryj.QueryJCommand command)
    {
        Log t_Log = UniqueLogFactory.getLog(QueryJChain.class);

        if  (t_Log != null)
        {
             t_Log.info("Performing clean up");
        }

        try
        {
            new JdbcConnectionClosingHandler().handle(command);
        }
        catch  (@NotNull final QueryJBuildException closingException)
        {
            if  (t_Log != null)
            {
                t_Log.error("Error closing the connection", closingException);
            }
        }
    }

    /**
     * Builds the command.
     * @param command the command to be initialized.
     * @return the initialized command.
     * @precondition command != null
     */
    @NotNull
    protected org.acmsl.queryj.QueryJCommand buildCommand(@NotNull final org.acmsl.queryj.QueryJCommand command)
    {
        return buildCommand(command, getSettings());
    }

    /**
     * Builds the command.
     * @param command the command to be initialized.
     * @param settings the already-loaded settings.
     * @return the initialized command.
     * @precondition command != null
     */
    @NotNull
    protected org.acmsl.queryj.QueryJCommand buildCommand(
        @NotNull final org.acmsl.queryj.QueryJCommand command, final Properties settings)
    {
        @Nullable Properties t_Settings = settings;

        if  (t_Settings == null)
        {
            t_Settings = readSettings(getSettingsFile());
        }

        return buildCommandFromSettingsIfPossible(command, t_Settings);
    }

    /**
     * Builds the command.
     * @param command the command to be initialized.
     * @param settings the properties file.
     * @return the initialized command.
     */
    @NotNull
    protected org.acmsl.queryj.QueryJCommand buildCommandFromSettingsIfPossible(
        @NotNull final org.acmsl.queryj.QueryJCommand command, final Properties settings)
    {
        mapAttributes(
            command.getAttributeMap(),
            getDriver(settings),
            getUrl(settings),
            getUsername(settings),
            getPassword(settings),
            getCatalog(settings),
            getSchema(settings),
            getRepository(settings),
            getPackage(settings),
            getOutputdir(settings),
            getHeaderfile(settings),
            getOutputdirsubfoldersFlag(settings),
            getExtractTablesFlag(settings),
            getExtractProceduresFlag(settings),
            getExtractFunctionsFlag(settings),
            getJndiDataSource(settings),
            getGenerateMockDAOImplementationFlag(settings),
            getGenerateXMLDAOImplementationFlag(settings),
            getGenerateTestsFlag(settings),
            getAllowEmptyRepositoryDAOFlag(settings),
            getImplementMarkerInterfacesFlag(settings),
            getCustomSqlModel(settings),
            getDisableCustomSqlValidationFlag(settings),
            getSqlXmlFile(settings),
            getGrammarbundle(settings),
            getEncoding(settings));

        return command;
    }
                
    /**
     * Maps the attributes in the command map.
     * @param attributes the command attributes.
     * @param driver the JDBC driver.
     * @param url the JDBC url.
     * @param username the JDBC username.
     * @param password the JDBC password.
     * @param catalog the JDBC catalog.
     * @param schema the JDBC schema.
     * @param repository the repository.
     * @param packageName the base package of the generated sources.
     * @param outputDir the output folder.
     * @param header the copyright header.
     * @param outputDirSubFolders whether to use main/ and test/ as
     * sub folders.
     * @param extractTables whether to extract tables or not.
     * @param extractProcedures whether to extract the procedures or not.
     * @param extractFunctions whether to extract the functions or not.
     * @param jndiDataSource the location in JNDI of the
     * {@link javax.sql.DataSource}.
     * @param generateMockDAOImplementation whether to generate Mock DAOs.
     * @param generateXmlDAOImplementation whether to generate XML DAOs.
     * @param generateTests whether to generate test cases.
     * @param allowEmptyRepositoryDAO whether to generate a repository
     * DAO even tough it'll contain no custom queries..
     * @param implementMarkerInterfaces whether to make some generated 
     * sources implement <code></code>org.acmsl.commons.patterns</code> <i>Marker</i>
     * interfaces.
     * @param customSqlModel the format of the custom SQL file.
     * @param disableCustomSqlValidation whether to disable custom sql
     * validation.
     * @param sqlXmlFile the file containing the custom SQL.
     * @param grammarBundle the grammar with irregular singular and plural
     * forms of the table names.
     * @param encoding the encoding.
     */
    @SuppressWarnings("unchecked")
    protected void mapAttributes(
        @Nullable final Map attributes,
        @Nullable final String driver,
        @Nullable final String url,
        @Nullable final String username,
        @Nullable final String password,
        @Nullable final String catalog,
        @Nullable final String schema,
        @Nullable final String repository,
        @Nullable final String packageName,
        @Nullable final File outputDir,
        @Nullable final File header,
        final boolean outputDirSubFolders,
        final boolean extractTables,
        final boolean extractProcedures,
        final boolean extractFunctions,
        @Nullable final String jndiDataSource,
        final boolean generateMockDAOImplementation,
        final boolean generateXmlDAOImplementation,
        final boolean generateTests,
        final boolean allowEmptyRepositoryDAO,
        final boolean implementMarkerInterfaces,
        @Nullable final String customSqlModel,
        final boolean disableCustomSqlValidation,
        @Nullable final File sqlXmlFile,
        @Nullable final String grammarBundle,
        @Nullable final String encoding)
    {
        if  (attributes != null)
        {
            if  (driver != null)
            {
                attributes.put(
                    ParameterValidationHandler.JDBC_DRIVER,
                    driver);
            }

            if  (url != null)
            {
                attributes.put(
                    ParameterValidationHandler.JDBC_URL,
                    url);
            }

            if  (username != null)
            {
                attributes.put(
                    ParameterValidationHandler.JDBC_USERNAME,
                    username);
            }

            if  (password != null)
            {
                attributes.put(
                    ParameterValidationHandler.JDBC_PASSWORD,
                    password);
            }

            if  (catalog != null)
            {
                attributes.put(
                    ParameterValidationHandler.JDBC_CATALOG,
                    catalog);
            }

            if  (schema != null)
            {
                attributes.put(
                    ParameterValidationHandler.JDBC_SCHEMA,
                    schema);
            }

            if  (repository != null)
            {
                attributes.put(
                    ParameterValidationHandler.REPOSITORY,
                    repository);
            }

            if  (packageName != null)
            {
                attributes.put(
                    ParameterValidationHandler.PACKAGE,
                    packageName);
            }

            if  (outputDir != null)
            {
                attributes.put(
                    ParameterValidationHandler.OUTPUT_DIR,
                    outputDir);
            }

            if  (header != null)
            {
                attributes.put(
                    ParameterValidationHandler.HEADER_FILE,
                    header);
            }

            attributes.put(
                ParameterValidationHandler.OUTPUT_DIR_SUBFOLDERS,
                (outputDirSubFolders
                 ?  Boolean.TRUE
                 :  Boolean.FALSE));

            attributes.put(
                ParameterValidationHandler.EXTRACT_TABLES,
                (extractTables
                 ?  Boolean.TRUE
                 :  Boolean.FALSE));

            attributes.put(
                ParameterValidationHandler.EXTRACT_PROCEDURES,
                (extractProcedures
                 ?  Boolean.TRUE
                 :  Boolean.FALSE));

            attributes.put(
                ParameterValidationHandler.EXTRACT_FUNCTIONS,
                (extractFunctions
                 ?  Boolean.TRUE
                 :  Boolean.FALSE));

            if  (jndiDataSource != null)
            {
                attributes.put(
                    ParameterValidationHandler.JNDI_DATASOURCES,
                    jndiDataSource);
            }

            attributes.put(
                ParameterValidationHandler.GENERATE_MOCK_DAO,
                (generateMockDAOImplementation
                 ?  Boolean.TRUE
                 :  Boolean.FALSE));

            attributes.put(
                ParameterValidationHandler.GENERATE_XML_DAO,
                (generateXmlDAOImplementation
                 ?  Boolean.TRUE
                 :  Boolean.FALSE));

            attributes.put(
                ParameterValidationHandler.GENERATE_TESTS,
                (generateTests
                 ?  Boolean.TRUE
                 :  Boolean.FALSE));

            attributes.put(
                ParameterValidationHandler.ALLOW_EMPTY_REPOSITORY_DAO,
                (allowEmptyRepositoryDAO
                 ?  Boolean.TRUE
                 :  Boolean.FALSE));

            attributes.put(
                ParameterValidationHandler.IMPLEMENT_MARKER_INTERFACES,
                (implementMarkerInterfaces
                 ?  Boolean.TRUE
                 :  Boolean.FALSE));

            if  (customSqlModel != null)
            {
                attributes.put(
                    ParameterValidationHandler.CUSTOM_SQL_MODEL,
                    customSqlModel);
            }

            attributes.put(
                ParameterValidationHandler.DISABLE_CUSTOM_SQL_VALIDATION,
                (disableCustomSqlValidation
                 ?  Boolean.TRUE
                 :  Boolean.FALSE));

            if  (sqlXmlFile != null)
            {
                attributes.put(
                    ParameterValidationHandler.SQL_XML_FILE,
                    sqlXmlFile);
            }

            if  (grammarBundle != null)
            {
                attributes.put(
                    ParameterValidationHandler.GRAMMAR_BUNDLE_NAME,
                    grammarBundle);
            }

            if  (encoding != null)
            {
                attributes.put(
                    ParameterValidationHandler.ENCODING,
                    encoding);
            }
        }
    }

    /**
     * Converts given value to boolean.
     * @param value the value.
     * @return the converted value.
     */
    protected boolean toBoolean(final String value)
    {
        return toBoolean(value, true);
    }

    /**
     * Converts given value to boolean.
     * @param value the value.
     * @param defaultValue the default value if input is null.
     * @return the converted value.
     */
    protected boolean toBoolean(
        @Nullable final String value, final boolean defaultValue)
    {
        boolean result = defaultValue;

        if  (value != null)
        {
            String t_strTrimmedValue = value.trim().toLowerCase();

            result =
                (   (t_strTrimmedValue.equals("yes"))
                 || (t_strTrimmedValue.equals("true")));
        }

        return result;
    }

    /**
     * Reads the settings from given file.
     * @param file the file.
     * @return the {@link Properties} instance, or <code>null</code>
     * if the file doesn't exist.
     */
    @Nullable
    protected Properties readSettings(@Nullable final File file)
    {
        @Nullable Properties result = new Properties();

        if  (file != null)
        {
            @Nullable InputStream t_isSettings = null;

            try
            {
                t_isSettings = new FileInputStream(file);

                result.load(t_isSettings);
            }
            catch  (@NotNull final IOException ioException)
            {
                result = null;

                Log t_Log = UniqueLogFactory.getLog(QueryJChain.class);

                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot read the configuration properties "
                        + "file.",
                        ioException);
                }
            }
            finally
            {
                if  (t_isSettings != null)
                {
                    try
                    {
                        t_isSettings.close();
                    }
                    catch  (@NotNull final IOException ioException)
                    {
                        Log t_Log = UniqueLogFactory.getLog(QueryJChain.class);

                        if  (t_Log != null)
                        {
                            t_Log.error(
                                  "Cannot close the configuration properties "
                                + "file.",
                                ioException);
                        }
                    }
                }
            }
        }

        return result;
    }
}
