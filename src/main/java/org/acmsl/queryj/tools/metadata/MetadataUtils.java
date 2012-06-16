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
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.metadata.vo.Table;

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
     * @return the collection of attributes participating in the primary key.
     */
    @NotNull
    public List<Attribute> retrievePrimaryKeyAttributes(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        List<Attribute> result;

        Table t_Table =
            metadataManager.getTableDAO().findByName(tableName, null, null);

        if (t_Table != null)
        {
            result = t_Table.getPrimaryKey();
        }
        else
        {
            result = new ArrayList<Attribute>(0);
        }

        return result;
    }

    /**
     * Retrieves the non-primary key attributes.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the collection of attributes not participating in the primary
     * key.
     */
    @NotNull
    public List<Attribute> retrieveNonPrimaryKeyAttributes(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<Attribute> result = null;

        @Nullable Table t_Table = metadataManager.getTableDAO().findByName(tableName, null, null);

        if (t_Table != null)
        {
            List<Attribute> t_lAttributes = t_Table.getAttributes();

            List<Attribute> t_lPrimaryKey = t_Table.getPrimaryKey();

            result = new ArrayList<Attribute>(Math.max(t_lAttributes.size() - t_lPrimaryKey.size(), 0));

            result.addAll(t_lAttributes);
            result.removeAll(t_lPrimaryKey);
        }

        if (result == null)
        {
            result = new ArrayList<Attribute>(0);
        }

        return result;
    }

    /**
     * Retrieves the foreign key attributes.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the foreign key attributes.
     */
    @NotNull
    public List<ForeignKey> retrieveForeignKeyAttributes(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<ForeignKey> result = null;

        @Nullable Table t_Table = metadataManager.getTableDAO().findByName(tableName, null, null);

        if (t_Table != null)
        {
            result = t_Table.getForeignKeys();
        }

        if (result == null)
        {
            result = new ArrayList<ForeignKey>(0);
        }

        return result;
    }

    /**
     * Retrieves the complete attribute list.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the attributes.
     */
    @NotNull
    public List<Attribute> retrieveAttributes(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<Attribute> result = null;

        Table t_Table = metadataManager.getTableDAO().findByName(tableName, null, null);

        if (t_Table != null)
        {
            result = t_Table.getAttributes();
        }

        if (result == null)
        {
            result = new ArrayList<Attribute>(0);
        }

        return result;
    }

    /**
     * Retrieves the externally-managed attributes.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the externally-managed attributes.
     */
    @NotNull
    public List<Attribute> retrieveExternallyManagedAttributes(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<Attribute> result = null;

        @Nullable Table t_Table = metadataManager.getTableDAO().findByName(tableName, null, null);

        if (t_Table != null)
        {
            List<Attribute> t_lAttributes = t_Table.getAttributes();

            for (Attribute t_Attribute : t_lAttributes)
            {
                if (t_Attribute.isManagedExternally())
                {
                    if (result == null)
                    {
                        result = new ArrayList<Attribute>(t_lAttributes.size());
                    }
                    result.add(t_Attribute);
                }
            }
        }

        if (result == null)
        {
            result = new ArrayList<Attribute>(0);
        }

        return result;
    }

    /**
     * Retrieves all but the externally-managed attributes.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return all but the externally-managed attributes.
     */
    @NotNull
    public List<Attribute> retrieveAllButExternallyManagedAttributes(
        final String tableName,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<Attribute> result = null;

        @Nullable Table t_Table = metadataManager.getTableDAO().findByName(tableName, null, null);

        if (t_Table != null)
        {
            @NotNull List<Attribute> t_lAttributes = t_Table.getAttributes();

            for (Attribute t_Attribute : t_lAttributes)
            {
                if (   (t_Attribute != null)
                    && (!t_Attribute.isManagedExternally()))
                {
                    if (result == null)
                    {
                        result = new ArrayList<Attribute>(t_lAttributes.size());
                    }
                    result.add(t_Attribute);
                }
            }
        }

        if (result == null)
        {
            result = new ArrayList<Attribute>(0);
        }

        return result;
    }

    /**
     * Retrieves the referring keys.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the foreign keys of other tables pointing
     * to this one:
     * a map of referringTableName -> ForeignKey[].
     */
    @NotNull
    public Map<String,List<ForeignKey>> retrieveReferringKeys(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        @NotNull Map<String,List<ForeignKey>> result = new HashMap<String,List<ForeignKey>>(2);

        for (Table t_Table : metadataManager.getTableDAO().findAllTables())
        {
            if (t_Table != null)
            {
                @NotNull List<ForeignKey> t_lForeignKeys = t_Table.getForeignKeys();

                for (ForeignKey t_ForeignKey : t_lForeignKeys)
                {
                    if (   (t_ForeignKey != null)
                        && (t_ForeignKey.getTargetTableName().equals(tableName)))
                    {
                        @Nullable List<ForeignKey> t_lReferringForeignKeys =
                            result.get(t_ForeignKey.getSourceTableName());

                        if (t_lReferringForeignKeys == null)
                        {
                            t_lReferringForeignKeys = new ArrayList<ForeignKey>(1);
                        }
                        t_lReferringForeignKeys.add(t_ForeignKey);

                        result.put(t_ForeignKey.getSourceTableName(), t_lReferringForeignKeys);
                    }
                }
            }
        }

        return result;
    }

    /**
     * Checks whether the foreign key allows null or not.
     * @param attributes the attributes.
     * @return such information.
     * @precondition attributes != null
     */
    @SuppressWarnings("unused")
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
     */
    @SuppressWarnings("unused")
    public boolean allowsNullAsAWhole(
        @NotNull final List<String> columnNames,
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager)
    {
        boolean result = false;

        @Nullable Table t_Table = metadataManager.getTableDAO().findByName(tableName, null, null);

        if (t_Table != null)
        {
            for (Attribute t_Attribute : t_Table.getAttributes())
            {
                if (   (t_Attribute != null)
                    && (columnNames.contains(t_Attribute.getName())))
                {
                    result = t_Attribute.isNullable();

                    if (!result)
                    {
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the foreign keys starting at given table.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the foreign keys.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<ForeignKey> retrieveForeignKeys(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<ForeignKey> result = null;

        @Nullable Table t_Table = metadataManager.getTableDAO().findByName(tableName, null, null);

        if (t_Table != null)
        {
            result = t_Table.getForeignKeys();
        }

        if (result == null)
        {
            result = new ArrayList<ForeignKey>(0);
        }

        return result;
    }

    /**
     * Builds the foreign keys pointing from one table to another.
     * @param sourceTableName the name of the source table.
     * @param targetTableName the name of the target table..
     * @param metadataManager the {@link MetadataManager} instance.
     */
    @NotNull
    @SuppressWarnings("unused")
    protected List<ForeignKey> retrieveForeignKeys(
        @NotNull final String sourceTableName,
        @NotNull final String targetTableName,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<ForeignKey> result = null;

        @Nullable Table t_Table = metadataManager.getTableDAO().findByName(sourceTableName, null, null);

        if (t_Table != null)
        {
            for (@Nullable ForeignKey t_ForeignKey : t_Table.getForeignKeys())
            {
                if (   (t_ForeignKey != null)
                    && (t_ForeignKey.getTargetTableName().equals(targetTableName)))
                {
                    if (result == null)
                    {
                        result = new ArrayList<ForeignKey>(1);
                    }
                    result.add(t_ForeignKey);
                }
            }
        }

        if (result == null)
        {
            result = new ArrayList<ForeignKey>(0);
        }

        return result;
    }

    /**
     * Retrieves the LOB attributes.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> retrieveLobAttributes(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            filterLobAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                true);
    }

    /**
     * Retrieves all but the LOB attributes.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> retrieveAllButLobAttributes(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            filterLobAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                false);
    }

    /**
     * Retrieves all but the LOB attributes.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param includeLob whether to include or not Lob attributes.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> filterLobAttributes(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        final boolean includeLob)
    {
        @Nullable List<Attribute> result = null;

        @Nullable Table t_Table = metadataManager.getTableDAO().findByName(tableName, null, null);

        if (t_Table != null)
        {
            boolean t_bIsClob;

            for (Attribute t_Attribute : t_Table.getAttributes())
            {
                t_bIsClob = false;

                if (t_Attribute != null)
                {
                    t_bIsClob = metadataTypeManager.isClob(t_Attribute.getType());
                }

                if (   (t_Attribute != null)
                    && (   (   (includeLob)
                            && (t_bIsClob))
                        || (   (!includeLob))
                            && (!t_bIsClob)))
                {
                    if (result == null)
                    {
                        result = new ArrayList<Attribute>(1);
                    }
                    result.add(t_Attribute);
                }
            }
        }

        if (result == null)
        {
            result = new ArrayList<Attribute>(0);
        }

        return result;
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
