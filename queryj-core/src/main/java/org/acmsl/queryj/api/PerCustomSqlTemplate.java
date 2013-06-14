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
 * Filename: BasePerCustomSqlTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base logic for all per-FK templates.
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Base logic for all templates to be processed once per custom SQL.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface PerCustomSqlTemplate<C extends PerCustomSqlTemplateContext>
    extends Template<C>
{
    /**
     * Builds the correct chain.
     *
     * @param context the context.
     * @param relevantOnly whether to include only relevant placeholders.
     * @return the specific {@link AbstractFillTemplateChain}.
     */
    @SuppressWarnings("unused")
    @NotNull
    public FillTemplateChain<C> buildFillTemplateChain(
        @NotNull final C context, final boolean relevantOnly);
}
