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
 * Filename: TextFunctionsTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes the text functions template.
 *
 */
package org.acmsl.queryj.tools.templates.functions.text.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.functions.text.TextFunctionsTemplate;
import org.acmsl.queryj.tools.templates.functions.text.TextFunctionsTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TemplateWritingHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Writes the text functions template.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TextFunctionsTemplateWritingHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a <code>TextFunctionsTemplateWritingHandler</code> instance.
     */
    public TextFunctionsTemplateWritingHandler() {};

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    @Override
    protected boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        writeTemplate(
            retrieveTextFunctionsTemplate(parameters),
            retrieveOutputDir(parameters),
            retrieveCharset(parameters),
            TextFunctionsTemplateGenerator.getInstance());

        return false;
    }

    /**
     * Writes the TextFunctions template.
     * @param template the template to write.
     * @param outputDir the output dir.
     * @param charset the file encoding.
     * @param generator the <code>TextFunctionsTemplateGenerator</code>
     * instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition outputDir != null
     * @precondition generator != null
     */
    protected void writeTemplate(
        @Nullable final TextFunctionsTemplate template,
        @NotNull final File outputDir,
        final Charset charset,
        @NotNull final TextFunctionsTemplateGenerator generator)
      throws  QueryJBuildException
    {
        if  (template != null)
        {
            try 
            {
                generator.write(template, outputDir, charset);
            }
            catch  (@NotNull final IOException ioException)
            {
                throw
                    new QueryJBuildException(
                        "Cannot write the TextFunctions template",
                        ioException);
            }
        }
    }

    /**
     * Retrieves the text functions template from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     * @precondition parameters != null
     */
    @NotNull
    protected TextFunctionsTemplate retrieveTextFunctionsTemplate(
        @NotNull final Map parameters)
    {
        return
            (TextFunctionsTemplate)
                parameters.get(
                    TextFunctionsTemplateBuildHandler.TEXT_FUNCTIONS_TEMPLATE);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return such folder.
     * @precondition parameters != null
     */
    @Nullable
    protected File retrieveOutputDir(@NotNull final Map parameters)
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
    @Nullable
    protected File retrieveOutputDir(
        @NotNull final Map parameters, @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveFunctionsFolder(
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters));
    }
}
