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
 * Filename: BasePerTableTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base logic for all per-table templates.
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing StringTemplate classes.
 */
import org.acmsl.queryj.api.exceptions.InvalidTemplateException;
import org.antlr.stringtemplate.StringTemplate;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Base logic for all per-table templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface PerTableTemplate<C extends PerTableTemplateContext>
    extends  Template<C>
{
    /**
     * Builds a context-specific exception.
     * @param context the context.
     * @param template the {@link StringTemplate} instance.
     * @return the specific {@link org.acmsl.queryj.api.exceptions.InvalidTemplateException} for the template.
     */
    @SuppressWarnings("unused")
    @NotNull
    public InvalidTemplateException buildInvalidTemplateException(
        @NotNull final C context,
        @NotNull final StringTemplate template,
        @NotNull final Throwable actualException);

    /**
     * Builds the correct chain.
     *
     * @param context the context.
     * @return the specific {@link AbstractFillTemplateChain}.
     */
    @SuppressWarnings("unused")
    @NotNull
    public FillTemplateChain buildFillTemplateChain(
        @NotNull final C context, final boolean relevantOnly);
}
