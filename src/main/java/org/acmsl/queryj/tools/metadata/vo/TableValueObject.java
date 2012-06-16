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

 *****************************************************************************
 *
 * Filename: TableValueObject.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Value-object implementation of Table interface.
 *
 */
package org.acmsl.queryj.tools.metadata.vo;

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
 * Value-object implementation of <code>Table</code> interface.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public final class TableValueObject
    extends AbstractTable
    implements Table
{

    private static final long serialVersionUID = -1699360842530765122L;

    /**
     * Creates an <code>TableValueObject</code> with the following
     * information.
     * @param name the name.
     * @param comment the table comment.
     * @param primaryKey the primary key.
     * @param attributes the columns.
     * @param foreignKeys the foreign keys.
     * @param parentTable the parent table.
     * @param isStatic whether the table is static.
     * @param voDecorated whether the table is decorated.
     */
    public TableValueObject(
        @NotNull final String name,
        @Nullable final String comment,
        @NotNull final List<Attribute> primaryKey,
        @NotNull final List<Attribute> attributes,
        @NotNull final List<ForeignKey> foreignKeys,
        @Nullable final Table parentTable,
        final boolean isStatic,
        final boolean voDecorated)
    {
        super(
            name,
            comment,
            primaryKey,
            attributes,
            foreignKeys,
            parentTable,
            isStatic,
            voDecorated);
    }
}

