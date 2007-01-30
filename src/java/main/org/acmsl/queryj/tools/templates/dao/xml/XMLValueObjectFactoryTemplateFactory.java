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

 ******************************************************************************
 *
 * Filename: XMLValueObjectFactoryTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents entities able to create XML value object factory
 *              templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.dao.xml.XMLValueObjectFactoryTemplate;

/**
 * Represents entities able to create XML value object templates.
 * @author <a href="mailto:chous@acm-sl.org"
 * >Jose San Leandro</a>
 * @version $Revision: 1659 $ at $Date: 2007-01-25 21:18:08 +0100 (Thu, 25 Jan 2007) $ by $Author: chous $
 */
public interface XMLValueObjectFactoryTemplateFactory
{
    /**
     * Generates a value object factory template.
     * @param packageName the package name.
     * @param valueObjectPackageName the value object package name.
     * @param tableTemplate the table template.
     * @param metadataManager the metadata manager.
     * @param header the header.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     */
    public XMLValueObjectFactoryTemplate createXMLValueObjectFactoryTemplate(
        final String packageName,
        final String valueObjectPackageName,
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String header)
      throws  QueryJException;
}
