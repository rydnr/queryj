/*
                        QueryJ Core

    Copyright (C) 2001-today Jose San Leandro Armendariz
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
 * Filename: MetadataManager.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Manages the information provided by database metadata,
 *              using plain JDBC.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing some ACM-SL classes.
 */

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.api.exceptions.QueryJException;
    import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.Serializable;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Manages the information provided by database metadata, using plain JDBC.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public interface MetadataManagerOld
    extends Serializable
{
    /**
     * The system property prefix to disable generation for concrete (or all, with *) tables.
     */
    public static final String TABLES_DISABLED = "queryj.tables.disabled";

    /**
     * The system property to enable generation for concrete (or all, with * or missing property) tables.
     */
    public static final String TABLES_ENABLED = "queryj.tables.enabled";

    /**
     * Retrieves the name identifying the manager instance.
     * @return such name.
     */
    public String getName();

    /// EVERYTHING BELOW IS DEPRECATED //

    /**
     * Retrieves the metadata.
     * @throws SQLException if the metadata extraction fails.
     * @throws QueryJException if any other error occurs.
     */
    public void retrieveMetadata()
        throws  SQLException,
                QueryJException;

    /**
     * Retrieves the table names.
     * @return such names.
     */
    public String[] getTableNames();

    /**
     * Adds the comments of given table.
     * @param tableName the table name.
     * @param tableComment the table comment.
     */
    public void addTableComment(
        final String tableName, final String tableComment);

    /**
     * Retrieves the table comments.
     * @param tableName the table name.
     * @return the table comment.
     */
    public String getTableComment(final String tableName);

    /**
     * Adds the column names of given table.
     * @param tableName the table name.
     * @param columnNames the column names.
     */
    public void addColumnNames(
        @NotNull final String tableName, @NotNull final String[] columnNames);

    /**
     * Retrieves the column names.
     * @param tableName the table name.
     * @return the column names.
     */
    public String[] getColumnNames(final String tableName);

    /**
     * Retrieves all column names, including the parents' if any.
     * @param tableName the table name.
     * @return the column names.
     */
    @SuppressWarnings("unused")
    public String[] getAllColumnNames(final String tableName);

    /**
     * Retrieves the column type.
     * @param tableName the table name.
     * @param columnName the column name.
     * @return the column type.
     * @see java.sql.Types
     */
    public int getColumnType(
        final String tableName, final String columnName);
    
    /**
     * Adds a column type.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param columnType the column type.
     */
    public void addColumnType(
        final String tableName,
        final String columnName,
        final int columnType);

    /**
     * Retrieves the allow null.
     * @param tableName the table name.
     * @param allowName the allow name.
     * @return the allow null.
     */
    public boolean allowsNull(
        final String tableName, final String allowName);
    
    /**
     * Retrieves the allow null.
     * @param tableName the table name.
     * @param allowNames the allow names.
     * @return the allow null.
     */
    public boolean allowsNull(
        final String tableName, final String[] allowNames);

    /**
     * Retrieves whether given attribute allows nulls.
     * @param tableName the table name.
     * @param attributeName the attribute name.
     * @return such information.
     */
    public boolean getAllowNull(
        final String tableName, final String attributeName);

    /**
     * Retrieves whether given attributes allow nulls.
     * @param tableName the table name.
     * @param attributeNames the attribute names.
     * @return such information.
     */
    public boolean getAllowNull(
        final String tableName, final String[] attributeNames);

    /**
     * Adds a null flag.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param flag the flag.
     */
    public void addAllowNull(
        final String tableName,
        final String columnName,
        final boolean flag);

    /**
     * Retrieves the column comment.
     * @param tableName the table name.
     * @param columnName the column name.
     * @return the column comment.
     */
    public String getColumnComment(
        final String tableName, final String columnName);
    
    /**
     * Adds a primary key.
     * @param tableName the table name.
     * @param columnName the column name.
     */
    public void addPrimaryKey(final String tableName, final String columnName);

    /**
     * Retrieves the number of columns building the primary key.
     * @param tableName the table name.
     * @return the primary keys.
     */
    public int getPrimaryKeyColumnCount(final String tableName);
    
    /**
     * Retrieves the primary key.
     * @param tableName the table name.
     * @return the primary key.
     */
    public String[] getPrimaryKey(final String tableName);

    /**
     * Checks whether given field belongs to the primary key or not.
     * @param tableName the table name.
     * @param fieldName the field name.
     * @return <code>true</code> if such field is part of what dentifies a
     * concrete row.
     */
    public boolean isPartOfPrimaryKey(
        final String tableName, final String fieldName);

    /**
     * Checks whether given field belongs to the primary key or not.
     * @param tableName the table name.
     * @param fieldNames the field names.
     * @param parentTableName the parent table name.
     * @return <code>true</code> if such field is part of what dentifies a
     * concrete row.
     */
    public boolean pointsToPrimaryKey(
        final String tableName,
        final String[] fieldNames,
        final String parentTableName);

    /**
     * Adds a foreign key.
     * @param tableName the table name.
     * @param columnNames the column names.
     * @param refTableName the referred table name.
     * @param refColumnNames the referred column names.
     */
    public void addForeignKey(
        final String tableName,
        final String[] columnNames,
        final String refTableName,
        final String[] refColumnNames);

    /**
     * Retrieves the tables referring to given table's.
     * @param tableName the table name.
     * @return such tables.
     */
    @SuppressWarnings("unused")
    public String[] getReferingTables(final String tableName);
    
    /**
     * Retrieves the foreign keys of given table.
     * @param tableName the table name.
     * @return its foreign keys, a structure of
     * <pre>
     * [referredTableName1]
     *   {foreign-key1-attribute1,..foreign-key1-attributeN}
     * [referredTableName1]
     *   {foreign-key2-attribute1,..foreign-key2-attributeN}
     * ..
     * [referredTableName1]
     *   {foreign-keyN-attribute1,..foreign-keyN-attributeN}
     * ..
     * [referredTableNameN]
     *   {foreign-key1-attribute1,..foreign-key1-attributeN}
     * ..
     * [referredTableNameN]
     *   {foreign-keyN-attribute1,..foreign-keyN-attributeN}
     * </pre>
     */
    public String[][] getForeignKeys(final String tableName);

    /**
     * Checks whether given table contains foreign keys.
     * @param tableName the table name.
     * @return <code>true</code> in such case.
     */
    public boolean containsForeignKeys(final String tableName);

    /**
     * Retrieves the tables referred by given table's foreign keys.
     * @param tableName the table name.
     * @return such tables.
     */
    public String[] getReferredTables(final String tableName);

    /**
     * Retrieves the field of given table that points to a field on the referred one.
     * @param tableName the table name.
     * @param refTableName the referred table name.
     * @return such field.
     */
    public String[][] getForeignKeys(
        final String tableName, String refTableName);

    /**
     * Retrieves the table referred by given foreign key.
     * @param tableName the table name.
     * @param foreignKey the foreign key.
     * @return the referred table name.
     */
    public String getReferredTable(
        final String tableName, final String[] foreignKey);
    
    /**
     * Retrieves the field of referred table pointed by a field on the
     * original one.
     * @param tableName the table name.
     * @param refTableName the referred table name.
     * @return such field.
     */
    public String[][] getReferredKeys(
        final String tableName, String refTableName);

    /**
     * Annotates a externally-managed field.
     * @param tableName the table name.
     * @param columnName the column name.
     */
    @SuppressWarnings("unused")
    public void addExternallyManagedField(
        final String tableName, final String columnName);

    /**
     * Annotates a externally-managed field.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param keyword the keyword.
     */
    public void addExternallyManagedField(
        final String tableName,
        final String columnName,
        final String keyword);

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
        final String query);
    
    /**
     * Retrieves the externally-managed fields.
     * @param tableName the table name.
     * @return the externally-managed fields of such table.
     */
    public String[] getExternallyManagedFields(final String tableName);

    /**
     * Checks whether given field is externally managed or not.
     * @param tableName the table name.
     * @param fieldName the field name.
     * @return <code>true</code> if such field is managed externally.
     */
    public boolean isManagedExternally(
        final String tableName, String fieldName);

    /**
     * Retrieves the keyword used to create new values of given field.
     * @param tableName the table name.
     * @param fieldName the field name.
     * @return such keyword, or <code>null</code> if such information is
     * unknown.
     */
    public String getKeyword(final String tableName, String fieldName);

    /**
     * Retrieves the keyword used to create new values of given field.
     * @param tableName the table name.
     * @param fieldName the field name.
     * @return such keyword, or <code>null</code> if such information is
     * unknown.
     */
    public String getExternallyManagedFieldRetrievalQuery(
        final String tableName, String fieldName);
    
    /**
     * Retrieves the procedures metadata.
     * @return such metadata.
     */
    public ProcedureMetadata[] getProceduresMetadata();

    /**
     * Retrieves the parameters of given procedure.
     * @param procedure the procedure.
     * @return the parameters metadata.
     */
    public ProcedureParameterMetadata[] getProcedureParametersMetadata(
        final ProcedureMetadata procedure);

    /**
     * Retrieves the parameters of given procedure.
     * @param procedureName the procedure name.
     * @return the parameters metadata.
     */
    public ProcedureParameterMetadata[] getProcedureParametersMetadata(
        final String procedureName);

    /**
     * Retrieves the type manager.
     * @return such instance.
     */
    public MetadataTypeManager getMetadataTypeManager();

    /**
     * Checks whether the engine requires specific CLOB handling.
     * @return <code>true</code> in such case.
     */
    public boolean requiresCustomClobHandling();

    /**
     * Retrieves the parent table in the ISA relationship, if any.
     * @param table the table.
     * @return the parent's table name, or <code>null</code> otherwise.
     */
    public String getParentTable(final String table);

    /**
     * Retrieves whether the engine is case sensitive or not.
     * @return such information.
     */
    public boolean isCaseSensitive();

    /**
     * Retrieves whether given column is marked as <b>read-only</b> or not.
     * @param tableName the table name.
     * @param columnName the column name.
     * @return <code>true</code> in such case.
     */
    public boolean isReadOnly(
        final String tableName, final String columnName);

    /**
     * Retrieves whether given column is marked as <b>bool</b> or not.
     * @param tableName the table name.
     * @param columnName the column name.
     * @return <code>true</code> in such case.
     */
    public boolean isBoolean(
        final String tableName, final String columnName);

    /**
     * Retrieves the symbol for <code>true</code> values, in
     * boolean attributes.
     * @param tableName the table name.
     * @param columnName the column name.
     * @return such symbol.
     */
    public String getBooleanTrue(
        final String tableName, final String columnName);

    /**
     * Retrieves the symbol for <code>false</code> values, in
     * boolean attributes.
     * @param tableName the table name.
     * @param columnName the column name.
     * @return such symbol.
     */
    public String getBooleanFalse(
        final String tableName, final String columnName);

    /**
     * Retrieves the symbol for <code>null</code> values, in
     * boolean attributes.
     * @param tableName the table name.
     * @param columnName the column name.
     * @return such symbol.
     */
    public String getBooleanNull(
        final String tableName, final String columnName);

    /**
     * Retrieves whether given table is static or not.
     * @param table the table.
     * @return <code>true</code> in such case.
     */
    public boolean isTableStatic(final String table);

    /**
     * Retrieves whether given table is decorated or not.
     * @param table the table.
     * @return <code>true</code> in such case.
     */
    public boolean isTableDecorated(final String table);

    /**
     * Retrieves the referring tables.
     * @param tableName the table name.
     * @return the list of referring tables to given table.
     */
    public String[] getReferringTables(@NotNull final String tableName);

    /**
     * Specifies the database metadata to use, in case of offline use.
     * @param metaData the {@link DatabaseMetaData} instance.
     */
    public void setMetaData(@NotNull final DatabaseMetaData metaData);

    /**
     * Retrieves the {@link DatabaseMetaData}.
     * @return such instance.
     */
    public DatabaseMetaData getMetaData();

    /**
     * Checks whether the generation phase is enabled for given table.
     * @param tableName the table name.
     * @return <code>true</code> in such case.
     */
    boolean isGenerationAllowedForTable(@NotNull final String tableName);

    /**
     * Retrieves the engine name.
     * @return such information.
     */
    @NotNull
    String getEngineName();

    /**
     * Retrieves the engine version.
     * @return such information.
     */
    @NotNull
    String getEngineVersion();

    /**
     * Retrieves the identifier quote string.
     * @return such information.
     */
    @NotNull
    String getQuote();
}
