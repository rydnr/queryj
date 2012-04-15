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
 * Filename: BaseValueObjectTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a ValueObject templates using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.valueobject.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateFactory;
import org.acmsl.queryj.tools.templates.valueobject.BaseValueObjectTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.BasePerTableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Builds {@link org.acmsl.queryj.tools.templates.valueobject.BaseValueObjectTemplate} instances using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class BaseValueObjectTemplateBuildHandler
    extends  BasePerTableTemplateBuildHandler
{
    /**
     * Creates a {@link BaseValueObjectTemplateBuildHandler} instance.
     */
    public BaseValueObjectTemplateBuildHandler() {}

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @NotNull
    protected BasePerTableTemplateFactory retrieveTemplateFactory()
    {
        return BaseValueObjectTemplateGenerator.getInstance();
    }

    /**
     * Retrieves the package name.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the {@link PackageUtils} instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String tableName,
        final String engineName,
        final String projectPackage,
        @NotNull final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveValueObjectPackage(projectPackage);
    }

    /**
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     * @precondition templates != null
     * @precondition parameters != null
     */
    protected void storeTemplates(
        final BasePerTableTemplate[] templates, @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.BASE_VALUE_OBJECT_TEMPLATES, templates);
    }
}
