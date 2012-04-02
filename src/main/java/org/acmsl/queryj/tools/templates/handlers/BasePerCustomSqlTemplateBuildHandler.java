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
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.QueryJCommand;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.SqlElement;
import org.acmsl.queryj.tools.metadata.AttributeDecorator;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerCustomSqlTemplate;
import org.acmsl.queryj.tools.templates.BasePerCustomSqlTemplateFactory;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
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
 * Builds all templates to generate sources for each custom SQL.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerCustomSqlTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty Sql array.
     */
    public static final SqlElement[] EMPTY_SQL_ARRAY = new SqlElement[0];

    /**
     * The key for storing the custom SQL in the parameter map.
     */
    public static final String CUSTOM_SQL = "..CustOMsqL:::";
    
    /**
     * Creates a <code>BasePerCustomSqlTemplateBuildHandler</code> instance.
     */
    public BasePerCustomSqlTemplateBuildHandler() {};

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
     * Builds the templates
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom SQL provider.
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
            retrieveProjectPackage(parameters),
            retrieveTableRepositoryName(parameters),
            retrieveCustomSql(
                parameters, customSqlProvider, metadataManager),
            retrieveHeader(parameters));
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
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition templateFactory != null
     * @precondition projectPackage != null
     * @precondition repository != null
     * @precondition sqlElements != null
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        @NotNull final BasePerCustomSqlTemplateFactory templateFactory,
        final String projectPackage,
        final String repository,
        @Nullable final SqlElement[] sqlElements,
        final String header)
      throws  QueryJBuildException
    {
        int t_iLength = (sqlElements != null) ? sqlElements.length : 0;
        
        @NotNull BasePerCustomSqlTemplate[] t_aTemplates =
            new BasePerCustomSqlTemplate[t_iLength];

        @Nullable SqlElement t_SqlElement = null;
            
        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++) 
        {
            t_SqlElement = sqlElements[t_iIndex];
                
            t_aTemplates[t_iIndex] =
                templateFactory.createTemplate(
                    t_SqlElement,
                    customSqlProvider,
                    metadataManager,
                    retrievePackage(t_SqlElement, engineName, parameters),
                    engineName,
                    engineVersion,
                    projectPackage,
                    repository,
                    header);
        }

        storeTemplates(t_aTemplates, parameters);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param customSql the custom SQL.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition customSql != null
     * @precondition parameters != null
     */
    @NotNull
    protected String retrievePackage(
        final SqlElement customSql,
        final String engineName,
        @NotNull final Map parameters)
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
        final SqlElement customSql,
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
        final BasePerCustomSqlTemplate[] templates, final Map parameters);

    /**
     * Retrieves the foreign keys.
     * @param parameters the parameter map.
     * @param customSqlProvider the custom SQL provider.
     * @param metadataManager the database metadata manager.
     * @return such templates.
     * @precondition parameters != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     */
    @NotNull
    protected SqlElement[] retrieveCustomSql(
        @NotNull final Map parameters,
        @NotNull final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager)
    {
        @NotNull SqlElement[] result = (SqlElement[]) parameters.get(CUSTOM_SQL);

        if  (result == null)
        {
            @NotNull Collection t_cCustomSql = new ArrayList();
            
            @NotNull SqlElement[] t_aSqlElements =
                retrieveCustomSqlElements(customSqlProvider, metadataManager);

            int t_iLength =
                (t_aSqlElements != null) ? t_aSqlElements.length : 0;
            
            for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
            {
                t_cCustomSql.add(t_aSqlElements[t_iIndex]);
            }

            result =
                (SqlElement[]) t_cCustomSql.toArray(EMPTY_SQL_ARRAY);
        }

        return result;
    }

    /**
     * Retrieves the custom SQL elements.
     * @param customSqlProvider the custom SQL provider.
     * @param metadataManager the database metadata manager.
     * @return such foreign keys.
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     */
    @NotNull
    protected SqlElement[] retrieveCustomSqlElements(
        @NotNull final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager)
    {
        @NotNull Collection t_cResult = new ArrayList();
        
        Collection t_cCollection = customSqlProvider.getCollection();

        if  (t_cCollection != null)
        {
            Iterator t_itElements = t_cCollection.iterator();
            
            if  (t_itElements != null)
            {
                @Nullable Object t_Item = null;

                while  (t_itElements.hasNext())
                {
                    t_Item = t_itElements.next();
                    
                    if  (t_Item instanceof SqlElement)
                    {
                        t_cResult.add(t_Item);
                    }
                }
            }
        }
        
        return (SqlElement[]) t_cResult.toArray(EMPTY_SQL_ARRAY);
    }
}
