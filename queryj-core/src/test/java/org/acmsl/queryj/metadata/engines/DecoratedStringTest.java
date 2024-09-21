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
 * Date: 2014/03/22
 * Time: 17:55
 *
 */
package org.acmsl.queryj.metadata.engines;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.DecoratedString;

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
 * Created: 2014/03/22 17:55
 */
@RunWith(JUnit4.class)
public class DecoratedStringTest
{
    /**
     * Checks whether capitalize() works for upper-case texts.
     */
    @Test
    public void capitalize_works_for_upper_cased_strings()
    {
        Assert.assertEquals("GCycleTypes", new DecoratedString("G_CYCLE_TYPES").getCapitalized().getValue());
        Assert.assertEquals("Name", new DecoratedString("NAME").getCapitalized().getValue());
    }

    /**
     * Checks whether capitalize() works for mixed-case texts.
     */
    @Test
    public void capitalize_works_for_mixed_cased_strings()
    {
        @NotNull final DecoratedString instance = new DecoratedString("GCycleTypes");

        Assert.assertEquals("GCycleTypes", instance.getCapitalized().getValue());
    }

    /**
     * Checks whether getNoExtension() does nothing for texts with dots.
     */
    @Test
    public void getNoExtension_does_nothing_for_texts_with_dots()
    {
        Assert.assertEquals("bla", new DecoratedString("bla.txt").getNoExtension().getValue());
    }

    /**
     * Checks whether getNoExtension() does nothing for texts with no dots.
     */
    @Test
    public void getNoExtension_does_nothing_for_texts_with_no_dots()
    {
        Assert.assertEquals("bla", new DecoratedString("bla").getNoExtension().getValue());
    }

    /**
     * Checks whether shrink() removes all non-alphanumeric characters.
     */
    @Test
    public void shrink_removes_all_non_alphanumeric_characters()
    {
        Assert.assertEquals("bcd", new DecoratedString("b_c_d$").getShrink().getValue());

        Assert.assertEquals("GCYCLETYPES", new DecoratedString("G_CYCLE_TYPES").getShrink().getValue());
    }
}
