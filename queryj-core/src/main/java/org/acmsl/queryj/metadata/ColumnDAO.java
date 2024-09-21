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
 * Filename: ColumnDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Provides methods to access column information.
 *
 * Date: 6/6/12
 * Time: 5:29 AM
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.vo.Attribute;

/*
 * Importing some ACM-SL Commons classes.
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
import java.util.List;

/**
 * Provides methods to access {@link Attribute column} information.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2012/06/06
 */
public interface ColumnDAO
    extends DAO
{
    /**
     * Retrieves a concrete column in a given table.
     * @param table the table name.
     * @param columnName the column name.
     * @return the column.
     */
    @Nullable
    Attribute<String> findColumn(@NotNull final String table, @NotNull final String columnName);

    /**
     * Retrieves the columns for given table.
     * @param table the table name.
     * @return the columns.
     */
    @NotNull
    List<Attribute<String>> findColumns(@NotNull final String table);

    /**
     * Retrieves the columns for given table, including parent's, if any.
     * @param table the table name.
     * @return the columns.
     */
    @NotNull
    List<Attribute<String>> findAllColumns(@NotNull final String table);

    /**
     * Inserts a new column (needed when processing manually-defined schemas)
     * @param table the table name.
     * @param columnName the column name.
     * @param typeId the column type id.
     */
    void insert(
        @NotNull final String table,
        @NotNull final String columnName,
        final int typeId);

    /**
     * Updates a given column.
     * @param table the table.
     * @param columnName the column name.
     * @param typeId the type id.
     * @param type the type.
     * @param comment the comment.
     * @param ordinalPosition the ordinal position.
     * @param length the maximum allowed length.
     * @param precision the precision.
     * @param value the value.
     * @param keyword the keyword used to get the column's value.
     * @param retrievalQuery the query used to get the column's value.
     * @param readOnly whether it's read-only or not.
     * @param nullable whether it allows null.
     * @param isBoolean whether it's represents boolean values.
     * @param booleanTrue the value representing <code>true</code>.
     * @param booleanFalse the value representing <code>false</code>.
     * @param booleanNull if nullable, the value representing <code>null</code>.
     */
    void update(
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
        @Nullable final String booleanNull);
}
