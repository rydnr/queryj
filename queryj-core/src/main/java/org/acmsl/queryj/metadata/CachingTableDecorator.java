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
import org.acmsl.queryj.customsql.Result;
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
import java.util.ArrayList;
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Adds a simple caching mechanism while decorating <code>Table</code>
 * instances.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
@ThreadSafe
public class CachingTableDecorator
    extends AbstractTableDecorator
{

    private static final long serialVersionUID = 7125795956047030470L;
    /**
     * The cached primary key.
     */
    private List<Attribute<DecoratedString>> m__lCachedPrimaryKey;

    /**
     * The cached non-readonly attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedNonReadOnlyAttributes;

    /**
     * The cached non-readonly attributes, including parent's.
     */
    private List<Attribute<DecoratedString>> m__lCachedAllNonReadOnlyAttributes;

    /**
     * The cached non-parent attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedNonParentAttributes;

    /**
     * The cached parent table.
     */
    private Table<DecoratedString, Attribute<DecoratedString>> m__CachedParentTable;

    /**
     * The cached all attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedAllAttributes;

    /**
     * The cached attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedAttributes;

    /**
     * The cached non-parent non-managed-externally attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedNonParentNonExternallyManagedAttributes;

    /**
     * The cached parent's all attributes and the non-parent own attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedAllParentAndNonParentAttributes;

    /**
     * The cached parent's all attributes and the non-parent non-managed-externally attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedAllParentAndNonParentNonExternallyManagedAttributes;

    /**
     * The cached all non-managed externally attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedAllNonExternallyManagedAttributes;

    /**
     * The cached all parent and non-parent non-managed-externally read-only attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes;

    /**
     * The cached all parent and non-parent non-read-only attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedAllParentAndNonParentNonReadOnlyAttributes;

    /**
     * The cached all parent and non-parent read-only attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedAllParentAndNonParentReadOnlyAttributes;

    /**
     * The cached non-parent non-managed-externally attributes, plus the primary key.
     */
    private List<Attribute<DecoratedString>> m__lCachedNonParentNonExternallyManagedPlusPkAttributes;

    /**
     * The cached all non-managed-externally attributes, plus the primary key.
     */
    private List<Attribute<DecoratedString>> m__lCachedAllNonExternallyManagedPlusPkAttributes;

    /**
     * The cached all non-managed-externally, non-readonly attributes, plus the primary key.
     */
    private List<Attribute<DecoratedString>> m__lCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes;

    /**
     * The cached all parent and non-parent non-managed-externally, non-readonly attributes,
     * plus the primary key.
     */
    private List<Attribute<DecoratedString>> m__lCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes;

    /**
     * The cached non-parent attributes, plus the primary key.
     */
    private List<Attribute<DecoratedString>> m__lCachedNonParentPlusPkAttributes;

    /**
     * The cached non-readonly, except the externally-managed attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedAllNonReadOnlyButExternallyManagedAttributes;

    /**
     * The cached non-primary-key, non-readonly attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedNonPrimaryKeyNonReadOnlyAttributes;

    /**
     * The cached all parent tables.
     */
    private List<Table<DecoratedString, Attribute<DecoratedString>>> m__lCachedAllParentTables;

    /**
     * The cached static rows.
     */
    private List<Row<DecoratedString>> m__lCachedStaticContents;

    /**
     * The cached dynamic queries.
     */
    private List<Sql> m__lCachedDynamicQueries;

    /**
     * The cached non-primary-key attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedNonPrimaryKeyAttributes;

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
    private List<Attribute<DecoratedString>> m__lCachedChildAttributes;

    /**
     * The cached foreign keys.
     */
    private List<ForeignKey<DecoratedString>> m__lCachedForeignKeys;

    /**
     * The cached parent foreign key.
     */
    private ForeignKey<DecoratedString> m__CachedParentForeignKey;

    /**
     * Whether the parent foreign key was already retrieved.
     */
    private boolean m__bParentForeignKeyAlreadyRetrieved;

    /**
     * The cached read-only attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedReadOnlyAttributes;

    /**
     * The cached non-read only, non-primary key attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedNonPrimaryKeyReadOnlyAttributes;

    /**
     * The cached externally-managed attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedAllExternallyManagedAttributes;

    /**
     * The cached list of different results.
     */
    private List<Result> m__lCachedDifferentCustomResults;

    /**
     * Creates a <code>CachingTableDecorator</code> with the
     * <code>Table</code> to decorate.
     * @param table the table.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the decorator factory.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     */
    public CachingTableDecorator(
        @NotNull final Table<String, Attribute<String>> table,
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
    protected final void immutableSetCachedPrimaryKey(@NotNull final List<Attribute<DecoratedString>> list)
    {
        m__lCachedPrimaryKey = list;
    }

    /**
     * Specifies the cached primary key.
     * @param list the attribute list.
     */
    protected void setCachedPrimaryKey(@NotNull final List<Attribute<DecoratedString>> list)
    {
        immutableSetCachedPrimaryKey(list);
    }

    /**
     * Retrieves the cached primary key.
     * @return such list.
     */
    @Nullable
    public List<Attribute<DecoratedString>> getCachedPrimaryKey()
    {
        return m__lCachedPrimaryKey;
    }

    /**
     * Retrieves the primary key attributes.
     * @return such list.
     */
    @Override
    @NotNull
    public List<Attribute<DecoratedString>> getPrimaryKey()
    {
        @Nullable List<Attribute<DecoratedString>> result = getCachedPrimaryKey();

        if (result == null)
        {
            result = super.getPrimaryKey();

            setCachedPrimaryKey(result);
        }

        return result;
    }


    /**
     * Specifies the cached all attributes.
     * @param attributes the attributes.
     */
    protected final void immutableSetCachedAllAttributes(
        @NotNull final List<Attribute<DecoratedString>> attributes)
    {
        m__lCachedAllAttributes = attributes;
    }

    /**
     * Specifies the cached all attributes.
     * @param attributes the attributes.
     */
    protected void setCachedAllAttributes(@NotNull final List<Attribute<DecoratedString>> attributes)
    {
        immutableSetCachedAllAttributes(attributes);
    }

    /**
     * Retrieves the cached all attributes.
     * @return such attributes.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedAllAttributes()
    {
        return m__lCachedAllAttributes;
    }

    /**
     * Retrieves the attributes.
     * @return such information.
     */
    @NotNull
    public List<Attribute<DecoratedString>> getAllAttributes()
    {
        List<Attribute<DecoratedString>> result = getCachedAllAttributes();

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
    protected final void immutableSetCachedAttributes(@NotNull final List<Attribute<DecoratedString>> attributes)
    {
        m__lCachedAttributes = attributes;
    }

    /**
     * Specifies the cached attributes.
     * @param attributes the attributes.
     */
    protected void setCachedAttributes(@NotNull final List<Attribute<DecoratedString>> attributes)
    {
        immutableSetCachedAttributes(attributes);
    }

    /**
     * Retrieves the cached attributes.
     * @return such attributes.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedAttributes()
    {
        return m__lCachedAttributes;
    }

    /**
     * Retrieves the attributes.
     * @return such information.
     */
    @NotNull
    public List<Attribute<DecoratedString>> getAttributes()
    {
        List<Attribute<DecoratedString>> result = getCachedAttributes();

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
    protected final void immutableSetCachedParentTable(
        @NotNull final Table<DecoratedString, Attribute<DecoratedString>> parent)
    {
        m__CachedParentTable = parent;
    }

    /**
     * Specifies the cached parent table.
     * @param parent such table.
     */
    protected void setCachedParentTable(@NotNull final Table<DecoratedString, Attribute<DecoratedString>> parent)
    {
        immutableSetCachedParentTable(parent);
    }

    /**
     * Retrieves the cached parent table.
     * @return such table.
     */
    @Nullable
    public Table<DecoratedString, Attribute<DecoratedString>> getCachedParentTable()
    {
        return m__CachedParentTable;
    }

    /**
     * Retrieves the parent table.
     * @return such information.
     */
    @Nullable
    public Table<DecoratedString, Attribute<DecoratedString>> getParentTable()
    {
        @Nullable Table<DecoratedString, Attribute<DecoratedString>> result = getCachedParentTable();

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
    protected final void immutableSetCachedNonReadOnlyAttributes(@NotNull final List<Attribute<DecoratedString>> attrs)
    {
        m__lCachedNonReadOnlyAttributes = attrs;
    }

    /**
     * Specifies the cached, non-read-only attributes.
     * @param attrs such attributes.
     */
    protected void setCachedNonReadOnlyAttributes(@NotNull final List<Attribute<DecoratedString>> attrs)
    {
        immutableSetCachedNonReadOnlyAttributes(attrs);
    }

    /**
     * Retrieves the cached, non-read-only attributes.
     * @return such information.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedNonReadOnlyAttributes()
    {
        return m__lCachedNonReadOnlyAttributes;
    }

    /**
     * Retrieves the non-read-only attributes.
     * @return such attributes.
     */
    @Override
    @NotNull
    public List<Attribute<DecoratedString>> getNonReadOnlyAttributes()
    {
        List<Attribute<DecoratedString>> result = getCachedNonReadOnlyAttributes();

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
    protected final void immutableSetCachedAllNonReadOnlyAttributes(@NotNull final List<Attribute<DecoratedString>> attrs)
    {
        m__lCachedAllNonReadOnlyAttributes = attrs;
    }

    /**
     * Specifies the cached, non-read-only attributes, including parent's.
     * @param attrs such attributes.
     */
    protected void setCachedAllNonReadOnlyAttributes(@NotNull final List<Attribute<DecoratedString>> attrs)
    {
        immutableSetCachedAllNonReadOnlyAttributes(attrs);
    }

    /**
     * Retrieves the cached, non-read-only attributes, including parent's.
     * @return such information.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedAllNonReadOnlyAttributes()
    {
        return m__lCachedAllNonReadOnlyAttributes;
    }

    /**
     * Retrieves the non-read-only attributes, including parent's.
     * @return such attributes.
     */
    @Override
    @NotNull
    public List<Attribute<DecoratedString>> getAllNonReadOnlyAttributes()
    {
        List<Attribute<DecoratedString>> result = getCachedAllNonReadOnlyAttributes();

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
    protected final void immutableSetCachedNonParentAttributes(@NotNull final List<Attribute<DecoratedString>> attrs)
    {
        m__lCachedNonParentAttributes = attrs;
    }

    /**
     * Specifies the cached, non-parent attributes.
     * @param attrs such attributes.
     */
    protected void setCachedNonParentAttributes(@NotNull final List<Attribute<DecoratedString>> attrs)
    {
        immutableSetCachedNonParentAttributes(attrs);
    }

    /**
     * Retrieves the cached, non-parent attributes.
     * @return such information.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedNonParentAttributes()
    {
        return m__lCachedNonParentAttributes;
    }

    /**
     * Retrieves the non-parent attributes.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute<DecoratedString>> getNonParentAttributes()
    {
        @Nullable List<Attribute<DecoratedString>> result = getCachedNonParentAttributes();

        if  (   (result == null)
             || (result.size() == 0))
        {
            result = super.getNonParentAttributes();
            setCachedNonParentAttributes(result);
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
    @Nullable
    protected Table<DecoratedString, Attribute<DecoratedString>> createTableDecorator(
        @Nullable final String parentTable,
        @NotNull final List<Attribute<String>> primaryKey,
        @NotNull final List<Attribute<String>> attributes,
        final boolean isStatic,
        final boolean voDecorated,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        @Nullable TableDecorator result = null;

        @Nullable Table<String, Attribute<String>> t_ParentTable = null;

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
    protected TableDecorator createTableDecorator(
        @NotNull final Table<String, Attribute<String>> parentTable,
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
    protected final void immutableSetCachedNonParentNonExternallyManagedAttributes(
        @NotNull final List<Attribute<DecoratedString>> list)
    {
        m__lCachedNonParentNonExternallyManagedAttributes = list;
    }

    /**
     * Specifies the cached list of non-parent, non-externally-managed
     * attributes.
     * @param list such list.
     */
    protected void setCachedNonParentNonExternallyManagedAttributes(
        @NotNull final List<Attribute<DecoratedString>> list)
    {
        immutableSetCachedNonParentNonExternallyManagedAttributes(list);
    }

    /**
     * Retrieves the list of non-parent, non-externally-managed
     * attributes.
     * @return such list.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedNonParentNonExternallyManagedAttributes()
    {
        return m__lCachedNonParentNonExternallyManagedAttributes;
    }

    /**
     * Retrieves the list of non-parent, non-externally-managed
     * attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute<DecoratedString>> getNonParentNonExternallyManagedAttributes()
    {
        @Nullable List<Attribute<DecoratedString>> result = getCachedNonParentNonExternallyManagedAttributes();

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
    protected final void immutableSetCachedNonPrimaryKeyAttributes(
        @NotNull final List<Attribute<DecoratedString>> attributes)
    {
        m__lCachedNonPrimaryKeyAttributes = attributes;
    }

    /**
     * Specifies the cached non-primary-key attributes.
     * @param attributes the attributes to cache.
     */
    protected void setCachedNonPrimaryKeyAttributes(@NotNull final List<Attribute<DecoratedString>> attributes)
    {
        immutableSetCachedNonPrimaryKeyAttributes(attributes);
    }

    /**
     * Retrieves the cached non-primary-key attributes.
     * @return the attributes to cache.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedNonPrimaryKeyAttributes()
    {
        return m__lCachedNonPrimaryKeyAttributes;
    }

    /**
     * Retrieves the non-primary-key attributes.
     * @return such list.
     */
    @Override
    @NotNull
    public List<Attribute<DecoratedString>> getNonPrimaryKeyAttributes()
    {
        @Nullable List<Attribute<DecoratedString>> result = getCachedNonPrimaryKeyAttributes();

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
    protected final void immutableSetCachedAllParentAndNonParentAttributes(
        @NotNull final List<Attribute<DecoratedString>> list)
    {
        m__lCachedAllParentAndNonParentAttributes = list;
    }

    /**
     * Specifies the cached list of parent's all attributes and the non-parent own attributes.
     * @param list such list.
     */
    protected void setCachedAllParentAndNonParentAttributes(@NotNull final List<Attribute<DecoratedString>> list)
    {
        immutableSetCachedAllParentAndNonParentAttributes(list);
    }

    /**
     * Retrieves the cached list of parent's all attributes and the non-parent own attributes.
     * @return such list.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedAllParentAndNonParentAttributes()
    {
        return m__lCachedAllParentAndNonParentAttributes;
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent own attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute<DecoratedString>> getAllParentAndNonParentAttributes()
    {
        @Nullable List<Attribute<DecoratedString>> result = getCachedAllParentAndNonParentAttributes();

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
        @NotNull final List<Attribute<DecoratedString>> list)
    {
        m__lCachedAllParentAndNonParentNonExternallyManagedAttributes = list;
    }

    /**
     * Specifies the cached list of parent's all attributes and the non-parent
     * non-managed-externally own attributes.
     * @param list such list.
     */
    protected void setCachedAllParentAndNonParentNonExternallyManagedAttributes(@NotNull final List<Attribute<DecoratedString>> list)
    {
        immutableSetCachedAllParentAndNonParentNonExternallyManagedAttributes(list);
    }

    /**
     * Retrieves the cached list of parent's all attributes and the non-parent
     * non-managed-externally own attributes.
     * @return such list.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedAllParentAndNonParentNonExternallyManagedAttributes()
    {
        return m__lCachedAllParentAndNonParentNonExternallyManagedAttributes;
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally own attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute<DecoratedString>> getAllParentAndNonParentNonExternallyManagedAttributes()
    {
        @Nullable List<Attribute<DecoratedString>> result = getCachedAllParentAndNonParentNonExternallyManagedAttributes();

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
    protected final void immutableSetCachedAllNonExternallyManagedAttributes(
        @NotNull final List<Attribute<DecoratedString>> list)
    {
        m__lCachedAllNonExternallyManagedAttributes = list;
    }

    /**
     * Specifies cached all attributes, including the parent's, but not the externally-managed.
     * @param list such attributes.
     */
    protected void setCachedAllNonExternallyManagedAttributes(@NotNull final List<Attribute<DecoratedString>> list)
    {
        immutableSetCachedAllNonExternallyManagedAttributes(list);
    }

    /**
     * Retrieves cached all attributes, including the parent's, but not the externally-managed.
     * @return such attributes.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedAllNonExternallyManagedAttributes()
    {
        return m__lCachedAllNonExternallyManagedAttributes;
    }

    /**
     * Retrieves cached all attributes, including the parent's, but not the externally-managed.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute<DecoratedString>> getAllNonExternallyManagedAttributes()
    {
        @Nullable List<Attribute<DecoratedString>> result = getCachedAllNonExternallyManagedAttributes();

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
        @NotNull final List<Attribute<DecoratedString>> list)
    {
        m__lCachedAllNonReadOnlyButExternallyManagedAttributes = list;
    }

    /**
     * Specifies the cached non-readonly, except the externally-managed attributes.
     * @param list such list.
     */
    protected void setCachedAllNonReadOnlyButExternallyManagedAttributes(
        @NotNull final List<Attribute<DecoratedString>> list)
    {
        immutableSetCachedAllNonReadOnlyButExternallyManagedAttributes(list);
    }

    /**
     * Specifies the cached non-readonly, except the externally-managed attributes.
     * @return such list.
     */
    @Nullable
    public List<Attribute<DecoratedString>> getCachedAllNonReadOnlyButExternallyManagedAttributes()
    {
        return m__lCachedAllNonReadOnlyButExternallyManagedAttributes;
    }

    /**
     * Specifies the cached non-readonly, except the externally-managed attributes.
     * @return such list.
     */
    @Override
    @NotNull
    public List<Attribute<DecoratedString>> getAllNonReadOnlyButExternallyManagedAttributes()
    {
        @Nullable List<Attribute<DecoratedString>> result = getCachedAllNonReadOnlyButExternallyManagedAttributes();

        if (result == null)
        {
            result = super.getAllNonReadOnlyButExternallyManagedAttributes();

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
        @NotNull final List<Attribute<DecoratedString>> list)
    {
        m__lCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes = list;
    }

    /**
     * Specifies the cached list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes.
     * @param list such list.
     */
    protected void setCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes(
        @NotNull final List<Attribute<DecoratedString>> list)
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
    protected List<Attribute<DecoratedString>> getCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes()
    {
        return m__lCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes;
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute<DecoratedString>> getAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes()
    {
        @Nullable List<Attribute<DecoratedString>> result =
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
        @NotNull final List<Attribute<DecoratedString>> list)
    {
        m__lCachedAllParentAndNonParentNonReadOnlyAttributes = list;
    }

    /**
     * Specifies the cached value for all parent + non parent, non-read-only
     * attributes.
     * @param list the list.
     */
    protected void setCachedAllParentAndNonParentNonReadOnlyAttributes(
        @NotNull final List<Attribute<DecoratedString>> list)
    {
        immutableSetCachedAllParentAndNonParentNonReadOnlyAttributes(list);
    }

    /**
     * Retrieves the cached value for all parent + non parent, non-read-only
     * attributes.
     * @return such list.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedAllParentAndNonParentNonReadOnlyAttributes()
    {
        return m__lCachedAllParentAndNonParentNonReadOnlyAttributes;
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent,
     * non-read-only own attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute<DecoratedString>> getAllParentAndNonParentNonReadOnlyAttributes()
    {
        @Nullable List<Attribute<DecoratedString>> result = getCachedAllParentAndNonParentNonReadOnlyAttributes();

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
        @NotNull final List<Attribute<DecoratedString>> list)
    {
        m__lCachedAllParentAndNonParentReadOnlyAttributes = list;
    }

    /**
     * Specifies the cached list of all parent's and non parent's read-only attributes.
     * @param list such list.
     */
    protected void setCachedAllParentAndNonParentReadOnlyAttributes(@NotNull final List<Attribute<DecoratedString>> list)
    {
        immutableSetCachedAllParentAndNonParentReadOnlyAttributes(list);
    }

    /**
     * Retrieves the cached list of all parent's and non parent's read-only attributes.
     * @return such condition.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedAllParentAndNonParentReadOnlyAttributes()
    {
        return m__lCachedAllParentAndNonParentReadOnlyAttributes;
    }

    /**
     * Retrieves the all parent plus non-parent read-only attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute<DecoratedString>> getAllParentAndNonParentReadOnlyAttributes()
    {
        @Nullable List<Attribute<DecoratedString>> result = getCachedAllParentAndNonParentReadOnlyAttributes();

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
        @NotNull final List<Attribute<DecoratedString>> list)
    {
        m__lCachedNonParentNonExternallyManagedPlusPkAttributes = list;
    }

    /**
     * Specifies the cached list of non-parent, non-externally-managed attributes,
     * plus the primary key.
     * @param list such list.
     */
    protected void setCachedNonParentNonExternallyManagedPlusPkAttributes(@NotNull final List<Attribute<DecoratedString>> list)
    {
        immutableSetCachedNonParentNonExternallyManagedPlusPkAttributes(list);
    }

    /**
     * Retrieves the cached list of non-parent, non-externally-managed attributes,
     * plus the primary key.
     * @return such list.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedNonParentNonExternallyManagedPlusPkAttributes()
    {
        return m__lCachedNonParentNonExternallyManagedPlusPkAttributes;
    }

    /**
     * Retrieves the list of non-parent, non-externally-managed
     * attributes, plus the primary key.
     * @return such list.
     */
    @NotNull
    public List<Attribute<DecoratedString>> getNonParentNonExternallyManagedPlusPkAttributes()
    {
        @Nullable List<Attribute<DecoratedString>> result = getCachedNonParentNonExternallyManagedPlusPkAttributes();

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
        @NotNull final List<Attribute<DecoratedString>> list)
    {
        m__lCachedAllNonExternallyManagedPlusPkAttributes = list;
    }

    /**
     * Specifies all attributes, including the parent's, but not the externally-managed,
     * plus the primary key.
     * @param list the list.
     */
    protected void setCachedAllNonExternallyManagedPlusPkAttributes(@NotNull final List<Attribute<DecoratedString>> list)
    {
        immutableSetCachedAllNonExternallyManagedPlusPkAttributes(list);
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * plus the primary key.
     * @return such attributes.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedAllNonExternallyManagedPlusPkAttributes()
    {
        return m__lCachedAllNonExternallyManagedPlusPkAttributes;
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * plus the primary key.
     * @return such attributes.
     */
    @Override
    @NotNull
    public List<Attribute<DecoratedString>> getAllNonExternallyManagedPlusPkAttributes()
    {
        @Nullable List<Attribute<DecoratedString>> result = getCachedAllNonExternallyManagedPlusPkAttributes();

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
        @NotNull final List<Attribute<DecoratedString>> list)
    {
        m__lCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes = list;
    }

    /**
     * Specifies all attributes, including the parent's, but not the externally-managed,
     * or read-only, plus the primary key.
     * @param list the list.
     */
    protected void setCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes(@NotNull final List<Attribute<DecoratedString>> list)
    {
        immutableSetCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes(list);
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * or read-only, plus the primary key.
     * @return such attributes.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes()
    {
        return m__lCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes;
    }

    /**
     * Retrieves all attributes, including the parent's, but not the externally-managed,
     * or read-only, plus the primary key.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute<DecoratedString>> getAllNonExternallyManagedNonReadOnlyPlusPkAttributes()
    {
        @Nullable List<Attribute<DecoratedString>> result = getCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes();

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
        @NotNull final List<Attribute<DecoratedString>> list)
    {
        m__lCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes = list;
    }

    /**
     * Specifies the cached list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes, including the primary key.
     * @param list such list.
     */
    protected void setCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes(
        @NotNull final List<Attribute<DecoratedString>> list)
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
    protected List<Attribute<DecoratedString>> getCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes()
    {
        return m__lCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes;
    }

    /**
     * Retrieves the list of parent's all attributes and the non-parent
     * non-managed-externally, non-read-only own attributes, including the primary key.
     * @return such list.
     */
    @NotNull
    public List<Attribute<DecoratedString>> getAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes()
    {
        @Nullable List<Attribute<DecoratedString>> result =
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
    protected final void immutableSetCachedNonParentPlusPkAttributes(@NotNull final List<Attribute<DecoratedString>> attrs)
    {
        m__lCachedNonParentPlusPkAttributes = attrs;
    }

    /**
     * Specifies the cached, non-parent attributes, plus the primary key.
     * @param attrs such attributes.
     */
    protected void setCachedNonParentPlusPkAttributes(@NotNull final List<Attribute<DecoratedString>> attrs)
    {
        immutableSetCachedNonParentPlusPkAttributes(attrs);
    }

    /**
     * Retrieves the cached, non-parent attributes, plus the primary key.
     * @return such information.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedNonParentPlusPkAttributes()
    {
        return m__lCachedNonParentPlusPkAttributes;
    }

    /**
     * Retrieves the non-parent attributes, plus the primary key.
     * @return such attributes.
     */
    @NotNull
    public List<Attribute<DecoratedString>> getNonParentPlusPkAttributes()
    {
        @Nullable List<Attribute<DecoratedString>> result = getCachedNonParentPlusPkAttributes();

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
    protected final void immutableSetCachedAllParentTables(@NotNull final List<Table<DecoratedString, Attribute<DecoratedString>>> list)
    {
        m__lCachedAllParentTables = list;
    }

    /**
     * Specifies the cached all parent tables.
     * @param list such list.
     */
    protected void setCachedAllParentTables(@NotNull final List<Table<DecoratedString, Attribute<DecoratedString>>> list)
    {
        immutableSetCachedAllParentTables(list);
    }

    /**
     * Retrieves the cached all parent tables.
     * @return such list.
     */
    @Nullable
    protected List<Table<DecoratedString, Attribute<DecoratedString>>> getCachedAllParentTables()
    {
        return m__lCachedAllParentTables;
    }

    /**
     * Retrieves all parent tables.
     * @return such information.
     */
    @NotNull
    public List<Table<DecoratedString, Attribute<DecoratedString>>> getAllParentTables()
    {
        @Nullable List<Table<DecoratedString, Attribute<DecoratedString>>> result = getCachedAllParentTables();

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
    protected final void immutableSetCachedStaticContent(@NotNull final List<Row<DecoratedString>> rows)
    {
        m__lCachedStaticContents = rows;
    }

    /**
     * Specifies the cached static contents.
     * @param rows the rows.
     */
    protected void setCachedStaticContent(@NotNull final List<Row<DecoratedString>> rows)
    {
        immutableSetCachedStaticContent(rows);
    }

    /**
     * Retrieves the cached static contents.
     * @return such rows.
     */
    @Nullable
    public List<Row<DecoratedString>> getCachedStaticContent()
    {
        return m__lCachedStaticContents;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public List<Row<DecoratedString>> getStaticContent()
    {
        @Nullable List<Row<DecoratedString>> result = getCachedStaticContent();

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
        @NotNull final List<Attribute<DecoratedString>> attributes)
    {
        m__lCachedNonPrimaryKeyNonReadOnlyAttributes = attributes;
    }

    /**
     * Specifies the cached non-primary-key, non-readonly attributes.
     * @param attributes the attributes to cache.
     */
    protected void setCachedNonPrimaryKeyNonReadOnlyAttributes(
        @NotNull final List<Attribute<DecoratedString>> attributes)
    {
        immutableSetCachedNonPrimaryKeyNonReadOnlyAttributes(attributes);
    }

    /**
     * Retrieves the cached non-primary-key, non-readonly attributes.
     * @return the cached attributes.
     */
    protected List<Attribute<DecoratedString>> getCachedNonPrimaryKeyNonReadOnlyAttributes()
    {
        return m__lCachedNonPrimaryKeyNonReadOnlyAttributes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public List<Attribute<DecoratedString>> getNonPrimaryKeyNonReadOnlyAttributes()
    {
        @Nullable List<Attribute<DecoratedString>> result = getCachedNonPrimaryKeyNonReadOnlyAttributes();

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
    protected final void immutableSetCachedChildAttributes(@NotNull final List<Attribute<DecoratedString>> attributes)
    {
        this.m__lCachedChildAttributes = attributes;
    }

    /**
     * Specifies the cached child attributes.
     * @param attributes such {@link Attribute attributes}.
     */
    protected void setCachedChildAttributes(@NotNull final List<Attribute<DecoratedString>> attributes)
    {
        immutableSetCachedChildAttributes(attributes);
    }

    /**
     * Retrieves the cached child attributes.
     * @return such attributes.
     */
    @Nullable
    public List<Attribute<DecoratedString>> getCachedChildAttributes()
    {
        return m__lCachedChildAttributes;
    }

    /**
     * Retrieves the child's attributes.
     * @return such list.
     */
    @Override
    @NotNull
    public List<Attribute<DecoratedString>> getChildAttributes()
    {
        List<Attribute<DecoratedString>> result = getCachedChildAttributes();

        if (result == null)
        {
            result = super.getChildAttributes();
            if (result == null)
            {
                result = new ArrayList<Attribute<DecoratedString>>(0);
            }
            setCachedChildAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached foreign keys.
     * @param foreignKeys the {@link ForeignKey foreignKeys}.
     */
    protected final void immutableSetCachedForeignKeys(@NotNull final List<ForeignKey<DecoratedString>> foreignKeys)
    {
        this.m__lCachedForeignKeys = foreignKeys;
    }

    /**
     * Specifies the cached foreign keys.
     * @param foreignKeys the {@link ForeignKey foreignKeys}.
     */
    protected void setCachedForeignKeys(@NotNull final List<ForeignKey<DecoratedString>> foreignKeys)
    {
        immutableSetCachedForeignKeys(foreignKeys);
    }

    /**
     * Retrieves the cached foreign keys.
     * @return such list.
     */
    @Nullable
    public List<ForeignKey<DecoratedString>> getCachedForeignKeys()
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
    public List<ForeignKey<DecoratedString>> getForeignKeys()
    {
        List<ForeignKey<DecoratedString>> result = getCachedForeignKeys();

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
    protected final void immutableSetCachedParentForeignKey(@NotNull final ForeignKey<DecoratedString> foreignKey)
    {
        this.m__CachedParentForeignKey = foreignKey;
    }

    /**
     * Specifies the cached parent foreign key.
     * @param foreignKey such {@link ForeignKey}.
     */
    protected void setCachedParentForeignKey(@NotNull final ForeignKey<DecoratedString> foreignKey)
    {
        immutableSetCachedParentForeignKey(foreignKey);
    }

    /**
     * Retrieves the cached parent foreign key.
     * @return such {@link ForeignKey}.
     */
    @Nullable
    public ForeignKey<DecoratedString> getCachedParentForeignKey()
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
    public ForeignKey<DecoratedString> getParentForeignKey()
    {
        ForeignKey<DecoratedString> result = getCachedParentForeignKey();

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
     * Specifies the cached read-only attributes.
     * @param list such {@link Attribute} list.
     */
    protected final void immutableSetCachedReadOnlyAttributes(@NotNull final List<Attribute<DecoratedString>> list)
    {
        this.m__lCachedReadOnlyAttributes = list;
    }

    /**
     * Specifies the cached read-only attributes.
     * @param list such {@link Attribute} list.
     */
    protected void setCachedReadOnlyAttributes(@NotNull final List<Attribute<DecoratedString>> list)
    {
        immutableSetCachedReadOnlyAttributes(list);
    }

    /**
     * Retrieves the cached read-only attributes.
     * @return such list.
     */
    @Nullable
    public List<Attribute<DecoratedString>> getCachedReadOnlyAttributes()
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
    public List<Attribute<DecoratedString>> getReadOnlyAttributes()
    {
        List<Attribute<DecoratedString>> result = getCachedReadOnlyAttributes();

        if (result == null)
        {
            result = super.getReadOnlyAttributes();
            setCachedReadOnlyAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached non-read-only, non-primary-key attributes.
     * @param list such {@link Attribute} list.
     */
    protected final void immutableSetCachedNonPrimaryKeyReadOnlyAttributes(@NotNull final List<Attribute<DecoratedString>> list)
    {
        this.m__lCachedNonPrimaryKeyReadOnlyAttributes = list;
    }

    /**
     * Specifies the cached non-read-only, non-primary-key attributes.
     * @param list such {@link Attribute} list.
     */
    protected void setCachedNonPrimaryKeyReadOnlyAttributes(@NotNull final List<Attribute<DecoratedString>> list)
    {
        immutableSetCachedNonPrimaryKeyReadOnlyAttributes(list);
    }

    /**
     * Retrieves the cached non-read-only, non-primary-key attributes.
     * @return such list.
     */
    @Nullable
    public List<Attribute<DecoratedString>> getCachedNonPrimaryKeyReadOnlyAttributes()
    {
        return m__lCachedNonPrimaryKeyReadOnlyAttributes;
    }

    /**
     * Retrieves the non-read-only, non-primary-key attributes.
     * @return such list.
     */
    @Override
    @NotNull
    public List<Attribute<DecoratedString>> getNonPrimaryKeyReadOnlyAttributes()
    {
        List<Attribute<DecoratedString>> result = getCachedNonPrimaryKeyReadOnlyAttributes();

        if (result == null)
        {
            result = super.getNonPrimaryKeyReadOnlyAttributes();
            setCachedNonPrimaryKeyReadOnlyAttributes(result);
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
        protected final void immutableSetCachedAllExternallyManagedAttributes(@NotNull final List<Attribute<DecoratedString>> list)
    {
        this.m__lCachedAllExternallyManagedAttributes = list;
    }

    /**
     * Specifies the cached, externally-managed attributes.
     * @param list such {@link AttributeDecorator attributes}.
     */
    protected void setCachedAllExternallyManagedAttributes(@NotNull final List<Attribute<DecoratedString>> list)
    {
        immutableSetCachedAllExternallyManagedAttributes(list);
    }

    /**
     * Retrieves the cached, externally-managed attributes.
     * @return such {@link AttributeDecorator list}.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedAllExternallyManagedAttributes()
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
    public List<Attribute<DecoratedString>> getAllExternallyManagedAttributes()
    {
        List<Attribute<DecoratedString>> result = getCachedAllExternallyManagedAttributes();

        if (result == null)
        {
            result = super.getAllExternallyManagedAttributes();
            setCachedAllExternallyManagedAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached different custom results.
     * @param results such results.
     */
    protected final void immutableSetCachedDifferentCustomResults(@NotNull final List<Result> results)
    {
        this.m__lCachedDifferentCustomResults = results;
    }

    /**
     * Specifies the cached different custom results.
     * @param results such results.
     */
    protected void setCachedDifferentCustomResults(@NotNull final List<Result> results)
    {
        immutableSetCachedDifferentCustomResults(results);
    }

    /**
     * Retrieves the cached different custom results.
     * @return such results.
     A*/
    @Nullable
    public List<Result> getCachedDifferentCustomResults()
    {
        return this.m__lCachedDifferentCustomResults;
    }

    /**
     * Retrieves the list of different results defined for this table (using the referring custom-selects).
     * @return such list.
     */
    @NotNull
    @Override
    public List<Result> getDifferentCustomResults()
    {
        List<Result> result = getCachedDifferentCustomResults();

        if (result == null)
        {
            result = super.getDifferentCustomResults();
            setCachedDifferentCustomResults(result);
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

    @NotNull
    @Override
    public String toString()
    {
        return
            // TODO !!!
            "CachingTableDecorator{" +
               "m__bParentForeignKeyAlreadyRetrieved=" + m__bParentForeignKeyAlreadyRetrieved +
               ", m__lCachedPrimaryKey=" + m__lCachedPrimaryKey +
               ", m__lCachedNonReadOnlyAttributes=" + m__lCachedNonReadOnlyAttributes +
               ", m__lCachedAllNonReadOnlyAttributes=" + m__lCachedAllNonReadOnlyAttributes +
               ", m__lCachedNonParentAttributes=" + m__lCachedNonParentAttributes +
               ", m__CachedParentTable=" + m__CachedParentTable +
               ", m__lCachedAllAttributes=" + m__lCachedAllAttributes +
               ", m__lCachedAttributes=" + m__lCachedAttributes +
               ", m__lCachedNonParentNonExternallyManagedAttributes=" +
               m__lCachedNonParentNonExternallyManagedAttributes +
               ", m__lCachedAllParentAndNonParentAttributes=" + m__lCachedAllParentAndNonParentAttributes +
               ", m__lCachedAllParentAndNonParentNonExternallyManagedAttributes=" +
               m__lCachedAllParentAndNonParentNonExternallyManagedAttributes +
               ", m__lCachedAllNonExternallyManagedAttributes=" + m__lCachedAllNonExternallyManagedAttributes +
               ", m__lCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes=" +
               m__lCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyAttributes +
               ", m__lCachedAllParentAndNonParentNonReadOnlyAttributes=" +
               m__lCachedAllParentAndNonParentNonReadOnlyAttributes +
               ", m__lCachedAllParentAndNonParentReadOnlyAttributes=" +
               m__lCachedAllParentAndNonParentReadOnlyAttributes +
               ", m__lCachedNonParentNonExternallyManagedPlusPkAttributes=" +
               m__lCachedNonParentNonExternallyManagedPlusPkAttributes +
               ", m__lCachedAllNonExternallyManagedPlusPkAttributes=" +
               m__lCachedAllNonExternallyManagedPlusPkAttributes +
               ", m__lCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes=" +
               m__lCachedAllNonExternallyManagedNonReadOnlyPlusPkAttributes +
               ", m__lCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes=" +
               m__lCachedAllParentAndNonParentNonExternallyManagedNonReadOnlyPlusPkAttributes +
               ", m__lCachedNonParentPlusPkAttributes=" + m__lCachedNonParentPlusPkAttributes +
               ", m__lCachedAllNonReadOnlyButExternallyManagedAttributes=" +
               m__lCachedAllNonReadOnlyButExternallyManagedAttributes +
               ", m__lCachedNonPrimaryKeyNonReadOnlyAttributes=" + m__lCachedNonPrimaryKeyNonReadOnlyAttributes +
               ", m__lCachedAllParentTables=" + m__lCachedAllParentTables +
               ", m__lCachedStaticContents=" + m__lCachedStaticContents +
               ", m__lCachedDynamicQueries=" + m__lCachedDynamicQueries +
               ", m__lCachedNonPrimaryKeyAttributes=" + m__lCachedNonPrimaryKeyAttributes +
               ", m__lCachedCustomSelects=" + m__lCachedCustomSelects +
               ", m__lCachedCustomUpdatesOrInserts=" + m__lCachedCustomUpdatesOrInserts +
               ", m__lCachedCustomResults=" + m__lCachedCustomResults +
               ", m__lCachedChildAttributes=" + m__lCachedChildAttributes +
               ", m__lCachedForeignKeys=" + m__lCachedForeignKeys +
               ", m__CachedParentForeignKey=" + m__CachedParentForeignKey +
               ", m__lCachedReadOnlyAttributes=" + m__lCachedReadOnlyAttributes +
               ", m__lCachedNonPrimaryKeyReadOnlyAttributes=" + m__lCachedNonPrimaryKeyReadOnlyAttributes +
               ", m__lCachedAllExternallyManagedAttributes=" + m__lCachedAllExternallyManagedAttributes +
               ", m__lCachedDifferentCustomResults=" + m__lCachedDifferentCustomResults +
               '}';
    }
}
