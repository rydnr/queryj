/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate numeric function repositories according to
 *              MySQL database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.numeric.mysql;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.functions.numeric
    .NumericFunctionsTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Is able to generate numeric function repositories according to MySQL
 * database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class MySQLNumericFunctionsTemplate
    extends  NumericFunctionsTemplate
{
    /**
     * The capitalized words.
     */
    protected static final String[] CAPITALIZED_WORDS =
        new String[]
        {
            "abs",
            "acos",
            "asin",
            "atan",
            "bit",
            "count",
            "ceiling",
            "degrees",
            "floor",
            "log",
            "max",
            "min",
            "mod",
            "pi",
            "pow",
            "radians",
            "rand",
            "round",
            "sqrt",
            "truncate"
        };

    /**
     * The field types.
     */
    public static final String[] FIELD_TYPES =
        new String[]
        {
            "Double",
            "Int",
            "Long",
        };

    /**
     * Builds a MySQLNumericFunctionsTemplate using given information.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    public MySQLNumericFunctionsTemplate(
        final DecoratorFactory decoratorFactory,
        String  packageName,
        String  engineName,
        String  engineVersion,
        String  quote)
    {
        super(
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
    protected Map fillUpMappings(final Map mappings)
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
                buildKey("ABS", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("ACOS", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("ASIN", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("ATAN", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("ATAN2", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("BIT_COUNT", MySQLNumericFunctionsTemplate.class),
                "Int");
            result.put(
                buildKey("CEILING", MySQLNumericFunctionsTemplate.class),
                "Long");
            result.put(
                buildKey("COS", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("COT", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("DEGREES", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("EXP", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("FLOOR", MySQLNumericFunctionsTemplate.class),
                "Long");
            result.put(
                buildKey("LOG", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("LOG10", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("RADIANS", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("SIN", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("SQRT", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("TAN", MySQLNumericFunctionsTemplate.class),
                "Double");
        }

        return result;
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
                buildSpecialKey("MAX", MySQLNumericFunctionsTemplate.class),
                ANY__DOUBLE_DOUBLE_FUNCTION);
            result.put(
                buildSpecialKey("MIN", MySQLNumericFunctionsTemplate.class),
                ANY__DOUBLE_DOUBLE_FUNCTION);
            result.put(
                buildSpecialKey("MOD", MySQLNumericFunctionsTemplate.class),
                ANY__DOUBLE_LONG_FUNCTION);
            result.put(
                buildSpecialKey("PI", MySQLNumericFunctionsTemplate.class),
                ANY__FUNCTION);
            result.put(
                buildSpecialKey("POW", MySQLNumericFunctionsTemplate.class),
                ANY__DOUBLE_DOUBLE_FUNCTION);
            result.put(
                buildSpecialKey("POWER", MySQLNumericFunctionsTemplate.class),
                ANY__DOUBLE_DOUBLE_FUNCTION);
            result.put(
                buildSpecialKey("RAND", MySQLNumericFunctionsTemplate.class),
                  ANY__FUNCTION
                + ANY__DOUBLE_FUNCTION);
            result.put(
                buildSpecialKey("ROUND", MySQLNumericFunctionsTemplate.class),
                  ANY__DOUBLE_FUNCTION
                + ANY__DOUBLE_DOUBLE_FUNCTION);
            result.put(
                buildSpecialKey("TRUNCATE", MySQLNumericFunctionsTemplate.class),
                ANY__DOUBLE_DOUBLE_FUNCTION);
        }
        
        return result;
    }

    /**
     * Builds the special mappings' return types.
     * @param mappings the initial mapping collection.
     * @return the updated collection.
     */
    protected Map fillUpSpecialMappingsReturnTypes(final Map mappings)
    {
        return buildSpecialMappingsReturnTypes(mappings);
    }

    /**
     * Builds the special mappings' return types.
     * @param mappings the initial mapping collection.
     * @return the updated collection.
     */
    public static Map buildSpecialMappingsReturnTypes(final Map mappings)
    {
        Map result = mappings;

        if  (result != null)
        {
            result.put(
                buildSpecialKeyReturnType("MAX", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildSpecialKeyReturnType("MIN", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildSpecialKeyReturnType("MOD", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildSpecialKeyReturnType("PI", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildSpecialKeyReturnType("POW", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildSpecialKeyReturnType("POWER", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildSpecialKeyReturnType("RAND", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildSpecialKeyReturnType("ROUND", MySQLNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildSpecialKeyReturnType("TRUNCATE", MySQLNumericFunctionsTemplate.class),
                "Double");
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
        return getMapping(function, MySQLNumericFunctionsTemplate.class);
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
                function, MySQLNumericFunctionsTemplate.class);
    }

    /**
     * Retrieves the mapping for given function.
     * @param function the function.
     * @return the field type.
     */
    protected String getSpecialMappingReturnType(final String function)
    {
        return
            getSpecialMappingReturnType(
                function, MySQLNumericFunctionsTemplate.class);
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
                         "MAX",
                         MySQLNumericFunctionsTemplate.class))));
    }

    /**
     * Retrieves the default function method.
     * @return such method template.
     */
    protected String getDefaultFunctionMethod()
    {
        return ANY__DOUBLE_FUNCTION;
    }

    /**
     * Retrieves the capitalized words.
     * @return such words.
     */
    protected String[] getCapitalizedWords()
    {
        return CAPITALIZED_WORDS;
    }

    /**
     * Retrieves the field types.
     * @return such array.
     */
    protected String[] getFieldTypes()
    {
        return FIELD_TYPES;
    }
}
