/*
                        QueryJ

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
 *              templates.
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing some QueryJ-Core classes.
 */
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;
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
 * Importing some Apache Commons Configuration classes.
 */
import org.apache.commons.configuration.PropertiesConfiguration;

/*
 * Importing some Jetbrains annotations.
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
 * Generates required QueryJ boilerplate from user-provided templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @goal package-templates
 * @execute phase="generate-sources"
 * @threadSafe
 * @since 2013/06/16
 */
@SuppressWarnings("unused")
@ThreadSafe
@Mojo( name = "package-templates", defaultPhase = LifecyclePhase.GENERATE_SOURCES, threadSafe = true )
public class TemplatePackagingMojo
    extends AbstractMojo
    implements TemplatePackagingSettings
{
    /**
     * The location of pom.properties within the jar file.
     */
    protected static final String POM_PROPERTIES_LOCATION =
        "META-INF/maven/org.acmsl/queryj-template-packaging-maven-plugin/pom.properties";

    /**
     * Additional source directories.
     * @parameter property="sources" @required
     */
    @Parameter( required = true, property = SOURCES)
    private File[] m__aSources;

    /**
     * The output folder.
     * @parameter property="outputDir" expression="${project.build.directory}/generated-sources" default-value="${project.build.directory}/generated-sources"
     */
    @Parameter( property = OUTPUT_DIR, defaultValue = "${project.build.directory}/generated-sources")
    private File m__OutputDir;

    /**
     * The <code>QueryJChain</code> delegee.
     */
    private QueryJCommand m__QueryJCommand;

    /**
     * Creates a {@link TemplatePackagingMojo} instance.
     */
    public TemplatePackagingMojo()
    {
        immutableSetQueryJCommand(new ConfigurationQueryJCommandImpl(new PropertiesConfiguration()));
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
     * Executes Template Packaging via Maven2.
     * @throws MojoExecutionException if something goes wrong.
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
     * @throws MojoExecutionException if something goes wrong.
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
     * @throws MojoExecutionException whenever the execution fails.
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

            new TemplatePackagingChain<QueryJCommandHandler<QueryJCommand>>().process(command);
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

    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + TemplatePackagingMojo.class.getName() + "\""
            + ", \"queryJCommand\": \"" + this.m__QueryJCommand + "\""
            + ", \"sources\": \"" + Arrays.toString(this.m__aSources) + "\""
            + ", \"outputDir\": \"" + this.m__OutputDir.getAbsolutePath() + "\""
            + " }";
    }
}
