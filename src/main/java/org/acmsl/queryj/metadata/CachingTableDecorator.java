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
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 *****************************************************************************
 *
 * Filename: CachingTableDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Adds a simple caching mechanism while decorating 'Table'
 *              instances.
 */
package org.acmsl.queryj.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.metadata.vo.Table;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * Adds a simple caching mechanism while decorating <code>Table</code>
 * instances.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class CachingTableDecorator
    extends AbstractTableDecorator
{
    /**
     * The cached uppercased name.
     */
    private String m__strCachedNameUppercased;

    /**
     * The cached capitalized name.
     */
    private String m__strCachedNameCapitalized;

    /**
     * The cached lowercased name.
     */
    private String m__strCachedNameLowercased;

    /**
     * The cached uncapitalized name.
     */
    private String m__strCachedUncapitalizedName;

    /**
     * The cached VO name.
     */
    private String m__strCachedVoName;

    /**
     * The cached normalized lowercased name.
     */
    private String m__strCachedNameNormalizedLowercased;

    /**
     * The cached normalized lowercased singular name.
     */
    private String m__strCachedSingularNameNormalizedLowercased;

    /**
     * The cached normalized name.
     */
    private String m__strCachedNameNormalized;

    /**
     * The cached capitalized singular name.
     */
    private String m__strCachedSingularNameCapitalized;

    /**
     * The cached primary key.
     */
    private List<Attribute> m__lCachedPrimaryKey;

    /**
     * The cached non-readonly attributes.
     */
    private List<Attribute> m__lCachedNonReadOnlyAttributes;

    /**
     * The cached non-readonly attributes, including parent's.
     */
    private List<Attribute> m__lCachedAllNonReadOnlyAttributes;

    /**
     * The cached non-parent attributes.
     */
    private List<Attribute> m__lCachedNonParentAttributes;

    /**
     * The cached parent table.
     */
    private Table m__CachedParentTable;

    /**
     * The cached all attributes.
     */
    private List<Attribute> m__lCachedAllAttributes;

    /**
     * The cached attributes.
     */
    private List<Attribute> m__lCachedAttributes;

    /**
     * The cached non-parent non-managed-externally attributes.
     */
    private List<Attribute> m__lCachedNonParentNonExternallyManagedAttributes;

    /**
     * The cached parent's all attributes and the non-parent own attributes.
     */
    private List<Attribute> m__lCachedAllParentAndNonParentAttributes;

    /**
     * The cached parent's all attributes and the non-parent non-managed-externally attributes.
     */
    private List<Attribute> m__lCachedAllParentAndNonParentNonExternallyManagedAttributes;

    /**
     * The cached all non-managed externally attributes.
     */
    private List<Attribute> m__lCachedAllNonExternallyManagedAttributes;

    /**
     * The cached all parent and non-parent non-managed-externally read-only attributes.
     */
    private List<Attribute> m__lCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes;

    /**
     * The cached all parent and non-parent non-read-only attributes.
     */
    private List<Attribute> m__lCachedAllParentAndNonParentNonReadOnlyAttributes;

    /**
     * The cached all parent and non-parent read-only attributes.
     */
    private List<Attribute> m__lCachedAllParentAndNonParentReadOnlyAttributes;

    /**
     * The cached non-parent non-managed-externally attributes, plus the primary key.
     */
    private List<Attribute> m__lCachedNonParentNonExternallyManagedPlusPkAttributes;

    /**
     * The cached all non-managed-externally attributes, plus the primary key.
     */
    private List<Attribute> m__lCachedAllNonExternallyManagedPlusPkAttributes;

    /**
     * The cached all non-managed-externally, non-readonly attributes, plus the primary key.
     */
    private List<Attribute> m__lCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes;

    /**
     * The cached all parent and non-parent non-managed-externally, non-readonly attributes,
     * plus the primary key.
     */
    private List<Attribute> m__lCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes;

    /**
     * The cached non-parent attributes, plus the primary key.
     */
    private List<Attribute> m__lCachedNonParentPlusPkAttributes;

    /**
     * The cached non-readonly, except the externally-managed attributes.
     */
    private List<Attribute> m__lCachedAllNonReadOnlyButExternallyManagedAttributes;

    /**
     * The cached non-primary-key, non-readonly attributes.
     */
    private List<Attribute> m__lCachedNonPrimaryKeyNonReadOnlyAttributes;

    /**
     * The cached all parent tables.
     */
    private List<Table> m__lCachedAllParentTables;

    /**
     * The cached static rows.
     */
    private List<Row> m__lCachedStaticContents;

    /**
     * The cached dynamic queries.
     */
    private List<Sql> m__lCachedDynamicQueries;

    /**
     * The cached non-primary-key attributes.
     */
    private List<Attribute> m__lCachedNonPrimaryKeyAttributes;

    /**
     * The cached value-object name, uncapitalized;
     */
    private String m__strCachedVoNameUncapitalized;

    /**
     * The cached custom selects.
     */
    private List<Sql> m__lCachedCustomSelects;

    /**
     * The cached custom updates or inserts.
     */
    private List<Sql> m__lCachedCustomUpdatesOrInserts;

    /**
     * The cached custom results.
     */
    private List<ResultDecorator> m__lCachedCustomResults;

    /**
     * The cached child attributes.
     */
    private List<Attribute> m__lCachedChildAttributes;

    /**
     * The cached foreign keys.
     */
    private List<ForeignKey> m__lCachedForeignKeys;

    /**
     * The cached parent foreign key.
     */
    private ForeignKey m__CachedParentForeignKey;

    /**
     * Whether the parent foreign key was already retrieved.
     */
    private boolean m__bParentForeignKeyAlreadyRetrieved;

    /**
     * The cached version of the singular name, in upper case.
     */
    private String m__strCachedSingularNameUppercased;

    /**
     * The cached version of the singular name, in lower case.
     */
    private String m__strCachedSingularNameLowercased;

    /**
     * The cached read-only attributes.
     */
    private List<Attribute> m__lCachedReadOnlyAttributes;

    /**
     * The cached externally-managed attributes.
     */
    private List<AttributeDecorator> m__lCachedAllExternallyManagedAttributes;

    /**
     * Creates a <code>CachingTableDecorator</code> with the
     * <code>Table</code> to decorate.
     * @param table the table.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the decorator factory.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     */
    public CachingTableDecorator(
        @NotNull final Table table,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        super(table, metadataManager, decoratorFactory, customSqlProvider);
    }

    /**
     * Specifies the cached primary key.
     * @param list the attribute list.
     */
    protected final void immutableSetCachedPrimaryKey(@NotNull final List<Attribute> list)
    {
        m__lCachedPrimaryKey = list;
    }

    /**
     * Specifies the cached primary key.
     * @param list the attribute list.
     */
    protected void setCachedPrimaryKey(@NotNull final List<Attribute> list)
    {
        immutableSetCachedPrimaryKey(list);
    }

    /**
     * Retrieves the cached primary key.
     * @return such list.
     */
    @Nullable
    public List<Attribute> getCachedPrimaryKey()
    {
        return m__lCachedPrimaryKey;
    }

    /**
     * Retrieves the primary key attributes.
     * @return such list.
     */
    @Override
    @NotNull
    public List<Attribute> getPrimaryKey()
    {
        @Nullable List<Attribute> result = getCachedPrimaryKey();

        if (result == null)
        {
            result = super.getPrimaryKey();

            result = decorateAttributes(result, getMetadataManager(), getDecoratorFactory());

            setCachedPrimaryKey(result);
        }

        return result;
    }


    /**
     * Specifies the cached all attributes.
     * @param attributes the attributes.
     */
    protected final void immutableSetCachedAllAttributes(
        @NotNull final List<Attribute> attributes)
    {
        m__lCachedAllAttributes = attributes;
    }

    /**
     * Specifies the cached all attributes.
     * @param attributes the attributes.
     */
    protected void setCachedAllAttributes(@NotNull final List<Attribute> attributes)
    {
        immutableSetCachedAllAttributes(attributes);
    }

    /**
     * Retrieves the cached all attributes.
     * @return such attributes.
     */
    @Nullable
    protected List<Attribute> getCachedAllAttributes()
    {
        return m__lCachedAllAttributes;
    }

    /**
     * Retrieves the attributes.
     * @return such information.
     */
    @NotNull
    public List<Attribute> getAllAttributes()
    {
        List<Attribute> result = getCachedAllAttributes();

        if  (   (result == null)
             || (result.size() == 0))
        {
            result = super.getAllAttributes();
            setCachedAllAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached attributes.
     * @param attributes the attributes.
     */
    protected final void immutableSetCachedAttributes(@NotNull final List<Attribute> attributes)
    {
        m__lCachedAttributes = attributes;
    }

    /**
     * Specifies the cached attributes.
     * @param attributes the attributes.
     */
    protected void setCachedAttributes(@NotNull final List<Attribute> attributes)
    {
        immutableSetCachedAttributes(attributes);
    }

    /**
     * Retrieves the cached attributes.
     * @return such attributes.
     */
    @Nullable
    protected List<Attribute> getCachedAttributes()
    {
        return m__lCachedAttributes;
    }

    /**
     * Retrieves the attributes.
     * @return such information.
     */
    @NotNull
    public List<Attribute> getAttributes()
    {
        List<Attribute> result = getCachedAttributes();

        if  (   (result == null)
             || (result.size() == 0))
        {
            result = super.getAttributes();
            setCachedAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached parent table.
     * @param parent such table.
     */
    protected final void immutableSetCachedParentTable(@NotNull final Table parent)
    {
        m__CachedParentTable = parent;
    }

    /**
     * Specifies the cached parent table.
     * @param parent such table.
     */
    @SuppressWarnings("unused")
    protected void setCachedParentTable(@NotNull final Table parent)
    {
        immutableSetCachedParentTable(parent);
    }

    /**
     * Retrieves the cached parent table.
     * @return such table.
     */
    @Nullable
    public Table getCachedParentTable()
    {
        return m__CachedParentTable;
    }

    /**
     * Retrieves the parent table.
     * @return such information.
     */
    @Nullable
    public Table getParentTable()
    {
        @Nullable Table result = getCachedParentTable();

        if  (result == null)
        {
            result = super.getParentTable();
            if (result != null)
            {
                setCachedParentTable(result);
            }
        }

        return result;
    }

    /**
     * Specifies the cached, non-parent attributes.
     * @param attrs such attributes.
     */
    protected final void immutableSetCachedNonReadOnlyAttributes(@NotNull final List<Attribute> attrs)
    {
        m__lCachedNonReadOnlyAttributes = attrs;
    }

    /**
     * Specifies the cached, non-read-only attributes.
     * @param attrs such attributes.
     */
    protected void setCachedNonReadOnlyAttributes(@NotNull final List<Attribute> attrs)
    {
        immutableSetCachedNonReadOnlyAttributes(attrs);
    }

    /**
     * Retrieves the cached, non-read-only attributes.
     * @return such information.
     */
    @Nullable
    protected List<Attribute> getCachedNonReadOnlyAttributes()
    {
        return m__lCachedNonReadOnlyAttributes;
    }

    /**
     * Retrieves the non-read-only attributes.
     * @return such attributes.
     */
    @Override
    @NotNull
    public List<Attribute> getNonReadOnlyAttributes()
    {
        List<Attribute> result = getCachedNonReadOnlyAttributes();

        if  (   (result == null)
             || (result.size() == 0))
        {
            result = super.getNonReadOnlyAttributes();
            setCachedNonReadOnlyAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached, non-parent attributes.
     * @param attrs such attributes.
     */
    protected final void immutableSetCachedAllNonReadOnlyAttributes(@NotNull final List<Attribute> attrs)
    {
        m__lCachedAllNonReadOnlyAttributes = attrs;
    }

    /**
     * Specifies the cached, non-read-only attributes, including parent's.
     * @param attrs such attributes.
     */
    protected void setCachedAllNonReadOnlyAttributes(@NotNull final List<Attribute> attrs)
    {
        immutableSetCachedAllNonReadOnlyAttributes(attrs);
    }

    /**
     * Retrieves the cached, non-read-only attributes, including parent's.
     * @return such information.
     */
    @Nullable
    protected List<Attribute> getCachedAllNonReadOnlyAttributes()
    {
        return m__lCachedAllNonReadOnlyAttributes;
    }

    /**
     * Retrieves the non-read-only attributes, including parent's.
     * @return such attributes.
     */
    @Override
    @NotNull
    public List<Attribute> getAllNonReadOnlyAttributes()
    {
        List<Attribute> result = getCachedAllNonReadOnlyAttributes();

        if  (   (result == null)
                || (result.size() == 0))
        {
            result = super.getAllNonReadOnlyAttributes();
            setCachedAllNonReadOnlyAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached, non-parent attributes.
     * @param attrs such attributes.
     */
    protected final void immutableSetCachedNonParentAttributes(@NotNull final List<Attribute> attrs)
    {
        m__lCachedNonParentAttributes = attrs;
    }

    /**
     * Specifies the cached, non-parent attributes.
     * @param attrs such attributes.
     */
    protected void setCachedNonParentAttributes(@NotNull final List<Attribute> attrs)
    {
        immutableSetCachedNonParentAttributes(attrs);
    }

    /**
     * Retrieves the cached, non-parent attributes.
     * @return such information.
     */
    @Nullable
    protected List<Attribute> getCachedNonParentAttributes()
    {
        return m__lCachedNonParentAttributes;
    }

    /**
     * Retrieves the non-parent attributes.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> getNonParentAttributes()
    {
        @Nullable List<Attribute> result = getCachedNonParentAttributes();

        if  (   (result == null)
             || (result.size() == 0))
        {
            result = super.getNonParentAttributes();
            setCachedNonParentAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached uppercased name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedNameUppercased(
        @NotNull final String value)
    {
        m__strCachedNameUppercased = value;
    }
    
    /**
     * Specifies the cached uppercased name.
     * @param value the value to cache.
     */
    protected void setCachedNameUppercased(@NotNull final String value)
    {
        immutableSetCachedNameUppercased(value);
    }

    /**
     * Retrieves the cached uppercased name.
     * @return such value.
     */
    @Nullable
    public String getCachedNameUppercased()
    {
        return m__strCachedNameUppercased;
    }

    /**
     * Retrieves the name, in upper case.
     * @return such value.
     */
    @NotNull
    public String getNameUppercased()
    {
        @Nullable String result = getCachedNameUppercased();
        
        if  (result == null)
        {
            result = super.getNameUppercased();
            setCachedNameUppercased(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached capitalized name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedNameCapitalized(
        @NotNull final String value)
    {
        m__strCachedNameCapitalized = value;
    }
    
    /**
     * Specifies the cached capitalized name.
     * @param value the value to cache.
     */
    protected void setCachedNameCapitalized(@NotNull final String value)
    {
        immutableSetCachedNameCapitalized(value);
    }

    /**
     * Retrieves the cached capitalized name.
     * @return such value.
     */
    @Nullable
    public String getCachedNameCapitalized()
    {
        return m__strCachedNameCapitalized;
    }

    /**
     * Retrieves the name, in upper case.
     * @return such value.
     */
    @NotNull
    public String getNameCapitalized()
    {
        @Nullable String result = getCachedNameCapitalized();
        
        if  (result == null)
        {
            result = super.getNameCapitalized();
            setCachedNameCapitalized(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached lowercased name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedNameLowercased(
        @NotNull final String value)
    {
        m__strCachedNameLowercased = value;
    }
    
    /**
     * Specifies the cached lowercased name.
     * @param value the value to cache.
     */
    protected void setCachedNameLowercased(@NotNull final String value)
    {
        immutableSetCachedNameLowercased(value);
    }

    /**
     * Retrieves the cached lowercased name.
     * @return such value.
     */
    @Nullable
    public String getCachedNameLowercased()
    {
        return m__strCachedNameLowercased;
    }

    /**
     * Retrieves the name, in lower case.
     * @return such value.
     */
    @NotNull
    public String getNameLowercased()
    {
        @Nullable String result = getCachedNameLowercased();
        
        if  (result == null)
        {
            result = super.getNameLowercased();
            setCachedNameLowercased(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached uncapitalized name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedUncapitalizedName(
        @NotNull final String value)
    {
        m__strCachedUncapitalizedName = value;
    }
    
    /**
     * Specifies the cached uncapitalized name.
     * @param value the value to cache.
     */
    protected void setCachedUncapitalizedName(@NotNull final String value)
    {
        immutableSetCachedUncapitalizedName(value);
    }

    /**
     * Retrieves the cached uncapitalized name.
     * @return such value.
     */
    @Nullable
    public String getCachedUncapitalizedName()
    {
        return m__strCachedUncapitalizedName;
    }
    
    /**
     * Retrieves the table name, uncapitalized.
     * @return such value.
     */
    @NotNull
    public String getUncapitalizedName()
    {
        @Nullable String result = getCachedUncapitalizedName();
        
        if  (result == null)
        {
            result = super.getUncapitalizedName();
            setCachedUncapitalizedName(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached VO name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedVoName(
        @NotNull final String value)
    {
        m__strCachedVoName = value;
    }
    
    /**
     * Specifies the cached VO name.
     * @param value the value to cache.
     */
    protected void setCachedVoName(@NotNull final String value)
    {
        immutableSetCachedVoName(value);
    }

    /**
     * Retrieves the cached VO name.
     * @return such value.
     */
    @Nullable
    public String getCachedVoName()
    {
        return m__strCachedVoName;
    }

    /**
     * Retrieves the value-object name associated to the table name.
     * @return such name.
     */
    @NotNull
    public String getVoName()
    {
        @Nullable String result = getCachedVoName();
        
        if  (result == null)
        {
            result = super.getVoName();
            setCachedVoName(result);
        }
        
        return result;
    }

    /**
     * Caches the value-object name, uncapitalized.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedVoNameUncapitalized(@NotNull final String value)
    {
        m__strCachedVoNameUncapitalized = value;
    }

    /**
     * Caches the value-object name, uncapitalized.
     * @param value the value to cache.
     */
    @SuppressWarnings("unused")
    protected void setCachedVoNameUncapitalized(@NotNull final String value)
    {
        immutableSetCachedVoNameUncapitalized(value);
    }

    /**
     * Retrieves the cached value-object name, uncapitalized.
     * @return the cached value.
     */
    @SuppressWarnings("unused")
    @Nullable
    protected String getCachedVoNameUncapitalized()
    {
        return m__strCachedVoNameUncapitalized;
    }

    /**
     * Retrieves the value-object name associated to the table name, uncapitalized.
     * @return such name.
     */
    @Override
    @NotNull
    public String getVoNameUncapitalized()
    {
        String result = getCachedVoNameUncapitalized();

        if (result == null)
        {
            result = super.getVoNameUncapitalized();
            setCachedVoNameUncapitalized(result);
        }

        return result;
    }

    /**
     * Specifies the cached normalized lowercased name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedNameNormalizedLowercased(
        @NotNull final String value)
    {
        m__strCachedNameNormalizedLowercased = value;
    }
    
    /**
     * Specifies the cached normalized lowercased name.
     * @param value the value to cache.
     */
    protected void setCachedNameNormalizedLowercased(@NotNull final String value)
    {
        immutableSetCachedNameNormalizedLowercased(value);
    }

    /**
     * Retrieves the cached normalized lowercased name.
     * @return such value.
     */
    @Nullable
    public String getCachedNameNormalizedLowercased()
    {
        return m__strCachedNameNormalizedLowercased;
    }

    /**
     * Retrieves the table's name in lower-case, once normalized.
     * @return such information.
     */
    @NotNull
    public String getNameNormalizedLowercased()
    {
        @Nullable String result = getCachedNameNormalizedLowercased();
        
        if  (result == null)
        {
            result = super.getNameNormalizedLowercased();
            setCachedNameNormalizedLowercased(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached normalized lowercased singular name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedSingularNameNormalizedLowercased(
        @NotNull final String value)
    {
        m__strCachedSingularNameNormalizedLowercased = value;
    }
    
    /**
     * Specifies the cached normalized lowercased singular name.
     * @param value the value to cache.
     */
    protected void setCachedSingularNameNormalizedLowercased(@NotNull final String value)
    {
        immutableSetCachedSingularNameNormalizedLowercased(value);
    }

    /**
     * Retrieves the cached normalized lowercased singular name.
     * @return such value.
     */
    @Nullable
    public String getCachedSingularNameNormalizedLowercased()
    {
        return m__strCachedSingularNameNormalizedLowercased;
    }

    /**
     * Retrieves the table's name in lower-case, once normalized.
     * @return such information.
     */
    @NotNull
    public String getSingularNameNormalizedLowercased()
    {
        @Nullable String result = getCachedSingularNameNormalizedLowercased();
        
        if  (result == null)
        {
            result = super.getSingularNameNormalizedLowercased();
            setCachedSingularNameNormalizedLowercased(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached normalized name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedNameNormalized(
        @NotNull final String value)
    {
        m__strCachedNameNormalized = value;
    }
    
    /**
     * Specifies the cached normalized name.
     * @param value the value to cache.
     */
    protected void setCachedNameNormalized(@NotNull final String value)
    {
        immutableSetCachedNameNormalized(value);
    }

    /**
     * Retrieves the cached normalized name.
     * @return such value.
     */
    @Nullable
    public String getCachedNameNormalized()
    {
        return m__strCachedNameNormalized;
    }

    /**
     * Retrieves the name, in lower case.
     * @return such value.
     */
    @NotNull
    public String getNameNormalized()
    {
        @Nullable String result = getCachedNameNormalized();
        
        if  (result == null)
        {
            result = super.getNameNormalized();
            setCachedNameNormalized(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached capitalized singular name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedSingularNameCapitalized(
        @NotNull final String value)
    {
        m__strCachedSingularNameCapitalized = value;
    }
    
    /**
     * Specifies the cached capitalized singular name.
     * @param value the value to cache.
     */
    protected void setCachedSingularNameCapitalized(@NotNull final String value)
    {
        immutableSetCachedSingularNameCapitalized(value);
    }

    /**
     * Retrieves the cached capitalized singular name.
     * @return such value.
     */
    @Nullable
    public String getCachedSingularNameCapitalized()
    {
        return m__strCachedSingularNameCapitalized;
    }

    /**
     * Retrieves the capitalized table name in singular form.
     * @return such information.
     */
    @NotNull
    public String getSingularNameCapitalized()
    {
        @Nullable String result = getCachedSingularNameCapitalized();
        
        if  (result == null)
        {
            result = super.getSingularNameCapitalized();
            setCachedSingularNameCapitalized(result);
        }
        
        return result;
    }

    /**
     * Creates a table decorator.
     * @param parentTable the table name.
     * @param primaryKey the primary key.
     * @param attributes the attributes.
     * @param isStatic whether the table is static.
     * @param voDecorated whether the value-object is decorated.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return such decorator.
     */
    @SuppressWarnings("unused")
    @Nullable
    protected TableDecorator createTableDecorator(
        @Nullable final String parentTable,
        @NotNull final List<Attribute> primaryKey,
        @NotNull final List<Attribute> attributes,
        final boolean isStatic,
        final boolean voDecorated,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        @Nullable TableDecorator result = null;

        @Nullable Table t_ParentTable = null;

        if (parentTable != null)
        {
            t_ParentTable = metadataManager.getTableDAO().findByName(parentTable);
        }

        if (t_ParentTable != null)
        {
            result =
                new CachingTableDecorator(
                    t_ParentTable,
                    metadataManager,
                    decoratorFactory,
                    customSqlProvider);
        }

        return result;
    }

    /**
     * Creates a table decorator.
     * @param parentTable the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return such decorator.
     */
    @SuppressWarnings("unused")
    protected TableDecorator createTableDecorator(
        @NotNull final Table parentTable,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return
            new CachingTableDecorator(
                parentTable,
                metadataManager,
                decoratorFactory,
                customSqlProvider);
    }

    /**
     * Specifies the cached list of non-parent, non-externally-managed
     * attributes.
     * @param list such list.
     */
    protected final void immutableSetCachedNonParentNonExternallyManagedAttributes(@NotNull final List<Attribute> list)
    {
        m__lCachedNonParentNonExternallyManagedAttributes = list;
    }

    /**
     * Specifies the cached list of non-parent, non-externally-managed
     * attributes.
     * @param list such list.
     */
    protected void setCachedNonParentNonExternallyManagedAttributes(@NotNull final List<Attribute> list)
    {
        immutableSetCachedNonParentNonExternallyManagedAttributes(list);
    }

    /**
     * Retrieves the list of non-parent, non-externally-managed
     * attributes.
     * @return such list.
     */
    @Nullable
    protected List<Attribute> getCachedNonParentNonExternallyManagedAttributes()
    {
        return m__lCachedNonParentNonExternallyManagedAttributes;
    }

    /**
     * Retrieves the list of non-parent, non-externally-managed
     * attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getNonParentNonExternallyManagedAttributes()
    {
        @Nullable List<Attribute> result = getCachedNonParentNonExternallyManagedAttributes();

        if  (   (result == null)
             || (result.size() == 0))
        {
            result = super.getNonParentNonExternallyManagedAttributes();
            setCachedNonParentNonExternallyManagedAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached non-primary-key attributes.
     * @param attributes the attributes to cache.
     */
    protected final void immutableSetCachedNonPrimaryKeyAttributes(@NotNull final List<Attribute> attributes)
    {
        m__lCachedNonPrimaryKeyAttributes = attributes;
    }

    /**
     * Specifies the cached non-primary-key attributes.
     * @param attributes the attributes to cache.
     */
    protected void setCachedNonPrimaryKeyAttributes(@NotNull final List<Attribute> attributes)
    {
        immutableSetCachedNonPrimaryKeyAttributes(attributes);
    }

    /**
     * Retrieves the cached non-primary-key attributes.
     * @return the attributes to cache.
     */
    @Nullable
    protected List<Attribute> getCachedNonPrimaryKeyAttributes()
    {
        return m__lCachedNonPrimaryKeyAttributes;
    }

    /**
     * Retrieves the non-primary-key attributes.
     * @return such list.
     */
    @Override
    @NotNull
    public List<Attribute> getNonPrimaryKeyAttributes()
    {
        @Nullable List<Attribute> result = getCachedNonPrimaryKeyAttributes();

        if (result == null)
        {
            result = super.getNonPrimaryKeyAttributes();
            setCachedNonPrimaryKeyAttributes(result);
        }

        return result;
    }
    /**
     * Specifies the cached list of parent's all attributes and the non-parent own attributes.
     * @param list such list.
     */
    protected final void immutableSetCachedAllParentAndNonParentAttributes(@NotNull final List<Attribute> list)
    {
        m__lCachedAllParentAndNonParentAttributes = list;
    }

    /**
     * Specifies the cached list of parent's all attributes and the non-parent own attributes.
     * @param list such list.
     */
    protected void setCachedAllParentAndNonParentAttributes(@NotNull final List<Attribute> list)
    {
        immutableSetCachedAllParentAndNonParentAttributes(list);
    }

    /**
     * Retrieves the cached list of parent's all attributes and the non-parent own attributes.
     * @return such list.
     */
    @Nullable
    protected List<Attribute> getCachedAllParentAndNonParentAttributes()
    {
        return m__lCachedAllParentAndNonParentAttributes;
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent own attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getAllParentAndNonParentAttributes()
    {
        @Nullable List<Attribute> result = getCachedAllParentAndNonParentAttributes();

        if  (   (result == null)
             || (result.size() == 0))
        {
            result = super.getAllParentAndNonParentAttributes();
            setCachedAllParentAndNonParentAttributes(result);
        }

        return result;
    }


    /**
     * Specifies the cached list of parent's all attributes and the non-parent,
     * non-managed-externally own attributes.
     * @param list such list.
     */
    protected final void immutableSetCachedAllParentAndNonParentNonExternallyManagedAttributes(
        @NotNull final List<Attribute> list)
    {
        m__lCachedAllParentAndNonParentNonExternallyManagedAttributes = list;
    }

    /**
     * Specifies the cached list of parent's all attributes and the non-parent
     * non-managed-externally own attributes.
     * @param list such list.
     */
    protected void setCachedAllParentAndNonParentNonExternallyManagedAttributes(@NotNull final List<Attribute> list)
    {
        immutableSetCachedAllParentAndNonParentNonExternallyManagedAttributes(list);
    }

    /**
     * Retrieves the cached list of parent's all attributes and the non-parent
     * non-managed-externally own attributes.
     * @return such list.
     */
    @Nullable
    protected List<Attribute> getCachedAllParentAndNonParentNonExternallyManagedAttributes()
    {
        return m__lCachedAllParentAndNonParentNonExternallyManagedAttributes;
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally own attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getAllParentAndNonParentNonExternallyManagedAttributes()
    {
        @Nullable List<Attribute> result = getCachedAllParentAndNonParentNonExternallyManagedAttributes();

        if  (   (result == null)
             || (result.size() == 0))
        {
            result = super.getAllParentAndNonParentNonExternallyManagedAttributes();
            setCachedAllParentAndNonParentNonExternallyManagedAttributes(result);
        }

        return result;
    }

    /**
     * Specifies cached all attributes, including the parent's, but not the externally-managed.
     * @param list such attributes.
     */
    protected final void immutableSetCachedAllNonExternallyManagedAttributes(@NotNull final List<Attribute> list)
    {
        m__lCachedAllNonExternallyManagedAttributes = list;
    }

    /**
     * Specifies cached all attributes, including the parent's, but not the externally-managed.
     * @param list such attributes.
     */
    protected void setCachedAllNonExternallyManagedAttributes(@NotNull final List<Attribute> list)
    {
        immutableSetCachedAllNonExternallyManagedAttributes(list);
    }

    /**
     * Retrieves cached all attributes, including the parent's, but not the externally-managed.
     * @return such attributes.
     */
    @Nullable
    protected List<Attribute> getCachedAllNonExternallyManagedAttributes()
    {
        return m__lCachedAllNonExternallyManagedAttributes;
    }

    /**
     * Retrieves cached all attributes, including the parent's, but not the externally-managed.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> getAllNonExternallyManagedAttributes()
    {
        @Nullable List<Attribute> result = getCachedAllNonExternallyManagedAttributes();

        if  (   (result == null)
             || (result.size() == 0))
        {
            result = super.getAllNonExternallyManagedAttributes();
            setCachedAllNonExternallyManagedAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached non-readonly, except the externally-managed attributes.
     * @param list such list.
     */
    protected final void immutableSetCachedAllNonReadOnlyButExternallyManagedAttributes(
        @NotNull final List<Attribute> list)
    {
        m__lCachedAllNonReadOnlyButExternallyManagedAttributes = list;
    }

    /**
     * Specifies the cached non-readonly, except the externally-managed attributes.
     * @param list such list.
     */
    protected void setCachedAllNonReadOnlyButExternallyManagedAttributes(
        @NotNull final List<Attribute> list)
    {
        immutableSetCachedAllNonReadOnlyButExternallyManagedAttributes(list);
    }

    /**
     * Specifies the cached non-readonly, except the externally-managed attributes.
     * @return such list.
     */
    @Nullable
    public List<Attribute> getCachedAllNonReadOnlyButExternallyManagedAttributes()
    {
        return m__lCachedAllNonReadOnlyButExternallyManagedAttributes;
    }

    /**
     * Specifies the cached non-readonly, except the externally-managed attributes.
     * @return such list.
     */
    @Override
    @NotNull
    public List<Attribute> getAllNonReadOnlyButExternallyManagedAttributes()
    {
        @Nullable List<Attribute> result = getCachedAllNonReadOnlyButExternallyManagedAttributes();

        if (result == null)
        {
            result = super.getAllNonReadOnlyButExternallyManagedAttributes();

            result = decorateAttributes(result, getMetadataManager(), getDecoratorFactory());

            setCachedAllNonReadOnlyButExternallyManagedAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes.
     * @param list such list.
     */
    protected final void immutableSetCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes(
        @NotNull final List<Attribute> list)
    {
        m__lCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes = list;
    }

    /**
     * Specifies the cached list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes.
     * @param list such list.
     */
    protected void setCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes(
        @NotNull final List<Attribute> list)
    {
        immutableSetCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes(
            list);
    }

    /**
     * Retrieves the cached list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes.
     * @return such list.
     */
    @Nullable
    protected List<Attribute> getCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes()
    {
        return m__lCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes;
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes()
    {
        @Nullable List<Attribute> result =
            getCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes();

        if  (   (result == null)
             || (result.size() == 0))
        {
            result = super.getAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes();
            setCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached value for all parent + non parent, non-read-only
     * attributes.
     * @param list the list.
     */
    protected final void immutableSetCachedAllParentAndNonParentNonReadOnlyAttributes(
        @NotNull final List<Attribute> list)
    {
        m__lCachedAllParentAndNonParentNonReadOnlyAttributes = list;
    }

    /**
     * Specifies the cached value for all parent + non parent, non-read-only
     * attributes.
     * @param list the list.
     */
    protected void setCachedAllParentAndNonParentNonReadOnlyAttributes(
        @NotNull final List<Attribute> list)
    {
        immutableSetCachedAllParentAndNonParentNonReadOnlyAttributes(list);
    }

    /**
     * Retrieves the cached value for all parent + non parent, non-read-only
     * attributes.
     * @return such list.
     */
    @Nullable
    protected List<Attribute> getCachedAllParentAndNonParentNonReadOnlyAttributes()
    {
        return m__lCachedAllParentAndNonParentNonReadOnlyAttributes;
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent,
     * non-read-only own attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getAllParentAndNonParentNonReadOnlyAttributes()
    {
        @Nullable List<Attribute> result = getCachedAllParentAndNonParentNonReadOnlyAttributes();

        if  (   (result == null)
             || (result.size() == 0))
        {
            result = super.getAllParentAndNonParentNonReadOnlyAttributes();
            setCachedAllParentAndNonParentNonReadOnlyAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached list of all parent's and non parent's read-only attributes.
     * @param list such list.
     */
    protected final void immutableSetCachedAllParentAndNonParentReadOnlyAttributes(
        @NotNull final List<Attribute> list)
    {
        m__lCachedAllParentAndNonParentReadOnlyAttributes = list;
    }

    /**
     * Specifies the cached list of all parent's and non parent's read-only attributes.
     * @param list such list.
     */
    protected void setCachedAllParentAndNonParentReadOnlyAttributes(@NotNull final List<Attribute> list)
    {
        immutableSetCachedAllParentAndNonParentReadOnlyAttributes(list);
    }

    /**
     * Retrieves the cached list of all parent's and non parent's read-only attributes.
     * @return such condition.
     */
    @Nullable
    protected List<Attribute> getCachedAllParentAndNonParentReadOnlyAttributes()
    {
        return m__lCachedAllParentAndNonParentReadOnlyAttributes;
    }

    /**
     * Retrieves the all parent plus non-parent read-only attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getAllParentAndNonParentReadOnlyAttributes()
    {
        @Nullable List<Attribute> result = getCachedAllParentAndNonParentReadOnlyAttributes();

        if  (result == null)
        {
            result = super.getAllParentAndNonParentReadOnlyAttributes();
            setCachedAllParentAndNonParentReadOnlyAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached list of non-parent, non-externally-managed attributes,
     * plus the primary key.
     * @param list such list.
     */
    protected final void immutableSetCachedNonParentNonExternallyManagedPlusPkAttributes(
        @NotNull final List<Attribute> list)
    {
        m__lCachedNonParentNonExternallyManagedPlusPkAttributes = list;
    }

    /**
     * Specifies the cached list of non-parent, non-externally-managed attributes,
     * plus the primary key.
     * @param list such list.
     */
    protected void setCachedNonParentNonExternallyManagedPlusPkAttributes(@NotNull final List<Attribute> list)
    {
        immutableSetCachedNonParentNonExternallyManagedPlusPkAttributes(list);
    }

    /**
     * Retrieves the cached list of non-parent, non-externally-managed attributes,
     * plus the primary key.
     * @return such list.
     */
    @Nullable
    protected List<Attribute> getCachedNonParentNonExternallyManagedPlusPkAttributes()
    {
        return m__lCachedNonParentNonExternallyManagedPlusPkAttributes;
    }

    /**
     * Retrieves the list of non-parent, non-externally-managed
     * attributes, plus the primary key.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getNonParentNonExternallyManagedPlusPkAttributes()
    {
        @Nullable List<Attribute> result = getCachedNonParentNonExternallyManagedPlusPkAttributes();

        if  (result == null)
        {
            result = super.getNonParentNonExternallyManagedPlusPkAttributes();
            setCachedNonParentNonExternallyManagedPlusPkAttributes(result);
        }

        return result;
    }

    /**
     * Specifies all attributes, including the parent's, but not the externally-managed,
     * plus the primary key.
     * @param list the list.
     */
    protected final void immutableSetCachedAllNonExternallyManagedPlusPkAttributes(
        @NotNull final List<Attribute> list)
    {
        m__lCachedAllNonExternallyManagedPlusPkAttributes = list;
    }

    /**
     * Specifies all attributes, including the parent's, but not the externally-managed,
     * plus the primary key.
     * @param list the list.
     */
    protected void setCachedAllNonExternallyManagedPlusPkAttributes(@NotNull final List<Attribute> list)
    {
        immutableSetCachedAllNonExternallyManagedPlusPkAttributes(list);
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * plus the primary key.
     * @return such attributes.
     */
    @Nullable
    protected List<Attribute> getCachedAllNonExternallyManagedPlusPkAttributes()
    {
        return m__lCachedAllNonExternallyManagedPlusPkAttributes;
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * plus the primary key.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> getAllNonExternallyManagedPlusPkAttributes()
    {
        @Nullable List<Attribute> result = getCachedAllNonExternallyManagedPlusPkAttributes();

        if  (result == null)
        {
            result = super.getAllNonExternallyManagedPlusPkAttributes();
            setCachedAllNonExternallyManagedPlusPkAttributes(result);
        }

        return result;
    }

    /**
     * Specifies all attributes, including the parent's, but not the externally-managed,
     * or read-only, plus the primary key.
     * @param list the list.
     */
    protected final void immutableSetCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes(
        @NotNull final List<Attribute> list)
    {
        m__lCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes = list;
    }

    /**
     * Specifies all attributes, including the parent's, but not the externally-managed,
     * or read-only, plus the primary key.
     * @param list the list.
     */
    protected void setCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes(@NotNull final List<Attribute> list)
    {
        immutableSetCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes(list);
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * or read-only, plus the primary key.
     * @return such attributes.
     */
    @Nullable
    protected List<Attribute> getCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes()
    {
        return m__lCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes;
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * or read-only, plus the primary key.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> getAllNonExternallyManagedNonReadOnlyPlusPkAttributes()
    {
        @Nullable List<Attribute> result = getCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes();

        if  (result == null)
        {
            result = super.getAllNonExternallyManagedNonReadOnlyPlusPkAttributes();
            setCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes, including the primary key.
     * @param list such list.
     */
    protected final void immutableSetCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes(
        @NotNull final List<Attribute> list)
    {
        m__lCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes = list;
    }

    /**
     * Specifies the cached list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes, including the primary key.
     * @param list such list.
     */
    protected void setCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes(
        @NotNull final List<Attribute> list)
    {
        immutableSetCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes(
            list);
    }

    /**
     * Retrieves the cached list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes, including the primary key.
     * @return such list.
     */
    @Nullable
    protected List<Attribute> getCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes()
    {
        return m__lCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes;
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes, including the primary key.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes()
    {
        @Nullable List<Attribute> result =
            getCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes();

        if  (result == null)
        {
            result =
                super.getAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes();
            setCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached, non-parent attributes, plus the primary key.
     * @param attrs such attributes.
     */
    protected final void immutableSetCachedNonParentPlusPkAttributes(@NotNull final List<Attribute> attrs)
    {
        m__lCachedNonParentPlusPkAttributes = attrs;
    }

    /**
     * Specifies the cached, non-parent attributes, plus the primary key.
     * @param attrs such attributes.
     */
    protected void setCachedNonParentPlusPkAttributes(@NotNull final List<Attribute> attrs)
    {
        immutableSetCachedNonParentPlusPkAttributes(attrs);
    }

    /**
     * Retrieves the cached, non-parent attributes, plus the primary key.
     * @return such information.
     */
    @Nullable
    protected List<Attribute> getCachedNonParentPlusPkAttributes()
    {
        return m__lCachedNonParentPlusPkAttributes;
    }

    /**
     * Retrieves the non-parent attributes, plus the primary key.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute> getNonParentPlusPkAttributes()
    {
        @Nullable List<Attribute> result = getCachedNonParentPlusPkAttributes();

        if  (   (result == null)
             || (result.size() == 0))
        {
            result = super.getNonParentPlusPkAttributes();
            setCachedNonParentPlusPkAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached all parent tables.
     * @param list such list.
     */
    protected final void immutableSetCachedAllParentTables(@NotNull final List<Table> list)
    {
        m__lCachedAllParentTables = list;
    }

    /**
     * Specifies the cached all parent tables.
     * @param list such list.
     */
    protected void setCachedAllParentTables(@NotNull final List<Table> list)
    {
        immutableSetCachedAllParentTables(list);
    }

    /**
     * Retrieves the cached all parent tables.
     * @return such list.
     */
    @Nullable
    protected List<Table> getCachedAllParentTables()
    {
        return m__lCachedAllParentTables;
    }

    /**
     * Retrieves all parent tables.
     * @return such information.
     */
    @NotNull
    public List<Table> getAllParentTables()
    {
        @Nullable List<Table> result = getCachedAllParentTables();

        if  (result == null)
        {
            result = super.getAllParentTables();
            setCachedAllParentTables(result);
        }

        return result;
    }

    /**
     * Specifies the cached static contents.
     * @param rows the rows.
     */
    protected final void immutableSetCachedStaticContent(@NotNull final List<Row> rows)
    {
        m__lCachedStaticContents = rows;
    }

    /**
     * Specifies the cached static contents.
     * @param rows the rows.
     */
    @SuppressWarnings("unused")
    protected void setCachedStaticContent(@NotNull final List<Row> rows)
    {
        immutableSetCachedStaticContent(rows);
    }

    /**
     * Retrieves the cached static contents.
     * @return such rows.
     */
    @SuppressWarnings("unused")
    @Nullable
    public List<Row> getCachedStaticContent()
    {
        return m__lCachedStaticContents;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public List<Row> getStaticContent()
    {
        @Nullable List<Row> result = getCachedStaticContent();

        if (result == null)
        {
            result = super.getStaticContent();
            setCachedStaticContent(result);
        }

        return result;
    }

    /**
     * Specifies the cached dynamic queries.
     * @param list such list.
     */
    protected final void immutableSetCachedDynamicQueries(@NotNull final List<Sql> list)
    {
        m__lCachedDynamicQueries = list;
    }

    /**
     * Specifies the cached dynamic queries.
     * @param list such list.
     */
    @SuppressWarnings("unused")
    protected void setCachedDynamicQueries(@NotNull final List<Sql> list)
    {
        immutableSetCachedDynamicQueries(list);
    }

    /**
     * Retrieves the cached dynamic queries.
     * @return such information.
     */
    @Nullable
    protected List<Sql> getCachedDynamicQueries()
    {
        return m__lCachedDynamicQueries;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public List<Sql> getDynamicQueries()
    {
        @Nullable List<Sql> result = getCachedDynamicQueries();

        if (result == null)
        {
            result = super.getDynamicQueries();
            setCachedDynamicQueries(result);
        }

        return result;
    }

    /**
     * Specifies the cached non-primary-key, non-readonly attributes.
     * @param attributes the attributes to cache.
     */
    protected final void immutableSetCachedNonPrimaryKeyNonReadOnlyAttributes(
        @NotNull final List<Attribute> attributes)
    {
        m__lCachedNonPrimaryKeyNonReadOnlyAttributes = attributes;
    }

    /**
     * Specifies the cached non-primary-key, non-readonly attributes.
     * @param attributes the attributes to cache.
     */
    protected void setCachedNonPrimaryKeyNonReadOnlyAttributes(
        @NotNull final List<Attribute> attributes)
    {
        immutableSetCachedNonPrimaryKeyNonReadOnlyAttributes(attributes);
    }

    /**
     * Retrieves the cached non-primary-key, non-readonly attributes.
     * @return the cached attributes.
     */
    protected List<Attribute> getCachedNonPrimaryKeyNonReadOnlyAttributes()
    {
        return m__lCachedNonPrimaryKeyNonReadOnlyAttributes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public List<Attribute> getNonPrimaryKeyNonReadOnlyAttributes()
    {
        @Nullable List<Attribute> result = getCachedNonPrimaryKeyNonReadOnlyAttributes();

        if (result == null)
        {
            result = super.getNonPrimaryKeyNonReadOnlyAttributes();
            setCachedNonPrimaryKeyNonReadOnlyAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached custom selects.
     * @param selects such information.
     */
    protected final void immutableSetCachedCustomSelects(@NotNull final List<Sql> selects)
    {
        this.m__lCachedCustomSelects =  selects;
    }

    /**
     * Specifies the cached custom selects.
     * @param selects such information.
     */
    @SuppressWarnings("unused")
    protected void setCachedCustomSelects(@NotNull final List<Sql> selects)
    {
        immutableSetCachedCustomSelects(selects);
    }

    /**
     * Retrieves the cached custom selects.
     * @return such information.
     */
    @Nullable
    protected List<Sql> getCachedCustomSelects()
    {
        return m__lCachedCustomSelects;
    }

    /**
     * Retrieves the custom selects.
     * @return such information.
     */
    @Override
    @NotNull
    public List<Sql> getCustomSelects()
    {
        List<Sql> result = getCachedCustomSelects();

        if (result == null)
        {
            result = super.getCustomSelects();
            setCachedCustomSelects(result);
        }

        return result;
    }


    /**
     * Specifies the cached custom updates or inserts.
     * @param queries such information.
     */
    protected final void immutableSetCachedCustomUpdatesOrInserts(@NotNull final List<Sql> queries)
    {
        this.m__lCachedCustomUpdatesOrInserts = queries;
    }

    /**
     * Specifies the cached custom updates or inserts.
     * @param queries such information.
     */
    @SuppressWarnings("unused")
    protected void setCachedCustomUpdatesOrInserts(@NotNull final List<Sql> queries)
    {
        immutableSetCachedCustomUpdatesOrInserts(queries);
    }

    /**
     * Retrieves the cached custom updates or inserts.
     * @return such information.
     */
    @Nullable
    protected List<Sql> getCachedCustomUpdatesOrInserts()
    {
        return m__lCachedCustomUpdatesOrInserts;
    }

    /**
     * Retrieves the custom updates or inserts.
     * @return such information.
     */
    @Override
    @NotNull
    public List<Sql> getCustomUpdatesOrInserts()
    {
        List<Sql> result = getCachedCustomUpdatesOrInserts();

        if (result == null)
        {
            result = super.getCustomUpdatesOrInserts();
            setCachedCustomUpdatesOrInserts(result);
        }

        return result;
    }

    /**
     * Specifies the cached custom results.
     * @param results the results to cache.
     */
    protected final void immutableSetCachedCustomResults(@NotNull final List<ResultDecorator> results)
    {
        m__lCachedCustomResults = results;
    }

    /**
     * Specifies the cached custom results.
     * @param results the results to cache.
     */
    @SuppressWarnings("unused")
    protected void setCachedCustomResults(@NotNull final List<ResultDecorator> results)
    {
        immutableSetCachedCustomResults(results);
    }

    /**
     * Retrieves the cached custom results.
     * @return such information.
     */
    @Nullable
    protected List<ResultDecorator> getCachedCustomResults()
    {
        return m__lCachedCustomResults;
    }

    /**
     * Retrieves the custom results.
     * @return such information.
     */
    @Override
    @NotNull
    public List<ResultDecorator> getCustomResults()
    {
        List<ResultDecorator> result = getCachedCustomResults();

        if (result == null)
        {
            result = super.getCustomResults();
            setCachedCustomResults(result);
        }

        return result;
    }

    /**
     * Specifies the cached child attributes.
     * @param attributes such {@link Attribute attributes}.
     */
    protected final void immutableSetCachedChildAttributes(@NotNull final List<Attribute> attributes)
    {
        this.m__lCachedChildAttributes = attributes;
    }

    /**
     * Specifies the cached child attributes.
     * @param attributes such {@link Attribute attributes}.
     */
    @SuppressWarnings("unused")
    protected void setCachedChildAttributes(@NotNull final List<Attribute> attributes)
    {
        immutableSetCachedChildAttributes(attributes);
    }

    /**
     * Retrieves the cached child attributes.
     * @return such attributes.
     */
    @Nullable
    public List<Attribute> getCachedChildAttributes()
    {
        return m__lCachedChildAttributes;
    }

    /**
     * Retrieves the child's attributes.
     * @return such list.
     */
    @Override
    @NotNull
    public List<Attribute> getChildAttributes()
    {
        List<Attribute> result = getCachedChildAttributes();

        if (result == null)
        {
            result = super.getChildAttributes();
            setCachedChildAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached foreign keys.
     * @param foreignKeys the {@link ForeignKey foreignKeys}.
     */
    protected final void immutableSetCachedForeignKeys(@NotNull final List<ForeignKey> foreignKeys)
    {
        this.m__lCachedForeignKeys = foreignKeys;
    }

    /**
     * Specifies the cached foreign keys.
     * @param foreignKeys the {@link ForeignKey foreignKeys}.
     */
    protected void setCachedForeignKeys(@NotNull final List<ForeignKey> foreignKeys)
    {
        immutableSetCachedForeignKeys(foreignKeys);
    }

    /**
     * Retrieves the cached foreign keys.
     * @return such list.
     */
    @Nullable
    public List<ForeignKey> getCachedForeignKeys()
    {
        return this.m__lCachedForeignKeys;
    }

    /**
     * Retrieves the foreign keys.
     *
     * @return such list.
     */
    @NotNull
    @Override
    public List<ForeignKey> getForeignKeys()
    {
        List<ForeignKey> result = getCachedForeignKeys();

        if (result == null)
        {
            result = super.getForeignKeys();
            setCachedForeignKeys(result);
        }

        return result;
    }

    /**
     * Specifies the cached parent foreign key.
     * @param foreignKey such {@link ForeignKey}.
     */
    protected final void immutableSetCachedParentForeignKey(@NotNull final ForeignKey foreignKey)
    {
        this.m__CachedParentForeignKey = foreignKey;
    }

    /**
     * Specifies the cached parent foreign key.
     * @param foreignKey such {@link ForeignKey}.
     */
    protected void setCachedParentForeignKey(@NotNull final ForeignKey foreignKey)
    {
        immutableSetCachedParentForeignKey(foreignKey);
    }

    /**
     * Retrieves the cached parent foreign key.
     * @return such {@link ForeignKey}.
     */
    @Nullable
    public ForeignKey getCachedParentForeignKey()
    {
        return this.m__CachedParentForeignKey;
    }

    /**
     * Specifies whether the parent foreign key was already retrieved or not.
     * @param flag such conditiion.
     */
    protected final void immutableSetParentForeignKeyAlreadyRetrieved(final boolean flag)
    {
        m__bParentForeignKeyAlreadyRetrieved = flag;
    }

    /**
     * Specifies whether the parent foreign key was already retrieved or not.
     * @param flag such conditiion.
     */
    protected void setParentForeignKeyAlreadyRetrieved(final boolean flag)
    {
        immutableSetParentForeignKeyAlreadyRetrieved(flag);
    }

    /**
     * Checks whether the parent foreign key was already retrieved or not.
     * @return <code>true</code> in such case.
     */
    public boolean isParentForeignKeyAlreadyRetrieved()
    {
        return m__bParentForeignKeyAlreadyRetrieved;
    }

    /**
     * Retrieves the parent foreign-key.
     * @return such foreign key.
     */
    @Override
    @Nullable
    public ForeignKey getParentForeignKey()
    {
        ForeignKey result = getCachedParentForeignKey();

        if (   (result == null)
            && (!isParentForeignKeyAlreadyRetrieved()))
        {
            result = super.getParentForeignKey();

            if (result != null)
            {
                setCachedParentForeignKey(result);
            }

            setParentForeignKeyAlreadyRetrieved(true);

        }

        return result;
    }

    /**
     * Specifies the cached singular name, in upper case.
     * @param value such value.
     */
    protected final void immutableSetCachedSingularNameUppercased(@NotNull final String value)
    {
        this.m__strCachedSingularNameUppercased = value;
    }

    /**
     * Specifies the cached singular name, in upper case.
     * @param value such value.
     */
    @SuppressWarnings("unused")
    protected void setCachedSingularNameUppercased(@NotNull final String value)
    {
        immutableSetCachedSingularNameUppercased(value);
    }

    /**
     * Retrieves the cached singular name, in upper case.
     * @return such value.
     */
    @Nullable
    public String getCachedSingularNameUppercased()
    {
        return m__strCachedSingularNameUppercased;
    }

    /**
     * Retrieves the singular table's name, upper-cased.
     *
     * @return such information.
     */
    @NotNull
    @Override
    public String getSingularNameUppercased()
    {
        String result = getCachedSingularNameUppercased();

        if (result == null)
        {
            result = super.getSingularNameUppercased();
            setCachedSingularNameUppercased(result);
        }

        return result;
    }


    /**
     * Specifies the cached singular name, in lower case.
     * @param value such value.
     */
    protected final void immutableSetCachedSingularNameLowercased(@NotNull final String value)
    {
        this.m__strCachedSingularNameLowercased = value;
    }

    /**
     * Specifies the cached singular name, in lower case.
     * @param value such value.
     */
    @SuppressWarnings("unused")
    protected void setCachedSingularNameLowercased(@NotNull final String value)
    {
        immutableSetCachedSingularNameLowercased(value);
    }

    /**
     * Retrieves the cached singular name, in lower case.
     * @return such value.
     */
    @Nullable
    public String getCachedSingularNameLowercased()
    {
        return m__strCachedSingularNameLowercased;
    }

    /**
     * Retrieves the singular table's name, lower-cased.
     *
     * @return such information.
     */
    @NotNull
    @Override
    public String getSingularNameLowercased()
    {
        String result = getCachedSingularNameLowercased();

        if (result == null)
        {
            result = super.getSingularNameLowercased();
            setCachedSingularNameLowercased(result);
        }

        return result;
    }

    /**
     * Specifies the cached read-only attributes.
     * @param list such {@link Attribute} list.
     */
    protected final void immutableSetCachedReadOnlyAttributes(@NotNull final List<Attribute> list)
    {
        this.m__lCachedReadOnlyAttributes = list;
    }

    /**
     * Specifies the cached read-only attributes.
     * @param list such {@link Attribute} list.
     */
    @SuppressWarnings("unused")
    protected void setCachedReadOnlyAttributes(@NotNull final List<Attribute> list)
    {
        immutableSetCachedReadOnlyAttributes(list);
    }

    /**
     * Retrieves the cached read-only attributes.
     * @return such list.
     */
    @Nullable
    public List<Attribute> getCachedReadOnlyAttributes()
    {
        return m__lCachedReadOnlyAttributes;
    }

    /**
     * Retrieves the read-only attributes.
     *
     * @return such list.
     */
    @NotNull
    @Override
    public List<Attribute> getReadOnlyAttributes()
    {
        List<Attribute> result = getCachedReadOnlyAttributes();

        if (result == null)
        {
            result = super.getReadOnlyAttributes();
            setCachedReadOnlyAttributes(result);
        }

        return result;
    }

    /**
     * Retrieves the custom select-for-update queries.
     *
     * @return such list of {@link org.acmsl.queryj.customsql.Sql} elements.
     */
    @NotNull
    @Override
    public List<Sql> getCustomSelectsForUpdate()
    {
        return super
            .getCustomSelectsForUpdate();    //To change body of overridden methods use File | Settings | File
            // Templates.
    }

    /**
     * Specifies the cached, externally-managed attributes.
     * @param list such {@link AttributeDecorator attributes}.
     */
    protected final void immutableSetCachedAllExternallyManagedAttributes(@NotNull final List<AttributeDecorator> list)
    {
        this.m__lCachedAllExternallyManagedAttributes = list;
    }

    /**
     * Specifies the cached, externally-managed attributes.
     * @param list such {@link AttributeDecorator attributes}.
     */
    @SuppressWarnings("unused")
    protected void setCachedAllExternallyManagedAttributes(@NotNull final List<AttributeDecorator> list)
    {
        immutableSetCachedAllExternallyManagedAttributes(list);
    }

    /**
     * Retrieves the cached, externally-managed attributes.
     * @return such {@link AttributeDecorator list}.
     */
    @Nullable
    protected List<AttributeDecorator> getCachedAllExternallyManagedAttributes()
    {
        return m__lCachedAllExternallyManagedAttributes;
    }
    /**
     * Retrieves the externally-managed attributes.
     *
     * @return such information.
     */
    @NotNull
    @Override
    public List<AttributeDecorator> getAllExternallyManagedAttributes()
    {
        List<AttributeDecorator> result = getCachedAllExternallyManagedAttributes();

        if (result == null)
        {
            result = super.getAllExternallyManagedAttributes();
            setCachedAllExternallyManagedAttributes(result);
        }

        return result;
    }

    /**
     * Workaround for debugging templates.
     * @return true.
     */
    @SuppressWarnings("unused")
    public boolean getStStuff()
    {
        return true;
    }
}
