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
import org.acmsl.queryj.tools.templates.AbstractTemplateGenerator;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL Commons classes.
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
 * Is able to generate numeric function repositories according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class NumericFunctionsTemplateGenerator<T extends NumericFunctionsTemplate>
    extends AbstractTemplateGenerator<T>
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
        @NotNull TemplateMappingManager t_TemplateMappingManager =
            TemplateMappingManager.getInstance();

        t_TemplateMappingManager.addDefaultTemplateFactoryClass(
            TemplateMappingManager.NUMERIC_FUNCTIONS_TEMPLATE,
            this.getClass().getName());
    }

    /**
     * Retrieves a {@link NumericFunctionsTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
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
    @Nullable
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
    @Nullable
    protected NumericFunctionsTemplateFactory getTemplateFactory(
        final String engineName,
        final String engineVersion,
        @NotNull final TemplateMappingManager templateMappingManager)
      throws  QueryJBuildException
    {
        @Nullable NumericFunctionsTemplateFactory result = null;

        @Nullable Object t_TemplateFactory =
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
    @Nullable
    public NumericFunctionsTemplate createNumericFunctionsTemplate(
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String header)
    {
        @Nullable NumericFunctionsTemplate result = null;

        try
        {
            @Nullable NumericFunctionsTemplateFactory t_TemplateFactory =
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

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String retrieveTemplateFileName(@NotNull final T template)
    {
        return "NumericFunctions.java";
    }
}
