/*
                        QueryJ Placeholders

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
 * Filename: BasePerCustomResultFillTemplateChain.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Sets up the chain required to provide placeholder replacements
 *              for per-custom-result templates.
 *
 * Date: 6/17/12
 * Time: 12:38 PM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 *Importing QueryJ Core classes.
*/
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.handlers.FillAdapterHandler;
import org.acmsl.queryj.api.handlers.fillhandlers.FillHandler;
import org.acmsl.queryj.api.AbstractFillTemplateChain;
import org.acmsl.queryj.api.PerCustomResultTemplateContext;
import org.acmsl.queryj.api.handlers.TemplateContextFillAdapterHandler;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Sets up the chain required to provide placeholder replacements for
 * {@link org.acmsl.queryj.api.PerCustomResultTemplate per-custom-result templates}.
 * @param <C> the template context.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2012/06/17
 */
@ThreadSafe
@SuppressWarnings("unused")
public class BasePerCustomResultFillTemplateChain<C extends PerCustomResultTemplateContext>
    extends AbstractFillTemplateChain<C>
{
    /**
     * Creates a {@code BasePerCustomResultFillTemplateChain} using given context.
     * @param context the {@link org.acmsl.queryj.api.PerCustomResultTemplateContext context}.
     */
    public BasePerCustomResultFillTemplateChain(@NotNull final C context)
    {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public QueryJCommand providePlaceholders(final boolean relevantOnly)
        throws QueryJBuildException
    {
        return new FillTemplateChainWrapper<>(this).providePlaceholders(relevantOnly);
    }

    /**
     * Retrieves the additional per-custom-result handlers.
     * @param context the {@link PerCustomResultTemplateContext context}.
     * @return such handlers.
     */
    @NotNull
    @Override
    protected List<FillHandler<?>> getHandlers(@NotNull final C context)
    {
        @NotNull final List<FillHandler<?>> result = new ArrayList<>(3);

        result.add(
            (FillAdapterHandler)
                new TemplateContextFillAdapterHandler<>(
                    new CustomResultHandler(context)));

        result.add(
            (FillAdapterHandler)
                new TemplateContextFillAdapterHandler<>(
                    new CustomResultTypeImportsHandler(context)));

        result.add(
            (FillAdapterHandler)
                new TemplateContextFillAdapterHandler<>(
                    new ResultIdHandler(context)));

        return result;
    }
}
