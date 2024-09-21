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
 * Filename: ExecuteQueryHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for ExecuteQueryHandler.
 *
 * Date: 2014/03/15
 * Time: 11:28
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
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.ParameterElement;
import org.acmsl.queryj.customsql.ParameterRefElement;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.SqlCardinality;
import org.acmsl.queryj.customsql.SqlElement;
import org.acmsl.queryj.customsql.handlers.CustomSqlProviderRetrievalHandler;
import org.acmsl.queryj.metadata.SqlParameterDAO;
import org.acmsl.queryj.tools.handlers.JdbcConnectionOpeningHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JUnit/EasyMock classes.
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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Tests for {@link ExecuteQueryHandler}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/15 11:28
 */
@RunWith(JUnit4.class)
public class ExecuteQueryHandlerTest
{
    /**
     * Tests whether a valid query executes correctly.
     * throws QueryJBuildException if the test fails.
     * throws SQLException if the test fails.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void a_valid_query_executes_correctly()
        throws QueryJBuildException, SQLException
    {
        @NotNull final ExecuteQueryHandler instance = new ExecuteQueryHandler();

        @NotNull final QueryJCommand parameters =
            new ConfigurationQueryJCommandImpl(new SerializablePropertiesConfiguration());

        @NotNull final SqlElement<String> sql =
            new SqlElement<>(
                "id", "dao", "name", "select", SqlCardinality.SINGLE, "all", true /* validation */, false, "description");

        sql.setValue("select sysdate from dual where ? = 'A'");

        @NotNull final Parameter parameter = new ParameterElement<>("id", 1, "id", "String", "1");
        sql.add(new ParameterRefElement("id"));

        new QueryJCommandWrapper<Sql<String>>(parameters).setSetting(RetrieveQueryHandler.CURRENT_SQL, sql);

        @NotNull final CustomSqlProvider t_CustomSqlProvider = PowerMock.createNiceMock(CustomSqlProvider.class);
        @NotNull final SqlParameterDAO t_SqlParameterDAO = PowerMock.createNiceMock(SqlParameterDAO.class);
        @NotNull final Connection t_Connection = PowerMock.createNiceMock(Connection.class);
        @NotNull final PreparedStatement t_Statement = PowerMock.createNiceMock(PreparedStatement.class);
        @NotNull final ResultSet t_ResultSet = PowerMock.createNiceMock(ResultSet.class);

        EasyMock.expect(t_CustomSqlProvider.getSqlParameterDAO()).andReturn(t_SqlParameterDAO);
        EasyMock.expect(t_SqlParameterDAO.findByPrimaryKey("" + parameter.getName())).andReturn(parameter);
        EasyMock.expect(t_Connection.prepareStatement(sql.getValue())).andReturn(t_Statement);
        t_Statement.setString(1, "1");
        EasyMock.expectLastCall();
        EasyMock.expect(t_Statement.executeQuery()).andReturn(t_ResultSet);

        new QueryJCommandWrapper<CustomSqlProvider>(parameters).setSetting(
            CustomSqlProviderRetrievalHandler.CUSTOM_SQL_PROVIDER, t_CustomSqlProvider);
        new QueryJCommandWrapper<Connection>(parameters).setSetting(
            JdbcConnectionOpeningHandler.JDBC_CONNECTION, t_Connection);

        EasyMock.replay(t_CustomSqlProvider);
        EasyMock.replay(t_SqlParameterDAO);
        EasyMock.replay(t_Connection);
        EasyMock.replay(t_Statement);
        EasyMock.replay(t_ResultSet);

        new SetupPreparedStatementHandler().handle(parameters);
        new BindQueryParametersHandler().handle(parameters);

        Assert.assertFalse(instance.handle(parameters));

        EasyMock.verify(t_CustomSqlProvider);
        EasyMock.verify(t_SqlParameterDAO);
        EasyMock.verify(t_Connection);
        EasyMock.verify(t_Statement);
        EasyMock.verify(t_ResultSet);
    }
}
