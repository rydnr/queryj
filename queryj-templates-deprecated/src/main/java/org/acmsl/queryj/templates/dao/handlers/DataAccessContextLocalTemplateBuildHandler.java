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
 * Filename: DataAccessContextLocalTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds the Spring DAO declaration file.
 *
 */
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.api.PerRepositoryTemplateContext;
import org.acmsl.queryj.templates.dao.DataAccessContextLocalTemplateFactory;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.templates.dao.DataAccessContextLocalTemplate;
import org.acmsl.queryj.api.handlers.BasePerRepositoryTemplateBuildHandler;
import org.acmsl.queryj.api.TemplateMappingManager;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Builds the Spring DAO declaration file.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class DataAccessContextLocalTemplateBuildHandler
    extends  BasePerRepositoryTemplateBuildHandler
                 <DataAccessContextLocalTemplate, DataAccessContextLocalTemplateFactory, PerRepositoryTemplateContext>
{
    /**
     * Creates a <code>DataAccessContextLocalTemplateBuildHandler</code>
     * instance.
     */
    public DataAccessContextLocalTemplateBuildHandler() {}

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected DataAccessContextLocalTemplateFactory retrieveTemplateFactory()
    {
        return DataAccessContextLocalTemplateFactory.getInstance();
    }

    /**
     * Retrieves the package name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     */
    @NotNull
    @Override
    protected String retrievePackage(
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils)
    {
        return "";
    }

    /**
     * Stores the template in given attribute map.
     * @param template the template.
     * @param parameters the parameter map.
     */
    @Override
    protected void storeTemplate(
        @NotNull final DataAccessContextLocalTemplate template,
        @NotNull final Map<String, List<DataAccessContextLocalTemplate>> parameters)
    {
        @NotNull final List<DataAccessContextLocalTemplate> list = new ArrayList<DataAccessContextLocalTemplate>(1);
        list.add(template);

        parameters.put(
            TemplateMappingManager.DATAACCESSCONTEXTLOCAL_TEMPLATE,
            list);
    }
}
