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
 * Filename: BasePerTableTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents entities able to create per-table templates.
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.Row;

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
 * Represents entities able to create per-table templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface BasePerTableTemplateFactory<T extends BasePerTableTemplate>
    extends  TemplateFactory
{
    /**
     * Generates a <i>per-table</i> template.
     * @param metadataManager the metadata manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the name of the repository.
     * @param header the header.
     * @param implementMarkerInterfaces whether to implement marker
     * interfaces.
     * @param jmx whether to include JMX support.
     * @param jndiLocation the JNDI path of the {@link javax.sql.DataSource}.
     * @param disableGenerationTimestamps whether to disable generation timestamps.
     * @param disableNotNullAnnotations whether to disable NotNull annotations.
     * @param disableCheckthreadAnnotations whether to disable checkthread.org annotations or not.
     * @param tableName the table name.
     * @param staticContents the table's static contents (optional).
     * @return a template.
     */
    @Nullable
    public T createTemplate(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String packageName,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        @Nullable final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        final boolean disableGenerationTimestamps,
        final boolean disableNotNullAnnotations,
        final boolean disableCheckthreadAnnotations,
        @NotNull final String tableName,
        @Nullable final List<Row> staticContents);
}
