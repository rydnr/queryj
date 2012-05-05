//;-*- mode: java -*-
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
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Sql;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.templates.RepositoryDAOTemplate;
import org.acmsl.queryj.tools.templates.RepositoryDAOTemplateGenerator;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.PackageUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Builds a repository DAO if requested.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class RepositoryDAOTemplateBuildHandler
    extends  BasePerRepositoryTemplateBuildHandler<RepositoryDAOTemplate, RepositoryDAOTemplateGenerator>
{
    /**
     * Retrieves the per-repository template factory.
     * @return such instance.
     */
    @NotNull
    protected RepositoryDAOTemplateGenerator retrieveTemplateFactory()
    {
        return RepositoryDAOTemplateGenerator.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void buildTemplate(
        @NotNull final Map parameters,
        @NotNull final String engineName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final RepositoryDAOTemplateGenerator templateFactory,
        @NotNull final String projectPackage,
        @NotNull final String packageName,
        @NotNull final String repository,
        @NotNull final String header,
        final boolean jmx,
        @Nullable final TableTemplate[] tableTemplates)
      throws  QueryJBuildException
    {
        if  (definesRepositoryScopedSql(
                 customSqlProvider,
                 getAllowEmptyRepositoryDAOSetting(parameters)))
        {
            super.buildTemplate(
                parameters,
                engineName,
                metadataManager,
                customSqlProvider,
                templateFactory,
                projectPackage,
                packageName,
                repository,
                header,
                jmx,
                tableTemplates);
        }
    }
    
    /**
     * Retrieves the package name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
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
     * @precondition template != null
     * @precondition parameters != null
     */
    @SuppressWarnings("unchecked")
    protected void storeTemplate(
        @NotNull final RepositoryDAOTemplate template, @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.REPOSITORY_DAO_TEMPLATE,
            template);
    }

    /**
     * Checks whether there's any custom SQL for the whole repository.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param allowEmptyRepositoryDAO whether to generate the repository DAO
     * in any case.
     * @return <code>true</code> in such case.
     */
    protected boolean definesRepositoryScopedSql(
        @Nullable final CustomSqlProvider customSqlProvider,
        final boolean allowEmptyRepositoryDAO)
    {
        boolean result = allowEmptyRepositoryDAO;

        if  (!result)
        {
            @Nullable Collection t_cElements =
                (customSqlProvider != null)
                ?  customSqlProvider.getCollection()
                :  null;
        
            @Nullable Iterator t_Iterator =
                (t_cElements != null) ? t_cElements.iterator() : null;
        
            if  (t_Iterator != null)
            {
                Object t_Item;
                Sql t_Sql;
            
                while  (t_Iterator.hasNext())
                {
                    t_Item = t_Iterator.next();
                
                    if  (t_Item instanceof Sql)
                    {
                        t_Sql = (Sql) t_Item;

                        if  (t_Sql.getRepositoryScope() != null)
                        {
                            result = true;
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Checks whether empty repository DAO is allowed explicitly.
     * @param parameters the parameters.
     * @return <code>true</code> in such case.
     * @precondition parameters != null
     */
    protected boolean getAllowEmptyRepositoryDAOSetting(@NotNull final Map parameters)
    {
        boolean result;

        @Nullable Boolean t_Result =
            (Boolean)
                parameters.get(
                    ParameterValidationHandler.ALLOW_EMPTY_REPOSITORY_DAO);

        result = (t_Result != null) && t_Result;

        return result;
    }
}
