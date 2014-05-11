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
 * Filename: TableDecoratorHelperTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for TableDecoratorHelper.
 *
 * Date: 2014/05/11
 * Time: 07:33
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.AttributeIncompleteValueObject;

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

/*
 * Importing JDK classes.
 */
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for {@link TableDecoratorHelper}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/11 07:33
 */
@RunWith(JUnit4.class)
public class TableDecoratorHelperTest
{
    /**
     * Checks containNullableAttributes() detects nullable attributes.
     */
    @Test
    public void containNullableAttributes_detects_nullable_attributes()
    {
        @NotNull final TableDecoratorHelper instance = TableDecoratorHelper.getInstance();

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
                true, // allowsNull
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
                true, // allowsNull
                null); // value

        @NotNull final List<Attribute<String>> attributes1 = new ArrayList<>(4);

        attributes1.add(childAttribute1);
        attributes1.add(childAttribute2);
        attributes1.add(childAttribute3);
        attributes1.add(childAttribute4);

        @NotNull final AbstractTableDecorator table1 =
            AbstractTableDecoratorTest.setupTableDecorator(attributes1, null);

        Assert.assertTrue(instance.containNullableAttributes(table1.getAttributes()));

        @NotNull final List<Attribute<String>> attributes2 = new ArrayList<>(2);

        @NotNull final AbstractTableDecorator table2 =
            AbstractTableDecoratorTest.setupTableDecorator(attributes1, null);

        attributes2.add(childAttribute1);
        attributes2.add(childAttribute3);

        Assert.assertTrue(instance.containNullableAttributes(table2.getAttributes()));
    }

    /**
     * Checks containNotNullAttributes() detects notnull attributes.
     */
    @Test
    public void containNotNullAttributes_detects_notnull_attributes()
    {
        @NotNull final TableDecoratorHelper instance = TableDecoratorHelper.getInstance();

        @NotNull final List<Attribute<String>> attributes1 = new ArrayList<>(4);

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
                true, // allowsNull
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
                true, // allowsNull
                null); // value

        attributes1.add(childAttribute1);
        attributes1.add(childAttribute2);
        attributes1.add(childAttribute3);
        attributes1.add(childAttribute4);

        @NotNull final AbstractTableDecorator table1 =
            AbstractTableDecoratorTest.setupTableDecorator(attributes1, null);

        Assert.assertTrue(
            instance.containNotNullAttributes(
                table1.getAttributes(), table1.getMetadataManager().getMetadataTypeManager()));

        @NotNull final List<Attribute<String>> attributes2 = new ArrayList<>(2);

        @NotNull final AbstractTableDecorator table2 =
            AbstractTableDecoratorTest.setupTableDecorator(attributes2, null);

        attributes2.add(childAttribute1);
        attributes2.add(childAttribute4);

        Assert.assertFalse(
            instance.containNotNullAttributes(
                table2.getAttributes(), table2.getMetadataManager().getMetadataTypeManager()));
    }
}
