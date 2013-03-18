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
 * Filename: TableTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a table template using database metadata.
 *
 */
package org.acmsl.queryj.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.templates.TableTemplateFactory;
import org.acmsl.queryj.templates.dao.DAOTemplateUtils;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.templates.TableTemplate;

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
import org.jetbrains.annotations.Nullable;

/**
 * Builds a table template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class TableTemplateBuildHandler
    extends    BasePerTableTemplateBuildHandler<TableTemplate, TableTemplateFactory>
    implements TemplateBuildHandler
{
    /**
     * The table templates attribute name.
     */
    public static final String TABLE_TEMPLATES = "table.templates";

    /**
     * The tables attribute name.
     */
    public static final String TABLES = "tables";

    /**
     * Creates a TableTemplateBuildHandler.
     */
    public TableTemplateBuildHandler() {}

    /**
     * {@inheritDoc}
     */
    @Override
    protected void buildTemplate(
        @NotNull final Map parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final TableTemplateFactory templateFactory,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @Nullable final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        final boolean disableGenerationTimestamps,
        final boolean disableNotNullAnnotations,
        final boolean disableCheckthreadAnnotations,
        @NotNull final List<Table> tables,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils)
        throws  QueryJBuildException
    {
        List<Table> t_lTables = tables;

        if (t_lTables.size() == 0)
        {
            t_lTables = metadataManager.getTableDAO().findAllTables();

            storeTables(t_lTables, parameters);
        }

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
            disableGenerationTimestamps,
            disableNotNullAnnotations,
            disableCheckthreadAnnotations,
            t_lTables,
            decoratorFactory,
            daoTemplateUtils);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    protected TableTemplateFactory retrieveTemplateFactory()
    {
        return TableTemplateFactory.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unused")
    protected String retrievePackage(
        @NotNull final String tableName,
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils) throws QueryJBuildException
    {
        return packageUtils.retrieveTablePackage(projectPackage);
    }

    /**
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     */
    @SuppressWarnings("unchecked")
    protected void storeTemplates(
        @NotNull final List<TableTemplate> templates, @NotNull final Map parameters)
    {
        parameters.put(TABLE_TEMPLATES, templates);
    }

    /**
     * Stores the table name collection in given attribute map.
     * @param tables the tables.
     * @param parameters the parameter map.
     */
    @SuppressWarnings("unchecked")
    protected void storeTables(
        @NotNull final List<Table> tables, @NotNull final Map parameters)
    {
        parameters.put(TABLES, tables);
    }
}
