//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Description: Is able to generate system function repositories according to
 *              MySQL database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.system.mysql;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTemplate;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Is able to generate system function repositories according to MySQL
 * database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class MySQLSystemFunctionsTemplate
    extends  SystemFunctionsTemplate
{
    /**
     * The capitalized words.
     */
    static final String[] CAPITALIZED_WORDS =
        new String[]
        {
            "user",
            "insert",
            "id"
        };

    /**
     * The field types.
     */
    static final String[] FIELD_TYPES =
        new String[]
        {
            "String",
            "Long"
        };

    /**
     * Builds a MySQLSystemFunctionsTemplate using given information.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    public MySQLSystemFunctionsTemplate(
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
                buildKey("DATABASE", MySQLSystemFunctionsTemplate.class),
                "String");
            result.put(
                buildKey("USER", MySQLSystemFunctionsTemplate.class),
                "String");
            result.put(
                buildKey("SYSTEM_USER", MySQLSystemFunctionsTemplate.class),
                "String");
            result.put(
                buildKey("SESSION_USER", MySQLSystemFunctionsTemplate.class),
                "String");
            result.put(
                buildKey("VERSION", MySQLSystemFunctionsTemplate.class),
                "String");
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
                buildSpecialKey("PASSWORD", MySQLSystemFunctionsTemplate.class),
                ANY__STRING_FUNCTION);
            result.put(
                buildSpecialKey("ENCRYPT", MySQLSystemFunctionsTemplate.class),
                ANY__STRING_FUNCTION);
            result.put(
                buildSpecialKey("LAST_INSERT_ID", MySQLSystemFunctionsTemplate.class),
                  ANY__FUNCTION
                + ANY__FIELD_FUNCTION);
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
                buildSpecialKeyReturnType("PASSWORD", MySQLSystemFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("ENCRYPT", MySQLSystemFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("LAST_INSERT_ID", MySQLSystemFunctionsTemplate.class),
                "Long");
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
        return getMapping(function, MySQLSystemFunctionsTemplate.class);
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
                function, MySQLSystemFunctionsTemplate.class);
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
                function, MySQLSystemFunctionsTemplate.class);
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
                         "PASSWORD",
                         MySQLSystemFunctionsTemplate.class))));
    }

    /**
     * Retrieves the default function method.
     * @return such method template.
     */
    protected String getDefaultFunctionMethod()
    {
        return ANY__FUNCTION;
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
