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
import org.acmsl.queryj.tools.templates.AbstractTemplateGenerator;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Is able to generate the JUnit classes to test the Database's text functions.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TextFunctionsTestTemplateGenerator<T extends TextFunctionsTestTemplate>
    extends AbstractTemplateGenerator<T>
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
    protected TextFunctionsTestTemplateGenerator() {}

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

        if  (   (engineName           != null)
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

        return result;
    }

    /**
     * Generates a text functions test template.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param header the file header.
     * @return a template.
     */
    @Nullable
    public TextFunctionsTestTemplate createTextFunctionsTestTemplate(
        @Nullable final String packageName,
        final String testedPackageName,
        @Nullable final String engineName,
        @Nullable final String engineVersion,
        @Nullable final String quote,
        final String header)
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
                        quote,
                        header);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String retrieveTemplateFileName(@NotNull final T template)
    {
        return "TextFunctionsTest.java";
    }
}
