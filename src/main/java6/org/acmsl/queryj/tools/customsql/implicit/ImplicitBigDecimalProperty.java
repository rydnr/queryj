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
    License abigDecimal with this library; if not, write to the Free Software
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
 * Filename: ImplicitBigDecimalProperty.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents implicit BigDecimal properties.
 *
 */
package org.acmsl.queryj.tools.customsql.implicit;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.implicit.AbstractImplicitProperty;

/*
 * Importing some JDK classes.
 */
import java.math.BigDecimal;

/**
 * Represents implicit BigDecimal properties.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class ImplicitBigDecimalProperty
    extends  AbstractImplicitProperty
{
    /**
     * Creates an <code>ImplicitBigDecimalProperty</code> with given information.
     * @param id the <i>id</i> attribute.
     * @param nullable the <i>nullable</i> attribute.
     * @precondition id != null
     */
    public ImplicitBigDecimalProperty(
        final String id, final boolean nullable)
    {
        super(id, BigDecimal.class.getName(), nullable);
    }
}
