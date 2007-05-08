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
 * Filename: TextFunctionsTestTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes the text functions test template.
 *
 */
package org.acmsl.queryj.tools.templates.functions.text.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTestTemplate;

import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTestTemplateGenerator;
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
 * Writes the text functions test template.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class TextFunctionsTestTemplateWritingHandler
    extends    AbstractAntCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a TextFunctionsTestTemplateWritingHandler.
     */
    public TextFunctionsTestTemplateWritingHandler() {};

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
        return handle(command.getAttributeMap());
    }

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
        throws  BuildException
    {
        return
            handle(
                retrieveTextFunctionsTestTemplate(parameters),
                retrieveOutputDir(parameters),
                TextFunctionsTestTemplateGenerator.getInstance());
    }

    /**
     * Writes the test templates for TextFunctions.
     * @param template the template to write.
     * @param outputDir the output dir.
     * @param generator the <code>TextFunctionsTestTemplateGenerator</code>
     * instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition outputDir != null
     * @precondition generator != null
     */
    protected boolean handle(
        final TextFunctionsTestTemplate template,
        final File outputDir,
        final TextFunctionsTestTemplateGenerator generator)
      throws  BuildException
    {
        boolean result = false;

        if  (template != null)
        {
            try 
            {
                generator.write(template, outputDir);
            }
            catch  (final IOException ioException)
            {
                throw new BuildException(ioException);
            }
        }
        
        return result;
    }

    /**
     * Retrieves the text functions test template from the attribute map.
     * @param parameters the parameter map.
     * @return the test template.
     * @throws BuildException if the test template retrieval process if faulty.
     * @precondition parameters != null
     */
    protected TextFunctionsTestTemplate retrieveTextFunctionsTestTemplate(
        final Map parameters)
      throws  BuildException
    {
        return
            (TextFunctionsTestTemplate)
                parameters.get(
                    TemplateMappingManager.TEXT_FUNCTIONS_TEST_TEMPLATE);
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
        return
            retrieveOutputDir(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected File retrieveOutputDir(
        final Map parameters, final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveTestFunctionsFolder(
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters));
    }
}
