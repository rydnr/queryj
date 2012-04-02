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
                    Spain

 ******************************************************************************
 *
 * Filename: QueryJCLILog.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Minimal logging helper to integrate with UniqueLogFactory
 * API. It implements *Log* only to make the
 * developer confortable with the API, which just means
 * replacing *LogFactory.getLog(..)* with
 * using the *UniqueLogLogFactory.getLog(..)*.
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.PrintStream;

/**
 * Minimal logging helper to integrate with UniqueLogFactory  API.
 * It implements <code>Log</code> only to make the
 * developer confortable with the API, which just means
 * replacing <code>LogFactory.getLog(..)</code> with
 * using the <code>UniqueLogFactory.getLog(..)</code>.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class QueryJCLILog
    implements  QueryJLog
{
    /**
     * The threshold.
     */
    private int m__iThreshold;

    /**
     * The <code>PrintStream</code> instance.
     */
    private PrintStream m__PrintStream;

    /**
     * Creates a <code>QueryJCLILog</code> instance
     * from given <code>PrintStream</code>.
     * @param threshold the threshold.
     * @param printStream the print stream.
     * @precondition printStream != null
     */
    public QueryJCLILog(final int threshold, final PrintStream printStream)
    {
        immutableSetThreshold(threshold);
        immutableSetPrintStream(printStream);
        immutableInitialize();
    }

    /**
     * Specifies the threshold.
     * @param threshold such value.
     */
    protected final void immutableSetThreshold(final int threshold)
    {
        m__iThreshold = threshold;
    }

    /**
     * Specifies the threshold.
     * @param threshold such value.
     */
    protected void setThreshold(final int threshold)
    {
        immutableSetThreshold(threshold);
    }

    /**
     * Retrieves the threshold.
     * @return such value.
     */
    public int getThreshold()
    {
        return m__iThreshold;
    }

    /**
     * Specifies the <code>PrintStream</code> instance.
     * @param printStream the print stream.
     */
    protected final void immutableSetPrintStream(
        final PrintStream printStream)
    {
        m__PrintStream = printStream;
    }

    /**
     * Specifies the <code>PrintStream</code> instance.
     * @param printStream the print stream.
     */
    protected void setPrintStream(final PrintStream printStream)
    {
        immutableSetPrintStream(printStream);
    }

    /**
     * Retrieves the <code>PrintStream</code> instance.
     * @return the print stream.
     */
    public PrintStream getPrintStream()
    {
        return m__PrintStream;
    }

    /**
     * Performs any initialization steps.
     */
    protected final void immutableInitialize()
    {
        UniqueLogFactory.initializeInstance(this);
    }

    /**
     * <p> Is trace logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than trace. </p>
     * @return <code>true</code> in such case.
     */
    public boolean isTraceEnabled()
    {
        return isTraceEnabled(getThreshold());
    }

    /**
     * <p> Is trace logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than trace. </p>
     * @return <code>true</code> in such case.
     */
    protected boolean isTraceEnabled(final int threshold)
    {
        return threshold >= TRACE;
    }

    /**
     * <p> Is debug logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than debug. </p>
     * @return <code>true</code> in such case.
     */
    public boolean isDebugEnabled()
    {
        return isDebugEnabled(getThreshold());
    }

    /**
     * <p> Is debug logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than debug. </p>
     * @param threshold the threshold.
     * @return <code>true</code> in such case.
     */
    protected boolean isDebugEnabled(final int threshold)
    {
        return threshold >= DEBUG;
    }

    /**
     * <p> Is info logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than info. </p>
     * @return <code>true</code> in such case.
     */
    public boolean isInfoEnabled()
    {
        return isInfoEnabled(getThreshold());
    }

    /**
     * <p> Is info logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than info. </p>
     * @param threshold the threshold.
     * @return <code>true</code> in such case.
     */
    protected boolean isInfoEnabled(final int threshold)
    {
        return threshold >= INFO;
    }

    /**
     * <p> Is warn logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than warn. </p>
     * @return <code>true</code> in such case.
     */
    public boolean isWarnEnabled()
    {
        return isWarnEnabled(getThreshold());
    }

    /**
     * <p> Is warn logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than warn. </p>
     * @param threshold the threshold.
     * @return <code>true</code> in such case.
     */
    protected boolean isWarnEnabled(final int threshold)
    {
        return threshold >= WARN;
    }

    /**
     * <p> Is error logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than error. </p>
     * @return <code>true</code> in such case.
     */
    public boolean isErrorEnabled()
    {
        return isErrorEnabled(getThreshold());
    }

    /**
     * <p> Is error logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than error. </p>
     * @param threshold the threshold.
     * @return <code>true</code> in such case.
     */
    protected boolean isErrorEnabled(final int threshold)
    {
        return threshold >= ERROR;
    }

    /**
     * <p> Is fatal logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than fatal. </p>
     * @return <code>true</code> in such case.
     */
    public boolean isFatalEnabled()
    {
        return isFatalEnabled(getThreshold());
    }

    /**
     * <p> Is fatal logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than fatal. </p>
     * @param threshold the threshold.
     * @return <code>true</code> in such case.
     */
    protected boolean isFatalEnabled(final int threshold)
    {
        return threshold >= FATAL;
    }

    /**
     * <p>Logs a message with trace log level.</p>
     * @param message the message.
     */
    public void trace(final Object message)
    {
        if  (isTraceEnabled())
        {
            log(message, getPrintStream());
        }
    }
    
    /**
     * <p>Logs an error with trace log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    public void trace(final Object message, final Throwable throwable)
    {
        if  (isTraceEnabled())
        {
            log(message, throwable, getPrintStream());
        }
    }
    
    /**
     * <p>Logs a message.</p>
     * @param message the message.
     * @param printStream the <code>PrintStream</code> instance.
     * @precondition project != null
     */
    protected void log(final Object message, @NotNull final PrintStream printStream)
    {
        printStream.println("" + message);
    }

    /**
     * <p>Logs an error.</p>
     * @param message the message.
     * @param throwable the cause.
     * @param printStream the print stream.
     * @precondition printStream != null
     */
    protected void log(
        final Object message,
        @Nullable final Throwable throwable,
        @NotNull final PrintStream printStream)
    {
        @NotNull String t_strMessage = "" + message;

        if  (throwable != null)
        {
            t_strMessage += "(" + throwable.getMessage() + ")";
        }

        printStream.println(t_strMessage);

        if  (throwable != null)
        {
            throwable.printStackTrace(printStream);
        }
    }
    
    /**
     * <p>Logs a message with debug log level.</p>
     * @param message the message.
     */
    public void debug(final Object message)
    {
        if  (isDebugEnabled())
        {
            log(message, getPrintStream());
        }
    }
    
    /**
     * <p>Logs an error with debug log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    public void debug(final Object message, final Throwable throwable)
    {
        if  (isDebugEnabled())
        {
            log(message, throwable, getPrintStream());
        }
    }
    
    /**
     * <p>Logs a message with info log level.</p>
     * @param message the message.
     */
    public void info(final Object message)
    {
        if  (isInfoEnabled())
        {
            log(message, getPrintStream());
        }
    }
    
    /**
     * <p>Logs an error with info log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    public void info(final Object message, final Throwable throwable)
    {
        if  (isInfoEnabled())
        {
            log(message, throwable, getPrintStream());
        }
    }
    
    /**
     * <p>Logs a message with warn log level.</p>
     * @param message the message.
     */
    public void warn(final Object message)
    {
        if  (isWarnEnabled())
        {
            log(message, getPrintStream());
        }
    }
    
    /**
     * <p>Logs an error with warn log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    public void warn(final Object message, final Throwable throwable)
    {
        if  (isWarnEnabled())
        {
            log(message, throwable, getPrintStream());
        }
    }
    
    /**
     * <p>Logs a message with error log level.</p>
     * @param message the message.
     */
    public void error(final Object message)
    {
        if  (isErrorEnabled())
        {
            log(message, getPrintStream());
        }
    }
    
    /**
     * <p>Logs an error with error log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    public void error(final Object message, final Throwable throwable)
    {
        if  (isErrorEnabled())
        {
            log(message, throwable, getPrintStream());
        }
    }
    
    /**
     * <p>Logs a message with fatal log level.</p>
     * @param message the message.
     */
    public void fatal(final Object message)
    {
        if  (isFatalEnabled())
        {
            log(message, getPrintStream());
        }
    }
    
    /**
     * <p>Logs an error with fatal log level.</p>
     * @param message the message.
     * @param throwable the cause.
     */
    public void fatal(final Object message, final Throwable throwable)
    {
        if  (isFatalEnabled())
        {
            log(message, throwable, getPrintStream());
        }
    }
}
