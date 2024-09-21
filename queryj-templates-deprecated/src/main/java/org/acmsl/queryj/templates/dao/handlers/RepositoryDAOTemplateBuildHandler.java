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
 * Filename: RepositoryDAOTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a repository DAO if requested.
 *
 */
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.api.PerRepositoryTemplateContext;
import org.acmsl.queryj.templates.dao.RepositoryDAOTemplate;
import org.acmsl.queryj.templates.dao.RepositoryDAOTemplateFactory;
import org.acmsl.queryj.api.handlers.BasePerRepositoryTemplateBuildHandler;
import org.acmsl.queryj.api.TemplateMappingManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;


/**
 * Builds a repository DAO if requested.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class RepositoryDAOTemplateBuildHandler
    extends BasePerRepositoryTemplateBuildHandler<RepositoryDAOTemplate,
    RepositoryDAOTemplateFactory, PerRepositoryTemplateContext>
{
    /**
     * Retrieves the per-repository template factory.
     * @return such instance.
     */
    @NotNull
    protected RepositoryDAOTemplateFactory retrieveTemplateFactory()
    {
        return RepositoryDAOTemplateFactory.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void buildTemplate(
        @NotNull final Map<String, ?> parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final RepositoryDAOTemplateFactory templateFactory,
        @NotNull final String packageName,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @Nullable final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        @NotNull final DecoratorFactory decoratorFactory)
      throws  QueryJBuildException
    {
        if  (definesRepositoryScopedSql(
                 customSqlProvider,
                 getAllowEmptyRepositoryDAOSetting((Map <String, Boolean>) parameters)))
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
                decoratorFactory);
        }
    }
    
    /**
     * Retrieves the package name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     */
    @NotNull
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
    @SuppressWarnings("unchecked")
    protected void storeTemplate(
        @NotNull final RepositoryDAOTemplate template, @NotNull final Map parameters)
    {
        @NotNull final List<RepositoryDAOTemplate> list = new ArrayList<RepositoryDAOTemplate>(1);
        list.add(template);

        parameters.put(
            TemplateMappingManager.REPOSITORY_DAO_TEMPLATE,
            list);
    }
}
