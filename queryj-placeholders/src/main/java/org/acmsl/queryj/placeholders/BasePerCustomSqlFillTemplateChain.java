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
 *              for per-custom-sql templates.
 *
 * Date: 6/3/12
 * Time: 12:38 PM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 *Importing project classes.
*/
import org.acmsl.queryj.templates.AbstractFillTemplateChain;
import org.acmsl.queryj.templates.BasePerCustomSqlTemplateContext;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Chain;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Sets up the chain required to provide placeholder replacements for
 * {@link org.acmsl.queryj.templates.BasePerCustomSqlTemplate per-custom-sql templates}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/07/07
 */
@ThreadSafe
public class BasePerCustomSqlFillTemplateChain
    extends AbstractFillTemplateChain<BasePerCustomSqlTemplateContext, CustomSqlHandler>
{
    /**
     * Creates a {@link BasePerCustomSqlFillTemplateChain} using given context.
     * @param context the {@link BasePerCustomSqlTemplateContext context}.
     * @param relevantOnly whether to include only relevant placeholders.
     */
    public BasePerCustomSqlFillTemplateChain(
        @NotNull final BasePerCustomSqlTemplateContext context,
        final boolean relevantOnly)
    {
        super(context, relevantOnly);
    }

    /**
     * Adds additional per-custom-sql handlers.
     * @param chain the chain to be configured.
     * @param context the {@link BasePerCustomSqlTemplateContext context}.
     * @param relevantOnly whether to include only relevant placeholders.
     */
    @SuppressWarnings("unused")
    @Override
    protected void addHandlers(
        @NotNull final Chain<CustomSqlHandler> chain,
        @NotNull final BasePerCustomSqlTemplateContext context,
        final boolean relevantOnly)
    {
        // TODO
    }
}
