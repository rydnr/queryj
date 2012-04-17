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
 * Filename: TextFunctionsTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a text functions template using database metadata.
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
import org.acmsl.queryj.tools.templates.functions.text.TextFunctionsTemplate;
import org.acmsl.queryj.tools.templates.functions.text.TextFunctionsTemplateFactory;
import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Builds a text functions template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TextFunctionsTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The text functions template attribute name.
     */
    public static final String TEXT_FUNCTIONS_TEMPLATE =
        "text.functions.template";

    /**
     * Creates a <code>TextFunctionsTemplateBuildHandler</code> instance.
     */
    public TextFunctionsTemplateBuildHandler() {};

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        if  (retrieveExtractFunctions(parameters))
        {
            buildTemplate(
                retrieveDatabaseMetaData(parameters),
                retrievePackage(parameters),
                TextFunctionsTemplateGenerator.getInstance(),
                parameters,
                StringUtils.getInstance());
        }

        return false;
    }
        
    /**
     * Build the <code>TextFunctions</code> template.
     * @param metadata the <code>DatabaseMetaData</code> instance.
     * @param packageName the package name.
     * @param generator the <code>TextFunctionsTemplateGenerator</code>
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
        @NotNull final DatabaseMetaData metadata,
        final String packageName,
        @NotNull final TextFunctionsTemplateGenerator generator,
        @NotNull final Map parameters,
        @NotNull final StringUtils stringUtils)
      throws  QueryJBuildException
    {
        try 
        {
            @Nullable TextFunctionsTemplate t_Template =
                generator.createTextFunctionsTemplate(
                    packageName,
                    metadata.getDatabaseProductName(),
                    metadata.getDatabaseProductVersion(),
                    fixQuote(metadata.getIdentifierQuoteString()),
                    retrieveHeader(parameters));

            Collection t_cFunctions =
                stringUtils.tokenize(metadata.getStringFunctions(), ",");

            @Nullable Iterator t_itFunctions =
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

            storeTextFunctionsTemplate(t_Template, parameters);
        }
        catch  (@NotNull final SQLException sqlException)
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
    protected boolean retrieveExtractFunctions(@NotNull final Map parameters)
    {
        boolean result = true;

        @NotNull Boolean t_bResult =
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
     * @param template the template to store.
     * @param parameters the parameter map.
     * @precondition template != null
     * @precondition parameters != null
     */
    protected void storeTextFunctionsTemplate(
        final TextFunctionsTemplate template, @NotNull final Map parameters)
    {
        parameters.put(TEXT_FUNCTIONS_TEMPLATE, template);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     */
    protected String retrievePackage(@NotNull final Map parameters)
    {
        return
            retrievePackage(
                retrieveProjectPackage(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String projectPackage, @NotNull final PackageUtils packageUtils)
    {
        return packageUtils.retrieveFunctionsPackage(projectPackage);
    }
}
