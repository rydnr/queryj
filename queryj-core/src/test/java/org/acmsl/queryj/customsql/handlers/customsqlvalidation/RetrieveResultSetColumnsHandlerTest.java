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
 * Filename: RetrieveResultSetColumnsHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for RetrieveResultSetColumnsHandler.
 *
 * Date: 2014/03/16
 * Time: 11:07
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
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyElement;
import org.acmsl.queryj.customsql.PropertyRefElement;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultElement;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.Sql.Cardinality;
import org.acmsl.queryj.customsql.SqlElement;

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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for {@link RetrieveResultSetColumnsHandler}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/16 11:07
 */
@RunWith(JUnit4.class)
public class RetrieveResultSetColumnsHandlerTest
{
    /**
     * Checks the properties are built from the result set metadata.
     * Throws QueryJBuildException
     * Throws SQLException
     */
    @Test
    public void builds_a_list_of_properties_from_the_ResultSetMetadata()
        throws QueryJBuildException,
               SQLException
    {
        @NotNull final RetrieveResultSetColumnsHandler instance = new RetrieveResultSetColumnsHandler();

        @NotNull final QueryJCommand t_Parameters =
            new ConfigurationQueryJCommandImpl(new SerializablePropertiesConfiguration());

        @NotNull final SqlElement<String> sql =
            new SqlElement<>(
                "id", "dao", "name", Sql.SELECT, Cardinality.SINGLE, "all", true /* validation */, false, "description");

        sql.setValue("select sysdate from dual where ? = 'A'");

        @NotNull final ResultSet t_ResultSet = PowerMock.createNiceMock(ResultSet.class);
        @NotNull final ResultSetMetaData t_ResultSetMetaData = PowerMock.createNiceMock(ResultSetMetaData.class);

        EasyMock.expect(t_ResultSet.getMetaData()).andReturn(t_ResultSetMetaData);

        @NotNull final List<Property<String>> t_lProperties = new ArrayList<>(2);
        t_lProperties.add(new PropertyElement<>("name", "name", 1, String.class.getSimpleName(), false));
        t_lProperties.add(new PropertyElement<>("tmst", "tmst", 1, "Date", false));

        @NotNull final Result<String> t_Result = new ResultElement<>("r1", "Vo");
        t_Result.add(new PropertyRefElement("name"));
        t_Result.add(new PropertyRefElement("tmst"));

        int t_iIndex = 1;

        EasyMock.expect(t_ResultSetMetaData.getColumnCount()).andReturn(t_lProperties.size());
        for (@NotNull final Property<String> t_Property : t_lProperties)
        {
            EasyMock.expect(t_ResultSetMetaData.getColumnName(t_iIndex)).andReturn(t_Property.getColumnName());
            EasyMock.expect(t_ResultSetMetaData.getColumnTypeName(t_iIndex)).andReturn(t_Property.getType());
            EasyMock.expect(t_ResultSetMetaData.isNullable(t_iIndex)).andReturn(ResultSetMetaData.columnNoNulls);

            t_iIndex++;
        }

        EasyMock.replay(t_ResultSet);
        EasyMock.replay(t_ResultSetMetaData);

        new QueryJCommandWrapper<Sql<String>>(t_Parameters).setSetting(RetrieveQueryHandler.CURRENT_SQL, sql);
        new QueryJCommandWrapper<ResultSet>(t_Parameters).setSetting(
            ExecuteQueryHandler.CURRENT_RESULTSET, t_ResultSet);

        Assert.assertFalse(instance.handle(t_Parameters));

        EasyMock.verify(t_ResultSet);
        EasyMock.verify(t_ResultSetMetaData);
    }

    /**
     * Checks it does not process non-select queries.
     * Throws QueryJBuildException
     * Throws SQLException
     */
    @Test
    public void does_not_process_non_select_queries()
        throws QueryJBuildException,
        SQLException
    {
        @NotNull final RetrieveResultSetColumnsHandler instance = new RetrieveResultSetColumnsHandler();

        @NotNull final QueryJCommand t_Parameters =
            new ConfigurationQueryJCommandImpl(new SerializablePropertiesConfiguration());

        @NotNull final ResultSet t_ResultSet = PowerMock.createMock(ResultSet.class);

        @NotNull final SqlElement<String> sql =
            new SqlElement<>(
                "id", "dao", "name", Sql.UPDATE, Cardinality.SINGLE, "all", true /* validation */, false, "description");

        new QueryJCommandWrapper<Sql<String>>(t_Parameters).setSetting(RetrieveQueryHandler.CURRENT_SQL, sql);
        new QueryJCommandWrapper<ResultSet>(t_Parameters).setSetting(
            ExecuteQueryHandler.CURRENT_RESULTSET, t_ResultSet);

        Assert.assertTrue(instance.handle(t_Parameters));
    }
}
