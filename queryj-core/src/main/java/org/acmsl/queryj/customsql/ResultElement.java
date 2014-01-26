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
 * Filename: ResultElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <result> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing project-specific classes.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JDK classes.
 */
import java.util.Collection;
import java.util.Locale;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/**
 * Models &lt;result&gt; elements in <i>custom-sql</i> models, which
 * satisfy the following DTD extract (to describe the model even in
 * non-xml implementations):
 *  <!ELEMENT result (property-ref)+>
 *  <!ATTLIST result
 *    id ID #REQUIRED
 *    class CDATA #IMPLIED
 *    matches (single | multiple) #REQUIRED>
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class ResultElement<T>
    extends  AbstractResult<T>
{
    private static final long serialVersionUID = -1123144871258452499L;
    /**
     * The <i>class</i> attribute.
     */
    public T m__strClass;

    /**
     * Creates a <code>ResultElement</code> with given information.
     * @param id the <i>id</i> attribute.
     * @param classValue the <i>class</i> attribute.
     */
    public ResultElement(@NotNull final T id, @Nullable final T classValue)
    {
        super(id);

        if (classValue != null)
        {
            immutableSetClassValue(classValue);
        }
    }



    /**
     * Specifies the <i>class</i> attribute.
     * @param classValue such value.
     */
    protected final void immutableSetClassValue(@NotNull final T classValue)
    {
        m__strClass = classValue;
    }

    /**
     * Specifies the <i>class</i> attribute.
     * @param classValue such value.
     */
    @SuppressWarnings("unused")
    protected void setClassValue(@NotNull final T classValue)
    {
        immutableSetClassValue(classValue);
    }

    /**
     * Retrieves the <i>class</i> attribute.
     * @return such value.
     */
    @Override
    @Nullable
    public T getClassValue()
    {
        return m__strClass;
    }

    /**
     * Retrieves the hashcode.
     * @return such value.
     */
    public int hashCode()
    {
        return hashCode(getId(), getClassValue(), getPropertyRefs());
    }

    /**
     * Retrieves the hashcode.
     * @param id the <i>id</i> attribute.
     * @param classValue the <i>class</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return such value.
     */
    @SuppressWarnings("unused")
    protected int hashCode(
        @NotNull final T id,
        @NotNull final T classValue,
        @NotNull final Collection<PropertyRef> propertyRefs)
    {
        return ("" + id).toLowerCase(Locale.US).hashCode() + Result.class.hashCode();
    }

    /**
     * Checks whether given instance is semantically equal to this one.
     * @param instance the instance.
     * @return <code>true</code> in such case.
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(@Nullable final Object instance)
    {
        boolean result = false;

        if (instance instanceof Result)
        {
            result =
                equals(
                    (Result<T>) instance,
                    getId(),
                    getClassValue(),
                    getPropertyRefs());
        }

        return result;
    }

    /**
     * Checks whether given instance is semantically equal to this one.
     * @param id the <i>id</i> attribute.
     * @param classValue the <i>class</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return <code>true</code> in such case.
     */
    @SuppressWarnings("unused")
    protected boolean equals(
        @NotNull final Result<T> candidate,
        @NotNull final T id,
        @Nullable final T classValue,
        @NotNull final Collection<PropertyRef> propertyRefs)
    {
        return ("" + id).equalsIgnoreCase("" + candidate.getId());
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
                getClassValue(),
                getPropertyRefs());
    }

    /**
     * Provides a text information about this instance.
     * @param id the <i>id</i> attribute.
     * @param classValue the <i>class</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return such information.
     */
    @NotNull
    protected String toString(
        @NotNull final T id,
        @Nullable final T classValue,
        @NotNull final Collection<PropertyRef> propertyRefs)
    {
        return
              getClass().getName()
            + "[" + "id=" + id + "]"
            + "[" + "class=" + classValue + "]"
            + "[" + "property-refs=" + propertyRefs + "]";
    }
}
