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
 * Filename: AbstractPartialListDecoratorTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for AbstractPartialListDecorator.
 *
 * Date: 2014/06/14
 * Time: 17:48
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JUnit/EasyMock classes.
 */
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link AbstractPartialListDecorator}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/06/14 17:48
 */
@RunWith(JUnit4.class)
public class AbstractPartialListDecoratorTest
{
    /**
     * Checks calling "plus()" throws a {@link RuntimeException}.
     */
    @Test
    public void plus_throws_exception()
    {
        @SuppressWarnings("unchecked")
        @NotNull final ListDecorator<Object> listDecorator =
            (ListDecorator<Object>) EasyMock.createNiceMock(ListDecorator.class);

        @NotNull final AbstractPartialListDecorator<Object> instance =
            new AbstractPartialListDecorator<Object>(
                listDecorator, AbstractPartialListDecorator.Operation.PLUS) {};

        try
        {
            instance.plus();
            Assert.fail("plus() didn't throw an exception");
        }
        catch (@NotNull final RuntimeException runtimeException)
        {
        }
    }
}
