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
 * Filename: TemplateWritingHandlerTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Build handler to build template writing handler templates.
 *
 * Date: 2013/08/21
 * Time: 22:27
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.templates.packaging.DefaultTemplatePackagingContext;
import org.acmsl.queryj.templates.packaging.TemplateDef;
import org.acmsl.queryj.templates.packaging.TemplateWritingHandlerTemplate;
import org.acmsl.queryj.templates.packaging.TemplateWritingHandlerTemplateFactory;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

import java.util.List;

/**
 * Build handler to build template writing handler templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/21 22:27
 */
@ThreadSafe
public class TemplateWritingHandlerTemplateBuildHandler
    extends TemplatePackagingBuildHandler
                <TemplateWritingHandlerTemplate<DefaultTemplatePackagingContext>,
                 TemplateWritingHandlerTemplateFactory,
                 DefaultTemplatePackagingContext>
{
    /**
     * Builds the context from given parameters.
     * @param templateDef the template def.
     * @param parameters  the command with the parameters.
     * @return the template context.
     */
    @NotNull
    @Override
    protected DefaultTemplatePackagingContext buildContext(
        @NotNull final TemplateDef templateDef, @NotNull final QueryJCommand parameters)
    {
        return buildDefaultContext(templateDef, parameters);
    }

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @NotNull
    @Override
    protected TemplateWritingHandlerTemplateFactory retrieveTemplateFactory()
    {
        return TemplateWritingHandlerTemplateFactory.getInstance();
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
        return "TemplateWritingHandler";
    }

    /**
     * Stores the templates in given attribute map.
     * @param templates  the templates.
     * @param parameters the parameter map.
     */
    @Override
    protected void storeTemplates(
        @NotNull final List<TemplateWritingHandlerTemplate<DefaultTemplatePackagingContext>> templates,
        @NotNull final QueryJCommand parameters)
    {
        new QueryJCommandWrapper<List<TemplateWritingHandlerTemplate<DefaultTemplatePackagingContext>>>(parameters)
            .setSetting(TEMPLATE_WRITING_HANDLER_TEMPLATES, templates);
    }
}
