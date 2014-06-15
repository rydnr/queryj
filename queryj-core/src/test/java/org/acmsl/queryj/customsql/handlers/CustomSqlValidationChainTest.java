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
 * Filename: CustomSqlValidationChainTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for CustomSqlValidationChain.
 *
 * Date: 2014/03/16
 * Time: 18:26
 *
 */
package org.acmsl.queryj.customsql.handlers;

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
import org.acmsl.queryj.customsql.handlers.customsqlvalidation.CustomQueryChainTest;
import org.acmsl.queryj.customsql.handlers.customsqlvalidation.GlobalValidationEnabledHandler;
import org.acmsl.queryj.customsql.handlers.customsqlvalidation.RetrieveQueryHandler;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JUnit classes.
 */
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link CustomSqlValidationChain}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/16 18:26
 */
@RunWith(JUnit4.class)
public class CustomSqlValidationChainTest
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
        @NotNull final CustomSqlValidationChain instance = new CustomSqlValidationChain();

        @NotNull final Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> t_Chain =
            new ArrayListChainAdapter<>();

        instance.buildChain(t_Chain);

        Assert.assertTrue(contains(RetrieveQueryHandler.class, t_Chain));
        Assert.assertTrue(contains(GlobalValidationEnabledHandler.class, t_Chain));
    }

    /**
     * Checks whether given chain contains the handler class.
     * @param handlerClass the handler class.
     * @param chain the chain.
     */
    protected boolean contains(
        @NotNull final Class<?> handlerClass,
        @NotNull final Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> chain)
    {
        return new CustomQueryChainTest().contains(handlerClass, chain);
    }
}
