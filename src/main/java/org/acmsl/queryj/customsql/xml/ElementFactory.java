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

 ******************************************************************************
 *
 * Filename: ElementFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create sql.xml element instances from their
 *              attributes, while being parsed by Digester.
 *
 */
package org.acmsl.queryj.customsql.xml;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.ConversionUtils;

/*
 * Importing some additional classes.
 */
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreationFactory;
import org.jetbrains.annotations.Nullable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Is able to create sql.xml element instances from their
 * attributes, while being parsed by Digester.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class ElementFactory
    implements ObjectCreationFactory
{
    /**
     * Temporary Digester reference.
     */
    private Digester m__Digester;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected ElementFactory() {};

    /**
     * Specifies a new Digester instance.
     * @param digester such instance.
     */
    public void setDigester(final Digester digester)
    {
        m__Digester = digester;
    }

    /**
     * Retrieves the Digester instance.
     * @return such instance.
     */
    public Digester getDigester()
    {
        return m__Digester;
    }

    /**
     * Creates a Customer value object from given SAX attributes.
     * @param attributes the attributes.
     * @return the Customer information.
     * @throws SAXException if the attributes are not valid.
     * @precondition attributes != null
     */
    @Nullable
    public Object createObject(final Attributes attributes)
      throws SAXException
    {
        return createObject(attributes, getDigester(), ConversionUtils.getInstance());
    }

    /**
     * Creates a Customer value object from given SAX attributes.
     * @param attributes the attributes.
     * @param digester the Digester instance.
     * @param conversionUtils the ConversionUtils instance.
     * @return the Customer information.
     * @throws SAXException if the attributes are not valid.
     * @precondition attributes != null
     * @precondition digester != null
     * @precondition conversionUtils != null
     */
    @Nullable
    protected abstract Object createObject(
        final Attributes attributes,
        final Digester digester,
        final ConversionUtils conversionUtils)
      throws SAXException;
}
