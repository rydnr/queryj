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
 * Filename: BaseDAOTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a base DAO template using database metadata.
 *
 */
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.templates.dao.BaseDAOTemplateFactory;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.templates.dao.BaseDAOTemplate;
import org.acmsl.queryj.templates.handlers.BasePerTableTemplateBuildHandler;
import org.acmsl.queryj.templates.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
 * Builds a base DAO template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class BaseDAOTemplateBuildHandler
    extends  BasePerTableTemplateBuildHandler<BaseDAOTemplate, BaseDAOTemplateFactory>
{
    /**
     * Creates a <code>BaseDAOTemplateBuildHandler</code> instance.
     */
    public BaseDAOTemplateBuildHandler() {}

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    protected BaseDAOTemplateFactory retrieveTemplateFactory()
    {
        return BaseDAOTemplateFactory.getInstance();
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
            packageUtils.retrieveBaseDAOPackage(projectPackage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void storeTemplates(
        @NotNull final List<BaseDAOTemplate> templates, @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.BASE_DAO_TEMPLATES, templates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    protected BaseDAOTemplate createTemplate(
        @NotNull final BaseDAOTemplateFactory templateFactory,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String packageName,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        @NotNull final String tableName,
        @Nullable List<Row> staticContents,
        @NotNull final Map parameters)
      throws  QueryJBuildException
    {        
        @Nullable BaseDAOTemplate result = null;

        if  (isStaticTable(tableName, metadataManager))
        {
            @Nullable List<Row> t_cStaticValues =
                retrieveStaticContent(
                    parameters,
                    tableName,
                    metadataManager,
                    decoratorFactory);

            if (t_cStaticValues != null)
            {
                result =
                    templateFactory.createTemplate(
                        metadataManager,
                        customSqlProvider,
                        decoratorFactory,
                        packageName,
                        projectPackage,
                        repository,
                        header,
                        implementMarkerInterfaces,
                        jmx,
                        jndiLocation,
                        tableName,
                        t_cStaticValues);
            }
        }

        return result;
    }
}
