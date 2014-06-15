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
 * Filename: CustomQueryChainTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for CustomQueryChain.
 *
 * Date: 2014/03/17
 * Time: 08:12
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

/*
 * Importing ACM SL Java Commons classes.
 */
import org.acmsl.commons.patterns.ArrayListChainAdapter;
import org.acmsl.commons.patterns.Chain;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JUnit classes.
 */
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link CustomQueryChain}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/17 08:12
 */
@RunWith(JUnit4.class)
public class CustomQueryChainTest
{
    /**
     * Checks whether it includes the required handlers.
     * throws QueryJBuildException
     */
    @SuppressWarnings("unchecked")
    @Test
    public void includes_required_handlers()
        throws QueryJBuildException
    {
        @NotNull final CustomQueryChain instance = new CustomQueryChain();

        @NotNull final Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> t_Chain =
            new ArrayListChainAdapter<>();

        instance.buildChain(t_Chain);

        Assert.assertTrue(contains(BindQueryParametersHandler.class, t_Chain));
        Assert.assertTrue(contains(CacheValidationOutcomeHandler.class, t_Chain));
        Assert.assertTrue(contains(CheckResultSetGettersWorkForDefinedPropertiesHandler.class, t_Chain));
        Assert.assertTrue(contains(ExecuteQueryHandler.class, t_Chain));
        Assert.assertTrue(contains(QueryValidationEnabledHandler.class, t_Chain));
        Assert.assertTrue(contains(ReportMissingPropertiesHandler.class, t_Chain));
        Assert.assertTrue(contains(ReportUnusedPropertiesHandler.class, t_Chain));
        Assert.assertTrue(contains(RetrieveResultPropertiesHandler.class, t_Chain));
        Assert.assertTrue(contains(RetrieveResultSetColumnsHandler.class, t_Chain));
        Assert.assertTrue(contains(SetupPreparedStatementHandler.class, t_Chain));
        Assert.assertTrue(contains(SkipValidationIfCacheExistsHandler.class, t_Chain));
    }

    /**
     * Checks whether given chain contains a handler class.
     * @param handlerClass the handler class.
     * @param chain the chain.
     * @return {@code true} in such case.
     */
    public boolean contains(
        @NotNull final Class<?> handlerClass,
        @NotNull final Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> chain)
    {
        boolean result = false;

        for (@Nullable final QueryJCommandHandler<QueryJCommand> t_Handler : chain.getHandlers())
        {
            if (   (t_Handler != null)
                   && (t_Handler.getClass().isAssignableFrom(handlerClass)))
            {
                result = true;
                break;
            }
        }

        return result;
    }

}
