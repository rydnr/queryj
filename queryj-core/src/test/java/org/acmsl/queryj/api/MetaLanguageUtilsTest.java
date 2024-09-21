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
 * Filename: MetaLanguageUtilsTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests MetaLanguageUtils logic.
 *
 * Date: 2014/02/04
 * Time: 06:43
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing JUnit classes.
 */
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

import java.util.List;

/**
 * Tests MetaLanguageUtils logic.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/02/04 06:43
 */
@ThreadSafe
@SuppressWarnings("unused")
@RunWith(JUnit4.class)
public class MetaLanguageUtilsTest
{
    /**
     * String literal.
     */
    public static final String NULL_META_LANGUAGE_UTILS_INSTANCE = "Null MetaLanguageUtils instance";
    /**
     * String literal.
     */
    public static final String TABLE_BLA = "Table BLA.";
    /**
     * String literal.
     */
    protected static final String IS_PARSED_AS_STATIC = "' is parsed as static";

    /**
     * Tests MetaLanguageUtils#retrieveStaticAttribute(String)
     */
    @Test
    public void testRetrieveStaticAttribute()
    {
        @NotNull final MetaLanguageUtils metaLanguageUtils = MetaLanguageUtils.getInstance();

        Assert.assertNotNull(NULL_META_LANGUAGE_UTILS_INSTANCE, metaLanguageUtils);

        Assert.assertFalse("'Table BLA.' is not static", metaLanguageUtils.retrieveStaticAttribute(TABLE_BLA) != null);

        @NotNull final String staticTableComment = "Table @static name";

        final String staticAttribute = metaLanguageUtils.retrieveStaticAttribute(staticTableComment);

        Assert.assertNotNull("'" + staticTableComment + "' is not parsed as static", staticAttribute);

        Assert.assertNotNull(
            "The static attribute of '" + staticTableComment + "' is not name", "name".equals(staticAttribute));

        @NotNull String commaComment = "The users, or customers.";

        String comment = metaLanguageUtils.retrieveStaticAttribute(commaComment);

        Assert.assertNull("'" + commaComment + IS_PARSED_AS_STATIC, comment);

        commaComment = "The bettors, usually users (see um_users) or clubs.";

        comment = metaLanguageUtils.retrieveStaticAttribute(commaComment);

        Assert.assertNull("'" + commaComment + IS_PARSED_AS_STATIC, comment);

    }

    /**
     * Tests MetaLanguageUtils#retrieveDeclaredParent(String)
     */
    @Test
    public void testRetrieveDeclaredParent()
    {
        @NotNull final MetaLanguageUtils metaLanguageUtils = MetaLanguageUtils.getInstance();

        Assert.assertNotNull(NULL_META_LANGUAGE_UTILS_INSTANCE, metaLanguageUtils);

        Assert.assertFalse(
            "'Table BLA.' does not have parent", metaLanguageUtils.retrieveDeclaredParent(TABLE_BLA) != null);

        @NotNull final String tableComment = "Table @isa parent";

        @Nullable final String parent = metaLanguageUtils.retrieveDeclaredParent(tableComment);

        Assert.assertNotNull("'" + tableComment + "' is not parsed as a child of 'parent'", parent);

        Assert.assertNotNull("The parent table of '" + tableComment + "' is not 'parent'", "parent".equals(parent));
    }

    /**
     * Tests MetaLanguageUtils#retrieveDiscriminatingParent(String)
     */
    @Test
    public void testRetrieveDiscriminatingParent()
    {
        @NotNull final MetaLanguageUtils metaLanguageUtils = MetaLanguageUtils.getInstance();

        Assert.assertNotNull(NULL_META_LANGUAGE_UTILS_INSTANCE, metaLanguageUtils);

        Assert.assertFalse(
            "'Table BLA.' does not have a discriminating parent",
            metaLanguageUtils.retrieveStaticAttribute(TABLE_BLA) != null);

        @NotNull final String tableComment = "Table @isatype parent_ref";

        @Nullable final String parent = metaLanguageUtils.retrieveDiscriminatingParent(tableComment);

        Assert.assertNotNull(
            "'" + tableComment + "' is not parsed to have a discriminating parent 'parent_ref'", parent);

        Assert.assertNotNull(
            "The discriminating parent of '" + tableComment + "' is not 'parent_ref'", "parent_ref".equals(parent));
    }

