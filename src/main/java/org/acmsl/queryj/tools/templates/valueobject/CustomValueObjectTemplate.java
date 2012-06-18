//;-*- mode: java -*-
/*
                        QueryJ

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

 *****************************************************************************
 *
 * Filename: CustomValueObjectTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create CustomValueObject implementation
 *              for each custom query requiring so.
 *
 * $Id$
 */
package org.acmsl.queryj.tools.templates.valueobject;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplate;

/*
 * Importing StringTemplate classes.
 */
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplateContext;
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Is able to create CustomValueObject implementations for each
 * custom query requiring so.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class CustomValueObjectTemplate
    extends   BasePerCustomResultTemplate<BasePerCustomResultTemplateContext>
{
    private static final long serialVersionUID = 2996313642901416338L;

    /**
     * Builds a <code>CustomValueObjectTemplate</code> using
     * information.
     * @param context the {@link BasePerCustomResultTemplateContext context}.
     */
    public CustomValueObjectTemplate(@NotNull final BasePerCustomResultTemplateContext context)
    {
        super(context);
    }

    /**
     * Fills the common parameters.
     * @param input the input.
     * @param result the result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     */
    @SuppressWarnings("unchecked,unused")
    protected void fillCommonParameters(
        @NotNull final Map input,
        @NotNull final Result result,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String engineName,
        @NotNull final String engineVersion)
    {
        @Nullable String t_strVoName = extractClassName(result.getClassValue());

        if  (t_strVoName != null)
        {
            input.put("vo_name", t_strVoName);
        }
    }

    /**
     * Extracts the class name of given fully-qualified class.
     * @param fqcn such information.
     * @return the class name.
     * @precondition fqcn != null
     */
    @Nullable
    public String extractClassName(final String fqcn)
    {
        return extractClassName(fqcn, PackageUtils.getInstance());
    }

    /**
     * Extracts the class name of given fully-qualified class.
     * @param fqcn such information.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the class name.
     * @precondition fqcn != null
     * @precondition packageUtils != null
     */
    @Nullable
    protected String extractClassName(
        final String fqcn, @NotNull final PackageUtils packageUtils)
    {
        return packageUtils.extractClassName(fqcn);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Nullable
    @Override
    public StringTemplateGroup retrieveGroup()
    {
        return
            retrieveGroup("/org/acmsl/queryj/vo/" + getTemplateName() + ".stg");
    }

    /**
     * Returns "CustomValueObject".
     * @return such information.
     */
    @NotNull
    public String getTemplateName()
    {
        return "CustomValueObject";
    }
}
