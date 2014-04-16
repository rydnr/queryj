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
 * Filename: PerTableTemplatesTestTemplateBuildHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for PerTableTemplatesTestTemplateBuildHandler.
 *
 * Date: 2014/04/16
 * Time: 21:50
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.templates.packaging.Literals;
import org.acmsl.queryj.templates.packaging.PerTableTemplatesTestTemplate;
import org.acmsl.queryj.templates.packaging.PerTableTemplatesTestTemplateFactory;
import org.acmsl.queryj.templates.packaging.TemplateDef;

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
 * Tests for {@link PerTableTemplatesTestTemplateBuildHandler}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/16 21:50
 */
@RunWith(JUnit4.class)
public class PerTableTemplatesTestTemplateBuildHandlerTest
{
    /**
     * Tests whether retrieveTemplateName() works.
     */
    @Test
    public void retrieveTemplateName_works()
    {
        @NotNull final PerTableTemplatesTestTemplateBuildHandler instance =
            new PerTableTemplatesTestTemplateBuildHandler();

        @NotNull final QueryJCommand command = EasyMock.createNiceMock(QueryJCommand.class);

        Assert.assertEquals(Literals.PER_TABLE_TEMPLATES_TEST, instance.retrieveTemplateName(command));
    }

    /**
     * Checks whether storeTemplate() binds the template to the command.
     */
    @Test
    public void storeTemplate_stores_the_templates_in_the_command()
    {
        @NotNull final PerTableTemplatesTestTemplateBuildHandler instance =
            new PerTableTemplatesTestTemplateBuildHandler();

        @NotNull final QueryJCommand command =
            new ConfigurationQueryJCommandImpl(new PropertiesConfiguration());

        @NotNull final PerTableTemplatesTestTemplate template =
            EasyMock.createNiceMock(PerTableTemplatesTestTemplate.class);

        instance.storeTemplate(template, command);

        Assert.assertEquals(template, command.getSetting(PerTableTemplatesTestTemplateBuildHandler.TEMPLATES_KEY));
    }

    /**
     * Checks whether retrieveTemplateFactory() retrieves
     * {@link PerTableTemplatesTestTemplateFactory}.
     */
    @Test
    public void retrieveTemplateFactory_retrieves_the_correct_factory()
    {
        @NotNull final PerTableTemplatesTestTemplateBuildHandler instance =
            new PerTableTemplatesTestTemplateBuildHandler();

        Assert.assertEquals(PerTableTemplatesTestTemplateFactory.getInstance(), instance.retrieveTemplateFactory());
    }

    /**
     * Checks whether the output package is the Cucumber's.
     */
    @Test
    public void retrieveOutputPackage_returns_the_cucumber_package()
    {
        @NotNull final PerTableTemplatesTestTemplateBuildHandler instance =
            new PerTableTemplatesTestTemplateBuildHandler();

        @NotNull final QueryJCommand command = EasyMock.createNiceMock(QueryJCommand.class);

        Assert.assertEquals(Literals.CUCUMBER_TEMPLATES, instance.retrieveOutputPackage(command));
    }

    /**
     * Checks whether buildContext() builds a global context.
     */
    @Test
    public void buildContext_builds_a_global_context()
        throws IOException
    {
        @NotNull final PerTableTemplatesTestTemplateBuildHandler instance =
            new PerTableTemplatesTestTemplateBuildHandler();

        @NotNull final QueryJCommand command =
            new ConfigurationQueryJCommandImpl(new PropertiesConfiguration());

        new QueryJCommandWrapper<File>(command).setSetting(
            PerTableTemplatesTestTemplate.OUTPUT_DIR_FOR_TESTS, new File("/"));

        @NotNull final List<TemplateDef<String>> templateDefs = new ArrayList<>(0);

        Assert.assertNotNull(instance.buildContext(templateDefs, command));
    }
}
