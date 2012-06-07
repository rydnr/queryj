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
 * Filename: ForeignKeyDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Provides methods to access ForeignKey information.
 *
 * Date: 6/6/12
 * Time: 5:40 AM
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.vo.Attribute;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;

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
 * Provides methods to access {@link ForeignKey} information.
 * @author <a href="mailto:chous@acm-sl.org>chous</a>
 * @since 2012/06/06
 */
public interface ForeignKeyDAO
    extends DAO
{
    /**
     * Retrieves all {@link ForeignKey foreign keys} for a given table.
     * @param table the table.
     * @param catalog the catalog.
     * @param schema the schema.
     * @return the list of foreign keys.
     */
    @NotNull
    List<ForeignKey> findForeignKeys(
        @NotNull final String table, @Nullable final String catalog, @Nullable final String schema);

    /**
     * Retrieves all {@link ForeignKey foreign keys} pointing to a given table.
     * @param table the table.
     * @param catalog the catalog.
     * @param schema the schema.
     * @return the list of foreign keys.
     */
    @NotNull
    List<ForeignKey> findReferringForeignKeys(
        @NotNull final String table, @Nullable final String catalog, @Nullable final String schema);

    /**
     * Inserts a new {@link ForeignKey} (required when processing explicit schemas).
     * @param table the table.
     * @param sourceAttributes the foreign key attributes.
     * @param targetAttributes the primary key attributes in the referred table.
     * @param catalog the catalog.
     * @param schema the schema.
     */
    void insert(
        @NotNull final String table,
        @NotNull final List<Attribute> sourceAttributes,
        @NotNull final List<Attribute> targetAttributes,
        @Nullable final String catalog,
        @Nullable final String schema);
}
