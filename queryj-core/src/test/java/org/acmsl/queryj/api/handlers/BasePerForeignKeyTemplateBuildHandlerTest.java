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
 * Filename: BasePerForeignKeyTemplateBuildHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for BasePerForeignKeyTemplateBuildHandler.
 *
 * Date: 2014/05/06
 * Time: 14:56
 *
 */
package org.acmsl.queryj.api.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.PerForeignKeyTemplate;
import org.acmsl.queryj.api.PerForeignKeyTemplateContext;
import org.acmsl.queryj.api.PerForeignKeyTemplateFactory;
import org.acmsl.queryj.metadata.ForeignKeyDecorator;
import org.acmsl.queryj.metadata.engines.Engine;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.ForeignKeyValueObject;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JUnit classes.
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for {@link BasePerForeignKeyTemplateBuildHandler}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/06 14:56
 */
@RunWith(JUnit4.class)
public class BasePerForeignKeyTemplateBuildHandlerTest
{
    /**
     * Checks decorate() works as expected.
     */
    @Test
    public void decorate_creates_a_ForeignKeyDecorator()
    {
        @NotNull final BasePerForeignKeyTemplateBuildHandler<PerForeignKeyTemplate<PerForeignKeyTemplateContext>,
            PerForeignKeyTemplateContext,
            PerForeignKeyTemplateFactory<PerForeignKeyTemplate<PerForeignKeyTemplateContext>, PerForeignKeyTemplateContext>> instance =
            createHandler();

        @NotNull final ForeignKey<String> foreignKey =
            new ForeignKeyValueObject(
                "source",
                new ArrayList<Attribute<String>>(0),
                "target",
                false);

//        @NotNull final ForeignKeyDecorator decorator = instance.decorate(foreignKey);
    }

    /**
     * Creates a new {@link BasePerForeignKeyTemplateBuildHandler} instance.
     * @return such instance.
     */
    @NotNull
    protected BasePerForeignKeyTemplateBuildHandler<PerForeignKeyTemplate<PerForeignKeyTemplateContext>,
        PerForeignKeyTemplateContext,
        PerForeignKeyTemplateFactory<PerForeignKeyTemplate<PerForeignKeyTemplateContext>, PerForeignKeyTemplateContext>> createHandler()
    {
        return
            new BasePerForeignKeyTemplateBuildHandler
                <PerForeignKeyTemplate<PerForeignKeyTemplateContext>,
                    PerForeignKeyTemplateContext,
                    PerForeignKeyTemplateFactory<PerForeignKeyTemplate<PerForeignKeyTemplateContext>, PerForeignKeyTemplateContext>>()
            {
                /**
                 * {@inheritDoc}
                 */
                @NotNull
                @Override
                protected PerForeignKeyTemplateFactory<PerForeignKeyTemplate<PerForeignKeyTemplateContext>, PerForeignKeyTemplateContext> retrieveTemplateFactory()
                {
                    return null;
                }

                /**
                 * {@inheritDoc}
                 */
                @Nullable
                @Override
                protected PerForeignKeyTemplate<PerForeignKeyTemplateContext> createTemplate(
                    @NotNull final PerForeignKeyTemplateFactory<PerForeignKeyTemplate<PerForeignKeyTemplateContext>, PerForeignKeyTemplateContext> templateFactory,
                    @NotNull final ForeignKey<String> foreignKey,
                    @NotNull final QueryJCommand parameters)
                {
                    return null;
                }

                /**
                 * {@inheritDoc}
                 */
                @NotNull
                @Override
                protected String retrievePackage(
                    @NotNull final ForeignKeyDecorator foreignKey,
                    @NotNull final Engine<String> engineName,
                    @NotNull final String projectPackage)
                {
                    return null;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                protected void storeTemplates(
                    @NotNull final List<PerForeignKeyTemplate<PerForeignKeyTemplateContext>> templates,
                    @NotNull final QueryJCommand parameters)
                {
                }
            };
    }
}
