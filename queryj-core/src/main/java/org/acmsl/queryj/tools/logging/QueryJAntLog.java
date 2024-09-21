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
                    Spain

 ******************************************************************************
 *
 * Filename: QueryJAntLog.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Minimal logging helper to avoid using Ant-specific
 *              API. It implements *Log* only to make the developer comfortable
 *              with the API, which just means replacing *LogFactory.getLog(..)*
 *              with *UniqueLogFactory.getLog(..)*.
 *
 */
package org.acmsl.queryj.tools.logging;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Minimal logging helper to avoid using Ant-specific
 * API. It implements <code>Log</code> only to make the
 * developer comfortable with the API, which just means
 * replacing <code>LogFactory.getLog(..)</code> with
 * <code>UniqueLogFactory.getLog(..)</code>.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class QueryJAntLog
    implements  QueryJLog
{
    /**
     * The <code>Project</code> instance.
     */
    private Project m__Project;

    /**
     * Creates a <code>QueryJAntLog</code> instance
     * from given <code>Project</code>.
     * @param project the Ant project.
     */
    public QueryJAntLog(@NotNull final Project project)
    {
        immutableSetProject(project);
        immutableInitialize();
    }

    /**
     * Specifies the <code>Project</code> instance.
     * @param project the Ant project.
     */
    protected final void immutableSetProject(@NotNull final Project project)
    {
        m__Project = project;
    }

    /**
     * Specifies the <code>Project</code> instance.
     * @param project the Ant project.
     */
    @SuppressWarnings("unused")
    protected void setProject(final Project project)
    {
        immutableSetProject(project);
    }

    /**
     * Retrieves the <code>Project</code> instance.
     * @return the Ant project.
     */
    public Project getProject()
    {
        return m__Project;
    }

    /**
     * Performs any initialization steps.
     */
    protected final void immutableInitialize()
    {
        UniqueLogFactory.initializeInstance(this);
    }

    /**
     * {inheritDoc}
     */
    @Override
    public boolean isDebugEnabled()
    {
        return true;
    }

    /**
     * {inheritDoc}
     */
    @Override
    public boolean isErrorEnabled()
    {
        return true;
    }

    /**
     * {inheritDoc}
     */
    @Override
    public boolean isFatalEnabled()
    {
        return true;
    }

    /**
     * {inheritDoc}
     */
    @Override
    public boolean isInfoEnabled()
    {
        return true;
    }

    /**
     * {inheritDoc}
     */
    @Override
    public boolean isTraceEnabled()
    {
        return true;
    }

    /**
     * {inheritDoc}
     */
    @Override
    public boolean isWarnEnabled()
    {
        return true;
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void trace(@Nullable final Object message)
    {
        trace(message, getProject());
    }
    
    /**
     * <p>Logs a message with trace log level.</p>
     * @param message the message.
     * @param project the <code>Project</code> instance.
     */
    protected void trace(@Nullable final Object message, @NotNull final Project project)
    {
        project.log("" + message, Project.MSG_VERBOSE);
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void trace(@Nullable final Object message, @NotNull final Throwable throwable)
    {
        trace(message, throwable, getProject());
    }
    
    /**
     * <p>Logs an error with trace log level.</p>
     * @param message the message.
     * @param throwable the cause.
     * @param project the project.
     */
    protected void trace(
        @Nullable final Object message,
        @NotNull final Throwable throwable,
        @NotNull final Project project)
    {
        project.log(
            message + "(" + throwable.getMessage() + ")",
            Project.MSG_VERBOSE);
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void debug(@Nullable final Object message)
    {
        debug(message, getProject());
    }
    
    /**
     * <p>Logs a message with debug log level.</p>
     * @param message the message.
     * @param project the <code>Project</code> instance.
     */
    protected void debug(@Nullable final Object message, @NotNull final Project project)
    {
        project.log("" + message, Project.MSG_DEBUG);
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void debug(@Nullable final Object message, @NotNull final Throwable throwable)
    {
        debug(message, throwable, getProject());
    }
    
    /**
     * <p>Logs an error with debug log level.</p>
     * @param message the message.
     * @param throwable the cause.
     * @param project the project.
     */
    protected void debug(
        @Nullable final Object message,
        @NotNull final Throwable throwable,
        @NotNull final Project project)
    {
        project.log(
            message + "(" + throwable.getMessage() + ")",
            Project.MSG_DEBUG);
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void info(@Nullable final Object message)
    {
        info(message, getProject());
    }
    
    /**
     * <p>Logs a message with info log level.</p>
     * @param message the message.
     * @param project the <code>Project</code> instance.
     */
    protected void info(@Nullable final Object message, @NotNull final Project project)
    {
        project.log("" + message, Project.MSG_INFO);
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void info(@Nullable final Object message, @NotNull final Throwable throwable)
    {
        info(message, throwable, getProject());
    }
    
    /**
     * <p>Logs an error with info log level.</p>
     * @param message the message.
     * @param throwable the cause.
     * @param project the project.
     */
    protected void info(
        @Nullable final Object message,
        @NotNull final Throwable throwable,
        @NotNull final Project project)
    {
        project.log(
            message + "(" + throwable.getMessage() + ")",
            Project.MSG_INFO);
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void warn(@Nullable final Object message)
    {
        warn(message, getProject());
    }
    
    /**
     * <p>Logs a message with warn log level.</p>
     * @param message the message.
     * @param project the <code>Project</code> instance.
     */
    protected void warn(@Nullable final Object message, @NotNull final Project project)
    {
        project.log("" + message, Project.MSG_WARN);
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void warn(@Nullable final Object message, @NotNull final Throwable throwable)
    {
        warn(message, throwable, getProject());
    }
    
    /**
     * <p>Logs an error with warn log level.</p>
     * @param message the message.
     * @param throwable the cause.
     * @param project the <code>Project</code> instance.
     */
    protected void warn(
        @Nullable final Object message,
        @NotNull final Throwable throwable,
        @NotNull final Project project)
    {
        project.log(
            message + "(" + throwable.getMessage() + ")",
            Project.MSG_WARN);
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void error(@Nullable final Object message)
    {
        error(message, getProject());
    }
    
    /**
     * <p>Logs a message with error log level.</p>
     * @param message the message.
     * @param project the <code>Project</code> instance.
     */
    protected void error(@Nullable final Object message, @NotNull final Project project)
    {
        project.log("" + message, Project.MSG_ERR);
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void error(@Nullable final Object message, @NotNull final Throwable throwable)
    {
        error(message, throwable, getProject());
    }
    
    /**
     * <p>Logs an error with error log level.</p>
     * @param message the message.
     * @param throwable the cause.
     * @param project the <code>Project</code> instance.
     */
    protected void error(
        @Nullable final Object message,
        @NotNull final Throwable throwable,
        @NotNull final Project project)
    {
        project.log(
            message + "(" + throwable.getMessage() + ")",
            Project.MSG_ERR);
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void fatal(@Nullable final Object message)
    {
        fatal(message, getProject());
    }
    
    /**
     * <p>Logs a message with fatal log level.</p>
     * @param message the message.
     * @param project the <code>Project</code> instance.
     */
    protected void fatal(@Nullable final Object message, @NotNull final Project project)
    {
        project.log("" + message, Project.MSG_ERR);
    }

    /**
     * {inheritDoc}
     */
    @Override
    public void fatal(@Nullable final Object message, @NotNull final Throwable throwable)
    {
        fatal(message, throwable, getProject());
    }
    
    /**
     * <p>Logs an error with fatal log level.</p>
     * @param message the message.
     * @param throwable the cause.
     * @param project the <code>Project</code> instance.
     */
    protected void fatal(
        @Nullable final Object message,
        @NotNull final Throwable throwable,
        @NotNull final Project project)
    {
        project.log(
            message + "(" + throwable.getMessage() + ")",
            Project.MSG_ERR);
    }
}
