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
 * Filename: BasePerCustomResultTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes per-custom-result templates.
 *
 */
package org.acmsl.queryj.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.templates.BasePerCustomResultTemplate;
import org.acmsl.queryj.templates.BasePerCustomResultTemplateContext;
import org.acmsl.queryj.templates.TemplateGenerator;

/*
 * Importing Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Writes <i>per-custom-result</i> templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerCustomResultTemplateWritingHandler
    <T extends BasePerCustomResultTemplate<C>, C extends BasePerCustomResultTemplateContext>
    extends    AbstractQueryJCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a {@link BasePerCustomResultTemplateWritingHandler}
     * instance.
     */
    public BasePerCustomResultTemplateWritingHandler() {}

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean handle(@NotNull final Map parameters)
      throws  QueryJBuildException
    {
        boolean result = true;

        MetadataManager t_MetadataManager = retrieveMetadataManager(parameters);

        if (t_MetadataManager != null)
        {
            writeTemplates(parameters, t_MetadataManager);
            result = false;
        }

        return result;
    }

    /**
     * Writes the templates.
     * @param parameters the parameters.
     * @param metadataManager the {@link MetadataManager} instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void writeTemplates(
        @NotNull final Map parameters, @NotNull final MetadataManager metadataManager)
      throws  QueryJBuildException
    {
        writeTemplates(
            retrieveTemplates(parameters),
            retrieveTemplateGenerator(),
            retrieveCustomSqlProvider(parameters),
            metadataManager,
            metadataManager.getEngineName(),
            retrieveCharset(parameters),
            parameters);
    }

    /**
     * Writes the templates.
     * @param templates the templates.
     * @param templateGenerator the template generator.
     * @param customSqlProvider the custom sql provider.
     * @param metadataManager the metadata manager.
     * @param engineName the engine name.
     * @param charset the file encoding.
     * @param parameters the parameter map.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void writeTemplates(
        @NotNull final List<T> templates,
        @NotNull final TemplateGenerator<T, C> templateGenerator,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final String engineName,
        @NotNull final Charset charset,
        @NotNull final Map parameters)
      throws  QueryJBuildException
    {
        try
        {
            for  (T t_Template : templates)
            {
                if  (t_Template != null)
                {
                    C t_Context = t_Template.getTemplateContext();

                    Result t_Result = t_Context.getResult();

                    File t_OutputDir =
                        retrieveOutputDir(
                            t_Result,
                            customSqlProvider,
                            metadataManager,
                            engineName,
                            parameters);

                    templateGenerator.write(
                        t_Template, t_OutputDir, charset);
                }
            }
        }
        catch  (@NotNull final IOException ioException)
        {
            throw
                new QueryJBuildException(
                    "Cannot write the templates", ioException);
        }
    }

    /**
     * Retrieves the template generator.
     * @return such instance.
     */
    protected abstract TemplateGenerator<T,C> retrieveTemplateGenerator();

    /**
     * Retrieves the templates from the attribute map.
     * @param parameters the parameter map.
     * @return the templates.
     * @throws QueryJBuildException if the template retrieval process if faulty.
     */
    @NotNull
    protected abstract List<T> retrieveTemplates(@NotNull final Map parameters)
      throws  QueryJBuildException;

    /**
     * Retrieves the output dir from the attribute map.
     * @param resultElement the result element.
     * @param customSqlProvider the custom sql provider.
     * @param metadataManager the metadata manager.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws QueryJBuildException if the output-dir retrieval process if faulty.
     */
    @NotNull
    protected File retrieveOutputDir(
        @NotNull final Result resultElement,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final String engineName,
        @NotNull final Map parameters)
      throws  QueryJBuildException
    {
        return
            retrieveOutputDir(
                resultElement,
                customSqlProvider,
                metadataManager,
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters),
                engineName,
                parameters,
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param resultElement the result element.
     * @param customSqlProvider the custom sql provider.
     * @param metadataManager the metadata manager.
     * @param projectFolder the project folder.
     * @param projectPackage the project base package.
     * @param useSubFolders whether to use subFolders for tests, or
     * using a different package naming scheme.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @throws QueryJBuildException if the output-dir retrieval process if faulty.
     */
    @NotNull
    protected abstract File retrieveOutputDir(
        @NotNull final Result resultElement,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final File projectFolder,
        @NotNull final String projectPackage,
        final boolean useSubFolders,
        @NotNull final String engineName,
        @NotNull final Map parameters,
        @NotNull final PackageUtils packageUtils)
      throws  QueryJBuildException;
}
