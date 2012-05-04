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
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.customsql.CustomResultUtils;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplate;
import org.acmsl.queryj.tools.templates.dao.CustomResultSetExtractorTemplate;
import org.acmsl.queryj.tools.templates.dao.CustomResultSetExtractorTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.BasePerCustomResultTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
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
     * Retrieves the template factory.
     * @return such instance.
     */
    @NotNull
    protected CustomResultSetExtractorTemplateGenerator retrieveTemplateFactory()
    {
        return CustomResultSetExtractorTemplateGenerator.getInstance();
    }

    /**
     * Retrieves the package name.
     * @param customResult the custom result.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the database metadata manager.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the {@link PackageUtils} instance.
     * @return the package name.
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
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     * @precondition templates != null
     * @precondition parameters != null
     */
    @Override
    protected void storeTemplates(
        @NotNull final List<CustomResultSetExtractorTemplate> templates, @NotNull final Map parameters)
    {
        @NotNull final Collection<CustomResultSetExtractorTemplate> t_cFilteredTemplates =
            new ArrayList<CustomResultSetExtractorTemplate>();
        
        int t_iCount = (templates != null) ? templates.size() : 0;

        @Nullable CustomResultSetExtractorTemplate t_Template;
        
        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_Template = templates.get(t_iIndex);
            
            if  (matchesSqlFilter(t_Template))
            {
                t_cFilteredTemplates.add(t_Template);
            }
        }
        
        parameters.put(
            TemplateMappingManager.CUSTOM_RESULTSET_EXTRACTOR_TEMPLATES,
            t_cFilteredTemplates.toArray(
                new CustomResultSetExtractorTemplate[t_iCount]));
    }

    /**
     * Checks whether the template matches the filter consisting of
     * finding out if there's any custom sql defined for the custom result.
     * @param template the template to check.
     * @return <code>true</code> in such case.
     */
    protected boolean matchesSqlFilter(@Nullable final BasePerCustomResultTemplate template)
    {
        boolean result = false;

        if  (template != null)
        {
            result =
                matchesSqlFilter(
                    template.getResult(),
                    template.getCustomSqlProvider(),
                    template.getMetadataManager(),
                    CustomResultUtils.getInstance());
        }
        
        return result;
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
        boolean result = false;

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
