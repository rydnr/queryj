/*
                        QueryJ Test

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
 * Filename: ForeignKeyTestHelperTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for ForeignKeyTestHelper.
 *
 * Date: 2014/04/22
 * Time: 06:13
 *
 */
package org.acmsl.queryj.test;

/*
 * Importing Cucumber classes.
 */
import cucumber.api.DataTable;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.AttributeValueObject;
import org.acmsl.queryj.metadata.vo.ForeignKey;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.metadata.vo.TableIncompleteValueObject;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Tests for {@link ForeignKeyTestHelper}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/22 06:13
 */
@RunWith(JUnit4.class)
public class ForeignKeyTestHelperTest
{
    /**
     * Tests whether defineInputForeignKey() adds new foreign keys to
     * the list passed.
     */
    @Test
    public void defineInputForeignKey_builds_the_foreign_key_list()
    {
        @NotNull final ForeignKeyTestHelper instance = ForeignKeyTestHelper.getInstance();

        @NotNull final String[] columnNames = { "source", "column(s)", "target", "allows null" };
        @NotNull final Map<String, String> row = new HashMap<>(4);
        row.put("source", "G_CYCLE_TYPES");
        row.put("column(s)", "G_FIRST_DRAW_TYPE_ID");
        row.put("target", "G_DRAWS");
        row.put("allows null", "false");
        @NotNull final List<?> data = Arrays.asList(row);
        @NotNull final DataTable dataTable = DataTable.create(data, Locale.getDefault(), columnNames);

        @NotNull final List<ForeignKey<String>> foreignKeys = new ArrayList<>();

        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table =
            new TableIncompleteValueObject("G_CYCLE_TYPES", "")
            {
                /**
                 * {@inheritDoc}
                 */
                @Override
                @NotNull
                public List<Attribute<String>> getAttributes()
                {
                    return
                        Arrays.<Attribute<String>>asList(
                            new AttributeValueObject(
                                "G_FIRST_DRAW_TYPE_ID",
                                Types.BIGINT,
                                "long",
                                "G_CYCLE_TYPES",
                                null,
                                1,
                                10,
                                1,
                                null,
                                null,
                                null,
                                false,
                                null,
                                false,
                                false,
                                null,
                                null,
                                null));
                };
            };

        @NotNull final Map<String, Table<String, Attribute<String>, List<Attribute<String>>>> tableMap =
            new HashMap<>(1);
        tableMap.put("G_CYCLE_TYPES", table);
        instance.defineInputForeignKey(dataTable, foreignKeys, tableMap);

        Assert.assertTrue(foreignKeys.size() == 1);
    }

    /**
     * Tests whether defineInputForeignKey() builds a foreign key which
     * uses the Table information passed in order to provide the metadata
     * about each one of the attributes.
     */
    @Test
    public void defineInputForeignKeys_takes_into_account_the_table_data()
    {
        @NotNull final ForeignKeyTestHelper instance = ForeignKeyTestHelper.getInstance();

        @NotNull final String[] columnNames = { "source", "column(s)", "target", "allows null" };
        @NotNull final Map<String, String> row = new HashMap<>(4);
        row.put("source", "G_CYCLE_TYPES");
        row.put("column(s)", "G_FIRST_DRAW_TYPE_ID");
        row.put("target", "G_DRAWS");
        row.put("allows null", "false");
        @NotNull final List<?> data = Arrays.asList(row);
        @NotNull final DataTable dataTable = DataTable.create(data, Locale.getDefault(), columnNames);

        @NotNull final List<ForeignKey<String>> foreignKeys = new ArrayList<>();

        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table =
            new TableIncompleteValueObject("G_CYCLE_TYPES", "")
            {
                /**
                 * {@inheritDoc}
                 */
                @Override
                @NotNull
                public List<Attribute<String>> getAttributes()
                {
                    return
                        Arrays.<Attribute<String>>asList(
                            new AttributeValueObject(
                                "G_FIRST_DRAW_TYPE_ID",
                                Types.BIGINT,
                                "long",
                                "G_CYCLE_TYPES",
                                null,
                                1,
                                10,
                                1,
                                null,
                                null,
                                null,
                                false,
                                null,
                                false,
                                false,
                                null,
                                null,
                                null));
                };
            };

        @NotNull final Map<String, Table<String, Attribute<String>, List<Attribute<String>>>> tableMap =
            new HashMap<>(1);
        tableMap.put("G_CYCLE_TYPES", table);
        instance.defineInputForeignKey(dataTable, foreignKeys, tableMap);

        Assert.assertTrue(foreignKeys.size() == 1);

        @NotNull final List<Attribute<String>> attributes = foreignKeys.get(0).getAttributes();
        Assert.assertTrue(attributes.size() == 1);
        @NotNull final Attribute<String> attribute = attributes.get(0);
        Assert.assertEquals("G_FIRST_DRAW_TYPE_ID", attribute.getName());
        Assert.assertEquals(Types.BIGINT, attribute.getTypeId());
        Assert.assertEquals("long", attribute.getType());
        Assert.assertEquals("G_CYCLE_TYPES", attribute.getTableName());
        Assert.assertNull(attribute.getComment());
        Assert.assertEquals(1, attribute.getOrdinalPosition());
        Assert.assertEquals(10, attribute.getLength());
        Assert.assertEquals(1, attribute.getPrecision());
        Assert.assertNull(attribute.getKeyword());
        Assert.assertNull(attribute.getRetrievalQuery());
        Assert.assertNull(attribute.getSequence());
        Assert.assertNull(attribute.getValue());
        Assert.assertFalse(attribute.isReadOnly());
        Assert.assertFalse(attribute.isNullable());
        Assert.assertFalse(attribute.isBoolean());
        Assert.assertNull(attribute.getBooleanTrue());
        Assert.assertNull(attribute.getBooleanFalse());
        Assert.assertNull(attribute.getBooleanNull());

    }
}
