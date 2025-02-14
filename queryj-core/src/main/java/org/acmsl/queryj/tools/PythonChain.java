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
 * Filename: PythonChain.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the chain when QueryJ extracts information from Python sources.
 *
 */
package org.acmsl.queryj.tools;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.AbstractQueryJChain;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.api.exceptions.CannotFindTemplatesException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.handlers.TemplateHandler;
import org.acmsl.queryj.tools.handlers.Log4JInitializerHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Chain;
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some JDK classes.
 */
import java.util.List;
import java.util.ServiceLoader;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Defines the chain when QueryJ extracts information from Python sources.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @param <CH> the command handler class.
 */
@ThreadSafe
public class PythonChain<CH extends QueryJCommandHandler<QueryJCommand>>
    extends AbstractQueryJChain<QueryJCommand, CH>
    implements QueryJSettings
{
    /**
     * Creates a {@link PythonChain} with given information.
     */
    public PythonChain()
    {
    }

    /**
     * Adds the pre-processing handlers.
     * @param chain the chain to build.
     * @throws QueryJBuildException if building the chain fails.
     */
    @SuppressWarnings("unchecked")
    protected void addPreProcessHandlers(
        @NotNull final Chain<QueryJCommand, QueryJBuildException, CH> chain)
        throws QueryJBuildException
    {
        chain.add((CH) new Log4JInitializerHandler());

        chain.add((CH) new ParameterValidationHandler());

    }

    /**
     * Adds the post-processing handlers.
     * @param chain the chain to build.
     * @throws QueryJBuildException if building the chain fails.
     */
    @SuppressWarnings("unchecked")
    protected void addPostProcessHandlers(
        @NotNull final Chain<QueryJCommand, QueryJBuildException, CH> chain)
        throws QueryJBuildException
    {
    }

    /**
     * Builds the chain.
     * @param chain the chain to be configured.
     * @return the updated chain.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    @Override
    protected Chain<QueryJCommand, QueryJBuildException, CH> buildChain(
        @NotNull final Chain<QueryJCommand, QueryJBuildException, CH> chain)
       throws QueryJBuildException
    {
        addPreProcessHandlers(chain);

        fillTemplateHandlers(chain);

        addPostProcessHandlers(chain);

        return chain;
    }

    /**
     * Performs any clean up whenever an error occurs.
     * @param buildException the error that triggers this clean-up.
     * @param command the command.
     */
    @Override
    protected void cleanUpOnError(
        @NotNull final QueryJBuildException buildException, @NotNull final QueryJCommand command)
    {
        @Nullable final Log t_Log = UniqueLogFactory.getLog(PythonChain.class);

        if  (t_Log != null)
        {
             t_Log.info("Performing clean up");
        }
    }
}
