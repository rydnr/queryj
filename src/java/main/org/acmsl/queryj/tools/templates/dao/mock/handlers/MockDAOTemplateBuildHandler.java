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
package org.acmsl.queryj.tools.templates.dao.mock.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.handlers.AntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOTemplateGenerator;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

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
import org.apache.tools.ant.Project;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;


/*
 * Importing Jakarta Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Builds a mock DAO template using database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class MockDAOTemplateBuildHandler
    implements  AntCommandHandler
{
    /**
     * Creates a MockDAOTemplateBuildHandler.
     */
    public MockDAOTemplateBuildHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @precondition command != null
     * @precondition command instanceof AntCommand
     */
    public boolean handle(final Command command)
    {
        boolean result = false;

        AntCommand t_AntCommand = (AntCommand) command;

        try 
        {
            result = handle(t_AntCommand);
        }
        catch  (BuildException buildException)
        {
            Project t_Project = t_AntCommand.getProject();

            if  (t_Project != null)
            {
                t_Project.log(
                    t_AntCommand.getTask(),
                      "Cannot handle the building of "
                    + "the Mock DAO template ("
                    + buildException.getMessage()
                    + ")",
                    Project.MSG_WARN);
            }
            else 
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
     * @precondition command != null
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        boolean result = false;

        try
        {
            Map attributes = command.getAttributeMap();

            DatabaseMetaDataManager t_MetaDataManager =
                retrieveDatabaseMetaDataManager(attributes);

            MockDAOTemplateGenerator t_MockDAOTemplateGenerator =
                MockDAOTemplateGenerator.getInstance();

            if  (t_MockDAOTemplateGenerator != null)
            {
                String t_strBasePackage =
                    retrieveProjectPackage(attributes);

                String t_strPackage =
                    retrievePackage(attributes);

                String t_strRepositoryName =
                    retrieveTableRepositoryName(attributes);

                TableTemplate[] t_aTableTemplates =
                    retrieveTableTemplates(attributes);

                if  (t_aTableTemplates != null)
                {
                    MockDAOTemplate[] t_aMockDAOTemplates =
                        new MockDAOTemplate[t_aTableTemplates.length];

                    for  (int t_iMockDAOIndex = 0;
                              t_iMockDAOIndex < t_aMockDAOTemplates.length;
                              t_iMockDAOIndex++) 
                    {
                        command.getProject().log(
                            command.getTask(),
                            "Building mock dao template ("
                            + t_aTableTemplates[t_iMockDAOIndex].getTableName()
                            + ")",
                            Project.MSG_VERBOSE);

                        t_aMockDAOTemplates[t_iMockDAOIndex] =
                            t_MockDAOTemplateGenerator.createMockDAOTemplate(
                                t_aTableTemplates[t_iMockDAOIndex],
                                t_MetaDataManager,
                                t_strPackage,
                                t_strBasePackage,
                                t_strRepositoryName);
                    }

                    storeMockDAOTemplates(t_aMockDAOTemplates, attributes);
                }
            }
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
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrieveProjectPackage(final Map parameters)
        throws  BuildException
    {
        return (String) parameters.get(ParameterValidationHandler.PACKAGE);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrievePackage(final Map parameters)
      throws  BuildException
    {
        String result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (t_PackageUtils != null)
        {
            result =
                t_PackageUtils.retrieveMockDAOPackage(
                    retrieveProjectPackage(parameters));
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
     * Stores the Mock DAO template collection in given attribute map.
     * @param mockDAOTemplates the Mock DAO templates.
     * @param parameters the parameter map.
     * @throws BuildException if the templates cannot be stored for any reason.
     * @precondition parameters != null
     */
    protected void storeMockDAOTemplates(
        final MockDAOTemplate[] mockDAOTemplates,
        final Map               parameters)
      throws  BuildException
    {
        parameters.put(
            TemplateMappingManager.MOCK_DAO_TEMPLATES, mockDAOTemplates);
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
