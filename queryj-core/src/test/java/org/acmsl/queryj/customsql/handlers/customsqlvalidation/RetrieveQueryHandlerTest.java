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
 * Filename: RetrieveQueryHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests RetrieveQueryHandler.
 *
 * Date: 2014/03/14
 * Time: 20:29
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
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.SerializablePropertiesConfiguration;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.Sql.Cardinality;
import org.acmsl.queryj.customsql.SqlElement;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Tests {@link RetrieveQueryHandler}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/14 20:29
 */
@RunWith(JUnit4.class)
public class RetrieveQueryHandlerTest
{
    /**
     * Checks the first query is retrieved correctly.
     * @throws QueryJBuildException
     */
    @Test
    public void retrieves_the_first_query()
        throws QueryJBuildException
    {
        @NotNull final CustomQueryChain chain =
            new CustomQueryChain()
            {
                /**
                 * {@inheritDoc}
                 */
                @Override
                protected Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> buildChain(
                    @NotNull final Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> chain)
                {
                    return chain;
                }
            };

        @NotNull final RetrieveQueryHandler instance = new RetrieveQueryHandler();

        @NotNull final QueryJCommand parameters =
            new ConfigurationQueryJCommandImpl(new SerializablePropertiesConfiguration());

        @NotNull final List<SqlElement<String>> list = new ArrayList<>(1);
        list.add(
            new SqlElement<>("id", "dao", "name", "String", Cardinality.SINGLE, "all", true, false, "description"));
        new QueryJCommandWrapper<List<SqlElement<String>>>(parameters).setSetting(RetrieveQueryHandler.SQL_LIST, list);


        Assert.assertEquals(0, instance.retrieveCurrentSqlIndex(parameters));
        Assert.assertFalse(instance.handle(parameters, chain));
    }

    /**
     * Checks the list of queries is retrieved correctly.
     * @throws QueryJBuildException
     */
    @Test
    public void retrieves_the_list_of_queries()
        throws QueryJBuildException
    {
        @NotNull final RetrieveQueryHandler instance = new RetrieveQueryHandler();

        @NotNull final QueryJCommand parameters =
            new ConfigurationQueryJCommandImpl(new SerializablePropertiesConfiguration());

        @NotNull final List<Sql<String>> list = new ArrayList<>(1);
        list.add(
            new SqlElement<>("id", "dao", "name", "String", Cardinality.SINGLE, "all", true, false, "description"));
        new QueryJCommandWrapper<List<Sql<String>>>(parameters).setSetting(RetrieveQueryHandler.SQL_LIST, list);

        Assert.assertEquals(list, instance.retrieveSqlList(parameters));
    }
}
