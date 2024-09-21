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
 * Filename: CustomResultSetExtractorTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes ResultSetExtractor templates.
 *
 */
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.api.PerCustomResultTemplateContext;
import org.acmsl.queryj.templates.dao.CustomRowMapperTemplate;
import org.acmsl.queryj.templates.dao.CustomRowMapperTemplateGenerator;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.templates.dao.CustomResultSetExtractorTemplateGenerator;
import org.acmsl.queryj.api.handlers.BasePerCustomResultTemplateWritingHandler;
import org.acmsl.queryj.api.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.List;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Writes ResultSetExtractor templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CustomRowMapperTemplateWritingHandler
    extends  BasePerCustomResultTemplateWritingHandler
                 <CustomRowMapperTemplate,
                     PerCustomResultTemplateContext,
                     CustomRowMapperTemplateGenerator>
{
    /**
     * Creates a {@link CustomRowMapperTemplateWritingHandler}
     * instance.
     */
    public CustomRowMapperTemplateWritingHandler() {}

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected CustomRowMapperTemplateGenerator retrieveTemplateGenerator(
        final boolean caching, final int threadCount)
    {
        return new CustomRowMapperTemplateGenerator(caching, threadCount);
    }

    /**
     * Retrieves the templates from the attribute map.
     * @param parameters the parameter map.
     * @return the templates.
     */
    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    protected List<CustomRowMapperTemplate> retrieveTemplates(
        @NotNull final Map parameters)
    {
        return
            (List<CustomRowMapperTemplate>)
                parameters.get(
                    TemplateMappingManager.CUSTOM_ROW_MAPPER_TEMPLATES);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected File retrieveOutputDir(
        @NotNull final Result resultElement,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final File projectFolder,
        @NotNull final String projectPackage,
        final boolean useSubfolders,
        @NotNull final String engineName,
        @NotNull final Map<String, ?> parameters,
        @NotNull final PackageUtils packageUtils)
    {
        return
            retrieveOutputDir(
                projectFolder,
                projectPackage,
                useSubfolders,
                engineName,
                packageUtils);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param engineName the engine name.
     * @param projectOutputDir the project output dir.
     * @param projectPackage the project package.
     * @param useSubfolders whether to use subfolders or not.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     */
    @NotNull
    protected File retrieveOutputDir(
        @NotNull final File projectOutputDir,
        final String projectPackage,
        final boolean useSubfolders,
        @NotNull final String engineName,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveCustomRowMapperFolder(
                projectOutputDir,
                projectPackage,
                engineName,
                useSubfolders);
    }
}
