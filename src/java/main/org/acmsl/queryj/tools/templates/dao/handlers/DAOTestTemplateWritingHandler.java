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
 * Description: Writes DAO test templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.DAOTestTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOTestTemplateGenerator;
import org.acmsl.queryj.tools.templates.dao.handlers
    .DAOTestTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateWritingHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * Writes DAO test templates.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class DAOTestTemplateWritingHandler
    extends    AbstractAntCommandHandler
    implements TemplateWritingHandler
{
    /**
     * A cached empty DAO test template array.
     */
    public static final DAOTestTemplate[] EMPTY_DAO_TEST_TEMPLATE_ARRAY =
        new DAOTestTemplate[0];

    /**
     * Creates a DAOTestTemplateWritingHandler.
     */
    public DAOTestTemplateWritingHandler() {};

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

                DAOTestTemplateGenerator t_DAOTestTemplateGenerator =
                    DAOTestTemplateGenerator.getInstance();

                DAOTestTemplate[] t_aDAOTestTemplates =
                    retrieveDAOTestTemplates(attributes);

                DatabaseMetaData t_DatabaseMetaData =
                    retrieveDatabaseMetaData(attributes);

                if  (   (t_DatabaseMetaData         != null) 
                     && (t_aDAOTestTemplates        != null)
                     && (t_DAOTestTemplateGenerator != null))
                {
                    File t_OutputDir =
                        retrieveOutputDir(
                            t_DatabaseMetaData.getDatabaseProductName(),
                            attributes);

                    for  (int t_iDAOTestIndex = 0;
                              t_iDAOTestIndex < t_aDAOTestTemplates.length;
                              t_iDAOTestIndex++)
                    {
                        t_DAOTestTemplateGenerator.write(
                            t_aDAOTestTemplates[t_iDAOTestIndex], t_OutputDir);
                    }
                }
            }
            catch  (SQLException sqlException)
            {
                throw new BuildException(sqlException);
            }
            catch  (IOException ioException)
            {
                throw new BuildException(ioException);
            }
        }
        
        return result;
    }

    /**
     * Retrieves the dao test templates from the attribute map.
     * @param parameters the parameter map.
     * @return the templates.
     * @throws BuildException if the template retrieval process if faulty.
     */
    protected DAOTestTemplate[] retrieveDAOTestTemplates(Map parameters)
        throws  BuildException
    {
        DAOTestTemplate[] result = EMPTY_DAO_TEST_TEMPLATE_ARRAY;

        if  (parameters != null)
        {
            result =
                (DAOTestTemplate[])
                    parameters.get(
                        TemplateMappingManager.DAO_TEST_TEMPLATES);
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
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return the output dir.
     * @throws BuildException if the output dir retrieval process if faulty.
     */
    protected File retrieveProjectFolder(Map parameters)
        throws  BuildException
    {
        File result = null;

        if  (parameters != null)
        {
            result =
                (File) parameters.get(ParameterValidationHandler.OUTPUT_DIR);
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
                t_PackageUtils.retrieveDAOTestFolder(
                    retrieveProjectFolder(parameters),
                    retrieveProjectPackage(parameters),
                    engineName);
        }
        
        return result;
    }
}
