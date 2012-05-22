//;-*- mode: java -*-
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
 * Filename: BasePerRepositoryTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents entities able to create per-repository templates.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Factory;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.MetadataManager;

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
 * Represents entities able to create per-repository templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface BasePerRepositoryTemplateFactory<T extends BasePerRepositoryTemplate>
    extends  Factory
{
    /**
     * Creates a {@link T} instance with given information.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param header the file header.
     * @param packageName the package name.
     * @param projectPackage the project's base package.
     * @param repository the repository name.
     * @param implementMarkerInterfaces whether to implement marker interfaces.
     * @param jmx whether to enable JMX.
     * @param tableNames the list of table names.
     * @param jndiLocation the JNDI location of the data source.
     * @return the new template.
     */
    @Nullable
    public T createTemplate(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String packageName,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final List<String> tableNames,
        @NotNull final String jndiLocation);
}
