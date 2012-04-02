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
 * Filename: NumericFunctionsTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a numeric functions template using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.numeric.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplate;
import org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplateFactory;
import org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplateGenerator;
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
 * Builds a numeric functions template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class NumericFunctionsTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The numeric functions template attribute name.
     */
    public static final String NUMERIC_FUNCTIONS_TEMPLATE =
        "numeric.functions.template";

    /**
     * Creates a <code>NumericFunctionsTemplateBuildHandler</code> instance.
     */
    public NumericFunctionsTemplateBuildHandler() {};

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
                retrieveHeader(parameters),
                NumericFunctionsTemplateGenerator.getInstance(),
                parameters,
                StringUtils.getInstance());
        }            

        return false;
    }

    /**
     * Builds the <code>NumericFunctions</code> template.
     * @param metadata the <code>DatabaseMetaData</code> instance.
     * @param packageName the name of the package.
     * @param header the header.
     * @param generator the <code>NumericFunctionsTemplateGenerator</code>
     * instance.
     * @param parameters the parameters to handle.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition metadata != null
     * @precondition packageName != null
     * @precondition header != null
     * @precondition generator != null
     * @precondition parameters != null
     * @precondition stringUtils != null
     */
    protected void buildTemplate(
        @NotNull final DatabaseMetaData metadata,
        final String packageName,
        final String header,
        @NotNull final NumericFunctionsTemplateGenerator generator,
        @NotNull final Map parameters,
        @NotNull final StringUtils stringUtils)
      throws  QueryJBuildException
    {
        try 
        {
            @Nullable NumericFunctionsTemplate t_Template =
                generator.createNumericFunctionsTemplate(
                    packageName,
                    metadata.getDatabaseProductName(),
                    metadata.getDatabaseProductVersion(),
                    fixQuote(metadata.getIdentifierQuoteString()),
                    header);

            Collection t_cFunctions =
                stringUtils.tokenize(metadata.getNumericFunctions(), ",");

            @Nullable Iterator t_itFunctions =
                (t_cFunctions != null) ? t_cFunctions.iterator() : null;

            if  (t_itFunctions != null)
            {
                String t_strFunctions;

                while  (t_itFunctions.hasNext())
                {
                    t_strFunctions = (String) t_itFunctions.next();

                    t_Template.addFunction(t_strFunctions);
                }
            }

            storeNumericFunctionsTemplate(t_Template, parameters);
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
     */
    protected boolean retrieveExtractFunctions(@NotNull final Map parameters)
    {
        boolean result = true;

        @NotNull Boolean t_bResult =
            (Boolean)
                parameters.get(ParameterValidationHandler.EXTRACT_FUNCTIONS);

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
    protected void storeNumericFunctionsTemplate(
        final NumericFunctionsTemplate template, @NotNull final Map parameters)
    {
        parameters.put(NUMERIC_FUNCTIONS_TEMPLATE, template);
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
