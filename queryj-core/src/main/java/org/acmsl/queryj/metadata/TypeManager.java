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
 * Filename: TypeManager.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base class for entities able to deal with type conversions
 *              between Java and JDBC.
 *
 * Date: 2014/02/24
 * Time: 06:55
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Base class for entities able to deal with type conversions between Java and JDBC.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/02/24 06:55
 */
public interface TypeManager
{
    /**
     * Retrieves the class of given type.
     * @param type the type.
     * @return the type.
     */
    @NotNull
    public Class<?> getClass(@NotNull final String type);

    /**
     * Checks whether given type is a primitive wrapper.
     * @param type the type.
     * @return {@code true} in such case.
     */
    public boolean isPrimitiveWrapper(@NotNull final Class<?> type);

    /**
     * Retrieves the primitive class for given primitive wrapper.
     * @param type the type.
     * @return the primitive class.
     */
    @Nullable
    public Class<?> toPrimitive(@NotNull final Class<?> type);

    /**
     * Retrieves the class for given JDBC type.
     * @param jdbcType the constant in {@link java.sql.Types}.
     * @return the associated class, or {@code null} if there's no match.
     */
    @Nullable
    public Class<?> getClass(final int jdbcType);

    /**
     * Checks whether given types (from {@link java.sql.Types} constants) are compatible.
     * @param first the first.
     * @param second the other.
     * @return {@code true} if so.
     */
    public boolean areColumnTypesCompatible(final int first, final int second);

    /**
     * Retrieves the reference type for given class.
     * @param classType the class.
     * @return the constant in {@link java.sql.Types}.
     */
    int getSqlType(@NotNull final Class<?> classType);

    /**
     * Retrieves the reference type for given type.
     * @param type the type.
     * @return the constant in {@link java.sql.Types}.
     */
    int getSqlType(@NotNull final String type);

    /**
     * Retrieves the column types compatible with given one.
     * @param type the type.
     * @return the list of compatible (or convertible in a way) types.
     */
    @NotNull
    List<Integer> getCompatibleTypesFor(final int type);
}
