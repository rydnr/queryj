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
 * Filename: RefactoredMetadataManager.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: A refactored version of the original MetadataManager.
 *
 * Date: 6/6/12
 * Time: 8:43 AM
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * A refactored version of the original {@link MetadataManager}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
public interface RefactoredMetadataManager
{
    /**
     * Retrieves the name identifying the manager instance.
     * @return such name.
     */
    public String getName();

    /**
     * Retrieves the metadata.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    public void eagerlyFetchMetadata()
      throws SQLException,
             QueryJException;

    /**
     * Retrieves the {@link DatabaseMetaData}.
     * @return such information.
     */
    @NotNull
    public DatabaseMetaData getMetaData();

    /**
     * Retrieves the {@link TableDAO} instance.
     * @return such instance.
     */
    @NotNull
    TableDAO getTableDAO();

    /**
     * Retrieves the {@link ColumnDAO} instance.
     * @return such instance.
     */
    @NotNull
    ColumnDAO getColumnDAO();

    /**
     * Retrieves the {@link PrimaryKeyDAO} instance.
     * @return such instance.
     */
    @NotNull
    PrimaryKeyDAO getPrimaryKeyDAO();

    /**
     * Retrieves the {@link ForeignKeyDAO} instance.
     * @return such instance.
     */
    @NotNull
    ForeignKeyDAO getForeignKeyDAO();
}
