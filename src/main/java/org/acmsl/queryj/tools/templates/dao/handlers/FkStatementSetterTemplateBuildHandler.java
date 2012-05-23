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
 * Filename: FkStatementSetterTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a FkStatementSetter template.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerForeignKeyTemplateContext;
import org.acmsl.queryj.tools.templates.dao.FkStatementSetterTemplate;
import org.acmsl.queryj.tools.templates.dao.FkStatementSetterTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.BasePerForeignKeyTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.List;
import java.util.Map;

/**
 * Builds a FkStatementSetter template.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class FkStatementSetterTemplateBuildHandler
    extends BasePerForeignKeyTemplateBuildHandler
                <FkStatementSetterTemplate, FkStatementSetterTemplateGenerator, BasePerForeignKeyTemplateContext>
    implements TemplateBuildHandler
{
    /**
     * Creates a <code>FkStatementSetterTemplateBuildHandler</code> instance.
     */
    public FkStatementSetterTemplateBuildHandler() {}

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @Override
    @NotNull
    protected FkStatementSetterTemplateGenerator retrieveTemplateFactory()
    {
        return FkStatementSetterTemplateGenerator.getInstance();
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param engineName the engine name.
     * @param tableName the table name.
     * @param parameters the parameter map.
     * @return the package name.
     */
    @NotNull
    @Override
    protected String retrievePackage(
        @NotNull final String engineName,
        @NotNull final String tableName,
        @NotNull final Map parameters)
    {
        return
            retrievePackage(
                retrieveProjectPackage(parameters),
                engineName,
                tableName,
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param projectPackage the project package.
     * @param engineName the engine name.
     * @param tableName the table name.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     */
    @NotNull
    protected String retrievePackage(
        @NotNull final String projectPackage,
        @NotNull final String engineName,
        @NotNull final String tableName,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveFkStatementSetterPackage(
                projectPackage,
                engineName,
                tableName);
    }

    /**
     * Stores the templates in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void storeTemplates(
        @NotNull final List<FkStatementSetterTemplate> templates,
        @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.FK_STATEMENT_SETTER_TEMPLATES,
            templates);
    }
}
