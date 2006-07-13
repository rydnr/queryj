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
 * Description: Models <property> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.tools.customsql;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.AbstractIdElement;

/**
 * Models &lt;property&gt; elements in <i>custom-sql</i> models, which
 * satisfy the following DTD extract (to describe the model even in
 * non-xml implementations):
 *   <!ELEMENT property EMPTY>
 *  <!ATTLIST property
 *    id ID #REQUIRED
 *    column_name CDATA #IMPLIED
 *    index CDATA #IMPLIED
 *    name CDATA #IMPLIED
 *    type CDATA #REQUIRED>
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class PropertyElement
    extends  AbstractParameterElement
    implements  Property
{
    /**
     * Whether the property allows nulls or not.
     */
    private boolean m__bNullable;
    
    /**
     * Creates a PropertyElement with given information.
     * @param id the <i>id</i> attribute.
     * @param columnName the <i>column_name</i> attribute.
     * @param index the <i>index</i> attribute.
     * @param name the <i>name</i> attribute.
     * @param type the <i>type</i> attribute.
     * @param nullable the <i>nullable</i> attribute.
     * @precondition id != null
     * @precondition type != null
     */
    public PropertyElement(
        final String id,
        final String columnName,
        final int index,
        final String name,
        final String type,
        final boolean nullable)
    {
        super(id, columnName, index, name, type);

        immutableSetNullable(nullable);
    }

    /**
     * Specifies whether the property is nullable or not.
     * @param flag such condition.
     */
    protected final void immutableSetNullable(final boolean flag)
    {
        m__bNullable = flag;
    }
    
    /**
     * Specifies whether the property is nullable or not.
     * @param flag such condition.
     */
    protected void setNullable(final boolean flag)
    {
        immutableSetNullable(flag);
    }
    

    /**
     * Retrieves ehether the property is nullable or not.
     * @return such condition.
     */
    public boolean isNullable()
    {
        return m__bNullable;
    }
}
