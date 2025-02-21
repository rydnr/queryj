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
 * Filename: QueryJMojo.java
 *
 * Author: Jose San Leandro Armendariz.
 *
 * Description: Executes QueryJ's QueryJMojo.
 */
package org.acmsl.queryj.tools.maven;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.tools.ant.QueryJTask;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;

/*
 * Importing some ACM-SL Java Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Maven classes.
 */
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
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
public abstract class QueryJMojo
    extends AbstractMojo
    implements QueryJSettings
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
     * String literal: "version"
     */
    public static final String VERSION_LITERAL = QueryJSettings.VERSION;

    /**
     * The output directory.
     */
    @Parameter (name = Literals.OUTPUT_DIR, property = OUTPUT_DIR, required = false, defaultValue = "${project.build.dir}")
    private File m__OutputDir;

    /**
     * The header file.
     */
    @Parameter (name = Literals.HEADER_FILE, property = HEADER_FILE, required = false, defaultValue = "header.txt")
    private File m__HeaderFile;

    /**
     * The file encoding.
     */
    @Parameter (name = Literals.ENCODING, property = ENCODING, required = false, defaultValue = "${project.build.sourceEncoding}")
    private String m__strEncoding;

    /**
     * Whether to generate file timestamps.
     */
    @Parameter (name = Literals.DISABLE_TIMESTAMPS, property = DISABLE_TIMESTAMPS, required = false, defaultValue = "false")
    private Boolean m__bDisableGenerationTimestamps;

    /**
     * The current build session instance. This is used for toolchain manager API calls.
     * @readonly
     */
    @Parameter (defaultValue = "${session}", required = true, readonly = true)
    private MavenSession session;

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
    public File getOutputDir()
    {
        final File result;

        final String aux = System.getProperty(OUTPUT_DIR);

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
    public File getHeaderFile()
    {
        final File result;

        final String aux = System.getProperty(HEADER_FILE);

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
        String result = System.getProperty(ENCODING);

        if (result == null)
        {
            result = immutableGetEncoding();
        }

        return result;
    }

    /**
     * Specifies whether to use generation timestamps.
     * @param flag the choice.
     */
    protected final void immutableSetDisableTimestamps(@NotNull final Boolean flag)
    {
        m__bDisableGenerationTimestamps = flag;
    }

    /**
     * Specifies whether to use generation timestamps.
     * @param flag the choice.
     */
    public void setDisableTimestamps(@NotNull final Boolean flag)
    {
        immutableSetDisableTimestamps(flag);
    }

    /**
     * Retrieves whether to use generation timestamps.
     * @return such setting.
     */
    @Nullable
    protected final Boolean immutableGetDisableTimestamps()
    {
        return m__bDisableGenerationTimestamps;
    }

    /**
     * Retrieves whether to use generation timestamps.
     * @return such setting.
     */
    @NotNull
    public Boolean getDisableTimestamps()
    {
        Boolean result = null;

        @Nullable final String aux = System.getProperty(DISABLE_TIMESTAMPS);

        if (aux == null)
        {
            result = immutableGetDisableTimestamps();
        }

        if (result == null)
        {
            result = Boolean.FALSE;
        }

        return result;
    }

    /**
     * Executes QueryJ via Maven.
     * @throws org.apache.maven.plugin.MojoExecutionException if the process fails.
     */
    @Override
    public void execute()
        throws MojoExecutionException
    {
        execute(getLog());
    }

    /**
     * Executes QueryJ via Maven.
     * @param log the Maven log.
     * @throws MojoExecutionException if the process fails.
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
        String result = UNKNOWN_LITERAL;

        if (   (properties != null)
            && (properties.containsKey(VERSION_LITERAL)))
        {
            result = properties.getProperty(VERSION_LITERAL);
        }

        return result;
    }

    /**
     * Executes QueryJ via Maven2.
     * @param log the Maven log.
     * @param version the QueryJ version.
     * @throws MojoExecutionException if the process fails.
     */
    protected void execute(@NotNull final Log log, final String version)
        throws MojoExecutionException
    {
        boolean running = false;

        @Nullable final File outputDirPath = getOutputDir();

        @Nullable final QueryJTask task;

        if  (outputDirPath != null)
        {
            //initialize directories
            @NotNull final File outputDir = outputDirPath.getAbsoluteFile();

            if (   (!outputDir.exists())
                && (!outputDir.mkdirs()))
            {
                log.warn("Cannot create output folder: " + outputDir);
            }

            //execute task
            task = buildTask(version, log);

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
            @Nullable final InputStream pomProperties =
                getClass().getClassLoader().getResourceAsStream(POM_PROPERTIES_LOCATION);

            if (pomProperties != null)
            {
                result = new Properties();

                result.load(pomProperties);
            }
        }
        catch (@NotNull final IOException ioException)
        {
            log.warn(
                Literals.CANNOT_READ_MY_OWN_POM + POM_PROPERTIES_LOCATION,
                ioException);
        }

        return result;
    }

    /**
     * Initializes the logging.
     * @param commonsLoggingLog such log.
     */
    protected void initLogging(@NotNull final org.apache.commons.logging.Log commonsLoggingLog)
    {
        UniqueLogFactory.initializeInstance(commonsLoggingLog);
    }

    /**
     * Instantiates a new task.
     * @param version the version.
     * @param log the log.
     * @return a new {@link org.acmsl.queryj.tools.ant.QueryJTask}.
     */
    @NotNull
    protected abstract QueryJTask instantiateTask(
        @NotNull final String version, @NotNull final org.apache.commons.logging.Log log);

    /**
     * Builds the QueryJ task.
     * @param version the version.
     * @param log the Maven log.
     * @return such info.
     */
    @NotNull
    protected abstract QueryJTask buildTask(@NotNull final String version, @NotNull final Log log);

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

        /*
        if (result < 1)
        {
            result = getRequestThreadCountFromRuntime();
        }
        */

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
            final Method getRequestMethod = this.session.getClass().getMethod("getRequest");
            final Object mavenExecutionRequest = getRequestMethod.invoke(this.session);
            final Method getThreadCountMethod = mavenExecutionRequest.getClass().getMethod("getThreadCount");
            final String threadCount = (String) getThreadCountMethod.invoke(mavenExecutionRequest);
            result  = Integer.valueOf(threadCount);
        }
        catch (@NotNull final Throwable unexpectedError)
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

    /**
     * Checks whether multi-threading is enabled.
     * @return {@code true} in such case.
     */
    @SuppressWarnings("unchecked")
    protected boolean isMultithreadEnabled()
    {
        @NotNull final Set<Map.Entry<?, ?>> entrySet = (Set<Map.Entry<?, ?>>) super.getPluginContext().entrySet();

        for (@NotNull final Map.Entry<?, ?> item : entrySet)
        {
            getLog().info(item.getKey() + " -> " + item.getValue());
        }

        return true;
    }

    /**
     * Retrieves the thread count from system properties (queryj.threadCount).
     * @return such value.
     */
    public int getRequestThreadCountFromSystemProperties()
    {
        int result = 0;

        @Nullable final String aux = System.getProperty("queryj.threadCount");

        if (aux != null)
        {
            result = Integer.parseInt(aux);
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
              "{ \"outputDir\": \"" + m__OutputDir + '"'
            + ", \"headerFile\": \"" + m__HeaderFile + '"'
            + ", \"encoding\": \"" + m__strEncoding + '"'
            + ", \"disableGenerationTimestamps\": \"" + m__bDisableGenerationTimestamps + '"'
            + ", \"session\": \"" + session.hashCode() + '"'
            + ", \"class\": \"QueryJMojo\""
            + ", \"package\": \"org.acmsl.queryj.tools.maven\" }";
    }
}
