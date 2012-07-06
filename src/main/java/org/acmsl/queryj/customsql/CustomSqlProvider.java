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
 * Filename: CustomSqlProvider.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the common interfaces of all providers of
 *              custom sql operations.
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.metadata.SqlDAO;
import org.acmsl.queryj.metadata.SqlParameterDAO;
import org.acmsl.queryj.metadata.SqlPropertyDAO;
import org.acmsl.queryj.metadata.SqlResultDAO;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.Serializable;

/**
 * Defines the common interfaces of all providers of
 * custom sql operations.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface CustomSqlProvider
    extends Serializable
{
    /**
     * Retrieves the {@link SqlDAO} instance.
     * @return such instance.
     */
    @SuppressWarnings("unused")
    @NotNull
    SqlDAO getSqlDAO();

    /**
     * Retrieves the {@link SqlResultDAO} instance.
     * @return such instance.
     */
    @SuppressWarnings("unused")
    @NotNull
    SqlResultDAO getSqlResultDAO();

    /**
     * Retrieves the {@link SqlParameterDAO} instance.
     * @return such instance.
     */
    @SuppressWarnings("unused")
    @NotNull
    SqlParameterDAO getSqlParameterDAO();

    /**
     * Retrieves the {@link SqlPropertyDAO} instance.
     * @return such instance.
     */
    @NotNull
    SqlPropertyDAO getSqlPropertyDAO();

    /**
     * Retrieves the {@link ConnectionFlagsDAO} instance.
     * @return such instance.
     */
    @NotNull
    SqlConnectionFlagsDAO getSqlConnectionFlagsDAO();
}

