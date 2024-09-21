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
 * Filename: TableAttributesListDecoratorTest.java
 *
 * Author: Jose San Leandro
 *
 * Description: Tests for TableAttributesListDecorator.
 *
 * Created: 2014/04/14 12:11
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.engines.JdbcMetadataTypeManager;
import org.acmsl.queryj.metadata.vo.AbstractAttribute;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.metadata.vo.TableValueObject;

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
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for {@link TableAttributesListDecorator}.
 * @author <a href="mailto:jose.sanleandro@ventura24.es">Jose San Leandro</a>
 * @since 3.0
 * Created 2014/04/14
 */
@RunWith(JUnit4.class)
public class TableAttributesListDecoratorTest
{
    /**
     * Checks whether getAttributeTypes() returns the types of the attributes.
     */
    @Test
    public void getAttributeTypes_returns_the_types_of_the_attributes()
    {
        @NotNull final MetadataManager metadataManager =
            EasyMock.createNiceMock(MetadataManager.class);

        @NotNull final MetadataTypeManager metadataTypeManager =
            new JdbcMetadataTypeManager();

        EasyMock.expect(metadataManager.getMetadataTypeManager()).andReturn(metadataTypeManager);

        EasyMock.replay(metadataManager);

        @NotNull final CustomSqlProvider customSqlProvider =
            EasyMock.createNiceMock(CustomSqlProvider.class);

        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table =
            new TableValueObject(
                "name",
                "comment",
                new ArrayList<Attribute<String>>(0),
                new ArrayList<Attribute<String>>(0),
                new ArrayList<ForeignKey<String>>(0),
                null, // parent
                null, // static
                false, // vo decorated
                false); // is relationship

        @NotNull final Attribute<DecoratedString> intAttribute =
            new AbstractAttribute<DecoratedString>(
                new DecoratedString("name"),
                Types.INTEGER,
                new DecoratedString("int"),
                new DecoratedString("tableName"),
                new DecoratedString("comment"),
                1,
                10, // length
                1, // precision
                null, // keyword
                null, // retrieval query
                null, // sequence
                false, // nullable
                null, // value
                false, // read-only
                false, // is-bool
                null, // boolean-true
                null, // boolean-false
                null) {}; // boolean-null

        @NotNull final Attribute<DecoratedString> dateAttribute =
            new AbstractAttribute<DecoratedString>(
                new DecoratedString("created"),
                Types.DATE,
                new DecoratedString("Date"),
                new DecoratedString("tableName"),
                new DecoratedString("comment"),
                1,
                10, // length
                1, // precision
                null, // keyword
                null, // retrieval query
                null, // sequence
                false, // nullable
                null, // value
                false, // read-only
                false, // is-bool
                null, // boolean-true
                null, // boolean-false
                null) {}; // boolean-null

        @NotNull final TableDecorator tableDecorator =
            new CachingTableDecorator(
                table, metadataManager, CachingDecoratorFactory.getInstance(), customSqlProvider);

        @NotNull final TableAttributesListDecorator instance =
            new TableAttributesListDecorator(
                Arrays.asList(intAttribute, dateAttribute),
                tableDecorator,
                customSqlProvider,
                CachingDecoratorFactory.getInstance()) {};

        @NotNull final List<DecoratedString> types = instance.getAttributeTypes();

        Assert.assertEquals(1, types.size());
    }
}
