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
 * Filename: BaseDAOTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a base DAO template using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.BaseDAOTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.BasePerTableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;
import java.util.Map;

/**
 * Builds a base DAO template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class BaseDAOTemplateBuildHandler
    extends  BasePerTableTemplateBuildHandler
{
    /**
     * Creates a <code>BaseDAOTemplateBuildHandler</code> instance.
     */
    public BaseDAOTemplateBuildHandler() {}

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @NotNull
    protected BasePerTableTemplateFactory retrieveTemplateFactory()
    {
        return BaseDAOTemplateGenerator.getInstance();
    }

    /**
     * Retrieves the package name.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String tableName,
        final String engineName,
        final String projectPackage,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveBaseDAOPackage(projectPackage);
    }

    /**
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     * @precondition templates != null
     * @precondition parameters != null
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void storeTemplates(
        final BasePerTableTemplate[] templates, @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.BASE_DAO_TEMPLATES, templates);
    }

    /**
     * Creates the template with given parameters.
     * @param templateFactory the template factory.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param projectPackage the project package.
     * @param repository the repository name.
     * @param header the header.
     * @param implementMarkerInterfaces whether to implement marker
     * interfaces.
     * @param parameters the parameters.
     * @return the template.
     * @throws QueryJBuildException if the template cannot be created.
     * @precondition templateFactory != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition engineName != null
     * @precondition projectPackage != null
     * @precondition repository != null
     * @precondition parameters != null
     */
    @Nullable
    protected BasePerTableTemplate createTemplate(
        @NotNull final BasePerTableTemplateFactory templateFactory,
        final String tableName,
        @NotNull final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String projectPackage,
        final String repository,
        final String header,
        final boolean implementMarkerInterfaces,
        @NotNull final Map parameters)
      throws  QueryJBuildException
    {        
        @Nullable BasePerTableTemplate result;

        if  (   (templateFactory instanceof BaseDAOTemplateGenerator)
             && (isStaticTable(tableName, metadataManager)))
        {
            @NotNull BaseDAOTemplateGenerator t_Generator =
                (BaseDAOTemplateGenerator) templateFactory;

            @Nullable Collection t_cStaticValues =
                retrieveStaticContent(
                    parameters,
                    tableName,
                    metadataManager,
                    t_Generator.getDecoratorFactory());

            result =
                t_Generator.createTemplate(
                    tableName,
                    metadataManager,
                    customSqlProvider,
                    packageName,
                    engineName,
                    engineVersion,
                    quote,
                    projectPackage,
                    repository,
                    header,
                    implementMarkerInterfaces,
                    t_cStaticValues);
        }
        else
        {
            result =
                super.createTemplate(
                    templateFactory,
                    tableName,
                    metadataManager,
                    customSqlProvider,
                    packageName,
                    engineName,
                    engineVersion,
                    quote,
                    projectPackage,
                    repository,
                    header,
                    implementMarkerInterfaces,
                    parameters);
        }

        return result;
    }
}
