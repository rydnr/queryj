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
 * Filename: RepositoryDAOFactoryTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the entities able to create RepositoryDAOFactory
 *              templates.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;

/**
 * Defines the entities able to create RepositoryDAOFactory templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface RepositoryDAOFactoryTemplateFactory
    extends  BasePerRepositoryTemplateFactory
{
    /**
     * Generates a <i>per-repository</i> template.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the name of the repository.
     * @param engineName the engine name.
     * @param jndiDataSource the JNDI location of the data source.
     * @param tables the tables.
     * @param header the header.
     * @return a template.
     */
    @NotNull
    public BasePerRepositoryTemplate createTemplate(
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String engineName,
        final String jndiDataSource,
        final Collection tables,
        final String header);
}
