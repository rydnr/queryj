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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 *****************************************************************************
 *
 * Filename: BaseResultSetExtractorTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create BaseResultSetExtractor sources.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplate;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;

/**
 * Is able to create BaseResultSetExtractor sources.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class BaseResultSetExtractorTemplate
    extends  BasePerRepositoryTemplate
{
    /**
     * Builds a {@link BaseResultSetExtractorTemplate} using given information.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the database metadata type manager.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param header the header.
     * @param jmx whether to support JMX or not.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param engineName the engine name.
     * @param tables the tables.
     */
    public BaseResultSetExtractorTemplate(
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        final String header,
        final boolean jmx,
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
//            jmx,
            decoratorFactory,
            packageName,
            basePackageName,
            repositoryName,
            engineName,
            tables);
    }

    /**
     * Builds a key to store the template cache.
     * @return such key.
     */
    @NotNull
    protected Object buildTemplateCacheKey()
    {
        return "//BaseResultSetExtractorTemplate//";
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Nullable
    protected StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/dao/BaseResultSetExtractor.stg");
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    @NotNull
    public String getTemplateName()
    {
        return "BaseResultSetExtractor";
    }
}
