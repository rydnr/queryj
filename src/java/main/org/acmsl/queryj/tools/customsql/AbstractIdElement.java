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
 * Description: Common parent to all <b>sql.xml</b> elements with <i>id</i>
 *              attributes.
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
package org.acmsl.queryj.sqlxml;

/**
 * Common parent to all <b>sql.xml</b> elements with <i>id</i>
 * attributes.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class AbstractIdElement
{
    /**
     * The <i>id</i> attribute.
     */
    private String m__strId;

    /**
     * Builds an AbstractIdElement with given <i>id</i>.
     * @param id the id.
     */
    protected AbstractIdElement(final String id)
    {
        immutableSetId(id);
    }

    /**
     * Specifies the <i>id</i> value.
     * @param id the id.
     */
    private void immutableSetId(final String id)
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
}
