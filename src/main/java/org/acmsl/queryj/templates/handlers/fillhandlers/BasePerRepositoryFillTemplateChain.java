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
 * Filename: BasePerRepositoryFillTemplateChain.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Sets up the chain required to provide placeholder replacements
 *              for per-repository templates.
 *
 * Date: 6/3/18
 * Time: 12:38 PM
 *
 */
package org.acmsl.queryj.templates.handlers.fillhandlers;

/*
 *Importing project classes.
*/
import org.acmsl.queryj.metadata.TableDecorator;
import org.acmsl.queryj.templates.BasePerRepositoryTemplateContext;
import org.acmsl.queryj.templates.FillTemplateChain;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Chain;

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.templates.handlers.TemplateContextFillAdapterHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Sets up the chain required to provide placeholder replacements for
 * {@link org.acmsl.queryj.templates.BasePerTableTemplate per-table templates}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/18
 */
public class BasePerRepositoryFillTemplateChain
    extends FillTemplateChain<BasePerRepositoryTemplateContext>
{
    /**
     * Creates a {@link BasePerRepositoryFillTemplateChain} using given context.
     * @param context the {@link BasePerRepositoryTemplateContext context}.
     */
    public BasePerRepositoryFillTemplateChain(@NotNull final BasePerRepositoryTemplateContext context)
    {
        super(context);
    }

    /**
     * Adds additional per-repository handlers.
     * @param chain the chain to be configured.
     * @param context the {@link BasePerRepositoryTemplateContext context}.
     */
    @Override
    protected void addHandlers(@NotNull final Chain chain, @NotNull final BasePerRepositoryTemplateContext context)
    {
        chain.add(
            new TemplateContextFillAdapterHandler<BasePerRepositoryTemplateContext,TableListHandler,List<TableDecorator>>(
                new TableListHandler(context)));
    }
}