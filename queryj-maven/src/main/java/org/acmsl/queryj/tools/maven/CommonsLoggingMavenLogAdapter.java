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
 * Filename: CommonsLoggingMavenLogAdapter.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Sends Commons-Logging log messages to Maven Log.
 *
 * Date: 2014/03/07
 * Time: 07:24
 *
 */
package org.acmsl.queryj.tools.maven;

/*
 * Importing Maven classes.
 */
import org.apache.maven.plugin.logging.Log;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/**
 * Sends Commons-Logging log messages to Maven Log.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/07 07:24
 */
@ThreadSafe
public class CommonsLoggingMavenLogAdapter
    implements org.apache.commons.logging.Log
{
    /**
     * The wrapped instance.
     */
    private Log log;

    /**
     * Creates an adapter for given Maven {@link Log}.
     * @param mavenLog the log instance.
     */
    public CommonsLoggingMavenLogAdapter(@NotNull final Log mavenLog)
    {
        immutableSetLog(mavenLog);
    }

    /**
     * Specifies the Maven {@link Log} instance.
     * @param mavenLog such log.
     */
    protected final void immutableSetLog(@NotNull final Log mavenLog)
    {
        this.log = mavenLog;
    }

    /**
     * Specifies the Maven {@link Log} instance.
     * @param mavenLog such log.
     */
    @SuppressWarnings("unused")
    protected void setLog(final Log mavenLog)
    {
        immutableSetLog(mavenLog);
    }

    /**
     * Retrieves the Maven {@link Log} instance.
     * @return such instance.
     */
    public Log getLog()
    {
        return log;
    }

    /**
     * <p> Is trace logging currently enabled? </p>
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than trace. </p>
     *
     * @return true if trace is enabled in the underlying logger.
     */
    @Override
    public boolean isTraceEnabled()
    {
        return isTraceEnabled(getLog());
    }

    /**
     * Checks whether the debug level is enabled.
     * @param mavenLog the underlying {@link Log}.
     * @return {@code true} in such case.
     */
    protected boolean isTraceEnabled(@NotNull final Log mavenLog)
    {
        return mavenLog.isDebugEnabled();
    }

    /**
     * <p> Log a message with trace log level. </p>
     * @param message log this message.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void trace(@NotNull final Object message, @NotNull final Log mavenLog)
    {
        mavenLog.debug(message.toString());
    }

    /**
     * <p> Log a message with trace log level. </p>
     * @param message log this message.
     * @param error the error to log.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void trace(@NotNull final Object message, @NotNull final Throwable error, @NotNull final Log mavenLog)
    {
        mavenLog.debug(message.toString(), error);
    }

    /**
     * <p> Log a message with trace log level. </p>
     * @param error the error to log.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void trace(@NotNull final Throwable error, @NotNull final Log mavenLog)
    {
        mavenLog.debug(error);
    }

    /**
     * <p> Log a message with trace log level. </p>
     * @param message log this message
     */
    @Override
    public void trace(@Nullable final Object message)
    {
        if (message != null)
        {
            trace(message, getLog());
        }
    }

    /**
     * <p> Log an error with trace log level. </p>
     *
     * @param message log this message
     * @param t       log this cause
     */
    @Override
    public void trace(@Nullable final Object message, @Nullable final Throwable t)
    {
        if (   (message != null)
            && (t != null))
        {
            trace(message, t, getLog());
        }
        else if (message != null)
        {
            trace(message, getLog());
        }
        else if (t != null)
        {
            trace(t, getLog());
        }
    }

    /**
     * <p> Is debug logging currently enabled? </p>
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than debug. </p>
     *
     * @return true if debug is enabled in the underlying logger.
     */
    @Override
    public boolean isDebugEnabled()
    {
        return isDebugEnabled(getLog());
    }

    /**
     * Checks whether the debug level is enabled.
     * @param mavenLog the underlying {@link Log}.
     * @return {@code true} in such case.
     */
    protected boolean isDebugEnabled(@NotNull final Log mavenLog)
    {
        return mavenLog.isDebugEnabled();
    }

    /**
     * <p> Log a message with debug log level. </p>
     * @param message log this message.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void debug(@NotNull final Object message, @NotNull final Log mavenLog)
    {
        mavenLog.debug(message.toString());
    }

    /**
     * <p> Log a message with debug log level. </p>
     * @param message log this message.
     * @param error the error to log.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void debug(@NotNull final Object message, @NotNull final Throwable error, @NotNull final Log mavenLog)
    {
        mavenLog.debug(message.toString(), error);
    }

    /**
     * <p> Log a message with debug log level. </p>
     * @param error the error to log.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void debug(@NotNull final Throwable error, @NotNull final Log mavenLog)
    {
        mavenLog.debug(error);
    }

    /**
     * <p> Log a message with debug log level. </p>
     * @param message log this message
     */
    @Override
    public void debug(@Nullable final Object message)
    {
        if (message != null)
        {
            debug(message, getLog());
        }
    }

    /**
     * <p> Log an error with debug log level. </p>
     *
     * @param message log this message
     * @param t       log this cause
     */
    @Override
    public void debug(@Nullable final Object message, @Nullable final Throwable t)
    {
        if (   (message != null)
            && (t != null))
        {
            debug(message, t, getLog());
        }
        else if (message != null)
        {
            debug(message, getLog());
        }
        else if (t != null)
        {
            debug(t, getLog());
        }
    }

    /**
     * <p> Is info logging currently enabled? </p>
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than info. </p>
     *
     * @return true if info is enabled in the underlying logger.
     */
    @Override
    public boolean isInfoEnabled()
    {
        return isInfoEnabled(getLog());
    }

    /**
     * Checks whether the debug level is enabled.
     * @param mavenLog the underlying {@link Log}.
     * @return {@code true} in such case.
     */
    protected boolean isInfoEnabled(@NotNull final Log mavenLog)
    {
        return mavenLog.isInfoEnabled();
    }


    /**
     * <p> Log a message with info log level. </p>
     * @param message log this message.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void info(@NotNull final Object message, @NotNull final Log mavenLog)
    {
        mavenLog.info(message.toString());
    }

    /**
     * <p> Log a message with info log level. </p>
     * @param message log this message.
     * @param info the info to log.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void info(@NotNull final Object message, @NotNull final Throwable info, @NotNull final Log mavenLog)
    {
        mavenLog.info(message.toString(), info);
    }

    /**
     * <p> Log a message with info log level. </p>
     * @param info the info to log.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void info(@NotNull final Throwable info, @NotNull final Log mavenLog)
    {
        mavenLog.info(info);
    }

    /**
     * <p> Log a message with info log level. </p>
     *
     * @param message log this message
     */
    @Override
    public void info(@Nullable final Object message)
    {
        if (message != null)
        {
            info(message, getLog());
        }
    }

    /**
     * <p> Log an info with info log level. </p>
     *
     * @param message log this message
     * @param t       log this cause
     */
    @Override
    public void info(final Object message, final Throwable t)
    {
        if (   (message != null)
            && (t != null))
        {
            info(message, t, getLog());
        }
        else if (message != null)
        {
            info(message, getLog());
        }
        else if (t != null)
        {
            info(t, getLog());
        }
    }

    /**
     * <p> Is warn logging currently enabled? </p>
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than warn. </p>
     *
     * @return true if warn is enabled in the underlying logger.
     */
    @Override
    public boolean isWarnEnabled()
    {
        return isWarnEnabled(getLog());
    }

    /**
     * Checks whether the debug level is enabled.
     * @param mavenLog the underlying {@link Log}.
     * @return {@code true} in such case.
     */
    protected boolean isWarnEnabled(@NotNull final Log mavenLog)
    {
        return mavenLog.isWarnEnabled();
    }
    /**
     * <p> Log a message with warn log level. </p>
     * @param message log this message.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void warn(@NotNull final Object message, @NotNull final Log mavenLog)
    {
        mavenLog.warn(message.toString());
    }

    /**
     * <p> Log a message with warn log level. </p>
     * @param message log this message.
     * @param warn the warn to log.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void warn(@NotNull final Object message, @NotNull final Throwable warn, @NotNull final Log mavenLog)
    {
        mavenLog.warn(message.toString(), warn);
    }

    /**
     * <p> Log a message with warn log level. </p>
     * @param warn the warn to log.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void warn(@NotNull final Throwable warn, @NotNull final Log mavenLog)
    {
        mavenLog.warn(warn);
    }

    /**
     * <p> Log a message with warn log level. </p>
     *
     * @param message log this message
     */
    @Override
    public void warn(@Nullable final Object message)
    {
        if (message != null)
        {
            warn(message, getLog());
        }
    }

    /**
     * <p> Log an warn with warn log level. </p>
     *
     * @param message log this message
     * @param t       log this cause
     */
    @Override
    public void warn(@Nullable final Object message, @Nullable final Throwable t)
    {
        if (   (message != null)
            && (t != null))
        {
            warn(message, t, getLog());
        }
        else if (message != null)
        {
            warn(message, getLog());
        }
        else if (t != null)
        {
            warn(t, getLog());
        }
    }

    /**
     * <p> Is error logging currently enabled? </p>
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than error. </p>
     *
     * @return true if error is enabled in the underlying logger.
     */
    @Override
    public boolean isErrorEnabled()
    {
        return isErrorEnabled(getLog());
    }

    /**
     * Checks whether the error level is enabled.
     * @param mavenLog the underlying {@link Log}.
     * @return {@code true} in such case.
     */
    protected boolean isErrorEnabled(@NotNull final Log mavenLog)
    {
        return mavenLog.isErrorEnabled();
    }

    /**
     * <p> Log a message with error log level. </p>
     * @param message log this message.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void error(@NotNull final Object message, @NotNull final Log mavenLog)
    {
        mavenLog.error(message.toString());
    }

    /**
     * <p> Log a message with error log level. </p>
     * @param message log this message.
     * @param error the error to log.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void error(@NotNull final Object message, @NotNull final Throwable error, @NotNull final Log mavenLog)
    {
        mavenLog.error(message.toString(), error);
    }

    /**
     * <p> Log a message with error log level. </p>
     * @param error the error to log.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void error(@NotNull final Throwable error, @NotNull final Log mavenLog)
    {
        mavenLog.error(error);
    }

    /**
     * <p> Log a message with error log level. </p>
     *
     * @param message log this message
     */
    @Override
    public void error(@Nullable final Object message)
    {
        if (message != null)
        {
            error(message, getLog());
        }
    }

    /**
     * <p> Log an error with error log level. </p>
     *
     * @param message log this message
     * @param t       log this cause
     */
    @Override
    public void error(@Nullable final Object message, @Nullable final Throwable t)
    {
        if (   (message != null)
            && (t != null))
        {
            error(message, t, getLog());
        }
        else if (message != null)
        {
            error(message, getLog());
        }
        else if (t != null)
        {
            error(t, getLog());
        }
    }

    /**
     * <p> Is fatal logging currently enabled? </p>
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than fatal. </p>
     *
     * @return true if fatal is enabled in the underlying logger.
     */
    @Override
    public boolean isFatalEnabled()
    {
        return isFatalEnabled(getLog());
    }

    /**
     * Checks whether the debug level is enabled.
     * @param mavenLog the underlying {@link Log}.
     * @return {@code true} in such case.
     */
    protected boolean isFatalEnabled(@NotNull final Log mavenLog)
    {
        return mavenLog.isErrorEnabled();
    }

    /**
     * <p> Log a message with fatal log level. </p>
     * @param message log this message.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void fatal(@NotNull final Object message, @NotNull final Log mavenLog)
    {
        mavenLog.error(message.toString());
    }

    /**
     * <p> Log a message with fatal log level. </p>
     * @param message log this message.
     * @param fatal the fatal to log.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void fatal(@NotNull final Object message, @NotNull final Throwable fatal, @NotNull final Log mavenLog)
    {
        mavenLog.error(message.toString(), fatal);
    }

    /**
     * <p> Log a message with fatal log level. </p>
     * @param fatal the fatal to log.
     * @param mavenLog the actual {@link Log} used for logging.
     */
    protected void fatal(@NotNull final Throwable fatal, @NotNull final Log mavenLog)
    {
        mavenLog.error(fatal);
    }

    /**
     * <p> Log a message with fatal log level. </p>
     *
     * @param message log this message
     */
    @Override
    public void fatal(@Nullable final Object message)
    {
        if (message != null)
        {
            fatal(message, getLog());
        }
    }

    /**
     * <p> Log an fatal with fatal log level. </p>
     *
     * @param message log this message
     * @param t       log this cause
     */
    @Override
    public void fatal(@Nullable final Object message, @Nullable final Throwable t)
    {
        if (   (message != null)
            && (t != null))
        {
            fatal(message, t, getLog());
        }
        else if (message != null)
        {
            fatal(message, getLog());
        }
        else if (t != null)
        {
            fatal(t, getLog());
        }
    }

    /**
     * Text version of the instance.
     * @return such text.
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + CommonsLoggingMavenLogAdapter.class.getSimpleName() + '"'
            + ", \"package\": \"org.acmsl.queryj.tools.maven\""
            + ", \"log\": " + log
            + " }";
    }
}
