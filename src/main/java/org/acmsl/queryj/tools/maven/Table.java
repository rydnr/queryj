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
 * Filename: Table.java
 *
 * Author: Jose Juan
 *
 * Description: Models a table.
 */
package org.acmsl.queryj.tools.maven;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * Models a table.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class Table
{
    /**
     * The name.
     * @parameter property="name"
     */
    private String m__strName;
    
    /**
     * The list of fields.
     * @parameter property="fields"
     */
    private List<Field> m__aFields;
    
    /**
     * Specifies the name.
     * @param name the table name.
     */
    protected final void immutableSetName(final String name)
    {
        m__strName = name;
    }

    /**
     * Specifies the name.
     * @param name the table name.
     */
    public void setName(final String name)
    {
        immutableSetName(name);
    }

    /**
     * Returns the name.
     * @return such value.
     */
    protected final String immutableGetName()
    {
        return m__strName;
    }

    /**
     * Returns the name.
     * @return such value.
     */
    protected String getName()
    {
        return immutableGetName();
    }

    /**
     * Specifies the fields.
     * @param fields the fields.
     */
    protected final void immutableSetFields(final List<Field> fields)
    {
        m__aFields = fields;
    }

    /**
     * Specifies the fields.
     * @param fields the fields.
     */
    public void setFields(final List<Field> fields)
    {
        immutableSetFields(fields);
    }

    /**
     * Returns the fields.
     * @return such values.
     */
    protected final List<Field> immutableGetFields()
    {
        return m__aFields;
    }

    /**
     * Returns the fields.
     * @return such values.
     */
    protected List<Field> getFields()
    {
        return immutableGetFields();
    }
}
