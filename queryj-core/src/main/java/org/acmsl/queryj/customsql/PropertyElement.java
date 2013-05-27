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
 * Filename: PropertyElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <property> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/**
 * Models &lt;property&gt; elements in <i>custom-sql</i> models, which
 * satisfy the following DTD extract (to describe the model even in
 * non-xml implementations):
 *   <!ELEMENT property EMPTY>
 *  <!ATTLIST property
 *    id ID #REQUIRED
 *    column_name CDATA #IMPLIED
 *    index CDATA #IMPLIED
 *    name CDATA #IMPLIED
 *    type CDATA #REQUIRED>
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class PropertyElement
    extends  AbstractParameterElement
    implements  Property
{
    private static final long serialVersionUID = -6387934586093006533L;

    /**
     * The column name.
     */
    private String m__strColumnName;

    /**
     * Whether the property allows nulls or not.
     */
    private boolean m__bNullable;

    /**
     * Creates a PropertyElement with given information.
     * @param id the <i>id</i> attribute.
     * @param index the <i>index</i> attribute.
     * @param type the <i>type</i> attribute.
     * @param nullable the <i>nullable</i> attribute.
     */
    public PropertyElement(
        @NotNull final String id,
        @NotNull final String columnName,
        final int index,
        @NotNull final String type,
        final boolean nullable)
    {
        super(id, index, type);

        immutableSetColumnName(columnName);
        immutableSetNullable(nullable);
    }

    /**
     * Specifies the column name.
     * @param name such name.
     */
    protected final void immutableSetColumnName(@NotNull final String name)
    {
        m__strColumnName = name;
    }

    /**
     * Specifies the column name.
     * @param name such name.
     */
    @SuppressWarnings("unused")
    protected void setColumnName(@NotNull final String name)
    {
        immutableSetColumnName(name);
    }

    /**
     * Retrieves the column name.
     * @return such name.
     */
    @Override
    @NotNull
    public String getColumnName()
    {
        return m__strColumnName;
    }

    /**
     * Specifies whether the property is nullable or not.
     * @param flag such condition.
     */
    protected final void immutableSetNullable(final boolean flag)
    {
        m__bNullable = flag;
    }
    
    /**
     * Specifies whether the property is nullable or not.
     * @param flag such condition.
     */
    @SuppressWarnings("unused")
    protected void setNullable(final boolean flag)
    {
        immutableSetNullable(flag);
    }
    

    /**
     * Retrieves ehether the property is nullable or not.
     * @return such condition.
     */
    public boolean isNullable()
    {
        return m__bNullable;
    }

    /**
     * Retrieves the validation value.
     *
     * @return such value.
     */
    public String getValidationValue()
    {
        return null;
    }

    /**
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
    @NotNull
    public String toString()
    {
        StringBuilder result = new StringBuilder(getClass().getName());
        result.append("{id:\"");
        result.append(getId());
        result.append("\"; columnName:\"");
        result.append(getColumnName());
        result.append("\"; type:\"");
        result.append(getType());
        result.append("\"; index:");
        result.append(getIndex());
        result.append("; validationValue:\"");
        result.append(getValidationValue());
        result.append("}");

        return result.toString();
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    public int hashCode()
    {
        return
            new org.apache.commons.lang.builder.HashCodeBuilder(-1682907407, 1052139971)
                .appendSuper(super.hashCode())
                .append(isNullable())
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

        if  (object instanceof Property)
        {
            @NotNull final Property t_OtherInstance = (Property) object;

            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .appendSuper(super.equals(t_OtherInstance))
                    .append(
                        isNullable(),
                        t_OtherInstance.isNullable())
                .isEquals();
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
    @Override
    public int compareTo(@Nullable final Property object)
        throws  ClassCastException
    {
        int result = 1;

        if  (object != null)
        {
            result = compareThem(this, object);
        }

        return result;
    }

    /**
     * Compares given {@link Property properties}.
     * @param first the first.
     * @param second the second.
     * @return the outcome of comparing <code>first.getIndex()</code> vs
     * <code>second.getIndex()</code>
     */
    protected int compareThem(@NotNull final Property first, @NotNull final Property second)
    {
        return first.getIndex() - second.getIndex();
    }
}
