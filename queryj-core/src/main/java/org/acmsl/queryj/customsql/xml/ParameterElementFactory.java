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
 * Filename: ParameterElementFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create sql.xml <parameter> element instances from
 *              their attributes, while being parsed by Digester.
 *
 */
package org.acmsl.queryj.customsql.xml;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.customsql.ParameterElement;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.ConversionUtils;

/*
 * Importing Digester classes.
 */
import org.apache.commons.digester.Digester;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing SAX classes.
 */
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Is able to create sql.xml &lt;parameter&gt; element instances from their
 * attributes, while being parsed by Digester.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class ParameterElementFactory
    extends  ElementFactory
{
    private static final String DATE_FORMAT_ES = "DD/MM/yyyy";
    private static final String DATE_FORMAT_EN = "yyyy/DD/MM";

    /**
     * Creates a ParameterElementFactory instance.
     */
    public ParameterElementFactory() {};

    /**
     * Creates a ParameterElement instance from given SAX attributes.
     * @param attributes the attributes.
     * @param digester the Digester instance.
     * @param conversionUtils the ConversionUtils instance.
     * @return the &lt;parameter&gt; information.
     * @throws SAXException if the attributes are not valid.
     */
    @Override
    @Nullable
    public Object createObject(
        @NotNull final Attributes attributes,
        @NotNull final Digester digester,
        @NotNull final ConversionUtils conversionUtils)
      throws SAXException
    {
        @Nullable ParameterElement<String, ?> result = null;

        @Nullable final String t_strId = attributes.getValue("id");

        final int t_iIndex =
            conversionUtils.toInt(attributes.getValue(Literals.INDEX));

        @Nullable final String t_strName = attributes.getValue("name");

        @Nullable final String t_strType = attributes.getValue("type");

        @Nullable final String t_strValidationValue =
            attributes.getValue("validation-value");

        if (   (t_strId != null)
            && (t_strName != null)
            && (t_strType != null))
        {
            if (t_strType.equals("Date"))
            {
                @Nullable Date date = null;

                try
                {
                    date = new SimpleDateFormat(DATE_FORMAT_ES).parse(t_strValidationValue);
                }
                catch (@NotNull final ParseException invalidDate)
                {
                    try
                    {
                        date = new SimpleDateFormat(DATE_FORMAT_EN).parse(t_strValidationValue);
                    }
                    catch (@NotNull final ParseException invalidEnglishDate)
                    {
                        result = null;
                    }
                }
                if (date != null)
                {
                    result =
                        new ParameterElement<>(
                            t_strId,
                            t_iIndex,
                            t_strName,
                            t_strType,
                            date);
                }
            }
            else
            {
                result =
                    new ParameterElement<>(
                        t_strId,
                        t_iIndex,
                        t_strName,
                        t_strType,
                        t_strValidationValue);
            }
        }
        else
        {
            result = null;
        }

        return result;
    }
}
