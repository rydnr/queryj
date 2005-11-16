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
 * Description: Builds a base DAO template using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.templates.dao.BaseDAOTemplateGenerator;
import org.acmsl.queryj.tools.templates.dao.DAOTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.handlers.DAOTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Builds a base DAO template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class BaseDAOTemplateBuildHandler
    extends  DAOTemplateBuildHandler
{
    /**
     * Creates a BaseDAOTemplateBuildHandler.
     */
    public BaseDAOTemplateBuildHandler() {};

    /**
     * Retrieves the DAO template factory.
     * @return such instance.
     */
    protected DAOTemplateFactory retrieveDAOTemplateFactory()
    {
        return BaseDAOTemplateGenerator.getInstance();
    }

    /**
     * Stores the base DAO template collection in given attribute map.
     * @param baseDAOTemplates the base DAO templates.
     * @param parameters the parameter map.
     * @throws BuildException if the templates cannot be stored for any reason.
     * @precondition baseDAOTemplates != null
     * @precondition parameters != null
     */
    protected void storeDAOTemplates(
        final DAOTemplate[] baseDAOTemplates, final Map parameters)
      throws  BuildException
    {
        parameters.put(
            TemplateMappingManager.BASE_DAO_TEMPLATES, baseDAOTemplates);
    }
}
