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
 * Description: Is able to generate numeric function repositories according to
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
package org.acmsl.queryj.tools.templates.functions.numeric;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplate;
import org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplateFactory;
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
 * Is able to generate numeric function repositories according to database
 * metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class NumericFunctionsTemplateGenerator
    implements  NumericFunctionsTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Public constructor to allow reflective instantiation.
     */
    public NumericFunctionsTemplateGenerator()
    {
        TemplateMappingManager t_TemplateMappingManager =
            TemplateMappingManager.getInstance();

        if  (t_TemplateMappingManager != null)
        {
            t_TemplateMappingManager.addDefaultTemplateFactoryClass(
                TemplateMappingManager.NUMERIC_FUNCTIONS_TEMPLATE,
                this.getClass().getName());
        }
    }

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(NumericFunctionsTemplateGenerator generator)
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
     * Retrieves a NumericFunctionsTemplateGenerator instance.
     * @return such instance.
     */
    public static NumericFunctionsTemplateGenerator getInstance()
    {
        NumericFunctionsTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (NumericFunctionsTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new NumericFunctionsTemplateGenerator() {};

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
                TemplateMappingManager.NUMERIC_FUNCTIONS_TEMPLATE,
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
                    TemplateMappingManager.NUMERIC_FUNCTIONS_TEMPLATE,
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
    protected NumericFunctionsTemplateFactory getTemplateFactory(
        final String engineName, final String engineVersion)
      throws  QueryJException
    {
        NumericFunctionsTemplateFactory result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            Object t_TemplateFactory =
                t_MappingManager.getTemplateFactory(
                    TemplateMappingManager.NUMERIC_FUNCTIONS_TEMPLATE,
                    engineName,
                    engineVersion);

            if  (t_TemplateFactory != null)
            {
                if  (!(t_TemplateFactory instanceof NumericFunctionsTemplateFactory))
                {
                    throw
                        new QueryJException(
                            "invalid.numeric.function.template.factory");
                }
                else 
                {
                    result = (NumericFunctionsTemplateFactory) t_TemplateFactory;
                }
            }
        }

        return result;
    }

    /**
     * Generates a numeric functions template.
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
     * @throws QueryJException if the factory class is invalid.
     */
    public NumericFunctionsTemplate createNumericFunctionsTemplate(
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String acmslImports,
        final String jdkImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String singletonBody,
        final String classConstructor,
        final String innerClass,
        final String classEnd)
      throws  QueryJException
    {
        NumericFunctionsTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            NumericFunctionsTemplateFactory t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);

            if  (   (t_TemplateFactory != null)
                 && (!t_TemplateFactory.getClass().equals(getClass())))
            {
                result =
                    t_TemplateFactory.createNumericFunctionsTemplate(
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
                          "Cannot find numeric functions' "
                        + "template factory for "
                        + engineName + "\n"
                        + "Disable extractfunctions setting.");
            }
        }

        return result;
    }

    /**
     * Generates a numeric functions template.
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
     * @throws QueryJException if the factory class is invalid.
     */
    public NumericFunctionsTemplate createNumericFunctionsTemplate(
        final String  header,
        final String  packageDeclaration,
        final String  packageName,
        final String  engineName,
        final String  engineVersion,
        final String  quote,
        final String  acmslImports,
        final String  jdkImports,
        final String  javadoc,
        final String  classDefinition,
        final String  classStart,
        final String  singletonBody,
        final String  classConstructor,
        final String  innerClass,
        final String  classEnd,
        final Project project,
        final Task    task)
      throws  QueryJException
    {
        NumericFunctionsTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            NumericFunctionsTemplateFactory t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);

            if  (   (t_TemplateFactory != null)
                 && (!t_TemplateFactory.getClass().equals(getClass())))
            {
                result =
                    t_TemplateFactory.createNumericFunctionsTemplate(
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
                          "Invalid numeric functions generator class: "
                        + t_TemplateFactory,
                        Project.MSG_WARN);
                }
                
                throw
                    new QueryJException(
                          "Cannot find numeric functions' "
                        + "template factory for "
                        + engineName + "\n"
                        + "Disable extractfunctions setting.");
            }
        }

        return result;
    }

    /**
     * Generates a numeric functions template.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     */
    public NumericFunctionsTemplate createNumericFunctionsTemplate(
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote)
      throws  QueryJException
    {
        NumericFunctionsTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            NumericFunctionsTemplateFactory t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);

            if  (   (t_TemplateFactory != null)
                 && (!t_TemplateFactory.getClass().equals(getClass())))
            {
                result =
                    t_TemplateFactory.createNumericFunctionsTemplate(
                        packageName,
                        engineName,
                        engineVersion,
                        quote);
            }
            else 
            {
                throw
                    new QueryJException(
                          "Cannot find numeric functions' "
                        + "template factory for "
                        + engineName + "\n"
                        + "Disable extractfunctions setting.");
            }
        }

        return result;
    }

    /**
     * Generates a numeric functions template.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     */
    public NumericFunctionsTemplate createNumericFunctionsTemplate(
        final String  packageName,
        final String  engineName,
        final String  engineVersion,
        final String  quote,
        final Project project,
        final Task    task)
      throws  QueryJException
    {
        NumericFunctionsTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            NumericFunctionsTemplateFactory t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);

            if  (   (t_TemplateFactory != null)
                 && (!t_TemplateFactory.getClass().equals(getClass())))
            {
                result =
                    t_TemplateFactory.createNumericFunctionsTemplate(
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
                          "Invalid numeric functions generator class: "
                        + t_TemplateFactory,
                        Project.MSG_WARN);
                }
                
                throw
                    new QueryJException(
                          "Cannot find numeric functions' "
                        + "template factory for "
                        + engineName + "\n"
                        + "Disable extractfunctions setting.");
            }
        }

        return result;
    }

    /**
     * Writes a numeric functions template to disk.
     * @param numericFunctionsTemplate the numeric functions template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     */
    public void write(
        final NumericFunctionsTemplate numericFunctionsTemplate,
        final File outputDir)
        throws  IOException
    {
        if  (   (numericFunctionsTemplate != null)
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
                    + "NumericFunctions.java",
                    numericFunctionsTemplate.toString());
            }
        }
    }
}
