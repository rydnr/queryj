/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a XML DAO template using database metadata.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTemplateGenerator;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/*
 * Importing Jakarta Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Builds a XML DAO template using database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class XMLDAOTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a XMLDAOTemplateBuildHandler.
     */
    public XMLDAOTemplateBuildHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition command != null
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        boolean result = false;

        try
        {
            Map attributes = command.getAttributeMap();

            DatabaseMetaDataManager t_MetaDataManager =
                retrieveDatabaseMetaDataManager(attributes);

            XMLDAOTemplateGenerator t_XMLDAOTemplateGenerator =
                XMLDAOTemplateGenerator.getInstance();

            if  (t_XMLDAOTemplateGenerator != null)
            {
                String t_strBasePackage =
                    retrieveProjectPackage(attributes);

                String t_strPackage =
                    retrievePackage(attributes);

                String t_strRepositoryName =
                    retrieveTableRepositoryName(attributes);

                TableTemplate[] t_aTableTemplates =
                    retrieveTableTemplates(attributes);

                if  (t_aTableTemplates != null)
                {
                    XMLDAOTemplate[] t_aXMLDAOTemplates =
                        new XMLDAOTemplate[t_aTableTemplates.length];

                    for  (int t_iXMLDAOIndex = 0;
                              t_iXMLDAOIndex < t_aXMLDAOTemplates.length;
                              t_iXMLDAOIndex++) 
                    {
                        command.getProject().log(
                            command.getTask(),
                            "Building XML dao template ("
                            + t_aTableTemplates[t_iXMLDAOIndex].getTableName()
                            + ")",
                            Project.MSG_VERBOSE);

                        t_aXMLDAOTemplates[t_iXMLDAOIndex] =
                            t_XMLDAOTemplateGenerator.createXMLDAOTemplate(
                                t_aTableTemplates[t_iXMLDAOIndex],
                                t_MetaDataManager,
                                t_strPackage,
                                t_strBasePackage,
                                t_strRepositoryName);
                    }

                    storeXMLDAOTemplates(t_aXMLDAOTemplates, attributes);
                }
            }
        }
        catch  (QueryJException queryjException)
        {
            Project t_Project = command.getProject();

            if  (t_Project != null)
            {
                t_Project.log(
                    command.getTask(),
                      "Error building XML DAO ("
                    + queryjException.getMessage()
                    + ")",
                    Project.MSG_WARN);
            }

            throw new BuildException(queryjException);
        }
        
        return result;
    }

    /**
     * Retrieves the database metadata manager from the attribute map.
     * @param parameters the parameter map.
     * @return the manager.
     * @throws BuildException if the manager retrieval process if faulty.
     * @precondition parameters != null
     */
    protected DatabaseMetaDataManager retrieveDatabaseMetaDataManager(
        final Map parameters)
      throws  BuildException
    {
        return
            (DatabaseMetaDataManager)
                parameters.get(
                    DatabaseMetaDataRetrievalHandler.DATABASE_METADATA_MANAGER);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrieveProjectPackage(final Map parameters)
        throws  BuildException
    {
        return (String) parameters.get(ParameterValidationHandler.PACKAGE);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrievePackage(final Map parameters)
      throws  BuildException
    {
        String result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (t_PackageUtils != null)
        {
            result =
                t_PackageUtils.retrieveXMLDAOPackage(
                    retrieveProjectPackage(parameters));
        }
        
        return result;
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
     * @throws BuildException if the templates cannot be stored for any reason.
     * @precondition parameters != null
     */
    protected void storeXMLDAOTemplates(
        final XMLDAOTemplate[] mockDAOTemplates,
        final Map               parameters)
      throws  BuildException
    {
        parameters.put(
            TemplateMappingManager.XML_DAO_TEMPLATES, mockDAOTemplates);
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @throws BuildException if the templates cannot be stored for any reason.
     * @precondition parameters != null
     */
    protected TableTemplate[] retrieveTableTemplates(final Map parameters)
      throws  BuildException
    {
        return
            (TableTemplate[])
                parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
    }
}
