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
 * Filename: TemplateFactoryTemplateFactoryTest.java
 *
 * Author: Jose San Leandro
 *
 * Description: Tests for TemplateFactoryTemplateFactory.
 *
 * Created: 2014/04/14 17:54
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JUnit/EasyMock classes.
 */
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link TemplateFactoryTemplateFactory}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created 2014/04/14
 */
@RunWith(JUnit4.class)
public class TemplateFactoryTemplateFactoryTest
    extends TemplateFactoryTestHelper<
                TemplateFactoryTemplateFactory,
                TemplateFactoryTemplate<DefaultTemplatePackagingContext>,
                DefaultTemplatePackagingContext>
{
    /**
     * Creates a new nice mock of {@link DefaultTemplatePackagingContext}.
     * @return such instance.
     */
    @Override
    protected DefaultTemplatePackagingContext createContext()
    {
        return EasyMock.createNiceMock(DefaultTemplatePackagingContext.class);
    }

    /**
     * Creates a new {@link TemplateWritingHandlerTemplateFactory} instance.
     * @return such instance.
     */
    @Override
    protected TemplateFactoryTemplateFactory createFactory()
    {
        return new TemplateFactoryTemplateFactory();
    }

    /**
     * Checks whether the createTemplate() method returns the correct template
     * for a per-repository type.
     */
    @Test
    public void createTemplate_returns_the_correct_template_for_a_per_repository_template()
    {
        testCreateTemplate(TemplateDefType.PER_REPOSITORY, "TemplateFactory");
    }

    /**
     * Checks whether the createTemplate() method returns the correct template
     * for a per-table type.
     */
    @Test
    public void createTemplate_returns_the_correct_template_for_a_per_table_template()
    {
        testCreateTemplate(TemplateDefType.PER_TABLE, "TemplateFactory");
    }

    /**
     * Checks whether the createTemplate() method returns the correct template
     * for a per-foreign-key type.
     */
    @Test
    public void createTemplate_returns_the_correct_template_for_a_per_foreign_key_template()
    {
        testCreateTemplate(TemplateDefType.PER_FOREIGN_KEY, "PerForeignKeyTemplateFactory");
    }

    /**
     * Checks whether the createTemplate() method returns the correct template
     * for a per-custom-result type.
     */
    @Test
    public void createTemplate_returns_the_correct_template_for_a_per_custom_result_template()
    {
        testCreateTemplate(TemplateDefType.PER_CUSTOM_RESULT, "TemplateFactory");
    }
}
