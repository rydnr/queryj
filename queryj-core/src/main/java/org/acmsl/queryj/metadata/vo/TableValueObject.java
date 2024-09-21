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

 *****************************************************************************
 *
 * Filename: TableValueObject.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Value-object implementation of Table interface.
 *
 */
package org.acmsl.queryj.metadata.vo;

/*
 * Importing some checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

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
@ThreadSafe
public final class TableValueObject
    extends AbstractTable<String, Attribute<String>, List<Attribute<String>>>
{
    /**
     * The serial version id.
     */
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
     * @param staticAttribute the attribute used to label static contents.
     * @param voDecorated whether the table is decorated.
     * @param isRelationship whether the table identifies a relationship.
     */
    public TableValueObject(
        @NotNull final String name,
        @Nullable final String comment,
        @NotNull final List<Attribute<String>> primaryKey,
        @NotNull final List<Attribute<String>> attributes,
        @NotNull final List<ForeignKey<String>> foreignKeys,
        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> parentTable,
        @Nullable final Attribute<String> staticAttribute,
        final boolean voDecorated,
        final boolean isRelationship)
    {
        super(
            name,
            comment,
            primaryKey,
            attributes,
            foreignKeys,
            parentTable,
            staticAttribute,
            voDecorated,
            isRelationship);
    }
}

