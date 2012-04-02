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
 * Filename: MySQLTimeFunctionsTestTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate tests on MySQL's time functions.
 *
 */
package org.acmsl.queryj.tools.templates.functions.time.mysql;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.functions.FunctionsTestTemplate;
import org.acmsl.queryj.tools.templates.functions.time.mysql
    .MySQLTimeFunctionsTemplate;

import org.acmsl.queryj.tools.templates.functions.time
    .TimeFunctionsTestTemplate;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Is able to generate tests on MySQL time functions.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class MySQLTimeFunctionsTestTemplate
    extends  TimeFunctionsTestTemplate
{
    /**
     * Builds a MySQLTimeFunctionsTestTemplate using given information.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    public MySQLTimeFunctionsTestTemplate(
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
     * Creates the special function mappings.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    public static Map buildMappings(final Map mappings)
    {
        return MySQLTimeFunctionsTemplate.buildMappings(mappings);
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
                buildSpecialKey("PERIOD_ADD", MySQLTimeFunctionsTestTemplate.class),
                ANY__CALENDAR_INT_FUNCTION_TEST);
            result.put(
                buildSpecialKey("PERIOD_DIFF", MySQLTimeFunctionsTestTemplate.class),
                ANY__CALENDAR_CALENDAR_FUNCTION_TEST);
            result.put(
                buildSpecialKey("FROM_DAYS", MySQLTimeFunctionsTestTemplate.class),
                ANY__LONG_FUNCTION_TEST);
            result.put(
                buildSpecialKey("DATE_FORMAT", MySQLTimeFunctionsTestTemplate.class),
                ANY__CALENDAR_STRING_FUNCTION_TEST);
            result.put(
                buildSpecialKey("TIME_FORMAT", MySQLTimeFunctionsTestTemplate.class),
                ANY__CALENDAR_STRING_FUNCTION_TEST);
            result.put(
                buildSpecialKey("CURDATE", MySQLTimeFunctionsTestTemplate.class),
                ANY__FUNCTION_TEST);
            result.put(
                buildSpecialKey("CURRENT_DATE", MySQLTimeFunctionsTestTemplate.class),
                ANY__NOFUNCTION_TEST);
            result.put(
                buildSpecialKey("CURTIME", MySQLTimeFunctionsTestTemplate.class),
                ANY__FUNCTION_TEST);
            result.put(
                buildSpecialKey("CURRENT_TIME", MySQLTimeFunctionsTestTemplate.class),
                ANY__NOFUNCTION_TEST);
            result.put(
                buildSpecialKey("NOW", MySQLTimeFunctionsTestTemplate.class),
                ANY__FUNCTION_TEST);
            result.put(
                buildSpecialKey("SYSDATE", MySQLTimeFunctionsTestTemplate.class),
                ANY__FUNCTION_TEST);
            result.put(
                buildSpecialKey("CURRENT_TIMESTAMP", MySQLTimeFunctionsTestTemplate.class),
                ANY__NOFUNCTION_TEST);
            result.put(
                buildSpecialKey("UNIX_TIMESTAMP", MySQLTimeFunctionsTestTemplate.class),
                  ANY__FUNCTION_TEST
                + ANY__CALENDAR_FUNCTION_TEST);
            result.put(
                buildSpecialKey("FROM_UNIXTIME", MySQLTimeFunctionsTestTemplate.class),
                  ANY__LONG_FUNCTION_TEST
                + ANY__LONG_STRING_FUNCTION_TEST);
            result.put(
                buildSpecialKey("SEC_TO_TIME", MySQLTimeFunctionsTestTemplate.class),
                ANY__LONG_FUNCTION_TEST);
            result.put(
                buildSpecialKey("TIME_TO_SEC", MySQLTimeFunctionsTestTemplate.class),
                ANY__CALENDAR_FUNCTION_TEST);
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
        return getMapping(function, MySQLTimeFunctionsTemplate.class);
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
                function, MySQLTimeFunctionsTestTemplate.class);
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
                         "PERIOD_ADD",
                         MySQLTimeFunctionsTestTemplate.class))));
    }

    /**
     * Retrieves the default function method.
     * @return such method template.
     */
    protected String getDefaultFunctionMethod()
    {
        return ANY__CALENDAR_FUNCTION_TEST;
    }

    /**
     * Retrieves the capitalized words.
     * @return such words.
     */
    protected String[] getCapitalizedWords()
    {
        return MySQLTimeFunctionsTemplate.CAPITALIZED_WORDS;
    }

    /**
     * Retrieves the field types.
     * @return such array.
     */
    protected String[] getFieldTypes()
    {
        return MySQLTimeFunctionsTemplate.FIELD_TYPES;
    }
}
