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
 * Filename: RepositoryDAOFactoryTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds the repository DAO factory implementation  if requested.
 *
 */
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.templates.RepositoryDAOFactoryTemplateFactory;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.templates.BasePerRepositoryTemplateContext;
import org.acmsl.queryj.templates.TableTemplate;
import org.acmsl.queryj.templates.handlers.BasePerRepositoryTemplateBuildHandler;
import org.acmsl.queryj.templates.RepositoryDAOFactoryTemplate;
import org.acmsl.queryj.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.PackageUtils;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Builds the repository DAO factory implementation if requested.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class RepositoryDAOFactoryTemplateBuildHandler
    extends BasePerRepositoryTemplateBuildHandler<
    RepositoryDAOFactoryTemplate, RepositoryDAOFactoryTemplateFactory, BasePerRepositoryTemplateContext>
{
    /**
     * Retrieves the per-repository template factory.
     * @return such instance.
     */
    @NotNull
    protected RepositoryDAOFactoryTemplateFactory retrieveTemplateFactory()
    {
        return RepositoryDAOFactoryTemplateFactory.getInstance();
    }
    
    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected String retrievePackage(
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveRepositoryDAOPackage(
                projectPackage, engineName);
    }

    /**
     * Stores the template in given attribute map.
     * @param template the template.
     * @param parameters the parameter map.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void storeTemplate(
        @NotNull final RepositoryDAOFactoryTemplate template, @NotNull final Map parameters)
    {
        List<RepositoryDAOFactoryTemplate> list = new ArrayList<RepositoryDAOFactoryTemplate>(1);
        list.add(template);

        parameters.put(
            TemplateMappingManager.REPOSITORY_DAO_FACTORY_TEMPLATE,
            list);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void buildTemplate(
        @NotNull final Map parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final RepositoryDAOFactoryTemplateFactory templateFactory,
        @NotNull final String packageName,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @Nullable final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        @NotNull final List<TableTemplate> tableTemplates,
        @NotNull final DecoratorFactory decoratorFactory)
        throws QueryJBuildException
    {
        if  (definesRepositoryScopedSql(
            customSqlProvider,
            getAllowEmptyRepositoryDAOSetting(parameters)))
        {
            super.buildTemplate(
                parameters,
                metadataManager,
                customSqlProvider,
                templateFactory,
                packageName,
                projectPackage,
                repository,
                header,
                implementMarkerInterfaces,
                jmx,
                jndiLocation,
                tableTemplates,
                decoratorFactory);
        }
    }
}
