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
 * Filename: SqlParameterDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Provides methods to access Parameter information.
 *
 * Date: 6/6/12
 * Time: 6:08 AM
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.Parameter;

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
 * Provides methods to access {@link Parameter} information.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
public interface SqlParameterDAO
    extends DAO
{
    /**
     * Retrieves the {@link Parameter} associated to given id.
     * @param id the parameter id.
     * @return the {@link Parameter}, or <code>null</code> if not found.
     */
    @Nullable
    Parameter<String, ?> findByPrimaryKey(@NotNull final String id);

    /**
     * Retrieves all {@link Parameter parameters} used in given {@link org.acmsl.queryj.customsql.Sql}.
     * @param sqlId the {@link org.acmsl.queryj.customsql.Sql} identifier.
     * @return the list of parameters required by given {@link org.acmsl.queryj.customsql.Sql}.
     */
    @NotNull
    List<Parameter<String, ?>> findBySql(@NotNull final String sqlId);
}
