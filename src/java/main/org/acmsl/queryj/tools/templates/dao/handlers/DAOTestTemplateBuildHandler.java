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
 * Description: Builds a DAO test template using database metadata.
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
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.DAOTestTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOTestTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.TestTemplate;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

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
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class DAOTestTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty table template array.
     */
    public static final TableTemplate[] EMPTY_TABLE_TEMPLATE_ARRAY =
        new TableTemplate[0];

    /**
     * Creates a DAOTestTemplateBuildHandler.
     */
    public DAOTestTemplateBuildHandler() {};

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

                String t_strPackage =
                    retrieveDAOTestPackage(
                        t_MetaData.getDatabaseProductName(),
                        attributes);

                DAOTestTemplateGenerator t_DAOTestTemplateGenerator =
                    DAOTestTemplateGenerator.getInstance();

                if  (   (t_MetaData                 != null)
                     && (t_MetaDataManager          != null)
                     && (t_DAOTestTemplateGenerator != null))
                {
                    TableTemplate[] t_aTableTemplates =
                        retrieveTableTemplates(attributes);

                    if  (t_aTableTemplates != null)
                    {
                        DAOTestTemplate[] t_aDAOTestTemplates =
                            new DAOTestTemplate[t_aTableTemplates.length];

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

                        for  (int t_iDAOTestIndex = 0;
                                  t_iDAOTestIndex < t_aDAOTestTemplates.length;
                                  t_iDAOTestIndex++) 
                        {
                            t_aDAOTestTemplates[t_iDAOTestIndex] =
                                t_DAOTestTemplateGenerator
                                    .createDAOTestTemplate(
                                        t_aTableTemplates[t_iDAOTestIndex],
                                        t_MetaDataManager,
                                        t_strPackage,
                                        t_MetaData.getDatabaseProductName(),
                                        t_MetaData.getDatabaseProductVersion(),
                                        t_strQuote,
                                        retrieveDAOPackage(
                                            t_MetaData
                                                .getDatabaseProductName(),
                                            attributes),
                                        retrieveValueObjectPackage(attributes),
                                        retrieveJdbcDriver(attributes),
                                        retrieveJdbcUrl(attributes),
                                        retrieveJdbcUsername(attributes),
                                        retrieveJdbcPassword(attributes));

                            storeTestTemplate(
                                t_aDAOTestTemplates[t_iDAOTestIndex],
                                attributes);
                        }

                        storeDAOTestTemplates(t_aDAOTestTemplates, attributes);
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

    /**
     * Retrieves the DAO Test's package name from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrieveDAOTestPackage(String engineName, Map parameters)
        throws  BuildException
    {
        String result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (parameters     != null)
             && (t_PackageUtils != null))
        {
            result =
                t_PackageUtils.retrieveDAOTestPackage(
                    retrieveProjectPackage(parameters),
                    engineName);
        }
        
        return result;
    }

    /**
     * Retrieves the DAO's package name from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrieveDAOPackage(String engineName, Map parameters)
        throws  BuildException
    {
        String result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (parameters     != null)
             && (t_PackageUtils != null))
        {
            result =
                t_PackageUtils.retrieveDAOPackage(
                    retrieveProjectPackage(parameters),
                    engineName);
        }
        
        return result;
    }

    /**
     * Retrieves the value object's package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrieveValueObjectPackage(Map parameters)
        throws  BuildException
    {
        String result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (parameters     != null)
             && (t_PackageUtils != null))
        {
            result =
                t_PackageUtils.retrieveValueObjectPackage(
                    retrieveProjectPackage(parameters));
        }
        
        return result;
    }

    /**
     * Retrieves the JDBC driver from the attribute map.
     * @param parameters the parameter map.
     * @return the driver name.
     * @throws BuildException if the driver retrieval process if faulty.
     */
    protected String retrieveJdbcDriver(Map parameters)
        throws  BuildException
    {
        String result = null;

        if  (parameters != null)
        {
            result =
                (String) parameters.get(ParameterValidationHandler.JDBC_DRIVER);
        }

        return result;
    }

    /**
     * Retrieves the JDBC url from the attribute map.
     * @param parameters the parameter map.
     * @return the url name.
     * @throws BuildException if the url retrieval process if faulty.
     */
    protected String retrieveJdbcUrl(Map parameters)
        throws  BuildException
    {
        String result = null;

        if  (parameters != null)
        {
            result =
                (String) parameters.get(ParameterValidationHandler.JDBC_URL);
        }

        return result;
    }

    /**
     * Retrieves the JDBC username from the attribute map.
     * @param parameters the parameter map.
     * @return the username name.
     * @throws BuildException if the username retrieval process if faulty.
     */
    protected String retrieveJdbcUsername(Map parameters)
        throws  BuildException
    {
        String result = null;

        if  (parameters != null)
        {
            result =
                (String) parameters.get(ParameterValidationHandler.JDBC_USERNAME);
        }

        return result;
    }

    /**
     * Retrieves the JDBC password from the attribute map.
     * @param parameters the parameter map.
     * @return the password name.
     * @throws BuildException if the password retrieval process if faulty.
     */
    protected String retrieveJdbcPassword(Map parameters)
        throws  BuildException
    {
        String result = null;

        if  (parameters != null)
        {
            result =
                (String) parameters.get(ParameterValidationHandler.JDBC_PASSWORD);
        }

        return result;
    }

    /**
     * Retrieves the test template collection.
     * @param parameters the parameter map.
     * @return the test templates.
     * @throws BuildException if the test template retrieval process if faulty.
     */
    protected Collection retrieveTestTemplates(Map parameters)
        throws  BuildException
    {
        Collection result = null;

        if  (parameters != null)
        {
            result =
                (Collection) parameters.get(TemplateMappingManager.TEST_TEMPLATES);
        }
        
        return result;
    }

    /**
     * Stores the DAO template collection in given attribute map.
     * @param daoTestTemplates the DAO templates.
     * @param parameters the parameter map.
     * @throws BuildException if the templates cannot be stored for any reason.
     */
    protected void storeDAOTestTemplates(
            DAOTestTemplate[] daoTestTemplates,
            Map           parameters)
        throws  BuildException
    {
        if  (   (daoTestTemplates != null)
             && (parameters       != null))
        {
            parameters.put(TemplateMappingManager.DAO_TEST_TEMPLATES, daoTestTemplates);
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
     * Stores the test template collection.
     * @param templates the test templates.
     * @param parameters the parameter map.
     * @return the test templates.
     * @throws BuildException if the test template retrieval process if faulty.
     */
    protected void storeTestTemplates(Collection templates, Map parameters)
        throws  BuildException
    {
        if  (   (templates  != null)
             && (parameters != null))
        {
            parameters.put(TemplateMappingManager.TEST_TEMPLATES, templates);
        }
    }

    /**
     * Stores a new test template.
     * @param testTemplate the test template.
     * @param parameters the parameter map.
     * @throws BuildException if the test template retrieval process if faulty.
     */
    protected void storeTestTemplate(TestTemplate template, Map parameters)
        throws  BuildException
    {
        if  (   (template   != null)
             && (parameters != null))
        {
            Collection t_cTestTemplates = retrieveTestTemplates(parameters);

            if  (t_cTestTemplates == null) 
            {
                t_cTestTemplates = new ArrayList();
                storeTestTemplates(t_cTestTemplates, parameters);
            }

            t_cTestTemplates.add(template);
        }
    }
}
