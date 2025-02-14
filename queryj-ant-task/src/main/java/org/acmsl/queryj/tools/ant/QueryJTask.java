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
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJChain;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.SerializablePropertiesConfiguration;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;
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
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 */
@SuppressWarnings("unused")
@ThreadSafe
public abstract class QueryJTask
    extends     Task
    implements  QueryJSettings,
                DynamicConfigurator
{
    /**
     * The <code>QueryJChain</code> delegee.
     */
    private QueryJCommand m__QueryJCommand;

    /**
     * The classpath.
     */
    private Path m__Classpath;

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
     * Retrieves the {@link Log} instance.
     * @return such instance.
     */
    @NotNull
    public Log getLog()
    {
        return getQueryJCommand().getLog();
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
            new QueryJCommandWrapper<File>(delegee).setSetting(OUTPUT_DIR, outputdir);
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
        return delegee.getFileSetting(OUTPUT_DIR);
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
     * @throws org.apache.tools.ant.BuildException if the process fails.
     */
    @Override
    public void execute()
        throws  BuildException
    {
        execute(getQueryJCommand());
    }

    /**
     * Creates a chain.
     * @return the chain.
     */
    @NotNull
    protected abstract <C extends QueryJCommand, CH extends QueryJCommandHandler<C>> QueryJChain<C, CH> createChain();

    /**
     * Requests the chained logic to be performed.
     * @param command the command.
     * @throws org.apache.tools.ant.BuildException if the process fails.
     */
    protected void execute(@NotNull final QueryJCommand command)
        throws  BuildException
    {
        try
        {
            createChain().process(command);
        }
        catch  (@NotNull final QueryJBuildException buildException)
        {
            throw new BuildException(buildException.getMessage(), buildException);
        }
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
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"classpath\": \"" + m__Classpath + '"'
            + ", \"command\": " + m__QueryJCommand
            + ", \"class\": \"QueryJTask\""
            + ", \"package\": \"org.acmsl.queryj.tools.ant\" }";
    }
}
