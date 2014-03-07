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
 * Filename: StatementFlagsElementFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create sql.xml <statement-flags> element instances
 *              from their attributes, while being parsed by Digester.
 *
 */
package org.acmsl.queryj.customsql.xml;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.StatementFlagsElement;

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

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to create sql.xml &lt;statement-flags&gt; element instances
 * from their attributes, while being parsed by Digester.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class StatementFlagsElementFactory
    extends  ElementFactory
{
    /**
     * Creates a StatementFlagsElementFactory instance.
     */
    public StatementFlagsElementFactory() {}

    /**
     * Creates a StatementFlagsElement instance from given SAX
     * attributes.
     * @param attributes the attributes.
     * @param digester the Digester instance.
     * @param conversionUtils the ConversionUtils instance.
     * @return the &lt;sql&gt; information.
     * @throws SAXException if the attributes are not valid.
     */
    @Nullable
    @Override
    public Object createObject(
        @NotNull final Attributes attributes,
        @NotNull final Digester digester,
        @NotNull final ConversionUtils conversionUtils)
      throws SAXException
    {
        @Nullable final StatementFlagsElement result;

        @Nullable final String t_strId = attributes.getValue("id");

        @Nullable final String t_strAutogeneratedKeys =
            attributes.getValue("autogeneratedkeys");

        @Nullable Integer t_iFetchSize = null;

        @Nullable final String t_strFetchSize =
            attributes.getValue("fetchsize");

        if  (t_strFetchSize != null)
        {
            t_iFetchSize =
                conversionUtils.toInt(t_strFetchSize);
        }

        @Nullable Integer t_iMaxFieldSize = null;

        @Nullable final String t_strMaxFieldSize =
            attributes.getValue("maxfieldsize");

        if  (t_strMaxFieldSize != null)
        {
            t_iMaxFieldSize =
                conversionUtils.toInt(t_strMaxFieldSize);
        }

        @Nullable Integer t_iMaxRows = null;

        @Nullable final String t_strMaxRows =
            attributes.getValue("maxrows");

        if  (t_strMaxRows != null)
        {
            t_iMaxRows =
                conversionUtils.toInt(t_strMaxRows);
        }

        @Nullable Integer t_iQueryTimeout = null;

        @Nullable final String t_strQueryTimeout =
            attributes.getValue("querytimeout");

        if  (t_strQueryTimeout != null)
        {
            t_iQueryTimeout =
                conversionUtils.toInt(t_strQueryTimeout);
        }

        @Nullable final String t_strFetchDirection =
            attributes.getValue("fetchdirection");

        @Nullable Boolean t_bEscapeProcessing = null;

        @Nullable final String t_strEscapeProcessing =
            attributes.getValue("escapeprocessing");

        if  (t_strEscapeProcessing != null)
        {
            t_bEscapeProcessing =
                conversionUtils.toBoolean(t_strEscapeProcessing)
                ?  Boolean.TRUE
                :  Boolean.FALSE;
        }

        @Nullable final String t_strMoreResults =
            attributes.getValue("moreresults");

        @Nullable final String t_strCursorName =
            attributes.getValue("cursorname");

        if (t_strId != null)
        {
            result =
                new StatementFlagsElement(
                    t_strId,
                    t_strAutogeneratedKeys,
                    t_iFetchSize,
                    t_iMaxFieldSize,
                    t_iMaxRows,
                    t_iQueryTimeout,
                    t_strFetchDirection,
                    t_bEscapeProcessing,
                    t_strMoreResults,
                    t_strCursorName);
        }
        else
        {
            result = null;
        }

        return result;
    }
}
