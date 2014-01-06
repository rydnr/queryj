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
 * Filename: AbstractTableDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates 'Table' instances to provide required
 *              alternate representations of the information stored therein.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.commons.utils.ToStringUtils;
import org.acmsl.queryj.customsql.CustomResultUtils;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultElement;
import org.acmsl.queryj.customsql.ResultRef;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.metadata.vo.AbstractTable;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.LazyAttribute;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.api.dao.DAOTemplateUtils;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Decorates <code>Table</code> instances to provide required alternate
 * representations of the information stored therein.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 */
public abstract class AbstractTableDecorator
    extends     AbstractTable<
                    DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>
    implements  TableDecorator
{
    /**
     * The decorated table.
     */
    private Table<String, Attribute<String>, List<Attribute<String>>> m__Table;

    /**
     * The metadata type manager.
     */
    private MetadataManager m__MetadataManager;

    /**
     * The decorator factory.
     */
    private DecoratorFactory m__DecoratorFactory;

    /**
     * The custom sql provider.
     */
    private CustomSqlProvider m__CustomSqlProvider;

    /**
     * The foreign keys.
     */
    @SuppressWarnings("unused")
    private List<ForeignKey<DecoratedString>> m__lForeignKeys;

    /**
     * The parent foreign key.
     */
    private ForeignKey<DecoratedString> m__ParentForeignKey;

    /**
     * The child's attributes.
     */
    private ListDecorator<Attribute<DecoratedString>> m__lChildAttributes;

    /**
     * A flag indicating whether the attributes should be cleaned up.
     */
    private boolean m__bAttributesShouldBeCleanedUp = true;

    /**
     * The read-only attributes.
     */
    private ListDecorator<Attribute<DecoratedString>> m__lReadOnlyAttributes;

    /**
     * The externally-managed attributes.
     */
    private ListDecorator<Attribute<DecoratedString>> m__lExternallyManagedAttributes;

    /**
     * Creates an <code>AbstractTableDecorator</code> with the
     * <code>Table</code> to decorate.
     * @param table the {@link Table table}.
     * @param metadataManager the {@link MetadataManager metadata manager}.
     * @param decoratorFactory the {@link DecoratorFactory decorator factory}.
     * @param customSqlProvider the {@link CustomSqlProvider custom-sql provider}.
     */
    @SuppressWarnings("unused")
    public AbstractTableDecorator(
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        this(
            table,
            table.getName(),
            table.getPrimaryKey(),
            table.getAttributes(),
            table.getForeignKeys(),
            table.getParentTable(),
            table.isStatic(),
            table.isVoDecorated(),
            metadataManager,
            decoratorFactory,
            customSqlProvider);
    }

    /**
     * Creates an <code>AbstractTableDecorator</code> with the following
     * information.
     * @param name the name.
     * @param primaryKey the primary key.
     * @param attributes the attributes.
     * @param foreignKeys the foreign keys.
     * @param parentTable the parent table.
     * @param isStatic whether the table is static.
     * @param voDecorated whether the value-object should be decorated.
     * @param metadataManager the {@link MetadataManager metadata manager}.
     * @param decoratorFactory the {@link DecoratorFactory decorator factory}.
     * @param customSqlProvider the {@link CustomSqlProvider custom-sql provider}.
     */
    public AbstractTableDecorator(
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table,
        @NotNull final String name,
        @NotNull final List<Attribute<String>> primaryKey,
        @NotNull final List<Attribute<String>> attributes,
        @NotNull final List<ForeignKey<String>> foreignKeys,
        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> parentTable,
        final boolean isStatic,
        final boolean voDecorated,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        super(
            new DecoratedString(name),
            new DecoratedString((table.getComment() != null) ? table.getComment() : ""),
            (parentTable != null)
            ? new CachingTableDecorator(parentTable, metadataManager, decoratorFactory, customSqlProvider)
            : null,
            isStatic,
            voDecorated);

        immutableSetTable(table);
        immutableSetPrimaryKey(
            new TableAttributesListDecorator<Attribute<DecoratedString>>(
                decorateAttributes(primaryKey, metadataManager, decoratorFactory), this));
        immutableSetAttributes(
            new TableAttributesListDecorator<Attribute<DecoratedString>>(
                decorateAttributes(attributes, metadataManager, decoratorFactory), this));
        immutableSetReadOnlyAttributes(
            new TableAttributesListDecorator<Attribute<DecoratedString>>(
                decorateAttributes(attributes, metadataManager, decoratorFactory), this));
        immutableSetForeignKeys(
            new TableAttributesListDecorator<ForeignKey<DecoratedString>>(
                decorate(foreignKeys, metadataManager, decoratorFactory, customSqlProvider),
                this));
        immutableSetMetadataManager(metadataManager);
        immutableSetDecoratorFactory(decoratorFactory);
        immutableSetCustomSqlProvider(customSqlProvider);
    }

    /**
     * Specifies the table to decorate.
     * @param table such table.
     */
    protected final void immutableSetTable(
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table)
    {
        this.m__Table = table;
    }

    /**
     * Specifies the table to decorate.
     * @param table such table.
     */
    @SuppressWarnings("unused")
    protected void setTable(@NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table)
    {
        immutableSetTable(table);
    }

    /**
     * Retrieves the decorated table.
     * @return such table.
     */
    @NotNull
    protected final Table<String, Attribute<String>, List<Attribute<String>>> immutableGetTable()
    {
        return this.m__Table;
    }

    /**
     * Retrieves the decorated table.
     * @return such table.
     */
    @NotNull
    public Table<String, Attribute<String>, List<Attribute<String>>> getTable()
    {
        return immutableGetTable();
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected final void immutableSetMetadataManager(
        @NotNull final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected void setMetadataManager(
        @NotNull final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such instance.
     */
    @NotNull
    protected MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Specifies the decorator factory.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     */
    protected final void immutableSetDecoratorFactory(
        @NotNull final DecoratorFactory decoratorFactory)
    {
        m__DecoratorFactory = decoratorFactory;
    }

    /**
     * Specifies the decorator factory.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     */
    protected void setDecoratorFactory(@NotNull final DecoratorFactory decoratorFactory)
    {
        immutableSetDecoratorFactory(decoratorFactory);
    }

    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    @Nullable
    protected final DecoratorFactory immutableGetDecoratorFactory()
    {
        return m__DecoratorFactory;
    }

    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    @NotNull
    public DecoratorFactory getDecoratorFactory()
    {
        DecoratorFactory result = immutableGetDecoratorFactory();

        if  (result == null)
        {
            result = CachingDecoratorFactory.getInstance();
            setDecoratorFactory(result);
        }

        return result;
    }

    /**
     * Specifies the {@link CustomSqlProvider}.
     * @param provider the {@link CustomSqlProvider provider}.
     */
    protected final void immutableSetCustomSqlProvider(@NotNull final CustomSqlProvider provider)
    {
        m__CustomSqlProvider = provider;
    }

    /**
     * Specifies the {@link CustomSqlProvider}.
     * @param provider the {@link CustomSqlProvider provider}.
     */
    @SuppressWarnings("unused")
    protected void setCustomSqlProvider(@NotNull final CustomSqlProvider provider)
    {
        immutableSetCustomSqlProvider(provider);
    }

    /**
     * Retrieves the {@link CustomSqlProvider}.
     * @return such instance.
     */
    @NotNull
    public CustomSqlProvider getCustomSqlProvider()
    {
        return m__CustomSqlProvider;
    }

    /**
     * Specifies whether the attributes should be cleaned up.
     * @param flag such flag.
     */
    protected final void immutableSetAttributesShouldBeCleanedUp(final boolean flag)
    {
        m__bAttributesShouldBeCleanedUp = flag;
    }

    /**
     * Specifies whether the attributes have been cleaned up.
     * @param flag such flag.
     */
    @SuppressWarnings("unused")
    protected void setAttributesShouldBeCleanedUp(final boolean flag)
    {
        immutableSetAttributesShouldBeCleanedUp(flag);
    }

    /**
     * Retrieves whether the attributes have been cleaned up.
     * @return such flag.
     */
    protected boolean getAttributesShouldBeCleanedUp()
    {
        return m__bAttributesShouldBeCleanedUp;
    }

    /**
     * Specifies the read-only attributes.
     * @param attrs the attributes.
     */
    protected final void immutableSetReadOnlyAttributes(
        @NotNull final ListDecorator<Attribute<DecoratedString>> attrs)
    {
        this.m__lReadOnlyAttributes = attrs;
    }

    /**
     * Specifies the read-only attributes.
     * @param attrs the attributes.
     */
    @SuppressWarnings("unused")
    protected void setReadOnlyAttributes(
        @NotNull final ListDecorator<Attribute<DecoratedString>> attrs)
    {
        immutableSetReadOnlyAttributes(attrs);
    }

    /**
     * Retrieves the read-only attributes.
     * @return the attributes.
     */
    @Override
    @NotNull
    public ListDecorator<Attribute<DecoratedString>> getReadOnlyAttributes()
    {
        return this.m__lReadOnlyAttributes;
    }

    /**
     * Specifies the externally-managed attributes.
     * @param attrs such attributes.
     */
    protected final void immutableSetExternallyManagedAttributes(
        @NotNull final ListDecorator<Attribute<DecoratedString>> attrs)
    {
        this.m__lExternallyManagedAttributes = attrs;
    }

    /**
     * Specifies the externally-managed attributes.
     * @param attrs such attributes.
     */
    @SuppressWarnings("unused")
    protected void setExternallyManagedAttributes(
        @NotNull final ListDecorator<Attribute<DecoratedString>> attrs)
    {
        immutableSetExternallyManagedAttributes(attrs);
    }

    /**
     * Retrieves the externally-managed attributes.
     * @return such attributes.
     */
    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getExternallyManagedAttributes()
    {
        return this.m__lExternallyManagedAttributes;
    }

    /**
     * Specifies the child attributes.
     * @param childAttributes the child attributes.
     */
    protected final void immutableSetChildAttributes(
        final ListDecorator<Attribute<DecoratedString>> childAttributes)
    {
        m__lChildAttributes = childAttributes;
    }

    /**
     * Specifies the child attributes.
     * @param childAttributes the child attributes.
     */
    @SuppressWarnings("unused")
    protected void setChildAttributes(
        @NotNull final ListDecorator<Attribute<DecoratedString>> childAttributes)
    {
        immutableSetChildAttributes(childAttributes);
    }

    /**
     * Retrieves the child's attributes.
     * @return such list.
     */
    @Nullable
    public ListDecorator<Attribute<DecoratedString>> getChildAttributes()
    {
        return m__lChildAttributes;
    }

    /**
     * Retrieves the foreign keys.
     * @return such list.
     */
    @NotNull
    @Override
    public List<ForeignKey<DecoratedString>> getForeignKeys()
    {
        return
            getForeignKeys(
                getName(), getMetadataManager(), getDecoratorFactory(), getCustomSqlProvider());
    }

    /**
     * Retrieves the foreign keys.
     * @param name the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instnace.
     * @return such list.
     */
    @NotNull
    protected List<ForeignKey<DecoratedString>> getForeignKeys(
        @NotNull final DecoratedString name,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        List<ForeignKey<DecoratedString>> result = immutableGetForeignKeys();

        if  (   (result == null)
             || (!areDecorated(result)))
        {
            result =
                decorate(
                    retrieveForeignKeys(name, metadataManager),
                    metadataManager,
                    decoratorFactory,
                    customSqlProvider);

            setForeignKeys(result);
        }

        try
        {
            Collections.sort(result);
        }
        catch (@NotNull final Throwable error)
        {
            @Nullable final Log t_Log = UniqueLogFactory.getLog(AbstractTableDecorator.class);

            if (t_Log != null)
            {
                t_Log.error(error);
            }
        }

        return result;
    }

    /**
     * Checks whether given foreign keys are decorated already or not.
     * @param foreignKeys the {@link ForeignKey} list.
     * @return <code>true</code> in such case.
     */
    protected <V> boolean areDecorated(@NotNull final List<ForeignKey<V>> foreignKeys)
    {
        boolean result = false;

        for (@Nullable final ForeignKey<V> t_ForeignKey : foreignKeys)
        {
            if (t_ForeignKey instanceof ForeignKeyDecorator)
            {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Retrieves the foreign keys.
     * @param name the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such foreign keys.
     */
    @NotNull
    protected List<ForeignKey<String>> retrieveForeignKeys(
        @NotNull final DecoratedString name, @NotNull final MetadataManager metadataManager)
    {
        @Nullable final List<ForeignKey<String>> result;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(name.getValue());

        if (t_Table != null)
        {
            result = t_Table.getForeignKeys();
        }
        else
        {
            result = new ArrayList<ForeignKey<String>>(0);
        }

        return result;
    }

    /**
     * Builds an attribute decorator with given information.
     * @param table the table name.
     * @param name the attribute name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return such decorator.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected Attribute<DecoratedString> buildAttribute(
        @NotNull final String table,
        @NotNull final String name,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            new CachingAttributeDecorator(
                new LazyAttribute(
                    table,
                    name,
                    metadataManager,
                    metadataTypeManager),
                metadataManager);
    }

    /**
     * Builds a foreign-key decorator with given information.
     * @param sourceTable the source table name.
     * @param attributes the attributes.
     * @param targetTable the target table name.
     * @param allowNull whether the foreign-key allows null.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the {@link DecoratorFactory} implementation.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
    * @return such decorator.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected ForeignKey<DecoratedString> buildForeignKeyDecorator(
        @NotNull final String sourceTable,
        @NotNull final ListDecorator<Attribute<String>> attributes,
        @NotNull final String targetTable,
        final boolean allowNull,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return
            new CachingForeignKeyDecorator(
                sourceTable,
                attributes,
                targetTable,
                allowNull,
                metadataManager,
                decoratorFactory,
                customSqlProvider);
    }

    /**
     * Specifies the parent foreign-key.
     * @param foreignKey such fk.
     */
    protected final void immutableSetParentForeignKey(
        @NotNull final ForeignKey<DecoratedString> foreignKey)
    {
        m__ParentForeignKey = foreignKey;
    }

    /**
     * Specifies the parent foreign-key.
     * @param foreignKey such fk.
     */
    protected void setParentForeignKey(
        @NotNull final ForeignKey<DecoratedString> foreignKey)
    {
        immutableSetParentForeignKey(foreignKey);
    }

    /**
     * Retrieves the parent foreign-key.
     * @return such foreign key.
     */
    @Nullable
    protected final ForeignKey<DecoratedString> immutableGetParentForeignKey()
    {
        return m__ParentForeignKey;
    }

    /**
     * Retrieves the parent foreign-key.
     * @return such foreign key.
     */
    @SuppressWarnings("unused")
    @Nullable
    public ForeignKey<DecoratedString> getParentForeignKey()
    {
        @Nullable ForeignKey<DecoratedString> result = immutableGetParentForeignKey();

        if  (result == null)
        {
            @Nullable final Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> t_Parent =
                getParentTable();

            if  (t_Parent != null)
            {
                result =
                    retrieveParentForeignKey(t_Parent, getForeignKeys());

                if  (result != null)
                {
                    setParentForeignKey(result);
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the parent foreign key.
     * @param parent the parent table.
     * @param foreignKeys the foreign keys.
     * @return such foreign key.
     */
    @Nullable
    protected ForeignKey<DecoratedString> retrieveParentForeignKey(
        @NotNull final Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> parent,
        @NotNull final List<ForeignKey<DecoratedString>> foreignKeys)
    {
        @Nullable ForeignKey<DecoratedString> result = null;

        for (@Nullable final ForeignKey<DecoratedString> t_ForeignKey : foreignKeys)
        {
            if (   (t_ForeignKey != null)
                 && (parent.getName().getValue().equalsIgnoreCase(
                        t_ForeignKey.getTargetTableName().getValue()))
                 && (attributeListMatch(
                        getPrimaryKey().getItems(), t_ForeignKey.getAttributes())))
            {
                result = t_ForeignKey;
                break;
            }
        }

        return result;
    }

    /**
     * Checks whether given attribute lists match.
     * @param first the first attribute list.
     * @param second the second attribute list.
     * @return <code>true</code> in such case.
     */
    protected boolean attributeListMatch(
        final List<Attribute<DecoratedString>> first, final List<Attribute<DecoratedString>> second)
    {
        boolean result =
            (   (first != null)
             && (second != null));

        if  (result)
        {
            final int t_iCount = first.size();
            result = (t_iCount == second.size());

            if  (result)
            {
                for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
                {
                    result =
                        attributesMatch(
                            first.get(t_iIndex),
                            second.get(t_iIndex));

                    if  (!result)
                    {
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Checks whether given attributes match.
     * @param first the first attribute.
     * @param second the second attribute.
     * @return <code>true</code> in such case.
     */
    protected boolean attributesMatch(
        @Nullable final Attribute<DecoratedString> first,
        @Nullable final Attribute<DecoratedString> second)
    {
        boolean result =
            (   (first != null)
             && (second != null));

        if  (result)
        {
            @NotNull final String t_strFirstName = first.getName().getValue();

            result =
                (   (first.getType().equals(second.getType()))
                 && (t_strFirstName.equalsIgnoreCase(second.getName().getValue())));
        }

        return result;
    }

    /**
     * Decorates the attributes.
     * @return the decorated attributes.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected List<Attribute<DecoratedString>> decorateAttributes()
    {
        return
            decorateAttributes(
                getName().getValue(), getMetadataManager(), getDecoratorFactory());
    }

    /**
     * Decorates the attributes.
     * @param attributes the attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the decorated attributes.
     */
    @NotNull
    protected List<Attribute<DecoratedString>> decorateAttributes(
        @NotNull final List<Attribute<String>> attributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return decoratorFactory.decorateAttributes(attributes, metadataManager);
    }

    /**
     * Decorates the attributes.
     * @param name the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the decorated attributes.
     */
    @NotNull
    protected List<Attribute<DecoratedString>> decorateAttributes(
        @NotNull final String name,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return decoratorFactory.decorateAttributes(name, metadataManager);
    }

    /**
     * Retrieves the child attributes.
     * @return such list.
     */
    @NotNull
    protected ListDecorator<Attribute<DecoratedString>> retrieveChildAttributes()
    {
        @Nullable List<Attribute<DecoratedString>> result = getChildAttributes();

        if (result == null)
        {
            result = new ArrayList<Attribute<DecoratedString>>(0);
        }

        return new TableAttributesListDecorator<Attribute<DecoratedString>>(result, this);
    }

    /**
     * Retrieves the attributes.
     * @return such information.
     */
    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getAttributes()
    {
        return
            getAttributes(
                getName().getValue(),
                retrieveChildAttributes(),
                getMetadataManager(),
                getAttributesShouldBeCleanedUp());
    }

    /**
     * Retrieves the attributes.
     * @param table the table name.
     * @param childAttributes the child's attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param attributesShouldBeCleanedUp whether the child attributes should be removed
     * from the attribute list.
     * @return such information.
     */
    @NotNull
    protected ListDecorator<Attribute<DecoratedString>> getAttributes(
        @NotNull final String table,
        @NotNull final ListDecorator<Attribute<DecoratedString>> childAttributes,
        @NotNull final MetadataManager metadataManager,
        final boolean attributesShouldBeCleanedUp)
    {
        @NotNull ListDecorator<Attribute<DecoratedString>> result = immutableGetAttributes();

        if (!alreadyDecorated(result))
        {
            if  (attributesShouldBeCleanedUp)
            {
                result =
                    removeOverridden(
                        childAttributes,
                        result,
                        table,
                        metadataManager,
                        TableDecoratorHelper.getInstance());

                setAttributes(result);
            }
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves whether the attributes are already decorated or not.
     * @param attributes the {@link Attribute} list.
     * @return <code>true</code> in such case.
     */
    protected <K> boolean alreadyDecorated(@NotNull final ListDecorator<Attribute<K>> attributes)
    {
        boolean result = false;

        for (@Nullable final Attribute<K> t_Attribute : attributes)
        {
            if (   (t_Attribute != null)
                && (t_Attribute.getName() instanceof DecoratedString))
            {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Retrieves the parent table.
     * @return such information.
     */
    @Override
    @Nullable
    public Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> getParentTable()
    {
        return getParentTable(getTable().getParentTable(), getMetadataManager());
    }

    /**
     * Retrieves the parent table.
     * @param parent the parent table.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     */
    @Nullable
    protected Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> getParentTable(
        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> parent,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable final Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> result;

        if (parent != null)
        {
            @Nullable final Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> cached =
                super.getParentTable();

            if  (cached == null)
            {
                @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
                    metadataManager.getTableDAO().findByName(parent.getName());

                if (t_Table != null)
                {
                    result =
                        new CachingTableDecorator(
                            t_Table,
                            metadataManager,
                            getDecoratorFactory(),
                            getCustomSqlProvider());
                }
                else
                {
                    result = null;
                }
            }
            else
            {
                result = cached;
            }
        }
        else
        {
            result = null;
        }

        return result;
    }


    /**
     * Removes the duplicated attributes from <code>secondAttributes</code>.
     * @param firstAttributes the child attributes.
     * @param secondAttributes the parent attributes.
     * @param parentTableName the parent table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param tableDecoratorHelper the {@link org.acmsl.queryj.metadata.TableDecoratorHelper} instance.
     * @return the cleaned-up attributes.
     */
    @NotNull
    public ListDecorator<Attribute<DecoratedString>> removeOverridden(
        @NotNull final ListDecorator<Attribute<DecoratedString>> firstAttributes,
        @NotNull final ListDecorator<Attribute<DecoratedString>> secondAttributes,
        @Nullable final String parentTableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        @NotNull final List<Attribute<DecoratedString>> result =
            tableDecoratorHelper.removeOverridden(
                firstAttributes,
                secondAttributes,
                parentTableName,
                metadataManager);

        Collections.sort(result);

        return new TableAttributesListDecorator<Attribute<DecoratedString>>(result, this);
    }

    /**
     * Sums up parent and child's attributes.
     * @return such collection.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected ListDecorator<Attribute<DecoratedString>> sumUpParentAndChildAttributes()
    {
        return
            sumUpParentAndChildAttributes(
                getAttributes(),
                retrieveChildAttributes(),
                TableDecoratorHelper.getInstance());
    }

    /**
     * Sums up parent and child's attributes.
     * @param parentTable the parent table.
     * @param attributes the attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code> instance.
     * @return such collection.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected ListDecorator<Attribute<DecoratedString>> sumUpParentAndChildAttributes(
        @NotNull final String parentTable,
        @NotNull final ListDecorator<Attribute<String>> attributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        @NotNull
        final List<Attribute<DecoratedString>> result =
            tableDecoratorHelper.sumUpParentAndChildAttributes(
                parentTable,
                attributes,
                metadataManager,
                decoratorFactory);

        return new TableAttributesListDecorator<Attribute<DecoratedString>>(result, this);
    }

    /**
     * Sums up parent and child's attributes.
     * @param attributes the attributes.
     * @param childAttributes the child attributes.
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code> instance.
     * @return such collection.
     */
    @NotNull
    protected ListDecorator<Attribute<DecoratedString>> sumUpParentAndChildAttributes(
        @NotNull final ListDecorator<Attribute<DecoratedString>> attributes,
        @NotNull final ListDecorator<Attribute<DecoratedString>> childAttributes,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        @NotNull
        final List<Attribute<DecoratedString>> result =
            tableDecoratorHelper.sumUpParentAndChildAttributes(
                attributes, childAttributes);

        return new TableAttributesListDecorator<Attribute<DecoratedString>>(result, this);
    }

    /**
     * Creates a table decorator.
     * @param parentTable the parent table name.
     * @param primaryKey the primary key.
     * @param attributes the attributes.
     * @param isStatic whether the table contains static values or not.
     * @param voDecorated whether the value-object is decorated.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return such decorator.
     */
    @SuppressWarnings("unused")
    @Nullable
    protected abstract Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> createTableDecorator(
        @Nullable final String parentTable,
        @NotNull final ListDecorator<Attribute<String>> primaryKey,
        @NotNull final ListDecorator<Attribute<String>> attributes,
        final boolean isStatic,
        final boolean voDecorated,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider);

    /**
     * Checks whether given attribute belongs to a concrete attribute list.
     * @param list the list.
     * @param attribute the attribute.
     * @return <tt>true</tt> in such case.
     */
    @SuppressWarnings("unused")
    protected boolean isPartOf(
        @NotNull final ListDecorator<Attribute<DecoratedString>> list,
        @NotNull final Attribute<DecoratedString> attribute)
    {
        boolean result = false;

        for (@Nullable final Attribute<DecoratedString> t_Attribute : list)
        {
            if  (attributesMatch(t_Attribute, attribute))
            {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Removes the read-only attributes from given list.
     * @param attributes the attributes.
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code> instance.
     * @return the list without the read-only attributes.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected ListDecorator<Attribute<DecoratedString>> removeReadOnly(
        @NotNull final ListDecorator<Attribute<DecoratedString>> attributes,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        return
            new TableAttributesListDecorator<Attribute<DecoratedString>>(
                tableDecoratorHelper.removeReadOnly(attributes), this);
    }

    /**
     * Retrieves all parent tables.
     * @return such tables.
     */
    @NotNull
    @Override
    public List<Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>> getAllParentTables()
    {
        return getAllParentTables(getName().getValue(), getMetadataManager());
    }

    /**
     * Retrieves all parent tables.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @return such tables.
     */
    @NotNull
    protected List<Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>> getAllParentTables(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        @NotNull final List<Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>> result =
            new ArrayList<Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>>(0);

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(tableName);

        if (t_Table != null)
        {
            @Nullable Table<String, Attribute<String>, List<Attribute<String>>> t_Parent;

            do
            {
                t_Parent = t_Table.getParentTable();

                if (t_Parent != null)
                {
                    result.add(
                        new CachingTableDecorator(
                            t_Parent,
                            metadataManager,
                            getDecoratorFactory(),
                            getCustomSqlProvider()));

                    t_Parent = t_Parent.getParentTable();
                }
            }
            while (t_Parent != null);
        }

        return result;
    }

    /**
     * Retrieves the static content.
     * @return such information.
     */
    @Override
    @NotNull
    public List<Row<DecoratedString>> getStaticContent()
    {
        List<Row<DecoratedString>> result = null;

        try
        {
            result =
                retrieveStaticContent(
                    getTable().getName(),
                    getMetadataManager(),
                    getDecoratorFactory(),
                    DAOTemplateUtils.getInstance());
        }
        catch (@NotNull final SQLException invalidQuery)
        {
            @Nullable final Log t_Log = UniqueLogFactory.getLog(AbstractTableDecorator.class);

            if (t_Log != null)
            {
                t_Log.error(
                    "Cannot retrieve contents in table " + getTable().getName(),
                    invalidQuery);
            }
        }

        if (result == null)
        {
            result = new ArrayList<Row<DecoratedString>>(0);
        }
        else
        {
            try
            {
                Collections.sort(result);
            }
            catch (@NotNull final Throwable throwable)
            {
                @Nullable final Log t_Log = UniqueLogFactory.getLog(AbstractTableDecorator.class);

                if (t_Log != null)
                {
                    t_Log.error(throwable);
                }
            }
        }

        return result;
    }
    /**
     * Retrieves the static values of given table.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the decorator factory.
     * @param daoTemplateUtils the {@link DAOTemplateUtils} instance.
     * @return such information.
     * @throws SQLException if the operation fails.
     */
    @NotNull
    protected List<Row<DecoratedString>> retrieveStaticContent(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils)
        throws SQLException
    {
        @NotNull final List<Row<DecoratedString>> result;

        @Nullable final List<Row<String>> aux =
            daoTemplateUtils.queryContents(
                tableName, metadataManager, decoratorFactory);

        if (aux == null)
        {
            result = new ArrayList<Row<DecoratedString>>(0);
        }
        else
        {
            result = decorate(aux, metadataManager, decoratorFactory);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public List<Sql> getDynamicQueries()
    {
        return getDynamicQueries(getTable(), getCustomSqlProvider());
    }

    /**
     * Retrieves the dynamic queries.
     * @param table the table.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return the list of dynamic queries.
     */
    @NotNull
    protected List<Sql> getDynamicQueries(
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return getDynamicQueries(table.getName(), customSqlProvider.getSqlDAO());
    }

    /**
     * Retrieves the dynamic queries.
     * @param tableName the table name.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @return the list of dynamic queries.
     */
    @NotNull
    protected List<Sql> getDynamicQueries(
        @NotNull final String tableName, @NotNull final SqlDAO sqlDAO)
    {
        @NotNull final List<Sql> result = sqlDAO.findDynamic(tableName);

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves the read-only attributes from given list.
     * @param attributes the attribute list.
     * @return the read-only subset.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected List<Attribute<DecoratedString>> filterReadOnlyAttributes(
        @NotNull final List<Attribute<DecoratedString>> attributes)
    {
        @NotNull final List<Attribute<DecoratedString>> result =  new ArrayList<Attribute<DecoratedString>>(0);

        for (@Nullable final Attribute<DecoratedString> t_Attribute : attributes)
        {
            if (   (t_Attribute != null)
                && (t_Attribute.isReadOnly()))
            {
                result.add(t_Attribute);
            }
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Filters certain attributes.
     * @param attributes the attributes.
     * @param toExclude the attributes to exclude.
     * @return such list.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected <K> List<Attribute<K>> filterAttributes(
        @NotNull final List<Attribute<K>> attributes,
        @NotNull final List<Attribute<K>> toExclude)
    {
        @NotNull final List<Attribute<K>> result = new ArrayList<Attribute<K>>(attributes);

        result.removeAll(toExclude);

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves the custom selects.
     * @return such list of {@link Sql} elements.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<Sql> getCustomSelects()
    {
        return decorate(getCustomSelects(getTable(), getCustomSqlProvider()));
    }

    /**
     * Retrieves the custom selects.
     * @param table the table.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return such list of {@link Sql} elements.
     */
    @NotNull
    protected List<Sql> getCustomSelects(
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return getCustomSelects(table, customSqlProvider.getSqlDAO());
    }

    /**
     * Retrieves the custom selects.
     * @param table the table.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @return such list of {@link Sql} elements.
     */
    @NotNull
    protected List<Sql> getCustomSelects(
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table,
        @NotNull final SqlDAO sqlDAO)
    {
        @NotNull final List<Sql> result = sqlDAO.findSelects(table.getName());

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves the custom select-for-update queries.
     * @return such list of {@link Sql} elements.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<Sql> getCustomSelectsForUpdate()
    {
        return decorate(getCustomSelectsForUpdate(getTable(), getCustomSqlProvider()));
    }

    /**
     * Retrieves the custom select-for-update queries.
     * @param table the table.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return such list of {@link Sql} elements.
     */
    @NotNull
    protected List<Sql> getCustomSelectsForUpdate(
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return getCustomSelectsForUpdate(table, customSqlProvider.getSqlDAO());
    }

    /**
     * Retrieves the custom select-for-update queries.
     * @param table the table.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @return such list of {@link Sql} elements.
     */
    @NotNull
    protected List<Sql> getCustomSelectsForUpdate(
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table,
        @NotNull final SqlDAO sqlDAO)
    {
        @NotNull final List<Sql> result = sqlDAO.findSelectsForUpdate(table.getName());

        Collections.sort(result);

        return result;
    }

    /**
     * Decorates given {@link Sql queries.}
     * @param queries the queries to decorate.
     * @return the decorated queries.
     */
    @NotNull
    protected List<Sql> decorate(@NotNull final List<Sql> queries)
    {
        return decorate(queries, getCustomSqlProvider(), getMetadataManager());
    }

    /**
     * Decorates given {@link Sql queries.}
     * @param queries the queries to decorate.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the decorated queries.
     */
    @NotNull
    protected List<Sql> decorate(
        @NotNull final List<Sql> queries,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        @NotNull final List<Sql> result = new ArrayList<Sql>(queries.size());

        for (@Nullable final Sql t_Sql : queries)
        {
            if (t_Sql != null)
            {
                result.add(decorate(t_Sql, customSqlProvider, metadataManager));
            }
        }

        return result;
    }

    /**
     * Decorates given {@link Sql query}.
     * @param query the query to decorate.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the decorated query.
     */
    @NotNull
    protected Sql decorate(
        @NotNull final Sql query,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        return new CachingSqlDecorator(query, customSqlProvider, metadataManager);
    }

    /**
     * Retrieves the custom updates or inserts.
     * @return such information.
     */
    @NotNull
    public List<Sql> getCustomUpdatesOrInserts()
    {
        return decorate(getCustomUpdatesOrInserts(getTable(), getCustomSqlProvider()));
    }

    /**
     * Retrieves the custom updates or inserts.
     * @return such information.
     */
    @NotNull
    protected List<Sql> getCustomUpdatesOrInserts(
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return getCustomUpdatesOrInserts(table.getName(), customSqlProvider.getSqlDAO());
    }

    /**
     * Retrieves the custom updates or inserts.
     * @param tableName the table name.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @return such information.
     */
    @NotNull
    protected List<Sql> getCustomUpdatesOrInserts(
        @NotNull final String tableName, @NotNull final SqlDAO sqlDAO)
    {
        @NotNull final List<Sql> result = new ArrayList<Sql>(sqlDAO.findInserts(tableName));

        result.addAll(sqlDAO.findUpdates(tableName));

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves the custom results.
     * @return such list of {@link ResultDecorator} elements.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<ResultDecorator> getCustomResults()
    {
        return getCustomResults(getTable(), getCustomSqlProvider(), getMetadataManager(), getDecoratorFactory());
    }


    /**
     * Retrieves the custom results.
     * @param table the {@link Table} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return such list of {@link Result} elements.
     */
    @NotNull
    protected List<ResultDecorator> getCustomResults(
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return
            getCustomResults(
                table.getName(),
                customSqlProvider.getSqlDAO(),
                customSqlProvider.getSqlResultDAO(),
                customSqlProvider,
                metadataManager,
                decoratorFactory);
    }

    /**
     * Retrieves the custom results.
     * @param tableName the table name.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @param resultDAO the {@link SqlResultDAO} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return such list of {@link Result} elements.
     */
    @NotNull
    protected List<ResultDecorator> getCustomResults(
        @NotNull final String tableName,
        @NotNull final SqlDAO sqlDAO,
        @NotNull final SqlResultDAO resultDAO,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull final List<ResultDecorator> result;

        @NotNull final List<Result> aux = new ArrayList<Result>(2);

        @Nullable Result singleResult = resultDAO.findSingleMatch(tableName);

        if (singleResult != null)
        {
            aux.add(singleResult);
        }

        @Nullable Result multipleResult = resultDAO.findMultipleMatch(tableName);

        if (   (multipleResult == null)
            && (singleResult != null))
        {
            multipleResult =
                new ResultElement(
                    singleResult.getId().replaceFirst("single\\.", "multiple\\."),
                    singleResult.getClassValue(),
                    Result.MULTIPLE);

            aux.add(multipleResult);
        }
        else if (   (multipleResult != null)
                 && (singleResult == null))
        {
            singleResult =
                new ResultElement(
                    multipleResult.getId().replaceFirst("multiple\\.", "single\\."),
                    multipleResult.getClassValue(),
                    Result.MULTIPLE);

            aux.add(singleResult);
        }

        @Nullable Result customResult;

        List<Sql> sqlList = sqlDAO.findSelects(tableName);

        for (@Nullable final Sql t_Sql : sqlList)
        {
            if (t_Sql != null)
            {
                customResult = resultDAO.findBySqlId(t_Sql.getId());

                if (   (customResult != null)
                    && (!aux.contains(customResult)))
                {
                    aux.add(customResult);
                }
            }
        }

        sqlList = sqlDAO.findSelectsForUpdate(tableName);

        for (@Nullable final Sql t_Sql : sqlList)
        {
            if (t_Sql != null)
            {
                customResult = resultDAO.findBySqlId(t_Sql.getId());

                if (   (customResult != null)
                    && (!aux.contains(customResult)))
                {
                    aux.add(customResult);
                }
            }
        }

        result = new ArrayList<ResultDecorator>(aux.size());

        for (@NotNull final Result t_Result : aux)
        {
            result.add(decorate(t_Result, customSqlProvider, metadataManager, decoratorFactory));
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Decorates given result.
     * @param customResult the {@link Result} element.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the decorated version.
     */
    @NotNull
    protected ResultDecorator decorate(
        @NotNull final Result customResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return new CachingResultDecorator(customResult, customSqlProvider, metadataManager, decoratorFactory);
    }

    /**
     * Decorates given foreign keys.
     * @param foreignKeys the {@link ForeignKey} list.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return the decorated version.
     */
    @NotNull
    protected List<ForeignKey<DecoratedString>> decorate(
        @NotNull final List<ForeignKey<String>> foreignKeys,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        @NotNull final List<ForeignKey<DecoratedString>> result =
            new ArrayList<ForeignKey<DecoratedString>>(foreignKeys.size());

        for (@Nullable final ForeignKey<String> t_ForeignKey : foreignKeys)
        {
            if (t_ForeignKey != null)
            {
                result.add(decorate(t_ForeignKey, metadataManager, decoratorFactory, customSqlProvider));
            }
        }

        return result;
    }

    /**
     * Decorates given foreign key.
     * @param foreignKey the {@link ForeignKey}.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return the decorated version.
     */
    @NotNull
    protected ForeignKey<DecoratedString> decorate(
        @NotNull final ForeignKey<String> foreignKey,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return new CachingForeignKeyDecorator(foreignKey, metadataManager, decoratorFactory, customSqlProvider);
    }

    /**
     * Decorates given attribute.
     * @param attribute the attribute to decorate.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the decorated attribute.
     */
    @NotNull
    protected Attribute<DecoratedString> decorate(
        @NotNull final Attribute<String> attribute, @NotNull final MetadataManager metadataManager)
    {
        return new CachingAttributeDecorator(attribute, metadataManager);
    }

    /**
     * Retrieves the list of different results defined for this table (using the referring custom-selects).
     * @return such list.
     */
    @SuppressWarnings("unused")
    @NotNull
    @Override
    public List<Result> getDifferentCustomResults()
    {
        return getDifferentCustomResults(getTable(), getCustomSqlProvider());
    }

    /**
     * Retrieves the list of different results defined for this table (using the referring custom-selects).
     * @return such list.
     */
    @NotNull
    protected List<Result> getDifferentCustomResults(
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return
            getDifferentCustomResults(
                table.getName(),
                getName().getVoName().getValue(),
                customSqlProvider.getSqlDAO(),
                customSqlProvider.getSqlResultDAO(),
                getMetadataManager(),
                customSqlProvider,
                getDecoratorFactory(),
                CustomResultUtils.getInstance());
    }

    /**
     * Retrieves the list of different results defined for this table (using the referring custom-selects).
     * @param table the {@link Table} instance.
     * @param voName the ValueObject name.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @param resultDAO the {@link SqlResultDAO} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param decoratorFactory the {@link DecoratorFactory} implementation.
     * @param customResultUtils the {@link CustomResultUtils} instance.
     * @return such list.
     */
    @NotNull
    protected List<Result> getDifferentCustomResults(
        @NotNull final String table,
        @NotNull final String voName,
        @NotNull final SqlDAO sqlDAO,
        @NotNull final SqlResultDAO resultDAO,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomResultUtils customResultUtils)
    {
        @NotNull final List<Result> result = new ArrayList<Result>();

        @NotNull final List<Sql> t_lSql = sqlDAO.findSelects(table);
        t_lSql.addAll(sqlDAO.findSelectsForUpdate(table));

        for (@Nullable final Sql t_Sql : t_lSql)
        {
            if (t_Sql != null)
            {
                @Nullable final ResultRef t_ResultRef = t_Sql.getResultRef();

                if (t_ResultRef != null)
                {
                    @Nullable final Result t_Result = resultDAO.findByPrimaryKey(t_ResultRef.getId());
                    if (   (t_Result != null)
                        && (!matches(t_Result.getClassValue(), voName))
                        && (!result.contains(t_Result)))
                    {
                        result.add(
                            customResultUtils.decorate(
                                t_Result, metadataManager, customSqlProvider, decoratorFactory));
                    }
                }
            }
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Checks whether given ValueObject classes match.
     * @param classValue the class value.
     * @param voName the ValueObject name.
     * @return <code>true</code> in such case.
     */
    protected boolean matches(@Nullable final String classValue, @NotNull final String voName)
    {
        boolean result = false;

        if (classValue != null)
        {
            @NotNull final String[] t_astrParts = classValue.split("\\.");

            if (t_astrParts.length > 0)
            {
                result = voName.equals(t_astrParts[t_astrParts.length - 1]);
            }
        }

        return result;
    }

    /**
     * Decorates given rows.
     * @param rows the rows to decorate.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the list of decorated rows.
     */
    @NotNull protected List<Row<DecoratedString>> decorate(
        @NotNull final List<Row<String>> rows,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull final List<Row<DecoratedString>> result = new ArrayList<Row<DecoratedString>>(rows.size());

        for (@Nullable final Row<String> row : rows)
        {
            if (row != null)
            {
                result.add(new CachingRowDecorator(row, metadataManager, decoratorFactory));
            }
        }
        return result;
    }

    /**
     * Retrieves the ordered list of the fully-qualified attribute types.
     * @return such list.
     */
    @Override
    @NotNull
    public List<DecoratedString> getAttributeTypes()
    {
        return getAttributeTypes(getAttributes(), getMetadataManager().getMetadataTypeManager());
    }

    /**
     * Retrieves the ordered list of the fully-qualified types of given attributes.
     * @param attrs such attributes.
     * @return such list.
     */
    @NotNull
    protected List<DecoratedString> getAttributeTypes(
        @NotNull final List<Attribute<DecoratedString>> attrs,
        @NotNull final MetadataTypeManager typeManager)
    {
        @NotNull final List<DecoratedString> result = new ArrayList<DecoratedString>(attrs.size());

        for (@Nullable final Attribute<DecoratedString> attr: attrs)
        {
            if (attr != null)
            {
                @Nullable final String importType =
                    typeManager.getImport(
                        typeManager.getJavaType(attr.getType().getValue()));

                if (importType != null)
                {
                    result.add(new DecoratedString(importType));
                }
            }
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves the string representation.
     *
     * @return such text.
     */
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + AbstractTableDecorator.class.getName() + '"'
            + ", \"table\": " + m__Table
            + ", \"metadataManager\": " + m__MetadataManager
            + ", \"decoratorFactory\": " + m__DecoratorFactory
            + ", \"customSqlProvider\": " + m__CustomSqlProvider
            + ", \"readOnlyAttributes\": " + ToStringUtils.getInstance().toJson(m__lReadOnlyAttributes)
            + ", \"externallyManagedAttributes\": " + ToStringUtils.getInstance().toJson(m__lExternallyManagedAttributes)
            + ", \"foreignKeys\": " + ToStringUtils.getInstance().toJson(m__lForeignKeys)
            + ", \"parentForeignKey\": " + m__ParentForeignKey
            + ", \"childAttributes\": " + ToStringUtils.getInstance().toJson(m__lChildAttributes)
            + ", \"attributesShouldBeCleanedUp\": " + m__bAttributesShouldBeCleanedUp
            + '}';
    }
}
