
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
     * An empty boolean array for performance issues.
     */
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];

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
     * The table comments.
     */
    private String[] m__astrTableComments;

    /**
     * The column names (as list) of each table.
     */
    private Map m__mColumnNames;

    /**
     * The column types (as list) of each table and column name.
     */
    private Map m__mColumnTypes;

    /**
     * The null flags (as list) of each table and column name.
     */
    private Map m__mAllowNulls;

    /**
     * The comments of each table.
     */
    private Map m__mTableComments;

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
        immutableSetColumnNames(t_UniqueMap);
        immutableSetColumnTypes(t_UniqueMap);
        immutableSetAllowNulls(t_UniqueMap);
        immutableSetProcedureParametersMetaData(t_UniqueMap);
        immutableSetPrimaryKeys(t_UniqueMap);
        immutableSetForeignKeys(t_UniqueMap);
        immutableSetExternallyManagedFields(t_UniqueMap);
        immutableSetTableComments(t_UniqueMap);
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
     * @param project the project, for logging purposes
     * @param task the task, for logging purposes.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    protected DatabaseMetaDataManager(
        final String[] tableNames,
        final String[] procedureNames,
        final boolean disableTableExtraction,
        final boolean lazyTableExtraction,
        final boolean disableProcedureExtraction,
        final boolean lazyProcedureExtraction,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final boolean retrieveMetaData,
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        this();
        immutableSetTableNames(tableNames);
        immutableSetProcedureNames(procedureNames);
        immutableSetDisableTableExtraction(disableTableExtraction);
        immutableSetLazyTableExtraction(lazyTableExtraction);
        immutableSetDisableProcedureExtraction(disableProcedureExtraction);
        immutableSetLazyProcedureExtraction(lazyProcedureExtraction);
        immutableSetMetaData(metaData);
        immutableSetCatalog(catalog);
        immutableSetSchema(schema);
        immutableSetProject(project);
        immutableSetTask(task);

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
                schema,
                project,
                task);
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
     * @param project the project, for logging purposes
     * @param task the task, for logging purposes.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    public DatabaseMetaDataManager(
        final String[] tableNames,
        final String[] procedureNames,
        final boolean disableTableExtraction,
        final boolean lazyTableExtraction,
        final boolean disableProcedureExtraction,
        final boolean lazyProcedureExtraction,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final Project project,
        final Task task)
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
            true,
            project,
            task);
    }

    /**
     * Creates a DatabaseMetaDataManager using given information.
     * @param tableNames explicitly specified table names.
     * @param procedureNames explicitly specified procedure names.
     * @param metaData the database meta data.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param project the project, for logging purposes
     * @param task the task, for logging purposes.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    protected DatabaseMetaDataManager(
        final String[] tableNames,
        final String[] procedureNames,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final Project project,
        final Task task)
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
            project,
            task);
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
     * @param project the project, for logging purposes
     * @param task the task, for logging purposes.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    protected DatabaseMetaDataManager(
        final String[] tableNames,
        final String[] procedureNames,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final boolean retrieveMetaData,
        final Project project,
        final Task task)
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
            retrieveMetaData,
            project,
            task);
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
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    private void retrieveMetaData(
        final String[] tableNames,
        final String[] procedureNames,
        final boolean disableTableExtraction,
        final boolean lazyTableExtraction,
        final boolean disableProcedureExtraction,
        final boolean lazyProcedureExtraction,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final Project project,
        final Task task)
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
                schema,
                project,
                task);

            extractPrimaryKeys(metaData, catalog, schema, project, task);
            extractForeignKeys(metaData, catalog, schema, project, task);
        }

        if  (   (!disableProcedureExtraction)
             && (!lazyProcedureExtraction))
        {
            extractProcedureMetaData(
                procedureNames,
                metaData,
                catalog,
                schema,
                project,
                task);
        }
    }

    /**
     * Specifies the meta data.
     * @param metaData the database meta data.
     */
    private void immutableSetMetaData(final DatabaseMetaData metaData)
    {
        m__MetaData = metaData;
    }

    /**
     * Specifies the meta data.
     * @param metaData the database meta data.
     */
    protected void setMetaData(final DatabaseMetaData metaData)
    {
        immutableSetMetaData(metaData);
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
    private void immutableSetCatalog(final String catalog)
    {
        m__strCatalog = catalog;
    }

    /**
     * Specifies the catalog.
     * @param catalog the database catalog.
     */
    protected void setCatalog(final String catalog)
    {
        immutableSetCatalog(catalog);
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
    private void immutableSetSchema(final String schema)
    {
        m__strSchema = schema;
    }

    /**
     * Specifies the schema.
     * @param schema the database schema.
     */
    protected void setSchema(final String schema)
    {
        immutableSetSchema(schema);
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
    private void immutableSetDisableTableExtraction(final boolean flag)
    {
        m__bDisableTableExtraction = flag;
    }

    /**
     * Specifies the whether the table extraction should be disabled.
     * @param flag such flag.
     */
    protected void setDisableTableExtraction(final boolean flag)
    {
        immutableSetDisableTableExtraction(flag);
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
    private void immutableSetLazyTableExtraction(final boolean flag)
    {
        m__bLazyTableExtraction = flag;
    }

    /**
     * Specifies the lazy table extraction flag.
     * @param flag such flag.
     */
    protected void setLazyTableExtraction(final boolean flag)
    {
        immutableSetLazyTableExtraction(flag);
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
    private void immutableSetDisableProcedureExtraction(final boolean flag)
    {
        m__bDisableProcedureExtraction = flag;
    }

    /**
     * Specifies the whether the procedure extraction should be disabled.
     * @param flag such flag.
     */
    protected void setDisableProcedureExtraction(final boolean flag)
    {
        immutableSetDisableProcedureExtraction(flag);
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
    private void immutableSetLazyProcedureExtraction(final boolean flag)
    {
        m__bLazyProcedureExtraction = flag;
    }

    /**
     * Specifies the lazy procedure extraction flag.
     * @param flag such flag.
     */
    protected void setLazyProcedureExtraction(final boolean flag)
    {
        immutableSetLazyProcedureExtraction(flag);
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
    private void immutableSetTableNames(final String[] names)
    {
        m__astrTableNames = names;
    }

    /**
     * Specifies the table names.
     * @param names such names.
     */
    protected void setTableNames(final String[] names)
    {
        immutableSetTableNames(names);
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
     * Specifies the table comments.
     * @param comments such comments.
     */
    private void immutableSetTableComments(final Map comments)
    {
        m__mTableComments = comments;
    }

    /**
     * Specifies the table comments.
     * @param comments such comments.
     */
    protected void setTableComments(final Map comments)
    {
        immutableSetTableComments(comments);
    }

    /**
     * Retrieves the table comments.
     * @return such comments.
     */
    public Map getTableComments()
    {
        return m__mTableComments;
    }

    /**
     * Adds the comments of given table.
     * @param tableName the table name.
     * @param tableComment the table comment.
     * @precondition tableName != null
     * @precondition tableComment != null
     */
    public void addTableComment(
        final String tableName,
        final String tableComment)
    {
        addTableComment(tableName, tableComment, getTableComments());
    }

    /**
     * Adds the comments of given table.
     * @param tableName the table name.
     * @param tableComment the table comment.
     * @param map the map.
     * @precondition tableName != null
     * @precondition tableComment != null
     * @precondition map != null
     */
    protected void addTableComment(
        final String tableName,
        final String tableComment,
        final Map map)
    {
        map.put(buildTableCommentKey(tableName), tableComment);
    }

    /**
     * Retrieves the table comments.
     * @param tableName the table name.
     * @return the table comment.
     * @precondition tableName != null
     */
    public String getTableComment(final String tableName)
    {
        return getTableComment(tableName, getTableComments());
    }

    /**
     * Retrieves the table comments.
     * @param tableName the table name.
     * @param tableComments the table comments.
     * @return the table comment.
     * @precondition tableName != null
     * @precondition tableComments != null
     */
    protected String getTableComment(
        final String tableName, final Map tableComments)
    {
        return (String) tableComments.get(buildTableCommentKey(tableName));
    }

    /**
     * Specifies the column names.
     * @param map the column names map.
     */
    private void immutableSetColumnNames(final Map map)
    {
        m__mColumnNames = map;
    }

    /**
     * Specifies the column names.
     * @param map the column names map.
     */
    protected void setColumnNames(final Map map)
    {
        immutableSetColumnNames(map);
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
     * @param columnNames the column names.
     * @precondition tableName != null
     * @precondition columnNames != null
     */
    public void addColumnNames(
        final String tableName,
        final String[] columnNames)
    {
        addColumnNames(tableName, columnNames, getColumnNames());
    }

    /**
     * Adds the column names of given table.
     * @param tableName the table name.
     * @param columnNames the column names.
     * @param map the map.
     * @precondition tableName != null
     * @precondition columnNames != null
     * @precondition map != null
     */
    protected void addColumnNames(
        final String tableName,
        final String[] columnNames,
        final Map map)
    {
        map.put(buildKey(tableName), columnNames);
    }

    /**
     * Retrieves the column names.
     * @param tableName the table name.
     * @return the column names.
     * @precondition tableName != null
     */
    public String[] getColumnNames(final String tableName)
    {
        return getColumnNames(tableName, getColumnNames());
    }

    /**
     * Retrieves the column names.
     * @param tableName the table name.
     * @param columnNames the column names.
     * @return the column names.
     * @precondition tableName != null
     * @precondition columnNames != null
     */
    protected String[] getColumnNames(
        final String tableName, final Map columnNames)
    {
        return (String[]) columnNames.get(buildKey(tableName));
    }

    /**
     * Specifies the column types.
     * @param map the column types map.
     */
    private void immutableSetColumnTypes(final Map map)
    {
        m__mColumnTypes = map;
    }

    /**
     * Specifies the column types.
     * @param map the column types map.
     */
    protected void setColumnTypes(final Map map)
    {
        immutableSetColumnTypes(map);
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
     * @precondition tableName != null
     * @precondition columnName != null
     */
    public int getColumnType(
        final String tableName, final String columnName)
    {
        return getColumnType(tableName, columnName, getColumnTypes());
    }

    /**
     * Retrieves the column type.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param columnTypes the column types.
     * @return the column type.
     * @see java.sql.Types
     * @precondition tableName != null
     * @precondition columnName != null
     * @precondition columnTypes != null
     */
    protected int getColumnType(
        final String tableName, final String columnName, final Map columnTypes)
    {
        int result = -1;

        Object t_Result = columnTypes.get(buildKey(tableName, columnName));

        if  (   (t_Result != null)
             && (t_Result instanceof Integer))
        {
            result = ((Integer) t_Result).intValue();
        }
        
        return result;
    }

    /**
     * Adds a column type.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param columnType the column type.
     * @precondition tableName != null
     * @precondition columnName != null
     * @precondition columnType != null
     */
    public void addColumnType(
        final String tableName,
        final String columnName,
        final int columnType)
    {
        addColumnType(tableName, columnName, columnType, getColumnTypes());
    }

    /**
     * Adds a column type.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param columnType the column type.
     * @param columnTypes the column types.
     * @precondition tableName != null
     * @precondition columnName != null
     * @precondition columnType != null
     * @precondition columnTypes != null
     */
    protected void addColumnType(
        final String tableName,
        final String columnName,
        final int columnType,
        final Map columnTypes)
    {
        columnTypes.put(
            buildKey(tableName, columnName),
            new Integer(columnType));
    }

    /**
     * Specifies the null flags.
     * @param map the flag map.
     */
    private void immutableSetAllowNulls(final Map map)
    {
        m__mAllowNulls = map;
    }

    /**
     * Specifies the allow nulls.
     * @param map the allow nulls map.
     */
    protected void setAllowNulls(final Map map)
    {
        immutableSetAllowNulls(map);
    }

    /**
     * Retrieves the allow nulls.
     * @return such map.
     */
    protected Map getAllowNulls()
    {
        return m__mAllowNulls;
    }

    /**
     * Retrieves the allow null.
     * @param tableName the table name.
     * @param allowName the allow name.
     * @return the allow null.
     * @see java.sql.Nulls
     * @precondition tableName != null
     * @precondition allowName != null
     */
    public boolean allowsNull(
        final String tableName, final String allowName)
    {
        return getAllowNull(tableName, allowName);
    }

    /**
     * Retrieves the allow null.
     * @param tableName the table name.
     * @param allowName the allow name.
     * @return the allow null.
     * @see java.sql.Nulls
     * @precondition tableName != null
     * @precondition allowName != null
     */
    public boolean getAllowNull(
        final String tableName, final String allowName)
    {
        return getAllowNull(tableName, allowName, getAllowNulls());
    }

    /**
     * Retrieves the allow null.
     * @param tableName the table name.
     * @param allowName the allow name.
     * @param allowNulls the allow nulls.
     * @return the allow null.
     * @see java.sql.Nulls
     * @precondition tableName != null
     * @precondition allowName != null
     * @precondition allowNulls != null
     */
    protected boolean getAllowNull(
        final String tableName, final String allowName, final Map allowNulls)
    {
        boolean result = true;

        Object t_Result = allowNulls.get(buildAllowNullKey(tableName, allowName));

        if  (   (t_Result != null)
             && (t_Result instanceof Boolean))
        {
            result = ((Boolean) t_Result).booleanValue();
        }
        
        return result;
    }

    /**
     * Adds a null flag.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param flag the flag.
     * @precondition tableName != null
     * @precondition columnName != null
     */
    public void addAllowNull(
        final String tableName,
        final String columnName,
        final boolean flag)
    {
        addAllowNull(tableName, columnName, flag, getAllowNulls());
    }

    /**
     * Adds a null flag.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param flag the flag.
     * @param flags the flags.
     * @precondition tableName != null
     * @precondition columnName != null
     * @precondition flags != null
     */
    protected void addAllowNull(
        final String tableName,
        final String columnName,
        final boolean flag,
        final Map flags)
    {
        flags.put(
            buildAllowNullKey(tableName, columnName),
            (flag ? Boolean.TRUE : Boolean.FALSE));
    }

    /**
     * Specifies the primary keys
     * @param map the primary keys map.
     */
    private void immutableSetPrimaryKeys(final Map map)
    {
        m__mPrimaryKeys = map;
    }

    /**
     * Specifies the primary keys.
     * @param map the primary keys map.
     */
    protected void setPrimaryKeys(final Map map)
    {
        immutableSetPrimaryKeys(map);
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
     * @precondition tableName != null
     * @precondition columnName != null
     */
    public void addPrimaryKey(
        final String tableName,
        final String columnName)
    {
        addPrimaryKey(tableName, columnName, getPrimaryKeys());
    }

    /**
     * Adds a primary key.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param primaryKeys the primary keys.
     * @precondition tableName != null
     * @precondition columnName != null
     * @precondition primaryKeys != null
     */
    protected void addPrimaryKey(
        final String tableName,
        final String columnName,
        final Map primaryKeys)
    {
        logVerbose(
            "Adding pk:" + tableName + "." + columnName);

        primaryKeys.put(
            buildPkKey(tableName, columnName),
            columnName);

        Collection t_cPks = (Collection) primaryKeys.get(buildPkKey(tableName));

        if  (t_cPks == null)
        {
            t_cPks = new ArrayList();
            primaryKeys.put(buildPkKey(tableName), t_cPks);
        }

        t_cPks.add(columnName);
    }

    /**
     * Retrieves the primary keys.
     * @param tableName the table name.
     * @return the primary keys.
     * @precondition tableName != null
     */
    public String[] getPrimaryKeys(final String tableName)
    {
        return getPrimaryKeys(tableName, getPrimaryKeys());
    }

    /**
     * Retrieves the primary keys.
     * @param tableName the table name.
     * @param primaryKeys the primary keys.
     * @return the primary keys.
     * @precondition tableName != null
     * @precondition primaryKeys != null
     */
    protected String[] getPrimaryKeys(final String tableName, final Map primaryKeys)
    {
        String[] result = EMPTY_STRING_ARRAY;

        Collection t_cPks = (Collection) primaryKeys.get(buildPkKey(tableName));

        if  (t_cPks != null)
        {
            result = (String[]) t_cPks.toArray(result);
        }
        
        return result;
    }

    /**
     * Checks whether given field is a primary key or not.
     * @param tableName the table name.
     * @param fieldName the field name.
     * @return <code>true</code> if such field identifies a concrete row.
     * @precondition tableName != null
     * @precondition fieldName != null
     */
    public boolean isPrimaryKey(final String tableName, final String fieldName)
    {
        return isPrimaryKey(fieldName, getPrimaryKeys(tableName));
    }

    /**
     * Checks whether given field is a primary key or not.
     * @param fieldName the field name.
     * @param primaryKeys the primary keys.
     * @return <code>true</code> if such field identifies a concrete row.
     * @precondition fieldName != null
     */
    protected boolean isPrimaryKey(final String fieldName, final String[] primaryKeys)
    {
        boolean result = false;

        if  (primaryKeys != null)
        {
            for  (int t_iPkIndex = 0;
                      t_iPkIndex < primaryKeys.length;
                      t_iPkIndex++)
            {
                if  (fieldName.equals(primaryKeys[t_iPkIndex]))
                {
                    result = true;

                    break;
                }
            }
        }

        return result;
    }

    /**
     * Specifies the foreign keys.
     * @param map the foreign keys map.
     */
    private void immutableSetForeignKeys(final Map map)
    {
        m__mForeignKeys = map;
    }

    /**
     * Specifies the foreign keys.
     * @param map the foreign keys map.
     */
    protected void setForeignKeys(final Map map)
    {
        immutableSetForeignKeys(map);
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
     * @precondition tableName != null
     * @precondition columnName != null
     * @precondition refRableName != null
     * @precondition refColumnName != null
     */
    public void addForeignKey(
        final String tableName,
        final String columnName,
        final String refTableName,
        final String refColumnName)
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
            Object t_ReferingTablesKey = buildReferingTablesKey();

            Collection t_cReferingTables =
                (Collection) t_mForeignKeys.get(t_ReferingTablesKey);

            if  (t_cReferingTables == null)
            {
                t_cReferingTables = new ArrayList();
                t_mForeignKeys.put(t_ReferingTablesKey, t_cReferingTables);
            }

            if  (!t_cReferingTables.contains(tableName))
            {
                t_cReferingTables.add(tableName);
            }

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

            t_mForeignKeys.put(
                buildRefTableKey(tableName, columnName),
                refTableName);

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
     * Retrieves the tables refering to given table's.
     * @param tableName the table name.
     * @return such tables.
     * @precondition tableName != null
     */
    public String[] getReferingTables(final String tableName)
    {
        Collection t_cResult = new ArrayList();

        Map t_mForeignKeys = getForeignKeys();

        if  (t_mForeignKeys != null) 
        {
            Collection t_cReferingTables =
                (Collection) t_mForeignKeys.get(buildReferingTablesKey());

            if  (t_cReferingTables != null)
            {
                Iterator t_itReferingTables = t_cReferingTables.iterator();

                if  (t_itReferingTables != null)
                {
                    String[] t_astrReferredTables = null;

                    String t_strReferingTable = null;

                    while  (t_itReferingTables.hasNext())
                    {
                        t_strReferingTable = (String) t_itReferingTables.next();

                        t_astrReferredTables =
                            getReferredTables(t_strReferingTable);

                        if  (t_astrReferredTables != null)
                        {
                            String t_strCurrentTable = null;

                            for  (int t_iIndex = 0;
                                      t_iIndex < t_astrReferredTables.length;
                                      t_iIndex++)
                            {
                                t_strCurrentTable = t_astrReferredTables[t_iIndex];

                                if  (   (t_strCurrentTable != null)
                                     && (!t_cResult.contains(t_strReferingTable))
                                     && (tableName.equalsIgnoreCase(
                                             t_strCurrentTable)))
                                {
                                    t_cResult.add(t_strReferingTable);
                                }
                            }
                        }
                    }
                }
            }
        }

        return (String[]) t_cResult.toArray(EMPTY_STRING_ARRAY);
    }

    /**
     * Retrieves the foreign keys of given table.
     * @param tableName the table name.
     * @return its foreign keys.
     * @precondition tableName != null
     */
    public String[] getForeignKeys(final String tableName)
    {
        String[] result = EMPTY_STRING_ARRAY;

        String[] t_astrReferredTables = getReferredTables(tableName);
        
        Collection t_cResult = null;

        for  (int t_iIndex = 0;
                  t_iIndex < t_astrReferredTables.length;
                  t_iIndex++)
        {
            if  (t_cResult == null)
            {
                t_cResult = new ArrayList();
            }

            t_cResult.add(
                getForeignKey(tableName, t_astrReferredTables[t_iIndex]));
        }

        if  (t_cResult != null)
        {
            result = (String[]) t_cResult.toArray(result);
        }

        return result;
    }

    /**
     * Checks whether given table contains foreign keys.
     * @param tableName the table name.
     * @return <code>true</code> in such case.
     * @precondition tableName != null
     */
    public boolean containsForeignKeys(final String tableName)
    {
        boolean result = false;

        String[] t_astrForeignKeys = getForeignKeys(tableName);

        result =
            (   (t_astrForeignKeys != null)
             && (t_astrForeignKeys.length > 0));

        return result;
    }

    /**
     * Retrieves the tables referred by given table's foreign keys.
     * @param tableName the table name.
     * @return such tables.
     * @precondition tableName != null
     */
    public String[] getReferredTables(final String tableName)
    {
        String[] result = EMPTY_STRING_ARRAY;

        Map t_mForeignKeys = getForeignKeys();

        if  (t_mForeignKeys != null) 
        {
            Collection t_Result =
                (Collection) t_mForeignKeys.get(buildFkKey(tableName));

            if  (t_Result != null)
            {
                result = (String[]) t_Result.toArray(EMPTY_STRING_ARRAY);
            }
        }
        
        return result;
    }

    /**
     * Retrieves the field of given table that points to a field on the referred one.
     * @param tableName the table name.
     * @param refTableName the referred table name.
     * @return such field.
     * @precondition tableName != null
     * @precondition refTableName != null
     */
    public String getForeignKey(final String tableName, String refTableName)
    {
        String result = null;

        Map t_mForeignKeys = getForeignKeys();

        if  (t_mForeignKeys != null) 
        {
            result =
                (String)
                    t_mForeignKeys.get(buildFkKey(tableName, refTableName));
        }
        
        return result;
    }

    /**
     * Retrieves the table referred by given foreign key.
     * @param tableName the table name.
     * @param foreignKey the foreign key.
     * @return the referred table name.
     * @precondition tableName != null
     * @precondition foreignKey != null
     */
    public String getReferredTable(final String tableName, final String foreignKey)
    {
        String result = null;

        Map t_mForeignKeys = getForeignKeys();

        if  (t_mForeignKeys != null) 
        {
            result =
                (String)
                    t_mForeignKeys.get(buildRefTableKey(tableName, foreignKey));
        }

        return result;
    }

    /**
     * Retrieves the field of referred table pointed by a field on the original one.
     * @param tableName the table name.
     * @param refTableName the referred table name.
     * @return such field.
     * @precondition tableName != null
     * @precondition refTableName != null
     */
    public String getReferredKey(final String tableName, String refTableName)
    {
        String result = null;

        Map t_mForeignKeys = getForeignKeys();

        if  (t_mForeignKeys != null) 
        {
            result =
                (String)
                    t_mForeignKeys.get(buildRefFkKey(tableName, refTableName));
        }
        
        return result;
    }

    /**
     * Specifies the externally managed fields.
     * @param map the externally managed fields map.
     */
    private void immutableSetExternallyManagedFields(Map map)
    {
        m__mExternallyManagedFields = map;
    }

    /**
     * Specifies the externally managed fields.
     * @param map the externally managed fields map.
     */
    protected void setExternallyManagedFields(Map map)
    {
        immutableSetExternallyManagedFields(map);
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
    public String[] getExternallyManagedFields(final String tableName)
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
    public boolean isManagedExternally(final String tableName, String fieldName)
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
    public String getKeyword(final String tableName, String fieldName)
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
    private void immutableSetProcedureNames(String[] names)
    {
        m__astrProcedureNames = names;
    }

    /**
     * Specifies the procedure names.
     * @param names such names.
     */
    protected void setProcedureNames(String[] names)
    {
        immutableSetProcedureNames(names);
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
    private void immutableSetProceduresMetaData(ProcedureMetaData[] proceduresMetaData)
    {
        m__aProceduresMetaData = proceduresMetaData;
    }

    /**
     * Specifies the procedures metadata.
     * @param proceduresMetaData such metadata.
     */
    protected void setProceduresMetaData(ProcedureMetaData[] proceduresMetaData)
    {
        immutableSetProceduresMetaData(proceduresMetaData);
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
    private void immutableSetProcedureParametersMetaData(Map map)
    {
        m__mProcedureParametersMetaData = map;
    }

    /**
     * Specifies the procedure parameter metadata.
     * @param map the parameter metadata map.
     */
    protected void setProcedureParametersMetaData(Map map)
    {
        immutableSetProcedureParametersMetaData(map);
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
        final String procedureName)
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
    private void immutableSetTask(final Task task)
    {
        m__Task = task;
    }

    /**
     * Specifies the task.
     * @param task the task.
     */
    protected void setTask(final Task task)
    {
        immutableSetTask(task);
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
    private void immutableSetProject(final Project project)
    {
        m__Project = project;
    }

    /**
     * Specifies the project.
     * @param project the project.
     */
    protected void setProject(final Project project)
    {
        immutableSetProject(project);
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
    protected Object buildKey(final Object key)
    {
        return ((key == null) ? "null" : key);
    }

    /**
     * Builds a key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    protected Object buildKey(final Object firstKey, final Object secondKey)
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
    protected Object buildPkKey(
        final Object firstKey, final Object secondKey)
    {
        return buildPkKey(firstKey) + ".,.," + secondKey;
    }

    /**
     * Builds a pk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    protected Object buildPkKey(
        final Object firstKey, final Object secondKey, final Object thirdKey)
    {
        return buildPkKey(firstKey, secondKey) + ";;" + thirdKey;
    }

    /**
     * Builds a fk key using given object.
     * @param firstKey the first object key.
     * @return the map key.
     */
    protected Object buildFkKey(final Object firstKey)
    {
        return "[fk]!!" + buildKey(firstKey);
    }

    /**
     * Builds a fk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    protected Object buildFkKey(
        final Object firstKey, final Object secondKey)
    {
        return buildFkKey(firstKey) + "++" + secondKey;
    }

    /**
     * Builds a fk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    protected Object buildFkKey(
        final Object firstKey, final Object secondKey, final Object thirdKey)
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
        final Object firstKey,
        final Object secondKey,
        final Object thirdKey,
        final Object fourthKey)
    {
        return buildFkKey(firstKey, secondKey, thirdKey) + ".;.;" + fourthKey;
    }

    /**
     * Builds a ref fk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    protected Object buildRefFkKey(final Object firstKey, final Object secondKey)
    {
        return "\\ref//" + buildFkKey(firstKey, secondKey);
    }

    /**
     * Builds a externally-managed field key using given object.
     * @param firstKey the first object key.
     * @return the map key.
     */
    protected Object buildExternallyManagedFieldKey(final Object firstKey)
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
        final Object firstKey, final Object secondKey)
    {
        return buildExternallyManagedFieldKey(firstKey) + ".,.," + secondKey;
    }

    /**
     * Builds a externally-managed field value using given object.
     * @param value the object.
     * @return the map value.
     */
    protected Object buildExternallyManagedFieldValue(final Object value)
    {
        Object result = "";

        if  (value != null)
        {
            result = value;
        }

        return result;
    }

    /**
     * Builds a allow-null key using given instances.
     * @param firstKey the first object key.
     * @return the map key.
     */
    protected Object buildAllowNullKey(final Object first, final Object second)
    {
        return "[allow-nulls]!!" + buildKey(first) + buildKey(second);
    }

    /**
     * Builds a table comment key using given object.
     * @param tableName the table name.
     * @return the map key.
     */
    protected Object buildTableCommentKey(final Object tableName)
    {
        return "[table-comment]!!" + buildKey(tableName);
    }

    /**
     * Retrieves the desired metadata.
     * @param tableNames optionally specified table names.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    private void extractTableMetaData(
        final String[] tableNames,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        String[] t_astrTableNames = tableNames;

        String t_strTableNames = "";

        if  (t_astrTableNames == null) 
        {
            t_astrTableNames =
                getTableNames(metaData, catalog, schema, project, task);
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
                String t_strTableComment =
                    getTableComment(
                        metaData,
                        catalog,
                        schema,
                        t_astrTableNames[t_iIndex],
                        project,
                        task);

                addTableComment(t_astrTableNames[t_iIndex], t_strTableComment);

                String[] t_astrColumnNames =
                    getColumnNames(
                        metaData,
                        catalog,
                        schema,
                        t_astrTableNames[t_iIndex],
                        project,
                        task);

                addColumnNames(t_astrTableNames[t_iIndex], t_astrColumnNames);

                if  (t_astrColumnNames != null) 
                {
                    int[] t_aiColumnTypes =
                        getColumnTypes(
                            metaData,
                            catalog,
                            schema,
                            t_astrTableNames[t_iIndex],
                            t_astrColumnNames.length,
                            project,
                            task);

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

                    boolean[] t_abAllowNull =
                        getAllowNulls(
                            metaData,
                            catalog,
                            schema,
                            t_astrTableNames[t_iIndex],
                            t_astrColumnNames.length,
                            project,
                            task);

                    if  (t_abAllowNull != null)
                    {
                        for  (int t_iColumnIndex = 0;
                                  t_iColumnIndex < t_aiColumnTypes.length;
                                  t_iColumnIndex++)
                        {
                            addAllowNull(
                                t_astrTableNames[t_iIndex],
                                t_astrColumnNames[t_iColumnIndex],
                                t_abAllowNull[t_iColumnIndex]);
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
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    private void extractProcedureMetaData(
        final String[] procedureNames,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final Project project,
        final Task task)
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
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the list of all table names.
     * @throws SQLException if the database operation fails.
     */
    protected String[] getTableNames(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final Project project,
        final Task task)
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
                catch  (final SQLException sqlException)
                {
                    throw
                        new QueryJException(
                            "cannot.retrieve.database.table.names",
                            sqlException);
                }

                result = extractTableNames(t_rsTables);
            }
            catch  (final SQLException sqlException)
            {
                throw sqlException;
            }
            catch  (final QueryJException queryjException)
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
     * @precondition resultSet != null
     */
    protected String[] extractTableNames(final ResultSet resultSet)
        throws  SQLException
    {
        return extractStringFields(resultSet, "TABLE_NAME");
    }

    /**
     * Retrieves the column names from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the list of all column names.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if the any other error occurs.
     */
    protected String[] getColumnNames(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String tableName,
        final Project project,
        final Task task)
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
        catch  (final SQLException sqlException)
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
     * @precondition resultSet != null
     * @precondition field != null
     */
    protected String[] extractColumnNames(
        final ResultSet resultSet, final Field field)
      throws  SQLException
    {
        return extractColumnNames(resultSet, field.toSimplifiedString());
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
     * @precondition resultSet != null
     */
    protected String[] extractColumnNames(
        final ResultSet resultSet, final String fieldName)
      throws  SQLException
    {
        return extractStringFields(resultSet, fieldName);
    }

    /**
     * Retrieves the column types from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param size the number of fields to extract.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the list of all column names.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if any other error occurs.
     */
    protected int[] getColumnTypes(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String tableName,
        final int size,
        final Project project,
        final Task task)
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
     * @precondition resultSet != null
     * @precondition field != null
     */
    protected int[] extractColumnTypes(
        final ResultSet resultSet, final Field field)
      throws  SQLException
    {
        return extractColumnTypes(resultSet, field.toSimplifiedString());
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param size the number of fields to extract.
     * @return the list of column types.
     * @throws SQLException if the database operation fails.
     */
    protected int[] extractColumnTypes(
        final ResultSet resultSet, final int size)
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
    protected int[] extractColumnTypes(
        final ResultSet resultSet, final String fieldName)
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
     * @precondition resultSet != null
     */
    protected int[] extractColumnTypes(
        final ResultSet resultSet, final String fieldName, final int size)
      throws  SQLException
    {
        return extractIntFields(resultSet, fieldName, size);
    }

    /**
     * Retrieves the null flag from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param size the number of fields to extract.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the list of all column names.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if any other error occurs.
     */
    protected boolean[] getAllowNulls(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String tableName,
        final int size,
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        boolean[] result = EMPTY_BOOLEAN_ARRAY;

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

                result = extractAllowNull(t_rsColumns, size);

                t_rsColumns.close();
            }
        }
        catch  (SQLException sqlException)
        {
            logWarn(
                "Cannot retrieve the null flag.",
                sqlException);

            throw sqlException;
        }

        return result;
    }

    /**
     * Extracts the null flag from given result set.
     * @param resultSet the result set with the column information.
     * @param field the field.
     * @return the list of null flag.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     * @precondition field != null
     */
    protected boolean[] extractAllowNull(
        final ResultSet resultSet, final Field field)
      throws  SQLException
    {
        return extractAllowNull(resultSet, field.toSimplifiedString());
    }

    /**
     * Extracts the null flag from given result set.
     * @param resultSet the result set with the column information.
     * @param size the number of fields to extract.
     * @return the list of null flags.
     * @throws SQLException if the database operation fails.
     */
    protected boolean[] extractAllowNull(
        final ResultSet resultSet, final int size)
      throws  SQLException
    {
        return extractAllowNull(resultSet, "NULLABLE", size);
    }

    /**
     * Extracts the null flag from given result set.
     * @param resultSet the result set with the column information.
     * @param fieldName the field name.
     * @return the list of null flags.
     * @throws SQLException if the database operation fails.
     */
    protected boolean[] extractAllowNull(
        final ResultSet resultSet, final String fieldName)
      throws  SQLException
    {
        return extractAllowNull(resultSet, fieldName, -1);
    }

    /**
     * Extracts the null flag from given result set.
     * @param resultSet the result set with the column information.
     * @param fieldName the field name.
     * @param size the number of fields to extract.
     * @return the list of null flags.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected boolean[] extractAllowNull(
        final ResultSet resultSet, final String fieldName, final int size)
      throws  SQLException
    {
        boolean[] result =  EMPTY_BOOLEAN_ARRAY;

        int[] t_iFlags = extractIntFields(resultSet, fieldName, size);

        if  (t_iFlags != null)
        {
            result = new boolean[t_iFlags.length];

            for  (int t_iIndex = 0; t_iIndex < result.length; t_iIndex++)
            {
                switch (t_iFlags[t_iIndex])
                {
                    case  DatabaseMetaData.columnNoNulls:
                        result[t_iIndex] = false;
                        break;
                    case  DatabaseMetaData.columnNullable:
                        result[t_iIndex] = true;
                        break;
                    case  DatabaseMetaData.columnNullableUnknown:
                    default:
                        result[t_iIndex] = true;
                        break;

                }
            }
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
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema)
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
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema)
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
                catch  (final SQLException sqlException)
                {
                    throw
                        new QueryJException(
                            "cannot.retrieve.database.procedure.names",
                            sqlException);
                }

                result = extractProceduresMetaData(t_rsProcedures);
            }
            catch  (final SQLException sqlException)
            {
                logWarn(
                    "cannot.retrieve.database.procedure.names",
                    sqlException);
//                throw sqlException;
            }
            catch  (final QueryJException queryjException)
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
     * @precondition resultSet != null
     */
    protected ProcedureMetaData[] extractProceduresMetaData(
        final ResultSet resultSet)
      throws  SQLException
    {
        ProcedureMetaData[] result = EMPTY_PROCEDURE_METADATA_ARRAY;

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
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String procedureName)
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
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String procedureName)
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
        catch  (final SQLException sqlException)
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
     * @precondition resultSet != null
     */
    protected ProcedureParameterMetaData[] extractProcedureParametersMetaData(
        final ResultSet resultSet)
      throws  SQLException
    {
        ProcedureParameterMetaData[] result =
            EMPTY_PROCEDURE_PARAMETER_METADATA_ARRAY;

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
            (ProcedureParameterMetaData[])
                t_lProcedureParameterList.toArray(result);

        return result;
    }

    /**
     * Extracts concrete field from given result set.
     * @param resultSet the result set with the table information.
     * @param field the field name.
     * @return the list of field values.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     * @precondition field != null
     */
    protected String[] extractStringFields(
        final ResultSet resultSet, final Field field)
        throws  SQLException
    {
        return extractStringFields(resultSet, field.toSimplifiedString());
    }

    /**
     * Extracts fields from given result set.
     * @param resultSet the result set with the table information.
     * @param field the field name.
     * @return the list of field values.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     * @precondition field != null
     */
    protected String[] extractStringFields(
        final ResultSet resultSet, final String field)
      throws  SQLException
    {
        Collection t_cResult = new ArrayList();

        while  (resultSet.next()) 
        {
            t_cResult.add(resultSet.getString(field));
        }

        return (String[]) t_cResult.toArray(EMPTY_STRING_ARRAY);
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param field the field.
     * @param size the number of fields to extract.
     * @return the list of column types.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected int[] extractColumnTypes(
        final ResultSet resultSet, final Field field, final int size)
      throws  SQLException
    {
        return extractColumnTypes(resultSet, field.toSimplifiedString(), size);
    }

    /**
     * Extracts the primary keys.
     * @param metaData the database metadata.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     * @precondition metaData != null
     */
    protected void extractPrimaryKeys(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        try 
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
        catch  (final SQLException sqlException)
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
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     * @precondition metaData != null
     */
    protected void extractForeignKeys(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        try 
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
        catch  (final SQLException sqlException)
        {
            logWarn(
                "Cannot retrieve the foreign keys.",
                sqlException);

            throw sqlException;
        }
    }

    /**
     * Retrieves the table comments.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the list of all table names.
     * @throws SQLException if the database operation fails.
     * @precondition metaData != null
     */
    protected String getTableComment(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String tableName,
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        String result = "";

        ResultSet t_rsTables = null;

        try 
        {
            try 
            {
                t_rsTables =
                    metaData.getTables(
                        catalog,
                        schema,
                        tableName,
                        new String[]{ "TABLE" });
            }
            catch  (final SQLException sqlException)
            {
                throw
                    new QueryJException(
                        "cannot.retrieve.database.table.names",
                        sqlException);
            }

            result = extractTableComment(t_rsTables);
        }
        catch  (final SQLException sqlException)
        {
            throw sqlException;
        }
        catch  (final QueryJException queryjException)
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

        return result;
    }

    /**
     * Extracts the table comment from given result set.
     * @param resultSet the result set with the table information.
     * @return the list of column names.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected String extractTableComment(final ResultSet resultSet)
        throws  SQLException
    {
        return resultSet.getString("REMARKS");
    }

    /**
     * Extracts concrete integer fields from given result set.
     * @param resultSet the result set with the table information.
     * @param field the field name.
     * @return the list of field values.
     * @throws SQLException if the database operation fails.
     */
    protected int[] extractIntFields(
        final ResultSet resultSet, final String field)
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
     * @precondition resultSet != null
     * @precondition field != null
     */
    protected int[] extractIntFields(
        final ResultSet resultSet,
        final String field,
        final int size)
        throws  SQLException
    {
        int[] result = EMPTY_INT_ARRAY;

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

        return result;
    }

    /**
     * Builds a key to store the referring tables.
     * @return such key.
     */
    protected final String buildReferingTablesKey()
    {
        return ".|_|referring_|_tables|_|.,";
    }

    /**
     * Builds a key to store given referred table.
     * @param tableName the table name.
     * @param foreignKey the foreign key.
     * @return such key
     * @precondition tableName != null
     * @precondition foreignKey != null.
     */
    protected final String buildRefTableKey(
        final String tableName, final String foreignKey)
    {
        return ".|_|referred|_table|_|.," + tableName + "$$" + foreignKey;
    }

    /**
     * Logs a verbose message.
     * @param message the message to log.
     * @precondition message != null
     */
    protected void logVerbose(final String message)
    {
        logVerbose(message, getProject(), getTask());
    }

    /**
     * Logs a verbose message.
     * @param message the message to log.
     * @param project the project.
     * @param task the task.
     * @precondition message != null
     * @precondition project != null
     * @precondition task != nul
     */
    protected void logVerbose(
        final String message, final Project project, final Task task)
    {
        project.log(
            task,
            message,
            Project.MSG_VERBOSE);
    }

    /**
     * Logs a warning message.
     * @param message the message to log.
     * @param exception the exception
     * @precondition message != null
     * @precondition exception != null
     */
    protected void logWarn(final String message, final Exception exception)
    {
        logWarn(message, exception, getProject(), getTask());
    }

    /**
     * Logs a warning message.
     * @param message the message to log.
     * @param exception the exception
     * @param project the project.
     * @param task the task.
     * @precondition message != null
     * @precondition exception != null
     * @precondition project != null
     * @precondition task != null
     */
    protected void logWarn(
        final String message,
        final Exception exception,
        final Project project,
        final Task task)
    {
        project.log(
            task,
            message + "(" + exception + ")",
            Project.MSG_WARN);
    }
}
