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
 * Filename: DAOTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create DAOTemplate instances.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing some project-specific classes.
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
 * Is able to create {@link DAOTemplate} instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 2.0
 * Created: 2012/07/09
 */
@ThreadSafe
public class DAOTemplateFactory
    implements PerTableTemplateFactory<DAOTemplate, PerTableTemplateContext>,
                Singleton
{
    /**
     * Singleton instance to avoid double-locking check.
     */
    protected static final class DAOTemplateFactorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final DAOTemplateFactory SINGLETON = new DAOTemplateFactory();
    }

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    public static DAOTemplateFactory getInstance()
    {
        return DAOTemplateFactorySingletonContainer.SINGLETON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public DAOTemplate createTemplate(
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
        @NotNull final List<Row<String>> staticContents)
    {
        return
            new DAOTemplate(
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
                    retrieveTemplateFileName(tableName, metadataManager),
                    tableName,
                    staticContents));
    }

    /**
     * Retrieves the file name of the template.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the file name.
     */
    @NotNull
    public String retrieveTemplateFileName(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        return
            retrieveTemplateFileName(
                tableName, metadataManager, EnglishGrammarUtils.getInstance(), StringUtils.getInstance());
    }

    /**
     * Retrieves the file name of the template.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param englishGrammarUtils the {@link EnglishGrammarUtils} instance.
     * @param stringUtils the {@link StringUtils} instance.
     * @return the file name.
     */
    @NotNull
    public String retrieveTemplateFileName(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final EnglishGrammarUtils englishGrammarUtils,
        @NotNull final StringUtils stringUtils)
    {
        return
              stringUtils.capitalize(metadataManager.getEngineName())
            + stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    tableName.toLowerCase(Locale.US)),
                '_')
            + "DAO.java";
    }
}