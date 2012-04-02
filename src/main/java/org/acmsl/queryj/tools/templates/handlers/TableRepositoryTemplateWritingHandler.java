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
 * Filename: TableRepositoryTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes the table repository.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplate;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.BasePerRepositoryTemplateWritingHandler;
import org.acmsl.queryj.tools.templates.handlers.TableRepositoryTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TableRepositoryTemplateGenerator;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/**
 * Writes the table repository.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TableRepositoryTemplateWritingHandler
    extends  BasePerRepositoryTemplateWritingHandler
{
    /**
     * Retrieves the template generator.
     * @return such instance.
     */
    @NotNull
    @Override
    protected BasePerRepositoryTemplateGenerator retrieveTemplateGenerator()
    {
        return TableRepositoryTemplateGenerator.getInstance();
    }

    /**
     * Retrieves the template from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     */
    @NotNull
    @Override
    protected BasePerRepositoryTemplate retrieveTemplate(
        @NotNull final Map parameters)
    {
        return
            (BasePerRepositoryTemplate)
                parameters.get(
                    TemplateMappingManager.TABLE_REPOSITORY_TEMPLATE);
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
     * @precondition engineName != null
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    @Nullable
    @Override
    protected File retrieveOutputDir(
        @NotNull final File projectFolder,
        final String projectPackage,
        final boolean useSubfolders,
        final String engineName,
        final Map parameters,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveTableRepositoryFolder(
                projectFolder,
                projectPackage,
                useSubfolders);
    }
}
