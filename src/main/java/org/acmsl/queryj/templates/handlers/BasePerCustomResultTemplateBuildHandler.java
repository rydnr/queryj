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
package org.acmsl.queryj.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomResultUtils;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.templates.BasePerCustomResultTemplate;
import org.acmsl.queryj.templates.BasePerCustomResultTemplateFactory;

/*
 * Importing some ACM-SL classes.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Builds all templates to generate sources for each custom result.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerCustomResultTemplateBuildHandler
    <T extends BasePerCustomResultTemplate,
     TF extends BasePerCustomResultTemplateFactory<T>>
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The key for storing the custom RESULT in the parameter map.
     */
    public static final String CUSTOM_RESULTS = "..CustOMresultS:::";

    /**
     * Creates a <code>BasePerCustomResultTemplateBuildHandler</code> instance.
     */
    public BasePerCustomResultTemplateBuildHandler() {}

    /**
     * Handles given information.
     *
     *
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    @Override
    protected boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        boolean result = true;

        @Nullable final MetadataManager t_MetadataManager = retrieveMetadataManager(parameters);

        if (t_MetadataManager != null)
        {
            buildTemplates(
                parameters,
                t_MetadataManager,
                retrieveCustomSqlProvider(parameters));
            result = false;
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
    protected void buildTemplates(
        @NotNull final Map parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider)
      throws  QueryJBuildException
    {
        buildTemplates(
            parameters,
            metadataManager,
            customSqlProvider,
            retrieveTemplateFactory(),
            retrieveProjectPackage(parameters),
            retrieveTableRepositoryName(parameters),
            retrieveHeader(parameters),
            retrieveImplementMarkerInterfaces(parameters),
            retrieveJmx(parameters),
            retrieveJNDILocation(parameters),
            retrieveCustomResults(parameters, customSqlProvider),
            CustomResultUtils.getInstance());
    }

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    protected abstract TF retrieveTemplateFactory();

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom result provider.
     * @param templateFactory the template factory.
     * @param projectPackage the project package.
     * @param repository the repository.
     * @param header the header.
     * @param implementMarkerInterfaces whether to implement marker interfaces or not.
     * @param jmx whether to include JMX support or not.
     * @param resultElements the custom {@link Result elements}.
     * @param customResultUtils the {@link CustomResultUtils} instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final TF templateFactory,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        @NotNull final List<Result> resultElements,
        @NotNull final CustomResultUtils customResultUtils)
      throws  QueryJBuildException
    {
        @NotNull List<T> t_lTemplates = new ArrayList<T>();

        @Nullable T t_Template;

        for  (Result t_ResultElement: resultElements)
        {
            if (   (t_ResultElement != null)
                && (customResultUtils.isGenerationAllowedForResult(t_ResultElement.getId())))
            {
                try
                {
                    t_Template =
                        templateFactory.createTemplate(
                            customSqlProvider,
                            metadataManager,
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
                            t_ResultElement);

                    if  (t_Template != null)
                    {
                        t_lTemplates.add(t_Template);
                    }
                }
                catch  (@NotNull final QueryJException queryjException)
                {
                    throw
                        new QueryJBuildException(
                            "Cannot create template for result " + t_ResultElement.getId(),
                            queryjException);
                }
            }
        }

        storeTemplates(t_lTemplates, parameters);
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
     * @precondition customResult != null
     * @precondition parameters != null
     */
    protected String retrievePackage(
        @NotNull final Result customResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final String engineName,
        @NotNull final Map parameters)
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
     * @precondition templates != null
     * @precondition parameters != null
     */
    protected abstract void storeTemplates(
        @NotNull final List<T> templates, @NotNull final Map parameters);

    /**
     * Retrieves the foreign keys.
     * @param parameters the parameter map.
     * @param customSqlProvider the custom RESULT provider.
     * @return such templates.
     * @throws QueryJBuildException if the templates cannot be retrieved for any
     * reason.
     * @precondition parameters != null
     * @precondition customSqlProvider != null
     */
    @NotNull
    @SuppressWarnings("unchecked")
    protected List<Result> retrieveCustomResults(
        @NotNull final Map parameters,
        @NotNull final CustomSqlProvider customSqlProvider)
      throws  QueryJBuildException
    {
        @Nullable List<Result> result = (List<Result>) parameters.get(CUSTOM_RESULTS);

        if  (result == null)
        {
            result = retrieveCustomResultElements(customSqlProvider);
            result = fixDuplicated(result);
            parameters.put(CUSTOM_RESULTS, result);
        }

        return result;
    }

    /**
     * Fixes (semantically-)duplicated results.
     * @param results the original results.
     * @return the list without duplicates (i.e. {@link Result} differing only if they're single or multiple).
     */
    @NotNull
    protected List<Result> fixDuplicated(@NotNull final List<Result> results)
    {
        @NotNull final List<Result> result = new ArrayList<Result>(results.size());

        @NotNull final Map<String,Result> t_mAux = new HashMap<String,Result>();

        for (@Nullable Result t_Result : results)
        {
            if (t_Result != null)
            {
                t_mAux.put(t_Result.getClassValue(), t_Result);
            }
        }

        result.addAll(t_mAux.values());

        return result;
    }

    /**
     * Retrieves the custom result elements.
     * @param customSqlProvider the custom RESULT provider.
     * @return such result elements.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected List<Result> retrieveCustomResultElements(
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        @NotNull final List<Result> result = new ArrayList<Result>();

        @Nullable Collection t_cCollection =
            customSqlProvider.getCollection();

        if  (t_cCollection != null)
        {
            Iterator t_itElements = t_cCollection.iterator();

            if  (t_itElements != null)
            {
                @Nullable Object t_Item;

                while  (t_itElements.hasNext())
                {
                    t_Item = t_itElements.next();

                    if  (t_Item instanceof Result)
                    {
                        result.add((Result) t_Item);
                    }
                }
            }
        }

        return result;
    }
}
