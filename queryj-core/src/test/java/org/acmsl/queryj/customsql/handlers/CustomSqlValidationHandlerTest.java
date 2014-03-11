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
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.CustomSqlProviderTest.SemiMockedAbstractCustomSqlProvider;
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.ParameterElement;
import org.acmsl.queryj.customsql.ParameterRefElement;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyElement;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultElement;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.Sql.Cardinality;
import org.acmsl.queryj.customsql.SqlConnectionFlagsDAO;
import org.acmsl.queryj.customsql.SqlElement;
import org.acmsl.queryj.customsql.SqlResultSetFlagsDAO;
import org.acmsl.queryj.customsql.SqlStatementFlagsDAO;
import org.acmsl.queryj.metadata.SqlDAO;
import org.acmsl.queryj.metadata.SqlPropertyDAO;
import org.acmsl.queryj.metadata.SqlResultDAO;
import org.acmsl.queryj.metadata.engines.JdbcTypeManager;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.SqlParameterDAO;
import org.acmsl.queryj.metadata.TypeManager;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;

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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
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
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    /**
     * A temporary folder for testing hash caches.
     */
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

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

    /**
     * Checks if validation works for date parameters.
     * @throws Exception
     */
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
    public void validate_works_for_int_parameters()
        throws Exception
    {
        @NotNull final String t_strSql = "select 1 from dual where ? = 1";

        @NotNull final SqlElement<String> t_Sql =
            new SqlElement<>("id", "DAO", "name", "select", Cardinality.SINGLE, "oracle", true, false, "description");

        t_Sql.setValue(t_strSql);

        t_Sql.add(new ParameterRefElement("userId"));

        @NotNull final Parameter t_Parameter =
            new ParameterElement<>("userId", 0, "userId", "int", "1");

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
    public void validate_works_for_double_parameters()
        throws Exception
    {
        @NotNull final String t_strSql = "select 1 from dual where ? = 1";

        @NotNull final SqlElement<String> t_Sql =
            new SqlElement<>("id", "DAO", "name", "select", Cardinality.SINGLE, "oracle", true, false, "description");

        t_Sql.setValue(t_strSql);

        t_Sql.add(new ParameterRefElement("userId"));

        @NotNull final Parameter t_Parameter =
            new ParameterElement<>("userId", 0, "userId", "double", "1");

        testBody(t_Sql, t_Parameter);
    }

    @SuppressWarnings("unchecked")
    public void testPrimitiveBody(@NotNull final Class<?> type)
        throws Exception
    {
        @NotNull final CustomSqlValidationHandler t_Handler = new CustomSqlValidationHandler();

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

    @Test
    public void validation_skipped_if_hash_is_cached()
        throws IOException
    {
        @NotNull final Sql<String> t_Sql =
            new SqlElement<>("sql-id", "dao", "sql-name", "select", Cardinality.SINGLE, "all", true, false, "fake sql");
        @NotNull final Result<String> t_Result1 = new ResultElement<>("p1", "class1");

        @NotNull final MetadataManager t_MetadataManager = PowerMock.createNiceMock(MetadataManager.class);
        @NotNull final SqlDAO sqlDAO = EasyMock.createNiceMock(SqlDAO.class);
        @NotNull final SqlParameterDAO parameterDAO = EasyMock.createNiceMock(SqlParameterDAO.class);
        @NotNull final SqlPropertyDAO propertyDAO = EasyMock.createNiceMock(SqlPropertyDAO.class);
        @NotNull final SqlResultDAO resultDAO = EasyMock.createNiceMock(SqlResultDAO.class);
        @NotNull final SqlConnectionFlagsDAO connectionFlagsDAO = EasyMock.createNiceMock(SqlConnectionFlagsDAO.class);
        @NotNull final SqlStatementFlagsDAO statementFlagsDAO = EasyMock.createNiceMock(SqlStatementFlagsDAO.class);
        @NotNull final SqlResultSetFlagsDAO resultSetFlagsDAO = EasyMock.createNiceMock(SqlResultSetFlagsDAO.class);
        @NotNull final List<Sql<String>> sqlList = new ArrayList<>(1);
        sqlList.add(t_Sql);

        @NotNull final CustomSqlProvider t_CustomSqlProvider =
            new SemiMockedAbstractCustomSqlProvider(
                sqlDAO,
                parameterDAO,
                propertyDAO,
                resultDAO,
                connectionFlagsDAO,
                statementFlagsDAO,
                resultSetFlagsDAO);

        @NotNull final QueryJCommand t_Command = new ConfigurationQueryJCommandImpl(new PropertiesConfiguration());
        new QueryJCommandWrapper<File>(t_Command).setSetting(
            CustomSqlCacheWritingHandler.CUSTOM_SQL_OUTPUT_FOLDER_FOR_HASHES, tempFolder.getRoot());
        new QueryJCommandWrapper<MetadataManager>(t_Command)
            .setSetting(DatabaseMetaDataRetrievalHandler.METADATA_MANAGER, t_MetadataManager);
        new QueryJCommandWrapper<CustomSqlProvider>(t_Command).setSetting(
            CustomSqlProviderRetrievalHandler.CUSTOM_SQL_PROVIDER, t_CustomSqlProvider);

        EasyMock.expect(sqlDAO.findAll()).andReturn(sqlList).anyTimes();
        EasyMock.replay(sqlDAO);
        EasyMock.expect(resultDAO.findBySqlId("sql-id")).andReturn(t_Result1).anyTimes();
        EasyMock.replay(resultDAO);

        @NotNull final Charset t_Charset = Charset.defaultCharset();

        @NotNull final String hash = t_CustomSqlProvider.getHash(t_Sql, t_Charset.displayName());

        FileUtils.getInstance().writeFile(tempFolder.getRoot() + File.separator + hash, "", Charset.defaultCharset());

        Assert.assertTrue(new File(tempFolder.getRoot() + File.separator + hash).exists());

        @NotNull final CustomSqlValidationHandler instance =
            new CustomSqlValidationHandler()
            {
                /**
                 * {@inheritDoc}
                 */
                @Override
                public void validate(
                    @NotNull final Sql<String> sql,
                    @NotNull final CustomSqlProvider customSqlProvider,
                    @NotNull final Connection connection,
                    @NotNull final MetadataManager metadataManager,
                    @NotNull final TypeManager typeManager)
                {
                    Assert.fail("Hash cache doesn't prevent validation");
                }

                /**
                 * {@inheritDoc}
                 */
                @NotNull
                @Override
                protected Connection retrieveConnection(@NotNull final QueryJCommand parameters)
                {
                    return PowerMock.createNiceMock(Connection.class);
                }
            };

        try
        {
            instance.handle(t_Command);

        }
        catch (@NotNull final QueryJBuildException exception)
        {
            Assert.fail(exception.getMessage());
        }
        finally
        {
            EasyMock.verify(sqlDAO);
        }
    }

    @Test
    public void property_type_check_works_for_Strings()
    {
        @NotNull final CustomSqlValidationHandler instance = new CustomSqlValidationHandler();

        final int type = Types.VARCHAR;

        @NotNull final Property<String> t_Property =
            new PropertyElement<>("id", "columnName", 1, String.class.getSimpleName(), false);

        Assert.assertTrue(instance.matchesType(type, t_Property, new JdbcTypeManager()));
    }

    @Test
    public void property_type_check_works_for_longs()
    {
        @NotNull final CustomSqlValidationHandler instance = new CustomSqlValidationHandler();

        final int bigInt = Types.BIGINT;
        final int numeric = Types.NUMERIC;

        @NotNull final Property<String> t_Property =
            new PropertyElement<>("id", "columnName", 1, long.class.getSimpleName(), false);

        Assert.assertTrue(instance.matchesType(bigInt, t_Property, new JdbcTypeManager()));
        Assert.assertTrue(instance.matchesType(numeric, t_Property, new JdbcTypeManager()));
    }

    @Test
    public void property_index_check_works()
    {
        @NotNull final CustomSqlValidationHandler instance = new CustomSqlValidationHandler();

        Assert.assertTrue(instance.matchesPosition(1, 1));
        Assert.assertFalse(instance.matchesPosition(1, 3));
    }
}
