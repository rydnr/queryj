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
 * Filename: TableValueObject.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Value-object implementation of Table interface.
 *
 */
package org.acmsl.queryj.tools.metadata.vo;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.vo.AbstractTable;
import org.acmsl.queryj.tools.metadata.vo.Table;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * Value-object implementation of <code>Table</code> interface.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public final class TableValueObject
    extends AbstractTable
{
    /**
     * Creates an <code>TableValueObject</code> with the following
     * information.
     * @param name the name.
     * @param attributes the attributes.
     * @param parentTable the parent table.
     * @param voDecorated whether the value-object for the table
     * is decorated.
     */
    public TableValueObject(
        final String name,
        final List attributes,
        final Table parentTable,
        final boolean voDecorated)
    {
        super(name, attributes, parentTable, voDecorated);
    }
}

