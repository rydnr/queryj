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

 ******************************************************************************
 *
 * Filename: CustomResultSetExtractorTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a ResultSetExtractor template.
 *
 */
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.CustomResultUtils;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.templates.BasePerCustomResultTemplateContext;
import org.acmsl.queryj.templates.dao.CustomResultSetExtractorTemplate;
import org.acmsl.queryj.templates.dao.CustomResultSetExtractorTemplateGenerator;
import org.acmsl.queryj.templates.handlers.BasePerCustomResultTemplateBuildHandler;
import org.acmsl.queryj.templates.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Builds custom ResultSetExtractor templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class CustomResultSetExtractorTemplateBuildHandler
    extends  BasePerCustomResultTemplateBuildHandler<CustomResultSetExtractorTemplate, CustomResultSetExtractorTemplateGenerator>
{
    /**
     * Creates a CustomResultSetExtractorTemplateBuildHandler.
     */
    public CustomResultSetExtractorTemplateBuildHandler() {}

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected CustomResultSetExtractorTemplateGenerator retrieveTemplateFactory()
    {
        return CustomResultSetExtractorTemplateGenerator.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String retrievePackage(
        @NotNull final Result customResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveCustomResultSetExtractorPackage(
                projectPackage, engineName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void storeTemplates(
        @NotNull final List<CustomResultSetExtractorTemplate> templates, @NotNull final Map parameters)
    {
        @NotNull final List<CustomResultSetExtractorTemplate> t_lFilteredTemplates =
            new ArrayList<CustomResultSetExtractorTemplate>();
        
        for  (CustomResultSetExtractorTemplate t_Template : templates)
        {
            if  (matchesSqlFilter(t_Template.getTemplateContext()))
            {
                t_lFilteredTemplates.add(t_Template);
            }
        }
        
        parameters.put(
            TemplateMappingManager.CUSTOM_RESULTSET_EXTRACTOR_TEMPLATES,
            t_lFilteredTemplates);
    }

    /**
     * Checks whether the template matches the filter consisting of
     * finding out if there's any custom sql defined for the custom result.
     * @param context the {@link BasePerCustomResultTemplateContext context}.
     * @return <code>true</code> in such case.
     */
    protected boolean matchesSqlFilter(@NotNull final BasePerCustomResultTemplateContext context)
    {
        return
            matchesSqlFilter(
                context.getResult(),
                context.getCustomSqlProvider(),
                context.getMetadataManager(),
                CustomResultUtils.getInstance());
    }
    
    /**
     * Checks whether the template matches the filter consisting of
     * finding out if there's any custom sql defined for the custom result.
     * @param resultElement the custom result.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customResultUtils the {@link CustomResultUtils} instance.
     * @return <code>true</code> in such case.
     * @precondition resultElement != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition customResultUtils != null
     */
    protected boolean matchesSqlFilter(
        @NotNull final Result resultElement,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomResultUtils customResultUtils)
    {
        boolean result;

        @Nullable String t_strTable =
            customResultUtils.retrieveTable(
                resultElement, customSqlProvider, metadataManager);
        
        result = (t_strTable != null);

        return result;
    }
    
    /**
     * Retrieves the package name from the attribute map.
     * @param projectPackage the project package.
     * @param engineName the engine name.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition engineName != null
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String projectPackage,
        @NotNull final String engineName,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveCustomResultSetExtractorPackage(
                projectPackage, engineName);
    }
}
