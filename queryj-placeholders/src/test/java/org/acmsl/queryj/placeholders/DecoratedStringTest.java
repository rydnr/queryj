/*
                        QueryJ Placeholders

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
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Tests some methods on DecoratedString.
 *
 * Date: 5/13/13
 * Time: 9:04 PM
 *
 */
package org.acmsl.queryj.placeholders;

import junit.framework.TestCase;
import org.acmsl.queryj.metadata.DecoratedString;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests some methods on DecoratedString.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/05/13
 */
@RunWith(JUnit4.class)
@SuppressWarnings("unused")
public class DecoratedStringTest
    extends TestCase
{
    /**
     * Tests {@link org.acmsl.queryj.metadata.DecoratedString#getCapitalized()}
     */
    @Test
    public void testCapitalized()
    {
        @NotNull final DecoratedString decorator = new DecoratedString("E-Commerce");

        Assert.assertEquals("Invalid capitalization for E-Commerce", "ECommerce", decorator.getCapitalized().getValue());
    }

    /**
     * Tests {@link DecoratedString#getUppercased()}
     */
    @Test
    public void testUppercased()
    {
        @NotNull final DecoratedString decorator = new DecoratedString("E-Commerce");

        Assert.assertEquals(
            "Invalid upper-case transformation for E-Commerce",
            "E-COMMERCE",
            decorator.getUppercased().getValue());
    }

    /**
     * Tests {@code DecoratedString#getNormalized().getUppercased()}
     */
    @Test
    public void testNormalizedUppercased()
    {
        @NotNull final DecoratedString decorator = new DecoratedString("E-Commerce");

        Assert.assertEquals(
            "Invalid normalized upper-case transformation for E-Commerce",
            "E_COMMERCE",
            decorator.getNormalized().getUppercased().getValue());
    }

    /**
     * Tests {@code DecoratedString#getCamelCase()}
     */
    @Test
    public void camelcase_works()
    {
        @NotNull final DecoratedString decorator = new DecoratedString("E_COMMERCE_RELOADED");

        @NotNull final String camelCased = decorator.getCamelCase().getValue();

        Assert.assertEquals(
            "Invalid camel-case transformation for E_COMMERCE_RELOADED",
            "ECommerceReloaded",
            camelCased);
    }

    /**
     * Tests {@code DecoratedString#getSingular().getCamelCase()}
     */
    @Test
    public void camelcase_works_also_when_converting_from_plural_to_singular()
    {
        @NotNull final DecoratedString decorator = new DecoratedString("THREE_HOUSES");

        @NotNull final String camelCased = decorator.getSingular().getCamelCase().getValue();

        Assert.assertEquals(
            "Invalid camel-case transformation for THREE_HOUSES",
            "ThreeHouse",
            camelCased);
    }

    /**
     * Tests {@code DecoratedString#getSingular().getCamelCase()}
     */
    @Test
    public void singleLine_works()
    {
        @NotNull final DecoratedString decorator = new DecoratedString("something\nwith\nmultiple\ncarriage\nreturns");

        @NotNull final String singleLine = decorator.getSingleLine().getValue();

        Assert.assertEquals(
            "Invalid camel-case transformation for \"something\\nwith\\nmultiple\\ncarriage\\nreturns\"",
            "something with multiple carriage returns",
            singleLine);
    }
}