    /**
     * Tests MetaLanguageUtils#retrieveTableDecorator(String)
     */
    @Test
    public void testRetrieveTableDecorator()
    {
        @NotNull final MetaLanguageUtils metaLanguageUtils = MetaLanguageUtils.getInstance();

        Assert.assertNotNull(NULL_META_LANGUAGE_UTILS_INSTANCE, metaLanguageUtils);

        Assert.assertFalse("'Table BLA.' is decorated", metaLanguageUtils.retrieveTableDecorator(TABLE_BLA));

        @NotNull final String tableComment = "Table @decorator";

        Assert.assertTrue(
            "'" + tableComment + "' is not decorated", metaLanguageUtils.retrieveTableDecorator(tableComment));
    }

    /**
     * Tests MetaLanguageUtils#retrieveTableRelationship(String)
     */
    @Test
    public void testRetrieveTableRelationShip()
    {
        @NotNull final MetaLanguageUtils metaLanguageUtils = MetaLanguageUtils.getInstance();

        Assert.assertNotNull(NULL_META_LANGUAGE_UTILS_INSTANCE, metaLanguageUtils);

        @Nullable final List<List<String>> nonRelationship =
            metaLanguageUtils.retrieveTableRelationship(TABLE_BLA);

        Assert.assertTrue("'Table BLA.' is parsed as relationship", nonRelationship.size() == 0);

        @NotNull final String tableComment = "Table @relationship (tab1.col1, tab2.col2)";

        @Nullable final List<List<String>> relationship =
            metaLanguageUtils.retrieveTableRelationship(tableComment);

        Assert.assertNotNull(
            "'" + tableComment + "' is not parsed as relationship",
            relationship);
    }

    /**
     * Tests MetaLanguageUtils#retrieveColumnBool(String)
     */
    @Test
    public void testRetrieveColumnBool()
    {
        @NotNull final MetaLanguageUtils metaLanguageUtils = MetaLanguageUtils.getInstance();

        Assert.assertNotNull(NULL_META_LANGUAGE_UTILS_INSTANCE, metaLanguageUtils);

        @NotNull final String[] nonBoolValues = metaLanguageUtils.retrieveColumnBool("Column Y");
        Assert.assertFalse("'Column Y' is parsed as boolean", nonBoolValues.length != 0);

        @NotNull final String columnComment = "Column @bool 0,1,2";

        @NotNull final String[] boolValues = metaLanguageUtils.retrieveColumnBool(columnComment);
        Assert.assertTrue(
            "'" + columnComment + "' is not parsed correctly. It doesn't find 3 values", boolValues.length == 3);
        Assert.assertTrue(
            "'" + columnComment + "' is not parsed correctly. The first value is not 0", "0".equals(boolValues[0]));
        Assert.assertTrue(
            "'" + columnComment + "' is not parsed correctly. The second value is not 1", "1".equals(boolValues[1]));
        Assert.assertTrue(
            "'" + columnComment + "' is not parsed correctly. The third value is not 2", "2".equals(boolValues[2]));
    }

    /**
     * Tests MetaLanguageUtils#retrieveColumnReadOnly(String)
     */
    @Test
    public void testRetrieveColumnReadOnly()
    {
        @NotNull final MetaLanguageUtils metaLanguageUtils = MetaLanguageUtils.getInstance();

        Assert.assertNotNull(NULL_META_LANGUAGE_UTILS_INSTANCE, metaLanguageUtils);

        Assert.assertFalse("'Column X' is parsed as read-only", metaLanguageUtils.retrieveColumnReadOnly("Column X"));

        @NotNull final String columnComment = "Column @readonly";
        Assert.assertTrue(
            "'" + columnComment + "' is not parsed as read-only",
            metaLanguageUtils.retrieveColumnReadOnly(columnComment));
    }

    /**
     * Tests MetaLanguageUtils#retrieveColumnDiscriminatedTables(String)
     */
    @Test
    public void testRetrieveColumnDiscriminatedTables()
    {
        @NotNull final MetaLanguageUtils metaLanguageUtils = MetaLanguageUtils.getInstance();

        Assert.assertNotNull(NULL_META_LANGUAGE_UTILS_INSTANCE, metaLanguageUtils);

        @Nullable List<List<String>> discriminatedTables =
            metaLanguageUtils.retrieveColumnDiscriminatedTables("Column Z");

        Assert.assertNotNull("'Column Z' is parsed as an isaref", discriminatedTables);

        @NotNull final String columnComment = "Column @isarefs (child1, 'c1'),(child2,'c2')";

        discriminatedTables =
            metaLanguageUtils.retrieveColumnDiscriminatedTables(columnComment);

        Assert.assertTrue(
            "'" + columnComment + "' is not parsed as an isarefs",
            discriminatedTables.size() == 2);
    }
}
