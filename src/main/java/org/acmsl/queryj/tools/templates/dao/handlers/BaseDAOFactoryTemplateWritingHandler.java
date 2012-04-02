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
 * Filename: BaseDAOFactoryTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes base DAO factory templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.BaseDAOFactoryTemplateGenerator;
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.BasePerTableTemplateWritingHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/**
 * Writes base DAO factory templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class BaseDAOFactoryTemplateWritingHandler
    extends  BasePerTableTemplateWritingHandler
{
    /**
     * Creates a <code>BaseDAOFactoryTemplateWritingHandler</code> instance.
     */
    public BaseDAOFactoryTemplateWritingHandler() {}

    /**
     * Retrieves the template generator.
     * @return such instance.
     */
    @NotNull
    @Override
    protected BasePerTableTemplateGenerator retrieveTemplateGenerator()
    {
        return BaseDAOFactoryTemplateGenerator.getInstance();
    }

    /**
     * Retrieves the templates from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     */
    @NotNull
    @Override
    protected BasePerTableTemplate[] retrieveTemplates(
        @NotNull final Map parameters)
    {
        return
            (BasePerTableTemplate[])
                parameters.get(
                    TemplateMappingManager.BASE_DAO_FACTORY_TEMPLATES);
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
     * @precondition projectFolder != null
     * @precondition projectPackage != null
     * @precondition engineName != null
     * @precondition packageUtils != null
     */
    @Nullable
    @Override
    protected File retrieveOutputDir(
        @NotNull final File projectFolder,
        final String projectPackage,
        final boolean useSubfolders,
        final String tableName,
        final String engineName,
        final Map parameters,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveBaseDAOFactoryFolder(
                projectFolder,
                projectPackage,
                useSubfolders);
    }
}
