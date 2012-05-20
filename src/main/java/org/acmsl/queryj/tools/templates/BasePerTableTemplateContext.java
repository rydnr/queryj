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
 * Filename: BasePerTableTemplateContext.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Context used by per-table templates.
 *
 * Date: 5/20/12
 * Time: 8:05 AM
 *
 */
package org.acmsl.queryj.tools.templates;

import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.vo.Row;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Context used by per-table templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/20
 */
public class BasePerTableTemplateContext
    extends AbstractTemplateContext
{
    private static final long serialVersionUID = -7439946925532182308L;
    /**
     * The table name.
     */
    private String tableName;

    /**
     * The static contents.
     */
    private List<Row> m__lStaticValues;

    /**
     * Creates a {@link BasePerTableTemplateContext} with given information.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param header the header.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param implementMarkerInterfaces whether to implement marker interfaces or not.
     * @param jmx whether to include JMX support.
     * @param tableName the table name.
     * @param staticValues the static rows, if the table is marked as <code>@static</code>.
     */
    public BasePerTableTemplateContext(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String header,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String packageName,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String tableName,
        @Nullable final List<Row> staticValues)
    {
        super(
            metadataManager,
            customSqlProvider,
            header,
            decoratorFactory,
            packageName,
            basePackageName,
            repositoryName,
            implementMarkerInterfaces,
            jmx);

        immutableSetTableName(tableName);
        immutableSetStaticValues(staticValues);
    }

    /**
     * Specifies the table name.
     * @param tableName such name.
     */
    protected final void immutableSetTableName(@NotNull final String tableName)
    {
        this.tableName = tableName;
    }

    /**
     * Specifies the table name.
     * @param tableName such name.
     */
    @SuppressWarnings("unused")
    protected void setTableName(@NotNull final String tableName)
    {
        immutableSetTableName(tableName);
    }

    /**
     * Retrieves the table name.
     * @return such name.
     */
    @NotNull
    public String getTableName()
    {
        return tableName;
    }

    /**
     * Specifies the static values.
     * @param values such values.
     */
    protected final void immutableSetStaticValues(@Nullable final List<Row> values)
    {
        m__lStaticValues = values;
    }

    /**
     * Specifies the static values.
     * @param values such values.
     */
    @SuppressWarnings("unused")
    protected void setStaticValues(@Nullable final List<Row> values)
    {
        immutableSetStaticValues(values);
    }

    /**
     * Retrieves the static values.
     * @return such values.
     */
    protected final List<Row> immutableGetStaticValues()
    {
        return m__lStaticValues;
    }

    /**
     * Retrieves the static values.
     * @return such values.
     */
    @Nullable
    public List<Row> getStaticValues()
    {
        @Nullable List<Row> result = null;

        @Nullable List<Row> t_lRows = immutableGetStaticValues();

        if (t_lRows != null)
        {
            result = new ArrayList<Row>(t_lRows.size());

            Collections.copy(result, t_lRows);
        }

        return result;
    }
}
