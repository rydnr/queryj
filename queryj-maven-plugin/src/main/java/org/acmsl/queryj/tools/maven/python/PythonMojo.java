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
 * Filename: PythonMojo.java
 *
 * Author: Jose San Leandro Armendariz.
 *
 * Description: Executes QueryJ's PythonMojo.
 */
package org.acmsl.queryj.tools.maven.python;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.tools.ant.QueryJTask;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.maven.python.Literals;
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
public class PythonMojo
    extends QueryJMojo
{
    /**
     * Whether to disable checkthread.org annotations.
     */
    @Parameter (name = Literals.PYTHON_ROOT_FOLDER, property = Literals.PYTHON_ROOT_FOLDER, required = true)
    private File m__PythonRootFolder;

    /**
     * The current build session instance. This is used for toolchain manager API calls.
     * @readonly
     */
    @Parameter (defaultValue = "${session}", required = true, readonly = true)
    private MavenSession session;

    /**
     * Specifies the root folder of Python sources.
     * @param rootFolder such folder.
     */
    protected final void immutableSetPythonRootFolder(@NotNull final File rootFolder)
    {
        m__PythonRootFolder = rootFolder;
    }

    /**
     * Specifies the root folder of Python sources.
     * @param rootFolder such folder.
     */
    public void setPythonRootFolder(@NotNull final File rootFolder)
    {
        immutableSetPythonRootFolder(rootFolder);
    }

    /**
     * Return the root folder of Python sources.
     * @return such folder.
     */
    @Nullable
    protected final File immutableGetPythonRootFolder()
    {
        return m__PythonRootFolder;
    }

    /**
     * Return the root folder of Python sources.
     * @return such folder.
     */
    @Nullable
    public File getPythonRootFolder()
    {
        final File result;

        @Nullable final String aux = System.getProperty(Literals.PYTHON_ROOT_FOLDER);

        if (aux == null)
        {
            result = immutableGetPythonRootFolder();
        }
        else
        {
            result = new File(aux);
        }

        return result;
    }

    /**
     * Builds the QueryJ task.
     * @param version the version.
     * @param log the Maven log.
     * @return such info.
     */
    @NotNull
    @Override
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

        t_Log.debug("Python root folder: " + getPythonRootFolder());
        result.setPythonRootFolder(getPythonRootFolder());

        t_Log.debug("Output dir: " + getOutputDir());
        result.setOutputdir(getOutputDir());

        t_Log.debug("Header file: " + getHeaderFile());
        result.setHeaderfile(getHeaderFile());

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
              "{ \"pythonRootFolder\": \"" + m__PythonRootFolder + '"'
            + ", \"outputDir\": \"" + getOutputDir() + '"'
            + ", \"headerFile\": \"" + getHeaderFile() + '"'
            + ", \"encoding\": \"" + getEncoding() + '"'
            + ", \"disableTimestamps\": \"" + getDisableTimestamps() + '"'
            + ", \"class\": \"PythonMojo\""
            + ", \"package\": \"org.acmsl.queryj.tools.maven.python\" }";
    }
}
