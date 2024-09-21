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
 * Filename: PrimaryKeyDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Provides methods to access information about primary keys.
 *
 * Date: 6/6/12
 * Time: 5:34 AM
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing some project classes.
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

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * Provides methods to access information about primary keys.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
public interface PrimaryKeyDAO
    extends DAO
{
    /**
     * Retrieves the primary key for given table.
     *
     * @param table the table.
     * @return the {@link Attribute} of the primary key for given table.
     */
    @NotNull
    @SuppressWarnings("unused")
    List<Attribute<String>> findPrimaryKey(@NotNull final String table);

    /**
     * Retrieves whether given {@link Attribute column} is part of the primary key for the table, or not.
     * @param table the table name.
     * @param column the column name.
     * @return <code>true</code> if the column is part of the primary key.
     */
    @SuppressWarnings("unused")
    boolean isPartOfPrimaryKey(
        @NotNull final String table, @NotNull final String column);

    /**
     * Inserts a new primary key (needed when processing manual schemas).
     * @param table the table name.
     * @param primaryKey the primary key.
     */
    void insert(
        @NotNull final String table,
        @NotNull final List<Attribute<String>> primaryKey);
}
