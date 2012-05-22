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
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TableTemplateGenerator;

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

/**
 * Builds a table template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TableTemplateBuildHandler
    extends    BasePerTableTemplateBuildHandler<TableTemplate, TableTemplateGenerator>
    implements TemplateBuildHandler
{
    /**
     * The table templates attribute name.
     */
    public static final String TABLE_TEMPLATES = "table.templates";

    /**
     * The table names attribute name.
     */
    public static final String TABLE_NAMES = "table.names";

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
        @NotNull final String engineName,
        @NotNull final String engineVersion,
        @NotNull final String quote,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final TableTemplateGenerator templateFactory,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        @Nullable final String[] tableNames)
        throws  QueryJBuildException
    {
        String[] t_astrTableNames = tableNames;

        if (   (t_astrTableNames == null)
            || (t_astrTableNames.length == 0))
        {
            t_astrTableNames = metadataManager.getTableNames();

            storeTableNames(t_astrTableNames, parameters);
        }

        super.buildTemplate(
            parameters,
            engineName,
            engineVersion,
            quote,
            metadataManager,
            customSqlProvider,
            templateFactory,
            projectPackage,
            repository,
            header,
            implementMarkerInterfaces,
            t_astrTableNames);
    }

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @Override
    @NotNull
    protected TableTemplateGenerator retrieveTemplateFactory()
    {
        return TableTemplateGenerator.getInstance();
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
     * @param tableNames the table names.
     * @param parameters the parameter map.
     */
    @SuppressWarnings("unchecked")
    protected void storeTableNames(
        @NotNull final String[] tableNames, @NotNull final Map parameters)
    {
        parameters.put(TABLE_NAMES, tableNames);
    }
}
