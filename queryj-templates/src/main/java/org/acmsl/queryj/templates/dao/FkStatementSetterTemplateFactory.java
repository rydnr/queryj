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
 * Filename: FkStatementSetterTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create FkStatementSetterTemplate instances.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.api.PerForeignKeyTemplateContext;
import org.acmsl.queryj.api.PerForeignKeyTemplateFactory;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

import java.util.Locale;

/**
 * Is able to create {@link FkStatementSetterTemplate} instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 2012/07/09
 */
@ThreadSafe
public class FkStatementSetterTemplateFactory
    implements PerForeignKeyTemplateFactory<FkStatementSetterTemplate>,
               Singleton
{
    /**
     * Singleton implementation to avoid double-locking check.
     */
    protected static final class FkStatementSetterTemplateFactorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final FkStatementSetterTemplateFactory SINGLETON = new FkStatementSetterTemplateFactory();
    }

    /**
     * Retrieves the singleton instance.
     *
     * @return such instance.
     */
    @NotNull
    public static FkStatementSetterTemplateFactory getInstance()
    {
        return FkStatementSetterTemplateFactorySingletonContainer.SINGLETON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public FkStatementSetterTemplate createTemplate(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String packageName,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        @Nullable final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        final boolean disableGenerationTimestamps,
        final boolean disableNotNullAnnotations,
        final boolean disableCheckthreadAnnotations,
        @NotNull final ForeignKey foreignKey)
    {
        return
            new FkStatementSetterTemplate(
                new PerForeignKeyTemplateContext(
                    metadataManager,
                    customSqlProvider,
                    header,
                    decoratorFactory,
                    packageName,
                    basePackageName,
                    repositoryName,
                    implementMarkerInterfaces,
                    jmx,
                    jndiLocation,
                    disableGenerationTimestamps,
                    disableNotNullAnnotations,
                    disableCheckthreadAnnotations,
                    retrieveFileName(foreignKey),
                    foreignKey));
    }

    /**
     * Retrieves given template's file name.
     * @param foreignKey the {@link ForeignKey} instance.
     * @return such name.
     */
    @NotNull
    public String retrieveFileName(@NotNull final ForeignKey foreignKey)
    {
        return
            retrieveFileName(
                foreignKey,
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
    protected String retrieveFileName(
        @NotNull final ForeignKey foreignKey,
        @NotNull final StringUtils stringUtils,
        @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        return
            retrieveFileName(
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
    protected String retrieveFileName(
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
