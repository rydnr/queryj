/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendáriz
 *
 * Description: Builds a DAO factory template using database metadata.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.templates.dao.DAOFactoryTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOFactoryTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TableTemplate;
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
import java.util.Map;

/**
 * Builds a DAO factory template using database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class DAOFactoryTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty table template array.
     */
    public static final TableTemplate[] EMPTY_TABLE_TEMPLATE_ARRAY =
        new TableTemplate[0];

    /**
     * Creates a DAOFactoryTemplateBuildHandler.
     */
    public DAOFactoryTemplateBuildHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        boolean result = false;

        if  (command != null) 
        {
            try
            {
                Map attributes = command.getAttributeMap();

                DatabaseMetaData t_MetaData =
                    retrieveDatabaseMetaData(attributes);

                DatabaseMetaDataManager t_MetaDataManager =
                    retrieveDatabaseMetaDataManager(attributes);

                String t_strBasePackage =
                    retrieveBasePackage(attributes);

                String t_strPackage =
                    retrievePackage(
                        t_strBasePackage,
                        t_MetaData.getDatabaseProductName().toLowerCase());

                String t_strJNDIDataSource =
                    retrieveJNDIDataSource(attributes);

                DAOFactoryTemplateGenerator t_DAOFactoryTemplateGenerator =
                    DAOFactoryTemplateGenerator.getInstance();

                if  (   (t_MetaData                    != null)
                     && (t_MetaDataManager             != null)
                     && (t_DAOFactoryTemplateGenerator != null))
                {
                    TableTemplate[] t_aTableTemplates =
                        retrieveTableTemplates(attributes);

                    if  (t_aTableTemplates != null)
                    {
                        DAOFactoryTemplate[] t_aDAOFactoryTemplates =
                            new DAOFactoryTemplate[t_aTableTemplates.length];

                        for  (int t_iDAOFactoryIndex = 0;
                                  t_iDAOFactoryIndex < t_aDAOFactoryTemplates.length;
                                  t_iDAOFactoryIndex++) 
                        {
                            String t_strQuote = t_MetaData.getIdentifierQuoteString();

                            if  (t_strQuote == null)
                            {
                                t_strQuote = "\"";
                            }

                            if  (t_strQuote.equals("\""))
                            {
                                t_strQuote = "\\\"";
                            }

                            t_aDAOFactoryTemplates[t_iDAOFactoryIndex] =
                                t_DAOFactoryTemplateGenerator.createDAOFactoryTemplate(
                                    t_aTableTemplates[t_iDAOFactoryIndex],
                                    t_MetaDataManager,
                                    t_strPackage,
                                    t_MetaData.getDatabaseProductName(),
                                    t_MetaData.getDatabaseProductVersion(),
                                    t_strQuote,
                                    t_strBasePackage,
                                    t_strJNDIDataSource);
                        }

                        storeDAOFactoryTemplates(t_aDAOFactoryTemplates, attributes);
                    }
                }
            }
            catch  (SQLException sqlException)
            {
                throw new BuildException(sqlException);
            }
            catch  (QueryJException queryjException)
            {
                throw new BuildException(queryjException);
            }
        }
        
        return result;
    }

    /**
     * Retrieves the database metadata from the attribute map.
     * @param parameters the parameter map.
     * @return the metadata.
     * @throws BuildException if the metadata retrieval process if faulty.
     */
    protected DatabaseMetaData retrieveDatabaseMetaData(
            Map parameters)
        throws  BuildException
    {
        DatabaseMetaData result = null;

        if  (parameters != null)
        {
            result =
                (DatabaseMetaData)
                    parameters.get(
                        DatabaseMetaDataRetrievalHandler.DATABASE_METADATA);
        }
        
        return result;
    }

    /**
     * Retrieves the database metadata manager from the attribute map.
     * @param parameters the parameter map.
     * @return the manager.
     * @throws BuildException if the manager retrieval process if faulty.
     */
    protected DatabaseMetaDataManager retrieveDatabaseMetaDataManager(
            Map parameters)
        throws  BuildException
    {
        DatabaseMetaDataManager result = null;

        if  (parameters != null)
        {
            result =
                (DatabaseMetaDataManager)
                    parameters.get(
                        DatabaseMetaDataRetrievalHandler.DATABASE_METADATA_MANAGER);
        }
        
        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrieveBasePackage(Map parameters)
        throws  BuildException
    {
        String result = null;

        if  (parameters != null)
        {
            result =
                (String) parameters.get(ParameterValidationHandler.PACKAGE);
        }
        
        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param basePackage the base package.
     * @param engineName the engine name.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrievePackage(String basePackage, String engineName)
        throws  BuildException
    {
        String result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (basePackage     != null)
             && (t_PackageUtils != null))
        {
            result =
                t_PackageUtils.retrieveDAOFactoryPackage(
                    basePackage,
                    engineName);
        }
        
        return result;
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     */
    protected File retrieveOutputDir(String engineName, Map parameters)
        throws  BuildException
    {
        File result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (parameters     != null)
             && (t_PackageUtils != null))
        {
            result =
                t_PackageUtils.retrieveDAOFactoryFolder(
                    (File)
                        parameters.get(
                            ParameterValidationHandler.OUTPUT_DIR),
                    retrieveProjectPackage(parameters),
                    engineName);
        }

        return result;
    }

    /**
     * Retrieves the JNDI location of the data source from the attribute map.
     * @param parameters the parameter map.
     * @return the JNDI location.
     * @throws BuildException if the JNDI location retrieval process if faulty.
     */
    protected String retrieveJNDIDataSource(Map parameters)
        throws  BuildException
    {
        String result = null;

        if  (parameters != null)
        {
            result =
                (String)
                    parameters.get(
                        ParameterValidationHandler.JNDI_DATASOURCES);
        }

        return result;
    }

    /**
     * Stores the DAO factory template collection in given attribute map.
     * @param daoFactoryTemplates the DAO factory templates.
     * @param parameters the parameter map.
     * @throws BuildException if the templates cannot be stored for any reason.
     */
    protected void storeDAOFactoryTemplates(
            DAOFactoryTemplate[] daoFactoryTemplates,
            Map                  parameters)
        throws  BuildException
    {
        if  (   (daoFactoryTemplates != null)
             && (parameters          != null))
        {
            parameters.put(
                TemplateMappingManager.DAO_FACTORY_TEMPLATES,
                daoFactoryTemplates);
        }
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @throws BuildException if the templates cannot be stored for any reason.
     */
    protected TableTemplate[] retrieveTableTemplates(
            Map parameters)
        throws  BuildException
    {
        TableTemplate[] result = EMPTY_TABLE_TEMPLATE_ARRAY;

        if  (parameters != null)
        {
            result =
                (TableTemplate[])
                    parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
        }

        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrieveProjectPackage(Map parameters)
        throws  BuildException
    {
        String result = null;

        if  (parameters != null)
        {
            result =
                (String) parameters.get(ParameterValidationHandler.PACKAGE);
        }
        
        return result;
    }
}
