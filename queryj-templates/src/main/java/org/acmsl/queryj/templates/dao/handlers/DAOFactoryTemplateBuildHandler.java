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
 * Filename: DAOFactoryTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a DAO factory template using database metadata.
 *
 */
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.templates.dao.DAOFactoryTemplateFactory;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.templates.dao.DAOFactoryTemplate;
import org.acmsl.queryj.templates.dao.DAOFactoryTemplateGenerator;
import org.acmsl.queryj.templates.handlers.BasePerTableTemplateBuildHandler;
import org.acmsl.queryj.templates.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.List;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Builds a DAO factory template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class DAOFactoryTemplateBuildHandler
    extends BasePerTableTemplateBuildHandler<DAOFactoryTemplate, DAOFactoryTemplateFactory>
{
    /**
     * Creates a <code>DAOFactoryTemplateBuildHandler</code> instance.
     */
    public DAOFactoryTemplateBuildHandler() {}

    /**
     * Retrieves the {@link DAOFactoryTemplateGenerator} instance.
     * @return such instance.
     */
    @Override
    @NotNull
    protected DAOFactoryTemplateFactory retrieveTemplateFactory()
    {
        return DAOFactoryTemplateFactory.getInstance();
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param basePackage the base package.
     * @param engineName the engine name.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     */
    @NotNull
    @Override
    protected String retrievePackage(
        @NotNull final String tableName,
        @NotNull final String engineName,
        @NotNull final String basePackage,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveDAOFactoryPackage(
                basePackage, engineName);
    }

    /**
     * Stores the DAO factory template collection in given attribute map.
     * @param daoFactoryTemplates the DAO factory templates.
     * @param parameters the parameter map.
     */
    @Override
    protected void storeTemplates(
        @NotNull final List<DAOFactoryTemplate> daoFactoryTemplates,
        @NotNull final Map<String, List<DAOFactoryTemplate>> parameters)
    {
        parameters.put(
            TemplateMappingManager.DAO_FACTORY_TEMPLATES,
            daoFactoryTemplates);
    }
}
