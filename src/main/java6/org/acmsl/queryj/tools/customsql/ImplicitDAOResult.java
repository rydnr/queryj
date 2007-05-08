//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: ImplicitDAOResult.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models pre-defined per-DAO <result> elements in custom-sql
 *              models.
 *
 */
package org.acmsl.queryj.tools.customsql;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.AbstractIdElement;
import org.acmsl.queryj.tools.customsql.PropertyRefElement;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;

/**
 * Models pre-defined per-DAO &lt;result&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class ImplicitDAOResult
    extends  AbstractResult
{
    /**
     * The <i>DAO</i> attribute.
     */
    public String m__strDAORef;

    /**
     * Creates a <code>ImplicitDAOResult</code> with given information.
     * @param id the <i>id</i> attribute.
     * @param matches the <i>matches</i> attribute.
     * @param daoRef the <i>daoRef</i> attribute.
     * @precondition id != null
     * @precondition matches != null
     * @precondition daoRef != null
     */
    public ImplicitDAOResult(
        final String id, final String matches, final String daoRef)
    {
        super(id, matches);
        immutableSetDaoRef(daoRef);
    }

    /**
     * Specifies the <i>daoRef</i> attribute.
     * @param daoRef such value.
     */
    protected final void immutableSetDaoRef(final String daoRef)
    {
        m__strDAORef = daoRef;
    }

    /**
     * Specifies the <i>daoRef</i> attribute.
     * @param daoRef such value.
     */
    protected void setDaoRef(final String daoRef)
    {
        immutableSetDaoRef(daoRef);
    }

    /**
     * Retrieves the <i>daoRef</i> attribute.
     * @return such value.
     */
    public String getDaoRef()
    {
        return m__strDAORef;
    }

    /**
     * Retrieves the <i>class</i> attribute.
     * @return such value.
     */
    public String getClassValue()
    {
        throw
            new IllegalArgumentException(
                "Implicit results don't allow classValue");
    }

    /**
     * Retrieves the hashcode.
     * @return such value.
     */
    public int hashCode()
    {
        return
            hashCode(
                getId(), getMatches(), getDaoRef(), getPropertyRefs());
    }

    /**
     * Retrieves the hashcode.
     * @param id the <i>id</i> attribute.
     * @param matches the <i>matches</i> attribute.
     * @param daoRef the <i>daoRef</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return such value.
     */
    protected int hashCode(
        final String id,
        final String matches,
        final String daoRef,
        final Collection propertyRefs)
    {
        return
            (id + "@#" + matches + "#@" + daoRef + "@#" + propertyRefs)
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
                getMatches(),
                getDaoRef(),
                getPropertyRefs());
    }

    /**
     * Checks whether given instance is semantically equal to this one.
     * @param id the <i>id</i> attribute.
     * @param matches the <i>matches</i> attribute.
     * @param daoRef the <i>daoRef</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return <code>true</code> in such case.
     */
    public boolean equals(
        final Object instance,
        final String id,
        final String matches,
        final String daoRef,
        final Collection propertyRefs)
    {
        boolean result = false;

        if  (instance instanceof ImplicitDAOResult)
        {
            ImplicitDAOResult candidate = (ImplicitDAOResult) instance;

            result =
                (   (id.equalsIgnoreCase(candidate.getId())
                 && (matches.equalsIgnoreCase(candidate.getMatches()))
                 && (daoRef.equalsIgnoreCase(candidate.getDaoRef()))
                 && ("" + propertyRefs).equals(
                         "" + candidate.getPropertyRefs())));
        }

        return result;
    }

    /**
     * Provides a text information about this instance.
     * @return such information.
     */
    public String toString()
    {
        return
            toString(
                getId(),
                getMatches(),
                getDaoRef(),
                getPropertyRefs());
    }

    /**
     * Provides a text information about this instance.
     * @param id the <i>id</i> attribute.
     * @param matches the <i>matches</i> attribute.
     * @param daoRef the <i>daoRef</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return such information.
     */
    protected String toString(
        final String id,
        final String matches,
        final String daoRef,
        final Collection propertyRefs)
    {
        return
              getClass().getName()
            + "[" + "id=" + id + "]"
            + "[" + "matches=" + matches + "]"
            + "[" + "daoRef=" + daoRef + "]"
            + "[" + "property-refs=" + propertyRefs + "]";
    }
}
