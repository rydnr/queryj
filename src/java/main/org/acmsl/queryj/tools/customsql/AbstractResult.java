//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Description: Common logic for all <result> elements in custom-sql models.
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
 * Commons logic for all &lt;result&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractResult
    extends  AbstractIdElement
    implements  Result
{
    /**
     * The <i>matches</i> attribute.
     */
    private String m__strMatches;

    /**
     * The <i>property-ref> elements.
     */
    private Collection m__cPropertyRefs;

    /**
     * Creates a <code>AbstractResult</code> with given information.
     * @param id the <i>id</i> attribute.
     * @param matches the <i>matches</i> attribute.
     * @precondition id != null
     * @precondition matches != null
     */
    public AbstractResult(final String id, final String matches)
    {
        super(id);
        immutableSetMatches(matches);
    }

    /**
     * Specifies the <i>matches</i> attribute.
     * @param matches such value.
     */
    protected final void immutableSetMatches(final String matches)
    {
        m__strMatches = matches;
    }

    /**
     * Specifies the <i>matches</i> attribute.
     * @param matches such value.
     */
    protected void setMatches(final String matches)
    {
        immutableSetMatches(matches);
    }

    /**
     * Retrieves the <i>matches</i> attribute.
     * @return such value.
     */
    public String getMatches()
    {
        return m__strMatches;
    }

    /**
     * Specifies the &lt;property-ref&gt; elements.
     * @param propertyRefs such elements.
     */
    protected final void immutableSetPropertyRefs(final Collection collection)
    {
        m__cPropertyRefs = collection;
    }

    /**
     * Specifies the &lt;property-ref&gt; elements.
     * @param propertyRefs such elements.
     */
    protected void setPropertyRefs(final Collection collection)
    {
        immutableSetPropertyRefs(collection);
    }

    /**
     * Retrieves the &lt;property-ref&gt; elements.
     * @return such elements.
     */
    public Collection getPropertyRefs()
    {
        return m__cPropertyRefs;
    }

    /**
     * Adds a new &lt;property-ref&gt; element.
     * @param propertyRef such element.
     */
    public void add(final PropertyRefElement propertyRef)
    {
        add(propertyRef, getPropertyRefs());
    }

    /**
     * Adds a new &lt;property-ref&gt; element.
     * @param propertyRef such element.
     * @param propertyRefs thhe &ltproperty-ref&gt; elements.
     */
    protected synchronized void add(
        final PropertyRefElement propertyRef, final Collection propertyRefs)
    {
        Collection t_cPropertyRefs = propertyRefs;

        if  (t_cPropertyRefs == null)
        {
            t_cPropertyRefs = new ArrayList();
            setPropertyRefs(t_cPropertyRefs);
        }

        t_cPropertyRefs.add(propertyRef);
    }

    /**
     * Retrieves the hashcode.
     * @return such value.
     */
    public int hashCode()
    {
        return
            hashCode(
                getId(), getMatches(), getPropertyRefs());
    }

    /**
     * Retrieves the hashcode.
     * @param id the <i>id</i> attribute.
     * @param matches the <i>matches</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return such value.
     */
    protected int hashCode(
        final String id,
        final String matches,
        final Collection propertyRefs)
    {
        return
            (id + "@#" + matches + "@#" + propertyRefs)
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
                getPropertyRefs());
    }

    /**
     * Checks whether given instance is semantically equal to this one.
     * @param id the <i>id</i> attribute.
     * @param matches the <i>matches</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return <code>true</code> in such case.
     */
    public boolean equals(
        final Object instance,
        final String id,
        final String matches,
        final Collection propertyRefs)
    {
        boolean result = false;

        if  (instance instanceof Result)
        {
            Result candidate = (Result) instance;

            result =
                (   (id.equalsIgnoreCase(candidate.getId())
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
    public String toString()
    {
        return
            toString(
                getId(),
                getMatches(),
                getPropertyRefs());
    }

    /**
     * Provides a text information about this instance.
     * @param id the <i>id</i> attribute.
     * @param matches the <i>matches</i> attribute.
     * @param propertyRefs the <i>property-ref</i> elements.
     * @return such information.
     */
    protected String toString(
        final String id,
        final String matches,
        final Collection propertyRefs)
    {
        return
              getClass().getName()
            + "[" + "id=" + id + "]"
            + "[" + "matches=" + matches + "]"
            + "[" + "property-refs=" + propertyRefs + "]";
    }
}
