/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
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
 * Description: Is able to generate tests on MySQL database's text
 *              functions.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package org.acmsl.queryj.tools.templates.functions.text.mysql;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.tools.templates.functions.text.TextFunctionsTemplate;
import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTestTemplate;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Is able to generate tests on MySQL database's text functions.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class MySQLTextFunctionsTestTemplate
    extends  TextFunctionsTestTemplate
{
    /**
     * Builds a MySQLTextFunctionsTestTemplate using given information.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    public MySQLTextFunctionsTestTemplate(
        String  packageName,
        String  testedPackageName,
        String  engineName,
        String  engineVersion,
        String  quote,
        Project project,
        Task    task)
    {
        super(
            packageName,
            testedPackageName,
            engineName,
            engineVersion,
            quote,
            project,
            task);
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
        return MySQLTextFunctionsTemplate.buildMappings(mappings);
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
                buildSpecialKey("CONCAT", MySQLTextFunctionsTestTemplate.class),
                ANY__STRING_STRING_FUNCTION_TEST);
            result.put(
                buildSpecialKey("ELT", MySQLTextFunctionsTestTemplate.class),
                ANY__INT_STRING_STRING_FUNCTION_TEST);
            result.put(
                buildSpecialKey("FIELD", MySQLTextFunctionsTestTemplate.class),
                ANY__STRING_STRING_STRING_FUNCTION_TEST);
            result.put(
                buildSpecialKey("FIND_IN_SET", MySQLTextFunctionsTestTemplate.class),
                ANY__STRING_STRING_FUNCTION_TEST);
            /*
            // TOO MANY METHOD PARAMETER SIGNATURES FOR THIS RELEASE
            result.put(
                buildSpecialKey("INSERT", MySQLTextFunctionsTestTemplate.class),
                ANY__STRING_INT_INT_STRING_FUNCTION_TEST);
            */
            result.put(
                buildSpecialKey("INSTR", MySQLTextFunctionsTestTemplate.class),
                ANY__STRING_STRING_FUNCTION_TEST);
            /*
            result.put(
                buildSpecialKey("INTERVAL", TextFunctionsTestTemplate.class),
                "Int"); // ?
            */
            result.put(
                buildSpecialKey("LEFT", MySQLTextFunctionsTestTemplate.class),
                ANY__STRING_INT_FUNCTION_TEST);
            result.put(
                buildSpecialKey("LOCATE", MySQLTextFunctionsTestTemplate.class),
                ANY__STRING_STRING_INT_FUNCTION_TEST);
            result.put(
                buildSpecialKey("MID", MySQLTextFunctionsTestTemplate.class),
                ANY__STRING_INT_INT_FUNCTION_TEST);
            /*
            // NOT SUPPORTED -> POSITION(str2 IN str2).
            // Use LOCATE instead.
            result.put(
                buildSpecialKey("POSITION", MySQLTextFunctionsTestTemplate.class),
                ANY__STRING_STRING_INT_FUNCTION_TEST);
            */
            result.put(
                buildSpecialKey("REPEAT", MySQLTextFunctionsTestTemplate.class),
                ANY__STRING_INT_FUNCTION_TEST);
            result.put(
                buildSpecialKey("REPLACE", MySQLTextFunctionsTestTemplate.class),
                ANY__STRING_STRING_STRING_FUNCTION_TEST);
            result.put(
                buildSpecialKey("RIGHT", MySQLTextFunctionsTestTemplate.class),
                ANY__STRING_INT_FUNCTION_TEST);
            result.put(
                buildSpecialKey("SPACE", MySQLTextFunctionsTestTemplate.class),
                ANY__INT_FUNCTION_TEST);
            result.put(
                buildSpecialKey("SUBSTRING", MySQLTextFunctionsTestTemplate.class),
                  ANY__STRING_INT_FUNCTION_TEST
                + ANY__STRING_INT_INT_FUNCTION_TEST);
            result.put(
                buildSpecialKey("SUBSTRING_INDEX", MySQLTextFunctionsTestTemplate.class),
                ANY__STRING_STRING_INT_FUNCTION_TEST);
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
                function, MySQLTextFunctionsTestTemplate.class);
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
                         MySQLTextFunctionsTestTemplate.class))));
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
        return MySQLTextFunctionsTemplate.CAPITALIZED_WORDS;
    }

    /**
     * Retrieves the field types.
     * @return such array.
     */
    protected String[] getFieldTypes()
    {
        return MySQLTextFunctionsTemplate.FIELD_TYPES;
    }
}
