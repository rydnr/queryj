/*
                        QueryJ

    Copyright (C) 2004  Jose San Leandro Armendáriz
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
 * Description: Retrieves the database metadata instance.
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
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.AntFieldElement;
import org.acmsl.queryj.tools.AntFieldFkElement;
import org.acmsl.queryj.tools.AntTableElement;
import org.acmsl.queryj.tools.AntTablesElement;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.handlers.AntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.MetaDataUtils;

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
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

/*
 * Importing Jakarta Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Retrieves the database metadata instance.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class DatabaseMetaDataRetrievalHandler
    implements  AntCommandHandler
{
    /**
     * The Database Metadata attribute name.
     */
    public static final String DATABASE_METADATA = "jdbc.database.metadata";

    /**
     * The Database Metadata manager attribute name.
     */
    public static final String DATABASE_METADATA_MANAGER = "jdbc.database.metadata.manager";

    /**
     * The table names attribute name.
     */
    public static final String TABLE_NAMES = "table.names";

    /**
     * The procedure names attribute name.
     */
    public static final String PROCEDURE_NAMES = "procedure.names";

    /**
     * The table fields attribute name.
     */
    public static final String TABLE_FIELDS = "table.fields";

    /**
     * Creates a DatabaseMetaDataRetrievalHandler.
     */
    public DatabaseMetaDataRetrievalHandler() {};

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
                        buildException.getMessage(),
                        Project.MSG_ERR);
                }
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
            Map t_mAttributes = command.getAttributeMap();

            storeMetaData(retrieveMetaData(t_mAttributes), t_mAttributes);

            DatabaseMetaDataManager t_MetaDataManager = null;

            boolean t_bDisableTableExtraction =
                !retrieveExtractTables(t_mAttributes);

            boolean t_bDisableProcedureExtraction =
                !retrieveExtractProcedures(t_mAttributes);

            Collection t_cTableElements = null;

            Iterator t_itTableElements = null;

            String[] t_astrTableNames = null;

            Collection t_cFieldElements = null;

            int t_iTableIndex = 0;

            Map t_mKeys = new HashMap();

            Collection t_cTables = null;

            if  (!t_bDisableTableExtraction)
            {
                AntTablesElement t_TablesElement =
                    retrieveTablesElement(t_mAttributes);

                if  (t_TablesElement != null)
                {
                    t_cTableElements = t_TablesElement.getTables();

                    if  (   (t_cTableElements != null)
                         && (t_cTableElements.size() > 0))
                    {
                        t_astrTableNames = new String[t_cTableElements.size()];

                        t_itTableElements = t_cTableElements.iterator();

                        while  (   (t_itTableElements != null)
                                && (t_itTableElements.hasNext()))
                        {
                            AntTableElement t_Table =
                                (AntTableElement) t_itTableElements.next();

                            if  (t_Table != null)
                            {
                                t_astrTableNames[t_iTableIndex] = t_Table.getName();
                            }

                            t_iTableIndex++;
                        }

                        storeTableNames(t_astrTableNames, t_mAttributes);

                        t_itTableElements = t_cTableElements.iterator();

                        t_iTableIndex = 0;

                        while  (   (t_itTableElements != null)
                                && (t_itTableElements.hasNext()))
                        {
                            AntTableElement t_Table =
                                (AntTableElement) t_itTableElements.next();

                            if  (t_Table != null)
                            {
                                t_cFieldElements = t_Table.getFields();

                                if  (   (t_cFieldElements != null)
                                     && (t_cFieldElements.size() > 0))
                                {
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            t_MetaDataManager =
                buildMetaDataManager(
                    t_bDisableTableExtraction,
                    (   (t_cFieldElements != null)
                     && (t_cFieldElements.size() > 0)),
                    t_bDisableProcedureExtraction,
                    false,
                    t_mAttributes,
                    command.getProject(),
                    command.getTask());

            storeMetaDataManager(t_MetaDataManager, t_mAttributes);

            MetaDataUtils t_MetaDataUtils = MetaDataUtils.getInstance();

            if  (t_cTableElements != null)
            {
                t_itTableElements = t_cTableElements.iterator();
            }

            if  (   (t_itTableElements != null)
                 && (t_MetaDataUtils   != null)
                 && (t_MetaDataManager != null))
            {
                while  (t_itTableElements.hasNext())
                {
                    AntTableElement t_Table =
                        (AntTableElement) t_itTableElements.next();

                    if  (t_Table != null)
                    {
                        t_cTables = (Collection) t_mKeys.get(buildTableKey());

                        if  (t_cTables == null)
                        {
                            t_cTables = new ArrayList();
                            t_mKeys.put(buildTableKey(), t_cTables);
                        }

                        t_cTables.add(t_Table.getName());

                        t_cFieldElements = t_Table.getFields();

                        if  (   (t_cFieldElements  != null)
                             && (t_cFieldElements.size() > 0))
                        {
                            String[] t_astrTableFieldNames =
                                new String[t_cFieldElements.size()];

                            Iterator t_itFieldElements = t_cFieldElements.iterator();

                            int t_iFieldIndex = 0;

                            while  (   (t_itFieldElements != null)
                                    && (t_itFieldElements.hasNext()))
                            {
                                AntFieldElement t_Field =
                                    (AntFieldElement) t_itFieldElements.next();

                                if  (t_Field != null)
                                {
                                    t_astrTableFieldNames[t_iFieldIndex] =
                                        t_Field.getName();

                                    t_MetaDataManager.addColumnType(
                                        t_Table.getName(),
                                        t_Field.getName(),
                                        t_MetaDataUtils.getJavaType(t_Field.getType()));

                                    Collection t_cFields =
                                        (Collection)
                                            t_mKeys.get(buildTableFieldsKey(t_Table.getName()));

                                    if  (t_cFields == null)
                                    {
                                        t_cFields = new ArrayList();
                                        t_mKeys.put(buildTableFieldsKey(t_Table.getName()), t_cFields);
                                    }

                                    t_cFields.add(t_Field.getName());

                                    if  (t_Field.isPk())
                                    {
                                        t_MetaDataManager.addPrimaryKey(
                                            t_Table.getName(),
                                            t_Field.getName());

                                        Collection t_cPks =
                                            (Collection) t_mKeys.get(buildPkKey(t_Table.getName()));

                                        if  (t_cPks == null)
                                        {
                                            t_cPks = new ArrayList();
                                            t_mKeys.put(buildPkKey(t_Table.getName()), t_cPks);
                                        }

                                        t_cPks.add(t_Field.getName());

                                        t_mKeys.put(
                                            buildPkKey(
                                                t_Table.getName(),
                                                t_Field.getName()),
                                            t_Field.getName());
                                    }

                                    Collection t_cFieldFks =
                                        t_Field.getFieldFks();

                                    if  (t_cFieldFks != null)
                                    {
                                        t_mKeys.put(
                                            buildFkKey(
                                                t_Table.getName(),
                                                t_Field.getName()),
                                            t_cFieldFks);
                                    }
                                }

                                t_iFieldIndex++;
                            }

                            t_MetaDataManager.addColumnNames(
                                t_Table.getName(),
                                t_astrTableFieldNames);
                        }
                    }
                }

                storeMetaDataManager(t_MetaDataManager, t_mAttributes);
            }

            t_cTables = (Collection) t_mKeys.get(buildTableKey());

            if  (t_cTables != null)
            {
                Iterator t_itTables = t_cTables.iterator();

                while  (   (t_itTables != null)
                        && (t_itTables.hasNext()))
                {
                    String t_strTableName = (String) t_itTables.next();

                    if  (t_strTableName != null)
                    {
                        Collection t_cFields =
                            (Collection)
                                t_mKeys.get(
                                    buildTableFieldsKey(
                                        t_strTableName));

                        if  (t_cFields != null)
                        {
                            Iterator t_itFields = t_cFields.iterator();

                            while  (   (t_itFields != null)
                                    && (t_itFields.hasNext()))
                            {
                                String t_strFieldName =
                                    (String) t_itFields.next();

                                Collection t_cFieldFks =
                                    (Collection)
                                        t_mKeys.get(
                                            buildFkKey(
                                                t_strTableName,
                                                t_strFieldName));

                                if  (t_cFieldFks != null)
                                {
                                    Iterator t_itFieldFks = t_cFieldFks.iterator();

                                    while  (   (t_itFieldFks != null)
                                            && (t_itFieldFks.hasNext()))
                                    {
                                        AntFieldFkElement t_FieldFk =
                                            (AntFieldFkElement)
                                                t_itFieldFks.next();

                                        if  (t_FieldFk != null)
                                        {
                                            t_MetaDataManager.addForeignKey(
                                                t_strTableName,
                                                t_strFieldName,
                                                t_FieldFk.getTable(),
                                                t_FieldFk.getField());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return result;
    }

    /**
     * Retrieves the database metadata using the JDBC connection stored in the
     * attribute map.
     * @param parameters the parameter map.
     * @return the metadata instance.
     * @throws BuildException if the retrieval process cannot be performed.
     */
    protected DatabaseMetaData retrieveMetaData(Map parameters)
        throws  BuildException
    {
        DatabaseMetaData result = null;

        if  (parameters != null) 
        {
            result =
                retrieveMetaData(
                    (Connection)
                        parameters.get(
                            JdbcConnectionOpeningHandler.JDBC_CONNECTION));
        }

        return result;
    }

    /**
     * Retrieves the database metadata.
     * @param connection the JDBC connection.
     * @return the metadata instance.
     * @throws org.apache.tools.ant.BuildException whenever the required
     * parameters are not present or valid.
     */
    protected DatabaseMetaData retrieveMetaData(Connection connection)
        throws  BuildException
    {
        DatabaseMetaData result = null;

        if  (connection != null)
        {
            try 
            {
                result = connection.getMetaData();
            }
            catch  (Exception exception)
            {
                throw new BuildException(exception);
            }
        }

        return result;
    }

    /**
     * Retrieves the tables XML element stored in the
     * attribute map.
     * @param parameters the parameter map.
     * @return the table information.
     * @throws BuildException if the retrieval process cannot be performed.
     */
    protected AntTablesElement retrieveTablesElement(Map parameters)
        throws  BuildException
    {
        AntTablesElement result = null;

        if  (parameters != null) 
        {
            result =
                (AntTablesElement)
                    parameters.get(
                        ParameterValidationHandler.TABLES);
        }

        return result;
    }

    /**
     * Retrieves whether the table extraction is disabled or not.
     * @param parameters the parameter map.
     * @return such setting.
     * @throws BuildException if the retrieval process cannot be performed.
     */
    protected boolean retrieveExtractTables(Map parameters)
        throws  BuildException
    {
        return
            retrieveBoolean(
                ParameterValidationHandler.EXTRACT_TABLES, parameters);
    }

    /**
     * Retrieves whether the procedure extraction is disabled or not.
     * @param parameters the parameter map.
     * @return such setting.
     * @throws BuildException if the retrieval process cannot be performed.
     */
    protected boolean retrieveExtractProcedures(Map parameters)
        throws  BuildException
    {
        return
            retrieveBoolean(
                ParameterValidationHandler.EXTRACT_PROCEDURES, parameters);
    }

    /**
     * Retrieves a boolean value stored in the attribute map.
     * @param name the key name.
     * @param parameters the parameter map.
     * @return the boolean value.
     * @throws BuildException if the retrieval process cannot be performed.
     */
    protected boolean retrieveBoolean(String name, Map parameters)
        throws  BuildException
    {
        Boolean t_Result = null;

        if  (parameters != null) 
        {
            t_Result = (Boolean) parameters.get(name);
        }

        return (   (t_Result != null)
                && (t_Result.booleanValue()));
    }

    /**
     * Retrieves the database metadata manager using the database metadata and
     * parameters stored in the attribute map.
     * @param disableTableExtraction if the table metadata should not be
     * extracted.
     * @param lazyTableExtraction if the table metadata should not be
     * extracted inmediately.
     * @param disableProcedureExtraction if the procedure metadata should not be
     * extracted.
     * @param lazyProcedureExtraction if the procedure metadata should not be
     * extracted inmediately.
     * @param parameters the parameter map.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the metadata manager instance.
     * @throws BuildException if the retrieval process cannot be performed.
     */
    protected DatabaseMetaDataManager buildMetaDataManager(
            boolean disableTableExtraction,
            boolean lazyTableExtraction,
            boolean disableProcedureExtraction,
            boolean lazyProcedureExtraction,
            Map     parameters,
            Project project,
            Task    task)
        throws  BuildException
    {
        DatabaseMetaDataManager result = null;

        if  (parameters != null) 
        {
            result =
                (DatabaseMetaDataManager)
                    parameters.get(DATABASE_METADATA_MANAGER);

            if  (result == null) 
            {
                result =
                    buildMetaDataManager(
                        (String[]) parameters.get(TABLE_NAMES),
                        (String[]) parameters.get(PROCEDURE_NAMES),
                        disableTableExtraction,
                        lazyTableExtraction,
                        disableProcedureExtraction,
                        lazyProcedureExtraction,
                        (DatabaseMetaData) parameters.get(DATABASE_METADATA),
                        (String) parameters.get(ParameterValidationHandler.JDBC_CATALOG),
                        (String) parameters.get(ParameterValidationHandler.JDBC_SCHEMA),
                        project,
                        task);
            }
        }

        return result;
    }

    /**
     * Builds a database metadata manager.
     * @param tableNames the table names.
     * @param procedureNames the procedure names.
     * @param disableTableExtraction if the table metadata should not
     * be extracted.
     * @param lazyTableExtraction if the table metadata should not
     * be extracted inmediately.
     * @param disableProcedureExtraction if the procedure metadata should not
     * be extracted.
     * @param lazyProcedureExtraction if the procedure metadata should not
     * be extracted inmediately.
     * @param metaData the database metadata.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the metadata manager instance.
     * @throws org.apache.tools.ant.BuildException whenever the required
     * parameters are not present or valid.
     */
    protected DatabaseMetaDataManager buildMetaDataManager(
            String[]         tableNames,
            String[]         procedureNames,
            boolean          disableTableExtraction,
            boolean          lazyTableExtraction,
            boolean          disableProcedureExtraction,
            boolean          lazyProcedureExtraction,
            DatabaseMetaData metaData,
            String           catalog,
            String           schema,
            Project          project,
            Task             task)
        throws  BuildException
    {
        DatabaseMetaDataManager result = null;

        if  (metaData != null)
        {
            try 
            {
                result =
                    new DatabaseMetaDataManager(
                        tableNames,
                        procedureNames,
                        disableTableExtraction,
                        lazyTableExtraction,
                        disableProcedureExtraction,
                        lazyProcedureExtraction,
                        metaData,
                        catalog,
                        schema,
                        project,
                        task);
            }
            catch  (Exception exception)
            {
                if  (project != null)
                {
                    project.log(
                        task,
                        "Cannot build a database metadata manager ("
                        + exception
                        + ")",
                        Project.MSG_ERR);
                }

                throw new BuildException(exception);
            }
        }

        return result;
    }

    /**
     * Stores the database metadata in the attribute map.
     * @param metaData the database metadata.
     * @param parameters the parameter map.
     * @throws BuildException if the metadata cannot be stored for any reason.
     */
    protected void storeMetaData(
            DatabaseMetaData metaData,
            Map              parameters)
        throws  BuildException
    {
        if  (   (metaData   != null)
             && (parameters != null))
        {
            parameters.put(DATABASE_METADATA, metaData);
        }
    }


    /**
     * Stores the table names in the attribute map.
     * @param tableNames the table names.
     * @param parameters the parameter map.
     * @throws BuildException if the table names cannot be stored for any reason.
     */
    protected void storeTableNames(
            String[] tableNames,
            Map      parameters)
        throws  BuildException
    {
        if  (   (tableNames != null)
             && (parameters != null))
        {
            parameters.put(TABLE_NAMES, tableNames);
        }
    }

    /**
     * Stores the database metadata manager in the attribute map.
     * @param metaDataManager the database metadata manager.
     * @param parameters the parameter map.
     * @throws BuildException if the manager cannot be stored for any reason.
     */
    protected void storeMetaDataManager(
            DatabaseMetaDataManager metaDataManager,
            Map                    parameters)
        throws  BuildException
    {
        if  (   (metaDataManager != null)
             && (parameters      != null))
        {
            parameters.put(DATABASE_METADATA_MANAGER, metaDataManager);
        }
    }

    /**
     * Builds the table key.
     * @return the map key.
     */
    protected Object buildTableKey()
    {
        return "'¡'¡'table";
    }

    /**
     * Builds the table fields key.
     * @return the map key.
     */
    protected Object buildTableFieldsKey(Object key)
    {
        return ".98.table'¡'¡'fields`p" + key;
    }

    /**
     * Builds a pk key using given object.
     * @param firstKey the first object key.
     * @return the map key.
     */
    protected Object buildPkKey(Object firstKey)
    {
        return ".|\\|.pk" + firstKey;
    }

    /**
     * Builds a pk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    protected Object buildPkKey(Object firstKey, Object secondKey)
    {
        return buildPkKey(firstKey) + "-.,.,-" + secondKey;
    }

    /**
     * Builds a fk key using given object.
     * @param firstKey the first object key.
     * @return the map key.
     */
    protected Object buildFkKey(Object firstKey)
    {
        return "==fk''" + firstKey;
    }

    /**
     * Builds a fk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    protected Object buildFkKey(Object firstKey, Object secondKey)
    {
        return buildFkKey(firstKey) + ".,.," + secondKey;
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
