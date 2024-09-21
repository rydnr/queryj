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
 * Filename: DAOFactoryTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create DAOFactoryTemplate instances.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.api.PerTableTemplateContext;
import org.acmsl.queryj.api.PerTableTemplateFactory;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.Row;

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
 * Is able to create {@link DAOFactoryTemplate} instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 2012/07/09
 */
@ThreadSafe
public class DAOFactoryTemplateFactory
    implements PerTableTemplateFactory<DAOFactoryTemplate>,
                Singleton
{
    /**
     * Singleton implemented to avoid double-locking check.
     */
    protected static final class DAOFactoryTemplateFactorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final DAOFactoryTemplateFactory SINGLETON = new DAOFactoryTemplateFactory();
    }

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    public static DAOFactoryTemplateFactory getInstance()
    {
        return DAOFactoryTemplateFactorySingletonContainer.SINGLETON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public DAOFactoryTemplate createTemplate(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String packageName,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @Nullable final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiDataSource,
        final boolean disableGenerationTimestamps,
        final boolean disableNotNullAnnotations,
        final boolean disableCheckthreadAnnotations,
        @NotNull final String tableName,
        @Nullable final List<Row> staticContents)
    {
        return
            new DAOFactoryTemplate(
                new PerTableTemplateContext(
                    metadataManager,
                    customSqlProvider,
                    header,
                    decoratorFactory,
                    packageName,
                    projectPackage,
                    repository,
                    implementMarkerInterfaces,
                    jmx,
                    jndiDataSource,
                    disableGenerationTimestamps,
                    disableNotNullAnnotations,
                    disableCheckthreadAnnotations,
                    retrieveTemplateFileName(tableName, metadataManager),
                    tableName,
                    staticContents));
    }

    /**
     * Retrieves given template's file name.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return such name.
     */
    @NotNull
    public String retrieveTemplateFileName(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        return
            retrieveTemplateFileName(
                tableName,
                metadataManager,
                StringUtils.getInstance(),
                EnglishGrammarUtils.getInstance());
    }

    /**
     * Retrieves given template's file name.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param stringUtils the {@link StringUtils} instance.
     * @param englishGrammarUtils the {@link EnglishGrammarUtils} instance.
     * @return such name.
     */
    @NotNull
    protected String retrieveTemplateFileName(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final StringUtils stringUtils,
        @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        return
            metadataManager.getEngineName()
            + stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    tableName.toLowerCase(Locale.US)),
                '_')
            + "DAOFactory.java";
    }

}
