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
 * Filename: TableTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes the table templates.
 *
 */
package org.acmsl.queryj.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.templates.BasePerTableTemplateContext;
import org.acmsl.queryj.templates.TableTemplate;
import org.acmsl.queryj.templates.TableTemplateGenerator;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Writes the table templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TableTemplateWritingHandler
    extends BasePerTableTemplateWritingHandler
                <TableTemplate, TableTemplateGenerator, BasePerTableTemplateContext>
{
    /**
     * Creates a TableTemplateWritingHandler.
     */
    public TableTemplateWritingHandler() {}

    /**
     * Writes the templates.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param threadCount the number of threads.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @Override
    protected void writeTemplates(
        @NotNull final Map parameters,
        @NotNull final String engineName,
        final int threadCount,
        @NotNull final File rootDir)
    throws QueryJBuildException
    {
        // Disabled
    }

    /**
     * Retrieves the template generator.
     * @return such instance.
     */
    @NotNull
    @Override
    protected TableTemplateGenerator retrieveTemplateGenerator(final boolean caching, final int threadCount)
    {
        return new TableTemplateGenerator(caching, threadCount);
    }

    /**
     * Retrieves the table templates from the attribute map.
     * @param parameters the parameter map.
     * @return the template array.
     */
    @SuppressWarnings("unchecked")
    @Nullable
    protected List<TableTemplate> retrieveTemplates(@NotNull final Map parameters)
    {
        return
            (List<TableTemplate>)
                parameters.get(
                    TableTemplateBuildHandler.TABLE_TEMPLATES);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param projectFolder the project folder.
     * @param projectPackage the project base package.
     * @param useSubfolders whether to use subfolders for tests, or
     * using a different package naming scheme.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     */
    @NotNull
    @Override
    protected File retrieveOutputDir(
        @NotNull final File projectFolder,
        @NotNull final String projectPackage,
        final boolean useSubfolders,
        @NotNull final String tableName,
        @NotNull final String engineName,
        @NotNull final Map parameters,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveTableFolder(
                projectFolder,
                projectPackage,
                useSubfolders);
    }
}
