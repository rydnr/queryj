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
 * Filename: PerForeignKeyTemplatesTestTemplateBuildHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for PerForeignKeyTemplatesTestTemplateBuildHandler.
 *
 * Date: 2014/04/16
 * Time: 22:32
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing Apache Commons Configuration classes.
 */
import org.apache.commons.configuration.PropertiesConfiguration;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.templates.packaging.Literals;
import org.acmsl.queryj.templates.packaging.PerForeignKeyTemplatesTestTemplate;
import org.acmsl.queryj.templates.packaging.PerForeignKeyTemplatesTestTemplateFactory;
import org.acmsl.queryj.templates.packaging.TemplateDef;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JUnit/EasyMock classes.
 */
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for {@link PerForeignKeyTemplatesTestTemplateBuildHandler}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/16 22:32
 */
@ThreadSafe
public class PerForeignKeyTemplatesTestTemplateBuildHandlerTest
{
    /**
     * Tests whether retrieveTemplateName() works.
     */
    @Test
    public void retrieveTemplateName_works()
    {
        @NotNull final PerForeignKeyTemplatesTestTemplateBuildHandler instance =
            new PerForeignKeyTemplatesTestTemplateBuildHandler();

        @NotNull final QueryJCommand command = EasyMock.createNiceMock(QueryJCommand.class);

        Assert.assertEquals(Literals.PER_FOREIGN_KEY_TEMPLATES_TEST, instance.retrieveTemplateName(command));
    }

    /**
     * Checks whether storeTemplate() binds the template to the command.
     */
    @Test
    public void storeTemplate_stores_the_templates_in_the_command()
    {
        @NotNull final PerForeignKeyTemplatesTestTemplateBuildHandler instance =
            new PerForeignKeyTemplatesTestTemplateBuildHandler();

        @NotNull final QueryJCommand command =
            new ConfigurationQueryJCommandImpl(new PropertiesConfiguration());

        @NotNull final PerForeignKeyTemplatesTestTemplate template =
            EasyMock.createNiceMock(PerForeignKeyTemplatesTestTemplate.class);

        instance.storeTemplate(template, command);

        Assert.assertEquals(template, command.getSetting(PerForeignKeyTemplatesTestTemplateBuildHandler.TEMPLATES_KEY));
    }

    /**
     * Checks whether retrieveTemplateFactory() retrieves
     * {@link PerForeignKeyTemplatesTestTemplateFactory}.
     */
    @Test
    public void retrieveTemplateFactory_retrieves_the_correct_factory()
    {
        @NotNull final PerForeignKeyTemplatesTestTemplateBuildHandler instance =
            new PerForeignKeyTemplatesTestTemplateBuildHandler();

        Assert.assertEquals(PerForeignKeyTemplatesTestTemplateFactory.getInstance(), instance.retrieveTemplateFactory());
    }

    /**
     * Checks whether the output package is the Cucumber's.
     */
    @Test
    public void retrieveOutputPackage_returns_the_cucumber_package()
    {
        @NotNull final PerForeignKeyTemplatesTestTemplateBuildHandler instance =
            new PerForeignKeyTemplatesTestTemplateBuildHandler();

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
        @NotNull final PerForeignKeyTemplatesTestTemplateBuildHandler instance =
            new PerForeignKeyTemplatesTestTemplateBuildHandler();

        @NotNull final QueryJCommand command =
            new ConfigurationQueryJCommandImpl(new PropertiesConfiguration());

        new QueryJCommandWrapper<File>(command).setSetting(
            PerForeignKeyTemplatesTestTemplate.OUTPUT_DIR_FOR_TESTS, new File("/"));

        @NotNull final List<TemplateDef<String>> templateDefs = new ArrayList<>(0);

        Assert.assertNotNull(instance.buildContext(templateDefs, command));
    }
}
