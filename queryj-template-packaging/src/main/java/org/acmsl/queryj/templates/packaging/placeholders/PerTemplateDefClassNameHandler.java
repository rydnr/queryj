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
 * Filename: PerTemplateDefClassNameHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Resolves "class_name" placeholders, for per-TemplateDef
 *              templates.
 *
 * Date: 2013/09/04
 * Time: 20:05
 *
 */
package org.acmsl.queryj.templates.packaging.placeholders;

/*
 * Importing QueryJ Placeholders classes.
 */
import org.acmsl.queryj.placeholders.AbstractDecoratedStringHandler;
import org.acmsl.queryj.placeholders.Literals;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.DecoratedString;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.DefaultTemplatePackagingContext;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.templates.packaging.TemplateDef;
import org.acmsl.queryj.templates.packaging.TemplatePackagingContext;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplate;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplateFactory;
import org.acmsl.queryj.templates.packaging.TemplatePackagingUtils;
import org.acmsl.queryj.templates.packaging.handlers.TemplatePackagingBuildHandler;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.regex.Pattern;

/**
 * Resolves "class_name" placeholders, for
 * per-{@link org.acmsl.queryj.templates.packaging.TemplateDef} templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/09/04 20:05
 */
@ThreadSafe
public class PerTemplateDefClassNameHandler
    extends AbstractDecoratedStringHandler<DefaultTemplatePackagingContext>
{
    /**
     * The regex to remove the trailing .stg extension.
     */
    @NotNull
    public static final Pattern STG_EXT = Pattern.compile("\\.stg$");

    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 7668827891857653722L;

    /**
     * Creates a new instance to resolve "class_name" placeholders in
     * per-{@link org.acmsl.queryj.templates.packaging.TemplateDef} templates.
     * @param context the {@link DefaultTemplatePackagingContext context}.
     */
    public PerTemplateDefClassNameHandler(@NotNull final DefaultTemplatePackagingContext context)
    {
        super(context);
    }

    /**
     * Returns "class_name".
     * @return such variable name.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return Literals.CLASS_NAME;
    }

    /**
     * Resolves "class_name" values.
     * @param context the {@link DefaultTemplatePackagingContext context}.
     * @return such value.
     */
    @NotNull
    @Override
    protected String resolveContextValue(@NotNull final DefaultTemplatePackagingContext context)
    {
        return
            resolveContextValue(
                context.getTemplateDef(), context.getTemplateName(), TemplatePackagingUtils.getInstance());
    }

    /**
     * Resolves "class_name" values.
     * @param templateDef the {@link TemplateDef}.
     * @param templateName the template name.
     * @param templatePackagingUtils the {@link TemplatePackagingUtils} instance.
     * @return such value.
     */
    @NotNull
    protected String resolveContextValue(
        @NotNull final TemplateDef<String> templateDef,
        @NotNull final String templateName,
        @NotNull final TemplatePackagingUtils templatePackagingUtils)
    {
        @NotNull final String result =
            templatePackagingUtils.buildFilename(templateDef, templateName, "");

        return result;
    }
}
