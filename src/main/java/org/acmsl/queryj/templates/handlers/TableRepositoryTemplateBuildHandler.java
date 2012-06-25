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
 * Filename: TableRepositoryTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a table repository using database metadata.
 *
 */
package org.acmsl.queryj.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.templates.BasePerRepositoryTemplateContext;
import org.acmsl.queryj.templates.TableRepositoryTemplate;
import org.acmsl.queryj.templates.TableRepositoryTemplateGenerator;
import org.acmsl.queryj.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.PackageUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Builds a table repository using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TableRepositoryTemplateBuildHandler
    extends  BasePerRepositoryTemplateBuildHandler
                 <TableRepositoryTemplate, TableRepositoryTemplateGenerator, BasePerRepositoryTemplateContext>
{
    /**
     * Retrieves the per-repository template factory.
     * @return such instance.
     */
    @NotNull
    protected TableRepositoryTemplateGenerator retrieveTemplateFactory()
    {
        return TableRepositoryTemplateGenerator.getInstance();
    }

    /**
     * Retrieves the package name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    @NotNull
    protected String retrievePackage(
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils)
    {
        return packageUtils.retrieveTableRepositoryPackage(projectPackage);
    }

    /**
     * Stores the template in given attribute map.
     * @param template the template.
     * @param parameters the parameter map.
     * @precondition template != null
     * @precondition parameters != null
     */
    @SuppressWarnings("unchecked")
    protected void storeTemplate(
        @NotNull final TableRepositoryTemplate template, @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.TABLE_REPOSITORY_TEMPLATE,
            template);
    }
}
