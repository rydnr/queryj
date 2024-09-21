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
 * Filename: AbstractIdElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Common parent to all <b>sql.xml</b> elements with <i>id</i>
 *              attributes.
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Common parent to all <i>custom-sql</i> elements with <i>id</i>
 * attributes.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @param <T> the type.
 */
public abstract class AbstractIdElement<T>
    implements IdentifiableElement<T>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -3906756800505246803L;

    /**
     * The <i>id</i> attribute.
     */
    private T m__strId;

    /**
     * Builds an AbstractIdElement with given <i>id</i>.
     * @param id the id.
     */
    protected AbstractIdElement(@NotNull final T id)
    {
        immutableSetId(id);
    }

    /**
     * Specifies the <i>id</i> value.
     * @param id the id.
     */
    protected final void immutableSetId(@NotNull final T id)
    {
        m__strId = id;
    }

    /**
     * Specifies the <i>id</i> value.
     * @param id the id.
     */
    @SuppressWarnings("unused")
    protected void setId(@NotNull final T id)
    {
        immutableSetId(id);
    }

    /**
     * Retrieves the <i>id</i> value.
     * @return such information.
     */
    @NotNull
    public T getId()
    {
        return m__strId;
    }

    /**
     * Provides a text information about this instance.
     * @return such information.
     */
    @NotNull
    @Override
    public String toString()
    {
        return toString(getId());
    }

    /**
     * Provides a text information about this instance.
     * @param id the <i>id</i> attribute.
     * @return such information.
     */
    @NotNull
    protected String toString(@NotNull final T id)
    {
        return
              getClass().getName()
            + "[" + "id=" + id + "]";
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    @Override
    public int hashCode()
    {
        return
            new org.apache.commons.lang.builder.HashCodeBuilder(-1682907411, 786895267)
                .append(getId())
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
        boolean result = false;

        if  (object instanceof IdentifiableElement)
        {
            @NotNull final IdentifiableElement<?> t_OtherInstance =
                (IdentifiableElement) object;

            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .append(
                        getId(),
                        t_OtherInstance.getId())
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
    public int compareTo(@Nullable final IdentifiableElement<T> object)
        throws  ClassCastException
    {
        int result = -1;

        if (object != null)
        {
            result =
                new org.apache.commons.lang.builder.CompareToBuilder()
                    .append(getId(), object.getId())
                    .toComparison();
        }

        return result;
    }
}
