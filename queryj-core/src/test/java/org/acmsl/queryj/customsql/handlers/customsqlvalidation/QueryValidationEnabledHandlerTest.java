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
 * Filename: QueryValidationEnabledHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for QueryValidationEnabledHandler.
 *
 * Date: 2014/03/15
 * Time: 07:00
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

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
 * Tests for {@link QueryValidationEnabledHandler}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/15 07:00
 */
@RunWith(JUnit4.class)
public class QueryValidationEnabledHandlerTest
{
    /**
     * Checks a query is not validated if its flag is disabled.
     * @throws QueryJBuildException
     */
    @Test
    public void stops_the_validation_process_for_the_current_query_if_validation_flag_is_disabled()
        throws QueryJBuildException
    {
        @NotNull final QueryValidationEnabledHandler instance = new QueryValidationEnabledHandler();

        @NotNull final QueryJCommand parameters =
            new ConfigurationQueryJCommandImpl(new SerializablePropertiesConfiguration());

        @NotNull final Sql<String> sql =
            new SqlElement<>(
                "id", "dao", "name", "String", Cardinality.SINGLE, "all", false /* validation */, false, "description");

        new QueryJCommandWrapper<Sql<String>>(parameters).setSetting(RetrieveQueryHandler.CURRENT_SQL, sql);

        Assert.assertTrue(instance.handle(parameters));
    }

    /**
     * Checks a query is validated if its flag is enabled.
     * @throws QueryJBuildException
     */
    @Test
    public void allows_the_validation_process_for_the_current_query_if_validation_flag_is_enabled()
        throws QueryJBuildException
    {
        @NotNull final QueryValidationEnabledHandler instance = new QueryValidationEnabledHandler();

        @NotNull final QueryJCommand parameters =
            new ConfigurationQueryJCommandImpl(new SerializablePropertiesConfiguration());

        @NotNull final Sql<String> sql =
            new SqlElement<>(
                "id", "dao", "name", "String", Cardinality.SINGLE, "all", true /* validation */, false, "description");

        new QueryJCommandWrapper<Sql<String>>(parameters).setSetting(RetrieveQueryHandler.CURRENT_SQL, sql);

        Assert.assertFalse(instance.handle(parameters));
    }
}
