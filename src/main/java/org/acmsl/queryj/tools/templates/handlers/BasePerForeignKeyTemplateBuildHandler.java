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
 * Filename: BasePerForeignKeyTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds all templates to generate sources for each
 *              foreign key.
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
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.vo.AttributeValueObject;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.metadata.vo.ForeignKeyValueObject;
import org.acmsl.queryj.tools.metadata.vo.Attribute;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerForeignKeyTemplate;
import org.acmsl.queryj.tools.templates.BasePerForeignKeyTemplateFactory;

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
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Builds all templates to generate sources for each foreign key.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerForeignKeyTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty ForeignKey array.
     */
    public static final ForeignKey[] EMPTY_FOREIGNKEY_ARRAY =
        new ForeignKey[0];

    /**
     * The key for storing the foreign keys in the parameter map.
     */
    public static final String FOREIGN_KEYS = "..FOreignKeY:::";
    
    /**
     * Creates a <code>BasePerForeignKeyTemplateBuildHandler</code> instance.
     */
    public BasePerForeignKeyTemplateBuildHandler() {}

    /**
     * Handles given information.
     *
     *
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
            retrieveTemplateFactory());
    }

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param metadataManager the database metadata manager.
     * @param templateFactory the {@link BasePerForeignKeyTemplateFactory} instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     * @precondition templateFactory != null
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote,
        final MetadataManager metadataManager,
        @NotNull final BasePerForeignKeyTemplateFactory templateFactory)
      throws  QueryJBuildException
    {
        buildTemplates(
            parameters,
            engineName,
            engineVersion,
            quote,
            retrieveMetadataManager(parameters),
            templateFactory,
            templateFactory.getDecoratorFactory());
    }

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param metadataManager the database metadata manager.
     * @param templateFactory the template factory.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote,
        @NotNull final MetadataManager metadataManager,
        @NotNull final BasePerForeignKeyTemplateFactory templateFactory,
        @NotNull final DecoratorFactory decoratorFactory)
      throws  QueryJBuildException
    {
        buildTemplates(
            parameters,
            engineName,
            engineVersion,
            quote,
            metadataManager,
            retrieveCustomSqlProvider(parameters),
            templateFactory,
            retrieveProjectPackage(parameters),
            retrieveTableRepositoryName(parameters),
            retrieveHeader(parameters),
            retrieveForeignKeys(
                parameters, metadataManager, decoratorFactory));
    }
    
    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @NotNull
    protected abstract BasePerForeignKeyTemplateFactory retrieveTemplateFactory();

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
     * @param header the header.
     * @param foreignKeys the foreign keys.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition templateFactory != null
     * @precondition projectPackage != null
     * @precondition repository != null
     * @precondition foreignKeys != null
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        @NotNull final BasePerForeignKeyTemplateFactory templateFactory,
        final String projectPackage,
        final String repository,
        final String header,
        @Nullable final ForeignKey[] foreignKeys)
      throws  QueryJBuildException
    {
        int t_iLength = (foreignKeys != null) ? foreignKeys.length : 0;
        
        @NotNull BasePerForeignKeyTemplate[] t_aTemplates =
            new BasePerForeignKeyTemplate[t_iLength];

        @Nullable ForeignKey t_ForeignKey = null;
            
        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++) 
        {
            t_ForeignKey = foreignKeys[t_iIndex];
                
            t_aTemplates[t_iIndex] =
                templateFactory.createTemplate(
                    t_ForeignKey,
                    metadataManager,
                    retrievePackage(
                        t_ForeignKey.getSourceTableName(),
                        engineName,
                        parameters),
                    engineName,
                    engineVersion,
                    quote,
                    projectPackage,
                    repository,
                    header);
        }

        storeTemplates(t_aTemplates, parameters);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition parameters != null
     */
    @NotNull
    protected String retrievePackage(
        final String tableName, final String engineName, @NotNull final Map parameters)
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
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    @NotNull
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
        final BasePerForeignKeyTemplate[] templates, final Map parameters);

    /**
     * Retrieves the foreign keys.
     * @param parameters the parameter map.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such templates.
     * for any reason.
     * @precondition parameters != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    @NotNull
    protected ForeignKey[] retrieveForeignKeys(
        @NotNull final Map parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @Nullable ForeignKey[] result = (ForeignKey[]) parameters.get(FOREIGN_KEYS);

        if  (result == null)
        {
            @NotNull Collection<ForeignKey> t_cForeignKeys = new ArrayList<ForeignKey>();
            
            @NotNull ForeignKey[] t_aFks =
                retrieveForeignKeys(metadataManager, decoratorFactory);

            for  (ForeignKey t_ForeignKey : t_aFks)
            {
                t_cForeignKeys.add(t_ForeignKey);
            }

            result = t_cForeignKeys.toArray(new ForeignKey[t_aFks.length]);
        }

        if (result == null)
        {
            result = EMPTY_FOREIGNKEY_ARRAY;
        }

        return result;
    }

    /**
     * Retrieves the foreign keys.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such foreign keys.
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    @NotNull
    protected ForeignKey[] retrieveForeignKeys(
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull ForeignKey[] result = EMPTY_FOREIGNKEY_ARRAY;

        @Nullable Collection<ForeignKey> t_cResult = null;

        String[] t_astrTableNames = metadataManager.getTableNames();

        int t_iTableLength =
            (t_astrTableNames != null) ? t_astrTableNames.length : 0;

        @Nullable String t_strSourceTable;
        @Nullable String[] t_astrReferredTables;
        int t_iReferredTableLength;
        @Nullable String[][] t_aastrForeignKeys;
        int t_iForeignKeyLength;
        @Nullable String t_strReferredTable;
        
        for  (int t_iTableIndex = 0;
                  t_iTableIndex < t_iTableLength;
                  t_iTableIndex++)
        {
            if (t_astrTableNames != null)
            {
                t_strSourceTable = t_astrTableNames[t_iTableIndex];

                if (t_strSourceTable != null)
                {
                    t_astrReferredTables =
                        metadataManager.getReferringTables(t_strSourceTable);

                    t_iReferredTableLength =
                        (t_astrReferredTables != null)
                        ?  t_astrReferredTables.length
                        :  0;

                    for  (int t_iReferredTableIndex = 0;
                              t_iReferredTableIndex < t_iReferredTableLength;
                              t_iReferredTableIndex++)
                    {
                        if (t_astrReferredTables != null)
                        {
                            t_strReferredTable =
                                t_astrReferredTables[t_iReferredTableIndex];

                            t_aastrForeignKeys =
                                metadataManager.getReferredKeys(
                                    t_strSourceTable, t_strReferredTable);

                            if  (t_cResult == null)
                            {
                                t_cResult = new ArrayList<ForeignKey>();
                            }

                            t_iForeignKeyLength =
                                (t_aastrForeignKeys != null)
                                ?  t_aastrForeignKeys.length
                                :  0;

                            for  (int t_iForeignKeyIndex = 0;
                                      t_iForeignKeyIndex < t_iForeignKeyLength;
                                      t_iForeignKeyIndex++)
                            {
                                if (t_aastrForeignKeys != null)
                                {
                                    t_cResult.add(
                                        buildForeignKey(
                                            t_aastrForeignKeys[t_iForeignKeyIndex],
                                            t_strSourceTable,
                                            t_strReferredTable,
                                            metadataManager,
                                            decoratorFactory));
                                }
                            }
                        }
                    }
                }
            }
        }

        if  (t_cResult != null)
        {
            result = t_cResult.toArray(new ForeignKey[t_cResult.size()]);
        }

        return result;
    }

    /**
     * Builds a foreign key.
     * @param attributes the attributes.
     * @param sourceTable the source table.
     * @param targetTable the target table.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the foreign key.
     * @precondition attributes != null
     * @precondition sourceTable != null
     * @precondition targetTable != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    @Nullable
    protected ForeignKey buildForeignKey(
        @Nullable final String[] attributes,
        final String sourceTable,
        final String targetTable,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @Nullable ForeignKey result;

        @NotNull List<Attribute> t_lAttributes = new ArrayList<Attribute>();

        int t_iAttributeLength = (attributes != null) ? attributes.length : 0;

        MetadataTypeManager t_TypeManager =
            metadataManager.getMetadataTypeManager();

        @Nullable Attribute t_Attribute;
        boolean t_bAllowsNull = false;

        for  (int t_iIndex = 0; t_iIndex < t_iAttributeLength; t_iIndex++)
        {
            if (attributes != null)
            {
                t_Attribute =
                    buildAttribute(
                        attributes[t_iIndex],
                        sourceTable,
                        metadataManager,
                        t_TypeManager,
                        decoratorFactory);

                if  (t_Attribute != null)
                {
                    t_lAttributes.add(t_Attribute);

                    if  (   (!t_bAllowsNull)
                         && (t_Attribute.isNullable()))
                    {
                        t_bAllowsNull = true;
                    }
                }
            }
        }

        result =
            new ForeignKeyValueObject(
                sourceTable, t_lAttributes, targetTable, t_bAllowsNull);

        return result;
    }

    /**
     * Builds an attribute.
     * @param attributeName the name.
     * @param tableName the source table.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the attribute.
     * @precondition attributeName != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    @Nullable
    protected Attribute buildAttribute(
        final String attributeName,
        final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @Nullable Attribute result;

        int t_iType = metadataManager.getColumnType(tableName, attributeName);

        @Nullable String t_strNativeType = metadataTypeManager.getNativeType(t_iType);

        boolean t_bAllowsNull =
            metadataManager.allowsNull(tableName, attributeName);

        boolean t_bIsBool =
            metadataManager.isBoolean(tableName, attributeName);

        String t_strFieldType =
            metadataTypeManager.getFieldType(t_iType, t_bAllowsNull, t_bIsBool);

        boolean t_bManagedExternally =
            metadataManager.isManagedExternally(tableName, attributeName);
        
        result =
            decoratorFactory.createDecorator(
                new AttributeValueObject(
                    attributeName,
                    t_iType,
                    t_strNativeType,
                    tableName,
                    metadataManager.getTableComment(tableName),
                    t_bManagedExternally,
                    t_bAllowsNull,
                    null, // value
                    metadataManager.isReadOnly(tableName, attributeName),
                    metadataManager.isBoolean(tableName, attributeName),
                    metadataManager.getBooleanTrue(tableName, attributeName),
                    metadataManager.getBooleanFalse(tableName, attributeName),
                    metadataManager.getBooleanNull(tableName, attributeName)),
                metadataManager);

        return result;
    }
}
