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
 * Filename: OracleTextFunctionsTestTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate tests on Oracle's text functions.
 *
 */
package org.acmsl.queryj.tools.templates.functions.text.oracle;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.functions.text.oracle
    .OracleTextFunctionsTemplate;

import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTestTemplate;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Is able to generate tests on Oracle database's text functions.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class OracleTextFunctionsTestTemplate
    extends  TextFunctionsTestTemplate
{
    /**
     * Builds a <code>OracleTextFunctionsTestTemplate</code>
     * using given information.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    public OracleTextFunctionsTestTemplate(
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
    public static Map buildMappings(final Map mappings)
    {
        return OracleTextFunctionsTemplate.buildMappings(mappings);
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
    public static Map buildSpecialMappings(final Map mappings)
    {
        Map result = mappings;

        if  (result != null)
        {
            result.put(
                buildSpecialKey("CHR", OracleTextFunctionsTestTemplate.class),
                ANY__INT_FUNCTION_TEST);
            result.put(
                buildSpecialKey("LPAD", OracleTextFunctionsTestTemplate.class),
                  ANY__STRING_INT_FUNCTION_TEST
                + ANY__STRING_INT_STRING_FUNCTION_TEST);
            result.put(
                buildSpecialKey("LTRIM", OracleTextFunctionsTestTemplate.class),
                  ANY__STRING_FUNCTION_TEST
                + ANY__STRING_STRING_FUNCTION_TEST);
            result.put(
                buildSpecialKey("REPLACE", OracleTextFunctionsTestTemplate.class),
                  ANY__STRING_STRING_FUNCTION_TEST
                + ANY__STRING_STRING_STRING_FUNCTION_TEST);
            result.put(
                buildSpecialKey("RPAD", OracleTextFunctionsTestTemplate.class),
                  ANY__STRING_INT_FUNCTION_TEST
                + ANY__STRING_INT_STRING_FUNCTION_TEST);
            result.put(
                buildSpecialKey("RTRIM", OracleTextFunctionsTestTemplate.class),
                  ANY__STRING_FUNCTION_TEST
                + ANY__STRING_STRING_FUNCTION_TEST);
            result.put(
                buildSpecialKey("SUBSTR", OracleTextFunctionsTestTemplate.class),
                  ANY__STRING_INT_FUNCTION_TEST
                + ANY__STRING_INT_INT_FUNCTION_TEST);
            result.put(
                buildSpecialKey("SUBSTRB", OracleTextFunctionsTestTemplate.class),
                  ANY__STRING_INT_FUNCTION_TEST
                + ANY__STRING_INT_INT_FUNCTION_TEST);
            result.put(
                buildSpecialKey("INSTR", OracleTextFunctionsTestTemplate.class),
                ANY__STRING_STRING_INT_FUNCTION_TEST);
            /* Too many parameters: not supported yet.
                + ANY__STRING_STRING_INT_INT_FUNCTION_TEST);
            */
            result.put(
                buildSpecialKey("INSTRB", OracleTextFunctionsTestTemplate.class),
                ANY__STRING_STRING_INT_FUNCTION_TEST);
            /* Too many parameters: not supported yet.
                + ANY__STRING_STRING_INT_INT_FUNCTION_TEST);
            */
        }
        
        return result;
    }

    /**
     * Retrieves the mapping for given function.
     * @param function the function.
     * @return the mapping.
     */
    protected String getMapping(final String function)
    {
        // It's not OracleTextFunctionsTestTemplate.class since
        // the mappings are performed by OracleTextFunctionsTemplate.
        return getMapping(function, OracleTextFunctionsTemplate.class);
    }

    /**
     * Retrieves the mapping for given function.
     * @param function the function.
     * @return the mapping.
     */
    protected String getSpecialMapping(final String function)
    {
        return
            getSpecialMapping(
                function, OracleTextFunctionsTestTemplate.class);
    }

    /**
     * Checks whether given map is already filled with specific
     * template mappings.
     * @param mapping the mapping table.
     * @return <code>true</code> if the map contains the specific
     * mapping entries for this template.
     */
    protected boolean isFilledIn(final Map mappings)
    {
        return
            (   (mappings != null)
             && (mappings.containsKey(
                     buildSpecialKey(
                         "CHR",
                         OracleTextFunctionsTestTemplate.class))));
    }

    /**
     * Checks whether a concrete mapping can be omitted without generating a
     * warning message.
     * @param mapping the concrete mapping.
     * @return <code>true</code> to generate the warning message.
     */
    protected boolean generateWarning(final String mapping)
    {
        return false;
    }

    /**
     * Retrieves the default function method.
     * @return such method template.
     */
    protected String getDefaultFunctionMethod()
    {
        return ANY__STRING_FUNCTION_TEST;
    }

    /**
     * Retrieves the capitalized words.
     * @return such words.
     */
    protected String[] getCapitalizedWords()
    {
        return OracleTextFunctionsTemplate.CAPITALIZED_WORDS;
    }

    /**
     * Retrieves the field types.
     * @return such array.
     */
    protected String[] getFieldTypes()
    {
        return OracleTextFunctionsTemplate.FIELD_TYPES;
    }
}
