//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
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
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: NumericFunctionsTestTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate the JUnit classes to test the Database's
 *              numeric functions.
 *
 */
package org.acmsl.queryj.tools.templates.functions.numeric;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.templates.functions.numeric
    .NumericFunctionsTestTemplate;

import org.acmsl.queryj.tools.templates.functions.numeric
    .NumericFunctionsTestTemplateFactory;

import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Is able to generate the JUnit classes to test the Database's numeric functions.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class NumericFunctionsTestTemplateGenerator
    implements  NumericFunctionsTestTemplateFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class NumericFunctionsTestTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final NumericFunctionsTestTemplateGenerator SINGLETON =
            new NumericFunctionsTestTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected NumericFunctionsTestTemplateGenerator() {};

    /**
     * Retrieves a {@link NumericFunctionsTestTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static NumericFunctionsTestTemplateGenerator getInstance()
    {
        return NumericFunctionsTestTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Adds a new template factory class.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     */
    public void addTemplateFactoryClass(
        @Nullable String engineName,
        String engineVersion,
        @Nullable String templateFactoryClass)
    {
        @NotNull TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (   (t_MappingManager     != null)
             && (engineName           != null)
             && (templateFactoryClass != null))
        {
            t_MappingManager.addTemplateFactoryClass(
                TemplateMappingManager.NUMERIC_FUNCTIONS_TEST_TEMPLATE,
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
    @Nullable
    protected String getTemplateFactoryClass(
        @Nullable String engineName, String engineVersion)
    {
        @Nullable String result = null;

        @NotNull TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (   (t_MappingManager != null)
             && (engineName       != null))
        {
            result =
                t_MappingManager.getTemplateFactoryClass(
                    TemplateMappingManager.NUMERIC_FUNCTIONS_TEST_TEMPLATE,
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
     * @throws QueryJBuildException if the factory class is invalid.
     */
    @Nullable
    protected NumericFunctionsTestTemplateFactory getTemplateFactory(
            String engineName, String engineVersion)
        throws  QueryJBuildException
    {
        @Nullable NumericFunctionsTestTemplateFactory result = null;

        @NotNull TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            @Nullable Object t_TemplateFactory =
                t_MappingManager.getTemplateFactory(
                    TemplateMappingManager.NUMERIC_FUNCTIONS_TEST_TEMPLATE,
                    engineName,
                    engineVersion);

            if  (t_TemplateFactory != null)
            {
                if  (!(t_TemplateFactory instanceof NumericFunctionsTestTemplateFactory))
                {
                    throw
                        new QueryJBuildException(
                            "invalid.numeric.function.test.template.factory");
                }
                else 
                {
                    result =
                        (NumericFunctionsTestTemplateFactory) t_TemplateFactory;
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
     */
    @Nullable
    public NumericFunctionsTestTemplate createNumericFunctionsTestTemplate(
        final String header,
        final String packageDeclaration,
        @Nullable final String packageName,
        final String testedPackageName,
        @Nullable final String engineName,
        @Nullable final String engineVersion,
        @Nullable final String quote,
        final String projectImports,
        final String acmslImports,
        final String jdkImports,
        final String junitImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String classConstructor,
        final String memberAccessors,
        final String setUpTearDownMethods,
        final String mainMethod,
        final String getInstanceTest,
        final String innerClass,
        final String innerTable,
        final String classEnd)
    {
        @Nullable NumericFunctionsTestTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            @Nullable NumericFunctionsTestTemplateFactory t_TemplateFactory = null;

            try
            {
                t_TemplateFactory =
                    getTemplateFactory(engineName, engineVersion);
            }
            catch  (@NotNull final QueryJBuildException buildException)
            {
                Log t_Log =
                    UniqueLogFactory.getLog(
                        NumericFunctionsTemplateGenerator.class);

                if  (t_Log != null)
                {
                    t_Log.warn(
                        "Cannot retrieve numeric-functions template",
                        buildException);
                }
            }

            if  (   (t_TemplateFactory != null)
                 && (!t_TemplateFactory.getClass().equals(getClass())))
            {
                result =
                    t_TemplateFactory.createNumericFunctionsTestTemplate(
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
        }

        return result;
    }

    /**
     * Generates a numeric functions template.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param header the header.
     * @return a template.
     */
    @Nullable
    public NumericFunctionsTestTemplate createNumericFunctionsTestTemplate(
        @Nullable final String packageName,
        final String testedPackageName,
        @Nullable final String engineName,
        @Nullable final String engineVersion,
        @Nullable final String quote,
        final String header)
    {
        @Nullable NumericFunctionsTestTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            @Nullable NumericFunctionsTestTemplateFactory t_TemplateFactory = null;

            try
            {
                t_TemplateFactory =
                    getTemplateFactory(engineName, engineVersion);
            }
            catch  (@NotNull final QueryJBuildException buildException)
            {
                Log t_Log =
                    UniqueLogFactory.getLog(
                        NumericFunctionsTemplateGenerator.class);

                if  (t_Log != null)
                {
                    t_Log.warn(
                        "Cannot retrieve numeric-functions template",
                        buildException);
                }
            }

            if  (   (t_TemplateFactory != null)
                 && (!t_TemplateFactory.getClass().equals(getClass())))
            {
                result =
                    t_TemplateFactory.createNumericFunctionsTestTemplate(
                        packageName,
                        testedPackageName,
                        engineName,
                        engineVersion,
                        quote,
                        header);
            }
        }

        return result;
    }

    /**
     * Writes a numeric functions test template to disk.
     * @param numericFunctionsTestTemplate the numeric functions template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @throws IOException if the file cannot be created.
     */
    public void write(
        @NotNull final NumericFunctionsTestTemplate numericFunctionsTestTemplate,
        @NotNull final File outputDir,
        final Charset charset)
      throws  IOException
    {
        write(
            numericFunctionsTestTemplate,
            outputDir,
            charset,
            StringUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a numeric functions test template to disk.
     * @param numericFunctionsTestTemplate the numeric functions template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @param stringUtils the {@link StringUtils} instance.
     * @param fileUtils the {@link FileUtils} instance.
     * @throws IOException if the file cannot be created.
     * @precondition numericFunctionsTestTemplate != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        @NotNull final NumericFunctionsTestTemplate numericFunctionsTestTemplate,
        @NotNull final File outputDir,
        final Charset charset,
        final StringUtils stringUtils,
        @NotNull final FileUtils fileUtils)
      throws  IOException
    {
        boolean folderCreated = outputDir.mkdirs();

        if (   (!folderCreated)
            && (!outputDir.exists()))
        {
            throw
                new IOException("Cannot create output dir: " + outputDir);
        }
        else
        {
            fileUtils.writeFile(
                  outputDir.getAbsolutePath()
                + File.separator
                + "NumericFunctionsTest.java",
                numericFunctionsTestTemplate.generate(),
                charset);
        }
    }
}
