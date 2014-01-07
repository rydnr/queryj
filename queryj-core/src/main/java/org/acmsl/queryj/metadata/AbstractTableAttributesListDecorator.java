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
 * Filename: AbstractTableAttributesListDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description:
 *
 * Date: 2013/12/30
 * Time: 11:00
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.metadata.TableAttributesPartialListDecorator.Operation;
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
import java.util.List;

/**
 *
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/12/30 11:00
 */
@ThreadSafe
public abstract class AbstractTableAttributesListDecorator<T>
    extends AbstractListDecorator<T>
    implements TableDecorator<T>
{
    /**
     * The table decorator.
     */
    private TableDecorator<T> m__Table;

    /**
     * Creates a new instance.
     * @param list the {@link List}.
     * @param table the {@link TableDecorator}.
     */
    public AbstractTableAttributesListDecorator(
        @NotNull final List<T> list,
        @NotNull final TableDecorator<T> table)
    {
        super(list);
        immutableSetTable(table);
    }

    /**
     * Specifies the table.
     * @param table the table.
     */
    protected final void immutableSetTable(@NotNull final TableDecorator<T> table)
    {
        this.m__Table = table;
    }

    /**
     * Specifies the table.
     * @param table the table.
     */
    @SuppressWarnings("unused")
    protected void setTable(@NotNull final TableDecorator<T> table)
    {
        immutableSetTable(table);
    }

    /**
     * Retrieves the table.
     * @return such instance.
     */
    @NotNull
    public TableDecorator<T> getTable()
    {
        return this.m__Table;
    }

    @NotNull
    @Override
    public PartialListDecorator getPlus()
    {
        return
            new TableAttributesPartialListDecorator<T>(
                this, getTable(), Operation.PLUS);
    }

    @NotNull
    @Override
    public PartialListDecorator getMinus()
    {
        return
            new TableAttributesPartialListDecorator<T>(
                this, getTable(), Operation.MINUS);
    }

    // TableDecorator implementation

    @NotNull
    @Override
    public ListDecorator<T> getReadOnlyAttributes()
    {
        throw new RuntimeException("Invalid operation");
    }

    @NotNull
    @Override
    public List<Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>>>
    getAllParentTables()
    {
        throw new RuntimeException("Invalid operation");
    }

    @NotNull
    @Override
    public ListDecorator<T> getExternallyManagedAttributes()
    {
        throw new RuntimeException("Invalid operation");
    }

    @NotNull
    @Override
    public List<Sql> getDynamicQueries()
    {
        throw new RuntimeException("Invalid operation");
    }

    @NotNull
    @Override
    public List<Row<DecoratedString>> getStaticContent()
    {
        throw new RuntimeException("Invalid operation");
    }

    @NotNull
    @Override
    public List<Result> getDifferentCustomResults()
    {
        throw new RuntimeException("Invalid operation");
    }

    @NotNull
    @Override
    public List<DecoratedString> getAttributeTypes()
    {
        throw new RuntimeException("Invalid operation");
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

    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": " + AbstractTableAttributesListDecorator.class.getName() + '"'
            + ", \"table\": " + m__Table
            + " }";
    }
}
