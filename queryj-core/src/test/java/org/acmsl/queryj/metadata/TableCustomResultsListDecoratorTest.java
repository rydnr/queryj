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
 * Filename: TableCustomResultsListDecoratorTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for TableCustomResultsListDecorator.
 *
 * Date: 2014/06/15
 * Time: 19:11
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.customsql.ResultElement;
import org.jetbrains.annotations.NotNull;

/*
 * Importing JUnit/EasyMock classes.
 */
import org.easymock.EasyMock;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for {@link TableCustomResultsListDecorator}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/06/15 19:11
 */
@RunWith(JUnit4.class)
public class TableCustomResultsListDecoratorTest
{
    /**
     * Checks whether getItems() returns decorated results.
     */
    @Test
    public void getItems_returns_decorated_results()
    {
        @NotNull final List<Result<DecoratedString>> list = new ArrayList<>(2);

        @NotNull final Result<DecoratedString> result1 =
            new ResultElement<>(new DecoratedString("id1"), null);

        @NotNull final Result<DecoratedString> result2 =
            new ResultElement<>(new DecoratedString("id2"), null);

        list.add(result1);
        list.add(result2);

        @NotNull final TableDecorator tableDecorator =
            EasyMock.createNiceMock(TableDecorator.class);

        @NotNull final MetadataManager metadataManager = EasyMock.createNiceMock(MetadataManager.class);
        EasyMock.expect(tableDecorator.getMetadataManager()).andReturn(metadataManager).anyTimes();
        EasyMock.replay(tableDecorator);

        @NotNull final CustomSqlProvider customSqlProvider = EasyMock.createNiceMock(CustomSqlProvider.class);
        @NotNull final DecoratorFactory decoratorFactory = EasyMock.createNiceMock(DecoratorFactory.class);

        @NotNull final TableCustomResultsListDecorator instance =
            new TableCustomResultsListDecorator(list, tableDecorator, customSqlProvider, decoratorFactory);

        @NotNull final List<Result<DecoratedString>> items =
            instance.getItems();

        for (@Nullable final Result<DecoratedString> item : items)
        {
            Assert.assertTrue(item instanceof TableResultDecorator);
        }
    }

    /**
     * Checks getDifferent() returns the different custom results.
     */
    @Test
    public void getDifferent_returns_no_duplicates()
    {
        @NotNull final List<Result<DecoratedString>> list = new ArrayList<>(2);

        @NotNull final Result<DecoratedString> result1 =
            new ResultElement<>(new DecoratedString("id1"), null);

        @NotNull final Result<DecoratedString> result2 =
            new ResultElement<>(new DecoratedString("id2"), null);

        list.add(result1);
        list.add(result2);

        @NotNull final TableDecorator tableDecorator =
            EasyMock.createNiceMock(TableDecorator.class);

        @NotNull final MetadataManager metadataManager = EasyMock.createNiceMock(MetadataManager.class);
        EasyMock.expect(tableDecorator.getMetadataManager()).andReturn(metadataManager).anyTimes();
        EasyMock.replay(tableDecorator);

        @NotNull final CustomSqlProvider customSqlProvider = EasyMock.createNiceMock(CustomSqlProvider.class);
        @NotNull final DecoratorFactory decoratorFactory = EasyMock.createNiceMock(DecoratorFactory.class);

        @NotNull final TableCustomResultsListDecorator instance =
            new TableCustomResultsListDecorator(list, tableDecorator, customSqlProvider, decoratorFactory);

        @NotNull final ListDecorator<Result<DecoratedString>> different = instance.getDifferent();

        Assert.assertEquals(2, different.size());
    }
}
