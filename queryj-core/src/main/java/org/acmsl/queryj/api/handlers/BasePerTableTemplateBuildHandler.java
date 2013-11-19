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
 * Filename: BasePerTableTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a per-table templates using database metadata.
 *
 */
package org.acmsl.queryj.api.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.metadata.TableDAO;
import org.acmsl.queryj.api.PerTableTemplate;
import org.acmsl.queryj.api.PerTableTemplateFactory;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.api.dao.DAOTemplateUtils;
import org.acmsl.queryj.api.MetaLanguageUtils;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some JDK classes.
 */
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
 * Builds a per-table template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerTableTemplateBuildHandler
       <T extends PerTableTemplate,
        TF extends PerTableTemplateFactory<T>>
    extends AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a {@link BasePerTableTemplateBuildHandler} instance.
     */
    protected BasePerTableTemplateBuildHandler() {}
    /**
     * Handles given information.
     *
     *
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand parameters)
        throws  QueryJBuildException
    {
        final boolean result;

        @Nullable final MetadataManager t_MetadataManager =
            retrieveMetadataManager(parameters);

        if (t_MetadataManager != null)
        {
            buildTemplate(parameters, t_MetadataManager, t_MetadataManager.getTableDAO());
            result = false;
        }
        else
        {
            result = true;
        }

        return result;
    }

    /**
     * Builds the template.
     * @param parameters the parameters.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param tableDAO the {@link TableDAO} instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void buildTemplate(
        @NotNull final QueryJCommand parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final TableDAO tableDAO)
      throws  QueryJBuildException
    {
        buildTemplate(
            parameters,
            metadataManager,
            retrieveCustomSqlProvider(parameters),
            retrieveTemplateFactory(),
            retrieveProjectPackage(parameters),
            retrieveTableRepositoryName(parameters),
            retrieveHeader(parameters),
            retrieveImplementMarkerInterfaces(parameters),
            retrieveJmx(parameters),
            retrieveJNDILocation(parameters),
            retrieveDisableGenerationTimestamps(parameters),
            retrieveDisableNotNullAnnotations(parameters),
            retrieveDisableCheckthreadAnnotations(parameters),
            tableDAO.findAllTables(),
            CachingDecoratorFactory.getInstance(),
            DAOTemplateUtils.getInstance());
    }

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    protected abstract TF retrieveTemplateFactory();

    /**
     * Builds the template.
     *
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom sql provider.
     * @param templateFactory the template factory.
     * @param projectPackage the project package.
     * @param repository the repository.
     * @param header the header.
     * @param implementMarkerInterfaces whether to implement marker
     * interfaces.
     * @param jmx whether to include JMX support.
     * @param jndiLocation the JNDI path of the {@link javax.sql.DataSource}.
     * @param disableGenerationTimestamps whether to disable generation timestamps.
     * @param disableNotNullAnnotations whether to disable NotNull annotations.
     * @param disableCheckthreadAnnotations whether to disable checkthread.org annotations or not.
     * @param tables the tables.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param daoTemplateUtils the {@link DAOTemplateUtils} instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @SuppressWarnings("unchecked")
    protected void buildTemplate(
        @NotNull final QueryJCommand parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final TF templateFactory,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @Nullable final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        final boolean disableGenerationTimestamps,
        final boolean disableNotNullAnnotations,
        final boolean disableCheckthreadAnnotations,
        @NotNull final List<Table<String, Attribute<String>>> tables,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils)
      throws  QueryJBuildException
    {
        @NotNull final List<T> t_lTemplates = new ArrayList<T>();

        @Nullable T t_Template;

        for  (@Nullable final Table<String, Attribute<String>> t_Table : tables)
        {
            if (t_Table != null)
            {
                if (metadataManager.isGenerationAllowedForTable(t_Table.getName()))
                {
                    List<Row<String>> t_lStaticContent = retrieveCachedStaticContent(parameters, t_Table.getName());

                    if (t_lStaticContent == null)
                    {
                        try
                        {
                            t_lStaticContent =
                                retrieveStaticContent(
                                    t_Table.getName(),
                                    metadataManager,
                                    decoratorFactory,
                                    daoTemplateUtils);
                        }
                        catch (@Nullable final SQLException cannotRetrieveTableContents)
                        {
                            @Nullable
                            final Log t_Log = UniqueLogFactory.getLog(BasePerTableTemplateBuildHandler.class);

                            if (t_Log != null)
                            {
                                t_Log.error(
                                    "Cannot retrieve static contents for " + t_Table.getName(),
                                    cannotRetrieveTableContents);
                            }
                        }
                        if (t_lStaticContent == null)
                        {
                            t_lStaticContent = new ArrayList<Row<String>>(0);
                        }
                        storeCachedStaticContent(t_lStaticContent, parameters, t_Table.getName());
                    }

                    t_Template =
                        createTemplate(
                            templateFactory,
                            metadataManager,
                            customSqlProvider,
                            decoratorFactory,
                            retrievePackage(
                                t_Table.getName(), metadataManager.getEngineName(), parameters),
                            projectPackage,
                            repository,
                            header,
                            implementMarkerInterfaces,
                            jmx,
                            jndiLocation,
                            disableGenerationTimestamps,
                            disableNotNullAnnotations,
                            disableCheckthreadAnnotations,
                            t_Table.getName(),
                            t_lStaticContent,
                            parameters);

                    if  (t_Template != null)
                    {
                        t_lTemplates.add(t_Template);
                    }
                }
            }
        }

        storeTemplates(t_lTemplates, parameters);
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
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     */
    protected abstract void storeTemplates(
        @NotNull final List<T> templates, @NotNull final QueryJCommand parameters);

    /**
     * Creates a template with required information.
     * @param templateFactory the {@link org.acmsl.queryj.api.PerTableTemplateFactory} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param packageName the package name.
     * @param projectPackage the project's base package.
     * @param repository the repository name.
     * @param header the custom file header.
     * @param implementMarkerInterfaces whether to use marker interface or not.
     * @param jmx whether to include JMX support or not.
     * @param jndiLocation the JNDI path of the {@link javax.sql.DataSource}.
     * @param disableGenerationTimestamps whether to disable generation timestamps.
     * @param disableNotNullAnnotations whether to disable NotNull annotations.
     * @param disableCheckthreadAnnotations whether to disable checkthread.org annotations or not.
     * @param tableName the table name.
     * @param staticContents the table's static contents (optional).
     * @param parameters the parameter map.
     */
    @Nullable
    protected T createTemplate(
        @NotNull final TF templateFactory,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String packageName,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @Nullable final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        final boolean disableGenerationTimestamps,
        final boolean disableNotNullAnnotations,
        final boolean disableCheckthreadAnnotations,
        @NotNull final String tableName,
        @NotNull final List<Row<String>> staticContents,
        @SuppressWarnings("unused") @NotNull final QueryJCommand parameters)
      throws  QueryJBuildException
    {
        return
            templateFactory.createTemplate(
                metadataManager,
                customSqlProvider,
                decoratorFactory,
                packageName,
                projectPackage,
                repository,
                header,
                implementMarkerInterfaces,
                jmx,
                jndiLocation,
                disableGenerationTimestamps,
                disableNotNullAnnotations,
                disableCheckthreadAnnotations,
                tableName,
                staticContents);
    }

    /**
     * Checks whether given table contains static values or not.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return such information.
     */
    @SuppressWarnings("unused")
    protected boolean isStaticTable(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        return
            isStaticTable(
                tableName, metadataManager, MetaLanguageUtils.getInstance());
    }

    /**
     * Checks whether given table contains static values or not.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metaLanguageUtils the {@link MetaLanguageUtils} instance.
     * @return such information.
     */
    protected boolean isStaticTable(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetaLanguageUtils metaLanguageUtils)
    {
        return
            metaLanguageUtils.containsStaticValues(
                tableName, metadataManager);
    }

    /**
     * Checks whether given table contains static values or not.
     * @param parameters the parameter map.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the decorator factory.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    protected List<Row<String>> retrieveStaticContent(
        @NotNull final QueryJCommand parameters,
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @Nullable List<Row<String>> result =
            retrieveCachedStaticContent(parameters, tableName);

        if  (result == null)
        {
            try
            {
                result =
                    retrieveStaticContent(
                        tableName,
                        metadataManager,
                        decoratorFactory,
                        DAOTemplateUtils.getInstance());

                if (result == null)
                {
                    result = new ArrayList<Row<String>>(0);
                }
                storeCachedStaticContent(result, parameters, tableName);
            }
            catch  (@NotNull final SQLException sqlException)
            {
                @Nullable final Log t_Log =
                    UniqueLogFactory.getLog(
                        BasePerTableTemplateBuildHandler.class);
                
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Error retrieving static content for " + tableName,
                        sqlException);
                }
            }
        }

        return result;
    }

    /**
     * Checks whether given table contains static values or not.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the decorator factory.
     * @param daoTemplateUtils the {@link DAOTemplateUtils} instance.
     * @return such information.
     * @throws SQLException if the operation fails.
     */
    @Nullable
    protected List<Row<String>> retrieveStaticContent(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils)
      throws  SQLException
    {
        return
            daoTemplateUtils.queryContents(
                tableName, metadataManager, decoratorFactory);
    }

    /**
     * Retrieves the cached static content for given table.
     * @param parameters the parameter map.
     * @param tableName the table name.
     * @return such content, if present.
     */
    @Nullable
    protected List<Row<String>> retrieveCachedStaticContent(
        @NotNull final QueryJCommand parameters, @NotNull final String tableName)
    {
        return new QueryJCommandWrapper<List<Row<String>>>(parameters).getSetting(buildStaticContentKey(tableName));
    }

    /**
     * Stores the static content of a concrete table.
     * @param contents the contents to cache.
     * @param parameters the parameters.
     * @param tableName the table name.
     */
    @SuppressWarnings("unchecked")
    protected void storeCachedStaticContent(
        @NotNull final List<Row<String>> contents,
        @NotNull final QueryJCommand parameters,
        @NotNull final String tableName)
    {
        new QueryJCommandWrapper<List<Row<String>>>(parameters).setSetting(buildStaticContentKey(tableName), contents);
    }

    /**
     * Builds a key for storing the static content for given table.
     * @param tableName the table name.
     * @return such key.
     */
    @NotNull
    protected String buildStaticContentKey(@NotNull final String tableName)
    {
        return "..static-contents-for-table-" + tableName + "..";
    }
}
