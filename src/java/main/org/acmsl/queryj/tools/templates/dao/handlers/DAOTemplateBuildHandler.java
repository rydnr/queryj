/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
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
 * Description: Builds a DAO template using database metadata.
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
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.handlers.CustomSqlProviderRetrievalHandler;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.DAOTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOTemplateGenerator;
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
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;


/*
 * Importing Jakarta Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Builds a DAO template using database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class DAOTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The output dir key.
     */
    public static final String OUTPUT_DIR = "dao.template.output.dir";

    /**
     * Creates a DAOTemplateBuildHandler.
     */
    public DAOTemplateBuildHandler() {};

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
        boolean result = false;

        try
        {
            Map attributes = command.getAttributeMap();

            DatabaseMetaData t_MetaData =
                retrieveDatabaseMetaData(attributes);

            DatabaseMetaDataManager t_MetaDataManager =
                retrieveDatabaseMetaDataManager(attributes);

            CustomSqlProvider t_CustomSqlProvider =
                retrieveCustomSqlProvider(attributes);

            DAOTemplateGenerator t_DAOTemplateGenerator =
                DAOTemplateGenerator.getInstance();

            if  (   (t_MetaData             != null)
                 && (t_MetaDataManager      != null)
                 && (t_DAOTemplateGenerator != null))
            {
                String t_strBasePackage =
                    retrieveProjectPackage(attributes);

                String t_strPackage =
                    retrievePackage(
                        t_MetaData.getDatabaseProductName(),
                        attributes);

                String t_strRepositoryName =
                    retrieveTableRepositoryName(attributes);

                TableTemplate[] t_aTableTemplates =
                    retrieveTableTemplates(attributes);

                if  (t_aTableTemplates != null)
                {
                    DAOTemplate[] t_aDAOTemplates =
                        new DAOTemplate[t_aTableTemplates.length];

                    for  (int t_iDAOIndex = 0;
                              t_iDAOIndex < t_aDAOTemplates.length;
                              t_iDAOIndex++) 
                    {
                        String t_strQuote =
                            t_MetaData.getIdentifierQuoteString();

                        if  (t_strQuote == null)
                        {
                            t_strQuote = "\"";
                        }

                        if  (t_strQuote.equals("\""))
                        {
                            t_strQuote = "\\\"";
                        }

                        t_aDAOTemplates[t_iDAOIndex] =
                            t_DAOTemplateGenerator.createDAOTemplate(
                                t_aTableTemplates[t_iDAOIndex],
                                t_MetaDataManager,
                                t_CustomSqlProvider,
                                t_strPackage,
                                t_MetaData.getDatabaseProductName(),
                                t_MetaData.getDatabaseProductVersion(),
                                t_strQuote,
                                t_strBasePackage,
                                t_strRepositoryName);
                    }

                    storeDAOTemplates(t_aDAOTemplates, attributes);
                }
            }
        }
        catch  (SQLException sqlException)
        {
            Project t_Project = command.getProject();

            if  (t_Project != null)
            {
                t_Project.log(
                    command.getTask(),
                      "Error building DAO ("
                    + sqlException.getMessage()
                    + ")",
                    Project.MSG_WARN);
            }
            
            throw new BuildException(sqlException);
        }
        catch  (QueryJException queryjException)
        {
            Project t_Project = command.getProject();

            if  (t_Project != null)
            {
                t_Project.log(
                    command.getTask(),
                      "Error building Mock DAO ("
                    + queryjException.getMessage()
                    + ")",
                    Project.MSG_WARN);
            }

            throw new BuildException(queryjException);
        }
        
        return result;
    }

    /**
     * Retrieves the database metadata from the attribute map.
     * @param parameters the parameter map.
     * @return the metadata.
     * @throws BuildException if the metadata retrieval process if faulty.
     * @precondition parameters != null
     */
    protected DatabaseMetaData retrieveDatabaseMetaData(final Map parameters)
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
     * @precondition parameters != null
     */
    protected DatabaseMetaDataManager retrieveDatabaseMetaDataManager(
        final Map parameters)
      throws  BuildException
    {
        return
            (DatabaseMetaDataManager)
                parameters.get(
                    DatabaseMetaDataRetrievalHandler.DATABASE_METADATA_MANAGER);
    }

    /**
     * Retrieves the custom-sql provider from the attribute map.
     * @param parameters the parameter map.
     * @return the provider.
     * @throws BuildException if the manager retrieval process if faulty.
     * @precondition parameters != null
     */
    protected CustomSqlProvider retrieveCustomSqlProvider(
        final Map parameters)
      throws  BuildException
    {
        return
            (CustomSqlProvider)
                parameters.get(
                    CustomSqlProviderRetrievalHandler.CUSTOM_SQL_PROVIDER);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrieveProjectPackage(final Map parameters)
        throws  BuildException
    {
        return
            (String) parameters.get(ParameterValidationHandler.PACKAGE);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrievePackage(
        final String engineName, final Map parameters)
      throws  BuildException
    {
        String result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (t_PackageUtils != null)
        {
            result =
                t_PackageUtils.retrieveDAOPackage(
                    retrieveProjectPackage(parameters),
                    engineName);
        }
        
        return result;
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
     * Stores the DAO template collection in given attribute map.
     * @param daoTemplates the DAO templates.
     * @param parameters the parameter map.
     * @throws BuildException if the templates cannot be stored for any reason.
     * @precondition parameters != null
     */
    protected void storeDAOTemplates(
        final DAOTemplate[] daoTemplates,
        final Map           parameters)
      throws  BuildException
    {
        if  (daoTemplates != null)
        {
            parameters.put(TemplateMappingManager.DAO_TEMPLATES, daoTemplates);
        }
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
}
