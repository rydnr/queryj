//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds an Oracle-specific table repository.
 *
 */
package org.acmsl.queryj.tools.handlers.oracle;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.templates.handlers.TableRepositoryTemplateBuildHandler;

/**
 * Builds an Oracle-specific table repository.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class OracleTableRepositoryBuildHandler
    extends  TableRepositoryTemplateBuildHandler
{
    /**
     * Creates a <code>OracleTableRepositoryBuildHandler</code> instance.
     */
    public OracleTableRepositoryBuildHandler() {};

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     *
    public boolean handle(final Map parameters)
        throws  QueryJBuildException
    {
        storeTableRepositoryTemplate(
            buildTableRepositoryTemplate(parameters), parameters);
        
        return false;
    }
    */
}
