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
 * Filename: TimeFunctionsTestTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate the JUnit classes to test the Database's
 *              time functions.
 *
 */
package org.acmsl.queryj.tools.templates.functions.time;

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
 * Is able to generate the JUnit classes to test the Database's time functions.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TimeFunctionsTestTemplateGenerator<T extends TimeFunctionsTestTemplate>
    extends AbstractTemplateGenerator<T>
    implements  TimeFunctionsTestTemplateFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class TimeFunctionsTestTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final TimeFunctionsTestTemplateGenerator SINGLETON =
            new TimeFunctionsTestTemplateGenerator();
    }

    /**
     * Public constructor to allow reflective instantiation.
     */
    public TimeFunctionsTestTemplateGenerator() {}

    /**
     * Retrieves a {@link TimeFunctionsTestTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static TimeFunctionsTestTemplateGenerator getInstance()
    {
        return TimeFunctionsTestTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the template factory instance.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @throws QueryJBuildException if the factory class is invalid.
     * @precondition engineName != null
     */
    @Nullable
    protected TimeFunctionsTestTemplateFactory getTemplateFactory(
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
     * @precondition engineName != null
     * @precondition templateMappingManager != null
     */
    @Nullable
    protected TimeFunctionsTestTemplateFactory getTemplateFactory(
        final String engineName,
        final String engineVersion,
        @NotNull final TemplateMappingManager templateMappingManager)
      throws  QueryJBuildException
    {
        @Nullable TimeFunctionsTestTemplateFactory result = null;

        @Nullable Object t_TemplateFactory =
            templateMappingManager.getTemplateFactory(
                TemplateMappingManager.TIME_FUNCTIONS_TEST_TEMPLATE,
                engineName,
                engineVersion);

        if  (t_TemplateFactory != null)
        {
            if  (!(t_TemplateFactory instanceof TimeFunctionsTestTemplateFactory))
            {
                throw
                    new QueryJBuildException(
                        "invalid.time.function.test.template.factory");
            }
            else 
            {
                result = (TimeFunctionsTestTemplateFactory) t_TemplateFactory;
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
     * @param header the file header.
     * @return a template.
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition quote != null
     */
    @Nullable
    public TimeFunctionsTestTemplate createTimeFunctionsTestTemplate(
        final String packageName,
        final String testedPackageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String header)
    {
        @Nullable TimeFunctionsTestTemplate result = null;

        @Nullable TimeFunctionsTestTemplateFactory t_TemplateFactory = null;

        try
        {
            t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);
        }
        catch  (@NotNull final QueryJBuildException buildException)
        {
            Log t_Log =
                UniqueLogFactory.getLog(
                    TimeFunctionsTestTemplateGenerator.class);

            if  (t_Log != null)
            {
                t_Log.warn(
                    "Cannot retrieve time-functions test template",
                    buildException);
            }
        }

        if  (t_TemplateFactory != null)
        {
            result =
                t_TemplateFactory.createTimeFunctionsTestTemplate(
                    packageName,
                    testedPackageName,
                    engineName,
                    engineVersion,
                    quote,
                    header);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String retrieveTemplateFileName(@NotNull final T template)
    {
        return "TimeFunctionsTest.java";
    }
}
