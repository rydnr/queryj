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
 * Filename: QueryJVersionHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for QueryJVersionHandler.
 *
 * Date: 2014/03/30
 * Time: 18:38
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.PerTableTemplateContext;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.metadata.DecoratedString;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JUnit/EasyMock classes.
 */
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link QueryJVersionHandler}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/30 18:38
 */
@ThreadSafe
public class QueryJVersionHandlerTest
{
    /**
     * Checks whether the handler returns the expected placeholder.
     */
    @Test
    public void returns_the_expected_placeholder()
    {
        @NotNull final PerTableTemplateContext t_Context =
            EasyMock.createNiceMock(PerTableTemplateContext.class);

        @NotNull final QueryJVersionHandler instance =
            new QueryJVersionHandler<>(t_Context);

        Assert.assertEquals("version", instance.getPlaceHolder());
    }

    /**
     * Checks whether the handler returns the expected value.
     */
    @Test
    public void returns_the_expected_values()
        throws QueryJBuildException
    {
        @NotNull final String t_strVersion = "3.0";

        @NotNull final PerTableTemplateContext t_Context =
            EasyMock.createNiceMock(PerTableTemplateContext.class);

        EasyMock.expect(t_Context.getVersion()).andReturn(t_strVersion);
        EasyMock.replay(t_Context);

        @NotNull final QueryJVersionHandler<PerTableTemplateContext> instance =
            new QueryJVersionHandler<>(t_Context);

        @Nullable final DecoratedString t_Value = instance.getValue();

        Assert.assertNotNull(t_Value);
        Assert.assertEquals(t_strVersion, t_Value.getValue());

        EasyMock.verify(t_Context);
    }
}
