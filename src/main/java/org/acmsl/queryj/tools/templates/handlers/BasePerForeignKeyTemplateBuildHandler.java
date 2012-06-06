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
import org.acmsl.queryj.tools.templates.BasePerForeignKeyTemplateContext;
import org.acmsl.queryj.tools.templates.BasePerForeignKeyTemplateFactory;

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.tools.templates.BasePerTableTemplateContext;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds all templates to generate sources for each foreign key.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerForeignKeyTemplateBuildHandler
    <T extends BasePerForeignKeyTemplate<C>,
        TF extends BasePerForeignKeyTemplateFactory<T>, C extends BasePerForeignKeyTemplateContext>
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
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
     */
    @Override
    protected boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        @Nullable MetadataManager t_MetadataManager = retrieveMetadataManager(parameters);

        if (t_MetadataManager == null)
        {
            throw new QueryJBuildException("Error accessing database metadata");
        }

        buildTemplates(
            parameters,
            t_MetadataManager,
            retrieveTemplateFactory());

        return false;
    }

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param templateFactory the {@link BasePerForeignKeyTemplateFactory} instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final TF templateFactory)
      throws  QueryJBuildException
    {
        buildTemplates(
            parameters,
            metadataManager,
            templateFactory,
            templateFactory.getDecoratorFactory());
    }

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param templateFactory the template factory.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final TF templateFactory,
        @NotNull final DecoratorFactory decoratorFactory)
      throws  QueryJBuildException
    {
        buildTemplates(
            parameters,
            metadataManager,
            retrieveCustomSqlProvider(parameters),
            templateFactory,
            retrieveProjectPackage(parameters),
            retrieveTableRepositoryName(parameters),
            retrieveHeader(parameters),
            retrieveImplementMarkerInterfaces(parameters),
            retrieveJmx(parameters),
            retrieveJNDILocation(parameters),
            retrieveForeignKeys(
                parameters, metadataManager, decoratorFactory));
    }
    
    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @NotNull
    protected abstract TF retrieveTemplateFactory();

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom sql provider.
     * @param templateFactory the template factory.
     * @param projectPackage the project package.
     * @param repository the repository.
     * @param header the header.
     * @param implementMarkerInterfaces whether to implement marker interfaces.
     * @param jmx whether to include JMX support.
     * @param jndiLocation the JNDI location.
     * @param foreignKeys the foreign keys.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final TF templateFactory,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        @NotNull final ForeignKey[] foreignKeys)
      throws  QueryJBuildException
    {
        @NotNull List<T> t_lTemplates = new ArrayList<T>();

        for  (ForeignKey t_ForeignKey : foreignKeys)
        {
            t_lTemplates.add(
                templateFactory.createTemplate(
                    metadataManager,
                    customSqlProvider,
                    retrievePackage(
                        t_ForeignKey.getSourceTableName(),
                        metadataManager.getEngineName(),
                        parameters),
                    projectPackage,
                    repository,
                    header,
                    implementMarkerInterfaces,
                    jmx,
                    jndiLocation,
                    t_ForeignKey));
        }

        storeTemplates(t_lTemplates, parameters);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     */
    @NotNull
    protected String retrievePackage(
        @NotNull final String tableName, @NotNull final String engineName, @NotNull final Map parameters)
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
     */
    @NotNull
    protected abstract String retrievePackage(
        @NotNull final String tableName,
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils);

    /**
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     */
    protected abstract void storeTemplates(
        @NotNull final List<T> templates, @NotNull final Map parameters);

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

            t_cForeignKeys.addAll(Arrays.asList(t_aFks));

            result = t_cForeignKeys.toArray(new ForeignKey[t_aFks.length]);
        }

        if (result == null)
        {
            result = new ForeignKey[0];
        }

        return result;
    }

    /**
     * Retrieves the foreign keys.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such foreign keys.
     */
    @NotNull
    protected ForeignKey[] retrieveForeignKeys(
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull ForeignKey[] result = new ForeignKey[0];

        @Nullable Collection<ForeignKey> t_cResult = null;

        String[] t_astrTableNames = metadataManager.getTableNames();

        int t_iTableLength =
            (t_astrTableNames != null) ? t_astrTableNames.length : 0;

        @Nullable String t_strSourceTable;
        @Nullable String[] t_astrReferredTables;
        int t_iReferredTableLength;
        @Nullable String[][] t_aastrForeignKeys;
        int t_iForeignKeyLength;
        @NotNull String t_strReferredTable;
        
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
        @NotNull final String[] attributes,
        @NotNull final String sourceTable,
        @NotNull final String targetTable,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @Nullable ForeignKey result;

        @NotNull List<Attribute> t_lAttributes = new ArrayList<Attribute>();

        MetadataTypeManager t_TypeManager =
            metadataManager.getMetadataTypeManager();

        @Nullable Attribute t_Attribute;
        boolean t_bAllowsNull = false;

        for  (String t_strAttribute : attributes)
        {
            t_Attribute =
                buildAttribute(
                    t_strAttribute,
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
     */
    @Nullable
    @SuppressWarnings("unused")
    protected Attribute buildAttribute(
        @NotNull final String attributeName,
        @NotNull final String tableName,
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
                    1, // ordinal position
                    -1, // length
                    -1, // precision
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


    /**
     * Checks whether the table associated to given template contains foreign keys.
     * @param tableTemplate the table template.
     * @param metadataManager the database metadata manager.
     * @return <code>true</code> in such case.
     */
    @SuppressWarnings("unused")
    protected boolean containsForeignKeys(
        @NotNull final TableTemplate tableTemplate,
        @NotNull final MetadataManager metadataManager)
    {
        return containsForeignKeys(tableTemplate.getTemplateContext(), metadataManager);
    }

    /**
     * Checks whether the table associated to given template contains foreign keys.
     * @param context the {@link BasePerTableTemplateContext} instance.
     * @param metadataManager the database metadata manager.
     * @return <code>true</code> in such case.
     */
    protected boolean containsForeignKeys(
        @NotNull final BasePerTableTemplateContext context,
        @NotNull final MetadataManager metadataManager)
    {
        return
            metadataManager.containsForeignKeys(context.getTableName());
    }

    /**
     * Retrieves the simple foreign keys.
     * @param allFks the complete list of foreign keys.
     * @param tableName the table name.
     * @param metadataManager the database metadata manager.
     */
    @SuppressWarnings("unused,unchecked")
    @NotNull
    protected String[] retrieveSimpleFks(
        @NotNull final String[] allFks,
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager)
    {
        @NotNull Collection<String> t_cResult;

        @NotNull Map<String, String> t_mAux = new HashMap<String, String>();

        for  (String t_strFk: allFks)
        {
            // TODO: FIXME!!
            String t_strReferredTable =
                metadataManager.getReferredTable(
                    tableName, new String[] { t_strFk });

            if  (t_mAux.containsKey(t_strReferredTable))
            {
                t_mAux.remove(t_strReferredTable);
            }
            else
            {
                t_mAux.put(t_strReferredTable, t_strFk);
            }
        }

        t_cResult = t_mAux.values();

        return t_cResult.toArray(new String[t_cResult.size()]);
    }

    /**
     * Retrieves the tables referred by compound foreign keys.
     * @param tableName the table name.
     * @param metadataManager the database metadata manager.
     * @precondition tableName != null
     * @precondition metadataManager != null
     */
    @SuppressWarnings("unused,unchecked")
    @NotNull
    protected String[] retrieveReferredTablesByCompoundFks(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager)
    {
        @NotNull Collection<String> t_cResult = new ArrayList<String>();

        @NotNull String[] t_astrReferredTables =
            metadataManager.getReferredTables(tableName);

        for  (String t_strReferredTable: t_astrReferredTables)
        {
            String[][] t_astrForeignKeys =
                metadataManager.getForeignKeys(
                    tableName, t_strReferredTable);

            if  (   (t_astrForeignKeys != null)
                    && (t_astrForeignKeys.length > 0))
            {
                t_cResult.add(t_strReferredTable);
            }
        }

        return t_cResult.toArray(new String[t_cResult.size()]);
    }
}
