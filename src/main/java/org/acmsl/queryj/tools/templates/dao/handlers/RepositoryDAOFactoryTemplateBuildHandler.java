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
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateContext;
import org.acmsl.queryj.tools.templates.RepositoryDAOFactoryTemplate;
import org.acmsl.queryj.tools.templates.RepositoryDAOFactoryTemplateGenerator;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.PackageUtils;

/*
 * Importing some JDK classes.
 */
import java.util.List;
import java.util.Map;

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.tools.templates.handlers.BasePerRepositoryTemplateBuildHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Builds the repository DAO factory implementation if requested.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class RepositoryDAOFactoryTemplateBuildHandler
    extends BasePerRepositoryTemplateBuildHandler<RepositoryDAOFactoryTemplate, RepositoryDAOFactoryTemplateGenerator, BasePerRepositoryTemplateContext>
{
    /**
     * Retrieves the per-repository template factory.
     * @return such instance.
     */
    @NotNull
    protected RepositoryDAOFactoryTemplateGenerator retrieveTemplateFactory()
    {
        return RepositoryDAOFactoryTemplateGenerator.getInstance();
    }
    
    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    protected RepositoryDAOFactoryTemplate createTemplate(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final RepositoryDAOFactoryTemplateGenerator templateFactory,
        @NotNull final String projectPackage,
        @NotNull final String packageName,
        @NotNull final String repository,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final List<String> tableNames,
        @NotNull final String jndiLocation,
        @NotNull final Map parameters)
      throws  QueryJBuildException
    {
        return
            templateFactory.createTemplate(
                metadataManager,
                customSqlProvider,
                packageName,
                projectPackage,
                repository,
                header,
                implementMarkerInterfaces,
                jmx,
                tableNames,
                jndiLocation);
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
     * @precondition template != null
     * @precondition parameters != null
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void storeTemplate(
        @NotNull final RepositoryDAOFactoryTemplate template, @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.REPOSITORY_DAO_FACTORY_TEMPLATE,
            template);
    }
}
