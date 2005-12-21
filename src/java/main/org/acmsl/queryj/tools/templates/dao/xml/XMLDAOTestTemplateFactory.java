/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create XML DAO test templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTestTemplate;
import org.acmsl.queryj.tools.templates.TableTemplate;

/**
 * Is able to create XML DAO test templates.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public interface XMLDAOTestTemplateFactory
{
    /**
     * Generates a XML DAO test template.
     * @param tableTemplate the table template.
     * @param metadataManager the metadata manager.
     * @param packageName the package name.
     * @param daoPackageName the DAO's package name.
     * @param valueObjectPackageName the value object's package name.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     */
    public XMLDAOTestTemplate createXMLDAOTestTemplate(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String packageName,
        final String daoPackageName,
        final String valueObjectPackageName)
      throws  QueryJException;
}
