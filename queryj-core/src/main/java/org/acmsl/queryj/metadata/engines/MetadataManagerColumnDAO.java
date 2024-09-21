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
 * Filename: MetadataManagerColumnDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 6/6/12
 * Time: 6:35 AM
 *
 */
package org.acmsl.queryj.metadata.engines;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.metadata.ColumnDAO;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.TableDAO;
import org.acmsl.queryj.metadata.vo.Attribute;

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.metadata.vo.Table;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * {@link MetadataManager}-backed {@link ColumnDAO} instance.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
@ThreadSafe
public class MetadataManagerColumnDAO
    implements ColumnDAO
{
    /**
     * The {@link MetadataManager} instance.
     */
    private MetadataManager m__MetadataManager;

    /**
     * Creates a new {@link MetadataManagerColumnDAO} instance using given {@link MetadataManager}.
     * @param manager the {@link MetadataManager} instance.
     */
    public MetadataManagerColumnDAO(@NotNull final MetadataManager manager)
    {
        immutableSetMetadataManager(manager);
    }

    /**
     * Specifies the {@link MetadataManager} instance.
     * @param manager the {@link MetadataManager manager}.
     */
    protected final void immutableSetMetadataManager(@NotNull final MetadataManager manager)
    {
        m__MetadataManager = manager;
    }

    /**
     * Specifies the {@link MetadataManager} instance.
     * @param manager the {@link MetadataManager manager}.
     */
    @SuppressWarnings("unused")
    protected void setMetadataManager(@NotNull final MetadataManager manager)
    {
        immutableSetMetadataManager(manager);
    }

    /**
     * Retrieves the {@link MetadataManager} instance.
     * @return such {@link MetadataManager instance}.
     */
    @NotNull
    protected MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Retrieves a concrete column in a given table.
     * @param table the table name.
     * @param columnName the column name.
     * @return the {@link Attribute column}.
     */
    @Nullable
    @Override
    public Attribute<String> findColumn(
        @NotNull final String table,
        @NotNull final String columnName)
    {
        return findColumn(table, columnName, getMetadataManager());
    }

    /**
     * Retrieves a concrete column in a given table.
     * @param table the table name.
     * @param columnName the column name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the {@link Attribute column}.
     */
    @Nullable
    protected Attribute<String> findColumn(
        @NotNull final String table,
        @NotNull final String columnName,
        @NotNull final MetadataManager metadataManager)
    {
        return findColumn(table, columnName, metadataManager.getTableDAO());
    }

    /**
     * Retrieves a concrete column in a given table.
     * @param table the table name.
     * @param columnName the column name.
     * @param tableDAO the {@link TableDAO} instance.
     * @return the {@link Attribute column}.
     */
    @Nullable
    protected Attribute<String> findColumn(
        @NotNull final String table,
        @NotNull final String columnName,
        @NotNull final TableDAO tableDAO)
    {
        Attribute<String> result = null;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            tableDAO.findByName(table);

        if (t_Table != null)
        {
            for (@Nullable final Attribute<String> t_Attribute : t_Table.getAttributes())
            {
                if (   (t_Attribute != null)
                    && (columnName.equalsIgnoreCase(t_Attribute.getName())))
                {
                    result = t_Attribute;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the columns for given table.
     * @param table the table name.
     * @return the list of {@link Attribute columns}.
     */
    @NotNull
    @Override
    public List<Attribute<String>> findColumns(@NotNull final String table)
    {
        return findColumns(table, getMetadataManager());
    }

    /**
     * Retrieves the columns for given table.
     * @param table the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the list of {@link Attribute columns}.
     */
    @NotNull
    protected List<Attribute<String>> findColumns(@NotNull final String table, @NotNull final MetadataManager metadataManager)
    {
        return findColumns(table, metadataManager.getTableDAO());
    }

    /**
     * Retrieves the columns for given table.
     * @param table the table name.
     * @param tableDAO the {@link TableDAO} instance.
     * @return the list of {@link Attribute columns}.
     */
    @NotNull
    protected List<Attribute<String>> findColumns(
        @NotNull final String table, @NotNull final TableDAO tableDAO)
    {
        @NotNull final List<Attribute<String>> result;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            tableDAO.findByName(table);

        if (t_Table != null)
        {
            result = t_Table.getAttributes();
        }
        else
        {
            result = new ArrayList<Attribute<String>>(0);
        }

        return result;
    }

    /**
     * Retrieves the columns for given table, including parent's, if any.
     * @param table the table name.
     * @return the list of {@link Attribute columns}.
     */
    @NotNull
    @Override
    public List<Attribute<String>> findAllColumns(@NotNull final String table)
    {
        return findAllColumns(table, getMetadataManager());
    }

    /**
     * Retrieves the columns for given table, including parent's, if any.
     * @param table the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the list of {@link Attribute columns}.
     */
    @NotNull
    protected List<Attribute<String>> findAllColumns(
        @NotNull final String table, @NotNull final MetadataManager metadataManager)
    {
        return findAllColumns(table, metadataManager.getTableDAO());
    }

    /**
     * Retrieves the columns for given table, including parent's, if any.
     * @param table the table name.
     * @param tableDAO the {@link TableDAO} instance.
     * @return the list of {@link Attribute columns}.
     */
    @NotNull
    protected List<Attribute<String>> findAllColumns(
        @NotNull final String table, @NotNull final TableDAO tableDAO)
    {
        @NotNull final List<Attribute<String>> result = new ArrayList<>(0);

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            tableDAO.findByName(table);

        if (t_Table != null)
        {
            @Nullable Table<String, Attribute<String>, List<Attribute<String>>> t_Parent =
                t_Table.getParentTable();

            while (t_Parent != null)
            {
                result.addAll(t_Parent.getAttributes());

                t_Parent = t_Parent.getParentTable();
            }

            result.addAll(t_Table.getAttributes());
        }

        return result;
    }

    /**
     * Inserts a new column (needed when processing manually-defined schemas)
     * @param table the table name.
     * @param columnName the column name.
     * @param typeId the column type id.
     */
    @Override
    public void insert(
        @NotNull final String table,
        @NotNull final String columnName,
        final int typeId)
    {
        // TODO
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(
        @NotNull final String table,
        @NotNull final String columnName,
        final int typeId,
        @NotNull final String type,
        @Nullable final String comment,
        final int ordinalPosition,
        final int length,
        final int precision,
        @Nullable final String value,
        @Nullable final String keyword,
        @Nullable final String retrievalQuery,
        final boolean readOnly,
        final boolean nullable,
        final boolean isBoolean,
        @Nullable final String booleanTrue,
        @Nullable final String booleanFalse,
        @Nullable final String booleanNull)
    {
        // TODO
    }

    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + MetadataManagerColumnDAO.class.getName() + "\""
            + ", \"metadataManager:\" " + m__MetadataManager + "\" }";
    }
}
