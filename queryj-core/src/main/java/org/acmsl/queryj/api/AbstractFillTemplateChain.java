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
 * Filename: FillTemplateCommandBuilder.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Sets up the chain to provide all placeholder replacements in templates.
 *
 * Date: 6/3/12
 * Time: 12:34 PM
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.AbstractQueryJChain;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.handlers.FillAdapterHandler;
import org.acmsl.queryj.api.handlers.fillhandlers.FillHandler;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Chain;
import org.acmsl.commons.patterns.CommandHandler;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JDK classes.
 */
import java.util.List;

/**
 * Sets up the chain to provide all placeholder replacements in templates.
 * @param <C> the template context.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2012/06/03
 */
public abstract class AbstractFillTemplateChain<C extends TemplateContext>
    extends AbstractQueryJChain<QueryJCommand, FillHandler<?>>
    implements FillTemplateChain<C>
{
    /**
     * The template context.
     */
    private C templateContext;

    /**
     * Creates a new {@link AbstractFillTemplateChain} associated to given
     * {@link QueryJTemplateContext}.
     * @param context the template.
     */
    protected AbstractFillTemplateChain(@NotNull final C context)
    {
        immutableSetTemplateContext(context);
    }

    /**
     * Specifies the template context.
     * @param context the context.
     */
    protected final void immutableSetTemplateContext(@NotNull final C context)
    {
        this.templateContext = context;
    }

    /**
     * Specifies the template context.
     * @param context the context.
     */
    @SuppressWarnings("unused")
    protected void setTemplateContext(@NotNull final C context)
    {
        immutableSetTemplateContext(context);
    }

    /**
     * Retrieves the template context.
     * @return such information.
     */
    @Override
    @NotNull
    public C getTemplateContext()
    {
        return templateContext;
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
        // nothing required.
    }

    /**
     * Adds additional per-table handlers.
     * @param chain the chain to be configured.
     * documentation, etc. can be considered not relevant.
     */
    @Override
    @NotNull
    protected Chain<QueryJCommand, QueryJBuildException, FillHandler<?>> buildChain(
        @NotNull final Chain<QueryJCommand, QueryJBuildException, FillHandler<?>> chain)
    {
        return buildChain(chain, false);
    }

    /**
     * Adds additional per-table handlers.
     * @param chain the chain to be configured.
     * @param relevantOnly to include only the relevant ones: the ones that are necessary to
     * be able to find out if two template realizations are equivalent. Usually, generation timestamps,
     * documentation, etc. can be considered not relevant.
     * @return the chain.
     */
    @NotNull
    @SuppressWarnings("unchecked")
    protected Chain<QueryJCommand, QueryJBuildException, FillHandler<?>> buildChain(
        @NotNull final Chain<QueryJCommand, QueryJBuildException, FillHandler<?>> chain, final boolean relevantOnly)
    {
        @NotNull final List<FillAdapterHandler> t_lHandlers = (List<FillAdapterHandler>) getHandlers();

        // Don't know how to fix the generics warnings
        for (@NotNull final FillAdapterHandler t_Handler : t_lHandlers)
        {
            add(chain, t_Handler, relevantOnly);
        }

        return chain;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<?> getHandlers()
    {
        @NotNull final List<FillHandler<?>> result = getChain().getHandlers();

        result.addAll(getHandlers(getTemplateContext()));

        return result;
    }

    /**
     * Retrieves the handlers.
     * @param context the context.
     * @return such handlers.
     */
    @NotNull
    protected abstract List<FillHandler<?>> getHandlers(@NotNull final C context);

    /**
     * Adds given handler depending on whether it's relevant or not.
     * @param chain the chain.
     * @param handler the handler.
     * @param relevantOnly whether to include only relevant placeholders.
     */
    @SuppressWarnings("unchecked")
    protected  void add(
        @NotNull final Chain chain,
        @NotNull final FillAdapterHandler handler,
        final boolean relevantOnly)
    {
        final CommandHandler<QueryJCommand, QueryJBuildException> actualHandler;

        if (   (relevantOnly)
            && (handler.getFillHandler() instanceof NonRelevantFillHandler))
        {
            actualHandler = new EmptyFillAdapterHandler(handler.getFillHandler());
        }
        else
        {
            actualHandler = handler;
        }

        chain.add(actualHandler);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + AbstractFillTemplateChain.class.getSimpleName() + "\""
            + ", \"package\": \"org.acmsl.queryj.api\""
            + ", \"templateContext\": \"" + templateContext + "\" }";
    }
}
