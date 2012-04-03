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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: BaseResultSetExtractorTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes BaseResultSetExtractor templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplate;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.BasePerRepositoryTemplateWritingHandler;
import org.acmsl.queryj.tools.templates.dao.BaseResultSetExtractorTemplateGenerator;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/**
 * Writes {@link org.acmsl.queryj.tools.templates.dao.BaseResultSetExtractorTemplate} templates.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class BaseResultSetExtractorTemplateWritingHandler
    extends  BasePerRepositoryTemplateWritingHandler
{
    /**
     * Retrieves the template generator.
     * @return such instance.
     */
    @NotNull
    protected BasePerRepositoryTemplateGenerator retrieveTemplateGenerator()
    {
        return BaseResultSetExtractorTemplateGenerator.getInstance();
    }

    /**
     * Retrieves the template from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     */
    @NotNull
    protected BasePerRepositoryTemplate retrieveTemplate(
        @NotNull final Map parameters)
    {
        return
            (BasePerRepositoryTemplate)
                parameters.get(
                    TemplateMappingManager.BASE_RESULTSET_EXTRACTOR_TEMPLATE);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param projectFolder the project folder.
     * @param projectPackage the project base package.
     * @param useSubFolders whether to use sub folders for tests, or
     * using a different package naming scheme.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @param packageUtils the {@link PackageUtils} instance.
     * @return such folder.
     * @precondition engineName != null
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected File retrieveOutputDir(
        @NotNull final File projectFolder,
        final String projectPackage,
        final boolean useSubFolders,
        final String engineName,
        final Map parameters,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveBaseResultSetExtractorFolder(
                projectFolder,
                projectPackage,
                useSubFolders);
    }
}
