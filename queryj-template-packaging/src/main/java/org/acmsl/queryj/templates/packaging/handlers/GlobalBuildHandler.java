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
 * Filename: GlobalBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base build handler for all templates using global scope
 *              (i.e. need all available information).
 *
 * Date: 2013/09/15
 * Time: 07:34
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
import org.acmsl.queryj.templates.packaging.GlobalTemplateContext;
import org.acmsl.queryj.templates.packaging.TemplateDef;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplate;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplateFactory;

/*
 * Importing JetBrains annotations.
 */
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
import java.util.List;

/**
 * Base build handler for all templates using global scope (i.e. need all available information).
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/09/15 07:34
 * @param <T> the template type.
 * @param <TF> the template factory type.
 * @param <C> the context type.
 */
@ThreadSafe
public abstract class GlobalBuildHandler
    <T extends TemplatePackagingTemplate<C>,
        TF extends TemplatePackagingTemplateFactory<T, C>,
        C extends GlobalTemplateContext>
    extends TemplatePackagingBuildHandler<T, TF, C>
{
    /**
     * Creates a new instance.
     */
    protected GlobalBuildHandler() {};

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the template cannot be built.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand parameters)
        throws QueryJBuildException
    {
        @Nullable final T template = buildTemplate(parameters, retrieveTemplateFactory());

        storeTemplate(template, parameters);

        return false;
    }

    /**
     * Builds the template.
     * @param parameters the parameters.
     * @param factory the TF instance.
     * @return the template.
     * @throws QueryJBuildException if the template cannot be built.
     */
    @NotNull
    protected T buildTemplate(@NotNull final QueryJCommand parameters, @NotNull final TF factory)
        throws  QueryJBuildException
    {
        @NotNull final T result;

        @NotNull final List<TemplateDef<String>> templateDefs = retrieveTemplateDefs(parameters);

        @NotNull final C context = buildContext(templateDefs, parameters);

        @Nullable final T template = buildTemplate(factory, context);

        if (template == null)
        {
            throw new TemplateFactoryRefusedToBuildTemplateException(factory, context);
        }
        else
        {
            result = template;
        }

        return result;
    }

    /**
     * Builds the context from given parameters.
     * @param templateDefs the template defs.
     * @param parameters the command with the parameters.
     * @return the template context.
     */
    @NotNull
    protected abstract C buildContext(
        @NotNull final List<TemplateDef<String>> templateDefs,
        @NotNull final QueryJCommand parameters);

    /**
     * Stores the templates in given attribute map.
     * @param template the template.
     * @param parameters the parameter map.
     */
    protected abstract void storeTemplate(
        @NotNull final T template, @NotNull final QueryJCommand parameters);
}
