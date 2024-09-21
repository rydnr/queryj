/*
                        QueryJ Core

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
 * Filename: FillTemplateChain.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 5/30/13
 * Time: 6:29 PM
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Chain to provide placeholders.
 * @param <C> the QueryJTemplateContext.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/05/30
 */
public interface FillTemplateChain<C extends TemplateContext>
{
    /**
     * Retrieves the template context.
     * @return such information.
     */
    @NotNull
    C getTemplateContext();

    /**
     * Performs the required processing.
     * @param relevantOnly to include only the relevant ones: the ones that are necessary to
     * be able to find out if two template realizations are equivalent. Usually, generation timestamps,
     * documentation, etc. can be considered not relevant.
     * @return the placeholders.
     * @throws QueryJBuildException if the placeholders cannot be provided.
     */
    @NotNull
    QueryJCommand providePlaceholders(final boolean relevantOnly)
        throws QueryJBuildException;

    /**
     * Retrieves the handlers.
     * @return such handlers.
     */
    @NotNull
    List<?> getHandlers();
}
