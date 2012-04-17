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
 * Filename: FkStatementSetterTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate FkStatementSetter templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.templates.AbstractTemplateGenerator;
import org.acmsl.queryj.tools.templates.BasePerForeignKeyTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Locale;

/**
 * Is able to generate FkStatementSetter templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class FkStatementSetterTemplateGenerator<T extends FkStatementSetterTemplate>
    extends AbstractTemplateGenerator<T>
    implements  FkStatementSetterTemplateFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class FkStatementSetterTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final FkStatementSetterTemplateGenerator SINGLETON =
            new FkStatementSetterTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected FkStatementSetterTemplateGenerator() {}

    /**
     * Retrieves a FkStatementSetterTemplateGenerator instance.
     * @return such instance.
     */
    @NotNull
    public static FkStatementSetterTemplateGenerator getInstance()
    {
        return FkStatementSetterTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Creates a FkStatementSetter template.
     * @param foreignKey the foreign key.
     * @param metadataManager the database metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param header the header.
     * @return a template.
     * @precondition foreignKey != null
     * @precondition metadataManager != null
     * @precondition packageName != null
     * @precondition repositoryName != null
     */
    @NotNull
    public BasePerForeignKeyTemplate createTemplate(
        final ForeignKey foreignKey,
        final MetadataManager metadataManager,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName,
        final String header)
    {
        return
            new FkStatementSetterTemplate(
                foreignKey,
                metadataManager,
                header,
                getDecoratorFactory(),
                packageName,
                engineName,
                engineVersion,
                quote,
                basePackageName,
                repositoryName);
    }

    /**
     * Retrieves the decorator factory for each template.
     * @return such instance.
     */
    @NotNull
    public DecoratorFactory getDecoratorFactory()
    {
        return FkStatementSetterDecoratorFactory.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String retrieveTemplateFileName(@NotNull final T template)
    {
        return
            retrieveTemplateFileName(
                template.getForeignKey(),
                StringUtils.getInstance(),
                EnglishGrammarUtils.getInstance());
    }

    /**
     * Retrieves given template's file name.
     * @param foreignKey the {@link ForeignKey} instance.
     * @param stringUtils the {@link StringUtils} instance.
     * @param englishGrammarUtils the {@link EnglishGrammarUtils} instance.
     * @return such name.
     */
    @NotNull
    protected String retrieveTemplateFileName(
        @NotNull final ForeignKey foreignKey,
        @NotNull final StringUtils stringUtils,
        @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        return
            retrieveTemplateFileName(
                foreignKey.getSourceTableName(),
                foreignKey.getTargetTableName(),
                stringUtils,
                englishGrammarUtils);
    }

    /**
     * Retrieves given template's file name.
     *
     * @param sourceTable the source table.
     * @param targetTable the target table.
     * @param stringUtils the {@link org.acmsl.commons.utils.StringUtils} instance.
     * @param englishGrammarUtils the {@link org.acmsl.commons.utils.EnglishGrammarUtils} instance.
     * @return such name.
     */
    @NotNull
    protected String retrieveTemplateFileName(
        @NotNull final String sourceTable,
        @NotNull final String targetTable,
        @NotNull final StringUtils stringUtils,
        @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        return
              stringUtils.capitalize(
                  englishGrammarUtils.getSingular(
                      sourceTable.toLowerCase(Locale.US)),
                  '_')
            + "By"
            + stringUtils.capitalize(
                  englishGrammarUtils.getSingular(
                      targetTable.toLowerCase(Locale.US)),
                  '_')
            + "StatementSetter.java";
    }
}
