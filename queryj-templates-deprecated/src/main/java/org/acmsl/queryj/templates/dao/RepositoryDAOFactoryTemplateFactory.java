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
 * Filename: RepositoryDAOFactoryTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create RepositoryDAOFactoryTemplate instances.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.DecorationUtils;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.api.PerRepositoryTemplateContext;
import org.acmsl.queryj.api.PerRepositoryTemplateFactory;

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
 * Importing some JDK classes.
 */
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to create {@link RepositoryDAOFactoryTemplate} instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 2012/07/09
 */
@ThreadSafe
public class RepositoryDAOFactoryTemplateFactory
    implements PerRepositoryTemplateFactory<RepositoryDAOFactoryTemplate>,
                Singleton
{
    /**
     * Singleton implemented to avoid double-locking check.
     */
    protected static final class RepositoryDAOFactoryTemplateFactorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final RepositoryDAOFactoryTemplateFactory SINGLETON =
            new RepositoryDAOFactoryTemplateFactory();
    }

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    public static RepositoryDAOFactoryTemplateFactory getInstance()
    {
        return RepositoryDAOFactoryTemplateFactorySingletonContainer.SINGLETON;
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public RepositoryDAOFactoryTemplate createTemplate(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String packageName,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @Nullable final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final List<String> tableNames,
        @NotNull final String jndiLocation,
        final boolean disableGenerationTimestamps,
        final boolean disableNotNullAnnotations,
        final boolean disableCheckthreadAnnotations)
    {
        @Nullable RepositoryDAOFactoryTemplate result = null;

        if (customSqlProvider.getSqlDAO().containsRepositoryScopedSql())
        {
            result =
                new RepositoryDAOFactoryTemplate(
                    new PerRepositoryTemplateContext(
                        metadataManager,
                        customSqlProvider,
                        header,
                        decoratorFactory,
                        packageName,
                        projectPackage,
                        repository,
                        implementMarkerInterfaces,
                        jmx,
                        tableNames,
                        jndiLocation,
                        disableGenerationTimestamps,
                        disableNotNullAnnotations,
                        disableCheckthreadAnnotations,
                        retrieveTemplateFileName(repository, metadataManager)));
        }

        return result;
    }

    /**
     * Retrieves given template's file name.
     * @param repository the repository.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return such name.
     */
    @NotNull
    public String retrieveTemplateFileName(
        @NotNull final String repository, @NotNull final MetadataManager metadataManager)
    {
        return retrieveTemplateFileName(repository, metadataManager, DecorationUtils.getInstance());
    }

    /**
     * Retrieves given template's file name.
     * @param repository the repository.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return such name.
     */
    @NotNull
    protected String retrieveTemplateFileName(
        @NotNull final String repository,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecorationUtils decorationUtils)
    {
        return
            metadataManager.getEngineName()
            + decorationUtils.capitalize(repository)
            + "DAOFactory.java";
    }
}
