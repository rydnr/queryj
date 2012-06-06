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
package org.acmsl.queryj.tools.metadata.engines;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.metadata.ForeignKeyDAO;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.RefactoredMetadataManager;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;

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
 * {@link MetadataManager}-backed {@link ForeignKeyDAO} implementation.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
public class MetadataManagerForeignKeyDAO
    implements ForeignKeyDAO
{
    /**
     * The {@link MetadataManager} instance.
     */
    private RefactoredMetadataManager m__MetadataManager;

    /**
     * Creates a new {@link MetadataManagerColumnDAO} instance using given {@link MetadataManager}.
     * @param manager the {@link MetadataManager} instance.
     */
    public MetadataManagerForeignKeyDAO(@NotNull final RefactoredMetadataManager manager)
    {
        immutableSetMetadataManager(manager);
    }

    /**
     * Specifies the {@link MetadataManager} instance.
     * @param manager the {@link MetadataManager manager}.
     */
    protected final void immutableSetMetadataManager(@NotNull final RefactoredMetadataManager manager)
    {
        m__MetadataManager = manager;
    }

    /**
     * Specifies the {@link MetadataManager} instance.
     * @param manager the {@link MetadataManager manager}.
     */
    @SuppressWarnings("unused")
    protected void setMetadataManager(@NotNull final RefactoredMetadataManager manager)
    {
        immutableSetMetadataManager(manager);
    }

    /**
     * Retrieves the {@link MetadataManager} instance.
     * @return such {@link MetadataManager instance}.
     */
    @NotNull
    protected RefactoredMetadataManager getMetadataManager()
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
     * Retrieves all {@link org.acmsl.queryj.tools.metadata.vo.ForeignKey foreign keys} pointing to a given table.
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
}
