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
 * Filename: CustomResultSetExtractorTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate custom ResultSetExtractor templates.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.api.PerCustomResultTemplateContext;
import org.acmsl.queryj.api.AbstractTemplateGenerator;
import org.acmsl.queryj.api.PerCustomResultTemplateGenerator;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/**
 * Is able to generate custom ResultSetExtractor templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CustomResultSetExtractorTemplateGenerator
    extends  AbstractTemplateGenerator<CustomResultSetExtractorTemplate, PerCustomResultTemplateContext>
    implements PerCustomResultTemplateGenerator<CustomResultSetExtractorTemplate, PerCustomResultTemplateContext>
{
    /**
     * Creates a new {@link CustomResultSetExtractorTemplateGenerator} with given settings.
     * @param caching whether to enable caching.
     * @param threadCount the number of threads.
     */
    public CustomResultSetExtractorTemplateGenerator(final boolean caching, final int threadCount)
    {
        super(caching, threadCount);
    }

    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    @NotNull
    @Override
    public DecoratorFactory getDecoratorFactory()
    {
        return CustomResultSetExtractorDecoratorFactory.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String retrieveTemplateFileName(@NotNull final PerCustomResultTemplateContext context)
    {
        return retrieveTemplateFileName(context, StringUtils.getInstance());
    }

    /**
     * Retrieves the file name for given template.
     * @param context the {@link org.acmsl.queryj.api.PerCustomResultTemplateContext} context.
     * @param stringUtils the {@link StringUtils} instance.
     * @return the file name.
     */
    @NotNull
    protected String retrieveTemplateFileName(
        @NotNull final PerCustomResultTemplateContext context,
        @Nullable final StringUtils stringUtils)
    {
        @NotNull final String result;

        if (stringUtils != null)
        {
            result =
                stringUtils.capitalize(
                    stringUtils.capitalize(
                        stringUtils.capitalize(
                            context.getResult().getId(),
                            '.'),
                        '_'),
                    '-')
                + "Extractor.java";
        }
        else
        {
            result = "";
        }

        return result;
    }
}
