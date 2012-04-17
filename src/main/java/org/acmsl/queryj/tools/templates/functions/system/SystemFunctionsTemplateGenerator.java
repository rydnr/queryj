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
 * Filename: SystemFunctionsTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate system function repositories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.system;

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
 * Is able to generate system function repositories according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class SystemFunctionsTemplateGenerator<T extends SystemFunctionsTemplate>
    extends AbstractTemplateGenerator<T>
    implements  SystemFunctionsTemplateFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class SystemFunctionsTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final SystemFunctionsTemplateGenerator SINGLETON =
            new SystemFunctionsTemplateGenerator();
    }

    /**
     * Public constructor to allow reflective instantiation.
     */
    public SystemFunctionsTemplateGenerator()
    {
        @NotNull TemplateMappingManager t_TemplateMappingManager =
            TemplateMappingManager.getInstance();

        t_TemplateMappingManager.addDefaultTemplateFactoryClass(
            TemplateMappingManager.SYSTEM_FUNCTIONS_TEMPLATE,
            getClass().getName());
    }

    /**
     * Retrieves a {@link SystemFunctionsTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static SystemFunctionsTemplateGenerator getInstance()
    {
        return SystemFunctionsTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Adds a new template factory class.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     */
    public void addTemplateFactoryClass(
        @NotNull final String engineName,
        final String engineVersion,
        @NotNull final String templateFactoryClass)
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
     */
    protected void addTemplateFactoryClass(
        @NotNull final String engineName,
        final String engineVersion,
        @NotNull final String templateFactoryClass,
        @NotNull final TemplateMappingManager templateMappingManager)
    {
        templateMappingManager.addTemplateFactoryClass(
            TemplateMappingManager.SYSTEM_FUNCTIONS_TEMPLATE,
            engineName,
            engineVersion,
            templateFactoryClass);
    }

    /**
     * Retrieves the template factory instance.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @throws QueryJBuildException if the factory class is invalid.
     */
    @Nullable
    protected SystemFunctionsTemplateFactory getTemplateFactory(
        @NotNull final String engineName, final String engineVersion)
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
     */
    @Nullable
    protected SystemFunctionsTemplateFactory getTemplateFactory(
        @NotNull final String engineName,
        final String engineVersion,
        @NotNull final TemplateMappingManager templateMappingManager)
      throws  QueryJBuildException
    {
        @Nullable SystemFunctionsTemplateFactory result = null;

        @Nullable Object t_TemplateFactory =
            templateMappingManager.getTemplateFactory(
                TemplateMappingManager.SYSTEM_FUNCTIONS_TEMPLATE,
                engineName,
                engineVersion);

        if  (t_TemplateFactory != null)
        {
            if  (!(t_TemplateFactory instanceof SystemFunctionsTemplateFactory))
            {
                throw
                    new QueryJBuildException(
                        "invalid.system.function.template.factory");
            }
            else 
            {
                result = (SystemFunctionsTemplateFactory) t_TemplateFactory;
            }
        }

        return result;
    }

    /**
     * Generates a system functions template.
     * @param packageName the package name.
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
    public SystemFunctionsTemplate createSystemFunctionsTemplate(
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String header)
    {
        @Nullable SystemFunctionsTemplate result = null;

        @Nullable SystemFunctionsTemplateFactory t_TemplateFactory = null;

        try
        {
            t_TemplateFactory =
                getTemplateFactory(engineName, engineVersion);
        }
        catch  (@NotNull final QueryJBuildException buildException)
        {
            Log t_Log =
                UniqueLogFactory.getLog(
                    SystemFunctionsTemplateGenerator.class);

            if  (t_Log != null)
            {
                t_Log.warn(
                    "Cannot retrieve system-functions template",
                    buildException);
            }
        }

        if  (   (t_TemplateFactory != null)
             && (!t_TemplateFactory.getClass().equals(getClass())))
        {
            result =
                t_TemplateFactory.createSystemFunctionsTemplate(
                    packageName,
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
        return "SystemFunctions.java";
    }
}
