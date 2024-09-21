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
 * Filename: AbstractParameterElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <parameter> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing project-specific classes.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Models elements in <i>custom-sql</i> models, which
 * satisfy the following DTD extract (to describe the model even in
 * non-xml implementations):
 *  &lt;!ELEMENT <i>element</i> EMPTY&gt;
 *  &lt;!ATTLIST <i>element</i>
 *    id ID #REQUIRED
 *    column_name CDATA #IMPLIED
 *    index CDATA #IMPLIED
 *    name CDATA #IMPLIED
 *    type CDATA #REQUIRED&gt;
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractParameterElement<T>
    extends  AbstractIdElement<T>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -1378141777332493923L;

    /**
     * The <i>index</i> attribute.
     */
    private int m__iIndex;

    /**
     * The <i>type</i> attribute.
     */
    private T m__strType;

    /**
     * Creates an AbstractParameterElement with given information.
     * @param id the <i>id</i> attribute.
     * @param index the <i>index</i> attribute.
     * @param type the <i>type</i> attribute.
     */
    protected AbstractParameterElement(
        @NotNull final T id,
        final int index,
        @NotNull final T type)
    {
        super(id);
        immutableSetIndex(index);
        immutableSetType(type);
    }

    /**
     * Specifies the <i>index</i> attribute.
     * @param index such value.
     */
    protected final void immutableSetIndex(final int index)
    {
        m__iIndex = index;
    }

    /**
     * Specifies the <i>index</i> attribute.
     * @param index such value.
     */
    @SuppressWarnings("unused")
    protected void setIndex(final int index)
    {
        immutableSetIndex(index);
    }

    /**
     * Retrieves the <i>index</i> attribute.
     * @return such value.
     */
    public int getIndex()
    {
        return m__iIndex;
    }

    /**
     * Specifies the <i>type</i> attribute.
     * @param type such value.
     */
    protected final void immutableSetType(@NotNull final T type)
    {
        m__strType = type;
    }

    /**
     * Specifies the <i>type</i> attribute.
     * @param type such value.
     */
    @SuppressWarnings("unused")
    protected void setType(@NotNull final T type)
    {
        immutableSetType(type);
    }

    /**
     * Retrieves the <i>type</i> attribute.
     * @return such value.
     */
    @NotNull
    public T getType()
    {
        return m__strType;
    }

    /**
     * Provides a text information about this instance.
     * @return such information.
     */
    @Override
    @NotNull
    public String toString()
    {
        return
            toString(
                getId(),
                getIndex(),
                getType());
    }

    /**
     * Provides a text information about this instance.
     * @param id the <i>id</i> attribute.
     * @param index the <i>index</i> attribute.
     * @param type the <i>type</i> attribute.
     * @return such information.
     */
    @NotNull
    protected String toString(
        final T id,
        final int index,
        final T type)
    {
        return
              getClass().getName()
            + "[" + "id=" + id + "]"
            + "[" + "index=" + index + "]"
            + "[" + "type=" + type + "]";
    }


    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    @Override
    public int hashCode()
    {
        return
            new org.apache.commons.lang.builder.HashCodeBuilder(-1682907425, 860212091)
                .appendSuper(super.hashCode())
                .append(getIndex())
                .append(getType())
                .toHashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(@Nullable final Object object)
    {
        boolean result = false;

        if  (object instanceof AbstractParameterElement)
        {
            @NotNull final AbstractParameterElement t_OtherInstance = (AbstractParameterElement) object;

            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .appendSuper(super.equals(t_OtherInstance))
                    .append(
                        getIndex(),
                        t_OtherInstance.getIndex())
                    .append(
                        getType(),
                        t_OtherInstance.getType())
                .isEquals();
        }

        return result;
    }

    /**
     * Compares given object with this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * object prevents it from being compared to this Object.
     */
    protected int compare(@Nullable final Parameter<T, ?> object)
    {
        int result = 0;

        if (object != null)
        {
            result =
                new org.apache.commons.lang.builder.CompareToBuilder()
                    .append(getIndex(), object.getIndex())
                    .append(getType(), object.getType())
                    .toComparison();
        }

        return result;
    }
}
