//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
import org.acmsl.queryj.tools.metadata.CachingAttributeDecorator;
import org.acmsl.queryj.tools.metadata.CachingForeignKeyDecorator;
import org.acmsl.queryj.tools.metadata.DecorationUtils;
import org.acmsl.queryj.tools.metadata.ForeignKeyDecorator;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.vo.Attribute;
import org.acmsl.queryj.tools.metadata.vo.AttributeValueObject;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;
import org.apache.commons.logging.LogFactory;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
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
    public static final ForeignKey[] EMPTY_FOREIGNKEY_ARRAY = new ForeignKey[0];

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
    public List retrieveNonPrimaryKeyAttributes(
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

        String[][] t_aastrForeignKeys =
            metadataManager.getForeignKeys(tableName);

        int t_iLength =
            (t_aastrForeignKeys != null) ? t_aastrForeignKeys.length : 0;

        List t_lAttributes = null;
        Iterator t_itAttributeIterator = null;
        ForeignKey t_CurrentFk = null;
        Attribute t_FirstAttribute = null;
        boolean t_bAllowsNullAsAWhole = false;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            t_lAttributes =
                buildAttributes(
                    t_aastrForeignKeys[t_iIndex],
                    tableName,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory);

            if  (   (t_lAttributes != null)
                 && (t_lAttributes.size() > 0))
            {
                t_bAllowsNullAsAWhole =
                    allowsNullAsAWhole(t_lAttributes);

                t_itAttributeIterator = t_lAttributes.iterator();

                if  (   (t_itAttributeIterator != null)
                     && (t_itAttributeIterator.hasNext()))
                {
                    t_FirstAttribute =
                        (Attribute) t_itAttributeIterator.next();

                    if  (t_FirstAttribute != null)
                    {
                        t_CurrentFk =
                            new CachingForeignKeyDecorator(
                                t_FirstAttribute.getTableName(),
                                t_lAttributes,
                                tableName,
                                t_bAllowsNullAsAWhole);

                        result.add(t_CurrentFk);
                    }
                }
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
    public List retrieveAttributes(
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
    public List retrieveAllButExternallyManagedAttributes(
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

        if  (metadataManager != null)
        {
            String[] t_astrReferredTables =
                metadataManager.getReferredTables(tableName);

            String[] t_astrReferredColumns = null;

            int t_iLength =
                (t_astrReferredTables != null)
                ? t_astrReferredTables.length : 0;

            Collection t_cCurrentForeignKey = null;

            String t_strReferredTable = null;

            for  (int t_iRefTableIndex = 0;
                      t_iRefTableIndex < t_iLength;
                      t_iRefTableIndex++)
            {
                t_strReferredTable =
                    t_astrReferredTables[t_iRefTableIndex];

                String[][] t_aastrForeignKeys =
                    metadataManager.getForeignKeys(
                        t_strReferredTable, tableName);

                int t_iFkLength =
                    (t_aastrForeignKeys != null)
                    ? t_aastrForeignKeys.length : 0;

                for  (int t_iIndex = 0; t_iIndex < t_iFkLength; t_iIndex++)
                {
                    t_cCurrentForeignKey =
                        buildAttributes(
                            t_aastrForeignKeys[t_iIndex],
                            t_strReferredTable,
                            metadataManager.allowsNull(
                                t_strReferredTable, t_astrReferredColumns)
                            ?  Boolean.TRUE : Boolean.FALSE,
                            metadataManager,
                            metadataTypeManager,
                            decoratorFactory);

                    // Note: 'result' contains a list of lists.
                    result.add(t_cCurrentForeignKey);

                    t_cCurrentForeignKey = null;
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the refering keys.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the foreign keys of other tables pointing
     * to this one:
     * a map of "fk_"referringTableName -> foreign_keys (list of attribute
     * lists).
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    public Map retrieveReferingKeys(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
    {
        Map result = new HashMap();

        String[] t_astrReferingTables =
            metadataManager.getReferingTables(tableName);

        String[][] t_aastrReferingColumns;

        int t_iLength =
            (t_astrReferingTables != null) ? t_astrReferingTables.length : 0;

        Collection t_cReferingFks = null;

        Collection t_cCurrentForeignKey = null;

        String t_strReferingTable = null;

        for  (int t_iRefTableIndex = 0;
                  t_iRefTableIndex < t_iLength;
                  t_iRefTableIndex++)
        {
            t_cReferingFks = new ArrayList();

            t_strReferingTable =
                t_astrReferingTables[t_iRefTableIndex];

            t_aastrReferingColumns =
                metadataManager.getForeignKeys(
                    t_strReferingTable, tableName);

            int t_iFkCount =
                (t_aastrReferingColumns != null)
                ?  t_aastrReferingColumns.length
                :  0;

            for  (int t_iFk = 0; t_iFk < t_iFkCount; t_iFk++)
            {
                t_cCurrentForeignKey =
                    buildAttributes(
                        t_aastrReferingColumns[t_iFk],
                        t_strReferingTable,
                        (metadataManager.allowsNull(
                            t_strReferingTable,
                            t_aastrReferingColumns[t_iFk])
                         ?  Boolean.TRUE : Boolean.FALSE),
                        metadataManager,
                        metadataTypeManager,
                        decoratorFactory);

                // Note: 't_cReferingFks' contains a list of lists.
                t_cReferingFks.add(t_cCurrentForeignKey);

                t_cCurrentForeignKey = null;
            }

            result.put(t_strReferingTable, t_cReferingFks);
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
    public List buildAttributes(
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
    public List buildAttributes(
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
    public List buildAttributes(
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
    public List buildAttributes(
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
     * @param columnNames the columns' names.
     * @param columnValues the columns' values.
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
    public List buildAttributes(
        final String[] columnNames,
        final String[] columnValues,
        final String tableName,
        final Boolean allowsNullAsAWhole,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
    {
        List result = new ArrayList();

        int t_iLength = (columnNames != null) ? columnNames.length : 0;

        String t_strColumnName;

        int t_iType;
        String t_strNativeType;
        boolean t_bAllowsNull;
        String t_strColumnComment;
        String t_strFieldType;
        boolean t_bManagedExternally;
        boolean t_bReadOnly;
        boolean t_bIsBool;
        String t_strBooleanTrue;
        String t_strBooleanFalse;
        String t_strBooleanNull;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            t_strColumnName = columnNames[t_iIndex];

            t_iType =
                metadataManager.getColumnType(
                    tableName, t_strColumnName);

            t_strNativeType =
                metadataTypeManager.getNativeType(t_iType);
            
            t_bAllowsNull = false;

            if  (allowsNullAsAWhole != null)
            {
                t_bAllowsNull = allowsNullAsAWhole.booleanValue();
            }
            else
            {
                t_bAllowsNull =
                    metadataManager.allowsNull(
                        tableName, t_strColumnName);
            }

            t_strColumnComment =
                metadataManager.getColumnComment(
                    tableName, t_strColumnName);

            t_bIsBool =
                metadataManager.isBoolean(
                    tableName, t_strColumnName);

            t_strFieldType =
                metadataTypeManager.getFieldType(
                    t_iType, t_bAllowsNull, t_bIsBool);

            t_bManagedExternally =
                metadataManager.isManagedExternally(
                    tableName, t_strColumnName);

            t_bReadOnly =
                metadataManager.isReadOnly(
                    tableName, t_strColumnName);

            t_strBooleanTrue =
                metadataManager.getBooleanTrue(
                    tableName, t_strColumnName);

            t_strBooleanFalse =
                metadataManager.getBooleanFalse(
                    tableName, t_strColumnName);

            t_strBooleanNull =
                metadataManager.getBooleanNull(
                    tableName, t_strColumnName);

            result.add(
                decoratorFactory.createDecorator(
                    new AttributeValueObject(
                        t_strColumnName,
                        t_iType,
                        t_strNativeType,
                        t_strFieldType,
                        tableName,
                        t_strColumnComment,
                        t_bManagedExternally,
                        t_bAllowsNull,
                        columnValues[t_iIndex],
                        t_bReadOnly,
                        t_bIsBool,
                        t_strBooleanTrue,
                        t_strBooleanFalse,
                        t_strBooleanNull),
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
            String[][] t_aastrForeignKeys =
                metadataManager.getForeignKeys(
                    tableName, t_astrReferredTables[t_iIndex]);

            int t_iFkCount =
                (t_aastrForeignKeys != null) ? t_aastrForeignKeys.length : 0;

            for  (int t_iFkIndex = 0; t_iFkIndex < t_iFkCount; t_iFkIndex++)
            {
                boolean t_bAllowsNullAsAWhole =
                    allowsNullAsAWhole(
                        t_aastrForeignKeys[t_iFkIndex],
                        tableName,
                        metadataManager);

                result.add(
                    new CachingForeignKeyDecorator(
                        tableName,
                        buildAttributes(
                            t_aastrForeignKeys[t_iFkIndex],
                            tableName,
                            t_bAllowsNullAsAWhole,
                            metadataManager,
                            metadataTypeManager,
                            decoratorFactory),
                        t_astrReferredTables[t_iIndex],
                        t_bAllowsNullAsAWhole));
            }
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
    public List retrieveAllButLobAttributes(
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
    public List retrieveAllNonPkButLobAttributes(
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
                false,
                decoratorFactory);
    }

    /**
     * Retrieves attributes depending on their LOB nature.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param includeLob whether to include or exclude the LOB attributes.
     * @param includePks whether to include or exclude the Primary Key attributes.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    protected List retrieveLobAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final boolean includeLob,
        final boolean includePks,
        final DecoratorFactory decoratorFactory)
    {
        List t_lLobAttributeNames = new ArrayList();

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
                if(includePks)
                {
                    t_lLobAttributeNames.add(t_astrColumnNames[t_iIndex]);
                }
                else
                {
                    
                    if(!metadataManager.isPartOfPrimaryKey(
                            tableName, t_astrColumnNames[t_iIndex]))
                    {
                        t_lLobAttributeNames.add(t_astrColumnNames[t_iIndex]);
                    }
                }
            }
            
        }

        return
            buildAttributes(
                (String[]) t_lLobAttributeNames.toArray(EMPTY_STRING_ARRAY),
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
