//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Minimal logging helper to avoid using Ant-specific
 * API. It implements *Log* only to make the
 * developer confortable with the API, which just means
 * replacing *LogFactory.getLog(..)* with
 * using the *UniqueLogFactory.getLog(..)*.
 *
 * Version: $Revision$ ($Author$ at $Date$)
 *
 * $Id$
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

/**
 * Minimal logging helper to avoid using Ant-specific
 * API. It implements <code>Log</code> only to make the
 * developer confortable with the API, which just means
 * replacing <code>LogFactory.getLog(..)</code> with
 * using the <code>UniqueLogFactory.getLog(..)</code>.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 * @version $Revision$ ($Author$ at $Date$)
 */
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
     * @precondition project != null
     */
    public QueryJAntLog(final Project project)
    {
        immutableSetProject(project);
        immutableInitialize();
    }

    /**
     * Specifies the <code>Project</code> instance.
     * @param project the Ant project.
     */
    protected final void immutableSetProject(
        final Project project)
    {
        m__Project = project;
    }

    /**
     * Specifies the <code>Project</code> instance.
     * @param project the Ant project.
     */
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
     * <p> Is debug logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than debug. </p>
     */
    public boolean isDebugEnabled()
    {
        return true;
    }

    /**
     * <p> Is error logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than error. </p>
     */
    public boolean isErrorEnabled()
    {
        return true;
    }

    /**
     * <p> Is fatal logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than fatal. </p>
     */
    public boolean isFatalEnabled()
    {
        return true;
    }

    /**
     * <p> Is info logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than info. </p>
     */
    public boolean isInfoEnabled()
    {
        return true;
    }

    /**
     * <p> Is trace logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than trace. </p>
     */
    public boolean isTraceEnabled()
    {
        return true;
    }


    /**
     * <p> Is warn logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than warn. </p>
     */
    public boolean isWarnEnabled()
    {
        return true;
    }

    /**
     * <p>Logs a message with trace log level.</p>
     * @param message the message.
     */
    public void trace(final Object message)
    {
        trace(message, getProject());
    }
    
    /**
     * <p>Logs a message with trace log level.</p>
     * @param message the message.
     * @param project the <code>Project</code> instance.
     * @precondition project != null
     */
    protected void trace(final Object message, final Project project)
    {
        project.log("" + message, Project.MSG_VERBOSE);
    }

    /**
     * <p>Logs an error with trace log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    public void trace(final Object message, final Throwable throwable)
    {
        trace(message, throwable, getProject());
    }
    
    /**
     * <p>Logs an error with trace log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    protected void trace(
        final Object message,
        final Throwable throwable,
        final Project project)
    {
        project.log(
            message + "(" + throwable.getMessage() + ")",
            Project.MSG_VERBOSE);
    }
    
    /**
     * <p>Logs a message with debug log level.</p>
     * @param message the message.
     */
    public void debug(final Object message)
    {
        debug(message, getProject());
    }
    
    /**
     * <p>Logs a message with debug log level.</p>
     * @param message the message.
     * @param project the <code>Project</code> instance.
     * @precondition project != null
     */
    protected void debug(final Object message, final Project project)
    {
        project.log("" + message, Project.MSG_DEBUG);
    }

    /**
     * <p>Logs an error with debug log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    public void debug(final Object message, final Throwable throwable)
    {
        debug(message, throwable, getProject());
    }
    
    /**
     * <p>Logs an error with debug log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    protected void debug(
        final Object message,
        final Throwable throwable,
        final Project project)
    {
        project.log(
            message + "(" + throwable.getMessage() + ")",
            Project.MSG_DEBUG);
    }
    
    /**
     * <p>Logs a message with info log level.</p>
     * @param message the message.
     */
    public void info(final Object message)
    {
        info(message, getProject());
    }
    
    /**
     * <p>Logs a message with info log level.</p>
     * @param message the message.
     * @param project the <code>Project</code> instance.
     * @precondition project != null
     */
    protected void info(final Object message, final Project project)
    {
        project.log("" + message, Project.MSG_INFO);
    }

    /**
     * <p>Logs an error with info log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    public void info(final Object message, final Throwable throwable)
    {
        info(message, throwable, getProject());
    }
    
    /**
     * <p>Logs an error with info log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    protected void info(
        final Object message,
        final Throwable throwable,
        final Project project)
    {
        project.log(
            message + "(" + throwable.getMessage() + ")",
            Project.MSG_INFO);
    }
    
    /**
     * <p>Logs a message with warn log level.</p>
     * @param message the message.
     */
    public void warn(final Object message)
    {
        warn(message, getProject());
    }
    
    /**
     * <p>Logs a message with warn log level.</p>
     * @param message the message.
     * @param project the <code>Project</code> instance.
     * @precondition project != null
     */
    protected void warn(final Object message, final Project project)
    {
        project.log("" + message, Project.MSG_WARN);
    }

    /**
     * <p>Logs an error with warn log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    public void warn(final Object message, final Throwable throwable)
    {
        warn(message, throwable, getProject());
    }
    
    /**
     * <p>Logs an error with warn log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    protected void warn(
        final Object message,
        final Throwable throwable,
        final Project project)
    {
        project.log(
            message + "(" + throwable.getMessage() + ")",
            Project.MSG_WARN);
    }

    /**
     * <p>Logs a message with error log level.</p>
     * @param message the message.
     */
    public void error(final Object message)
    {
        error(message, getProject());
    }
    
    /**
     * <p>Logs a message with error log level.</p>
     * @param message the message.
     * @param project the <code>Project</code> instance.
     * @precondition project != null
     */
    protected void error(final Object message, final Project project)
    {
        project.log("" + message, Project.MSG_ERR);
    }

    /**
     * <p>Logs an error with error log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    public void error(final Object message, final Throwable throwable)
    {
        error(message, throwable, getProject());
    }
    
    /**
     * <p>Logs an error with error log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    protected void error(
        final Object message,
        final Throwable throwable,
        final Project project)
    {
        project.log(
            message + "(" + throwable.getMessage() + ")",
            Project.MSG_ERR);
    }

    /**
     * <p>Logs a message with fatal log level.</p>
     * @param message the message.
     */
    public void fatal(final Object message)
    {
        fatal(message, getProject());
    }
    
    /**
     * <p>Logs a message with fatal log level.</p>
     * @param message the message.
     * @param project the <code>Project</code> instance.
     * @precondition project != null
     */
    protected void fatal(final Object message, final Project project)
    {
        project.log("" + message, Project.MSG_ERR);
    }

    /**
     * <p>Logs an error with fatal log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    public void fatal(final Object message, final Throwable throwable)
    {
        fatal(message, throwable, getProject());
    }
    
    /**
     * <p>Logs an error with fatal log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    protected void fatal(
        final Object message,
        final Throwable throwable,
        final Project project)
    {
        project.log(
            message + "(" + throwable.getMessage() + ")",
            Project.MSG_ERR);
    }
}
