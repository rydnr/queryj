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

/**
 * Is able to create sql.xml &lt;sql&gt; element instances from their
 * attributes, while being parsed by Digester.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class SqlElementFactory
    extends  ElementFactory
{
    /**
     * Creates a SqlElementFactory instance.
     */
    public SqlElementFactory() {};

    /**
     * Creates a SqlElement instance from given SAX attributes.
     * @param attributes the attributes.
     * @param digester the Digester instance.
     * @param conversionUtils the ConversionUtils instance.
     * @return the &lt;sql&gt; information.
     * @throws SAXException if the attributes are not valid.
     * @precondition attributes != null
     * @precondition digester != null
     * @precondition conversionUtils != null
     */
    @Nullable
    public Object createObject(
        @NotNull final Attributes attributes,
        final Digester digester,
        @NotNull final ConversionUtils conversionUtils)
      throws SAXException
    {
        @Nullable SqlElement result = null;

        String t_strId = attributes.getValue("id");

        String t_strDAO = attributes.getValue("dao");

        String t_strRepositoryScope = attributes.getValue("repositoryScope");

        String t_strName = attributes.getValue("name");

        String t_strType = attributes.getValue("type");

        String t_strImplementation = attributes.getValue("implementation");

        boolean t_bValidate = false;

        String t_strValidate = attributes.getValue("validate");

        if  (t_strValidate != null)
        {
            t_bValidate = conversionUtils.toBoolean(t_strValidate);
        }

        if  (t_strRepositoryScope == null)
        {
            result =
                new SqlElement(
                    t_strId,
                    t_strDAO,
                    t_strName,
                    t_strType,
                    t_strImplementation,
                    t_bValidate);
        }
        else
        {
            result =
                new SqlElement(
                    t_strId,
                    t_strName,
                    t_strType,
                    t_strImplementation,
                    t_bValidate,
                    t_strRepositoryScope);
        }

        return result;
    }
}
