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
 * Description: Is able to generate text function repositories according to
 *              database metadata.
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
import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTemplate;

import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTemplateFactory;

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
 * Is able to generate text function repositories according to database
 * metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class TextFunctionsTemplateGenerator
    implements  TextFunctionsTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Public constructor to allow reflective instantiation.
     */
    public TextFunctionsTemplateGenerator()
    {
        TemplateMappingManager t_TemplateMappingManager =
            TemplateMappingManager.getInstance();

        if  (t_TemplateMappingManager != null)
        {
            t_TemplateMappingManager.addDefaultTemplateFactoryClass(
                TemplateMappingManager.TEXT_FUNCTIONS_TEMPLATE,
                this.getClass().getName());
        }
    }

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        TextFunctionsTemplateGenerator generator)
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
     * Retrieves a TextFunctionsTemplateGenerator instance.
     * @return such instance.
     */
    public static TextFunctionsTemplateGenerator getInstance()
    {
        TextFunctionsTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (TextFunctionsTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new TextFunctionsTemplateGenerator() {};

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
                TemplateMappingManager.TEXT_FUNCTIONS_TEMPLATE,
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
                    TemplateMappingManager.TEXT_FUNCTIONS_TEMPLATE,
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
    protected TextFunctionsTemplateFactory getTemplateFactory(
            String engineName, String engineVersion)
        throws  QueryJException
    {
        TextFunctionsTemplateFactory result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            Object t_TemplateFactory =
                t_MappingManager.getTemplateFactory(
                    TemplateMappingManager.TEXT_FUNCTIONS_TEMPLATE,
                    engineName,
                    engineVersion);

            if  (t_TemplateFactory != null)
            {
                if  (!(           t_TemplateFactory
                       instanceof TextFunctionsTemplateFactory))
                {
                    throw
                        new QueryJException(
                            "invalid.text.function.template.factory");
                }
                else 
                {
                    result = (TextFunctionsTemplateFactory) t_TemplateFactory;
                }
            }
        }

        return result;
    }

    /**
     * Generates a text functions template.
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
     * @return a template.
     * @throws QueryJException if the input information is invalid.
     */
    public TextFunctionsTemplate createTextFunctionsTemplate(
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
      throws  QueryJException
    {
        TextFunctionsTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            TextFunctionsTemplateFactory t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);

            if  (   (t_TemplateFactory != null)
                 && (!t_TemplateFactory.getClass().equals(getClass())))
            {
                result =
                    t_TemplateFactory.createTextFunctionsTemplate(
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
            else 
            {
                throw
                    new QueryJException(
                          "Cannot find text functions' "
                        + "template factory for "
                        + engineName + "\n"
                        + "Disable extractfunctions setting.");
            }
        }

        return result;
    }

    /**
     * Generates a text functions template.
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
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return a template.
     * @throws QueryJException if the input information is invalid.
     */
    public TextFunctionsTemplate createTextFunctionsTemplate(
        String  header,
        String  packageDeclaration,
        String  packageName,
        String  engineName,
        String  engineVersion,
        String  quote,
        String  acmslImports,
        String  jdkImports,
        String  javadoc,
        String  classDefinition,
        String  classStart,
        String  singletonBody,
        String  classConstructor,
        String  innerClass,
        String  classEnd,
        Project project,
        Task    task)
      throws  QueryJException
    {
        TextFunctionsTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            TextFunctionsTemplateFactory t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);

            if  (   (t_TemplateFactory != null)
                 && (!t_TemplateFactory.getClass().equals(getClass())))
            {
                result =
                    t_TemplateFactory.createTextFunctionsTemplate(
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
                        "Invalid text functions generator class: "
                        + t_TemplateFactory,
                        Project.MSG_WARN);
                }
                
                throw
                    new QueryJException(
                          "Cannot find text functions' "
                        + "template factory for "
                        + engineName + "\n"
                        + "Disable extractfunctions setting.");
            }
        }

        return result;
    }

    /**
     * Generates a text functions template.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @return a template.
     * @throws QueryJException if the input information is invalid.
     */
    public TextFunctionsTemplate createTextFunctionsTemplate(
        String packageName,
        String engineName,
        String engineVersion,
        String quote)
      throws  QueryJException
    {
        TextFunctionsTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            TextFunctionsTemplateFactory t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);

            if  (   (t_TemplateFactory != null)
                 && (!t_TemplateFactory.getClass().equals(getClass())))
            {
                result =
                    t_TemplateFactory.createTextFunctionsTemplate(
                        packageName,
                        engineName,
                        engineVersion,
                        quote);
            }
            else 
            {
                throw
                    new QueryJException(
                          "Cannot find text functions' "
                        + "template factory for "
                        + engineName + "\n"
                        + "Disable extractfunctions setting.");
            }
        }

        return result;
    }

    /**
     * Generates a text functions template.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return a template.
     * @throws QueryJException if the input information is invalid.
     */
    public TextFunctionsTemplate createTextFunctionsTemplate(
        String  packageName,
        String  engineName,
        String  engineVersion,
        String  quote,
        Project project,
        Task    task)
      throws  QueryJException
    {
        TextFunctionsTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            TextFunctionsTemplateFactory t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);

            if  (   (t_TemplateFactory != null)
                 && (!t_TemplateFactory.getClass().equals(getClass())))
            {
                result =
                    t_TemplateFactory.createTextFunctionsTemplate(
                        packageName,
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
                        "Invalid text functions generator class: "
                        + t_TemplateFactory,
                        Project.MSG_WARN);
                }
                
                throw
                    new QueryJException(
                          "Cannot find text functions' "
                        + "template factory for "
                        + engineName + "\n"
                        + "Disable extractfunctions setting.");
            }
        }

        return result;
    }

    /**
     * Writes a text functions template to disk.
     * @param textFunctionsTemplate the text functions template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     */
    public void write(
            TextFunctionsTemplate textFunctionsTemplate,
            File                  outputDir)
        throws  IOException
    {
        if  (   (textFunctionsTemplate != null)
             && (outputDir             != null))
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
                    + "TextFunctions.java",
                    textFunctionsTemplate.toString());
            }
        }
    }
}
