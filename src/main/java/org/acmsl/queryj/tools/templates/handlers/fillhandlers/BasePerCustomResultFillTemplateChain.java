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
 * Filename: BasePerTableFillTemplateChain.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Sets up the chain required to provide placeholder replacements
 *              for per-table templates.
 *
 * Date: 6/3/12
 * Time: 12:38 PM
 *
 */
package org.acmsl.queryj.tools.templates.handlers.fillhandlers;

/*
 *Importing project classes.
*/
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplateContext;
import org.acmsl.queryj.tools.templates.FillTemplateChain;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Chain;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Sets up the chain required to provide placeholder replacements for
 * {@link org.acmsl.queryj.tools.templates.BasePerTableTemplate per-table templates}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/03
 */
public class BasePerCustomResultFillTemplateChain
    extends FillTemplateChain<BasePerCustomResultTemplateContext>
{
    /**
     * Creates a {@link org.acmsl.queryj.tools.templates.handlers.fillhandlers.BasePerCustomResultFillTemplateChain} using given context.
     * @param context the {@link org.acmsl.queryj.tools.templates.BasePerTableTemplateContext context}.
     */
    public BasePerCustomResultFillTemplateChain(@NotNull final BasePerCustomResultTemplateContext context)
    {
        super(context);
    }

    /**
     * Adds additional per-table handlers.
     * @param chain the chain to be configured.
     * @param context the {@link org.acmsl.queryj.tools.templates.BasePerTableTemplateContext context}.
     */
    @Override
    protected void addHandlers(@NotNull final Chain chain, @NotNull final BasePerCustomResultTemplateContext context)
    {
    }
}
