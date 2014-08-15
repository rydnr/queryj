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
 * Filename: AbstractResult.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Common logic for all <result> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Commons logic for all &lt;result&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @param <T> the type.
 */
public abstract class AbstractResult<T>
    extends  AbstractIdElement<T>
    implements  Result<T>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -9151430656489323601L;

    /**
     * The <i>property-ref</i> elements.
     */
    private List<PropertyRef> m__lPropertyRefs;

    /**
     * Creates a <code>AbstractResult</code> with given information.
     * @param id the <i>id</i> attribute.
     */
    public AbstractResult(@NotNull final T id)
    {
        super(id);
    }

    /**
     * Specifies the &lt;property-ref&gt; elements.
     * @param propertyRefs such elements.
     */
    protected final void immutableSetPropertyRefs(@NotNull final List<PropertyRef> propertyRefs)
    {
        m__lPropertyRefs = propertyRefs;
    }

    /**
     * Specifies the &lt;property-ref&gt; elements.
     * @param propertyRefs such elements.
     */
    @SuppressWarnings("unused")
    protected void setPropertyRefs(@NotNull final List<PropertyRef> propertyRefs)
    {
        immutableSetPropertyRefs(propertyRefs);
    }

    /**
     * Retrieves the &lt;property-ref&gt; elements.
     * @return such elements.
     */
    @Nullable
    protected final List<PropertyRef> immutableGetPropertyRefs()
    {
        return m__lPropertyRefs;
    }

    /**
     * Retrieves the &lt;property-ref&gt; elements.
     * @return such elements.
     */
    @Override
    @NotNull
    public List<PropertyRef> getPropertyRefs()
    {
        List<PropertyRef> result = immutableGetPropertyRefs();

        if (result == null)
        {
            result = new ArrayList<>(0);
            setPropertyRefs(result);
        }

        return result;
    }

    /**
     * Adds a new &lt;property-ref&gt; element.
     * @param propertyRef such element.
     */
    public void add(@NotNull final PropertyRef propertyRef)
    {
        add(propertyRef, getPropertyRefs());
    }

    /**
     * Adds a new &lt;property-ref&gt; element.
     * @param propertyRef such element.
     * @param propertyRefs thhe &lt;property-ref&gt; elements.
     */
    protected synchronized void add(
        @NotNull final PropertyRef propertyRef, @NotNull final List<PropertyRef> propertyRefs)
    {
        propertyRefs.add(propertyRef);
    }

    /**
     * Retrieves the hashcode.
     * @return such value.
     */
    @Override
    public int hashCode()
    {
        return hashCode(getId(), getPropertyRefs());
    }

    /**
     * Retrieves the hashcode.
     * @param id the <i>id</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return such value.
     */
    protected int hashCode(
        final T id, final Collection<PropertyRef> propertyRefs)
    {
        return (id + "@#" + propertyRefs).toLowerCase(Locale.US).hashCode();
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
                    getPropertyRefs());
        }

        return result;
    }

    /**
     * Checks whether given instance is semantically equal to this one.
     * @param candidate the candidate.
     * @param id the <i>id</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return <code>true</code> in such case.
     */
    public boolean equals(
        @NotNull final Result<T> candidate,
        @NotNull final T id,
        final Collection<PropertyRef> propertyRefs)
    {
        return
            (   (("" + id).equalsIgnoreCase("" + candidate.getId())
             && ("" + propertyRefs).equals(
                     "" + candidate.getPropertyRefs())));
    }

    /**
     * Compares given object with this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    @Override
    public int compareTo(@Nullable final Result<T> object)
        throws  ClassCastException
    {
        int result = -1;

        if (object != null)
        {
            result =
                new org.apache.commons.lang.builder.CompareToBuilder()
                    .append(
                        getId(),
                        object.getId())
                    .toComparison();
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
            "{ \"property-refs\": \"" + this.m__lPropertyRefs + '"'
            + ", \"super\": " + super.toString()
            + ", \"class\": \"AbstractResult\""
            + ", \"package\": \"org.acmsl.queryj.customsql\" }";
    }
}
