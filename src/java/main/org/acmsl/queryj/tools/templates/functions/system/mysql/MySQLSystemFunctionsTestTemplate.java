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
 * Description: Is able to generate tests on MySQL's system functions.
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
package org.acmsl.queryj.tools.templates.functions.system.mysql;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.tools.templates.functions.system.mysql
    .MySQLSystemFunctionsTemplate;
import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTestTemplate;

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
 * Is able to generate tests on MySQL database's system functions.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class MySQLSystemFunctionsTestTemplate
    extends  SystemFunctionsTestTemplate
{
    /**
     * Builds a MySQLSystemFunctionsTestTemplate using given information.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param projectImports the JDK imports.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param junitImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param testFunctionMethod the test function method.
     * @param classConstructor the class constructor.
     * @param memberAccessors the member accessors.
     * @param setUpTearDownMethods the setUp and tearDown methods.
     * @param mainMethod the main method.
     * @param getInstanceTest the getInstance test.
     * @param innerClass the inner class.
     * @param innerTable the inner table.
     * @param classEnd the class end.
     */
    public MySQLSystemFunctionsTestTemplate(
        String header,
        String packageDeclaration,
        String packageName,
        String testedPackageName,
        String engineName,
        String engineVersion,
        String quote,
        String projectImports,
        String acmslImports,
        String jdkImports,
        String junitImports,
        String javadoc,
        String classDefinition,
        String classStart,
        String classConstructor,
        String memberAccessors,
        String setUpTearDownMethods,
        String mainMethod,
        String getInstanceTest,
        String innerClass,
        String innerTable,
        String classEnd)
    {
        super(
            header,
            packageDeclaration,
            packageName,
            testedPackageName,
            engineName,
            engineVersion,
            quote,
            projectImports,
            acmslImports,
            jdkImports,
            junitImports,
            javadoc,
            classDefinition,
            classStart,
            classConstructor,
            memberAccessors,
            setUpTearDownMethods,
            mainMethod,
            getInstanceTest,
            innerClass,
            innerTable,
            classEnd);
    }

    /**
     * Builds a MySQLSystemFunctionsTestTemplate using given information.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param projectImports the JDK imports.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param junitImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param testFunctionMethod the test function method.
     * @param classConstructor the class constructor.
     * @param memberAccessors the member accessors.
     * @param setUpTearDownMethods the setUp and tearDown methods.
     * @param mainMethod the main method.
     * @param getInstanceTest the getInstance test.
     * @param innerClass the inner class.
     * @param innerTable the inner table.
     * @param classEnd the class end.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    public MySQLSystemFunctionsTestTemplate(
        String  header,
        String  packageDeclaration,
        String  packageName,
        String  testedPackageName,
        String  engineName,
        String  engineVersion,
        String  quote,
        String  projectImports,
        String  acmslImports,
        String  jdkImports,
        String  junitImports,
        String  javadoc,
        String  classDefinition,
        String  classStart,
        String  classConstructor,
        String  memberAccessors,
        String  setUpTearDownMethods,
        String  mainMethod,
        String  getInstanceTest,
        String  innerClass,
        String  innerTable,
        String  classEnd,
        Project project,
        Task    task)
    {
        super(
            header,
            packageDeclaration,
            packageName,
            testedPackageName,
            engineName,
            engineVersion,
            quote,
            projectImports,
            acmslImports,
            jdkImports,
            junitImports,
            javadoc,
            classDefinition,
            classStart,
            classConstructor,
            memberAccessors,
            setUpTearDownMethods,
            mainMethod,
            getInstanceTest,
            innerClass,
            innerTable,
            classEnd,
            project,
            task);
    }

    /**
     * Builds a SystemFunctionsTestTemplate using given information.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    public MySQLSystemFunctionsTestTemplate(
        String packageName,
        String testedPackageName,
        String engineName,
        String engineVersion,
        String quote)
    {
        this(
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            testedPackageName,
            engineName,
            engineVersion,
            quote,
            PROJECT_IMPORTS,
            ACMSL_IMPORTS,
            JDK_IMPORTS,
            JUNIT_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            CLASS_CONSTRUCTOR,
            MEMBER_ACCESSORS,
            SETUP_TEARDOWN_METHODS,
            MAIN_METHOD,
            GET_INSTANCE_TEST,
            INNER_CLASS,
            INNER_TABLE,
            DEFAULT_CLASS_END);
    }

    /**
     * Builds a SystemFunctionsTestTemplate using given information.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    public MySQLSystemFunctionsTestTemplate(
        String  packageName,
        String  testedPackageName,
        String  engineName,
        String  engineVersion,
        String  quote,
        Project project,
        Task    task)
    {
        this(
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            testedPackageName,
            engineName,
            engineVersion,
            quote,
            PROJECT_IMPORTS,
            ACMSL_IMPORTS,
            JDK_IMPORTS,
            JUNIT_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            CLASS_CONSTRUCTOR,
            MEMBER_ACCESSORS,
            SETUP_TEARDOWN_METHODS,
            MAIN_METHOD,
            GET_INSTANCE_TEST,
            INNER_CLASS,
            INNER_TABLE,
            DEFAULT_CLASS_END,
            project,
            task);
    }

    /**
     * Creates the special function mappings.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    protected Map fillUpMappings(Map mappings)
    {
        return buildMappings(mappings);
    }

    /**
     * Creates the special function mappings.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    public static Map buildMappings(Map mappings)
    {
        return MySQLSystemFunctionsTemplate.buildMappings(mappings);
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
                buildSpecialKey("PASSWORD", MySQLSystemFunctionsTestTemplate.class),
                ANY__STRING_FUNCTION_TEST);
            result.put(
                buildSpecialKey("ENCRYPT", MySQLSystemFunctionsTestTemplate.class),
                ANY__STRING_FUNCTION_TEST);
            result.put(
                buildSpecialKey("LAST_INSERT_ID", MySQLSystemFunctionsTestTemplate.class),
                  ANY__FUNCTION_TEST
                + ANY__FIELD_FUNCTION_TEST);
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
        return getMapping(function, MySQLSystemFunctionsTemplate.class);
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
                function, MySQLSystemFunctionsTestTemplate.class);
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
                         "PASSWORD",
                         MySQLSystemFunctionsTestTemplate.class))));
    }

    /**
     * Retrieves the default function method.
     * @return such method template.
     */
    protected String getDefaultFunctionMethod()
    {
        return ANY__FUNCTION_TEST;
    }

    /**
     * Retrieves the capitalized words.
     * @return such words.
     */
    protected String[] getCapitalizedWords()
    {
        return MySQLSystemFunctionsTemplate.CAPITALIZED_WORDS;
    }

    /**
     * Retrieves the field types.
     * @return such array.
     */
    protected String[] getFieldTypes()
    {
        return MySQLSystemFunctionsTemplate.FIELD_TYPES;
    }
}
