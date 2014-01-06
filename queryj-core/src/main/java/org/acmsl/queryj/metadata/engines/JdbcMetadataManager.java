/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
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
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: JdbcMetadataManager.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: MetadataManager implementation using plain JDBC.
 *
 * Date: 6/8/12
 * Time: 6:12 AM
 *
 */
package org.acmsl.queryj.metadata.engines;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.api.exceptions.QueryJException;
import org.acmsl.queryj.api.exceptions.CannotRetrieveDatabaseTableNamesException;
import org.acmsl.queryj.metadata.MetadataExtractionListener;
import org.acmsl.queryj.metadata.MetadataTypeManager;
import org.acmsl.queryj.metadata.TableDAO;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.AttributeIncompleteValueObject;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.ForeignKeyValueObject;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.metadata.vo.TableIncompleteValueObject;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * {@link org.acmsl.queryj.metadata.MetadataManager} implementation using
 * plain JDBC.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/08
 */
@ThreadSafe
public class JdbcMetadataManager
    extends AbstractJdbcMetadataManager
{

    private static final long serialVersionUID = -3287133509095459164L;

    /**
     * Creates a {@link JdbcMetadataManager} with given name.
     * @param name the name.
     */
    @SuppressWarnings("unused")
    public JdbcMetadataManager(@NotNull final String name)
    {
        super(name);
    }



    /**
     * Creates a {@link AbstractJdbcMetadataManager} with given information.
     * @param name the manager name.
     * @param metadata the {@link DatabaseMetaData} instance.
     * @param metadataExtractionListener the {@link MetadataExtractionListener} instance.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param tableNames the table names.
     * @param tables the list of tables.
     * @param disableTableExtraction whether to disable table extraction or not.
     * @param lazyTableExtraction whether to retrieve table information on demand.
     * @param caseSensitive whether it's case sensitive.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    public JdbcMetadataManager(
        @NotNull final String name,
        @Nullable final DatabaseMetaData metadata,
        @NotNull final MetadataExtractionListener metadataExtractionListener,
        @Nullable final String catalog,
        @Nullable final String schema,
        @NotNull final List<String> tableNames,
        @NotNull final List<Table<String, Attribute<String>, List<Attribute<String>>>> tables,
        final boolean disableTableExtraction,
        final boolean lazyTableExtraction,
        final boolean caseSensitive,
        @NotNull final String engineName,
        @NotNull final String engineVersion,
        @NotNull final String quote)
    {
        super(
            name,
            metadata,
            metadataExtractionListener,
            catalog,
            schema,
            tableNames,
            tables,
            disableTableExtraction,
            lazyTableExtraction,
            caseSensitive,
            engineName,
            engineVersion,
            quote);
    }

    /**
     * Retrieves the table names.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableNames the fixed table names to process.
     * @param metadataExtractionListener the metadata extraction listener.
     * @param caseSensitiveness whether the checks are case sensitive or not.
     * @return the list of all table names.
     * @throws SQLException if the database operation fails.
     */
    @Override
    protected List<TableIncompleteValueObject> extractTableNamesAndComments(
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema,
        @NotNull final List<String> tableNames,
        @NotNull final MetadataExtractionListener metadataExtractionListener,
        final boolean caseSensitiveness)
        throws  SQLException,
                QueryJException
    {
        @NotNull final List<TableIncompleteValueObject> result = new ArrayList<TableIncompleteValueObject>();

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
            catch  (@NotNull final SQLException sqlException)
            {
                throw
                    new CannotRetrieveDatabaseTableNamesException(catalog, schema, sqlException);
            }

            TableIncompleteValueObject t_Table;
            String t_strTableName;
            String t_strTableComment;

            while (t_rsTables.next())
            {
                t_strTableName = t_rsTables.getString(Literals.TABLE_NAME_U);
                t_strTableComment = t_rsTables.getString("REMARKS");

                if (passesFilter(t_strTableName, caseSensitiveness, tableNames))
                {
                    t_Table =
                        new TableIncompleteValueObject(
                            t_strTableName,
                            t_strTableComment);

                    result.add(t_Table);
                }
            }
        }
        catch  (final SQLException sqlException)
        {
            logWarn(
                "Cannot retrieve the table names.",
                sqlException);

            throw sqlException;
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
     * Retrieves the column information for given tables.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param caseSensitiveness whether the table names are case sensitive or not.
     * @param metadataExtractionListener the metadata extraction listener.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if the any other error occurs.
     */
    @Override
    protected void extractTableColumns(
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema,
        @NotNull final List<TableIncompleteValueObject> tables,
        final boolean caseSensitiveness,
        @Nullable final MetadataExtractionListener metadataExtractionListener)
        throws SQLException,
               QueryJException
    {
        final Map<String,List<AttributeIncompleteValueObject>> t_mAux =
            new HashMap<String,List<AttributeIncompleteValueObject>>(tables.size());

        final ResultSet t_rsColumns;
        String t_strTableName;
        String t_strColumnName;
        int t_iColumnType;
        String t_strTypeName;
        int t_iColumnSize;
        int t_iDecimalDigits;
        int t_iNullable;
        String t_strColumnComment;
        String t_strDefaultValue;
        //int t_iMaxStringBytes;
        int t_iOrdinalPosition;

        try
        {
            t_rsColumns =
                metaData.getColumns(
                    catalog,
                    schema,
                    null,
                    null);

            List<AttributeIncompleteValueObject> t_lColumns;
            AttributeIncompleteValueObject t_Attribute;

            while (t_rsColumns.next())
            {
                t_strTableName = t_rsColumns.getString(Literals.TABLE_NAME_U);

                if (passesFilter(t_strTableName, tables, caseSensitiveness))
                {
                    t_lColumns = t_mAux.get(t_strTableName);

                    if (t_lColumns == null)
                    {
                        t_lColumns = new ArrayList<AttributeIncompleteValueObject>(9);
                        t_mAux.put(t_strTableName, t_lColumns);
                    }

                    t_strColumnName = t_rsColumns.getString(Literals.COLUMN_NAME_U);
                    t_iColumnType = t_rsColumns.getInt("COLUMN_TYPE");
                    t_strTypeName = t_rsColumns.getString("TYPE_NAME");
                    t_iColumnSize = t_rsColumns.getInt("COLUMN_SIZE");
                    t_iDecimalDigits = t_rsColumns.getInt("DECIMAL_DIGITS");
                    t_iNullable = t_rsColumns.getInt("NULLABLE");
                    t_strColumnComment = t_rsColumns.getString("REMARKS");
                    t_strDefaultValue = t_rsColumns.getString("COLUMN_DEF");
                    //t_iMaxStringBytes = t_rsColumns.getInt("CHAR_OCTET_LENGTH");
                    t_iOrdinalPosition = t_rsColumns.getInt("ORDINAL_POSITION");

                    t_Attribute =
                        new AttributeIncompleteValueObject(
                            t_strColumnName,
                            t_iColumnType,
                            t_strTypeName,
                            t_strTableName,
                            t_strColumnComment,
                            t_iOrdinalPosition,
                            t_iColumnSize,
                            t_iDecimalDigits,
                            (t_iNullable == DatabaseMetaData.columnNullable),
                            t_strDefaultValue);

                    t_lColumns.add(t_Attribute);
                }
                else
                {
                    logVerbose("Discarding " + t_strTableName);
                }
            }

            t_rsColumns.close();
        }
        catch  (final SQLException sqlException)
        {
            logWarn(
                "Cannot retrieve the column names.",
                sqlException);

            throw sqlException;
        }

        bindAttributes(tables, t_mAux);
    }

    /**
     * Retrieves the primary keys for given tables.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param caseSensitiveness whether the table names are case sensitive or not.
     * @param metadataExtractionListener the metadata extraction listener.
     * @throws java.sql.SQLException if the database operation fails.
     * @throws org.acmsl.queryj.api.exceptions.QueryJException if the any other error occurs.
     */
    @Override
    protected void extractPrimaryKeys(
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema,
        @NotNull final List<TableIncompleteValueObject> tables,
        final boolean caseSensitiveness,
        @Nullable final MetadataExtractionListener metadataExtractionListener)
        throws  SQLException,
                QueryJException
    {
        @NotNull final Map<String, List<Attribute<String>>> t_mAux =
            new HashMap<String,List<Attribute<String>>>(tables.size());

        final ResultSet t_rsPrimaryKeys;
        String t_strTableName;
        String t_strColumnName;
        int t_iOrdinalPosition;

        try
        {
            // TODO: check if the last parameter (table) can be null
            t_rsPrimaryKeys =
                metaData.getPrimaryKeys(
                    catalog,
                    schema,
                    null);

            List<Attribute<String>> t_lPrimaryKeys;
            Attribute<String> t_Attribute;

            while (t_rsPrimaryKeys.next())
            {
                t_strTableName = t_rsPrimaryKeys.getString(Literals.TABLE_NAME_U);

                @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
                    findTable(t_strTableName, tables, caseSensitiveness);

                if (   (t_Table != null)
                    && (passesFilter(t_Table, tables, caseSensitiveness)))
                {
                    t_lPrimaryKeys = t_mAux.get(t_strTableName);

                    if (t_lPrimaryKeys == null)
                    {
                        t_lPrimaryKeys = new ArrayList<Attribute<String>>(9);
                        t_mAux.put(t_strTableName, t_lPrimaryKeys);
                    }

                    t_strColumnName = t_rsPrimaryKeys.getString(Literals.COLUMN_NAME_U);
                    t_iOrdinalPosition = t_rsPrimaryKeys.getInt("KEY_SEQ");

                    t_Attribute = findAttribute(t_strColumnName, t_Table.getAttributes(), caseSensitiveness);

                    if (t_Attribute != null)
                    {
                        t_lPrimaryKeys.add(fixOrdinalPosition(t_iOrdinalPosition, t_Attribute));
                    }

                    t_lPrimaryKeys.add(t_Attribute);
                }
                else
                {
                    logVerbose("Discarding " + t_strTableName);
                }
            }

            t_rsPrimaryKeys.close();
        }
        catch  (final SQLException sqlException)
        {
            logWarn(
                "Cannot retrieve the primary keys.",
                sqlException);

            throw sqlException;
        }

        Table<String, Attribute<String>, List<Attribute<String>>> t_ParentTable;
        List<Attribute<String>> t_lParentTablePrimaryKey;
        List<Attribute<String>> t_lChildTablePrimaryKey;
        List<Attribute<String>> t_lCompletePrimaryKey;

        for (@NotNull final TableIncompleteValueObject t_Table : tables)
        {
            t_lChildTablePrimaryKey = t_mAux.get(t_Table.getName());

            t_ParentTable = t_Table.getParentTable();

            if (t_ParentTable != null)
            {
                t_lParentTablePrimaryKey = t_ParentTable.getAttributes();

                t_lCompletePrimaryKey =
                    new ArrayList<Attribute<String>>(t_lParentTablePrimaryKey.size() + t_lChildTablePrimaryKey.size());

                t_lCompletePrimaryKey.addAll(t_lParentTablePrimaryKey);
                t_lCompletePrimaryKey.addAll(t_lChildTablePrimaryKey);
                fixOrdinalPositions(t_lCompletePrimaryKey);
                t_Table.setAttributes(t_lCompletePrimaryKey);
            }
            else
            {
                t_Table.setAttributes(t_lChildTablePrimaryKey);
            }
        }
    }

    /**
     * Extracts the foreign keys for given tables.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param caseSensitiveness whether the table names are case sensitive or not.
     * @param metadataExtractionListener the metadata extraction listener.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if the any other error occurs.
     */
    protected void extractForeignKeys(
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema,
        @NotNull final List<TableIncompleteValueObject> tables,
        final boolean caseSensitiveness,
        @Nullable final MetadataExtractionListener metadataExtractionListener)
        throws  SQLException,
                QueryJException
    {
        @NotNull final Map<String,List<ForeignKey<String>>> t_mAux =
            new HashMap<String,List<ForeignKey<String>>>(tables.size());

        final ResultSet t_rsForeignKeys;
        String t_strSourceTableName;
        Table<String, Attribute<String>, List<Attribute<String>>> t_SourceTable;
        String t_strTargetTableName;
        Table<String, Attribute<String>, List<Attribute<String>>> t_TargetTable;
        String t_strSourceColumnName;
        int t_iOrdinalPosition;

        try
        {
            // TODO: check if the last parameter (table) can be null
            t_rsForeignKeys =
                metaData.getImportedKeys(
                    catalog,
                    schema,
                    null);

            List<ForeignKey<String>> t_lForeignKeys;
            ForeignKey<String> t_ForeignKey;
            List<Attribute<String>> t_lFkAttributes = null;
            Attribute<String> t_Attribute;

            while (t_rsForeignKeys.next())
            {
                t_strSourceTableName = t_rsForeignKeys.getString("FKTABLE_NAME");
                t_strTargetTableName = t_rsForeignKeys.getString("PKTABLE_NAME");

                t_SourceTable = findTable(t_strSourceTableName, tables, caseSensitiveness);
                t_TargetTable = findTable(t_strTargetTableName, tables, caseSensitiveness);

                if (   (t_SourceTable != null)
                    && (t_TargetTable != null)
                    && (passesFilter(t_SourceTable, tables, caseSensitiveness))
                    && (passesFilter(t_TargetTable, tables, caseSensitiveness)))
                {
                    t_lForeignKeys = t_mAux.get(t_strSourceTableName);

                    if (t_lForeignKeys == null)
                    {
                        t_lForeignKeys = new ArrayList<ForeignKey<String>>(1);
                        t_mAux.put(t_strSourceTableName, t_lForeignKeys);
                    }

                    t_strSourceColumnName = t_rsForeignKeys.getString("FKCOLUMN_NAME");
                    t_iOrdinalPosition = t_rsForeignKeys.getInt("KEY_SEQ");

                    t_Attribute =
                        findAttribute(t_strSourceColumnName, t_SourceTable.getAttributes(), caseSensitiveness);

                    if (t_Attribute != null)
                    {
                        if (t_lFkAttributes == null)
                        {
                            t_lFkAttributes = new ArrayList<Attribute<String>>(1);
                            t_ForeignKey =
                                new ForeignKeyValueObject(
                                    t_strSourceTableName,
                                    t_lFkAttributes,
                                    t_strTargetTableName,
                                    t_Attribute.isNullable());

                            t_lForeignKeys.add(t_ForeignKey);
                        }
                        t_lFkAttributes.add(fixOrdinalPosition(t_iOrdinalPosition, t_Attribute));
                    }
                }
                else
                {
                    logVerbose("Discarding " + t_strSourceTableName);
                }
            }

            t_rsForeignKeys.close();
        }
        catch  (final SQLException sqlException)
        {
            logWarn(
                "Cannot retrieve the foreign keys.",
                sqlException);

            throw sqlException;
        }

        Table<String, Attribute<String>, List<Attribute<String>>> t_ParentTable;
        List<ForeignKey<String>> t_lParentForeignKeys;
        List<ForeignKey<String>> t_lChildForeignKeys;
        List<ForeignKey<String>> t_lCompleteForeignKey;

        for (@NotNull final TableIncompleteValueObject t_Table : tables)
        {
            t_lChildForeignKeys = t_mAux.get(t_Table.getName());

            t_ParentTable = t_Table.getParentTable();

            if (t_ParentTable != null)
            {
                t_lParentForeignKeys = t_ParentTable.getForeignKeys();

                t_lCompleteForeignKey =
                    new ArrayList<ForeignKey<String>>(t_lParentForeignKeys.size() + t_lChildForeignKeys.size());

                t_lCompleteForeignKey.addAll(t_lParentForeignKeys);
                t_lCompleteForeignKey.addAll(t_lChildForeignKeys);
                t_Table.setForeignKeys(t_lCompleteForeignKey);
            }
            else
            {
                t_Table.setForeignKeys(t_lChildForeignKeys);
            }
        }
    }

    /**
     * Retrieves the type manager.
     * @return such instance.
     */
    @Override
    @NotNull
    public MetadataTypeManager getMetadataTypeManager()
    {
        return JdbcMetadataTypeManager.getInstance();
    }

    /**
     * Checks whether the engine requires specific CLOB handling.
     * @return <code>true</code> in such case.
     */
    @Override
    public boolean requiresCustomClobHandling()
    {
        return false;
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.metadata.TableDAO} instance.
     *
     * @return such instance.
     */
    @NotNull
    @Override
    public TableDAO getTableDAO()
    {
        return new JdbcTableDAO(this);
    }
}
