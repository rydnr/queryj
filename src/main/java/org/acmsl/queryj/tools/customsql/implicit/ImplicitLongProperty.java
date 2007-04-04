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

 ******************************************************************************
 *
 * Filename: ImplicitLongProperty.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents implicit long properties.
 *
 */
package org.acmsl.queryj.tools.customsql.implicit;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.implicit.AbstractImplicitProperty;

/**
 * Represents implicit long properties.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class ImplicitLongProperty
    extends  AbstractImplicitProperty
{
    /**
     * Creates an <code>ImplicitLongProperty</code> with given information.
     * @param id the <i>id</i> attribute.
     * @param nullable the <i>nullable</i> attribute.
     * @precondition id != null
     */
    public ImplicitLongProperty(
        final String id, final boolean nullable)
    {
        super(id, Long.class.getName(), nullable);
    }
}
