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
 * Filename: TableAttributesPartialListDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Partial list decorator for table attributes.
 *
 * Date: 2013/12/30
 * Time: 10:25
 *
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
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Partial list decorator for table attributes.
 * @param <V> the type of the items.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/12/30 10:25
 */
@ThreadSafe
public class TablePartialListDecorator<V>
    extends AbstractTablePartialListDecorator<V>
    implements TableDecorator
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -7540355104418964835L;

    /**
     * The custom SQL provider.
     */
    private CustomSqlProvider m__CustomSqlProvider;

    /**
     * The decorator factory.
     */
    private DecoratorFactory m__DecoratorFactory;

    /**
     * Creates a new instance.
     * @param listDecorator the {@link ListDecorator}.
     * @param table the {@link TableDecorator}.
     * @param operation the {@link Operation}.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     */
    public TablePartialListDecorator(
        @NotNull final ListDecorator<V> listDecorator,
        @NotNull final TableDecorator table,
        @NotNull final Operation operation,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        super(listDecorator, operation, table);
        immutableSetCustomSqlProvider(customSqlProvider);
        immutableSetDecoratorFactory(decoratorFactory);
    }

    /**
     * Specifies the custom sql provider.
     * @param provider such {@link CustomSqlProvider instance}.
     */
    protected final void immutableSetCustomSqlProvider(@NotNull final CustomSqlProvider provider)
    {
        this.m__CustomSqlProvider = provider;
    }

    /**
     * Specifies the custom sql provider.
     * @param provider such {@link CustomSqlProvider instance}.
     */
    @SuppressWarnings("unused")
    protected void setCustomSqlProvider(@NotNull final CustomSqlProvider provider)
    {
        immutableSetCustomSqlProvider(provider);
    }

    /**
     * Retrieves the custom sql provider.
     * @return such {@link CustomSqlProvider instance}.
     */
    @NotNull
    public CustomSqlProvider getCustomSqlProvider()
    {
        return this.m__CustomSqlProvider;
    }

    /**
     * Specifies the decorator factory.
     * @param factory the {@link DecoratorFactory} instance.
     */
    protected final void immutableSetDecoratorFactory(@NotNull final DecoratorFactory factory)
    {
        this.m__DecoratorFactory = factory;
    }

    /**
     * Specifies the decorator factory.
     * @param factory the {@link DecoratorFactory} instance.
     */
    @SuppressWarnings("unused")
    protected void setDecoratorFactory(@NotNull final DecoratorFactory factory)
    {
        immutableSetDecoratorFactory(factory);
    }

    /**
     * Retrieves the decorator factory.
     * @return the {@link DecoratorFactory} instance.
     */
    @NotNull
    public DecoratorFactory getDecoratorFactory()
    {
        return this.m__DecoratorFactory;
    }

    // TableDecorator implementation

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getReadOnlyAttributes()
    {
        return getReadOnlyAttributes(getListDecorator(), getTable(), getOperation());
    }

    /**
     * An alias to make templates more readable.
     * @return the read-only attributes.
     */
    @SuppressWarnings("unused")
    @NotNull
    public ListDecorator<Attribute<DecoratedString>> getReadOnly()
    {
        return getReadOnlyAttributes();
    }

    /**
     * Retrieves the result of adding or removing the read-only attributes from the wrapped list.
     * @param list the list.
     * @param table the table.
     * @param operation the operation.
     * @return the resulting attributes.
     */
    @NotNull
    protected ListDecorator<Attribute<DecoratedString>> getReadOnlyAttributes(
        @NotNull final ListDecorator<V> list,
        @NotNull final TableDecorator table,
        @NotNull final Operation operation)
    {
        @NotNull final ListDecorator<Attribute<DecoratedString>> result;

        if (isListOfAttributes(list))
        {
            @SuppressWarnings("unchecked")
            @NotNull final List<Attribute<DecoratedString>> aux =
                new ArrayList<>((ListDecorator<Attribute<DecoratedString>>) list);

            if (operation.equals(Operation.MINUS))
            {
                aux.removeAll(table.getReadOnlyAttributes());
            }
            else if (operation.equals(Operation.PLUS))
            {
                aux.addAll(table.getReadOnlyAttributes());
            }
            else
            {
                aux.retainAll(table.getReadOnlyAttributes());
            }

            result = new TableAttributesListDecorator(aux, table, getCustomSqlProvider(), getDecoratorFactory());
        }
        else
        {
            throw new RuntimeException(ListDecorator.INVALID_OPERATION);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>>
    getAllParentTables()
    {
        return getTable().getAllParentTables();
    }

    /**
     * An alias to make templates more readable.
     * @return the externally managed attributes.
     */
    @SuppressWarnings("unused")
    @NotNull
    public ListDecorator<Attribute<DecoratedString>> getExternallyManaged()
    {
        return getExternallyManagedAttributes();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getExternallyManagedAttributes()
    {
        return getExternallyManagedAttributes(getListDecorator(), getTable(), getOperation());
    }

    /**
     * Retrieves the result of adding or removing the externally-managed attributes from the wrapped list.
     * @param list the list.
     * @param table the table.
     * @param operation the operation.
     * @return the resulting attributes.
     */
    @NotNull
    protected ListDecorator<Attribute<DecoratedString>> getExternallyManagedAttributes(
        @NotNull final ListDecorator<V> list,
        @NotNull final TableDecorator table,
        @NotNull final Operation operation)
    {
        @NotNull final ListDecorator<Attribute<DecoratedString>> result;

        if (isListOfAttributes(list))
        {
            @SuppressWarnings("unchecked")
            @NotNull final List<Attribute<DecoratedString>> aux =
                (List<Attribute<DecoratedString>>) new ArrayList<>(list);

            if (operation.equals(Operation.MINUS))
            {
                aux.removeAll(table.getExternallyManagedAttributes());
            }
            else if (operation.equals(Operation.PLUS))
            {
                aux.addAll(table.getExternallyManagedAttributes());
            }
            else
            {
                aux.retainAll(table.getExternallyManagedAttributes());
            }

            result = new TableAttributesListDecorator(aux, table, getCustomSqlProvider(), getDecoratorFactory());
        }
        else
        {
            throw new UnsupportedOperationException();
        }

        return result;
    }

    /**
     * Conditionally adds each element of the source, to the destination list,
     * if it's not included already.
     * @param source the source.
     * @param destination the destination.
     */
    @SuppressWarnings("unchecked")
    protected void addAll(
        @NotNull final ListDecorator<Attribute<DecoratedString>> source,
        @NotNull final List<Attribute<DecoratedString>> destination)
    {
        for (@Nullable final Attribute<DecoratedString> item : source)
        {
            if (   (item != null)
                && (!destination.contains(item)))
            {
                destination.add(item);
            }
        }
    }

    /**
     * Conditionally removes each element of the source, from the destination list,
     * if it's included already.
     * @param source the source.
     * @param destination the destination.
     */
    @SuppressWarnings("unused")
    protected void removeAll(
        @NotNull final List<Attribute<DecoratedString>> source,
        @NotNull final List<Attribute<DecoratedString>> destination)
    {
        for (@Nullable final Attribute<DecoratedString> item : source)
        {
            if (   (item != null)
                && (destination.contains(item)))
            {
                destination.remove(item);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<Sql<DecoratedString>> getDynamicQueries()
    {
        return getTable().getDynamicQueries();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<Row<DecoratedString>> getStaticContent()
    {
        return getTable().getStaticContent();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ListDecorator<Result<DecoratedString>> getCustomResults()
    {
        return getTable().getCustomResults();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<DecoratedString> getAttributeTypes()
    {
        return getTable().getAttributeTypes();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<DecoratedString> getNullableAttributeTypes()
    {
        return getTable().getNullableAttributeTypes();
    }

    // Table implementation
    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public DecoratedString getName()
    {
        return getTable().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public DecoratedString getComment()
    {
        return getTable().getComment();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getPrimaryKey()
    {
        return getPrimaryKey(getListDecorator(), getTable(), getOperation());
    }

    /**
     * Retrieves the result of adding or removing the primary key from the wrapped list.
     * @param list the list.
     * @param table the table.
     * @param operation the operation.
     * @return the resulting attributes.
     */
    @NotNull
    protected ListDecorator<Attribute<DecoratedString>> getPrimaryKey(
        @NotNull final ListDecorator<V> list,
        @NotNull final TableDecorator table,
        @NotNull final Operation operation)
    {
        @NotNull final ListDecorator<Attribute<DecoratedString>> result;

        if (isListOfAttributes(list))
        {
            @SuppressWarnings("unchecked")
            @NotNull final List<Attribute<DecoratedString>> aux =
                (List<Attribute<DecoratedString>>) new ArrayList<>(list);

            if (operation.equals(Operation.MINUS))
            {
                aux.removeAll(table.getPrimaryKey());
            }
            else if (operation.equals(Operation.PLUS))
            {
                addAll(table.getPrimaryKey(), aux);
            }
            else
            {
                aux.retainAll(table.getPrimaryKey());
            }

            result = new TableAttributesListDecorator(aux, table, getCustomSqlProvider(), getDecoratorFactory());
        }
        else
        {
            throw new UnsupportedOperationException();
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getAttributes()
    {
        return getAttributes(getListDecorator(), getTable(), getOperation());
    }

    /**
     * Retrieves the result of adding or removing the primary key from the wrapped list.
     * @param list the list.
     * @param table the table.
     * @param operation the operation.
     * @return the resulting attributes.
     */
    @NotNull
    protected ListDecorator<Attribute<DecoratedString>> getAttributes(
        @NotNull final ListDecorator<V> list,
        @NotNull final TableDecorator table,
        @NotNull final Operation operation)
    {
        @NotNull final ListDecorator<Attribute<DecoratedString>> result;

        if (isListOfAttributes(list))
        {
            @SuppressWarnings("unchecked")
            @NotNull final List<Attribute<DecoratedString>> aux =
                (List<Attribute<DecoratedString>>) new ArrayList<>(list);

            if (operation.equals(Operation.MINUS))
            {
                aux.removeAll(table.getAttributes());
            }
            else if (operation.equals(Operation.PLUS))
            {
                addAll(table.getAttributes(), aux);
            }
            else
            {
                aux.retainAll(table.getAttributes());
            }

            result = new TableAttributesListDecorator(aux, table, getCustomSqlProvider(), getDecoratorFactory());
        }
        else
        {
            throw new UnsupportedOperationException();
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<ForeignKey<DecoratedString>> getForeignKeys()
    {
        return getTable().getForeignKeys();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>
    getParentTable()
    {
        return getTable().getParentTable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStatic()
    {
        return getTable().isStatic();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public Attribute<DecoratedString> getStaticAttribute()
    {
        return getTable().getStaticAttribute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVoDecorated()
    {
        return getTable().isVoDecorated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRelationship()
    {
        return getTable().isRelationship();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public MetadataManager getMetadataManager()
    {
        return getTable().getMetadataManager();
    }

    /**
     * Retrieves all attributes, including parent's.
     *
     * @return such attributes.
     */
    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getAll()
    {
        return getTable().getAll();
    }

    /**
     * Checks whether any attribute is a clob.
     *
     * @return {@code true} in such case.
     */
    @Override
    public boolean getContainsClobs()
    {
        return getTable().getContainsClobs();
    }

    /**
     * Retrieves all attributes, including parent's.
     *
     * @return such attributes.
     */
    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getAllAttributes()
    {
        return getTable().getAll();
    }

    /**
     * Checks whether some of the attributes are nullable or not.
     *
     * @return {@code true} in such case.
     */
    @Override
    public boolean getContainsNullableAttributes()
    {
        return getTable().getContainsNullableAttributes();
    }

    /**
     * Checks whether some of the attributes cannot be null.
     *
     * @return {@code true} in such case.
     */
    @Override
    public boolean getContainsNotNullAttributes()
    {
        return getTable().getContainsNotNullAttributes();
    }

    /**
     * Retrieves the custom result.
     *
     * @return such {@link org.acmsl.queryj.metadata.ResultDecorator} element.
     */
    @Nullable
    @Override
    public Result<DecoratedString> getCustomResult()
    {
        return getTable().getCustomResult();
    }

    /**
     * Retrieves the custom selects.
     *
     * @return such list of {@link org.acmsl.queryj.customsql.Sql} elements.
     */
    @NotNull
    @Override
    public List<Sql<DecoratedString>> getCustomSelects()
    {
        return getTable().getCustomSelects();
    }

    /**
     * Retrieves the custom updates or inserts.
     *
     * @return such information.
     */
    @NotNull
    @Override
    public List<Sql<DecoratedString>> getCustomUpdatesOrInserts()
    {
        return getTable().getCustomUpdatesOrInserts();
    }

    /**
     * Retrieves the custom select-for-update queries.
     *
     * @return such list of {@link org.acmsl.queryj.customsql.Sql} elements.
     */
    @NotNull
    @Override
    public List<Sql<DecoratedString>> getCustomSelectsForUpdate()
    {
        return getTable().getCustomSelectsForUpdate();
    }

    /**
     * Retrieves the name of the parent table, or {@code null} if no parent exists.
     *
     * @return such information.
     */
    @Nullable
    @Override
    public DecoratedString getParentTableName()
    {
        return getTable().getParentTableName();
    }

    /**
     * Retrieves the parent foreign-key.
     *
     * @return such foreign key.
     */
    @Nullable
    @Override
    public ForeignKey<DecoratedString> getParentForeignKey()
    {
        return getTable().getParentForeignKey();
    }

    /**
     * Alias to make templates more readable.
     *
     * @return the table's own attributes.
     */
    @Nullable
    @Override
    public ListDecorator<Attribute<DecoratedString>> getOwn()
    {
        return getTable().getOwn();
    }

    /**
     * Alias to make templates more readable.
     *
     * @return the child attributes.
     */
    @Nullable
    @Override
    public ListDecorator<Attribute<DecoratedString>> getChild()
    {
        return getTable().getChild();
    }

    /**
     * Retrieves the nullable attributes.
     *
     * @return such list.
     */
    @NotNull
    @Override
    public List<Attribute<DecoratedString>> getNullableAttributes()
    {
        return getTable().getNullableAttributes();
    }


    /**
     * Compares the wrapped table to given one.
     * @param table the table to compare with.
     * @return the result of comparing them.
     */
    @Override
    public int compareTo(
        @Nullable final Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> table)
    {
        return getTable().compareTo(table);
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
            + ", \"customSqlProvider\": " + this.m__CustomSqlProvider
            + ", \"decoratorFactory\": " + this.m__DecoratorFactory
            + ", \"class\": \"" + TablePartialListDecorator.class.getSimpleName() + '"'
            + ", \"package\": \"org.acmsl.queryj.metadata\""
            + " }";
    }

    /**
     * Retrieves the items, after removing duplicates.
     * @return such list.
     */
    @NotNull
    public ListDecorator<V> getDifferent()
    {
        return
            getDifferent(
                getListDecorator(),
                getTable(),
                getCustomSqlProvider(),
                getDecoratorFactory(),
                TableDecoratorHelper.getInstance());
    }

    /**
     * Retrieves the items, after removing duplicates.
     * @param items the items.
     * @param table the {@link TableDecorator table}.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param tableDecoratorHelper the {@link TableDecoratorHelper} instance.
     * @return such list.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected ListDecorator<V> getDifferent(
        @NotNull final ListDecorator<V> items,
        @NotNull final TableDecorator table,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        @NotNull final ListDecorator<V> result;

        @NotNull final List<V> aux = new ArrayList<>(items.size());

        for (@Nullable final V item : items)
        {
            if (!aux.contains(item))
            {
                aux.add(item);
            }
        }

        if (tableDecoratorHelper.isListOfAttributes(items))
        {
            result =
                (ListDecorator<V>)
                    new TableAttributesListDecorator(
                        (List<Attribute<DecoratedString>>) aux,
                        table,
                        customSqlProvider,
                        decoratorFactory);
        }
        else if (tableDecoratorHelper.isListOfCustomResults(items))
        {
            result =
                (ListDecorator<V>)
                    new TableCustomResultsListDecorator(
                        (List<Result<DecoratedString>>) aux,
                        table,
                        customSqlProvider,
                        decoratorFactory);
        }
        else
        {
            throw new RuntimeException(AbstractTableListDecorator.INVALID_OPERATION);
        }

        return result;
    }
}
