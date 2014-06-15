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
 * Filename: AbstractListDecorator.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Core implementation of ListDecorator.
 *
 * Date: 12/29/13
 * Time: 12:16 PM
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.vo.Attribute;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Core implementation of {@link org.acmsl.queryj.metadata.ListDecorator}.
 * @param <T> the item type.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/12/29
 */
public abstract class AbstractListDecorator<T>
    implements ListDecorator<T>
{
    /**
     * The actual list.
     */
    @NotNull
    private List<T> m__lList;

    /**
     * Creates a new decorator.
     * @param list the list to decorate.
     */
    public AbstractListDecorator(@NotNull final List<T> list)
    {
        immutableSetList(list);
    }

    /**
     * Specifies the list.
     * @param list such list.
     */
    protected final void immutableSetList(@NotNull final List<T> list)
    {
        this.m__lList = list;
    }

    /**
     * Specifies the list.
     * @param list such list.
     */
    @SuppressWarnings("unused")
    protected void setList(@NotNull final List<T> list)
    {
        immutableSetList(list);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<T> getItems()
    {
        return this.m__lList;
    }

    // java.util.List implementation
    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<T> subList(final int i, final int i2)
    {
        return getItems().subList(i, i2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(@NotNull final Collection<? extends T> attributes)
    {
        return getItems().addAll(attributes);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public <T> T[] toArray(@NotNull final T[] ts)
    {
        return getItems().toArray(ts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear()
    {
        getItems().clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(@Nullable final Object o)
    {
        return getItems().remove(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(final int i, @NotNull final Collection<? extends T> attributes)
    {
        return getItems().addAll(i, attributes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public T remove(final int i)
    {
        return getItems().remove(i);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int indexOf(@Nullable final Object o)
    {
        return getItems().indexOf(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public ListIterator<T> listIterator(final int i)
    {
        return getItems().listIterator(i);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(@NotNull final Collection<?> objects)
    {
        return getItems().removeAll(objects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T set(final int i, @Nullable final T item)
    {
        return getItems().set(i, item);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ListIterator<T> listIterator()
    {
        return getItems().listIterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(final int i, @Nullable final T item)
    {
        getItems().add(i, item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(@NotNull final Collection<?> objects)
    {
        return getItems().containsAll(objects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(@NotNull final T item)
    {
        return getItems().add(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(@NotNull final Collection<?> objects)
    {
        return getItems().retainAll(objects);
    }

    /**
     * Checks whether the list is empty.
     * @return such information.
     */
    @Override
    public boolean isEmpty()
    {
        return getItems().isEmpty();
    }

    /**
     * Retrieves an {@link Iterator} to browse all items.
     * @return such iterator.
     */
    @Override
    @NotNull
    public Iterator<T> iterator()
    {
        return getItems().iterator();
    }

    /**
     * Retrieves the item at given position.
     * @param i the position.
     * @return the item.
     */
    @Override
    @Nullable
    public T get(final int i)
    {
        return getItems().get(i);
    }

    /**
     * Checks whether given item is included.
     * @param o the item.
     * @return {@code true} in such case.
     */
    @Override
    public boolean contains(@Nullable final Object o)
    {
        return getItems().contains(o);
    }

    /**
     * Finds the position of given item in the list.
     * @param o the item.
     * @return among all occurrences of the item in the list, the position of the right-most item.
     */
    @Override
    public int lastIndexOf(@Nullable final Object o)
    {
        return getItems().lastIndexOf(o);
    }

    /**
     * Returns the number of items.
     * @return such count.
     */
    @Override
    public int size()
    {
        return getItems().size();
    }

    /**
     * Converts the items to an array.
     * @return such items.
     */
    @NotNull
    @Override
    public Object[] toArray()
    {
        return getItems().toArray();
    }

    /**
     * Retrieves the attributes.
     * @param items the items.
     * @return the items, if they're the attributes. An empty list otherwise.
     */
    @NotNull
    protected List<Attribute<DecoratedString>> retrieveAttributes(@NotNull final List<T> items)
    {
        return retrieveAttributes(items, TableDecoratorHelper.getInstance());
    }

    /**
     * Retrieves the attributes.
     * @param items the items.
     * @param tableDecoratorHelper the {@link TableDecoratorHelper} instance.
     * @return the items, if they're the attributes. An empty list otherwise.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected List<Attribute<DecoratedString>> retrieveAttributes(
        @NotNull final List<T> items, @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        @NotNull final List<Attribute<DecoratedString>> result;

        if (tableDecoratorHelper.isListOfAttributes(items))
        {
            result = (List<Attribute<DecoratedString>>) items;
        }
        else
        {
            result = new ArrayList<>(0);
        }

        return result;

    }

    /**
     * Retrieves the custom results.
     * @param items the items.
     * @return the items, if they're the custom results. An empty list otherwise.
     */
    @NotNull
    protected List<Result<DecoratedString>> retrieveCustomResults(@NotNull final List<T> items)
    {
        return retrieveCustomResults(items, TableDecoratorHelper.getInstance());
    }

    /**
     * Retrieves the attributes.
     * @param items the items.
     * @param tableDecoratorHelper the {@link TableDecoratorHelper} instance.
     * @return the items, if they're the custom results. An empty list otherwise.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected List<Result<DecoratedString>> retrieveCustomResults(
        @NotNull final List<T> items, @NotNull final TableDecoratorHelper tableDecoratorHelper)
    {
        @NotNull final List<Result<DecoratedString>> result;

        if (tableDecoratorHelper.isListOfCustomResults(items))
        {
            result = (List<Result<DecoratedString>>) items;
        }
        else
        {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * Writes a textual representation of the instance.
     * @return such text.
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + AbstractListDecorator.class.getName()
            + ", \"list\": " + this.m__lList
            + " }";
    }
}
