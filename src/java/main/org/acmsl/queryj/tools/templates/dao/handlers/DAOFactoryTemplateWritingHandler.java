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
 * Description: Writes DAO factory templates.
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
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.DAOFactoryTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOFactoryTemplateGenerator;
import org.acmsl.queryj.tools.templates.dao.handlers
    .DAOFactoryTemplateBuildHandler;
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
 * Writes DAO factory templates.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class DAOFactoryTemplateWritingHandler
    extends  AntCommandHandler
    implements TemplateWritingHandler
{
    /**
     * A cached empty DAO template array.
     */
    public static final DAOFactoryTemplate[] EMPTY_DAO_FACTORY_TEMPLATE_ARRAY =
        new DAOFactoryTemplate[0];

    /**
     * Creates a DAOFactoryTemplateWritingHandler.
     */
    public DAOFactoryTemplateWritingHandler() {};

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

                DAOFactoryTemplateGenerator t_DAOFactoryTemplateGenerator =
                    DAOFactoryTemplateGenerator.getInstance();

                DAOFactoryTemplate[] t_aDAOFactoryTemplates =
                    retrieveDAOFactoryTemplates(attributes);

                DatabaseMetaData t_MetaData =
                    retrieveDatabaseMetaData(attributes);

                if  (   (t_MetaData                    != null) 
                     && (t_aDAOFactoryTemplates        != null)
                     && (t_DAOFactoryTemplateGenerator != null))
                {
                    File t_OutputDir =
                        retrieveOutputDir(
                            t_MetaData.getDatabaseProductName().toLowerCase(),
                        attributes);

                    for  (int t_iDAOFactoryIndex = 0;
                              t_iDAOFactoryIndex < t_aDAOFactoryTemplates.length;
                              t_iDAOFactoryIndex++)
                    {
                        t_DAOFactoryTemplateGenerator.write(
                            t_aDAOFactoryTemplates[t_iDAOFactoryIndex], t_OutputDir);
                    }
                }
            }
            catch  (IOException ioException)
            {
                throw new BuildException(ioException);
            }
            catch  (SQLException sqlException)
            {
                throw new BuildException(sqlException);
            }
        }
        
        return result;
    }

    /**
     * Retrieves the DAO factory templates from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     * @throws BuildException if the template retrieval process if faulty.
     */
    protected DAOFactoryTemplate[] retrieveDAOFactoryTemplates(Map parameters)
        throws  BuildException
    {
        DAOFactoryTemplate[] result = EMPTY_DAO_FACTORY_TEMPLATE_ARRAY;

        if  (parameters != null)
        {
            result =
                (DAOFactoryTemplate[])
                    parameters.get(
                        TemplateMappingManager.DAO_FACTORY_TEMPLATES);
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
                        parameters.get(ParameterValidationHandler.OUTPUT_DIR),
                    retrieveProjectPackage(parameters),
                    engineName);
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
