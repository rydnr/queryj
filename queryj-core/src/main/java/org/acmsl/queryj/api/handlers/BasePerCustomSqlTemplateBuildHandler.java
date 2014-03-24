//;-*- mode: java -*-
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
 * Filename: BasePerCustomSqlTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds all templates to generate sources for each
 *              custom SQL.
 *
 */
package org.acmsl.queryj.api.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.PerCustomSqlTemplateContext;
import org.acmsl.queryj.api.exceptions.CannotRetrieveDatabaseInformationException;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.metadata.SqlDAO;
import org.acmsl.queryj.api.PerCustomSqlTemplate;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.SqlElement;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.api.PerCustomSqlTemplateFactory;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Builds all templates to generate sources for each custom SQL.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@SuppressWarnings("unused")
public abstract class BasePerCustomSqlTemplateBuildHandler
    <T extends PerCustomSqlTemplate<C>, C extends PerCustomSqlTemplateContext>
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The key for storing the custom SQL in the parameter map.
     */
    public static final String CUSTOM_SQL = "..CustOMsqL:::";
    
    /**
     * Creates a <code>BasePerCustomSqlTemplateBuildHandler</code> instance.
     */
    public BasePerCustomSqlTemplateBuildHandler() {}

    /**
     * Handles given information.
     *
     *
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand parameters)
        throws  QueryJBuildException
    {
        @NotNull final DatabaseMetaData t_DatabaseMetadata = retrieveDatabaseMetaData(parameters);

        buildTemplates(parameters, t_DatabaseMetadata);

        return false;
    }

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param metaData the database metadata.
     */
    protected void buildTemplates(
        @NotNull final QueryJCommand parameters, @NotNull final DatabaseMetaData metaData)
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
                new CannotRetrieveDatabaseInformationException(sqlException);
        }
    }

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     */
    protected void buildTemplates(
        @NotNull final QueryJCommand parameters,
        @NotNull final String engineName,
        @Nullable final String engineVersion,
        @NotNull final String quote)
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
     * Builds the templates
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom SQL provider.
     */
    protected void buildTemplates(
        @NotNull final QueryJCommand parameters,
        @NotNull final String engineName,
        @Nullable final String engineVersion,
        @NotNull final String quote,
        @Nullable final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider)
      throws  QueryJBuildException
    {
        if (metadataManager != null)
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
                retrieveCustomSql(
                    parameters, customSqlProvider, metadataManager),
                retrieveHeader(parameters));
        }
    }
    
    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @NotNull
    protected abstract PerCustomSqlTemplateFactory<T, C> retrieveTemplateFactory();

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom sql provider.
     * @param templateFactory the template factory.
     * @param projectPackage the project package.
     * @param repository the repository.
     * @param sqlElements the custom SQL elements.
     * @param header the header.
     */
    protected void buildTemplates(
        @NotNull final QueryJCommand parameters,
        @NotNull final String engineName,
        @Nullable final String engineVersion,
        @NotNull final String quote,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final PerCustomSqlTemplateFactory<T, C> templateFactory,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @NotNull final List<Sql<String>> sqlElements,
        @Nullable final String header)
      throws  QueryJBuildException
    {
        @NotNull final List<T> t_lTemplates = new ArrayList<>(sqlElements.size());

        @Nullable final SqlElement<String> t_SqlElement = null;

        for (@Nullable final Sql<String> t_Sql : sqlElements)
        {
            if (t_Sql != null)
            {
                t_lTemplates.add(
                    templateFactory.createTemplate(
                        t_Sql,
                        customSqlProvider,
                        metadataManager,
                        retrievePackage(t_Sql, engineName, parameters),
                        engineName,
                        engineVersion,
                        projectPackage,
                        repository,
                        header));
            }
        }

        storeTemplates(t_lTemplates, parameters);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param customSql the custom SQL.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     */
    @NotNull
    protected String retrievePackage(
        @NotNull final Sql<String> customSql,
        @NotNull final String engineName,
        @NotNull final QueryJCommand parameters)
    {
        return
            retrievePackage(
                customSql,
                engineName,
                retrieveProjectPackage(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name.
     * @param customSql the custom SQL.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     */
    @NotNull
    protected abstract String retrievePackage(
        @NotNull final Sql<String> customSql,
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
     * @param customSqlProvider the custom SQL provider.
     * @param metadataManager the database metadata manager.
     * @return such templates.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected List<Sql<String>> retrieveCustomSql(
        @NotNull final QueryJCommand parameters,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        @NotNull final List<Sql<String>> result;

        @Nullable final List<Sql<String>> aux = new QueryJCommandWrapper<List<Sql<String>>>(parameters).getSetting(CUSTOM_SQL);

        if  (aux == null)
        {
            result = retrieveCustomSqlElements(customSqlProvider.getSqlDAO(), metadataManager);
        }
        else
        {
            result = aux;
        }

        return result;
    }

    /**
     * Retrieves the custom SQL elements.
     * @param sqlDAO the {SqlDAO} instance.
     * @param metadataManager the database metadata manager.
     * @return such foreign keys.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected List<Sql<String>> retrieveCustomSqlElements(
        @NotNull final SqlDAO sqlDAO,
        @NotNull final MetadataManager metadataManager)
    {
        return sqlDAO.findAll();
    }
}
