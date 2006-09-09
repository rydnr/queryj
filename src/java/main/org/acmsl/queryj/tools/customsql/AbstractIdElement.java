//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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

 ******************************************************************************
 *
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Common parent to all <b>sql.xml</b> elements with <i>id</i>
 *              attributes.
 *
 */
package org.acmsl.queryj.tools.customsql;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.customsql.IdentifiableElement;

/**
 * Common parent to all <i>custom-sql</i> elements with <i>id</i>
 * attributes.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractIdElement
    implements IdentifiableElement
{
    /**
     * The <i>id</i> attribute.
     */
    private String m__strId;

    /**
     * Builds an AbstractIdElement with given <i>id</i>.
     * @param id the id.
     * @precondition id != null
     */
    protected AbstractIdElement(final String id)
    {
        immutableSetId(id);
    }

    /**
     * Specifies the <i>id</i> value.
     * @param id the id.
     */
    protected final void immutableSetId(final String id)
    {
        m__strId = id;
    }

    /**
     * Specifies the <i>id</i> value.
     * @param id the id.
     */
    protected void setId(final String id)
    {
        immutableSetId(id);
    }

    /**
     * Retrieves the <i>id</i> value.
     * @return such information.
     */
    public String getId()
    {
        return m__strId;
    }

    /**
     * Provides a text information about this instance.
     * @return such information.
     */
    public String toString()
    {
        return toString(getId());
    }

    /**
     * Provides a text information about this instance.
     * @param id the <i>id</i> attribute.
     * @param classValue the <i>class</i> attribute.
     * @param matches the <i>matches</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return such information.
     */
    protected String toString(final String id)
    {
        return
              getClass().getName()
            + "[" + "id=" + id + "]";
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    public int hashCode()
    {
        return
            new org.apache.commons.lang.builder.HashCodeBuilder(-1682907411, 786895267)
                .appendSuper(super.hashCode())
                .append(getId())
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

        if  (object instanceof IdentifiableElement)
        {
            final IdentifiableElement t_OtherInstance =
                (IdentifiableElement) object;

            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .appendSuper(super.equals(t_OtherInstance))
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
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     */
    public int compareTo(final Object object)
        throws  ClassCastException
    {
        int result = 1;

        ClassCastException exceptionToThrow = null;

        if  (object instanceof IdentifiableElement)
        {
                final IdentifiableElement t_OtherInstance =
                    (IdentifiableElement) object;

                result =
                    new org.apache.commons.lang.builder.CompareToBuilder()
                        .append(getId(), t_OtherInstance.getId())
                        .toComparison();
        }
        else
        {
            exceptionToThrow =
                new ClassCastException(
                      "Cannot compare "
                    + object
                    + " with "
                    + toString());
        }

        if  (exceptionToThrow != null)
        {
            throw  exceptionToThrow;
        }

        return result;
    }
}
