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
 * Filename: BasePerCustomSqlTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes per-custom-sql templates.
 *
 */
package org.acmsl.queryj.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.templates.BasePerCustomSqlTemplate;
import org.acmsl.queryj.templates.BasePerCustomSqlTemplateContext;
import org.acmsl.queryj.templates.BasePerCustomSqlTemplateGenerator;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Writes <i>per-custom-sql</i> templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@SuppressWarnings("unused")
public abstract class BasePerCustomSqlTemplateWritingHandler
    <T extends BasePerCustomSqlTemplate<C>, C extends BasePerCustomSqlTemplateContext>
extends    AbstractQueryJCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a <code>BasePerCustomSqlTemplateWritingHandler</code> instance.
     */
    public BasePerCustomSqlTemplateWritingHandler() {}

    /**
     * Handles given information.
     *
     *
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    @Override
    protected boolean handle(@NotNull final Map parameters)
      throws  QueryJBuildException
    {
        boolean result = true;

        MetadataManager t_MetadataManager = retrieveMetadataManager(parameters);

        if (t_MetadataManager != null)
        {
            writeTemplate(parameters, t_MetadataManager);
            result = false;
        }

        return result;
    }

    /**
     * Writes the template.
     * @param parameters the parameters.
     * @param metadataManager the {@link MetadataManager} instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void writeTemplate(
        @NotNull final Map parameters, @NotNull final MetadataManager metadataManager)
      throws  QueryJBuildException
    {
        writeTemplate(parameters, metadataManager.getEngineName());
    }

    /**
     * Writes the template.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void writeTemplate(
        @NotNull final Map parameters, @NotNull final String engineName)
      throws  QueryJBuildException
    {
        writeTemplate(
            retrieveTemplate(parameters),
            retrieveOutputDir(engineName, parameters),
            retrieveCharset(parameters),
            retrieveTemplateGenerator());
    }
            
    /**
     * Writes the templates.
     * @param template the template.
     * @param outputDir the output dir.
     * @param charset the file encoding.
     * @param templateGenerator the template generator.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void writeTemplate(
        @NotNull final T template,
        @NotNull final File outputDir,
        @NotNull final Charset charset,
        @NotNull final BasePerCustomSqlTemplateGenerator<T, C> templateGenerator)
      throws  QueryJBuildException
    {
        try 
        {
            templateGenerator.write(template, outputDir, charset);
        }
        catch  (@NotNull final IOException ioException)
        {
            throw
                new QueryJBuildException(
                    "Cannot write the template", ioException);
        }
    }

    /**
     * Retrieves the template generator.
     * @return such instance.
     */
    @NotNull
    protected abstract BasePerCustomSqlTemplateGenerator<T,C> retrieveTemplateGenerator();

    /**
     * Retrieves the template from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     */
    @NotNull
    protected abstract T retrieveTemplate(@NotNull final Map parameters);

    /**
     * Retrieves the output dir from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return such folder.
     */
    @NotNull
    protected File retrieveOutputDir(
        @NotNull final String engineName, @NotNull final Map parameters)
    {
        return
            retrieveOutputDir(
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters),
                engineName,
                parameters,
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param projectFolder the project folder.
     * @param projectPackage the project base package.
     * @param useSubfolders whether to use subfolders for tests, or
     * using a different package naming scheme.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     */
    @NotNull
    protected abstract File retrieveOutputDir(
        @NotNull final File projectFolder,
        @NotNull final String projectPackage,
        final boolean useSubfolders,
        @NotNull final String engineName,
        @NotNull final Map parameters,
        @NotNull final PackageUtils packageUtils);
}
