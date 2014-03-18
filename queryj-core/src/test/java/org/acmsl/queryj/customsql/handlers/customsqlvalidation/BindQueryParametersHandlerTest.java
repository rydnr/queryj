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
 * Filename: BindQueryParametersHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for BindQueryParametersHandler.
 *
 * Date: 2014/03/15
 * Time: 08:03
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.ParameterElement;
import org.acmsl.queryj.customsql.ParameterRefElement;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.Sql.Cardinality;
import org.acmsl.queryj.customsql.SqlElement;
import org.acmsl.queryj.customsql.handlers.CustomSqlProviderRetrievalHandler;
import org.acmsl.queryj.metadata.SqlParameterDAO;
import org.acmsl.queryj.metadata.TypeManager;
import org.acmsl.queryj.metadata.engines.JdbcTypeManager;
import org.acmsl.queryj.tools.handlers.JdbcConnectionOpeningHandler;

/*
 * Importing Apache Commons Configuration classes.
 */
import org.apache.commons.configuration.PropertiesConfiguration;

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
import org.powermock.api.easymock.PowerMock;
import org.easymock.EasyMock;

/*
 * Importing JDK classes.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Tests for {@link BindQueryParametersHandler}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/15 08:03
 */
@RunWith(JUnit4.class)
public class BindQueryParametersHandlerTest
{
    @Test
    public void binds_the_parameters_to_the_query()
        throws QueryJBuildException, SQLException
    {
        @NotNull final BindQueryParametersHandler instance = new BindQueryParametersHandler();

        @NotNull final QueryJCommand parameters = new ConfigurationQueryJCommandImpl(new PropertiesConfiguration());

        @NotNull final SqlElement<String> sql =
            new SqlElement<>(
                "id", "dao", "name", "select", Cardinality.SINGLE, "all", true /* validation */, false, "description");

        sql.setValue("select sysdate from dual where ? = 'A'");

        @NotNull final Parameter parameter = new ParameterElement("id", 1, "id", "String", "1");
        sql.add(new ParameterRefElement("id"));

        new QueryJCommandWrapper<Sql<String>>(parameters).setSetting(RetrieveQueryHandler.CURRENT_SQL, sql);

        @NotNull final CustomSqlProvider t_CustomSqlProvider = PowerMock.createNiceMock(CustomSqlProvider.class);
        @NotNull final SqlParameterDAO t_SqlParameterDAO = PowerMock.createNiceMock(SqlParameterDAO.class);
        @NotNull final Connection t_Connection = PowerMock.createNiceMock(Connection.class);
        @NotNull final PreparedStatement t_Statement = PowerMock.createNiceMock(PreparedStatement.class);

        EasyMock.expect(t_CustomSqlProvider.getSqlParameterDAO()).andReturn(t_SqlParameterDAO);
        EasyMock.expect(t_SqlParameterDAO.findByPrimaryKey("" + parameter.getName())).andReturn(parameter);
        EasyMock.expect(t_Connection.prepareStatement(sql.getValue())).andReturn(t_Statement);
        t_Statement.setString(1, "1");
        EasyMock.expectLastCall();

        new QueryJCommandWrapper<CustomSqlProvider>(parameters).setSetting(
            CustomSqlProviderRetrievalHandler.CUSTOM_SQL_PROVIDER, t_CustomSqlProvider);
        new QueryJCommandWrapper<Connection>(parameters).setSetting(
            JdbcConnectionOpeningHandler.JDBC_CONNECTION, t_Connection);

        EasyMock.replay(t_CustomSqlProvider);
        EasyMock.replay(t_SqlParameterDAO);
        EasyMock.replay(t_Connection);
        EasyMock.replay(t_Statement);

        new SetupPreparedStatementHandler().handle(parameters);

        Assert.assertFalse(instance.handle(parameters));

        EasyMock.verify(t_CustomSqlProvider);
        EasyMock.verify(t_SqlParameterDAO);
        EasyMock.verify(t_Connection);
        EasyMock.verify(t_Statement);
    }

    @SuppressWarnings("unchecked")
    public void testPrimitiveBody(@NotNull final Class<?> type)
        throws Exception
    {
        @NotNull final BindQueryParametersHandler t_Handler = new BindQueryParametersHandler();

        @NotNull final Parameter t_Parameter = new ParameterElement<>("id", 1, "id", type, "1");

        @NotNull final Sql<String> t_Sql =
            new SqlElement<>("id", "DAO", "name", "select", Cardinality.SINGLE, "oracle", true, false, "description");

        @NotNull final Class<?> t_ParameterType;

        @NotNull final TypeManager t_TypeManager = new JdbcTypeManager();

        if (t_TypeManager.isPrimitiveWrapper(type))
        {
            t_ParameterType = t_TypeManager.toPrimitive(type);
        }
        else
        {
            t_ParameterType = type;
        }

        Assert.assertNotNull(
            t_Handler.retrievePreparedStatementMethod(
                t_Parameter, 0, type, t_Sql, Arrays.asList(int.class, t_ParameterType)));
    }

    @Test
    public void retrieve_statement_setter_method_works_for_int_parameter()
        throws Exception
    {
        testPrimitiveBody(int.class);
    }

    @Test
    public void retrieve_statement_setter_method_works_for_Int_parameter()
        throws Exception
    {
        testPrimitiveBody(Integer.class);
    }

    @Test
    public void retrieve_statement_setter_method_works_for_long_parameter()
        throws Exception
    {
        testPrimitiveBody(long.class);
    }

    @Test
    public void retrieve_statement_setter_method_works_for_Long_parameter()
        throws Exception
    {
        testPrimitiveBody(Long.class);
    }

    @Test
    public void retrieve_statement_setter_method_works_for_double_parameter()
        throws Exception
    {
        testPrimitiveBody(double.class);
    }

    @Test
    public void retrieve_statement_setter_method_works_for_Double_parameter()
        throws Exception
    {
        testPrimitiveBody(Double.class);
    }

    @Test
    public void retrieve_statement_setter_method_works_for_float_parameter()
        throws Exception
    {
        testPrimitiveBody(float.class);
    }

    @Test
    public void retrieve_statement_setter_method_works_for_Float_parameter()
        throws Exception
    {
        testPrimitiveBody(Float.class);
    }

    @Test
    public void retrieve_statement_setter_method_works_for_boolean_parameter()
        throws Exception
    {
        testPrimitiveBody(boolean.class);
    }

    @Test
    public void retrieve_statement_setter_method_works_for_Boolean_parameter()
        throws Exception
    {
        testPrimitiveBody(Boolean.class);
    }
}
