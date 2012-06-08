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
 * Filename: AbstractRefactoredJdbcMetadataManager.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Abstract implementation of RefactoredJdbcMetadataManager.
 *
 * Date: 6/6/12
 * Time: 8:42 AM
 *
 */
package org.acmsl.queryj.tools.metadata.engines;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.metadata.ColumnDAO;
import org.acmsl.queryj.tools.metadata.ForeignKeyDAO;
import org.acmsl.queryj.tools.metadata.MetadataExtractionListener;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.PrimaryKeyDAO;
import org.acmsl.queryj.tools.metadata.TableDAO;
import org.acmsl.queryj.tools.metadata.vo.Attribute;
import org.acmsl.queryj.tools.metadata.vo.AttributeIncompleteValueObject;
import org.acmsl.queryj.tools.metadata.vo.Table;
import org.acmsl.queryj.tools.metadata.vo.TableIncompleteValueObject;
import org.acmsl.queryj.tools.templates.MetaLanguageUtils;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Apache Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

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

/**
 * Abstract implementation of {@link MetadataManager}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
@SuppressWarnings("unused")
public class AbstractJdbcMetadataManager
    implements MetadataManager
{
    /**
     * The manager name.
     */
    private String m__strName;

    /**
     * The database metadata.
     */
    private transient DatabaseMetaData m__MetaData;

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
     * The table list.
     */
    private List<Table> m__lTables;

    /**
     * The table attributes (table name -> columns).
     */
    private Map<String,List<Attribute>> m__mColumns;

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
     * Whether the engine is case sensitive.
     */
    private boolean m__bCaseSensitive = false;

    /**
     * The engine name.
     */
    private String m__strEngineName;

    /**
     * The engine version.
     */
    private String m__strEngineVersion;

    /**
     * The identifier quote string.
     */
    private String m__strQuote;

    /**
     * Creates a new {@link AbstractJdbcMetadataManager} with given information.
     * @param name the name.
     */
    @SuppressWarnings("unused")
    protected AbstractJdbcMetadataManager(@NotNull final String name)
    {
        immutableSetName(name);
    }

    /**
     * Specifies the name.
     * @param name the name.
     */
    protected final void immutableSetName(@NotNull final String name)
    {
        m__strName = name;
    }

    /**
     * Specifies the name.
     * @param name the name.
     */
    protected void setName(@NotNull final String name)
    {
        immutableSetName(name);
    }

    /**
     * Retrieves the name identifying the manager instance.
     * @return such name.
     */
    @Override
    @NotNull
    public String getName()
    {
        return m__strName;
    }

    /**
     * Specifies the table names.
     * @param names such names.
     */
    protected final void immutableSetTableNames(@NotNull final String[] names)
    {
        m__astrTableNames = names;
    }

    /**
     * Specifies the table names.
     * @param names such names.
     */
    protected void setTableNames(@NotNull final String[] names)
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
     * Specifies the tables.
     * @param tables the tables.
     */
    protected final void immutableSetTables(@NotNull final List<Table> tables)
    {
        m__lTables = tables;
    }

    /**
     * Specifies the tables.
     * @param tables the tables.
     */
    protected void setTables(@NotNull final List<Table> tables)
    {
        immutableSetTables(tables);
    }

    /**
     * Retrieves the tables.
     * @return the list of {@link Table tables}.
     */
    @Nullable
    protected final List<Table> immutableGetTables()
    {
        return m__lTables;
    }

    /**
     * Retrieves the tables.
     * @return the list of {@link Table tables}.
     */
    @NotNull
    public List<Table> getTables()
    {
        List<Table> result = immutableGetTables();

        if (result == null)
        {
            result = new ArrayList<Table>();
            setTables(result);
        }

        return result;
    }

    /**
     * Specifies the columns.
     * @param columns the information about all columns for all tables.
     */
    protected final void immutableSetColumns(@NotNull Map<String,List<Attribute>> columns)
    {
        m__mColumns = columns;
    }

    /**
     * Specifies the columns.
     * @param columns the information about all columns for all tables.
     */
    protected void setColumns(@NotNull Map<String,List<Attribute>> columns)
    {
        immutableSetColumns(columns);
    }

    /**
     * Retrieves the columns.
     * @return such information.
     */
    @Nullable
    protected final Map<String,List<Attribute>> immutableGetColumns()
    {
        return m__mColumns;
    }

    /**
     * Retrieves the columns.
     * @return such information.
     */
    @NotNull
    public Map<String,List<Attribute>> getColumns()
    {
        Map<String,List<Attribute>> result = immutableGetColumns();

        if (result == null)
        {
            result = new HashMap<String,List<Attribute>>();
            setColumns(result);
        }

        return result;
    }

    /**
     * Specifies the meta data.
     * @param metaData the database meta data.
     */
    protected final void immutableSetMetaData(@NotNull final DatabaseMetaData metaData)
    {
        m__MetaData = metaData;
    }

    /**
     * Specifies the meta data.
     * @param metaData the database meta data.
     */
    @SuppressWarnings("unused")
    public void setMetaData(@NotNull final DatabaseMetaData metaData)
    {
        immutableSetMetaData(metaData);
    }

    /**
     * Retrieves the database meta data.
     * @return such information.
     */
    @NotNull
    @Override
    public DatabaseMetaData getMetaData()
    {
        return m__MetaData;
    }

    /**
     * Specifies the metadata extraction listener.
     * @param listener such listener.
     */
    protected final void immutableSetMetadataExtractionListener(
        @NotNull final MetadataExtractionListener listener)
    {
        m__MetadataExtractionListener = listener;
    }

    /**
     * Specifies the metadata extraction listener.
     * @param listener such listener.
     */
    @SuppressWarnings("unused")
    protected void setMetadataExtractionListener(
        @NotNull final MetadataExtractionListener listener)
    {
        immutableSetMetadataExtractionListener(listener);
    }

    /**
     * Retrieves the metadata extraction listener.
     * @return such listener.
     */
    @NotNull
    @SuppressWarnings("unused")
    public MetadataExtractionListener getMetadataExtractionListener()
    {
        return m__MetadataExtractionListener;
    }

    /**
     * Specifies the catalog.
     * @param catalog the database catalog.
     */
    protected final void immutableSetCatalog(@Nullable final String catalog)
    {
        m__strCatalog = catalog;
    }

    /**
     * Specifies the catalog.
     * @param catalog the database catalog.
     */
    @SuppressWarnings("unused")
    protected void setCatalog(@Nullable final String catalog)
    {
        immutableSetCatalog(catalog);
    }

    /**
     * Retrieves the catalog.
     * @return the database catalog.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getCatalog()
    {
        return m__strCatalog;
    }

    /**
     * Specifies the schema.
     * @param schema the database schema.
     */
    protected final void immutableSetSchema(@Nullable final String schema)
    {
        m__strSchema = schema;
    }

    /**
     * Specifies the schema.
     * @param schema the database schema.
     */
    @SuppressWarnings("unused")
    protected void setSchema(@Nullable final String schema)
    {
        immutableSetSchema(schema);
    }

    /**
     * Retrieves the schema.
     * @return the database schema.
     */
    @SuppressWarnings("unused")
    @Nullable
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
    @SuppressWarnings("unused")
    protected void setDisableTableExtraction(final boolean flag)
    {
        immutableSetDisableTableExtraction(flag);
    }

    /**
     * Retrieves whether the table extraction is disabled.
     * @return such flag.
     */
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
    protected void setLazyTableExtraction(final boolean flag)
    {
        immutableSetLazyTableExtraction(flag);
    }

    /**
     * Retrieves the lazy table extraction flag.
     * @return such flag.
     */
    @SuppressWarnings("unused")
    public boolean getLazyTableExtraction()
    {
        return m__bLazyTableExtraction;
    }

    /**
     * Specifies the engine name.
     * @param engineName the new engine name.
     */
    protected final void immutableSetEngineName(@NotNull final String engineName)
    {
        m__strEngineName = engineName;
    }

    /**
     * Specifies the engine name.
     * @param engineName the new engine name.
     */
    @SuppressWarnings("unused")
    protected void setEngineName(@NotNull final String engineName)
    {
        immutableSetEngineName(engineName);
    }

    /**
     * Retrieves the engine name.
     * @return such information.
     */
    @NotNull
    public String getEngineName()
    {
        return m__strEngineName;
    }

    /**
     * Specifies the engine version.
     * @param engineVersion the new engine version.
     */
    private void immutableSetEngineVersion(@NotNull final String engineVersion)
    {
        m__strEngineVersion = engineVersion;
    }

    /**
     * Specifies the engine version.
     * @param engineVersion the new engine version.
     */
    @SuppressWarnings("unused")
    protected void setEngineVersion(@NotNull final String engineVersion)
    {
        immutableSetEngineVersion(engineVersion);
    }

    /**
     * Retrieves the engine version.
     * @return such information.
     */
    @NotNull
    @SuppressWarnings("unused")
    public String getEngineVersion()
    {
        return m__strEngineVersion;
    }

    /**
     * Specifies the identifier quote string.
     * @param quote such identifier.
     */
    protected void immutableSetQuote(@NotNull final String quote)
    {
        m__strQuote = quote;
    }

    /**
     * Specifies the identifier quote string.
     * @param quote such identifier.
     */
    @SuppressWarnings("unused")
    protected void setQuote(@NotNull final String quote)
    {
        immutableSetQuote(quote);
    }

    /**
     * Retrieves the identifier quote string.
     * @return such identifier.
     */
    @NotNull
    @SuppressWarnings("unused")
    public String getQuote()
    {
        return m__strQuote;
    }

    /**
     * Specifies the case sensitiveness.
     * @param caseSensitive whether the engine is case sensitive.
     */
    protected final void immutableSetCaseSensitive(final boolean caseSensitive)
    {
        this.m__bCaseSensitive = caseSensitive;
    }

    /**
     * Specifies the case sensitiveness.
     * @param caseSensitive whether the engine is case sensitive.
     */
    @SuppressWarnings("unused")
    protected void setCaseSensitive(final boolean caseSensitive)
    {
        immutableSetCaseSensitive(caseSensitive);
    }

    /**
     * Retrieves the case sensitiveness.
     * @return whether the engine is case sensitive.
     */
    protected boolean immutableIsCaseSensitive()
    {
        return m__bCaseSensitive;
    }

    /**
     * Retrieves the case sensitiveness.
     * @return whether the engine is case sensitive.
     */
    @SuppressWarnings("unused")
    public boolean isCaseSensitive()
    {
        return immutableIsCaseSensitive();
    }

    /**
     * Logs a verbose message.
     * @param message the message to log.
     */
    protected void logVerbose(@NotNull final String message)
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
     */
    protected void logWarn(@NotNull final String message, @NotNull final Exception exception)
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
     */
    @NotNull
    protected String concat(
        @NotNull final String[] values, @NotNull final String separator)
    {
        StringBuilder result = new StringBuilder();

        for (int t_iIndex = 0; t_iIndex < values.length; t_iIndex++)
        {
            String t_strValue = values[t_iIndex];

            if  (t_strValue != null)
            {
                result.append(t_strValue);

                if  (   (t_iIndex < values.length - 1)
                     && (anythingElseToConcatenate(values, t_iIndex + 1)))
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
     * @precondition start >= 0;
     * @precondition start < values.length
     */
    protected boolean anythingElseToConcatenate(
        @NotNull final String[] values, final int start)
    {
        boolean result = false;

        for  (int t_iIndex = start; t_iIndex < values.length; t_iIndex++)
        {
            if  (values[t_iIndex] != null)
            {
                result = true;
                break;
            }
        }

        return result;
    }

    // RefactoredMetadataManager interface.
    /**
     * Retrieves the metadata.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ, occurs.
     */
    @Override
    public void eagerlyFetchMetadata()
        throws SQLException, QueryJException
    {
        extractTableMetadata(
            getTableNames(),
            getMetaData(),
            getCatalog(),
            getSchema(),
            isCaseSensitive(),
            getMetadataExtractionListener(),
            MetaLanguageUtils.getInstance());
    }

    /**
     * Retrieves the table names.
     * @param tableNames optionally specified table names.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param caseSensitiveness whether it's case sensitive or not.
     * @param metadataExtractionListener the
     * <code>MetadataExtractionListener</code> instance.
     * @param metaLanguageUtils the {@link MetaLanguageUtils} instance.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    protected void extractTableMetadata(
        @Nullable final String[] tableNames,
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema,
        final boolean caseSensitiveness,
        @NotNull final MetadataExtractionListener metadataExtractionListener,
        @NotNull final MetaLanguageUtils metaLanguageUtils)
        throws  SQLException,
        QueryJException
    {
        metadataExtractionListener.tableNamesExtractionStarted();

        String[] t_astrTableNames = tableNames;

        if (t_astrTableNames == null)
        {
            t_astrTableNames = new String[0];
        }

        List<TableIncompleteValueObject> t_lTables =
            extractTableNamesAndComments(
                metaData, catalog, schema, t_astrTableNames, metadataExtractionListener, caseSensitiveness);

        if  (t_astrTableNames.length == 0)
        {
            t_astrTableNames = retrieveTableNames(t_lTables);

            setTableNames(t_astrTableNames);
        }

        int t_iTableCount = t_astrTableNames.length;

        metadataExtractionListener.tableNamesExtracted(t_iTableCount);

        metadataExtractionListener.tableMetadataExtractionStarted();

        String t_strTableName;
        Table t_Parent;

        for (TableIncompleteValueObject t_Table : t_lTables)
        {
            t_strTableName = t_Table.getName();
            t_Parent =
                retrieveParentTable(t_strTableName, t_lTables, caseSensitiveness, metaLanguageUtils);

            if (t_Parent != null)
            {
                t_Table.setParentTable(t_Parent);
            }
        }

        extractTableColumns(
            metaData,
            catalog,
            schema,
            t_lTables,
            caseSensitiveness,
            metadataExtractionListener);

        // TODO: retrieve primary keys.

        // TODO: retrieve foreign keys.

        metadataExtractionListener.tableMetadataExtracted();
    }

    /**
     * Retrieves the parent table, if any, based on given table's comment.
     * @param tableComment the comment.
     * @param tables the list of tables.
     * @param caseSensitiveness whether the tables are case sensitive or not.
     * @param metaLanguageUtils the {@link MetaLanguageUtils} instance.
     * @return the {@link Table parent table}, if any.
     */
    @Nullable
    protected Table retrieveParentTable(
        @NotNull final String tableComment,
        @NotNull final List<? extends Table> tables,
        final boolean caseSensitiveness,
        @NotNull final MetaLanguageUtils metaLanguageUtils)
    {
        Table result = null;

        @Nullable String t_strParentTable = metaLanguageUtils.retrieveDeclaredParent(tableComment);

        if (t_strParentTable != null)
        {
            result = findTableByName(t_strParentTable, tables, caseSensitiveness);
        }

        return result;
    }

    /**
     * Retrieves the table matching given name.
     * @param table the table to find.
     * @param tables the list of tables.
     * @param caseSensitiveness whether the tables are case sensitive or not.
     * @return the matching {@link Table} or <code>null</code> otherwise.
     */
    @Nullable
    protected Table findTableByName(
        @NotNull final String table, @NotNull final List<? extends Table> tables, final boolean caseSensitiveness)
    {
        Table result = null;

        for (Table t_Table : tables)
        {
            if (t_Table != null)
            {
                if (caseSensitiveness)
                {
                    if (table.equalsIgnoreCase(t_Table.getName()))
                    {
                        result = t_Table;
                        break;
                    }
                }
                else
                {
                    if (table.equals(t_Table.getName()))
                    {
                        result = t_Table;
                        break;
                    }
                }
            }
        }

        return result;
    }


    /**
     * Retrieves the list of table names, extracted from given list.
     * @param tables the table information.
     * @return just the table names.
     */
    protected String[] retrieveTableNames(@NotNull final List<? extends Table> tables)
    {
        String[] result = new String[tables.size()];

        for (int t_iIndex = 0; t_iIndex < tables.size(); t_iIndex++)
        {
            result[t_iIndex] = tables.get(t_iIndex).getName();
        }

        return result;
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
    protected List<TableIncompleteValueObject> extractTableNamesAndComments(
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable  String schema,
        @NotNull final String[] tableNames,
        @NotNull final MetadataExtractionListener metadataExtractionListener,
        final boolean caseSensitiveness)
        throws  SQLException,
        QueryJException
    {
        List<TableIncompleteValueObject> result = new ArrayList<TableIncompleteValueObject>();

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

            TableIncompleteValueObject t_Table;
            String t_strTableName;

            while (t_rsTables.next())
            {
                t_strTableName = t_rsTables.getString("TABLE_NAME");

                if (passesFilter(t_strTableName, tableNames, caseSensitiveness))
                {
                    t_Table =
                        new TableIncompleteValueObject(
                            t_strTableName,
                            t_rsTables.getString("REMARKS"));

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
     * Checks whether given table passes the filter.
     * @param table the table name.
     * @param fixedTables the fixed list of tables (empty means no filter)
     * @param caseSensitiveness whether the check is case sensitive.
     * @return <code>true</code> if the table passes the filter.
     */
    protected boolean passesFilter(
        @NotNull final String table, @NotNull final List<? extends Table> fixedTables, final boolean caseSensitiveness)
    {
        boolean result = true;

        for (Table t_FixedTable : fixedTables)
        {
            if (t_FixedTable != null)
            {
                if (caseSensitiveness)
                {
                    result = table.equals(t_FixedTable.getName());
                }
                else
                {
                    result = table.equalsIgnoreCase(t_FixedTable.getName());
                }

                if (result)
                {
                    break;
                }
            }
        }

        return result;
    }


    /**
     * Checks whether given table passes the filter.
     * @param table the table name.
     * @param fixedTables the fixed list of tables (empty means no filter)
     * @param caseSensitiveness whether the check is case sensitive.
     * @return <code>true</code> if the table passes the filter.
     */
    protected boolean passesFilter(
        @NotNull final String table, @NotNull final String[] fixedTables, final boolean caseSensitiveness)
    {
        boolean result = true;

        for (String t_strFixedTable : fixedTables)
        {
            if (caseSensitiveness)
            {
                result = table.equals(t_strFixedTable);
            }
            else
            {
                result = table.equalsIgnoreCase(t_strFixedTable);
            }

            if (result)
            {
                break;
            }
        }

        return result;
    }

    /**
     * Extracts the comments for all tables.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableNames the table names.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return a map of table -> comment.
     * @throws SQLException if the database operation fails.
     */
    protected Map<String,String> extractTableComments(
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema,
        @NotNull final String[] tableNames,
        @Nullable final MetadataExtractionListener metadataExtractionListener)
        throws  SQLException,
        QueryJException
    {
        Map<String,String> result;

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

            result = extractTableComments(t_rsTables, tableNames.length);
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
     * Extracts the comment from given result set.
     * @param resultSet the result set with the table comment information.
     * @param tableCount the number of tables, to provide accurate dimension to the resulting map.
     * @return a map of table name -> comment.
     * @throws SQLException if the database operation fails.
     */
    @NotNull
    protected Map<String,String> extractTableComments(@NotNull final ResultSet resultSet, final int tableCount)
        throws  SQLException
    {
        Map<String,String> result = new HashMap<String,String>(tableCount);

        String t_strTableName;
        String t_strTableComment;

        while  (resultSet.next())
        {
            t_strTableName = resultSet.getString("TABLE_NAME");
            t_strTableComment = resultSet.getString("REMARKS");

            if (t_strTableName != null)
            {
                if (t_strTableComment == null)
                {
                    t_strTableComment = "";
                }

                result.put(t_strTableName, t_strTableComment);
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
    protected void extractTableColumns(
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema,
        @NotNull final List<TableIncompleteValueObject> tables,
        final boolean caseSensitiveness,
        @Nullable final MetadataExtractionListener metadataExtractionListener)
        throws  SQLException,
        QueryJException
    {
        Map<String,List<Attribute>> t_mAux = new HashMap<String,List<Attribute>>(tables.size());

        ResultSet t_rsColumns;
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

            List<Attribute> t_lColumns;
            Attribute t_Attribute;

            while (t_rsColumns.next())
            {
                t_strTableName = t_rsColumns.getString("TABLE_NAME");

                if (passesFilter(t_strTableName, tables, caseSensitiveness))
                {
                    t_lColumns = t_mAux.get(t_strTableName);

                    if (t_lColumns == null)
                    {
                        t_lColumns = new ArrayList<Attribute>(9);
                        t_mAux.put(t_strTableName, t_lColumns);
                    }

                    t_strColumnName = t_rsColumns.getString("COLUMN_NAME");
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

        Table t_ParentTable;
        List<Attribute> t_lParentTableAttributes;
        List<Attribute> t_lChildTableAttributes;
        List<Attribute> t_lCompleteAttributes;

        for (TableIncompleteValueObject t_Table : tables)
        {
            t_lChildTableAttributes = t_mAux.get(t_Table.getName());

            t_ParentTable = t_Table.getParentTable();

            if (t_ParentTable != null)
            {
                t_lParentTableAttributes = t_ParentTable.getAttributes();

                t_lCompleteAttributes =
                    new ArrayList<Attribute>(t_lParentTableAttributes.size() + t_lChildTableAttributes.size());

                t_lCompleteAttributes.addAll(t_lParentTableAttributes);
                t_lCompleteAttributes.addAll(t_lChildTableAttributes);
                t_Table.setAttributes(t_lCompleteAttributes);
            }
            else
            {
                t_Table.setAttributes(t_lChildTableAttributes);
            }
        }
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.tools.metadata.TableDAO} instance.
     *
     * @return such instance.
     */
    @NotNull
    @Override
    public TableDAO getTableDAO()
    {
        return new MetadataManagerTableDAO(this);
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.tools.metadata.ColumnDAO} instance.
     *
     * @return such instance.
     */
    @NotNull
    @Override
    public ColumnDAO getColumnDAO()
    {
        return new MetadataManagerColumnDAO(this);
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.tools.metadata.PrimaryKeyDAO} instance.
     *
     * @return such instance.
     */
    @NotNull
    @Override
    public PrimaryKeyDAO getPrimaryKeyDAO()
    {
        return new MetadataManagerPrimaryKeyDAO(this);
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.tools.metadata.ForeignKeyDAO} instance.
     *
     * @return such instance.
     */
    @NotNull
    @Override
    public ForeignKeyDAO getForeignKeyDAO()
    {
        return new MetadataManagerForeignKeyDAO(this);
    }

    /**
     * Checks whether the generation phase is enabled for given table.
     *
     * @param tableName the table name.
     * @return <code>true</code> in such case.
     */
    @Override
    public boolean isGenerationAllowedForTable(@NotNull final String tableName)
    {
        return false;
    }

    /**
     * Retrieves the type manager.
     *
     * @return such instance.
     */
    @Override
    public MetadataTypeManager getMetadataTypeManager()
    {
        return JdbcMetadataTypeManager.getInstance();
    }

    /**
     * Checks whether the engine requires specific CLOB handling.
     *
     * @return <code>true</code> in such case.
     */
    @Override
    public boolean requiresCustomClobHandling()
    {
        return false;
    }
}
