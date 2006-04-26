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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate text function repositories according to
 *              MySQL database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.text.mysql;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.functions.text.TextFunctionsTemplate;

/*
 * Importing some JDK classes.
 */
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * Is able to generate text function repositories according to MySQL
 * database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class MySQLTextFunctionsTemplate
    extends  TextFunctionsTemplate
{
    /**
     * The capitalized words.
     */
    static final String[] CAPITALIZED_WORDS =
        (String[])
            Collections.unmodifiableList(
                Arrays.asList(
                    new String[]
                    {
                        "ascii",
                        "char",
                        "length",
                     // "in",
                        "insert",
                        "index"
                    }))
            .toArray();

    /**
     * The field types.
     */
    static final String[] FIELD_TYPES =
        (String[])
            Collections.unmodifiableList(
                Arrays.asList(
                    new String[]
                    {
                        "String",
                        "Int"
                    }))
            .toArray();

    /**
     * Builds a <code>MySQLTextFunctionsTemplate</code> using given
     * information.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    public MySQLTextFunctionsTemplate(
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
                buildKey("ASCII", MySQLTextFunctionsTemplate.class),
                "String"); // The metadata says ACII instead
            result.put(
                buildKey("CHAR", MySQLTextFunctionsTemplate.class),
                "String");
            result.put(
                buildKey("CHAR_LENGTH", MySQLTextFunctionsTemplate.class),
                "Int");
            result.put(
                buildKey("CHARACTER_LENGTH", MySQLTextFunctionsTemplate.class),
                "Int");
            result.put(
                buildKey("LCASE", MySQLTextFunctionsTemplate.class),
                "String");
            result.put(
                buildKey("LENGTH", MySQLTextFunctionsTemplate.class),
                "Int");
            result.put(
                buildKey("LOWER", MySQLTextFunctionsTemplate.class),
                "String"); // ?
            result.put(
                buildKey("LTRIM", MySQLTextFunctionsTemplate.class),
                "String");
            result.put(
                buildKey("OCTET_LENGTH", MySQLTextFunctionsTemplate.class),
                "Int"); // ?
            result.put(
                buildKey("REVERSE", MySQLTextFunctionsTemplate.class),
                "String");
            result.put(
                buildKey("RTRIM", MySQLTextFunctionsTemplate.class),
                "String");
            result.put(
                buildKey("SOUNDEX", MySQLTextFunctionsTemplate.class),
                "String");
            result.put(
                buildKey("TRIM", MySQLTextFunctionsTemplate.class),
                "String");
            result.put(
                buildKey("UCASE", MySQLTextFunctionsTemplate.class),
                "String");
            result.put(
                buildKey("UPPER", MySQLTextFunctionsTemplate.class),
                "String"); // ?
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
                buildSpecialKey("CONCAT", MySQLTextFunctionsTemplate.class),
                ANY__STRING_STRING_FUNCTION);
            result.put(
                buildSpecialKey("ELT", MySQLTextFunctionsTemplate.class),
                ANY__INT_STRING_STRING_FUNCTION);
            result.put(
                buildSpecialKey("FIELD", MySQLTextFunctionsTemplate.class),
                ANY__STRING_STRING_STRING_FUNCTION);
            result.put(
                buildSpecialKey("FIND_IN_SET", MySQLTextFunctionsTemplate.class),
                ANY__STRING_STRING_FUNCTION);
            /*
            // TOO MANY METHOD PARAMETER SIGNATURES FOR THIS RELEASE
            result.put(
                buildSpecialKey("INSERT", MySQLTextFunctionsTemplate.class),
                ANY__STRING_INT_INT_STRING_FUNCTION);
            */
            result.put(
                buildSpecialKey("INSTR", MySQLTextFunctionsTemplate.class),
                ANY__STRING_STRING_FUNCTION);
            /*
            result.put(
                buildSpecialKey("INTERVAL", MySQLTextFunctionsTemplate.class),
                "Int"); // ?
            */
            result.put(
                buildSpecialKey("LEFT", MySQLTextFunctionsTemplate.class),
                ANY__STRING_INT_FUNCTION);
            result.put(
                buildSpecialKey("LOCATE", MySQLTextFunctionsTemplate.class),
                ANY__STRING_STRING_INT_FUNCTION);
            result.put(
                buildSpecialKey("MID", MySQLTextFunctionsTemplate.class),
                ANY__STRING_INT_INT_FUNCTION);
            /*
            // NOT SUPPORTED -> POSITION(str2 IN str2).
            // Use LOCATE instead.
            result.put(
                buildSpecialKey("POSITION", MySQLTextFunctionsTemplate.class),
                ANY__STRING_STRING_INT_FUNCTION);
            */
            result.put(
                buildSpecialKey("REPEAT", MySQLTextFunctionsTemplate.class),
                ANY__STRING_INT_FUNCTION);
            result.put(
                buildSpecialKey("REPLACE", MySQLTextFunctionsTemplate.class),
                ANY__STRING_STRING_STRING_FUNCTION);
            result.put(
                buildSpecialKey("RIGHT", MySQLTextFunctionsTemplate.class),
                ANY__STRING_INT_FUNCTION);
            result.put(
                buildSpecialKey("SPACE", MySQLTextFunctionsTemplate.class),
                ANY__INT_FUNCTION);
            result.put(
                buildSpecialKey("SUBSTRING", MySQLTextFunctionsTemplate.class),
                  ANY__STRING_INT_FUNCTION
                + ANY__STRING_INT_INT_FUNCTION);
            result.put(
                buildSpecialKey("SUBSTRING_INDEX", MySQLTextFunctionsTemplate.class),
                ANY__STRING_STRING_INT_FUNCTION);
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
                buildSpecialKeyReturnType("CONCAT", MySQLTextFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("ELT", MySQLTextFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("FIELD", MySQLTextFunctionsTemplate.class),
                "Int");
            result.put(
                buildSpecialKeyReturnType("FIND_IN_SET", MySQLTextFunctionsTemplate.class),
                "Int");
            result.put(
                buildSpecialKeyReturnType("INSERT", MySQLTextFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("INSTR", MySQLTextFunctionsTemplate.class),
                "Int");
            result.put(
                buildSpecialKeyReturnType("INTERVAL", MySQLTextFunctionsTemplate.class),
                "Int"); // ?
            result.put(
                buildSpecialKeyReturnType("LEFT", MySQLTextFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("LOCATE", MySQLTextFunctionsTemplate.class),
                "Int");
            result.put(
                buildSpecialKeyReturnType("MID", MySQLTextFunctionsTemplate.class),
                "String"); // ?
            result.put(
                buildSpecialKeyReturnType("POSITION", MySQLTextFunctionsTemplate.class),
                "String"); // ?
            result.put(
                buildSpecialKeyReturnType("REPEAT", MySQLTextFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("REPLACE", MySQLTextFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("RIGHT", MySQLTextFunctionsTemplate.class),
                "Int");
            result.put(
                buildSpecialKeyReturnType("SPACE", MySQLTextFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("SUBSTRING", MySQLTextFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("SUBSTRING_INDEX", MySQLTextFunctionsTemplate.class),
                "String");
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
        return getMapping(function, MySQLTextFunctionsTemplate.class);
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
                function, MySQLTextFunctionsTemplate.class);
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
                function, MySQLTextFunctionsTemplate.class);
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
                         "CONCAT",
                         MySQLTextFunctionsTemplate.class))));
    }

    /**
     * Retrieves the default function method.
     * @return such method template.
     */
    protected String getDefaultFunctionMethod()
    {
        return ANY__STRING_FUNCTION;
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
