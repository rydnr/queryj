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
import org.acmsl.queryj.metadata.SqlResultDAO;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.Serializable;
import java.util.Collection;

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
    @NotNull
    SqlDAO getSqlDAO();

    /**
     * Retrieves the {@link SqlResultDAO} instance.
     * @return such instance.
     */
    @NotNull
    SqlResultDAO getSqlResultDAO();

    /**
     * Retrieves the {@link SqlParameterDAO} instance.
     * @return such instance.
     */
    @NotNull
    SqlParameterDAO getSqlParameterDAO();

    // EVERYTHING BELOW IS DEPRECATED //
    /**
     * Retrieves the custom sql element collection.
     * return such collection.
     */
    public Collection getCollection();

    /**
     * Resolves the parameter reference.
     * @param reference such reference.
     * @return the referenced parameter.
     */
    @Nullable
    public ParameterElement resolveReference(
        final ParameterRefElement reference);

    /**
     * Resolves the result reference.
     * @param reference such reference.
     * @return the referenced result.
     */
    @Nullable
    public ResultElement resolveReference(
        final ResultRefElement reference);

    /**
     * Resolves the property reference.
     * @param reference such reference.
     * @return the referenced property.
     */
    @Nullable
    public PropertyElement resolveReference(
        final PropertyRefElement reference);

    /**
     * Adds a new property.
     * @param id the id of the property.
     * @param name the property name.
     * @param type the property type.
     */
    public void addProperty(
        final String id, final String name, final String type);

    /**
     * Adds a new result.
     * @param id the id of the result.
     * @param result the result information.
     */
    public void addResult(final String id, final Result result);

    /**
     * Resolves the connection-flags reference.
     * @param reference such reference.
     * @return the referenced property.
     */
    @Nullable
    public ConnectionFlagsElement resolveReference(
        final ConnectionFlagsRefElement reference);

    /**
     * Resolves the connection-flags reference.
     * @param reference such reference.
     * @return the referenced property.
     */
    @Nullable
    public StatementFlagsElement resolveReference(
        final StatementFlagsRefElement reference);

    /**
     * Resolves the statement-flags reference.
     * @param reference such reference.
     * @return the referenced property.
     */
    @Nullable
    public ResultSetFlagsElement resolveReference(
        final ResultSetFlagsRefElement reference);
}

