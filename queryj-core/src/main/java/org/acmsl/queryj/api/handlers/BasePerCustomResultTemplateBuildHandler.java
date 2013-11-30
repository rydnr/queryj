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
 * Filename: BasePerCustomResultTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds all templates to generate sources for each
 *              custom result.
 *
 */
package org.acmsl.queryj.api.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.PerCustomResultTemplateContext;
import org.acmsl.queryj.api.exceptions.CannotCreateCustomResultTemplateException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.exceptions.QueryJException;
import org.acmsl.queryj.api.PerCustomResultTemplate;
import org.acmsl.queryj.api.PerCustomResultTemplateFactory;
import org.acmsl.queryj.customsql.CustomResultUtils;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.SqlResultDAO;
import org.acmsl.queryj.tools.DebugUtils;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Builds all templates to generate sources for each custom result.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
@SuppressWarnings("unused")
public abstract class BasePerCustomResultTemplateBuildHandler
    <T extends PerCustomResultTemplate<C>,
     C extends PerCustomResultTemplateContext,
     TF extends PerCustomResultTemplateFactory<T, C>>
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The key for storing the custom RESULT in the parameter map.
     */
    public static final String CUSTOM_RESULTS = "..CustOMresultS:::";

    /**
     * The key for storing the custom RESULTS (no duplicated entries) in the parameter map.
     */
    public static final String CUSTOM_RESULTS_NO_DUPLICATES = CUSTOM_RESULTS + "..NoDuplic4tes;";

    /**
     * Creates a <code>BasePerCustomResultTemplateBuildHandler</code> instance.
     */
    @SuppressWarnings("unused")
    public BasePerCustomResultTemplateBuildHandler() {}

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand parameters)
        throws QueryJBuildException
    {
        final boolean result;

        @Nullable final MetadataManager t_MetadataManager =
            retrieveMetadataManager(parameters);

        if (t_MetadataManager != null)
        {
            buildTemplates(
                parameters,
                t_MetadataManager,
                retrieveCustomSqlProvider(parameters));

            result = false;
        }
        else
        {
            result = true;
        }

        return result;
    }

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom RESULT provider.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @ThreadSafe
    protected void buildTemplates(
        @NotNull final QueryJCommand parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider)
      throws  QueryJBuildException
    {
        buildTemplates(
            parameters,
            metadataManager,
            customSqlProvider,
            retrieveTemplateFactory(),
            CachingDecoratorFactory.getInstance(),
            retrieveProjectPackage(parameters),
            retrieveTableRepositoryName(parameters),
            retrieveHeader(parameters),
            retrieveImplementMarkerInterfaces(parameters),
            retrieveJmx(parameters),
            retrieveJNDILocation(parameters),
            retrieveDisableGenerationTimestamps(parameters),
            retrieveDisableNotNullAnnotations(parameters),
            retrieveDisableCheckthreadAnnotations(parameters),
            retrieveCustomResults(parameters, customSqlProvider, isDuplicatedResultsAllowed()),
            CustomResultUtils.getInstance());
    }

    /**
     * Retrieves whether for this template type in particular, duplicated results
     * are allowed or not (i.e., results differing only in the multiplicity factor).
     * @return <code>true</code> in such case.
     */
    protected boolean isDuplicatedResultsAllowed()
    {
        return false;
    }

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    protected abstract TF retrieveTemplateFactory();

    /**
     * Builds the templates.
     * @param parameters the parameter map.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param templateFactory the {@link org.acmsl.queryj.api.TemplateFactory} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param projectPackage the project package.
     * @param repository the repository.
     * @param header the header.
     * @param implementMarkerInterfaces whether to implement marker interfaces.
     * @param jmx whether to enable JMX support.
     * @param jndiLocation the JNDI location.
     * @param disableGenerationTimestamps whether to disable generation timestamps.
     * @param disableNotNullAnnotations whether to disable NotNull annotations.
     * @param disableCheckthreadAnnotations whether to disable checkthread.org annotations or not.
     * @param resultElements the {@link Result} list.
     * @param customResultUtils the {@link CustomResultUtils} instance.
     * @throws QueryJBuildException if the templates cannot be built.
     */
    @ThreadSafe
    @SuppressWarnings("unchecked")
    protected void buildTemplates(
        @NotNull final QueryJCommand parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final TF templateFactory,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @Nullable final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        final boolean disableGenerationTimestamps,
        final boolean disableNotNullAnnotations,
        final boolean disableCheckthreadAnnotations,
        @NotNull final List<Result> resultElements,
        @NotNull final CustomResultUtils customResultUtils)
      throws  QueryJBuildException
    {
        @NotNull final List<T> t_lTemplates = new ArrayList<T>();

        @Nullable T t_Template;

        for  (@Nullable final Result t_ResultElement: resultElements)
        {
            if (   (t_ResultElement != null)
                && (isGenerationAllowedForResult(
                        t_ResultElement, customSqlProvider, metadataManager, customResultUtils)))
            {
                try
                {
                    t_Template =
                        templateFactory.createTemplate(
                            customSqlProvider,
                            metadataManager,
                            decoratorFactory,
                            retrievePackage(
                                t_ResultElement,
                                customSqlProvider,
                                metadataManager,
                                metadataManager.getEngineName(),
                                parameters),
                            projectPackage,
                            repository,
                            header,
                            implementMarkerInterfaces,
                            jmx,
                            jndiLocation,
                            disableGenerationTimestamps,
                            disableNotNullAnnotations,
                            disableCheckthreadAnnotations,
                            t_ResultElement);

                    if  (   (t_Template != null)
                         && (!t_lTemplates.contains(t_Template)))
                    {
                        t_lTemplates.add(t_Template);
                    }
                }
                catch  (@NotNull final QueryJException queryjException)
                {
                    throw
                        new CannotCreateCustomResultTemplateException(t_ResultElement, queryjException);
                }
            }
        }

        storeTemplates(t_lTemplates, parameters);
    }

    /**
     * Checks whether the generation is allowed for given result.
     * @param customResult the custom result.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customResultUtils the {@link CustomResultUtils} instance.
     * @return <code>true</code> in such case.
     */
    @ThreadSafe
    protected boolean isGenerationAllowedForResult(
        @NotNull final Result customResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomResultUtils customResultUtils)
    {
        if (DebugUtils.getInstance().debugEnabledForResultId(customResult.getId()))
        {
            @SuppressWarnings("unused")
            final int a = 1;
        }

        boolean result =
            customResultUtils.isGenerationAllowedForResult(customResult.getId());

        if (result)
        {
            result =
                !customResultUtils.isImplicit(customResult, customSqlProvider, metadataManager);

        }

        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param customResult the custom RESULT.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the database metadata manager.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws QueryJBuildException if the package retrieval process if faulty.
     */
    @ThreadSafe
    protected String retrievePackage(
        @NotNull final Result customResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final String engineName,
        @NotNull final QueryJCommand parameters)
      throws  QueryJBuildException
    {
        return
            retrievePackage(
                customResult,
                customSqlProvider,
                metadataManager,
                engineName,
                retrieveProjectPackage(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name.
     * @param customResult the custom result.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the database metadata manager.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the {@link PackageUtils} instance.
     * @return the package name.
     */
    @ThreadSafe
    protected abstract String retrievePackage(
        @NotNull final Result customResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils);

    /**
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     */
    protected abstract void storeTemplates(
        @NotNull final List<T> templates, @NotNull final QueryJCommand parameters);

    /**
     * Retrieves the foreign keys.
     * @param parameters the parameter map.
     * @param customSqlProvider the custom RESULT provider.
     * @param allowDuplicates whether to remove duplicates.
     * @return such templates.
     * @throws QueryJBuildException if the templates cannot be retrieved for any
     * reason.
     */
    @NotNull
    protected List<Result> retrieveCustomResults(
        @NotNull final QueryJCommand parameters,
        @NotNull final CustomSqlProvider customSqlProvider,
        final boolean allowDuplicates)
      throws  QueryJBuildException
    {
        String t_strKey = CUSTOM_RESULTS;

        if (!allowDuplicates)
        {
            t_strKey = CUSTOM_RESULTS_NO_DUPLICATES;
        }

        @Nullable List<Result> result = new QueryJCommandWrapper<List<Result>>(parameters).getSetting(t_strKey);

        if  (result == null)
        {
            result = retrieveCustomResultElements(customSqlProvider);

            if (!allowDuplicates)
            {
                result = fixDuplicated(result);
            }

            parameters.setSetting(t_strKey, result);
        }

        return result;
    }

    /**
     * Fixes (semantically-)duplicated results.
     * @param results the original results.
     * @return the list without duplicates (i.e. {@link Result} differing only if they're single or multiple).
     */
    @ThreadSafe
    @NotNull
    protected List<Result> fixDuplicated(@NotNull final List<Result> results)
    {
        @NotNull final List<Result> result = new ArrayList<Result>(results.size());

        @NotNull final HashSet<Result> t_Aux = new HashSet<Result>(results.size());

        t_Aux.addAll(results);

        result.addAll(t_Aux);

        return result;
    }

    /**
     * Retrieves the custom result elements.
     * @param customSqlProvider the custom RESULT provider.
     * @return such result elements.
     */
    @ThreadSafe
    @NotNull
    protected List<Result> retrieveCustomResultElements(
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return retrieveCustomResultElements(customSqlProvider.getSqlResultDAO());
    }

    /**
     * Retrieves the custom result elements.
     * @param resultDAO the {@link SqlResultDAO} instance.
     * @return such result elements.
     */
    @ThreadSafe
    @NotNull
    protected List<Result> retrieveCustomResultElements(
        @NotNull final SqlResultDAO resultDAO)
    {
        return resultDAO.findAll();
    }

    /**
     * Displays useful information about this handler.
     * @return such information.
     */
    @ThreadSafe
    @NotNull
    @Override
    public String toString()
    {
        return TemplateBuildHandler.PREFIX + getClass().getSimpleName();
    }
}
