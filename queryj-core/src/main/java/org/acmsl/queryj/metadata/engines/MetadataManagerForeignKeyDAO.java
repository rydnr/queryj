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
 * Filename: MetadataManagerForeignKeyDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 6/6/12
 * Time: 6:46 AM
 *
 */
package org.acmsl.queryj.metadata.engines;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.metadata.ForeignKeyDAO;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;

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

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * {@link MetadataManager}-backed {@link ForeignKeyDAO} implementation.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
@ThreadSafe
public class MetadataManagerForeignKeyDAO
    implements ForeignKeyDAO
{
    /**
     * The {@link MetadataManager} instance.
     */
    private MetadataManager m__MetadataManager;

    /**
     * Creates a new {@link MetadataManagerColumnDAO} instance using given {@link MetadataManager}.
     * @param manager the {@link MetadataManagerForeignKeyDAO} instance.
     */
    public MetadataManagerForeignKeyDAO(@NotNull final MetadataManager manager)
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
     * Retrieves all {@link ForeignKey foreign keys} for a given table.
     * @param table   the table.
     * @param catalog the catalog.
     * @param schema  the schema.
     * @return the list of foreign keys.
     */
    @NotNull
    @Override
    public List<ForeignKey> findForeignKeys(
        @NotNull final String table, @Nullable final String catalog, @Nullable final String schema)
    {
        // TODO
        return new ArrayList<ForeignKey>(0);
    }

    /**
     * Retrieves all {@link org.acmsl.queryj.metadata.vo.ForeignKey foreign keys} pointing to a given table.
     * @param table the table.
     * @param catalog the catalog.
     * @param schema the schema.
     * @return the list of foreign keys.
     */
    @NotNull
    @Override
    public List<ForeignKey> findReferringForeignKeys(
        @NotNull final String table, @Nullable final String catalog, @Nullable final String schema)
    {
        // TODO
        return new ArrayList<ForeignKey>(0);
    }

    /**
     * Inserts a new {@link ForeignKey} (required when processing explicit schemas).
     * @param table the table.
     * @param sourceAttributes the foreign key attributes.
     * @param targetAttributes the primary key attributes in the referred table.
     * @param catalog the catalog.
     * @param schema the schema.
     */
    public void insert(
        @NotNull final String table,
        @NotNull final List<Attribute> sourceAttributes,
        @NotNull final List<Attribute> targetAttributes,
        @Nullable final String catalog,
        @Nullable final String schema)
    {
        // TODO
    }
}
