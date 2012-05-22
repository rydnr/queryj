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

 ******************************************************************************
 *
 * Filename: ResultSetExtractorTemplateBuildHandler.java
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
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.ResultSetExtractorTemplate;
import org.acmsl.queryj.tools.templates.dao.ResultSetExtractorTemplateGenerator;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
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
import java.util.List;
import java.util.Map;

/**
 * Builds a ResultSetExtractor template.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class ResultSetExtractorTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a <code>ResultSetExtractorTemplateBuildHandler</code>
     * instance.
     */
    public ResultSetExtractorTemplateBuildHandler() {}

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        buildTemplates(
            parameters,
            retrieveMetadataManager(parameters),
            retrieveCustomSqlProvider(parameters),
            retrieveProjectPackage(parameters),
            retrieveTableRepositoryName(parameters),
            retrieveHeader(parameters),
            retrieveImplementMarkerInterfaces(parameters),
            retrieveJmx(parameters),
            ResultSetExtractorTemplateGenerator.getInstance(),
            filterTableTemplates(
                retrieveTableTemplates(parameters),
                retrieveCustomSqlProvider(parameters)));

        return false;
    }

    /**
     * Builds the <code>ResultSetExtractor</code> templates..
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param basePackageName the base package name.
     * @param repositoryName the repository.
     * @param header the header.
     * @param implementMarkerInterfaces whether to implement marker
     * interfaces.
     * @param jmx whether to include JMX support.
     * @param templateFactory the template factory.
     * @param tableTemplates the table templates.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final BasePerTableTemplateFactory<ResultSetExtractorTemplate> templateFactory,
        @Nullable final TableTemplate[] tableTemplates)
      throws  QueryJBuildException
    {
        int t_iLength = (tableTemplates != null) ? tableTemplates.length : 0;
        
        @NotNull final List<ResultSetExtractorTemplate> t_lTemplates =
            new ArrayList<ResultSetExtractorTemplate>(t_iLength);

        String t_strTableName;

        if (tableTemplates != null)
        {
            for  (TableTemplate t_TableTemplate: tableTemplates)
            {
                if  (t_TableTemplate != null)
                {
                    t_strTableName = t_TableTemplate.getTemplateContext().getTableName();

                    t_lTemplates.add(
                        templateFactory.createTemplate(
                            metadataManager,
                            customSqlProvider,
                            retrievePackage(
                                metadataManager.getEngineName(), t_strTableName, parameters),
                            basePackageName,
                            repositoryName,
                            header,
                            implementMarkerInterfaces,
                            jmx,
                            t_strTableName,
                            null));
                }
            }
        }

        storeTemplates(t_lTemplates.toArray(new ResultSetExtractorTemplate[t_lTemplates.size()]), parameters);
    }

    /**
     * Filters the table templates to process.
     * @param tableTemplates the table templates.
     * @param customSqlProvider the custom SQL provider.
     * @return the filtered templates.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected TableTemplate[] filterTableTemplates(
        @NotNull final TableTemplate[] tableTemplates,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return tableTemplates;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param engineName the engine name.
     * @param tableName the table name.
     * @param parameters the parameter map.
     * @return the package name.
     */
    @NotNull
    protected String retrievePackage(
        @NotNull final String engineName,
        @NotNull final String tableName,
        @NotNull final Map parameters)
    {
        return
            retrievePackage(
                retrieveProjectPackage(parameters),
                engineName,
                tableName,
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param projectPackage the project package.
     * @param engineName the engine name.
     * @param tableName the table name.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     */
    @NotNull
    protected String retrievePackage(
        @NotNull final String projectPackage,
        @NotNull final String engineName,
        @NotNull final String tableName,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveResultSetExtractorPackage(
                projectPackage,
                engineName,
                tableName);
    }

    /**
     * Retrieves the repository name.
     * @param parameters the parameters.
     * @return the repository's name.
     */
    @NotNull
    @SuppressWarnings("unchecked")
    protected String retrieveTableRepositoryName(@NotNull final Map parameters)
    {
        return
            (String)
                parameters.get(
                    ParameterValidationHandler.REPOSITORY);
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @precondition parameters != null
     */
    @NotNull
    protected TableTemplate[] retrieveTableTemplates(@NotNull final Map parameters)
    {
        return
            (TableTemplate[])
                parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
    }

    /**
     * Retrieves the custom-sql provider from the attribute map.
     * @param parameters the parameter map.
     * @return the provider.
     * @precondition parameters != null
     */
    @NotNull
    public static CustomSqlProvider retrieveCustomSqlProvider(
        @NotNull final Map parameters)
    {
        return
            DAOTemplateBuildHandler.retrieveCustomSqlProvider(parameters);
    }

    /**
     * Stores the templates in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     * @precondition templates != null
     * @precondition parameters != null
     */
    @SuppressWarnings("unchecked")
    protected void storeTemplates(
        final ResultSetExtractorTemplate[] templates,
        @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.RESULTSET_EXTRACTOR_TEMPLATES,
            templates);
    }
}
