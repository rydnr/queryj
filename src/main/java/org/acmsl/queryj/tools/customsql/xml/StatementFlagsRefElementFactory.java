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
 * Filename: StatementFlagsRefElementFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create sql.xml <statement-flags-ref> element
 *              instances from their attributes, while being parsed by
 *              Digester.
 *
 */
package org.acmsl.queryj.tools.customsql.xml;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.customsql.StatementFlagsRefElement;
import org.acmsl.queryj.tools.customsql.xml.ElementFactory;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.ConversionUtils;

/*
 * Importing some additional classes.
 */
import org.apache.commons.digester.Digester;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Is able to create sql.xml &lt;statement-flags-ref&gt; element instances
 * from their attributes, while being parsed by Digester.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class StatementFlagsRefElementFactory
    extends  ElementFactory
{
    /**
     * Creates a StatementFlagsRefElementFactory instance.
     */
    public StatementFlagsRefElementFactory() {};

    /**
     * Creates a StatementFlagsRefElement instance from given SAX attributes.
     * @param attributes the attributes.
     * @param digester the Digester instance.
     * @param conversionUtils the ConversionUtils instance.
     * @return the &lt;statement-ref&gt; information.
     * @throws SAXException if the attributes are not valid.
     * @precondition attributes != null
     * @precondition digester != null
     * @precondition conversionUtils != null
     */
    @Nullable
    public Object createObject(
        @NotNull final Attributes attributes,
        final Digester digester,
        final ConversionUtils conversionUtils)
      throws SAXException
    {
        @Nullable StatementFlagsRefElement result = null;

        String t_strId = attributes.getValue("id");

        result = new StatementFlagsRefElement(t_strId);

        return result;
    }
}
