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
 * Filename: OracleNumericFunctionsTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate numeric function repositories according to
 *              Oracle database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.numeric.oracle;

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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * Is able to generate numeric function repositories according to Oracle
 * database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class OracleNumericFunctionsTemplate
    extends  NumericFunctionsTemplate
{
    /**
     * The capitalized words.
     */
    static final String[] CAPITALIZED_WORDS =
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
    static final String[] FIELD_TYPES =
        new String[]
        {
            "Double",
            "Int",
            "Long"
        };
    private static final long serialVersionUID = 4127957139576593734L;

    /**
     * Builds a <code>OracleNumericFunctionsTemplate</code>
     * using given information.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    public OracleNumericFunctionsTemplate(
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
                buildKey("ABS", OracleNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("CEIL", OracleNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("COS", OracleNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("COSH", OracleNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("EXP", OracleNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("FLOOR", OracleNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("LN", OracleNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("SIGN", OracleNumericFunctionsTemplate.class),
                "Int");
            result.put(
                buildKey("SIN", OracleNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("SINH", OracleNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("SQRT", OracleNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("TAN", OracleNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildKey("TANH", OracleNumericFunctionsTemplate.class),
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
                buildSpecialKey("LOG", OracleNumericFunctionsTemplate.class),
                ANY__DOUBLE_DOUBLE_FUNCTION);
            result.put(
                buildSpecialKey("MOD", OracleNumericFunctionsTemplate.class),
                ANY__LONG_LONG_FUNCTION);
            result.put(
                buildSpecialKey("POWER", OracleNumericFunctionsTemplate.class),
                ANY__DOUBLE_DOUBLE_FUNCTION);
            result.put(
                buildSpecialKey("ROUND", OracleNumericFunctionsTemplate.class),
                  ANY__DOUBLE_FUNCTION
                + ANY__DOUBLE_LONG_FUNCTION);
            result.put(
                buildSpecialKey("TRUNC", OracleNumericFunctionsTemplate.class),
                  ANY__DOUBLE_FUNCTION
                + ANY__DOUBLE_LONG_FUNCTION);
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
                buildSpecialKeyReturnType("LOG", OracleNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildSpecialKeyReturnType("MOD", OracleNumericFunctionsTemplate.class),
                "Long");
            result.put(
                buildSpecialKeyReturnType("POWER", OracleNumericFunctionsTemplate.class),
                "Double");
            result.put(
                buildSpecialKeyReturnType("ROUND", OracleNumericFunctionsTemplate.class),
                "Long");
            result.put(
                buildSpecialKeyReturnType("TRUNC", OracleNumericFunctionsTemplate.class),
                "Long");
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
        return getMapping(function, OracleNumericFunctionsTemplate.class);
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
                function, OracleNumericFunctionsTemplate.class);
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
                function, OracleNumericFunctionsTemplate.class);
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
                         "LOG",
                         OracleNumericFunctionsTemplate.class))));
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
        return ANY__DOUBLE_FUNCTION;
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
     * Retrieves the template name.
     * @return such name.
     */
    @NotNull
    public String getTemplateName()
    {
        return "legacy";
    }
}
