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
 * Description: Writes XML DAO factory templates.
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
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOFactoryTemplate;
import org.acmsl.queryj.tools.templates.dao.xml
    .XMLDAOFactoryTemplateGenerator;
import org.acmsl.queryj.tools.templates.dao.xml.handlers
    .XMLDAOFactoryTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateWritingHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * Writes XML DAO factory templates.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class XMLDAOFactoryTemplateWritingHandler
    extends    AbstractAntCommandHandler
    implements TemplateWritingHandler
{
    /**
     * A cached empty XML DAO factory template array.
     */
    public static final XMLDAOFactoryTemplate[]
        EMPTY_XML_DAO_FACTORY_TEMPLATE_ARRAY =
            new XMLDAOFactoryTemplate[0];

    /**
     * Creates a XMLDAOFactoryTemplateWritingHandler.
     */
    public XMLDAOFactoryTemplateWritingHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        boolean result = false;

        if  (command != null) 
        {
            try 
            {
                Map attributes = command.getAttributeMap();

                XMLDAOFactoryTemplateGenerator
                    t_XMLDAOFactoryTemplateGenerator =
                        XMLDAOFactoryTemplateGenerator.getInstance();

                XMLDAOFactoryTemplate[] t_aXMLDAOFactoryTemplates =
                    retrieveXMLDAOFactoryTemplates(attributes);

                if  (   (t_aXMLDAOFactoryTemplates        != null)
                     && (t_XMLDAOFactoryTemplateGenerator != null))
                {
                    File t_OutputDir =
                        retrieveOutputDir(attributes);

                    for  (int t_iXMLDAOFactoryIndex = 0;
                                t_iXMLDAOFactoryIndex
                              < t_aXMLDAOFactoryTemplates.length;
                              t_iXMLDAOFactoryIndex++)
                    {
                        t_XMLDAOFactoryTemplateGenerator.write(
                            t_aXMLDAOFactoryTemplates[t_iXMLDAOFactoryIndex],
                            t_OutputDir);
                    }
                }
            }
            catch  (IOException ioException)
            {
                throw new BuildException(ioException);
            }
        }
        
        return result;
    }

    /**
     * Retrieves the XML DAO factory templates from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     * @throws BuildException if the template retrieval process if faulty.
     */
    protected XMLDAOFactoryTemplate[] retrieveXMLDAOFactoryTemplates(
        final Map parameters)
      throws  BuildException
    {
        XMLDAOFactoryTemplate[] result = EMPTY_XML_DAO_FACTORY_TEMPLATE_ARRAY;

        if  (parameters != null)
        {
            result =
                (XMLDAOFactoryTemplate[])
                    parameters.get(
                        TemplateMappingManager.XML_DAO_FACTORY_TEMPLATES);
        }
        
        return result;
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     */
    protected File retrieveOutputDir(final Map parameters)
        throws  BuildException
    {
        File result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (parameters     != null)
             && (t_PackageUtils != null))
        {
            result =
                t_PackageUtils.retrieveXMLDAOFactoryFolder(
                    (File)
                        parameters.get(ParameterValidationHandler.OUTPUT_DIR),
                    retrieveProjectPackage(parameters));
        }
        
        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrieveProjectPackage(final Map parameters)
        throws  BuildException
    {
        String result = null;

        if  (parameters != null)
        {
            result =
                (String) parameters.get(ParameterValidationHandler.PACKAGE);
        }
        
        return result;
    }
}
