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
 * Filename: ForeignKeyValueObject.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models foreign keys.
 *
 */
package org.acmsl.queryj.metadata.vo;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JDK classes.
 */
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Models foreign keys.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class ForeignKeyValueObject
    extends AbstractForeignKey<String>
{

    private static final long serialVersionUID = 3292464819930780148L;

    /**
     * Creates a <code>ForeignKeyValueObject</code> with given information.
     * @param sourceTableName the source table name.
     * @param attributes the attributes.
     * @param targetTableName the target table name.
     * @param allowsNull whether the foreign key allows null values.
     */
    public ForeignKeyValueObject(
        @NotNull final String sourceTableName,
        @NotNull final List<Attribute<String>> attributes,
        @NotNull final String targetTableName,
        final boolean allowsNull)
    {
        super(sourceTableName, attributes, targetTableName, allowsNull);
    }
}
