/*
                 QueryJ Template Packaging Maven Plugin

    Copyright (C) 2013-today  Jose San Leandro Armendariz
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
 * Filename: TemplatePackagingMojo.java
 *
 * Author: Jose San Leandro Armendariz.
 *
 * Description: Generates required QueryJ boilerplate from user-provided
 *              templates, via Maven.
 */
package org.acmsl.queryj.templates.packaging.maven;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.TemplatePackagingChain;
import org.acmsl.queryj.templates.packaging.TemplatePackagingSettings;

/*
 * Importing some QueryJ Core classes.
 */
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.SerializablePropertiesConfiguration;
import org.acmsl.queryj.tools.maven.QueryJMojo;

/*
 * Importing some Maven classes.
 */
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.io.InputStream;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Generates required QueryJ boilerplate from user-provided templates, via Maven.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 3.0
 * Created: 2013/06/16
 */
@SuppressWarnings("unused")
@ThreadSafe
@Mojo( name = "queryj-package-templates", defaultPhase = LifecyclePhase.GENERATE_SOURCES, threadSafe = true )
public class TemplatePackagingMojo
    extends AbstractMojo
    implements TemplatePackagingSettings
{
    /**
     * The location of pom.properties within the jar file.
     */
    protected static final String POM_PROPERTIES_LOCATION =
        "META-INF/maven/org.acmsl.queryj/queryj-template-packaging-maven-plugin/pom.properties";

    /**
     * The prefix for environment variables.
     */
    public static final String ENV_PREFIX = "queryj-templates.";

    /**
     * Additional source directories.
     */
    @Parameter(name = SOURCES, property = ENV_PREFIX + SOURCES, required = true)
    private File[] m__aSources;

    /**
     * The output folder.
     */
    @Parameter(name = OUTPUT_DIR, property = ENV_PREFIX + OUTPUT_DIR, defaultValue = "${project.build.directory}/generated-sources")
    private File m__OutputDir;

    /**
     * The output folder for tests.
     */
    @Parameter(name = OUTPUT_DIR_FOR_TESTS, property = ENV_PREFIX + OUTPUT_DIR_FOR_TESTS, defaultValue = "${project.build.directory}/generated-test-sources")
    private File m__OutputDirForTests;

    /**
     * The JDBC driver.
     */
    @Parameter(name = JDBC_DRIVER, property = ENV_PREFIX + JDBC_DRIVER)
    private String m__strJdbcDriver;

    /**
     * The JDBC url.
     */
    @Parameter(name = JDBC_URL, property = ENV_PREFIX + JDBC_URL)
    private String m__strJdbcUrl;

    /**
     * The JDBC username.
     */
    @Parameter(name = JDBC_USERNAME, property = ENV_PREFIX + JDBC_USERNAME)
    private String m__strJdbcUserName;

    /**
     * The JDBC password.
     */
    @Parameter(name = JDBC_PASSWORD, property = ENV_PREFIX + JDBC_PASSWORD)
    private String m__strJdbcPassword;

    /**
     * The <code>QueryJChain</code> delegee.
     */
    private QueryJCommand m__QueryJCommand;

    /**
     * Creates a {@code TemplatePackagingMojo} instance.
     */
    public TemplatePackagingMojo()
    {
        immutableSetQueryJCommand(new ConfigurationQueryJCommandImpl(new SerializablePropertiesConfiguration()));
    }

    /**
     * Specifies the {@link org.acmsl.queryj.QueryJCommand} to delegate.
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
     * Specifies the sources.
     * @param sources the sources.
     */
    protected final void immutableSetSources(@NotNull final File[] sources)
    {
        this.m__aSources = sources;
    }

    /**
     * Specifies the sources.
     * @param sources the sources.
     */
    @SuppressWarnings("unused")
    public void setSources(@NotNull final File[] sources)
    {
        immutableSetSources(sources);
    }

    /**
     * Retrieves the sources.
     * @return such folders.
     */
    @Nullable
    protected final File[] immutableGetSources()
    {
        return this.m__aSources;
    }

    /**
     * Retrieves the sources.
     * @return such folders.
     */
    @NotNull
    public File[] getSources()
    {
        @NotNull final File[] result;

        @Nullable final File[] aux = immutableGetSources();

        if (   (aux == null)
            || (aux.length == 0))
        {
            result = new File[0];
        }
        else
        {
            result = new File[aux.length];
            System.arraycopy(aux, 0, result, 0, aux.length);
        }

        return result;
    }

    /**
     * Specifies the output dir.
     * @param dir such folder.
     */
    protected final void immutableSetOutputDir(@NotNull final File dir)
    {
        this.m__OutputDir = dir;
    }

    /**
     * Specifies the output dir.
     * @param dir such folder.
     */
    @SuppressWarnings("unused")
    public void setOutputDir(@NotNull final File dir)
    {
        immutableSetOutputDir(dir);
    }

    /**
     * Retrieves the output dir.
     * @return such folder.
     */
    @NotNull
    public File getOutputDir()
    {
        return this.m__OutputDir;
    }

    /**
     * Specifies the output dir for tests.
     * @param dir such folder.
     */
    protected final void immutableSetOutputDirForTests(@NotNull final File dir)
    {
        this.m__OutputDirForTests = dir;
    }

    /**
     * Specifies the output dir for tests.
     * @param dir such folder.
     */
    @SuppressWarnings("unused")
    public void setOutputDirForTests(@NotNull final File dir)
    {
        immutableSetOutputDirForTests(dir);
    }

    /**
     * Retrieves the output dir for tests.
     * @return such folder.
     */
    @NotNull
    public File getOutputDirForTests()
    {
        return this.m__OutputDirForTests;
    }

    /**
     * Specifies the JDBC driver.
     * @param jdbcDriver the JDBC driver.
     */
    protected final void immutableSetJdbcDriver(@NotNull final String jdbcDriver)
    {
        this.m__strJdbcDriver = jdbcDriver;
    }

    /**
     * Specifies the JDBC driver.
     * @param jdbcDriver the JDBC driver.
     */
    @SuppressWarnings("unused")
    public void setJdbcDriver(@NotNull final String jdbcDriver)
    {
        immutableSetJdbcDriver(jdbcDriver);
    }

    /**
     * Retrieves the JDBC driver.
     * @return the JDBC driver.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getJdbcDriver()
    {
        return this.m__strJdbcDriver;
    }

    /**
     * Specifies the JDBC url.
     * @param jdbcUrl the JDBC url.
     */
    protected final void immutableSetJdbcUrl(@NotNull final String jdbcUrl)
    {
        this.m__strJdbcUrl = jdbcUrl;
    }

    /**
     * Specifies the JDBC url.
     * @param jdbcUrl the JDBC url.
     */
    @SuppressWarnings("unused")
    public void setJdbcUrl(@NotNull final String jdbcUrl)
    {
        immutableSetJdbcUrl(jdbcUrl);
    }

    /**
     * Retrieves the JDBC url.
     * @return the JDBC url.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getJdbcUrl()
    {
        return this.m__strJdbcUrl;
    }

    /**
     * Specifies the JDBC user name.
     * @param jdbcUserName the JDBC user name.
     */
    protected final void immutableSetJdbcUserName(@NotNull final String jdbcUserName)
    {
        this.m__strJdbcUserName = jdbcUserName;
    }

    /**
     * Specifies the JDBC user name.
     * @param jdbcUserName the JDBC user name.
     */
    @SuppressWarnings("unused")
    public void setJdbcUserName(@NotNull final String jdbcUserName)
    {
        immutableSetJdbcUserName(jdbcUserName);
    }

    /**
     * Retrieves the JDBC user name.
     * @return the JDBC user name.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getJdbcUserName()
    {
        return this.m__strJdbcUserName;
    }

    /**
     * Specifies the JDBC password.
     * @param jdbcPassword the JDBC password.
     */
    protected final void immutableSetJdbcPassword(@NotNull final String jdbcPassword)
    {
        this.m__strJdbcPassword = jdbcPassword;
    }

    /**
     * Specifies the JDBC url.
     * @param jdbcPassword the JDBC password.
     */
    @SuppressWarnings("unused")
    public void setJdbcPassword(@NotNull final String jdbcPassword)
    {
        immutableSetJdbcPassword(jdbcPassword);
    }

    /**
     * Retrieves the JDBC url.
     * @return the JDBC url.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getJdbcPassword()
    {
        return this.m__strJdbcPassword;
    }

    /**
     * Executes Template Packaging via Maven2.
     */
    @Override
    public void execute()
        throws MojoExecutionException
    {
        execute(getLog());
    }

    /**
     * Executes Template Packaging via Maven2.
     * @param log the Maven log.
     * @throws MojoExecutionException if the process fails.
     */
    protected void execute(@NotNull final Log log)
        throws MojoExecutionException
    {
        execute(getQueryJCommand(), log, retrieveVersion(retrievePomProperties(log)));
    }

    /**
     * Retrieves the pom.properties bundled within the Template Packaging jar.
     * @param log the Maven log.
     * @return such information.
     */
    @Nullable
    protected Properties retrievePomProperties(@NotNull final Log log)
    {  
        @Nullable Properties result = null;

        try
        {  
            @NotNull final InputStream pomProperties =
                getClass().getClassLoader().getResourceAsStream(POM_PROPERTIES_LOCATION);

            result = new Properties();

            result.load(pomProperties);
        }
        catch (@NotNull final Throwable throwable)
        {  
            log.warn(
                QueryJMojo.CANNOT_READ_MY_OWN_POM + POM_PROPERTIES_LOCATION,
                throwable);
        }

        return result;
    }

    /**
     * Retrieves the version of Template Packaging currently running.
     * @param properties the pom.properties information.
     * @return the version entry.
     */
    protected String retrieveVersion(@Nullable final Properties properties)
    {
        String result = QueryJMojo.UNKNOWN_LITERAL;

        if (   (properties != null)
            && (properties.containsKey(QueryJMojo.VERSION_LITERAL)))
        {
            result = properties.getProperty(QueryJMojo.VERSION_LITERAL);
        }

        return result;
    }

    /**
     * Requests the chained logic to be performed.
     * @param command the command.
     * @param log the log.
     * @param version the version.
     * @throws MojoExecutionException if the process fails.
     */
    protected void execute(
        @NotNull final QueryJCommand command, @NotNull final Log log, final String version)
        throws MojoExecutionException
    {
        log.info("Running QueryJ Template Packaging " + version);

        try
        {
            populateCommand(command, getSources());
            populateCommand(command, getOutputDir());
            populateCommandForTests(command, getOutputDirForTests());
            populateCommand(command, getJdbcDriver(), getJdbcUrl(), getJdbcUserName(), getJdbcPassword());
            populateCommand(command, version);

            new TemplatePackagingChain<>().process(command);
        }
        catch  (@NotNull final QueryJBuildException buildException)
        {
            throw new MojoExecutionException(buildException.getMessage(), buildException);
        }
    }

    /**
     * Populates the source folders in the Mojo to be available in the command.
     * @param command the command.
     * @param sources the source folders.
     */
    protected void populateCommand(@NotNull final QueryJCommand command, @NotNull final File[] sources)
    {
        new QueryJCommandWrapper<List<File>>(command).setSetting(SOURCES, Arrays.asList(sources));
    }

    /**
     * Populates the output dir in the Mojo to be available in the command.
     * @param command the command.
     * @param outputDir the output dir.
     */
    protected void populateCommand(@NotNull final QueryJCommand command, @NotNull final File outputDir)
    {
        new QueryJCommandWrapper<File>(command).setSetting(OUTPUT_DIR, outputDir);
    }

    /**
     * Populates the output dir for tests to be available in the command.
     * @param command the command.
     * @param outputDir the output dir.
     */
    protected void populateCommandForTests(@NotNull final QueryJCommand command, @NotNull final File outputDir)
    {
        new QueryJCommandWrapper<File>(command).setSetting(OUTPUT_DIR_FOR_TESTS, outputDir);
    }

    /**
     * Populates the JDBC information in the Mojo to be available in the command.
     * @param command the command.
     * @param jdbcDriver the JDBC driver.
     * @param jdbcUrl the JDBC url.
     * @param jdbcUserName the JDBC user name.
     * @param jdbcPassword the JDBC password.
     */
    protected void populateCommand(
        @NotNull final QueryJCommand command,
        @NotNull final String jdbcDriver,
        @NotNull final String jdbcUrl,
        @NotNull final String jdbcUserName,
        @NotNull final String jdbcPassword)
    {
        new QueryJCommandWrapper<String>(command).setSetting(JDBC_DRIVER, jdbcDriver);
        new QueryJCommandWrapper<String>(command).setSetting(JDBC_URL, jdbcUrl);
        new QueryJCommandWrapper<String>(command).setSetting(JDBC_USERNAME, jdbcUserName);
        new QueryJCommandWrapper<String>(command).setSetting(JDBC_PASSWORD, jdbcPassword);
    }

    /**
     * Populates the version information in the Mojo to be available in the command.
     * @param command the command.
     * @param version the version.
     */
    protected void populateCommand(
        @NotNull final QueryJCommand command, @NotNull final String version)
    {
        new QueryJCommandWrapper<String>(command).setSetting(QueryJSettings.VERSION, version);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"queryJCommand\": \"" + this.m__QueryJCommand.hashCode() + '"'
            + ", \"sources\": \"" + Arrays.toString(this.m__aSources) + '"'
            + ", \"outputDir\": \"" + this.m__OutputDir.getAbsolutePath() + '"'
            + ", \"outputDirForTests\": \"" + this.m__OutputDirForTests.getAbsolutePath() + '"'
            + ", \"jdbcDriver\": \"" + this.m__strJdbcDriver + '"'
            + ", \"jdbcUrl\": \"" + this.m__strJdbcUrl + '"'
            + ", \"jdbcUserName\": \"" + this.m__strJdbcUserName + '"'
            + ", \"jdbcPassword\": \"XXXX-" + this.m__strJdbcPassword.length() + '"'
            + ", \"class\": \"TemplatePackagingMojo\""
            + ", \"package\": \"org.acmsl.queryj.templates.packaging\" }";
    }
}
