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
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomResultUtils;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.metadata.DecorationUtils;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.InvalidTemplateException;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplate;

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
    extends   BasePerCustomResultTemplate
{
    private static final long serialVersionUID = 9130292102465717049L;

    /**
     * Builds a <code>CustomResultSetExtractorTemplate</code> using
     * information.
     * @param result the custom result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @precondition result != null
     * @precondition customSqlProvider != null
     * @precondition metadataNanager != null
     * @precondition decoratorFactory != null
     */
    public CustomResultSetExtractorTemplate(
        final Result result,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final String header,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String repositoryName)
    {
        super(
            result,
            customSqlProvider,
            metadataManager,
            header,
            decoratorFactory,
            packageName,
            engineName,
            engineVersion,
            basePackageName,
            repositoryName);
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
     * @precondition input != null
     * @precondition result != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     */
    protected void fillCommonParameters(
        @NotNull final Map input,
        @NotNull final Result result,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        final String engineName,
        final String engineVersion)
    {
        super.fillCommonParameters(
            input,
            result,
            customSqlProvider,
            metadataManager,
            decoratorFactory,
            engineName,
            engineVersion);

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
    public String retrieveTable()
    {
        return
            retrieveTable(
                getResult(),
                getCustomSqlProvider(),
                getMetadataManager(),
                CustomResultUtils.getInstance());
    }

    /**
     * Retrieves the table associated to the result.
     * @param result the result element.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the database metadata manager.
     * @param customResultUtils the <code>CustomResultUtils</code> instance.
     * @return the table name.
     * @precondition result != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition customResultUtils != null
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
    @NotNull
    @Override
    public StringTemplateGroup retrieveGroup()
    {
        return
            retrieveGroup(
                "/org/acmsl/queryj/dao/" + getTemplateName() + ".stg");
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    @NotNull
    public String getTemplateName()
    {
        return "CustomResultSetExtractor";
    }
}
