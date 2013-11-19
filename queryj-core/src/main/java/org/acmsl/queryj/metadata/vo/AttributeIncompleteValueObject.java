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
 * Filename: AttributeIncompleteValueObject.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: An incomplete Attribute.
 *
 * Date: 6/6/12
 * Time: 12:05 PM
 *
 */
package org.acmsl.queryj.metadata.vo;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * An incomplete {@link Attribute}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
@ThreadSafe
public class AttributeIncompleteValueObject
    extends AbstractAttribute<String>
{

    private static final long serialVersionUID = -8585509128283241379L;

    /**
     * Creates an <code>AbstractAttribute</code> with the following
     * information.
     * @param name the name.
     * @param typeId the type id.
     * @param type the type.
     * @param tableName the table name.
     * @param comment the comment.
     * @param ordinalPosition the ordinal position.
     * @param length the maximum data length that can be stored in this attribute.
     * @param precision the precision if the data is numeric.
     * @param allowsNull whether the attribute allows null values or not.
     * @param value the optional value.
     */
    public AttributeIncompleteValueObject(
        @NotNull final String name,
        final int typeId,
        @NotNull final String type,
        @NotNull final String tableName,
        @Nullable final String comment,
        final int ordinalPosition,
        final int length,
        final int precision,
        final boolean allowsNull,
        @Nullable final String value)
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
            allowsNull,
            value);
    }

    /**
     * Specifies whether the attribute is marked as boolean.
     * @param flag such condition.
     */
    @Override
    public void setBoolean(final boolean flag)
    {
        super.setBoolean(flag);
    }

    /**
     * Specifies whether the attribute is marked as read-only.
     *
     * @param flag such condition.
     */
    @Override
    public void setReadOnly(final boolean flag)
    {
        super.setReadOnly(flag);
    }

    /**
     * Specifies the symbol for <code>true</code> values.
     *
     * @param value such information.
     */
    @Override
    public void setBooleanTrue(final String value)
    {
        super.setBooleanTrue(value);
    }

    /**
     * Specifies the symbol for <code>false</code> values.
     *
     * @param value such information.
     */
    @Override
    public void setBooleanFalse(final String value)
    {
        super.setBooleanFalse(value);
    }

    /**
     * Specifies the symbol for <code>null</code> values.
     *
     * @param value such information.
     */
    @Override
    public void setBooleanNull(final String value)
    {
        super.setBooleanNull(value);
    }

    /**
     * Specifies the keyword used to retrieve the value.
     * @param keyword such information.
     */
    @Override
    public void setKeyword(@NotNull final String keyword)
    {
        super.setKeyword(keyword);
    }

    /**
     * Specifies the query used to retrieve the value.
     * @param query such information.
     */
    @Override
    public void setRetrievalQuery(@NotNull final String query)
    {
        super.setRetrievalQuery(query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOrdinalPosition(final int position)
    {
        super.setOrdinalPosition(position);
    }
}
