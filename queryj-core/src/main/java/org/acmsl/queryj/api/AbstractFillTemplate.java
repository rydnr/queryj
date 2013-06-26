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
 * Filename: AbstractFillTemplate.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Tunes the template generation process to use a
 *              chain-of-responsibility approach.
 *
 * Date: 6/3/12
 * Time: 11:37 AM
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.api.exceptions.InvalidTemplateException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.QueryJCommand;

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Tunes the template generation process to use a chain-of-responsibility approach.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @param <C>
 * @since 2012/06/03
 */
@SuppressWarnings("unused")
public abstract class AbstractFillTemplate<C extends TemplateContext, CH extends QueryJCommandHandler<QueryJCommand>>
    extends AbstractTemplate<C>
{
    /**
     * Creates an {@link AbstractFillTemplate instance} using given context.
     * @param context the context.
     */
    public AbstractFillTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Generates the actual source code, using a chain-of-responsibility approach.
     * @param header  the header.
     * @param context the {@link TemplateContext} instance.
     * @param relevantOnly whether to include only relevant placeholders.
     * @return such output.
     * @throws org.acmsl.queryj.api.exceptions.InvalidTemplateException if the template cannot be generated.
     */
    @NotNull
    @Override
    protected String generateOutput(
        @Nullable final String header, @NotNull final C context, final boolean relevantOnly)
        throws InvalidTemplateException
    {
        final String result = "";

        try
        {
            createChain().process();
        }
        catch (@NotNull final QueryJBuildException processFailed)
        {
            //
        }

        return "";
    }

    /**
     * Creates the correct chain according to the {@link TemplateContext context type}.
     * @return such chain.
     */
    @NotNull
    protected abstract AbstractFillTemplateChain<C, CH> createChain();
}