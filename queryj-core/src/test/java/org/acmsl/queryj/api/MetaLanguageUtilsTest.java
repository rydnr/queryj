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
 * Filename: MetaLanguageUtilsTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests MetaLanguageUtils logic.
 *
 * Date: 2014/02/04
 * Time: 06:43
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing JUnit classes.
 */
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Tests MetaLanguageUtils logic.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/02/04 06:43
 */
@ThreadSafe
@SuppressWarnings("unused")
@RunWith(JUnit4.class)
public class MetaLanguageUtilsTest
{
    /**
     * Tests MetaLanguageUtils#retrieveTableDecorator(String)
     */
    @Test
    public void testRetrieveTableDecorator()
    {
        @NotNull final MetaLanguageUtils metaLanguageUtils = MetaLanguageUtils.getInstance();

        Assert.assertNotNull("Null MetaLanguageUtils instance", metaLanguageUtils);

        Assert.assertFalse("'Table BLA.' is not decorated", metaLanguageUtils.retrieveTableDecorator("Table BLA."));
    }
}
