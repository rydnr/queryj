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
 * Filename: BasePerTableTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a per-table templates using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.DAOTemplateUtils;
import org.acmsl.queryj.tools.templates.MetaLanguageUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/**
 * Builds a per-table template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerTableTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * An empty <code>BasePerTableTemplate</code> array.
     */
    protected static final BasePerTableTemplate[] EMPTY_BASEPERTABLETEMPLATE_ARRAY =
        new BasePerTableTemplate[0];

    /**
     * Creates a <code>BasePerTableTemplateBuildHandler</code> instance.
     */
    public BasePerTableTemplateBuildHandler() {};

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
        throws  QueryJBuildException
    {
        buildTemplate(parameters, retrieveDatabaseMetaData(parameters));

        return false;
    }

    /**
     * Builds the template.
     * @param parameters the parameters.
     * @param metaData the database metadata.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     */
    protected void buildTemplate(
        final Map parameters, final DatabaseMetaData metaData)
      throws  QueryJBuildException
    {
        try
        {
            buildTemplate(
                parameters,
                metaData.getDatabaseProductName(),
                retrieveDatabaseProductVersion(metaData),
                fixQuote(metaData.getIdentifierQuoteString()));
        }
        catch  (final SQLException sqlException)
        {
            throw
                new QueryJBuildException(
                    "Cannot build the table-specific template", sqlException);
        }
    }

    /**
     * Builds the template.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition quote != null
     */
    protected void buildTemplate(
        final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote)
      throws  QueryJBuildException
    {
        buildTemplate(
            parameters,
            engineName,
            engineVersion,
            quote,
            retrieveMetadataManager(parameters),
            retrieveCustomSqlProvider(parameters),
            retrieveTemplateFactory(),
            retrieveProjectPackage(parameters),
            retrieveTableRepositoryName(parameters),
            retrieveHeader(parameters),
            retrieveImplementMarkerInterfaces(parameters),
            retrieveTableTemplates(parameters));
    }

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    protected abstract BasePerTableTemplateFactory retrieveTemplateFactory();

    /**
     * Builds the template.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom sql provider.
     * @param templateFactory the template factory.
     * @param projectPackage the project package.
     * @param repository the repository.
     * @param header the header.
     * @param implementMarkerInterfaces whether to implement marker
     * interfaces.
     * @param tableTemplates the table templates.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition templateFactory != null
     * @precondition projectPackage != null
     * @precondition repository != null
     * @precondition tableTemplates != null
     */
    protected void buildTemplate(
        final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final BasePerTableTemplateFactory templateFactory,
        final String projectPackage,
        final String repository,
        final String header,
        final boolean implementMarkerInterfaces,
        final TableTemplate[] tableTemplates)
      throws  QueryJBuildException
    {
        int t_iLength = (tableTemplates != null) ? tableTemplates.length : 0;

        Collection t_cTemplates = new ArrayList();

        BasePerTableTemplate t_Template;

        String t_strTableName;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++) 
        {
            t_strTableName = tableTemplates[t_iIndex].getTableName();

            t_Template =
                createTemplate(
                    templateFactory,
                    t_strTableName,
                    metadataManager,
                    customSqlProvider,
                    retrievePackage(
                        t_strTableName, engineName, parameters),
                    engineName,
                    engineVersion,
                    quote,
                    projectPackage,
                    repository,
                    header,
                    implementMarkerInterfaces,
                    parameters);

            if  (t_Template != null)
            {
                t_cTemplates.add(t_Template);
            }
        }

        storeTemplates(
            (BasePerTableTemplate[])
                t_cTemplates.toArray(EMPTY_BASEPERTABLETEMPLATE_ARRAY),
            parameters);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws QueryJBuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrievePackage(
        final String tableName, final String engineName, final Map parameters)
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
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws QueryJBuildException if the package retrieval process if faulty.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected abstract String retrievePackage(
        final String tableName,
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
        final BasePerTableTemplate[] templates, final Map parameters);

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @throws QueryJBuildException if the templates cannot be stored for
     * any reason.
     * @precondition parameters != null
     */
    protected TableTemplate[] retrieveTableTemplates(final Map parameters)
        throws  QueryJBuildException
    {
        return
            (TableTemplate[])
                parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
    }

    /**
     * Creates the template with given parameters.
     * @param templateFactory the template factory.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param projectPackage the project package.
     * @param repository the repository name.
     * @param header the header.
     * @param implementMarkerInterfaces whether to implement marker
     * interfaces.
     * @param parameters the parameters.
     * @return the template.
     * @throws QueryJBuildException if the template cannot be created.
     * @precondition templateFactory != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition engineName != null
     * @precondition projectPackage != null
     * @precondition repository != null
     * @precondition parameters != null
     */
    protected BasePerTableTemplate createTemplate(
        final BasePerTableTemplateFactory templateFactory,
        final String tableName,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String projectPackage,
        final String repository,
        final String header,
        final boolean implementMarkerInterfaces,
        final Map parameters)
      throws  QueryJBuildException
    {
        return
            templateFactory.createTemplate(
                tableName,
                metadataManager,
                customSqlProvider,
                packageName,
                engineName,
                engineVersion,
                quote,
                projectPackage,
                repository,
                header,
                implementMarkerInterfaces);
    }

    /**
     * Checks whether given table contains static values or not.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     * @precondition tableName != null
     * @precondition metadataManager != null
     */
    protected boolean isStaticTable(
        final String tableName,
        final MetadataManager metadataManager)
    {
        return
            isStaticTable(
                tableName, metadataManager, MetaLanguageUtils.getInstance());
    }

    /**
     * Checks whether given table contains static values or not.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metaLanguageUtils the <code>MetaLanguageUtils</code> instance.
     * @return such information.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metaLanguageUtils != null
     */
    protected boolean isStaticTable(
        final String tableName,
        final MetadataManager metadataManager,
        final MetaLanguageUtils metaLanguageUtils)
    {
        return
            metaLanguageUtils.containsStaticValues(
                tableName, metadataManager);
    }

    /**
     * Checks whether given table contains static values or not.
     * @param parameters the parameter map.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the decorator factory.
     * @return such information.
     * @precondition parameters != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected Collection retrieveStaticContent(
        final Map parameters,
        final String tableName,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        Collection result =
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
                storeCachedStaticContent(result, parameters, tableName);
            }
            catch  (final SQLException sqlException)
            {
                Log t_Log =
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
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the decorator factory.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @return such information.
     * @throws SQLException if the operation fails.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     * @precondition daoTemplateUtils != null
     */
    protected Collection retrieveStaticContent(
        final String tableName,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory,
        final DAOTemplateUtils daoTemplateUtils)
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
     * @precondition parameters != null
     * @precondition tableName != null
     */
    protected Collection retrieveCachedStaticContent(
        final Map parameters, final String tableName)
    {
        return (Collection) parameters.get(buildStaticContentKey(tableName));
    }

    /**
     * Stores the static content of a concrete table.
     * @param contents the contents to cache.
     * @param parameters the parameters.
     * @param tableName the table name.
     * @precondition contents != null
     * @precondition parameters != null
     * @precondition tableName != null
     */
    protected void storeCachedStaticContent(
        final Collection contents,
        final Map parameters,
        final String tableName)
    {
        parameters.put(buildStaticContentKey(tableName), contents);
    }

    /**
     * Builds a key for storing the static content for given table.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildStaticContentKey(final String tableName)
    {
        return "..static-contents-for-table-" + tableName + "..";
    }
}
