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
 * Filename: AttributeDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the ways an Attribute can be decorated.
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.vo.Attribute;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Decorator;

/**
 * Defines the ways an <code>Attribute</code> can be decorated.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface AttributeDecorator
    extends  Attribute,
             Decorator
{
    /**
     * Retrieves the decorated <code>Attribute</code>.
     * @return such instance.
     */
    public Attribute getAttribute();

    /**
     * Retrieves whether the attribute is a blob or not.
     * return such information.
     */
    public boolean isBlob();

    /**
     * Retrieves whether the attribute is a clob or not.
     * return such information.
     */
    public boolean isClob();

    /**
     * Retrieves whether the attribute is a string or not.
     * return such information.
     */
    public boolean isString();

    /**
     * Retrieves whether the attribute is a date or not.
     * return such information.
     */
    public boolean isDate();

    /**
     * Retrieves whether the attribute is a timestamp or not.
     * return such information.
     */
    public boolean isTimestamp();

    /**
     * Retrieves whether the type means the attribute is a
     * number smaller than an int.
     * @return such condition.
     */
    public boolean isNumberSmallerThanInt();

    /**
     * Retrieves whether the attribute is numeric or not.
     * @return such information.
     */
    public boolean isNumeric();
}
