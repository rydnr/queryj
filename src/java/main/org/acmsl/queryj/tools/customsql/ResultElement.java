/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <result> elements in custom-sql models.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
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
 * Models &lt;result&gt; elements in <i>custom-sql</i> models, which
 * satisfy the following DTD extract (to describe the model even in
 * non-xml implementations):
 *  <!ELEMENT result (property-ref)+>
 *  <!ATTLIST result
 *    id ID #REQUIRED
 *    class CDATA #IMPLIED
 *    matches (single | multiple) #REQUIRED>
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public class ResultElement
    extends  AbstractIdElement
{
    /**
     * The <b>none</b> value for <i>matches</i> attribute.
     */
    public static final String NONE = "none";

    /**
     * The <b>single</b> value for <i>matches</i> attribute.
     */
    public static final String SINGLE = "single";

    /**
     * The <b>multiple</b> value for <i>multiple</i> attribute.
     */
    public static final String MULTIPLE = "multiple";

    /**
     * The <i>class</i> attribute.
     */
    public String m__strClass;

    /**
     * The <i>matches</i> attribute.
     */
    private String m__strMatches;

    /**
     * The <i>property-ref> elements.
     */
    private Collection m__cPropertyRefs;

    /**
     * Creates a PropertyElement with given information.
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
        super(id);
        immutableSetClassValue(classValue);
        immutableSetMatches(matches);
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
    public String getClassValue()
    {
        return m__strClass;
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
     * Provides a text information about this instance.
     * @return such information.
     */
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
