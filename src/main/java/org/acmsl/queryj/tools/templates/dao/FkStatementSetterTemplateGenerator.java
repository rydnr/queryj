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
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.templates.AbstractTemplateGenerator;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.tools.templates.BasePerForeignKeyTemplateContext;
import org.acmsl.queryj.tools.templates.BasePerForeignKeyTemplateFactory;
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Locale;

/**
 * Is able to generate FkStatementSetter templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class FkStatementSetterTemplateGenerator
    extends AbstractTemplateGenerator<FkStatementSetterTemplate, BasePerForeignKeyTemplateContext>
    implements BasePerForeignKeyTemplateFactory<FkStatementSetterTemplate>,
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
     * {@inheritDoc}
     */
    @NotNull
    public FkStatementSetterTemplate createTemplate(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String packageName,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        @NotNull final ForeignKey foreignKey)
    {
        return
            new FkStatementSetterTemplate(
                new BasePerForeignKeyTemplateContext(
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
                    foreignKey));
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
    @Override
    @NotNull
    public String retrieveTemplateFileName(@NotNull final BasePerForeignKeyTemplateContext context)
    {
        return
            retrieveTemplateFileName(
                context.getForeignKey(),
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
