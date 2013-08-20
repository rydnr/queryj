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
 * Filename: TemplatePackagingBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base build handler for Template Packaging templates.
 *
 * Date: 2013/08/17
 * Time: 11:00
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.templates.packaging.DefaultTemplatePackagingContext;
import org.acmsl.queryj.templates.packaging.TemplateDef;
import org.acmsl.queryj.templates.packaging.TemplatePackagingContext;
import org.acmsl.queryj.templates.packaging.TemplatePackagingSettings;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplate;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplateFactory;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Base build handler for Template Packaging templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/17 11:00
 */
@ThreadSafe
public abstract class TemplatePackagingBuildHandler
    <T extends TemplatePackagingTemplate<C>,
     TF extends TemplatePackagingTemplateFactory<T, C>,
     C extends TemplatePackagingContext>
    implements QueryJCommandHandler<QueryJCommand>,
               TemplatePackagingSettings
{
    /**
     * Creates a {@link TemplatePackagingBuildHandler} instance.
     */
    protected TemplatePackagingBuildHandler() {}

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws org.acmsl.queryj.api.exceptions.QueryJBuildException if the build process cannot be performed.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand parameters)
        throws QueryJBuildException
    {
        @Nullable final List<T> templates = buildTemplates(parameters, retrieveTemplateFactory());

        storeTemplates(templates, parameters);

        return false;
    }

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    protected abstract TF retrieveTemplateFactory();

    /**
     * Builds the template.
     * @param parameters the parameters.
     * @param factory the TF instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @NotNull
    protected List<T> buildTemplates(@NotNull final QueryJCommand parameters, @NotNull final TF factory)
        throws  QueryJBuildException
    {
        @NotNull final List<T> result = new ArrayList<T>();

        @NotNull final List<TemplateDef> templateDefs = retrieveTemplateDefs(parameters);

        for (@Nullable final TemplateDef templateDef : templateDefs)
        {
            if (templateDef == null)
            {
                // TODO
                throw new RuntimeException("TemplateDef null");
            }
            else
            {
                @NotNull final C context = buildContext(templateDef, parameters);

                @Nullable final T template = buildTemplate(factory, context);

                if (template == null)
                {
                    // TODO
                    throw new RuntimeException("Template null");
                }
                else
                {
                    result.add(template);
                }
            }
        }

        return result;
    }

    /**
     * Creates a template with required information.
     * @param templateFactory the {@link TemplatePackagingTemplateFactory} instance.
     * @param context the context.
     * @return the template.
     */
    @Nullable
    protected T buildTemplate(@NotNull final TF templateFactory, @NotNull final C context)
        throws  QueryJBuildException
    {
        return templateFactory.createTemplate(context);
    }

    /**
     * Builds the context from given parameters.
     * @param templateDef the template def.
     * @param parameters the command with the parameters.
     * @return the template context.
     */
    @NotNull
    protected abstract C buildContext(
        @NotNull final TemplateDef templateDef, @NotNull final QueryJCommand parameters);

    /**
     * Builds the default context.
     * @param parameters the parameters.
     * @return the default context.
     */
    @NotNull
    protected DefaultTemplatePackagingContext buildDefaultContext(
        @NotNull final TemplateDef templateDef, @NotNull final QueryJCommand parameters)
    {
        return
            new DefaultTemplatePackagingContext(
                templateDef,
                retrieveTemplateName(parameters),
                templateDef.getFile().getAbsolutePath());
    }

    /**
     * Retrieves the template defs.
     * @param parameters the command.
     * @return the list of {@link TemplateDef}s.
     */
    @NotNull
    public List<TemplateDef> retrieveTemplateDefs(@NotNull final QueryJCommand parameters)
    {
        @NotNull final List<TemplateDef> result;

        @Nullable final List<TemplateDef> aux =
            new QueryJCommandWrapper<TemplateDef>(parameters).getListSetting(TEMPLATE_DEFS);

        if (aux == null)
        {
            result = new ArrayList<TemplateDef>(0);
        }
        else
        {
            result = aux;
        }

        return result;
    }

    /**
     * Retrieves the template name, using the parameters if necessary.
     * @param parameters the parameters.
     * @return the template name.
     */
    @NotNull
    protected abstract String retrieveTemplateName(@NotNull final QueryJCommand parameters);

    /**
     * Stores the templates in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     */
    protected abstract void storeTemplates(
        @NotNull final List<T> templates, @NotNull final QueryJCommand parameters);
}
