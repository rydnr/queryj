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
 * Filename: TemplateFillChainHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Sets up a chain to resolve template placeholders.
 *
 * Date: 6/3/12
 * Time: 11:45 AM
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing project classes.
 */
import org.acmsl.commons.patterns.Chain;
import org.acmsl.queryj.AbstractQueryJChain;
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.QueryJCommand;

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Sets up a chain to resolve template placeholders.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/03
 */
@SuppressWarnings("unused")
public abstract class TemplateFillChain<C extends QueryJTemplateContext>
    extends AbstractQueryJChain<QueryJCommand, QueryJCommandHandler<QueryJCommand>>
{
    /**
     * The template context.
     */
    @NotNull private C templateContext;

    /**
     * Creates a {@link TemplateFillChain} with given context.
     * @param context the {@link QueryJTemplateContext context}.
     */
    @SuppressWarnings("unused")
    public TemplateFillChain(@NotNull final C context)
    {
        immutableSetTemplateContext(context);
    }

    /**
     * Specifies the context.
     * @param context such {@link QueryJTemplateContext context}.
     */
    protected final void immutableSetTemplateContext(@NotNull final C context)
    {
        this.templateContext = context;
    }

    /**
     * Specifies the context.
     * @param context such template context.
     */
    @SuppressWarnings("unused")
    protected void setTemplateContext(@NotNull final C context)
    {
        immutableSetTemplateContext(context);
    }

    /**
     * Retrieves the template context.
     * @return such {@link QueryJTemplateContext context}.
     */
    @NotNull
    public C getTemplateContext()
    {
        return this.templateContext;
    }

    /**
     * Builds the chain.
     *
     * @param chain the chain to be configured.
     * @return the updated chain.
     */
    @Override
    @NotNull
    protected Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> buildChain(
        @NotNull final Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> chain)
    {
        @NotNull final Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> result = chain;

        fillChain(result, getTemplateContext());

        return result;
    }

    /**
     * Builds the command.
     * @param configuration the configuration.
     * @param log the log.
     * @return the initialized command.
     */
    @NotNull
    protected QueryJCommand process(@NotNull final Configuration configuration, @Nullable final Log log)
    {
        @NotNull final QueryJCommand result = new ConfigurationQueryJCommandImpl(configuration, log);

        new QueryJCommandWrapper<QueryJTemplateContext>(result).setSetting("context", getTemplateContext());

        return result;
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
    }

    /**
     * Fills up given {@link Chain chain}.
     * @param chain the chain to customize.
     * @param context the {@link QueryJTemplateContext context}.
     */
    protected abstract void fillChain(
        @NotNull final Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> chain,
        @NotNull final C context);

    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + TemplateFillChain.class.getName() + '"'
            + ", \"templateContext\": " + templateContext + " }";
    }
}
