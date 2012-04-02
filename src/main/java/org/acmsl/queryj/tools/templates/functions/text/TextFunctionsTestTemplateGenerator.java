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
 * Filename: TextFunctionsTestTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate the JUnit classes to test the Database's
 *              text functions.
 *
 */
package org.acmsl.queryj.tools.templates.functions.text;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTestTemplate;
import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTestTemplateFactory;
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
 * Is able to generate the JUnit classes to test the Database's text functions.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TextFunctionsTestTemplateGenerator
    implements  TextFunctionsTestTemplateFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class TextFunctionsTestTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final TextFunctionsTestTemplateGenerator SINGLETON =
            new TextFunctionsTestTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected TextFunctionsTestTemplateGenerator() {};

    /**
     * Retrieves a {@link TextFunctionsTestTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static TextFunctionsTestTemplateGenerator getInstance()
    {
        return TextFunctionsTestTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Adds a new template factory class.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     */
    public void addTemplateFactoryClass(
        @Nullable final String engineName,
        final String engineVersion,
        @Nullable final String templateFactoryClass)
    {
        @NotNull TemplateMappingManager t_MappingManager =
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
    @Nullable
    protected String getTemplateFactoryClass(
        @Nullable final String engineName, final String engineVersion)
    {
        @Nullable String result = null;

        @NotNull TemplateMappingManager t_MappingManager =
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
     * @throws QueryJBuildException if the factory class is invalid.
     */
    @Nullable
    protected TextFunctionsTestTemplateFactory getTemplateFactory(
        final String engineName, final String engineVersion)
      throws  QueryJBuildException
    {
        @Nullable TextFunctionsTestTemplateFactory result = null;

        @NotNull TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            @Nullable Object t_TemplateFactory =
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
                        new QueryJBuildException(
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
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @return a template.
     */
    @Nullable
    public TextFunctionsTestTemplate createTextFunctionsTestTemplate(
        @Nullable final String packageName,
        final String testedPackageName,
        @Nullable final String engineName,
        @Nullable final String engineVersion,
        @Nullable final String quote)
    {
        @Nullable TextFunctionsTestTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            @Nullable TextFunctionsTestTemplateFactory t_TemplateFactory = null;

            try
            {
                t_TemplateFactory =
                    getTemplateFactory(engineName, engineVersion);
            }
            catch  (@NotNull final QueryJBuildException buildException)
            {
                Log t_Log =
                    UniqueLogFactory.getLog(
                        TextFunctionsTestTemplateGenerator.class);

                if  (t_Log != null)
                {
                    t_Log.warn(
                        "Cannot retrieve text-functions test template",
                        buildException);
                }
            }

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
        }

        return result;
    }

    /**
     * Writes a text functions test template to disk.
     * @param textFunctionsTestTemplate the text functions template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @throws IOException if the file cannot be created.
     */
    public void write(
        @NotNull final TextFunctionsTestTemplate textFunctionsTestTemplate,
        @NotNull final File outputDir,
        final Charset charset)
      throws  IOException
    {
        write(
            textFunctionsTestTemplate,
            outputDir,
            charset,
            StringUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a text functions test template to disk.
     * @param textFunctionsTestTemplate the text functions template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @param stringUtils the {@link StringUtils} instance.
     * @param fileUtils the {@link FileUtils} instance.
     * @throws IOException if the file cannot be created.
     * @precondition textFunctionsTestTemplate != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        @NotNull final TextFunctionsTestTemplate textFunctionsTestTemplate,
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
                + "TextFunctionsTest.java",
                textFunctionsTestTemplate.generate(),
                charset);
        }
    }
}
