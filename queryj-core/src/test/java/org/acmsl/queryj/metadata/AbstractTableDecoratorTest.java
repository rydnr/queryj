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
 * Filename: AbstractTableDecoratorTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for AbstractTableDecorator.
 *
 * Date: 2014/04/11
 * Time: 19:52
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
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
 * Importing JUnit/PowerMock/EasyMock classes.
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
import java.util.List;

/**
 * Tests for {@link AbstractTableDecorator}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/11 19:52
 */
@RunWith(JUnit4.class)
public class AbstractTableDecoratorTest
{
    /**
     * Creates a decorator with the required mock dependencies.
     * @return the table decorator.
     */
    protected AbstractTableDecorator setupTableDecorator()
    {
        return setupTableDecorator(new ArrayList<>(0), null);
    }

    /**
     * Creates a decorator with the required mock dependencies.
     * @param attributes the attributes.
     * @return the table decorator.
     */
    protected AbstractTableDecorator setupTableDecorator(@NotNull final List<Attribute<String>> attributes)
    {
        return setupTableDecorator(attributes, null);
    }

    /**
     * Creates a decorator with the required mock dependencies.
     * @param attributes the attributes.
     * @param parentTable the parent table.
     * @return the table decorator.
     */
    protected static AbstractTableDecorator setupTableDecorator(
        @NotNull final List<Attribute<String>> attributes,
        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> parentTable)
    {
        @NotNull final AbstractTableDecorator result;

        @NotNull final String name = "name";
        @NotNull final String comment = "comment";
        @NotNull final List<Attribute<String>> primaryKey = new ArrayList<>(0);
        @NotNull final List<ForeignKey<String>> foreignKeys = new ArrayList<>(0);
        @Nullable final Attribute<String> staticAttribute = null;
        final boolean voDecorated = false;
        final boolean isRelationship = false;

        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table =
            new TableValueObject(
                name,
                comment,
                primaryKey,
                attributes,
                foreignKeys,
                parentTable,
                staticAttribute,
                voDecorated,
                isRelationship);

        @NotNull final MetadataManager metadataManager = EasyMock.createNiceMock(MetadataManager.class);
        @NotNull final MetadataTypeManager metadataTypeManager = new JdbcMetadataTypeManager();
        EasyMock.expect(metadataManager.getMetadataTypeManager()).andReturn(metadataTypeManager).anyTimes();
        EasyMock.replay(metadataManager);
        @NotNull final DecoratorFactory decoratorFactory = CachingDecoratorFactory.getInstance();
        @NotNull final CustomSqlProvider customSqlProvider = EasyMock.createNiceMock(CustomSqlProvider.class);

        result =
            new AbstractTableDecorator(table, metadataManager, decoratorFactory, customSqlProvider)
            {
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
                    return null;
                }
            };

