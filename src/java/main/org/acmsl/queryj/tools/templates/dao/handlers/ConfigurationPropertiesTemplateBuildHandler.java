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
 * Description: Builds the configuration properties.
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
import org.acmsl.queryj.tools.templates.dao.ConfigurationPropertiesTemplate;
import org.acmsl.queryj.tools.templates.dao.ConfigurationPropertiesTemplateGenerator;
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
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/*
 * Importing Jakarta Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Builds the configuration properties.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class ConfigurationPropertiesTemplateBuildHandler
    extends    AntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a ConfigurationPropertiesTemplateBuildHandler.
     */
    public ConfigurationPropertiesTemplateBuildHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     */
    public boolean handle(AntCommand command)
        throws  BuildException
    {
        boolean result = false;

        if  (command != null) 
        {
            Map t_mAttributes = command.getAttributeMap();

            storeConfigurationPropertiesTemplate(
                buildConfigurationPropertiesTemplate(t_mAttributes),
                t_mAttributes);
        }
        
        return result;
    }

    /**
     * Builds a ConfigurationProperties template using the information
     * stored in the attribute map.
     * @param parameters the parameter map.
     * @return the TableRepository instance.
     * @throws BuildException if the template cannot be created.
     */
    protected ConfigurationPropertiesTemplate buildConfigurationPropertiesTemplate(Map parameters)
        throws  BuildException
    {
        ConfigurationPropertiesTemplate result = null;

        if  (parameters != null) 
        {
            DatabaseMetaData t_MetaData =
                retrieveDatabaseMetaData(parameters);

            try 
            {
                if  (t_MetaData != null)
                {
                    result =
                        buildConfigurationPropertiesTemplate(
                            retrieveRepository(parameters),
                            t_MetaData.getDatabaseProductName(),
                            t_MetaData.getDatabaseProductVersion(),
                            retrieveProjectPackage(parameters));
                }

                if  (result != null) 
                {
                    String[] t_astrTableNames =
                        (String[])
                            parameters.get(
                                TableTemplateBuildHandler.TABLE_NAMES);

                    if  (t_astrTableNames != null)
                    {
                        for  (int t_iTableIndex = 0;
                                  t_iTableIndex < t_astrTableNames.length;
                                  t_iTableIndex++)
                        {
                            result.addTable(t_astrTableNames[t_iTableIndex]);
                        }
                    }
                }
            }
            catch  (SQLException sqlException)
            {
                throw new BuildException(sqlException);
            }
        }

        return result;
    }

    /**
     * Builds a ConfigurationProperties template using given information.
     * @param repository the repository.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @throws org.apache.tools.ant.BuildException whenever the template
     * information is not valid.
     */
    protected ConfigurationPropertiesTemplate
        buildConfigurationPropertiesTemplate(
            String repository,
            String engineName,
            String engineVersion,
            String basePackageName)
        throws  BuildException
    {
        ConfigurationPropertiesTemplate result = null;

        if  (   (repository      != null)
             && (engineName      != null)
             && (basePackageName != null))
        {
            ConfigurationPropertiesTemplateGenerator
                t_ConfigurationPropertiesTemplateGenerator =
                    ConfigurationPropertiesTemplateGenerator.getInstance();

            if  (t_ConfigurationPropertiesTemplateGenerator != null)
            {
                result =
                    t_ConfigurationPropertiesTemplateGenerator
                        .createConfigurationPropertiesTemplate(
                            repository,
                            engineName,
                            engineVersion,
                            basePackageName);
            }
        }

        return result;
    }

    /**
     * Retrieves the repository from the attribute map.
     * @param parameters the parameter map.
     * @return the repository name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrieveRepository(Map parameters)
        throws  BuildException
    {
        String result = null;

        if  (parameters != null)
        {
            result =
                (String) parameters.get(ParameterValidationHandler.REPOSITORY);
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
     * Stores the ConfigurationProperties template in given attribute map.
     * @param configurationPropertiesTemplate the ConfigurationProperties template.
     * @param parameters the parameter map.
     * @throws BuildException if the template cannot be stored for any reason.
     */
    protected void storeConfigurationPropertiesTemplate(
            ConfigurationPropertiesTemplate configurationPropertiesTemplate,
            Map                             parameters)
        throws  BuildException
    {
        if  (   (configurationPropertiesTemplate != null)
             && (parameters                      != null))
        {
            parameters.put(
                TemplateMappingManager.CONFIGURATION_PROPERTIES_TEMPLATE,
                configurationPropertiesTemplate);
        }
    }
}
