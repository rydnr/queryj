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
 * Filename: BaseDAOFactoryTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a base DAO factory templates using database metadata.
 *
 */
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.templates.dao.DAOFactoryTemplateGenerator;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.templates.dao.BaseDAOFactoryTemplate;
import org.acmsl.queryj.templates.dao.BaseDAOFactoryTemplateGenerator;
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

/**
 * Builds base DAO factory templates using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class BaseDAOFactoryTemplateBuildHandler
    extends  BasePerTableTemplateBuildHandler<BaseDAOFactoryTemplate, BaseDAOFactoryTemplateGenerator>
{
    /**
     * Creates a <code>BaseDAOFactoryTemplateBuildHandler</code> instance.
     */
    public BaseDAOFactoryTemplateBuildHandler() {}

    protected void buildTemplate(
        @NotNull final Map parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final BaseDAOFactoryTemplateGenerator templateFactory,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        @NotNull final List<Table> tables)
        throws QueryJBuildException
    {
        super.buildTemplate(
            parameters,
            metadataManager,
            customSqlProvider,
            templateFactory,
            projectPackage,
            repository,
            header,
            implementMarkerInterfaces,
            jmx,
            jndiLocation,
            tables);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    protected BaseDAOFactoryTemplateGenerator retrieveTemplateFactory()
    {
        return BaseDAOFactoryTemplateGenerator.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    protected String retrievePackage(
        @NotNull final String tableName,
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils)
      throws QueryJBuildException
    {
        return
            packageUtils.retrieveBaseDAOFactoryPackage(projectPackage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void storeTemplates(
        @NotNull final List<BaseDAOFactoryTemplate> templates, @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.BASE_DAO_FACTORY_TEMPLATES, templates);
    }
}
