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
 * Filename: TextFunctionsTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate text function repositories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.text;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTemplate;

import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTemplateFactory;

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
 * Is able to generate text function repositories according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TextFunctionsTemplateGenerator
    implements  TextFunctionsTemplateFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class TextFunctionsTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final TextFunctionsTemplateGenerator SINGLETON =
            new TextFunctionsTemplateGenerator();
    }

    /**
     * Public constructor to allow reflective instantiation.
     */
    public TextFunctionsTemplateGenerator()
    {
        @NotNull TemplateMappingManager t_TemplateMappingManager =
            TemplateMappingManager.getInstance();

        if  (t_TemplateMappingManager != null)
        {
            t_TemplateMappingManager.addDefaultTemplateFactoryClass(
                TemplateMappingManager.TEXT_FUNCTIONS_TEMPLATE,
                this.getClass().getName());
        }
    }

    /**
     * Retrieves a TextFunctionsTemplateGenerator instance.
     * @return such instance.
     */
    @NotNull
    public static TextFunctionsTemplateGenerator getInstance()
    {
        return TextFunctionsTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Adds a new template factory class.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     * @precondition engineName != null
     * @precondition templateFactoryClass != null
     */
    public void addTemplateFactoryClass(
        final String engineName,
        final String engineVersion,
        final String templateFactoryClass)
    {
        addTemplateFactoryClass(
            engineName,
            engineVersion,
            templateFactoryClass,
            TemplateMappingManager.getInstance());
    }

    /**
     * Adds a new template factory class.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     * @param templateMappingManager the {@link TemplateMappingManager}
     * instance.
     * @precondition engineName != null
     * @precondition templateFactoryClass != null
     * @precondition templateMappingManager != null
     */
    protected void addTemplateFactoryClass(
        final String engineName,
        final String engineVersion,
        final String templateFactoryClass,
        @NotNull final TemplateMappingManager templateMappingManager)
    {
        templateMappingManager.addTemplateFactoryClass(
            TemplateMappingManager.TEXT_FUNCTIONS_TEMPLATE,
            engineName,
            engineVersion,
            templateFactoryClass);
    }

    /**
     * Retrieves the template factory class.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @precondition engineName != null
     */
    @Nullable
    protected String getTemplateFactoryClass(
        final String engineName, final String engineVersion)
    {
        return
            getTemplateFactoryClass(
                engineName,
                engineVersion,
                TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory class.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateMappingManager the {@link TemplateMappingManager}
     * instance.
     * @return the template factory class name.
     * @precondition engineName != null
     * @precondition templateMappingManager != null
     */
    @Nullable
    protected String getTemplateFactoryClass(
        final String engineName,
        final String engineVersion,
        @NotNull final TemplateMappingManager templateMappingManager)
    {
        return
            templateMappingManager.getTemplateFactoryClass(
                TemplateMappingManager.TEXT_FUNCTIONS_TEMPLATE,
                engineName,
                engineVersion);
    }

    /**
     * Retrieves the template factory instance.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @throws QueryJBuildException if the factory class is invalid.
     */
    @Nullable
    protected TextFunctionsTemplateFactory getTemplateFactory(
        final String engineName, final String engineVersion)
      throws  QueryJBuildException
    {
        return
            getTemplateFactory(
                engineName,
                engineVersion,
                TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory instance.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateMappingManager the {@link TemplateMappingManager}
     * instance.
     * @return the template factory class name.
     * @throws QueryJBuildException if the factory class is invalid.
     * @precondition templateMappingManager != null
     */
    @Nullable
    protected TextFunctionsTemplateFactory getTemplateFactory(
        final String engineName,
        final String engineVersion,
        @NotNull final TemplateMappingManager templateMappingManager)
      throws  QueryJBuildException
    {
        @Nullable TextFunctionsTemplateFactory result = null;

        @Nullable Object t_TemplateFactory =
            templateMappingManager.getTemplateFactory(
                TemplateMappingManager.TEXT_FUNCTIONS_TEMPLATE,
                engineName,
                engineVersion);

        if  (t_TemplateFactory != null)
        {
            if  (!(t_TemplateFactory instanceof TextFunctionsTemplateFactory))
            {
                throw
                    new QueryJBuildException(
                        "invalid.text.function.template.factory");
            }
            else 
            {
                result = (TextFunctionsTemplateFactory) t_TemplateFactory;
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
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition quote != null
     */
    @Nullable
    public TextFunctionsTemplate createTextFunctionsTemplate(
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote)
    {
        @Nullable TextFunctionsTemplate result = null;

        @Nullable TextFunctionsTemplateFactory t_TemplateFactory = null;

        try
        {
            t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);
        }
        catch  (@NotNull final QueryJBuildException buildException)
        {
            Log t_Log =
                UniqueLogFactory.getLog(
                    TextFunctionsTemplateGenerator.class);

            if  (t_Log != null)
            {
                t_Log.warn(
                    "Cannot retrieve text-functions template",
                    buildException);
            }
        }

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

        return result;
    }

    /**
     * Writes a text functions template to disk.
     * @param textFunctionsTemplate the text functions template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @throws IOException if the file cannot be created.
     * @precondition textFunctionsTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        @NotNull final TextFunctionsTemplate textFunctionsTemplate,
        @NotNull final File outputDir,
        final Charset charset)
      throws  IOException
    {
        write(
            textFunctionsTemplate,
            outputDir,
            charset,
            StringUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a text functions template to disk.
     * @param textFunctionsTemplate the text functions template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @param stringUtils the {@link StringUtils} instance.
     * @param fileUtils the {@link FileUtils} instance.
     * @throws IOException if the file cannot be created.
     * @precondition textFunctionsTemplate != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        @NotNull final TextFunctionsTemplate textFunctionsTemplate,
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
                + "TextFunctions.java",
                textFunctionsTemplate.generate(),
                charset);
        }
    }
}
