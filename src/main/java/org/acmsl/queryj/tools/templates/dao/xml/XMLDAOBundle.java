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
 * Filename: XMLDAOBundle.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Bundles the complete set of handlers related to XML DAO
 *              templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.templates.dao.xml.handlers.XMLDAOTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.dao.xml.handlers.XMLValueObjectFactoryTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.dao.xml.handlers.XmlDAOFactoryTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.dao.xml.handlers.XMLDAOTestTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.handlers.TemplateHandlerBundle;

/**
 * Bundles the complete set of handlers related to XML DAO templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class XMLDAOBundle
    extends  TemplateHandlerBundle
{
    /**
     * Builds a bundle with XMLDAO-related handlers.
     */
    public XMLDAOBundle()
    {
        super(
            new TemplateHandlerBundle[]
            {
                new XMLDAOTemplateHandlerBundle(),
                new XMLValueObjectFactoryTemplateHandlerBundle(),
                new XmlDAOFactoryTemplateHandlerBundle(),
                new XMLDAOTestTemplateHandlerBundle()
            });
    }
}
