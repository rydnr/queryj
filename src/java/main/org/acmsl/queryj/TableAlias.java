/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

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
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendáriz
 *
 * Description: Represents SQL fields.
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
package org.acmsl.queryj;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.Table;

/**
 * Represents SQL fields.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class TableAlias
{
    /**
     * The alias name.
     */
    private String m__strName;

    /**
     * The table this alias points to.
     */
    private Table m__Table;

    /**
     * Creates a table alias using given information.
     * @param name the field name.
     * @param table the table.
     */
    public TableAlias(String name, Table table)
    {
        unmodifiableSetName(name);
        unmodifiableSetTable(table);
    }

    /**
     * Specifies the alias name.
     * @param name the name.
     */
    private void unmodifiableSetName(String name)
    {
        m__strName = name;
    }

    /**
     * Specifies the alias name.
     * @param name the name.
     */
    protected void setName(String name)
    {
        unmodifiableSetName(name);
    }

    /**
     * Retrieves the alias name.
     * @return such reference.
     */
    public String getName()
    {
        return m__strName;
    }

    /**
     * Specifies the table.
     * @param table the table.
     */
    private void unmodifiableSetTable(Table table)
    {
        m__Table = table;
    }

    /**
     * Specifies the table.
     * @param table the table.
     */
    protected void setTable(Table table)
    {
        unmodifiableSetTable(table);
    }

    /**
     * Retrieves the table.
     * @return such reference.
     */
    public Table getTable()
    {
        return m__Table;
    }

    /**
     * Outputs a text version of the tablealias.
     * @return such text.
     */
    public String toString()
    {
        return getName();
    }
}
