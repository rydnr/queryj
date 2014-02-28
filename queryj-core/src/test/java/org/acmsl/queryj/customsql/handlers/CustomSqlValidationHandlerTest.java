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
 * Filename: CustomSqlValidationHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for CustomSqlValidationHandler.
 *
 * Date: 2014/02/20
 * Time: 20:41
 *
 */
package org.acmsl.queryj.customsql.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.ParameterElement;
import org.acmsl.queryj.customsql.ParameterRefElement;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.Sql.Cardinality;
import org.acmsl.queryj.customsql.SqlElement;
import org.acmsl.queryj.metadata.engines.JdbcTypeManager;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.SqlParameterDAO;
import org.acmsl.queryj.metadata.TypeManager;

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

/*
 * Importing EasyMock classes.
 */
import org.easymock.EasyMock;

/*
 * Importing PowerMock classes.
 */
import org.powermock.api.easymock.PowerMock;
import org.powermock.modules.junit4.PowerMockRunner;

/*
 * Importing JDK classes.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Date;

/**
 * Tests for {@link CustomSqlValidationHandler}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/02/20 20:41
 */
@SuppressWarnings("unused")
@RunWith(PowerMockRunner.class)
public class CustomSqlValidationHandlerTest
{
    @SuppressWarnings("unused, unchecked")
    @Test
    public void validate_works_for_date_parameters()
        throws Exception
    {
        @NotNull final String t_strSql = "select sysdate from dual where sysdate = ?";

        @NotNull final SqlElement<String> t_Sql =
            new SqlElement<>("id", "DAO", "name", "select", Cardinality.SINGLE, "oracle", true, false, "description");

        t_Sql.setValue(t_strSql);

        t_Sql.add(new ParameterRefElement("today"));

        @NotNull final Parameter t_Parameter =
            new ParameterElement<>("today", 0, "today", "Date", new Date());

        testBody(t_Sql, t_Parameter);
    }

    @SuppressWarnings("unchecked")
    protected void testBody(@NotNull final Sql<String> sql, @NotNull final Parameter parameter)
        throws Exception
    {
        @NotNull final CustomSqlProvider t_CustomSqlProvider = PowerMock.createNiceMock(CustomSqlProvider.class);
        @NotNull final SqlParameterDAO t_SqlParameterDAO = PowerMock.createNiceMock(SqlParameterDAO.class);
        @NotNull final MetadataManager t_MetadataManager = PowerMock.createNiceMock(MetadataManager.class);
        @NotNull final TypeManager t_TypeManager = new JdbcTypeManager();
        @NotNull final Connection t_Connection = PowerMock.createNiceMock(Connection.class);
        @NotNull final PreparedStatement t_Statement = PowerMock.createNiceMock(PreparedStatement.class);
        @NotNull final ResultSet t_ResultSet = PowerMock.createNiceMock(ResultSet.class);
        EasyMock.expect(t_CustomSqlProvider.getSqlParameterDAO()).andReturn(t_SqlParameterDAO);
        EasyMock.expect(t_Connection.getAutoCommit()).andReturn(true);
        EasyMock.expect(t_Connection.prepareStatement(sql.getValue())).andReturn(t_Statement);
        EasyMock.expect(t_SqlParameterDAO.findByPrimaryKey("" + parameter.getName())).andReturn(parameter);

        EasyMock.replay(t_CustomSqlProvider);
        EasyMock.replay(t_SqlParameterDAO);
        EasyMock.replay(t_MetadataManager);
        EasyMock.replay(t_Connection);
        EasyMock.replay(t_Statement);
        EasyMock.replay(t_ResultSet);

        try
        {
            new CustomSqlValidationHandler().validate(
                sql, t_CustomSqlProvider, t_Connection, t_MetadataManager, t_TypeManager);
        }
        catch (@NotNull final QueryJBuildException exception)
        {
            Assert.fail(exception.getMessage());
        }
        finally
        {
            EasyMock.verify(t_Connection);
            EasyMock.verify(t_Statement);
            EasyMock.verify(t_ResultSet);
            EasyMock.verify(t_CustomSqlProvider);
            EasyMock.verify(t_SqlParameterDAO);
            EasyMock.verify(t_MetadataManager);
        }
    }

    @Test
    public void validate_works_for_String_parameters()
        throws Exception
    {
        @NotNull final String t_strSql = "select 1 from dual where ? = 'blah'";

        @NotNull final SqlElement<String> t_Sql =
            new SqlElement<>("id", "DAO", "name", "select", Cardinality.SINGLE, "oracle", true, false, "description");

        t_Sql.setValue(t_strSql);

        t_Sql.add(new ParameterRefElement("text"));

        @NotNull final Parameter t_Parameter =
            new ParameterElement<>("text", 0, "text", "String", "blah");

        testBody(t_Sql, t_Parameter);
    }

    @Test
    public void validate_works_for_Long_parameters()
        throws Exception
    {
        @NotNull final String t_strSql = "select 1 from dual where ? = 1";

        @NotNull final SqlElement<String> t_Sql =
            new SqlElement<>("id", "DAO", "name", "select", Cardinality.SINGLE, "oracle", true, false, "description");

        t_Sql.setValue(t_strSql);

        t_Sql.add(new ParameterRefElement("userId"));

        @NotNull final Parameter t_Parameter =
            new ParameterElement<>("userId", 0, "userId", "Long", "1");

        testBody(t_Sql, t_Parameter);
    }

    @Test
    public void retrieve_statement_setter_method_works_for_Long_parameter()
        throws Exception
    {
        @NotNull final CustomSqlValidationHandler t_Handler = new CustomSqlValidationHandler();

        @NotNull final Parameter t_Parameter = new ParameterElement<>("id", 1, "id", Long.class, "1");

        @NotNull final Sql<String> t_Sql =
            new SqlElement<>("id", "DAO", "name", "select", Cardinality.SINGLE, "oracle", true, false, "description");

        Assert.assertNotNull(
            t_Handler.retrievePreparedStatementMethod(
                t_Parameter, 0, Long.class, t_Sql, Arrays.asList(new Class<?>[] { int.class, long.class })));
    }
}
