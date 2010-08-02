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
 * Description: Builds a system functions template using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.system.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTemplate;
import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTemplateFactory;
import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Builds a system functions template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class SystemFunctionsTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The system functions template attribute name.
     */
    public static final String SYSTEM_FUNCTIONS_TEMPLATE =
        "system.functions.template";

    /**
     * Creates a <code>SystemFunctionsTemplateBuildHandler</code> instance.
     */
    public SystemFunctionsTemplateBuildHandler() {};

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
                SystemFunctionsTemplateGenerator.getInstance(),
                parameters,
                StringUtils.getInstance());
        }

        return false;
    }

    /**
     * Builds the <code>SystemFunctions</code> template.
     * @param metadata the <code>DatabaseMetaData</code> instance.
     * @param packageName the package name.
     * @param generator the <code>SystemFunctionsTemplateGenerator</code>
     * instance.
     * @param parameters the map to store the template into.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition metadata != null
     * @precondition packageName != null
     * @precondition generator != null
     * @precondition parameters != null
     * @precondition stringUtils != null
     */
    protected void buildTemplate(
        final DatabaseMetaData metadata,
        final String packageName,
        final SystemFunctionsTemplateGenerator generator,
        final Map parameters,
        final StringUtils stringUtils)
      throws  QueryJBuildException
    {
        try 
        {
            SystemFunctionsTemplate t_Template =
                generator.createSystemFunctionsTemplate(
                    packageName,
                    metadata.getDatabaseProductName(),
                    metadata.getDatabaseProductVersion(),
                    fixQuote(metadata.getIdentifierQuoteString()));

            Collection t_cFunctions =
                stringUtils.tokenize(metadata.getSystemFunctions(), ",");

            Iterator t_itFunctions =
                (t_cFunctions != null) ? t_cFunctions.iterator() : null;

            if  (t_itFunctions != null)
            {
                String t_strFunction;

                while  (t_itFunctions.hasNext())
                {
                    t_strFunction = (String) t_itFunctions.next();

                    t_Template.addFunction(t_strFunction);
                }
            }
            storeSystemFunctionsTemplate(t_Template, parameters);
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
     * Stores given template.
     * @param systemFunctionsTemplate the template to store.
     * @param parameters the parameter map.
     * @precondition template != null
     * @precondition parameters != null
     */
    protected void storeSystemFunctionsTemplate(
        final SystemFunctionsTemplate template, final Map parameters)
    {
        parameters.put(SYSTEM_FUNCTIONS_TEMPLATE, template);
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
            retrievePackage(
                retrieveProjectPackage(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String projectPackage, final PackageUtils packageUtils)
    {
        return packageUtils.retrieveFunctionsPackage(projectPackage);
    }
}
