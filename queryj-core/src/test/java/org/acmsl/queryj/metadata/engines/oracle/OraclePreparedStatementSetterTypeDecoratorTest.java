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
 * Filename: OraclePreparedStatementSetterTypeDecoratorTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for OraclePreparedStatementSetterTypeDecorator.
 *
 * Date: 2014/02/22
 * Time: 20:27
 *
 */
package org.acmsl.queryj.metadata.engines.oracle;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.metadata.Literals;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/*
 * Importing JDK classes.
 */
import java.lang.reflect.Method;
import java.sql.Array;
import java.sql.PreparedStatement;

/**
 * Tests for {@link OraclePreparedStatementSetterTypeDecorator}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/02/22 20:27
 */
@RunWith(JUnit4.class)
public class OraclePreparedStatementSetterTypeDecoratorTest
{
    /**
     * Tests for getSetterMethod(String).
     */
    @Test
    public void getSetterMethodWorksForArray()
        throws Exception
    {
        @NotNull final OraclePreparedStatementSetterTypeDecorator instance =
            new OraclePreparedStatementSetterTypeDecorator();

        @NotNull final Method t_Method = instance.getSetterMethod(Array.class);

        Assert.assertEquals(PreparedStatement.class.getMethod(Literals.SET_ARRAY, int.class, Array.class), t_Method);
    }
}
