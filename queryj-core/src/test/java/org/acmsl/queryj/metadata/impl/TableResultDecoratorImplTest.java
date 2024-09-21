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
 * Filename: TableResultDecoratorImplTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for TableResultDecoratorImpl.
 *
 * Date: 2014/06/20
 * Time: 05:37
 *
 */
package org.acmsl.queryj.metadata.impl;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultElement;
import org.acmsl.queryj.customsql.ResultRefElement;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.SqlCardinality;
import org.acmsl.queryj.customsql.SqlElement;
import org.acmsl.queryj.metadata.AbstractTableDecorator;
import org.acmsl.queryj.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.metadata.DecoratedString;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.ListDecorator;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.SqlDAO;
import org.acmsl.queryj.metadata.TableDecorator;
import org.acmsl.queryj.metadata.engines.JdbcMetadataTypeManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.AttributeIncompleteValueObject;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.metadata.vo.TableValueObject;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for {@link TableResultDecoratorImpl}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/06/20 05:37
 */
@RunWith(JUnit4.class)
public class TableResultDecoratorImplTest
{
    /**
     * Ad-hoc class used for testing purposes.
     */
    public static class MyTableDecorator
        extends AbstractTableDecorator
    {
        /**
         * The serial version id.
         */
        private static final long serialVersionUID = 6624129343682091078L;

        /**
         * Creates a new instance.
         * @param table the table.
         * @param metadataManager the {@link MetadataManager} instance.
         * @param decoratorFactory the {@link DecoratorFactory} instance.
         * @param customSqlProvider the {@link CustomSqlProvider} instance.
         */
        public MyTableDecorator(
            @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table,
            @NotNull final MetadataManager metadataManager,
            @NotNull final DecoratorFactory decoratorFactory,
            @NotNull final CustomSqlProvider customSqlProvider)
        {
            super(
                table,
                metadataManager,
                decoratorFactory,
                customSqlProvider);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        protected Table<DecoratedString, Attribute<DecoratedString>, ListDecorator<Attribute<DecoratedString>>> createTableDecorator(
            @Nullable final String parentTable,
            @NotNull final ListDecorator<Attribute<String>> primaryKey,
            @NotNull final ListDecorator<Attribute<String>> attributes,
            final boolean isStatic,
            final boolean voDecorated,
            @NotNull final MetadataManager metadataManager,
            @NotNull final DecoratorFactory decoratorFactory,
            @NotNull final CustomSqlProvider customSqlProvider)
        {
            return
                new MyTableDecorator(
                    getTable(),
                    metadataManager,
                    decoratorFactory,
                    customSqlProvider);
        }
    }

    /**
     * Checks whether isSingleBeingUsed() is correct depending on whether
     * the associated table has queries returning one sole instance of
     * the custom result.
     */
    @Test
    public void isSingleBeingUsed_returns_true_if_it_is_being_used_by_the_table_custom_selects()
    {
        @NotNull final Result<String> result =
            new ResultElement<>("resultId", "com.foo.bar.MyResult");

        @NotNull final MetadataManager metadataManager = EasyMock.createNiceMock(MetadataManager.class);
        EasyMock.expect(metadataManager.getMetadataTypeManager()).andReturn(JdbcMetadataTypeManager.getInstance()).anyTimes();
        EasyMock.replay(metadataManager);

        @NotNull final CustomSqlProvider customSqlProvider = EasyMock.createNiceMock(CustomSqlProvider.class);
        @NotNull final DecoratorFactory decoratorFactory = CachingDecoratorFactory.getInstance();

        @NotNull final List<Attribute<String>> primaryKey = new ArrayList<>(1);
        @NotNull final List<Attribute<String>> attributes = primaryKey;
        @NotNull final List<ForeignKey<String>> foreignKeys = new ArrayList<>(0);

        @NotNull final Attribute<String> attribute1 =
            new AttributeIncompleteValueObject(
                "name", Types.BIGINT, "long", "table", "comment", 1, 10, 1, false, null);

        primaryKey.add(attribute1);

        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> wrappedTable =
            new TableValueObject("table", "comment", primaryKey, attributes, foreignKeys, null, null, false, false);

        @NotNull final TableDecorator table =
            new MyTableDecorator(
                wrappedTable,
                metadataManager,
                decoratorFactory,
                customSqlProvider);

        @NotNull final Sql<String> sql =
            new SqlElement<>(
                "sqlId",
                "table",
                "name",
                "select",
                SqlCardinality.SINGLE,
                "oracle",
                false,
                false,
                "description");

        ((SqlElement <String>) sql).setResultRef(new ResultRefElement("resultId"));

        @NotNull final List<Sql<String>> queries = Arrays.asList(sql);

        @NotNull final SqlDAO sqlDAO = EasyMock.createNiceMock(SqlDAO.class);
        EasyMock.expect(sqlDAO.findByResultId("resultId")).andReturn(queries);
        EasyMock.expect(sqlDAO.findByDAO("table")).andReturn(queries);
        EasyMock.expect(customSqlProvider.getSqlDAO()).andReturn(sqlDAO);

        EasyMock.replay(sqlDAO);
        EasyMock.replay(customSqlProvider);

        @NotNull final TableResultDecoratorImpl<String> instance =
            new TableResultDecoratorImpl<>(
                result,
                table,
                customSqlProvider,
                decoratorFactory);

        Assert.assertTrue(instance.isSingleBeingUsed());
    }


    /**
     * Checks whether isMultipleBeingUsed() is correct depending on whether
     * the associated table has queries returning one sole instance of
     * the custom result.
     */
    @Test
    public void isMultipleBeingUsed_returns_true_if_it_is_being_used_by_the_table_custom_selects()
    {
        @NotNull final Result<String> result =
            new ResultElement<>("resultId", "com.foo.bar.MyResult");

        @NotNull final MetadataManager metadataManager = EasyMock.createNiceMock(MetadataManager.class);
        EasyMock.expect(metadataManager.getMetadataTypeManager()).andReturn(JdbcMetadataTypeManager.getInstance()).anyTimes();
        EasyMock.replay(metadataManager);

        @NotNull final CustomSqlProvider customSqlProvider = EasyMock.createNiceMock(CustomSqlProvider.class);
        @NotNull final DecoratorFactory decoratorFactory = CachingDecoratorFactory.getInstance();

        @NotNull final List<Attribute<String>> primaryKey = new ArrayList<>(1);
        @NotNull final List<Attribute<String>> attributes = primaryKey;
        @NotNull final List<ForeignKey<String>> foreignKeys = new ArrayList<>(0);

        @NotNull final Attribute<String> attribute1 =
            new AttributeIncompleteValueObject(
                "name", Types.BIGINT, "long", "table", "comment", 1, 10, 1, false, null);

        primaryKey.add(attribute1);

        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> wrappedTable =
            new TableValueObject("table", "comment", primaryKey, attributes, foreignKeys, null, null, false, false);

        @NotNull final TableDecorator table =
            new MyTableDecorator(
                wrappedTable,
                metadataManager,
                decoratorFactory,
                customSqlProvider);

        @NotNull final Sql<String> sql =
            new SqlElement<>(
                "sqlId",
                "table",
                "name",
                "select",
                SqlCardinality.MULTIPLE,
                "oracle",
                false,
                false,
                "description");

        ((SqlElement <String>) sql).setResultRef(new ResultRefElement("resultId"));

        @NotNull final List<Sql<String>> queries = Arrays.asList(sql);

        @NotNull final SqlDAO sqlDAO = EasyMock.createNiceMock(SqlDAO.class);
        EasyMock.expect(sqlDAO.findByResultId("resultId")).andReturn(queries);
        EasyMock.expect(sqlDAO.findByDAO("table")).andReturn(queries);
        EasyMock.expect(customSqlProvider.getSqlDAO()).andReturn(sqlDAO);

        EasyMock.replay(sqlDAO);
        EasyMock.replay(customSqlProvider);

        @NotNull final TableResultDecoratorImpl<String> instance =
            new TableResultDecoratorImpl<>(
                result,
                table,
                customSqlProvider,
                decoratorFactory);

        Assert.assertTrue(instance.isMultipleBeingUsed());
    }
}
