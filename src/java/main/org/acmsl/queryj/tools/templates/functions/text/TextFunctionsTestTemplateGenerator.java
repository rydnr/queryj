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
 * Description: Is able to generate the JUnit classes to test the Database's
 *              text functions.
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
package org.acmsl.queryj.tools.templates.functions.text;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTestTemplate;

import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTestTemplateFactory;

import org.acmsl.queryj.tools.templates.TemplateMappingManager;

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
 * Is able to generate the JUnit classes to test the Database's text functions.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class TextFunctionsTestTemplateGenerator
    implements  TextFunctionsTestTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected TextFunctionsTestTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        TextFunctionsTestTemplateGenerator generator)
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
     * Retrieves a TextFunctionsTestTemplateGenerator instance.
     * @return such instance.
     */
    public static TextFunctionsTestTemplateGenerator getInstance()
    {
        TextFunctionsTestTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (TextFunctionsTestTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new TextFunctionsTestTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Adds a new template factory class.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     */
    public void addTemplateFactoryClass(
        String engineName,
        String engineVersion,
        String templateFactoryClass)
    {
        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (   (t_MappingManager     != null)
             && (engineName           != null)
             && (templateFactoryClass != null))
        {
            t_MappingManager.addTemplateFactoryClass(
                TemplateMappingManager.TEXT_FUNCTIONS_TEST_TEMPLATE,
                engineName,
                engineVersion,
                templateFactoryClass);
        }
    }

    /**
     * Retrieves the template factory class.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     */
    protected String getTemplateFactoryClass(
        String engineName, String engineVersion)
    {
        String result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (   (t_MappingManager != null)
             && (engineName       != null))
        {
            result =
                t_MappingManager.getTemplateFactoryClass(
                    TemplateMappingManager.TEXT_FUNCTIONS_TEST_TEMPLATE,
                    engineName,
                    engineVersion);
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
    protected TextFunctionsTestTemplateFactory getTemplateFactory(
            String engineName, String engineVersion)
        throws  QueryJException
    {
        TextFunctionsTestTemplateFactory result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            Object t_TemplateFactory =
                t_MappingManager.getTemplateFactory(
                    TemplateMappingManager.TEXT_FUNCTIONS_TEST_TEMPLATE,
                    engineName,
                    engineVersion);

            if  (t_TemplateFactory != null)
            {
                if  (!(    t_TemplateFactory
                       instanceof TextFunctionsTestTemplateFactory))
                {
                    throw
                        new QueryJException(
                            "invalid.text.function.test.template.factory");
                }
                else 
                {
                    result =
                        (TextFunctionsTestTemplateFactory) t_TemplateFactory;
                }
            }
        }

        return result;
    }

    /**
     * Generates a text functions test template.
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
     * @throws QueryJException if the provided information is
     * invalid.
     */
    public TextFunctionsTestTemplate createTextFunctionsTestTemplate(
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
        TextFunctionsTestTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            TextFunctionsTestTemplateFactory t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);

            if  (   (t_TemplateFactory != null)
                 && (!t_TemplateFactory.getClass().equals(getClass())))
            {
                result =
                    t_TemplateFactory.createTextFunctionsTestTemplate(
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
                          "Cannot find text test functions' "
                        + "template factory for "
                        + engineName + "\n"
                        + "Disable extractfunctions setting.");
            }
        }

        return result;
    }

    /**
     * Generates a text functions test template.
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
     * @throws QueryJException if the provided information is
     * invalid.
     */
    public TextFunctionsTestTemplate createTextFunctionsTestTemplate(
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
        TextFunctionsTestTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            TextFunctionsTestTemplateFactory t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);

            if  (   (t_TemplateFactory != null)
                 && (!t_TemplateFactory.getClass().equals(getClass())))
            {
                result =
                    t_TemplateFactory.createTextFunctionsTestTemplate(
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
                          "Invalid text functions test template "
                        + "generator: " + t_TemplateFactory,
                        Project.MSG_WARN);
                }
                
                throw
                    new QueryJException(
                          "Cannot find text test functions' "
                        + "template factory for "
                        + engineName + "\n"
                        + "Disable extractfunctions setting.");
            }
        }

        return result;
    }

    /**
     * Generates a text functions test template.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @return a template.
     * @throws QueryJException if the provided information is
     * invalid.
     */
    public TextFunctionsTestTemplate createTextFunctionsTestTemplate(
        String packageName,
        String testedPackageName,
        String engineName,
        String engineVersion,
        String quote)
      throws  QueryJException
    {
        TextFunctionsTestTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            TextFunctionsTestTemplateFactory t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);

            if  (   (t_TemplateFactory != null)
                 && (!t_TemplateFactory.getClass().equals(getClass())))
            {
                result =
                    t_TemplateFactory.createTextFunctionsTestTemplate(
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
                          "Cannot find text test functions' "
                        + "template factory for "
                        + engineName + "\n"
                        + "Disable extractfunctions setting.");
            }
        }

        return result;
    }

    /**
     * Generates a text functions test template.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return a template.
     * @throws QueryJException if the provided information is
     * invalid.
     */
    public TextFunctionsTestTemplate createTextFunctionsTestTemplate(
        String  packageName,
        String  testedPackageName,
        String  engineName,
        String  engineVersion,
        String  quote,
        Project project,
        Task    task)
      throws  QueryJException
    {
        TextFunctionsTestTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            TextFunctionsTestTemplateFactory t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);

            if  (   (t_TemplateFactory != null)
                 && (!t_TemplateFactory.getClass().equals(getClass())))
            {
                result =
                    t_TemplateFactory.createTextFunctionsTestTemplate(
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
                          "Invalid text functions test template "
                        + "generator: " + t_TemplateFactory,
                        Project.MSG_WARN);
                }
                
                throw
                    new QueryJException(
                          "Cannot find text test functions' "
                        + "template factory for "
                        + engineName + "\n"
                        + "Disable extractfunctions setting.");
            }
        }

        return result;
    }

    /**
     * Writes a text functions test template to disk.
     * @param textFunctionsTestTemplate the text functions template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     */
    public void write(
            TextFunctionsTestTemplate textFunctionsTestTemplate,
            File                      outputDir)
        throws  IOException
    {
        if  (   (textFunctionsTestTemplate != null)
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
                    + "TextFunctionsTest.java",
                    textFunctionsTestTemplate.toString());
            }
        }
    }
}
