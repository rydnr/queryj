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
 * Filename: MySQLTimeFunctionsTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate time function repositories according to
 *              MySQL database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.time.mysql;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.functions.time
    .TimeFunctionsTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Is able to generate time function repositories according to MYSQL database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class MySQLTimeFunctionsTemplate
    extends  TimeFunctionsTemplate
{
    /**
     * The capitalized words.
     */
    static final String[] CAPITALIZED_WORDS =
        new String[]
        {
            "week",
            "day",
            "month",
            "year",
            "name",
            "hour",
            "minute",
            "sec",
            "second",
            "format",
            "date",
            "time",
            "cur",
            "current",
            "timestamp",
            "unix",
            "of",
            "from"
        };

    /**
     * The field types.
     */
    static final String[] FIELD_TYPES =
        new String[]
        {
            "Int",
            "String",
            "Long",
            "Calendar"
        };
    private static final long serialVersionUID = -4864230184970077463L;

    /**
     * Builds a MySQLTimeFunctionsTemplate using given information.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    public MySQLTimeFunctionsTemplate(
        final String header,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote)
    {
        super(
            header,
            decoratorFactory,
            packageName,
            engineName,
            engineVersion,
            quote);
    }

    /**
     * Builds the mappings.
     * @param mappings the initial mapping collection.
     * @return the updated collection.
     */
    public Map fillUpMappings(final Map mappings)
    {
        return buildMappings(mappings);
    }

    /**
     * Builds the mappings.
     * @param mappings the initial mapping collection.
     * @return the updated collection.
     */
    public static Map buildMappings(final Map mappings)
    {
        Map result = mappings;

        if  (result != null)
        {
            result.put(
                buildKey("DAYOFWEEK", MySQLTimeFunctionsTemplate.class),
                "Int");
            result.put(
                buildKey("WEEKDAY", MySQLTimeFunctionsTemplate.class),
                "Int");
            result.put(
                buildKey("DAYOFMONTH", MySQLTimeFunctionsTemplate.class),
                "Int");
            result.put(
                buildKey("DAYOFYEAR", MySQLTimeFunctionsTemplate.class),
                "Int");
            result.put(
                buildKey("MONTH", MySQLTimeFunctionsTemplate.class),
                "Int");
            result.put(
                buildKey("DAYNAME", MySQLTimeFunctionsTemplate.class),
                "String");
            result.put(
                buildKey("MONTHNAME", MySQLTimeFunctionsTemplate.class),
                "String");
            result.put(
                buildKey("QUARTER", MySQLTimeFunctionsTemplate.class),
                "Int");
            result.put(
                buildKey("WEEK", MySQLTimeFunctionsTemplate.class),
                "Int");
            result.put(
                buildKey("YEAR", MySQLTimeFunctionsTemplate.class),
                "Int");
            result.put(
                buildKey("HOUR", MySQLTimeFunctionsTemplate.class),
                "Int");
            result.put(
                buildKey("MINUTE", MySQLTimeFunctionsTemplate.class),
                "Int");
            result.put(
                buildKey("SECOND", MySQLTimeFunctionsTemplate.class),
                "Int");
            result.put(
                buildKey("TO_DAYS", MySQLTimeFunctionsTemplate.class),
                "Long");
        }

        return result;
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
                buildSpecialKey("PERIOD_ADD", MySQLTimeFunctionsTemplate.class),
                ANY__CALENDAR_INT_FUNCTION);
            result.put(
                buildSpecialKey("PERIOD_DIFF", MySQLTimeFunctionsTemplate.class),
                ANY__CALENDAR_CALENDAR_FUNCTION);
            result.put(
                buildSpecialKey("FROM_DAYS", MySQLTimeFunctionsTemplate.class),
                ANY__LONG_FUNCTION);
            result.put(
                buildSpecialKey("DATE_FORMAT", MySQLTimeFunctionsTemplate.class),
                ANY__CALENDAR_STRING_FUNCTION);
            result.put(
                buildSpecialKey("TIME_FORMAT", MySQLTimeFunctionsTemplate.class),
                ANY__CALENDAR_STRING_FUNCTION);
            result.put(
                buildSpecialKey("CURDATE", MySQLTimeFunctionsTemplate.class),
                ANY__FUNCTION);
            result.put(
                buildSpecialKey("CURRENT_DATE", MySQLTimeFunctionsTemplate.class),
                ANY__NOFUNCTION);
            result.put(
                buildSpecialKey("CURTIME", MySQLTimeFunctionsTemplate.class),
                ANY__FUNCTION);
            result.put(
                buildSpecialKey("CURRENT_TIME", MySQLTimeFunctionsTemplate.class),
                ANY__NOFUNCTION);
            result.put(
                buildSpecialKey("NOW", MySQLTimeFunctionsTemplate.class),
                ANY__FUNCTION);
            result.put(
                buildSpecialKey("SYSDATE", MySQLTimeFunctionsTemplate.class),
                ANY__FUNCTION);
            result.put(
                buildSpecialKey("CURRENT_TIMESTAMP", MySQLTimeFunctionsTemplate.class),
                ANY__NOFUNCTION);
            result.put(
                buildSpecialKey("UNIX_TIMESTAMP", MySQLTimeFunctionsTemplate.class),
                  ANY__FUNCTION
                + ANY__CALENDAR_FUNCTION);
            result.put(
                buildSpecialKey("FROM_UNIXTIME", MySQLTimeFunctionsTemplate.class),
                  ANY__LONG_FUNCTION
                + ANY__LONG_STRING_FUNCTION);
            result.put(
                buildSpecialKey("SEC_TO_TIME", MySQLTimeFunctionsTemplate.class),
                ANY__LONG_FUNCTION);
            result.put(
                buildSpecialKey("TIME_TO_SEC", MySQLTimeFunctionsTemplate.class),
                ANY__CALENDAR_FUNCTION);
        }
        
        return result;
    }

    /**
     * Creates the special function mappings' return types.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    protected Map fillUpSpecialMappingsReturnTypes(final Map mappings)
    {
        return buildSpecialMappingsReturnTypes(mappings);
    }

    /**
     * Creates the special function mappings' return types.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    public static Map buildSpecialMappingsReturnTypes(final Map mappings)
    {
        Map result = mappings;

        if  (result != null) 
        {
            result.put(
                buildSpecialKeyReturnType("PERIOD_ADD", MySQLTimeFunctionsTemplate.class),
                "Calendar");
            result.put(
                buildSpecialKeyReturnType("PERIOD_DIFF", MySQLTimeFunctionsTemplate.class),
                "Calendar");
            result.put(
                buildSpecialKeyReturnType("FROM_DAYS", MySQLTimeFunctionsTemplate.class),
                "Calendar");
            result.put(
                buildSpecialKeyReturnType("DATE_FORMAT", MySQLTimeFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("TIME_FORMAT", MySQLTimeFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("CURDATE", MySQLTimeFunctionsTemplate.class),
                "Calendar");
            result.put(
                buildSpecialKeyReturnType("CURRENT_DATE", MySQLTimeFunctionsTemplate.class),
                "Calendar");
            result.put(
                buildSpecialKeyReturnType("CURTIME", MySQLTimeFunctionsTemplate.class),
                "Calendar");
            result.put(
                buildSpecialKeyReturnType("CURRENT_TIME", MySQLTimeFunctionsTemplate.class),
                "Calendar");
            result.put(
                buildSpecialKeyReturnType("NOW", MySQLTimeFunctionsTemplate.class),
                "Calendar");
            result.put(
                buildSpecialKeyReturnType("SYSDATE", MySQLTimeFunctionsTemplate.class),
                "Calendar");
            result.put(
                buildSpecialKeyReturnType("CURRENT_TIMESTAMP", MySQLTimeFunctionsTemplate.class),
                "Calendar");
            result.put(
                buildSpecialKeyReturnType("UNIX_TIMESTAMP", MySQLTimeFunctionsTemplate.class),
                "Calendar");
            result.put(
                buildSpecialKeyReturnType("FROM_UNIXTIME", MySQLTimeFunctionsTemplate.class),
                "Calendar");
            result.put(
                buildSpecialKeyReturnType("SEC_TO_TIME", MySQLTimeFunctionsTemplate.class),
                "Calendar");
            result.put(
                buildSpecialKeyReturnType("TIME_TO_SEC", MySQLTimeFunctionsTemplate.class),
                "Long");
        }
        
        return result;
    }

    /**
     * Retrieves the mapping's  for given function.
     * @param function the function.
     * @return the mapping.
     */
    @Nullable
    protected String getMapping(final String function)
    {
        return
            getMapping(
                function, MySQLTimeFunctionsTemplate.class);
    }

    /**
     * Retrieves the special mapping's  for given function.
     * @param function the function.
     * @return the mapping.
     */
    @Nullable
    protected String getSpecialMapping(final String function)
    {
        return
            getSpecialMapping(
                function, MySQLTimeFunctionsTemplate.class);
    }

    /**
     * Retrieves the special mapping's return type for given function.
     * @param function the function.
     * @return the field type.
     */
    protected String getSpecialMappingReturnType(final String function)
    {
        return
            getSpecialMappingReturnType(
                function, MySQLTimeFunctionsTemplate.class);
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
                         MySQLTimeFunctionsTemplate.class))));
    }

    /**
     * Retrieves the default function method.
     * @return such method template.
     */
    protected String getDefaultFunctionMethod()
    {
        return ANY__CALENDAR_FUNCTION;
    }

    /**
     * Retrieves the capitalized words.
     * @return such words.
     */
    @NotNull
    protected String[] getCapitalizedWords()
    {
        return CAPITALIZED_WORDS;
    }

    /**
     * Retrieves the field types.
     * @return such array.
     */
    @NotNull
    protected String[] getFieldTypes()
    {
        return FIELD_TYPES;
    }

    /**
     * Retrieves the class name.
     * @return such name.
     */
    @NotNull
    protected static Class getTemplateClass()
    {
        return MySQLTimeFunctionsTemplate.class;
    }
}
