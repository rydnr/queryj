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
 * Description: Is able to generate the JUnit classes to test MySQL's
 *              time functions.
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
package org.acmsl.queryj.tools.templates.functions.time.mysql;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.templates.functions.time.mysql
    .MySQLTimeFunctionsTestTemplate;

import org.acmsl.queryj.tools.templates.functions.time
    .TimeFunctionsTestTemplate;

import org.acmsl.queryj.tools.templates.functions.time
    .TimeFunctionsTestTemplateGenerator;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Is able to generate the JUnit classes to test the Database's time functions.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class MySQLTimeFunctionsTestTemplateGenerator
    extends  TimeFunctionsTestTemplateGenerator
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Public constructor to allow reflective instantiation.
     */
    public MySQLTimeFunctionsTestTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        MySQLTimeFunctionsTestTemplateGenerator generator)
    {
        singleton = new WeakReference(generator);
    }

    /**
     * Retrieves the weak reference.
     * @return such reference.
     */
    protected static WeakReference getReference()
    {
        return singleton;
    }

    /**
     * Retrieves a TimeFunctionsTestTemplateGenerator instance.
     * @return such instance.
     */
    public static MySQLTimeFunctionsTestTemplateGenerator
        getMySQLInstance()
    {
        MySQLTimeFunctionsTestTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result =
                (MySQLTimeFunctionsTestTemplateGenerator)
                    reference.get();
        }

        if  (result == null) 
        {
            result =
                new MySQLTimeFunctionsTestTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Generates a time functions test template.
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
     * @return a template.
     * @throws QueryJException if the template factory is invalid.
     */
    public TimeFunctionsTestTemplate createTimeFunctionsTestTemplate(
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
      throws  QueryJException
    {
        TimeFunctionsTestTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            result =
                new MySQLTimeFunctionsTestTemplate(
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
                    classEnd) {};
        }

        return result;
    }

    /**
     * Generates a time functions test template.
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
     * @return a template.
     * @throws QueryJException if the template factory is invalid.
     */
    public TimeFunctionsTestTemplate createTimeFunctionsTestTemplate(
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
      throws  QueryJException
    {
        TimeFunctionsTestTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            result =
                new MySQLTimeFunctionsTestTemplate(
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
                    task) {};
        }

        return result;
    }

    /**
     * Generates a time functions test template.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @return a template.
     * @throws QueryJException if the template factory is invalid.
     */
    public TimeFunctionsTestTemplate createTimeFunctionsTestTemplate(
        String packageName,
        String testedPackageName,
        String engineName,
        String engineVersion,
        String quote)
      throws  QueryJException
    {
        TimeFunctionsTestTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            result =
                new MySQLTimeFunctionsTestTemplate(
                    packageName,
                    testedPackageName,
                    engineName,
                    engineVersion,
                    quote) {};
        }

        return result;
    }

    /**
     * Generates a time functions test template.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return a template.
     * @throws QueryJException if the template factory is invalid.
     */
    public TimeFunctionsTestTemplate createTimeFunctionsTestTemplate(
        String  packageName,
        String  testedPackageName,
        String  engineName,
        String  engineVersion,
        String  quote,
        Project project,
        Task    task)
      throws  QueryJException
    {
        TimeFunctionsTestTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            result =
                new MySQLTimeFunctionsTestTemplate(
                    packageName,
                    testedPackageName,
                    engineName,
                    engineVersion,
                    quote,
                    project,
                    task) {};
        }

        return result;
    }
}
