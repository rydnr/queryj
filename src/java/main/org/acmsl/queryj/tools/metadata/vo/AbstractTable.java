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
     * Creates an <code>AbstractTable</code> with the following
     * information.
     * @param name the name.
     */
    protected AbstractTable(final String name)
    {
        immutableSetName(name);
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
}
