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
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplateFactory;
import org.acmsl.queryj.tools.templates.AbstractTemplateGenerator;

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
    extends  AbstractTemplateGenerator<CustomResultSetExtractorTemplate>
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
     * Generates a CustomResultSetExtractor template.
     * @param customResult the custom result.
     * @param customSqlProvider the custom sql provider.
     * @param metadataManager the database metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param header the header.
     * @return the new template.
     * @precondition resultElement != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition packageName != null
     * @precondition basePackageName != null
     * @precondition repositoryName != null
     */
    @NotNull
    public CustomResultSetExtractorTemplate createTemplate(
        @NotNull final Result customResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final String packageName,
        @NotNull final String engineName,
        @NotNull final String engineVersion,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        @NotNull final String header)
    {
        return
            new CustomResultSetExtractorTemplate(
                customResult,
                customSqlProvider,
                metadataManager,
                header,
                getDecoratorFactory(),
                packageName,
                engineName,
                engineVersion,
                basePackageName,
                repositoryName);
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
    @NotNull
    public String retrieveTemplateFileName(@NotNull final CustomResultSetExtractorTemplate template)
    {
        return retrieveTemplateFileName(template, StringUtils.getInstance());
    }

    /**
     * Retrieves the file name for given template.
     * @param template the template.
     * @param stringUtils the {@link StringUtils} instance.
     * @return the file name.
     */
    protected String retrieveTemplateFileName(
        @NotNull final CustomResultSetExtractorTemplate template,
        @NotNull final StringUtils stringUtils)
    {
        return
            stringUtils.capitalize(
                stringUtils.capitalize(
                    stringUtils.capitalize(
                        template.getResult().getId(),
                        '.'),
                    '_'),
                '-')
            + "Extractor.java";
    }
}
