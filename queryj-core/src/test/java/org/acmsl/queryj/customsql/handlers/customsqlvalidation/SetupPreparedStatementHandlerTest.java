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
 * Filename: SetupPreparedStatementHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for SetupPreparedStatementHandler.
 *
 * Date: 2014/03/15
 * Time: 09:56
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
import org.acmsl.queryj.tools.handlers.JdbcConnectionOpeningHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JUnit classes.
 */
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.powermock.api.easymock.PowerMock;

/*
 * Importing JDK classes.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Tests for {@link SetupPreparedStatementHandler}
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/15 09:56
 */
@RunWith(JUnit4.class)
public class SetupPreparedStatementHandlerTest
{
    /**
     * Checks it injects a prepared statement into the command.
     * Throws QueryJBuildException
     * Throws SQLException
     */
    @Test
    public void injects_a_prepared_statement_into_the_command()
        throws QueryJBuildException, SQLException
    {
        @NotNull final SetupPreparedStatementHandler instance = new SetupPreparedStatementHandler();

        @NotNull final QueryJCommand parameters =
            new ConfigurationQueryJCommandImpl(new SerializablePropertiesConfiguration());

        @NotNull final SqlElement<String> sql =
            new SqlElement<>(
                "id", "dao", "name", "select", Cardinality.SINGLE, "all", true /* validation */, false, "description");

        sql.setValue("select sysdate from dual where ? = 'A'");

        new QueryJCommandWrapper<Sql<String>>(parameters).setSetting(RetrieveQueryHandler.CURRENT_SQL, sql);

        @NotNull final Connection t_Connection = PowerMock.createNiceMock(Connection.class);
        @NotNull final PreparedStatement t_Statement = PowerMock.createNiceMock(PreparedStatement.class);

        EasyMock.expect(t_Connection.prepareStatement(sql.getValue())).andReturn(t_Statement);

        new QueryJCommandWrapper<Connection>(parameters).setSetting(
            JdbcConnectionOpeningHandler.JDBC_CONNECTION, t_Connection);

        EasyMock.replay(t_Connection);

        Assert.assertFalse(instance.handle(parameters));

        @Nullable final PreparedStatement t_ActualStatement =
            new QueryJCommandWrapper<PreparedStatement>(parameters).getSetting(
                SetupPreparedStatementHandler.CURRENT_PREPARED_STATEMENT);

        Assert.assertNotNull(t_ActualStatement);
        Assert.assertEquals(t_Statement, t_ActualStatement);
        Assert.assertEquals(t_Statement, instance.retrieveCurrentPreparedStatement(parameters));

        EasyMock.verify(t_Connection);
    }
}
