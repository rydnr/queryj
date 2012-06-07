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
package org.acmsl.queryj.tools.metadata.engines;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.TableDAO;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.metadata.vo.Table;

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
public class MetadataManagerTableDAO
    implements TableDAO
{
    /**
     * The {@link MetadataManager} instance.
     */
    private MetadataManager m__MetadataManager;

    /**
     * Creates a new {@link MetadataManagerTableDAO} instance using given {@link MetadataManager}.
     * @param manager the {@link MetadataManager} instance.
     */
    public MetadataManagerTableDAO(@NotNull final MetadataManager manager)
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
     * Retrieves all tables.
     *
     * @return such information.
     */
    @NotNull
    @Override
    public List<Table> findAllTables()
    {
        // TODO
        return new ArrayList<Table>();
    }

    /**
     * Retrieves the table matching given name.
     * @param name the table name.
     * @param catalog the catalog.
     * @param schema  the schema.
     * @return the associated {@link org.acmsl.queryj.tools.metadata.vo.Table} instance, if the table is found.
     */
    @Override
    public Table findByName(@NotNull final String name, @Nullable final String catalog, @Nullable final String schema)
    {
        // TODO
        return null;
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
