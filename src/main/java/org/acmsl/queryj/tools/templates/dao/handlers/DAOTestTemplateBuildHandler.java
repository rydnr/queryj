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
 * Filename: DAOTestTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a DAO test template using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.DAOTestTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOTestTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.DAOTestTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.TestTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Builds a DAO test template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class DAOTestTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a <code>DAOTestTemplateBuildHandler</code> instance.
     */
    public DAOTestTemplateBuildHandler() {};

    /**
     * Handles given parameters.
     *
     *
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        return
            handle(
                parameters,
                retrieveDatabaseMetaData(parameters),
                retrieveMetadataManager(parameters));
    }
    
    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @param metaData the database metadata.
     * @param metadataManager the database metadata manager.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     * @precondition metadataManager != null
     */
    protected boolean handle(
        @NotNull final Map parameters,
        @NotNull final DatabaseMetaData metaData,
        final MetadataManager metadataManager)
      throws  QueryJBuildException
    {
        try
        {
            buildTemplates(
                parameters,
                metaData,
                metadataManager,
                retrieveTableTemplates(parameters),
                metaData.getDatabaseProductName(),
                retrieveDatabaseProductVersion(metaData),
                retrieveDAOPackage(
                    metaData.getDatabaseProductName(),
                    parameters),
                retrieveValueObjectPackage(parameters),
                retrieveJdbcDriver(parameters),
                retrieveJdbcUrl(parameters),
                retrieveJdbcUsername(parameters),
                retrieveJdbcPassword(parameters),
                retrieveHeader(parameters),
                DAOTestTemplateGenerator.getInstance());
        }
        catch  (@NotNull final SQLException sqlException)
        {
            throw
                new QueryJBuildException(
                      "Cannot retrieve database product name, "
                    + "version or quote string",
                    sqlException);
        }

        return false;
    }
    
    /**
     * Builds the DAO test templates.
     * @param parameters the parameters.
     * @param metaData the database metadata.
     * @param metadataManager the database metadata manager.
     * @param tableTemplates the table templates.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param daoPackage the DAO package.
     * @param voPackage the value-object package.
     * @param jdbcDriver the JDBC driver.
     * @param jdbcUrl the JDBC url.
     * @param jdbcUsername the JDBC username.
     * @param jdbcPassword the JDBC password.
     * @param header the header.
     * @param templateFactory the template factory.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     * @precondition metadataManager != null
     * @precondition tableTemplates != null
     * @precondition templateFactory != null
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        @NotNull final DatabaseMetaData metaData,
        final MetadataManager metadataManager,
        @Nullable final TableTemplate[] tableTemplates,
        @NotNull final String engineName,
        final String engineVersion,
        final String daoPackage,
        final String voPackage,
        final String jdbcDriver,
        final String jdbcUrl,
        final String jdbcUsername,
        final String jdbcPassword,
        final String header,
        @NotNull final DAOTestTemplateFactory factory)
      throws  QueryJBuildException
    {
        try
        {
            int t_iLength =
                (tableTemplates != null) ? tableTemplates.length : 0;

            String t_strPackage =
                retrieveDAOTestPackage(
                    engineName, parameters);

            @NotNull DAOTestTemplate[] t_aDAOTestTemplates =
                new DAOTestTemplate[t_iLength];

            String t_strQuote = fixQuote(metaData.getIdentifierQuoteString());

            for  (int t_iDAOTestIndex = 0;
                      t_iDAOTestIndex < t_iLength;
                      t_iDAOTestIndex++) 
            {
                t_aDAOTestTemplates[t_iDAOTestIndex] =
                    factory.createDAOTestTemplate(
                        tableTemplates[t_iDAOTestIndex],
                        metadataManager,
                        t_strPackage,
                        engineName,
                        engineVersion,
                        t_strQuote,
                        daoPackage,
                        voPackage,
                        jdbcDriver,
                        jdbcUrl,
                        jdbcUsername,
                        jdbcPassword,
                        header);

                storeTestTemplate(
                    t_aDAOTestTemplates[t_iDAOTestIndex],
                    parameters);
            }

            storeDAOTestTemplates(t_aDAOTestTemplates, parameters);
        }
        catch  (@NotNull final SQLException sqlException)
        {
            throw
                new QueryJBuildException(
                    "Cannot retrieve database quote string",
                    sqlException);
        }
    }

    /**
     * Retrieves the DAO Test's package name from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition engineName != nul
     * @precondition parameters != null
     */
    protected String retrieveDAOTestPackage(
        @NotNull final String engineName, @NotNull final Map parameters)
    {
        return
            retrieveDAOTestPackage(
                engineName, parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the DAO Test's package name from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition engineName != nul
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrieveDAOTestPackage(
        @NotNull final String engineName,
        @NotNull final Map parameters,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveDAOTestPackage(
                retrieveProjectPackage(parameters),
                engineName,
                retrieveUseSubfoldersFlag(parameters));
    }

    /**
     * Retrieves the DAO's package name from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition engineName != nul
     * @precondition parameters != null
     */
    protected String retrieveDAOPackage(
        @NotNull final String engineName, @NotNull final Map parameters)
    {
        return
            retrieveDAOPackage(
                engineName,
                parameters,
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the DAO's package name from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition engineName != nul
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrieveDAOPackage(
        @NotNull final String engineName,
        @NotNull final Map parameters,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveDAOPackage(
                retrieveProjectPackage(parameters),
                engineName);
    }

    /**
     * Retrieves the value object's package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition parameters != null
     */
    protected String retrieveValueObjectPackage(@NotNull final Map parameters)
    {
        return
            retrieveValueObjectPackage(
                parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the value object's package name from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrieveValueObjectPackage(
        @NotNull final Map parameters, @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveValueObjectPackage(
                retrieveProjectPackage(parameters));
    }

    /**
     * Retrieves the test template collection.
     * @param parameters the parameter map.
     * @return the test templates.
     * @precondition parameters != null
     */
    @NotNull
    protected Collection retrieveTestTemplates(@NotNull final Map parameters)
    {
        return
            (Collection) parameters.get(TemplateMappingManager.TEST_TEMPLATES);
    }

    /**
     * Stores the DAO template collection in given attribute map.
     * @param daoTestTemplates the DAO templates.
     * @param parameters the parameter map.
     * @precondition daoTestTemplates != null
     * @precondition parameters != null
     */
    protected void storeDAOTestTemplates(
        final DAOTestTemplate[] daoTestTemplates,
        @NotNull final Map parameters)
    {
        parameters.put(TemplateMappingManager.DAO_TEST_TEMPLATES, daoTestTemplates);
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @precondition parameters != null
     */
    @NotNull
    protected TableTemplate[] retrieveTableTemplates(
        @NotNull final Map parameters)
    {
        return
            (TableTemplate[])
                parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
    }

    /**
     * Stores the test template collection.
     * @param templates the test templates.
     * @param parameters the parameter map.
     * @return the test templates.
     * @precondition templates != null
     * @precondition parameters != null
     */
    protected void storeTestTemplates(
        final Collection templates, @NotNull final Map parameters)
    {
        parameters.put(TemplateMappingManager.TEST_TEMPLATES, templates);
    }

    /**
     * Stores a new test template.
     * @param testTemplate the test template.
     * @param parameters the parameter map.
     * @precondition template != null
     * @precondition parameters != null
     */
    protected void storeTestTemplate(
        final TestTemplate template, @NotNull final Map parameters)
    {
        @NotNull Collection t_cTestTemplates = retrieveTestTemplates(parameters);

        if  (t_cTestTemplates == null) 
        {
            t_cTestTemplates = new ArrayList();
            storeTestTemplates(t_cTestTemplates, parameters);
        }

        t_cTestTemplates.add(template);
    }
}
