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
 * Filename: TableTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a table template using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TableTemplateFactory;
import org.acmsl.queryj.tools.templates.TableTemplateGenerator;

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
import java.util.Map;

/**
 * Builds a table template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TableTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The table templates attribute name.
     */
    public static final String TABLE_TEMPLATES = "table.templates";

    /**
     * The table names attribute name.
     */
    public static final String TABLE_NAMES = "table.names";

    /**
     * Creates a TableTemplateBuildHandler.
     */
    public TableTemplateBuildHandler() {};

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
            retrieveCustomSqlProvider(parameters),
            TableTemplateGenerator.getInstance(),
            retrieveProjectPackage(parameters),
            retrievePackage(engineName, parameters),
            retrieveTableRepositoryName(parameters),
            retrieveHeader(parameters),
            retrieveImplementMarkerInterfaces(parameters));
    }

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
     * @param packageName the package name.
     * @param repository the repository.
     * @param header the header.
     * @param implementMarkerInterfaces whether to implement marker
     * interfaces.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition templateFactory != null
     * @precondition projectPackage != null
     * @precondition packageName != null
     * @precondition repository != null
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote,
        @NotNull final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        @NotNull final TableTemplateFactory templateFactory,
        final String projectPackage,
        final String packageName,
        final String repository,
        final String header,
        final boolean implementMarkerInterfaces)
      throws  QueryJBuildException
    {
        String[] t_astrTableNames = metadataManager.getTableNames();

        @Nullable String[] t_astrColumnNames = null;

        MetadataTypeManager t_MetadataTypeManager =
            metadataManager.getMetadataTypeManager();

        int t_iColumnType = -1;

        int t_iCount =
            (t_astrTableNames != null) ? t_astrTableNames.length : 0;

        if  (t_iCount > 0)
        {
            @NotNull TableTemplate[] t_aTableTemplates =
                new TableTemplate[t_iCount];

            for  (int t_iTableIndex = 0;
                      t_iTableIndex < t_iCount;
                      t_iTableIndex++)
            {
                t_aTableTemplates[t_iTableIndex] =
                    createTemplate(
                        templateFactory,
                        t_astrTableNames[t_iTableIndex],
                        metadataManager,
                        customSqlProvider,
                        header,
                        packageName,
                        engineName,
                        engineVersion,
                        quote,
                        projectPackage,
                        repository,
                        implementMarkerInterfaces);

                t_astrColumnNames =
                    metadataManager.getColumnNames(
                        t_astrTableNames[t_iTableIndex]);

                int t_iColumnCount =
                    (t_astrColumnNames != null)
                    ?  t_astrColumnNames.length
                    :  0;

                if  (t_iColumnCount > 0)
                {
                    for  (int t_iColumnIndex = 0;
                              t_iColumnIndex < t_iColumnCount;
                              t_iColumnIndex++)
                    {
                        t_aTableTemplates[t_iTableIndex].addField(
                            t_astrColumnNames[t_iColumnIndex]);

                        t_iColumnType =
                            metadataManager.getColumnType(
                                t_astrTableNames[t_iTableIndex],
                                t_astrColumnNames[t_iColumnIndex]);

                        t_aTableTemplates[t_iTableIndex].addFieldType(
                            t_astrColumnNames[t_iColumnIndex],
                            t_MetadataTypeManager.getQueryJFieldType(
                                t_iColumnType,
                                metadataManager.isBoolean(
                                    t_astrTableNames[t_iTableIndex],
                                    t_astrColumnNames[t_iColumnIndex])));
                    }
                }
            }

            storeTableNames(t_astrTableNames, parameters);
            storeTableTemplates(t_aTableTemplates, parameters);
        }
    }

    /**
     * Retrieves the table package name from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition parameters != null
     */
    protected String retrievePackage(
        final String engineName, @NotNull final Map parameters)
    {
        return retrievePackage(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the table package name from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        @NotNull final Map parameters, @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveTablePackage(
                retrieveProjectPackage(parameters));
    }

    /**
     * Stores the table name collection in given attribute map.
     * @param tableNames the table names.
     * @param parameters the parameter map.
     * @precondition tableNames != null
     * @precondition parameters != null
     */
    protected void storeTableNames(
        final String[] tableNames, @NotNull final Map parameters)
    {
        parameters.put(TABLE_NAMES, tableNames);
    }

    /**
     * Stores the table template collection in given attribute map.
     * @param tableTemplates the table templates.
     * @param parameters the parameter map.
     * @precondition tableTemplates != null
     * @precondition parameters != null
     */
    protected void storeTableTemplates(
        final TableTemplate[] tableTemplates, @NotNull final Map parameters)
    {
        parameters.put(TABLE_TEMPLATES, tableTemplates);
    }

    /**
     * Creates the template.
     * @param templateFactory the template factory.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param header the header.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param projectPackage the project package.
     * @param repository the repository name.
     * @param implementMarkerInterfaces whether to implement marker
     * interfaces.
     * @return such template.
     * @precondition templateFactory != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition header != null
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition projectPackage != null
     * @precondition repository != null
     */
    @NotNull
    protected TableTemplate createTemplate(
        @NotNull final TableTemplateFactory templateFactory,
        final String tableName,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final String header,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String projectPackage,
        final String repository,
        final boolean implementMarkerInterfaces)
    {
        return
            templateFactory.createTableTemplate(
                tableName,
                metadataManager,
                customSqlProvider,
                header,
                packageName,
                engineName,
                engineVersion,
                quote,
                projectPackage,
                repository,
                implementMarkerInterfaces);
    }
}
