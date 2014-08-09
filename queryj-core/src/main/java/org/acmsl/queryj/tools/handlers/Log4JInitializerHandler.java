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
 * Filename: Log4JInitializerHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Ensures Log4J is correctly configured.
 *
 * Date: 6/19/12
 * Time: 6:23 AM
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

/*
 * Importing some Apache Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some Log4J classes.
 */
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.WriterAppender;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.IOException;
import java.io.Writer;

/*
 * Importing some Log4J classes.
 */
import org.apache.log4j.Logger;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Ensures Log4J is correctly configured.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2012/06/19
 */
@ThreadSafe
public class Log4JInitializerHandler
    extends  AbstractQueryJCommandHandler
{
    /**
     * Handles given parameters.
     * @param parameters the parameters.
     * @return {@code true} to avoid further processing of such command
     *         by different handlers.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand parameters)
        throws QueryJBuildException
    {
        if (!isLog4JConfigured())
        {
            connectLog4JToMaven(parameters.getLog());
        }

        return false;
    }

    /**
     * Checks whether Log4J is correctly configured.
     * @return <code>true</code> in such case.
     */
    protected boolean isLog4JConfigured()
    {
        final boolean result;

        @NotNull final Logger root = Logger.getRootLogger();

        result = root.getAllAppenders().hasMoreElements();

        return result;
    }

    /**
     * Configures Log4J to use Maven's plugin API.
     * @param log the runtime log from Maven.
     */
    protected void connectLog4JToMaven(@Nullable final Log log)
    {
        @NotNull final Logger t_Root = Logger.getRootLogger();

        if (log != null)
        {
            t_Root.addAppender(
                new WriterAppenderImpl(
                    new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN),
                    new WriterAdapter(log, Level.INFO),
                    Level.INFO));
        }

        /*
        @NotNull final Log4JLogger t_LoggerAdapter = new Log4JLogger(t_Root);

        @Nullable final Log t_Log = UniqueLogFactory.getLog("queryj");

        if (t_Log == null)
        {
            UniqueLogFactory.initializeInstance(t_LoggerAdapter);
        }
        */
    }

    /**
     * A {@link WriterAppender} implementation to ensure the priority conditions
     * are met.
     */
    public static class WriterAppenderImpl
        extends WriterAppender
    {
        /**
         * The priority.
         */
        private Priority m__Priority;

        /**
         * Instantiate a WriterAppender and set the output destination to
         * <code>writer</code>.
         * <p>The <code>writer</code> must have been previously opened by
         * the user.
         * @param layout the layout.
         * @param writer the writer.
         * @param priority the priority.
         */
        public WriterAppenderImpl(final Layout layout, final Writer writer, final Priority priority)
        {
            super(layout, writer);
            setThreshold(priority);
        }

        /**
         * Logs the event if the priority conditions are met.
         * @param event the {@link LoggingEvent event}.
         */
        @Override
        public void append(@NotNull final LoggingEvent event)
        {
            append(event, getThreshold());
        }

        /**
         * Logs the event if the priority conditions are met.
         * @param event the {@link LoggingEvent event}.
         * @param priority the {@link Priority}.
         */
        protected void append(@NotNull final LoggingEvent event, @NotNull final Priority priority)
        {
            if (event.getLevel().isGreaterOrEqual(priority))
            {
                super.append(event);
            }
        }
    }

    /**
     * Appender to drive Log4J messages to Maven Plugin API Logging (via QueryJLog).
     * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
     * @since 2012/06/19
     */
    public static class WriterAdapter
        extends Writer
    {
        /**
         * The Maven log.
         */
        private Log m__Log;

        /**
         * The log level.
         */
        private Level m__Level;

        /**
         * Creates a new instance for given log.
         * @param log the {@link Log}.
         * @param level the level.
         */
        public WriterAdapter(@NotNull final Log log, @NotNull final Level level)
        {
            immutableSetLog(log);
            immutableSetLevel(level);
        }

        /**
         * Specifies the level.
         * @param level such {@link Level}.
         */
        protected final void immutableSetLevel(@NotNull final Level level)
        {
            this.m__Level = level;
        }

        /**
         * Specifies the level.
         * @param level such {@link Level}.
         */
        @SuppressWarnings("unused")
        protected void setLevel(@NotNull final Level level)
        {
            immutableSetLevel(level);
        }

        /**
         * Retrievs the level.
         * @return such {@link Level}.
         */
        public Level getLevel()
        {
            return this.m__Level;
        }

        /**
         * Specifies the log.
         * @param log such {@link Log} instance.
         */
        protected final void immutableSetLog(@NotNull final Log log)
        {
            this.m__Log = log;
        }

        /**
         * Specifies the log.
         * @param log such {@link Log} instance.
         */
        protected void setLog(@NotNull final Log log)
        {
            immutableSetLog(log);
        }

        /**
         * Retrieves the log.
         * @return such {@link Log} instance.
         */
        public Log getLog()
        {
            return this.m__Log;
        }

        /**
         * Logs the buffer.
         * @param cbuf the buffer to log.
         * @param off the offset.
         * @param len the buffer length.
         */
        @Override
        public void write(final char[] cbuf, final int off, final int len)
            throws IOException
        {
            write(cbuf, off, len, getLog(), getLevel());
        }

        /**
         * Logs the buffer.
         * @param cbuf the buffer to log.
         * @param off the offset.
         * @param len the buffer length.
         * @param log the actual {@link Log}.
         * @param level the log {@link Level level}.
         * @throws IOException if the content cannot be written.
         */
        protected void write(
            final char[] cbuf, final int off, final int len, @NotNull final Log log, @NotNull final Level level)
            throws IOException
        {
            switch (level.toInt())
            {
                case Level.TRACE_INT: log.trace(new String(cbuf, off, len));
                    break;
                case Level.DEBUG_INT: log.debug(new String(cbuf, off, len));
                    break;
                case Level.INFO_INT: log.info(new String(cbuf, off, len));
                    break;
                case Level.WARN_INT: log.warn(new String(cbuf, off, len));
                    break;
                case Level.FATAL_INT: log.fatal(new String(cbuf, off, len));
                    break;
                default:
                    break;
            }
        }

        /**
         * Flushes the writer's contents.
         */
        @Override
        public void flush()
            throws IOException
        {
            // Nothing to do
        }

        /**
         * Closes the writer.
         */
        @Override
        public void close()
            throws IOException
        {
            // Nothing to do
        }

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public String toString()
        {
            return "WriterAdapter{ " +
                   "\"log\"= \"" + this.m__Log
                    + "\", \"level\"=" + m__Level +
                   '}';
        }
    }
}
