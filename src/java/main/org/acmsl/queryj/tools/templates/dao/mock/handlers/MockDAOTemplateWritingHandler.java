/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

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
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendáriz
 *
 * Description: Writes Mock DAO templates.
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
package org.acmsl.queryj.tools.templates.dao.mock.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOTemplateGenerator;
import org.acmsl.queryj.tools.templates.dao.mock.handlers.MockDAOTemplateBuildHandler;
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
 * Writes Mock DAO templates.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class MockDAOTemplateWritingHandler
    extends    AntCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a MockDAOTemplateWritingHandler.
     */
    public MockDAOTemplateWritingHandler() {};

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

            MockDAOTemplateGenerator t_MockDAOTemplateGenerator =
                MockDAOTemplateGenerator.getInstance();

            MockDAOTemplate[] t_aMockDAOTemplates =
                retrieveMockDAOTemplates(attributes);

            if  (   (t_aMockDAOTemplates        != null)
                 && (t_MockDAOTemplateGenerator != null))
            {
                File t_OutputDir =
                    retrieveOutputDir(
                        attributes);

                for  (int t_iMockDAOIndex = 0;
                          t_iMockDAOIndex < t_aMockDAOTemplates.length;
                          t_iMockDAOIndex++)
                {
                    t_MockDAOTemplateGenerator.write(
                        t_aMockDAOTemplates[t_iMockDAOIndex],
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
                    "Cannot write Mock DAO template ("
                    + ioException.getMessage()
                    + ")",
                    Project.MSG_WARN);
            }
            
            throw new BuildException(ioException);
        }
        
        return result;
    }

    /**
     * Retrieves the Mock DAO templates from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     * @throws BuildException if the template retrieval process if faulty.
     * @precondition parameters != null
     */
    protected MockDAOTemplate[] retrieveMockDAOTemplates(Map parameters)
        throws  BuildException
    {
        return
            (MockDAOTemplate[])
                parameters.get(
                    TemplateMappingManager.MOCK_DAO_TEMPLATES);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     * @precondition parameters != null
     */
    protected File retrieveOutputDir(Map parameters)
        throws  BuildException
    {
        File result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (t_PackageUtils != null)
        {
            result =
                t_PackageUtils.retrieveMockDAOFolder(
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
