//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a text functions test template using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.text.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTestTemplate;
import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTestTemplateFactory;
import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTestTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.TestTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Builds a text functions test template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class TextFunctionsTestTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a <code>TextFunctionsTestTemplateBuildHandler</code> instance.
     */
    public TextFunctionsTestTemplateBuildHandler() {};

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
        throws  QueryJBuildException
    {
        if  (retrieveExtractFunctions(parameters))
        {
            buildTemplate(
                retrieveDatabaseMetaData(parameters),
                retrievePackage(parameters),
                retrieveTestedPackage(parameters),
                TextFunctionsTestTemplateGenerator.getInstance(),
                parameters,
                StringUtils.getInstance());
        }

        return false;
    }
        
    /**
     * Builds the <code>TextFunctionsTest</code> template.
     * @param metadata the <code>DatabaseMetaData</code> instance.
     * @param packageName the package name.
     * @param testedPackage the tested package.
     * @param generator the <code>TextFunctionsTestTemplateGenerator</code>
     * instance.
     * @param parameters the map to store the template into.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition metadata != null
     * @precondition packageName != null
     * @precondition testedPackage != null
     * @precondition generator != null
     * @precondition parameters != null
     * @precondition stringUtils != null
     */
    protected void buildTemplate(
        final DatabaseMetaData metadata,
        final String packageName,
        final String testedPackage,
        final TextFunctionsTestTemplateGenerator generator,
        final Map parameters,
        final StringUtils stringUtils)
      throws  QueryJBuildException
    {
        try 
        {
            TextFunctionsTestTemplate t_Template =
                generator.createTextFunctionsTestTemplate(
                    packageName,
                    testedPackage,
                    metadata.getDatabaseProductName(),
                    metadata.getDatabaseProductVersion(),
                    fixQuote(metadata.getIdentifierQuoteString()));

            Collection t_cFunctions =
                stringUtils.tokenize(metadata.getStringFunctions(), ",");

            Iterator t_itFunctions =
                (t_cFunctions != null) ? t_cFunctions.iterator() : null;

            if  (t_itFunctions != null) 
            {
                String t_strFunction;

                while  (t_itFunctions.hasNext())
                {
                    t_strFunction = (String) t_itFunctions.next();

                    if  ("CHAR".equals(t_strFunction))
                    {
                        t_strFunction = "CHAR_";
                    }
                    else if  ("ACII".equals(t_strFunction))
                    {
                        t_strFunction = "ASCII";
                    }

                    t_Template.addFunction(t_strFunction);
                }
            }

            storeTextFunctionsTestTemplate(t_Template, parameters);
        }
        catch  (final SQLException sqlException)
        {
            throw
                new QueryJBuildException(
                      "Cannot retrieve database product name, "
                    + "version or quote string",
                    sqlException);
        }
    }

    /**
     * Retrieves whether the functions should be extracted or not.
     * @param parameters the parameter map.
     * @return such information.
     * @precondition parameters != null
     */
    protected boolean retrieveExtractFunctions(final Map parameters)
    {
        boolean result = true;

        Boolean t_bResult =
            (Boolean)
                parameters.get(
                    ParameterValidationHandler.EXTRACT_FUNCTIONS);

        if  (t_bResult != null)
        {
            result = t_bResult.booleanValue();
        }

        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition parameters != null
     */
    protected String retrievePackage(final Map parameters)
    {
        return
            retrievePackage(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final Map parameters, final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveTestFunctionsPackage(
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters));
    }

    /**
     * Retrieves the tested package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition parameters != null
     */
    protected String retrieveTestedPackage(final Map parameters)
    {
        return retrieveTestedPackage(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the tested package name from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrieveTestedPackage(
        final Map parameters, final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveFunctionsPackage(
                retrieveProjectPackage(parameters));
    }

    /**
     * Retrieves the test template collection.
     * @param parameters the parameter map.
     * @return the test templates.
     * @precondition parameters != null
     */
    protected Collection retrieveTestTemplates(Map parameters)
    {
        return
            (Collection) parameters.get(TemplateMappingManager.TEST_TEMPLATES);
    }

    /**
     * Stores the text functions test template.
     * @param template the test template.
     * @param parameters the parameter map.
     * @precondition template != null
     * @precondition parameters != null
     */
    protected void storeTextFunctionsTestTemplate(
        final TextFunctionsTestTemplate template,
        final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.TEXT_FUNCTIONS_TEST_TEMPLATE, template);

        storeTestTemplate(template, parameters);
    }

    /**
     * Stores the test template collection.
     * @param templates the test templates.
     * @param parameters the parameter map.
     * @return the test templates.
     * @precondition templates != null
     * @precondition parameters != null
     */
    protected void storeTestTemplates(
        final Collection templates, final Map parameters)
    {
        parameters.put(TemplateMappingManager.TEST_TEMPLATES, templates);
    }

    /**
     * Stores a new test template.
     * @param testTemplate the test template.
     * @param parameters the parameter map.
     * @precondition template != null
     * @precondition parameters != null
     */
    protected void storeTestTemplate(
        final TestTemplate template, final Map parameters)
    {
        Collection t_cTestTemplates = retrieveTestTemplates(parameters);

        if  (t_cTestTemplates == null) 
        {
            t_cTestTemplates = new ArrayList();
            storeTestTemplates(t_cTestTemplates, parameters);
        }

        t_cTestTemplates.add(template);
    }
}
