/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

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
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendáriz
 *
 * Description: Is able to generate the JUnit classes to test the Database's
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
package org.acmsl.queryj.tools.templates.functions.time;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.functions.time
    .TimeFunctionsTestTemplate;

import org.acmsl.queryj.tools.templates.functions.time
    .TimeFunctionsTestTemplateFactory;

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
public class TimeFunctionsTestTemplateGenerator
    implements  TimeFunctionsTestTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Public constructor to allow reflective instantiation.
     */
    public TimeFunctionsTestTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(TimeFunctionsTestTemplateGenerator generator)
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
    public static TimeFunctionsTestTemplateGenerator getInstance()
    {
        TimeFunctionsTestTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (TimeFunctionsTestTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new TimeFunctionsTestTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Retrieves the template factory instance.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     */
    protected TimeFunctionsTestTemplateFactory getTemplateFactory(
            String engineName, String engineVersion)
        throws  QueryJException
    {
        TimeFunctionsTestTemplateFactory result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            Object t_TemplateFactory =
                t_MappingManager.getTemplateFactory(
                    TemplateMappingManager.TIME_FUNCTIONS_TEST_TEMPLATE,
                    engineName,
                    engineVersion);

            if  (t_TemplateFactory != null)
            {
                if  (!(t_TemplateFactory instanceof TimeFunctionsTestTemplateFactory))
                {
                    throw
                        new QueryJException(
                            "invalid.time.function.test.template.factory");
                }
                else 
                {
                    result = (TimeFunctionsTestTemplateFactory) t_TemplateFactory;
                }
            }
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
            TimeFunctionsTestTemplateFactory t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);

            if  (t_TemplateFactory != null)
            {
                result =
                    t_TemplateFactory.createTimeFunctionsTestTemplate(
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
            else 
            {
                throw
                    new QueryJException(
                        "cannot.find.test.template.factory.for." + engineName);
            }
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
            TimeFunctionsTestTemplateFactory t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);

            if  (t_TemplateFactory != null)
            {
                result =
                    t_TemplateFactory.createTimeFunctionsTestTemplate(
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
            else 
            {
                if  (project != null)
                {
                    project.log(
                        task,
                          "Invalid time functions test template "
                        + "generator: " + t_TemplateFactory,
                        Project.MSG_WARN);
                }
                
                throw
                    new QueryJException(
                        "cannot.find.test.template.factory.for." + engineName);
            }
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
            TimeFunctionsTestTemplateFactory t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);

            if  (t_TemplateFactory != null)
            {
                result =
                    t_TemplateFactory.createTimeFunctionsTestTemplate(
                        packageName,
                        testedPackageName,
                        engineName,
                        engineVersion,
                        quote);
            }
            else 
            {
                throw
                    new QueryJException(
                        "cannot.find.test.template.factory.for." + engineName);
            }
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
            TimeFunctionsTestTemplateFactory t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);

            if  (t_TemplateFactory != null)
            {
                result =
                    t_TemplateFactory.createTimeFunctionsTestTemplate(
                        packageName,
                        testedPackageName,
                        engineName,
                        engineVersion,
                        quote,
                        project,
                        task);
            }
            else 
            {
                if  (project != null)
                {
                    project.log(
                        task,
                          "Invalid time functions test template "
                        + "generator: " + t_TemplateFactory,
                        Project.MSG_WARN);
                }
                
                throw
                    new QueryJException(
                        "cannot.find.test.template.factory.for." + engineName);
            }
        }

        return result;
    }

    /**
     * Writes a time functions test template to disk.
     * @param timeFunctionsTestTemplate the time functions template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     */
    public void write(
            TimeFunctionsTestTemplate timeFunctionsTestTemplate,
            File                      outputDir)
        throws  IOException
    {
        if  (   (timeFunctionsTestTemplate != null)
             && (outputDir                 != null))
        {
            StringUtils t_StringUtils = StringUtils.getInstance();
            FileUtils t_FileUtils = FileUtils.getInstance();

            if  (   (t_StringUtils != null)
                 && (t_FileUtils   != null))
            {
                outputDir.mkdirs();

                t_FileUtils.writeFile(
                      outputDir.getAbsolutePath()
                    + File.separator
                    + "TimeFunctionsTest.java",
                    timeFunctionsTestTemplate.toString());
            }
        }
    }
}
