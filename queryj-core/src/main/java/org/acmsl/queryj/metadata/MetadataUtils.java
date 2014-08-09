/*
                        QueryJ Core

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
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.Table;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;
import org.acmsl.commons.utils.EnglishGrammarUtils;

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
import java.util.Locale;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Provides some methods commonly-used when working with metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
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
     * A map-based cache to improve performance when retrieving
     * singular and plural forms for tables, and to cache other
     * other data if needed.
     */
    private static final Map<String,String> CACHE = new HashMap<>();

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
    public List<Attribute<String>> retrievePrimaryKeyAttributes(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        @NotNull final List<Attribute<String>> result;

        final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(tableName);

        if (t_Table != null)
        {
            result = t_Table.getPrimaryKey();
        }
        else
        {
            result = new ArrayList<>(0);
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
    public List<Attribute<String>> retrieveNonPrimaryKeyAttributes(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<Attribute<String>> result = null;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(tableName);

        if (t_Table != null)
        {
            @NotNull final List<Attribute<String>> t_lAttributes = t_Table.getAttributes();

            @NotNull final List<Attribute<String>> t_lPrimaryKey = t_Table.getPrimaryKey();

            result = new ArrayList<>(Math.max(t_lAttributes.size() - t_lPrimaryKey.size(), 0));

            result.addAll(t_lAttributes);
            result.removeAll(t_lPrimaryKey);
        }

        if (result == null)
        {
            result = new ArrayList<>(0);
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
    public List<ForeignKey<String>> retrieveForeignKeyAttributes(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<ForeignKey<String>> result = null;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(tableName);

        if (t_Table != null)
        {
            result = t_Table.getForeignKeys();
        }

        if (result == null)
        {
            result = new ArrayList<>(0);
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
    public List<Attribute<String>> retrieveAttributes(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<Attribute<String>> result = null;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(tableName);

        if (t_Table != null)
        {
            result = t_Table.getAttributes();
        }

        if (result == null)
        {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * Retrieves the externally-managed attributes.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the externally-managed attributes.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<Attribute<String>> retrieveExternallyManagedAttributes(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<Attribute<String>> result = null;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(tableName);

        if (t_Table != null)
        {
            @NotNull final List<Attribute<String>> t_lAttributes = t_Table.getAttributes();

            for (@Nullable final Attribute<String> t_Attribute : t_lAttributes)
            {
                if (   (t_Attribute != null)
                    && (t_Attribute.isExternallyManaged()))
                {
                    if (result == null)
                    {
                        result = new ArrayList<>(t_lAttributes.size());
                    }
                    result.add(t_Attribute);
                }
            }
        }

        if (result == null)
        {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * Retrieves all but the externally-managed attributes.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return all but the externally-managed attributes.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<Attribute<String>> retrieveAllButExternallyManagedAttributes(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<Attribute<String>> result = null;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(tableName);

        if (t_Table != null)
        {
            @NotNull final List<Attribute<String>> t_lAttributes = t_Table.getAttributes();

            for (@Nullable final Attribute<String> t_Attribute : t_lAttributes)
            {
                if (   (t_Attribute != null)
                    && (!t_Attribute.isExternallyManaged()))
                {
                    if (result == null)
                    {
                        result = new ArrayList<>(t_lAttributes.size());
                    }
                    result.add(t_Attribute);
                }
            }
        }

        if (result == null)
        {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * Retrieves the referring keys.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the foreign keys of other tables pointing
     * to this one:
     * a map of referringTableName -&gt; ForeignKey[].
     */
    @SuppressWarnings("unused")
    @NotNull
    public Map<String,List<ForeignKey<String>>> retrieveReferringKeys(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        @NotNull final Map<String,List<ForeignKey<String>>> result = new HashMap<>(2);

        for (@Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table : metadataManager.getTableDAO().findAllTables())
        {
            if (t_Table != null)
            {
                @NotNull final List<ForeignKey<String>> t_lForeignKeys = t_Table.getForeignKeys();

                for (@Nullable final ForeignKey<String> t_ForeignKey : t_lForeignKeys)
                {
                    if (   (t_ForeignKey != null)
                        && (t_ForeignKey.getTargetTableName().equals(tableName)))
                    {
                        @Nullable List<ForeignKey<String>> t_lReferringForeignKeys =
                            result.get(t_ForeignKey.getSourceTableName());

                        if (t_lReferringForeignKeys == null)
                        {
                            t_lReferringForeignKeys = new ArrayList<>(1);
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
     */
    @SuppressWarnings("unused")
    public boolean allowsNullAsAWhole(@Nullable final Collection<Attribute<String>> attributes)
    {
        boolean result = false;

        if  (attributes != null)
        {
            for (@Nullable final Attribute<String> attribute : attributes)
            {
                if  (   (attribute != null)
                     && (attribute.isNullable()))
                {
                    result = true;
                    break;
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

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(tableName);

        if (t_Table != null)
        {
            for (@Nullable final Attribute<String> t_Attribute : t_Table.getAttributes())
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
    public List<ForeignKey<String>> retrieveForeignKeys(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<ForeignKey<String>> result = null;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(tableName);

        if (t_Table != null)
        {
            result = t_Table.getForeignKeys();
        }

        if (result == null)
        {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * Builds the foreign keys pointing from one table to another.
     * @param sourceTableName the name of the source table.
     * @param targetTableName the name of the target table..
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the foreign keys.
     */
    @NotNull
    @SuppressWarnings("unused")
    protected List<ForeignKey<String>> retrieveForeignKeys(
        @NotNull final String sourceTableName,
        @NotNull final String targetTableName,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<ForeignKey<String>> result = null;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(sourceTableName);

        if (t_Table != null)
        {
            for (@Nullable final ForeignKey<String> t_ForeignKey : t_Table.getForeignKeys())
            {
                if (   (t_ForeignKey != null)
                    && (t_ForeignKey.getTargetTableName().equals(targetTableName)))
                {
                    if (result == null)
                    {
                        result = new ArrayList<>(1);
                    }
                    result.add(t_ForeignKey);
                }
            }
        }

        if (result == null)
        {
            result = new ArrayList<>(0);
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
    @SuppressWarnings("unused")
    @NotNull
    public List<Attribute<String>> retrieveLobAttributes(
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
    @SuppressWarnings("unused")
    @NotNull
    public List<Attribute<String>> retrieveAllButLobAttributes(
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
    public List<Attribute<String>> filterLobAttributes(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        final boolean includeLob)
    {
        @Nullable List<Attribute<String>> result = null;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(tableName);

        if (t_Table != null)
        {
            boolean t_bIsClob;

            for (@Nullable final Attribute<String> t_Attribute : t_Table.getAttributes())
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
                        result = new ArrayList<>(1);
                    }
                    result.add(t_Attribute);
                }
            }
        }

        if (result == null)
        {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * Retrieves the large-object-block properties.
     * @param properties the properties.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return such collection.
     * @param <T> the type.
     */
    @NotNull
    public <T> List<Property<T>> filterLobProperties(
        @Nullable final Collection<Property<T>> properties,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        @NotNull final List<Property<T>> result = new ArrayList<>();

        if (properties != null)
        {
            for (@Nullable final Property<T> t_Property : properties)
            {
                if  (   (t_Property != null)
                     && (metadataTypeManager.isClob(t_Property.getType())))
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
     */
    @SuppressWarnings("unused")
    public boolean contain(
        @Nullable final Collection<Attribute<String>> attributes,
        @NotNull final String attributeName,
        @NotNull final String tableName)
    {
        boolean result = false;

        @NotNull final Iterator<Attribute<String>> t_Iterator =
            (attributes != null)
            ? attributes.iterator() : new ArrayList<Attribute<String>>().iterator();

        @Nullable Attribute<String> t_CurrentItem;

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
     */
    public boolean match(
        @Nullable final Attribute<String> attribute,
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

    /**
     * Caches the singular table name.
     * @param tableName the table name.
     * @param singularForm the singular form.
     */
    protected void cacheSingularTableName(
        @NotNull final String tableName, @NotNull final String singularForm)
    {
        cacheEntry(buildSingularKey(tableName), singularForm);
    }

    /**
     * Caches the plural table name.
     * @param tableName the table name.
     * @param pluralForm the plural form.
     */
    protected void cachePluralTableName(
        @NotNull final String tableName, @NotNull final String pluralForm)
    {
        cacheEntry(buildPluralKey(tableName), pluralForm);
    }

    /**
     * Caches given singular or plural form of a table name.
     * @param key the key.
     * @param value the value.
     */
    protected void cacheEntry(
        @NotNull final String key, @Nullable final String value)
    {
        if  (value == null)
        {
            removeEntryFromCache(CACHE, key);
        }
        else
        {
            cache(CACHE, key, value);
        }
    }

    /**
     * Retrieves the cached singular form for given table.
     * @param tableName the table name.
     * @return such value.
     */
    @Nullable
    protected String retrieveCachedSingularTableName(@NotNull final String tableName)
    {
        return retrieveCachedEntry(buildSingularKey(tableName));
    }

    /**
     * Retrieves the cached plural form for given table.
     * @param tableName the table name.
     * @return such value.
     */
    @Nullable
    protected String retrieveCachedPluralTableName(@NotNull final String tableName)
    {
        return retrieveCachedEntry(buildPluralKey(tableName));
    }

    /**
     * Retrieves the cached entry.
     * @param key the key.
     * @return the cached entry, or <code>null</code> if it's not cached.
     */
    @Nullable
    protected String retrieveCachedEntry(@NotNull final String key)
    {
        return retrieveCachedEntry(CACHE, key);
    }

    /**
     * Caches given entry.
     * @param map the cache.
     * @param key the key.
     * @param value the value.
     */
    protected void cache(@NotNull final Map<String,String> map, @NotNull final String key, @NotNull final String value)
    {
        map.put(key, value);
    }

    /**
     * Retrieves the cached entry.
     * @param cache the cache.
     * @param key the key.
     * @return the cached entry, or <code>null</code> if it's not cached.
     */
    @Nullable
    protected String retrieveCachedEntry(@NotNull final Map<String,String> cache, @NotNull final String key)
    {
        return cache.get(key);
    }

    /**
     * Removes an entry from the cache.
     * @param cache the cache.
     * @param key the key.
     */
    protected void removeEntryFromCache(@NotNull final Map<String,String> cache, @NotNull final String key)
    {
        cache.remove(key);
    }

    /**
     * Builds the singular key for given table.
     * @param tableName the table name.
     * @return such key.
     */
    @NotNull
    protected String buildSingularKey(@NotNull final String tableName)
    {
        return "~singular--" + tableName;
    }

    /**
     * Builds the plural key for given table.
     * @param tableName the table name.
     * @return such key.
     */
    @NotNull
    protected String buildPluralKey(@NotNull final String tableName)
    {
        return "~plural--" + tableName;
    }

    /**
     * Checks whether given table name matches the DAO id.
     * @param tableName the table name.
     * @param daoId the DAO id.
     * @return <code>true</code> if they match.
     */
    public boolean matches(
        @NotNull final String tableName, @NotNull final String daoId)
    {
        return matches(tableName, daoId, EnglishGrammarUtils.getInstance());
    }

    /**
     * Checks whether given table name matches the DAO id.
     * @param tableName the table name.
     * @param daoId the DAO id.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @return <code>true</code> if they match.
     */
    protected boolean matches(
        @NotNull final String tableName,
        @NotNull final String daoId,
        @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        boolean result;

        final String t_strTableInLowerCase = tableName.trim().toLowerCase(Locale.US);

        result = daoId.equalsIgnoreCase(t_strTableInLowerCase);

        if  (!result)
        {
            String t_strSingularName =
                retrieveCachedSingularTableName(t_strTableInLowerCase);

            if (t_strSingularName == null)
            {
                t_strSingularName =
                    englishGrammarUtils.getSingular(t_strTableInLowerCase);

                cacheSingularTableName(
                    t_strTableInLowerCase, t_strSingularName);
            }

            result = daoId.equalsIgnoreCase(t_strSingularName);
        }

        if  (!result)
        {
            String t_strPluralName =
                retrieveCachedPluralTableName(t_strTableInLowerCase);

            if (t_strPluralName == null)
            {
                t_strPluralName =
                    englishGrammarUtils.getPlural(t_strTableInLowerCase);

                cachePluralTableName(
                    t_strTableInLowerCase, t_strPluralName);
            }

            result = daoId.equalsIgnoreCase(t_strPluralName);
        }

        return result;
    }
}
