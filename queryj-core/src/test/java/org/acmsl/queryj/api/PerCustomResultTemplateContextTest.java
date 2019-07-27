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
 * Filename: PerCustomResultTemplateContextTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for PerCustomResultTemplateContext.
 *
 * Date: 2014/04/18
 * Time: 17:34
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.SerializablePropertiesConfiguration;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyElement;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultElement;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for {@link PerCustomResultTemplateContext}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/18 17:34
 */
@RunWith(JUnit4.class)
public class PerCustomResultTemplateContextTest
{
    /**
     * Tests the properties are correctly stored.
     */
    @Test
    public void properties_are_stored_correctly_in_the_command()
    {
        @NotNull final List<Property<String>> properties = new ArrayList<Property<String>>(0);

        properties.add(new PropertyElement<String>("id", "columnName", 1, "type", false));

        @NotNull final Result<String> result = new ResultElement<String>("id", "class");

        @NotNull final QueryJCommand command =
            new ConfigurationQueryJCommandImpl(new SerializablePropertiesConfiguration());

        @NotNull final PerCustomResultTemplateContext instance =
            new PerCustomResultTemplateContext(result, properties, false, command);

        Assert.assertEquals(properties, instance.getProperties());

        @NotNull final Result<String> result2 = new ResultElement<String>("id2", "class");

        @NotNull final PerCustomResultTemplateContext instance2 =
            new PerCustomResultTemplateContext(result2, new ArrayList<Property<String>>(0), false, command);

        Assert.assertEquals(properties, instance.getProperties());
        Assert.assertNotEquals(properties, instance2.getProperties());
    }
}
