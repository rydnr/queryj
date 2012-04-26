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
 * Filename: TableRepositoryTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate table repositories according to database
 *              metadata.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;

/*
 * Importing some StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;

/**
 * Is able to generate Table repositories according to database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TableRepositoryTemplate
    extends  BasePerRepositoryTemplate
{
    /**
     * Builds a <code>TableRepositoryTemplate</code> using given
     * information.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param engineName the engine name.
     * @param tables the tables.
     */
    public TableRepositoryTemplate(
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        final String header,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String engineName,
        final Collection tables)
    {
        super(
            metadataManager,
            metadataTypeManager,
            customSqlProvider,
            header,
            decoratorFactory,
            packageName,
            basePackageName,
            repositoryName,
            engineName,
            tables);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Nullable
    @Override
    public StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/sql/TableRepository.stg");
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    @NotNull
    public String getTemplateName()
    {
        return "Table repository";
    }
}
