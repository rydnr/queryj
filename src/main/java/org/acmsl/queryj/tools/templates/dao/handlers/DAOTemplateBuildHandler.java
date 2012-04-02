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
 * Filename: DAOTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a DAO template using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.DAOTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.BasePerTableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Builds a DAO template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class DAOTemplateBuildHandler
    extends  BasePerTableTemplateBuildHandler
{
    /**
     * Creates a <code>DAOTemplateBuildHandler</code> instance.
     */
    public DAOTemplateBuildHandler() {};

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @NotNull
    protected BasePerTableTemplateFactory retrieveTemplateFactory()
    {
        return DAOTemplateGenerator.getInstance();
    }

    /**
     * Retrieves the package name.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String tableName,
        @NotNull final String engineName,
        final String projectPackage,
        @NotNull final PackageUtils packageUtils)
    {
        return packageUtils.retrieveDAOPackage(projectPackage, engineName);
    }

    /**
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     * @precondition templates != null
     * @precondition parameters != null
     */
    protected void storeTemplates(
        final BasePerTableTemplate[] templates, @NotNull final Map parameters)
    {
        parameters.put(TemplateMappingManager.DAO_TEMPLATES, templates);
    }
}
