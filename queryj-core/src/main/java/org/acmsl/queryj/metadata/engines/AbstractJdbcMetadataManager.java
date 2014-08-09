/*
                        QueryJ Core

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
package org.acmsl.queryj.metadata.engines;

/*
 * Importing project classes.
 */
import org.acmsl.commons.utils.Chronometer;
import org.acmsl.queryj.api.exceptions.QueryJException;
import org.acmsl.queryj.api.MetaLanguageUtils;
import org.acmsl.queryj.metadata.ColumnDAO;
import org.acmsl.queryj.metadata.exceptions.MissingDatabaseMetadataAtRuntimeException;
import org.acmsl.queryj.metadata.ForeignKeyDAO;
import org.acmsl.queryj.metadata.MetadataExtractionListener;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.PrimaryKeyDAO;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.AttributeIncompleteValueObject;
import org.acmsl.queryj.metadata.vo.AttributeValueObject;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.ForeignKeyIncompleteValueObject;
import org.acmsl.queryj.metadata.vo.ForeignKeyValueObject;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.metadata.vo.TableIncompleteValueObject;
import org.acmsl.queryj.metadata.vo.TableValueObject;
import org.acmsl.queryj.tools.DebugUtils;

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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract implementation of {@link MetadataManager}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 3.0
 * Created: 2012/06/06
 */
