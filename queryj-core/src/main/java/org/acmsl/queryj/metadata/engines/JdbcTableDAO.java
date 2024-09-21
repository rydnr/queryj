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
 * Description: A TableDAO implementation using JdbcMetadataManager.
 *
 * Date: 6/12/12
 * Time: 10:54 AM
 *
 */
package org.acmsl.queryj.metadata.engines;

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.Table;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
* Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * A {@link org.acmsl.queryj.metadata.TableDAO} implementation using {@link JdbcMetadataManager}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @version 2012/06/12
 */
@ThreadSafe
public class JdbcTableDAO
    extends MetadataManagerTableDAO<JdbcMetadataManager>
{
    /**
     * Creates a {@link JdbcTableDAO} using given manager.
     * @param manager the {@link JdbcMetadataManager} instance.
     */
    public JdbcTableDAO(@NotNull final JdbcMetadataManager manager)
    {
        super(manager);
    }

    /**
     * Retrieves all tables.
     * @return such information.
     */
    @NotNull
    @Override
    public List<String> findAllTableNames()
    {
        return getMetadataManager().getTableNames();
    }

    /**
     * Retrieves all tables.
     * @return such information.
     */
    @NotNull
    @Override
    public List<Table<String, Attribute<String>, List<Attribute<String>>>> findAllTables()
    {
        return getMetadataManager().getTables();
    }

    /**
     * Retrieves the table matching given name.
     * @param name the table name.
     * @param catalog the catalog.
     * @param schema the schema.
     * @return the associated {@link Table} instance, if the table is found.
     */
    @Nullable
    public Table<String, Attribute<String>, List<Attribute<String>>> findByName(
        @NotNull final String name,
        @SuppressWarnings("unused") @Nullable final String catalog,
        @SuppressWarnings("unused") @Nullable final String schema)
    {
        @Nullable Table<String, Attribute<String>, List<Attribute<String>>> result = null;

        final boolean t_bCaseSensitive = getMetadataManager().isCaseSensitive();

        for (@Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table : getMetadataManager().getTables())
        {
            if (t_Table != null)
            {
                if (   (t_bCaseSensitive)
                    && (t_Table.getName().equals(name)))
                {
                    result = t_Table;
                    break;
                }
                else if (   (!t_bCaseSensitive)
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
    @Override
    @NotNull
    public List<Table<String, Attribute<String>, List<Attribute<String>>>> findReferringTables(
        @NotNull final String target)
    {
        @NotNull final List<Table<String, Attribute<String>, List<Attribute<String>>>> result =
            new ArrayList<>(1);

        final boolean t_bCaseSensitive = getMetadataManager().isCaseSensitive();

        for (@Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table : getMetadataManager().getTables())
        {
            if (t_Table != null)
            {
                for (@Nullable final ForeignKey<String> t_ForeignKey : t_Table.getForeignKeys())
                {
                    if (t_ForeignKey != null)
                    {
                        if (   (t_bCaseSensitive)
                            && (t_ForeignKey.getTargetTableName().equals(target)))
                        {
                            result.add(t_Table);
                            break;
                        }
                        else if (   (!t_bCaseSensitive)
                                 && (t_ForeignKey.getTargetTableName().equalsIgnoreCase(target)))
                        {
                            result.add(t_Table);
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }
}
