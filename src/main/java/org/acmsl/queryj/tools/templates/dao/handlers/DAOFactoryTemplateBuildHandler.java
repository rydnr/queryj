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
 * Filename: DAOFactoryTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a DAO factory template using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.DAOFactoryTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOFactoryTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.DAOFactoryTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * Builds a DAO factory template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class DAOFactoryTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty table template array.
     */
    public static final TableTemplate[] EMPTY_TABLE_TEMPLATE_ARRAY =
        new TableTemplate[0];

    /**
     * Creates a <code>DAOFactoryTemplateBuildHandler</code> instance.
     */
    public DAOFactoryTemplateBuildHandler() {};

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param log the log instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
      throws  QueryJBuildException
    {
        return
            handle(
                parameters,
                retrieveDatabaseMetaData(parameters),
                retrieveProjectPackage(parameters));
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param metaData the database metadata.
     * @param basePackage the base package.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     * @precondition basePackage != null
     */
    protected boolean handle(
        final Map parameters,
        final DatabaseMetaData metaData,
        final String basePackage)
      throws  QueryJBuildException
    {
        boolean result = false;

        try
        {
            buildTemplates(
                parameters,
                retrieveJNDIDataSource(parameters),
                retrievePackage(
                    basePackage,
                    metaData.getDatabaseProductName().toLowerCase()),
                basePackage,
                metaData.getDatabaseProductName(),
                retrieveHeader(parameters),
                retrieveTableTemplates(parameters),
                DAOFactoryTemplateGenerator.getInstance());
        }
        catch  (final SQLException sqlException)
        {
            throw
                new QueryJBuildException(
                    "Cannot retrieve database product name", sqlException);
        }

        return result;
    }

    /**
     * Builds the <code>DAOFactory</code> templates.
     * @param parameters the parameters.
     * @param jndiDataSource the JNDI location of the data source.
     * @param packageName the package name.
     * @param basePackage the base package.
     * @param engineName the engine name.
     * @param header the header.
     * @param tableTemplates the table templates.
     * @param templateFactory the template factory.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition jndiDataSource != null
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition tableTemplates != null
     * @precondition templateFactory != null
     */
    protected void buildTemplates(
        final Map parameters,
        final String jndiDataSource,
        final String packageName,
        final String basePackage,
        final String engineName,
        final String header,
        final TableTemplate[] tableTemplates,
        final DAOFactoryTemplateFactory templateFactory)
      throws  QueryJBuildException
    {
        int t_iLength =
            (tableTemplates != null) ? tableTemplates.length : 0;
            
        DAOFactoryTemplate[] t_aDAOFactoryTemplates =
            new DAOFactoryTemplate[t_iLength];

        for  (int t_iDAOFactoryIndex = 0;
                  t_iDAOFactoryIndex < t_iLength;
                  t_iDAOFactoryIndex++) 
        {
            t_aDAOFactoryTemplates[t_iDAOFactoryIndex] =
                templateFactory.createDAOFactoryTemplate(
                    tableTemplates[t_iDAOFactoryIndex],
                    packageName,
                    engineName,
                    basePackage,
                    jndiDataSource,
                    header);
        }

        storeDAOFactoryTemplates(t_aDAOFactoryTemplates, parameters);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param basePackage the base package.
     * @param engineName the engine name.
     * @return the package name.
     * @precondition parameters != null
     */
    protected String retrievePackage(
        final String basePackage, final String engineName)
    {
        return
            retrievePackage(
                basePackage, engineName, PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param basePackage the base package.
     * @param engineName the engine name.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String basePackage,
        final String engineName,
        final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveDAOFactoryPackage(
                basePackage, engineName);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return such folder.
     * @precondition engineName != null
     * @precondition parameters != null
     */
    protected File retrieveOutputDir(
        final String engineName, final Map parameters)
    {
        return
            retrieveOutputDir(
                engineName, parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @precondition engineName != null
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected File retrieveOutputDir(
        final String engineName,
        final Map parameters,
        final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveDAOFactoryFolder(
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                engineName,
                retrieveUseSubfoldersFlag(parameters));
    }

    /**
     * Retrieves the JNDI location of the data source from the attribute map.
     * @param parameters the parameter map.
     * @return the JNDI location.
     * @precondition parameters != null
     */
    protected String retrieveJNDIDataSource(final Map parameters)
    {
        return
            (String)
                parameters.get(
                    ParameterValidationHandler.JNDI_DATASOURCES);
    }

    /**
     * Stores the DAO factory template collection in given attribute map.
     * @param daoFactoryTemplates the DAO factory templates.
     * @param parameters the parameter map.
     * @precondition daoFactoryTemplates != null
     * @precondition parameters != null
     */
    protected void storeDAOFactoryTemplates(
        final DAOFactoryTemplate[] daoFactoryTemplates,
        final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.DAO_FACTORY_TEMPLATES,
            daoFactoryTemplates);
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @precondition parameters != null
     */
    protected TableTemplate[] retrieveTableTemplates(
        final Map parameters)
    {
        return
            (TableTemplate[])
                parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
    }
}
