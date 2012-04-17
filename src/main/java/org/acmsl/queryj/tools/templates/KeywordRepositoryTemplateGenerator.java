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
 * Filename: KeywordRepositoryTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate keyword repository templates according to
 *              keyword definition.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.DecorationUtils;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Is able to generate keyword repositories template according to
 * keyword definition.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class KeywordRepositoryTemplateGenerator<T extends KeywordRepositoryTemplate>
    extends AbstractTemplateGenerator<T>
    implements  KeywordRepositoryTemplateFactory,
                BasePerRepositoryTemplateGenerator<T>,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class KeywordRepositoryTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final KeywordRepositoryTemplateGenerator SINGLETON =
            new KeywordRepositoryTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected KeywordRepositoryTemplateGenerator() {}

    /**
     * Retrieves a {@link KeywordRepositoryTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static KeywordRepositoryTemplateGenerator getInstance()
    {
        return KeywordRepositoryTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a <i>per-repository</i> template.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the database metadata type manager.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the name of the repository.
     * @param engineName the engine name.
     * @param header the header.
     * @return a template.
     */
    @NotNull
    public BasePerRepositoryTemplate createTemplate(
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String engineName,
        final String header)
    {
        return
            new KeywordRepositoryTemplate(
                metadataManager,
                metadataTypeManager,
                customSqlProvider,
                header,
                getDecoratorFactory(),
                packageName,
                basePackageName,
                repositoryName,
                engineName);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String retrieveTemplateFileName(@NotNull final T template) {
        return retrieveTemplateFileName(template, DecorationUtils.getInstance());
    }

    /**
     * Retrieves given template's file name.
     *
     * @param template the template.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return such name.
     */
    @NotNull
    protected String retrieveTemplateFileName(@NotNull final T template, @NotNull final DecorationUtils decorationUtils)
    {
        return
            decorationUtils.capitalize(
                template.getRepositoryName())
            + "KeywordRepository.java";
    }
}
