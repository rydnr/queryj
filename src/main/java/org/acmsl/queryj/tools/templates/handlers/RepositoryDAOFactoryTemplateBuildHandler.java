//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: RepositoryDAOFactoryTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds the repository DAO factory implementation  if requested.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplate;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateFactory;
import org.acmsl.queryj.tools.templates.RepositoryDAOFactoryTemplateFactory;
import org.acmsl.queryj.tools.templates.RepositoryDAOFactoryTemplateGenerator;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.PackageUtils;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;
import java.util.Map;

/*
 * Importing some Apache Commons-Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Builds the repository DAO factory implementation if requested.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class RepositoryDAOFactoryTemplateBuildHandler
    extends  RepositoryDAOTemplateBuildHandler
{
    /**
     * Retrieves the per-repository template factory.
     * @return such instance.
     */
    protected BasePerRepositoryTemplateFactory retrieveTemplateFactory()
    {
        return RepositoryDAOFactoryTemplateGenerator.getInstance();
    }
    
    /**
     * Uses the factory to create the template.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param factory the template factory.
     * @param packageName the package name.
     * @param projectPackage the base package.
     * @param repository the repository.
     * @param engineName the engine name.
     * @param tableNames the table names.
     * @param header the header.
     * @return the template.
     * @throws QueryJException on invalid input.
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition customSqlProvider != null
     * @precondition packageName != null
     * @precondition projectPackage != null
     * @precondition repository != null
     * @precondition engineName != null
     * @precondition tableNames != null
     * @precondition factory != null
     */
    protected BasePerRepositoryTemplate createTemplate(
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        final BasePerRepositoryTemplateFactory templateFactory,
        final String projectPackage,
        final String packageName,
        final String repository,
        final String engineName,
        final String header,
        final Collection tableNames,
        final Map parameters)
      throws  QueryJException
    {
        BasePerRepositoryTemplate result = null;

        if  (templateFactory instanceof RepositoryDAOFactoryTemplateFactory)
        {
            result =
                ((RepositoryDAOFactoryTemplateFactory) templateFactory)
                    .createTemplate(
                        metadataManager,
                        metadataTypeManager,
                        customSqlProvider,
                        packageName,
                        projectPackage,
                        repository,
                        engineName,
                        retrieveJNDIDataSource(parameters),
                        tableNames,
                        header);
        }
        else
        {
            LogFactory.getLog(RepositoryDAOFactoryTemplateBuildHandler.class).warn(
                "Unexpected RepositoryDAOFactory template factory.");
        }

        return result;
    }

    /**
     * Retrieves the JNDI location of the data source from the attribute map.
     * @param parameters the parameter map.
     * @return the JNDI location.
     * @throws BuildException if the JNDI location retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrieveJNDIDataSource(final Map parameters)
        throws  BuildException
    {
        return
            (String)
                parameters.get(
                    ParameterValidationHandler.JNDI_DATASOURCES);
    }

    /**
     * Retrieves the package name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String engineName,
        final String projectPackage,
        final PackageUtils packageUtils)
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
    protected void storeTemplate(
        final BasePerRepositoryTemplate template, final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.REPOSITORY_DAO_FACTORY_TEMPLATE,
            template);
    }
}
