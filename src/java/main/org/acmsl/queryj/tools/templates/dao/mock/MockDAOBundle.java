/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
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
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendáriz
 *
 * Description: Bundles the complete set of handlers related to Mock DAO
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
package org.acmsl.queryj.tools.templates.dao.mock;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.templates.dao.mock.handlers.MockDAOTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.dao.mock.handlers.MockDAOFactoryTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.dao.mock.handlers.MockDAOTestTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.handlers.TemplateHandlerBundle;

/**
 * Bundles the complete set of handlers related to Mock DAO templates.
 * @author <a href="mailto:jsanleandro@yahoo.es">Jose San Leandro</a>
 * @version $Revision$ at $Date$
 */
public class MockDAOBundle
    extends  TemplateHandlerBundle
{
    /**
     * Builds a bundle with MockDAO-related handlers.
     */
    public MockDAOBundle()
    {
        super(
            new TemplateHandlerBundle[]
            {
                new MockDAOTemplateHandlerBundle(),
                new MockDAOFactoryTemplateHandlerBundle(),
                new MockDAOTestTemplateHandlerBundle()
            });
    }
}
