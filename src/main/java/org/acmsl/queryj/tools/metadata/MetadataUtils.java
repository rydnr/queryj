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
 * Filename: MetadataUtils.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides some methods commonly-reused when working with
 *              metadata.
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.Property;
import org.acmsl.queryj.tools.metadata.vo.Attribute;
import org.acmsl.queryj.tools.metadata.vo.AttributeValueObject;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.metadata.vo.ForeignKeyValueObject;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Provides some methods commonly-used when working with metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class MetadataUtils
    implements Singleton,
               Utils
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class MetadataUtilsSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final MetadataUtils SINGLETON = new MetadataUtils();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected MetadataUtils() {}

    /**
     * Retrieves a {@link MetadataUtils} instance.
     * @return such instance.
     */
    @NotNull
    public static MetadataUtils getInstance()
    {
        return MetadataUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the primary key attributes.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the collection of attributes participating in the primary key.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    @NotNull
    public List<Attribute> retrievePrimaryKeyAttributes(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return
            buildAttributes(
                metadataManager.getPrimaryKey(tableName),
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Retrieves the non-primary key attributes.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the collection of attributes not participating in the primary
     * key.
     */
    @NotNull
    public List<Attribute> retrieveNonPrimaryKeyAttributes(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull Collection<String> t_cNonPkNames = new ArrayList<String>();

        @NotNull String[] t_astrColumnNames =
            metadataManager.getColumnNames(tableName);

        for  (String t_strColumnName: t_astrColumnNames)
        {
            if  (!metadataManager.isPartOfPrimaryKey(
                     tableName, t_strColumnName))
            {
                t_cNonPkNames.add(t_strColumnName);
            }
        }

        return
            buildAttributes(
                t_cNonPkNames.toArray(new String[t_cNonPkNames.size()]),
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Retrieves the foreign key attributes.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the foreign key attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    @NotNull
    public List<ForeignKey> retrieveForeignKeyAttributes(
        final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull List<ForeignKey> result = new ArrayList<ForeignKey>();

        @NotNull String[] t_astrReferredTables =
            metadataManager.getReferredTables(tableName);

        @Nullable String[][] t_aastrForeignKeys =
            metadataManager.getForeignKeys(tableName);

        int t_iLength =
            (t_aastrForeignKeys != null) ? t_aastrForeignKeys.length : 0;

        t_iLength =
            Math.min(
                t_astrReferredTables.length,
                t_iLength);

        @Nullable List<Attribute> t_lAttributes;
        @Nullable ForeignKey t_CurrentFk;
        boolean t_bAllowsNullAsAWhole;

        @Nullable String[] t_astrForeignKey;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            t_astrForeignKey =
                t_aastrForeignKeys[t_iIndex];

            if (t_astrForeignKey != null)
            {
                t_lAttributes =
                    buildAttributes(
                        t_astrForeignKey,
                        tableName,
                        metadataManager,
                        metadataTypeManager,
                        decoratorFactory);

                if  (t_lAttributes.size() > 0)
                {
                    t_bAllowsNullAsAWhole =
                        allowsNullAsAWhole(t_lAttributes);

                    t_CurrentFk =
                        new CachingForeignKeyDecorator(
                            new ForeignKeyValueObject(
                                t_astrReferredTables[t_iIndex],
                                t_lAttributes,
                                tableName,
                                t_bAllowsNullAsAWhole));

                    result.add(t_CurrentFk);
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the complete attribute list.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    @NotNull
    public List<Attribute> retrieveAttributes(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return
            buildAttributes(
                metadataManager.getColumnNames(tableName),
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Retrieves the externally-managed attributes.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the externally-managed attributes.
     */
    @NotNull
    public List<Attribute> retrieveExternallyManagedAttributes(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull Collection<String> t_cExternallyManagedAttributeNames =
            new ArrayList<String>();

        @NotNull String[] t_astrColumnNames =
            metadataManager.getColumnNames(tableName);

        for  (String t_strColumnName : t_astrColumnNames)
        {
            if  (metadataManager.isManagedExternally(tableName, t_strColumnName))
            {
                t_cExternallyManagedAttributeNames.add(t_strColumnName);
            }
        }

        return
            buildAttributes(
                t_cExternallyManagedAttributeNames.toArray(
                    new String[t_cExternallyManagedAttributeNames.size()]),
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Retrieves all but the externally-managed attributes.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return all but the externally-managed attributes.
     */
    @NotNull
    public List<Attribute> retrieveAllButExternallyManagedAttributes(
        final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull Collection<String> t_cNonExternallyManagedAttributeNames =
            new ArrayList<String>();

        @NotNull String[] t_astrColumnNames =
            metadataManager.getColumnNames(tableName);

        for  (String t_strColumnName : t_astrColumnNames)
        {
            if  (!metadataManager.isManagedExternally(tableName, t_strColumnName))
            {
                t_cNonExternallyManagedAttributeNames.add(t_strColumnName);
            }
        }

        return
            buildAttributes(
                t_cNonExternallyManagedAttributeNames.toArray(
                    new String[t_cNonExternallyManagedAttributeNames.size()]),
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Retrieves the foreign key attributes.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the foreign key attributes (a list of attribute lists,
     * grouped by referred tables.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<List<Attribute>> retrieveForeignKeys(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull List<List<Attribute>> result = new ArrayList<List<Attribute>>();

        @NotNull String[] t_astrReferredTables =
            metadataManager.getReferredTables(tableName);

        @Nullable String[] t_astrReferredColumns = null;

        @Nullable List<Attribute> t_cCurrentForeignKey;

        for  (String t_strReferredTable : t_astrReferredTables)
        {
            @NotNull String[][] t_aastrForeignKeys =
                metadataManager.getForeignKeys(tableName, t_strReferredTable);

            for  (String[] t_astrForeignKey : t_aastrForeignKeys)
            {
                t_cCurrentForeignKey =
                    buildAttributes(
                        t_astrForeignKey,
                        t_strReferredTable,
                        (metadataManager.allowsNull(
                            t_strReferredTable, t_astrReferredColumns)
                         ?  Boolean.TRUE : Boolean.FALSE),
                        metadataManager,
                        metadataTypeManager,
                        decoratorFactory);

                // Note: 'result' contains a list of lists.
                result.add(t_cCurrentForeignKey);
            }
        }

        return result;
    }

    /**
     * Retrieves the referring keys.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the foreign keys of other tables pointing
     * to this one:
     * a map of referringTableName -> ForeignKey[].
     */
    @NotNull
    public Map<String,ForeignKey[]> retrieveReferringKeys(
        final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull Map<String,ForeignKey[]> result = new HashMap<String,ForeignKey[]>();

        @NotNull String[] t_astrReferringTables =
            metadataManager.getReferringTables(tableName);

        for  (String t_strReferringTable : t_astrReferringTables)
        {
            result.put(
                t_strReferringTable,
                retrieveForeignKeys(
                    t_strReferringTable,
                    tableName,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory));
        }

        return result;
    }

    /**
     * Builds the attributes associated to given column names.
     * @param columnNames the column names.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the attribute collection.
     */
    @NotNull
    public List<Attribute> buildAttributes(
        @NotNull final String[] columnNames,
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return
            buildAttributes(
                columnNames,
                new String[columnNames.length],
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Builds the attributes associated to given column names.
     * @param columnNames the column names.
     * @param allowsNullAsAWhole whether the attributes allow null.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the attribute collection.
     * @precondition columnNames != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    @NotNull
    public List<Attribute> buildAttributes(
        @NotNull final String[] columnNames,
        @NotNull final String tableName,
        final boolean allowsNullAsAWhole,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return
            buildAttributes(
                columnNames,
                tableName,
                (allowsNullAsAWhole) ? Boolean.TRUE : Boolean.FALSE,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Builds the attributes associated to given column names.
     * @param columnNames the column names.
     * @param columnValues the column values.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the attribute collection.
     * @precondition columnNames != null
     * @precondition columnValues != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    @NotNull
    public List<Attribute> buildAttributes(
        @NotNull final String[] columnNames,
        @NotNull final String[] columnValues,
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return
            buildAttributes(
                columnNames,
                columnValues,
                tableName,
                null,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Builds the attributes associated to given column names.
     * @param columnNames the column names.
     * @param tableName the table name.
     * @param allowsNullAsAWhole whether given column names can be null
     * as a whole or not.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the attribute collection.
     * @precondition columnNames != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    @NotNull
    public List<Attribute> buildAttributes(
        @NotNull final String[] columnNames,
        @NotNull final String tableName,
        @Nullable final Boolean allowsNullAsAWhole,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return
            buildAttributes(
                columnNames,
                new String[columnNames.length],
                tableName,
                allowsNullAsAWhole,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Builds the attributes associated to given column names.
     * @param columnNames the column names.
     * @param columnValues the column values.
     * @param tableName the table name.
     * @param allowsNullAsAWhole whether given column names can be null
     * as a whole or not.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the attribute collection.
     * @precondition columnNames != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    @NotNull
    public List<Attribute> buildAttributes(
        @NotNull final String[] columnNames,
        @NotNull final String[] columnValues,
        @NotNull final String tableName,
        @Nullable final Boolean allowsNullAsAWhole,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull List<Attribute> result = new ArrayList<Attribute>();

        int t_iLength = columnNames.length;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            int t_iType =
                metadataManager.getColumnType(
                    tableName, columnNames[t_iIndex]);

            @Nullable String t_strNativeType =
                metadataTypeManager.getNativeType(t_iType);

            boolean t_bAllowsNull;

            if  (allowsNullAsAWhole != null)
            {
                t_bAllowsNull = allowsNullAsAWhole;
            }
            else
            {
                t_bAllowsNull =
                    metadataManager.allowsNull(
                        tableName, columnNames[t_iIndex]);
            }

            boolean t_bIsBool = metadataManager.isBoolean(tableName, columnNames[t_iIndex]);

            @SuppressWarnings("unused")
            String t_strFieldType =
                metadataTypeManager.getFieldType(t_iType, t_bAllowsNull, t_bIsBool);

            boolean t_bManagedExternally =
                metadataManager.isManagedExternally(
                    tableName, columnNames[t_iIndex]);

            result.add(
                decoratorFactory.createDecorator(
                    new AttributeValueObject(
                        columnNames[t_iIndex],
                        t_iType,
                        t_strNativeType,
                        tableName,
                        metadataManager.getColumnComment(tableName, columnNames[t_iIndex]),
                        t_bManagedExternally,
                        t_bAllowsNull,
                        columnValues[t_iIndex],
                        metadataManager.isReadOnly(tableName, columnNames[t_iIndex]),
                        metadataManager.isBoolean(tableName, columnNames[t_iIndex]),
                        metadataManager.getBooleanTrue(tableName, columnNames[t_iIndex]),
                        metadataManager.getBooleanFalse(tableName, columnNames[t_iIndex]),
                        metadataManager.getBooleanNull(tableName, columnNames[t_iIndex])),
                    metadataManager));
        }

        return result;
    }

    /**
     * Checks whether the foreign key allows null or not.
     * @param attributes the attributes.
     * @return such information.
     * @precondition attributes != null
     */
    public boolean allowsNullAsAWhole(@Nullable final Collection<Attribute> attributes)
    {
        boolean result = false;

        if  (attributes != null)
        {
            Iterator<Attribute> t_itAttributes = attributes.iterator();

            if  (t_itAttributes != null)
            {
                @Nullable Attribute t_CurrentAttribute;

                while  (t_itAttributes.hasNext())
                {
                    t_CurrentAttribute = t_itAttributes.next();

                    if  (   (t_CurrentAttribute != null)
                         && (t_CurrentAttribute.isNullable()))
                    {
                        result = true;
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Checks whether the foreign key allows null or not.
     * @param columnNames the column names.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return such information.
     * @precondition columnNames != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     */
    public boolean allowsNullAsAWhole(
        @NotNull final String[] columnNames,
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager)
    {
        boolean result = false;

        for  (String t_strColumnName : columnNames)
        {
            result =
                metadataManager.allowsNull(
                    tableName, t_strColumnName);

            if  (result)
            {
                break;
            }
        }

        return result;
    }

    /**
     * Retrieves the foreign keys starting at given table.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the foreign keys.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    @NotNull
    public ForeignKey[] retrieveForeignKeys(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return
            retrieveFks(
                tableName,
                metadataManager,
                metadataManager.getMetadataTypeManager(),
                decoratorFactory);
    }

    /**
     * Retrieves the foreign keys starting at given table.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the foreign keys.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    @NotNull
    protected ForeignKey[] retrieveFks(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull Collection<ForeignKey> result = new ArrayList<ForeignKey>();

        @NotNull String[] t_astrReferredTables =
            metadataManager.getReferredTables(tableName);

        for (String t_strReferredTable : t_astrReferredTables)
        {
            result.addAll(
                Arrays.asList(
                    retrieveForeignKeys(
                        tableName,
                        t_strReferredTable,
                        metadataManager,
                        metadataTypeManager,
                        decoratorFactory)));
        }

        return result.toArray(new ForeignKey[result.size()]);
    }

    /**
     * Builds the foreign keys pointing from one table to another.
     * @param sourceTableName the name of the source table.
     * @param targetTableName the name of the target table..
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @precondition sourceTableName != null
     * @precondition targetTableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    @NotNull
    protected ForeignKey[] retrieveForeignKeys(
        @NotNull final String sourceTableName,
        @NotNull final String targetTableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull Collection<ForeignKey> result = new ArrayList<ForeignKey>();

        @NotNull String[][] t_aastrForeignKeys =
            metadataManager.getForeignKeys(
                sourceTableName, targetTableName);

        for (String[] t_aastrForeignKey : t_aastrForeignKeys)
        {
            boolean t_bAllowsNullAsAWhole =
                allowsNullAsAWhole(
                    t_aastrForeignKey,
                    sourceTableName,
                    metadataManager);

            result.add(
                decoratorFactory.createDecorator(
                    sourceTableName,
                    buildAttributes(
                        t_aastrForeignKey,
                        sourceTableName,
                        t_bAllowsNullAsAWhole,
                        metadataManager,
                        metadataTypeManager,
                        decoratorFactory),
                    targetTableName,
                    t_bAllowsNullAsAWhole));
        }

        return result.toArray(new ForeignKey[result.size()]);
    }

    /**
     * Retrieves the LOB attributes.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> retrieveLobAttributes(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return
            retrieveLobAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                true,
                decoratorFactory);
    }

    /**
     * Retrieves all but the LOB attributes.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> retrieveAllButLobAttributes(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return
            retrieveLobAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                false,
                decoratorFactory);
    }

    /**
     * Retrieves attributes depending on their LOB nature.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param includeLob whether to include or exclude the LOB attributes.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return such attributes.
     */
    @NotNull
    protected List<Attribute> retrieveLobAttributes(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        final boolean includeLob,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull Collection<String> t_cLobAttributeNames = new ArrayList<String>();

        @NotNull String[] t_astrColumnNames =
            metadataManager.getColumnNames(tableName);

        boolean t_bCheck;

        for  (String t_strColumnName : t_astrColumnNames)
        {
            t_bCheck =
                metadataTypeManager.isClob(
                    metadataManager.getColumnType(
                        tableName, t_strColumnName));

            t_bCheck = includeLob ? t_bCheck : !t_bCheck;

            if  (t_bCheck)
            {
                t_cLobAttributeNames.add(t_strColumnName);
            }
        }

        return
            buildAttributes(
                t_cLobAttributeNames.toArray(new String[t_cLobAttributeNames.size()]),
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Retrieves the large-object-block properties.
     * @param properties the properties.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return such collection.
     */
    @NotNull
    public List<Property> filterLobProperties(
        @Nullable final Collection<Property> properties,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        @NotNull List<Property> result = new ArrayList<Property>();

        Iterator<Property> t_itPropertyIterator =
            (properties != null)
            ? properties.iterator() : new ArrayList<Property>().iterator();

        Property t_Property;

        boolean t_bLob;

        while  (t_itPropertyIterator.hasNext())
        {
            t_Property = t_itPropertyIterator.next();

            if  (t_Property != null)
            {
                t_bLob =
                    metadataTypeManager.isClob(t_Property.getType());

                if  (t_bLob)
                {
                    result.add(t_Property);
                }
            }
        }

        return result;
    }

    /**
     * Checks whether given collection contains an attribute belonging
     * to given table, with a concrete name.
     * @param attributes the attribute collection.
     * @param attributeName the attribute name.
     * @param tableName the table name.
     * @return <code>true</code> in such case.
     * @precondition attributes != null
     * @precondition attributeName != null
     * @precondition tableName != null
     */
    public boolean contain(
        @Nullable final Collection<Attribute> attributes,
        @NotNull final String attributeName,
        @NotNull final String tableName)
    {
        boolean result = false;

        Iterator<Attribute> t_Iterator =
            (attributes != null)
            ? attributes.iterator() : new ArrayList<Attribute>().iterator();

        @Nullable Attribute t_CurrentItem;

        while  (t_Iterator.hasNext())
        {
            t_CurrentItem = t_Iterator.next();

            if  (t_CurrentItem != null)
            {
                result = match(t_CurrentItem, attributeName, tableName);

                if  (result)
                {
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Checks whether given attribute match the name and belongs to given table.
     * @param attribute the attribute to match.
     * @param name the attribute name.
     * @param tableName the table name.
     * @return <code>true</code> in such case.
     * @precondition attribute != null
     * @precondition name != null
     * @precondition tableName != null
     */
    public boolean match(
        @Nullable final Attribute attribute,
        @NotNull final String name,
        @NotNull final String tableName)
    {
        boolean result = false;

        if  (   (attribute != null)
             && (name.equalsIgnoreCase(attribute.getName()))
             && (tableName.equalsIgnoreCase(attribute.getTableName())))
        {
            result = true;
        }

        return result;
    }
}
