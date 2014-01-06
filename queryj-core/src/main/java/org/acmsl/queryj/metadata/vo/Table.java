/*
                        QueryJ

    Copyright (C) 2002-2014  Jose San Leandro Armendariz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 *****************************************************************************
 *
 * Filename: Table.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents 'table' entities in the metadata model.
 *
 */
package org.acmsl.queryj.metadata.vo;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.Serializable;
import java.util.List;

/**
 * Represents <i>table</i> entities in the metadata model.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface Table<V, A extends Attribute<V>, L extends List<A>>
    extends Serializable,
            Comparable<Table<V, A, L>>
{
    /**
     * Retrieves the table name.
     * @return such name.
     */
    @NotNull
    V getName();

    /**
     * Retrieves the table comment.
     * @return such comment.
     */
    @Nullable
    V getComment();

    /**
     * Retrieves the primary key attributes.
     * @return such list.
     */
    @NotNull
    L getPrimaryKey();

    /**
     * Retrieves the attributes.
     * @return such list.
     */
    @NotNull
    L getAttributes();

    /**
     * Retrieves the {@link org.acmsl.queryj.metadata.vo.ForeignKey foreign keys}.
     * @return such list.
     */
    @NotNull
    List<ForeignKey<V>> getForeignKeys();

    /**
     * Retrieves the parent table.
     * @return such table.
     */
    @Nullable
    Table<V, A, L> getParentTable();

    /**
     * Retrieves whether the table contains static values or not.
     * @return <tt>true</tt> in such case.
     */
    boolean isStatic();

    /**
     * Retrieves whether the value object for the table is
     * decorated or not.
     * @return such information.
     */
    boolean isVoDecorated();
}
