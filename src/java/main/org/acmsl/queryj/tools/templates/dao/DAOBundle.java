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
 * Description: Bundles the complete set of handlers related to DAO
 *              templates.
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
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.templates.dao.handlers.AttributesStatementSetterTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.dao.handlers.BaseDAOFactoryTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.dao.handlers.BaseDAOTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.dao.handlers.ConfigurationPropertiesTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.dao.handlers.DAOChooserTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.dao.handlers.DAOFactoryTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.dao.handlers.DAOTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.dao.handlers.DAOTestTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.dao.handlers.DataAccessManagerTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.dao.handlers.JdbcDAOTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.dao.handlers.QueryPreparedStatementCreatorTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.dao.handlers.ResultSetExtractorTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOBundle;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOBundle;
import org.acmsl.queryj.tools.templates.handlers.TemplateHandlerBundle;

/**
 * Bundles the complete set of handlers related to DAO templates.
 * @author <a href="mailto:jsanleandro@yahoo.es">Jose San Leandro</a>
 * @version $Revision$ at $Date$
 */
public class DAOBundle
    extends  TemplateHandlerBundle
{
    /**
     * Builds a bundle with DAO-related handlers.
     * @param includeMock whether to include Mock implementations.
     * @param includeXML whether to include XML implementations.
     */
    public DAOBundle(final boolean includeMock, final boolean includeXML)
    {
        super(
            new TemplateHandlerBundle[]
            {
                new AttributesStatementSetterTemplateHandlerBundle(),
                new BaseDAOFactoryTemplateHandlerBundle(),
                new BaseDAOTemplateHandlerBundle(),
                new ConfigurationPropertiesTemplateHandlerBundle(),
                new DAOChooserTemplateHandlerBundle(),
                new DAOFactoryTemplateHandlerBundle(),
                new DAOTemplateHandlerBundle(),
                new DAOTestTemplateHandlerBundle(),
                new DataAccessManagerTemplateHandlerBundle(),
                new JdbcDAOTemplateHandlerBundle(),
                new QueryPreparedStatementCreatorTemplateHandlerBundle(),
                new ResultSetExtractorTemplateHandlerBundle(),
                (includeMock ? new MockDAOBundle() : null),
                (includeXML ? new XMLDAOBundle() : null)
            });
    }
}
