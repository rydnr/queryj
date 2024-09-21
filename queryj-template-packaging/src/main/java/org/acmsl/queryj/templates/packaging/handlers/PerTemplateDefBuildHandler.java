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
 * Filename: PerTemplateDefBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Build handlers for per-TemplateDef templates.
 *
 * Date: 2013/09/15
 * Time: 07:18
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.TemplateDef;
import org.acmsl.queryj.templates.packaging.TemplatePackagingContext;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplate;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplateFactory;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.templates.packaging.exceptions.FoundNullTemplateDefException;
import org.acmsl.queryj.templates.packaging.exceptions.TemplateFactoryRefusedToBuildTemplateException;
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
 * Build handlers for per-{@link TemplateDef} templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/09/15 07:18
 * @param <T> the template type.
 * @param <TF> the template factory type.
 * @param <C> the context.
 */
@ThreadSafe
public abstract class PerTemplateDefBuildHandler
    <T extends TemplatePackagingTemplate<C>,
        TF extends TemplatePackagingTemplateFactory<T, C>,
        C extends TemplatePackagingContext>
    extends TemplatePackagingBuildHandler<T, TF, C>
{
    /**
     * Creates a new instance.
     */
    protected PerTemplateDefBuildHandler() {};

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
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
     * Builds the template.
     * @param parameters the parameters.
     * @param factory the TF instance.
     * @return the templates.
     * @throws QueryJBuildException if the templates cannot be built.
     */
    @NotNull
    protected List<T> buildTemplates(@NotNull final QueryJCommand parameters, @NotNull final TF factory)
        throws  QueryJBuildException
    {
        @NotNull final List<T> result = new ArrayList<>();

        @NotNull final List<TemplateDef<String>> templateDefs = retrieveTemplateDefs(parameters);

        for (@Nullable final TemplateDef<String> templateDef : templateDefs)
        {
            if (templateDef == null)
            {
                throw new FoundNullTemplateDefException();
            }
            else
            {
                @NotNull final C context = buildContext(templateDef, parameters);

                @Nullable final T template = buildTemplate(factory, context);

                if (template == null)
                {
                    throw new TemplateFactoryRefusedToBuildTemplateException(factory, context);
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
     * Builds the context from given parameters.
     * @param templateDef the template def.
     * @param parameters the command with the parameters.
     * @return the template context.
     */
    @NotNull
    protected abstract C buildContext(
        @NotNull final TemplateDef<String> templateDef, @NotNull final QueryJCommand parameters);

    /**
     * Stores the templates in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     */
    protected abstract void storeTemplates(
        @NotNull final List<T> templates, @NotNull final QueryJCommand parameters);

}
