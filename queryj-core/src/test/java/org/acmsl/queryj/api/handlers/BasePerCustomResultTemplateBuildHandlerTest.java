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
 * Filename: BasePerCustomResultTemplateBuildHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for BasePerCustomResultTemplateBuildHandler.
 *
 * Date: 2014/05/17
 * Time: 12:30
 *
 */
package org.acmsl.queryj.api.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.PerCustomResultTemplate;
import org.acmsl.queryj.api.PerCustomResultTemplateContext;
import org.acmsl.queryj.api.PerCustomResultTemplateFactory;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultElement;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.engines.Engine;

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

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for {@link BasePerCustomResultTemplateBuildHandler}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/17 12:30
 */
@RunWith(JUnit4.class)
public class BasePerCustomResultTemplateBuildHandlerTest
{
    /**
     * Checks if fixDuplicated() actually removes results differing only in cardinality.
     */
    @Test
    public void fixDuplicated_removes_extra_results_differing_only_in_cardinality()
    {
        @NotNull final BasePerCustomResultTemplateBuildHandler instance = createInstance();

        @NotNull final List<Result<String>> results = new ArrayList<>(4);

        results.add(new ResultElement<>("single.r1", "Class1"));
        results.add(new ResultElement<>("multiple.r1", "Class1"));
        results.add(new ResultElement<>("r3", "Class3"));
        results.add(new ResultElement<>("r4", "Class4"));

        Assert.assertEquals(3, instance.fixDuplicated(results).size());
    }

    /**
     * Creates a new instance.
     * @return such instance.
     */
    protected BasePerCustomResultTemplateBuildHandler
                <PerCustomResultTemplate<PerCustomResultTemplateContext>,
                PerCustomResultTemplateContext,
                PerCustomResultTemplateFactory
                    <PerCustomResultTemplate
                        <PerCustomResultTemplateContext>, PerCustomResultTemplateContext>> createInstance()
    {
        return
            new BasePerCustomResultTemplateBuildHandler
                <PerCustomResultTemplate<PerCustomResultTemplateContext>,
                    PerCustomResultTemplateContext,
                    PerCustomResultTemplateFactory<PerCustomResultTemplate<PerCustomResultTemplateContext>, PerCustomResultTemplateContext>>
                ()
            {
                /**
                 * {@inheritDoc}
                 */
                @Override
                protected PerCustomResultTemplateFactory<PerCustomResultTemplate<PerCustomResultTemplateContext>, PerCustomResultTemplateContext> retrieveTemplateFactory()
                {
                    return null;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                protected PerCustomResultTemplate<PerCustomResultTemplateContext> createTemplate(
                    @NotNull final PerCustomResultTemplateFactory<PerCustomResultTemplate<PerCustomResultTemplateContext>, PerCustomResultTemplateContext> templateFactory,
                    @NotNull final Result<String> result,
                    @NotNull final List<Property<String>> properties,
                    @NotNull final QueryJCommand parameters)
                {
                    return null;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                protected String retrievePackage(
                    @NotNull final Result<String> customResult,
                    @NotNull final CustomSqlProvider customSqlProvider,
                    @NotNull final MetadataManager metadataManager,
                    @NotNull final Engine<String> engine,
                    @NotNull final QueryJCommand parameters)
                {
                    return null;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                protected void storeTemplates(
                    @NotNull final List templates, @NotNull final QueryJCommand parameters)
                {
                }
            };
    }
}
