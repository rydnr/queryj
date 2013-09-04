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
import org.acmsl.queryj.api.handlers.TemplateBuildHandler;

/*
 * Importing QueryJ Placeholders classes.
 */
import org.acmsl.queryj.placeholders.DecoratedString;

/*
 * Importing QueryJ Template-Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.DefaultTemplatePackagingContext;
import org.acmsl.queryj.templates.packaging.TemplateDef;
import org.acmsl.queryj.templates.packaging.TemplatePackagingContext;
import org.acmsl.queryj.templates.packaging.TemplatePackagingSettings;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplate;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplateFactory;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.regexpplugin.RegexpManager;

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
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
    implements TemplateBuildHandler,
               QueryJCommandHandler<QueryJCommand>,
               TemplatePackagingSettings
{
    @NotNull
    private final Pattern STG_EXT = Pattern.compile("\\.stg$");

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
    @NotNull
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

        @NotNull final List<TemplateDef<String>> templateDefs = retrieveTemplateDefs(parameters);

        for (@Nullable final TemplateDef<String> templateDef : templateDefs)
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
        @NotNull final TemplateDef<String> templateDef, @NotNull final QueryJCommand parameters);

    /**
     * Builds the default context.
     * @param templateDef the template def.
     * @param parameters the parameters.
     * @return the default context.
     */
    @NotNull
    protected DefaultTemplatePackagingContext buildDefaultContext(
        @NotNull final TemplateDef<String> templateDef, @NotNull final QueryJCommand parameters)
    {
        @NotNull final String templateName = retrieveTemplateName(parameters);
        @NotNull final String outputPackage = retrieveOutputPackage(parameters);

        @NotNull final File rootDir = retrieveRootDir(parameters);

        return
            new DefaultTemplatePackagingContext(
                templateDef,
                templateName,
                buildFilename(templateDef, templateName),
                outputPackage,
                rootDir,
                new File(rootDir.getAbsolutePath()
                    + File.separator + outputPackage.replaceAll("\\.", File.separator)));
    }

    /**
     * Builds the final file name.
     * @param templateDef the {@link TemplateDef} instance.
     * @param templateName the template name.
     * @return such file name.
     */
    @NotNull
    protected String buildFilename(
        @NotNull final TemplateDef<String> templateDef, @NotNull final String templateName)
    {
        return
              new DecoratedString(STG_EXT.matcher(templateDef.getName()).replaceAll("")).getCapitalized()
            + templateName
            + ".java";
    }

    /**
     * Retrieves the output package for the generated file.
     * @param parameters the parameters.
     * @return such package.
     */
    @NotNull
    protected abstract String retrieveOutputPackage(@NotNull final QueryJCommand parameters);

    /**
     * Retrieves the template defs.
     * @param parameters the command.
     * @return the list of {@link TemplateDef}s.
     */
    @NotNull
    public List<TemplateDef<String>> retrieveTemplateDefs(@NotNull final QueryJCommand parameters)
    {
        @NotNull final List<TemplateDef<String>> result;

        @Nullable final List<TemplateDef<String>> aux =
            new QueryJCommandWrapper<TemplateDef<String>>(parameters).getListSetting(TEMPLATE_DEFS);

        if (aux == null)
        {
            result = new ArrayList<TemplateDef<String>>(0);
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

    /**
     * Retrieves the output dir.
     * @param parameters the parameters.
     * @return the output dir.
     */
    @NotNull
    public File retrieveRootDir(@NotNull final QueryJCommand parameters)
    {
        @Nullable final File result = new QueryJCommandWrapper<File>(parameters).getSetting(OUTPUT_DIR);

        if (result == null)
        {
            // TODO
            throw new RuntimeException("Output dir missing");
        }

        return result;
    }
}
