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
 * Importing QueryJ Core classes.
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
 * Adds a simple caching mechanism while decorating {@link Table}
 * instances.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 */
@ThreadSafe
public class CachingTableDecorator
    extends AbstractTableDecorator
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 7125795956047030470L;

    /**
     * The cached primary key.
     */
    private ListDecorator<Attribute<DecoratedString>> m__lCachedPrimaryKey;

    /**
     * The cached parent table.
     */
    private Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> m__CachedParentTable;

    /**
     * The cached attributes.
     */
    private ListDecorator<Attribute<DecoratedString>> m__lCachedAttributes;

    /**
     * The cached all parent tables.
     */
    private List<Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>> m__lCachedAllParentTables;

    /**
     * The cached static rows.
     */
    private List<Row<DecoratedString>> m__lCachedStaticContents;

    /**
     * The cached dynamic queries.
     */
    private List<Sql<DecoratedString>> m__lCachedDynamicQueries;

    /**
     * The cached custom selects.
     */
    private List<Sql<DecoratedString>> m__lCachedCustomSelects;

    /**
     * The cached custom updates or inserts.
     */
    private List<Sql<DecoratedString>> m__lCachedCustomUpdatesOrInserts;

    /**
     * The cached custom result.
     */
    private Result<DecoratedString> m__CachedCustomResult;

    /**
     * The cached child attributes.
     */
    private ListDecorator<Attribute<DecoratedString>> m__lCachedChildAttributes;

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
     * The cached list of different results.
     */
    private ListDecorator<Result<DecoratedString>> m__lCachedCustomResults;

    /**
     * The cached list of read-only attributes.
     */
    private ListDecorator<Attribute<DecoratedString>> m__lCachedReadOnlyAttributes;

    /**
     * The cached list of externally-managed attributes.
     */
    private ListDecorator<Attribute<DecoratedString>> m__lCachedExternallyManagedAttributes;

    /**
     * Creates a <code>CachingTableDecorator</code> with the
     * <code>Table</code> to decorate.
     * @param table the table.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the decorator factory.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     */
    public CachingTableDecorator(
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table,
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
    protected final void immutableSetCachedPrimaryKey(
        @NotNull final ListDecorator<Attribute<DecoratedString>> list)
    {
        m__lCachedPrimaryKey = list;
    }

    /**
     * Specifies the cached primary key.
     * @param list the attribute list.
     */
    protected void setCachedPrimaryKey(@NotNull final ListDecorator<Attribute<DecoratedString>> list)
    {
        immutableSetCachedPrimaryKey(list);
    }

    /**
     * Retrieves the cached primary key.
     * @return such list.
     */
    @Nullable
    public ListDecorator<Attribute<DecoratedString>> getCachedPrimaryKey()
    {
        return m__lCachedPrimaryKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public ListDecorator<Attribute<DecoratedString>> getPrimaryKey()
    {
        @Nullable ListDecorator<Attribute<DecoratedString>> result = getCachedPrimaryKey();

        if (result == null)
        {
            result = super.getPrimaryKey();

            setCachedPrimaryKey(result);
        }

        return result;
    }

    /**
     * Specifies the cached attributes.
     * @param attributes the attributes.
     */
    protected final void immutableSetCachedAttributes(
        @NotNull final ListDecorator<Attribute<DecoratedString>> attributes)
    {
        m__lCachedAttributes = attributes;
    }

    /**
     * Specifies the cached attributes.
     * @param attributes the attributes.
     */
    protected void setCachedAttributes(
        @NotNull final ListDecorator<Attribute<DecoratedString>> attributes)
    {
        immutableSetCachedAttributes(attributes);
    }

    /**
     * Retrieves the cached attributes.
     * @return such attributes.
     */
    @Nullable
    protected ListDecorator<Attribute<DecoratedString>> getCachedAttributes()
    {
        return m__lCachedAttributes;
    }

    /**
     * Retrieves the attributes.
     * @return such information.
     */
    @Override
    @NotNull
    public ListDecorator<Attribute<DecoratedString>> getAttributes()
    {
        ListDecorator<Attribute<DecoratedString>> result = getCachedAttributes();

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
        @NotNull final Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> parent)
    {
        m__CachedParentTable = parent;
    }

    /**
     * Specifies the cached parent table.
     * @param parent such table.
     */
    protected void setCachedParentTable(
        @NotNull final Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> parent)
    {
        immutableSetCachedParentTable(parent);
    }

    /**
     * Retrieves the cached parent table.
     * @return such table.
     */
    @Nullable
    public Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> getCachedParentTable()
    {
        return m__CachedParentTable;
    }

    /**
     * Retrieves the parent table.
     * @return such information.
     */
    @Nullable
    public Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> getParentTable()
    {
        @Nullable Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> result =
            getCachedParentTable();

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
     * {@inheritDoc}
     */
    @Override
    @Nullable
    protected Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> createTableDecorator(
        @Nullable final String parentTable,
        @NotNull final ListDecorator<Attribute<String>> primaryKey,
        @NotNull final ListDecorator<Attribute<String>> attributes,
        final boolean isStatic,
        final boolean voDecorated,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        @Nullable TableDecorator result = null;

        @Nullable Table<String, Attribute<String>, List<Attribute<String>>> t_ParentTable = null;

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
     * Specifies the cached all parent tables.
     * @param list such list.
     */
    protected final void immutableSetCachedAllParentTables(
        @NotNull final List<Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>> list)
    {
        m__lCachedAllParentTables = list;
    }

    /**
     * Specifies the cached all parent tables.
     * @param list such list.
     */
    protected void setCachedAllParentTables(
        @NotNull final List<Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>> list)
    {
        immutableSetCachedAllParentTables(list);
    }

    /**
     * Retrieves the cached all parent tables.
     * @return such list.
     */
    @Nullable
    protected List<Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>> getCachedAllParentTables()
    {
        return m__lCachedAllParentTables;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public List<Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>> getAllParentTables()
    {
        @Nullable List<Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>> result = getCachedAllParentTables();

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
    protected final void immutableSetCachedDynamicQueries(@NotNull final List<Sql<DecoratedString>> list)
    {
        m__lCachedDynamicQueries = list;
    }

    /**
     * Specifies the cached dynamic queries.
     * @param list such list.
     */
    protected void setCachedDynamicQueries(@NotNull final List<Sql<DecoratedString>> list)
    {
        immutableSetCachedDynamicQueries(list);
    }

    /**
     * Retrieves the cached dynamic queries.
     * @return such information.
     */
    @Nullable
    protected List<Sql<DecoratedString>> getCachedDynamicQueries()
    {
        return m__lCachedDynamicQueries;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public List<Sql<DecoratedString>> getDynamicQueries()
    {
        @Nullable List<Sql<DecoratedString>> result = getCachedDynamicQueries();

        if (result == null)
        {
            result = super.getDynamicQueries();
            setCachedDynamicQueries(result);
        }

        return result;
    }

    /**
     * Specifies the cached custom selects.
     * @param selects such information.
     */
    protected final void immutableSetCachedCustomSelects(@NotNull final List<Sql<DecoratedString>> selects)
    {
        this.m__lCachedCustomSelects =  selects;
    }

    /**
     * Specifies the cached custom selects.
     * @param selects such information.
     */
    protected void setCachedCustomSelects(@NotNull final List<Sql<DecoratedString>> selects)
    {
        immutableSetCachedCustomSelects(selects);
    }

    /**
     * Retrieves the cached custom selects.
     * @return such information.
     */
    @Nullable
    protected List<Sql<DecoratedString>> getCachedCustomSelects()
    {
        return m__lCachedCustomSelects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public List<Sql<DecoratedString>> getCustomSelects()
    {
        List<Sql<DecoratedString>> result = getCachedCustomSelects();

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
    protected final void immutableSetCachedCustomUpdatesOrInserts(@NotNull final List<Sql<DecoratedString>> queries)
    {
        this.m__lCachedCustomUpdatesOrInserts = queries;
    }

    /**
     * Specifies the cached custom updates or inserts.
     * @param queries such information.
     */
    protected void setCachedCustomUpdatesOrInserts(@NotNull final List<Sql<DecoratedString>> queries)
    {
        immutableSetCachedCustomUpdatesOrInserts(queries);
    }

    /**
     * Retrieves the cached custom updates or inserts.
     * @return such information.
     */
    @Nullable
    protected List<Sql<DecoratedString>> getCachedCustomUpdatesOrInserts()
    {
        return m__lCachedCustomUpdatesOrInserts;
    }

    /**
     * Retrieves the custom updates or inserts.
     * @return such information.
     */
    @Override
    @NotNull
    public List<Sql<DecoratedString>> getCustomUpdatesOrInserts()
    {
        List<Sql<DecoratedString>> result = getCachedCustomUpdatesOrInserts();

        if (result == null)
        {
            result = super.getCustomUpdatesOrInserts();
            setCachedCustomUpdatesOrInserts(result);
        }

        return result;
    }

    /**
     * Specifies the cached custom result.
     * @param result the result to cache.
     */
    protected final void immutableSetCachedCustomResult(@NotNull final Result<DecoratedString> result)
    {
        m__CachedCustomResult = result;
    }

    /**
     * Specifies the cached custom result.
     * @param result the result to cache.
     */
    protected void setCachedCustomResult(@NotNull final Result<DecoratedString> result)
    {
        immutableSetCachedCustomResult(result);
    }

    /**
     * Retrieves the cached custom result.
     * @return such information.
     */
    @Nullable
    protected Result<DecoratedString> getCachedCustomResult()
    {
        return m__CachedCustomResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public Result<DecoratedString> getCustomResult()
    {
        @Nullable Result<DecoratedString> result = getCachedCustomResult();

        if (result == null)
        {
            result = super.getCustomResult();
            if (result != null)
            {
                setCachedCustomResult(result);
            }
        }

        return result;
    }

    /**
     * Specifies the cached child attributes.
     * @param attributes such {@link Attribute attributes}.
     */
    protected final void immutableSetCachedChildAttributes(
        @NotNull final ListDecorator<Attribute<DecoratedString>> attributes)
    {
        this.m__lCachedChildAttributes = attributes;
    }

    /**
     * Specifies the cached child attributes.
     * @param attributes such {@link Attribute attributes}.
     */
    protected void setCachedChildAttributes(
        @NotNull final ListDecorator<Attribute<DecoratedString>> attributes)
    {
        immutableSetCachedChildAttributes(attributes);
    }

    /**
     * Retrieves the cached child attributes.
     * @return such attributes.
     */
    @Nullable
    public ListDecorator<Attribute<DecoratedString>> getCachedChildAttributes()
    {
        return m__lCachedChildAttributes;
    }

    /**
     * Retrieves the child's attributes.
     * @return such list.
     */
    @Override
    @NotNull
    public ListDecorator<Attribute<DecoratedString>> getChildAttributes()
    {
        @NotNull final ListDecorator<Attribute<DecoratedString>> result;

        @Nullable final ListDecorator<Attribute<DecoratedString>> cached = getCachedChildAttributes();

        if (cached == null)
        {
            @Nullable final ListDecorator<Attribute<DecoratedString>> parent = super.getChildAttributes();

            if (parent == null)
            {
                result =
                    new TableAttributesListDecorator(
                        new ArrayList<Attribute<DecoratedString>>(0),
                        this,
                        getCustomSqlProvider(),
                        getDecoratorFactory());
            }
            else
            {
                result = parent;
            }

            setCachedChildAttributes(result);
        }
        else
        {
            result = cached;
        }

        return result;
    }

    /**
     * Specifies the cached foreign keys.
     * @param foreignKeys the {@link ForeignKey foreignKeys}.
     */
    protected final void immutableSetCachedForeignKeys(
        @NotNull final List<ForeignKey<DecoratedString>> foreignKeys)
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
     * {@inheritDoc}
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
    protected final void immutableSetCachedParentForeignKey(
        @NotNull final ForeignKey<DecoratedString> foreignKey)
    {
        this.m__CachedParentForeignKey = foreignKey;
    }

    /**
     * Specifies the cached parent foreign key.
     * @param foreignKey such {@link ForeignKey}.
     */
    protected void setCachedParentForeignKey(
        @NotNull final ForeignKey<DecoratedString> foreignKey)
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
     * @param flag such condition.
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
    protected final void immutableSetCachedReadOnlyAttributes(
        @NotNull final ListDecorator<Attribute<DecoratedString>> list)
    {
        this.m__lCachedReadOnlyAttributes = list;
    }

    /**
     * Specifies the cached read-only attributes.
     * @param list such {@link Attribute} list.
     */
    protected void setCachedReadOnlyAttributes(
        @NotNull final ListDecorator<Attribute<DecoratedString>> list)
    {
        immutableSetCachedReadOnlyAttributes(list);
    }

    /**
     * Retrieves the cached read-only attributes.
     * @return such list.
     */
    @Nullable
    public ListDecorator<Attribute<DecoratedString>> getCachedReadOnlyAttributes()
    {
        return m__lCachedReadOnlyAttributes;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getReadOnlyAttributes()
    {
        ListDecorator<Attribute<DecoratedString>> result = getCachedReadOnlyAttributes();

        if (result == null)
        {
            result = super.getReadOnlyAttributes();
            setCachedReadOnlyAttributes(result);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<Sql<DecoratedString>> getCustomSelectsForUpdate()
    {
        return super.getCustomSelectsForUpdate();
    }

    /**
     * Specifies the cached, externally-managed attributes.
     * @param list such {@link Attribute attributes}.
     */
        protected final void immutableSetCachedExternallyManagedAttributes(
            @NotNull final ListDecorator<Attribute<DecoratedString>> list)
    {
        this.m__lCachedExternallyManagedAttributes = list;
    }

    /**
     * Specifies the cached, externally-managed attributes.
     * @param list such {@link Attribute attributes}.
     */
    protected void setCachedExternallyManagedAttributes(
        @NotNull final ListDecorator<Attribute<DecoratedString>> list)
    {
        immutableSetCachedExternallyManagedAttributes(list);
    }

    /**
     * Retrieves the cached, externally-managed attributes.
     * @return such {@link AttributeDecorator list}.
     */
    @Nullable
    protected ListDecorator<Attribute<DecoratedString>> getCachedExternallyManagedAttributes()
    {
        return m__lCachedExternallyManagedAttributes;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getExternallyManagedAttributes()
    {
        ListDecorator<Attribute<DecoratedString>> result = getCachedExternallyManagedAttributes();

        if (result == null)
        {
            result = super.getExternallyManagedAttributes();
            setCachedExternallyManagedAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the cached custom results.
     * @param results such results.
     */
    protected final void immutableSetCachedCustomResults(@NotNull final ListDecorator<Result<DecoratedString>> results)
    {
        this.m__lCachedCustomResults = results;
    }

    /**
     * Specifies the cached custom results.
     * @param results such results.
     */
    protected void setCachedCustomResults(@NotNull final ListDecorator<Result<DecoratedString>> results)
    {
        immutableSetCachedCustomResults(results);
    }

    /**
     * Retrieves the cached custom results.
     * @return such results.
     A*/
    @Nullable
    public ListDecorator<Result<DecoratedString>> getCachedCustomResults()
    {
        return this.m__lCachedCustomResults;
    }

    /**
     * Retrieves the list of results defined for this table (using the referring custom-selects).
     * @return such list.
     */
    @NotNull
    @Override
    public ListDecorator<Result<DecoratedString>> getCustomResults()
    {
        ListDecorator<Result<DecoratedString>> result = getCachedCustomResults();

        if (result == null)
        {
            result = super.getCustomResults();
            setCachedCustomResults(result);
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

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"super\": " + super.toString()
            + ", \"class\": \"CachingTableDecorator\""
            + ", \"package\": \"org.acmsl.queryj.metadata\""
            + ", \"parentForeignKeyAlreadyRetrieved\": " + m__bParentForeignKeyAlreadyRetrieved
            + ", \"cachedPrimaryKey\": " + m__lCachedPrimaryKey
            + ", \"cachedReadOnlyAttributes\": " + m__lCachedReadOnlyAttributes
            + ", \"cachedParentTable\": " + m__CachedParentTable
            + ", \"cachedAttributes\": " + m__lCachedAttributes
            + ", \"cachedAllParentTables\": " + m__lCachedAllParentTables
            + ", \"cachedStaticContents\": " + m__lCachedStaticContents
            + ", \"cachedDynamicQueries\": " + m__lCachedDynamicQueries
            + ", \"cachedCustomSelects\": " + m__lCachedCustomSelects
            + ", \"cachedCustomUpdatesOrInserts\": " + m__lCachedCustomUpdatesOrInserts
            + ", \"cachedCustomResult\": " + m__CachedCustomResult
            + ", \"cachedChildAttributes\": " + m__lCachedChildAttributes
            + ", \"cachedForeignKeys\": " + m__lCachedForeignKeys
            + ", \"cachedParentForeignKey\": " + m__CachedParentForeignKey
            + ", \"cachedExternallyManagedAttributes\": " + m__lCachedExternallyManagedAttributes
            + ", \"cachedDifferentCustomResults\": " + m__lCachedCustomResults
            + " }";
    }
}
