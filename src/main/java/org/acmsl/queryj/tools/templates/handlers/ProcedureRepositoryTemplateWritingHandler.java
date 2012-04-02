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
 * Filename: ProcedureRepositoryTemplateWritingHandler.java
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
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.templates.handlers.ProcedureRepositoryTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateWritingHandler;
import org.acmsl.queryj.tools.templates.ProcedureRepositoryTemplate;
import org.acmsl.queryj.tools.templates.ProcedureRepositoryTemplateGenerator;
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
 * Writes the procedure repository.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class ProcedureRepositoryTemplateWritingHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a <code>ProcedureRepositoryTemplateWritingHandler</code>
     * instance.
     */
    public ProcedureRepositoryTemplateWritingHandler() {};

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    @Override
    protected boolean handle(@NotNull final Map parameters)
        throws QueryJBuildException
    {
        writeTemplate(
            retrieveProcedureRepositoryTemplate(parameters),
            retrieveProjectOutputDir(parameters),
            retrieveCharset(parameters),
            ProcedureRepositoryTemplateGenerator.getInstance());

        return false;
    }

    /**
     * Writes the <code>ProcedureRepository</code> template.
     * @param template the template.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @param generator the generator.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected void writeTemplate(
        @Nullable final ProcedureRepositoryTemplate template,
        @NotNull final File outputDir,
        final Charset charset,
        @NotNull final ProcedureRepositoryTemplateGenerator generator)
      throws QueryJBuildException
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
                        "Cannot write the ProcedureRepository template",
                        ioException);
            }
        }
    }

    /**
     * Retrieves the procedure repository template from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     * @precondition parameters != null
     */
    @NotNull
    protected ProcedureRepositoryTemplate retrieveProcedureRepositoryTemplate(
        @NotNull final Map parameters)
    {
        return
            (ProcedureRepositoryTemplate)
                parameters.get(
                    ProcedureRepositoryTemplateBuildHandler
                        .PROCEDURE_REPOSITORY_TEMPLATE);
    }
}
