/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
                              chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the factoryied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: ValueObjectFactoryTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a ValueObject templates using database metadata.
 *
 */
package org.acmsl.queryj.templates.valueobject.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.templates.valueobject.ValueObjectFactoryTemplateFactory;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.templates.valueobject.ValueObjectFactoryTemplate;
import org.acmsl.queryj.api.handlers.BasePerTableTemplateBuildHandler;
import org.acmsl.queryj.api.TemplateMappingManager;

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

/**
 * Builds <code>ValueObjectFactoryTemplate</code> instances using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class ValueObjectFactoryTemplateBuildHandler
    extends  BasePerTableTemplateBuildHandler<ValueObjectFactoryTemplate, ValueObjectFactoryTemplateFactory>
{
    /**
     * Creates a <code>ValueObjectFactoryTemplateBuildHandler</code> instance.
     */
    public ValueObjectFactoryTemplateBuildHandler() {}

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @NotNull
    protected ValueObjectFactoryTemplateFactory retrieveTemplateFactory()
    {
        return ValueObjectFactoryTemplateFactory.getInstance();
    }

    /**
     * Retrieves the package name.
     *
     *
     *
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws QueryJBuildException if the package retrieval process if faulty.
     */
    protected String retrievePackage(
        @NotNull final String tableName,
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils)
        throws QueryJBuildException
    {
        return
            packageUtils.retrieveValueObjectFactoryPackage(projectPackage);
    }

    /**
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     */
    @Override
    protected void storeTemplates(
        @NotNull final List<ValueObjectFactoryTemplate> templates,
        @NotNull final Map<String, List<ValueObjectFactoryTemplate>> parameters)
    {
        parameters.put(
            TemplateMappingManager.VALUE_OBJECT_FACTORY_TEMPLATES, templates);
    }
}
