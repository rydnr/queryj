//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create sql.xml <property> element instances from
 *              their attributes, while being parsed by Digester.
 *
 */
package org.acmsl.queryj.tools.customsql.xml;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.customsql.PropertyElement;
import org.acmsl.queryj.tools.customsql.xml.ElementFactory;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.ConversionUtils;

/*
 * Importing some additional classes.
 */
import org.apache.commons.digester.Digester;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Is able to create sql.xml &lt;property&gt; element instances from their
 * attributes, while being parsed by Digester.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class PropertyElementFactory
    extends  ElementFactory
{
    /**
     * Creates a PropertyElementFactory instance.
     */
    public PropertyElementFactory() {};

    /**
     * Creates a PropertyElement instance from given SAX attributes.
     * @param attributes the attributes.
     * @param digester the Digester instance.
     * @param conversionUtils the ConversionUtils instance.
     * @return the &lt;property&gt; information.
     * @throws SAXException if the attributes are not valid.
     * @precondition attributes != null
     * @precondition digester != null
     * @precondition conversionUtils != null
     */
    public Object createObject(
        final Attributes attributes,
        final Digester digester,
        final ConversionUtils conversionUtils)
      throws SAXException
    {
        PropertyElement result = null;

        String t_strId = attributes.getValue("id");

        String t_strColumnName =
            attributes.getValue("column_name");

        int t_iIndex =
            conversionUtils.toInt(attributes.getValue("index"));

        String t_strName = attributes.getValue("name");

        String t_strType = attributes.getValue("type");

        String t_strNullable = attributes.getValue("nullable");

        result =
            new PropertyElement(
                t_strId,
                t_strColumnName,
                t_iIndex,
                t_strName,
                t_strType,
                Boolean.TRUE.toString().equalsIgnoreCase(t_strNullable));

        return result;
    }
}