public abstract class AbstractJdbcMetadataManager
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
    private List<String> m__lTableNames;

    /**
     * The table list.
     */
    private List<Table<String, Attribute<String>, List<Attribute<String>>>> m__lTables;

    /**
     * The table attributes (table name -&gt; columns).
     */
    private Map<String,List<Attribute<String>>> m__mColumns;

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
    private Engine<String> m__Engine;

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
     * @param engine the engine.
     */
    protected AbstractJdbcMetadataManager(
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
        @NotNull final Engine<String> engine)
    {
        this(name);
        immutableSetMetaData(metadata);
        immutableSetMetadataExtractionListener(metadataExtractionListener);
        immutableSetCatalog(catalog);
        immutableSetSchema(schema);
        immutableSetTableNames(tableNames);
        immutableSetTables(tables);
        immutableSetDisableTableExtraction(disableTableExtraction);
        immutableSetLazyTableExtraction(lazyTableExtraction);
        immutableSetCaseSensitive(caseSensitive);
        immutableSetEngine(engine);
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
    protected final void immutableSetTableNames(@NotNull final List<String> names)
    {
        m__lTableNames = names;
    }

    /**
     * Specifies the table names.
     * @param names such names.
     */
    @SuppressWarnings("unused")
    protected void setTableNames(@NotNull final List<String> names)
    {
        immutableSetTableNames(names);
    }

    /**
     * Retrieves the table names.
     * @return such names.
     */
    @Nullable
    protected final List<String> immutableGetTableNames()
    {
        return m__lTableNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public List<String> getTableNames()
    {
        List<String> result = immutableGetTableNames();

        if (   (result == null)
            || (result.size() == 0))
        {
            result = extractTableNames(getTables());
            setTableNames(result);
        }

        return result;
    }

    /**
     * Specifies the tables.
     * @param tables the tables.
     */
    protected final void immutableSetTables(
        @NotNull final List<Table<String, Attribute<String>, List<Attribute<String>>>> tables)
    {
        m__lTables = tables;
    }

    /**
     * Specifies the tables.
     * @param tables the tables.
     */
    protected void setTables(
        @NotNull final List<Table<String, Attribute<String>, List<Attribute<String>>>> tables)
    {
        immutableSetTables(tables);
    }

    /**
     * Retrieves the tables.
     * @return the list of {@link Table tables}.
     */
    @Nullable
    protected final List<Table<String, Attribute<String>, List<Attribute<String>>>> immutableGetTables()
    {
        return m__lTables;
    }

    /**
     * Retrieves the tables.
     * @return the list of {@link Table tables}.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<Table<String, Attribute<String>, List<Attribute<String>>>> getTables()
    {
        List<Table<String, Attribute<String>, List<Attribute<String>>>> result = immutableGetTables();

        if (result == null)
        {
            result = new ArrayList<>();
            setTables(result);
        }

        return result;
    }

    /**
     * Specifies the columns.
     * @param columns the information about all columns for all tables.
     */
    protected final void immutableSetColumns(@NotNull final Map<String, List<Attribute<String>>> columns)
    {
        m__mColumns = columns;
    }

    /**
     * Specifies the columns.
     * @param columns the information about all columns for all tables.
     */
    protected void setColumns(@NotNull final Map<String, List<Attribute<String>>> columns)
    {
        immutableSetColumns(columns);
    }

    /**
     * Retrieves the columns.
     * @return such information.
     */
    @Nullable
    protected final Map<String, List<Attribute<String>>> immutableGetColumns()
    {
        return m__mColumns;
    }

    /**
     * Retrieves the columns.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @NotNull
    public Map<String, List<Attribute<String>>> getColumns()
    {
        Map<String, List<Attribute<String>>> result = immutableGetColumns();

        if (result == null)
        {
            result = new HashMap<>(0);
            setColumns(result);
        }

        return result;
    }

    /**
     * Specifies the meta data.
     * @param metaData the database meta data.
     */
    protected final void immutableSetMetaData(@Nullable final DatabaseMetaData metaData)
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
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public DatabaseMetaData getMetaData()
    {
        final DatabaseMetaData result = m__MetaData;

        if (result == null)
        {
            throw new MissingDatabaseMetadataAtRuntimeException();
        }

        return result;
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
     * Specifies the engine.
     * @param engine the new engine.
     */
    protected final void immutableSetEngine(@NotNull final Engine<String> engine)
    {
        m__Engine = engine;
    }

    /**
     * Specifies the engine.
     * @param engine the new engine.
     */
    @SuppressWarnings("unused")
    protected void setEngineName(@NotNull final Engine<String> engine)
    {
        immutableSetEngine(engine);
    }

    /**
     * Retrieves the engine name.
     * @return such information.
     */
    @NotNull
    public Engine<String> getEngine()
    {
        return m__Engine;
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
     * {@inheritDoc}
     */
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
        @Nullable final Log t_Log = UniqueLogFactory.getLog(AbstractJdbcMetadataManager.class);

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
        @Nullable final Log t_Log = UniqueLogFactory.getLog(AbstractJdbcMetadataManager.class);

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
    @SuppressWarnings("unused")
    @NotNull
    protected String concat(
        @NotNull final String[] values, @NotNull final String separator)
    {
        @NotNull final StringBuilder result = new StringBuilder();

        for (int t_iIndex = 0; t_iIndex < values.length; t_iIndex++)
        {
            @Nullable final String t_strValue = values[t_iIndex];

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
     * {@inheritDoc}
     */
    @Override
    public void eagerlyFetchMetadata()
        throws SQLException, QueryJException
    {
        if (getTables().size() == 0)
        {
            @Nullable final Chronometer t_Chronometer;

            @Nullable final Log t_Log = UniqueLogFactory.getLog(AbstractJdbcMetadataManager.class);
            if (   (t_Log != null)
                && (t_Log.isInfoEnabled()))
            {
                t_Chronometer = new Chronometer();
                t_Log.info("Starting database crawl");
            }
            else
            {
                t_Chronometer = null;
            }
            setTables(
                extractTableMetadata(
                    getTableNames(),
                    getMetaData(),
                    getCatalog(),
                    getSchema(),
                    isCaseSensitive(),
                    getMetadataExtractionListener(),
                    MetaLanguageUtils.getInstance()));

            if (   (t_Log != null)
                && (t_Log.isInfoEnabled())
                && (t_Chronometer != null))
            {
                @NotNull final String t_strMessage = "Finished database crawl: " + t_Chronometer.now();
                t_Log.info(t_strMessage);
            }
        }
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
     * @return the list of tables.
     * @throws SQLException if the extraction fails.
     * @throws QueryJException if any other error occurs.
     */
    @NotNull
    protected List<Table<String, Attribute<String>, List<Attribute<String>>>> extractTableMetadata(
        @Nullable final List<String> tableNames,
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema,
        final boolean caseSensitiveness,
        @NotNull final MetadataExtractionListener metadataExtractionListener,
        @NotNull final MetaLanguageUtils metaLanguageUtils)
      throws  SQLException,
              QueryJException
    {
        List<String> t_lTableNames = tableNames;

        if (t_lTableNames == null)
        {
            t_lTableNames = new ArrayList<>();
        }

        @NotNull final List<TableIncompleteValueObject> t_lTables =
            extractTableNamesAndComments(
                metaData, catalog, schema, t_lTableNames, metadataExtractionListener, caseSensitiveness);

        if  (t_lTableNames.size() == 0)
        {
            t_lTableNames = retrieveTableNames(t_lTables);

            setTableNames(t_lTableNames);
        }

        metadataExtractionListener.tableNamesExtracted(t_lTableNames.size());

        metadataExtractionListener.tableMetadataExtractionStarted();

        bindParentChildRelationships(t_lTables, caseSensitiveness, metaLanguageUtils);

        extractTableColumns(
            metaData,
            catalog,
            schema,
            t_lTables,
            caseSensitiveness,
            metadataExtractionListener);

        extractPrimaryKeys(
            metaData,
            catalog,
            schema,
            t_lTables,
            caseSensitiveness,
            metadataExtractionListener);

        extractForeignKeys(
            metaData,
            catalog,
            schema,
            t_lTables,
            caseSensitiveness,
            metadataExtractionListener);

        metadataExtractionListener.tableMetadataExtracted();

        return cloneTables(t_lTables);
    }

    /**
     * Converts between given lists.
     * @param list the list to convert.
     * @return the list.
     */
    @NotNull
    protected List<Attribute<String>> toAttributeList(@NotNull final List<AttributeIncompleteValueObject> list)
    {
        @NotNull final List<Attribute<String>> result = new ArrayList<>(list.size());

        for (@Nullable final AttributeIncompleteValueObject t_Attribute : list)
        {
            result.add(t_Attribute);
        }

        return result;
    }

    /**
     * Converts between given lists.
     * @param list the list to convert.
     * @return the foreign keys.
     */
    @NotNull
    protected List<ForeignKey<String>> toForeignKeyList(@NotNull final List<ForeignKeyIncompleteValueObject> list)
    {
        @NotNull final List<ForeignKey<String>> result = new ArrayList<>(list.size());

        for (@Nullable final ForeignKey<String> t_Item : list)
        {
            result.add(t_Item);
        }

        return result;
    }

    /**
     * Clones given incomplete tables.
     * @param tables the tables to clone.
     * @return the ultimate table list.
     */
    protected List<Table<String, Attribute<String>, List<Attribute<String>>>> cloneTables(
        @NotNull final Collection<TableIncompleteValueObject> tables)
    {
        @NotNull final List<Table<String, Attribute<String>, List<Attribute<String>>>> result =
            new ArrayList<>(tables.size());

        for (@Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table : tables)
        {
            if (t_Table != null)
            {
                result.add(cloneTable(t_Table));
            }
        }
        return result;
    }

    /**
     * Clones given incomplete tables.
     * @param table the table to clone.
     * @return the ultimate table list.
     */
    protected Table<String, Attribute<String>, List<Attribute<String>>> cloneTable(
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table)
    {
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> result;

        if (table instanceof TableValueObject)
        {
            result = table;
        }
        else
        {
            Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
                table.getParentTable();

            if (t_Table != null)
            {
                t_Table = cloneTable(t_Table);
            }
            result =
                new TableValueObject(
                    table.getName(),
                    table.getComment(),
                    cloneAttributes(table.getPrimaryKey()),
                    cloneAttributes(table.getAttributes()),
                    cloneForeignKeys(table.getForeignKeys()),
                    t_Table,
                    table.getStaticAttribute(),
                    table.isVoDecorated(),
                    table.isRelationship());
        }

        return result;
    }

    /**
     * Clones given attributes.
     * @param attributes the attributes to clone.
     * @return such attributes.
     */
    @NotNull
    protected List<Attribute<String>> cloneAttributes(@NotNull final List<Attribute<String>> attributes)
    {
        return cloneAttributes(attributes, MetaLanguageUtils.getInstance());
    }

    /**
     * Clones given attributes.
     * @param attributes the attributes to clone.
     * @param metaLanguageUtils the {@link MetaLanguageUtils} instance.
     * @return such attributes.
     */
    @NotNull
    protected List<Attribute<String>> cloneAttributes(
        @NotNull final List<Attribute<String>> attributes,
        @NotNull final MetaLanguageUtils metaLanguageUtils)
    {
        @NotNull final List<Attribute<String>> result = new ArrayList<>(attributes.size());

        for (@Nullable final Attribute<String> t_Attribute : attributes)
        {
            if (t_Attribute != null)
            {
                result.add(cloneAttribute(t_Attribute, metaLanguageUtils));
            }
        }

        return result;
    }

    /**
     * Clones given attribute.
     * @param attribute the {@link Attribute} to clone.
     * @param metaLanguageUtils the {@link MetaLanguageUtils} instance.
     * @return the cloned attribute.
     */
    @NotNull
    public Attribute<String> cloneAttribute(
        @NotNull final Attribute<String> attribute,
        @NotNull final MetaLanguageUtils metaLanguageUtils)
    {
        @Nullable final String t_strComment = attribute.getComment();

        @Nullable final String t_strSequence;

        if (t_strComment == null)
        {
            t_strSequence = null;
        }
        else
        {
            t_strSequence = metaLanguageUtils.retrieveColumnOraseq(t_strComment);
        }


        return
            new AttributeValueObject(
                attribute.getName(),
                attribute.getTypeId(),
                attribute.getType(),
                attribute.getTableName(),
                attribute.getComment(),
                attribute.getOrdinalPosition(),
                attribute.getLength(),
                attribute.getPrecision(),
                attribute.getKeyword(),
                attribute.getRetrievalQuery(),
                t_strSequence,
                attribute.isNullable(),
                attribute.getValue(),
                attribute.isBoolean(),
                attribute.isReadOnly(),
                attribute.getBooleanTrue(),
                attribute.getBooleanFalse(),
                attribute.getBooleanNull());
    }

    /**
     * Clones given foreign keys.
     * @param foreignKeys the {@link org.acmsl.queryj.metadata.vo.AbstractForeignKey foreign keys}.
     * @return the cloned foreign keys.
     */
    @NotNull
    protected List<ForeignKey<String>> cloneForeignKeys(
        @NotNull final List<ForeignKey<String>> foreignKeys)
    {
        @NotNull final List<ForeignKey<String>> result = new ArrayList<>(foreignKeys.size());

        for (@Nullable final ForeignKey<String> t_ForeignKey : foreignKeys)
        {
            if (t_ForeignKey != null)
            {
                result.add(
                    new ForeignKeyValueObject(
                        t_ForeignKey.getSourceTableName(),
                        cloneAttributes(t_ForeignKey.getAttributes()),
                        t_ForeignKey.getTargetTableName(),
                        t_ForeignKey.isNullable()));
            }
        }
        return result;
    }

    /**
     * Appends parent attributes into their children.
     * @param tables the list of tables.
     * @param attributes the map of tableName -&gt; attributes.
     */
    protected void bindAttributes(
        @NotNull final Collection<TableIncompleteValueObject> tables,
        @NotNull final Map<String, List<AttributeIncompleteValueObject>> attributes)
    {
        Table<String, Attribute<String>, List<Attribute<String>>> t_ParentTable;
        List<Attribute<String>> t_lParentTableAttributes;
        List<AttributeIncompleteValueObject> t_lChildTableAttributes;
        List<Attribute<String>> t_lCompleteAttributes;

        for (@NotNull final TableIncompleteValueObject t_Table : tables)
        {
            t_lChildTableAttributes = attributes.get(t_Table.getName());

            t_ParentTable = t_Table.getParentTable();

            if (t_ParentTable != null)
            {
                t_lParentTableAttributes = t_ParentTable.getAttributes();

                t_lCompleteAttributes =
                    new ArrayList<>(t_lParentTableAttributes.size() + t_lChildTableAttributes.size());

                t_lCompleteAttributes.addAll(t_lParentTableAttributes);
                t_lCompleteAttributes.addAll(t_lChildTableAttributes);
                t_Table.setAttributes(t_lCompleteAttributes);
            }
            else
            {
                t_Table.setAttributes(toAttributeList(t_lChildTableAttributes));
            }
        }

    }
    /**
     * Binds parent-child relationships within given list.
     * @param tables the list of tables.
     * @param caseSensitiveness whether to care about case sensitiveness or not.
     * @param metaLanguageUtils the {@link MetaLanguageUtils} instance.
     */
    protected void bindParentChildRelationships(
        @NotNull final Collection<TableIncompleteValueObject> tables,
        final boolean caseSensitiveness,
        @NotNull final MetaLanguageUtils metaLanguageUtils)
    {
        String t_strTableName;
        Table<String, Attribute<String>, List<Attribute<String>>> t_Parent;

        for (@NotNull final TableIncompleteValueObject t_Table : tables)
        {
            t_strTableName = t_Table.getName();
            t_Parent =
                retrieveParentTable(t_strTableName, tables, caseSensitiveness, metaLanguageUtils);

            if (t_Parent != null)
            {
                t_Table.setParentTable(t_Parent);
            }
        }
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
    protected Table<String, Attribute<String>, List<Attribute<String>>> retrieveParentTable(
        @NotNull final String tableComment,
        @NotNull final Collection<? extends Table<String, Attribute<String>, List<Attribute<String>>>> tables,
        final boolean caseSensitiveness,
        @NotNull final MetaLanguageUtils metaLanguageUtils)
    {
        Table<String, Attribute<String>, List<Attribute<String>>> result = null;

        @Nullable final String t_strParentTable = metaLanguageUtils.retrieveDeclaredParent(tableComment);

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
    protected Table<String, Attribute<String>, List<Attribute<String>>> findTableByName(
        @NotNull final String table,
        @NotNull final Collection<? extends Table<String, Attribute<String>, List<Attribute<String>>>> tables,
        final boolean caseSensitiveness)
    {
        @Nullable Table<String, Attribute<String>, List<Attribute<String>>> result = null;

        for (@Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table : tables)
        {
            if (t_Table != null)
            {
                if (caseSensitiveness)
                {
                    if (table.equals(t_Table.getName()))
                    {
                        result = t_Table;
                        break;
                    }
                }
                else
                {
                    if (table.equalsIgnoreCase(t_Table.getName()))
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
    protected List<String> retrieveTableNames(
        @NotNull final List<? extends Table<String, Attribute<String>, List<Attribute<String>>>> tables)
    {
        @NotNull final List<String> result = new ArrayList<>(tables.size());

        for (@Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table : tables)
        {
            if (t_Table != null)
            {
                result.add(t_Table.getName());
            }
        }

        return result;
    }

    /**
     * Extracts the table names and comments.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableNames the fixed table names to process.
     * @param metadataExtractionListener the metadata extraction listener.
     * @param caseSensitiveness whether the checks are case sensitive or not.
     * @return the list of all table names.
     * @throws SQLException if the extraction fails.
     * @throws QueryJException if any other error occurs.
     */
    protected abstract List<TableIncompleteValueObject> extractTableNamesAndComments(
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable  String schema,
        @NotNull final List<String> tableNames,
        @NotNull final MetadataExtractionListener metadataExtractionListener,
        final boolean caseSensitiveness)
        throws  SQLException,
                QueryJException;

    /**
     * Extracts the column information for given tables.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tables the tables.
     * @param caseSensitiveness whether the table names are case sensitive or not.
     * @param metadataExtractionListener the metadata extraction listener.
     * @throws SQLException if the extraction fails.
     * @throws QueryJException if any other error occurs.
     */
    protected abstract void extractTableColumns(
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema,
        @NotNull final List<TableIncompleteValueObject> tables,
        final boolean caseSensitiveness,
        @Nullable final MetadataExtractionListener metadataExtractionListener)
        throws  SQLException,
                QueryJException;

    /**
     * Extracts the primary keys for given tables.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tables the tables.
     * @param caseSensitiveness whether the table names are case sensitive or not.
     * @param metadataExtractionListener the metadata extraction listener.
     * @throws SQLException if the extraction fails.
     * @throws QueryJException if any other error occurs.
     */
    protected abstract void extractPrimaryKeys(
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema,
        @NotNull final List<TableIncompleteValueObject> tables,
        final boolean caseSensitiveness,
        @Nullable final MetadataExtractionListener metadataExtractionListener)
        throws  SQLException,
                QueryJException;

    /**
     * Extracts the foreign keys for given tables.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tables the tables.
     * @param caseSensitiveness whether the table names are case sensitive or not.
     * @param metadataExtractionListener the metadata extraction listener.
     * @throws SQLException if the extraction fails.
     * @throws QueryJException if any other error occurs.
     */
    protected abstract void extractForeignKeys(
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema,
        @NotNull final List<TableIncompleteValueObject> tables,
        final boolean caseSensitiveness,
        @Nullable final MetadataExtractionListener metadataExtractionListener)
        throws  SQLException,
                QueryJException;

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ColumnDAO getColumnDAO()
    {
        return new MetadataManagerColumnDAO(this);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public PrimaryKeyDAO getPrimaryKeyDAO()
    {
        return new MetadataManagerPrimaryKeyDAO(this);
    }

    /**
     *{@inheritDoc}
     */
    @NotNull
    @Override
    public ForeignKeyDAO getForeignKeyDAO()
    {
        return new MetadataManagerForeignKeyDAO(this);
    }

    /**
     * Checks whether the generation phase is enabled for given table.
     * @param tableName the table name.
     * @return <code>true</code> in such case.
     */
    @Override
    public boolean isGenerationAllowedForTable(@NotNull final String tableName)
    {
        if (DebugUtils.getInstance().debugEnabledForTable(tableName))
        {
//            @SuppressWarnings("unused")
//            int a = 1;
        }

        return
            isGenerationAllowed(
                retrieveExplicitlyEnabledTables(),
                retrieveExplicitlyDisabledTables(),
                tableName);
    }

    /**
     * Checks whether the generation phase is enabled for given item.
     * @param enabled the explicitly-enabled ones.
     * @param disabled the explicitly-disabled ones.
     * @param item the item.
     * @return <code>true</code> in such case.
     */
    protected final boolean isGenerationAllowed(
        @Nullable final String[] enabled,
        @Nullable final String[] disabled,
        @NotNull final String item)
    {
        boolean result = true;

        boolean t_bExplicitlyEnabled = false;

        if (enabled != null)
        {
            t_bExplicitlyEnabled = Arrays.asList(enabled).contains(item);

            result = t_bExplicitlyEnabled;
        }

        if (   (!t_bExplicitlyEnabled)
            && (enabled != null)
            && (disabled != null))
        {
            @NotNull final List<String> t_lDisabled = Arrays.asList(disabled);

            result =
                !(   (t_lDisabled.contains("*"))
                  || (enabled.length > 0))
                && !Arrays.asList(disabled).contains(item);
        }

        return result;
    }

    /**
     * Retrieves the explicitly enabled table names, via environment property. This means
     * no other tables should be processed.
     * @return such list.
     */
    @NotNull
    public String[] retrieveExplicitlyEnabledTables()
    {
        String[] result = null;

        @Nullable final String t_strProperty = System.getProperty(TABLES_ENABLED);

        if (t_strProperty != null)
        {
            result = parseProperty(t_strProperty);
        }

        if (result == null)
        {
            result = new String[0];
        }

        return result;
    }

    /**
     * Retrieves the explicitly disabled table names, via environment property. This means
     * no other tables should be processed.
     * @return such list.
     */
    @NotNull
    public String[] retrieveExplicitlyDisabledTables()
    {
        String[] result = null;

        @Nullable final String t_strProperty = System.getProperty(TABLES_DISABLED);

        if (t_strProperty != null)
        {
            result = parseProperty(t_strProperty);
        }

        if (result == null)
        {
            result = new String[0];
        }

        return result;
    }

    /**
     * Parses given environment property to find out whether some tables are
     * explicitly specified.
     * @param environmentProperty the environment property.
     * @return the tables specified in given environment property.
     */
    @NotNull
    protected String[] parseProperty(@NotNull final String environmentProperty)
    {
        return environmentProperty.split(",");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGenerationAllowedForForeignKey(@NotNull final ForeignKey<String> foreignKey)
    {
        return
            isGenerationAllowed(
                retrieveExplicitlyEnabledForeignKeys(),
                retrieveExplicitlyDisabledForeignKeys(),
                toNormalizedString(foreignKey));
    }

    /**
     * Retrieves a normalized representation of given foreign key.
     * @param foreignKey the foreign key.
     * @return such representation.
     */
    @NotNull
    protected String toNormalizedString(@NotNull final ForeignKey<String> foreignKey)
    {
        return
            foreignKey.getSourceTableName()
            + "(" + toNormalizedString(foreignKey.getAttributes()) + ")->"
            + foreignKey.getTargetTableName();
    }

    /**
     * Retrieves a normalized representation of given attributes.
     * @param attributes the attributes.
     * @return such representation.
     */
    @NotNull
    protected String toNormalizedString(@NotNull final List<Attribute<String>> attributes)
    {
        @NotNull final StringBuilder t_sbResult = new StringBuilder();

        boolean t_bFirstTime = true;

        for (@Nullable final Attribute<String> t_Attribute : attributes)
        {
            if (!t_bFirstTime)
            {
                t_sbResult.append(",");
            }
            else
            {
                t_bFirstTime = false;
            }
            if (t_Attribute != null)
            {
                t_sbResult.append(t_Attribute.getName());
            }

        }

        return t_sbResult.toString();
    }

    /**
     * Retrieves the explicitly enabled foreign keys, via environment property. This means
     * no other tables should be processed.
     * @return such list.
     */
    @NotNull
    public String[] retrieveExplicitlyEnabledForeignKeys()
    {
        String[] result = null;

        @Nullable final String t_strProperty = System.getProperty(FOREIGN_KEYS_ENABLED);

        if (t_strProperty != null)
        {
            result = parseProperty(t_strProperty);
        }

        if (result == null)
        {
            result = new String[0];
        }

        return result;
    }

    /**
     * Retrieves the explicitly disabled foreign keys, via environment property. This means
     * no other tables should be processed.
     * @return such list.
     */
    @NotNull
    public String[] retrieveExplicitlyDisabledForeignKeys()
    {
        String[] result = null;

        @Nullable final String t_strProperty = System.getProperty(FOREIGN_KEYS_DISABLED);

        if (t_strProperty != null)
        {
            result = parseProperty(t_strProperty);
        }

        if (result == null)
        {
            result = new String[0];
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
        @NotNull final String table,
        @NotNull final List<? extends Table<String, Attribute<String>, List<Attribute<String>>>> fixedTables,
        final boolean caseSensitiveness)
    {
        boolean result = true;

        for (@Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_FixedTable : fixedTables)
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
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table,
        @NotNull final List<? extends Table<String, Attribute<String>, List<Attribute<String>>>> fixedTables,
        final boolean caseSensitiveness)
    {
        boolean result = true;

        for (@Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_FixedTable : fixedTables)
        {
            if (t_FixedTable != null)
            {
                if (caseSensitiveness)
                {
                    result = table.getName().equals(t_FixedTable.getName());
                }
                else
                {
                    result = table.getName().equalsIgnoreCase(t_FixedTable.getName());
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
        @NotNull final String table, final boolean caseSensitiveness, @NotNull final List<String> fixedTables)
    {
        boolean result = true;

        for (@Nullable final String t_strFixedTable : fixedTables)
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
     * Finds a concrete table in given list.
     * @param name the table name.
     * @param tables the list of tables.
     * @param caseSensitiveness whether to care about case sensitiveness or not.
     * @return the matching table.
     */
    @Nullable
    protected Table<String, Attribute<String>, List<Attribute<String>>> findTable(
        @NotNull final String name,
        @NotNull final List<? extends Table<String, Attribute<String>, List<Attribute<String>>>> tables,
        final boolean caseSensitiveness)
    {
        @Nullable Table<String, Attribute<String>, List<Attribute<String>>> result = null;

        for (@Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table : tables)
        {
            if (t_Table != null)
            {
                if (   (caseSensitiveness)
                       && (t_Table.getName().equals(name)))
                {
                    result = t_Table;
                    break;
                }
                else if (   (!caseSensitiveness)
                            && (t_Table.getName().equalsIgnoreCase(name)))
                {
                    result = t_Table;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Finds a concrete attribute in given list.
     * @param name the table name.
     * @param attributes the list of attributes.
     * @param caseSensitiveness whether to care about case sensitiveness or not.
     * @return the matching attribute.
     */
    @Nullable
    protected Attribute<String> findAttribute(
        @NotNull final String name,
        @NotNull final List<? extends Attribute<String>> attributes,
        final boolean caseSensitiveness)
    {
        @Nullable Attribute<String> result = null;

        for (@Nullable final Attribute<String> t_Attribute : attributes)
        {
            if (t_Attribute != null)
            {
                if (   (caseSensitiveness)
                    && (t_Attribute.getName().equals(name)))
                {
                    result = t_Attribute;
                    break;
                }
                else if (   (!caseSensitiveness)
                         && (t_Attribute.getName().equalsIgnoreCase(name)))
                {
                    result = t_Attribute;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Changes the ordinal position of given {@link org.acmsl.queryj.metadata.vo.Attribute}. Involves
     * cloning if necessary.
     * @param position the new position.
     * @param attribute the {@link org.acmsl.queryj.metadata.vo.Attribute}.
     * @return the updated attribute.
     */
    @NotNull
    protected Attribute<String> fixOrdinalPosition(final int position, @NotNull final Attribute<String> attribute)
    {
        Attribute<String> result = attribute;

        if (result instanceof AttributeIncompleteValueObject)
        {
            ((AttributeIncompleteValueObject) result).setOrdinalPosition(position);
        }
        else
        {
            result =
                new AttributeValueObject(
                    result.getName(),
                    result.getTypeId(),
                    result.getType(),
                    result.getTableName(),
                    result.getComment(),
                    position,
                    result.getLength(),
                    result.getPrecision(),
                    result.getKeyword(),
                    result.getRetrievalQuery(),
                    result.getSequence(),
                    result.isNullable(),
                    result.getValue(),
                    result.isReadOnly(),
                    result.isBoolean(),
                    result.getBooleanTrue(),
                    result.getBooleanFalse(),
                    result.getBooleanNull());
        }

        return result;
    }

    /**
     * Fixes the ordinal positions of given attributes, according to their
     * order in the list (involves attribute cloning if necessary).
     * @param list the list of attributes.
     */
    protected void fixOrdinalPositions(@NotNull final List<Attribute<String>> list)
    {
        Attribute<String> t_Attribute;

        for (int t_iIndex = 0; t_iIndex < list.size(); t_iIndex++)
        {
            t_Attribute = list.get(t_iIndex);

            if (t_Attribute != null)
            {
                list.set(t_iIndex, fixOrdinalPosition(t_iIndex, t_Attribute));
            }
        }

    }

    /**
     * Extracts the names of given tables.
     * @param tables the {@link Table} list.
     * @return the list of table names.
     */
    @NotNull
    protected List<String> extractTableNames(
        @NotNull final List<Table<String, Attribute<String>, List<Attribute<String>>>> tables)
    {
        @NotNull final List<String> result = new ArrayList<>(tables.size());

        for (@Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table : tables)
        {
            if (t_Table != null)
            {
                result.add(t_Table.getName());
            }
        }

        return result;
    }

    /**
     * Checks whether given exception identifies an "Invalid column name".
     * @param exception the exception.
     * @return {@code true} in such case.
     */
    protected SQLException unwrap(@NotNull final Throwable exception)
    {
        @Nullable final SQLException result;

        @Nullable Throwable underlying = exception.getCause();

        while (   (underlying != null)
               && (!(underlying instanceof SQLException)))
        {
            underlying = underlying.getCause();
        }

        if (underlying != null)
        {
            result = (SQLException) underlying;
        }
        else
        {
            result = null;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInvalidColumnNameException(@NotNull final Throwable exception)
    {
        final boolean result;

        @Nullable final SQLException underlying = unwrap(exception);

        if (underlying != null)
        {
            result = isInvalidColumnNameException(underlying);
        }
        else
        {
            result = false;
        }

        return result;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInvalidColumnTypeException(@NotNull final Throwable exception)
    {
        final boolean result;

        @Nullable final SQLException underlying = unwrap(exception);

        if (underlying != null)
        {
            result = isInvalidColumnTypeException(underlying);
        }
        else
        {
            result = false;
        }

        return result;
    }

    /**
     * Checks whether given exception identifies an "Invalid column name".
     * @param exception the exception.
     * @return {@code true} in such case.
     */
    protected abstract boolean isInvalidColumnNameException(@NotNull final SQLException exception);

    /**
     * Checks whether given exception identifies an "Invalid column type".
     * @param exception the exception.
     * @return {@code true} in such case.
     */
    protected abstract boolean isInvalidColumnTypeException(@NotNull final SQLException exception);

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{  \"class\": \"" + AbstractJdbcMetadataManager.class.getSimpleName() + "\""
            + ", \"package\": \"org.acmsl.queryj.metadata.engines\""
            + ", \"caseSensitive\": \"" + m__bCaseSensitive + "\""
            + ", \"name\": \"" + m__strName + "\""
            + ", \"metaData\": \"" + m__MetaData + "\""
            + ", \"metadataExtractionListener\": \"" + m__MetadataExtractionListener + "\""
            + ", \"catalog\": \"" + m__strCatalog + "\""
            + ", \"schema\": \"" + m__strSchema + "\""
            + ", \"tableNames\": \"" + m__lTableNames + "\""
//            + ", \"tables\": \"" + m__lTables + "\""
            + ", \"columns\": \"" + m__mColumns + "\""
            + ", \"disableTableExtraction\": \"" + m__bDisableTableExtraction + "\""
            + ", \"lazyTableExtraction\": \"" + m__bLazyTableExtraction + "\""
            + ", \"engine\": " + m__Engine
            + " }";
    }
}
