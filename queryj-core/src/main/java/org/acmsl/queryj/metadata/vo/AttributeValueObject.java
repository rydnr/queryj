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
 * Filename: AttributeValueObject.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Value-object implementation of Attribute interface.
 *
 */
package org.acmsl.queryj.metadata.vo;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Value-object implementation of <code>Attribute</code> interface.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public final class AttributeValueObject
    extends AbstractAttribute<String>
{
    private static final long serialVersionUID = 8975433666493838327L;

    /**
     * Creates an <code>AttributeValueObject</code> with the following
     * information.
     * @param name the name.
     * @param typeId the type id.
     * @param type the type.
     * @param tableName the table name.
     * @param comment the column comment.
     * @param ordinalPosition the ordinal position.
     * @param length the maximum data length that can be stored in this attribute.
     * @param precision the precision if the data is numeric.
     * @param keyword the keyword used to retrieve the value, if any.
     * @param retrievalQuery the query used to retrieve the value, if any.
     * @param sequence the sequence (for Oracle engines).
     * @param allowsNull whether the attribute allows null values or not.
     * @param value the optional value.
     * @param readOnly if the attribute is read-only.
     * @param isBool if the attribute is boolean.
     * @param booleanTrue the value indicating a <code>true</code> value.
     * @param booleanFalse the value indicating a <code>false</code> value.
     * @param booleanNull the value indicating a <code>null</code> value.
     */
    public AttributeValueObject(
        @NotNull final String name,
        final int typeId,
        @NotNull final String type,
        @NotNull final String tableName,
        @Nullable final String comment,
        final int ordinalPosition,
        final int length,
        final int precision,
        @Nullable final String keyword,
        @Nullable final String retrievalQuery,
        @Nullable final String sequence,
        final boolean allowsNull,
        @Nullable final String value,
        final boolean readOnly,
        final boolean isBool,
        @Nullable final String booleanTrue,
        @Nullable final String booleanFalse,
        @Nullable final String booleanNull)
    {
        super(
            name,
            typeId,
            type,
            tableName,
            comment,
            ordinalPosition,
            length,
            precision,
            keyword,
            retrievalQuery,
            sequence,
            allowsNull,
            value,
            readOnly,
            isBool,
            booleanTrue,
            booleanFalse,
            booleanNull);
    }

    /**
     * Retrieves whether it's managed externally.
     * @return such information.
     */
    @Override
    public boolean isExternallyManaged()
    {
        boolean result = false;

        @Nullable final String keyword = getKeyword();
        @Nullable final String retrievalQuery = getRetrievalQuery();

        if (   (keyword != null)
            && (!keyword.trim().equals(""))
            && (retrievalQuery != null)
            && (!retrievalQuery.trim().equals("")))
        {
            result = true;
        }

        return result;
    }
}

