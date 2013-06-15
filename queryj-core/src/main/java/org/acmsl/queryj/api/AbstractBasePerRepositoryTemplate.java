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
 * Filename: AbstractBasePerRepositoryTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Logic-less container for all templates to be processed once
 *              per repository.
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing StringTemplate classes.
 */
import org.acmsl.queryj.api.exceptions.InvalidPerRepositoryTemplateException;
import org.acmsl.queryj.api.exceptions.InvalidTemplateException;
import org.antlr.stringtemplate.StringTemplate;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Logic-less container for all templates to be processed once per repository.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractBasePerRepositoryTemplate<C extends PerRepositoryTemplateContext>
    extends AbstractTemplate<C>
    implements PerRepositoryTemplate<C>
{
    /**
     * Builds a <code>PerRepositoryTemplate</code> using given
     * information.
     * @param context the {@link PerRepositoryTemplateContext} instance.
     */
    public AbstractBasePerRepositoryTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Builds a context-specific exception.
     * @param context the context.
     * @param template the {@link StringTemplate} instance.
     * @return the specific {@link org.acmsl.queryj.api.exceptions.InvalidTemplateException} for the template.
     */
    @Override
    @NotNull
    public InvalidTemplateException buildInvalidTemplateException(
        @NotNull final C context,
        @NotNull final StringTemplate template,
        @NotNull final Throwable actualException)
    {
        return
            new InvalidPerRepositoryTemplateException(
                getTemplateName(),
                context.getRepositoryName(),
                actualException);
    }
}
