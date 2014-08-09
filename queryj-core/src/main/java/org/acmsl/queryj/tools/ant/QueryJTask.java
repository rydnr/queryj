/*
                        QueryJ Core

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
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.SerializablePropertiesConfiguration;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.tools.QueryJChain;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;

/*
 * Importing some JDK classes.
 */
import java.io.File;

/*
 * Importing some Ant classes.
 */
import org.apache.commons.logging.Log;
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
     * String literal: " elements are not supported"
     */
    public static final String ELEMENTS_ARE_NOT_SUPPORTED = " elements are not supported";

    /**
     * String literal: "externally-managed-fields"
     */
    public static final String EXTERNALLY_MANAGED_FIELDS = ParameterValidationHandler.EXTERNALLY_MANAGED_FIELDS;

    /**
     * String literal: "No dynamic attributes are supported ("
     */
    public static final String NO_DYNAMIC_ATTRIBUTES_ARE_SUPPORTED = "No dynamic attributes are supported (";

    /**
     * The <code>QueryJChain</code> delegee.
     */
    private QueryJCommand m__QueryJCommand;

    /**
     * The classpath.
     */
    private Path m__Classpath;

    /**
     * The nested tables.
     */
    private AntTablesElement m__Tables;

    /**
     * Creates a {@link QueryJTask} instance.
     * @param log the log.
     */
    public QueryJTask(@NotNull final Log log)
    {
        immutableSetQueryJCommand(
            new ConfigurationQueryJCommandImpl(new SerializablePropertiesConfiguration(), log));
    }

    /**
     * Specifies the {@link QueryJCommand} to delegate.
     * @param delegee such instance.
     */
    protected final void immutableSetQueryJCommand(@NotNull final QueryJCommand delegee)
    {
        this.m__QueryJCommand = delegee;
    }

    /**
     * Specifies the {@link QueryJCommand} to delegate.
     * @param delegee such instance.
     */
    @SuppressWarnings("unused")
    protected void setQueryJCommand(@NotNull final QueryJCommand delegee)
    {
        immutableSetQueryJCommand(delegee);
    }

    /**
     * Retrieves the delegating {@link QueryJCommand} instance.
     * @return such instance.
     */
    @NotNull
    protected QueryJCommand getQueryJCommand()
    {
        return m__QueryJCommand;
    }

    /**
     * Specifies the driver.
     * @param driver the new driver.
     */
    public void setDriver(@Nullable final String driver)
    {
        setDriver(driver, getQueryJCommand());
    }

    /**
     * Specifies the driver.
     * @param driver the new driver.
     * @param delegee the delegee.
     */
    protected void setDriver(@Nullable final String driver, @NotNull final QueryJCommand delegee)
    {
        if (driver != null)
        {
            delegee.setSetting(JDBC_DRIVER, driver);
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
        return getDriver(getQueryJCommand());
    }

    /**
     * Retrieves the driver.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getDriver(@NotNull final QueryJCommand delegee)
    {
        return delegee.getSetting(JDBC_DRIVER);
    }

    /**
     * Specifies the url.
     * @param url the new url.
     */
    public void setUrl(@Nullable final String url)
    {
        setUrl(url, getQueryJCommand());
    }

    /**
     * Specifies the url.
     * @param url the new url.
     * @param delegee the delegee.
     */
    protected void setUrl(@Nullable final String url, @NotNull final QueryJCommand delegee)
    {
        if (url != null)
        {
            delegee.setSetting(JDBC_URL, url);
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
        return getUrl(getQueryJCommand());
    }

    /**
     * Retrieves the url.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getUrl(@NotNull final QueryJCommand delegee)
    {
        return delegee.getSetting(JDBC_URL);
    }

    /**
     * Specifies the username.
     * @param username the new username.
     */
    public void setUsername(@Nullable final String username)
    {
        setUsername(username, getQueryJCommand());
    }

    /**
     * Specifies the username.
     * @param username the new username.
     * @param delegee the delegee.
     */
    protected void setUsername(
        @Nullable final String username, @NotNull final QueryJCommand delegee)
    {
        if (username != null)
        {
            delegee.setSetting(JDBC_USERNAME, username);
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
        return getUsername(getQueryJCommand());
    }

    /**
     * Retrieves the username.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getUsername(@NotNull final QueryJCommand delegee)
    {
        return delegee.getSetting(JDBC_USERNAME);
    }

    /**
     * Specifies the password.
     * @param password the new password.
     */
    public void setPassword(@Nullable final String password)
    {
        setPassword(password, getQueryJCommand());
    }

    /**
     * Specifies the password.
     * @param password the new password.
     * @param delegee the delegee.
     */
    protected void setPassword(
        @Nullable final String password, @NotNull final QueryJCommand delegee)
    {
        if (password != null)
        {
            delegee.setSetting(JDBC_PASSWORD, password);
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
        return getPassword(getQueryJCommand());
    }

    /**
     * Retrieves the password.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getPassword(@NotNull final QueryJCommand delegee)
    {
        return delegee.getSetting(JDBC_PASSWORD);
    }

    /**
     * Specifies the catalog.
     * @param catalog the new catalog.
     */
    public void setCatalog(@Nullable final String catalog)
    {
        setCatalog(catalog, getQueryJCommand());
    }

    /**
     * Specifies the catalog.
     * @param catalog the new catalog.
     * @param delegee the delegee.
     */
    protected void setCatalog(
        @Nullable final String catalog, @NotNull final QueryJCommand delegee)
    {
        if (catalog != null)
        {
            delegee.setSetting(JDBC_CATALOG, catalog);
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
        return getCatalog(getQueryJCommand());
    }

    /**
     * Retrieves the catalog.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getCatalog(@NotNull final QueryJCommand delegee)
    {
        return delegee.getSetting(JDBC_CATALOG);
    }

    /**
     * Specifies the schema.
     * @param schema the new schema.
     */
    public void setSchema(@Nullable final String schema)
    {
        setSchema(schema, getQueryJCommand());
    }

    /**
     * Specifies the schema.
     * @param schema the new schema.
     * @param delegee the delegee.
     */
    protected void setSchema(
        @Nullable final String schema, @NotNull final QueryJCommand delegee)
    {
        if (schema != null)
        {
            delegee.setSetting(JDBC_SCHEMA, schema);
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
        return getSchema(getQueryJCommand());
    }

    /**
     * Retrieves the schema.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getSchema(@NotNull final QueryJCommand delegee)
    {
        return delegee.getSetting(JDBC_SCHEMA);
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     */
    public void setRepository(@Nullable final String repository)
    {
        setRepository(repository, getQueryJCommand());
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     * @param delegee the delegee.
     */
    protected void setRepository(
        @Nullable final String repository, @NotNull final QueryJCommand delegee)
    {
        if (repository != null)
        {
            delegee.setSetting(REPOSITORY, repository);
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
        return getRepository(getQueryJCommand());
    }

    /**
     * Retrieves the repository.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getRepository(@NotNull final QueryJCommand delegee)
    {
        return delegee.getSetting(REPOSITORY);
    }

    /**
     * Specifies the package.
     * @param packageName the new package.
     */
    public void setPackage(@Nullable final String packageName)
    {
        setPackage(packageName, getQueryJCommand());
    }

    /**
     * Specifies the package.
     * @param packageName the new package.
     * @param delegee the delegee.
     */
    protected void setPackage(
        @Nullable final String packageName, @NotNull final QueryJCommand delegee)
    {
        if (packageName != null)
        {
            delegee.setSetting(PACKAGE, packageName);
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
        return getPackage(getQueryJCommand());
    }

    /**
     * Retrieves the package.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getPackage(@NotNull final QueryJCommand delegee)
    {
        return delegee.getSetting(PACKAGE);
    }

    /**
     * Specifies the output dir.
     * @param outputdir the new output dir.
     */
    public void setOutputdir(@Nullable final File outputdir)
    {
        setOutputdir(outputdir, getQueryJCommand());
    }

    /**
     * Specifies the output dir.
     * @param outputdir the new output dir.
     * @param delegee the delegee.
     */
    protected void setOutputdir(
        @Nullable final File outputdir, @NotNull final QueryJCommand delegee)
    {
        if (outputdir != null)
        {
            new QueryJCommandWrapper<File>(delegee).setSetting(OUTPUT_FOLDER, outputdir);
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
        return getOutputdir(getQueryJCommand());
    }

    /**
     * Retrieves the output dir.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected File getOutputdir(@NotNull final QueryJCommand delegee)
    {
        return delegee.getFileSetting(OUTPUT_FOLDER);
    }

    /**
     * Specifies the header file.
     * @param headerfile the new header file.
     */
    public void setHeaderfile(@Nullable final File headerfile)
    {
        setHeaderfile(headerfile, getQueryJCommand());
    }

    /**
     * Specifies the header file.
     * @param headerfile the new header file.
     * @param delegee the delegee.
     */
    protected void setHeaderfile(
        @Nullable final File headerfile, @NotNull final QueryJCommand delegee)
    {
        if (headerfile != null)
        {
            new QueryJCommandWrapper<File>(delegee).setSetting(HEADER_FILE, headerfile);
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
        return getHeaderfile(getQueryJCommand());
    }

    /**
     * Retrieves the header file.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected File getHeaderfile(@NotNull final QueryJCommand delegee)
    {
        return delegee.getFileSetting(HEADER_FILE);
    }

    /**
     * Specifies the JNDI location for the data sources.
     * @param jndiLocation the JNDI location.
     */
    public void setJndiDataSource(@Nullable final String jndiLocation)
    {
        setJndiDataSource(jndiLocation, getQueryJCommand());
    }

    /**
     * Specifies the JNDI location for the data sources.
     * @param jndiLocation the JNDI location.
     * @param delegee the delegee.
     */
    protected void setJndiDataSource(
        @Nullable final String jndiLocation, @NotNull final QueryJCommand delegee)
    {
        if (jndiLocation != null)
        {
            delegee.setSetting(JNDI_DATASOURCE, jndiLocation);
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
        return getJndiDataSource(getQueryJCommand());
    }

    /**
     * Retrieves the JNDI location for the data sources.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getJndiDataSource(@NotNull final QueryJCommand delegee)
    {
        return delegee.getSetting(JNDI_DATASOURCE);
    }

    /**
     * Specifies whether to disable custom sql validation.
     * @param disable such setting.
     */
    @SuppressWarnings("unused")
    public void setDisableCustomSqlValidation(@Nullable final String disable)
    {
        setDisableCustomSqlValidation(disable, getQueryJCommand());
    }

    /**
     * Specifies whether to disable custom sql validation.
     * @param disable such setting.
     * @param delegee the delegee.
     */
    protected void setDisableCustomSqlValidation(
        @Nullable final String disable, @NotNull final QueryJCommand delegee)
    {
        if (disable != null)
        {
            new QueryJCommandWrapper<Boolean>(delegee)
                .setSetting(DISABLE_CUSTOM_SQL_VALIDATION, Boolean.valueOf(disable));
        }
    }

    /**
     * Retrieves whether to disable custom sql validation.
     * @return such setting.
     */
    @SuppressWarnings("unused")
    public boolean getDisableCustomSqlValidation()
    {
        return getDisableCustomSqlValidation(getQueryJCommand());
    }

    /**
     * Retrieves whether to disable custom sql validation.
     * @param delegee the delegee.
     * @return such setting.
     */
    protected boolean getDisableCustomSqlValidation(@NotNull final QueryJCommand delegee)
    {
        return delegee.getBooleanSetting(DISABLE_CUSTOM_SQL_VALIDATION, false);
    }

    /**
     * Specifies the sql.xml file.
     * @param file the new file.
     */
    public void setSqlXmlFile(@Nullable final File file)
    {
        setSqlXmlFile(file, getQueryJCommand());
    }

    /**
     * Specifies the sql.xml file.
     * @param file the new file.
     * @param delegee the delegee.
     */
    protected void setSqlXmlFile(@Nullable final File file, @NotNull final QueryJCommand delegee)
    {
        if (file != null)
        {
            new QueryJCommandWrapper<File>(delegee).setSetting(SQL_XML_FILE, file);
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
        return getSqlXmlFile(getQueryJCommand());
    }

    /**
     * Retrieves the sql.xml file.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected File getSqlXmlFile(@NotNull final QueryJCommand delegee)
    {
        return delegee.getFileSetting(SQL_XML_FILE);
    }

    /**
     * Specifies the grammar name.
     * @param folder the new grammar name.
     */
    public void setGrammarFolder(@Nullable final File folder)
    {
        setGrammarFolder(folder, getQueryJCommand());
    }

    /**
     * Specifies the grammar folder.
     * @param folder the new grammar folder.
     * @param delegee the delegee.
     */
    protected void setGrammarFolder(
        @Nullable final File folder, @NotNull final QueryJCommand delegee)
    {
        if (folder != null)
        {
            new QueryJCommandWrapper<File>(delegee).setSetting(GRAMMAR_FOLDER, folder);
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
        return getGrammarFolder(getQueryJCommand());
    }

    /**
     * Retrieves the grammar folder.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected File getGrammarFolder(@NotNull final QueryJCommand delegee)
    {
        return delegee.getFileSetting(GRAMMAR_FOLDER);
    }

    /**
     * Specifies the grammar name.
     * @param grammarName the new grammar name.
     */
    public void setGrammarName(@Nullable final String grammarName)
    {
        setGrammarName(grammarName, getQueryJCommand());
    }

    /**
     * Specifies the grammar name.
     * @param grammarName the new grammar name.
     * @param delegee the delegee.
     */
    protected void setGrammarName(
        @Nullable final String grammarName, @NotNull final QueryJCommand delegee)
    {
        if (grammarName != null)
        {
            delegee.setSetting(GRAMMAR_NAME, grammarName);
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
        return getGrammarName(getQueryJCommand());
    }

    /**
     * Retrieves the grammar bundle.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getGrammarName(@NotNull final QueryJCommand delegee)
    {
        return delegee.getSetting(GRAMMAR_NAME);
    }

    /**
     * Specifies the grammar suffix.
     * @param grammarSuffix the new grammar suffix.
     */
    public void setGrammarSuffix(@Nullable final String grammarSuffix)
    {
        setGrammarSuffix(grammarSuffix, getQueryJCommand());
    }

    /**
     * Specifies the grammar suffix.
     * @param grammarSuffix the new grammar suffix.
     * @param delegee the delegee.
     */
    protected void setGrammarSuffix(
        @Nullable final String grammarSuffix, @NotNull final QueryJCommand delegee)
    {
        if (grammarSuffix != null)
        {
            delegee.setSetting(GRAMMAR_SUFFIX, grammarSuffix);
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
        return getGrammarSuffix(getQueryJCommand());
    }

    /**
     * Retrieves the grammar suffix.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getGrammarSuffix(@NotNull final QueryJCommand delegee)
    {
        return delegee.getSetting(GRAMMAR_SUFFIX);
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
        @Nullable Path t_Classpath = getClasspath();

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
        @Nullable final Path t_Path = createClasspath();

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
        setEncoding(encoding, getQueryJCommand());
    }

    /**
     * Specifies the encoding.
     * @param encoding the new encoding.
     * @param delegee the delegee.
     */
    protected void setEncoding(
        @Nullable final String encoding, @NotNull final QueryJCommand delegee)
    {
        if (encoding != null)
        {
            delegee.setSetting(ENCODING, encoding);
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
        return getEncoding(getQueryJCommand());
    }

    /**
     * Retrieves the encoding.
     * @param delegee the delegee.
     * @return such information.
     */
    @Nullable
    protected String getEncoding(@NotNull final QueryJCommand delegee)
    {
        return delegee.getSetting(ENCODING);
    }

    /**
     * Requests the chained logic to be performed.
     */
    @Override
    public void execute()
        throws  BuildException
    {
        execute(getQueryJCommand());
    }

    /**
     * Requests the chained logic to be performed.
     * @param command the command.
     */
    protected void execute(@NotNull final QueryJCommand command)
        throws  BuildException
    {
        try
        {
            new QueryJChain<>().process(command);
        }
        catch  (@NotNull final QueryJBuildException buildException)
        {
            throw
                new BuildException(buildException.getMessage(), buildException);
        }
    }

    /**
     * Specifies the "externally-managed-fields" nested element.
     * @param externallyManagedFields the externally-managed-fields xml
     * element.
     */
    public void setExternallyManagedFields(
        @NotNull final AntExternallyManagedFieldsElement externallyManagedFields)
    {
        setExternallyManagedFields(externallyManagedFields, getQueryJCommand());
    }

    /**
     * Specifies the "externally-managed-fields" nested element.
     * @param externallyManagedFields the externally-managed-fields xml
     * element.
     * @param command the command.
     */
    protected void setExternallyManagedFields(
        @NotNull final AntExternallyManagedFieldsElement externallyManagedFields,
        @NotNull final QueryJCommand command)
    {
        new QueryJCommandWrapper<AntExternallyManagedFieldsElement>(command)
            .setSetting(EXTERNALLY_MANAGED_FIELDS, externallyManagedFields);
    }

    /**
     * Retrieves the "externally-managed-fields" nested element.
     * @return such information.
     */
    @Nullable
    @SuppressWarnings("unused")
    public AntExternallyManagedFieldsElement getExternallyManagedFields()
    {
        return getExternallyManagedFields(getQueryJCommand());
    }

    /**
     * Retrieves the "externally-managed-fields" nested element.
     * @param command the command.
     * @return such information.
     */
    @Nullable
    @SuppressWarnings("unused")
    protected AntExternallyManagedFieldsElement getExternallyManagedFields(@NotNull final QueryJCommand command)
    {
        return
            new QueryJCommandWrapper<AntExternallyManagedFieldsElement>(command).getSetting(EXTERNALLY_MANAGED_FIELDS);
    }

    /**
     * Specifies the thread count.
     * @param threadCount such value.
     */
    public void setThreadCount(final int threadCount)
    {
        setThreadCount(threadCount, getQueryJCommand());
    }

    /**
     * Specifies the thread count.
     * @param threadCount such value.
     * @param delegee the command.
     */
    protected void setThreadCount(final int threadCount, @NotNull final QueryJCommand delegee)
    {
        new QueryJCommandWrapper<Integer>(delegee).setSetting(THREAD_COUNT, threadCount);
    }

    /**
     * Retrieves the thread count.
     * @return such value.
     */
    @SuppressWarnings("unused")
    public int getThreadCount()
    {
        return getThreadCount(getQueryJCommand());
    }

    /**
     * Retrieves the thread count.
     * @param delegee the command.
     * @return such value.
     */
    @SuppressWarnings("unused")
    protected int getThreadCount(@NotNull final QueryJCommand delegee)
    {
        return delegee.getIntSetting(THREAD_COUNT, Runtime.getRuntime().availableProcessors());
    }

    /**
     * Specifies whether to include timestamps in the generated code.
     * @param disableGenerateTimestamps such choice.
     */
    public void setDisableGenerationTimestamps(final boolean disableGenerateTimestamps)
    {
        setDisableGenerationTimestamps(disableGenerateTimestamps, getQueryJCommand());
    }

    /**
     * Specifies whether to include timestamps in the generated code.
     * @param disableGenerateTimestamps such choice.
     * @param delegee the command.
     */
    protected void setDisableGenerationTimestamps(
        final boolean disableGenerateTimestamps, @NotNull final QueryJCommand delegee)
    {
        new QueryJCommandWrapper<Boolean>(delegee).setSetting(DISABLE_TIMESTAMPS, disableGenerateTimestamps);
    }

    /**
     * Retrieves whether to include timestamps in the generated code.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean getDisableGenerationTimestamps()
    {
        return getDisableGenerationTimestamps(getQueryJCommand());
    }

    /**
     * Retrieves whether to include timestamps in the generated code.
     * @param delegee the command.
     * @return such information.
     */
    protected final boolean getDisableGenerationTimestamps(@NotNull final QueryJCommand delegee)
    {
        return delegee.getBooleanSetting(DISABLE_TIMESTAMPS, false);
    }

    /**
     * Specifies whether to include NotNull annotations in the generated code.
     * @param disableNotNullAnnotations such choice.
     */
    public void setDisableNotNullAnnotations(final boolean disableNotNullAnnotations)
    {
        setDisableNotNullAnnotations(disableNotNullAnnotations, getQueryJCommand());
    }

    /**
     * Specifies whether to include NotNull annotations in the generated code.
     * @param disableNotNullAnnotations such choice.
     * @param delegee the command.
     */
    protected void setDisableNotNullAnnotations(
        final boolean disableNotNullAnnotations, @NotNull final QueryJCommand delegee)
    {
        new QueryJCommandWrapper<Boolean>(delegee).setSetting(DISABLE_NOTNULL_ANNOTATIONS, disableNotNullAnnotations);
    }

    /**
     * Retrieves whether to include NotNull annotations in the generated code.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean getDisableNotNullAnnotations()
    {
        return getDisableNotNullAnnotations(getQueryJCommand());
    }

    /**
     * Retrieves whether to include NotNull annotations in the generated code.
     * @param delegee the command.
     * @return such information.
     */
    protected final boolean getDisableNotNullAnnotations(@NotNull final QueryJCommand delegee)
    {
        return delegee.getBooleanSetting(DISABLE_NOTNULL_ANNOTATIONS, false);
    }

    /**
     * Specifies whether to include Checkthread.org annotations in the generated code.
     * @param disableCheckthreadAnnotations such choice.
     */
    public void setDisableCheckthreadAnnotations(final boolean disableCheckthreadAnnotations)
    {
        setDisableNotNullAnnotations(disableCheckthreadAnnotations, getQueryJCommand());
    }

    /**
     * Specifies whether to include Checkthread.org annotations in the generated code.
     * @param disableCheckthreadAnnotations such choice.
     * @param delegee the command.
     */
    @SuppressWarnings("unused")
    protected void setDisableCheckthreadAnnotations(
        final boolean disableCheckthreadAnnotations, @NotNull final QueryJCommand delegee)
    {
        new QueryJCommandWrapper<Boolean>(delegee)
            .setSetting(DISABLE_CHECKTHREAD_ANNOTATIONS, disableCheckthreadAnnotations);
    }

    /**
     * Retrieves whether to include Checkthread.org annotations in the generated code.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean getDisableCheckthreadAnnotations()
    {
        return getDisableNotNullAnnotations(getQueryJCommand());
    }

    /**
     * Retrieves whether to include Checkthread.org annotations in the generated code.
     * @param delegee the command.
     * @return such information.
     */
    @SuppressWarnings("unused")
    protected final boolean getDisableCheckthreadAnnotations(@NotNull final QueryJCommand delegee)
    {
        return delegee.getBooleanSetting(DISABLE_CHECKTHREAD_ANNOTATIONS, false);
    }

    /**
     * Specifies the QueryJ version.
     * @param version the version.
     */
    public void setVersion(@NotNull final String version)
    {
        setVersion(version, getQueryJCommand());
    }

    /**
     * Specifies the QueryJ version.
     * @param version the version.
     * @param command the command.
     */
    protected void setVersion(@NotNull final String version, @NotNull final QueryJCommand command)
    {
        new QueryJCommandWrapper<String>(command).setSetting(VERSION, version);
    }

    /**
     * Retrieves the version.
     * @return such information.
     */
    @Nullable
    public String getVersion()
    {
        return getVersion(getQueryJCommand());
    }

    /**
     * Retrieves the version.
     * @param command the command.
     * @return such information.
     */
    @Nullable
    protected String getVersion(@NotNull final QueryJCommand command)
    {
        return new QueryJCommandWrapper<String>(command).getSetting(VERSION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDynamicAttribute(@NotNull final String name, @NotNull final String value)
    {
        throw
            new BuildException(
                  NO_DYNAMIC_ATTRIBUTES_ARE_SUPPORTED
                + name + "=" + value + ")");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public Object createDynamicElement(@NotNull final String name)
    {
        @Nullable final Object result;

        if  (ParameterValidationHandler.TABLES.equals(name))
        {
            @Nullable final AntTablesElement t_ateResult;

            t_ateResult = new AntTablesElement();

            setTables(t_ateResult);

            result = t_ateResult;
        }
        else if  (EXTERNALLY_MANAGED_FIELDS.equals(name))
        {
            @NotNull final AntExternallyManagedFieldsElement t_aemfeResult =
                new AntExternallyManagedFieldsElement();

            setExternallyManagedFields(t_aemfeResult);

            result = t_aemfeResult;
        }
        else
        {
            throw new BuildException(name + ELEMENTS_ARE_NOT_SUPPORTED);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"classpath\": \"" + m__Classpath + '"'
            + ", \"command\": " + m__QueryJCommand
            + ", \"tables\": " + m__Tables
            + ", \"class\": \"QueryJTask\""
            + ", \"package\": \"org.acmsl.queryj.tools.ant\" }";
    }
}
