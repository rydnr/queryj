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
 * Filename: SqlPropertyDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Provides methods to access Property information.
 *
 * Date: 7/6/12
 * Time: 6:08 AM
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.Property;

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
 * Provides methods to access {@link Property} information.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/07/06
 */
public interface SqlPropertyDAO
    extends DAO
{
    /**
     * Retrieves the {@link Property} associated to given id.
     * @param id the parameter id.
     * @return the {@link org.acmsl.queryj.customsql.Property}, or <code>null</code> if not found.
     */
    @Nullable
    Property findByPrimaryKey(@NotNull final String id);

    /**
     * Retrieves all {@link org.acmsl.queryj.customsql.Parameter parameters} used in given
     * {@link org.acmsl.queryj.customsql.Result}.
     * @param resultId the {@link org.acmsl.queryj.customsql.Result} identifier.
     * @return the list of properties associated to given {@link org.acmsl.queryj.customsql.Result}.
     */
    @SuppressWarnings("unused")
    @NotNull
    List<Property> findByResult(@NotNull final String resultId);

    /**
     * Inserts a new property.
     * @param id the property id.
     * @param columnName the column name.
     * @param index the property index.
     * @param type the type.
     * @param nullable whether it allows null or not.
     */
    public void insert(
        @NotNull final String id,
        @NotNull final String columnName,
        final int index,
        @NotNull final String type,
        final boolean nullable);
}
