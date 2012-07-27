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
public class ResultElement
    extends  AbstractResult
{
    private static final long serialVersionUID = -1123144871258452499L;
    /**
     * The <i>class</i> attribute.
     */
    public String m__strClass;

    /**
     * Creates a <code>ResultElement</code> with given information.
     * @param id the <i>id</i> attribute.
     * @param classValue the <i>class</i> attribute.
     * @param matches the <i>matches</i> attribute.
     */
    public ResultElement(
        @NotNull final String id,
        @Nullable final String classValue,
        @NotNull final String matches)
    {
        super(id, matches);

        if (classValue != null)
        {
            immutableSetClassValue(classValue);
        }
    }

    /**
     * Specifies the <i>class</i> attribute.
     * @param classValue such value.
     */
    protected final void immutableSetClassValue(@NotNull final String classValue)
    {
        m__strClass = classValue;
    }

    /**
     * Specifies the <i>class</i> attribute.
     * @param classValue such value.
     */
    @SuppressWarnings("unused")
    protected void setClassValue(@NotNull final String classValue)
    {
        immutableSetClassValue(classValue);
    }

    /**
     * Retrieves the <i>class</i> attribute.
     * @return such value.
     */
    @Override
    @Nullable
    public String getClassValue()
    {
        return m__strClass;
    }

    /**
     * Retrieves the hashcode.
     * @return such value.
     */
    public int hashCode()
    {
        return
            hashCode(
                getId(), getClassValue(), getMatches(), getPropertyRefs());
    }

    /**
     * Retrieves the hashcode.
     * @param id the <i>id</i> attribute.
     * @param classValue the <i>class</i> attribute.
     * @param matches the <i>matches</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return such value.
     */
    protected int hashCode(
        final String id,
        final String classValue,
        final String matches,
        final Collection propertyRefs)
    {
        return
            (id + "@#" + classValue + "#@" + matches + "@#" + propertyRefs)
            .toLowerCase().hashCode();
    }

    /**
     * Checks whether given instance is semantically equal to this one.
     * @param instance the instance.
     * @return <code>true</code> in such case.
     */
    public boolean equals(@Nullable final Object instance)
    {
        boolean result = false;

        if (instance instanceof Result)
        {
            result =
                equals(
                    (Result) instance,
                    getId(),
                    getClassValue(),
                    getMatches(),
                    getPropertyRefs());
        }

        return result;
    }

    /**
     * Checks whether given instance is semantically equal to this one.
     * @param id the <i>id</i> attribute.
     * @param classValue the <i>class</i> attribute.
     * @param matches the <i>matches</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return <code>true</code> in such case.
     */
    @SuppressWarnings("unused")
    protected boolean equals(
        @NotNull final Result candidate,
        @NotNull final String id,
        @Nullable final String classValue,
        @NotNull final String matches,
        final Collection<PropertyRef> propertyRefs)
    {
        return id.equalsIgnoreCase(candidate.getId());
    }

    /**
     * Provides a text information about this instance.
     * @return such information.
     */
    @NotNull
    public String toString()
    {
        return
            toString(
                getId(),
                getClassValue(),
                getMatches(),
                getPropertyRefs());
    }

    /**
     * Provides a text information about this instance.
     * @param id the <i>id</i> attribute.
     * @param classValue the <i>class</i> attribute.
     * @param matches the <i>matches</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return such information.
     */
    @NotNull
    protected String toString(
        final String id,
        final String classValue,
        final String matches,
        final Collection propertyRefs)
    {
        return
              getClass().getName()
            + "[" + "id=" + id + "]"
            + "[" + "class=" + classValue + "]"
            + "[" + "matches=" + matches + "]"
            + "[" + "property-refs=" + propertyRefs + "]";
    }
}
