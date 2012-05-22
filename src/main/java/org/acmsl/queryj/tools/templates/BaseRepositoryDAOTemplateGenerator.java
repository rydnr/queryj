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
 * Filename: BaseRepositoryDAOTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate the DAO repository interface.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.DecorationUtils;
import org.acmsl.queryj.tools.metadata.MetadataManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * Is able to generate the repository DAO interface.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class BaseRepositoryDAOTemplateGenerator
    extends AbstractTemplateGenerator<BaseRepositoryDAOTemplate, BasePerRepositoryTemplateContext>
    implements  BasePerRepositoryTemplateGenerator<BaseRepositoryDAOTemplate, BasePerRepositoryTemplateContext>,
                BasePerRepositoryTemplateFactory<BaseRepositoryDAOTemplate>,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class BaseRepositoryDAOTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final BaseRepositoryDAOTemplateGenerator SINGLETON =
            new BaseRepositoryDAOTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected BaseRepositoryDAOTemplateGenerator() {}

    /**
     * Retrieves a {@link RepositoryDAOTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static BaseRepositoryDAOTemplateGenerator getInstance()
    {
        return BaseRepositoryDAOTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public BaseRepositoryDAOTemplate createTemplate(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String projectPackage,
        @NotNull final String packageName,
        @NotNull final String repository,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final List<String> tableNames,
        @NotNull final String jndiLocation)
    {
        return
            new BaseRepositoryDAOTemplate(
                new BasePerRepositoryTemplateContext(
                    metadataManager,
                    customSqlProvider,
                    header,
                    getDecoratorFactory(),
                    packageName,
                    projectPackage,
                    repository,
                    implementMarkerInterfaces,
                    jmx,
                    tableNames,
                    jndiLocation));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String retrieveTemplateFileName(@NotNull final BasePerRepositoryTemplateContext context)
    {
        return retrieveTemplateFileName(context, DecorationUtils.getInstance());
    }

    /**
     * Retrieves the template's file name.
     * @param context the {@link BasePerRepositoryTemplateContext context}.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return such file name.
     */
    @NotNull
    protected String retrieveTemplateFileName(
        @NotNull final BasePerRepositoryTemplateContext context, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(context.getRepositoryName()) + "DAO.java";
    }
}
