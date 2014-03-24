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
import org.acmsl.queryj.api.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.exceptions.MissingJdbcDriverAtRuntimeException;
import org.acmsl.queryj.tools.exceptions.MissingJdbcPasswordAtRuntimeException;
import org.acmsl.queryj.tools.exceptions.MissingJdbcUrlAtRuntimeException;
import org.acmsl.queryj.tools.exceptions.MissingJdbcUserNameAtRuntimeException;
import org.acmsl.queryj.tools.exceptions.MissingOutputDirAtRuntimeException;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;

/*
 * Importing QueryJ Placeholders classes.
 */
import org.acmsl.queryj.metadata.DecoratedString;

/*
 * Importing QueryJ Template-Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.DefaultTemplatePackagingContext;
import org.acmsl.queryj.templates.packaging.GlobalTemplateContext;
import org.acmsl.queryj.templates.packaging.GlobalTemplateContextImpl;
import org.acmsl.queryj.templates.packaging.TemplateDef;
import org.acmsl.queryj.templates.packaging.TemplatePackagingContext;
import org.acmsl.queryj.templates.packaging.TemplatePackagingSettings;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplate;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplateFactory;

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
    private static final Pattern STG_EXT = Pattern.compile("\\.stg$");

    /**
     * Creates a {@link TemplatePackagingBuildHandler} instance.
     */
    protected TemplatePackagingBuildHandler() {}

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @NotNull
    protected abstract TF retrieveTemplateFactory();

    /**
     * Builds the default context.
     * @param templateDef the template def.
     * @param parameters the parameters.
     * @return the default context.
     */
    @NotNull
    protected DefaultTemplatePackagingContext buildDefaultContext(
        @NotNull final TemplateDef<String> templateDef,
        @NotNull final QueryJCommand parameters)
    {
        @NotNull final String templateName = retrieveTemplateName(parameters);
        @NotNull final String outputPackage = retrieveOutputPackage(parameters);

        @NotNull final File rootDir = retrieveRootDir(parameters);

        @NotNull final String jdbcDriver = retrieveJdbcDriver(parameters);
        @NotNull final String jdbcUrl = retrieveJdbcUrl(parameters);
        @NotNull final String jdbcUsername = retrieveJdbcUsername(parameters);
        @NotNull final String jdbcPassword = retrieveJdbcPassword(parameters);

        return
            new DefaultTemplatePackagingContext(
                templateDef,
                templateName,
                buildFilename(templateDef, templateName),
                outputPackage,
                rootDir,
                new File(rootDir.getAbsolutePath()
                    + File.separator + outputPackage.replaceAll("\\.", File.separator)),
                jdbcDriver,
                jdbcUrl,
                jdbcUsername,
                jdbcPassword);
    }

    /**
     * Builds the default context.
     * @param templateDefs the template defs.
     * @param parameters the parameters.
     * @return the default context.
     */
    @NotNull
    protected GlobalTemplateContext buildGlobalContext(
        @NotNull final List<TemplateDef<String>> templateDefs,
        @NotNull final QueryJCommand parameters)
    {
        @NotNull final String templateName = retrieveTemplateName(parameters);
        @NotNull final String outputPackage = retrieveOutputPackage(parameters);

        @NotNull final File rootDir = retrieveRootDir(parameters);

        @NotNull final String jdbcDriver = retrieveJdbcDriver(parameters);
        @NotNull final String jdbcUrl = retrieveJdbcUrl(parameters);
        @NotNull final String jdbcUsername = retrieveJdbcUsername(parameters);
        @NotNull final String jdbcPassword = retrieveJdbcPassword(parameters);

        return
            new GlobalTemplateContextImpl(
                templateName,
                buildFilename(templateDefs, templateName),
                outputPackage,
                rootDir,
                new File(rootDir.getAbsolutePath()
                         + File.separator + outputPackage.replaceAll("\\.", File.separator)),
                jdbcDriver,
                jdbcUrl,
                jdbcUsername,
                jdbcPassword,
                templateDefs);
    }

    /**
     * Builds the final file name.
     * @param templateDef the {@link TemplateDef} instance.
     * @param templateName the template name.
     * @return such file name.
     */
    @NotNull
    protected String buildFilename(
        @NotNull final TemplateDef<String> templateDef,
        @NotNull final String templateName)
    {
        return
              new DecoratedString(STG_EXT.matcher(templateDef.getName()).replaceAll("")) //.getCapitalized()
            + templateName
            + ".java";
    }

    /**
     * Builds the final file name.
     * @param templateDefs the {@link TemplateDef} instances.
     * @param templateName the template name.
     * @return such file name.
     */
    @NotNull
    protected String buildFilename(
        @SuppressWarnings("unused") @NotNull final List<TemplateDef<String>> templateDefs,
        @NotNull final String templateName)
    {
        return
            new DecoratedString(templateName).getCapitalized()
            + ".java";
    }

    /**
     * Retrieves the output package for the generated file.
     * @param parameters the parameters.
     * @return such package.
     */
    @NotNull
    protected abstract String retrieveOutputPackage(
        @NotNull final QueryJCommand parameters);

    /**
     * Retrieves the template defs.
     * @param parameters the command.
     * @return the list of {@link TemplateDef}s.
     */
    @NotNull
    public List<TemplateDef<String>> retrieveTemplateDefs(
        @NotNull final QueryJCommand parameters)
    {
        @NotNull final List<TemplateDef<String>> result;

        @Nullable final List<TemplateDef<String>> aux =
            new QueryJCommandWrapper<TemplateDef<String>>(parameters)
                .getListSetting(TEMPLATE_DEFS);

        if (aux == null)
        {
            result = new ArrayList<>(0);
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
    protected abstract String retrieveTemplateName(
        @NotNull final QueryJCommand parameters);

    /**
     * Retrieves the output dir.
     * @param parameters the parameters.
     * @return the output dir.
     */
    @NotNull
    public File retrieveRootDir(@NotNull final QueryJCommand parameters)
    {
        @Nullable final File result =
            new QueryJCommandWrapper<File>(parameters).getSetting(OUTPUT_DIR);

        if (result == null)
        {
            throw new MissingOutputDirAtRuntimeException();
        }

        return result;
    }

    /**
     * Retrieves the JDBC driver.
     * @param parameters the parameters.
     * @return the driver.
     */
    @NotNull
    public String retrieveJdbcDriver(@NotNull final QueryJCommand parameters)
    {
        @Nullable final String result =
            new QueryJCommandWrapper<String>(parameters).getSetting(JDBC_DRIVER);

        if (result == null)
        {
            throw new MissingJdbcDriverAtRuntimeException();
        }

        return result;
    }

    /**
     * Retrieves the JDBC url.
     * @param parameters the parameters.
     * @return the url.
     */
    @NotNull
    public String retrieveJdbcUrl(@NotNull final QueryJCommand parameters)
    {
        @Nullable final String result =
            new QueryJCommandWrapper<String>(parameters).getSetting(JDBC_URL);

        if (result == null)
        {
            throw new MissingJdbcUrlAtRuntimeException();
        }

        return result;
    }

    /**
     * Retrieves the JDBC user name.
     * @param parameters the parameters.
     * @return the information.
     */
    @NotNull
    public String retrieveJdbcUsername(@NotNull final QueryJCommand parameters)
    {
        @Nullable final String result =
            new QueryJCommandWrapper<String>(parameters).getSetting(JDBC_USERNAME);

        if (result == null)
        {
            throw new MissingJdbcUserNameAtRuntimeException();
        }

        return result;
    }

    /**
     * Retrieves the JDBC password.
     * @param parameters the parameters.
     * @return the password.
     */
    @NotNull
    public String retrieveJdbcPassword(@NotNull final QueryJCommand parameters)
    {
        @Nullable final String result =
            new QueryJCommandWrapper<String>(parameters).getSetting(JDBC_PASSWORD);

        if (result == null)
        {
            throw new MissingJdbcPasswordAtRuntimeException();
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
    protected T buildTemplate(
        @NotNull final TF templateFactory, @NotNull final C context)
    {
        return templateFactory.createTemplate(context);
    }
}
