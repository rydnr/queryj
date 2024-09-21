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
     * Retrieves the {@link SqlConnectionFlagsDAO} instance.
     * @return such instance.
     */
    @NotNull
    SqlConnectionFlagsDAO getSqlConnectionFlagsDAO();

    /**
     * Retrieves the {@link SqlStatementFlagsDAO} instance.
     * @return such instance.
     */
    @NotNull
    SqlStatementFlagsDAO getSqlStatementFlagsDAO();

    /**
     * Retrieves the {@link SqlResultSetFlagsDAO} instance.
     * @return such instance.
     */
    @NotNull
    SqlResultSetFlagsDAO getSqlResultSetFlagsDAO();

    /**
     * Computes the hash for given {@link Sql} instance.
     * @param sql the SQL instance.
     * @param charset the charset.
     * @param <T> the type.
     * @return the hash.
     */
    @NotNull
    <T> String getHash(@NotNull final Sql<T> sql, @NotNull final String charset);

    /**
     * Computes the hash for given {@link Parameter parameter}.
     * @param parameter the parameter.
     * @param charset the charset.
     * @param <T> the type.
     * @param <V> the type of the parameter value.
     * @return the hash.
     */
    @NotNull
    <T, V> String getHash(@NotNull final Parameter<T, V> parameter, @NotNull final String charset);

    /**
     * Computes the hash for given {@link Result result}.
     * @param result the result.
     * @param charset the charset.
     * @param <T> the type.
     * @return the hash.
     */
    @NotNull
    <T> String getHash(@NotNull final Result<T> result, @NotNull final String charset);

    /**
     * Computes the hash for given {@link Property} information.
     * @param property the property.
     * @param charset the charset.
     * @param <T> the type.
     * @return the computed hash.
     */
    @NotNull
    <T> String getHash(@NotNull final Property<T> property, @NotNull final String charset);
}