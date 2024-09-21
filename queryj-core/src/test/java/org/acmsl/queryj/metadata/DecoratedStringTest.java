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
 * Filename: DecoratedStringTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for DecoratedString.
 *
 * Date: 2014/04/10
 * Time: 09:20
 *
 */
package org.acmsl.queryj.metadata;

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

/**
 * Tests for {@link DecoratedString}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/10 09:20
 */
@RunWith(JUnit4.class)
public class DecoratedStringTest
{
    /**
     * Checks isEmpty(String) work for empty Strings.
     */
    @Test
    public void isEmpty_works_for_empty_strings()
    {
        @NotNull final DecoratedString instance = new DecoratedString("");

        Assert.assertTrue(instance.isEmpty());
    }

    /**
     * Checks isEmpty(String) work for blank Strings.
     */
    @Test
    public void isEmpty_works_for_blank_strings()
    {
        @NotNull final DecoratedString instance = new DecoratedString("  ");

        Assert.assertTrue(instance.isEmpty());
    }

    /**
     * Checks isEmpty(String) work for regular Strings.
     */
    @Test
    public void isEmpty_works_for_non_blank_strings()
    {
        @NotNull final DecoratedString instance = new DecoratedString("test");

        Assert.assertFalse(instance.isEmpty());
    }

    /**
     * Checks whether capitalize() preserves camel case.
     */
    @Test
    public void capitalize_preserves_camelCase()
    {
        @NotNull final DecoratedString instance = new DecoratedString("test.with.CamelCase.example");

        Assert.assertEquals("TestWithCamelCaseExample", instance.getCapitalized().getValue());
    }

    /**
     * Checks whether isTrue works for "true".
     */
    @Test
    public void isTrue_works_for_true_literals()
    {
        @NotNull final DecoratedString instance = new DecoratedString("true");

        Assert.assertTrue(instance.isTrue());
    }

    /**
     * Checks whether isFalse returns true for anything but "true".
     */
    @Test
    public void isFalse_returns_true_for_literals_different_than_true()
    {
        @NotNull final DecoratedString instance = new DecoratedString("false");

        Assert.assertTrue(instance.isFalse());

        @NotNull final DecoratedString somethingElse = new DecoratedString("something else");

        Assert.assertTrue(somethingElse.isFalse());
    }

    /**
     * Checks whether normalize() replaces non-alphanumeric characters
     * with underscores.
     */
    @Test
    public void normalize_replaces_nonalphanumeric_with_underscores()
    {
        @NotNull final DecoratedString instance = new DecoratedString("A-Constant");

        Assert.assertEquals("A_Constant", instance.getNormalized().getValue());

        @NotNull final DecoratedString instance2 = new DecoratedString("A Constant");

        Assert.assertEquals("A_Constant", instance2.getNormalized().getValue());

        @NotNull final DecoratedString instance3 = new DecoratedString("A_Constant");

        Assert.assertEquals("A_Constant", instance3.getNormalized().getValue());
    }
}
