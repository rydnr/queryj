/*
                        QueryJ

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
package org.acmsl.queryj.templates.handlers.fillhandlers;

import junit.framework.TestCase;
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
     * Tests {@link DecoratedString#getCapitalized()}
     */
    @Test
    public void testCapitalized()
    {
        DecoratedString decorator = new DecoratedString("E-Commerce");

        Assert.assertEquals("Invalid capitalization for E-Commerce", "ECommerce", decorator.getCapitalized());
    }

    /**
     * Tests {@link DecoratedString#getUppercased()}
     */
    @Test
    public void testUppercased()
    {
        DecoratedString decorator = new DecoratedString("E-Commerce");

        Assert.assertEquals("Invalid upper-case transformation for E-Commerce", "E-COMMERCE", decorator.getUppercased());
    }

    /**
     * Tests {@link DecoratedString#getNormalizedUppercased()}
     */
    @Test
    public void testNormalizedUppercased()
    {
        DecoratedString decorator = new DecoratedString("E-Commerce");

        Assert.assertEquals("Invalid upper-case transformation for E-Commerce", "E_COMMERCE", decorator.getNormalizedUppercased());
    }
}
