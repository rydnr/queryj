/*
                        queryj

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
 * Filename: TemplateBuildHandlerTemplateFactoryTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for TemplateBuildHandlerTemplateFactory.
 *
 * Date: 2014/04/23
 * Time: 18:13
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JetBrains annotations.
 */
import org.easymock.EasyMock;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.junit.Test;

/**
 * Tests for {@link TemplateBuildHandlerTemplateFactory}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/23 18:13
 */
@ThreadSafe
public class TemplateBuildHandlerTemplateFactoryTest
    extends TemplateFactoryTestHelper<
                TemplateBuildHandlerTemplateFactory,
                TemplateBuildHandlerTemplate<DefaultTemplatePackagingContext>,
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
    protected TemplateBuildHandlerTemplateFactory createFactory()
    {
        return new TemplateBuildHandlerTemplateFactory();
    }

    /**
     * Checks whether the createTemplate() method returns the correct template
     * for a per-repository type.
     */
    @Test
    public void createTemplate_returns_the_correct_template_for_a_per_repository_template()
    {
        testCreateTemplate(TemplateDefType.PER_REPOSITORY, Literals.PER_REPOSITORY_TEMPLATE_BUILD_HANDLER);
    }

    /**
     * Checks whether the createTemplate() method returns the correct template
     * for a per-table type.
     */
    @Test
    public void createTemplate_returns_the_correct_template_for_a_per_table_template()
    {
        testCreateTemplate(TemplateDefType.PER_TABLE, Literals.TEMPLATE_BUILD_HANDLER);
    }

    /**
     * Checks whether the createTemplate() method returns the correct template
     * for a per-foreign-key type.
     */
    @Test
    public void createTemplate_returns_the_correct_template_for_a_per_foreign_key_template()
    {
        testCreateTemplate(TemplateDefType.PER_FOREIGN_KEY, Literals.TEMPLATE_BUILD_HANDLER);
    }

    /**
     * Checks whether the createTemplate() method returns the correct template
     * for a per-custom-result type.
     */
    @Test
    public void createTemplate_returns_the_correct_template_for_a_per_custom_result_template()
    {
        testCreateTemplate(TemplateDefType.PER_CUSTOM_RESULT, Literals.TEMPLATE_BUILD_HANDLER);
    }

    /**
     * Checks whether the createTemplate() method returns the correct template
     * for a per-sql type.
     */
    @Test
    public void createTemplate_returns_the_correct_template_for_a_per_sql_template()
    {
        testCreateTemplate(TemplateDefType.PER_CUSTOM_SQL, Literals.TEMPLATE_BUILD_HANDLER);
    }
}