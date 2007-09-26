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
 * Filename: DAOTestTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a DAO test template using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.DAOTestTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.BasePerTableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Builds a DAO test template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class DAOTestTemplateBuildHandler
    extends  BasePerTableTemplateBuildHandler
{
    /**
     * Checks whether the handler should actually perform its logic
     * or not.
     * @param parameters the parameters.
     * @return <code>true</code> in such case.
     * @precondition parameters != null
     */
    protected boolean shouldHandle(final Map parameters)
    {
        return
            Boolean.TRUE.equals(
                parameters.get(ParameterValidationHandler.GENERATE_TESTS));
    }

    /**
     * Retrieves the template factory.
     * @param map the map.
     * @return such instance.
     */
    protected BasePerTableTemplateFactory retrieveTemplateFactory(
        final Map map)
    {
        return createTemplateGenerator(map);
    }

    /**
     * Creates the template generator.
     * @param map the map.
     * @return such instance.
     */
    public static DAOTestTemplateGenerator createTemplateGenerator(
        final Map map)
    {
        return
            new DAOTestTemplateGenerator(
                retrieveJdbcDriver(map),
                retrieveJdbcUrl(map),
                retrieveJdbcUser(map),
                retrieveJdbcPassword(map));
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
        throw
            new IllegalArgumentException(
                "Descendant classes should override this. "
                + "retrievePackage logic follows a different approach "
                + "in BasePerTableTemplateBuildHandler than "
                + DAOTestTemplateBuildHandler.class);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrievePackage(
        final String tableName, final String engineName, final Map parameters)
    {
        return
            retrievePackage(
                retrieveProjectPackage(parameters),
                engineName,
                retrieveUseSubfoldersFlag(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name.
     * @param projectPackage the project package.
     * @param engineName the engine name.
     * @param useSubfoldersFlag whether to use src/main/java or not.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrievePackage(
        final String projectPackage,
        final String engineName,
        final boolean useSubfoldersFlag,
        final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveDAOTestPackage(
                projectPackage, engineName, useSubfoldersFlag);
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
        parameters.put(TemplateMappingManager.DAO_TEST_TEMPLATES, templates);
    }
}
