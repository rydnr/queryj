/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: AbstractAttribute.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Abstract logic-less implementation of Attribute interface.
 *
 */
package org.acmsl.queryj.metadata.vo;

/*
 * Importing some Apache Commons-Lang classes.
 */
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Abstract logic-less implementation of {@link Attribute} interface.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractAttribute
    implements Attribute
{
    /**
     * The name.
     */
    private String m__strName;

    /**
     * The type.
     */
    private int m__iTypeId;

    /**
     * The type.
     */
    private String m__strType;

    /**
     * The table name.
     */
    private String m__strTableName;

    /**
     * The comment.
     */
    private String m__strComment;

    /**
     * The keyword user to retrieve the value, if any.
     */
    private String m__strKeyword;

    /**
     * The query user to retrieve the value, if any.
     */
    private String m__strRetrievalQuery;

    /**
     * Whether the attribute allows null values or not.
     */
    private boolean m__bNullable = false;

    /**
     * The optional value.
     */
    private String m__strValue;

    /**
     * Whether the attribute is marked as read-only.
     */
    private boolean m__bReadOnly = false;

    /**
     * Whether the attribute is marked as boolean.
     */
    private boolean m__bBoolean = false;

    /**
     * The symbol for <code>true</code> values in boolean attributes.
     */
    private String m__strBooleanTrue;

    /**
     * The symbol for <code>false</code> values in boolean attributes.
     */
    private String m__strBooleanFalse;

    /**
     * The symbol for <code>null</code> values in boolean attributes.
     */
    private String m__strBooleanNull;

    /**
     * The ordinal position.
     */
    private int m__iOrdinalPosition;

    /**
     * The column length.
     */
    private int m__iColumnLength;

    /**
     * The column precision.
     */
    private int m__iColumnPrecision;

    /**
     * The stack trace to know who created me.
     */
    private final StackTraceElement[] m__aStackTrace =
        new RuntimeException("fake").getStackTrace();

    /**
     * Creates an <code>AbstractAttribute</code> with minimal information,
     * allowing lazy-loading mechanisms for non-essential information.
     * @param tableName the table name.
     * @param name the name.
     */
    protected AbstractAttribute(
        final String tableName, final String name)
    {
        immutableSetTableName(tableName);
        immutableSetName(name);
    }

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
    protected AbstractAttribute(
        @Nullable final String name,
        final int typeId,
        @Nullable final String type,
        @Nullable final String tableName,
        @Nullable final String comment,
        final int ordinalPosition,
        final int length,
        final int precision,
        final boolean allowsNull,
        @Nullable final String value)
    {
        this(tableName, name);
        immutableSetTypeId(typeId);
        if (type != null)
        {
            immutableSetType(type);
        }
        immutableSetComment(comment);
        immutableSetOrdinalPosition(ordinalPosition);
        immutableSetLength(length);
        immutableSetPrecision(precision);
        immutableIsNullable(allowsNull);
        immutableSetValue(value);
    }

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
     * @param keyword the keyword used to retrieve the value, if any.
     * @param retrievalQuery the query used to retrieve the value, if any.
     * @param allowsNull whether the attribute allows null values or not.
     * @param value the optional value.
     * @param readOnly whether the attribute is marked as read-only.
     * @param isBool whether the attribute is marked as boolean.
     * @param booleanTrue the symbol for <code>true</code> values in boolean attributes.
     * @param booleanFalse the symbol for <code>false</code> values in boolean attributes.
     * @param booleanNull the symbol for <code>null</code> values in boolean attributes.
     */
    protected AbstractAttribute(
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
        final boolean allowsNull,
        @Nullable final String value,
        final boolean readOnly,
        final boolean isBool,
        @Nullable final String booleanTrue,
        @Nullable final String booleanFalse,
        @Nullable final String booleanNull)
    {
        this(
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

        if (keyword != null)
        {
            immutableSetKeyword(keyword);
        }
        if (retrievalQuery != null)
        {
            immutableSetRetrievalQuery(retrievalQuery);
        }
        immutableSetReadOnly(readOnly);
        immutableSetBoolean(isBool);
        immutableSetBooleanTrue(booleanTrue);
        immutableSetBooleanFalse(booleanFalse);
        immutableSetBooleanNull(booleanNull);
    }

    /**
     * Specifies the name.
     * @param name such name.
     */
    protected final void immutableSetName(@NotNull final String name)
    {
        m__strName = name;
    }

    /**
     * Specifies the name.
     * @param name such name.
     */
    protected void setName(@NotNull final String name)
    {
        immutableSetName(name);
    }

    /**
     * Retrieves the attribute name.
     * @return such name.
     */
    @NotNull
    public String getName()
    {
        return m__strName;
    }

    /**
     * Specifies the type.
     * @param type such information.
     */
    protected final void immutableSetTypeId(final int type)
    {
        m__iTypeId = type;
    }

    /**
     * Specifies the type.
     * @param type such information.
     */
    @SuppressWarnings("unused")
    protected void setTypeId(final int type)
    {
        immutableSetTypeId(type);
    }

    /**
     * Retrieves the attribute type.
     * @return its type.
     */
    public int getTypeId()
    {
        return m__iTypeId;
    }

    /**
     * Specifies the type.
     * @param type such information.
     */
    protected final void immutableSetType(@NotNull final String type)
    {
        m__strType = type;
    }

    /**
     * Specifies the native type.
     * @param type such information.
     */
    @SuppressWarnings("unused")
    protected void setType(@NotNull final String type)
    {
        immutableSetType(type);
    }

    /**
     * Retrieves the type.
     * @return such information.
     */
    @NotNull
    public String getType()
    {
        return m__strType;
    }

    /**
     * Specifies the table name.
     * @param tableName such information.
     */
    protected final void immutableSetTableName(@NotNull final String tableName)
    {
        m__strTableName = tableName;
    }

    /**
     * Specifies the table name.
     * @param tableName such information.
     */
    protected void setTableName(@NotNull final String tableName)
    {
        immutableSetTableName(tableName);
    }

    /**
     * Retrieves the table name.
     * @return such information.
     */
    @NotNull
    public String getTableName()
    {
        return m__strTableName;
    }

    /**
     * Specifies the comment.
     * @param comment such comment.
     */
    protected final void immutableSetComment(@Nullable final String comment)
    {
        m__strComment = comment;
    }

    /**
     * Specifies the comment.
     * @param comment such comment.
     */
    @SuppressWarnings("unused")
    protected void setComment(@Nullable final String comment)
    {
        immutableSetComment(comment);
    }

    /**
     * Retrieves the attribute comment.
     * @return such comment.
     */
    @Nullable
    public String getComment()
    {
        return m__strComment;
    }

    /**
     * Specifies the keyword used to retrieve the value, if any.
     * @param keyword such information.
     */
    protected final void immutableSetKeyword(@NotNull final String keyword)
    {
        m__strKeyword = keyword;
    }

    /**
     * Specifies the keyword used to retrieve the value, if any.
     * @param keyword such information.
     */
    @SuppressWarnings("unused")
    protected void setKeyword(@NotNull final String keyword)
    {
        immutableSetKeyword(keyword);
    }

    /**
     * Retrieves the keyword used to retrieve the value, if any.
     * @return such information.
     */
    @Nullable
    public String getKeyword()
    {
        return m__strKeyword;
    }

    /**
     * Specifies the query used to retrieve the value, if any.
     * @param query such information.
     */
    protected final void immutableSetRetrievalQuery(@NotNull final String query)
    {
        m__strRetrievalQuery = query;
    }

    /**
     * Specifies the query used to retrieve the value, if any.
     * @param query such information.
     */
    @SuppressWarnings("unused")
    protected void setRetrievalQuery(@NotNull final String query)
    {
        immutableSetRetrievalQuery(query);
    }

    /**
     * Retrieves the query used to retrieve the value, if any.
     * @return such information.
     */
    @Nullable
    public String getRetrievalQuery()
    {
        return m__strRetrievalQuery;
    }

    /**
     * Retrieves whether it's managed externally.
     * @return such information.
     */
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

    /**
     * Specifies whether the attribute allows null values or not.
     * @param allowsNull such information.
     */
    protected final void immutableIsNullable(final boolean allowsNull)
    {
        m__bNullable = allowsNull;
    }

    /**
     * Specifies whether the attribute allows null values or not.
     * @param allowsNull such information.
     */
    @SuppressWarnings("unused")
    protected void setIsNullable(final boolean allowsNull)
    {
        immutableIsNullable(allowsNull);
    }

    /**
     * Retrieves whether it allows null values or not.
     * @return such information.
     */
    public boolean isNullable()
    {
        return m__bNullable;
    }

    /**
     * Alias for {@link #isNullable()}
     * @return whether it allows null values or not.
     */
    @SuppressWarnings("unused")
    public boolean getAllowsNull()
    {
        return isNullable();
    }

    /**
     * Specifies the optional attribute's value, meaning
     * it doesn't just describe the metadata, but also
     * contains data.
     * @param value such information.
     */
    protected final void immutableSetValue(@Nullable final String value)
    {
        m__strValue = value;
    }

    /**
     * Specifies the optional attribute's value, meaning
     * it doesn't just describe the metadata, but also
     * contains data.
     * @param value such information.
     */
    @SuppressWarnings("unused")
    protected void setValue(@Nullable final String value)
    {
        immutableSetValue(value);
    }

    /**
     * Retrieves the optional attribute's value, meaning
     * it doesn't just describe the metadata, but also
     * contains data.
     * @return such information.
     */
    @Nullable
    public String getValue()
    {
        return m__strValue;
    }

    /**
     * Specifies whether the attribute is marked as read-only.
     * @param flag such condition.
     */
    protected final void immutableSetReadOnly(final boolean flag)
    {
        m__bReadOnly = flag;
    }

    /**
     * Specifies whether the attribute is marked as read-only.
     * @param flag such condition.
     */
    @SuppressWarnings("unused")
    protected void setReadOnly(final boolean flag)
    {
        immutableSetReadOnly(flag);
    }

    /**
     * Retrieves whether the attribute is marked as read-only.
     * @return such condition.
     */
    public boolean isReadOnly()
    {
        return m__bReadOnly;
    }

    /**
     * Specifies whether the attribute is marked as boolean.
     * @param flag such condition.
     */
    protected final void immutableSetBoolean(final boolean flag)
    {
        m__bBoolean = flag;
    }

    /**
     * Specifies whether the attribute is marked as boolean.
     * @param flag such condition.
     */
    @SuppressWarnings("unused")
    protected void setBoolean(final boolean flag)
    {
        immutableSetBoolean(flag);
    }

    /**
     * Retrieves whether the attribute is marked as boolean.
     * @return such condition.
     */
    public boolean isBoolean()
    {
        return m__bBoolean;
    }

    /**
     * Specifies the symbol for <code>true</code> values.
     * @param value such information.
     */
    protected final void immutableSetBooleanTrue(@Nullable final String value)
    {
        m__strBooleanTrue = value;
    }

    /**
     * Specifies the symbol for <code>true</code> values.
     * @param value such information.
     */
    @SuppressWarnings("unused")
    protected void setBooleanTrue(@Nullable final String value)
    {
        immutableSetBooleanTrue(value);
    }

    /**
     * Retrieves the symbol for <code>true</code> values.
     * @return such information.
     */
    @Nullable
    public String getBooleanTrue()
    {
        return m__strBooleanTrue;
    }

    /**
     * Specifies the symbol for <code>false</code> values.
     * @param value such information.
     */
    protected final void immutableSetBooleanFalse(@Nullable final String value)
    {
        m__strBooleanFalse = value;
    }

    /**
     * Specifies the symbol for <code>false</code> values.
     * @param value such information.
     */
    @SuppressWarnings("unused")
    protected void setBooleanFalse(@Nullable final String value)
    {
        immutableSetBooleanFalse(value);
    }

    /**
     * Retrieves the symbol for <code>false</code> values.
     * @return such information.
     */
    @Nullable
    public String getBooleanFalse()
    {
        return m__strBooleanFalse;
    }

    /**
     * Specifies the symbol for <code>null</code> values.
     * @param value such information.
     */
    protected final void immutableSetBooleanNull(@Nullable final String value)
    {
        m__strBooleanNull = value;
    }

    /**
     * Specifies the symbol for <code>null</code> values.
     * @param value such information.
     */
    @SuppressWarnings("unused")
    protected void setBooleanNull(@Nullable final String value)
    {
        immutableSetBooleanNull(value);
    }

    /**
     * Retrieves the symbol for <code>null</code> values.
     * @return such information.
     */
    @Nullable
    public String getBooleanNull()
    {
        return m__strBooleanNull;
    }

    /**
     * Specifies the ordinal position.
     * @param position the position.
     */
    protected final void immutableSetOrdinalPosition(final int position)
    {
        m__iOrdinalPosition = position;
    }

    /**
     * Specifies the ordinal position.
     * @param position the position.
     */
    @SuppressWarnings("unused")
    protected void setOrdinalPosition(final int position)
    {
        immutableSetOrdinalPosition(position);
    }

    /**
     * Retrieves the ordinal position.
     * @return the position.
     */
    @SuppressWarnings("unused")
    public int getOrdinalPosition()
    {
        return m__iOrdinalPosition;
    }

    /**
     * Specifies the column length.
     * @param length the length.
     */
    protected final void immutableSetLength(final int length)
    {
        m__iColumnLength = length;
    }

    /**
     * Specifies the column length.
     * @param length the length.
     */
    @SuppressWarnings("unused")
    protected void setLength(final int length)
    {
        immutableSetLength(length);
    }

    /**
     * Retrieves the column length.
     * @return such information.
     */
    public int getLength()
    {
        return m__iColumnLength;

    }

    /**
     * Specifies the column precision.
     * @param precision the precision.
     */
    protected final void immutableSetPrecision(final int precision)
    {
        m__iColumnPrecision = precision;
    }

    /**
     * Specifies the column precision.
     * @param precision the precision.
     */
    @SuppressWarnings("unused")
    protected void setPrecision(final int precision)
    {
        immutableSetPrecision(precision);
    }

    /**
     * Retrieves the column precision.
     * @return such information.
     */
    public int getPrecision()
    {
        return m__iColumnPrecision;
    }

    /**
     * Retrieves the stack trace when the attribute was created.
     * @return such information.
     */
    @SuppressWarnings("unused")
    protected StackTraceElement[] getStackTrace()
    {
        return m__aStackTrace;
    }

    /**
     * Prints given stack trace in JSON format.
     * @param stackTrace the stack trace.
     * @return the JSON format.
     */
    @NotNull
    protected String toJSON(@NotNull final StackTraceElement[] stackTrace)
    {
        @NotNull final StringBuilder result = new StringBuilder("[ ");

        boolean firstTime = true;
        for (@NotNull final StackTraceElement entry : stackTrace)
        {
            if (firstTime)
            {
                firstTime = false;
            }
            else
            {
                result.append(", ");
            }

            result.append("\"");
            result.append(entry);
            result.append("\"");
        }

        return result.toString();
    }

    /**
     * Retrieves the attribute name.
     *
     * @return such information.
     */
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + AbstractAttribute.class.getName() + "\""
            + ", \"stackTrace\": " + toJSON(m__aStackTrace)
            + ", \"name\": \"" + m__strName + "\""
            + ", \"typeId\": " + m__iTypeId
            + ", \"type\": \"" + m__strType + "\""
            + ", \"tableName\": \"" + m__strTableName + "\""
            + ", \"comment\": \"" + m__strComment + "\""
            + ", \"keyword\": \"" + m__strKeyword +  "\""
            + ", \"retrievalQuery\": \"" + m__strRetrievalQuery + "\""
            + ", \"nullable\": \"" + m__bNullable + "\""
            + ", \"value\": " + m__strValue
            + ", \"readOnly\": " + m__bReadOnly
            + ", \"boolean\": " + m__bBoolean
            + ", \"booleanTrue\": \"" + m__strBooleanTrue + "\""
            + ", \"booleanFalse\": \"" + m__strBooleanFalse + "\""
            + ", \"booleanNull\":" + m__strBooleanNull + "\""
            + ", \"ordinalPosition\":" + m__iOrdinalPosition
            + ", \"columnLength\": " + m__iColumnLength
            + ", \"columnPrecision\": " + m__iColumnPrecision + " }";
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.m__strName).append(this.m__iTypeId).append(this.m__strType)
            .append(this.m__strTableName).append(this.m__strComment).append(this.m__strKeyword)
            .append(this.m__strRetrievalQuery).append(this.m__bNullable).append(this.m__strValue)
            .append(this.m__bReadOnly).append(this.m__bBoolean).append(this.m__strBooleanTrue)
            .append(this.m__strBooleanFalse).append(this.m__strBooleanNull).append(this.m__iOrdinalPosition)
            .append(this.m__iColumnLength).append(this.m__iColumnPrecision).append(this.m__aStackTrace).toHashCode();
    }

    @Override
    public boolean equals(@Nullable final Object obj)
    {
        boolean result = false;

        if (obj instanceof Attribute)
        {
            result = areEqual(this, (Attribute) obj);
        }

        return result;
    }

    /**
     * Checks whether given {@link Attribute attributes} are equal.
     * @param first the first attribute.
     * @param second the second attribute.
     * @return <code>true</code> in such case.
     */
    protected boolean areEqual(@NotNull final Attribute first, @NotNull final Attribute second)
    {
        return
            new EqualsBuilder()
                .append(first.getName(), second.getName())
                .append(first.getTypeId(), second.getTypeId())
                .append(first.getTableName(), second.getTableName())
                .isEquals();
    }

    /**
     * Compares given object with this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     */
    @Override
    public int compareTo(@Nullable final Attribute object)
        throws  ClassCastException
    {
        int result = 1;

        if (object != null)
        {
            result = compareThem(this, object);
        }

        return result;
    }

    /**
     * Compares both {@link Attribute} instances.
     * @param first the first.
     * @param second the second.
     * @return the outcome of comparing <code>first.getOrdinalPosition()</code> vs
     * <code>second.getOrdinalPosition()</code>
     */
    protected int compareThem(@NotNull final Attribute first, @NotNull final Attribute second)
    {
        return first.getOrdinalPosition() - second.getOrdinalPosition();
    }
}