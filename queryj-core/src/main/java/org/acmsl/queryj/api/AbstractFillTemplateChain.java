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
 * Filename: FillTemplateChain.java
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
 * Importing project classes.
 */
import org.acmsl.queryj.AbstractQueryJChain;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.handlers.FillAdapterHandler;
import org.acmsl.queryj.api.handlers.fillhandlers.FillHandler;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Chain;

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Sets up the chain to provide all placeholder replacements in templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/03
 */
public abstract class AbstractFillTemplateChain<C extends TemplateContext, CH extends QueryJCommandHandler<QueryJCommand>>
    extends AbstractQueryJChain<CH, QueryJCommand>
    implements FillTemplateChain<C>
{
    /**
     * The template context.
     */
    private C templateContext;

    /**
     * Creates a new {@link AbstractFillTemplateChain} associated to given
     * {@link TemplateContext}.
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
     * Builds the command.
     * @param command the command to be initialized.
     * @return the initialized command.
     */
    @NotNull
    @Override
    protected QueryJCommand buildCommand(@NotNull final QueryJCommand command)
    {
        return command;
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
     * {@inheritDoc}
     */
    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, ?> providePlaceholders(final boolean relevantOnly)
        throws QueryJBuildException
    {
        final Map<String, ?> result;

        @NotNull final QueryJCommand t_Command = buildCommand();

        super.process(buildChain(getChain(), relevantOnly), t_Command);

        result = t_Command.getAttributeMap();

        return result;
    }


    /**
     * Adds additional per-table handlers.
     * @param chain the chain to be configured.
     * documentation, etc. can be considered not relevant.
     */
    @Override
    protected Chain<CH> buildChain(@NotNull final Chain<CH> chain)
    {
        return buildChain(chain, false);
    }

    /**
     * Adds additional per-table handlers.
     * @param chain the chain to be configured.
     * @param relevantOnly to include only the relevant ones: the ones that are necessary to
     * be able to find out if two template realizations are equivalent. Usually, generation timestamps,
     * documentation, etc. can be considered not relevant.
     */
    protected Chain<CH> buildChain(@NotNull final Chain<CH> chain, final boolean relevantOnly)
    {
        addHandlers(chain, getTemplateContext(), relevantOnly);

        return chain;
    }

    /**
     * Adds additional per-table handlers.
     * @param chain the chain to be configured.
     */
    protected abstract void addHandlers(
        @NotNull final Chain<CH> chain,
        @NotNull final C context,
        final boolean relevantOnly);

    /**
     * Adds given handler depending on whether it's relevant or not.
     * @param chain the chain.
     * @param handler the handler.
     * @param relevantOnly whether to include only relevant placeholders.
     */
    @SuppressWarnings("unchecked")
    protected <F extends FillHandler<P>, P> void add(
        @NotNull final Chain<F> chain,
        @NotNull final FillAdapterHandler<F, P> handler,
        final boolean relevantOnly)
    {
        final F actualHandler;

        if (   (relevantOnly)
            && (handler.getFillHandler() instanceof NonRelevantFillHandler))
        {
            actualHandler = (F) new EmptyFillAdapterHandler(handler.getFillHandler());
        }
        else
        {
            actualHandler = (F) handler;
        }

        chain.add(actualHandler);
    }

    @NotNull
    @Override
    public String toString()
    {
        return
              "AbstractFillTemplateChain{"
            + " templateContext=" + templateContext
            + " }";
    }
}
