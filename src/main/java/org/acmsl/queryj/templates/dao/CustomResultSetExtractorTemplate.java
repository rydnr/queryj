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
 * Filename: CustomResultSetExtractorTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create CustomResultSetExtractor implementation
 *              for each custom query requiring so.
 *
 * $Id$
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.customsql.CustomResultUtils;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.DecorationUtils;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.templates.BasePerCustomResultTemplateContext;
import org.acmsl.queryj.templates.InvalidTemplateException;
import org.acmsl.queryj.templates.BasePerCustomResultTemplate;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Is able to create CustomResultSetExtractor implementations for each
 * custom query requiring so.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class CustomResultSetExtractorTemplate
    extends   BasePerCustomResultTemplate<BasePerCustomResultTemplateContext>
{
    private static final long serialVersionUID = 9130292102465717049L;

    /**
     * Builds a <code>CustomResultSetExtractorTemplate</code> using
     * information.
     * @param context the {@link BasePerCustomResultTemplateContext} instance.
     */
    public CustomResultSetExtractorTemplate(@NotNull final BasePerCustomResultTemplateContext context)
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
        @Nullable String t_strTable =
            retrieveTable(
                result,
                customSqlProvider,
                metadataManager,
                CustomResultUtils.getInstance());

        if  (t_strTable != null)
        {
            input.put("table", t_strTable);
            input.put(
                "tableNormalizedLowercased", normalizeLowercase(t_strTable));
        }
    }

    /**
     * Retrieves the table associated to the result.
     * @return the table name.
     */
    @Nullable
    @SuppressWarnings("unused")
    public String retrieveTable()
    {
        return retrieveTable(getTemplateContext());
    }

    /**
     * Retrieves the table associated to the result.
     * @return the table name.
     */
    @Nullable
    public String retrieveTable(@NotNull final BasePerCustomResultTemplateContext context)
    {
        return
            retrieveTable(
                context.getResult(),
                context.getCustomSqlProvider(),
                context.getMetadataManager(),
                CustomResultUtils.getInstance());
    }

    /**
     * Retrieves the table associated to the result.
     * @param result the result element.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the database metadata manager.
     * @param customResultUtils the <code>CustomResultUtils</code> instance.
     * @return the table name.
     */
    @Nullable
    protected String retrieveTable(
        @NotNull final Result result,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomResultUtils customResultUtils)
      throws  InvalidTemplateException
    {
        return
            customResultUtils.retrieveTable(
                result, customSqlProvider, metadataManager);
    }

    /**
     * Normalizes and lowers the case of given value.
     * @param value the value.
     * @return the processed value.
     * @precondition value != null
     */
    protected String normalizeLowercase(final String value)
    {
        return normalizeLowercase(value, DecorationUtils.getInstance());
    }

    /**
     * Normalizes and lowers the case of given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the processed value.
     * @precondition value != null
     * @precondition decoratorUtils != null
     */
    protected String normalizeLowercase(
        final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalizeLowercase(value);
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
            retrieveGroup(
                "/org/acmsl/queryj/dao/" + getTemplateName() + ".stg");
    }

    /**
     * Returns "CustomResultSetExtractor".
     * @return such information.
     */
    @Override
    @NotNull
    public String getTemplateName()
    {
        return "CustomResultSetExtractor";
    }
}