        return result;
    }

    /**
     * Checks whether getContainsClobs() is correct for tables with no Clob attributes.
     */
    @Test
    public void getContainsClobs_is_correct_if_table_does_not_contain_clobs()
    {
        @NotNull final AbstractTableDecorator instance = setupTableDecorator();

        Assert.assertFalse(instance.getContainsClobs());
    }

    /**
     * Checks whether getContainsClobs() is correct for tables with Clob attributes.
     */
    @Test
    public void getContainsClobs_is_correct_if_table_contains_clobs()
    {
        @NotNull final String name = "name";
        @NotNull final List<Attribute<String>> attributes = new ArrayList<>();
        attributes.add(
            new AttributeIncompleteValueObject(
                "name",
                Types.CLOB,
                "String",
                name,
                "comment",
                1, // ordinalPosition
                6222, // length
                1, // precision
                false, // allowsNull
                null)); // value

        @NotNull final AbstractTableDecorator instance = setupTableDecorator(attributes);

        Assert.assertTrue(instance.getContainsClobs());
    }

    /**
     * Checks if "getAll()" returns also the parent's attributes.
     */
    @Test
    public void getAll_includes_parent_attributes()
    {
        @NotNull final String name = "parentTable";
        @NotNull final String comment = "comment";
        @NotNull final List<Attribute<String>> primaryKey = new ArrayList<>(0);
        @NotNull final List<Attribute<String>> attributes = new ArrayList<>(0);
        @NotNull final List<Attribute<String>> parentAttributes = new ArrayList<>(0);
        @NotNull final List<ForeignKey<String>> foreignKeys = new ArrayList<>(0);
        @Nullable final Attribute<String> staticAttribute = null;
        final boolean voDecorated = false;
        final boolean isRelationship = false;

        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> parentTable =
            new TableValueObject(
                name,
                comment,
                primaryKey,
                parentAttributes,
                foreignKeys,
                null,
                staticAttribute,
                voDecorated,
                isRelationship);

        @NotNull final Attribute<String> parentAttribute =
            new AttributeIncompleteValueObject(
                "myParentId",
                Types.BIGINT,
                "long",
                name,
                "parent comment",
                1, // ordinalPosition
                6222, // length
                1, // precision
                false, // allowsNull
                null); // value

        parentAttributes.add(parentAttribute);

        @NotNull final Attribute<String> childAttribute =
            new AttributeIncompleteValueObject(
                "myChildId",
                Types.BIGINT,
                "long",
                "name",
                "child comment",
                1, // ordinalPosition
                6222, // length
                1, // precision
                false, // allowsNull
                null); // value

        attributes.add(childAttribute);

        @NotNull final AbstractTableDecorator instance = setupTableDecorator(attributes, parentTable);

        @NotNull final ListDecorator<Attribute<DecoratedString>> listDecorator = instance.getAllAttributes();

        @NotNull final List<Attribute<DecoratedString>> allAttributes = listDecorator.getItems();

        Assert.assertEquals(2, allAttributes.size());

        for (@NotNull final Attribute<DecoratedString> attribute : allAttributes)
        {
            Assert.assertTrue(
                   attribute.getName().getValue().equals("myChildId")
                || attribute.getName().getValue().equals("myParentId"));
        }
    }

    /**
     * Checks getAttributeTypes() does not return duplicates.
     */
    @Test
    public void getAttributeTypes_does_not_contain_duplicates()
    {
        @NotNull final List<Attribute<String>> attributes = new ArrayList<>(0);

        @NotNull final Attribute<String> childAttribute1 =
            new AttributeIncompleteValueObject(
                "myChildId1",
                Types.BIGINT,
                "long",
                "id1",
                "child comment 1",
                1, // ordinalPosition
                6222, // length
                1, // precision
                false, // allowsNull
                null); // value

        @NotNull final Attribute<String> childAttribute2 =
            new AttributeIncompleteValueObject(
                "time2",
                Types.TIMESTAMP,
                "Timestamp",
                "id2",
                "child comment 2",
                2, // ordinalPosition
                6222, // length
                1, // precision
                false, // allowsNull
                null); // value

        @NotNull final Attribute<String> childAttribute3 =
            new AttributeIncompleteValueObject(
                "date3",
                Types.DATE,
                "Date",
                "name",
                "child comment 3",
                3, // ordinalPosition
                6222, // length
                1, // precision
                false, // allowsNull
                null); // value

        @NotNull final Attribute<String> childAttribute4 =
            new AttributeIncompleteValueObject(
                "date4",
                Types.DATE,
                "Date",
                "name",
                "child comment 4",
                3, // ordinalPosition
                6222, // length
                1, // precision
                false, // allowsNull
                null); // value

        attributes.add(childAttribute1);
        attributes.add(childAttribute2);
        attributes.add(childAttribute3);
        attributes.add(childAttribute4);

        @NotNull final AbstractTableDecorator instance = setupTableDecorator(attributes, null);

        @NotNull final List<DecoratedString> attributeTypes = instance.getAttributeTypes();

        Assert.assertEquals(2, attributeTypes.size());

        for (@NotNull final DecoratedString type: attributeTypes)
        {
            Assert.assertTrue(
                   type.getValue().equals("java.util.Date")
                || type.getValue().equals("java.sql.Timestamp"));
        }
    }
}
