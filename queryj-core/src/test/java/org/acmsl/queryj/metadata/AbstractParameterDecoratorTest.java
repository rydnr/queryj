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
 * Filename: AbstractParameterDecoratorTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for AbstractParameterDecorator.
 *
 * Date: 2014/05/17
 * Time: 18:06
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.ParameterElement;
import org.acmsl.queryj.metadata.engines.JdbcMetadataTypeManager;

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
 * Tests for {@link AbstractParameterDecorator}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/17 18:06
 */
@RunWith(JUnit4.class)
public class AbstractParameterDecoratorTest
{
    /**
     * Tests whether isDate() detects Date parameters.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void isDate_detects_date_parameters()
    {
        @NotNull final AbstractParameterDecorator<String> instance =
            new AbstractParameterDecorator(
                new ParameterElement<String, String>("p1", 1, "p1", "Date", null),
                new JdbcMetadataTypeManager()) {};

        Assert.assertTrue(instance.isDate());

    }

    /**
     * Tests whether isDate() detects non-Date parameters.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void isDate_detects_non_date_parameters()
    {
        @NotNull final AbstractParameterDecorator<String> instance =
            new AbstractParameterDecorator(
                new ParameterElement<String, String>("p1", 1, "p1", "String", null),
                new JdbcMetadataTypeManager()) {};

        Assert.assertFalse(instance.isDate());

    }
}
