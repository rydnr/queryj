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
 * Filename: SqlElementFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create sql.xml <sql> element instances from their
 *              attributes, while being parsed by Digester.
 *
 */
package org.acmsl.queryj.customsql.xml;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.SqlElement;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.ConversionUtils;

/*
 * Importing some Digester classes.
 */
import org.apache.commons.digester.Digester;

/*
 * Importing some SAX classes.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to create sql.xml &lt;sql&gt; element instances from their
 * attributes, while being parsed by Digester.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class SqlElementFactory
    extends  ElementFactory
{
    /**
     * Creates a SqlElementFactory instance.
     */
    public SqlElementFactory() {}

    /**
     * Creates a SqlElement instance from given SAX attributes.
     * @param attributes the attributes.
     * @param digester the Digester instance.
     * @param conversionUtils the ConversionUtils instance.
     * @return the &lt;sql&gt; information.
     * @throws SAXException if the attributes are not valid.
     */
    @Nullable
    public Object createObject(
        @NotNull final Attributes attributes,
        @NotNull final Digester digester,
        @NotNull final ConversionUtils conversionUtils)
      throws SAXException
    {
        @Nullable final SqlElement<String> result;

        @NotNull final String t_strId = attributes.getValue("id");

        @Nullable final String t_strDAO = attributes.getValue("dao");

        @Nullable final String t_strRepositoryScope = attributes.getValue("repositoryScope");

        @NotNull final String t_strName = attributes.getValue("name");

        @NotNull final String t_strType = attributes.getValue("type");

        @NotNull final String t_strImplementation = attributes.getValue("implementation");

        boolean t_bValidate = false;

        @Nullable final String t_strValidate = attributes.getValue("validate");

        if  (t_strValidate != null)
        {
            t_bValidate = conversionUtils.toBoolean(t_strValidate);
        }

        boolean t_bDynamic = false;

        @Nullable final String t_strDynamic = attributes.getValue("dynamic");

        if  (t_strDynamic != null)
        {
            t_bDynamic = conversionUtils.toBoolean(t_strDynamic);
        }

        if  (t_strRepositoryScope == null)
        {
            result =
                new SqlElement<>(
                    t_strId,
                    t_strDAO,
                    t_strName,
                    t_strType,
                    t_strImplementation,
                    t_bValidate,
                    t_bDynamic,
                    "");
        }
        else
        {
            result =
                new SqlElement<>(
                    t_strId,
                    t_strName,
                    t_strType,
                    t_strImplementation,
                    t_bValidate,
                    t_bDynamic,
                    t_strRepositoryScope,
                    "");
        }

        return result;
    }
}
