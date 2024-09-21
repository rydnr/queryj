/*
                        QueryJ Test

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
 * Filename: AbstractTemplatesTestTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for AbstractTemplatesTest.
 *
 * Date: 2014/05/01
 * Time: 17:19
 *
 */
package org.acmsl.queryj.test;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.metadata.DecoratorFactory;

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
 * Tests for {@link AbstractTemplatesTest}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/01 17:19
 */
@RunWith(JUnit4.class)
public class AbstractTemplatesTestTest
{
    /**
     * Checks whether the retrieveTemplateDef() method actually
     * retrieves a {@code TemplateDef}.
     */
    @Test
    public void retrieveTemplateDef_finds_the_templateDef()
    {
        @NotNull final AbstractTemplatesTest instance = createInstance();

        Assert.assertNotNull(instance.retrieveTemplateDef("MyTemplate"));
    }

    /**
     * Creates a new instance.
     * @return the new instance.
     */
    @NotNull
    protected AbstractTemplatesTest createInstance()
    {
        return new AbstractTemplatesTest()
        {
            /**
             * {@inheritDoc}
             */
            @NotNull
            @Override
            protected DecoratorFactory retrieveDecoratorFactory(@NotNull final Object generator)
            {
                return CachingDecoratorFactory.getInstance();
            }
        };
    }

}
