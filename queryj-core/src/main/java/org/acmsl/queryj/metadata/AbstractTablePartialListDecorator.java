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
 * Filename: AbstractTablePartialListDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base class for partial list decorators, wrapping a table.
 *
 * Date: 2014/06/14
 * Time: 18:42
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/**
 * Base class for partial list decorators, wrapping a table.
 * @param <V> the type of the items.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/06/14 18:42
 */
@ThreadSafe
public abstract class AbstractTablePartialListDecorator<V>
    extends AbstractPartialListDecorator<V>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 8290862520868390047L;

    /**
     * The table decorator.
     */
    private TableDecorator m__Table;

    /**
     * Creates a new instance.
     * @param listDecorator the list decorator.
     * @param operation the operation.
     * @param table the table.
     */
    protected AbstractTablePartialListDecorator(
        @NotNull final ListDecorator<V> listDecorator,
        @NotNull final Operation operation,
        @NotNull final TableDecorator table)
    {
        super(listDecorator, operation);
        immutableSetTable(table);
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
     * Checks whether given list is composed of {@link org.acmsl.queryj.metadata.vo.Attribute attributes} or not.
     * @param list such list.
     * @return {@code true} in such case.
     */
    protected boolean isListOfAttributes(@NotNull final ListDecorator<V> list)
    {
        final boolean result;

        if (list.size() > 0)
        {
            @Nullable final V item = list.get(0);

            if (item instanceof Attribute)
            {
                result = true;
            }
            else
            {
                result = false;
            }
        }
        else
        {
            result = true;
        }

        return result;
    }

    /**
     * Checks whether given list is composed of {@link Attribute attributes} or not.
     * @param list such list.
     * @return {@code true} in such case.
     */
    protected boolean isListOfCustomResults(@NotNull final ListDecorator<V> list)
    {
        final boolean result;

        if (list.size() > 0)
        {
            @Nullable final V item = list.get(0);

            if (item instanceof Result)
            {
                result = true;
            }
            else
            {
                result = false;
            }
        }
        else
        {
            result = true;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"table\": \"" + m__Table
            + ", \"super\": " + super.toString()
            + ", \"class\": \"" + AbstractTablePartialListDecorator.class.getSimpleName() + '"'
            + ", \"package\": \"org.acmsl.queryj.metadata\""
            + " }";
    }
}
