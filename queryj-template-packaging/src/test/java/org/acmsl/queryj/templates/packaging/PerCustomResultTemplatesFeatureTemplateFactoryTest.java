/*
                        QueryJ Template Packaging

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
 * Filename: PerCustomResultTemplatesFeatureTemplateFactoryTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for PerCustomResultTemplatesFeatureTemplateFactory.
 *
 * Date: 2014/04/16
 * Time: 15:40
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.metadata.engines.UndefinedJdbcEngine;
import org.easymock.EasyMock;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link PerCustomResultTemplatesFeatureTemplateFactory}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/16 15:40
 */
@ThreadSafe
public class PerCustomResultTemplatesFeatureTemplateFactoryTest
{
    /**
     * Tests whether the createTemplate() method returns null.
     */
    @Test
    public void createTemplate_returns_not_null()
    {
        @NotNull final GlobalTemplateContext context = EasyMock.createNiceMock(GlobalTemplateContext.class);

        @NotNull final PerCustomResultTemplatesFeatureTemplateFactory instance =
            PerCustomResultTemplatesFeatureTemplateFactory.getInstance();

        Assert.assertNotNull(instance.createTemplate(context));
    }

    /**
     * Checks whether retrieveTemplateFileName() returns "PerCustomResultTemplates.feature".
     */
    @Test
    public void retrieveTemplateFileName_retrieves_the_fixed_value()
    {
        @NotNull final PerCustomResultTemplatesFeatureTemplateFactory instance =
            PerCustomResultTemplatesFeatureTemplateFactory.getInstance();

        Assert.assertEquals(
            "PerCustomResultTemplates.feature",
            instance.retrieveTemplateFileName("repo", new UndefinedJdbcEngine("unknown", "1.0")));
    }

}
