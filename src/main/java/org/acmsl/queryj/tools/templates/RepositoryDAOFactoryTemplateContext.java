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
 * Filename: RepositoryDAOFactoryTemplateContext.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Context for RepositoryDAOFactoryTemplates.
 *
 * Date: 5/20/12
 * Time: 8:41 AM
 *
 */
package org.acmsl.queryj.tools.templates;

import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Context for {@link RepositoryDAOFactoryTemplate} templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/20
 */
public class RepositoryDAOFactoryTemplateContext
    extends BasePerRepositoryTemplateContext
{
    /**
     * The JNDI data source.
     */
    private String jndiDataSource;

    /**
     * Creates a {@link RepositoryDAOFactoryTemplateContext} with given information.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param header the header.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param implementMarkerInterfaces whether to implement marker interfaces
     * @param jmx whether to include JMX support.
     * @param tableNames the list of table names.
     * @param jndiDataSource the JNDI datasource location.
     */
    public RepositoryDAOFactoryTemplateContext(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String header,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String packageName,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final List<String> tableNames,
        @NotNull final String jndiDataSource)
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
            jmx,
            tableNames);

        immutableSetJndiDataSource(jndiDataSource);
    }

    /**
     * Specifies the JNDI location for the data source.
     * @param jndiDataSource such location.
     */
    protected final void immutableSetJndiDataSource(@NotNull final String jndiDataSource)
    {
        this.jndiDataSource = jndiDataSource;
    }

    /**
     * Specifies the JNDI location for the data source.
     * @param jndiDataSource such location.
     */
    @SuppressWarnings("unused")
    protected void setJndiDataSource(@NotNull final String jndiDataSource)
    {
        immutableSetJndiDataSource(jndiDataSource);
    }

    /**
     * Retrieves the JNDI location for the data source.
     * @return such location.
     */
    @NotNull
    public String getJndiDataSource()
    {
        return jndiDataSource;
    }

}
