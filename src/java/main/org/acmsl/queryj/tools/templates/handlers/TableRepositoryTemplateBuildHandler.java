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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a table repository using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.oracle.OracleTableRepositoryBuildHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TableRepositoryTemplate;
import org.acmsl.queryj.tools.templates.TableRepositoryTemplateFactory;
import org.acmsl.queryj.tools.templates.TableRepositoryTemplateGenerator;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.PackageUtils;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Builds a table repository using database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class TableRepositoryTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The mappings.
     */
    private static Map m__mMappings;

    /**
     * Creates a TableRepositoryTemplateBuildHandler.
     */
    public TableRepositoryTemplateBuildHandler() {};

    /**
     * Specifies the mappings.
     * @param map the new mappings.
     */
    protected static void setMappings(final Map map)
    {
        m__mMappings = map;
    }

    /**
     * Retrieves the mappings.
     * @return such collection.
     */
    protected static Map getMappings()
    {
        return m__mMappings;
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

        Map t_mAttributes = command.getAttributeMap();

        String t_strEngine = retrieveEngine(t_mAttributes);

        String t_strEngineVersion = retrieveEngineVersion(t_mAttributes);

        TableRepositoryTemplateBuildHandler t_ActualHandler =
            retrieveActualHandler(
                t_strEngine,
                t_strEngineVersion);

        if  (t_ActualHandler != null)
        {
            if  (t_ActualHandler.getClass().equals(getClass()))
            {
                result = handleByDefault(command);
            }
            else 
            {
                result = t_ActualHandler.handle(command);
            }
        }
        else 
        {
            Log t_Log = UniqueLogFactory.getLog(getClass());

            if  (t_Log != null)
            {
                t_Log.error(
                      "Cannot retrieve handler for generating "
                    + "the table repository for "
                    + t_strEngine
                    + ", "
                    + t_strEngineVersion);
            }

            result = true;
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
    protected boolean handleByDefault(final AntCommand command)
        throws  BuildException
    {
        storeTableRepositoryTemplate(
            buildTableRepositoryTemplate(
                command.getAttributeMap()),
            command.getAttributeMap());

        return false;
    }

    /**
     * Retrieves the engine from given attributes.
     * @param attributes the command attributes.
     * @return the engine information.
     * @throws BuildException if the engine cannot be retrieved.
     * @precondition attributes != null
     */
    protected String retrieveEngine(final Map attributes)
      throws  BuildException
    {
        String result = "";

        DatabaseMetaData t_DatabaseMetaData =
            (DatabaseMetaData)
                attributes.get(
                    DatabaseMetaDataRetrievalHandler.DATABASE_METADATA);

        if  (t_DatabaseMetaData != null)
        {
            try 
            {
                result = t_DatabaseMetaData.getDatabaseProductName();
            }
            catch  (final SQLException sqlException)
            {
                Log t_Log = UniqueLogFactory.getLog(getClass());
                
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot retrieve engine's name.",
                        sqlException);
                }

                throw
                    new BuildException(
                        "cannot.retrieve.engine.name",
                        sqlException);
            }
        }
        
        return result;
    }

    /**
     * Retrieves the engine version from given attributes.
     * @param attributes the command attributes.
     * @return the engine version information.
     * @throws BuildException if the engine cannot be retrieved.
     * @precondition attributes != null
     */
    protected String retrieveEngineVersion(final Map attributes)
        throws  BuildException
    {
        String result = "";

        DatabaseMetaData t_DatabaseMetaData =
            (DatabaseMetaData)
                attributes.get(
                    DatabaseMetaDataRetrievalHandler.DATABASE_METADATA);

        if  (t_DatabaseMetaData != null)
        {
            try 
            {
                result = t_DatabaseMetaData.getDatabaseProductVersion();
            }
            catch  (final SQLException sqlException)
            {
                Log t_Log = UniqueLogFactory.getLog(getClass());
                
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot retrieve engine's version.",
                        sqlException);
                }

                throw
                    new BuildException(
                        "cannot.retrieve.engine.version",
                        sqlException);
            }
        }
        
        return result;
    }

    /**
     * Retrieves the actual handler.
     * @param engine the engine.
     * @param engineVersion the engine version.
     * @return the concrete handler.
     * @precondition engine != null
     */
    protected TableRepositoryTemplateBuildHandler retrieveActualHandler(
        final String engine, final String engineVersion)
    {
        TableRepositoryTemplateBuildHandler result = null;

        Map t_mMappings = getMappings();

        if  (t_mMappings == null)
        {
            t_mMappings = fillUpMappings(new HashMap());
            setMappings(t_mMappings);
        }

        if  (t_mMappings != null)
        {
            Log t_Log = UniqueLogFactory.getLog(getClass());
                        
            Object t_Key = buildKey(engine, engineVersion);
            Object t_Value = t_mMappings.get(t_Key);

            if  (t_Value == null)
            {
                t_Key = buildKey(engine);
                t_Value = t_mMappings.get(t_Key);
            }

            if  (t_Value == null)
            {
                t_Key = buildKey();
                t_Value = t_mMappings.get(t_Key);
            }

            if  (t_Value != null)
            {
                if  (t_Value instanceof TableRepositoryTemplateBuildHandler)
                {
                    result = (TableRepositoryTemplateBuildHandler) t_Value;
                }
                else
                {
                    try 
                    {
                        Class t_HandlerClass =
                            Class.forName(t_Value.toString());

                        Object t_HandlerObject = t_HandlerClass.newInstance();

                        if  (           t_HandlerObject
                             instanceof TableRepositoryTemplateBuildHandler)
                        {
                            result =
                                (TableRepositoryTemplateBuildHandler)
                                    t_HandlerObject;

                            t_mMappings.put(t_Key, result);
                        }
                    }
                    catch  (final ClassNotFoundException classNotFoundException)
                    {
                        if  (t_Log != null)
                        {
                            t_Log.error(
                                "Invalid table repository handler.",
                                classNotFoundException);
                        }

                        throw
                            new BuildException(
                                  "unknown.table.repository.build."
                                + "handler.class",
                                classNotFoundException);
                    }
                    catch  (final InstantiationException instantiationException)
                    {
                        if  (t_Log != null)
                        {
                            t_Log.error(
                                "Invalid table repository handler.",
                                instantiationException);
                        }

                        throw
                            new BuildException(
                                "invalid.table.repository.build.handler.class",
                                instantiationException);
                    }
                    catch  (final IllegalAccessException illegalAccessException)
                    {
                        if  (t_Log != null)
                        {
                            t_Log.error(
                                  "Forbidden access to table repository "
                                + "build handler.",
                                illegalAccessException);
                        }

                        throw
                            new BuildException(
                                  "forbidden.access.to.table.repository."
                                + "build.handler",
                                illegalAccessException);
                    }
                }
            }
        }
        
        return result;
    }

    /**
     * Builds a key using the engine and version.
     * @param engine the engine.
     * @param engineVersion the engine version.
     * @return such key.
     */
    protected Object buildKey(final String engine, final String engineVersion)
    {
        String result = "";

        if  (engine != null)
        {
            result = "\\\\" + engine;

            if  (engineVersion != null) 
            {
                result += "//" + engineVersion;
            }
        }

        return result;
    }

    /**
     * Builds a key using the engine and version.
     * @param engine the engine.
     * @return such key.
     */
    protected Object buildKey(final String engine)
    {
        return buildKey(engine, null);
    }

    /**
     * Builds the default key.
     * @return such key.
     */
    protected Object buildKey()
    {
        return buildKey(null, null);
    }

    /**
     * Fills up the mappings.
     * @param mappings the mappings to fill.
     * @return the filled mappings.
     * @precondition mappings != null
     */
    protected Map fillUpMappings(final Map mappings)
    {
        Map result = mappings;

        result.put(
            buildKey(
                "oracle",
                  "Oracle8i Enterprise Edition Release 8.1.7.0.1 - "
                + "Production\n JServer Release 8.1.7.0.1 - Production"),
            new OracleTableRepositoryBuildHandler());

        result.put(buildKey(), this);
        
        return result;
    }

    /**
     * Builds a table repository template using the information stored in the
     * attribute map.
     * @param parameters the parameter map.
     * @return the TableRepositoryTemplate instance.
     * @throws BuildException if the repository cannot be created.
     * @precondition parameters != null
     * @precondition PackageUtils.getInstance() != null
     */
    protected TableRepositoryTemplate buildTableRepositoryTemplate(
        final Map parameters)
      throws  BuildException
    {
        TableRepositoryTemplate result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        result =
            buildTableRepositoryTemplate(
                t_PackageUtils.retrieveTableRepositoryPackage(
                    (String)
                        parameters.get(
                            ParameterValidationHandler.PACKAGE)),
                (String)
                    parameters.get(
                        ParameterValidationHandler.REPOSITORY));

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

        return result;
    }

    /**
     * Builds a table repository template using given information.
     * @param packageName the package name.
     * @param repository the repository.
     * @return such template.
     * @throws org.apache.tools.ant.BuildException whenever the repository
     * information is not valid.
     * @precondition packageName != null
     * @precondition repository != null
     * @precondition TableRepositoryTemplateGenerator.getInstance() != null
     */
    protected TableRepositoryTemplate buildTableRepositoryTemplate(
        final String packageName, final String repository)
      throws  BuildException
    {
        return
            buildTableRepositoryTemplate(
                packageName,
                repository,
                TableRepositoryTemplateGenerator.getInstance());
    }
    
    /**
     * Builds a table repository template using given information.
     * @param packageName the package name.
     * @param repository the repository.
     * @param factory the template factory.
     * @return such template.
     * @throws org.apache.tools.ant.BuildException whenever the repository
     * information is not valid.
     * @precondition packageName != null
     * @precondition repository != null
     * @precondition factory != null
     */
    protected TableRepositoryTemplate buildTableRepositoryTemplate(
        final String packageName,
        final String repository,
        final TableRepositoryTemplateFactory factory)
      throws  BuildException
    {
        return
            factory.createTableRepositoryTemplate(
                packageName, repository);
    }

    /**
     * Stores the table repository template in given attribute map.
     * @param tableRepositoryTemplate the table repository template.
     * @param parameters the parameter map.
     * @throws BuildException if the repository cannot be stored for any reason.
     * @precondition tableRepositoryTemplate != null
     * @precondition parameters != null
     */
    protected void storeTableRepositoryTemplate(
        final TableRepositoryTemplate tableRepositoryTemplate,
        final Map parameters)
      throws  BuildException
    {
        parameters.put(
            TemplateMappingManager.TABLE_REPOSITORY_TEMPLATE,
            tableRepositoryTemplate);
    }
}
