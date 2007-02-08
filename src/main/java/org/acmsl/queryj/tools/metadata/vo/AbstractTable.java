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

 *****************************************************************************
 *
 * Filename: AbstractTable.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Abstract logicless implementation of Table interface.
 *
 */
package org.acmsl.queryj.tools.metadata.vo;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.vo.Table;

/*
 * Importing some JDK classes.
 */
import java.util.List;
 
/**
 * Abstract logicless implementation of <code>Table</code> interface.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractTable
    implements Table
{
    /**
     * The name.
     */
    private String m__strName;

    /**
     * The attribute list.
     */
    private List m__lAttributes;

    /**
     * The parent table, if any.
     */
    private Table m__ParentTable;

    /**
     * Creates an <code>AbstractTable</code> with the following
     * information.
     * @param name the name.
     * @param attributes the attributes.
     * @param parentTable the parent table, if any.
     */
    protected AbstractTable(
        final String name, final List attributes, final Table parentTable)
    {
        immutableSetName(name);
        immutableSetAttributes(attributes);
        immutableSetParentTable(parentTable);
    }
    
    /**
     * Specifies the name.
     * @param name such name.
     */
    protected final void immutableSetName(final String name)
    {
        m__strName = name;
    }
    
    /**
     * Specifies the name.
     * @param name such name.
     */
    protected void setName(final String name)
    {
        immutableSetName(name);
    }
    
    /**
     * Retrieves the table name.
     * @return such name.
     */
    public String getName()
    {
        return m__strName;
    }

    /**
     * Specifies the attributes.
     * @param atts the attributes.
     */
    protected final void immutableSetAttributes(final List attrs)
    {
        m__lAttributes = attrs;
    }

    /**
     * Specifies the attributes.
     * @param atts the attributes.
     */
    protected void setAttributes(final List attrs)
    {
        immutableSetAttributes(attrs);
    }

    /**
     * Retrieves the attributes.
     * @return such list.
     */
    protected final List immutableGetAttributes()
    {
        return m__lAttributes;
    }

    /**
     * Retrieves the attributes.
     * @return such list.
     */
    public List getAttributes()
    {
        return immutableGetAttributes();
    }

    /**
     * Specifies the parent table.
     * @param parentTable the parent table.
     */
    protected final void immutableSetParentTable(final Table parentTable)
    {
        m__ParentTable = parentTable;
    }

    /**
     * Specifies the parent table.
     * @param parentTable the parent table.
     */
    protected void setParentTable(final Table parentTable)
    {
        immutableSetParentTable(parentTable);
    }

    /**
     * Retrieves the parent table.
     * @return such table.
     */
    public Table getParentTable()
    {
        return m__ParentTable;
    }
}
