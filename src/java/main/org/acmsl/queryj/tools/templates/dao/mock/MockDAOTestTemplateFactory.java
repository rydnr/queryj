/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
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
 * Description: Is able to create Mock DAO test templates.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package org.acmsl.queryj.tools.templates.dao.mock;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOTestTemplate;
import org.acmsl.queryj.tools.templates.TableTemplate;

/**
 * Is able to create DAO test templates.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public interface MockDAOTestTemplateFactory
{
    /**
     * Generates a Mock DAO test template.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param daoPackageName the DAO's package name.
     * @param valueObjectPackageName the value object's package name.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     */
    public MockDAOTestTemplate createMockDAOTestTemplate(
            TableTemplate           tableTemplate,
            DatabaseMetaDataManager metaDataManager,
            String                  packageName,
            String                  daoPackageName,
            String                  valueObjectPackageName)
        throws  QueryJException;
}