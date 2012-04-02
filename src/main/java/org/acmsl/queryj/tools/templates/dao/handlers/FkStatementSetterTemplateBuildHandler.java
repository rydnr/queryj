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
 * Filename: FkStatementSetterTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a FkStatementSetter template.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.logging.QueryJLog;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataUtils;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.FkStatementSetterTemplate;
import org.acmsl.queryj.tools.templates.dao.FkStatementSetterTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.FkStatementSetterTemplateGenerator;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Builds a FkStatementSetter template.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class FkStatementSetterTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty String array.
     */
    protected final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * A cached empty template array.
     */
    protected final FkStatementSetterTemplate[]
        EMPTY_FKSTATEMENTSETTERTEMPLATE_ARRAY =
            new FkStatementSetterTemplate[0];

    /**
     * Creates a <code>FkStatementSetterTemplateBuildHandler</code> instance.
     */
    public FkStatementSetterTemplateBuildHandler() {};

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
        return
            handle(
                parameters,
                retrieveDatabaseMetaData(parameters));
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param metaData the database metadata.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     */
    protected boolean handle(
        @NotNull final Map parameters, @NotNull final DatabaseMetaData metaData)
      throws  QueryJBuildException
    {
        boolean result = false;

        try
        {
            handle(
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

        return result;
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     */
    protected boolean handle(
        @NotNull final Map parameters,
        @NotNull final String engineName,
        final String engineVersion,
        final String quote)
      throws  QueryJBuildException
    {
        return
            handle(
                parameters,
                engineName,
                engineVersion,
                quote,
                FkStatementSetterTemplateGenerator.getInstance());
    }
    
    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     */
    protected boolean handle(
        @NotNull final Map parameters,
        @NotNull final String engineName,
        final String engineVersion,
        final String quote,
        @NotNull final FkStatementSetterTemplateFactory templateFactory)
      throws  QueryJBuildException
    {
        buildTemplates(
            parameters,
            engineName,
            engineVersion,
            quote,
            retrieveMetadataManager(parameters),
            retrieveProjectPackage(parameters),
            retrieveTableRepositoryName(parameters),
            retrieveHeader(parameters),
            templateFactory,
            templateFactory.getDecoratorFactory(),
            retrieveTableTemplates(parameters),
            MetadataUtils.getInstance());

        return false;
    }

    /**
     * Builds the <code>FkStatementSetter</code> templates.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote.
     * @param metadataManager the database metadata manager.
     * @param basePackageName the base package name.
     * @param repository the repository.
     * @param header the header.
     * @param templateFactory the template factory.
     * @param tableTemplates the table templates.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     * @precondition packageName != null
     * @precondition basePackageName != null
     * @precondition repositoryName != null
     * @precondition templateFactory != null
     * @precondition tableTemplates != null
     * @precondition metadataUtils != null
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        @NotNull final String engineName,
        final String engineVersion,
        final String quote,
        @NotNull final MetadataManager metadataManager,
        final String basePackageName,
        final String repositoryName,
        final String header,
        @NotNull final FkStatementSetterTemplateFactory templateFactory,
        @NotNull final DecoratorFactory decoratorFactory,
        @Nullable final TableTemplate[] tableTemplates,
        @NotNull final MetadataUtils metadataUtils)
      throws  QueryJBuildException
    {
        @NotNull Collection t_cTemplates = new ArrayList();

        int t_iLength = (tableTemplates != null) ? tableTemplates.length : 0;
        
        int t_iFkCount = 0;
        
        String t_strTableName;
        String t_strPackageName;
        ForeignKey[] t_aForeignKeys;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++) 
        {
            t_strTableName =
                tableTemplates[t_iIndex].getTableName();
                
            t_strPackageName =
                retrievePackage(engineName, t_strTableName, parameters);

            t_aForeignKeys =
                metadataUtils.retrieveForeignKeys(
                    t_strTableName, metadataManager, decoratorFactory);
                
            t_iFkCount =
                (t_aForeignKeys != null) ? t_aForeignKeys.length : 0;
                
            for  (int t_iFkIndex = 0; t_iFkIndex < t_iFkCount; t_iFkIndex++)
            {
                t_cTemplates.add(
                    templateFactory.createTemplate(
                        t_aForeignKeys[t_iFkIndex],
                        metadataManager,
                        t_strPackageName,
                        engineName,
                        engineVersion,
                        quote,
                        basePackageName,
                        repositoryName,
                        header));
            }
        }

        storeTemplates(
            (FkStatementSetterTemplate[])
            t_cTemplates.toArray(
                EMPTY_FKSTATEMENTSETTERTEMPLATE_ARRAY),
            parameters);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param engineName the engine name.
     * @param tableName the table name.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition tableName != null
     */
    protected String retrievePackage(
        @NotNull final String engineName,
        @NotNull final String tableName,
        @NotNull final Map parameters)
    {
        return
            retrievePackage(
                retrieveProjectPackage(parameters),
                engineName,
                tableName,
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param projectPackage the project package.
     * @param engineName the engine name.
     * @param tableName the table name.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition engineName != null
     * @precondition projectPackage != null
     * @precondition tableName != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String projectPackage,
        @NotNull final String engineName,
        @NotNull final String tableName,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveFkStatementSetterPackage(
                projectPackage,
                engineName,
                tableName);
    }

    /**
     * Retrieves the repository name.
     * @param parameters the parameters.
     * @return the repository's name.
     * @precondition parameters != null
     */
    @NotNull
    protected String retrieveTableRepositoryName(@NotNull final Map parameters)
    {
        return
            (String)
                parameters.get(
                    ParameterValidationHandler.REPOSITORY);
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @precondition parameters != null
     */
    @NotNull
    protected TableTemplate[] retrieveTableTemplates(@NotNull final Map parameters)
    {
        return
            (TableTemplate[])
                parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
    }

    /**
     * Stores the templates in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     * @precondition templates != null
     * @precondition parameters != null
     */
    protected void storeTemplates(
        final FkStatementSetterTemplate[] templates,
        @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.FK_STATEMENT_SETTER_TEMPLATES,
            templates);
    }

    /**
     * Checks whether the table associated to given template contains foreign keys.
     * @param tableTemplate the table template.
     * @param metadataManager the database metadata manager.
     * @return <code>true</code> in such case.
     * @precondition tableTemplate != null
     * @precondition metadataManager != null
     */
    protected boolean containsForeignKeys(
        @NotNull final TableTemplate tableTemplate,
        @NotNull final MetadataManager metadataManager)
    {
        return
            metadataManager.containsForeignKeys(tableTemplate.getTableName());
    }

    /**
     * Retrieves the simple foreign keys.
     * @param allFks the complete list of foreign keys.
     * @param tableName the table name.
     * @param metadataManager the database metadata manager.
     * @precondition allFks != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     */
    @NotNull
    protected String[] retrieveSimpleFks(
        @Nullable final String[] allFks,
        final String tableName,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable Collection t_cResult = null;

        @NotNull Map t_mAux = new HashMap();

        int t_iLength = (allFks != null) ? allFks.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            // TODO: FIXME!!
            String t_strReferredTable =
                metadataManager.getReferredTable(
                    tableName, new String[] {allFks[t_iIndex]});

            if  (t_mAux.containsKey(t_strReferredTable))
            {
                t_mAux.remove(t_strReferredTable);
            }
            else
            {
                t_mAux.put(t_strReferredTable, allFks[t_iIndex]);
            }
        }

        t_cResult = t_mAux.values();

        return (String[]) t_cResult.toArray(EMPTY_STRING_ARRAY);
    }

    /**
     * Retrieves the tables referred by compound foreign keys.
     * @param tableName the table name.
     * @param metadataManager the database metadata manager.
     * @precondition tableName != null
     * @precondition metadataManager != null
     */
    @NotNull
    protected String[] retrieveReferredTablesByCompoundFks(
        final String tableName,
        @NotNull final MetadataManager metadataManager)
    {
        @NotNull Collection t_cResult = new ArrayList();

        String[] t_astrReferredTables =
            metadataManager.getReferredTables(tableName);

        int t_iLength =
            (t_astrReferredTables != null) ? t_astrReferredTables.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            String[][] t_astrForeignKeys =
                metadataManager.getForeignKeys(
                    tableName, t_astrReferredTables[t_iIndex]);

            if  (   (t_astrForeignKeys != null)
                 && (t_astrForeignKeys.length > 0))
            {
                t_cResult.add(t_astrReferredTables[t_iIndex]);
            }
        }

        return (String[]) t_cResult.toArray(EMPTY_STRING_ARRAY);
    }
}
