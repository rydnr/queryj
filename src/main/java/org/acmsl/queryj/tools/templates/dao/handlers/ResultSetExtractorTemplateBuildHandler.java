//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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

 ******************************************************************************
 *
 * Filename: ResultSetExtractorTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a ResultSetExtractor template.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.ResultSetExtractorTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.BasePerTableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Builds a ResultSetExtractor template.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class ResultSetExtractorTemplateBuildHandler
    extends  BasePerTableTemplateBuildHandler
{
    /**
     * Retrieves the template factory.
     * @param map the map.
     * @return such instance.
     */
    protected BasePerTableTemplateFactory retrieveTemplateFactory(final Map map)
    {
        return ResultSetExtractorTemplateGenerator.getInstance();
    }

    /**
     * Retrieves the package name.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String tableName,
        final String engineName,
        final String projectPackage,
        final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveResultSetExtractorPackage(
                projectPackage,
                engineName,
                tableName);
    }

    /**
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     * @precondition templates != null
     * @precondition parameters != null
     */
    protected void storeTemplates(
        final BasePerTableTemplate[] templates, final Map parameters)
    {
        parameters.put(TemplateMappingManager.RESULTSET_EXTRACTOR_TEMPLATES, templates);
    }
}
