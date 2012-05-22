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
 * Filename: DataAccessManagerTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a DataAccessManager template using database
 *              metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateContext;
import org.acmsl.queryj.tools.templates.dao.DataAccessManagerTemplate;
import org.acmsl.queryj.tools.templates.dao.DataAccessManagerTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.BasePerRepositoryTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Builds a DataAccessManager template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class DataAccessManagerTemplateBuildHandler
    extends  BasePerRepositoryTemplateBuildHandler
                 <DataAccessManagerTemplate, DataAccessManagerTemplateGenerator, BasePerRepositoryTemplateContext>
{
    /**
     * Creates a <code>DataAccessManagerTemplateBuildHandler</code> instance.
     */
    public DataAccessManagerTemplateBuildHandler() {}

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @NotNull
    protected DataAccessManagerTemplateGenerator retrieveTemplateFactory()
    {
        return DataAccessManagerTemplateGenerator.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    protected String retrievePackage(
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveDataAccessManagerPackage(projectPackage);
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
        @NotNull final DataAccessManagerTemplate template, @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.DATA_ACCESS_MANAGER_TEMPLATE,
            template);
    }
}
