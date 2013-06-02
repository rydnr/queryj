/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: DAOListenerImplTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a DAO listener implementations if requested.
 *
 */
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.templates.BasePerRepositoryTemplateContext;
import org.acmsl.queryj.templates.dao.DAOListenerImplTemplate;
import org.acmsl.queryj.templates.dao.DAOListenerImplTemplateFactory;
import org.acmsl.queryj.templates.handlers.BasePerRepositoryTemplateBuildHandler;
import org.acmsl.queryj.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.PackageUtils;

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
 * Builds a DAO listener implementations if requested.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
@ThreadSafe
public class DAOListenerImplTemplateBuildHandler
    extends  BasePerRepositoryTemplateBuildHandler
                 <DAOListenerImplTemplate, DAOListenerImplTemplateFactory, BasePerRepositoryTemplateContext>
{
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
     * Retrieves the per-repository template factory.
     * @return such instance.
     */
    @Override
    @NotNull
    protected DAOListenerImplTemplateFactory retrieveTemplateFactory()
    {
        return DAOListenerImplTemplateFactory.getInstance();
    }

    /**
     * Retrieves the package name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the {@link PackageUtils} instance.
     * @return the package name.
     */
    @NotNull
    @Override
    protected String retrievePackage(
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveDAOListenerImplPackage(
                projectPackage, engineName);
    }

    /**
     * Stores the template in given attribute map.
     * @param template the template.
     * @param parameters the parameter map.
     */
    @Override
    protected void storeTemplate(
        @NotNull final DAOListenerImplTemplate template,
        @NotNull final Map<String, List<DAOListenerImplTemplate>> parameters)
    {
        @NotNull final List<DAOListenerImplTemplate> list = new ArrayList<DAOListenerImplTemplate>(1);
        list.add(template);

        parameters.put(
            TemplateMappingManager.DAO_LISTENER_IMPL_TEMPLATE,
            list);
    }
}
