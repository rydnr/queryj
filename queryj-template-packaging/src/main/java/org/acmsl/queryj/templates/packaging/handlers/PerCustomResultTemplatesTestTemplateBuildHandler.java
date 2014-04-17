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
 * Filename: PerCustomResultTemplatesTestTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Build handler for PerCustomResultTemplatesTestTemplates.
 *
 * Date: 2014/04/17
 * Time: 08:12
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.GlobalTemplateContext;
import org.acmsl.queryj.templates.packaging.Literals;
import org.acmsl.queryj.templates.packaging.PerCustomResultTemplatesTestTemplate;
import org.acmsl.queryj.templates.packaging.PerCustomResultTemplatesTestTemplateFactory;
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
 * Importing JDK classes.
 */
import java.util.List;

/**
 * Build handler for {@link PerCustomResultTemplatesTestTemplate}s.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/17 08:12
 */
@ThreadSafe
public class PerCustomResultTemplatesTestTemplateBuildHandler
    extends TemplatePackagingTestBuildHandler
                <PerCustomResultTemplatesTestTemplate,
                    PerCustomResultTemplatesTestTemplateFactory,
                    GlobalTemplateContext>
{
    /**
     * The key to access the templates in the command.
     */
    @NotNull static final String TEMPLATES_KEY = "PerCustomResultTemplatesTest_templates";

    /**
     * Creates a {@code PerCustomResultTemplatesTestTemplateBuildHandler}.
     */
    public PerCustomResultTemplatesTestTemplateBuildHandler() {}

    /**
     * Retrieves the template factory.
     * @return the {@link PerCustomResultTemplatesTestTemplateFactory} instance.
     */
    @Override
    @NotNull
    protected PerCustomResultTemplatesTestTemplateFactory retrieveTemplateFactory()
    {
        return PerCustomResultTemplatesTestTemplateFactory.getInstance();
    }

    /**
     * Builds the context from given parameters.
     * @param templateDefs the template defs.
     * @param parameters   the command with the parameters.
     * @return the template context.
     */
    @NotNull
    @Override
    protected GlobalTemplateContext buildContext(
        @NotNull final List<TemplateDef<String>> templateDefs, @NotNull final QueryJCommand parameters)
    {
        return buildGlobalContext(templateDefs, parameters);
    }

    /**
     * Retrieves the output package for the generated file.
     * @param parameters the parameters.
     * @return such package.
     */
    @NotNull
    @Override
    protected String retrieveOutputPackage(@NotNull final QueryJCommand parameters)
    {
        return org.acmsl.queryj.templates.packaging.Literals.CUCUMBER_TEMPLATES;
    }

    /**
     * Retrieves the template name, using the parameters if necessary.
     * @param parameters the parameters.
     * @return the template name.
     */
    @NotNull
    @Override
    protected String retrieveTemplateName(@NotNull final QueryJCommand parameters)
    {
        return Literals.PER_CUSTOM_RESULT_TEMPLATES_TEST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void storeTemplate(
        @NotNull final PerCustomResultTemplatesTestTemplate template,
        @NotNull final QueryJCommand parameters)
    {
        new QueryJCommandWrapper<PerCustomResultTemplatesTestTemplate>(parameters)
            .setSetting(TEMPLATES_KEY, template);
    }
}
