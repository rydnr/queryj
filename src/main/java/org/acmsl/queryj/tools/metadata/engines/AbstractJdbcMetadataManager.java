/*
                        QueryJ

    Copyright (C) 2001-2007  Jose San Leandro Armendariz
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
 * Filename: AbstractJdbcMetadataManager.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Manages the information provided by database metadata.
 *
 */
package org.acmsl.queryj.tools.metadata.engines;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.Field;
import org.acmsl.queryj.tools.metadata.MetadataExtractionListener;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.ProcedureMetadata;
import org.acmsl.queryj.tools.metadata.ProcedureParameterMetadata;
import org.acmsl.queryj.tools.templates.MetaLanguageUtils;
import org.acmsl.queryj.QueryJException;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Manages the information provided by database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public abstract class AbstractJdbcMetadataManager
    implements MetadataManager
{
    /**
     * An empty String array.
     */
    protected final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * An empty int array.
     */
    protected final int[] EMPTY_INT_ARRAY = new int[0];

    /**
     * An empty integer array.
     */
    protected final Integer[] EMPTY_INTEGER_ARRAY = new Integer[0];

    /**
     * An empty boolean array.
     */
    protected final boolean[] EMPTY_BOOL_ARRAY = new boolean[0];

    /**
     * An empty boolean array.
     */
    protected final Boolean[] EMPTY_BOOLEAN_ARRAY = new Boolean[0];

    /**
     * An empty array of String arrays.
     */
    protected final String[][] EMPTY_ARRAY_OF_STRING_ARRAYS = new String[0][0];

    /**
     * An empty array of <code>ProcedureMetadata</code> instances.
     */
    protected final ProcedureMetadata[] EMPTY_PROCEDURE_METADATA_ARRAY =
        new ProcedureMetadata[0];
    
    /**
     * An empty array of <code>ProcedureParameterMetadata</code> instances.
     */
    protected final ProcedureParameterMetadata[]
        EMPTY_PROCEDURE_PARAMETER_METADATA_ARRAY =
            new ProcedureParameterMetadata[0];
    
    /**
     * The database metadata.
     */
    private DatabaseMetaData m__MetaData;

    /**
     * The metadata extraction listener.
     */
    private MetadataExtractionListener m__MetadataExtractionListener;

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
     * The null flags (as list) of each table and column name.
     */
    private Map m__mAllowNulls;

    /**
     * The comments of each table.
     */
    private Map m__mTableComments;

    /**
     * The comments of each column.
     */
    private Map m__mColumnComments;

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
    private ProcedureMetadata[] m__aProceduresMetadata;

    /**
     * The procedure parameters meta data (as list).
     */
    private Map m__mProcedureParametersMetadata;

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
     * Creates an empty <code>AbstractJdbcMetadataManager</code> instance.
     * @param metadataExtractionListener the
     * <code>MetadataExtractionListener</code> instance.
     */
    protected AbstractJdbcMetadataManager(
        final MetadataExtractionListener metadataExtractionListener)
    {
        immutableSetMetadataExtractionListener(metadataExtractionListener);
        Map t_UniqueMap = new HashMap();
        immutableSetColumnNames(t_UniqueMap);
        immutableSetColumnTypes(t_UniqueMap);
        immutableSetAllowNulls(t_UniqueMap);
        immutableSetProcedureParametersMetadata(t_UniqueMap);
        immutableSetPrimaryKeys(t_UniqueMap);
        immutableSetForeignKeys(t_UniqueMap);
        immutableSetExternallyManagedFields(t_UniqueMap);
        immutableSetTableComments(t_UniqueMap);
        immutableSetColumnComments(t_UniqueMap);
    }

    /**
     * Creates an <code>AbstractJdbcMetadataManager</code> using given
     * information.
     * @param metadataExtractionListener the
     * <code>MetadataExtractionListener</code> instance.
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
     * @precondition tableNames != null
     * @precondition procedureNames != null
     * @precondition metaData != null
     */
    protected AbstractJdbcMetadataManager(
        final MetadataExtractionListener metadataExtractionListener,
        final String[] tableNames,
        final String[] procedureNames,
        final boolean disableTableExtraction,
        final boolean lazyTableExtraction,
        final boolean disableProcedureExtraction,
        final boolean lazyProcedureExtraction,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema)
      throws  SQLException,
              QueryJException
    {
        this(metadataExtractionListener);
        immutableSetTableNames(tableNames);
        immutableSetProcedureNames(procedureNames);
        immutableSetDisableTableExtraction(disableTableExtraction);
        immutableSetLazyTableExtraction(lazyTableExtraction);
        immutableSetDisableProcedureExtraction(disableProcedureExtraction);
        immutableSetLazyProcedureExtraction(lazyProcedureExtraction);
        immutableSetMetaData(metaData);
        immutableSetCatalog(catalog);
        immutableSetSchema(schema);
    }

    /**
     * Retrieves the metadata.
     * @param metadataExtractionListener the
     * <code>MetadataExtractionListener</code> instance.
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
    protected void retrieveMetadata(
        final MetadataExtractionListener metadataExtractionListener,
        final String[] tableNames,
        final String[] procedureNames,
        final boolean disableTableExtraction,
        final boolean lazyTableExtraction,
        final boolean disableProcedureExtraction,
        final boolean lazyProcedureExtraction,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema)
      throws  SQLException,
              QueryJException
    {
        immutableSetMetadataExtractionListener(metadataExtractionListener);

        if  (   (!disableTableExtraction)
             && (!lazyTableExtraction))
        {
            extractTableMetadata(
                tableNames,
                metaData,
                catalog,
                schema,
                metadataExtractionListener);

            if  (metadataExtractionListener != null)
            {
                metadataExtractionListener.primaryKeyExtractionStarted();
            }

            extractPrimaryKeys(
                metaData, catalog, schema, metadataExtractionListener);

            if  (metadataExtractionListener != null)
            {
                metadataExtractionListener.allPrimaryKeysExtracted();
            }

            if  (metadataExtractionListener != null)
            {
                metadataExtractionListener.foreignKeyExtractionStarted();
            }

            extractForeignKeys(
                metaData, catalog, schema, metadataExtractionListener);

            if  (metadataExtractionListener != null)
            {
                metadataExtractionListener.allForeignKeysExtracted();
            }
        }

        if  (   (!disableProcedureExtraction)
             && (!lazyProcedureExtraction))
        {
            if  (metadataExtractionListener != null)
            {
                metadataExtractionListener.procedureExtractionStarted();
            }

            extractProcedureMetadata(
                procedureNames,
                metaData,
                catalog,
                schema,
                metadataExtractionListener);

            if  (metadataExtractionListener != null)
            {
                metadataExtractionListener.allProceduresExtracted();
            }
        }
    }

    /**
     * Specifies the meta data.
     * @param metaData the database meta data.
     */
    protected final void immutableSetMetaData(final DatabaseMetaData metaData)
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
     * Specifies the metadata extraction listener.
     * @param listener such listener.
     */
    protected final void immutableSetMetadataExtractionListener(
        final MetadataExtractionListener listener)
    {
        m__MetadataExtractionListener = listener;
    }

    /**
     * Specifies the metadata extraction listener.
     * @param listener such listener.
     */
    protected void setMetadataExtractionListener(
        final MetadataExtractionListener listener)
    {
        immutableSetMetadataExtractionListener(listener);
    }

    /**
     * Retrieves the metadata extraction listener.
     * @return such listener.
     */
    public MetadataExtractionListener getMetadataExtractionListener()
    {
        return m__MetadataExtractionListener;
    }

    /**
     * Specifies the catalog.
     * @param catalog the database catalog.
     */
    protected final void immutableSetCatalog(final String catalog)
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
    public String getCatalog()
    {
        return m__strCatalog;
    }

    /**
     * Specifies the schema.
     * @param schema the database schema.
     */
    protected final void immutableSetSchema(final String schema)
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
    public String getSchema()
    {
        return m__strSchema;
    }

    /**
     * Specifies the whether the table extraction should be disabled.
     * @param flag such flag.
     */
    protected final void immutableSetDisableTableExtraction(
        final boolean flag)
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
    public boolean getDisableTableExtraction()
    {
        return m__bDisableTableExtraction;
    }

    /**
     * Specifies the lazy table extraction flag.
     * @param flag such flag.
     */
    protected final void immutableSetLazyTableExtraction(final boolean flag)
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
    public boolean getLazyTableExtraction()
    {
        return m__bLazyTableExtraction;
    }

    /**
     * Specifies the whether the procedure extraction should be disabled.
     * @param flag such flag.
     */
    protected final void immutableSetDisableProcedureExtraction(
        final boolean flag)
    {
        m__bDisableProcedureExtraction = flag;
    }

    /**
     * Specifies the whether the procedure extraction should be disabled.
     * @param flag such flag.
     */
    protected void setDisableProcedureExtraction(
        final boolean flag)
    {
        immutableSetDisableProcedureExtraction(flag);
    }

    /**
     * Retrieves whether the procedure extraction is disabled.
     * @return such flag.
     */
    public boolean getDisableProcedureExtraction()
    {
        return m__bDisableProcedureExtraction;
    }

    /**
     * Specifies the lazy procedure extraction flag.
     * @param flag such flag.
     */
    protected final void immutableSetLazyProcedureExtraction(
        final boolean flag)
    {
        m__bLazyProcedureExtraction = flag;
    }

    /**
     * Specifies the lazy procedure extraction flag.
     * @param flag such flag.
     */
    protected void setLazyProcedureExtraction(
        final boolean flag)
    {
        immutableSetLazyProcedureExtraction(flag);
    }

    /**
     * Retrieves the lazy procedure extraction flag.
     * @return such flag.
     */
    public boolean getLazyProcedureExtraction()
    {
        return m__bLazyProcedureExtraction;
    }

    /**
     * Specifies the table names.
     * @param names such names.
     */
    protected final void immutableSetTableNames(final String[] names)
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
    protected final String[] immutableGetTableNames()
    {
        return m__astrTableNames;
    }

    /**
     * Retrieves the table names.
     * @return such names.
     */
    public String[] getTableNames()
    {
        return clone(immutableGetTableNames());
    }

    /**
     * Specifies the table comments.
     * @param comments such comments.
     */
    protected final void immutableSetTableComments(final Map comments)
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
    protected Map getTableComments()
    {
        return m__mTableComments;
    }

    /**
     * Specifies the column comments.
     * @param comments such comments.
     */
    protected final void immutableSetColumnComments(final Map comments)
    {
        m__mColumnComments = comments;
    }

    /**
     * Specifies the column comments.
     * @param comments such comments.
     */
    protected void setColumnComments(final Map comments)
    {
        immutableSetColumnComments(comments);
    }

    /**
     * Retrieves the column comments.
     * @return such comments.
     */
    protected Map getColumnComments()
    {
        return m__mColumnComments;
    }

    /**
     * Adds the comments of given table.
     * @param tableName the table name.
     * @param tableComment the table comment.
     * @precondition tableName != null
     * @precondition tableComment != null
     */
    public void addTableComment(
        final String tableName, final String tableComment)
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
        return
            getTableComment(tableName, getTableComments());
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
        return
            (String) tableComments.get(buildTableCommentKey(tableName));
    }

    /**
     * Adds the comments of given column.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param columnComment the column comment.
     * @precondition tableName != null
     * @precondition columnName != null
     * @precondition columnComment != null
     */
    public void addColumnComment(
        final String tableName,
        final String columnName,
        final String columnComment)
    {
        addColumnComment(
            tableName, columnName, columnComment, getColumnComments());
    }

    /**
     * Adds the comments of given column.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param columnComment the column comment.
     * @param map the map.
     * @precondition tableName != null
     * @precondition columnName != null
     * @precondition columnComment != null
     * @precondition map != null
     */
    protected void addColumnComment(
        final String tableName,
        final String columnName,
        final String columnComment,
        final Map map)
    {
        map.put(
            buildColumnCommentKey(tableName, columnName), columnComment);
    }

    /**
     * Retrieves the column comments.
     * @param tableName the table name.
     * @param columnName the column name.
     * @return the column comment.
     * @precondition tableName != null
     * @precondition columnName != null
     */
    public String getColumnComment(
        final String tableName, final String columnName)
    {
        return
            getColumnComment(tableName, columnName, getColumnComments());
    }

    /**
     * Retrieves the column comments.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param columnComments the column comments.
     * @return the column comment.
     * @precondition tableName != null
     * @precondition columnName != null
     * @precondition columnComments != null
     */
    protected String getColumnComment(
        final String tableName,
        final String columnName,
        final Map columnComments)
    {
        return
            (String)
                columnComments.get(
                    buildColumnCommentKey(tableName, columnName));
    }

    /**
     * Specifies the column names.
     * @param map the column names map.
     */
    protected final void immutableSetColumnNames(final Map map)
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
        final String tableName, final String[] columnNames)
    {
        addColumnNames(
            tableName, columnNames, getColumnNames());
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
    protected final void immutableSetColumnTypes(final Map map)
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
        return
            getColumnType(
                tableName, columnName, getColumnTypes());
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
        final String tableName,
        final String columnName,
        final Map columnTypes)
    {
        int result = -1;

        Object t_Result =
            columnTypes.get(buildKey(tableName, columnName));

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
        addColumnType(
            tableName, columnName, columnType, getColumnTypes());
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
    protected final void immutableSetAllowNulls(final Map map)
    {
        m__mAllowNulls = map;
    }

    /**
     * Specifies the null flags.
     * @param map the flag map.
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
        return getAllowNull(tableName, new String[] {allowName});
    }

    /**
     * Retrieves the allow null.
     * @param tableName the table name.
     * @param allowNames the allow names.
     * @return the allow null.
     * @see java.sql.Nulls
     * @precondition tableName != null
     * @precondition allowNames != null
     */
    public boolean allowsNull(
        final String tableName, final String[] allowNames)
    {
        return getAllowNull(tableName, allowNames);
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
        return getAllowNull(tableName, new String[] {allowName});
    }

    /**
     * Retrieves the allow null.
     * @param tableName the table name.
     * @param allowNames the allow names.
     * @return the allow null.
     * @see java.sql.Nulls
     * @precondition tableName != null
     * @precondition allowNames != null
     */
    public boolean getAllowNull(
        final String tableName, final String[] allowNames)
    {
        return
            getAllowNull(
                tableName, allowNames, getAllowNulls());
    }

    /**
     * Retrieves the allow null.
     * @param tableName the table name.
     * @param allowNames the allow names.
     * @param allowNulls the allow nulls.
     * @return the allow null.
     * @see java.sql.Nulls
     * @precondition tableName != null
     * @precondition allowNames != null
     * @precondition allowNulls != null
     */
    protected boolean getAllowNull(
        final String tableName,
        final String[] allowNames,
        final Map allowNulls)
    {
        boolean result = false;

        int t_iLength = (allowNames != null) ? allowNames.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            Object t_Result =
                allowNulls.get(
                    buildAllowNullKey(
                        tableName, allowNames[t_iIndex]));

            if  (   (t_Result != null)
                 && (t_Result instanceof Boolean))
            {
                result = ((Boolean) t_Result).booleanValue();

                if  (!result)
                {
                    break;
                }
            }
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
    public final void addAllowNull(
        final String tableName,
        final String columnName,
        final boolean flag)
    {
        addAllowNull(
            tableName, columnName, flag, getAllowNulls());
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
    protected final void immutableSetPrimaryKeys(final Map map)
    {
        m__mPrimaryKeys = map;
    }

    /**
     * Specifies the primary keys
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
        final String tableName, final String columnName)
    {
        addPrimaryKey(
            tableName, columnName, getPrimaryKeys());
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
        primaryKeys.put(
            buildPkKey(tableName, columnName),
            columnName);

        Collection t_cPks =
            (Collection) primaryKeys.get(buildPkKey(tableName));

        if  (t_cPks == null)
        {
            t_cPks = new ArrayList();
            primaryKeys.put(buildPkKey(tableName), t_cPks);
        }

        t_cPks.add(columnName);
    }

    /**
     * Retrieves the number of columns building the primary key.
     * @param tableName the table name.
     * @return the primary keys.
     * @precondition tableName != null
     */
    public int getPrimaryKeyColumnCount(final String tableName)
    {
        String[] t_astrPrimaryKey = getPrimaryKey(tableName);

        return (t_astrPrimaryKey != null) ? t_astrPrimaryKey.length : 0;
    }

    /**
     * Retrieves the primary key.
     * @param tableName the table name.
     * @return the primary key.
     * @precondition tableName != null
     */
    public String[] getPrimaryKey(final String tableName)
    {
        return getPrimaryKey(tableName, getPrimaryKeys());
    }

    /**
     * Retrieves the primary keys.
     * @param tableName the table name.
     * @param primaryKeys the primary keys.
     * @return the primary keys.
     * @precondition tableName != null
     * @precondition primaryKeys != null
     */
    protected String[] getPrimaryKey(
        final String tableName, final Map primaryKeys)
    {
        String[] result = EMPTY_STRING_ARRAY;

        Collection t_cPk =
            (Collection) primaryKeys.get(buildPkKey(tableName));

        if  (t_cPk != null)
        {
            result = (String[]) t_cPk.toArray(result);
        }
        
        return result;
    }

    /**
     * Checks whether given field belongs to the primary key or not.
     * @param tableName the table name.
     * @param fieldName the field name.
     * @return <code>true</code> if such field is part of what dentifies a
     * concrete row.
     * @precondition tableName != null
     * @precondition fieldName != null
     */
    public boolean isPartOfPrimaryKey(
        final String tableName, final String fieldName)
    {
        return
            isPartOfPrimaryKey(
                fieldName, getPrimaryKey(tableName));
    }

    /**
     * Checks whether given field is a primary key or not.
     * @param fieldName the field name.
     * @param primaryKey the primary key.
     * @return <code>true</code> if such field identifies a concrete row.
     * @precondition fieldName != null
     */
    public boolean isPartOfPrimaryKey(
        final String fieldName, final String[] primaryKey)
    {
        boolean result = false;

        if  (primaryKey != null)
        {
            for  (int t_iPkIndex = 0;
                      t_iPkIndex < primaryKey.length;
                      t_iPkIndex++)
            {
                if  (fieldName.equals(primaryKey[t_iPkIndex]))
                {
                    result = true;

                    break;
                }
            }
        }

        return result;
    }

    /**
     * Checks whether given field belongs to the primary key or not.
     * @param tableName the table name.
     * @param fieldNames the field names.
     * @param parentTableName the parent table name.
     * @return <code>true</code> if such field is part of what dentifies a
     * concrete row.
     */
    public boolean pointsToPrimaryKey(
        final String tableName, final String[] fieldNames, final String parentTableName)
    {
        return
            pointsToPrimaryKey(
                tableName,
                fieldNames,
                getForeignKeys(tableName, parentTableName),
                isCaseSensitive());
    }

    /**
     * Checks whether given field belongs to the primary key or not.
     * @param tableName the table name.
     * @param fieldNames the field names.
     * @param foreignKeys the foreign keys key.
     * @param caseSensitive whether case matters.
     * @return <code>true</code> in such case.
     * @precondition tableName != null
     * @precondition fieldNames != null
     * @precondition parentTableName != null
     * @precondition parentPrimaryKey != null
     */
    public boolean pointsToPrimaryKey(
        final String tableName,
        final String[] fieldNames,
        final String[][] foreignKeys,
        final boolean caseSensitive)
    {
        boolean result = false;

        int t_iFkCount = (foreignKeys != null) ? foreignKeys.length : 0;

        String[] t_astrFk;
        String t_strFkField;

        for  (int t_iFkIndex = 0; t_iFkIndex < t_iFkCount; t_iFkIndex++)
        {
            result = false;

            t_astrFk = foreignKeys[t_iFkIndex];

            if  (matchFields(fieldNames, t_astrFk, caseSensitive))
            {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Checks whether given field sets are equivalent.
     * @param firstFields the first field set.
     * @param secondFields the second field set.
     * @param caseSensitive whether case matters.
     * @return <code>true</code> in such case.
     * @precondition firstFields != null
     * @precondition secondFields != null
     */
    protected boolean matchFields(
        final String[] firstFields,
        final String[] secondFields,
        final boolean caseSensitive)
    {
        boolean result = false;

        int t_iFirstCount = (firstFields != null) ? firstFields.length : 0;
        int t_iSecondCount = (secondFields != null) ? secondFields.length : 0;

        if  (t_iFirstCount == t_iSecondCount)
        {
            for  (int t_iFirstIndex = 0;
                      t_iFirstIndex < t_iFirstCount;
                      t_iFirstIndex++)
            {
                for  (int t_iSecondIndex = 0;
                          t_iSecondIndex < t_iSecondCount;
                          t_iSecondIndex++)
                {
                    if  (   (caseSensitive)
                         && (firstFields[t_iFirstIndex].equals(
                                 secondFields[t_iSecondIndex])))
                    {
                        result = true;
                        break;
                    }
                    else if  (   (!caseSensitive)
                              && (firstFields[t_iFirstIndex].equalsIgnoreCase(
                                      secondFields[t_iSecondIndex])))
                    {
                        result = true;
                        break;
                    }
                }

                if  (!result)
                {
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
    protected final void immutableSetForeignKeys(final Map map)
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
     * @param columnNames the column names.
     * @param refTableName the referred table name.
     * @param refColumnNames the referred column names.
     * @precondition tableName != null
     * @precondition columnNames != null
     * @precondition refRableName != null
     * @precondition refColumnNames != null
     */
    public void addForeignKey(
        final String tableName,
        final String[] columnNames,
        final String refTableName,
        final String[] refColumnNames)
    {
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
                buildFkKey(
                    tableName, columnNames, refTableName, refColumnNames),
                columnNames);

            t_mForeignKeys.put(
                buildFkKey(tableName, columnNames, refTableName),
                refColumnNames);

            annotateForeignKey(
                buildFkKey(tableName, refTableName),
                columnNames,
                t_mForeignKeys);

            t_mForeignKeys.put(
                buildRefTableKey(tableName, columnNames),
                refTableName);

            t_mForeignKeys.put(
                buildRefFkKey(tableName, refTableName),
                refColumnNames);

            Collection t_ReferredTables =
                (Collection)
                    t_mForeignKeys.get(buildFkKey(tableName));

            if  (t_ReferredTables == null)
            {
                t_ReferredTables = new ArrayList();
            }

            if  (!t_ReferredTables.contains(refTableName))
            {
                t_ReferredTables.add(refTableName);
            }

            t_mForeignKeys.put(
                buildFkKey(tableName), t_ReferredTables);
        }
    }

    /**
     * Annotates the foreign key in given map.
     * @param key the key.
     * @param value the value.
     * @param map the map.
     * @precondition key != null
     * @precondition value != null
     * @precondition map != null
     */
    protected void annotateForeignKey(
        final Object key, final String value, final Map map)
    {
        annotateForeignKey(key, new String[] { value }, map);
    }
    
    /**
     * Annotates the foreign key in given map.
     * @param key the key.
     * @param values the values.
     * @param map the map.
     * @precondition key != null
     * @precondition values != null
     * @precondition map != null
     */
    protected void annotateForeignKey(
        final Object key, final String[] values, final Map map)
    {
        Object t_Fks = map.get(key);

        synchronized(map)
        {
            String[][] t_aastrFks = EMPTY_ARRAY_OF_STRING_ARRAYS;

            if  (   (t_Fks == null)
                 && (values != null))
            {
                t_aastrFks = new String[1][values.length];
                t_aastrFks[0] = values;
            }
            else if  (t_Fks instanceof String)
            {
                t_aastrFks = new String[][] { { (String) t_Fks }, values };
            }
            else if  (t_Fks instanceof String[][])
            {
                String[][] t_aastrCurrentFks = (String[][]) t_Fks;
            
                t_aastrFks =
                    new String[t_aastrCurrentFks.length + 1][values.length];

                for  (int t_iIndex = 0;
                          t_iIndex < t_aastrCurrentFks.length;
                          t_iIndex++)
                {
                    t_aastrFks[t_iIndex] = t_aastrCurrentFks[t_iIndex];
                }

                t_aastrFks[t_aastrCurrentFks.length] = values;
            }

            map.put(key, t_aastrFks);
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
                (Collection)
                    t_mForeignKeys.get(buildReferingTablesKey());

            if  (t_cReferingTables != null)
            {
                Iterator t_itReferingTables = t_cReferingTables.iterator();

                if  (t_itReferingTables != null)
                {
                    String[] t_astrReferredTables = null;

                    String t_strReferingTable = null;

                    while  (t_itReferingTables.hasNext())
                    {
                        t_strReferingTable =
                            (String) t_itReferingTables.next();

                        t_astrReferredTables =
                            getReferredTables(t_strReferingTable);

                        if  (t_astrReferredTables != null)
                        {
                            String t_strCurrentTable = null;

                            for  (int t_iIndex = 0;
                                      t_iIndex < t_astrReferredTables.length;
                                      t_iIndex++)
                            {
                                t_strCurrentTable =
                                    t_astrReferredTables[t_iIndex];

                                if  (   (t_strCurrentTable != null)
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
    public String[][] getForeignKeys(final String tableName)
    {
        String[][] result = null;
        
        Collection t_cResult = new ArrayList();

        String[] t_astrReferredTables = getReferredTables(tableName);

        int t_iLength =
            (t_astrReferredTables != null) ? t_astrReferredTables.length : 0;
        
        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            String[][] t_aastrFks =
                getForeignKeys(
                    tableName, t_astrReferredTables[t_iIndex]);

            int t_iFkLength = (t_aastrFks != null) ? t_aastrFks.length : 0;
            
            for  (int t_iFkIndex = 0; t_iFkIndex < t_iFkLength; t_iFkIndex++)
            {
                t_cResult.add(t_aastrFks[t_iFkIndex]);
            }
        }

        Iterator t_Iterator = t_cResult.iterator();
        
        result = new String[t_cResult.size()][];
        
        int t_iActualIndex = 0;
        
        while  (t_Iterator.hasNext())
        {
            result[t_iActualIndex++] = (String[]) t_Iterator.next();
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

        String[][] t_aastrForeignKeys = getForeignKeys(tableName);

        result =
            (   (t_aastrForeignKeys != null)
             && (t_aastrForeignKeys.length > 0));

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
        Collection t_cResult = null;

        Map t_mForeignKeys = getForeignKeys();

        if  (t_mForeignKeys != null) 
        {
            t_cResult =
                (Collection)
                    t_mForeignKeys.get(buildFkKey(tableName));
        }
        
        if  (t_cResult == null)
        {
            t_cResult = new ArrayList();
        }
        
        return (String[]) t_cResult.toArray(EMPTY_STRING_ARRAY);
    }

    /**
     * Retrieves the field of given table that points to a field on the
     * referred one.
     * @param tableName the table name.
     * @param refTableName the referred table name.
     * @return such field.
     * @precondition tableName != null
     * @precondition refTableName != null
     */
    public String[][] getForeignKeys(
        final String tableName, String refTableName)
    {
        String[][] result = EMPTY_ARRAY_OF_STRING_ARRAYS;

        Map t_mForeignKeys = getForeignKeys();

        if  (t_mForeignKeys != null) 
        {
            Object t_ForeignKeys = 
                t_mForeignKeys.get(buildFkKey(tableName, refTableName));

            if  (t_ForeignKeys instanceof String)
            {
                result = new String[][] { { (String) t_ForeignKeys } };
            }
            else if  (t_ForeignKeys instanceof String[][])
            {
                result = (String[][]) t_ForeignKeys;
            }
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
    public String getReferredTable(
        final String tableName, final String[] foreignKey)
    {
        String result = null;

        Map t_mForeignKeys = getForeignKeys();

        if  (t_mForeignKeys != null) 
        {
            result =
                (String)
                    t_mForeignKeys.get(
                        buildRefTableKey(tableName, foreignKey));
        }

        return result;
    }

    /**
     * Retrieves the field of referred table pointed by a field on the
     * original one.
     * @param tableName the table name.
     * @param refTableName the referred table name.
     * @return such field.
     * @precondition tableName != null
     * @precondition refTableName != null
     */
    public String[][] getReferredKeys(
        final String tableName, String refTableName)
    {
        String[][] result = EMPTY_ARRAY_OF_STRING_ARRAYS;

        Map t_mForeignKeys = getForeignKeys();

        if  (t_mForeignKeys != null) 
        {
            Object t_ForeignKeys = 
                t_mForeignKeys.get(
                    buildFkKey(tableName, refTableName));

            if  (t_ForeignKeys instanceof String)
            {
                result = new String[][] { { (String) t_ForeignKeys } };
            }
            else if  (t_ForeignKeys instanceof String[][])
            {
                result = (String[][]) t_ForeignKeys;
            }
        }
        
        return result;
    }

    /**
     * Specifies the externally managed fields.
     * @param map the externally managed fields map.
     */
    protected final void immutableSetExternallyManagedFields(final Map map)
    {
        m__mExternallyManagedFields = map;
    }

    /**
     * Specifies the externally managed fields.
     * @param map the externally managed fields map.
     */
    protected void setExternallyManagedFields(final Map map)
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
        final String tableName, final String columnName)
    {
        addExternallyManagedField(tableName, columnName, null);
    }

    /**
     * Annotates a externally-managed field.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param keyword the keyword.
     */
    public void addExternallyManagedField(
        final String tableName,
        final String columnName,
        final String keyword)
    {
        addExternallyManagedField(
            tableName, columnName, keyword, null);
    }

    /**
     * Annotates a externally-managed field.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param keyword the keyword.
     * @param query the retrieval query.
     */
    public final void addExternallyManagedField(
        final String tableName,
        final String columnName,
        final String keyword,
        final String query)
    {
        Map t_mExternallyManagedFields =
            getExternallyManagedFields();

        if  (t_mExternallyManagedFields != null) 
        {
            t_mExternallyManagedFields.put(
                buildExternallyManagedFieldKey(tableName, columnName),
                buildExternallyManagedFieldValue(keyword));

            t_mExternallyManagedFields.put(
                buildExternallyManagedFieldRetrievalQueryKey(
                    tableName, columnName),
                buildExternallyManagedFieldRetrievalQueryValue(
                    query));

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
    public String[] getExternallyManagedFields(
        final String tableName)
    {
        String[] result = EMPTY_STRING_ARRAY;

        if  (tableName != null)
        {
            Map t_mExternallyManagedFields =
                getExternallyManagedFields();

            if  (t_mExternallyManagedFields != null)
            {
                Collection t_cExternallyManagedFields =
                    (Collection)
                        t_mExternallyManagedFields.get(
                            buildExternallyManagedFieldKey(
                                tableName));

                if  (t_cExternallyManagedFields != null)
                {
                    result =
                        (String[]) t_cExternallyManagedFields.toArray(result);
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
    public boolean isManagedExternally(
        final String tableName, String fieldName)
    {
        boolean result = false;

        if  (   (tableName != null)
             && (fieldName != null))
        {
            String[] t_astrExternallyManagedFields =
                getExternallyManagedFields(tableName);

            if  (t_astrExternallyManagedFields != null)
            {
                for  (int t_iExternallyManagedFieldIndex = 0;
                            t_iExternallyManagedFieldIndex
                          < t_astrExternallyManagedFields.length;
                          t_iExternallyManagedFieldIndex++)
                {
                    if  (fieldName.equalsIgnoreCase(
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
    public String getKeyword(
        final String tableName, String fieldName)
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
     * Retrieves the keyword used to create new values of given field.
     * @param tableName the table name.
     * @param fieldName the field name.
     * @return such keyword, or <code>null</code> if such information is
     * unknown.
     */
    public String getExternallyManagedFieldRetrievalQuery(
        final String tableName, String fieldName)
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
                              buildExternallyManagedFieldRetrievalQueryKey(
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
    protected final void immutableSetProcedureNames(final String[] names)
    {
        m__astrProcedureNames = names;
    }

    /**
     * Specifies the procedure names.
     * @param names such names.
     */
    protected void setProcedureNames(final String[] names)
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
     * @param proceduresMetadata such metadata.
     */
    protected final void immutableSetProceduresMetadata(
        final ProcedureMetadata[] proceduresMetadata)
    {
        m__aProceduresMetadata = proceduresMetadata;
    }

    /**
     * Specifies the procedures metadata.
     * @param proceduresMetadata such metadata.
     */
    protected void setProceduresMetadata(
        final ProcedureMetadata[] proceduresMetadata)
    {
        immutableSetProceduresMetadata(proceduresMetadata);
    }

    /**
     * Retrieves the procedures metadata.
     * @return such metadata.
     */
    protected final ProcedureMetadata[] immutableGetProceduresMetadata()
    {
        return m__aProceduresMetadata;
    }

    /**
     * Retrieves the procedures metadata.
     * @return such metadata.
     */
    public ProcedureMetadata[] getProceduresMetadata()
    {
        return clone(immutableGetProceduresMetadata());
    }

    /**
     * Specifies the procedure parameter metadata.
     * @param map the parameter metadata map.
     */
    protected final void immutableSetProcedureParametersMetadata(
        final Map map)
    {
        m__mProcedureParametersMetadata = map;
    }

    /**
     * Specifies the procedure parameter metadata.
     * @param map the parameter metadata map.
     */
    protected void setProcedureParametersMetadata(final Map map)
    {
        immutableSetProcedureParametersMetadata(map);
    }

    /**
     * Retrieves the procedure parameters metadata.
     * @return such map.
     */
    protected Map getProcedureParametersMetadata()
    {
        return m__mProcedureParametersMetadata;
    }

    /**
     * Adds the parameters of given procedure.
     * @param procedureName the procedure name.
     * @param parametersMetadata the parameters metadata.
     */
    protected void addProcedureParametersMetadata(
        final String procedureName,
        final ProcedureParameterMetadata[] parametersMetadata)
    {
        if  (parametersMetadata != null) 
        {
            Map t_mParametersMetadata =
                getProcedureParametersMetadata();

            if  (t_mParametersMetadata != null) 
            {
                t_mParametersMetadata.put(
                    buildKey(procedureName),
                    parametersMetadata);
            }
        }
    }

    /**
     * Retrieves the parameters of given procedure.
     * @param procedure the procedure.
     * @return the parameters metadata.
     * @precondition procedure != null
     */
    public ProcedureParameterMetadata[] getProcedureParametersMetadata(
        final ProcedureMetadata procedure)
    {
        return getProcedureParametersMetadata(procedure.getName());
    }

    /**
     * Retrieves the parameters of given procedure.
     * @param procedureName the procedure name.
     * @return the parameters metadata.
     */
    public ProcedureParameterMetadata[] getProcedureParametersMetadata(
        final String procedureName)
    {
        ProcedureParameterMetadata[] result =
            EMPTY_PROCEDURE_PARAMETER_METADATA_ARRAY;

        if  (procedureName != null)
        {
            Map t_mParametersMetadata =
                getProcedureParametersMetadata();

            if  (t_mParametersMetadata != null) 
            {
                result =
                    (ProcedureParameterMetadata[])
                        t_mParametersMetadata.get(
                            buildKey(procedureName));
            }
        }

        return result;
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
    protected Object buildKey(
        final Object firstKey, final Object secondKey)
    {
        return
            "_|_" + buildKey(firstKey)
            + "|'|" + buildKey(secondKey) + "|_|";
    }

    /**
     * Builds a pk key using given object.
     * @param firstKey the first object key.
     * @return the map key.
     */
    protected Object buildPkKey(final Object firstKey)
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
        Object result = null;
        
        String secondPart = "" + secondKey;
        
        if  (secondKey instanceof String[])
        {
            secondPart = concat((String[]) secondKey, ",");
        }
        
        result = buildFkKey(firstKey) + "++" + secondPart;

        return result;
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
        Object result = null;
        
        String fourthPart = "" + fourthKey;
        
        if  (fourthKey instanceof String[])
        {
            fourthPart = concat((String[]) fourthKey, ",");
        }
        
        result =
              buildFkKey(firstKey, secondKey, thirdKey)
            + ".;.;" + fourthPart;

        return result;
    }

    /**
     * Builds a ref fk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    protected Object buildRefFkKey(
        final Object firstKey, final Object secondKey)
    {
        return "\\ref//" + buildFkKey(firstKey, secondKey);
    }

    /**
     * Builds a externally-managed field key using given object.
     * @param firstKey the first object key.
     * @return the map key.
     */
    protected Object buildExternallyManagedFieldKey(
        final Object firstKey)
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
        return
              buildExternallyManagedFieldKey(firstKey)
            + ".,.," + secondKey;
    }

    /**
     * Builds a retrieval query key for an externally-managed field using given
     * object.
     * @param firstKey the first object key.
     * @return the map key.
     */
    protected Object buildExternallyManagedFieldRetrievalQueryKey(
        final Object firstKey)
    {
        return
              "[externally-managed-field-retrieval-query]!!"
            + buildKey(firstKey);
    }

    /**
     * Builds a retrieval query key for an externally-managed field
     * using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    protected Object buildExternallyManagedFieldRetrievalQueryKey(
        final Object firstKey, final Object secondKey)
    {
        return
              buildExternallyManagedFieldRetrievalQueryKey(firstKey)
            + ",.,." + secondKey;
    }

    /**
     * Builds a externally-managed field value using given object.
     * @param value the object.
     * @return the map value.
     */
    protected Object buildExternallyManagedFieldValue(
        final Object value)
    {
        Object result = "";

        if  (value != null)
        {
            result = value;
        }

        return result;
    }

    /**
     * Builds a retrieval query value key for an externally-managed field
     * using given object.
     * @param value the object.
     * @return the map value.
     */
    protected Object buildExternallyManagedFieldRetrievalQueryValue(
        final Object value)
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
     * @param first the first object.
     * @param second the second.
     * @return the map key.
     */
    protected Object buildAllowNullKey(
        final Object first, final Object second)
    {
        return
              "[allow-nulls]!!"
            + buildKey(first)
            + buildKey(second);
    }

    /**
     * Builds a table comment key using given object.
     * @param tableName the table name.
     * @return the map key.
     */
    protected Object buildTableCommentKey(
        final Object tableName)
    {
        return "[table-comment]!!" + buildKey(tableName);
    }

    /**
     * Builds a column comment key using given object.
     * @param tableName the table name.
     * @param columnName the column name.
     * @return the map key.
     * @precondition tableName != null
     * @precondition columnName != null
     */
    protected Object buildColumnCommentKey(
        final Object tableName, final String columnName)
    {
        return
            "[column-comment]!!" + buildKey(tableName) + buildKey(columnName);
    }

    /**
     * Retrieves the desired metadata.
     * @param tableNames optionally specified table names.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param metadataExtractionListener the
     * <code>MetadataExtractionListener</code> instance.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    protected void extractTableMetadata(
        final String[] tableNames,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        if  (metadataExtractionListener != null)
        {
            metadataExtractionListener.tableNamesExtractionStarted();
        }

        String[] t_astrTableNames = tableNames;

        if  (   (t_astrTableNames == null)
             || (t_astrTableNames.length == 0))
        {
            t_astrTableNames =
                getTableNames(
                    metaData, catalog, schema, metadataExtractionListener);

            setTableNames(t_astrTableNames);
        }

        int t_iLength =
            (t_astrTableNames != null) ? t_astrTableNames.length : 0;

        if  (metadataExtractionListener != null)
        {
            metadataExtractionListener.tableNamesExtracted(t_iLength);
        }

        if  (metadataExtractionListener != null)
        {
            metadataExtractionListener.tableMetadataExtractionStarted();
        }

        String t_strTableName;
        String t_strParentTable;
        
        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++) 
        {
            t_strTableName = t_astrTableNames[t_iIndex];

            if  (metadataExtractionListener != null)
            {
                metadataExtractionListener.tableExtractionStarted(
                    t_strTableName);

                metadataExtractionListener.tableCommentExtractionStarted(
                    t_strTableName);
            }

            String t_strTableComment =
                getTableComment(
                    metaData,
                    catalog,
                    schema,
                    t_strTableName,
                    metadataExtractionListener);

            addTableComment(t_strTableName, t_strTableComment);

            if  (metadataExtractionListener != null)
            {
                metadataExtractionListener.columnNamesExtractionStarted(
                    t_strTableName);
            }

            t_strParentTable = getParentTable(t_strTableName);
            
            String[] t_astrColumnNames =
                getColumnNames(
                    metaData,
                    catalog,
                    schema,
                    t_strTableName,
                    t_strParentTable,
                    metadataExtractionListener);

            if  (metadataExtractionListener != null)
            {
                metadataExtractionListener.columnNamesExtracted(
                    t_strTableName,
                    (t_astrColumnNames != null)
                    ? t_astrColumnNames.length : 0);
            }

            if  (metadataExtractionListener != null)
            {
                metadataExtractionListener.columnTypesExtractionStarted(
                    t_strTableName);
            }

            if  (t_astrColumnNames != null) 
            {
                addColumnNames(t_strTableName, t_astrColumnNames);

                int[] t_aiColumnTypes =
                    getColumnTypes(
                        metaData,
                        catalog,
                        schema,
                        t_strTableName,
                        t_strParentTable,
                        t_astrColumnNames.length,
                        metadataExtractionListener);

                int t_iColumnLength =
                    (t_aiColumnTypes != null) ? t_aiColumnTypes.length : 0;
                
                for  (int t_iColumnIndex = 0;
                          t_iColumnIndex < t_iColumnLength;
                          t_iColumnIndex++)
                {
                    addColumnType(
                        t_strTableName,
                        t_astrColumnNames[t_iColumnIndex],
                        t_aiColumnTypes[t_iColumnIndex]);
                }

                if  (metadataExtractionListener != null)
                {
                    metadataExtractionListener.columnTypesExtracted(
                        t_strTableName);
                }

                if  (metadataExtractionListener != null)
                {
                    metadataExtractionListener.columnCommentsExtractionStarted(
                        t_strTableName);
                }

                String[] t_astrColumnComments =
                    getColumnComments(
                        metaData,
                        catalog,
                        schema,
                        t_strTableName,
                        t_astrColumnNames,
                        metadataExtractionListener);

                t_iColumnLength =
                    (t_astrColumnComments != null)
                    ?  t_astrColumnComments.length
                    :  0;
                
                for  (int t_iColumnIndex = 0;
                          t_iColumnIndex < t_iColumnLength;
                          t_iColumnIndex++)
                {
                    addColumnComment(
                        t_strTableName,
                        t_astrColumnNames[t_iColumnIndex],
                        t_astrColumnComments[t_iColumnIndex]);
                }

                if  (metadataExtractionListener != null)
                {
                    metadataExtractionListener.columnCommentsExtracted(
                        t_strTableName);
                }

                if  (metadataExtractionListener != null)
                {
                    metadataExtractionListener.columnNullablesExtractionStarted(
                        t_strTableName);
                }

                boolean[] t_abAllowNull =
                    getAllowNulls(
                        metaData,
                        catalog,
                        schema,
                        t_strTableName,
                        t_strParentTable,
                        t_astrColumnNames.length,
                        metadataExtractionListener);

                t_iColumnLength =
                    (t_abAllowNull != null) ? t_abAllowNull.length : 0;
                
                for  (int t_iColumnIndex = 0;
                          t_iColumnIndex < t_iColumnLength;
                          t_iColumnIndex++)
                {
                    addAllowNull(
                        t_astrTableNames[t_iIndex],
                        t_astrColumnNames[t_iColumnIndex],
                        t_abAllowNull[t_iColumnIndex]);
                }

                if  (metadataExtractionListener != null)
                {
                    metadataExtractionListener.columnNullablesExtracted(
                        t_strTableName);
                }
            }

            if  (metadataExtractionListener != null)
            {
                metadataExtractionListener.tableExtracted(
                    t_strTableName);
            }
        }        
    }

    /**
     * Retrieves the desired metadata.
     * @param procedureNames optionally specified procedure names.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param metadataExtractionListener the metadata extraction listener.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    protected void extractProcedureMetadata(
        final String[] procedureNames,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        String[] t_astrProcedureNames = procedureNames;

        if  (t_astrProcedureNames == null) 
        {
            t_astrProcedureNames =
                getProcedureNames(
                    metaData, catalog, schema, metadataExtractionListener);
        }

        setProcedureNames(t_astrProcedureNames);

        int t_iLength =
            (t_astrProcedureNames != null) ? t_astrProcedureNames.length : 0;
        
        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++) 
        {
            ProcedureParameterMetadata[] t_aProcedureParametersMetadata =
                getProcedureParametersMetadata(
                    metaData,
                    catalog,
                    schema,
                    t_astrProcedureNames[t_iIndex],
                    metadataExtractionListener);

            addProcedureParametersMetadata(
                t_astrProcedureNames[t_iIndex],
                t_aProcedureParametersMetadata);

            if  (metadataExtractionListener != null)
            {
                metadataExtractionListener.procedureExtracted(
                    t_astrProcedureNames[t_iIndex],
                    t_aProcedureParametersMetadata);
            }
        }        
    }

    /**
     * Retrieves the table names.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of all table names.
     * @throws SQLException if the database operation fails.
     * @precondition metaData != null
     */
    protected String[] getTableNames(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        String[] result;

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

            result = extractTableNames(t_rsTables, metadataExtractionListener);
        }
        catch  (final SQLException sqlException)
        {
            logWarn(
                "Cannot retrieve the table names.",
                sqlException);

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

        if  (result == null)
        {
            result = EMPTY_STRING_ARRAY;
        }

        return result;
    }

    /**
     * Extracts the table names from given result set.
     * @param resultSet the result set with the table information.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of column names.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected String[] extractTableNames(
        final ResultSet resultSet,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        return
            extractStringFields(
                resultSet,
                "TABLE_NAME",
                null,
                null
                // TODO //metadataExtractionListener,
                )[0];
    }

    /**
     * Retrieves the column names from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of all column names.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if the any other error occurs.
     * @precondition metaData != null
     */
    protected String[] getColumnNames(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String tableName,
        final String parentTable,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        Collection result = new ArrayList();

        if  (parentTable != null)
        {
            result.addAll(
                Arrays.asList(
                    getColumnNames(
                        metaData,
                        catalog,
                        schema,
                        parentTable,
                        getParentTable(parentTable),
                        metadataExtractionListener)));
        }
        
        try 
        {
            ResultSet t_rsColumns =
                metaData.getColumns(
                    catalog,
                    schema,
                    tableName,
                    null);

            result.addAll(
                Arrays.asList(
                    extractColumnNames(
                        t_rsColumns, tableName, metadataExtractionListener)));

            t_rsColumns.close();
        }
        catch  (final SQLException sqlException)
        {
            logWarn(
                "Cannot retrieve the column names.",
                sqlException);

            throw sqlException;
        }

        return (String[]) result.toArray(EMPTY_STRING_ARRAY);
    }

    /**
     * Extracts the column names from given result set.
     * @param resultSet the result set with the column information.
     * @param field the field.
     * @param table the table.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of column names.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     * @precondition field != null
     */
    protected String[] extractColumnNames(
        final ResultSet resultSet,
        final Field field,
        final String table,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        return
            extractColumnNames(
                resultSet,
                field.toSimplifiedString(),
                table,
                metadataExtractionListener);
    }

    /**
     * Extracts the column names from given result set.
     * @param resultSet the result set with the column information.
     * @param table the table.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of column names.
     * @throws SQLException if the database operation fails.
     */
    protected String[] extractColumnNames(
        final ResultSet resultSet,
        final String table,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        return
            extractColumnNames(
                resultSet, "COLUMN_NAME", table, metadataExtractionListener);
    }

    /**
     * Extracts the column names from given result set.
     * @param resultSet the result set with the column information.
     * @param fieldName the field name.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of column names.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected String[] extractColumnNames(
        final ResultSet resultSet,
        final String fieldName,
        final String table,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        return
            extractStringFields(
                resultSet,
                fieldName,
                null,
                null)[0];
//TODO                metadataExtractionListener)[0];
    }

    /**
     * Retrieves the column types from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param parentTable the parent table, if any.
     * @param size the number of fields to extract.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of all column names.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if any other error occurs.
     */
    protected int[] getColumnTypes(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String tableName,
        final String parentTable,
        final int size,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        Collection result = new ArrayList();
        
        if  (parentTable != null)
        {
            result.addAll(
                Arrays.asList(
                    (Object[])
                        toIntegerArray(
                            getColumnTypes(
                                metaData,
                                catalog,
                                schema,
                                parentTable,
                                getParentTable(parentTable),
                                -1,
                                metadataExtractionListener))));
        }

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

                result.addAll(
                    Arrays.asList(
                        (Object[])
                            toIntegerArray(
                                extractColumnTypes(
                                    t_rsColumns,
                                    tableName,
                                    size,
                                    metadataExtractionListener))));

                t_rsColumns.close();
            }
        }
        catch  (final SQLException sqlException)
        {
            logWarn(
                "Cannot retrieve the column types.",
                sqlException);

            throw sqlException;
        }

        return toIntArray((Integer[]) result.toArray(EMPTY_INTEGER_ARRAY));
    }

    /**
     * Retrieves the column comments from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param columnNames the column names, or null if not available.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of all column comments.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if any other error occurs.
     */
    protected String[] getColumnComments(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String tableName,
        final String[] columnNames,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        Collection result = new ArrayList();
        
        try 
        {
            if  (metaData != null) 
            {
                int t_iCount =
                    (columnNames != null) ? columnNames.length : 0;

                for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
                {
                    result.add(
                        getColumnComment(
                            metaData,
                            catalog,
                            schema,
                            tableName,
                            columnNames[t_iIndex],
                            metadataExtractionListener));
                }
            }
        }
        catch  (final SQLException sqlException)
        {
            logWarn(
                "Cannot retrieve the column comment.",
                sqlException);

            throw sqlException;
        }

        return (String[]) result.toArray(EMPTY_STRING_ARRAY);
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param field the field.
     * @param table the table.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of column types.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     * @precondition field != null
     */
    protected int[] extractColumnTypes(
        final ResultSet resultSet,
        final Field field,
        final String table,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        return
            extractColumnTypes(
                resultSet,
                field.toSimplifiedString(),
                table,
                metadataExtractionListener);
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param table the table.
     * @param size the number of fields to extract.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of column types.
     * @throws SQLException if the database operation fails.
     */
    protected int[] extractColumnTypes(
        final ResultSet resultSet,
        final String table,
        final int size,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        return
            extractColumnTypes(
                resultSet,
                "DATA_TYPE",
                table,
                size,
                metadataExtractionListener);
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param fieldName the field name.
     * @param table the table.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of column types.
     * @throws SQLException if the database operation fails.
     */
    protected int[] extractColumnTypes(
        final ResultSet resultSet,
        final String fieldName,
        final String table,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        return
            extractColumnTypes(
                resultSet, fieldName, table, -1, metadataExtractionListener);
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param fieldName the field name.
     * @param table the table.
     * @param size the number of fields to extract.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of column types.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected int[] extractColumnTypes(
        final ResultSet resultSet,
        final String fieldName,
        final String table,
        final int size,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        return
            toIntArray(
                extractIntFields(
                    resultSet,
                    fieldName,
                    table,
                    null,
                    size)[0]);
                    // TODO metadataExtractionListener)[0]);
    }

    /**
     * Retrieves the null flag from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param parentTable the parent table, if any.
     * @param size the number of fields to extract.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of all column names.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if any other error occurs.
     * @precondition metaData != null
     */
    protected boolean[] getAllowNulls(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String tableName,
        final String parentTable,
        final int size,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        Collection result = new ArrayList();

        if  (parentTable != null)
        {
            result.addAll(
                Arrays.asList(
                    (Object[])
                       toBooleanArray(
                           getAllowNulls(
                               metaData,
                               catalog,
                               schema,
                               parentTable,
                               getParentTable(parentTable),
                               -1,
                               metadataExtractionListener))));
        }
                    
        try 
        {
            ResultSet t_rsColumns =
                metaData.getColumns(
                    catalog,
                    schema,
                    tableName,
                    null);

            result.addAll(
                Arrays.asList(
                    (Object[])
                        toBooleanArray(
                            extractAllowNull(
                                t_rsColumns,
                                tableName,
                                size,
                                metadataExtractionListener))));
            
            t_rsColumns.close();
        }
        catch  (final SQLException sqlException)
        {
            logWarn(
                "Cannot retrieve the null flag.",
                sqlException);

            throw sqlException;
        }

        return toBoolArray((Boolean[]) result.toArray(EMPTY_BOOLEAN_ARRAY));
    }

    /**
     * Extracts the null flag from given result set.
     * @param resultSet the result set with the column information.
     * @param field the field.
     * @param table the table.
     * @param nameField the name field.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of null flag.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     * @precondition field != null
     */
    protected boolean[] extractAllowNull(
        final ResultSet resultSet,
        final Field field,
        final String table,
        final Field nameField,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        return
            extractAllowNull(
                resultSet,
                field.toSimplifiedString(),
                table,
                nameField.toSimplifiedString(),
                metadataExtractionListener);
    }

    /**
     * Extracts the null flag from given result set.
     * @param resultSet the result set with the column information.
     * @param table the table name.
     * @param size the number of fields to extract.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of null flags.
     * @throws SQLException if the database operation fails.
     */
    protected boolean[] extractAllowNull(
        final ResultSet resultSet,
        final String table,
        final int size,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        return
            extractAllowNull(
                resultSet,
                "NULLABLE",
                table,
                "COLUMN_NAME",
                size,
                metadataExtractionListener);
    }

    /**
     * Extracts the null flag from given result set.
     * @param resultSet the result set with the column information.
     * @param fieldName the field name.
     * @param table the table name.
     * @param nameField the name field.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of null flags.
     * @throws SQLException if the database operation fails.
     */
    protected boolean[] extractAllowNull(
        final ResultSet resultSet,
        final String fieldName,
        final String table,
        final String nameField,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        return
            extractAllowNull(
                resultSet,
                fieldName,
                table,
                nameField,
                -1,
                metadataExtractionListener);
    }

    /**
     * Extracts the null flag from given result set.
     * @param resultSet the result set with the column information.
     * @param fieldName the field name.
     * @param table the table name.
     * @param size the number of fields to extract.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of null flags.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected boolean[] extractAllowNull(
        final ResultSet resultSet,
        final String fieldName,
        final String table,
        final String nameField,
        final int size,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        boolean[] result =  EMPTY_BOOL_ARRAY;

        String[][] t_astrExtractedValues =
            extractIntFields(
                resultSet,
                fieldName,
                table,
                nameField,
                size);
                // TODO metadataExtractionListener);

        int[] t_iFlags = toIntArray(t_astrExtractedValues[0]);
        String[] t_astrNames = t_astrExtractedValues[1];

        int t_iLength = (t_iFlags != null) ? t_iFlags.length : 0;

        if  (t_iLength != 0)
        {
            result = new boolean[t_iLength];
        }

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            switch  (t_iFlags[t_iIndex])
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

        return result;
    }

    /**
     * Retrieves the procedure names.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of all procedure names.
     * @throws SQLException if the database operation fails.
     */
    protected String[] getProcedureNames(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        String[] result = EMPTY_STRING_ARRAY;

        ProcedureMetadata[] t_aProceduresMetadata =
            getProceduresMetadata(
                metaData, catalog, schema, metadataExtractionListener);

        int t_iLength =
            (t_aProceduresMetadata != null) ? t_aProceduresMetadata.length : 0;
        
        setProceduresMetadata(t_aProceduresMetadata);

        if  (t_iLength != 0)
        {
            result = new String[t_aProceduresMetadata.length];
        }
        
        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++) 
        {
            result[t_iIndex] = t_aProceduresMetadata[t_iIndex].getName();
        }

        return result;
    }

    /**
     * Retrieves the procedures' meta data.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of all procedure metadata.
     * @throws SQLException if the database operation fails.
     * @precondition metaData != null
     */
    protected ProcedureMetadata[] getProceduresMetadata(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        ProcedureMetadata[] result = EMPTY_PROCEDURE_METADATA_ARRAY;

        ResultSet t_rsProcedures = null;

        try 
        {
            try 
            {
                t_rsProcedures =
                    metaData.getProcedures(catalog, schema, null);
            }
            catch  (final SQLException sqlException)
            {
                throw
                    new QueryJException(
                        "cannot.retrieve.database.procedure.names",
                        sqlException);
            }

            result =
                extractProceduresMetadata(
                    t_rsProcedures, metadataExtractionListener);
        }
        catch  (final SQLException sqlException)
        {
            logWarn(
                "cannot.retrieve.database.procedure.names",
                sqlException);
//            throw sqlException;
        }
        catch  (final QueryJException queryjException)
        {
            logWarn(
                "cannot.retrieve.database.procedure.names",
                queryjException);
//              throw queryjException;
        }
        finally 
        {
            if  (t_rsProcedures != null)
            {
                t_rsProcedures.close();
            }
        }

        return result;
    }

    /**
     * Extracts the procedures' metadata from given result set.
     * @param resultSet the result set with the procedure information.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of procedure metadata.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected ProcedureMetadata[] extractProceduresMetadata(
        final ResultSet resultSet,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        ProcedureMetadata[] result = EMPTY_PROCEDURE_METADATA_ARRAY;

        List t_lProcedureList = new ArrayList();

        if  (resultSet.next())
        {
            String t_strName;
            int t_iType;
            String t_strComment;

            while  (resultSet.next()) 
            {
                t_strName = resultSet.getString("PROCEDURE_NAME");
                t_iType = (int) resultSet.getShort("PROCEDURE_TYPE");
                t_strComment = resultSet.getString("REMARKS");

                t_lProcedureList.add(
                    new ProcedureMetadata(t_strName, t_iType, t_strComment));

                metadataExtractionListener.procedureMetadataExtracted(
                    t_strName, t_iType, t_strComment);
            }
        }

        result = (ProcedureMetadata[]) t_lProcedureList.toArray(result);

        return result;
    }

    /**
     * Retrieves the procedure parameter names from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param procedureName the procedure name.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of all procedure parameter names.
     * @throws SQLException if the database operation fails.
     */
    protected String[] getProcedureParameterNames(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String procedureName,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        String[] result = EMPTY_STRING_ARRAY;

        ProcedureParameterMetadata[] t_aProcedureParametersMetadata =
            getProcedureParametersMetadata(
                metaData,
                catalog,
                schema,
                procedureName,
                metadataExtractionListener);

        int t_iLength =
            (t_aProcedureParametersMetadata != null)
            ?  t_aProcedureParametersMetadata.length
            :  0;
        
        //setProceduresMetadata(t_aProcedureParametersMetadata);

        if  (t_iLength != 0)
        {
            result = new String[t_iLength];
        }
        
        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++) 
        {
            result[t_iIndex] =
                t_aProcedureParametersMetadata[t_iIndex].getName();
        }

        return result;
    }

    /**
     * Retrieves the procedure parameter names from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param procedureName the procedure name.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of all procedure parameter names.
     * @throws SQLException if the database operation fails.
     * @precondition metaData != null
     */
    protected ProcedureParameterMetadata[] getProcedureParametersMetadata(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String procedureName,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        ProcedureParameterMetadata[] result;

        try 
        {
            ResultSet t_rsProcedureParameters =
                metaData.getProcedureColumns(
                    catalog,
                    schema,
                    procedureName,
                    null);

            result =
                extractProcedureParametersMetadata(
                    t_rsProcedureParameters, metadataExtractionListener);

            t_rsProcedureParameters.close();
        }
        catch  (final SQLException sqlException)
        {
            logWarn(
                "Cannot retrieve the procedure parameter names.",
                sqlException);

            throw sqlException;
        }

        if  (result == null)
        {
            result = EMPTY_PROCEDURE_PARAMETER_METADATA_ARRAY;
        }

        return result;
    }

    /**
     * Extracts the procedure parameter metadata from given result set.
     * @param resultSet the result set with the procedure parameter
     * information.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of procedure parameter metadata.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected ProcedureParameterMetadata[] extractProcedureParametersMetadata(
        final ResultSet resultSet,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        ProcedureParameterMetadata[] result =
            EMPTY_PROCEDURE_PARAMETER_METADATA_ARRAY;

        List t_lProcedureParameterList = new ArrayList();

        if  (resultSet.next())
        {
            String t_strColumnName;
            int t_iColumnType;
            String t_strComment;
            int t_iDataType;
            int t_iLength;
            int t_iNullable;
            
            while  (resultSet.next()) 
            {
                t_strColumnName = resultSet.getString("COLUMN_NAME");
                t_iColumnType = (int) resultSet.getShort("COLUMN_TYPE");
                t_strComment = resultSet.getString("REMARKS");
                t_iDataType = resultSet.getInt("DATA_TYPE");
                t_iLength = resultSet.getInt("LENGTH");
                t_iNullable = (int) resultSet.getShort("NULLABLE");
                
                t_lProcedureParameterList.add(
                    new ProcedureParameterMetadata(
                        t_strColumnName,
                        t_iColumnType,
                        t_strComment,
                        t_iDataType,
                        t_iLength,
                        t_iNullable));

                if  (metadataExtractionListener != null)
                {
                    metadataExtractionListener
                        .procedureParameterMetadataExtracted(
                            t_strColumnName,
                            t_iColumnType,
                            t_strComment,
                            t_iDataType,
                            t_iLength,
                            t_iNullable);
                }
            }
        }

        result =
            (ProcedureParameterMetadata[])
                t_lProcedureParameterList.toArray(result);

        return result;
    }
    
    /**
     * Extracts concrete field from given result set.
     * @param resultSet the result set with the table information.
     * @param field the field name.
     * @param table the table name.
     * @param nameField the name field.
     * @return the list of field values (position 0), and the field names (1).
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     * @precondition field != null
     */
    protected String[][] extractStringFields(
        final ResultSet resultSet,
        final Field field,
        final String table,
        final Field nameField)
      throws  SQLException
    {
        return
            extractStringFields(
                resultSet,
                field.toSimplifiedString(),
                table,
                (nameField != null) ? nameField.toSimplifiedString() : null);
    }

    /**
     * Extracts fields from given result set.
     * @param resultSet the result set with the table information.
     * @param field the field name.
     * @param table the table name.
     * @param nameField the name field.
     * @return the list of field values (position 0), and the field names (1).
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     * @precondition field != null
     */
    protected String[][] extractStringFields(
        final ResultSet resultSet,
        final String field,
        final String table,
        final String nameField)
      throws  SQLException
    {
        String[][] result = new String[2][0];

        Collection t_cFields = new ArrayList();

        boolean t_bExtractNames = (nameField != null);
        Collection t_cNames = null;

        if  (t_bExtractNames)
        {
            t_cNames = new ArrayList();
        }

        while  (resultSet.next()) 
        {
            t_cFields.add(resultSet.getString(field));

            if  (t_bExtractNames)
            {
                t_cNames.add(resultSet.getString(nameField));
            }
        }

        result[0] = new String[t_cFields.size()];
        t_cFields.toArray(result[0]);

        if  (t_bExtractNames)
        {
            result[1] = new String[t_cNames.size()];
            t_cNames.toArray(result[1]);
        }

        return result;
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param field the field.
     * @param table the table.
     * @param size the number of fields to extract.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of column types.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected int[] extractColumnTypes(
        final ResultSet resultSet,
        final Field field,
        final String table,
        final int size,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        return
            extractColumnTypes(
                resultSet,
                field.toSimplifiedString(),
                table,
                size,
                metadataExtractionListener);
    }

    /**
     * Extracts the primary keys.
     * @param metaData the database metadata.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param metadataExtractionListener the metadata extraction listener.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     * @precondition metaData != null
     */
    protected void extractPrimaryKeys(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        try 
        {
            String[] t_astrTableNames = getTableNames();

            String t_strTableName;

            int t_iLength =
                (t_astrTableNames != null) ? t_astrTableNames.length : 0;

            for  (int t_iTableIndex = 0;
                      t_iTableIndex < t_iLength;
                      t_iTableIndex++)
            {
                t_strTableName = t_astrTableNames[t_iTableIndex];

                ResultSet t_rsPrimaryKeys =
                    metaData.getPrimaryKeys(
                        catalog, schema, t_strTableName);

                if  (t_rsPrimaryKeys != null)
                {
                    while  (t_rsPrimaryKeys.next())
                    {
                        addPrimaryKey(
                            t_strTableName,
                            t_rsPrimaryKeys.getString("COLUMN_NAME"));
                    }

                    if  (metadataExtractionListener != null)
                    {
                        metadataExtractionListener.primaryKeyExtracted(
                            t_strTableName,
                            getPrimaryKey(t_strTableName));
                    }

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
     * @param metadataExtractionListener the metadata extraction listener.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     * @precondition metaData != null
     */
    protected void extractForeignKeys(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        try 
        {
            String[] t_astrTableNames = getTableNames();

            String t_strTableName;

            int t_iLength =
                (t_astrTableNames != null) ? t_astrTableNames.length : 0;
            
            for  (int t_iTableIndex = 0;
                      t_iTableIndex < t_iLength;
                      t_iTableIndex++)
            {
                t_strTableName = t_astrTableNames[t_iTableIndex];

                ResultSet t_rsForeignKeys =
                    metaData.getImportedKeys(catalog, schema, t_strTableName);

                String t_strFkTableName = null;
                String t_strPreviousFkTableName = null;
                Collection t_cPkColumnNames = new ArrayList();
                Collection t_cFkColumnNames = new ArrayList();

                int t_iPkColumnCount =
                    getPrimaryKeyColumnCount(t_strTableName);
                
                int t_iIndex = 0;

                if  (t_rsForeignKeys != null)
                {
                    while  (t_rsForeignKeys.next())
                    {
                        t_strFkTableName =
                            t_rsForeignKeys.getString("FKTABLE_NAME");

                        if  (   (   (t_strFkTableName == null)
                                 || (!t_strFkTableName.equals(
                                         t_strPreviousFkTableName)))
                             || (t_iIndex == t_iPkColumnCount))
                        {
                            addForeignKey(
                                t_strTableName,
                                (String[])
                                    t_cPkColumnNames.toArray(
                                        EMPTY_STRING_ARRAY),
                                t_strPreviousFkTableName,
                                (String[])
                                    t_cFkColumnNames.toArray(
                                        EMPTY_STRING_ARRAY));

                            t_cPkColumnNames = new ArrayList();
                            t_cFkColumnNames = new ArrayList();
                            t_iIndex = 0;

                            if  (metadataExtractionListener != null)
                            {
                                metadataExtractionListener.foreignKeyExtracted(
                                    t_strTableName,
                                    t_strFkTableName,
                                    (String[])
                                        t_cPkColumnNames.toArray(
                                            EMPTY_STRING_ARRAY));
                            }
                        }

                        t_cPkColumnNames.add(
                            t_rsForeignKeys.getString("PKCOLUMN_NAME"));
                    
                        t_cFkColumnNames.add(
                            t_rsForeignKeys.getString("FKCOLUMN_NAME"));

                        t_strPreviousFkTableName = t_strFkTableName;

                        t_iIndex++;
                    }

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
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of all table names.
     * @throws SQLException if the database operation fails.
     * @precondition metaData != null
     */
    protected String getTableComment(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String tableName,
        final MetadataExtractionListener metadataExtractionListener)
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

            result = extractComment(t_rsTables);

            if  (metadataExtractionListener != null)
            {
                metadataExtractionListener.tableCommentExtracted(
                    tableName, result);
            }
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
     * Retrieves the column comments.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of all table names.
     * @throws SQLException if the database operation fails.
     * @precondition metaData != null
     */
    protected String getColumnComment(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String tableName,
        final String columnName,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        String result = "";

        ResultSet t_rsColumns = null;

        try 
        {
            try 
            {
                t_rsColumns =
                    metaData.getColumns(
                        catalog,
                        schema,
                        tableName,
                        columnName);
            }
            catch  (final SQLException sqlException)
            {
                throw
                    new QueryJException(
                        "cannot.retrieve.database.table.names",
                        sqlException);
            }

            result = extractComment(t_rsColumns);

            if  (metadataExtractionListener != null)
            {
                metadataExtractionListener.columnCommentExtracted(
                    tableName, columnName, result);
            }
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
            if  (t_rsColumns != null)
            {
                t_rsColumns.close();
            }
        }

        return result;
    }

    /**
     * Extracts the comment from given result set.
     * @param resultSet the result set with the table information.
     * @return the list of column names.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected String extractComment(final ResultSet resultSet)
      throws  SQLException
    {
        String result = "";

        if  (resultSet.next())
        {
            result = resultSet.getString("REMARKS");
        }

        return result;
    }

    /**
     * Extracts concrete integer fields from given result set.
     * @param resultSet the result set with the table information.
     * @param field the field name.
     * @param table the table.
     * @param nameField the column identifying the column names.
     * @return the list of field values, plus the column names.
     * @throws SQLException if the database operation fails.
     */
    protected String[][] extractIntFields(
        final ResultSet resultSet,
        final String field,
        final String table,
        final String nameField)
      throws  SQLException
    {
        return extractIntFields(resultSet, field, table, nameField, -1);
    }

    /**
     * Extracts concrete integer fields from given result set.
     * @param resultSet the result set with the table information.
     * @param field the field name.
     * @param table the table.
     * @param nameField the column identifying the column names.
     * @param size the number of fields to extract.
     * @return the list of field values, plus the column names.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     * @precondition field != null
     */
    protected String[][] extractIntFields(
        final ResultSet resultSet,
        final String field,
        final String table,
        final String nameField,
        final int size)
      throws  SQLException
    {
        String[][] result = new String[2][0];

        boolean t_bBounded = (size > 0);

        Collection t_cResult = null;

        boolean t_bExtractNames = (nameField != null);

        Collection t_cNames = null;

        if  (t_bBounded)
        {
            result[0] = new String[size];

            if  (t_bExtractNames)
            {
                result[1] = new String[size];
            }
        }
        else
        {
            t_cResult = new ArrayList();
            t_cNames = new ArrayList();
        }
            
        int t_iCounter = 0;

        int t_iCurrentRecord = -1;

        while  (resultSet.next())
        {
            t_iCurrentRecord = resultSet.getInt(field);

            if  (t_bBounded)
            {
                result[0][t_iCounter] = "" + t_iCurrentRecord;

                if  (t_bExtractNames)
                {
                    result[1][t_iCounter] = resultSet.getString(nameField);
                }

                if  (t_iCounter == size - 1)
                {
                    break;
                }

                t_iCounter++;
            }
            else
            {
                t_cResult.add(new Integer(t_iCurrentRecord));
                t_cNames.add(resultSet.getString(nameField));
            }
        }

        if  (!t_bBounded)
        {
            result[0] = new String[t_cResult.size()];

            t_iCounter = 0;

            Iterator t_itResults = t_cResult.iterator();

            if  (t_itResults != null)
            {
                while  (t_itResults.hasNext())
                {
                    result[0][t_iCounter++] =
                        "" + ((Integer) t_itResults.next()).intValue();
                }
            }

            result[1] = new String[t_cNames.size()];
            t_cNames.toArray(result[1]);
        }

        return result;
    }

    /**
     * Builds a key to store the referring tables.
     * @return such key.
     */
    protected String buildReferingTablesKey()
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
    protected String buildRefTableKey(
        final String tableName, final String[] foreignKey)
    {
        return
              ".|_|referred|_table|_|.," + tableName
            + "$$" + concat(foreignKey, ",");
    }

    /**
     * Logs a verbose message.
     * @param message the message to log.
     * @precondition message != null
     */
    protected void logVerbose(final String message)
    {
        Log t_Log = UniqueLogFactory.getLog(AbstractJdbcMetadataManager.class);

        if  (t_Log != null)
        {
            t_Log.trace(message);
        }
    }

    /**
     * Logs a warning message.
     * @param message the message to log.
     * @param exception the exception
     * @precondition message != null
     * @precondition exception != null
     */
    protected void logWarn(
        final String message, final Exception exception)
    {
        Log t_Log = UniqueLogFactory.getLog(AbstractJdbcMetadataManager.class);

        if  (t_Log != null)
        {
            t_Log.warn(message, exception);
        }
    }

    /**
     * Concatenates given array using a separator.
     * @param values the values.
     * @param separator the separator.
     * @return the result of concatenating given values.
     * @precondition values != null
     * @precondition separator != null
     */
    protected String concat(
        final String[] values, final String separator)
    {
        StringBuffer result = new StringBuffer();

        int t_iLength = (values != null) ? values.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            if  (values[t_iIndex] != null)
            {
                result.append(values[t_iIndex]);
            
                if  (   (t_iIndex < values.length - 1)
                     && (anythingElseToConcatenate(
                             values, t_iIndex + 1)))
                {
                    result.append(separator);
                }
            }
        }

        return result.toString();
    }

    /**
     * Checks whether there's something to concatenate.
     * @param values the values.
     * @param start the index to start from.
     * @return <code>true</code> if there are still items to concatenate.
     * @precondition values != null
     * @precondition start >= 0;
     * @precondition start < values.length
     */
    protected boolean anythingElseToConcatenate(
        final String[] values, final int start)
    {
        boolean result = false;

        int t_iLength = (values != null) ? values.length : 0;
        
        for  (int t_iIndex = start; t_iIndex < t_iLength; t_iIndex++)
        {
            if  (values[t_iIndex] != null)
            {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Clones given String array.
     * @param array the array to clone.
     * @return the cloned array.
     * @precondition array != null
     */
    protected ProcedureMetadata[] clone(final ProcedureMetadata[] array)
    {
        ProcedureMetadata[] result = EMPTY_PROCEDURE_METADATA_ARRAY;

        int t_iCount = (array != null) ? array.length : 0;

        if  (t_iCount > 0)
        {
            result = new ProcedureMetadata[t_iCount];

            for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                result[t_iIndex] = array[t_iIndex];
            }
        }

        return result;
    }

    /**
     * Clones given String array.
     * @param array the array to clone.
     * @return the cloned array.
     * @precondition array != null
     */
    protected String[] clone(final String[] array)
    {
        String[] result = EMPTY_STRING_ARRAY;

        int t_iCount = (array != null) ? array.length : 0;

        if  (t_iCount > 0)
        {
            result = new String[t_iCount];

            for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                result[t_iIndex] = array[t_iIndex];
            }
        }

        return result;
    }

    /**
     * Retrieves the parent table in the ISA relationship, if any.
     * @param table the table.
     * @return the parent's table name, or <code>null</code> otherwise.
     */
    public String getParentTable(final String table)
    {
        return getParentTable(table, MetaLanguageUtils.getInstance());
    }
    
    /**
     * Retrieves the parent table in the ISA relationship, if any.
     * @param table the table.
     * @param metaLanguageUtils the <code>MetaLanguageUtils</code> instance.
     * @return the parent's table name, or <code>null</code> otherwise.
     * @precondition metaLanguageUtils != null
     */
    protected String getParentTable(
        final String table, final MetaLanguageUtils metaLanguageUtils)
    {
        return
            metaLanguageUtils.retrieveDeclaredParent(getTableComment(table));
    }

    /**
     * Converts given array to integer.
     * @param array the array to convert.
     * @return the converted array.
     * @precondition array != null
     */
    protected int[] toIntArray(final Object[] array)
    {
        int[] result =
            (array == null) ? EMPTY_INT_ARRAY : new int[array.length];

        int t_iCount = result.length;
        
        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            try
            {
                result[t_iIndex] = Integer.parseInt("" + array[t_iIndex]);
            }
            catch  (final NumberFormatException numberFormatException)
            {
                // skipping the whole stuff
                result = EMPTY_INT_ARRAY;
                break;
            }
        }
        
        return result;
    }

    /**
     * Converts given array to integer.
     * @param array the array to convert.
     * @return the converted array.
     * @precondition array != null
     */
    protected Integer[] toIntegerArray(final int[] array)
    {
        Integer[] result =
            (array == null) ? EMPTY_INTEGER_ARRAY : new Integer[array.length];

        int t_iCount = result.length;
        
        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            result[t_iIndex] = new Integer(array[t_iIndex]);
        }
        
        return result;
    }

    /**
     * Converts given array to boolean.
     * @param array the array to convert.
     * @return the converted array.
     * @precondition array != null
     */
    protected boolean[] toBoolArray(final Object[] array)
    {
        boolean[] result =
            (array == null) ? EMPTY_BOOL_ARRAY : new boolean[array.length];

        int t_iCount = result.length;
        
        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            result[t_iIndex] = Boolean.valueOf("" + array[t_iIndex]).booleanValue();
        }
        
        return result;
    }

    /**
     * Converts given array to boolean.
     * @param array the array to convert.
     * @return the converted array.
     * @precondition array != null
     */
    protected Boolean[] toBooleanArray(final boolean[] array)
    {
        Boolean[] result = (array == null) ? EMPTY_BOOLEAN_ARRAY : new Boolean[array.length];

        int t_iCount = result.length;
        
        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            result[t_iIndex] = Boolean.valueOf(array[t_iIndex]);
        }
        
        return result;
    }
}
