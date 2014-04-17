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
 * Filename: AbstractTemplatesTestTemplateBuildHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Common logic and tests to follow a DRY approach when testing
 *              TemplatesTest build handlers.
 *
 * Date: 2014/04/17
 * Time: 07:34
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.AbstractTemplatePackagingTemplate;
import org.acmsl.queryj.templates.packaging.GlobalTemplateContext;
import org.acmsl.queryj.templates.packaging.Literals;
import org.acmsl.queryj.templates.packaging.PerRepositoryTemplatesTestTemplate;
import org.acmsl.queryj.templates.packaging.PerRepositoryTemplatesTestTemplateFactory;
import org.acmsl.queryj.templates.packaging.TemplateDef;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplateFactory;

/*
 * Importing Apache Commons Configuration classes.
 */
import org.apache.commons.configuration.PropertiesConfiguration;

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

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Common logic and tests to follow a DRY approach when testing TemplatesTest build handlers.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/17 07:34
 * @param <BH> the build handler class.
 * @param <T> the template class.
 * @param <TF> the template factory class.
 */
@RunWith(JUnit4.class)
public abstract class AbstractTemplatesTestTemplateBuildHandlerTest
    <BH extends TemplatePackagingTestBuildHandler<T, TF, GlobalTemplateContext>,
        T extends AbstractTemplatePackagingTemplate<GlobalTemplateContext>,
        TF extends TemplatePackagingTemplateFactory<T, GlobalTemplateContext>>
{
    /**
     * Creates a new build handler instance.
     * @return such new instance.
     */
    @NotNull
    protected abstract BH createInstance();

    /**
     * Retrieves a new template mock.
     * @return such mock.
     */
    @NotNull
    protected abstract T createTemplateMock();

    /**
     * Tests whether retrieveTemplateName() works.
     */
    @SuppressWarnings("unused")
    public abstract void retrieveTemplateName_works();

    /**
     * Tests whether retrieveTemplateName() works.
     */
    protected void retrieveTemplateName_works(@NotNull final String templateName)
    {
        @NotNull final BH instance = createInstance();

        @NotNull final QueryJCommand command = EasyMock.createNiceMock(QueryJCommand.class);

        Assert.assertEquals(templateName, instance.retrieveTemplateName(command));
    }

    /**
     * Checks whether storeTemplate() binds the template to the command.
     */
    @SuppressWarnings("unused")
    public abstract void storeTemplate_stores_the_templates_in_the_command();

    /**
     * Checks whether storeTemplate() binds the template to the command.
     * @param commandKey the key in the command where the templates are expected to be stored.
     */
    protected void storeTemplate_stores_the_templates_in_the_command(@NotNull final String commandKey)
    {
        @NotNull final BH instance = createInstance();

        @NotNull final QueryJCommand command =
            new ConfigurationQueryJCommandImpl(new PropertiesConfiguration());

        @NotNull final T template = createTemplateMock();

        instance.storeTemplate(template, command);

        Assert.assertEquals(template, command.getSetting(commandKey));
    }

    /**
     * Checks whether retrieveTemplateFactory() retrieves
     * {@link org.acmsl.queryj.templates.packaging.PerRepositoryTemplatesTestTemplateFactory}.
     */
    public abstract void retrieveTemplateFactory_retrieves_the_correct_factory();

    /**
     * Checks whether retrieveTemplateFactory() retrieves a &lt;TF&gt; instance.
     * @param factory the expected factory.
     */
    protected void retrieveTemplateFactory_retrieves_the_correct_factory(@NotNull final TF factory)
    {
        @NotNull final BH instance = createInstance();

        Assert.assertEquals(factory, instance.retrieveTemplateFactory());
    }

    /**
     * Checks whether the output package is the Cucumber's.
     */
    @SuppressWarnings("unused")
    @Test
    public void retrieveOutputPackage_returns_the_cucumber_package()
    {
        @NotNull final BH instance = createInstance();

        @NotNull final QueryJCommand command = EasyMock.createNiceMock(QueryJCommand.class);

        Assert.assertEquals(Literals.CUCUMBER_TEMPLATES, instance.retrieveOutputPackage(command));
    }

    /**
     * Checks whether buildContext() builds a global context.
     */
    @SuppressWarnings("unused")
    @Test
    public void buildContext_builds_a_global_context()
        throws IOException
    {
        @NotNull final BH instance = createInstance();

        @NotNull final QueryJCommand command =
            new ConfigurationQueryJCommandImpl(new PropertiesConfiguration());

        new QueryJCommandWrapper<File>(command).setSetting(
            PerRepositoryTemplatesTestTemplate.OUTPUT_DIR_FOR_TESTS, new File("/"));

        @NotNull final List<TemplateDef<String>> templateDefs = new ArrayList<>(0);

        Assert.assertNotNull(instance.buildContext(templateDefs, command));
    }
}
