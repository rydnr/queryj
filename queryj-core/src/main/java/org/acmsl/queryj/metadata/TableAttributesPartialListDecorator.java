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
 * Importing QueryJ-Core classes.
 */
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Partial list decorator for table attributes.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/12/30 10:25
 */
@ThreadSafe
public class TableAttributesPartialListDecorator<T>
    implements PartialListDecorator,
               TableDecorator
{
    /**
     * The table decorator.
     */
    private TableDecorator m__Table;

    /**
     * The list decorator.
     */
    private ListDecorator<T> m__ListDecorator;

    /**
     * The operation types.
     */
    public static enum Operation
    {
        PLUS,
        MINUS
    }

    /**
     * The operation.
     */
    private Operation m__Operation;

    /**
     * Creates a new instance.
     * @param listDecorator the {@link ListDecorator}.
     * @param table the {@link TableDecorator}.
     * @param operation the {@link Operation}.
     */
    public TableAttributesPartialListDecorator(
        @NotNull final ListDecorator<T> listDecorator,
        @NotNull final TableDecorator table,
        @NotNull final Operation operation)
    {
        immutableSetListDecorator(listDecorator);
        immutableSetTable(table);
        immutableSetOperation(operation);
    }

    /**
     * Specifies the list decorator.
     * @param listDecorator such instance.
     */
    protected final void immutableSetListDecorator(@NotNull final ListDecorator<T> listDecorator)
    {
        this.m__ListDecorator = listDecorator;
    }

    /**
     * Specifies the list decorator.
     * @param listDecorator such instance.
     */
    @SuppressWarnings("unused")
    protected void setListDecorator(@NotNull final ListDecorator<T> listDecorator)
    {
        immutableSetListDecorator(listDecorator);
    }

    /**
     * Retrieves the list decorator.
     * @return such instance.
     */
    @SuppressWarnings("unused")
    public ListDecorator<T> getListDecorator()
    {
        return this.m__ListDecorator;
    }

    /**
     * Specifies the table.
     * @param table the table.
     */
    protected final void immutableSetTable(@NotNull final TableDecorator table)
    {
        this.m__Table = table;
    }

    /**
     * Specifies the table.
     * @param table the table.
     */
    @SuppressWarnings("unused")
    protected void setTable(@NotNull final TableDecorator table)
    {
        immutableSetTable(table);
    }

    /**
     * Retrieves the table.
     * @return such instance.
     */
    @NotNull
    public TableDecorator getTable()
    {
        return this.m__Table;
    }

    /**
     * Specifies the operation.
     * @param operation such operation.
     */
    protected final void immutableSetOperation(@NotNull final Operation operation)
    {
        this.m__Operation = operation;
    }

    /**
     * Specifies the operation.
     * @param operation such operation.
     */
    @SuppressWarnings("unused")
    protected void setOperation(@NotNull final Operation operation)
    {
        immutableSetOperation(operation);
    }

    /**
     * Retrieves the operation.
     * @return such operation.
     */
    public Operation getOperation()
    {
        return this.m__Operation;
    }

    // ListDecorator implementation
    @NotNull
    public List<T> getItems()
    {
        throw new RuntimeException("Invalid operation");
    }

    @NotNull
    public PartialListDecorator plus()
    {
        throw new RuntimeException("Invalid operation");
    }

    @NotNull
    public PartialListDecorator minus()
    {
        throw new RuntimeException("Invalid operation");
    }

    // TableDecorator implementation

    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getReadOnlyAttributes()
    {
        return getTable().getReadOnlyAttributes();
    }

    @NotNull
    @Override
    public List<Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>>
    getAllParentTables()
    {
        return getTable().getAllParentTables();
    }

    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getExternallyManagedAttributes()
    {
        return getTable().getExternallyManagedAttributes();
    }

    @NotNull
    @Override
    public List<Sql> getDynamicQueries()
    {
        return getTable().getDynamicQueries();
    }

    @NotNull
    @Override
    public List<Row<DecoratedString>> getStaticContent()
    {
        return getTable().getStaticContent();
    }

    @NotNull
    @Override
    public List<Result> getDifferentCustomResults()
    {
        return getTable().getDifferentCustomResults();
    }

    @NotNull
    @Override
    public List<DecoratedString> getAttributeTypes()
    {
        return getTable().getAttributeTypes();
    }

    // Table implementation
    @NotNull
    @Override
    public DecoratedString getName()
    {
        return getTable().getName();
    }

    @Nullable
    @Override
    public DecoratedString getComment()
    {
        return getTable().getComment();
    }

    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getPrimaryKey()
    {
        return getTable().getPrimaryKey();
    }

    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getAttributes()
    {
        return getTable().getAttributes();
    }

    @NotNull
    @Override
    public List<ForeignKey<DecoratedString>> getForeignKeys()
    {
        return getTable().getForeignKeys();
    }

    @Nullable
    @Override
    public Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>
    getParentTable()
    {
        return getTable().getParentTable();
    }

    @Override
    public boolean isStatic()
    {
        return getTable().isStatic();
    }

    @Override
    public boolean isVoDecorated()
    {
        return getTable().isVoDecorated();
    }

    @Override
    public int compareTo(final Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> table)
    {
        return getTable().compareTo(table);
    }

    // java.util.List implementation
    public List<T> subList(final int i, final int i2)
    {
        return getListDecorator().subList(i, i2);
    }

    public boolean addAll(final Collection<T> attributes)
    {
        return getListDecorator().addAll(attributes);
    }

    public <T> T[] toArray(final T[] ts)
    {
        return getListDecorator().toArray(ts);
    }

    public void clear()
    {
        getListDecorator().clear();
    }

    public boolean remove(final Object o)
    {
        return getListDecorator().remove(o);
    }

    public boolean addAll(final int i, final Collection<T> attributes)
    {
        return getListDecorator().addAll(i, attributes);
    }

    public T remove(final int i)
    {
        return getListDecorator().remove(i);
    }

    public int indexOf(final Object o)
    {
        return getListDecorator().indexOf(o);
    }

    public ListIterator<T> listIterator(final int i)
    {
        return getListDecorator().listIterator(i);
    }

    public boolean removeAll(final Collection<?> objects)
    {
        return getListDecorator().removeAll(objects);
    }

    public T set(final int i, final T item)
    {
        return getListDecorator().set(i, item);
    }

    public ListIterator<T> listIterator()
    {
        return getListDecorator().listIterator();
    }

    public void add(final int i, final T item)
    {
        getListDecorator().add(i, item);
    }

    public boolean containsAll(final Collection<?> objects)
    {
        return getListDecorator().containsAll(objects);
    }

    public boolean add(final T item)
    {
        return getListDecorator().add(item);
    }

    public boolean retainAll(final Collection<?> objects)
    {
        return getListDecorator().retainAll(objects);
    }

    public boolean isEmpty()
    {
        return getListDecorator().isEmpty();
    }

    public Iterator<T> iterator()
    {
        return getListDecorator().iterator();
    }

    public T get(final int i)
    {
        return getListDecorator().get(i);
    }

    public boolean contains(final Object o)
    {
        return getListDecorator().contains(o);
    }

    public int lastIndexOf(final Object o)
    {
        return getListDecorator().lastIndexOf(o);
    }

    public int size()
    {
        return getListDecorator().size();
    }

    public Object[] toArray()
    {
        return getListDecorator().toArray();
    }
}
