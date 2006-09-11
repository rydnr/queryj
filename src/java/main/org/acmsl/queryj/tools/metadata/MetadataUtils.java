//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile: $
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
import org.acmsl.queryj.tools.metadata.CachingAttributeDecorator;
import org.acmsl.queryj.tools.metadata.CachingForeignKeyDecorator;
import org.acmsl.queryj.tools.metadata.DecorationUtils;
import org.acmsl.queryj.tools.metadata.ForeignKeyDecorator;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
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
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class MetadataUtils
    implements Singleton,
               Utils
{
    /**
     * An empty <code>String</code> array.
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * An empty <code>ForeignKey</code> array.
     */
    public static final ForeignKey[] EMPTY_FOREIGNKEY_ARRAY = 
        new ForeignKey[0];

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
    protected MetadataUtils() {};

    /**
     * Retrieves a <code>MetadataUtils</code> instance.
     * @return such instance.
     */
    public static MetadataUtils getInstance()
    {
        return MetadataUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the primary key attributes.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the collection of attributes participating in the primary key.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    public Collection retrievePrimaryKeyAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
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
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the collection of attributes not participating in the primary
     * key.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    public Collection retrieveNonPrimaryKeyAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
    {
        Collection t_cNonPkNames = new ArrayList();

        String[] t_astrColumnNames =
            metadataManager.getColumnNames(tableName);

        int t_iLength =
            (t_astrColumnNames != null) ? t_astrColumnNames.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            if  (!metadataManager.isPartOfPrimaryKey(
                     tableName, t_astrColumnNames[t_iIndex]))
            {
                t_cNonPkNames.add(t_astrColumnNames[t_iIndex]);
            }
        }

        return
            buildAttributes(
                (String[]) t_cNonPkNames.toArray(EMPTY_STRING_ARRAY),
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Retrieves the foreign key attributes.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the foreign key attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    public Collection retrieveForeignKeyAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
    {
        Collection result = new ArrayList();

        String[] t_astrReferredTables =
            metadataManager.getReferredTables(tableName);

        String[][] t_aastrForeignKeys =
            metadataManager.getForeignKeys(tableName);

        int t_iLength =
            (t_aastrForeignKeys != null) ? t_aastrForeignKeys.length : 0;

        Collection t_cAttributes = null;
        ForeignKey t_CurrentFk = null;
        Attribute t_FirstAttribute = null;
        boolean t_bAllowsNullAsAWhole = false;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            t_cAttributes =
                buildAttributes(
                    t_aastrForeignKeys[t_iIndex],
                    tableName,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory);

            if  (   (t_cAttributes != null)
                 && (t_cAttributes.size() > 0))
            {
                t_bAllowsNullAsAWhole =
                    allowsNullAsAWhole(t_cAttributes);

                t_CurrentFk =
                    new CachingForeignKeyDecorator(
                        new ForeignKeyValueObject(
                            t_astrReferredTables[t_iIndex],
                            t_cAttributes,
                            tableName,
                            t_bAllowsNullAsAWhole));

                result.add(t_CurrentFk);
            }
        }

        return result;
    }

    /**
     * Retrieves the complete attribute list.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    public Collection retrieveAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
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
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the externally-managed attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    public Collection retrieveExternallyManagedAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
    {
        Collection t_cExternallyManagedAttributeNames = new ArrayList();

        String[] t_astrColumnNames =
            metadataManager.getColumnNames(tableName);

        int t_iLength =
            (t_astrColumnNames != null) ? t_astrColumnNames.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            if  (metadataManager.isManagedExternally(
                     tableName, t_astrColumnNames[t_iIndex]))
            {
                t_cExternallyManagedAttributeNames.add(
                    t_astrColumnNames[t_iIndex]);
            }
        }

        return
            buildAttributes(
                (String[])
                    t_cExternallyManagedAttributeNames.toArray(
                        EMPTY_STRING_ARRAY),
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Retrieves all but the externally-managed attributes.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return all but the externally-managed attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    public Collection retrieveAllButExternallyManagedAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
    {
        Collection t_cNonExternallyManagedAttributeNames = new ArrayList();

        String[] t_astrColumnNames =
            metadataManager.getColumnNames(tableName);

        int t_iLength =
            (t_astrColumnNames != null) ? t_astrColumnNames.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            if  (!metadataManager.isManagedExternally(
                     tableName, t_astrColumnNames[t_iIndex]))
            {
                t_cNonExternallyManagedAttributeNames.add(
                    t_astrColumnNames[t_iIndex]);
            }
        }

        return
            buildAttributes(
                (String[])
                    t_cNonExternallyManagedAttributeNames.toArray(
                        EMPTY_STRING_ARRAY),
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Retrieves the foreign key attributes.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the foreign key attributes (a list of attribute lists,
     * grouped by referred tables.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    public Collection retrieveForeignKeys(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
    {
        Collection result = new ArrayList();

        String[] t_astrReferredTables =
            metadataManager.getReferredTables(tableName);

        String[] t_astrReferredColumns = null;

        int t_iLength =
            (t_astrReferredTables != null) ? t_astrReferredTables.length : 0;

        Collection t_cCurrentForeignKey = null;

        String t_strReferredTable = null;

        for  (int t_iRefTableIndex = 0;
                  t_iRefTableIndex < t_iLength;
                  t_iRefTableIndex++)
        {
            t_strReferredTable =
                t_astrReferredTables[t_iRefTableIndex];

            String[][] t_aastrForeignKeys =
                metadataManager.getForeignKeys(tableName, t_strReferredTable);

            int t_iFkLength =
                (t_aastrForeignKeys != null) ? t_aastrForeignKeys.length : 0;

            for  (int t_iIndex = 0; t_iIndex < t_iFkLength; t_iIndex++)
            {
                t_cCurrentForeignKey =
                    buildAttributes(
                        t_aastrForeignKeys[t_iIndex],
                        t_strReferredTable,
                        (    (metadataManager != null)
                          && (metadataManager.allowsNull(
                                  t_strReferredTable, t_astrReferredColumns))
                         ?  Boolean.TRUE : Boolean.FALSE),
                        metadataManager,
                        metadataTypeManager,
                        decoratorFactory);

                // Note: 'result' contains a list of lists.
                result.add(t_cCurrentForeignKey);

                t_cCurrentForeignKey = null;
            }
        }

        return result;
    }

    /**
     * Retrieves the referring keys.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the foreign keys of other tables pointing
     * to this one:
     * a map of referringTableName -> ForeignKey[].
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    public Map retrieveReferringKeys(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
    {
        Map result = new HashMap();

        String[] t_astrReferringTables =
            metadataManager.getReferringTables(tableName);

        int t_iLength =
            (t_astrReferringTables != null) ? t_astrReferringTables.length : 0;

        String t_strReferringTable;

        for  (int t_iRefTableIndex = 0;
                  t_iRefTableIndex < t_iLength;
                  t_iRefTableIndex++)
        {
            t_strReferringTable = t_astrReferringTables[t_iRefTableIndex];

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
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the attribute collection.
     * @precondition columnNames != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    public Collection buildAttributes(
        final String[] columnNames,
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
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
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the attribute collection.
     * @precondition columnNames != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    public Collection buildAttributes(
        final String[] columnNames,
        final String tableName,
        final boolean allowsNullAsAWhole,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
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
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the attribute collection.
     * @precondition columnNames != null
     * @precondition columnValues != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    public Collection buildAttributes(
        final String[] columnNames,
        final String[] columnValues,
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
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
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the attribute collection.
     * @precondition columnNames != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    public Collection buildAttributes(
        final String[] columnNames,
        final String tableName,
        final Boolean allowsNullAsAWhole,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
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
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the attribute collection.
     * @precondition columnNames != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    public Collection buildAttributes(
        final String[] columnNames,
        final String[] columnValues,
        final String tableName,
        final Boolean allowsNullAsAWhole,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
    {
        Collection result = new ArrayList();

        int t_iLength = (columnNames != null) ? columnNames.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            int t_iType =
                metadataManager.getColumnType(
                    tableName, columnNames[t_iIndex]);

            String t_strNativeType =
                metadataTypeManager.getNativeType(t_iType);

            boolean t_bAllowsNull = false;

            if  (allowsNullAsAWhole != null)
            {
                t_bAllowsNull = allowsNullAsAWhole.booleanValue();
            }
            else
            {
                t_bAllowsNull =
                    metadataManager.allowsNull(
                        tableName, columnNames[t_iIndex]);
            }

            String t_strFieldType =
                metadataTypeManager.getFieldType(t_iType, t_bAllowsNull);

            boolean t_bManagedExternally =
                metadataManager.isManagedExternally(
                    tableName, columnNames[t_iIndex]);

            result.add(
                decoratorFactory.createDecorator(
                    new AttributeValueObject(
                        columnNames[t_iIndex],
                        t_iType,
                        t_strNativeType,
                        t_strFieldType,
                        tableName,
                        t_bManagedExternally,
                        t_bAllowsNull,
                        columnValues[t_iIndex]),
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
    public boolean allowsNullAsAWhole(final Collection attributes)
    {
        boolean result = false;

        if  (attributes != null)
        {
            Iterator t_itAttributes = attributes.iterator();

            if  (t_itAttributes != null)
            {
                Attribute t_CurrentAttribute = null;

                while  (t_itAttributes.hasNext())
                {
                    t_CurrentAttribute = (Attribute) t_itAttributes.next();

                    if  (   (t_CurrentAttribute != null)
                         && (t_CurrentAttribute.getAllowsNull()))
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
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @return such information.
     * @precondition columnNames != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     */
    public boolean allowsNullAsAWhole(
        final String[] columnNames,
        final String tableName,
        final MetadataManager metadataManager)
    {
        boolean result = false;

        int t_iLength = (columnNames != null) ? columnNames.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            result =
                metadataManager.allowsNull(
                    tableName, columnNames[t_iIndex]);

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
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the foreign keys.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    public ForeignKey[] retrieveForeignKeys(
        final String tableName,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
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
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the foreign keys.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    protected ForeignKey[] retrieveFks(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
    {
        Collection result = new ArrayList();

        String[] t_astrReferredTables =
            metadataManager.getReferredTables(tableName);

        int t_iLength =
            (t_astrReferredTables != null) ? t_astrReferredTables.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            result.addAll(
                Arrays.asList(
                    retrieveForeignKeys(
                        tableName,
                        t_astrReferredTables[t_iIndex],
                        metadataManager,
                        metadataTypeManager,
                        decoratorFactory)));
        }

        return (ForeignKey[]) result.toArray(EMPTY_FOREIGNKEY_ARRAY);
    }

    /**
     * Builds the foreign keys pointing from one table to another.
     * @param sourceTableName the name of the source table.
     * @param targetTableName the name of the target table..
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition sourceTableName != null
     * @precondition targetTableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    protected ForeignKey[] retrieveForeignKeys(
        final String sourceTableName,
        final String targetTableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
    {
        Collection result = new ArrayList();

        String[][] t_aastrForeignKeys =
            metadataManager.getForeignKeys(
                sourceTableName, targetTableName);

        int t_iFkCount =
            (t_aastrForeignKeys != null) ? t_aastrForeignKeys.length : 0;

        for  (int t_iFkIndex = 0; t_iFkIndex < t_iFkCount; t_iFkIndex++)
        {
            boolean t_bAllowsNullAsAWhole =
                allowsNullAsAWhole(
                    t_aastrForeignKeys[t_iFkIndex],
                    sourceTableName,
                    metadataManager);

            result.add(
                decoratorFactory.createDecorator(
                    sourceTableName,
                    buildAttributes(
                        t_aastrForeignKeys[t_iFkIndex],
                        sourceTableName,
                        t_bAllowsNullAsAWhole,
                        metadataManager,
                        metadataTypeManager,
                        decoratorFactory),
                    targetTableName,
                    t_bAllowsNullAsAWhole));
        }

        return (ForeignKey[]) result.toArray(EMPTY_FOREIGNKEY_ARRAY);
    }

    /**
     * Retrieves the LOB attributes.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    public Collection retrieveLobAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
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
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    public Collection retrieveAllButLobAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
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
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    protected Collection retrieveLobAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final boolean includeLob,
        final DecoratorFactory decoratorFactory)
    {
        Collection t_cLobAttributeNames = new ArrayList();

        String[] t_astrColumnNames =
            metadataManager.getColumnNames(tableName);

        int t_iLength =
            (t_astrColumnNames != null) ? t_astrColumnNames.length : 0;

        boolean t_bCheck = false;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            t_bCheck =
                metadataTypeManager.isClob(
                    metadataManager.getColumnType(
                        tableName, t_astrColumnNames[t_iIndex]));

            t_bCheck = includeLob ? t_bCheck : !t_bCheck;

            if  (t_bCheck)
            {
                t_cLobAttributeNames.add(t_astrColumnNames[t_iIndex]);
            }
        }

        return
            buildAttributes(
                (String[]) t_cLobAttributeNames.toArray(EMPTY_STRING_ARRAY),
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Retrieves the large-object-block properties.
     * @param properties the properties.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     instance.
     * @return such collection.
     * @precondition properties != null
     * @precondition metadataTypeManager != null
     */
    public Collection filterLobProperties(
        final Collection properties,
        final MetadataTypeManager metadataTypeManager)
    {
        Collection result = new ArrayList();

        Iterator t_itPropertyIterator =
            (properties != null) ? properties.iterator() : null;

        if  (t_itPropertyIterator != null)
        {
            Property t_Property;

            boolean t_bLob;

            while  (t_itPropertyIterator.hasNext())
            {
                t_Property = (Property) t_itPropertyIterator.next();

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
        final Collection attributes,
        final String attributeName,
        final String tableName)
    {
        boolean result = false;

        Iterator t_Iterator =
            (attributes != null) ? attributes.iterator() : null;

        if  (t_Iterator != null)
        {
            Object t_CurrentItem = null;

            while  (t_Iterator.hasNext())
            {
                t_CurrentItem = t_Iterator.next();

                if  (   (t_CurrentItem != null)
                     && (t_CurrentItem instanceof Attribute))
                {
                    result =
                        match(
                            (Attribute) t_CurrentItem, attributeName, tableName);

                    if  (result)
                    {
                        break;
                    }
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
        final Attribute attribute,
        final String name,
        final String tableName)
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
