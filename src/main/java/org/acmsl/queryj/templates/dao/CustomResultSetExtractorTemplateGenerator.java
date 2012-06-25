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
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.templates.BasePerCustomResultTemplateContext;
import org.acmsl.queryj.templates.BasePerCustomResultTemplateFactory;
import org.acmsl.queryj.templates.AbstractTemplateGenerator;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Is able to generate custom ResultSetExtractor templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class CustomResultSetExtractorTemplateGenerator
    extends  AbstractTemplateGenerator<CustomResultSetExtractorTemplate, BasePerCustomResultTemplateContext>
    implements  BasePerCustomResultTemplateFactory<CustomResultSetExtractorTemplate>,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class CustomResultSetExtractorTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final CustomResultSetExtractorTemplateGenerator SINGLETON =
            new CustomResultSetExtractorTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected CustomResultSetExtractorTemplateGenerator() {}

    /**
     * Retrieves a {@link CustomResultSetExtractorTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static CustomResultSetExtractorTemplateGenerator getInstance()
    {
        return
            CustomResultSetExtractorTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public CustomResultSetExtractorTemplate createTemplate(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final String packageName,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        @NotNull final Result customResult)
    {
        return
            new CustomResultSetExtractorTemplate(
                new BasePerCustomResultTemplateContext(
                    metadataManager,
                    customSqlProvider,
                    header,
                    getDecoratorFactory(),
                    packageName,
                    basePackageName,
                    repositoryName,
                    implementMarkerInterfaces,
                    jmx,
                    jndiLocation,
                    customResult));
    }

    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    @NotNull
    public DecoratorFactory getDecoratorFactory()
    {
        return CustomResultSetExtractorDecoratorFactory.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String retrieveTemplateFileName(@NotNull final BasePerCustomResultTemplateContext context)
    {
        return retrieveTemplateFileName(context, StringUtils.getInstance());
    }

    /**
     * Retrieves the file name for given template.
     * @param context the {@link BasePerCustomResultTemplateContext} context.
     * @param stringUtils the {@link StringUtils} instance.
     * @return the file name.
     */
    protected String retrieveTemplateFileName(
        @NotNull final BasePerCustomResultTemplateContext context,
        @NotNull final StringUtils stringUtils)
    {
        return
            stringUtils.capitalize(
                stringUtils.capitalize(
                    stringUtils.capitalize(
                        context.getResult().getId(),
                        '.'),
                    '_'),
                '-')
            + "Extractor.java";
    }
}
