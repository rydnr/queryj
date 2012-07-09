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
 * Filename: TableTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate table repositories according to database
 *              metadata.
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.Row;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * Is able to generate Table repositories according to database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TableTemplateFactory
    implements  BasePerTableTemplateFactory<TableTemplate>,
                Singleton
{
    /**
     * Singleton implementation to avoid double-locking check.
     */
    protected static final class TableTemplateFactorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        protected static final TableTemplateFactory SINGLETON = new TableTemplateFactory();
    }

    /**
     * Retrieves the {@link TableTemplateFactory} singleton.
     */
    public static TableTemplateFactory getInstance()
    {
        return TableTemplateFactorySingletonContainer.SINGLETON;
    }

    /**
     * Generates a <i>per-table</i> template.
     *
     * @param metadataManager           the metadata manager.
     * @param customSqlProvider         the CustomSqlProvider instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param packageName               the package name.
     * @param basePackageName           the base package name.
     * @param repositoryName            the name of the repository.
     * @param header                    the header.
     * @param implementMarkerInterfaces whether to implement marker
     *                                  interfaces.
     * @param jmx                       whether to include JMX support.
     * @param jndiLocation              the JNDI path of the {@link javax.sql.DataSource}.
     * @param tableName                 the table name.
     * @param staticContents            the table's static contents (optional).
     * @return a template.
     */
    @Override
    public TableTemplate createTemplate(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String packageName,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        @NotNull final String tableName,
        @Nullable final List<Row> staticContents)
    {
        return
            new TableTemplate(
                new BasePerTableTemplateContext(
                    metadataManager,
                    customSqlProvider,
                    header,
                    decoratorFactory,
                    packageName,
                    basePackageName,
                    repositoryName,
                    implementMarkerInterfaces,
                    jmx,
                    jndiLocation,
                    tableName,
                    staticContents));
    }
}