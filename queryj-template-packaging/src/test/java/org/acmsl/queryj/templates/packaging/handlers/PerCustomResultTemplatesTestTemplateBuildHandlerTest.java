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
 * Filename: PerCustomResultTemplatesTestTemplateBuildHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for PerCustomResultTemplatesTestTemplateBuildHandler.
 *
 * Date: 2014/04/17
 * Time: 08:05
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.Literals;
import org.acmsl.queryj.templates.packaging.PerCustomResultTemplatesTestTemplate;
import org.acmsl.queryj.templates.packaging.PerCustomResultTemplatesTestTemplateFactory;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link PerCustomResultTemplatesTestTemplateBuildHandler}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/17 08:05
 */
@RunWith(JUnit4.class)
public class PerCustomResultTemplatesTestTemplateBuildHandlerTest
    extends AbstractTemplatesTestTemplateBuildHandlerTest<
                PerCustomResultTemplatesTestTemplateBuildHandler,
                PerCustomResultTemplatesTestTemplate,
                PerCustomResultTemplatesTestTemplateFactory>
{
    /**
     * Creates a new build handler instance.
     * @return such new instance.
     */
    @NotNull
    @Override
    protected PerCustomResultTemplatesTestTemplateBuildHandler createInstance()
    {
        return new PerCustomResultTemplatesTestTemplateBuildHandler();
    }

    /**
     * Retrieves a new template mock.
     * @return such mock.
     */
    @NotNull
    @Override
    protected PerCustomResultTemplatesTestTemplate createTemplateMock()
    {
        return EasyMock.createNiceMock(PerCustomResultTemplatesTestTemplate.class);
    }

    /**
     * Tests whether retrieveTemplateName() works.
     */
    @Override
    @Test
    public void retrieveTemplateName_works()
    {
        retrieveTemplateName_works(Literals.PER_CUSTOM_RESULT_TEMPLATES_TEST);
    }

    /**
     * Checks whether storeTemplate() binds the template to the command.
     */
    @Override
    @Test
    public void storeTemplate_stores_the_templates_in_the_command()
    {
        storeTemplate_stores_the_templates_in_the_command(
            PerCustomResultTemplatesTestTemplateBuildHandler.TEMPLATES_KEY);
    }

    /**
     * Checks whether retrieveTemplateFactory() retrieves
     * {@link org.acmsl.queryj.templates.packaging.PerCustomResultTemplatesFeatureTemplateFactory}.
     */
    @Override
    @Test
    public void retrieveTemplateFactory_retrieves_the_correct_factory()
    {
        retrieveTemplateFactory_retrieves_the_correct_factory(
            PerCustomResultTemplatesTestTemplateFactory.getInstance());
    }

}
