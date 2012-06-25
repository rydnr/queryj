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

 *****************************************************************************
 *
 * Filename: BasePerCustomResultTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base logic for all per-FK templates.
 */
package org.acmsl.queryj.templates;

/*
 * Importing some project-specific classes.
 */

/*
 * Importing some ACM-SL classes.
 */

/*
 * Importing StringTemplate classes.
 */
import org.acmsl.queryj.templates.handlers.fillhandlers.BasePerCustomResultFillTemplateChain;
import org.antlr.stringtemplate.StringTemplate;

/*
 * Importing some JDK classes.
 */

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Base logic for all templates to be processed once per custom result.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerCustomResultTemplate<C extends BasePerCustomResultTemplateContext>
    extends  AbstractBasePerCustomResultTemplate<C>
{
    /**
     * Builds a <code>BasePerCustomResultTemplate</code> using given
     * information.
     * @param context the {@link BasePerCustomResultTemplateContext} instance.
     */
    public BasePerCustomResultTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Builds the correct chain.
     *
     * @param context the context.
     * @return the specific {@link FillTemplateChain}.
     */
    @NotNull
    @Override
    protected FillTemplateChain buildFillTemplateChain(@NotNull final C context)
    {
        return new BasePerCustomResultFillTemplateChain(context);
    }

    /**
     * Builds a context-specific exception.
     *
     * @param context  the context.
     * @param template the {@link org.antlr.stringtemplate.StringTemplate} instance.
     * @return the specific {@link org.acmsl.queryj.templates.InvalidTemplateException} for the template.
     */
    @NotNull
    @Override
    protected InvalidTemplateException buildInvalidTemplateException(
        @NotNull final C context,
        @NotNull final StringTemplate template,
        @NotNull final Throwable actualException)
    {
        return
            new InvalidTemplateException(
                "invalid.per.custom.result.template",
                new Object[]
                {
                    template.getName(),
                    getTemplateName(),
                    context.getResult().getId()
                },
                actualException);
    }
}
