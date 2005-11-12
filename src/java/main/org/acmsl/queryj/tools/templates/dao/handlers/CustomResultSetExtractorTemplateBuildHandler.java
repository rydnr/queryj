/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile$
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
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.ResultElement;
import org.acmsl.queryj.tools.customsql.SqlElement;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.DAOTemplateUtils;
import org.acmsl.queryj.tools.templates.dao.CustomResultSetExtractorTemplate;
import org.acmsl.queryj.tools.templates.dao.CustomResultSetExtractorTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.CustomResultSetExtractorTemplateGenerator;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
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
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The SqlElement types.
     */
    protected static final String[] TYPES =
        new String[]
        {
            SqlElement.SELECT,
            SqlElement.SELECT_FOR_UPDATE
        };

    /**
     * An empty template array.
     */
    protected static final CustomResultSetExtractorTemplate[]
        EMPTY_CUSTOMRESULTSETEXTRACTOR_TEMPLATE_ARRAY =
            new CustomResultSetExtractorTemplate[0];

    /**
     * Creates a ResultSetExtractorTemplateBuildHandler.
     */
    public CustomResultSetExtractorTemplateBuildHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition command != null
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        return handle(command.getAttributeMap());
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
        throws  BuildException
    {
        return
            handle(
                parameters,
                retrieveDatabaseMetaData(parameters));
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param metaData the database metadata.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     */
    protected boolean handle(
        final Map parameters, final DatabaseMetaData metaData)
      throws  BuildException
    {
        boolean result = false;

        try
        {
            handle(
                parameters,
                metaData.getDatabaseProductName());
        }
        catch  (final SQLException sqlException)
        {
            throw new BuildException(sqlException);
        }

        return result;
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     */
    protected boolean handle(
        final Map parameters, final String engineName)
      throws  BuildException
    {
        return
            handle(
                parameters,
                engineName,
                retrieveMetadataManager(parameters),
                retrieveProjectPackage(parameters),
                retrieveTableRepositoryName(parameters),
                CustomResultSetExtractorTemplateGenerator.getInstance(),
                retrieveTableTemplates(parameters),
                retrieveCustomSqlProvider(parameters),
                DAOTemplateUtils.getInstance());
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param metadataManager the database metadata manager.
     * @param basePackageName the base package name.
     * @param repository the repository.
     * @param templateFactory the template factory.
     * @param tableTemplates the table templates.
     * @param customSqlProvider the <code>CustomSqlprovider</code> instance.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     * @precondition packageName != null
     * @precondition basePackageName != null
     * @precondition repositoryName != null
     * @precondition templateFactory != null
     * @precondition tableTemplates != null
     * @precondition customSqlProvider != null
     */
    protected boolean handle(
        final Map parameters,
        final String engineName,
        final MetadataManager metadataManager,
        final String basePackageName,
        final String repositoryName,
        final CustomResultSetExtractorTemplateFactory templateFactory,
        final TableTemplate[] tableTemplates,
        final CustomSqlProvider customSqlProvider,
        final DAOTemplateUtils daoTemplateUtils)
      throws  BuildException
    {
        boolean result = false;

        Collection t_cTemplates = new ArrayList();

        SqlElement[] t_CurrentSqlElements = null;

        try
        {
            for  (int t_iTypeIndex = 0;
                      t_iTypeIndex < TYPES.length;
                      t_iTypeIndex++)
            {
                ResultElement[] t_aResultElements =
                    daoTemplateUtils.retrieveResultElementsByType(
                        customSqlProvider, TYPES[t_iTypeIndex]);

                for  (int t_iTableIndex = 0;
                          t_iTableIndex < tableTemplates.length;
                          t_iTableIndex++)
                {
                    for  (int t_iResultIndex = 0;
                              t_iResultIndex < t_aResultElements.length;
                              t_iResultIndex++)
                    {
                        SqlElement[] t_aSqlElements =
                            daoTemplateUtils.retrieveSqlElementsByResultId(
                                customSqlProvider,
                                t_aResultElements[t_iResultIndex].getId());

                        for  (int t_iSqlIndex = 0;
                                  t_iSqlIndex < t_aSqlElements.length;
                                  t_iSqlIndex++)
                        {
                            if  (daoTemplateUtils.matches(
                                     tableTemplates[t_iTableIndex].getTableName(),
                                     t_aSqlElements[t_iSqlIndex].getDao()))
                            {
                                t_cTemplates.add(
                                    templateFactory
                                        .createCustomResultSetExtractorTemplate(
                                            t_aResultElements[t_iResultIndex],
                                            customSqlProvider,
                                            tableTemplates[t_iTableIndex],
                                            metadataManager,
                                            retrievePackage(
                                                engineName,
                                                tableTemplates[t_iTableIndex]
                                                    .getTableName(),
                                                parameters),
                                            basePackageName,
                                            repositoryName));
                            }
                        }
                    }
                }
            }

            storeTemplates(
                (CustomResultSetExtractorTemplate[])
                    t_cTemplates.toArray(
                        EMPTY_CUSTOMRESULTSETEXTRACTOR_TEMPLATE_ARRAY),
                parameters);
        }
        catch  (final QueryJException queryjException)
        {
            throw new BuildException(queryjException);
        }
        
        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param engineName the engine name.
     * @param tableName the table name.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition tableName != null
     */
    protected String retrievePackage(
        final String engineName,
        final String tableName,
        final Map parameters)
      throws  BuildException
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
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition engineName != null
     * @precondition projectPackage != null
     * @precondition tableName != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String projectPackage,
        final String engineName,
        final String tableName,
        final PackageUtils packageUtils)
      throws  BuildException
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
     * @precondition parameters != null
     */
    protected String retrieveTableRepositoryName(final Map parameters)
    {
        return
            (String)
                parameters.get(
                    ParameterValidationHandler.REPOSITORY);
    }

    /**
     * Retrieves the custom-sql provider from the attribute map.
     * @param parameters the parameter map.
     * @return the provider.
     * @throws BuildException if the manager retrieval process if faulty.
     * @precondition parameters != null
     */
    public static CustomSqlProvider retrieveCustomSqlProvider(
        final Map parameters)
      throws  BuildException
    {
        return
            DAOTemplateBuildHandler.retrieveCustomSqlProvider(parameters);
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @throws BuildException if the templates cannot be stored for any reason.
     * @precondition parameters != null
     */
    protected TableTemplate[] retrieveTableTemplates(final Map parameters)
        throws  BuildException
    {
        return
            (TableTemplate[])
                parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
    }

    /**
     * Stores the templates in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     * @precondition templates != null
     * @precondition parameters != null
     */
    protected void storeTemplates(
        final CustomResultSetExtractorTemplate[] templates,
        final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.CUSTOM_RESULTSET_EXTRACTOR_TEMPLATES,
            templates);
    }
}
