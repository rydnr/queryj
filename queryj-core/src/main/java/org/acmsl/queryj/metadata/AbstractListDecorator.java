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
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.ToStringUtils;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
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

    @NotNull
    @Override
    public List<T> getItems()
    {
        return this.m__lList;
    }

    // java.util.List implementation
    @NotNull
    @Override
    public List<T> subList(final int i, final int i2)
    {
        return getItems().subList(i, i2);
    }

    @Override
    public boolean addAll(@NotNull final Collection<? extends T> attributes)
    {
        return getItems().addAll(attributes);
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull final T[] ts)
    {
        return getItems().toArray(ts);
    }

    @Override
    public void clear()
    {
        getItems().clear();
    }

    @Override
    public boolean remove(@Nullable final Object o)
    {
        return getItems().remove(o);
    }

    @Override
    public boolean addAll(final int i, @NotNull final Collection<? extends T> attributes)
    {
        return getItems().addAll(i, attributes);
    }

    @Override
    @Nullable
    public T remove(final int i)
    {
        return getItems().remove(i);
    }

    @Override
    public int indexOf(@Nullable final Object o)
    {
        return getItems().indexOf(o);
    }

    @Override
    @NotNull
    public ListIterator<T> listIterator(final int i)
    {
        return getItems().listIterator(i);
    }

    @Override
    public boolean removeAll(@NotNull final Collection<?> objects)
    {
        return getItems().removeAll(objects);
    }

    @Override
    public T set(final int i, @Nullable final T item)
    {
        return getItems().set(i, item);
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator()
    {
        return getItems().listIterator();
    }

    @Override
    public void add(final int i, @Nullable final T item)
    {
        getItems().add(i, item);
    }

    @Override
    public boolean containsAll(@NotNull final Collection<?> objects)
    {
        return getItems().containsAll(objects);
    }

    @Override
    public boolean add(@NotNull final T item)
    {
        return getItems().add(item);
    }

    @Override
    public boolean retainAll(@NotNull final Collection<?> objects)
    {
        return getItems().retainAll(objects);
    }

    @Override
    public boolean isEmpty()
    {
        return getItems().isEmpty();
    }

    @Override
    @NotNull
    public Iterator<T> iterator()
    {
        return getItems().iterator();
    }

    @Override
    @Nullable
    public T get(final int i)
    {
        return getItems().get(i);
    }

    @Override
    public boolean contains(@Nullable final Object o)
    {
        return getItems().contains(o);
    }

    @Override
    public int lastIndexOf(@Nullable final Object o)
    {
        return getItems().lastIndexOf(o);
    }

    @Override
    public int size()
    {
        return getItems().size();
    }

    @NotNull
    @Override
    public Object[] toArray()
    {
        return getItems().toArray();
    }

    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + AbstractListDecorator.class.getName()
            + ", \"list\": " + ToStringUtils.getInstance().toJson(this.m__lList)
            + " }";
    }
}
