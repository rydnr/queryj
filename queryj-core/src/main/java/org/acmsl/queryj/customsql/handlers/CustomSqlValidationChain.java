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
 * Filename: CustomSqlValidationChain.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds and processes the chain to validate the custom Sql.
 *
 * Date: 2014/03/16
 * Time: 18:31
 *
 */
package org.acmsl.queryj.customsql.handlers;

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
import org.acmsl.queryj.customsql.handlers.customsqlvalidation.BindQueryParametersHandler;
import org.acmsl.queryj.customsql.handlers.customsqlvalidation.CacheValidationOutcomeHandler;
import org.acmsl.queryj.customsql.handlers.customsqlvalidation.CheckResultSetGettersWorkForDefinedPropertiesHandler;
import org.acmsl.queryj.customsql.handlers.customsqlvalidation.ExecuteQueryHandler;
import org.acmsl.queryj.customsql.handlers.customsqlvalidation.GlobalValidationEnabledHandler;
import org.acmsl.queryj.customsql.handlers.customsqlvalidation.QueryValidationEnabledHandler;
import org.acmsl.queryj.customsql.handlers.customsqlvalidation.ReportMissingPropertiesHandler;
import org.acmsl.queryj.customsql.handlers.customsqlvalidation.ReportUnusedPropertiesHandler;
import org.acmsl.queryj.customsql.handlers.customsqlvalidation.RetrieveQueryHandler;
import org.acmsl.queryj.customsql.handlers.customsqlvalidation.RetrieveResultPropertiesHandler;
import org.acmsl.queryj.customsql.handlers.customsqlvalidation.RetrieveResultSetColumnsHandler;
import org.acmsl.queryj.customsql.handlers.customsqlvalidation.SetupPreparedStatementHandler;
import org.acmsl.queryj.customsql.handlers.customsqlvalidation.SkipValidationIfCacheExistsHandler;
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
 * Builds and processes the chain to validate the custom {@link org.acmsl.queryj.customsql.Sql}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/16 18:31
 */
@ThreadSafe
public class CustomSqlValidationChain
    extends AbstractQueryJChain<QueryJCommand, QueryJCommandHandler<QueryJCommand>>
{
    /**
     * Builds the chain.
     * @param chain the chain to be configured.
     * @return the updated chain.
     * @throws QueryJBuildException if the chain cannot be built successfully.
     */
    @Override
    protected Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> buildChain(
        @NotNull final Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> chain)
        throws QueryJBuildException
    {
        chain.add(new GlobalValidationEnabledHandler());

        chain.add(new RetrieveQueryHandler());

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
