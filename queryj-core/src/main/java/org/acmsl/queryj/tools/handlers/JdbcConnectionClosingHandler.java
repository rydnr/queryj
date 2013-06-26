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
 * Filename: JdbcConnectionClosingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Closes a JDBC connection.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.api.Template;
import org.acmsl.queryj.api.exceptions.CannotCloseJdbcConnectionException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.List;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Closes a JDBC connection.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class JdbcConnectionClosingHandler
    extends  AbstractQueryJCommandHandler
{
    /**
     * A token used to wait for the threads to complete.
     */
    private static final Object LOCK = new Object();

    /**
     * Creates a <code>JdbcConnectionClosingHandler</code> instance.
     */
    public JdbcConnectionClosingHandler() {}

    /**
     * Handles given information.
     *
     *
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException the build process cannot be performed.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected boolean handle(@NotNull final Map<String, ?> parameters)
        throws  QueryJBuildException
    {
        waitUntilGenerationThreadsFinish(parameters);

        closeConnection((Map <String, Connection>) parameters);

        removeConnection((Map <String, Connection>) parameters);

        return false;
    }

    /**
     * Waits until all generation threads have finish.
     * @param parameters the parameters.
     */
    @SuppressWarnings("unchecked")
    protected void waitUntilGenerationThreadsFinish(@NotNull final Map<String, ?> parameters)
    {
        waitUntilGenerationThreadsFinish(
            retrieveGenerationTasks((Map<String, List<Future<Template>>>) parameters),
            UniqueLogFactory.getLog(JdbcConnectionClosingHandler.class));
    }

    /**
     * Waits until all generation threads have finish.
     * @param tasks the tasks.
     * @param log the {@link Log} instance.
     */
    protected void waitUntilGenerationThreadsFinish(
        @NotNull final List<Future<Template>> tasks, @Nullable final Log log)
    {
        for (@Nullable final Future<Template> t_Task : tasks)
        {
            if (t_Task != null)
            {
                while (!t_Task.isDone())
                {
                    try
                    {
                        if (log != null)
                        {
                            log.debug(
                                "Waiting for " + t_Task.get().getTemplateContext().getTemplateName() + " to finish");
                        }
                    }
                    catch (@NotNull final InterruptedException interrupted)
                    {
                        if (log != null)
                        {
                            log.info(
                                "Interrupted while waiting for the threads to finish", interrupted);
                        }
                    }
                    catch (@NotNull final ExecutionException interrupted)
                    {
                        if (log != null)
                        {
                            log.info(interrupted.getMessage());
                        }

                        Throwable cause = interrupted.getCause();

                        while (cause != null)
                        {
                            if (log != null)
                            {
                                log.error(cause.getMessage(), cause);
                            }
                            cause = cause.getCause();
                        }
                    }

                    synchronized(LOCK)
                    {
                        try
                        {
                            LOCK.wait(1000);
                        }
                        catch (@NotNull final InterruptedException interrupted)
                        {
                            if (log != null)
                            {
                                log.info(
                                    "Interrupted while waiting for the threads to finish", interrupted);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Closes the JDBC connection stored in the attribute map.
     * @param parameters the parameter map.
     * @throws QueryJBuildException if the connection cannot be closed.
     */
    protected void closeConnection(@NotNull final Map<String, Connection> parameters)
        throws  QueryJBuildException
    {
        closeConnection(parameters.get(JdbcConnectionOpeningHandler.JDBC_CONNECTION));
    }

    /**
     * Closes the connection.
     * @param connection the JDBC connection.
     * @throws QueryJBuildException whenever the required
     * connection is not present or valid.
     */
    protected void closeConnection(@Nullable final Connection connection)
        throws  QueryJBuildException
    {
        if  (connection != null)
        {
            try
            {
                connection.close();
            }
            catch  (@NotNull final SQLException sqlException)
            {
                throw new CannotCloseJdbcConnectionException(sqlException);
            }
        }
    }

    /**
     * Removes the JDBC connection in given attribute map.
     * @param parameters the parameter map.
     * @throws QueryJBuildException if the connection cannot be removed for
     * any reason.
     */
    protected void removeConnection(@NotNull final Map<String, Connection> parameters)
        throws  QueryJBuildException
    {
        parameters.remove(JdbcConnectionOpeningHandler.JDBC_CONNECTION);
    }
}