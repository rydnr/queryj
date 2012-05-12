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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 *****************************************************************************
 *
 * Filename: Attribute.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents 'attribute' entities in the metadata model.
 *
 */
package org.acmsl.queryj.tools.metadata.vo;

/**
 * Represents <i>attribute</i> entities in the metadata model.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface Attribute
    extends Comparable
{
    /**
     * Retrieves the attribute name.
     * @return such name.
     */
    public String getName();

    /**
     * Retrieves the attribute type.
     * @return its type.
     */
    public int getTypeId();
    
    /**
     * Retrieves the field type.
     * @return such information.
     */
    public String getType();

    /**
     * Retrieves the table name.
     * @return such information.
     */
    public String getTableName();

    /**
     * Retrieves the column comment.
     * @return such information.
     */
    public String getComment();

    /**
     * Retrieves whether it's managed externally.
     * @return such information.
     */
    public boolean getManagedExternally();

    /**
     * Retrieves whether it allows null values or not.
     * @return such information.
     */
    public boolean isNullable();

    /**
     * Retrieves the optional attribute's value, meaning
     * it doesn't just describe the metadata, but also
     * contains data.
     * @return such information.
     */
    public String getValue();

    /**
     * Retrieves whether the attribute is marked as read-only.
     * @return such information.
     */
    public boolean isReadOnly();

    /**
     * Retrieves whether the attribute is marked as boolean.
     * @return such information.
     */
    public boolean isBoolean();

    /**
     * Retrieves the symbol for <code>true</code> values.
     * @return such information.
     */
    public String getBooleanTrue();

    /**
     * Retrieves the symbol for <code>false</code> values.
     * @return such information.
     */
    public String getBooleanFalse();

    /**
     * Retrieves the symbol for <code>null</code> values.
     * @return such information.
     */
    public String getBooleanNull();
}

