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
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.PerCustomSqlTemplate;
import org.acmsl.queryj.api.PerCustomSqlTemplateContext;
import org.acmsl.queryj.api.PerCustomSqlTemplateFactory;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.SqlDAO;
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
import java.util.List;

/**
 * Builds all templates to generate sources for each custom SQL.
 * @param <T> the template type.
 * @param <C> the template context type.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 2.0
 */
@SuppressWarnings("unused")
@ThreadSafe
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
        buildTemplates(
            parameters,
            retrieveMetadataManager(parameters),
            retrieveCustomSqlProvider(parameters));

        return false;
    }

    /**
     * Builds the templates
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom SQL provider.
     * @throws QueryJBuildException if the templates cannot be built.
     */
    protected void buildTemplates(
        @NotNull final QueryJCommand parameters,
        @Nullable final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider)
      throws  QueryJBuildException
    {
        if (metadataManager != null)
        {
            buildTemplates(
                parameters,
                retrieveTemplateFactory(),
                retrieveCustomSql(parameters, customSqlProvider, metadataManager));
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
     * @param templateFactory the template factory.
     * @param sqlElements the custom SQL elements.
     * @throws QueryJBuildException if the templates cannot be built.
     */
    protected void buildTemplates(
        @NotNull final QueryJCommand parameters,
        @NotNull final PerCustomSqlTemplateFactory<T, C> templateFactory,
        @NotNull final List<Sql<String>> sqlElements)
      throws  QueryJBuildException
    {
        @NotNull final List<T> t_lTemplates = new ArrayList<>(sqlElements.size());

        for (@Nullable final Sql<String> t_Sql : sqlElements)
        {
            if (t_Sql != null)
            {
                t_lTemplates.add(
                    createTemplate(
                        templateFactory,
                        t_Sql,
                        parameters));
            }
        }

        storeTemplates(t_lTemplates, parameters);
    }

    /**
     * Creates a template.
     * @param templateFactory the template factory.
     * @param sql the SQL.
     * @param parameters the parameters.
     * @return the new template.
     */
    protected abstract T createTemplate(
        @NotNull final PerCustomSqlTemplateFactory<T, C> templateFactory,
        @NotNull final Sql<String> sql,
        @NotNull final QueryJCommand parameters);

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
