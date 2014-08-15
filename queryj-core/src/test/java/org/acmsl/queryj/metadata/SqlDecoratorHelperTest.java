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
 * Filename: SqlDecoratorHelperTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for SqlDecoratorHelper.
 *
 * Date: 2014/05/17
 * Time: 10:25
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.ParameterElement;
import org.acmsl.queryj.customsql.ParameterRefElement;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.SqlCardinality;
import org.acmsl.queryj.customsql.SqlElement;
import org.acmsl.queryj.metadata.engines.JdbcMetadataTypeManager;

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

/*
 * Importing JDK classes.
 */
import java.util.List;

/**
 * Tests for {@link SqlDecoratorHelper}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/17 10:25
 */
@RunWith(JUnit4.class)
public class SqlDecoratorHelperTest
{
    /**
     * Tests getParameterTypes() returns no duplicates.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void getParameterTypes_returns_no_duplicates()
    {
        @NotNull final Sql<String> sql =
            new SqlElement<>("id1", "name1", "select", SqlCardinality.SINGLE, "all", false, false, "none", "desc1");

        @NotNull final Parameter parameter =
            new ParameterElement<>("pid", 1, "paramName", "Date", null);

        sql.add(new ParameterRefElement("pid"));

        @NotNull final CustomSqlProvider customSqlProvider = EasyMock.createNiceMock(CustomSqlProvider.class);
        @NotNull final MetadataManager metadataManager = EasyMock.createNiceMock(MetadataManager.class);
        @NotNull final MetadataTypeManager metadataTypeManager = new JdbcMetadataTypeManager();
        @NotNull final SqlParameterDAO parameterDAO = EasyMock.createNiceMock(SqlParameterDAO.class);

        EasyMock.expect(customSqlProvider.getSqlParameterDAO()).andReturn(parameterDAO).anyTimes();
        EasyMock.expect(metadataManager.getMetadataTypeManager()).andReturn(metadataTypeManager).anyTimes();
        EasyMock.expect(parameterDAO.findByPrimaryKey("pid")).andReturn(parameter).anyTimes();

        EasyMock.replay(customSqlProvider);
        EasyMock.replay(metadataManager);
        EasyMock.replay(parameterDAO);

        @NotNull final SqlDecorator sqlDecorator =
            new CachingSqlDecorator(sql, customSqlProvider, metadataManager);

        @NotNull final SqlDecoratorHelper instance = SqlDecoratorHelper.getInstance();

        @NotNull final List<DecoratedString> parameterTypes =
            instance.getParameterTypes(sqlDecorator.getParameters(), metadataManager.getMetadataTypeManager());

        Assert.assertEquals(1, parameterTypes.size());

        EasyMock.verify(parameterDAO);
        EasyMock.verify(metadataManager);
    }
}
