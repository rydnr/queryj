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
 * Filename: PropertyElementFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create sql.xml <property> element instances from
 *              their attributes, while being parsed by Digester.
 *
 */
package org.acmsl.queryj.customsql.xml;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.customsql.PropertyElement;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.ConversionUtils;

/*
 * Importing some additional classes.
 */
import org.apache.commons.digester.Digester;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing SAX classes.
 */
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/*
 * Importing JDK classes.
 */
import java.util.Locale;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to create sql.xml &lt;property&gt; element instances from their
 * attributes, while being parsed by Digester.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class PropertyElementFactory
    extends  ElementFactory
{
    /**
     * Creates a PropertyElementFactory instance.
     */
    public PropertyElementFactory() {}

    /**
     * Creates a PropertyElement instance from given SAX attributes.
     * @param attributes the attributes.
     * @param digester the Digester instance.
     * @param conversionUtils the ConversionUtils instance.
     * @return the &lt;property&gt; information.
     * @throws SAXException if the attributes are not valid.
     */
    @Nullable
    public Object createObject(
        @NotNull final Attributes attributes,
        @NotNull final Digester digester,
        @NotNull final ConversionUtils conversionUtils)
      throws SAXException
    {
        @Nullable final PropertyElement<String> result;

        @Nullable final String t_strId = attributes.getValue("id");

        @Nullable final String t_strColumnName =
            attributes.getValue("column_name");

        final int t_iIndex =
            conversionUtils.toInt(attributes.getValue(Literals.INDEX));

        String t_strType = attributes.getValue("type");

        @Nullable final String t_strNullable = attributes.getValue("nullable");

        final boolean t_bNullable;

        if (t_strNullable != null)
        {
            t_bNullable = Boolean.TRUE.toString().equalsIgnoreCase(t_strNullable);
        }
        else
        {
            t_bNullable =
                (   (t_strType != null)
                 && (startsWithUpperCase(t_strType)));
        }

        if (t_strType == null)
        {
            t_strType = Literals.STRING;
        }

        result =
            new PropertyElement<>(
                t_strId,
                t_strColumnName,
                t_iIndex,
                t_strType,
                t_bNullable);

        return result;
    }

    /**
     * Checks whether given value starts with upper case.
     */
    protected boolean startsWithUpperCase(@NotNull final String type)
    {
        boolean result = false;

        if (type.length() > 0)
        {
            @NotNull final String t_strInitialCharacter = type.substring(0, 1);

            result =
                t_strInitialCharacter.equals(t_strInitialCharacter.toUpperCase(Locale.US));
        }

        return result;
    }
}
