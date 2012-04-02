//;-*- mode: java -*-
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
import org.acmsl.queryj.tools.ant.AntExternallyManagedFieldsElement;
import org.acmsl.queryj.tools.ant.AntTablesElement;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.QueryJCommand;
import org.acmsl.queryj.tools.QueryJSettings;
import org.acmsl.queryj.tools.QueryJChain;
import org.acmsl.queryj.tools.logging.QueryJAntLog;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.util.Map;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DynamicConfigurator;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Generates QueryJ classes using Ant.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
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
    protected final void immutableSetQueryJChain(final QueryJChain delegee)
    {
        m__QueryJChain = delegee;
    }

    /**
     * Specifies the {@link QueryJChain} to delegate.
     * @param delegee such instance.
     */
    protected void setQueryJChain(final QueryJChain delegee)
    {
        immutableSetQueryJChain(delegee);
    }

    /**
     * Retrieves the delegating {@link QueryJChain} instance.
     * @return such instance.
     */
    protected QueryJChain getQueryJChain()
    {
        return m__QueryJChain;
    }

    /**
     * Specifies the properties file.
     * @param file such file.
     */
    public void setSettingsFile(final File file)
    {
        setSettingsFile(file, getQueryJChain());
    }

    /**
     * Specifies the properties file.
     * @param file such file.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setSettingsFile(final File file, @NotNull final QueryJChain delegee)
    {
        delegee.setSettingsFile(file);
    }

    /**
     * Retrieves the properties file.
     * @return such file.
     */
    public File getSettingsFile()
    {
        return getSettingsFile(getQueryJChain());
    }

    /**
     * Retrieves the properties file.
     * @param delegee the delegee.
     * @return such file.
     * @precondition delegee != null
     */
    protected File getSettingsFile(@NotNull final QueryJChain delegee)
    {
        return delegee.getSettingsFile();
    }

    /**
     * Specifies the driver.
     * @param driver the new driver.
     */
    public void setDriver(final String driver)
    {
        setDriver(driver, getQueryJChain());
    }

    /**
     * Specifies the driver.
     * @param driver the new driver.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setDriver(final String driver, @NotNull final QueryJChain delegee)
    {
        delegee.setDriver(driver);
    }

    /**
     * Retrieves the driver.
     * @return such information.
     */
    public String getDriver()
    {
        return getDriver(getQueryJChain());
    }

    /**
     * Retrieves the driver.
     * @param delegee the delegee.
     * @return such information.
     * @precondition delegee != null
     */
    protected String getDriver(@NotNull final QueryJChain delegee)
    {
        return delegee.getDriver();
    }

    /**
     * Specifies the url.
     * @param url the new url.
     */
    public void setUrl(final String url)
    {
        setUrl(url, getQueryJChain());
    }

    /**
     * Specifies the url.
     * @param url the new url.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setUrl(final String url, @NotNull final QueryJChain delegee)
    {
        delegee.setUrl(url);
    }

    /**
     * Retrieves the url.
     * @return such information.
     */
    public String getUrl()
    {
        return getUrl(getQueryJChain());
    }

    /**
     * Retrieves the url.
     * @param delegee the delegee.
     * @return such information.
     * @precondition delegee != null
     */
    protected String getUrl(@NotNull final QueryJChain delegee)
    {
        return delegee.getUrl();
    }

    /**
     * Specifies the username.
     * @param username the new username.
     */
    public void setUsername(final String username)
    {
        setUsername(username, getQueryJChain());
    }

    /**
     * Specifies the username.
     * @param username the new username.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setUsername(
        final String username, @NotNull final QueryJChain delegee)
    {
        delegee.setUsername(username);
    }

    /**
     * Retrieves the username.
     * @return such information.
     */
    public String getUsername()
    {
        return getUsername(getQueryJChain());
    }

    /**
     * Retrieves the username.
     * @param delegee the delegee.
     * @return such information.
     * @precondition delegee != null
     */
    protected String getUsername(@NotNull final QueryJChain delegee)
    {
        return delegee.getUsername();
    }

    /**
     * Specifies the password.
     * @param password the new password.
     */
    public void setPassword(final String password)
    {
        setPassword(password, getQueryJChain());
    }

    /**
     * Specifies the password.
     * @param password the new password.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setPassword(
        final String password, @NotNull final QueryJChain delegee)
    {
        delegee.setPassword(password);
    }

    /**
     * Retrieves the password.
     * @return such information.
     */
    public String getPassword()
    {
        return getPassword(getQueryJChain());
    }

    /**
     * Retrieves the password.
     * @param delegee the delegee.
     * @return such information.
     * @precondition delegee != null
     */
    protected String getPassword(@NotNull final QueryJChain delegee)
    {
        return delegee.getPassword();
    }

    /**
     * Specifies the catalog.
     * @param catalog the new catalog.
     */
    public void setCatalog(final String catalog)
    {
        setCatalog(catalog, getQueryJChain());
    }

    /**
     * Specifies the catalog.
     * @param catalog the new catalog.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setCatalog(
        final String catalog, @NotNull final QueryJChain delegee)
    {
        delegee.setCatalog(catalog);
    }

    /**
     * Retrieves the catalog.
     * @return such information.
     */
    public String getCatalog()
    {
        return getCatalog(getQueryJChain());
    }

    /**
     * Retrieves the catalog.
     * @param delegee the delegee.
     * @return such information.
     * @precondition delegee != null
     */
    protected String getCatalog(@NotNull final QueryJChain delegee)
    {
        return delegee.getCatalog();
    }

    /**
     * Specifies the schema.
     * @param schema the new schema.
     */
    public void setSchema(final String schema)
    {
        setSchema(schema, getQueryJChain());
    }

    /**
     * Specifies the schema.
     * @param schema the new schema.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setSchema(
        final String schema, @NotNull final QueryJChain delegee)
    {
        delegee.setSchema(schema);
    }

    /**
     * Retrieves the schema.
     * @return such information.
     */
    public String getSchema()
    {
        return getSchema(getQueryJChain());
    }

    /**
     * Retrieves the schema.
     * @param delegee the delegee.
     * @return such information.
     * @precondition delegee != null
     */
    protected String getSchema(@NotNull final QueryJChain delegee)
    {
        return delegee.getSchema();
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     */
    public void setRepository(final String repository)
    {
        setRepository(repository, getQueryJChain());
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setRepository(
        final String repository, @NotNull final QueryJChain delegee)
    {
        delegee.setRepository(repository);
    }

    /**
     * Retrieves the repository.
     * @return such information.
     */
    public String getRepository()
    {
        return getRepository(getQueryJChain());
    }

    /**
     * Retrieves the repository.
     * @param delegee the delegee.
     * @return such information.
     * @precondition delegee != null
     */
    protected String getRepository(@NotNull final QueryJChain delegee)
    {
        return delegee.getRepository();
    }

    /**
     * Specifies the package.
     * @param packageName the new package.
     */
    public void setPackage(final String packageName)
    {
        setPackage(packageName, getQueryJChain());
    }

    /**
     * Specifies the package.
     * @param packageName the new package.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setPackage(
        final String packageName, @NotNull final QueryJChain delegee)
    {
        delegee.setPackage(packageName);
    }

    /**
     * Retrieves the package.
     * @return such information.
     */
    public String getPackage()
    {
        return getPackage(getQueryJChain());
    }

    /**
     * Retrieves the package.
     * @param delegee the delegee.
     * @return such information.
     * @precondition delegee != null
     */
    protected String getPackage(@NotNull final QueryJChain delegee)
    {
        return delegee.getPackage();
    }

    /**
     * Specifies the output dir.
     * @param outputdir the new output dir.
     */
    public void setOutputdir(final File outputdir)
    {
        setOutputdir(outputdir, getQueryJChain());
    }

    /**
     * Specifies the output dir.
     * @param outputdir the new output dir.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setOutputdir(
        final File outputdir, @NotNull final QueryJChain delegee)
    {
        delegee.setOutputdir(outputdir);
    }

    /**
     * Retrieves the output dir.
     * @return such information.
     */
    public File getOutputdir()
    {
        return getOutputdir(getQueryJChain());
    }

    /**
     * Retrieves the output dir.
     * @param delegee the delegee.
     * @return such information.
     * @precondition delegee != null
     */
    protected File getOutputdir(@NotNull final QueryJChain delegee)
    {
        return delegee.getOutputdir();
    }

    /**
     * Specifies the header file.
     * @param headerfile the new header file.
     */
    public void setHeaderfile(final File headerfile)
    {
        setHeaderfile(headerfile, getQueryJChain());
    }

    /**
     * Specifies the header file.
     * @param headerfile the new header file.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setHeaderfile(
        final File headerfile, @NotNull final QueryJChain delegee)
    {
        delegee.setHeaderfile(headerfile);
    }

    /**
     * Retrieves the header file.
     * @return such information.
     */
    public File getHeaderfile()
    {
        return getHeaderfile(getQueryJChain());
    }

    /**
     * Retrieves the header file.
     * @param delegee the delegee.
     * @return such information.
     * @precondition delegee != null
     */
    protected File getHeaderfile(@NotNull final QueryJChain delegee)
    {
        return delegee.getHeaderfile();
    }

    /**
     * Specifies whether to use subfolders.
     * @param outputDirSubFolders such setting.
     */
    public void setOutputdirsubfolders(final String outputDirSubFolders)
    {
        setOutputdirsubfolders(outputDirSubFolders, getQueryJChain());
    }

    /**
     * Specifies whether to use subfolders.
     * @param outputDirSubFolders such setting.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setOutputdirsubfolders(
        final String outputDirSubFolders, @NotNull final QueryJChain delegee)
    {
        delegee.setOutputdirsubfolders(outputDirSubFolders);
    }

    /**
     * Retrieves whether to use subfolders.
     * @return such setting.
     */
    public String getOutputdirsubfolders()
    {
        return getOutputdirsubfolders(getQueryJChain());
    }

    /**
     * Retrieves whether to use subfolders.
     * @param delegee the delegee.
     * @return such setting.
     * @precondition delegee != null
     */
    protected String getOutputdirsubfolders(@NotNull final QueryJChain delegee)
    {
        return delegee.getOutputdirsubfolders();
    }

    /**
     * Specifies whether to extract the tables.
     * @param extractTables the procedure extraction setting.
     */
    public void setExtractTables(final String extractTables)
    {
        setExtractTables(extractTables, getQueryJChain());
    }

    /**
     * Specifies whether to extract the tables.
     * @param extractTables the procedure extraction setting.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setExtractTables(
        final String extractTables, @NotNull final QueryJChain delegee)
    {
        delegee.setExtractTables(extractTables);
    }

    /**
     * Retrieves whether to extract the tables.
     * @return such setting.
     */
    public String getExtractTables()
    {
        return getExtractTables(getQueryJChain());
    }

    /**
     * Retrieves whether to extract the tables.
     * @param delegee the delegee.
     * @return such setting.
     * @precondition delegee != null
     */
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
    public void setExtractProcedures(final String extractProcedures)
    {
        setExtractProcedures(extractProcedures, getQueryJChain());
    }

    /**
     * Specifies whether to extract the procedures.
     * @param extractProcedures the procedure extraction setting.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setExtractProcedures(
        final String extractProcedures, @NotNull final QueryJChain delegee)
    {
        delegee.setExtractProcedures(extractProcedures);
    }

    /**
     * Retrieves whether to extract the procedures.
     * @return such setting.
     */
    public String getExtractProcedures()
    {
        return getExtractProcedures(getQueryJChain());
    }

    /**
     * Retrieves whether to extract the procedures.
     * @param delegee the delegee.
     * @return such setting.
     * @precondition delegee != null
     */
    protected String getExtractProcedures(@NotNull final QueryJChain delegee)
    {
        return delegee.getExtractProcedures();
    }

    /**
     * Specifies whether to extract the functions.
     * @param extractFunctions the procedure extraction setting.
     */
    public void setExtractFunctions(final String extractFunctions)
    {
        setExtractFunctions(extractFunctions, getQueryJChain());
    }

    /**
     * Specifies whether to extract the functions.
     * @param extractFunctions the procedure extraction setting.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setExtractFunctions(
        final String extractFunctions, @NotNull final QueryJChain delegee)
    {
        delegee.setExtractFunctions(extractFunctions);
    }

    /**
     * Retrieves whether to extract the functions.
     * @return such setting.
     */
    public String getExtractFunctions()
    {
        return getExtractFunctions(getQueryJChain());
    }

    /**
     * Retrieves whether to extract the functions.
     * @param delegee the delegee.
     * @return such setting.
     * @precondition delegee != null
     */
    protected String getExtractFunctions(@NotNull final QueryJChain delegee)
    {
        return delegee.getExtractFunctions();
    }

    /**
     * Specifices the JNDI location for the data sources.
     * @param jndiLocation the JNDI location.
     */
    public void setJndiDataSource(final String jndiLocation)
    {
        setJndiDataSource(jndiLocation, getQueryJChain());
    }

    /**
     * Specifices the JNDI location for the data sources.
     * @param jndiLocation the JNDI location.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setJndiDataSource(
        final String jndiLocation, @NotNull final QueryJChain delegee)
    {
        delegee.setJndiDataSource(jndiLocation);
    }

    /**
     * Retrieves the JNDI location for the data sources.
     * @return such information.
     */
    public String getJndiDataSource()
    {
        return getJndiDataSource(getQueryJChain());
    }

    /**
     * Retrieves the JNDI location for the data sources.
     * @param delegee the delegee.
     * @return such information.
     * @precondition delegee != null
     */
    protected String getJndiDataSource(@NotNull final QueryJChain delegee)
    {
        return delegee.getJndiDataSource();
    }

    /**
     * Specifies whether to generate Mock DAO implementations.
     * @param generate such setting.
     */
    public void setGenerateMockDAOImplementation(final String generate)
    {
        setGenerateMockDAOImplementation(generate, getQueryJChain());
    }

    /**
     * Specifies whether to generate Mock DAO implementations.
     * @param generate such setting.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setGenerateMockDAOImplementation(
        final String generate, @NotNull final QueryJChain delegee)
    {
        delegee.setGenerateMockDAOImplementation(generate);
    }

    /**
     * Retrieves whether to generate Mock DAO implementations.
     * @return such setting.
     */
    public String getGenerateMockDAOImplementation()
    {
        return getGenerateMockDAOImplementation(getQueryJChain());
    }

    /**
     * Retrieves whether to generate Mock DAO implementations.
     * @return such setting.
     */
    protected String getGenerateMockDAOImplementation(
        @NotNull final QueryJChain delegee)
    {
        return delegee.getGenerateMockDAOImplementation();
    }

    /**
     * Specifies whether to generate XML DAO implementations.
     * @param generate such setting.
     */
    public void setGenerateXMLDAOImplementation(final String generate)
    {
        setGenerateXMLDAOImplementation(generate, getQueryJChain());
    }

    /**
     * Specifies whether to generate XML DAO implementations.
     * @param generate such setting.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setGenerateXMLDAOImplementation(
        final String generate, @NotNull final QueryJChain delegee)
    {
        delegee.setGenerateXMLDAOImplementation(generate);
    }

    /**
     * Retrieves whether to generate XML DAO implementations.
     * @return such setting.
     */
    public String getGenerateXMLDAOImplementation()
    {
        return getGenerateXMLDAOImplementation(getQueryJChain());
    }

    /**
     * Retrieves whether to generate XML DAO implementations.
     * @param delegee the delegee.
     * @return such setting.
     * @precondition delegee != null
     */
    protected String getGenerateXMLDAOImplementation(
        @NotNull final QueryJChain delegee)
    {
        return delegee.getGenerateXMLDAOImplementation();
    }

    /**
     * Specifies whether to generate test cases.
     * @param generate such setting.
     */
    public void setGenerateTests(final String generate)
    {
        setGenerateTests(generate, getQueryJChain());
    }

    /**
     * Specifies whether to generate test cases.
     * @param generate such setting.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setGenerateTests(
        final String generate, @NotNull final QueryJChain delegee)
    {
        delegee.setGenerateTests(generate);
    }

    /**
     * Retrieves whether to generate test cases.
     * @return such setting.
     */
    public String getGenerateTests()
    {
        return getGenerateTests(getQueryJChain());
    }

    /**
     * Retrieves whether to generate test cases.
     * @param delegee the delegee.
     * @return such setting.
     * @precondition delegee != null
     */
    protected String getGenerateTests(
        @NotNull final QueryJChain delegee)
    {
        return delegee.getGenerateTests();
    }

    /**
     * Specifies whether to allow empty repository DAO.
     * @param allow such setting.
     */
    public void setAllowEmptyRepositoryDAO(final String allow)
    {
        setAllowEmptyRepositoryDAO(allow, getQueryJChain());
    }

    /**
     * Specifies whether to allow empty repository DAO.
     * @param allow such setting.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setAllowEmptyRepositoryDAO(
        final String allow, @NotNull final QueryJChain delegee)
    {
        delegee.setAllowEmptyRepositoryDAO(allow);
    }

    /**
     * Retrieves whether to allow empty repository DAO.
     * @return such setting.
     */
    public String getAllowEmptyRepositoryDAO()
    {
        return getAllowEmptyRepositoryDAO(getQueryJChain());
    }

    /**
     * Retrieves whether to allow empty repository DAO.
     * @param delegee the delegee.
     * @return such setting.
     * @precondition delegee != null
     */
    protected String getAllowEmptyRepositoryDAO(@NotNull final QueryJChain delegee)
    {
        return delegee.getAllowEmptyRepositoryDAO();
    }

    /**
     * Specifies whether to implement marker interfaces.
     * @param implement such setting.
     */
    public void setImplementMarkerInterfaces(final String implement)
    {
        setImplementMarkerInterfaces(implement, getQueryJChain());
    }

    /**
     * Specifies whether to implement marker interfaces.
     * @param implement such setting.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setImplementMarkerInterfaces(
        final String implement, @NotNull final QueryJChain delegee)
    {
        delegee.setImplementMarkerInterfaces(implement);
    }

    /**
     * Retrieves whether to implement marker interfaces.
     * @return such setting.
     */
    public String getImplementMarkerInterfaces()
    {
        return getImplementMarkerInterfaces(getQueryJChain());
    }

    /**
     * Retrieves whether to implement marker interfaces.
     * @param delegee the delegee.
     * @return such setting.
     * @precondition delegee != null
     */
    protected String getImplementMarkerInterfaces(@NotNull final QueryJChain delegee)
    {
        return delegee.getImplementMarkerInterfaces();
    }

    /**
     * Specifies the custom-sql model.
     * @param model the model.
     */
    public void setCustomSqlModel(final String model)
    {
        setCustomSqlModel(model, getQueryJChain());
    }

    /**
     * Specifies the custom-sql model.
     * @param model the model.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setCustomSqlModel(
        final String model, @NotNull final QueryJChain delegee)
    {
        delegee.setCustomSqlModel(model);
    }

    /**
     * Retrieves the custom-sql model.
     * @return such model.
     */
    @Nullable
    public String getCustomSqlModel()
    {
        return getCustomSqlModel(getQueryJChain());
    }

    /**
     * Retrieves the custom-sql model.
     * @param delegee the delegee.
     * @return such model.
     * @precondition delegee != null
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
    public void setDisableCustomSqlValidation(final String disable)
    {
        setDisableCustomSqlValidation(disable, getQueryJChain());
    }

    /**
     * Specifies whether to disable custom sql validation.
     * @param disable such setting.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setDisableCustomSqlValidation(
        final String disable, @NotNull final QueryJChain delegee)
    {
        delegee.setDisableCustomSqlValidation(disable);
    }

    /**
     * Retrieves whether to disable custom sql validation.
     * @return such setting.
     */
    public String getDisableCustomSqlValidation()
    {
        return getDisableCustomSqlValidation(getQueryJChain());
    }

    /**
     * Retrieves whether to disable custom sql validation.
     * @param delegee the delegee.
     * @return such setting.
     * @precondition delegee != null
     */
    protected String getDisableCustomSqlValidation(@NotNull final QueryJChain delegee)
    {
        return delegee.getDisableCustomSqlValidation();
    }

    /**
     * Specifies the sql.xml file.
     * @param file the new file.
     */
    public void setSqlXmlFile(final File file)
    {
        setSqlXmlFile(file, getQueryJChain());
    }

    /**
     * Specifies the sql.xml file.
     * @param file the new file.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setSqlXmlFile(final File file, @NotNull final QueryJChain delegee)
    {
        delegee.setSqlXmlFile(file);
    }

    /**
     * Retrieves the sql.xml file.
     * @return such information.
     */
    public File getSqlXmlFile()
    {
        return getSqlXmlFile(getQueryJChain());
    }

    /**
     * Retrieves the sql.xml file.
     * @param delegee the delegee.
     * @return such information.
     * @precondition delegee != null
     */
    protected File getSqlXmlFile(@NotNull final QueryJChain delegee)
    {
        return delegee.getSqlXmlFile();
    }

    /**
     * Specifies the grammar bundle.
     * @param grammarBundle the new grammar bundle.
     */
    public void setGrammarbundle(final String grammarBundle)
    {
        setGrammarbundle(grammarBundle, getQueryJChain());
    }

    /**
     * Specifies the grammar bundle.
     * @param grammarBundle the new grammar bundle.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setGrammarbundle(
        final String grammarBundle, @NotNull final QueryJChain delegee)
    {
        delegee.setGrammarbundle(grammarBundle);
    }

    /**
     * Retrieves the grammar bundle.
     * @return such information.
     */
    public String getGrammarbundle()
    {
        return getGrammarbundle(getQueryJChain());
    }

    /**
     * Retrieves the grammar bundle.
     * @param delegee the delegee.
     * @return such information.
     * @precondition delegee != null
     */
    protected String getGrammarbundle(@NotNull final QueryJChain delegee)
    {
        return delegee.getGrammarbundle();
    }

    /**
     * Specifies the classpath.
     * @param classpath the new classpath.
     */
    protected final void immutableSetClasspath(final Path classpath)
    {
        m__Classpath = classpath;
    }

    /**
     * Specifies the classpath.
     * @param classpath the new classpath.
     */
    public void setClasspath(final Path classpath)
    {
        immutableSetClasspath(classpath);
    }

    /**
     * Creates the classpath structure.
     * @return such path.
     */
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
    public void setClasspathRef(final Reference classpathReference)
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
    public Path getClasspath()
    {
        return m__Classpath;
    }

    /**
     * Specifies the "tables" nested element.
     * @param tables the tables xml element.
     */
    protected void setTables(final AntTablesElement tables)
    {
        m__Tables = tables;
    }

    /**
     * Retrieves the "tables" nested element.
     * @return such information.
     */
    public AntTablesElement getTables()
    {
        return m__Tables;
    }

    /**
     * Specifies the encoding.
     * @param encoding the new encoding.
     */
    public void setEncoding(final String encoding)
    {
        setEncoding(encoding, getQueryJChain());
    }

    /**
     * Specifies the encoding.
     * @param encoding the new encoding.
     * @param delegee the delegee.
     * @precondition delegee != null
     */
    protected void setEncoding(
        final String encoding, @NotNull final QueryJChain delegee)
    {
        delegee.setEncoding(encoding);
    }

    /**
     * Retrieves the encoding.
     * @return such information.
     */
    public String getEncoding()
    {
        return getEncoding(getQueryJChain());
    }

    /**
     * Retrieves the encoding.
     * @param delegee the delegee.
     * @return such information.
     * @precondition delegee != null
     */
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
        final AntExternallyManagedFieldsElement externallyManagedFields)
    {
        m__ExternallyManagedFields = externallyManagedFields;
    }

    /**
     * Retrieves the "externally-managed-fields" nested element.
     * @return such information.
     */
    public AntExternallyManagedFieldsElement getExternallyManagedFields()
    {
        return m__ExternallyManagedFields;
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
        public AntQueryJChain() {};
                
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
         * @param outputDirSubfolders whether to use main/ and test/ as
         * subfolders.
         * @param extractTables whether to extract tables or not.
         * @param extractProcedures whether to extract the procedures or not.
         * @param extractFunctions whether to extract the functions or not.
         * @param jndiDataSource the location in JNDI of the
         * <code>DataSource</code>.
         * @param generateMockDAOImplementation whether to generate Mock DAOs.
         * @param generateXmlDAOImplementation whether to generate XML DAOs.
         * @param generateTests whether to generate tests.
         * @param allowEmptyRepositoryDAO whether to generate a repository
         * DAO even tough it'll contain no custom queries..
         * @param implementMarkerInterfaces whether to make some generated 
         * sources implement <code>org.acmsl.commons.patterns</code>
         * <i>Marker</i> interfaces.
         * @param customSqlModel the format of the custom SQL file.
         * @param disableCustomSqlValidation whether to disable custom sql
         * validation.
         * @param sqlXmlFile the file containing the custom SQL.
         * @param grammarBundle the grammar with irregular singular and plural
         * forms of the table names.
         * @param encoding the file encoding.
         * @precondition attributes != null
         */
        protected void mapAttributes(
            final Map attributes,
            final String driver,
            final String url,
            final String username,
            final String password,
            final String catalog,
            final String schema,
            final String repository,
            final String packageName,
            final File outputdir,
            final File header,
            final boolean outputdirsubfolders,
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
                grammarBundle,
                encoding);

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
         * @precondition attributes != null
         */
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
                        ParameterValidationHandler.TABLES,
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
        @Nullable Object result = null;

        if  ("tables".equals(name))
        {
            @Nullable AntTablesElement t_ateResult = null;

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
