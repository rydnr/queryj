/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes the DataAccessContextLocal template.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.templates.dao.DataAccessContextLocalTemplate;
import org.acmsl.queryj.tools.templates.dao.DataAccessContextLocalTemplateGenerator;
import org.acmsl.queryj.tools.templates.dao.handlers.DataAccessContextLocalTemplateBuildHandler;
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
import java.util.Map;

/**
 * Writes the DAO chooser template.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class DataAccessContextLocalTemplateWritingHandler
    extends    AbstractAntCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a DataAccessContextLocalTemplateWritingHandler.
     */
    public DataAccessContextLocalTemplateWritingHandler() {};

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

                DataAccessContextLocalTemplateGenerator
                    t_DataAccessContextLocalTemplateGenerator =
                        DataAccessContextLocalTemplateGenerator.getInstance();

                DataAccessContextLocalTemplate
                    t_DataAccessContextLocalTemplate =
                        retrieveDataAccessContextLocalTemplate(attributes);

                File t_OutputDir = retrieveOutputDir(attributes);

                if  (   (t_OutputDir                               != null) 
                     && (t_DataAccessContextLocalTemplate          != null)
                     && (t_DataAccessContextLocalTemplateGenerator != null))
                {
                    t_DataAccessContextLocalTemplateGenerator.write(
                        t_DataAccessContextLocalTemplate, t_OutputDir);
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
     * Retrieves the DataAccessContextLocal template from the attribute map.
     * @param parameters the parameter map.
     * @return the repository instance.
     * @throws BuildException if the DataAccessContextLocal template process if faulty.
     */
    protected DataAccessContextLocalTemplate
        retrieveDataAccessContextLocalTemplate(Map parameters)
        throws  BuildException
    {
        DataAccessContextLocalTemplate result = null;

        if  (parameters != null)
        {
            result =
                (DataAccessContextLocalTemplate)
                    parameters.get(
                        TemplateMappingManager.DATAACCESSCONTEXTLOCAL_TEMPLATE);
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

        if  (parameters != null)
        {
            result =
                (File) parameters.get(ParameterValidationHandler.OUTPUT_DIR);
        }

        return result;
    }
}
