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
 * Filename: MySQLNumericFunctionsTestTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate tests on Database's numeric functions.
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.numeric.mysql;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.functions.numeric
    .NumericFunctionsTemplate;

import org.acmsl.queryj.tools.templates.functions.numeric
    .NumericFunctionsTestTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Is able to generate tests on Database's numeric functions.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class MySQLNumericFunctionsTestTemplate
    extends  NumericFunctionsTestTemplate
{
    private static final long serialVersionUID = 6890294277262544895L;

    /**
     * Builds a MySQLNumericFunctionsTestTemplate using given information.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    public MySQLNumericFunctionsTestTemplate(
        final String header,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String testedPackageName,
        final String engineName,
        final String engineVersion,
        final String quote)
    {
        super(
            header,
            decoratorFactory,
            packageName,
            testedPackageName,
            engineName,
            engineVersion,
            quote);
    }

    /**
     * Creates the special function mappings.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    protected Map fillUpMappings(final Map mappings)
    {
        return buildMappings(mappings);
    }

    /**
     * Creates the special function mappings.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    protected static Map buildMappings(final Map mappings)
    {
        return MySQLNumericFunctionsTemplate.buildMappings(mappings);
    }

    /**
     * Creates the special function mappings.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    protected Map fillUpSpecialMappings(final Map mappings)
    {
        return buildSpecialMappings(mappings);
    }

    /**
     * Creates the special function mappings.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    protected static Map buildSpecialMappings(final Map mappings)
    {
        Map result = mappings;

        if  (result != null) 
        {
            result.put(
                buildSpecialKey("MAX", MySQLNumericFunctionsTestTemplate.class),
                ANY__DOUBLE_DOUBLE_FUNCTION_TEST);
            result.put(
                buildSpecialKey("MIN", MySQLNumericFunctionsTestTemplate.class),
                ANY__DOUBLE_DOUBLE_FUNCTION_TEST);
            result.put(
                buildSpecialKey("MOD", MySQLNumericFunctionsTestTemplate.class),
                ANY__DOUBLE_LONG_FUNCTION_TEST);
            result.put(
                buildSpecialKey("PI", MySQLNumericFunctionsTestTemplate.class),
                ANY__FUNCTION_TEST);
            result.put(
                buildSpecialKey("POW", MySQLNumericFunctionsTestTemplate.class),
                ANY__DOUBLE_DOUBLE_FUNCTION_TEST);
            result.put(
                buildSpecialKey("POWER", MySQLNumericFunctionsTestTemplate.class),
                ANY__DOUBLE_DOUBLE_FUNCTION_TEST);
            result.put(
                buildSpecialKey("RAND", MySQLNumericFunctionsTestTemplate.class),
                  ANY__FUNCTION_TEST
                + ANY__DOUBLE_FUNCTION_TEST);
            result.put(
                buildSpecialKey("ROUND", MySQLNumericFunctionsTestTemplate.class),
                  ANY__DOUBLE_FUNCTION_TEST
                + ANY__DOUBLE_DOUBLE_FUNCTION_TEST);
            result.put(
                buildSpecialKey("TRUNCATE", MySQLNumericFunctionsTestTemplate.class),
                ANY__DOUBLE_DOUBLE_FUNCTION_TEST);
        }
        
        return result;
    }

    /**
     * Retrieves the mapping for given function.
     * @param function the function.
     * @return the mapping.
     */
    @Nullable
    protected String getMapping(final String function)
    {
        return getMapping(function, MySQLNumericFunctionsTemplate.class);
    }

    /**
     * Retrieves the mapping for given function.
     * @param function the function.
     * @return the mapping.
     */
    @Nullable
    protected String getSpecialMapping(final String function)
    {
        return
            getSpecialMapping(
                function, MySQLNumericFunctionsTestTemplate.class);
    }

    /**
     * Checks whether given map is already filled with specific
     * template mappings.
     * @param mapping the mapping table.
     * @return <code>true</code> if the map contains the specific
     * mapping entries for this template.
     */
    protected boolean isFilledIn(@Nullable final Map mappings)
    {
        return
            (   (mappings != null)
             && (mappings.containsKey(
                     buildSpecialKey(
                         "MAX",
                         MySQLNumericFunctionsTestTemplate.class))));
    }

    /**
     * Retrieves the default function method.
     * @return such method template.
     */
    protected String getDefaultFunctionMethod()
    {
        return ANY__DOUBLE_FUNCTION_TEST;
    }

    /**
     * Retrieves the capitalized words.
     * @return such words.
     */
    @NotNull
    protected String[] getCapitalizedWords()
    {
        return MySQLNumericFunctionsTemplate.CAPITALIZED_WORDS;
    }

    /**
     * Retrieves the field types.
     * @return such words.
     */
    @NotNull
    protected String[] getFieldTypes()
    {
        return MySQLNumericFunctionsTemplate.FIELD_TYPES;
    }
}
