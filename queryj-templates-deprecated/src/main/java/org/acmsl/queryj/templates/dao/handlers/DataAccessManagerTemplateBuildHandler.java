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
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.api.PerRepositoryTemplateContext;
import org.acmsl.queryj.templates.dao.DataAccessManagerTemplateFactory;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.templates.dao.DataAccessManagerTemplate;
import org.acmsl.queryj.api.handlers.BasePerRepositoryTemplateBuildHandler;
import org.acmsl.queryj.api.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Builds a DataAccessManager template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class DataAccessManagerTemplateBuildHandler
    extends  BasePerRepositoryTemplateBuildHandler
                 <DataAccessManagerTemplate, DataAccessManagerTemplateFactory, PerRepositoryTemplateContext>
{
    /**
     * Creates a <code>DataAccessManagerTemplateBuildHandler</code> instance.
     */
    public DataAccessManagerTemplateBuildHandler() {}

    /**
     * Checks whether template generation is enabled for this kind of template.
     * @return <code>true</code> in such case.
     */
    @Override
    protected boolean isGenerationEnabled(
        @NotNull final CustomSqlProvider customSqlProvider, @NotNull final Map<String, ?> parameters)
    {
        return true;
    }

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @Override
    @NotNull
    protected DataAccessManagerTemplateFactory retrieveTemplateFactory()
    {
        return DataAccessManagerTemplateFactory.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     */
    @Override
    protected void storeTemplate(
        @NotNull final DataAccessManagerTemplate template,
        @NotNull final Map<String, List<DataAccessManagerTemplate>> parameters)
    {
        @NotNull final List<DataAccessManagerTemplate> list = new ArrayList<DataAccessManagerTemplate>(1);
        list.add(template);

        parameters.put(
            TemplateMappingManager.DATA_ACCESS_MANAGER_TEMPLATE,
            list);
    }
}
