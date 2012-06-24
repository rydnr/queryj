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
 * Filename: MetaDataBasedTableRepositoryBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a table repository using database metadata.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.templates.handlers.TableRepositoryTemplateBuildHandler;

/*
 * Importing some Ant classes.
 */

/**
 * Builds a table repository using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class MetaDataBasedTableRepositoryBuildHandler
    extends  TableRepositoryTemplateBuildHandler
{
    /**
     * Creates a <code>MetaDataBasedTableRepositoryBuildHandler</code>
     * instance.
     */
    public MetaDataBasedTableRepositoryBuildHandler() {};

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition command != null
     *
    public boolean handle(final Map parameters)
        throws  QueryJBuildException
    {
        storeTemplate(
            buildTableRepositoryTemplate(parameters), parameters);
        
        return false;
    }
    */
}
