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
 * Filename: TableDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Provides the methods to access Table information.
 *
 * Date: 6/6/12
 * Time: 5:24 AM
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.metadata.vo.Table;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.dao.DAO;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.sql.SQLException;
import java.util.List;

/**
 * Provides the methods to access {@link Table} information.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 3.0
 * Created: 2012/06/06
 */
public interface TableDAO
    extends DAO
{
    /**
     * Retrieves all tables.
     * @return such information.
     */
    @NotNull
    List<Table<String, Attribute<String>, List<Attribute<String>>>> findAllTables();

    /**
     * Retrieves all tables.
     * @return such information.
     */
    @NotNull
    List<String> findAllTableNames();

    /**
     * Retrieves the table matching given name.
     * @param name the table name.
     * @return the associated {@link Table} instance, if the table is found.
     */
    @Nullable
    Table<String, Attribute<String>, List<Attribute<String>>> findByName(@NotNull final String name);

    /**
     * Retrieves the list of tables with foreign keys to given table.
     * @param target the target table.
     * @return the list of referring tables.
     */
    @SuppressWarnings("unused")
    @NotNull
    List<Table<String, Attribute<String>, List<Attribute<String>>>> findReferringTables(
        @NotNull final String target);

    /**
     * Retrieves the table associated to given DAO.
     * @param dao the DAO name.
     * @return the table.
     */
    @Nullable
    Table<String, Attribute<String>, List<Attribute<String>>> findByDAO(@NotNull final String dao);

    /**
     * Retrieves the actual contents of given table.
     * @param tableName the table name.
     * @return the retrieved rows.
     * @throws SQLException if the query fails.
     */
    @NotNull
    List<Row<String>> queryContents(@NotNull final String tableName)
        throws SQLException;
}
