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
 * Filename: OracleTimeFunctionsTestTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate tests on Database's time functions.
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.time.oracle;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.functions.time.oracle
    .OracleTimeFunctionsTemplate;

import org.acmsl.queryj.tools.templates.functions.time
    .TimeFunctionsTestTemplate;

/*
 * Importing some JDK classes.
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Is able to generate tests on Database's time functions.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class OracleTimeFunctionsTestTemplate
    extends  TimeFunctionsTestTemplate
{
    /**
     * Builds an OracleTimeFunctionsTestTemplate using given information.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    public OracleTimeFunctionsTestTemplate(
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
     * Creates the function mappings.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    public Map fillUpMappings(final Map mappings)
    {
        return buildMappings(mappings);
    }

    /**
     * Creates the function mappings.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    public static Map buildMappings(final Map mappings)
    {
        return OracleTimeFunctionsTemplate.buildMappings(mappings);
    }

    /**
     * Creates the special function mappings.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    public Map fillUpSpecialMappings(final Map mappings)
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
                buildSpecialKey("ADD_MONTHS", OracleTimeFunctionsTestTemplate.class),
                ANY__INT_INT_FUNCTION_TEST);
            result.put(
                buildSpecialKey("MONTHS_BETWEEN", OracleTimeFunctionsTestTemplate.class),
                ANY__CALENDAR_CALENDAR_FUNCTION_TEST);
            result.put(
                buildSpecialKey("NEW_TIME", OracleTimeFunctionsTestTemplate.class),
                ANY__CALENDAR_STRING_STRING_FUNCTION_TEST);
            result.put(
                buildSpecialKey("NEXT_DAY", OracleTimeFunctionsTestTemplate.class),
                ANY__CALENDAR_STRING_FUNCTION_TEST);
            result.put(
                buildSpecialKey("ROUND", OracleTimeFunctionsTestTemplate.class),
                  ANY__CALENDAR_FUNCTION_TEST
                + ANY__CALENDAR_STRING_FUNCTION_TEST);
            result.put(
                buildSpecialKey("SYSDATE", OracleTimeFunctionsTestTemplate.class),
                ANY__NOFUNCTION_TEST);
            result.put(
                buildSpecialKey("TRUNC", OracleTimeFunctionsTestTemplate.class),
                  ANY__CALENDAR_FUNCTION_TEST
                + ANY__CALENDAR_STRING_FUNCTION_TEST);
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
        // It's not OracleTimeFunctionsTestTemplate.class since
        // the mappings are performed by OracleTimeFunctionsTemplate.
        return getMapping(function, OracleTimeFunctionsTemplate.class);
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
                function, OracleTimeFunctionsTestTemplate.class);
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
                         "ADD_MONTHS",
                         OracleTimeFunctionsTestTemplate.class))));
    }

    /**
     * Retrieves the capitalized words.
     * @return such words.
     */
    protected String[] getCapitalizedWords()
    {
        return OracleTimeFunctionsTemplate.CAPITALIZED_WORDS;
    }

    /**
     * Retrieves the field types.
     * @return such array.
     */
    protected String[] getFieldTypes()
    {
        return OracleTimeFunctionsTemplate.FIELD_TYPES;
    }

    /**
     * Retrieves the default function method.
     * @return such method template.
     */
    protected String getDefaultFunctionMethod()
    {
        return ANY__CALENDAR_FUNCTION_TEST;
    }
}
