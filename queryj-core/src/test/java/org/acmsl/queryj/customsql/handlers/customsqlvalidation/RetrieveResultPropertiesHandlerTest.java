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
 * Filename: RetrieveResultPropertiesHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for RetrieveResultPropertiesHandler.
 *
 * Date: 2014/03/15
 * Time: 16:34
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
import org.acmsl.queryj.customsql.ParameterRefElement;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyElement;
import org.acmsl.queryj.customsql.PropertyRefElement;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultElement;
import org.acmsl.queryj.customsql.ResultRefElement;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.SqlCardinality;
import org.acmsl.queryj.customsql.SqlElement;
import org.acmsl.queryj.customsql.handlers.CustomSqlProviderRetrievalHandler;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.SqlPropertyDAO;
import org.acmsl.queryj.metadata.SqlResultDAO;
import org.acmsl.queryj.metadata.TableDAO;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JUnit/PowerMock/EasyMock classes.
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for {@link RetrieveResultPropertiesHandler}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/15 16:34
 */
@RunWith(JUnit4.class)
public class RetrieveResultPropertiesHandlerTest
{
    /**
     * Checks the explicit properties are published.
     * Throws QueryJBuildException
     * Throws SQLException
     */
    @Test
    public void publishes_explicit_properties()
        throws  QueryJBuildException,
                SQLException
    {
        @NotNull final RetrieveResultPropertiesHandler instance = new RetrieveResultPropertiesHandler();

        @NotNull final QueryJCommand t_Parameters =
            new ConfigurationQueryJCommandImpl(new SerializablePropertiesConfiguration());

        @NotNull final SqlElement<String> t_Sql =
            new SqlElement<>(
                "id", "dao", "name", "String", SqlCardinality.SINGLE, "all", true /* validation */, false, "description");

        @NotNull final List<Property<String>> t_lProperties = new ArrayList<>(2);
        t_lProperties.add(new PropertyElement<>("name", "name", 1, String.class.getSimpleName(), false));
        t_lProperties.add(new PropertyElement<>("tmst", "tmst", 1, "Date", false));

        @NotNull final Result<String> t_Result = new ResultElement<>("r1", "Vo");
        t_Result.add(new PropertyRefElement("name"));
        t_Result.add(new PropertyRefElement("tmst"));

        t_Sql.add(new ParameterRefElement("id"));
        t_Sql.setResultRef(new ResultRefElement("r1"));

        @NotNull final CustomSqlProvider t_CustomSqlProvider = PowerMock.createNiceMock(CustomSqlProvider.class);
        @NotNull final MetadataManager t_MetadataManager = PowerMock.createMock(MetadataManager.class);
        @NotNull final SqlResultDAO t_ResultDAO = PowerMock.createNiceMock(SqlResultDAO.class);
        @NotNull final SqlPropertyDAO t_PropertyDAO = PowerMock.createNiceMock(SqlPropertyDAO.class);
        @NotNull final ResultSet t_ResultSet = PowerMock.createNiceMock(ResultSet.class);
        @NotNull final PreparedStatement t_Statement = PowerMock.createNiceMock(PreparedStatement.class);
        @NotNull final TableDAO t_TableDAO = PowerMock.createNiceMock(TableDAO.class);
        @SuppressWarnings("unchecked")
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> t_Table = PowerMock.createNiceMock(Table.class);

        EasyMock.expect(t_MetadataManager.getTableDAO()).andReturn(t_TableDAO);
        EasyMock.expect(t_TableDAO.findByDAO("dao")).andReturn(t_Table);
        EasyMock.expect(t_Table.getName()).andReturn("dao");
        EasyMock.expect(t_CustomSqlProvider.getSqlResultDAO()).andReturn(t_ResultDAO);
        EasyMock.expect(t_CustomSqlProvider.getSqlPropertyDAO()).andReturn(t_PropertyDAO);
        EasyMock.expect(t_ResultDAO.findByPrimaryKey(t_Result.getId())).andReturn(t_Result);
        EasyMock.expect(t_Statement.executeQuery()).andReturn(t_ResultSet);

        for (@NotNull final Property<String> t_Property : t_lProperties)
        {
            EasyMock.expect(t_PropertyDAO.findByPrimaryKey(t_Property.getId())).andReturn(t_Property);
        }

        new QueryJCommandWrapper<Sql<String>>(t_Parameters).setSetting(RetrieveQueryHandler.CURRENT_SQL, t_Sql);
        new QueryJCommandWrapper<MetadataManager>(t_Parameters)
            .setSetting(DatabaseMetaDataRetrievalHandler.METADATA_MANAGER, t_MetadataManager);
        new QueryJCommandWrapper<CustomSqlProvider>(t_Parameters).setSetting(
            CustomSqlProviderRetrievalHandler.CUSTOM_SQL_PROVIDER, t_CustomSqlProvider);
        new QueryJCommandWrapper<Sql<String>>(t_Parameters).setSetting(RetrieveQueryHandler.CURRENT_SQL, t_Sql);

        EasyMock.replay(t_CustomSqlProvider);
        EasyMock.replay(t_MetadataManager);
        EasyMock.replay(t_TableDAO);
        EasyMock.replay(t_Table);
        EasyMock.replay(t_PropertyDAO);
        EasyMock.replay(t_ResultDAO);
        EasyMock.replay(t_ResultSet);
        EasyMock.replay(t_Statement);

        new SetupPreparedStatementHandler().setCurrentPreparedStatement(t_Statement, t_Parameters);
        new ExecuteQueryHandler().handle(t_Parameters);

        Assert.assertFalse(instance.handle(t_Parameters));

        EasyMock.verify(t_CustomSqlProvider);
        EasyMock.verify(t_MetadataManager);
        EasyMock.verify(t_TableDAO);
        EasyMock.verify(t_Table);
        EasyMock.verify(t_PropertyDAO);
        EasyMock.verify(t_ResultDAO);
        EasyMock.verify(t_Statement);
    }
}
