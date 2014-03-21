/*
                        QueryJ-Core

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
import java.util.ArrayList;
import java.util.Arrays;
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
    extends AbstractTableAttributesListDecorator
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -4692044880067201567L;

    /**
     * Creates a new instance.
     * @param list the attributes.
     * @param table the wrapped table.
     */
    public TableAttributesListDecorator(
        @NotNull final List<Attribute<DecoratedString>> list,
        @NotNull final TableDecorator table)
    {
        super(list, table);
    }
    // TableDecorator implementation

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getReadOnlyAttributes()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>>
    getAllParentTables()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ListDecorator<Attribute<DecoratedString>> getExternallyManagedAttributes()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<Sql<DecoratedString>> getDynamicQueries()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<Row<DecoratedString>> getStaticContent()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<Result<DecoratedString>> getDifferentCustomResults()
    {
        throw new RuntimeException(INVALID_OPERATION);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<DecoratedString> getAttributeTypes()
    {
        throw new RuntimeException(INVALID_OPERATION);
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
        return getTable().getPrimaryKey();
    }

    // TODO: remove me
    /**
     * {@inheritDoc}
     */
    public List<Attribute<DecoratedString>> getPrimaryKey1()
    {
        List<Object> a = new ArrayList<>(2);

        Object[] b = { 1, 2, 3};
        a.addAll(Arrays.asList(b));

        return getPrimaryKey();
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
    @Override
    public int compareTo(final Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> table)
    {
        return getTable().compareTo(table);
    }
}
