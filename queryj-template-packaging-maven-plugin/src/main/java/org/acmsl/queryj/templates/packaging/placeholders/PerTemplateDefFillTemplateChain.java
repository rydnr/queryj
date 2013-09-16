/*
                        QueryJ Template Packaging

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
 * Filename: TemplatePackagingFillTemplateChain.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Sets up the chain required to provide placeholder replacements
 *              for Template-Packaging templates.
 *
 * Date: 2013/08/29
 * Time: 07:00
 *
 */
package org.acmsl.queryj.templates.packaging.placeholders;

/*
 * Importing ACM-SL Java Commons classes.
 */
import org.acmsl.commons.patterns.Chain;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.AbstractFillTemplateChain;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.handlers.fillhandlers.FillHandler;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.DefaultTemplatePackagingContext;
import org.acmsl.queryj.templates.packaging.PerTemplateDefFillTemplateChainWrapper;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Sets up the chain required to provide placeholder replacements for Template-Packaging
 * per-{@link org.acmsl.queryj.templates.packaging.TemplateDef} templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/29 07:00
 */
@ThreadSafe
public class PerTemplateDefFillTemplateChain
    extends AbstractFillTemplateChain<DefaultTemplatePackagingContext>
{
    /**
     * Creates an instance using given context.
     * @param context the {@link DefaultTemplatePackagingContext context}.
     */
    public PerTemplateDefFillTemplateChain(@NotNull final DefaultTemplatePackagingContext context)
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
        return
            new PerTemplateDefFillTemplateChainWrapper(this)
                .providePlaceholders(relevantOnly);
    }

    /**
     * Adds additional per-table handlers.
     * @param chain the chain to be configured.
     * @param context the {@link DefaultTemplatePackagingContext context}.
     * @param relevantOnly whether to use only relevant placeholders.
     */
    @Override
    protected void addHandlers(
        @NotNull final Chain<FillHandler<?>> chain,
        @NotNull final DefaultTemplatePackagingContext context,
        final boolean relevantOnly)
    {
    }
}
