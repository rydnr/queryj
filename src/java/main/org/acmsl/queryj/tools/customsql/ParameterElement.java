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
 * Description: Models <parameter> elements in custom-sql models.
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

/**
 * Models &lt;parameter&gt; elements in <i>custom-sql</i> models, which
 * satisfy the following DTD extract (to describe the model even in
 * non-xml implementations):
 *  <!ELEMENT parameter EMPTY>
 *  <!ATTLIST parameter
 *    id ID #REQUIRED
 *    index CDATA #IMPLIED
 *    type CDATA #REQUIRED>
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public class ParameterElement
    extends  AbstractIdElement
{
    /**
     * The <i>index</i> attribute.
     */
    private int m__iIndex;

    /**
     * The <i>type</i> attribute.
     */
    private String m__strType;

    /**
     * The <i>name</i> attribute.
     */
    private String m__strName;

    /**
     * Creates a ParameterElement with given information.
     * @param id the <i>id</i> attribute.
     * @param index the <i>index</i> attribute.
     * @param type the <i>type</i> attribute.
     * @param name the <i>name</i> attribute.
     */
    public ParameterElement(
        final String id,
        final int index,
        final String type,
        final String name)
    {
        super(id);
        immutableSetIndex(index);
        immutableSetType(type);
        immutableSetName(name);
    }

    /**
     * Specifies the <i>index</i> attribute.
     * @param index such value.
     */
    protected final void immutableSetIndex(final int index)
    {
        m__iIndex = index;
    }

    /**
     * Specifies the <i>index</i> attribute.
     * @param index such value.
     */
    protected void setIndex(final int index)
    {
        m__iIndex = index;
    }

    /**
     * Retrieves the <i>index</i> attribute.
     * @return such value.
     */
    public int getIndex()
    {
        return m__iIndex;
    }

    /**
     * Specifies the <i>type</i> attribute.
     * @param type such value.
     */
    protected final void immutableSetType(final String type)
    {
        m__strType = type;
    }

    /**
     * Specifies the <i>type</i> attribute.
     * @param type such value.
     */
    protected void setType(final String type)
    {
        m__strType = type;
    }

    /**
     * Retrieves the <i>type</i> attribute.
     * @return such value.
     */
    public String getType()
    {
        return m__strType;
    }

    /**
     * Specifies the <i>name</i> attribute.
     * @param name such value.
     */
    protected final void immutableSetName(final String name)
    {
        m__strName = name;
    }

    /**
     * Specifies the <i>name</i> attribute.
     * @param name such value.
     */
    protected void setName(final String name)
    {
        m__strName = name;
    }

    /**
     * Retrieves the <i>name</i> attribute.
     * @return such value.
     */
    public String getName()
    {
        return m__strName;
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
                getIndex(),
                getType(),
                getName());
    }

    /**
     * Provides a text information about this instance.
     * @param id the <i>id</i> attribute.
     * @param index the <i>index</i> attribute.
     * @param type the <i>type</i> attribute.
     * @param name the <i>name</i> attribute.
     * @return such information.
     */
    protected String toString(
        final String id,
        final int index,
        final String type,
        final String name)
    {
        return
              getClass().getName()
            + "[" + "id=" + id + "]"
            + "[" + "index=" + index + "]"
            + "[" + "type=" + type + "]"
            + "[" + "name=" + name + "]";
    }
}
