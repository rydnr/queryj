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
 * Filename: ResultSetExtractorTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes ResultSetExtractor templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.dao.ResultSetExtractorTemplate;
import org.acmsl.queryj.tools.templates.dao.ResultSetExtractorTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TemplateWritingHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Writes ResultSetExtractor templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class ResultSetExtractorTemplateWritingHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a {@link ResultSetExtractorTemplateWritingHandler}
     * instance..
     */
    public ResultSetExtractorTemplateWritingHandler() {}

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

        @Nullable final MetadataManager t_MetadataManager =
            retrieveMetadataManager(parameters);

        if (t_MetadataManager != null)
        {
            writeTemplates(
                parameters,
                t_MetadataManager,
                retrieveCharset(parameters),
                retrieveTemplates(parameters),
                ResultSetExtractorTemplateGenerator.getInstance());

            result = false;
        }

        return result;
    }

    /**
     * Writes the {@link ResultSetExtractorTemplate} templates.
     * @param parameters the parameters.
     * @param charset the file encoding.
     * @param templates the templates.
     * @param templateGenerator the template generator.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void writeTemplates(
        @NotNull final Map parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final Charset charset,
        @NotNull final List<ResultSetExtractorTemplate> templates,
        @NotNull final ResultSetExtractorTemplateGenerator templateGenerator)
      throws  QueryJBuildException
    {
        try 
        {
            for  (ResultSetExtractorTemplate t_Template : templates)
            {
                if  (t_Template != null)
                {
                    templateGenerator.write(
                        t_Template,
                        retrieveOutputDir(
                            metadataManager.getEngineName(),
                            t_Template.getTemplateContext().getTableName(),
                            parameters),
                        charset);
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
     * Retrieves the templates from the attribute map.
     * @param parameters the parameter map.
     * @return the templates.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected List<ResultSetExtractorTemplate> retrieveTemplates(
        @NotNull final Map parameters)
    {
        return
            (List<ResultSetExtractorTemplate>)
                parameters.get(
                    TemplateMappingManager.RESULTSET_EXTRACTOR_TEMPLATES);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param engineName the engine name.
     * @param tableName the table name.
     * @param parameters the parameter map.
     * @return such folder.
     * @precondition engineName != null
     * @precondition tableName != null
     * @precondition parameters != null
     */
    @NotNull
    protected File retrieveOutputDir(
        @NotNull final String engineName, @NotNull final String tableName, @NotNull final Map parameters)
    {
        return
            retrieveOutputDir(
                engineName,
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                tableName,
                retrieveUseSubfoldersFlag(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param engineName the engine name.
     * @param projectOutputDir the project output dir.
     * @param projectPackage the project package.
     * @param tableName the table name.
     * @param subFolders whether to use subfolders or not.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     */
    @NotNull
    protected File retrieveOutputDir(
        @NotNull final String engineName,
        @NotNull final File projectOutputDir,
        @NotNull final String projectPackage,
        @NotNull final String tableName,
        final boolean subFolders,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveResultSetExtractorFolder(
                projectOutputDir,
                projectPackage,
                engineName,
                tableName,
                subFolders);
    }
}
