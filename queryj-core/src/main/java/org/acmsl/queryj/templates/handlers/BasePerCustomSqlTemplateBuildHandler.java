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
 * Filename: BasePerCustomSqlTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds all templates to generate sources for each
 *              custom SQL.
 *
 */
package org.acmsl.queryj.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.metadata.SqlDAO;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.SqlElement;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.templates.BasePerCustomSqlTemplate;
import org.acmsl.queryj.templates.BasePerCustomSqlTemplateFactory;

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
import java.util.Map;

/**
 * Builds all templates to generate sources for each custom SQL.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@SuppressWarnings("unused")
public abstract class BasePerCustomSqlTemplateBuildHandler
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
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected boolean handle(@NotNull final Map<String, ?> parameters)
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
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void buildTemplates(
        @NotNull final Map<String, ?> parameters, @NotNull final DatabaseMetaData metaData)
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
                      "Cannot retrieve database product name, "
                    + "version or quote string",
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
     */
    protected void buildTemplates(
        @NotNull final Map<String, ?> parameters,
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
            retrieveMetadataManager((Map <String, MetadataManager>) parameters),
            retrieveCustomSqlProvider((Map<String, CustomSqlProvider>) parameters));
    }

    /**
     * Builds the templates
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom SQL provider.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void buildTemplates(
        @NotNull final Map<String, ?> parameters,
        @NotNull final String engineName,
        @Nullable final String engineVersion,
        @NotNull final String quote,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider)
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
            retrieveProjectPackage((Map<String, String>) parameters),
            retrieveTableRepositoryName((Map <String, String>) parameters),
            retrieveCustomSql(
                (Map <String, List<Sql>>) parameters, customSqlProvider, metadataManager),
            retrieveHeader((Map <String, String>) parameters));
    }
    
    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @NotNull
    protected abstract BasePerCustomSqlTemplateFactory retrieveTemplateFactory();

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
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void buildTemplates(
        @NotNull final Map<String, ?> parameters,
        @NotNull final String engineName,
        @Nullable final String engineVersion,
        @NotNull final String quote,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final BasePerCustomSqlTemplateFactory templateFactory,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @NotNull final List<Sql> sqlElements,
        @Nullable final String header)
      throws  QueryJBuildException
    {
        @NotNull final List<BasePerCustomSqlTemplate> t_lTemplates =
            new ArrayList<BasePerCustomSqlTemplate>(sqlElements.size());

        @Nullable final SqlElement t_SqlElement = null;

        for (@Nullable final Sql t_Sql : sqlElements)
        {
            if (t_Sql != null)
            {
                t_lTemplates.add(
                    templateFactory.createTemplate(
                        t_Sql,
                        customSqlProvider,
                        metadataManager,
                        retrievePackage(t_Sql, engineName, (Map<String, String>) parameters),
                        engineName,
                        engineVersion,
                        projectPackage,
                        repository,
                        header));
            }
        }

        storeTemplates(t_lTemplates, (Map <String, List<BasePerCustomSqlTemplate>>) parameters);
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
        @NotNull final Sql customSql,
        @NotNull final String engineName,
        @NotNull final Map<String, String> parameters)
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
        @NotNull final Sql customSql,
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils);

    /**
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     */
    protected abstract void storeTemplates(
        @NotNull final List<BasePerCustomSqlTemplate> templates,
        @NotNull final Map<String, List<BasePerCustomSqlTemplate>> parameters);

    /**
     * Retrieves the foreign keys.
     * @param parameters the parameter map.
     * @param customSqlProvider the custom SQL provider.
     * @param metadataManager the database metadata manager.
     * @return such templates.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected List<Sql> retrieveCustomSql(
        @NotNull final Map<String, List<Sql>> parameters,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<Sql> result = parameters.get(CUSTOM_SQL);

        if  (result == null)
        {
            result =
                retrieveCustomSqlElements(customSqlProvider.getSqlDAO(), metadataManager);
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
    protected List<Sql> retrieveCustomSqlElements(
        @NotNull final SqlDAO sqlDAO,
        @NotNull final MetadataManager metadataManager)
    {
        return sqlDAO.findAll();
    }
}
