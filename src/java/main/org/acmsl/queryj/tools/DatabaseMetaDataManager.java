
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
 * Description: Manages the information provided by database metadata.
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
package org.acmsl.queryj.tools;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.Field;
import org.acmsl.queryj.tools.ProcedureMetaData;
import org.acmsl.queryj.tools.ProcedureParameterMetaData;
import org.acmsl.queryj.QueryJException;

/*
 * Importing some JDK classes.
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing Jakarta Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Manages the information provided by database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class DatabaseMetaDataManager
{
    /**
     * An empty String array for performance issues.
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * An empty int array for performance issues.
     */
    public static final int[] EMPTY_INT_ARRAY = new int[0];

    /**
     * An empty procedure metadata array for performance issues.
     */
    public static final ProcedureMetaData[] EMPTY_PROCEDURE_METADATA_ARRAY =
        new ProcedureMetaData[0];

    /**
     * An empty procedure parameter metadata array for performance issues.
     */
    public static final ProcedureParameterMetaData[]
        EMPTY_PROCEDURE_PARAMETER_METADATA_ARRAY =
            new ProcedureParameterMetaData[0];

    /**
     * The database metadata.
     */
    private DatabaseMetaData m__MetaData;

    /**
     * The catalog.
     */
    private String m__strCatalog;

    /**
     * The schema.
     */
    private String m__strSchema;

    /**
     * The table names.
     */
    private String[] m__astrTableNames;

    /**
     * The column names (as list) of each table.
     */
    private Map m__mColumnNames;

    /**
     * The column types (as list) of each table and column name.
     */
    private Map m__mColumnTypes;

    /**
     * The tables' primary keys information.
     */
    private Map m__mPrimaryKeys;

    /**
     * The tables' foreign keys information.
     */
    private Map m__mForeignKeys;

    /**
     * The externally-managed fields.
     */
    private Map m__mExternallyManagedFields;

    /**
     * The procedure names.
     */
    private String[] m__astrProcedureNames;

    /**
     * The procedures metadata.
     */
    private ProcedureMetaData[] m__aProceduresMetaData;

    /**
     * The procedure parameters meta data (as list).
     */
    private Map m__mProcedureParametersMetaData;

    /**
     * The procedure return types (as list).
     */
    private Map m__mProcedureReturnTypes;

    /**
     * The table extraction flag. This flag allows to disable
     * the operations to retrieve the table information from
     * the database metadata.
     */
    private boolean m__bDisableTableExtraction;

    /**
     * The lazy table extraction flag. If the table names are explicitly
     * specified, then their metadata should be extracted only if
     * such information is not explicit as well.
     */
    private boolean m__bLazyTableExtraction;

    /**
     * The procedure extraction flag. This flag allows to disable
     * the operations to retrieve the procedure information from
     * the database metadata.
     */
    private boolean m__bDisableProcedureExtraction;

    /**
     * The lazy procedure extraction flag. If the procedure names are
     * explicitly specified, then their metadata should be extracted only if
     * such information is not explicit as well.
     */
    private boolean m__bLazyProcedureExtraction;

    /**
     * The task, for logging purposes.
     */
    private Task m__Task;

    /**
     * The project, for logging purposes.
     */
    private Project m__Project;

    /**
     * Creates an empty DatabaseMetaDataManager.
     */
    protected DatabaseMetaDataManager()
    {
        Map t_UniqueMap = new HashMap();
        inmutableSetColumnNames(t_UniqueMap);
        inmutableSetColumnTypes(t_UniqueMap);
        inmutableSetProcedureParametersMetaData(t_UniqueMap);
        inmutableSetPrimaryKeys(t_UniqueMap);
        inmutableSetForeignKeys(t_UniqueMap);
        inmutableSetExternallyManagedFields(t_UniqueMap);
    }

    /**
     * Creates a DatabaseMetaDataManager using given information.
     * @param tableNames explicitly specified table names.
     * @param procedureNames explicitly specified procedure names.
     * @param disableTableExtraction <code>true</code> to disable table
     * extraction.
     * @param lazyTableExtraction if the table metadata should not be
     * retrieved from the database until the tables to extract are
     * specified.
     * @param disableProcedureExtraction <code>true</code> to disable
     * procedure extraction.
     * @param lazyProcedureExtraction if the procedure metadata should not
     * be retrieved from the database until the procedures to extract are
     * specified.
     * @param metaData the database meta data.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param retrieveMetaData <code>true</code> for retrieving the metadata
     * directly in the constructor.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    protected DatabaseMetaDataManager(
            String[]         tableNames,
            String[]         procedureNames,
            boolean          disableTableExtraction,
            boolean          lazyTableExtraction,
            boolean          disableProcedureExtraction,
            boolean          lazyProcedureExtraction,
            DatabaseMetaData metaData,
            String           catalog,
            String           schema,
            boolean          retrieveMetaData)
        throws  SQLException,
                QueryJException
    {
        this();
        inmutableSetTableNames(tableNames);
        inmutableSetProcedureNames(procedureNames);
        inmutableSetDisableTableExtraction(disableTableExtraction);
        inmutableSetLazyTableExtraction(lazyTableExtraction);
        inmutableSetDisableProcedureExtraction(disableProcedureExtraction);
        inmutableSetLazyProcedureExtraction(lazyProcedureExtraction);
        inmutableSetMetaData(metaData);
        inmutableSetCatalog(catalog);
        inmutableSetSchema(schema);

        if  (retrieveMetaData)
        {
            retrieveMetaData(
                tableNames,
                procedureNames,
                disableTableExtraction,
                lazyTableExtraction,
                disableProcedureExtraction,
                lazyProcedureExtraction,
                metaData,
                catalog,
                schema);
        }
    }


    /**
     * Creates a DatabaseMetaDataManager using given information.
     * @param tableNames explicitly specified table names.
     * @param procedureNames explicitly specified procedure names.
     * @param disableTableExtraction <code>true</code> to disable table
     * extraction.
     * @param lazyTableExtraction if the table metadata should not be
     * retrieved from the database until the tables to extract are
     * specified.
     * @param disableProcedureExtraction <code>true</code> to disable
     * procedure extraction.
     * @param lazyProcedureExtraction if the procedure metadata should not
     * be retrieved from the database until the procedures to extract are
     * specified.
     * @param metaData the database meta data.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    public DatabaseMetaDataManager(
            String[]         tableNames,
            String[]         procedureNames,
            boolean          disableTableExtraction,
            boolean          lazyTableExtraction,
            boolean          disableProcedureExtraction,
            boolean          lazyProcedureExtraction,
            DatabaseMetaData metaData,
            String           catalog,
            String           schema)
        throws  SQLException,
                QueryJException
    {
        this(
            tableNames,
            procedureNames,
            disableTableExtraction,
            lazyTableExtraction,
            disableProcedureExtraction,
            lazyProcedureExtraction,
            metaData,
            catalog,
            schema,
            true);
    }

    /**
     * Creates a DatabaseMetaDataManager using given information.
     * @param tableNames explicitly specified table names.
     * @param procedureNames explicitly specified procedure names.
     * @param metaData the database meta data.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    protected DatabaseMetaDataManager(
            String[]         tableNames,
            String[]         procedureNames,
            DatabaseMetaData metaData,
            String           catalog,
            String           schema)
        throws  SQLException,
                QueryJException
    {
        this(
            tableNames,
            procedureNames,
            false,
            false,
            false,
            false,
            metaData,
            catalog,
            schema);
    }

    /**
     * Creates a DatabaseMetaDataManager using given information.
     * @param tableNames explicitly specified table names.
     * @param procedureNames explicitly specified procedure names.
     * @param metaData the database meta data.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param retrieveMetaData <code>true</code> for retrieving the metadata
     * directly in the constructor.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    protected DatabaseMetaDataManager(
            String[]         tableNames,
            String[]         procedureNames,
            DatabaseMetaData metaData,
            String           catalog,
            String           schema,
            boolean          retrieveMetaData)
        throws  SQLException,
                QueryJException
    {
        this(
            tableNames,
            procedureNames,
            false,
            false,
            false,
            false,
            metaData,
            catalog,
            schema,
            retrieveMetaData);
    }

    /**
     * Creates a DatabaseMetaDataManager using given information.
     * @param tableNames explicitly specified table names.
     * @param procedureNames explicitly specified procedure names.
     * @param disableTableExtraction <code>true</code> to disable table
     * extraction.
     * @param lazyTableExtraction if the table metadata should not be
     * retrieved from the database until the tables to extract are
     * specified.
     * @param disableProcedureExtraction <code>true</code> to disable
     * procedure extraction.
     * @param lazyProcedureExtraction if the procedure metadata should not
     * be retrieved from the database until the procedures to extract are
     * specified.
     * @param metaData the database meta data.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param project the project, for logging purposes
     * (optional).
     * @param task the task, for logging purposes (optional).
     * @{link http://192.168.0.2/bugzilla/show_bug.cgi?id=111}
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    public DatabaseMetaDataManager(
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
        throws  SQLException,
                QueryJException
    {
        this(
            tableNames,
            procedureNames,
            disableTableExtraction,
            lazyTableExtraction,
            disableProcedureExtraction,
            lazyProcedureExtraction,
            metaData,
            catalog,
            schema,
            false);

        inmutableSetProject(project);
        inmutableSetTask(task);

        retrieveMetaData(
            tableNames,
            procedureNames,
            disableTableExtraction,
            lazyTableExtraction,
            disableProcedureExtraction,
            lazyProcedureExtraction,
            metaData,
            catalog,
            schema);
    }

    /**
     * Creates a DatabaseMetaDataManager using given information.
     * @param tableNames explicitly specified table names.
     * @param procedureNames explicitly specified procedure names.
     * @param metaData the database meta data.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param task the task, for logging purposes (optional).
     * @param project the project, for logging purposes
     * (optional).
     * @{link http://192.168.0.2/bugzilla/show_bug.cgi?id=111}
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    public DatabaseMetaDataManager(
            String[]         tableNames,
            String[]         procedureNames,
            DatabaseMetaData metaData,
            String           catalog,
            String           schema,
            Project          project,
            Task             task)
        throws  SQLException,
                QueryJException
    {
        this(
            tableNames,
            procedureNames,
            metaData,
            catalog,
            schema,
            false);

        inmutableSetProject(project);
        inmutableSetTask(task);

        retrieveMetaData(
            tableNames,
            getProcedureNames(),
            getDisableTableExtraction(),
            getLazyTableExtraction(),
            getDisableProcedureExtraction(),
            getLazyProcedureExtraction(),
            metaData,
            catalog,
            schema);
    }

    /**
     * Retrieves the metadata.
     * @param tableNames explicitly specified table names.
     * @param procedureNames explicitly specified procedure names.
     * @param disableTableExtraction <code>true</code> to disable table
     * extraction.
     * @param lazyTableExtraction if the table metadata should not be
     * retrieved from the database until the tables to extract are
     * specified.
     * @param disableProcedureExtraction <code>true</code> to disable
     * procedure extraction.
     * @param lazyProcedureExtraction if the procedure metadata should not
     * be retrieved from the database until the procedures to extract are
     * specified.
     * @param metaData the database meta data.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    private void retrieveMetaData(
            String[]         tableNames,
            String[]         procedureNames,
            boolean          disableTableExtraction,
            boolean          lazyTableExtraction,
            boolean          disableProcedureExtraction,
            boolean          lazyProcedureExtraction,
            DatabaseMetaData metaData,
            String           catalog,
            String           schema)
        throws  SQLException,
                QueryJException
    {
        if  (   (!disableTableExtraction)
             && (!lazyTableExtraction))
        {
            extractTableMetaData(
                tableNames,
                metaData,
                catalog,
                schema);

            extractPrimaryKeys(metaData, catalog, schema);
            extractForeignKeys(metaData, catalog, schema);
        }

        if  (   (!disableProcedureExtraction)
             && (!lazyProcedureExtraction))
        {
            extractProcedureMetaData(
                procedureNames,
                metaData,
                catalog,
                schema);
        }
    }

    /**
     * Specifies the meta data.
     * @param metaData the database meta data.
     */
    private void inmutableSetMetaData(DatabaseMetaData metaData)
    {
        m__MetaData = metaData;
    }

    /**
     * Specifies the meta data.
     * @param metaData the database meta data.
     */
    protected void setMetaData(DatabaseMetaData metaData)
    {
        inmutableSetMetaData(metaData);
    }

    /**
     * Retrieves the database meta data.
     * @return such information.
     */
    public DatabaseMetaData getMetaData()
    {
        return m__MetaData;
    }

    /**
     * Specifies the catalog.
     * @param catalog the database catalog.
     */
    private void inmutableSetCatalog(String catalog)
    {
        m__strCatalog = catalog;
    }

    /**
     * Specifies the catalog.
     * @param catalog the database catalog.
     */
    protected void setCatalog(String catalog)
    {
        inmutableSetCatalog(catalog);
    }

    /**
     * Retrieves the catalog.
     * @return the database catalog.
     */
    protected String getCatalog()
    {
        return m__strCatalog;
    }

    /**
     * Specifies the schema.
     * @param schema the database schema.
     */
    private void inmutableSetSchema(String schema)
    {
        m__strSchema = schema;
    }

    /**
     * Specifies the schema.
     * @param schema the database schema.
     */
    protected void setSchema(String schema)
    {
        inmutableSetSchema(schema);
    }

    /**
     * Retrieves the schema.
     * @return the database schema.
     */
    protected String getSchema()
    {
        return m__strSchema;
    }

    /**
     * Specifies the whether the table extraction should be disabled.
     * @param flag such flag.
     */
    private void inmutableSetDisableTableExtraction(boolean flag)
    {
        m__bDisableTableExtraction = flag;
    }

    /**
     * Specifies the whether the table extraction should be disabled.
     * @param flag such flag.
     */
    protected void setDisableTableExtraction(boolean flag)
    {
        inmutableSetDisableTableExtraction(flag);
    }

    /**
     * Retrieves whether the table extraction is disabled.
     * @return such flag.
     */
    protected boolean getDisableTableExtraction()
    {
        return m__bDisableTableExtraction;
    }

    /**
     * Specifies the lazy table extraction flag.
     * @param flag such flag.
     */
    private void inmutableSetLazyTableExtraction(boolean flag)
    {
        m__bLazyTableExtraction = flag;
    }

    /**
     * Specifies the lazy table extraction flag.
     * @param flag such flag.
     */
    protected void setLazyTableExtraction(boolean flag)
    {
        inmutableSetLazyTableExtraction(flag);
    }

    /**
     * Retrieves the lazy table extraction flag.
     * @return such flag.
     */
    protected boolean getLazyTableExtraction()
    {
        return m__bLazyTableExtraction;
    }

    /**
     * Specifies the whether the procedure extraction should be disabled.
     * @param flag such flag.
     */
    private void inmutableSetDisableProcedureExtraction(boolean flag)
    {
        m__bDisableProcedureExtraction = flag;
    }

    /**
     * Specifies the whether the procedure extraction should be disabled.
     * @param flag such flag.
     */
    protected void setDisableProcedureExtraction(boolean flag)
    {
        inmutableSetDisableProcedureExtraction(flag);
    }

    /**
     * Retrieves whether the procedure extraction is disabled.
     * @return such flag.
     */
    protected boolean getDisableProcedureExtraction()
    {
        return m__bDisableProcedureExtraction;
    }

    /**
     * Specifies the lazy procedure extraction flag.
     * @param flag such flag.
     */
    private void inmutableSetLazyProcedureExtraction(boolean flag)
    {
        m__bLazyProcedureExtraction = flag;
    }

    /**
     * Specifies the lazy procedure extraction flag.
     * @param flag such flag.
     */
    protected void setLazyProcedureExtraction(boolean flag)
    {
        inmutableSetLazyProcedureExtraction(flag);
    }

    /**
     * Retrieves the lazy procedure extraction flag.
     * @return such flag.
     */
    protected boolean getLazyProcedureExtraction()
    {
        return m__bLazyProcedureExtraction;
    }

    /**
     * Specifies the table names.
     * @param names such names.
     */
    private void inmutableSetTableNames(String[] names)
    {
        m__astrTableNames = names;
    }

    /**
     * Specifies the table names.
     * @param names such names.
     */
    protected void setTableNames(String[] names)
    {
        inmutableSetTableNames(names);
    }

    /**
     * Retrieves the table names.
     * @return such names.
     */
    public String[] getTableNames()
    {
        return m__astrTableNames;
    }

    /**
     * Specifies the column names.
     * @param map the column names map.
     */
    private void inmutableSetColumnNames(Map map)
    {
        m__mColumnNames = map;
    }

    /**
     * Specifies the column names.
     * @param map the column names map.
     */
    protected void setColumnNames(Map map)
    {
        inmutableSetColumnNames(map);
    }

    /**
     * Retrieves the column names.
     * @return such map.
     */
    protected Map getColumnNames()
    {
        return m__mColumnNames;
    }


    /**
     * Adds the column names of given table.
     * @param tableName the table name.
     * @param columnName the column name.
     */
    public void addColumnNames(
        String   tableName,
        String[] columnNames)
    {
        if  (columnNames != null) 
        {
            Map t_mColumnNames = getColumnNames();

            if  (t_mColumnNames != null) 
            {
                t_mColumnNames.put(
                    buildKey(tableName),
                    columnNames);
            }
        }
    }

    /**
     * Retrieves the column names.
     * @param tableName the table name.
     * @return the column names.
     */
    public String[] getColumnNames(String tableName)
    {
        String[] result = EMPTY_STRING_ARRAY;

        if  (tableName != null) 
        {
            Map t_mColumnNames = getColumnNames();

            if  (t_mColumnNames != null) 
            {
                result = (String[]) t_mColumnNames.get(buildKey(tableName));
            }
        }
        
        return result;
    }

    /**
     * Specifies the column types.
     * @param map the column types map.
     */
    private void inmutableSetColumnTypes(Map map)
    {
        m__mColumnTypes = map;
    }

    /**
     * Specifies the column types.
     * @param map the column types map.
     */
    protected void setColumnTypes(Map map)
    {
        inmutableSetColumnTypes(map);
    }

    /**
     * Retrieves the column types.
     * @return such map.
     */
    protected Map getColumnTypes()
    {
        return m__mColumnTypes;
    }

    /**
     * Retrieves the column type.
     * @param tableName the table name.
     * @param columnName the column name.
     * @return the column type.
     * @see java.sql.Types
     */
    public int getColumnType(String tableName, String columnName)
    {
        int result = -1;

        if  (   (tableName  != null)
             && (columnName != null))
        {
            Map t_mColumnTypes = getColumnTypes();

            if  (t_mColumnTypes != null) 
            {
                Object t_Result =
                    t_mColumnTypes.get(buildKey(tableName, columnName));

                if  (   (t_Result != null)
                     && (t_Result instanceof Integer))
                {
                    result = ((Integer) t_Result).intValue();
                }
            }
        }
        
        return result;
    }

    /**
     * Adds a column type.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param columnType the column type.
     */
    public void addColumnType(
        String tableName,
        String columnName,
        int    columnType)
    {
        Map t_mColumnTypes = getColumnTypes();

        if  (t_mColumnTypes != null) 
        {
            t_mColumnTypes.put(
                buildKey(tableName, columnName),
                new Integer(columnType));
        }
    }

    /**
     * Specifies the primary keys
     * @param map the primary keys map.
     */
    private void inmutableSetPrimaryKeys(Map map)
    {
        m__mPrimaryKeys = map;
    }

    /**
     * Specifies the primary keys.
     * @param map the primary keys map.
     */
    protected void setPrimaryKeys(Map map)
    {
        inmutableSetPrimaryKeys(map);
    }

    /**
     * Retrieves the primary keys.
     * @return such map.
     */
    protected Map getPrimaryKeys()
    {
        return m__mPrimaryKeys;
    }

    /**
     * Adds a primary key.
     * @param tableName the table name.
     * @param columnName the column name.
     */
    public void addPrimaryKey(
        String tableName,
        String columnName)
    {
        Map t_mPrimaryKeys = getPrimaryKeys();

        if  (t_mPrimaryKeys != null) 
        {
            logVerbose(
                "Adding pk:" + tableName + "." + columnName);

            t_mPrimaryKeys.put(
                buildPkKey(tableName, columnName),
                columnName);

            Collection t_cPks = (Collection) t_mPrimaryKeys.get(buildPkKey(tableName));

            if  (t_cPks == null)
            {
                t_cPks = new ArrayList();
                t_mPrimaryKeys.put(buildPkKey(tableName), t_cPks);
            }

            t_cPks.add(columnName);
        }
    }

    /**
     * Retrieves the primary keys.
     * @param tableName the table name.
     * @return the primary keys.
     */
    public String[] getPrimaryKeys(String tableName)
    {
        String[] result = EMPTY_STRING_ARRAY;

        if  (tableName != null)
        {
            Map t_mPrimaryKeys = getPrimaryKeys();

            if  (t_mPrimaryKeys != null)
            {
                Collection t_cPks = (Collection) t_mPrimaryKeys.get(buildPkKey(tableName));

                if  (t_cPks != null)
                {
                    result = (String[]) t_cPks.toArray(result);
                }
            }
        }
        
        return result;
    }

    /**
     * Checks whether given field is a primary key or not.
     * @param tableName the table name.
     * @param fieldName the field name.
     * @return true if such field identifies a concrete row.
     */
    public boolean isPrimaryKey(String tableName, String fieldName)
    {
        boolean result = false;

        if  (   (tableName != null)
             && (fieldName != null))
        {
            String[] t_astrPks = getPrimaryKeys(tableName);

            if  (t_astrPks != null)
            {
                for  (int t_iPkIndex = 0;
                          t_iPkIndex < t_astrPks.length;
                          t_iPkIndex++)
                {
                    if  (fieldName.equals(t_astrPks[t_iPkIndex]))
                    {
                        result = true;

                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Specifies the foreign keys.
     * @param map the foreign keys map.
     */
    private void inmutableSetForeignKeys(Map map)
    {
        m__mForeignKeys = map;
    }

    /**
     * Specifies the foreign keys.
     * @param map the foreign keys map.
     */
    protected void setForeignKeys(Map map)
    {
        inmutableSetForeignKeys(map);
    }

    /**
     * Retrieves the foreign keys.
     * @return such map.
     */
    protected Map getForeignKeys()
    {
        return m__mForeignKeys;
    }

    /**
     * Adds a foreign key.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param refTableName the referred table name.
     * @param refColumnName the referred column name.
     */
    public void addForeignKey(
        String tableName,
        String columnName,
        String refTableName,
        String refColumnName)
    {
        logVerbose(
              "Adding foreign key: ("
            + tableName
            + ","
            + columnName
            + ") -> ("
            + refTableName
            + ","
            + refColumnName
            + ")");

        Map t_mForeignKeys = getForeignKeys();

        if  (t_mForeignKeys != null) 
        {
            t_mForeignKeys.put(
                buildFkKey(tableName, columnName, refTableName, refColumnName),
                columnName);

            t_mForeignKeys.put(
                buildFkKey(tableName, columnName, refTableName),
                refColumnName);

            t_mForeignKeys.put(
                buildFkKey(tableName, refTableName),
                columnName);

            t_mForeignKeys.put(
                buildRefFkKey(tableName, refTableName),
                refColumnName);

            Collection t_ReferredTables =
                (Collection) t_mForeignKeys.get(buildFkKey(tableName));

            if  (t_ReferredTables == null)
            {
                t_ReferredTables = new ArrayList();
            }

            t_ReferredTables.add(refTableName);

            t_mForeignKeys.put(
                buildFkKey(tableName),
                t_ReferredTables);
        }
    }

    /**
     * Retrieves the tables referred by given table's foreign keys.
     * @param tableName the table name.
     * @return such tables.
     */
    public String[] getReferredTables(String tableName)
    {
        String[] result = EMPTY_STRING_ARRAY;

        if  (tableName != null)
        {
            Map t_mForeignKeys = getForeignKeys();

            if  (t_mForeignKeys != null) 
            {
                Collection t_Result = (Collection) t_mForeignKeys.get(buildFkKey(tableName));

                if  (t_Result != null)
                {
                    result = (String[]) t_Result.toArray(EMPTY_STRING_ARRAY);
                }
            }
        }
        
        return result;
    }

    /**
     * Retrieves the field of given table that points to a field on the referred one.
     * @param tableName the table name.
     * @param refTableName the referred table name.
     * @return such field.
     */
    public String getForeignKey(String tableName, String refTableName)
    {
        String result = null;

        if  (   (tableName    != null)
             && (refTableName != null))
        {
            Map t_mForeignKeys = getForeignKeys();

            if  (t_mForeignKeys != null) 
            {
                result = (String) t_mForeignKeys.get(buildFkKey(tableName, refTableName));
            }
        }
        
        return result;
    }

    /**
     * Retrieves the field of referred table pointed by a field on the original one.
     * @param tableName the table name.
     * @param refTableName the referred table name.
     * @return such field.
     */
    public String getReferredKey(String tableName, String refTableName)
    {
        String result = null;

        if  (   (tableName    != null)
             && (refTableName != null))
        {
            Map t_mForeignKeys = getForeignKeys();

            if  (t_mForeignKeys != null) 
            {
                result = (String) t_mForeignKeys.get(buildRefFkKey(tableName, refTableName));
            }
        }
        
        return result;
    }

    /**
     * Specifies the externally managed fields.
     * @param map the externally managed fields map.
     */
    private void inmutableSetExternallyManagedFields(Map map)
    {
        m__mExternallyManagedFields = map;
    }

    /**
     * Specifies the externally managed fields.
     * @param map the externally managed fields map.
     */
    protected void setExternallyManagedFields(Map map)
    {
        inmutableSetExternallyManagedFields(map);
    }

    /**
     * Retrieves the externally managed fields.
     * @return such map.
     */
    protected Map getExternallyManagedFields()
    {
        return m__mExternallyManagedFields;
    }

    /**
     * Annotates a externally-managed field.
     * @param tableName the table name.
     * @param columnName the column name.
     */
    public void addExternallyManagedField(
        String tableName,
        String columnName)
    {
        addExternallyManagedField(
            tableName,
            columnName,
            null);
    }

    /**
     * Annotates a externally-managed field.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param keyword the keyword.
     */
    public void addExternallyManagedField(
        String tableName,
        String columnName,
        String keyword)
    {
        Map t_mExternallyManagedFields = getExternallyManagedFields();

        if  (t_mExternallyManagedFields != null) 
        {
            logVerbose(
                  "Adding externally-managed field:"
                + tableName + "." + columnName
                + "-" + keyword);

            t_mExternallyManagedFields.put(
                buildExternallyManagedFieldKey(tableName, columnName),
                buildExternallyManagedFieldValue(keyword));

            Collection t_cExternallyManagedTableFields =
                (Collection)
                    t_mExternallyManagedFields.get(
                        buildExternallyManagedFieldKey(tableName));

            if  (t_cExternallyManagedTableFields == null)
            {
                t_cExternallyManagedTableFields = new ArrayList();
                t_mExternallyManagedFields.put(
                    buildExternallyManagedFieldKey(tableName),
                    t_cExternallyManagedTableFields);
            }

            t_cExternallyManagedTableFields.add(columnName);
        }
    }

    /**
     * Retrieves the externally-managed fields.
     * @param tableName the table name.
     * @return the externally-managed fields of such table.
     */
    public String[] getExternallyManagedFields(String tableName)
    {
        String[] result = EMPTY_STRING_ARRAY;

        if  (tableName != null)
        {
            Map t_mExternallyManagedFields = getExternallyManagedFields();

            if  (t_mExternallyManagedFields != null)
            {
                Collection t_cExternallyManagedFields =
                    (Collection)
                        t_mExternallyManagedFields.get(
                            buildExternallyManagedFieldKey(tableName));

                if  (t_cExternallyManagedFields != null)
                {
                    result = (String[]) t_cExternallyManagedFields.toArray(result);
                }
            }
        }
        
        return result;
    }

    /**
     * Checks whether given field is externally managed or not.
     * @param tableName the table name.
     * @param fieldName the field name.
     * @return <code>true</code> if such field is managed externally.
     */
    public boolean isManagedExternally(String tableName, String fieldName)
    {
        boolean result = false;

        if  (   (tableName != null)
             && (fieldName != null))
        {
            String[] t_astrExternallyManagedFields = getExternallyManagedFields(tableName);

            if  (t_astrExternallyManagedFields != null)
            {
                for  (int t_iExternallyManagedFieldIndex = 0;
                            t_iExternallyManagedFieldIndex
                          < t_astrExternallyManagedFields.length;
                          t_iExternallyManagedFieldIndex++)
                {
                    if  (fieldName.equals(
                             t_astrExternallyManagedFields[
                                 t_iExternallyManagedFieldIndex]))
                    {
                        result = true;

                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the keyword used to create new values of given field.
     * @param tableName the table name.
     * @param fieldName the field name.
     * @return such keyword, or <code>null</code> if such information is
     * unknown.
     */
    public String getKeyword(String tableName, String fieldName)
    {
        String result = null;

        Map t_mExternallyManagedFields = getExternallyManagedFields();

        if  (   (tableName != null)
             && (fieldName != null)
             && (t_mExternallyManagedFields != null))
        {
            Collection t_cExternallyManagedTableFields =
                (Collection)
                    t_mExternallyManagedFields.get(
                        buildExternallyManagedFieldKey(tableName));

            if  (t_cExternallyManagedTableFields != null)
            {
                Iterator t_itFieldIterator =
                    t_cExternallyManagedTableFields.iterator();

                while  (   (t_itFieldIterator != null)
                        && (t_itFieldIterator.hasNext()))
                {
                    Object t_Field = t_itFieldIterator.next();

                    result =
                          ""
                        + t_mExternallyManagedFields.get(
                              buildExternallyManagedFieldKey(
                                  tableName, t_Field));
                }
            }
        }

        return result;
    }

    /**
     * Specifies the procedure names.
     * @param names such names.
     */
    private void inmutableSetProcedureNames(String[] names)
    {
        m__astrProcedureNames = names;
    }

    /**
     * Specifies the procedure names.
     * @param names such names.
     */
    protected void setProcedureNames(String[] names)
    {
        inmutableSetProcedureNames(names);
    }

    /**
     * Retrieves the procedure names.
     * @return such names.
     */
    protected String[] getProcedureNames()
    {
        return m__astrProcedureNames;
    }

    /**
     * Specifies the procedures metadata.
     * @param proceduresMetaData such metadata.
     */
    private void inmutableSetProceduresMetaData(ProcedureMetaData[] proceduresMetaData)
    {
        m__aProceduresMetaData = proceduresMetaData;
    }

    /**
     * Specifies the procedures metadata.
     * @param proceduresMetaData such metadata.
     */
    protected void setProceduresMetaData(ProcedureMetaData[] proceduresMetaData)
    {
        inmutableSetProceduresMetaData(proceduresMetaData);
    }

    /**
     * Retrieves the procedures metadata.
     * @return such metadata.
     */
    public ProcedureMetaData[] getProceduresMetaData()
    {
        return m__aProceduresMetaData;
    }

    /**
     * Specifies the procedure parameter metadata.
     * @param map the parameter metadata map.
     */
    private void inmutableSetProcedureParametersMetaData(Map map)
    {
        m__mProcedureParametersMetaData = map;
    }

    /**
     * Specifies the procedure parameter metadata.
     * @param map the parameter metadata map.
     */
    protected void setProcedureParametersMetaData(Map map)
    {
        inmutableSetProcedureParametersMetaData(map);
    }

    /**
     * Retrieves the procedure parameters metadata.
     * @return such map.
     */
    protected Map getProcedureParametersMetaData()
    {
        return m__mProcedureParametersMetaData;
    }

    /**
     * Adds the parameters of given procedure.
     * @param procedureName the procedure name.
     * @param parametersMetaData the parameters metadata.
     */
    protected void addProcedureParametersMetaData(
        String                       procedureName,
        ProcedureParameterMetaData[] parametersMetaData)
    {
        if  (parametersMetaData != null) 
        {
            Map t_mParametersMetaData = getProcedureParametersMetaData();

            if  (t_mParametersMetaData != null) 
            {
                t_mParametersMetaData.put(
                    buildKey(procedureName),
                    parametersMetaData);
            }
        }
    }

    /**
     * Retrieves the parameters of given procedure.
     * @param procedure the procedure.
     * @return the parameters metadata.
     */
    public ProcedureParameterMetaData[] getProcedureParametersMetaData(
        ProcedureMetaData procedure)
    {
        ProcedureParameterMetaData[] result =
            EMPTY_PROCEDURE_PARAMETER_METADATA_ARRAY;

        if  (procedure != null)
        {
            result = getProcedureParametersMetaData(procedure.getName());
        }

        return result;
    }

    /**
     * Retrieves the parameters of given procedure.
     * @param procedureName the procedure name.
     * @return the parameters metadata.
     */
    public ProcedureParameterMetaData[] getProcedureParametersMetaData(
        String procedureName)
    {
        ProcedureParameterMetaData[] result =
            EMPTY_PROCEDURE_PARAMETER_METADATA_ARRAY;

        if  (procedureName != null)
        {
            Map t_mParametersMetaData = getProcedureParametersMetaData();

            if  (t_mParametersMetaData != null) 
            {
                result =
                    (ProcedureParameterMetaData[])
                        t_mParametersMetaData.get(buildKey(procedureName));
            }
        }

        return result;
    }

    /**
     * Specifies the task.
     * @param task the task.
     */
    private void inmutableSetTask(Task task)
    {
        m__Task = task;
    }

    /**
     * Specifies the task.
     * @param task the task.
     */
    protected void setTask(Task task)
    {
        inmutableSetTask(task);
    }

    /**
     * Retrieves the task.
     * @return such instance.
     */
    public Task getTask()
    {
        return m__Task;
    }

    /**
     * Specifies the project.
     * @param project the project.
     */
    private void inmutableSetProject(Project project)
    {
        m__Project = project;
    }

    /**
     * Specifies the project.
     * @param project the project.
     */
    protected void setProject(Project project)
    {
        inmutableSetProject(project);
    }

    /**
     * Retrieves the project.
     * @return such instance.
     */
    public Project getProject()
    {
        return m__Project;
    }

    /**
     * Builds a key using given object.
     * @param key the object key.
     * @return the map key.
     */
    protected Object buildKey(Object key)
    {
        return ((key == null) ? "null" : key);
    }

    /**
     * Builds a key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    protected Object buildKey(Object firstKey, Object secondKey)
    {
        return
            "_|_" + buildKey(firstKey) + "|'|" + buildKey(secondKey) + "|_|";
    }

    /**
     * Builds a pk key using given object.
     * @param firstKey the first object key.
     * @return the map key.
     */
    protected Object buildPkKey(Object firstKey)
    {
        return "[pk]!!" + buildKey(firstKey);
    }

    /**
     * Builds a pk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    protected Object buildPkKey(Object firstKey, Object secondKey)
    {
        return buildPkKey(firstKey) + ".,.," + secondKey;
    }

    /**
     * Builds a pk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    protected Object buildPkKey(Object firstKey, Object secondKey, Object thirdKey)
    {
        return buildPkKey(firstKey, secondKey) + ";;" + thirdKey;
    }

    /**
     * Builds a fk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    protected Object buildFkKey(Object firstKey)
    {
        return "[fk]!!" + buildKey(firstKey);
    }

    /**
     * Builds a fk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    protected Object buildFkKey(Object firstKey, Object secondKey)
    {
        return buildFkKey(firstKey) + "++" + secondKey;
    }

    /**
     * Builds a fk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    protected Object buildFkKey(Object firstKey, Object secondKey, Object thirdKey)
    {
        return buildFkKey(firstKey, secondKey) + ";:;:" + thirdKey;
    }

    /**
     * Builds a ref fk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @param thirdKey the third object key.
     * @param fourthKey the fourth key.
     * @return the map key.
     */
    protected Object buildFkKey(
        Object firstKey,
        Object secondKey,
        Object thirdKey,
        Object fourthKey)
    {
        return buildFkKey(firstKey, secondKey, thirdKey) + ".;.;" + fourthKey;
    }

    /**
     * Builds a ref fk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    protected Object buildRefFkKey(Object firstKey, Object secondKey)
    {
        return "\\ref//" + buildFkKey(firstKey, secondKey);
    }

    /**
     * Builds a externally-managed field key using given object.
     * @param firstKey the first object key.
     * @return the map key.
     */
    protected Object buildExternallyManagedFieldKey(Object firstKey)
    {
        return "[externally-managed-field]!!" + buildKey(firstKey);
    }

    /**
     * Builds a externally-managed field key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    protected Object buildExternallyManagedFieldKey(
        Object firstKey, Object secondKey)
    {
        return buildExternallyManagedFieldKey(firstKey) + ".,.," + secondKey;
    }

    /**
     * Builds a externally-managed field value using given object.
     * @param value the object.
     * @return the map value.
     */
    protected Object buildExternallyManagedFieldValue(Object value)
    {
        Object result = "";

        if  (value != null)
        {
            result = value;
        }

        return result;
    }

    /**
     * Retrieves the desired metadata.
     * @param tableNames optionally specified table names.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    private void extractTableMetaData(
            String[]         tableNames,
            DatabaseMetaData metaData,
            String           catalog,
            String           schema)
        throws  SQLException,
                QueryJException
    {
        String[] t_astrTableNames = tableNames;

        String t_strTableNames = "";

        if  (t_astrTableNames == null) 
        {
            t_astrTableNames = getTableNames(metaData, catalog, schema);

        }

        if  (   (t_astrTableNames != null)
             && (t_astrTableNames.length > 0))
        {
            for  (int t_iIndex = 0; t_iIndex < t_astrTableNames.length; t_iIndex++) 
            {
                t_strTableNames += " " + t_astrTableNames[t_iIndex];
            }
        }

        setTableNames(t_astrTableNames);

        if  (   (t_astrTableNames != null)
             && (t_astrTableNames.length > 0))
        {
            for  (int t_iIndex = 0;
                      t_iIndex < t_astrTableNames.length;
                      t_iIndex++) 
            {
                String[] t_astrColumnNames =
                    getColumnNames(
                        metaData,
                        catalog,
                        schema,
                        t_astrTableNames[t_iIndex]);

                addColumnNames(t_astrTableNames[t_iIndex], t_astrColumnNames);

                if  (t_astrColumnNames != null) 
                {
                    int[] t_aiColumnTypes =
                        getColumnTypes(
                            metaData,
                            catalog,
                            schema,
                            t_astrTableNames[t_iIndex],
                            t_astrColumnNames.length);

                    if  (t_aiColumnTypes != null)
                    {
                        for  (int t_iColumnIndex = 0;
                                  t_iColumnIndex < t_aiColumnTypes.length;
                                  t_iColumnIndex++)
                        {
                            addColumnType(
                                t_astrTableNames[t_iIndex],
                                t_astrColumnNames[t_iColumnIndex],
                                t_aiColumnTypes[t_iColumnIndex]);
                        }
                    }
                }
            }
        }        
    }

    /**
     * Retrieves the desired metadata.
     * @param procedureNames optionally specified procedure names.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    private void extractProcedureMetaData(
            String[]         procedureNames,
            DatabaseMetaData metaData,
            String           catalog,
            String           schema)
        throws  SQLException,
                QueryJException
    {
        String[] t_astrProcedureNames = procedureNames;

        if  (t_astrProcedureNames == null) 
        {
            t_astrProcedureNames = getProcedureNames(metaData, catalog, schema);
        }

        setProcedureNames(t_astrProcedureNames);

        if  (   (t_astrProcedureNames != null)
             && (t_astrProcedureNames.length > 0))
        {
            for  (int t_iIndex = 0;
                      t_iIndex < t_astrProcedureNames.length;
                      t_iIndex++) 
            {
                ProcedureParameterMetaData[] t_aProcedureParametersMetaData =
                    getProcedureParametersMetaData(
                        metaData,
                        catalog,
                        schema,
                        t_astrProcedureNames[t_iIndex]);

                addProcedureParametersMetaData(
                    t_astrProcedureNames[t_iIndex],
                    t_aProcedureParametersMetaData);
            }
        }        
    }

    /**
     * Retrieves the table names.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @return the list of all table names.
     * @throws SQLException if the database operation fails.
     */
    protected String[] getTableNames(
            DatabaseMetaData metaData,
            String           catalog,
            String           schema)
        throws  SQLException,
                QueryJException
    {
        String[] result = EMPTY_STRING_ARRAY;

        if  (metaData != null)
        {
            ResultSet t_rsTables = null;

            try 
            {
                try 
                {
                    t_rsTables =
                        metaData.getTables(
                            catalog,
                            schema,
                            null,
                            new String[]{ "TABLE" });
                }
                catch  (SQLException sqlException)
                {
                    throw
                        new QueryJException(
                            "cannot.retrieve.database.table.names",
                            sqlException);
                }

                result = extractTableNames(t_rsTables);
            }
            catch  (SQLException sqlException)
            {
                throw sqlException;
            }
            catch  (QueryJException queryjException)
            {
                throw queryjException;
            }
            finally 
            {
                if  (t_rsTables != null)
                {
                    t_rsTables.close();
                }
            }
        }

        return result;
    }

    /**
     * Extracts the table names from given result set.
     * @param resultSet the result set with the table information.
     * @return the list of column names.
     * @throws SQLException if the database operation fails.
     */
    protected String[] extractTableNames(ResultSet resultSet)
        throws  SQLException
    {
        String[] result = EMPTY_STRING_ARRAY;

        if  (resultSet != null) 
        {
            result = extractStringFields(resultSet, "TABLE_NAME");
        }

        return result;
    }

    /**
     * Retrieves the column names from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @return the list of all column names.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if the any other error occurs.
     */
    protected String[] getColumnNames(
            DatabaseMetaData metaData,
            String           catalog,
            String           schema,
            String           tableName)
        throws  SQLException,
                QueryJException
    {
        String[] result = EMPTY_STRING_ARRAY;

        try 
        {
            if  (metaData != null) 
            {
                ResultSet t_rsColumns =
                    metaData.getColumns(
                        catalog,
                        schema,
                        tableName,
                        null);

                result = extractColumnNames(t_rsColumns);

                t_rsColumns.close();
            }
        }
        catch  (SQLException sqlException)
        {
            logWarn(
                "Cannot retrieve the column names.",
                sqlException);

            throw sqlException;
        }

        return result;
    }

    /**
     * Extracts the column names from given result set.
     * @param resultSet the result set with the column information.
     * @param field the field.
     * @return the list of column names.
     * @throws SQLException if the database operation fails.
     */
    protected String[] extractColumnNames(ResultSet resultSet, Field field)
        throws  SQLException
    {
        String[] result = EMPTY_STRING_ARRAY;

        if  (field != null) 
        {
            result = extractColumnNames(resultSet, field.toSimplifiedString());
        }

        return result;
    }

    /**
     * Extracts the column names from given result set.
     * @param resultSet the result set with the column information.
     * @return the list of column names.
     * @throws SQLException if the database operation fails.
     */
    protected String[] extractColumnNames(ResultSet resultSet)
        throws  SQLException
    {
        return extractColumnNames(resultSet, "COLUMN_NAME");
    }

    /**
     * Extracts the column names from given result set.
     * @param resultSet the result set with the column information.
     * @param fieldName the field name.
     * @return the list of column names.
     * @throws SQLException if the database operation fails.
     */
    protected String[] extractColumnNames(ResultSet resultSet, String fieldName)
        throws  SQLException
    {
        String[] result = EMPTY_STRING_ARRAY;

        if  (resultSet != null) 
        {
            result = extractStringFields(resultSet, fieldName);
        }

        return result;
    }

    /**
     * Retrieves the column names from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param size the number of fields to extract.
     * @return the list of all column names.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if any other error occurs.
     */
    protected int[] getColumnTypes(
            DatabaseMetaData metaData,
            String           catalog,
            String           schema,
            String           tableName,
            int              size)
        throws  SQLException,
                QueryJException
    {
        int[] result = EMPTY_INT_ARRAY;

        try 
        {
            if  (metaData != null) 
            {
                ResultSet t_rsColumns =
                    metaData.getColumns(
                        catalog,
                        schema,
                        tableName,
                        null);

                result = extractColumnTypes(t_rsColumns, size);

                t_rsColumns.close();
            }
        }
        catch  (SQLException sqlException)
        {
            logWarn(
                "Cannot retrieve the column types.",
                sqlException);

            throw sqlException;
        }

        return result;
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param field the field.
     * @return the list of column types.
     * @throws SQLException if the database operation fails.
     */
    protected int[] extractColumnTypes(ResultSet resultSet, Field field)
        throws  SQLException
    {
        int[] result = EMPTY_INT_ARRAY;

        if  (field != null) 
        {
            result = extractColumnTypes(resultSet, field.toSimplifiedString());
        }

        return result;
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param size the number of fields to extract.
     * @return the list of column types.
     * @throws SQLException if the database operation fails.
     */
    protected int[] extractColumnTypes(ResultSet resultSet, int size)
        throws  SQLException
    {
        return extractColumnTypes(resultSet, "DATA_TYPE", size);
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param fieldName the field name.
     * @return the list of column types.
     * @throws SQLException if the database operation fails.
     */
    protected int[] extractColumnTypes(ResultSet resultSet, String fieldName)
        throws  SQLException
    {
        return extractColumnTypes(resultSet, fieldName, -1);
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param fieldName the field name.
     * @param size the number of fields to extract.
     * @return the list of column types.
     * @throws SQLException if the database operation fails.
     */
    protected int[] extractColumnTypes(ResultSet resultSet, String fieldName, int size)
        throws  SQLException
    {
        int[] result = EMPTY_INT_ARRAY;

        if  (resultSet != null) 
        {
            result = extractIntFields(resultSet, fieldName, size);
        }

        return result;
    }

    /**
     * Retrieves the procedure names.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @return the list of all procedure names.
     * @throws SQLException if the database operation fails.
     */
    protected String[] getProcedureNames(
            DatabaseMetaData metaData,
            String           catalog,
            String           schema)
        throws  SQLException,
                QueryJException
    {
        String[] result = EMPTY_STRING_ARRAY;

        ProcedureMetaData[] t_aProceduresMetaData =
            getProceduresMetaData(metaData, catalog, schema);

        if  (   (t_aProceduresMetaData != null)
             && (t_aProceduresMetaData.length > 0))
        {
            setProceduresMetaData(t_aProceduresMetaData);

            result = new String[t_aProceduresMetaData.length];

            for  (int t_iIndex = 0; t_iIndex < result.length; t_iIndex++) 
            {
                result[t_iIndex] = t_aProceduresMetaData[t_iIndex].getName();
            }
        }

        return result;
    }

    /**
     * Retrieves the procedures' meta data.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @return the list of all procedure metadata.
     * @throws SQLException if the database operation fails.
     */
    protected ProcedureMetaData[] getProceduresMetaData(
            DatabaseMetaData metaData,
            String           catalog,
            String           schema)
        throws  SQLException,
                QueryJException
    {
        ProcedureMetaData[] result = EMPTY_PROCEDURE_METADATA_ARRAY;

        if  (metaData != null)
        {
            ResultSet t_rsProcedures = null;

            try 
            {
                try 
                {
                    t_rsProcedures =
                        metaData.getProcedures(
                            catalog,
                            schema,
                            null);
                }
                catch  (SQLException sqlException)
                {
                    throw
                        new QueryJException(
                            "cannot.retrieve.database.procedure.names",
                            sqlException);
                }

                result = extractProceduresMetaData(t_rsProcedures);
            }
            catch  (SQLException sqlException)
            {
                logWarn(
                    "cannot.retrieve.database.procedure.names",
                    sqlException);
//                throw sqlException;
            }
            catch  (QueryJException queryjException)
            {
                logWarn(
                    "cannot.retrieve.database.procedure.names",
                    queryjException);
//                throw queryjException;
            }
            finally 
            {
                if  (t_rsProcedures != null)
                {
                    t_rsProcedures.close();
                }
            }
        }

        return result;
    }

    /**
     * Extracts the procedures' metadata from given result set.
     * @param resultSet the result set with the procedure information.
     * @return the list of procedure metadata.
     * @throws SQLException if the database operation fails.
     */
    protected ProcedureMetaData[] extractProceduresMetaData(ResultSet resultSet)
        throws  SQLException
    {
        ProcedureMetaData[] result = EMPTY_PROCEDURE_METADATA_ARRAY;

        if  (resultSet != null) 
        {
            List t_lProcedureList = new ArrayList();

            while  (resultSet.next()) 
            {
                t_lProcedureList.add(
                    new ProcedureMetaData(
                        resultSet.getString("PROCEDURE_NAME"),
                        (int) resultSet.getShort("PROCEDURE_TYPE"),
                        resultSet.getString("REMARKS")));
            }

            result = (ProcedureMetaData[]) t_lProcedureList.toArray(result);
        }

        return result;
    }

    /**
     * Retrieves the procedure parameter names from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param procedureName the procedure name.
     * @return the list of all procedure parameter names.
     * @throws SQLException if the database operation fails.
     */
    protected String[] getProcedureParameterNames(
            DatabaseMetaData metaData,
            String           catalog,
            String           schema,
            String           procedureName)
        throws  SQLException
    {
        String[] result = EMPTY_STRING_ARRAY;

        ProcedureParameterMetaData[] t_aProcedureParametersMetaData =
            getProcedureParametersMetaData(
                metaData, catalog, schema, procedureName);

        if  (   (t_aProcedureParametersMetaData != null)
             && (t_aProcedureParametersMetaData.length > 0))
        {
            //setProceduresMetaData(t_aProcedureParametersMetaData);

            result = new String[t_aProcedureParametersMetaData.length];

            for  (int t_iIndex = 0; t_iIndex < result.length; t_iIndex++) 
            {
                result[t_iIndex] =
                    t_aProcedureParametersMetaData[t_iIndex].getName();
            }
        }

        return result;
    }

    /**
     * Retrieves the procedure parameter names from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param procedureName the procedure name.
     * @return the list of all procedure parameter names.
     * @throws SQLException if the database operation fails.
     */
    protected ProcedureParameterMetaData[] getProcedureParametersMetaData(
            DatabaseMetaData metaData,
            String           catalog,
            String           schema,
            String           procedureName)
        throws  SQLException
    {
        ProcedureParameterMetaData[] result =
            EMPTY_PROCEDURE_PARAMETER_METADATA_ARRAY;

        try 
        {
            if  (metaData != null) 
            {
                ResultSet t_rsProcedureParameters =
                    metaData.getProcedureColumns(
                        catalog,
                        schema,
                        procedureName,
                        null);

                result = extractProcedureParametersMetaData(t_rsProcedureParameters);

                t_rsProcedureParameters.close();
            }
        }
        catch  (SQLException sqlException)
        {
            logWarn(
                "Cannot retrieve the procedure parameter names.",
                sqlException);

            throw sqlException;
        }

        return result;
    }

    /**
     * Extracts the procedure parameter metadata from given result set.
     * @param resultSet the result set with the procedure parameter information.
     * @return the list of procedure parameter metadata.
     * @throws SQLException if the database operation fails.
     */
    protected ProcedureParameterMetaData[] extractProcedureParametersMetaData(ResultSet resultSet)
        throws  SQLException
    {
        ProcedureParameterMetaData[] result = EMPTY_PROCEDURE_PARAMETER_METADATA_ARRAY;

        if  (resultSet != null) 
        {
            List t_lProcedureParameterList = new ArrayList();

            while  (resultSet.next()) 
            {
                t_lProcedureParameterList.add(
                    new ProcedureParameterMetaData(
                        resultSet.getString("COLUMN_NAME"),
                        (int) resultSet.getShort("COLUMN_TYPE"),
                        resultSet.getString("REMARKS"),
                        resultSet.getInt("DATA_TYPE"),
                        resultSet.getInt("LENGTH"),
                        (int) resultSet.getShort("NULLABLE")));
            }

            result =
                (ProcedureParameterMetaData[]) t_lProcedureParameterList.toArray(result);
        }

        return result;
    }

    /**
     * Extracts concrete field from given result set.
     * @param resultSet the result set with the table information.
     * @param field the field name.
     * @return the list of field values.
     * @throws SQLException if the database operation fails.
     */
    protected String[] extractStringFields(ResultSet resultSet, Field field)
        throws  SQLException
    {
        String[] result = EMPTY_STRING_ARRAY;

        if  (   (resultSet != null)
             && (field     != null))
        {
            result = extractStringFields(resultSet, field.toSimplifiedString());
        }

        return result;
    }

    /**
     * Extracts fields from given result set.
     * @param resultSet the result set with the table information.
     * @param field the field name.
     * @return the list of field values.
     * @throws SQLException if the database operation fails.
     */
    protected String[] extractStringFields(ResultSet resultSet, String field)
        throws  SQLException
    {
        String[] result = EMPTY_STRING_ARRAY;

        if  (   (resultSet != null)
             && (field     != null))
        {
            List t_lFieldList = new ArrayList();

            while  (resultSet.next()) 
            {
                t_lFieldList.add(resultSet.getString(field));
            }

            result = (String[]) t_lFieldList.toArray(result);
        }

        return result;
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param field the field.
     * @param size the number of fields to extract.
     * @return the list of column types.
     * @throws SQLException if the database operation fails.
     */
    protected int[] extractColumnTypes(ResultSet resultSet, Field field, int size)
        throws  SQLException
    {
        int[] result = EMPTY_INT_ARRAY;

        if  (field != null) 
        {
            result = extractColumnTypes(resultSet, field.toSimplifiedString(), size);
        }

        return result;
    }

    /**
     * Extracts the primary keys.
     * @param metaData the database metadata.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     */
    protected void extractPrimaryKeys(
            DatabaseMetaData metaData,
            String           catalog,
            String           schema)
        throws  SQLException,
                QueryJException
    {
        try 
        {
            if  (metaData != null) 
            {
                String[] t_astrTableNames = getTableNames();

                for  (int t_iTableIndex = 0;
                          t_iTableIndex < t_astrTableNames.length;
                          t_iTableIndex++)
                {
                    ResultSet t_rsPrimaryKeys =
                        metaData.getPrimaryKeys(
                            catalog,
                            schema,
                            t_astrTableNames[t_iTableIndex]);

                    while  (   (t_rsPrimaryKeys != null)
                            && (t_rsPrimaryKeys.next()))
                    {
                        addPrimaryKey(
                            t_astrTableNames[t_iTableIndex],
                            t_rsPrimaryKeys.getString("COLUMN_NAME"));
                    }

                    if  (t_rsPrimaryKeys != null)
                    {
                        t_rsPrimaryKeys.close();
                    }
                }
            }
        }
        catch  (SQLException sqlException)
        {
            logWarn(
                "Cannot retrieve the primary keys.",
                sqlException);

            throw sqlException;
        }
    }

    /**
     * Extracts the foreign keys.
     * @param metaData the database metadata.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     */
    protected void extractForeignKeys(
            DatabaseMetaData metaData,
            String           catalog,
            String           schema)
        throws  SQLException,
                QueryJException
    {
        try 
        {
            if  (metaData != null) 
            {
                String[] t_astrTableNames = getTableNames();

                for  (int t_iTableIndex = 0;
                          t_iTableIndex < t_astrTableNames.length;
                          t_iTableIndex++)
                {
                    ResultSet t_rsForeignKeys =
                        metaData.getImportedKeys(
                            catalog,
                            schema,
                            t_astrTableNames[t_iTableIndex]);

                    while  (   (t_rsForeignKeys != null)
                            && (t_rsForeignKeys.next()))
                    {
                        addForeignKey(
                            t_astrTableNames[t_iTableIndex],
                            t_rsForeignKeys.getString("PKCOLUMN_NAME"),
                            t_rsForeignKeys.getString("FKTABLE_NAME"),
                            t_rsForeignKeys.getString("FKCOLUMN_NAME"));
                    }

                    if  (t_rsForeignKeys != null)
                    {
                        t_rsForeignKeys.close();
                    }
                }
            }
        }
        catch  (SQLException sqlException)
        {
            logWarn(
                "Cannot retrieve the foreign keys.",
                sqlException);

            throw sqlException;
        }
    }

    /**
     * Extracts concrete integer fields from given result set.
     * @param resultSet the result set with the table information.
     * @param field the field name.
     * @param size the number of fields to extract.
     * @return the list of field values.
     * @throws SQLException if the database operation fails.
     */
    protected int[] extractIntFields(
            ResultSet resultSet,
            String    field)
        throws  SQLException
    {
        return extractIntFields(resultSet, field, -1);
    }

    /**
     * Extracts concrete integer fields from given result set.
     * @param resultSet the result set with the table information.
     * @param field the field name.
     * @param size the number of fields to extract.
     * @return the list of field values.
     * @throws SQLException if the database operation fails.
     */
    protected int[] extractIntFields(
            ResultSet resultSet,
            String    field,
            int       size)
        throws  SQLException
    {
        int[] result = EMPTY_INT_ARRAY;

        if  (   (resultSet != null)
             && (field     != null))
        {
            boolean t_bBounded = (size > 0);

            Collection t_cResult = null;

            if  (t_bBounded)
            {
                result = new int[size];
            }
            else
            {
                t_cResult = new ArrayList();
            }
            
            int t_iCounter = 0;

            int t_iCurrentRecord = -1;

            while  (resultSet.next())
            {
                t_iCurrentRecord = resultSet.getInt(field);

                if  (t_bBounded)
                {
                    result[t_iCounter] = t_iCurrentRecord;

                    if  (t_iCounter == size - 1)
                    {
                        break;
                    }

                    t_iCounter++;
                }
                else
                {
                    t_cResult.add(new Integer(t_iCurrentRecord));
                }
            }

            if  (!t_bBounded)
            {
                result = new int[t_cResult.size()];

                t_iCounter = 0;

                Iterator t_itResults = t_cResult.iterator();

                while  (   (t_itResults != null)
                        && (t_itResults.hasNext()))
                {
                    result[t_iCounter++] = ((Integer) t_itResults.next()).intValue();
                }
            }
        }

        return result;
    }

    /**
     * Logs a verbose message.
     * @param message the message to log.
     */
    protected void logVerbose(String message)
    {
        Project t_Project = getProject();

        if  (t_Project != null)
        {
            t_Project.log(
                getTask(),
                message,
                Project.MSG_VERBOSE);
        }
        else 
        {
            LogFactory.getLog(DatabaseMetaDataManager.class).info(
                message);
        }
    }

    /**
     * Logs a warning message.
     * @param message the message to log.
     * @param exception the exception
     */
    protected void logWarn(String message, Exception exception)
    {
        Project t_Project = getProject();

        if  (t_Project != null)
        {
            t_Project.log(
                getTask(),
                message + "(" + exception + ")",
                Project.MSG_WARN);
        }
        else 
        {
            LogFactory.getLog(DatabaseMetaDataManager.class).warn(
                message,
                exception);
        }
    }
}
