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
 * Filename: SystemFunctionsTestTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate the JUnit classes to test the Database's
 *              system functions.
 *
 */
package org.acmsl.queryj.tools.templates.functions.system;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTestTemplate;

import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTestTemplateFactory;

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

/**
 * Is able to generate the JUnit classes to test the Database's system functions.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class SystemFunctionsTestTemplateGenerator
    implements  SystemFunctionsTestTemplateFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class SystemFunctionsTestTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final SystemFunctionsTestTemplateGenerator SINGLETON =
            new SystemFunctionsTestTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected SystemFunctionsTestTemplateGenerator() {};

    /**
     * Retrieves a <code>SystemFunctionsTestTemplateGenerator</code> instance.
     * @return such instance.
     */
    public static SystemFunctionsTestTemplateGenerator getInstance()
    {
        return SystemFunctionsTestTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Adds a new template factory class.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     */
    public void addTemplateFactoryClass(
        final String engineName,
        final String engineVersion,
        final String templateFactoryClass)
    {
        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (   (t_MappingManager     != null)
             && (engineName           != null)
             && (templateFactoryClass != null))
        {
            t_MappingManager.addTemplateFactoryClass(
                TemplateMappingManager.SYSTEM_FUNCTIONS_TEST_TEMPLATE,
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
        final String engineName, final String engineVersion)
    {
        String result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (   (t_MappingManager != null)
             && (engineName       != null))
        {
            result =
                t_MappingManager.getTemplateFactoryClass(
                    TemplateMappingManager.SYSTEM_FUNCTIONS_TEST_TEMPLATE,
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
    protected SystemFunctionsTestTemplateFactory getTemplateFactory(
        final String engineName, final String engineVersion)
      throws  QueryJBuildException
    {
        SystemFunctionsTestTemplateFactory result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            Object t_TemplateFactory =
                t_MappingManager.getTemplateFactory(
                    TemplateMappingManager.SYSTEM_FUNCTIONS_TEST_TEMPLATE,
                    engineName,
                    engineVersion);

            if  (t_TemplateFactory != null)
            {
                if  (!(t_TemplateFactory instanceof SystemFunctionsTestTemplateFactory))
                {
                    throw
                        new QueryJBuildException(
                            "invalid.system.function.test.template.factory");
                }
                else 
                {
                    result = (SystemFunctionsTestTemplateFactory) t_TemplateFactory;
                }
            }
        }

        return result;
    }

    /**
     * Generates a system functions test template.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @return a template.
     */
    public SystemFunctionsTestTemplate createSystemFunctionsTestTemplate(
        final String packageName,
        final String testedPackageName,
        final String engineName,
        final String engineVersion,
        final String quote)
    {
        SystemFunctionsTestTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            SystemFunctionsTestTemplateFactory t_TemplateFactory = null;

            try
            {
                t_TemplateFactory =
                    getTemplateFactory(engineName, engineVersion);
            }
            catch  (final QueryJBuildException buildException)
            {
                Log t_Log =
                    UniqueLogFactory.getLog(
                        SystemFunctionsTemplateGenerator.class);

                if  (t_Log != null)
                {
                    t_Log.warn(
                        "Cannot retrieve system-functions test template",
                        buildException);
                }
            }

            if  (   (t_TemplateFactory != null)
                 && (!t_TemplateFactory.getClass().equals(getClass())))
            {
                result =
                    t_TemplateFactory.createSystemFunctionsTestTemplate(
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
     * Writes a system functions test template to disk.
     * @param systemFunctionsTestTemplate the system functions template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @throws IOException if the file cannot be created.
     */
    public void write(
        final SystemFunctionsTestTemplate systemFunctionsTestTemplate,
        final File outputDir,
        final Charset charset)
      throws  IOException
    {
        write(
            systemFunctionsTestTemplate,
            outputDir,
            charset,
            StringUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a system functions test template to disk.
     * @param systemFunctionsTestTemplate the system functions template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @param stringUtils the {@link StringUtils} instance.
     * @param fileUtils the {@link FileUtils} instance.
     * @throws IOException if the file cannot be created.
     * @precondition stringUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final SystemFunctionsTestTemplate systemFunctionsTestTemplate,
        final File outputDir,
        final Charset charset,
        final StringUtils stringUtils,
        final FileUtils fileUtils)
      throws  IOException
    {
        if (outputDir.mkdirs())
        {
            fileUtils.writeFile(
                  outputDir.getAbsolutePath()
                + File.separator
                + "SystemFunctionsTest.java",
                systemFunctionsTestTemplate.generate(),
                charset);
        }
        else
        {
            throw
                new IOException(
                    "Cannot create output dir: " + outputDir);
        }
    }
}
