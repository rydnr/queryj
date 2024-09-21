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
 * Filename: StaticValuesHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for StaticValuesHandler.
 *
 * Date: 2014/03/27
 * Time: 07:18
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.PerTableTemplateContext;
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.metadata.vo.RowValueObject;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JUnit/PowerMock/EasyMock classes.
 */
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/*
 * Importing Apache-Commons Configuration classes.
 */
import org.apache.commons.configuration.MapConfiguration;

/**
 * Tests for {@link StaticValuesHandler}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/27 07:18
 */
@RunWith(PowerMockRunner.class)
public class StaticValuesHandlerTest
{
    protected class TestablePerTableTemplateContext
        extends PerTableTemplateContext {

        /**
         * Creates an empty context.
         * @return such instance.
         */
        public TestablePerTableTemplateContext() {
            super(
                  "test_table",
                  Arrays.<Row<String>>asList(new RowValueObject("row1", "table1", new ArrayList<Attribute<String>>(0))),
                  false,
                  new ConfigurationQueryJCommandImpl(new MapConfiguration(new HashMap())));
        }
    }

    @Test
    public void returns_the_expected_placeholder()
    {
        @NotNull final PerTableTemplateContext t_Context = EasyMock.createNiceMock(TestablePerTableTemplateContext.class);

        @NotNull final StaticValuesHandler instance =
            new StaticValuesHandler(t_Context);

        Assert.assertEquals("static_values", instance.getPlaceHolder());
    }

    /**
     * Retrieves static values.
     * @param tableName the table name.
     * @return such values.
     */
    protected List<Row<String>> build_static_values(@NotNull final String tableName)
    {
        return Arrays.<Row<String>>asList(new RowValueObject("row1", tableName, new ArrayList<Attribute<String>>(0)));
    }

    @Test
    public void returns_the_expected_values()
        throws QueryJBuildException
    {
        @NotNull final List<Row<String>> t_lStaticValues = new TestablePerTableTemplateContext().getStaticValues();

        @NotNull final PerTableTemplateContext t_Context = EasyMock.createNiceMock(TestablePerTableTemplateContext.class);

        EasyMock.expect(t_Context.getStaticValues()).andReturn(t_lStaticValues);
        EasyMock.replay(t_Context);

        @NotNull final StaticValuesHandler instance =
            new StaticValuesHandler(t_Context);

        Assert.assertEquals(t_lStaticValues, instance.getValue());

        EasyMock.verify(t_Context);
    }
}
