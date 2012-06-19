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
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Apache Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some Log4J classes.
 */
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/*
 * Importing some Log4J classes.
 */
import org.apache.log4j.Logger;

/**
 * Ensures Log4J is correctly configured.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2012/06/19
 */
public class Log4JInitializerHandler
    extends  AbstractQueryJCommandHandler
{
    /**
     * Handles given parameters.
     *
     * @param parameters the parameters.
     * @return <code>true</code> to avoid further processing of such command
     *         by different handlers.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @Override
    protected boolean handle(@NotNull final Map parameters)
        throws QueryJBuildException
    {
        if (!isLog4JConfigured())
        {
            connectLog4JToMaven();
        }

        return false;
    }

    /**
     * Checks whether Log4J is correctly configured.
     * @return <code>true</code> in such case.
     */
    protected boolean isLog4JConfigured()
    {
        boolean result = true;

        Logger root = Logger.getRootLogger();

        if (!root.getAllAppenders().hasMoreElements())
        {
            result = false;
        }

        return result;
    }

    /**
     * Configures Log4J to use Maven's plugin API.
     */
    protected void connectLog4JToMaven()
    {
        Logger root = Logger.getRootLogger();

        root.addAppender(
            new WriterAppender(
                new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN),
                new WriterAdapter()));
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
         * {@inheritDoc}
         */
        @Override
        public void write(final char[] cbuf, final int off, final int len)
            throws IOException
        {
            Log t_Log = UniqueLogFactory.getLog("log4j");

            if (t_Log != null)
            {
                t_Log.debug(new String(cbuf, off, len));
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void flush() throws IOException
        {
            // Nothing to do
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void close() throws IOException
        {
            // Nothing to do
        }
    }
}
