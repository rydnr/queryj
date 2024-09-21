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
 * Filename: TableCustomResultsListDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ListDecorator for custom results.
 *
 * Date: 2014/06/14
 * Time: 18:55
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.metadata.impl.TableResultDecoratorImpl;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * {@link ListDecorator} for custom results.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/06/14 18:55
 */
@ThreadSafe
public class TableCustomResultsListDecorator
    extends AbstractTableListDecorator<Result<DecoratedString>>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 6374510951489078749L;

    /**
     * Creates a new instance.
     * @param list the list.
     * @param table the {@link TableDecorator table}.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     */
    public TableCustomResultsListDecorator(
        @NotNull final List<Result<DecoratedString>> list,
        @NotNull final TableDecorator table,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        super(list, table, customSqlProvider, decoratorFactory);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<Result<DecoratedString>> getItems()
    {
        return decorate(super.getItems(), getTable(), getCustomSqlProvider(), getDecoratorFactory());
    }

    /**
     * Decorates the items.
     * @param items the items to decorate.
     * @param table the {@link TableDecorator table}.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the decorated items.
     */
    @NotNull
    public List<Result<DecoratedString>> decorate(
        @NotNull final List<Result<DecoratedString>> items,
        @NotNull final TableDecorator table,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull final List<Result<DecoratedString>> result = new ArrayList<>(items.size());

        for (@Nullable final Result<DecoratedString> item : items)
        {
            if (item != null)
            {
                result.add(decorate(item, table, customSqlProvider, decoratorFactory));
            }
        }

        return result;
    }

    /**
     * Decorates given item.
     * @param item the result to decorate.
     * @param table the {@link TableDecorator table}.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the decorated item.
     */
    @NotNull
    protected TableResultDecorator<DecoratedString> decorate(
        @NotNull final Result<DecoratedString> item,
        @NotNull final TableDecorator table,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return new TableResultDecoratorImpl<>(item, table, customSqlProvider, decoratorFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    protected ListDecorator<Result<DecoratedString>> createListDecorator(
        @NotNull final List<Result<DecoratedString>> items,
        @NotNull final TableDecorator table,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return new TableCustomResultsListDecorator(items, table, customSqlProvider, decoratorFactory);
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
            + ", \"class\": \"TableCustomResultsListDecorator\""
            + ", \"package\": \"org.acmsl.queryj.metadata\" }";
    }
}
