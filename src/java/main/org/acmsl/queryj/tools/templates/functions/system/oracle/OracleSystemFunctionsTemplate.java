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
 * Description: Is able to generate system function repositories according to
 *              Oracle database metadata.
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
package org.acmsl.queryj.tools.templates.functions.system.oracle;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.functions.system.SystemFunctionsTemplate;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Is able to generate system function repositories according to Oracle
 * database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class OracleSystemFunctionsTemplate
    extends  SystemFunctionsTemplate
{
    /**
     * The capitalized words.
     */
    protected static final String[] CAPITALIZED_WORDS = EMPTY_STRING_ARRAY;

    /**
     * The field types.
     */
    public static final String[] FIELD_TYPES =
        new String[]
        {
            "String",
            "Long"
        };

    /**
     * Builds a OracleSystemFunctionsTemplate using given information.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param singletonBody the singleton body.
     * @param classConstructor the class constructor.
     * @param innerClass the inner class.
     * @param classEnd the class end.
     */
    public OracleSystemFunctionsTemplate(
        String header,
        String packageDeclaration,
        String packageName,
        String engineName,
        String engineVersion,
        String quote,
        String acmslImports,
        String jdkImports,
        String javadoc,
        String classDefinition,
        String classStart,
        String singletonBody,
        String classConstructor,
        String innerClass,
        String classEnd)
    {
        super(
            header,
            packageDeclaration,
            packageName,
            engineName,
            engineVersion,
            quote,
            acmslImports,
            jdkImports,
            javadoc,
            classDefinition,
            classStart,
            singletonBody,
            classConstructor,
            innerClass,
            classEnd);
    }

    /**
     * Builds a OracleSystemFunctionsTemplate using given information.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    public OracleSystemFunctionsTemplate(
        String packageName,
        String engineName,
        String engineVersion,
        String quote)
    {
        this(
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            engineName,
            engineVersion,
            quote,
            ACMSL_IMPORTS,
            JDK_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            SINGLETON_BODY,
            CLASS_CONSTRUCTOR,
            INNER_CLASS,
            DEFAULT_CLASS_END);
    }

    /**
     * Builds the mappings.
     * @param mappings the initial mapping collection.
     * @return the updated collection.
     */
    protected Map fillUpMappings(Map mappings)
    {
        return buildMappings(mappings);
    }

    /**
     * Builds the mappings.
     * @param mappings the initial mapping collection.
     * @return the updated collection.
     */
    public static Map buildMappings(Map mappings)
    {
        Map result = mappings;

        if  (result != null)
        {
            result.put(
                buildKey("UID", OracleSystemFunctionsTemplate.class),
                "String");
            result.put(
                buildKey("USER", OracleSystemFunctionsTemplate.class),
                "String");
        }

        return result;
    }

    /**
     * Creates the special function mappings.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    protected Map fillUpSpecialMappings(Map mappings)
    {
        return buildSpecialMappings(mappings);
    }

    /**
     * Creates the special function mappings.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    public static Map buildSpecialMappings(Map mappings)
    {
        Map result = mappings;

        if  (result != null) 
        {
            result.put(
                buildSpecialKey("DUMP", OracleSystemFunctionsTemplate.class),
                ANY__STRING_INT_INT_FUNCTION);
            /* Too many parameters. Not supported yet.
                + ANY__STRING_INT_INT_INT_FUNCTION);
            */
            result.put(
                buildSpecialKey("GREATEST", OracleSystemFunctionsTemplate.class),
                  ANY__STRING_FUNCTION
                + ANY__ASTRING_FUNCTION);
            result.put(
                buildSpecialKey("LEAST", OracleSystemFunctionsTemplate.class),
                  ANY__STRING_FUNCTION
                + ANY__ASTRING_FUNCTION);
            result.put(
                buildSpecialKey("NVL", OracleSystemFunctionsTemplate.class),
                ANY__STRING_STRING_FUNCTION);
            result.put(
                buildSpecialKey("VSIZE", OracleSystemFunctionsTemplate.class),
                ANY__STRING_FUNCTION);
        }
        
        return result;
    }

    /**
     * Creates the special function mappings' return types.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    protected Map fillUpSpecialMappingsReturnTypes(Map mappings)
    {
        return buildSpecialMappingsReturnTypes(mappings);
    }

    /**
     * Creates the special function mappings' return types.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    public static Map buildSpecialMappingsReturnTypes(Map mappings)
    {
        Map result = mappings;

        if  (result != null)
        {
            result.put(
                buildSpecialKeyReturnType("DUMP", OracleSystemFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("GREATEST", OracleSystemFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("LEAST", OracleSystemFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("NVL", OracleSystemFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("UID", OracleSystemFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("USER", OracleSystemFunctionsTemplate.class),
                "String");
            result.put(
                buildSpecialKeyReturnType("VSIZE", OracleSystemFunctionsTemplate.class),
                "Long");
        }

        return result;
    }

    /**
     * Retrieves the mapping for given function.
     * @param function the function.
     * @return the mapping.
     */
    protected String getMapping(String function)
    {
        return getMapping(function, OracleSystemFunctionsTemplate.class);
    }

    /**
     * Retrieves the mapping for given function.
     * @param function the function.
     * @return the mapping.
     */
    protected String getSpecialMapping(String function)
    {
        return
            getSpecialMapping(
                function, OracleSystemFunctionsTemplate.class);
    }

    /**
     * Retrieves the mapping for given function.
     * @param function the function.
     * @return the field type.
     */
    protected String getSpecialMappingReturnType(String function)
    {
        return
            getSpecialMappingReturnType(
                function, OracleSystemFunctionsTemplate.class);
    }

    /**
     * Checks whether given map is already filled with specific
     * template mappings.
     * @param mapping the mapping table.
     * @return <code>true</code> if the map contains the specific
     * mapping entries for this template.
     */
    protected boolean isFilledIn(Map mappings)
    {
        return
            (   (mappings != null)
             && (mappings.containsKey(
                     buildSpecialKey(
                         "DUMP",
                         OracleSystemFunctionsTemplate.class))));
    }

    /**
     * Checks whether a concrete mapping can be omitted without generating a
     * warning message.
     * @param mapping the concrete mapping.
     * @return <code>true</code> to generate the warning message.
     */
    protected boolean generateWarning(String mapping)
    {
        return false;
    }

    /**
     * Retrieves the default function method.
     * @return such method template.
     */
    protected String getDefaultFunctionMethod()
    {
        return ANY__NOFUNCTION;
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
