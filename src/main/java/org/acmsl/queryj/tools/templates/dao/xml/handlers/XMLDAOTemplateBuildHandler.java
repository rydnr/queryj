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
 * Filename: XMLDAOTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a XML DAO template using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTemplateGenerator;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/**
 * Builds a XML DAO template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class XMLDAOTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a <code>XMLDAOTemplateBuildHandler</code> instance.
     */
    public XMLDAOTemplateBuildHandler() {};

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
      throws  QueryJBuildException
    {
        buildTemplates(
            parameters,
            retrieveMetadataManager(parameters),
            retrieveProjectPackage(parameters),
            retrievePackage(parameters),
            retrieveTableRepositoryName(parameters),
            retrieveTableTemplates(parameters),
            retrieveHeader(parameters),
            XMLDAOTemplateGenerator.getInstance());

        return false;
    }

    /**
     * Builds the <code>XMLDAO</code> templates..
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param basePackage the base package.
     * @param packageName the package name.
     * @param repositoryName the repository name.
     * @param tableTemplates the table templates.
     * @param header the header.
     * @param templateFactory the template factory.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metadataManager != null
     * @precondition basePackage != null
     * @precondition packageName != null
     * @precondition repositoryName != null
     * @precondition tableTemplates != null
     * @precondition templateFactory != null
     */
    protected void buildTemplates(
        final Map parameters,
        final MetadataManager metadataManager,
        final String basePackage,
        final String packageName,
        final String repositoryName,
        final TableTemplate[] tableTemplates,
        final String header,
        final XMLDAOTemplateFactory templateFactory)
      throws  QueryJBuildException
    {
        int t_iLength = (tableTemplates != null) ? tableTemplates.length : 0;

        XMLDAOTemplate[] t_aXMLDAOTemplates =
            new XMLDAOTemplate[t_iLength];

        for  (int t_iXMLDAOIndex = 0;
                  t_iXMLDAOIndex < t_iLength;
                  t_iXMLDAOIndex++) 
        {
            t_aXMLDAOTemplates[t_iXMLDAOIndex] =
                templateFactory.createXMLDAOTemplate(
                    tableTemplates[t_iXMLDAOIndex],
                    metadataManager,
                    packageName,
                    basePackage,
                    repositoryName,
                    header);
        }

        storeXMLDAOTemplates(t_aXMLDAOTemplates, parameters);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition parameters != null
     */
    protected String retrievePackage(final Map parameters)
    {
        return
            retrievePackage(
                retrieveProjectPackage(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String projectPackage, final PackageUtils packageUtils)
    {
        return packageUtils.retrieveXMLDAOPackage(projectPackage);
    }

    /**
     * Retrieves the repository name.
     * @param parameters the parameters.
     * @return the repository's name.
     * @precondition parameters != null
     */
    protected String retrieveTableRepositoryName(final Map parameters)
    {
        return
            (String)
                parameters.get(
                    ParameterValidationHandler.REPOSITORY);
    }

    /**
     * Stores the XML DAO template collection in given attribute map.
     * @param mockDAOTemplates the XML DAO templates.
     * @param parameters the parameter map.
     * @precondition mockDAOTemplates != null
     * @precondition parameters != null
     */
    protected void storeXMLDAOTemplates(
        final XMLDAOTemplate[] mockDAOTemplates,
        final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.XML_DAO_TEMPLATES, mockDAOTemplates);
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @precondition parameters != null
     */
    protected TableTemplate[] retrieveTableTemplates(final Map parameters)
    {
        return
            (TableTemplate[])
                parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
    }
}
