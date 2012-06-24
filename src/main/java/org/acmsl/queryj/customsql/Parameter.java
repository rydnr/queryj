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
 * Filename: Parameter.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <parameter> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing project-specific classes.
 */

/**
 * Models elements in <i>custom-sql</i> models, which
 * satisfy the following DTD extract (to describe the model even in
 * non-xml implementations):
 *   <!ELEMENT <i>element</i> EMPTY>
 *  <!ATTLIST <i>element</i>
 *    id ID #REQUIRED
 *    column_name CDATA #IMPLIED
 *    index CDATA #IMPLIED
 *    name CDATA #IMPLIED
 *    type CDATA #REQUIRED>
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface Parameter
    extends  IdentifiableElement
{
    /**
     * Retrieves the <i>column_name</i> attribute.
     * @return such value.
     */
    public String getColumnName();

    /**
     * Retrieves the <i>index</i> attribute.
     * @return such value.
     */
    public int getIndex();

    /**
     * Retrieves the <i>name</i> attribute.
     * @return such value.
     */
    public String getName();

    /**
     * Retrieves the <i>type</i> attribute.
     * @return such value.
     */
    public String getType();

    /**
     * Retrieves the validation value.
     * @return such value.
     */
    public String getValidationValue();

}
