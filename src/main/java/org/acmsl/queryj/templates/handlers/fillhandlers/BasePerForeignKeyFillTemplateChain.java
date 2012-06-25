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
 * Filename: BasePerForeignKeyFillTemplateChain.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Sets up the chain required to provide placeholder replacements
 *              for per-foreign-key templates.
 *
 * Date: 6/3/12
 * Time: 12:38 PM
 *
 */
package org.acmsl.queryj.templates.handlers.fillhandlers;

/*
 *Importing project classes.
*/
import org.acmsl.queryj.templates.BasePerForeignKeyTemplateContext;
import org.acmsl.queryj.templates.FillTemplateChain;

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
 * {@link org.acmsl.queryj.templates.BasePerForeignKeyTemplate per-foreign-key templates}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/18
 */
public class BasePerForeignKeyFillTemplateChain
    extends FillTemplateChain<BasePerForeignKeyTemplateContext>
{
    /**
     * Creates a {@link BasePerForeignKeyFillTemplateChain} using given context.
     * @param context the {@link BasePerForeignKeyTemplateContext context}.
     */
    public BasePerForeignKeyFillTemplateChain(@NotNull final BasePerForeignKeyTemplateContext context)
    {
        super(context);
    }

    /**
     * Adds additional per-foreign-key handlers.
     * @param chain the chain to be configured.
     * @param context the {@link BasePerForeignKeyTemplateContext context}.
     */
    @Override
    protected void addHandlers(@NotNull final Chain chain, @NotNull final BasePerForeignKeyTemplateContext context)
    {
        // TODO
        int a = 0;

        a++;
    }
}
