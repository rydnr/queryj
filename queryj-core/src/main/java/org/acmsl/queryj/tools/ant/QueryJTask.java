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
 * Filename: QueryJTask.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Generates QueryJ classes using Ant.
 *
 */
package org.acmsl.queryj.tools.ant;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.tools.QueryJChain;
import org.acmsl.queryj.tools.logging.QueryJAntLog;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DynamicConfigurator;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Generates QueryJ classes using Ant.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class QueryJTask
    extends     Task
    implements  QueryJSettings,
                DynamicConfigurator
{
    /**
     * The <code>QueryJChain</code> delegee.
     */
    private QueryJChain m__QueryJChain;

    /**
     * The classpath.
     */
    private Path m__Classpath;

    /**
     * The nested tables.
     */
    private AntTablesElement m__Tables;

    /**
     * The externally-managed fields.
     */
    private AntExternallyManagedFieldsElement m__ExternallyManagedFields;

    /**
     * Creates a {@link QueryJTask} instance.
     */
    public QueryJTask()
    {
        // AntQueryJChain is an inner class defined below.
        immutableSetQueryJChain(new AntQueryJChain());
    }

    /**
     * Specifies the {@link QueryJChain} to delegate.
     * @param delegee such instance.
     */
    protected final void immutableSetQueryJChain(@NotNull final QueryJChain delegee)
    {
        m__QueryJChain = delegee;
    }

    /**
     * Specifies the {@link QueryJChain} to delegate.
     * @param delegee such instance.
     */
    @SuppressWarnings("unused")
    protected void setQueryJChain(@NotNull final QueryJChain delegee)
    {
        immutableSetQueryJChain(delegee);
    }

    /**
     * Retrieves the delegating {@link QueryJChain} instance.
     * @return such instance.
     */
    @NotNull
    protected QueryJChain getQueryJChain()
    {
        return m__QueryJChain;
    }

    /**
     * Specifies the properties file.
     * @param file such file.
     */
    @SuppressWarnings("unused")
    public void setSettingsFile(@Nullable final File file)
    {
        setSettingsFile(file, getQueryJChain());
    }

    /**
     * Specifies the properties file.
     * @param file such file.
     * @param delegee the delegee.
     */
    protected void setSettingsFile(@Nullable final File file, @NotNull final QueryJChain delegee)
    {
        if (file != null)
        {
            delegee.setSettingsFile(file);
        }
    }

    /**
     * Retrieves the properties file.
     * @return such file.
     */
    @SuppressWarnings("unused")
    @Nullable
    public File getSettingsFile()
    {
        return getSettingsFile(getQueryJChain());
    }

    /**
     * Retrieves the properties file.
     * @param delegee the delegee.
     * @return such file.
     */
    @Nullable
    protected File getSettingsFile(@NotNull final QueryJChain delegee)
    {
        return delegee.getSettingsFile();
    }

    /**
     * Specifies the driver.
     * @param driver the new driver.
     */
    public void setDriver(@Nullable final String driver)
    {
        setDriver(driver, getQueryJChain());
    }

    /**
     * Specifies the driver.
     * @param driver the new driver.
     * @param delegee the delegee.
     */
    protected void setDriver(@Nullable final String driver, @NotNull final QueryJChain delegee)
    {
        if (driver != null)
        {
            delegee.setDriver(driver);
        }
    }

    /**
     * Retrieves the driver.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getDriver()
    {
        return getDriver(getQueryJChain());
    }

    /**
     * Retrieves the driver.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getDriver(@NotNull final QueryJChain delegee)
    {
        return delegee.getDriver();
    }

    /**
     * Specifies the url.
     * @param url the new url.
     */
    public void setUrl(@Nullable final String url)
    {
        setUrl(url, getQueryJChain());
    }

    /**
     * Specifies the url.
     * @param url the new url.
     * @param delegee the delegee.
     */
    protected void setUrl(@Nullable final String url, @NotNull final QueryJChain delegee)
    {
        if (url != null)
        {
            delegee.setUrl(url);
        }
    }

    /**
     * Retrieves the url.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getUrl()
    {
        return getUrl(getQueryJChain());
    }

    /**
     * Retrieves the url.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getUrl(@NotNull final QueryJChain delegee)
    {
        return delegee.getUrl();
    }

    /**
     * Specifies the username.
     * @param username the new username.
     */
    public void setUsername(@Nullable final String username)
    {
        setUsername(username, getQueryJChain());
    }

    /**
     * Specifies the username.
     * @param username the new username.
     * @param delegee the delegee.
     */
    protected void setUsername(
        @Nullable final String username, @NotNull final QueryJChain delegee)
    {
        if (username != null)
        {
            delegee.setUsername(username);
        }
    }

    /**
     * Retrieves the username.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getUsername()
    {
        return getUsername(getQueryJChain());
    }

    /**
     * Retrieves the username.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getUsername(@NotNull final QueryJChain delegee)
    {
        return delegee.getUsername();
    }

    /**
     * Specifies the password.
     * @param password the new password.
     */
    public void setPassword(@Nullable final String password)
    {
        setPassword(password, getQueryJChain());
    }

    /**
     * Specifies the password.
     * @param password the new password.
     * @param delegee the delegee.
     */
    protected void setPassword(
        @Nullable final String password, @NotNull final QueryJChain delegee)
    {
        if (password != null)
        {
            delegee.setPassword(password);
        }
    }

    /**
     * Retrieves the password.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getPassword()
    {
        return getPassword(getQueryJChain());
    }

    /**
     * Retrieves the password.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getPassword(@NotNull final QueryJChain delegee)
    {
        return delegee.getPassword();
    }

    /**
     * Specifies the catalog.
     * @param catalog the new catalog.
     */
    public void setCatalog(@Nullable final String catalog)
    {
        setCatalog(catalog, getQueryJChain());
    }

    /**
     * Specifies the catalog.
     * @param catalog the new catalog.
     * @param delegee the delegee.
     */
    protected void setCatalog(
        @Nullable final String catalog, @NotNull final QueryJChain delegee)
    {
        if (catalog != null)
        {
            delegee.setCatalog(catalog);
        }
    }

    /**
     * Retrieves the catalog.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getCatalog()
    {
        return getCatalog(getQueryJChain());
    }

    /**
     * Retrieves the catalog.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getCatalog(@NotNull final QueryJChain delegee)
    {
        return delegee.getCatalog();
    }

    /**
     * Specifies the schema.
     * @param schema the new schema.
     */
    public void setSchema(@Nullable final String schema)
    {
        setSchema(schema, getQueryJChain());
    }

    /**
     * Specifies the schema.
     * @param schema the new schema.
     * @param delegee the delegee.
     */
    protected void setSchema(
        @Nullable final String schema, @NotNull final QueryJChain delegee)
    {
        if (schema != null)
        {
            delegee.setSchema(schema);
        }
    }

    /**
     * Retrieves the schema.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getSchema()
    {
        return getSchema(getQueryJChain());
    }

    /**
     * Retrieves the schema.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getSchema(@NotNull final QueryJChain delegee)
    {
        return delegee.getSchema();
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     */
    public void setRepository(@Nullable final String repository)
    {
        setRepository(repository, getQueryJChain());
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     * @param delegee the delegee.
     */
    protected void setRepository(
        @Nullable final String repository, @NotNull final QueryJChain delegee)
    {
        if (repository != null)
        {
            delegee.setRepository(repository);
        }
    }

    /**
     * Retrieves the repository.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getRepository()
    {
        return getRepository(getQueryJChain());
    }

    /**
     * Retrieves the repository.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getRepository(@NotNull final QueryJChain delegee)
    {
        return delegee.getRepository();
    }

    /**
     * Specifies the package.
     * @param packageName the new package.
     */
    public void setPackage(@Nullable final String packageName)
    {
        setPackage(packageName, getQueryJChain());
    }

    /**
     * Specifies the package.
     * @param packageName the new package.
     * @param delegee the delegee.
     */
    protected void setPackage(
        @Nullable final String packageName, @NotNull final QueryJChain delegee)
    {
        if (packageName != null)
        {
            delegee.setPackage(packageName);
        }
    }

    /**
     * Retrieves the package.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getPackage()
    {
        return getPackage(getQueryJChain());
    }

    /**
     * Retrieves the package.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getPackage(@NotNull final QueryJChain delegee)
    {
        return delegee.getPackage();
    }

    /**
     * Specifies the output dir.
     * @param outputdir the new output dir.
     */
    public void setOutputdir(@Nullable final File outputdir)
    {
        setOutputdir(outputdir, getQueryJChain());
    }

    /**
     * Specifies the output dir.
     * @param outputdir the new output dir.
     * @param delegee the delegee.
     */
    protected void setOutputdir(
        @Nullable final File outputdir, @NotNull final QueryJChain delegee)
    {
        if (outputdir != null)
        {
            delegee.setOutputdir(outputdir);
        }
    }

    /**
     * Retrieves the output dir.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public File getOutputdir()
    {
        return getOutputdir(getQueryJChain());
    }

    /**
     * Retrieves the output dir.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected File getOutputdir(@NotNull final QueryJChain delegee)
    {
        return delegee.getOutputdir();
    }

    /**
     * Specifies the header file.
     * @param headerfile the new header file.
     */
    public void setHeaderfile(@Nullable final File headerfile)
    {
        setHeaderfile(headerfile, getQueryJChain());
    }

    /**
     * Specifies the header file.
     * @param headerfile the new header file.
     * @param delegee the delegee.
     */
    protected void setHeaderfile(
        @Nullable final File headerfile, @NotNull final QueryJChain delegee)
    {
        if (headerfile != null)
        {
            delegee.setHeaderfile(headerfile);
        }
    }

    /**
     * Retrieves the header file.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public File getHeaderfile()
    {
        return getHeaderfile(getQueryJChain());
    }

    /**
     * Retrieves the header file.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected File getHeaderfile(@NotNull final QueryJChain delegee)
    {
        return delegee.getHeaderfile();
    }

    /**
     * Specifies whether to use subfolders.
     * @param outputDirSubFolders such setting.
     */
    public void setOutputdirsubfolders(@Nullable final String outputDirSubFolders)
    {
        setOutputdirsubfolders(outputDirSubFolders, getQueryJChain());
    }

    /**
     * Specifies whether to use subfolders.
     * @param outputDirSubFolders such setting.
     * @param delegee the delegee.
     */
    protected void setOutputdirsubfolders(
        @Nullable final String outputDirSubFolders, @NotNull final QueryJChain delegee)
    {
        delegee.setOutputdirsubfolders(outputDirSubFolders);
    }

    /**
     * Retrieves whether to use subfolders.
     * @return such setting.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getOutputdirsubfolders()
    {
        return getOutputdirsubfolders(getQueryJChain());
    }

    /**
     * Retrieves whether to use subfolders.
     * @param delegee the delegee.
     * @return such setting.
     */
    @Nullable
    protected String getOutputdirsubfolders(@NotNull final QueryJChain delegee)
    {
        return delegee.getOutputdirsubfolders();
    }

    /**
     * Specifies whether to extract the tables.
     * @param extractTables the procedure extraction setting.
     */
    @SuppressWarnings("unused")
    public void setExtractTables(@Nullable final String extractTables)
    {
        setExtractTables(extractTables, getQueryJChain());
    }

    /**
     * Specifies whether to extract the tables.
     * @param extractTables the procedure extraction setting.
     * @param delegee the delegee.
     */
    protected void setExtractTables(
        @Nullable final String extractTables, @NotNull final QueryJChain delegee)
    {
        if (extractTables != null)
        {
            delegee.setExtractTables(extractTables);
        }
    }

    /**
     * Retrieves whether to extract the tables.
     * @return such setting.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getExtractTables()
    {
        return getExtractTables(getQueryJChain());
    }

    /**
     * Retrieves whether to extract the tables.
     * @param delegee the delegee.
     * @return such setting.
     */
    @Nullable
    protected String getExtractTables(@NotNull final QueryJChain delegee)
    {
        return delegee.getExtractTables();
    }

    /**
     * Retrieves the "extract-tables" flag.
     * @return such flag.
     */
    protected boolean getExtractTablesFlag()
    {
        return getExtractTablesFlag(getQueryJChain());
    }

    /**
     * Retrieves the "extract-tables" flag.
     * @return such flag.
     */
    protected boolean getExtractTablesFlag(@NotNull final QueryJChain delegee)
    {
        return delegee.getExtractTablesFlag();
    }

    /**
     * Specifies whether to extract the procedures.
     * @param extractProcedures the procedure extraction setting.
     */
    public void setExtractProcedures(@Nullable final String extractProcedures)
    {
        setExtractProcedures(extractProcedures, getQueryJChain());
    }

    /**
     * Specifies whether to extract the procedures.
     * @param extractProcedures the procedure extraction setting.
     * @param delegee the delegee.
     */
    protected void setExtractProcedures(
        @Nullable final String extractProcedures, @NotNull final QueryJChain delegee)
    {
        if (extractProcedures != null)
        {
            delegee.setExtractProcedures(extractProcedures);
        }
    }

    /**
     * Retrieves whether to extract the procedures.
     * @return such setting.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getExtractProcedures()
    {
        return getExtractProcedures(getQueryJChain());
    }

    /**
     * Retrieves whether to extract the procedures.
     * @param delegee the delegee.
     * @return such setting.
     */
    @Nullable
    protected String getExtractProcedures(@NotNull final QueryJChain delegee)
    {
        return delegee.getExtractProcedures();
    }

    /**
     * Specifies whether to extract the functions.
     * @param extractFunctions the procedure extraction setting.
     */
    public void setExtractFunctions(@Nullable final String extractFunctions)
    {
        setExtractFunctions(extractFunctions, getQueryJChain());
    }

    /**
     * Specifies whether to extract the functions.
     * @param extractFunctions the procedure extraction setting.
     * @param delegee the delegee.
     */
    protected void setExtractFunctions(
        @Nullable final String extractFunctions, @NotNull final QueryJChain delegee)
    {
        if (extractFunctions != null)
        {
            delegee.setExtractFunctions(extractFunctions);
        }
    }

    /**
     * Retrieves whether to extract the functions.
     * @return such setting.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getExtractFunctions()
    {
        return getExtractFunctions(getQueryJChain());
    }

    /**
     * Retrieves whether to extract the functions.
     * @param delegee the delegee.
     * @return such setting.
     */
    @Nullable
    protected String getExtractFunctions(@NotNull final QueryJChain delegee)
    {
        return delegee.getExtractFunctions();
    }

    /**
     * Specifices the JNDI location for the data sources.
     * @param jndiLocation the JNDI location.
     */
    public void setJndiDataSource(@Nullable final String jndiLocation)
    {
        setJndiDataSource(jndiLocation, getQueryJChain());
    }

    /**
     * Specifices the JNDI location for the data sources.
     * @param jndiLocation the JNDI location.
     * @param delegee the delegee.
     */
    protected void setJndiDataSource(
        @Nullable final String jndiLocation, @NotNull final QueryJChain delegee)
    {
        if (jndiLocation != null)
        {
            delegee.setJndiDataSource(jndiLocation);
        }
    }

    /**
     * Retrieves the JNDI location for the data sources.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getJndiDataSource()
    {
        return getJndiDataSource(getQueryJChain());
    }

    /**
     * Retrieves the JNDI location for the data sources.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getJndiDataSource(@NotNull final QueryJChain delegee)
    {
        return delegee.getJndiDataSource();
    }

    /**
     * Specifies whether to generate Mock DAO implementations.
     * @param generate such setting.
     */
    public void setGenerateMockDAOImplementation(@Nullable final String generate)
    {
        setGenerateMockDAOImplementation(generate, getQueryJChain());
    }

    /**
     * Specifies whether to generate Mock DAO implementations.
     * @param generate such setting.
     * @param delegee the delegee.
     */
    protected void setGenerateMockDAOImplementation(
        @Nullable final String generate, @NotNull final QueryJChain delegee)
    {
        if (generate != null)
        {
            delegee.setGenerateMockDAOImplementation(generate);
        }
    }

    /**
     * Retrieves whether to generate Mock DAO implementations.
     * @return such setting.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getGenerateMockDAOImplementation()
    {
        return getGenerateMockDAOImplementation(getQueryJChain());
    }

    /**
     * Retrieves whether to generate Mock DAO implementations.
     * @return such setting.
     */
    @Nullable
    protected String getGenerateMockDAOImplementation(
        @NotNull final QueryJChain delegee)
    {
        return delegee.getGenerateMockDAOImplementation();
    }

    /**
     * Specifies whether to generate XML DAO implementations.
     * @param generate such setting.
     */
    public void setGenerateXMLDAOImplementation(@Nullable final String generate)
    {
        setGenerateXMLDAOImplementation(generate, getQueryJChain());
    }

    /**
     * Specifies whether to generate XML DAO implementations.
     * @param generate such setting.
     * @param delegee the delegee.
     */
    protected void setGenerateXMLDAOImplementation(
        @Nullable final String generate, @NotNull final QueryJChain delegee)
    {
        delegee.setGenerateXMLDAOImplementation(generate);
    }

    /**
     * Retrieves whether to generate XML DAO implementations.
     * @return such setting.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getGenerateXMLDAOImplementation()
    {
        return getGenerateXMLDAOImplementation(getQueryJChain());
    }

    /**
     * Retrieves whether to generate XML DAO implementations.
     * @param delegee the delegee.
     * @return such setting.
     */
    @Nullable
    protected String getGenerateXMLDAOImplementation(
        @NotNull final QueryJChain delegee)
    {
        return delegee.getGenerateXMLDAOImplementation();
    }

    /**
     * Specifies whether to generate test cases.
     * @param generate such setting.
     */
    public void setGenerateTests(@Nullable final String generate)
    {
        setGenerateTests(generate, getQueryJChain());
    }

    /**
     * Specifies whether to generate test cases.
     * @param generate such setting.
     * @param delegee the delegee.
     */
    protected void setGenerateTests(
        @Nullable final String generate, @NotNull final QueryJChain delegee)
    {
        if (generate != null)
        {
            delegee.setGenerateTests(generate);
        }
    }

    /**
     * Retrieves whether to generate test cases.
     * @return such setting.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getGenerateTests()
    {
        return getGenerateTests(getQueryJChain());
    }

    /**
     * Retrieves whether to generate test cases.
     * @param delegee the delegee.
     * @return such setting.
     */
    @Nullable
    protected String getGenerateTests(
        @NotNull final QueryJChain delegee)
    {
        return delegee.getGenerateTests();
    }

    /**
     * Specifies whether to allow empty repository DAO.
     * @param allow such setting.
     */
    @SuppressWarnings("unused")
    public void setAllowEmptyRepositoryDAO(@Nullable final String allow)
    {
        setAllowEmptyRepositoryDAO(allow, getQueryJChain());
    }

    /**
     * Specifies whether to allow empty repository DAO.
     * @param allow such setting.
     * @param delegee the delegee.
     */
    protected void setAllowEmptyRepositoryDAO(
        @Nullable final String allow, @NotNull final QueryJChain delegee)
    {
        if (allow != null)
        {
            delegee.setAllowEmptyRepositoryDAO(allow);
        }
    }

    /**
     * Retrieves whether to allow empty repository DAO.
     * @return such setting.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getAllowEmptyRepositoryDAO()
    {
        return getAllowEmptyRepositoryDAO(getQueryJChain());
    }

    /**
     * Retrieves whether to allow empty repository DAO.
     * @param delegee the delegee.
     * @return such setting.
     */
    @Nullable
    protected String getAllowEmptyRepositoryDAO(@NotNull final QueryJChain delegee)
    {
        return delegee.getAllowEmptyRepositoryDAO();
    }

    /**
     * Specifies whether to implement marker interfaces.
     * @param implement such setting.
     */
    @SuppressWarnings("unused")
    public void setImplementMarkerInterfaces(@Nullable final String implement)
    {
        setImplementMarkerInterfaces(implement, getQueryJChain());
    }

    /**
     * Specifies whether to implement marker interfaces.
     * @param implement such setting.
     * @param delegee the delegee.
     */
    protected void setImplementMarkerInterfaces(
        @Nullable final String implement, @NotNull final QueryJChain delegee)
    {
        if (implement != null)
        {
            delegee.setImplementMarkerInterfaces(implement);
        }
    }

    /**
     * Retrieves whether to implement marker interfaces.
     * @return such setting.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getImplementMarkerInterfaces()
    {
        return getImplementMarkerInterfaces(getQueryJChain());
    }

    /**
     * Retrieves whether to implement marker interfaces.
     * @param delegee the delegee.
     * @return such setting.
     */
    @Nullable
    protected String getImplementMarkerInterfaces(@NotNull final QueryJChain delegee)
    {
        return delegee.getImplementMarkerInterfaces();
    }

    /**
     * Specifies the custom-sql model.
     * @param model the model.
     */
    public void setCustomSqlModel(@Nullable final String model)
    {
        setCustomSqlModel(model, getQueryJChain());
    }

    /**
     * Specifies the custom-sql model.
     * @param model the model.
     * @param delegee the delegee.
     */
    protected void setCustomSqlModel(
        @Nullable final String model, @NotNull final QueryJChain delegee)
    {
        if (model != null)
        {
            delegee.setCustomSqlModel(model);
        }
    }

    /**
     * Retrieves the custom-sql model.
     * @return such model.
     */
    @Nullable
    @SuppressWarnings("unused")
    public String getCustomSqlModel()
    {
        return getCustomSqlModel(getQueryJChain());
    }

    /**
     * Retrieves the custom-sql model.
     * @param delegee the delegee.
     * @return such model.
     */
    @Nullable
    protected String getCustomSqlModel(@NotNull final QueryJChain delegee)
    {
        return delegee.getCustomSqlModel();
    }

    /**
     * Specifies whether to disable custom sql validation.
     * @param disable such setting.
     */
    @SuppressWarnings("unused")
    public void setDisableCustomSqlValidation(@Nullable final String disable)
    {
        setDisableCustomSqlValidation(disable, getQueryJChain());
    }

    /**
     * Specifies whether to disable custom sql validation.
     * @param disable such setting.
     * @param delegee the delegee.
     */
    protected void setDisableCustomSqlValidation(
        @Nullable final String disable, @NotNull final QueryJChain delegee)
    {
        if (disable != null)
        {
            delegee.setDisableCustomSqlValidation(disable);
        }
    }

    /**
     * Retrieves whether to disable custom sql validation.
     * @return such setting.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getDisableCustomSqlValidation()
    {
        return getDisableCustomSqlValidation(getQueryJChain());
    }

    /**
     * Retrieves whether to disable custom sql validation.
     * @param delegee the delegee.
     * @return such setting.
     */
    @Nullable
    protected String getDisableCustomSqlValidation(@NotNull final QueryJChain delegee)
    {
        return delegee.getDisableCustomSqlValidation();
    }

    /**
     * Specifies the sql.xml file.
     * @param file the new file.
     */
    public void setSqlXmlFile(@Nullable final File file)
    {
        setSqlXmlFile(file, getQueryJChain());
    }

    /**
     * Specifies the sql.xml file.
     * @param file the new file.
     * @param delegee the delegee.
     */
    protected void setSqlXmlFile(@Nullable final File file, @NotNull final QueryJChain delegee)
    {
        if (file != null)
        {
            delegee.setSqlXmlFile(file);
        }
    }

    /**
     * Retrieves the sql.xml file.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public File getSqlXmlFile()
    {
        return getSqlXmlFile(getQueryJChain());
    }

    /**
     * Retrieves the sql.xml file.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected File getSqlXmlFile(@NotNull final QueryJChain delegee)
    {
        return delegee.getSqlXmlFile();
    }

    /**
     * Specifies the grammar name.
     * @param folder the new grammar name.
     */
    public void setGrammarFolder(@Nullable final File folder)
    {
        setGrammarFolder(folder, getQueryJChain());
    }

    /**
     * Specifies the grammar folder.
     * @param folder the new grammar folder.
     * @param delegee the delegee.
     */
    protected void setGrammarFolder(
        @Nullable final File folder, @NotNull final QueryJChain delegee)
    {
        if (folder != null)
        {
            delegee.setGrammarFolder(folder);
        }
    }

    /**
     * Retrieves the grammar bundle.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public File getGrammarFolder()
    {
        return getGrammarFolder(getQueryJChain());
    }

    /**
     * Retrieves the grammar folder.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected File getGrammarFolder(@NotNull final QueryJChain delegee)
    {
        return delegee.getGrammarFolder();
    }

    /**
     * Specifies the grammar name.
     * @param grammarName the new grammar name.
     */
    public void setGrammarName(@Nullable final String grammarName)
    {
        setGrammarName(grammarName, getQueryJChain());
    }

    /**
     * Specifies the grammar name.
     * @param grammarName the new grammar name.
     * @param delegee the delegee.
     */
    protected void setGrammarName(
        @Nullable final String grammarName, @NotNull final QueryJChain delegee)
    {
        if (grammarName != null)
        {
            delegee.setGrammarName(grammarName);
        }
    }

    /**
     * Retrieves the grammar bundle.
     * @return such information.
     */
    @Nullable
    @SuppressWarnings("unused")
    public String getGrammarName()
    {
        return getGrammarName(getQueryJChain());
    }

    /**
     * Retrieves the grammar bundle.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getGrammarName(@NotNull final QueryJChain delegee)
    {
        return delegee.getGrammarName();
    }

    /**
     * Specifies the grammar suffix.
     * @param grammarSuffix the new grammar suffix.
     */
    public void setGrammarSuffix(@Nullable final String grammarSuffix)
    {
        setGrammarSuffix(grammarSuffix, getQueryJChain());
    }

    /**
     * Specifies the grammar suffix.
     * @param grammarSuffix the new grammar suffix.
     * @param delegee the delegee.
     */
    protected void setGrammarSuffix(
        @Nullable final String grammarSuffix, @NotNull final QueryJChain delegee)
    {
        if (grammarSuffix != null)
        {
            delegee.setGrammarSuffix(grammarSuffix);
        }
    }

    /**
     * Retrieves the grammar suffix.
     * @return such information.
     */
    @Nullable
    @SuppressWarnings("unused")
    public String getGrammarSuffix()
    {
        return getGrammarSuffix(getQueryJChain());
    }

    /**
     * Retrieves the grammar suffix.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getGrammarSuffix(@NotNull final QueryJChain delegee)
    {
        return delegee.getGrammarSuffix();
    }

    /**
     * Specifies the classpath.
     * @param classpath the new classpath.
     */
    protected final void immutableSetClasspath(@NotNull final Path classpath)
    {
        m__Classpath = classpath;
    }

    /**
     * Specifies the classpath.
     * @param classpath the new classpath.
     */
    public void setClasspath(@NotNull final Path classpath)
    {
        immutableSetClasspath(classpath);
    }

    /**
     * Creates the classpath structure.
     * @return such path.
     */
    @Nullable
    public Path createClasspath()
    {
        Path t_Classpath = getClasspath();

        if  (t_Classpath == null)
        {
            t_Classpath = new Path(getProject());

            setClasspath(t_Classpath);
        }

        return t_Classpath.createPath();
    }

    /**
     * Reference to the classpath.
     * @param classpathReference the reference to the class path.
     */
    @SuppressWarnings("unused")
    public void setClasspathRef(@Nullable final Reference classpathReference)
    {
        Path t_Path = createClasspath();

        if  (t_Path != null)
        {
            t_Path.setRefid(classpathReference);
        }
    }

    /**
     * Retrieves the classpath.
     * @return such information.
     */
    @Nullable
    public Path getClasspath()
    {
        return m__Classpath;
    }

    /**
     * Specifies the "tables" nested element.
     * @param tables the tables xml element.
     */
    protected void setTables(@NotNull final AntTablesElement tables)
    {
        m__Tables = tables;
    }

    /**
     * Retrieves the "tables" nested element.
     * @return such information.
     */
    @Nullable
    public AntTablesElement getTables()
    {
        return m__Tables;
    }

    /**
     * Specifies the encoding.
     * @param encoding the new encoding.
     */
    public void setEncoding(@Nullable final String encoding)
    {
        setEncoding(encoding, getQueryJChain());
    }

    /**
     * Specifies the encoding.
     * @param encoding the new encoding.
     * @param delegee the delegee.
     */
    protected void setEncoding(
        @Nullable final String encoding, @NotNull final QueryJChain delegee)
    {
        if (encoding != null)
        {
            delegee.setEncoding(encoding);
        }
    }

    /**
     * Retrieves the encoding.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getEncoding()
    {
        return getEncoding(getQueryJChain());
    }

    /**
     * Retrieves the encoding.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getEncoding(@NotNull final QueryJChain delegee)
    {
        return delegee.getEncoding();
    }

    /**
     * Requests the chained logic to be performed.
     * @throws BuildException whenever the required
     * parameters are not present or valid.
     */
    public void execute()
        throws  BuildException
    {
        execute(getQueryJChain());
    }

    /**
     * Requests the chained logic to be performed.
     * @param delegee the delegee.
     * @throws BuildException whenever the required
     * parameters are not present or valid.
     */
    protected void execute(@NotNull final QueryJChain delegee)
        throws  BuildException
    {
        try
        {
            delegee.process();
        }
        catch  (@NotNull final QueryJBuildException buildException)
        {
            throw
                new BuildException(
                    buildException.getMessage(), buildException);
        }
    }

    /**
     * Specifies the "externally-managed-fields" nested element.
     * @param externallyManagedFields the externally-managed-fields xml
     * element.
     */
    protected void setExternallyManagedFields(
        @NotNull final AntExternallyManagedFieldsElement externallyManagedFields)
    {
        m__ExternallyManagedFields = externallyManagedFields;
    }

    /**
     * Retrieves the "externally-managed-fields" nested element.
     * @return such information.
     */
    @Nullable
    public AntExternallyManagedFieldsElement getExternallyManagedFields()
    {
        return m__ExternallyManagedFields;
    }

    /**
     * Specifies the thread count.
     * @param threadCount such value.
     */
    public void setThreadCount(final int threadCount)
    {
        setThreadCount(threadCount, getQueryJChain());
    }

    /**
     * Specifies the thread count.
     * @param threadCount such value.
     */
    protected void setThreadCount(final int threadCount, @NotNull final QueryJChain delegee)
    {
        delegee.setThreadCount(threadCount);
    }

    /**
     * Retrieves the thread count.
     * @return such value.
     */
    @SuppressWarnings("unused")
    public int getThreadCount()
    {
        return getThreadCount(getQueryJChain());
    }

    /**
     * Retrieves the thread count.
     * @return such value.
     */
    @SuppressWarnings("unused")
    protected int getThreadCount(@NotNull final QueryJChain delegee)
    {
        return delegee.getThreadCount();
    }

    /**
     * Specifies whether to disable custom sql validation.
     * @param disable such setting.
     */
    @SuppressWarnings("unused")
    public void setDisableCaching(@Nullable final String disable)
    {
        setDisableCaching(disable, getQueryJChain());
    }

    /**
     * Specifies whether to disable custom sql validation.
     * @param disable such setting.
     */
    public void setDisableCaching(@Nullable final String disable, @NotNull final QueryJChain delegee)
    {
        if (disable != null)
        {
            delegee.setDisableCaching(disable);
        }
    }

    /**
     * Retrieves whether to disable caching.
     * @return such setting.
     */
    @SuppressWarnings("unused")
    public boolean isCaching()
    {
        return isCaching(getQueryJChain());
    }

    /**
     * Retrieves whether to disable caching.
     * @return such setting.
     */
    protected boolean isCaching(@NotNull final QueryJChain delegee)
    {
        return delegee.isCaching();
    }

    /**
     * Specifies whether to enable caching or not.
     * @param caching such condition.
     */
    public void setCaching(final boolean caching)
    {
        setCaching(caching, getQueryJChain());
    }

    /**
     * Specifies whether to enable caching or not.
     * @param caching such condition.
     */
    protected void setCaching(final boolean caching, @NotNull final QueryJChain delegee)
    {
        delegee.setDisableCaching(!caching);
    }

    /**
     * Specifies whether to include timestamps in the generated code.
     * @param disableGenerateTimestamps such choice.
     */
    public void setDisableGenerationTimestamps(final boolean disableGenerateTimestamps)
    {
        setDisableGenerationTimestamps(disableGenerateTimestamps, getQueryJChain());
    }

    /**
     * Specifies whether to include timestamps in the generated code.
     * @param disableGenerateTimestamps such choice.
     */
    protected void setDisableGenerationTimestamps(
        final boolean disableGenerateTimestamps, @NotNull final QueryJChain delegee)
    {
        delegee.setDisableGenerationTimestampsFlag(disableGenerateTimestamps);
    }

    /**
     * Retrieves whether to include timestamps in the generated code.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean getDisableGenerationTimestamps()
    {
        return getDisableGenerationTimestamps(getQueryJChain());
    }

    /**
     * Retrieves whether to include timestamps in the generated code.
     * @return such information.
     */
    protected final boolean getDisableGenerationTimestamps(@NotNull final QueryJChain delegee)
    {
        return delegee.getDisableGenerationTimestampsFlag();
    }

    /**
     * Specifies whether to include NotNull annotations in the generated code.
     * @param disableNotNullAnnotations such choice.
     */
    public void setDisableNotNullAnnotations(final boolean disableNotNullAnnotations)
    {
        setDisableNotNullAnnotations(disableNotNullAnnotations, getQueryJChain());
    }

    /**
     * Specifies whether to include NotNull annotations in the generated code.
     * @param disableNotNullAnnotations such choice.
     */
    protected void setDisableNotNullAnnotations(
        final boolean disableNotNullAnnotations, @NotNull final QueryJChain delegee)
    {
        delegee.setDisableNotNullAnnotationsFlag(disableNotNullAnnotations);
    }

    /**
     * Retrieves whether to include NotNull annotations in the generated code.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean getDisableNotNullAnnotations()
    {
        return getDisableNotNullAnnotations(getQueryJChain());
    }

    /**
     * Retrieves whether to include NotNull annotations in the generated code.
     * @return such information.
     */
    protected final boolean getDisableNotNullAnnotations(@NotNull final QueryJChain delegee)
    {
        return delegee.getDisableNotNullAnnotationsFlag();
    }

    /**
     * Specifies whether to include Checkthread.org annotations in the generated code.
     * @param disableCheckthreadAnnotations such choice.
     */
    public void setDisableCheckthreadAnnotations(final boolean disableCheckthreadAnnotations)
    {
        setDisableNotNullAnnotations(disableCheckthreadAnnotations, getQueryJChain());
    }

    /**
     * Specifies whether to include Checkthread.org annotations in the generated code.
     * @param disableCheckthreadAnnotations such choice.
     */
    @SuppressWarnings("unused")
    protected void setDisableCheckthreadAnnotations(
        final boolean disableCheckthreadAnnotations, @NotNull final QueryJChain delegee)
    {
        delegee.setDisableCheckthreadAnnotationsFlag(disableCheckthreadAnnotations);
    }

    /**
     * Retrieves whether to include Checkthread.org annotations in the generated code.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean getDisableCheckthreadAnnotations()
    {
        return getDisableNotNullAnnotations(getQueryJChain());
    }

    /**
     * Retrieves whether to include Checkthread.org annotations in the generated code.
     * @return such information.
     */
    @SuppressWarnings("unused")
    protected final boolean getDisableCheckthreadAnnotations(@NotNull final QueryJChain delegee)
    {
        return delegee.getDisableCheckthreadAnnotationsFlag();
    }

    /**
     * Customizes <code>QueryJChain</code> to get parameters from Ant.
     * @author <a href="mailto:chous@acm-sl.org"
               >Jose San Leandro</a>
     */
    protected class AntQueryJChain
        extends  QueryJChain
    {
        /**
         * Creates an {@link AntQueryJChain} instance.
         */
        public AntQueryJChain() {}
                
        /**
         * Builds the command.
         * @return the initialized command.
         */
        @NotNull
        protected QueryJCommand buildCommand()
        {
            return
                buildCommand(
                    new QueryJCommand(new QueryJAntLog(getProject())));
        }

        /**
         * {@inheritDoc}
         */
        @Override
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
            @Nullable final File outputdir,
            @Nullable final File header,
            final boolean outputdirsubfolders,
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
            @Nullable final File grammarFolder,
            @Nullable final String grammarName,
            @Nullable final String grammarSuffix,
            @Nullable final String encoding,
            final boolean caching,
            final int threadCount,
            final boolean disableTimestamps,
            final boolean disableNotNullAnnotations,
            final boolean disableCheckthreadAnnotations)
        {
            super.mapAttributes(
                attributes,
                driver,
                url,
                username,
                password,
                catalog,
                schema,
                repository,
                packageName,
                outputdir,
                header,
                outputdirsubfolders,
                extractTables,
                extractProcedures,
                extractFunctions,
                jndiDataSource,
                generateMockDAOImplementation,
                generateXmlDAOImplementation,
                generateTests,
                allowEmptyRepositoryDAO,
                implementMarkerInterfaces,
                customSqlModel,
                disableCustomSqlValidation,
                sqlXmlFile,
                grammarFolder,
                grammarName,
                grammarSuffix,
                encoding,
                caching,
                threadCount,
                disableTimestamps,
                disableNotNullAnnotations,
                disableCheckthreadAnnotations);

            mapAttributes(
                attributes,
                getTables(),
                getExternallyManagedFields(),
                getClasspath());
        }

        /**
         * Maps the attributes in the command map.
         * @param attributes the command attributes.
         * @param tables the custom tables.
         * @param externallyManagedFields the externally-managed fields.
         * @param classpath the classpath.
         */
        @SuppressWarnings("unchecked")
        protected void mapAttributes(
            @Nullable final Map attributes,
            @Nullable final AntTablesElement tables,
            @Nullable final AntExternallyManagedFieldsElement externallyManagedFields,
            @Nullable final Path classpath)
        {
            if  (attributes != null)
            {
                if  (classpath != null)
                {
                    attributes.put(
                        ParameterValidationHandler.CLASSPATH,
                        classpath);
                }

                if  (tables != null)
                {
                    attributes.put(
                        ParameterValidationHandler.EXPLICIT_TABLES,
                        tables);
                }

                if  (externallyManagedFields != null)
                {
                    attributes.put(
                        ParameterValidationHandler.EXTERNALLY_MANAGED_FIELDS,
                        externallyManagedFields);
                }
            }
        }
    }

    /**
     * Specifies a dynamic attribute.
     * @param name the attribute name.
     * @param value the attribute value.
     */
    public void setDynamicAttribute(
        final String name, final String value)
    {
        throw
            new BuildException(
                  "No dynamic attributes are supported ("
                + name + "=" + value + ")");
    }

    /**
     * Creates a dynamic element.
     * @param name the element's name.
     * @return the object.
     * @throws BuildException if the element is not supported.
     */
    @Nullable
    public Object createDynamicElement(final String name)
    {
        @Nullable Object result;

        if  ("tables".equals(name))
        {
            @Nullable AntTablesElement t_ateResult;

            if  (!getExtractTablesFlag())
            {
                throw new BuildException("Table extraction is disabled.");
            }
            else
            {
                t_ateResult = new AntTablesElement();
            }

            setTables(t_ateResult);

            result = t_ateResult;
        }
        else if  ("externally-managed-fields".equals(name))
        {
            @NotNull AntExternallyManagedFieldsElement t_aemfeResult =
                new AntExternallyManagedFieldsElement();

            setExternallyManagedFields(t_aemfeResult);

            result = t_aemfeResult;
        }
        else
        {
            throw new BuildException(name + " elements are not supported");
        }

        return result;
    }
}
