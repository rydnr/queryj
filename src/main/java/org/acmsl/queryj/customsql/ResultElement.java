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
     * @precondition id != null
     * @precondition matches != null
     */
    public ResultElement(
        final String id,
        final String classValue,
        final String matches)
    {
        super(id, matches);
        immutableSetClassValue(classValue);
    }

    /**
     * Specifies the <i>class</i> attribute.
     * @param classValue such value.
     */
    protected final void immutableSetClassValue(final String classValue)
    {
        m__strClass = classValue;
    }

    /**
     * Specifies the <i>class</i> attribute.
     * @param classValue such value.
     */
    protected void setClassValue(final String classValue)
    {
        immutableSetClassValue(classValue);
    }

    /**
     * Retrieves the <i>class</i> attribute.
     * @return such value.
     */
    @NotNull
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
    public boolean equals(final Object instance)
    {
        return
            equals(
                instance,
                getId(),
                getClassValue(),
                getMatches(),
                getPropertyRefs());
    }

    /**
     * Checks whether given instance is semantically equal to this one.
     * @param id the <i>id</i> attribute.
     * @param classValue the <i>class</i> attribute.
     * @param matches the <i>matches</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return <code>true</code> in such case.
     */
    public boolean equals(
        final Object instance,
        @NotNull final String id,
        @NotNull final String classValue,
        @NotNull final String matches,
        final Collection propertyRefs)
    {
        boolean result = false;

        if  (instance instanceof ResultElement)
        {
            @NotNull ResultElement candidate = (ResultElement) instance;

            result =
                (   (id.equalsIgnoreCase(candidate.getId())
                 && (classValue.equals(candidate.getClassValue()))
                 && (matches.equalsIgnoreCase(candidate.getMatches()))
                 && ("" + propertyRefs).equals(
                         "" + candidate.getPropertyRefs())));
        }

        return result;
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