/*
                        QueryJ Debugging

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
 * Filename: QueryJDebuggingChain.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: QueryJ flow when debugging templates.
 *
 * Date: 2014/08/20
 * Time: 10:48
 *
 */
package org.acmsl.queryj.debugging;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.commons.patterns.Chain;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.TemplateContext;
import org.acmsl.queryj.api.exceptions.CannotFindTemplatesException;
import org.acmsl.queryj.api.exceptions.DevelopmentModeException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.handlers.TemplateHandler;
import org.acmsl.queryj.customsql.handlers.CustomSqlCacheWritingHandler;
import org.acmsl.queryj.customsql.handlers.CustomSqlProviderRetrievalHandler;
import org.acmsl.queryj.customsql.handlers.CustomSqlValidationHandler;
import org.acmsl.queryj.tools.QueryJChain;
import org.acmsl.queryj.tools.TemplateChainProvider;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataCacheWritingHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataLoggingHandler;
import org.acmsl.queryj.tools.handlers.JdbcConnectionClosingHandler;
import org.acmsl.queryj.tools.handlers.JdbcConnectionOpeningHandler;
import org.acmsl.queryj.tools.handlers.JdbcMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.Log4JInitializerHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.mysql.MySQL4xMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.oracle.OracleMetaDataRetrievalHandler;
import org.apache.commons.logging.Log;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.ServiceLoader;

/**
 * QueryJ flow when debugging templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/08/20 10:48
 * @param <CH> the QueryJCommandHandler.
 * @param <C> the template context.
 */
@ThreadSafe
public class QueryJDebuggingChain<CH extends QueryJCommandHandler<QueryJCommand>, C extends TemplateContext>
    extends QueryJChain<CH>
{
    /**
     * The template debugging service.
     */
    private TemplateDebuggingService<C> m__Service;

    /**
     * Creates a {@code QueryJDebuggingChain} with given information.
     */
    public QueryJDebuggingChain(@NotNull final TemplateDebuggingService<C> service)
    {
        immutableSetService(service);
    }

    /**
     * Specifies the template debugging service.
     * @param service such {@link TemplateDebuggingService service}.
     */
    protected final void immutableSetService(@NotNull final TemplateDebuggingService<C> service)
    {
        this.m__Service = service;
    }

    /**
     * Specifies the template debugging service.
     * @param service such {@link TemplateDebuggingService service}.
     */
    @SuppressWarnings("unused")
    protected void setService(@NotNull final TemplateDebuggingService<C> service)
    {
        immutableSetService(service);
    }

    /**
     * Retrieves the template debugging service.
     * @return the {@link TemplateDebuggingService service}.
     */
    @NotNull
    public TemplateDebuggingService<C> getService()
    {
        return this.m__Service;
    }

    /**
     * Sends given command to a concrete chain.
     * @param chain the concrete chain.
     * @param command the command that represents which actions should be done.
     * @return <code>true</code> if the command is processed by the chain.
     * @throws QueryJBuildException if the process fails.
     */
    @Override
    protected boolean process(
        @NotNull final Chain<QueryJCommand, QueryJBuildException, CH> chain, @NotNull final QueryJCommand command)
        throws QueryJBuildException
    {
        return process(chain, command, getService());
    }

    /**
     * Sends given command to a concrete chain.
     * @param chain the concrete chain.
     * @param command the command that represents which actions should be done.
     * @param service the {@link org.acmsl.queryj.debugging.TemplateDebuggingService service}.
     * @return <code>true</code> if the command is processed by the chain.
     * @throws QueryJBuildException if the process fails.
     */
    protected boolean process(
        @NotNull final Chain<QueryJCommand, QueryJBuildException, CH> chain,
        @NotNull final QueryJCommand command,
        @NotNull final TemplateDebuggingService<C> service)
        throws QueryJBuildException
    {
        boolean result = false;

        @Nullable final Log t_Log = command.getLog();

        final boolean t_bLoggingEnabled = (t_Log != null);

        @NotNull TemplateDebuggingCommand t_DebugCommand = TemplateDebuggingCommand.NEXT;

        try
        {
            @Nullable CH t_CurrentCommandHandler = null;

            do
            {
                if (t_DebugCommand.equals(TemplateDebuggingCommand.NEXT))
                {
                    t_CurrentCommandHandler =
                        getNextChainLink(chain, t_CurrentCommandHandler);
                }
                else if (t_DebugCommand.equals(TemplateDebuggingCommand.PREVIOUS))
                {
                    t_CurrentCommandHandler =
                        getPreviousChainLink(chain, t_CurrentCommandHandler);
                }

                if (t_bLoggingEnabled)
                {
                    t_Log.debug("Next handler: " + t_CurrentCommandHandler);
                }

                if  (t_CurrentCommandHandler != null)
                {
                     service.debug(t_CurrentCommandHandler);
                }

                if (t_bLoggingEnabled)
                {
                    t_Log.debug(
                        t_CurrentCommandHandler + "#handle(QueryJCommand) returned "
                        + result);
                }
            }
            while  (t_CurrentCommandHandler != null);
        }
        catch  (@NotNull final QueryJBuildException buildException)
        {
            cleanUpOnError(buildException, command);

            if (t_bLoggingEnabled)
            {
                t_Log.error(
                    "QueryJ could not generate sources correctly.",
                    buildException);
            }

            throw buildException;
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
              "{ \"service\": " + this.m__Service
            + ", \"class\": \"QueryJDebuggingChain\""
            + ", \"package\": \"org.acmsl.queryj.debugging\" }";
    }
}
