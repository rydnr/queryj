//;-*- mode: java -*-
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
 * Filename: AbstractField.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models the definition of table fields declared by the user.
 *
 */
package org.acmsl.queryj.tools.metadata.vo;

/*
 * Importing some JDK classes.
 */
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Models the definition of table fields declared by the user.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractField
    extends  AbstractAttribute
    implements  Field
{
    /**
     * The field pk nature.
     */
    private boolean m__bPk = false;

    /**
     * The keyword.
     */
    private String m__strKeyword;

    /**
     * The query to retrieve the keyword value.
     */
    private String m__strRetrievalQuery;

    /**
     * The field fk collection.
     */
    private Collection m__cFieldFks;

    /**
     * Creates an {@link AbstractField} with given information.
     * @param name the field name.
     * @param type the field type.
     * @param tableName the name of the table.
     * @param pk whether it participates in the table's pk.
          */
    public AbstractField(
        final String name,
        final int type,
        final String nativeType,
        final String fieldType,
        final String tableName,
        final String comment,
        final boolean managedExternally,
        final boolean allowsNull,
        final String value,
        final boolean readOnly,
        final boolean isBool,
        final String booleanTrue,
        final String booleanFalse,
        final String booleanNull,
        final boolean pk)
    {
        //super(name, type, tableName, false);
        super(
            name,
            type,
            nativeType,
            fieldType,
            tableName,
            comment,
            managedExternally,
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
     * Creates an <code>AbstractField</code> with given information.
     * @param name the field name.
     * @param type the field type.
     * @param pk whether it participates in the table's pk.
     * @param tableName the name of the table.
     * @param keyword the keyword.
     * @param retrievalQuery the query to retrieval field's values.
     */
    public AbstractField(
        final String name,
        final int type,
        final String nativeType,
        final String fieldType,
        final String tableName,
        final String comment,
        final boolean managedExternally,
        final boolean allowsNull,
        final String value,
        final boolean readOnly,
        final boolean isBool,
        final String booleanTrue,
        final String booleanFalse,
        final String booleanNull,
        final boolean pk,
        final String keyword,
        final String retrievalQuery)
    {
        this(
            name,
            type,
            nativeType,
            fieldType,
            tableName,
            comment,
            managedExternally,
            allowsNull,
            value,
            readOnly,
            isBool,
            booleanTrue,
            booleanFalse,
            booleanNull,
            pk);

        immutableSetKeyword(keyword);
        immutableSetRetrievalQuery(retrievalQuery);
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
     * Specifies the field's keyword.
     * @param keyword the keyword.
     */
    protected final void immutableSetKeyword(final String keyword)
    {
        m__strKeyword = keyword;
    }

    /**
     * Specifies the field's keyword.
     * @param keyword the keyword.
     */
    protected void setKeyword(final String keyword)
    {
        immutableSetKeyword(keyword);
    }

    /**
     * Retrieves the field's keyword.
     * @return such keyword.
     */
    public String getKeyword()
    {
        return m__strKeyword;
    }

    /**
     * Specifies the query to retrieve the field value.
     * @param query such query.
     */
    protected final void immutableSetRetrievalQuery(final String query)
    {
        m__strRetrievalQuery = query;
    }

    /**
     * Specifies the query to retrieve the field value.
     * @param query such query.
     */
    protected void setRetrievalQuery(final String query)
    {
        immutableSetRetrievalQuery(query);
    }

    /**
     * Retrieves the query to retrieve the field value.
     * @return such information.
     */
    public String getRetrievalQuery()
    {
        return m__strRetrievalQuery;
    }

    /**
     * Specifies the field fk collection.
     * @param fieldFks the collection
     */
    private void immutableSetFieldFks(final Collection fieldFks)
    {
        m__cFieldFks = fieldFks;
    }

    /**
     * Specifies the field fk collection.
     * @param fieldFks the collection
     */
    protected void setFieldFks(final Collection fieldFks)
    {
        immutableSetFieldFks(fieldFks);
    }

    /**
     * Retrieves the field fk collection.
     * @return such collection.
     */
    public Collection getFieldFks()
    {
        return m__cFieldFks;
    }

    /**
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
    public String toString()
    {
        return
            new org.apache.commons.lang.builder.ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("type", getType())
                .append("pk", isPk())
                .append("keyword", getKeyword())
                .append("retrievalQuery", getRetrievalQuery())
                .append("fieldFks", getFieldFks())
                .toString();
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
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
    public boolean equals(final Object object)
    {
        boolean result = false;

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
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     */
    public int compareTo(final Object object)
        throws  ClassCastException
    {
        int result = 1;

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
