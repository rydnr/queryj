//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a base abstract DAO template using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.BaseAbstractDAOTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.BasePerTableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;
import java.util.Map;

/**
 * Builds a base abstract DAO template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class BaseAbstractDAOTemplateBuildHandler
    extends  BasePerTableTemplateBuildHandler
{
    /**
     * Creates a <code>BaseAbstractDAOTemplateBuildHandler</code> instance.
     */
    public BaseAbstractDAOTemplateBuildHandler() {};

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    protected BasePerTableTemplateFactory retrieveTemplateFactory()
    {
        return BaseAbstractDAOTemplateGenerator.getInstance();
    }

    /**
     * Retrieves the package name.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String tableName,
        final String engineName,
        final String projectPackage,
        final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveBaseAbstractDAOPackage(projectPackage);
    }

    /**
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     * @precondition templates != null
     * @precondition parameters != null
     */
    protected void storeTemplates(
        final BasePerTableTemplate[] templates, final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.BASE_ABSTRACT_DAO_TEMPLATES, templates);
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
     * @throws QueryJException if the template cannot be created.
     * @precondition templateFactory != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition engineName != null
     * @precondition projectPackage != null
     * @precondition repository != null
     * @precondition parameters != null
     */
    protected BasePerTableTemplate createTemplate(
        final BasePerTableTemplateFactory templateFactory,
        final String tableName,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String projectPackage,
        final String repository,
        final String header,
        final boolean implementMarkerInterfaces,
        final Map parameters)
      throws  QueryJException
    {        
        BasePerTableTemplate result = null;

        if  (   (templateFactory instanceof BaseAbstractDAOTemplateGenerator)
             && (isStaticTable(tableName, metadataManager)))
        {
            BaseAbstractDAOTemplateGenerator t_Generator = 
                (BaseAbstractDAOTemplateGenerator) templateFactory;

            Collection t_cStaticValues =
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
