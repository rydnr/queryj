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
 * Description: Builds a procedure repository using database metadata.
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
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.handlers.AntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.ProcedureMetaData;
import org.acmsl.queryj.tools.ProcedureParameterMetaData;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.templates.ProcedureRepositoryTemplate;
import org.acmsl.queryj.tools.templates.ProcedureRepositoryTemplateGenerator;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;
import org.acmsl.commons.version.Version;
import org.acmsl.commons.version.VersionFactory;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/*
 * Importing Jakarta Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Builds a procedure repository using database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class ProcedureRepositoryTemplateBuildHandler
    implements  AntCommandHandler
{
    /**
     * The procedure repository template attribute name.
     */
    public static final String PROCEDURE_REPOSITORY_TEMPLATE =
        "procedure.repository.template";

    /**
     * Creates a ProcedureRepositoryTemplateBuildHandler.
     */
    public ProcedureRepositoryTemplateBuildHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     */
    public boolean handle(Command command)
    {
        boolean result = false;

        if  (command instanceof AntCommand) 
        {
            try 
            {
                result = handle((AntCommand) command);
            }
            catch  (BuildException buildException)
            {
                LogFactory.getLog(getClass()).error(
                    "unhandled.exception",
                    buildException);
            }
        }
        
        return result;
    }

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
            Map attributes = command.getAttributeMap();

            DatabaseMetaDataManager t_MetaDataManager =
                retrieveDatabaseMetaDataManager(attributes);

            String t_strPackage = retrievePackage(attributes);

            ProcedureRepositoryTemplateGenerator t_ProcedureRepositoryTemplateGenerator =
                ProcedureRepositoryTemplateGenerator.getInstance();

            MetaDataUtils t_MetaDataUtils = MetaDataUtils.getInstance();

            if  (   (t_MetaDataManager                      != null)
                 && (t_ProcedureRepositoryTemplateGenerator != null)
                 && (t_MetaDataUtils                        != null))
            {
                ProcedureMetaData[] t_aProceduresMetaData =
                    t_MetaDataManager.getProceduresMetaData();

                LogFactory.getLog(getClass()).info(
                    "Handling stored procedures..." + t_aProceduresMetaData);

                ProcedureRepositoryTemplate t_ProcedureRepositoryTemplate =
                    buildProcedureRepositoryTemplate(attributes);

                if  (  (t_ProcedureRepositoryTemplate != null)
                    && (t_aProceduresMetaData         != null))
                {
                    LogFactory.getLog(getClass()).info(
                        "Building " + t_aProceduresMetaData.length + " procedures.");

                    for  (int t_iIndex = 0;
                              t_iIndex < t_aProceduresMetaData.length;
                              t_iIndex++) 
                    {
                        ProcedureParameterMetaData[] t_aProcedureParametersMetaData =
                            t_MetaDataManager.getProcedureParametersMetaData(
                                t_aProceduresMetaData[t_iIndex]);

                        t_ProcedureRepositoryTemplate
                            .addProcedureMetaData(
                                t_aProceduresMetaData[t_iIndex]);

                        t_ProcedureRepositoryTemplate
                            .addProcedureParametersMetaData(
                                t_aProceduresMetaData[t_iIndex],
                                t_aProcedureParametersMetaData);
                    }
                }

                storeProcedureRepositoryTemplate(t_ProcedureRepositoryTemplate, attributes);
            }
        }
        
        return result;
    }

    /**
     * Retrieves the database metadata manager from the attribute map.
     * @param parameters the parameter map.
     * @return the manager instance.
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
    protected String retrievePackage(Map parameters)
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
     * Builds a procedure repository template using the information stored
     * in the attribute map.
     * @param parameters the parameter map.
     * @return the ProcedureRepositoryTemplate instance.
     * @throws BuildException if the repository cannot be created.
     */
    protected ProcedureRepositoryTemplate buildProcedureRepositoryTemplate(Map parameters)
        throws  BuildException
    {
        ProcedureRepositoryTemplate result = null;

        if  (parameters != null) 
        {
            result =
                buildProcedureRepositoryTemplate(
                    (String)
                        parameters.get(
                            ParameterValidationHandler.PACKAGE),
                    (String)
                        parameters.get(
                            ParameterValidationHandler.REPOSITORY));

            if  (result != null) 
            {
                /*
                String[] t_astrProcedureNames =
                    (String[])
                        parameters.get(
                            PROCE.TABLE_NAMES);

                if  (t_astrTableNames != null)
                {
                    for  (int t_iTableIndex = 0;
                              t_iTableIndex < t_astrTableNames.length;
                              t_iTableIndex++)
                    {
                        result.addTable(t_astrTableNames[t_iTableIndex]);
                    }                    
                }
                */
            }
            
        }

        return result;
    }

    /**
     * Builds a procedure repository template using given information.
     * @param packageName the package name.
     * @param repository the repository.
     * @return such template.
     * @throws org.apache.tools.ant.BuildException whenever the repository
     * information is not valid.
     */
    protected ProcedureRepositoryTemplate buildProcedureRepositoryTemplate(
            String packageName,
            String repository)
        throws  BuildException
    {
        ProcedureRepositoryTemplate result = null;

        if  (   (packageName != null)
             && (repository  != null))
        {
            ProcedureRepositoryTemplateGenerator t_ProcedureRepositoryTemplateGenerator =
                ProcedureRepositoryTemplateGenerator.getInstance();

            if  (t_ProcedureRepositoryTemplateGenerator != null)
            {
                result =
                    t_ProcedureRepositoryTemplateGenerator.createProcedureRepositoryTemplate(
                        packageName, repository);
            }
        }

        return result;
    }

    /**
     * Stores the procedure repository template in given attribute map.
     * @param procedureRepositoryTemplate the table repository template.
     * @param parameters the parameter map.
     * @throws BuildException if the repository cannot be stored for any reason.
     */
    protected void storeProcedureRepositoryTemplate(
            ProcedureRepositoryTemplate procedureRepositoryTemplate,
            Map                         parameters)
        throws  BuildException
    {
        if  (   (procedureRepositoryTemplate != null)
             && (parameters                  != null))
        {
            parameters.put(PROCEDURE_REPOSITORY_TEMPLATE, procedureRepositoryTemplate);
        }
    }

    /**
     * Concrete version object updated everytime it's checked-in in a
     * CVS repository.
     */
    public static final Version VERSION =
        VersionFactory.createVersion("$Revision$");

    /**
     * Retrieves the current version of this object.
     * @return the version object with such information.
     */
    public Version getVersion()
    {
        return VERSION;
    }

    /**
     * Retrieves the current version of this class. It's defined because
     * this is a utility class that cannot be instantiated.
     * @return the object with class version information.
     */
    public static Version getClassVersion()
    {
        return VERSION;
    }
}
