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
 * Description: Writes the system functions template.
 *
 */
package org.acmsl.queryj.tools.templates.functions.system.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTemplate;
import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TemplateWritingHandler;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Writes the system functions template.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class SystemFunctionsTemplateWritingHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a <code>SystemFunctionsTemplateWritingHandler</code> instance.
     */
    public SystemFunctionsTemplateWritingHandler() {};

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
        throws  QueryJBuildException
    {
        writeTemplate(
            retrieveSystemFunctionsTemplate(parameters),
            retrieveOutputDir(parameters),
            SystemFunctionsTemplateGenerator.getInstance());

        return false;
    }

    /**
     * Writes the SystemFunctions template.
     * @param template the template.
     * @param outputDir the output dir.
     * @param generator the <code>SystemFunctionsTemplateGenerator</code>
     * instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition outputDir != null
     * @precondition generator != null
     */
    protected void writeTemplate(
        final SystemFunctionsTemplate template,
        final File outputDir,
        final SystemFunctionsTemplateGenerator generator)
      throws  QueryJBuildException
    {
        if  (template != null)
        {
            try 
            {
                generator.write(template, outputDir);
            }
            catch  (final IOException ioException)
            {
                throw
                    new QueryJBuildException(
                        "Cannot write SystemFunctions template", ioException);
            }
        }
    }

    /**
     * Retrieves the system functions template from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     * @precondition parameters != null
     */
    protected SystemFunctionsTemplate retrieveSystemFunctionsTemplate(
        final Map parameters)
    {
        return
            (SystemFunctionsTemplate)
                parameters.get(
                    SystemFunctionsTemplateBuildHandler
                        .SYSTEM_FUNCTIONS_TEMPLATE);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return such folder.
     * @precondition parameters != null
     */
    protected File retrieveOutputDir(final Map parameters)
    {
        return retrieveOutputDir(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected File retrieveOutputDir(
        final Map parameters, final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveFunctionsFolder(
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters));
    }
}
