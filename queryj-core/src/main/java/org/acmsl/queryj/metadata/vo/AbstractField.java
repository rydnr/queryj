//;-*- mode: java -*-
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
 * Filename: AbstractField.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models the definition of table fields declared by the user.
 *
 */
package org.acmsl.queryj.metadata.vo;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.tools.ant.AntFieldElement;
import org.acmsl.queryj.tools.ant.AntFieldFkElement;

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
 * Models the definition of table fields declared by the user.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractField
    extends  AbstractAttribute<String>
    implements  Field
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 7474193219072803177L;

    /**
     * The field pk nature.
     */
    private boolean m__bPk = false;

    /**
     * The field fk collection.
     */
    private List<AntFieldFkElement> m__lFieldFks;

    /**
     * Creates an {@link AbstractField} with given information.
     * @param name the field name.
     * @param typeId the type id.
     * @param type the type.
     * @param tableName the name of the table.
     * @param ordinalPosition the ordinal position.
     * @param length the length.
     * @param precision the precision.
     * @param comment the field comment.
     * @param keyword the keyword used to retrieve the value, if any.
     * @param retrievalQuery the query used to retrieve the value, if any.
     * @param sequence the sequence (for Oracle engines).
     * @param allowsNull whether it allows null or not.
     * @param value the concrete value, if any.
     * @param readOnly whether it's read-only.
     * @param isBool whether it represents boolean values or not.
     * @param booleanTrue the value representing <code>true</code>.
     * @param booleanFalse the value representing <code>false</code>.
     * @param booleanNull the value representing <code>null</code>.
     * @param pk whether it participates in the table's pk.
     */
    public AbstractField(
        @NotNull final String name,
        final int typeId,
        @NotNull final String type,
        @NotNull final String tableName,
        @NotNull final String comment,
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
        @Nullable final String booleanNull,
        final boolean pk)
    {
        //super(name, type, tableName, false);
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

        immutableSetPk(pk);
    }

    /**
     * Specifies if the field is part of the primary key.
     * @param pk such information.
     */
    protected final void immutableSetPk(final boolean pk)
    {
        m__bPk = pk;
    }

    /**
     * Specifies if the field is a primary key.
     * @param pk such information.
     */
    protected void setPk(final boolean pk)
    {
        immutableSetPk(pk);
    }

    /**
     * Retrieves if the field is a primary key.
     * @return such information.
     */
    public boolean isPk()
    {
        return m__bPk;
    }

    /**
     * Specifies the field fk collection.
     * @param fieldFks the collection
     */
    private void immutableSetFieldFks(@NotNull final List<AntFieldFkElement> fieldFks)
    {
        m__lFieldFks = fieldFks;
    }

    /**
     * Specifies the field fk collection.
     * @param fieldFks the collection
     */
    protected void setFieldFks(@NotNull final List<AntFieldFkElement> fieldFks)
    {
        immutableSetFieldFks(fieldFks);
    }

    /**
     * Retrieves the field fk collection.
     * @return such collection.
     */
    @Nullable
    @Override
    public List<AntFieldFkElement> getFieldFks()
    {
        return m__lFieldFks;
    }

    /**
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
    @NotNull
    @Override
    public String toString()
    {
        return
            new org.apache.commons.lang.builder.ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("type", getType())
                .append("pk", isPk())
                .append(AntFieldElement.KEYWORD_LITERAL, getKeyword())
                .append("retrievalQuery", getRetrievalQuery())
                .append("fieldFks", getFieldFks())
                .toString();
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    @Override
    public int hashCode()
    {
        return
            new org.apache.commons.lang.builder.HashCodeBuilder(-2052006159, 737950259)
                .appendSuper(super.hashCode())
                .append(getType())
                .append(isPk())
                .append(getKeyword())
                .append(getRetrievalQuery())
                .append(getFieldFks())
                .toHashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    @Override
    public boolean equals(@Nullable final Object object)
    {
        final boolean result;

        if  (object instanceof AbstractField)
        {
            @NotNull final Field t_OtherInstance = (Field) object;

            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .appendSuper(super.equals(t_OtherInstance))
                    .append(
                        isPk(),
                        t_OtherInstance.isPk())
                    .append(
                        getKeyword(),
                        t_OtherInstance.getKeyword())
                    .append(
                        getRetrievalQuery(),
                        t_OtherInstance.getRetrievalQuery())
                    .append(
                        getFieldFks(),
                        t_OtherInstance.getFieldFks())
                .isEquals();
        }
        else
        {
            result = super.equals(object);
        }

        return result;
    }

    /**
     * Compares given object with this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * object prevents it from being compared to this Object.
     */
    @Override
    public int compareTo(@Nullable final Attribute<String> object)
        throws  ClassCastException
    {
        final int result;

        if  (object instanceof Field)
        {
            @NotNull final Field t_OtherInstance = (Field) object;

            result =
                new org.apache.commons.lang.builder.CompareToBuilder()
                .append(
                    isPk(),
                    t_OtherInstance.isPk())
                .append(
                    getKeyword(),
                    t_OtherInstance.getKeyword())
                .append(
                    getRetrievalQuery(),
                    t_OtherInstance.getRetrievalQuery())
                .append(
                    getFieldFks(),
                    t_OtherInstance.getFieldFks())
                .toComparison();
        }
        else
        {
            result = super.compareTo(object);
        }

        return result;
    }
}
