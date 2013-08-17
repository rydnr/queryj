/*
                        queryj

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
 * Filename: TemplatePackagingBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base build handler for Template Packaging templates.
 *
 * Date: 2013/08/17
 * Time: 11:00
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.dao.DAOTemplateUtils;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.handlers.BasePerTableTemplateBuildHandler;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.TableDAO;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.templates.packaging.TemplatePackagingContext;
import org.acmsl.queryj.templates.packaging.TemplatePackagingSettings;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplate;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplateFactory;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;

/*
 * Importing JetBrains annotations.
 */
import org.apache.commons.logging.Log;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Base build handler for Template Packaging templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/17 11:00
 */
@ThreadSafe
public abstract class TemplatePackagingBuildHandler
    <T extends TemplatePackagingTemplate<C>,
     TF extends TemplatePackagingTemplateFactory<T, C>,
     C extends TemplatePackagingContext>
    implements QueryJCommandHandler<QueryJCommand>,
               TemplatePackagingSettings
{
    /**
     * Creates a {@link TemplatePackagingBuildHandler} instance.
     */
    protected TemplatePackagingBuildHandler() {}

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws org.acmsl.queryj.api.exceptions.QueryJBuildException if the build process cannot be performed.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand parameters)
        throws QueryJBuildException
    {
        buildTemplate(parameters);

        return false;
    }

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    protected abstract TF retrieveTemplateFactory();

    /**
     * Builds the template.
     * @param parameters the parameters.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void buildTemplate(
        @NotNull final QueryJCommand parameters)
        throws  QueryJBuildException
    {
        buildTemplate(
            parameters,
            retrieveTemplateFactory(),
            retrieveProjectPackage(parameters),
            retrieveHeader(parameters),
            retrieveDisableGenerationTimestamps(parameters),
            retrieveDisableNotNullAnnotations(parameters),
            retrieveDisableCheckthreadAnnotations(parameters));
    }

    @NotNull
    public String retrieveProjectPackage(final QueryJCommand parameters)
    {
        @NotNull final String result;

        @Nullable final String projectPackage = parameters.getSetting(PROJECT_PACKAGE);

        if (projectPackage == null)
        {
            // TODO:
            throw new RuntimeException("Missing package");
        }
        else
        {
            result = projectPackage;
        }

        return result;
    }

    /**
     * Builds the template.
     *
     * @param parameters the parameters.
     * @param templateFactory the template factory.
     * @param projectPackage the project package.
     * @param header the header.
     * @param disableGenerationTimestamps whether to disable generation timestamps.
     * @param disableNotNullAnnotations whether to disable NotNull annotations.
     * @param disableCheckthreadAnnotations whether to disable checkthread.org annotations or not.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @SuppressWarnings("unchecked")
    protected void buildTemplate(
        @NotNull final QueryJCommand parameters,
        @NotNull final TF templateFactory,
        @NotNull final String projectPackage,
        @Nullable final String header,
        final boolean disableGenerationTimestamps,
        final boolean disableNotNullAnnotations,
        final boolean disableCheckthreadAnnotations)
        throws  QueryJBuildException
    {
        @NotNull final T t_Template;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws QueryJBuildException if the package retrieval process if faulty.
     */
    protected String retrievePackage(
        @NotNull final String tableName, @NotNull final String engineName, @NotNull final QueryJCommand parameters)
        throws  QueryJBuildException
    {
        return
            retrievePackage(
                tableName,
                engineName,
                retrieveProjectPackage(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the {@link PackageUtils} instance.
     * @return the package name.
     */
    protected abstract String retrievePackage(
        @NotNull final String tableName,
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils) throws QueryJBuildException;

    /**
     * Stores the template in given attribute map.
     * @param template the templates.
     * @param parameters the parameter map.
     */
    protected abstract void storeTemplate(
        @NotNull final T template, @NotNull final QueryJCommand parameters);

    /**
     * Creates a template with required information.
     * @param templateFactory the {@link org.acmsl.queryj.api.PerTableTemplateFactory} instance.
     * @param projectPackage the project's base package.
     * @param header the custom file header.
     * @param disableGenerationTimestamps whether to disable generation timestamps.
     * @param disableNotNullAnnotations whether to disable NotNull annotations.
     * @param disableCheckthreadAnnotations whether to disable checkthread.org annotations or not.
     * @param parameters the parameter map.
     */
    @Nullable
    protected T createTemplate(
        @NotNull final TF templateFactory,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String projectPackage,
        @Nullable final String header,
        final boolean disableGenerationTimestamps,
        final boolean disableNotNullAnnotations,
        final boolean disableCheckthreadAnnotations,
        @SuppressWarnings("unused") @NotNull final QueryJCommand parameters)
        throws  QueryJBuildException
    {
        return
            templateFactory.createTemplate(
                new TemplatePackagingContext()
                decoratorFactory,
                projectPackage,
                header,
                disableGenerationTimestamps,
                disableNotNullAnnotations,
                disableCheckthreadAnnotations);
    }

    /**
     * Retrieves the header.
     * @param command the command.
     * @return the header.
     */
    @NotNull
    public String retrieveHeader(@SuppressWarnings("unused") @NotNull final QueryJCommand command)
    {
        return TEMPLATE_PACKAGING_HEADER;
    }

    /**
     * Retrieves whether the generated content will be cached.
     * @param command the command.
     * @return always {@code false}.
     */
    public boolean retrieveCaching(@SuppressWarnings("unused") @NotNull final QueryJCommand command)
    {
        return false;
    }

    /**
     * Retrieves the number of threads to use in the generation process.
     * @param command the command.
     * @return the number of available processors.
     */
    public int retrieveThreadCount(@SuppressWarnings("unused") @NotNull final QueryJCommand command)
    {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Retrieves whether to disable generation timestamps or not.
     * @param parameters the parameter map.
     * @return such condition.
     */
    protected boolean retrieveDisableGenerationTimestamps(
        @SuppressWarnings("unused") @NotNull final QueryJCommand parameters)
    {
        return false;
    }

    /**
     * Retrieves whether to disable NotNull annotations or not.
     * @param parameters the parameter map.
     * @return such condition.
     */
    protected boolean retrieveDisableNotNullAnnotations(
        @SuppressWarnings("unused") @NotNull final QueryJCommand parameters)
    {
        return false;
    }

    /**
     * Retrieves whether to disable checkthread.org annotations or not.
     * @param parameters the parameter map.
     * @return such condition.
     */
    protected boolean retrieveDisableCheckthreadAnnotations(
        @SuppressWarnings("unused") @NotNull final QueryJCommand parameters)
    {
        return false;
    }
}
