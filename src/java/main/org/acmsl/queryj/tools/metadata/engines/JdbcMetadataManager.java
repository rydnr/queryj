/*
                        QueryJ

    Copyright (C) 2001-2005  Jose San Leandro Armendariz
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
 * Description: Manages the information provided by database metadata,
 *              using plain JDBC.
 *
 */
package org.acmsl.queryj.tools.metadata.engines;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.Field;
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.ProcedureMetadata;
import org.acmsl.queryj.tools.metadata.ProcedureParameterMetadata;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Manages the information provided by database metadata, using plain JDBC.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class JdbcMetadataManager
    extends AbstractJdbcMetadataManager
    implements MetadataManager
{
    /**
     * Creates an empty <code>JdbcMetadataManager</code> instance..
     */
    public JdbcMetadataManager()
    {
        super();
    }

    /**
     * Creates a <code>JdbcMetadataManager</code> using given information.
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
     * @precondition tableNames != null
     * @precondition procedureNames != null
     * @precondition metaData != null
     */
    protected JdbcMetadataManager(
        final String[] tableNames,
        final String[] procedureNames,
        final boolean disableTableExtraction,
        final boolean lazyTableExtraction,
        final boolean disableProcedureExtraction,
        final boolean lazyProcedureExtraction,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final boolean retrieveMetaData)
      throws  SQLException,
              QueryJException
    {
        super(
            tableNames,
            procedureNames,
            disableTableExtraction,
            lazyTableExtraction,
            disableProcedureExtraction,
            lazyProcedureExtraction,
            metaData,
            catalog,
            schema,
            retrieveMetaData);
    }

    /**
     * Creates a <code>JdbcMetadataManager</code> using given information.
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
    public JdbcMetadataManager(
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
     * Creates a <code>JdbcMetadataManager</code> using given information.
     * @param tableNames explicitly specified table names.
     * @param procedureNames explicitly specified procedure names.
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
    protected JdbcMetadataManager(
        final String[] tableNames,
        final String[] procedureNames,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema)
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
     * Creates a <code>JdbcMetadataManager</code> using given information.
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
     * @precondition tableNames != null
     * @precondition procedureNames != null
     * @precondition metaData != null
     */
    protected JdbcMetadataManager(
        final String[] tableNames,
        final String[] procedureNames,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final boolean retrieveMetaData)
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
    protected void retrieveMetadata(
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
        immutableRetrieveMetadata(
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
        return immutableGetMetaData();
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
        return immutableGetCatalog();
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
        return immutableGetSchema();
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
        return immutableGetDisableTableExtraction();
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
        return immutableGetLazyTableExtraction();
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
        return immutableGetDisableProcedureExtraction();
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
        return immutableGetLazyProcedureExtraction();
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
        return immutableGetTableNames();
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
        return immutableGetTableComments();
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
        immutableAddTableComment(tableName, tableComment);
    }

    /**
     * Retrieves the table comments.
     * @param tableName the table name.
     * @return the table comment.
     * @precondition tableName != null
     */
    public String getTableComment(final String tableName)
    {
        return immutableGetTableComment(tableName);
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
        return immutableGetColumnNames();
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
        immutableAddColumnNames(tableName, columnNames);
    }

    /**
     * Retrieves the column names.
     * @param tableName the table name.
     * @return the column names.
     * @precondition tableName != null
     */
    public String[] getColumnNames(final String tableName)
    {
        return immutableGetColumnNames(tableName);
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
        return immutableGetColumnTypes();
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
        return immutableGetColumnType(tableName, columnName);
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
        immutableAddColumnType(tableName, columnName, columnType);
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
        return immutableGetAllowNulls();
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
        return immutableAllowsNull(tableName, allowName);
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
        return immutableAllowsNull(tableName, allowNames);
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
        return immutableGetAllowNull(tableName, allowName);
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
        return immutableGetAllowNull(tableName, allowNames);
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
        immutableAddAllowNull(tableName, columnName, flag);
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
        return immutableGetPrimaryKeys();
    }

    /**
     * Adds a primary key.
     * @param tableName the table name.
     * @param columnName the column name.
     * @precondition tableName != null
     * @precondition columnName != null
     */
    public void addPrimaryKey(final String tableName, final String columnName)
    {
        immutableAddPrimaryKey(tableName, columnName);
    }

    /**
     * Retrieves the number of columns building the primary key.
     * @param tableName the table name.
     * @return the primary keys.
     * @precondition tableName != null
     */
    public int getPrimaryKeyColumnCount(final String tableName)
    {
        return immutableGetPrimaryKeyColumnCount(tableName);
    }
    
    /**
     * Retrieves the primary key.
     * @param tableName the table name.
     * @return the primary key.
     * @precondition tableName != null
     */
    public String[] getPrimaryKey(final String tableName)
    {
        return immutableGetPrimaryKey(tableName);
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
        return immutableIsPartOfPrimaryKey(tableName, fieldName);
    }

    /**
     * Checks whether given field is a primary key or not.
     * @param fieldName the field name.
     * @param primaryKey the primary key.
     * @return <code>true</code> if such field identifies a concrete row.
     * @precondition fieldName != null
     */
    protected boolean isPartOfPrimaryKey(
        final String fieldName, final String[] primaryKey)
    {
        return immutableIsPartOfPrimaryKey(fieldName, primaryKey);
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
        return immutableGetForeignKeys();
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
        immutableAddForeignKey(
            tableName, columnNames, refTableName, refColumnNames);
    }

    /**
     * Retrieves the tables refering to given table's.
     * @param tableName the table name.
     * @return such tables.
     * @precondition tableName != null
     */
    public String[] getReferingTables(final String tableName)
    {
        return immutableGetReferingTables(tableName);
    }
    
    /**
     * Retrieves the foreign keys of given table.
     * @param tableName the table name.
     * @return its foreign keys.
     * @precondition tableName != null
     */
    public String[][] getForeignKeys(final String tableName)
    {
        return immutableGetForeignKeys(tableName);
    }

    /**
     * Checks whether given table contains foreign keys.
     * @param tableName the table name.
     * @return <code>true</code> in such case.
     * @precondition tableName != null
     */
    public boolean containsForeignKeys(final String tableName)
    {
        return immutableContainsForeignKeys(tableName);
    }

    /**
     * Retrieves the tables referred by given table's foreign keys.
     * @param tableName the table name.
     * @return such tables.
     * @precondition tableName != null
     */
    public String[] getReferredTables(final String tableName)
    {
        return immutableGetReferredTables(tableName);
    }

    /**
     * Retrieves the field of given table that points to a field on the referred one.
     * @param tableName the table name.
     * @param refTableName the referred table name.
     * @return such field.
     * @precondition tableName != null
     * @precondition refTableName != null
     */
    public String[][] getForeignKeys(
        final String tableName, String refTableName)
    {
        return immutableGetForeignKeys(tableName, refTableName);
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
        return immutableGetReferredTable(tableName, foreignKey);
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
        return immutableGetReferredKeys(tableName, refTableName);
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
        return immutableGetExternallyManagedFields();
    }

    /**
     * Annotates a externally-managed field.
     * @param tableName the table name.
     * @param columnName the column name.
     */
    public void addExternallyManagedField(
        final String tableName, final String columnName)
    {
        immutableAddExternallyManagedField(tableName, columnName);
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
        immutableAddExternallyManagedField(
            tableName, columnName, keyword);
    }

    /**
     * Annotates a externally-managed field.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param keyword the keyword.
     * @param query the retrieval query.
     */
    public void addExternallyManagedField(
        final String tableName,
        final String columnName,
        final String keyword,
        final String query)
    {
        immutableAddExternallyManagedField(
            tableName, columnName, keyword, query);
    }
    
    /**
     * Retrieves the externally-managed fields.
     * @param tableName the table name.
     * @return the externally-managed fields of such table.
     */
    public String[] getExternallyManagedFields(final String tableName)
    {
        return immutableGetExternallyManagedFields(tableName);
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
        return immutableIsManagedExternally(tableName, fieldName);
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
        return immutableGetKeyword(tableName, fieldName);
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
        return
            immutableGetExternallyManagedFieldRetrievalQuery(
                tableName, fieldName);
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
        return immutableGetProcedureNames();
    }

    /**
     * Specifies the procedures metadata.
     * @param proceduresMetaData such metadata.
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
    public ProcedureMetadata[] getProceduresMetadata()
    {
        return immutableGetProceduresMetadata();
    }

    /**
     * Specifies the procedure parameter metadata.
     * @param map the parameter metadata map.
     */
    protected void setProcedureParametersMetadata(Map map)
    {
        immutableSetProcedureParametersMetadata(map);
    }

    /**
     * Retrieves the procedure parameters metadata.
     * @return such map.
     */
    protected Map getProcedureParametersMetadata()
    {
        return immutableGetProcedureParametersMetadata();
    }

    /**
     * Adds the parameters of given procedure.
     * @param procedureName the procedure name.
     * @param parametersMetadata the parameters metadata.
     * @precondition parametersMetaData != null
     */
    protected void addProcedureParametersMetadata(
        final String procedureName,
        final ProcedureParameterMetadata[] parametersMetadata)
    {
        immutableAddProcedureParametersMetadata(
            procedureName, parametersMetadata);
    }

    /**
     * Retrieves the parameters of given procedure.
     * @param procedure the procedure.
     * @return the parameters metadata.
     */
    public ProcedureParameterMetadata[] getProcedureParametersMetadata(
        final ProcedureMetadata procedure)
    {
        return immutableGetProcedureParametersMetadata(procedure);
    }

    /**
     * Retrieves the parameters of given procedure.
     * @param procedureName the procedure name.
     * @return the parameters metadata.
     */
    public ProcedureParameterMetadata[] getProcedureParametersMetadata(
        final String procedureName)
    {
        return immutableGetProcedureParametersMetadata(procedureName);
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
    protected void extractTableMetadata(
        final String[] tableNames,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema)
      throws  SQLException,
              QueryJException
    {
        immutableExtractTableMetadata(
            tableNames,
            metaData,
            catalog,
            schema);
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
    protected void extractProcedureMetadata(
        final String[] procedureNames,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema)
      throws  SQLException,
              QueryJException
    {
        immutableExtractProcedureMetadata(
            procedureNames,
            metaData,
            catalog,
            schema);
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
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema)
      throws  SQLException,
              QueryJException
    {
        return immutableGetTableNames(metaData, catalog, schema);
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
        return immutableExtractTableNames(resultSet);
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
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String tableName)
      throws  SQLException,
              QueryJException
    {
        return
            immutableGetColumnNames(
                metaData,
                catalog,
                schema,
                tableName);
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
        return immutableExtractColumnNames(resultSet, field);
    }
    
    /**
     * Extracts the column names from given result set.
     * @param resultSet the result set with the column information.
     * @return the list of column names.
     * @throws SQLException if the database operation fails.
     */
    protected String[] extractColumnNames(final ResultSet resultSet)
        throws  SQLException
    {
        return immutableExtractColumnNames(resultSet);
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
        return immutableExtractColumnNames(resultSet, fieldName);
    }
    
    /**
     * Retrieves the column types from given table name.
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
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String tableName,
        final int size)
      throws  SQLException,
              QueryJException
    {
        return
            immutableGetColumnTypes(
                metaData,
                catalog,
                schema,
                tableName,
                size);
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
        return immutableExtractColumnTypes(resultSet, field);
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
        return immutableExtractColumnTypes(resultSet, size);
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
        return immutableExtractColumnTypes(resultSet, fieldName);
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
        return immutableExtractColumnTypes(resultSet, fieldName, size);
    }

    /**
     * Retrieves the null flag from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param size the number of fields to extract.
     * @return the list of all column names.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if any other error occurs.
     */
    protected boolean[] getAllowNulls(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String tableName,
        final int size)
      throws  SQLException,
              QueryJException
    {
        return
            immutableGetAllowNulls(
                metaData,
                catalog,
                schema,
                tableName,
                size);
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
        return immutableExtractAllowNull(resultSet, field);
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
        return immutableExtractAllowNull(resultSet, size);
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
        return immutableExtractAllowNull(resultSet, fieldName);
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
        return immutableExtractAllowNull(resultSet, fieldName, size);
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
        return immutableGetProcedureNames(metaData, catalog, schema);
    }
    
    /**
     * Retrieves the procedures' meta data.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @return the list of all procedure metadata.
     * @throws SQLException if the database operation fails.
     * @precondition metaData != null
     */
    protected ProcedureMetadata[] getProceduresMetadata(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema)
      throws  SQLException,
              QueryJException
    {
        return immutableGetProceduresMetadata(metaData, catalog, schema);
    }
    
    /**
     * Extracts the procedures' metadata from given result set.
     * @param resultSet the result set with the procedure information.
     * @return the list of procedure metadata.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected ProcedureMetadata[] extractProceduresMetadata(
        final ResultSet resultSet)
      throws  SQLException
    {
        return immutableExtractProceduresMetadata(resultSet);
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
        return
            immutableGetProcedureParameterNames(
                metaData, catalog, schema, procedureName);
    }

    /**
     * Retrieves the procedure parameter names from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param procedureName the procedure name.
     * @return the list of all procedure parameter names.
     * @throws SQLException if the database operation fails.
     * @precondition metaData != null
     */
    protected ProcedureParameterMetadata[] getProcedureParametersMetadata(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String procedureName)
      throws  SQLException
    {
        return
            immutableGetProcedureParametersMetadata(
                metaData, catalog, schema, procedureName);
    }
    
    /**
     * Extracts the procedure parameter metadata from given result set.
     * @param resultSet the result set with the procedure parameter
     * information.
     * @return the list of procedure parameter metadata.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected ProcedureParameterMetadata[] extractProcedureParametersMetadata(
        final ResultSet resultSet)
      throws  SQLException
    {
        return immutableExtractProcedureParametersMetadata(resultSet);
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
        return immutableExtractStringFields(resultSet, field);
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
        return immutableExtractStringFields(resultSet, field);
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
        return immutableExtractColumnTypes(resultSet, field, size);
    }
    
    /**
     * Extracts the primary keys.
     * @param metaData the database metadata.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     * @precondition metaData != null
     */
    protected void extractPrimaryKeys(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema)
      throws  SQLException,
              QueryJException
    {
        immutableExtractPrimaryKeys(metaData, catalog, schema);
    }
    
    /**
     * Extracts the foreign keys.
     * @param metaData the database metadata.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     * @precondition metaData != null
     */
    protected void extractForeignKeys(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema)
      throws  SQLException,
              QueryJException
    {
        immutableExtractForeignKeys(metaData, catalog, schema);
    }

    /**
     * Retrieves the table comments.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @return the list of all table names.
     * @throws SQLException if the database operation fails.
     * @precondition metaData != null
     */
    protected String getTableComment(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final String tableName)
      throws  SQLException,
              QueryJException
    {
        return
            immutableGetTableComment(
                metaData,
                catalog,
                schema,
                tableName);
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
        return immutableExtractTableComment(resultSet);
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
        return immutableExtractIntFields(resultSet, field);
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
        return immutableExtractIntFields(resultSet, field, size);
    }
    
    /**
     * Logs a verbose message.
     * @param message the message to log.
     * @precondition message != null
     */
    protected void logVerbose(final String message)
    {
        immutableLogVerbose(message);
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
        immutableLogWarn(message, exception);
    }

    /**
     * Concatenates given array using a separator.
     * @param values the values.
     * @param separator the separator.
     * @return the result of concatenating given values.
     * @precondition values != null
     * @precondition separator != null
     */
    protected String concat(final String[] values, final String separator)
    {
        return immutableConcat(values, separator);
    }

    /**
     * Retrieves the type manager.
     * @return such instance.
     */
    public MetadataTypeManager getMetadataTypeManager()
    {
        return JdbcMetadataTypeManager.getInstance();
    }
}
