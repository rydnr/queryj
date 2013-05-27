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

 *****************************************************************************
 *
 * Filename: RowValueObject.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Value-object implementation of Row interface.
 *
 */
package org.acmsl.queryj.metadata.vo;

/*
 * Importing project classes.
 */

/*
 * Importing JDK classes.
 */
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Value-object implementation of <code>Row</code> interface.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@SuppressWarnings("unused")
@ThreadSafe
public class RowValueObject
    extends  AbstractRow
{
    private static final long serialVersionUID = -1296713765073878556L;

    /**
     * Creates a <code>RowValueObject</code> with the following
     * information.
     * @param name the name.
     * @param tableName the table name.
     * @param attributes the attributes.
     * @precondition name != null
     * @precondition tableName != null
     * @precondition attributes != null
     */
    public RowValueObject(
        final String name,
        final String tableName,
        final List<Attribute> attributes)
    {
        super(name, tableName, attributes);
    }
}
