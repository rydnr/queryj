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
 * Filename: AbstractTemplatesTestTemplateWritingHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Common logic and tests to follow a DRY approach when testing
 *              TemplatesTest writing handlers.
 *
 * Date: 2014/04/17
 * Time: 08:49
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.SerializablePropertiesConfiguration;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.GlobalTemplateContext;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplate;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplateFactory;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplateGenerator;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JUnit classes.
 */
import org.junit.Assert;
import org.junit.Test;

/**
 * Common logic and tests to follow a DRY approach when testing TemplatesTest writing handlers.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/17 08:49
 * @param <WH> the writing handler class.
 * @param <BH> the build handler class.
 * @param <T> the template class.
 * @param <TF> the template factory class.
 * @param <C> the template context class.
 */
public abstract class AbstractTemplatesTestTemplateWritingHandlerTest<
    WH extends TemplatePackagingTestWritingHandler
        <T, C, TemplatePackagingTemplateGenerator<T, C>>,
    BH extends TemplatePackagingTestBuildHandler<T, TF, C>,
    T extends TemplatePackagingTemplate<C>,
    TF extends TemplatePackagingTemplateFactory<T, C>,
    C extends GlobalTemplateContext>
{
    /**
     * Creates a new writing handler instance.
     * @return such instance.
     */
    @NotNull
    protected abstract WH createInstance();

    /**
     * Creates a new writing handler instance.
     * @return such instance.
     */
    @NotNull
    protected abstract BH createBuildHandlerInstance();

    /**
     * Retrieves a new template mock.
     * @return such mock.
     */
    @NotNull
    protected abstract T createTemplateMock();

    /**
     * Checks retrieveTemplateGenerator retrieves a not-null instance.
     * Redundant check since it's annotated with @NotNull.
     */
    @SuppressWarnings("unused")
    @Test
    public void retrieveTemplateGenerator_works()
    {
        @NotNull final WH instance = createInstance();

        Assert.assertNotNull(instance.retrieveTemplateGenerator(false, 1));
    }

    /**
     * Checks the templates built by the build handler are later
     * found by the writing handler.
     */
    @SuppressWarnings("unused")
    @Test
    public void retrieveTemplates_finds_the_templates()
        throws QueryJBuildException
    {
        @NotNull final WH instance = createInstance();

        @NotNull final BH buildHandler = createBuildHandlerInstance();

        @NotNull final QueryJCommand command =
            new ConfigurationQueryJCommandImpl(new SerializablePropertiesConfiguration());

        @NotNull final T template = createTemplateMock();

        buildHandler.storeTemplate(template, command);

        Assert.assertNotNull(instance.retrieveTemplates(command));
    }
}
