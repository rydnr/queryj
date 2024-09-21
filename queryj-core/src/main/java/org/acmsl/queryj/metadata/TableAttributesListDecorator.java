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
 * Filename: TableAttributesListDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description:  ListDecorator wrapping the list of attributes of a given
 *               table.
 * Date: 2013/12/30
 * Time: 11:18
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
import java.util.List;

/**
 * {@link ListDecorator} wrapping the list of {@link Attribute attributes} of
 * a given {@link TableDecorator table}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/12/30 11:18
 */
@ThreadSafe
public class TableAttributesListDecorator
    extends AbstractTableListDecorator<Attribute<DecoratedString>>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -4692044880067201567L;

    /**
     * Creates a new instance.
     * @param list the attributes.
     * @param table the wrapped table.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     */
    public TableAttributesListDecorator(
        @NotNull final List<Attribute<DecoratedString>> list,
        @NotNull final TableDecorator table,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        super(list, table, customSqlProvider, decoratorFactory);
    }

    // TableDecorator implementation

    /**
     * Retrieves all attributes, including parent's.
     * @return such attributes.
     */
    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getAll()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * Retrieves all attributes, including parent's.
     * @return such attributes.
     */
    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getAllAttributes()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * Checks whether some of the attributes are nullable or not.
     * @return {@code true} in such case.
     */
    @Override
    public boolean getContainsNullableAttributes()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * Checks whether some of the attributes cannot be null.
     * @return {@code true} in such case.
     */
    @Override
    public boolean getContainsNotNullAttributes()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * Retrieves the custom result.
     * @return such {@link org.acmsl.queryj.metadata.ResultDecorator} element.
     */
    @Nullable
    @Override
    public Result<DecoratedString> getCustomResult()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * Retrieves the custom selects.
     * @return such list of {@link org.acmsl.queryj.customsql.Sql} elements.
     */
    @NotNull
    @Override
    public List<Sql<DecoratedString>> getCustomSelects()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * Retrieves the custom updates or inserts.
     * @return such information.
     */
    @NotNull
    @Override
    public List<Sql<DecoratedString>> getCustomUpdatesOrInserts()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * Retrieves the custom select-for-update queries.
     * @return such list of {@link org.acmsl.queryj.customsql.Sql} elements.
     */
    @NotNull
    @Override
    public List<Sql<DecoratedString>> getCustomSelectsForUpdate()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * Retrieves the name of the parent table, or {@code null} if no parent exists.
     * @return such information.
     */
    @Nullable
    @Override
    public DecoratedString getParentTableName()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * Retrieves the parent foreign-key.
     * @return such foreign key.
     */
    @Nullable
    @Override
    public ForeignKey<DecoratedString> getParentForeignKey()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * Alias to make templates more readable.
     * @return the child attributes.
     */
    @Nullable
    @Override
    public ListDecorator<Attribute<DecoratedString>> getChild()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * Retrieves the nullable attributes.
     * @return such list.
     */
    @NotNull
    @Override
    public List<Attribute<DecoratedString>> getNullableAttributes()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    protected ListDecorator<Attribute<DecoratedString>> createListDecorator(
        @NotNull final List<Attribute<DecoratedString>> items,
        @NotNull final TableDecorator table,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return new TableAttributesListDecorator(items, table, customSqlProvider, decoratorFactory);
    }
}
