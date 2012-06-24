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
 * Filename: JdbcTableDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: MetadataManager-backed TableDAO instance.
 *
 * Date: 6/6/12
 * Time: 6:26 AM
 *
 */
package org.acmsl.queryj.metadata.engines;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.TableDAO;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.Table;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * {@link MetadataManager}-backed {@link TableDAO} instance.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
public abstract class MetadataManagerTableDAO<M extends MetadataManager>
    implements TableDAO
{
    /**
     * The {@link MetadataManager} instance.
     */
    private M m__MetadataManager;

    /**
     * Creates a new {@link MetadataManagerTableDAO} instance using given {@link MetadataManager}.
     * @param manager the {@link MetadataManager} instance.
     */
    public MetadataManagerTableDAO(@NotNull final M manager)
    {
        immutableSetMetadataManager(manager);
    }

    /**
     * Specifies the {@link MetadataManager} instance.
     * @param manager the {@link MetadataManager manager}.
     */
    protected final void immutableSetMetadataManager(@NotNull final M manager)
    {
        m__MetadataManager = manager;
    }

    /**
     * Specifies the {@link MetadataManager} instance.
     * @param manager the {@link MetadataManager manager}.
     */
    @SuppressWarnings("unused")
    protected void setMetadataManager(@NotNull final M manager)
    {
        immutableSetMetadataManager(manager);
    }

    /**
     * Retrieves the {@link MetadataManager} instance.
     * @return such {@link MetadataManager instance}.
     */
    @NotNull
    protected M getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Retrieves the table matching given name.
     * @param name the table name.
     * @return the associated {@link Table} instance, if the table is found.
     */
    @Override
    @Nullable
    public Table findByName(@NotNull final String name)
    {
        return findByName(name, getMetadataManager().isCaseSensitive());
    }

    /**
     * Retrieves the table matching given name.
     * @param name the table name.
     * @param caseSensitiveness whether the engine is case sensitive or not.
     * @return the associated {@link Table} instance, if the table is found.
     */
    @Nullable
    protected Table findByName(@NotNull final String name, final boolean caseSensitiveness)
    {
        @Nullable Table result = null;

        @NotNull final List<Table> t_Tables = findAllTables();

        for (@Nullable Table t_Table : t_Tables)
        {
            if (t_Table != null)
            {
                if (   (caseSensitiveness)
                    && (t_Table.getName().equals(name)))
                {
                    result = t_Table;
                    break;
                }
                else if (   (!caseSensitiveness)
                         && (t_Table.getName().equalsIgnoreCase(name)))
                {
                    result = t_Table;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the list of tables with foreign keys to given table.
     * @param target the target table.
     * @return the list of referring tables.
     */
    @NotNull
    public List<Table> findReferringTables(@NotNull final String target)
    {
        @NotNull final List<Table> result = new ArrayList<Table>(1);

        @Nullable final List<Table> t_Tables = findAllTables();

        @NotNull List<ForeignKey> t_lForeignKeys;

        for (@Nullable Table t_Table : t_Tables)
        {
            if (t_Table != null)
            {
                t_lForeignKeys = t_Table.getForeignKeys();

                for (@Nullable ForeignKey t_ForeignKey : t_lForeignKeys)
                {
                    if (   (t_ForeignKey != null)
                        && (t_ForeignKey.getTargetTableName().equalsIgnoreCase(target)))
                    {
                        result.add(t_Table);
                        break;

                    }
                }
            }
        }

        return result;
    }
}
