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
 * Description: Writes XML DAO templates.
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
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTemplateGenerator;
import org.acmsl.queryj.tools.templates.dao.xml.handlers.XMLDAOTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.handlers.TemplateWritingHandler;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Writes XML DAO templates.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class XMLDAOTemplateWritingHandler
    extends    AbstractAntCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a XMLDAOTemplateWritingHandler.
     */
    public XMLDAOTemplateWritingHandler() {};

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

            XMLDAOTemplateGenerator t_XMLDAOTemplateGenerator =
                XMLDAOTemplateGenerator.getInstance();

            XMLDAOTemplate[] t_aXMLDAOTemplates =
                retrieveXMLDAOTemplates(attributes);

            if  (   (t_aXMLDAOTemplates        != null)
                 && (t_XMLDAOTemplateGenerator != null))
            {
                File t_OutputDir =
                    retrieveOutputDir(
                        attributes);

                for  (int t_iXMLDAOIndex = 0;
                          t_iXMLDAOIndex < t_aXMLDAOTemplates.length;
                          t_iXMLDAOIndex++)
                {
                    t_XMLDAOTemplateGenerator.write(
                        t_aXMLDAOTemplates[t_iXMLDAOIndex],
                        t_OutputDir,
                        command.getProject(),
                        command.getTask());
                }
            }
        }
        catch  (IOException ioException)
        {
            Project t_Project = command.getProject();

            if  (t_Project != null)
            {
                t_Project.log(
                      command.getTask(),
                    "Cannot write XML DAO template ("
                    + ioException.getMessage()
                    + ")",
                    Project.MSG_WARN);
            }
            
            throw new BuildException(ioException);
        }
        
        return result;
    }

    /**
     * Retrieves the XML DAO templates from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     * @throws BuildException if the template retrieval process if faulty.
     * @precondition parameters != null
     */
    protected XMLDAOTemplate[] retrieveXMLDAOTemplates(final Map parameters)
        throws  BuildException
    {
        return
            (XMLDAOTemplate[])
                parameters.get(
                    TemplateMappingManager.XML_DAO_TEMPLATES);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     * @precondition parameters != null
     */
    protected File retrieveOutputDir(final Map parameters)
        throws  BuildException
    {
        File result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (t_PackageUtils != null)
        {
            result =
                t_PackageUtils.retrieveXMLDAOFolder(
                    retrieveProjectOutputDir(parameters),
                    retrieveProjectPackage(parameters));
        }
        
        return result;
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     * @precondition parameters != null
     */
    protected File retrieveProjectOutputDir(Map parameters)
        throws  BuildException
    {
        return
            (File) parameters.get(ParameterValidationHandler.OUTPUT_DIR);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrieveProjectPackage(Map parameters)
        throws  BuildException
    {
        return
            (String) parameters.get(ParameterValidationHandler.PACKAGE);
    }
}
