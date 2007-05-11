/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

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
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.customsql.CustomResultUtils;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.customsql.Sql;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplate;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.DAOTemplateUtils;
import org.acmsl.queryj.tools.templates.dao.CustomResultSetExtractorTemplate;
import org.acmsl.queryj.tools.templates.dao.CustomResultSetExtractorTemplateGenerator;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.handlers.BasePerCustomResultTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Builds custom ResultSetExtractor templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class CustomResultSetExtractorTemplateBuildHandler
    extends  BasePerCustomResultTemplateBuildHandler
{
    /**
     * An empty template array.
     */
    protected static final CustomResultSetExtractorTemplate[]
        EMPTY_CUSTOMRESULTSETEXTRACTOR_TEMPLATE_ARRAY =
            new CustomResultSetExtractorTemplate[0];

    /**
     * Creates a CustomResultSetExtractorTemplateBuildHandler.
     */
    public CustomResultSetExtractorTemplateBuildHandler() {};

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    protected BasePerCustomResultTemplateFactory retrieveTemplateFactory()
    {
        return CustomResultSetExtractorTemplateGenerator.getInstance();
    }

    /**
     * Checks whether the result should be processed.
     * @param customResult the custom result.
     * @param customSqlProvider the custom sql provider.
     * @param metadataManager the database metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param header the header.
     * @return <code>true</code> in such case.
     */
    protected boolean shouldProcessResult(
        final Result customResult,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String repositoryName,
        final String header)
    {
        return true;
    }
    
    /**
     * Retrieves the package name.
     * @param customResult the custom result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the database metadata manager.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrievePackage(
        final Result customResult,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final String engineName,
        final String projectPackage,
        final PackageUtils packageUtils)
      throws BuildException
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
    protected void storeTemplates(
        final BasePerCustomResultTemplate[] templates, final Map parameters)
    {
        Collection t_cFilteredTemplates = new ArrayList();
        
        int t_iCount = (templates != null) ? templates.length : 0;

        BasePerCustomResultTemplate t_Template;
        
        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_Template = templates[t_iIndex];
            
            if  (matchesSqlFilter(t_Template))
            {
                t_cFilteredTemplates.add(t_Template);
            }
        }
        
        parameters.put(
            TemplateMappingManager.CUSTOM_RESULTSET_EXTRACTOR_TEMPLATES,
            t_cFilteredTemplates.toArray(EMPTY_BASEPERCUSTOMRESULTTEMPLATE_ARRAY));
    }

    /**
     * Checks whether the template matches the filter consisting of
     * finding out if there's any custom sql defined for the custom result.
     * @param template the template to check.
     * @return <code>true</code> in such case.
     */
    protected boolean matchesSqlFilter(
        final BasePerCustomResultTemplate template)
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
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param customResultUtils the <code>CustomResultUtils</code> instance.
     * @return <code>true</code> in such case.
     * @precondition resultElement != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition customResultUtils != null
     */
    protected boolean matchesSqlFilter(
        final Result resultElement,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final CustomResultUtils customResultUtils)
    {
        boolean result = false;

        String t_strTable =
            customResultUtils.retrieveTable(
                resultElement, customSqlProvider, metadataManager);

        result = (t_strTable != null);

        if  (!result)
        {
            // Let's check if it's associated to the repository DAO.
            Sql[] t_aSql =
                customResultUtils.findSqlElementsByResultId(
                    resultElement.getId(), customSqlProvider);

            int t_iCount = (t_aSql != null) ? t_aSql.length : 0;

            Sql t_Sql;

            for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                t_Sql = t_aSql[t_iIndex];

                if  (   (t_Sql != null)
                     && (t_Sql.getRepositoryScope() != null))
                {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }
    
    /**
     * Retrieves the package name from the attribute map.
     * @param projectPackage the project package.
     * @param engineName the engine name.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition engineName != null
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String projectPackage,
        final String engineName,
        final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveCustomResultSetExtractorPackage(
                projectPackage, engineName);
    }
}