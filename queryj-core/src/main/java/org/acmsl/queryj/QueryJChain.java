//;-*- mode: java -*-
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
 * Filename: QueryJChain.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents sequentials chain of actions within QueryJ.
 *
 */
package org.acmsl.queryj;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Chain;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents sequentials chain of actions within QueryJ.
 * @param <C> the command type.
 * @param <CH> che command handler type.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface QueryJChain
    <C extends QueryJCommand, CH extends QueryJCommandHandler<C>>
{
    /**
     * Requests the chained logic to be performed.
     * @param settings the command.
     * @throws QueryJBuildException if the process fails.
    */
    public void process(@NotNull final C settings)
        throws QueryJBuildException;

    /**
     * Retrieves the link of the chain just after the one given command
     * handler takes.
     * @param chain the concrete chain.
     * @param commandHandler the handler just before the desired link.
     * @return the next handler in the chain.
     */
    @Nullable
    public CH getNextChainLink(
        @Nullable final Chain<C, QueryJBuildException, CH> chain,
        @Nullable final CH commandHandler);

    /**
     * Retrieves the link of the chain just before the one given command
     * handler takes.
     * @param chain the concrete chain.
     * @param commandHandler the handler just after the desired link.
     * @return the previous handler in the chain.
     */
    @Nullable
    public CH getPreviousChainLink(
        @Nullable final Chain<C, QueryJBuildException, CH> chain,
        @Nullable final CH commandHandler);
}
