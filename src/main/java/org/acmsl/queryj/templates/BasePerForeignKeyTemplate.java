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
 * Filename: BasePerForeignKeyTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base logic for all per-FK templates.
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.templates.handlers.fillhandlers.BasePerForeignKeyFillTemplateChain;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplate;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Base logic for all per-fk templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerForeignKeyTemplate<C extends BasePerForeignKeyTemplateContext>
    extends  AbstractBasePerForeignKeyTemplate<C>
{
    /**
     * Builds a <code>BasePerForeignKeyTemplate</code> using given
     * information.
     * @param context the {@link BasePerForeignKeyTemplateContext} instance.
     */
    public BasePerForeignKeyTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Builds a context-specific exception.
     * @param context the context.
     * @param template the {@link StringTemplate} instance.
     * @return the specific {@link InvalidTemplateException} for the template.
     */
    @NotNull
    protected  InvalidTemplateException buildInvalidTemplateException(
        @NotNull final BasePerForeignKeyTemplateContext context,
        @NotNull final StringTemplate template,
        @NotNull final Throwable actualException)
    {
        return
            new InvalidTemplateException(
                "invalid.per.foreign.key.template",
                new Object[]
                {
                    template.getName(),
                    getTemplateName(),
                    context.getForeignKey().getFkName()
                },
                actualException);
    }

    /**
     * Builds the correct chain.
     *
     * @param context the context.
     * @return the specific {@link FillTemplateChain}.
     */
    @NotNull
    @Override
    protected FillTemplateChain buildFillTemplateChain(
        @NotNull final C context)
    {
        return new BasePerForeignKeyFillTemplateChain(context);
    }
}
