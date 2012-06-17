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
 * Filename: MetadataManagerPrimaryKeyDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: MetadataManager-backed PrimaryKeyDAO implementation.
 *
 * Date: 6/6/12
 * Time: 6:41 AM
 *
 */
package org.acmsl.queryj.tools.metadata.engines;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.PrimaryKeyDAO;
import org.acmsl.queryj.tools.metadata.vo.Attribute;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * {@link MetadataManager}-backed {@link PrimaryKeyDAO} implementation.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
public class MetadataManagerPrimaryKeyDAO
    implements PrimaryKeyDAO
{
    /**
     * The {@link MetadataManager} instance.
     */
    private MetadataManager m__MetadataManager;

    /**
     * Creates a new {@link MetadataManagerPrimaryKeyDAO} instance using given {@link MetadataManager}.
     * @param manager the {@link MetadataManager} instance.
     */
    public MetadataManagerPrimaryKeyDAO(@NotNull final MetadataManager manager)
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
     * Retrieves the primary key for given table.
     *
     * @param table the table.
     * @return the {@link Attribute} of the primary key for given table.
     */
    @NotNull
    @Override
    public List<Attribute> findPrimaryKey(@NotNull final String table)
    {
        // TODO
        return new ArrayList<Attribute>(0);
    }

    /**
     * Retrieves whether given {@link Attribute column} is part of the primary key
     * for the table, or not.
     * @param table the table name.
     * @param column the column name.
     * @return <code>true</code> if the column is part of the primary key.
     */
    @Override
    public boolean isPartOfPrimaryKey(@NotNull final String table, @NotNull final String column)
    {
        // TODO
        return false;
    }

    /**
     * Inserts a new primary key (needed when processing manual schemas).
     * @param table the table name.
     * @param primaryKey the primary key.
     */
    public void insert(
        @NotNull final String table, @NotNull final List<Attribute> primaryKey)
    {
        // TODO
    }
}
