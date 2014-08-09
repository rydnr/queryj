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
 * Filename: ImplicitDAOResult.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models pre-defined per-DAO <result> elements in custom-sql
 *              models.
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing JDK classes.
 */
import java.util.Collection;

/*
 * Importing Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Models pre-defined per-DAO &lt;result&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
@SuppressWarnings("unused")
public class ImplicitDAOResult<T>
    extends  AbstractResult<T>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -8715430319121603602L;

    /**
     * The <i>DAO</i> attribute.
     */
    public T m__strDAORef;

    /**
     * Creates a <code>ImplicitDAOResult</code> with given information.
     * @param id the <i>id</i> attribute.
     * @param daoRef the <i>daoRef</i> attribute.
     */
    @SuppressWarnings("unused")
    public ImplicitDAOResult(@NotNull final T id, @NotNull final T daoRef)
    {
        super(id);
        immutableSetDaoRef(daoRef);
    }

    /**
     * Specifies the <i>daoRef</i> attribute.
     * @param daoRef such value.
     */
    protected final void immutableSetDaoRef(@NotNull final T daoRef)
    {
        m__strDAORef = daoRef;
    }

    /**
     * Specifies the <i>daoRef</i> attribute.
     * @param daoRef such value.
     */
    @SuppressWarnings("unused")
    protected void setDaoRef(@NotNull final T daoRef)
    {
        immutableSetDaoRef(daoRef);
    }

    /**
     * Retrieves the <i>daoRef</i> attribute.
     * @return such value.
     */
    public T getDaoRef()
    {
        return m__strDAORef;
    }

    /**
     * Retrieves the <i>class</i> attribute.
     * @return such value.
     */
    @Override
    @NotNull
    public T getClassValue()
    {
        throw
            new IllegalArgumentException(
                "Implicit results don't allow classValue");
    }

    /**
     * Retrieves the hashcode.
     * @return such value.
     */
    @Override
    public int hashCode()
    {
        return hashCode(getId(), getDaoRef(), getPropertyRefs());
    }

    /**
     * Retrieves the hashcode.
     * @param id the <i>id</i> attribute.
     * @param daoRef the <i>daoRef</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return such value.
     */
    protected int hashCode(
        final T id,
        final T daoRef,
        final Collection<PropertyRef> propertyRefs)
    {
        return
            (id + "@#" + daoRef + "@#" + propertyRefs)
            .toLowerCase().hashCode();
    }

    /**
     * Checks whether given instance is semantically equal to this one.
     * @param instance the instance.
     * @return <code>true</code> in such case.
     */
    @Override
    public boolean equals(final Object instance)
    {
        return
            equals(
                instance,
                getId(),
                getDaoRef(),
                getPropertyRefs());
    }

    /**
     * Checks whether given instance is semantically equal to this one.
     * @param instance the candidate.
     * @param id the <i>id</i> attribute.
     * @param daoRef the <i>daoRef</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return <code>true</code> in such case.
     */
    @SuppressWarnings("unchecked")
    public boolean equals(
        final Object instance,
        @NotNull final T id,
        @NotNull final T daoRef,
        final Collection<PropertyRef> propertyRefs)
    {
        boolean result = false;

        if  (instance instanceof ImplicitDAOResult)
        {
            @NotNull final ImplicitDAOResult<T> candidate = (ImplicitDAOResult<T>) instance;

            result =
                (   (("" + id).equalsIgnoreCase("" + candidate.getId())
                 && (("" + daoRef).equalsIgnoreCase("" + candidate.getDaoRef()))
                 && ("" + propertyRefs).equals("" + candidate.getPropertyRefs())));
        }

        return result;
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
                getDaoRef(),
                getPropertyRefs());
    }

    /**
     * Provides a text information about this instance.
     * @param id the <i>id</i> attribute.
     * @param daoRef the <i>daoRef</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return such information.
     */
    @NotNull
    protected String toString(
        final T id,
        final T daoRef,
        final Collection<PropertyRef> propertyRefs)
    {
        return
              getClass().getName()
            + "[" + "id=" + id + "]"
            + "[" + "daoRef=" + daoRef + "]"
            + "[" + "property-refs=" + propertyRefs + "]";
    }

    /**
     * Compares given object with this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    @SuppressWarnings("unchecked")
    @Override
    public int compareTo(@Nullable final Result<T> object)
        throws  ClassCastException
    {
        final int result;

        if  (object instanceof ImplicitDAOResult)
        {
            @NotNull final ImplicitDAOResult<T> t_OtherInstance =
                (ImplicitDAOResult<T>) object;

            result =
                new org.apache.commons.lang.builder.CompareToBuilder()
                .append(
                    getDaoRef(),
                    t_OtherInstance.getDaoRef())
                .append(
                    getClassValue(),
                    t_OtherInstance.getClassValue())
                .toComparison();
        }
        else
        {
            result = super.compareTo(object);
        }

        return result;
    }
}
