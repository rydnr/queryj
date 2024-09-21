/*
                        QueryJ Core

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
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.PerCustomResultTemplateContext;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.PerCustomResultTemplate;
import org.acmsl.queryj.api.PerCustomResultTemplateFactory;
import org.acmsl.queryj.customsql.CustomResultUtils;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.SqlResultDAO;
import org.acmsl.queryj.metadata.engines.Engine;
import org.acmsl.queryj.tools.DebugUtils;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Builds all templates to generate sources for each custom result.
 * @param <T> the template type.
 * @param <C> the context type.
 * @param <TF> the template factory type.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro Armendariz</a>
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
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand parameters)
        throws QueryJBuildException
    {
        buildTemplates(
            parameters,
            retrieveMetadataManager(parameters),
            retrieveCustomSqlProvider(parameters));

        return false;
    }

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom RESULT provider.
     * @throws QueryJBuildException if the templates cannot be built.
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
            retrieveCustomResults(parameters, customSqlProvider, isDuplicatedResultsAllowed()),
            CustomResultUtils.getInstance());
    }

    /**
     * Retrieves whether for this template type in particular, duplicated results
     * are allowed or not (i.e., results differing only in the multiplicity factor).
     * @return {@code true} in such case.
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
        @NotNull final List<Result<String>> resultElements,
        @NotNull final CustomResultUtils customResultUtils)
      throws  QueryJBuildException
    {
        @NotNull final List<T> t_lTemplates = new ArrayList<>();

        @Nullable T t_Template;

        for  (@Nullable final Result<String> t_ResultElement: resultElements)
        {
            if (   (t_ResultElement != null)
                && (isGenerationAllowedForResult(
                        t_ResultElement, customSqlProvider, metadataManager, customResultUtils)))
            {
                t_Template =
                    createTemplate(
                        templateFactory,
                        t_ResultElement,
                        customSqlProvider.getSqlPropertyDAO().findByResult(t_ResultElement.getId()),
                        parameters);

                    /*
                try
                {
                    retrievePackage(
                        t_ResultElement,
                        customSqlProvider,
                        metadataManager,
                        metadataManager.getEngine(),
                        parameters),
                }
                catch  (@NotNull final QueryJException queryjException)
                {
                    throw
                        new CannotCreateCustomResultTemplateException(t_ResultElement, queryjException);
                }
                    */
                if  (   (t_Template != null)
                     && (!t_lTemplates.contains(t_Template)))
                {
                    t_lTemplates.add(t_Template);
                }
            }
        }

        storeTemplates(t_lTemplates, parameters);
    }

    /**
     * Creates a template.
     * @param templateFactory the template factory.
     * @param result the result.
     * @param properties the properties.
     * @param parameters the parameters.
     * @return the template.
     * @throws QueryJBuildException if the template cannot be created.
     */
    @Nullable
    protected abstract T createTemplate(
        @NotNull final TF templateFactory,
        @NotNull final Result<String> result,
        @NotNull final List<Property<String>> properties,
        @NotNull final QueryJCommand parameters)
      throws  QueryJBuildException;

    /**
     * Checks whether the generation is allowed for given result.
     * @param customResult the custom result.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customResultUtils the {@link CustomResultUtils} instance.
     * @return {@code true} in such case.
     */
    @ThreadSafe
    protected boolean isGenerationAllowedForResult(
        @NotNull final Result<String> customResult,
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
                !customResultUtils.isImplicit(customResult, metadataManager);

        }

        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param customResult the custom RESULT.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the database metadata manager.
     * @param engine the engine.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws QueryJBuildException if the package is unavailable.
     */
    @ThreadSafe
    protected abstract String retrievePackage(
        @NotNull final Result<String> customResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final Engine<String> engine,
        @NotNull final QueryJCommand parameters)
      throws  QueryJBuildException;

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
     * @throws QueryJBuildException if the custom results are unavailable.
     */
    @NotNull
    protected List<Result<String>> retrieveCustomResults(
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

        @Nullable List<Result<String>> result =
            new QueryJCommandWrapper<List<Result<String>>>(parameters).getSetting(t_strKey);

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
    protected List<Result<String>> fixDuplicated(@NotNull final List<Result<String>> results)
    {
        @NotNull final Map<String, Result<String>> result = new HashMap<>(results.size());

        for (@Nullable final Result<String> customResult : results)
        {
            if (customResult != null)
            {
                @Nullable final Result<String> previous = result.get(customResult.getClassValue());

                if (   (previous == null)
                    || (customResult.compareTo(previous) > 0))
                {
                    result.put(customResult.getClassValue(), customResult);
                }
            }
        }

        return new ArrayList<>(result.values());
    }

    /**
     * Retrieves the custom result elements.
     * @param customSqlProvider the custom RESULT provider.
     * @return such result elements.
     */
    @ThreadSafe
    @NotNull
    protected List<Result<String>> retrieveCustomResultElements(
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
    protected List<Result<String>> retrieveCustomResultElements(
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
