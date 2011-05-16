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
 * Filename: NumericFunctionsTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate numeric function repositories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.numeric;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplate;
import org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplateFactory;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Is able to generate numeric function repositories according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class NumericFunctionsTemplateGenerator
    implements  NumericFunctionsTemplateFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class NumericFunctionsTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final NumericFunctionsTemplateGenerator SINGLETON =
            new NumericFunctionsTemplateGenerator();
    }

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
     * Retrieves a {@link NumericFunctionsTemplateGenerator} instance.
     * @return such instance.
     */
    public static NumericFunctionsTemplateGenerator getInstance()
    {
        return NumericFunctionsTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the template factory instance.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @throws QueryJBuildException if the factory class is invalid.
     */
    protected NumericFunctionsTemplateFactory getTemplateFactory(
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
    protected NumericFunctionsTemplateFactory getTemplateFactory(
        final String engineName,
        final String engineVersion,
        final TemplateMappingManager templateMappingManager)
      throws  QueryJBuildException
    {
        NumericFunctionsTemplateFactory result = null;

        Object t_TemplateFactory =
            templateMappingManager.getTemplateFactory(
                TemplateMappingManager.NUMERIC_FUNCTIONS_TEMPLATE,
                engineName,
                engineVersion);

        if  (t_TemplateFactory != null)
        {
            if  (!(t_TemplateFactory instanceof NumericFunctionsTemplateFactory))
            {
                throw
                    new QueryJBuildException(
                        "invalid.numeric.function.template.factory");
            }
            else 
            {
                result = (NumericFunctionsTemplateFactory) t_TemplateFactory;
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
     * @param header the header.
     * @return a template.
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition quote != null
     */
    public NumericFunctionsTemplate createNumericFunctionsTemplate(
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String header)
    {
        NumericFunctionsTemplate result = null;

        try
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
                        header);
            }
        }
        catch  (final QueryJBuildException buildException)
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

        return result;
    }

    /**
     * Writes a numeric functions template to disk.
     * @param numericFunctionsTemplate the numeric functions template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @throws IOException if the file cannot be created.
     * @precondition numericFunctionsTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final NumericFunctionsTemplate numericFunctionsTemplate,
        final File outputDir,
        final Charset charset)
      throws  IOException
    {
        write(
            numericFunctionsTemplate,
            outputDir,
            charset,
            StringUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a numeric functions template to disk.
     * @param numericFunctionsTemplate the numeric functions template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @param stringUtils the {@link StringUtils} instance.
     * @param fileUtils the {@link FileUtils} instance.
     * @throws IOException if the file cannot be created.
     * @precondition numericFunctionsTemplate != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final NumericFunctionsTemplate numericFunctionsTemplate,
        final File outputDir,
        final Charset charset,
        final StringUtils stringUtils,
        final FileUtils fileUtils)
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
                + "NumericFunctions.java",
                numericFunctionsTemplate.generate(),
                charset);
        }
    }
}
