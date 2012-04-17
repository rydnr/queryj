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
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.TemplateGenerator;
import org.acmsl.queryj.tools.templates.dao.CustomResultSetExtractorTemplate;
import org.acmsl.queryj.tools.templates.dao.CustomResultSetExtractorTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.BasePerCustomResultTemplateWritingHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/**
 * Writes ResultSetExtractor templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class CustomResultSetExtractorTemplateWritingHandler<T extends CustomResultSetExtractorTemplate>
    extends  BasePerCustomResultTemplateWritingHandler<T>
{
    /**
     * Creates a {@link CustomResultSetExtractorTemplateWritingHandler}
     * instance.
     */
    public CustomResultSetExtractorTemplateWritingHandler() {}

    /**
     * Retrieves the template generator.
     * @return such instance.
     */
    @NotNull
    @Override
    protected TemplateGenerator<T> retrieveTemplateGenerator()
    {
        return CustomResultSetExtractorTemplateGenerator.getInstance();
    }

    /**
     * Retrieves the templates from the attribute map.
     * @param parameters the parameter map.
     * @return the templates.
     */
    @NotNull
    @Override
    protected T[] retrieveTemplates(
        @NotNull final Map parameters)
    {
        return
            (T[])
                parameters.get(
                    TemplateMappingManager
                        .CUSTOM_RESULTSET_EXTRACTOR_TEMPLATES);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param resultElement the result element.
     * @param customSqlProvider the custom sql provider.
     * @param metadataManager the metadata manager.
     * @param projectFolder the project folder.
     * @param projectPackage the project base package.
     * @param useSubfolders whether to use subfolders for tests, or
     * using a different package naming scheme.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @param packageUtils the {@link PackageUtils} instance.
     * @return such folder.
     */
    @NotNull
    @Override
    protected File retrieveOutputDir(
        final Result resultElement,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        @NotNull final File projectFolder,
        final String projectPackage,
        final boolean useSubfolders,
        @NotNull final String engineName,
        final Map parameters,
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
     * @precondition engineName != null
     * @precondition projectOutputDir != null
     * @precondition projectPackage != null
     * @precondition packageUtils != null
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
            packageUtils.retrieveCustomResultSetExtractorFolder(
                projectOutputDir,
                projectPackage,
                engineName,
                useSubfolders);
    }
}
