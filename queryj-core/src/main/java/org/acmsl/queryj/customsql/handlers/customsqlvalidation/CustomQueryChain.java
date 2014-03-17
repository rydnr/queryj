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
 * Filename: CustomQueryChain.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Runs a list of steps to validate a custom query.
 *
 * Date: 2014/03/17
 * Time: 08:15
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

/*
 * Importing ACM SL Java Commons classes.
 */
import org.acmsl.commons.patterns.Chain;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.AbstractQueryJChain;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Runs a list of steps to validate a custom {@link org.acmsl.queryj.customsql.Sql query}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/17 08:15
 */
@ThreadSafe
public class CustomQueryChain
    extends AbstractQueryJChain<QueryJCommand, QueryJCommandHandler<QueryJCommand>>
{
    /**
     * Builds the chain.
     * @param chain the chain to be configured.
     * @return the updated chain.
     * @throws org.acmsl.queryj.api.exceptions.QueryJBuildException if the chain cannot be built successfully.
     */
    @Override
    protected Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> buildChain(
        @NotNull final Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> chain)
        throws QueryJBuildException
    {
        chain.add(new QueryValidationEnabledHandler());

        chain.add(new SkipValidationIfCacheExistsHandler());

        chain.add(new RetrieveResultPropertiesHandler());

        chain.add(new SetupPreparedStatementHandler());

        chain.add(new BindQueryParametersHandler());

        chain.add(new ExecuteQueryHandler());

        chain.add(new RetrieveResultSetColumnsHandler());

        chain.add(new CheckResultSetGettersWorkForDefinedPropertiesHandler());

        chain.add(new ReportMissingPropertiesHandler());

        chain.add(new ReportUnusedPropertiesHandler());

        chain.add(new CacheValidationOutcomeHandler());

        return chain;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void cleanUpOnError(
        @NotNull final QueryJBuildException buildException, @NotNull final QueryJCommand command)
    {
    }
}
