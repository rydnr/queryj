//;-*- mode: java -*-
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
 * Filename: AbstractBasePerCustomResultTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Logic-less container for all templates to be processed once
 *              per custom result.
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.api.exceptions.InvalidPerCustomResultTemplateException;
import org.acmsl.queryj.api.exceptions.InvalidTemplateException;
import org.acmsl.queryj.customsql.Result;

/*
 * Importing StringTemplate classes.
 */
import org.stringtemplate.v4.ST;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Logic-less container for all templates to be processed once per custom result.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@SuppressWarnings("unused")
public abstract class AbstractBasePerCustomResultTemplate<C extends PerCustomResultTemplateContext>
    extends AbstractQueryJTemplate<C>
    implements PerCustomResultTemplate<C>
{
    /**
     * Builds a <code>AbstractBasePerCustomResultTemplate</code> using
     * given information.
     */
    @SuppressWarnings("unused")
    public AbstractBasePerCustomResultTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    @Override
    @NotNull
    protected String buildHeader()
    {
        return buildHeader(getTemplateName(), getTemplateContext());
    }

    /**
     * Builds the header for logging purposes.
     * @param templateName the template name.
     * @param context the {@link PerCustomResultTemplateContext} instance.
     * @return such header.
     */
    @NotNull
    protected String buildHeader(
        @NotNull final String templateName, @NotNull final C context)
    {
        return buildHeader(templateName, context.getResult());
    }

    /**
     * Builds the header for logging purposes.
     * @param templateName the template name.
     * @param result the result.
     * @return such header.
     */
    @NotNull
    protected String buildHeader(
        @NotNull final String templateName, @NotNull final Result<String> result)
    {
        return Literals.GENERATING + templateName + " for " + result.getId();
    }

    /**
     * Builds a context-specific exception.
     * @param context the context.
     * @param template the {@link ST} instance.
     * @return the specific {@link InvalidTemplateException} for the template.
     */
    @Override
    @NotNull
    public InvalidTemplateException buildInvalidTemplateException(
        @NotNull final C context,
        @NotNull final ST template,
        @NotNull final Throwable actualException)
    {
        return
            new InvalidPerCustomResultTemplateException(
                getTemplateName(),
                context.getResult(),
                context.getRepositoryName(),
                actualException);
    }
}
