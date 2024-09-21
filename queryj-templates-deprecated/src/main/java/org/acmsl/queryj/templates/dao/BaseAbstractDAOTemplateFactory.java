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
 * Filename: BaseAbstractDAOTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create BaseAbstractDAOTemplate instances.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.api.PerTableTemplateContext;
import org.acmsl.queryj.api.PerTableTemplateFactory;
import org.acmsl.queryj.api.MetaLanguageUtils;

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
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.List;
import java.util.Locale;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to create {@link BaseAbstractDAOTemplate} instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 2012/07/09
 */
@ThreadSafe
public class BaseAbstractDAOTemplateFactory
    implements PerTableTemplateFactory<BaseAbstractDAOTemplate>,
               Singleton
{
    /**
     * Singleton implementation to avoid double-locking check.
     */
    protected static final class BaseAbstractDAOTemplateFactorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final BaseAbstractDAOTemplateFactory SINGLETON = new BaseAbstractDAOTemplateFactory();
    }

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    public static BaseAbstractDAOTemplateFactory getInstance()
    {
        return BaseAbstractDAOTemplateFactorySingletonContainer.SINGLETON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public BaseAbstractDAOTemplate createTemplate(
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
        @NotNull final String tableName,
        @Nullable final List<Row> staticContents)
    {
        @Nullable BaseAbstractDAOTemplate result = null;

        if  (isStaticTable(tableName, metadataManager))
        {
            result =
                new BaseAbstractDAOTemplate(
                    new PerTableTemplateContext(
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
                        retrieveTemplateFileName(tableName),
                        tableName,
                        staticContents));
        }

        return result;
    }

    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    @NotNull
    public DecoratorFactory getDecoratorFactory()
    {
        // Reusing to avoid copy/paste.
        return BaseDAODecoratorFactory.getInstance();
    }


    /**
     * Checks whether the table contains static values or not.
     * @param tableName the table name.
     * @param metadataManager the {@link org.acmsl.queryj.metadata.MetadataManager} instance.
     * @return such information.
     */
    protected boolean isStaticTable(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        return
            isStaticTable(
                tableName, metadataManager, MetaLanguageUtils.getInstance());
    }

    /**
     * Checks whether the table contains static values or not.
     * @param tableName the table name.
     * @param metadataManager the {@link org.acmsl.queryj.metadata.MetadataManager} instance.
     * @param metaLanguageUtils the {@link org.acmsl.queryj.api.MetaLanguageUtils} instance.
     * @return such information.
     */
    protected boolean isStaticTable(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetaLanguageUtils metaLanguageUtils)
    {
         return
            metaLanguageUtils.containsStaticValues(
                tableName, metadataManager);
    }

    /**
     * Retrieves given template's file name.
     * @param tableName the table name.
     * @return such name.
     */
    @NotNull
    public String retrieveTemplateFileName(@NotNull final String tableName)
    {
        return retrieveTemplateFileName(tableName, StringUtils.getInstance(), EnglishGrammarUtils.getInstance());
    }

    /**
     * Retrieves given template's file name.
     * @param tableName the table name.
     * @param stringUtils the {@link StringUtils} instance.
     * @param englishGrammarUtils the {@link EnglishGrammarUtils} instance.
     * @return such name.
     */
    @NotNull
    protected String retrieveTemplateFileName(
        @NotNull final String tableName,
        @NotNull final StringUtils stringUtils,
        @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        return
            "Abstract"
            + stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    tableName.toLowerCase(Locale.US)),
                '_')
            + "DAO.java";
    }
}
