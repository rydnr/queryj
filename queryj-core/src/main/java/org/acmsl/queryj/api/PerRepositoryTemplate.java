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
 * Filename: BasePerRepositoryTemplate.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Defines the external API for per-repository templates.
 *
 * Date: 6/1/13
 * Time: 7:17 AM
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing StringTemplate classes.
 */
import org.acmsl.queryj.api.exceptions.InvalidTemplateException;
import org.antlr.stringtemplate.StringTemplate;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Defines the external API for per-repository templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/01
 */
public interface PerRepositoryTemplate<C extends PerRepositoryTemplateContext>
    extends Template<C>
{
    /**
     * Builds the correct chain.
     * @param context the context.
     * @param relevantOnly whether to include relevant-only placeholders.
     * @return the specific {@link FillTemplateChain}.
     */
    @SuppressWarnings("unused")
    @NotNull
    public FillTemplateChain buildFillTemplateChain(
        @NotNull final C context, final boolean relevantOnly);

    /**
     * Builds a context-specific exception.
     * @param context  the context.
     * @param template the {@link StringTemplate} instance.
     * @return the specific {@link org.acmsl.queryj.api.exceptions.InvalidTemplateException} for the template.
     */
    @NotNull
    InvalidTemplateException buildInvalidTemplateException(
        @NotNull C context,
        @NotNull StringTemplate template,
        @NotNull Throwable actualException);

}
