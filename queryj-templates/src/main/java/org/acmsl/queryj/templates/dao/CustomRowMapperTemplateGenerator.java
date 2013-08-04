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
 * Filename: CustomRowMapperTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate custom RowMapper templates.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.metadata.DecorationUtils;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.ResultDecorator;
import org.acmsl.queryj.api.PerCustomResultTemplateContext;
import org.acmsl.queryj.api.AbstractTemplateGenerator;
import org.acmsl.queryj.api.PerCustomResultTemplateGenerator;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to generate custom RowMapperExtractor templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CustomRowMapperTemplateGenerator
    extends  AbstractTemplateGenerator<CustomRowMapperTemplate, PerCustomResultTemplateContext>
    implements PerCustomResultTemplateGenerator<CustomRowMapperTemplate, PerCustomResultTemplateContext>
{
    /**
     * Creates a new {@link CustomRowMapperTemplateGenerator} with given settings.
     * @param caching whether to enable caching.
     * @param threadCount the number of threads.
     */
    public CustomRowMapperTemplateGenerator(final boolean caching, final int threadCount)
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
        return CustomRowMapperDecoratorFactory.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String retrieveTemplateFileName(@NotNull final PerCustomResultTemplateContext context)
    {
        return
            retrieveTemplateFileName(
                context, getDecoratorFactory(), DecorationUtils.getInstance());
    }

    /**
     * Retrieves the file name for given template.
     * @param context the {@link PerCustomResultTemplateContext} context.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return the file name.
     */
    @NotNull
    protected String retrieveTemplateFileName(
        @NotNull final PerCustomResultTemplateContext context,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DecorationUtils decorationUtils)
    {
        @NotNull final ResultDecorator customResult =
            decoratorFactory.createDecorator(
                context.getResult(),
                context.getCustomSqlProvider(),
                context.getMetadataManager());

        @NotNull final String result =
              decorationUtils.standardCapitalize(
                  customResult.getVoName())
            + "RowMapper.java";

        return result;
    }
}
