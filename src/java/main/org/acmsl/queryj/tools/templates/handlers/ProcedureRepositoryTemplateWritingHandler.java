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
 * Description: Writes the procedure repository.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.templates.handlers.ProcedureRepositoryTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateWritingHandler;
import org.acmsl.queryj.tools.templates.ProcedureRepositoryTemplate;
import org.acmsl.queryj.tools.templates.ProcedureRepositoryTemplateGenerator;

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
 * Writes the procedure repository.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class ProcedureRepositoryTemplateWritingHandler
    extends    AbstractAntCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a ProcedureRepositoryTemplateWritingHandler.
     */
    public ProcedureRepositoryTemplateWritingHandler() {};

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

                ProcedureRepositoryTemplateGenerator t_ProcedureRepositoryTemplateGenerator =
                    ProcedureRepositoryTemplateGenerator.getInstance();

                ProcedureRepositoryTemplate t_ProcedureRepositoryTemplate =
                    retrieveProcedureRepositoryTemplate(attributes);

                File t_OutputDir = retrieveOutputDir(attributes);

                if  (   (t_OutputDir                            != null) 
                     && (t_ProcedureRepositoryTemplate          != null)
                     && (t_ProcedureRepositoryTemplateGenerator != null))
                {
                    t_ProcedureRepositoryTemplateGenerator.write(
                        t_ProcedureRepositoryTemplate, t_OutputDir);
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
     * Retrieves the procedure repository template from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     * @throws BuildException if the repository retrieval process if faulty.
     */
    protected ProcedureRepositoryTemplate retrieveProcedureRepositoryTemplate(Map parameters)
        throws  BuildException
    {
        ProcedureRepositoryTemplate result = null;

        if  (parameters != null)
        {
            result =
                (ProcedureRepositoryTemplate)
                    parameters.get(
                        ProcedureRepositoryTemplateBuildHandler.PROCEDURE_REPOSITORY_TEMPLATE);
        }
        
        return result;
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     */
    protected File retrieveOutputDir(Map parameters)
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
