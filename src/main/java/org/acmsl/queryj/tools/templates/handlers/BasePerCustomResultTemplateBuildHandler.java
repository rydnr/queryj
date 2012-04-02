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
 * Filename: BasePerCustomResultTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds all templates to generate sources for each
 *              custom result.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.metadata.AttributeDecorator;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplate;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplateFactory;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Builds all templates to generate sources for each custom result.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerCustomResultTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty Result array.
     */
    public static final Result[] EMPTY_RESULT_ARRAY = new Result[0];

    /**
     * A cached empty BasePerCustomResultTemplate array.
     */
    public static final BasePerCustomResultTemplate[]
        EMPTY_BASEPERCUSTOMRESULTTEMPLATE_ARRAY =
            new BasePerCustomResultTemplate[0];

    /**
     * The key for storing the custom RESULT in the parameter map.
     */
    public static final String CUSTOM_RESULT = "..CustOMresult:::";

    /**
     * Creates a <code>BasePerCustomResultTemplateBuildHandler</code> instance.
     */
    public BasePerCustomResultTemplateBuildHandler() {};

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        buildTemplates(parameters, retrieveDatabaseMetaData(parameters));

        return false;
    }

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param metaData the database metadata.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     */
    protected void buildTemplates(
        @NotNull final Map parameters, @NotNull final DatabaseMetaData metaData)
      throws  QueryJBuildException
    {
        try
        {
            buildTemplates(
                parameters,
                metaData.getDatabaseProductName(),
                retrieveDatabaseProductVersion(metaData),
                fixQuote(metaData.getIdentifierQuoteString()));
        }
        catch  (@NotNull final SQLException sqlException)
        {
            throw
                new QueryJBuildException(
                    "Cannot retrieve database product information",
                    sqlException);
        }
    }

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition quote != null
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote)
      throws  QueryJBuildException
    {
        buildTemplates(
            parameters,
            engineName,
            engineVersion,
            quote,
            retrieveMetadataManager(parameters),
            retrieveCustomSqlProvider(parameters));
    }

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom RESULT provider.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider)
      throws  QueryJBuildException
    {
        buildTemplates(
            parameters,
            engineName,
            engineVersion,
            quote,
            metadataManager,
            customSqlProvider,
            retrieveTemplateFactory(),
            retrieveProjectPackage(parameters),
            retrieveTableRepositoryName(parameters),
            retrieveHeader(parameters),
            retrieveCustomResult(
                parameters, customSqlProvider, metadataManager));
    }

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    protected abstract BasePerCustomResultTemplateFactory retrieveTemplateFactory();

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom result provider.
     * @param templateFactory the template factory.
     * @param projectPackage the project package.
     * @param repository the repository.
     * @param header the header.
     * @param resultElements the custom RESULT elements.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition templateFactory != null
     * @precondition projectPackage != null
     * @precondition repository != null
     * @precondition resultElements != null
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        @NotNull final BasePerCustomResultTemplateFactory templateFactory,
        final String projectPackage,
        final String repository,
        final String header,
        @Nullable final Result[] resultElements)
      throws  QueryJBuildException
    {
        boolean result = false;

        int t_iLength = (resultElements != null) ? resultElements.length : 0;

        @NotNull Collection t_cTemplates = new ArrayList();

        @Nullable Result t_ResultElement = null;

        @Nullable BasePerCustomResultTemplate t_Template = null;

        try
        {
            for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
            {
                t_ResultElement = resultElements[t_iIndex];

                t_Template =
                    templateFactory.createTemplate(
                        t_ResultElement,
                        customSqlProvider,
                        metadataManager,
                        retrievePackage(
                            t_ResultElement,
                            customSqlProvider,
                            metadataManager,
                            engineName,
                            parameters),
                        engineName,
                        engineVersion,
                        projectPackage,
                        repository,
                        header);

                if  (t_Template != null)
                {
                    t_cTemplates.add(t_Template);
                }
            }
        }
        catch  (@NotNull final QueryJException queryjException)
        {
            throw
                new QueryJBuildException(
                    "Cannot create template", queryjException);
        }

        storeTemplates(
            (BasePerCustomResultTemplate[])
            t_cTemplates.toArray(
                EMPTY_BASEPERCUSTOMRESULTTEMPLATE_ARRAY),
            parameters);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param customResult the custom RESULT.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the database metadata manager.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws QueryJBuildException if the package retrieval process if faulty.
     * @precondition customResult != null
     * @precondition parameters != null
     */
    protected String retrievePackage(
        final Result customResult,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final String engineName,
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
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the database metadata manager.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws QueryJBuildException if the package retrieval process if faulty.
     */
    protected abstract String retrievePackage(
        final Result customResult,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final String engineName,
        final String projectPackage,
        final PackageUtils packageUtils);

    /**
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     * @precondition templates != null
     * @precondition parameters != null
     */
    protected abstract void storeTemplates(
        final BasePerCustomResultTemplate[] templates, final Map parameters);

    /**
     * Retrieves the foreign keys.
     * @param parameters the parameter map.
     * @param customSqlProvider the custom RESULT provider.
     * @param metadataManager the database metadata manager.
     * @return such templates.
     * @throws QueryJBuildException if the templates cannot be retrieved for any
     * reason.
     * @precondition parameters != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     */
    @NotNull
    protected Result[] retrieveCustomResult(
        @NotNull final Map parameters,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager)
      throws  QueryJBuildException
    {
        @NotNull Result[] result = (Result[]) parameters.get(CUSTOM_RESULT);

        if  (result == null)
        {
            @NotNull Collection t_cCustomResult = new ArrayList();

            @NotNull Result[] t_aResultElements =
                retrieveCustomResultElements(
                    customSqlProvider, metadataManager);

            int t_iLength =
                (t_aResultElements != null) ? t_aResultElements.length : 0;

            for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
            {
                t_cCustomResult.add(t_aResultElements[t_iIndex]);
            }

            result =
                (Result[]) t_cCustomResult.toArray(EMPTY_RESULT_ARRAY);
        }

        return result;
    }

    /**
     * Retrieves the custom RESULT elements.
     * @param customSqlProvider the custom RESULT provider.
     * @param metadataManager the database metadata manager.
     * @return such foreign keys.
     * @precondition metadataManager != null
     */
    @NotNull
    protected Result[] retrieveCustomResultElements(
        @Nullable final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager)
    {
        @NotNull Collection t_cResult = new ArrayList();

        @Nullable Collection t_cCollection =
            (customSqlProvider != null)
            ? customSqlProvider.getCollection() : null;

        if  (t_cCollection != null)
        {
            Iterator t_itElements = t_cCollection.iterator();

            if  (t_itElements != null)
            {
                @Nullable Object t_Item = null;

                while  (t_itElements.hasNext())
                {
                    t_Item = t_itElements.next();

                    if  (t_Item instanceof Result)
                    {
                        t_cResult.add(t_Item);
                    }
                }
            }
        }

        return (Result[]) t_cResult.toArray(EMPTY_RESULT_ARRAY);
    }
}
