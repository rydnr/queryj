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
 * Filename: DatabaseMetaDataRetrievalHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Retrieves the database metadata instance.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJException;
import org.acmsl.queryj.api.exceptions.CannotRetrieveDatabaseMetadataException;
import org.acmsl.queryj.api.exceptions.CannotRetrieveDatabaseInformationException;
import org.acmsl.queryj.tools.ant.AntFieldElement;
import org.acmsl.queryj.tools.ant.AntFieldFkElement;
import org.acmsl.queryj.tools.ant.AntTableElement;
import org.acmsl.queryj.tools.ant.AntTablesElement;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.metadata.MetadataExtractionLogger;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.MetadataTypeManager;
import org.acmsl.queryj.metadata.engines.JdbcMetadataManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.metadata.vo.TableIncompleteValueObject;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Retrieves the database metadata instance.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class DatabaseMetaDataRetrievalHandler
    extends  AbstractQueryJCommandHandler
{
    /**
     * The Database Metadata attribute name.
     */
    public static final String DATABASE_METADATA = "jdbc.database.metadata";

    /**
     * The Database Metadata manager attribute name.
     */
    public static final String METADATA_MANAGER = "jdbc.database.metadata.manager";

    /**
     * The table names attribute name.
     */
    public static final String TABLES = ParameterValidationHandler.TABLES;

    /**
     * The table fields attribute name.
     */
    @SuppressWarnings("unused")
    public static final String TABLE_FIELDS = "table.fields";

    /**
     * The flag indicating whether the extraction has already been done.
     */
    public static final String METADATA_EXTRACTION_ALREADY_DONE =
        "metadata.extraction.already.done";

    /**
     * The database product name.
     */
    public static final String DATABASE_PRODUCT_NAME = "database.product.name";

    /**
     * The database product version.
     */
    public static final String DATABASE_PRODUCT_VERSION = "database.product.version";

    /**
     * The database major version.
     */
    public static final String DATABASE_MAJOR_VERSION = "database.major.version";

    /**
     * The database minor version.
     */
    public static final String DATABASE_MINOR_VERSION = "database.minor.version";

    /**
     * The entry key for case sensitivity.
     */
    public static final String CASE_SENSITIVE = "database.case.sensitive";

    /**
     * Creates a {@link DatabaseMetaDataRetrievalHandler} instance.
     */
    public DatabaseMetaDataRetrievalHandler() {}

    /**
     * Handles given parameters.
     *
     *
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand parameters)
        throws  QueryJBuildException
    {
        final boolean result;

        @NotNull final DatabaseMetaData t_Metadata =
            retrieveDatabaseMetaData(parameters);

        result =
            handle(
                parameters,
                retrieveAlreadyDoneFlag(parameters),
                t_Metadata);

        return result;
    }

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @param alreadyDone the flag indicating whether the metadata
     * extraction has already been done.
     * @param metaData the database metadata.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected boolean handle(
        @NotNull final QueryJCommand parameters,
        final boolean alreadyDone,
        @NotNull final DatabaseMetaData metaData)
      throws  QueryJBuildException
    {
        boolean result = false;

        if  (    (!alreadyDone)
              && (checkVendor(metaData, parameters)))
        {
            analyzeMetaData(metaData, parameters);

            result =
                /*
                handle(
                    parameters,
                    metaData,
                    retrieveExtractTables(parameters),
                    retrieveExtractProcedures(parameters));
                */
                handle(parameters, buildMetadataManager(parameters, metaData));
        }

        return result;
    }

    /**
     * Analyzes the database metadata.
     * @param metaData such metadata.
     * @param parameters the command.
     */
    protected void analyzeMetaData(@NotNull final DatabaseMetaData metaData, @NotNull final QueryJCommand parameters)
    {
        boolean t_bCaseSensitive = false;

        try
        {
            t_bCaseSensitive =
                   metaData.storesLowerCaseIdentifiers()
                || metaData.storesLowerCaseQuotedIdentifiers()
                || metaData.storesMixedCaseIdentifiers()
                || metaData.storesMixedCaseQuotedIdentifiers()
                || metaData.storesUpperCaseIdentifiers()
                || metaData.storesUpperCaseQuotedIdentifiers();
        }
        catch (@NotNull final SQLException cannotCheckCaseSensitivity)
        {
            @Nullable final Log t_Log =
                UniqueLogFactory.getLog(
                    DatabaseMetaDataRetrievalHandler.class);

            if  (t_Log != null)
            {
                t_Log.error(
                    "Cannot check whether the database engine is case sensitive or not.",
                    cannotCheckCaseSensitivity);
            }
        }

        storeCaseSensitive(t_bCaseSensitive, parameters);
    }

    /**
     * Retrieves the explicitly-defined table names.
     * @param parameters the parameters.
     * @return the array of table names.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected List<Table<String, Attribute<String>>> retrieveExplicitTableNames(@NotNull final QueryJCommand parameters)
    {
        List<Table<String, Attribute<String>>> result = null;

        @Nullable final AntTablesElement t_TablesElement =
            retrieveTablesElement(parameters);

        @Nullable final Iterator<AntTableElement> t_itTableElements;

        @Nullable final Collection<AntTableElement> t_cTableElements;

        if  (t_TablesElement != null)
        {
            t_cTableElements = t_TablesElement.getTables();

            if  (   (t_cTableElements != null)
                 && (t_cTableElements.size() > 0))
            {
                result = new ArrayList<Table<String, Attribute<String>>>(t_cTableElements.size());

                t_itTableElements = t_cTableElements.iterator();

                while  (t_itTableElements.hasNext())
                {
                    @NotNull final AntTableElement t_Table = t_itTableElements.next();

                    result.add(convertToTable(t_Table));
                }

                storeTables(result, parameters);
            }
        }

        if (result == null)
        {
            result = new ArrayList<Table<String, Attribute<String>>>(0);
        }

        return result;
    }

    /**
     * Converts to a {@link Table}.
     * @param table the {@link AntTableElement} instance to convert.
     * @return the converted table.
     */
    @NotNull
    protected Table<String, Attribute<String>> convertToTable(@NotNull final AntTableElement table)
    {
        @NotNull final TableIncompleteValueObject result = new TableIncompleteValueObject(table.getName(), null);

        @Nullable final List<AntFieldElement> t_lFields = table.getFields();

        if (t_lFields != null)
        {
            @NotNull final List<Attribute<String>> t_lAttributes = new ArrayList<Attribute<String>>(t_lFields.size());

            for (@Nullable final AntFieldElement t_Field : t_lFields)
            {
                if (t_Field != null)
                {
                    t_lAttributes.add(t_Field);
                }
            }

            result.setAttributes(t_lAttributes);
        }

        // TODO: add foreign keys

        return result;
    }

    /**
     * Checks whether there are at least one field in any explicitly-defined table.
     * @param explicitTables the explicitly-defined table information.
     * @return <code>true</code> in such case.
     */
    @SuppressWarnings("unused")
    protected boolean areExplicitTableFieldsEmpty(@Nullable final AntTablesElement explicitTables)
    {
        boolean result = true;

        if (explicitTables != null)
        {
            @Nullable final Iterator<AntTableElement> t_itTableElements;

            @Nullable final Collection<AntTableElement> t_cTableElements = explicitTables.getTables();

            @Nullable Collection<AntFieldElement> t_cFieldElements;

            t_itTableElements = t_cTableElements.iterator();

            while  (t_itTableElements.hasNext())
            {
                @NotNull final AntTableElement t_Table =
                    t_itTableElements.next();

                t_cFieldElements = t_Table.getFields();

                if  (   (t_cFieldElements != null)
                     && (t_cFieldElements.size() > 0))
                {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Process explicit schema.
     * @param parameters the parameter map.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param explicitTables the {@link AntTablesElement} instance.
     * @param fieldMap a map to store field information.
     */
    protected void processExplicitSchema(
        @SuppressWarnings("unused") @NotNull final QueryJCommand parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @Nullable final AntTablesElement explicitTables,
        @SuppressWarnings("unused") @NotNull final Map<String, List<AntTableElement>> tableMap,
        @NotNull final Map<String, List<String>> tableNameMap,
        @SuppressWarnings("unused") @NotNull final Map<String, List<AntFieldElement>> fieldMap,
        @NotNull final Map<String, List<AntFieldFkElement>> fieldFkMap,
        @NotNull final Map<String, String> fieldNameMap,
        @NotNull final Map<String, List<Attribute<String>>> attributeMap)
    {
        @Nullable List<AntTableElement> t_cTableElements = null;

        if (explicitTables != null)
        {
            t_cTableElements = explicitTables.getTables();
        }

        @Nullable Iterator<AntTableElement> t_itTableElements = null;

        @Nullable List<AntFieldElement> t_cFieldElements;

        List<String> t_lTables;

        if  (t_cTableElements != null)
        {
            t_itTableElements = t_cTableElements.iterator();
        }

        if  (t_itTableElements != null)
        {
            while  (t_itTableElements.hasNext())
            {
                @NotNull final AntTableElement t_Table =
                    t_itTableElements.next();

                t_lTables = tableNameMap.get(buildTableKey());

                if  (t_lTables == null)
                {
                    t_lTables = new ArrayList<String>();
                    tableNameMap.put(buildTableKey(), t_lTables);
                }

                t_lTables.add(t_Table.getName());

                t_cFieldElements = t_Table.getFields();

                if  (   (t_cFieldElements  != null)
                     && (t_cFieldElements.size() > 0))
                {
                    @NotNull final Iterator<AntFieldElement> t_itFieldElements = t_cFieldElements.iterator();

                    while  (t_itFieldElements.hasNext())
                    {
                        @NotNull final AntFieldElement t_Field = t_itFieldElements.next();

                        @SuppressWarnings("unchecked")
                        @Nullable List<Attribute<String>> t_lFields =
                            attributeMap.get(buildTableFieldsKey(t_Table.getName()));

                        if  (t_lFields == null)
                        {
                            t_lFields = new ArrayList<Attribute<String>>(4);

                            attributeMap.put(buildTableFieldsKey(t_Table.getName()), t_lFields);
                        }

                        t_lFields.add(t_Field);

                        if  (t_Field.isPk())
                        {
                            @Nullable List<Attribute<String>> t_lPks =
                                attributeMap.get(buildPkKey(t_Table.getName()));

                            if  (t_lPks == null)
                            {
                                t_lPks = new ArrayList<Attribute<String>>(1);
                                attributeMap.put(buildPkKey(t_Table.getName()), t_lPks);
                            }

                            t_lPks.add(t_Field);

                            fieldNameMap.put(
                                buildPkKey(t_Table.getName(), t_Field.getName()),
                                t_Field.getName());
                        }

                        @Nullable List<AntFieldFkElement> t_lFieldFks =
                            t_Field.getFieldFks();

                        if (t_lFieldFks == null)
                        {
                            t_lFieldFks = new ArrayList<AntFieldFkElement>(0);
                        }
                        fieldFkMap.put(
                            buildFkKey(t_Table.getName(), t_Field.getName()),
                            t_lFieldFks);

                        metadataManager.getColumnDAO().insert(
                            t_Table.getName(),
                            t_Field.getName(),
                            metadataTypeManager.getJavaType(
                                t_Field.getType()));
                    }
                }

                @Nullable final List<Attribute<String>> t_lFields =
                    attributeMap.get(buildTableFieldsKey(t_Table.getName()));

                if (t_lFields != null)
                {
                    metadataManager.getPrimaryKeyDAO().insert(
                        t_Table.getName(), t_lFields);
                }
            }
        }
    }

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @SuppressWarnings("unchecked")
    protected boolean handle(
        @NotNull final QueryJCommand parameters, @Nullable final MetadataManager metadataManager)
        throws  QueryJBuildException
    {
        final boolean result = false;

        @NotNull final DatabaseMetaData t_Metadata = retrieveDatabaseMetaData(parameters);

        storeMetadata(t_Metadata, parameters);

        @Nullable final MetadataTypeManager t_MetadataTypeManager;

        if  (metadataManager != null)
        {
            @NotNull final Map<String, ?> t_mKeys = new HashMap<String, Object>();

            storeMetadataManager(metadataManager, parameters);

            t_MetadataTypeManager =
                metadataManager.getMetadataTypeManager();

            processExplicitSchema(
                parameters,
                metadataManager,
                t_MetadataTypeManager,
                retrieveTablesElement(parameters),
                (Map<String, List<AntTableElement>>) t_mKeys,
                (Map<String, List<String>>) t_mKeys,
                (Map<String, List<AntFieldElement>>) t_mKeys,
                (Map<String, List<AntFieldFkElement>>) t_mKeys,
                (Map<String, String>) t_mKeys,
                (Map<String, List<Attribute<String>>>) t_mKeys);

            @Nullable final List<Table<String, Attribute<String>>> t_lTables =
                (List<Table<String, Attribute<String>>>) t_mKeys.get(buildTableKey());

            if  (t_lTables != null)
            {
                final Iterator<Table<String, Attribute<String>>> t_itTables = t_lTables.iterator();

                while  (t_itTables.hasNext())
                {
                    @Nullable final Table<String, Attribute<String>> t_Table = t_itTables.next();

                    if  (t_Table != null)
                    {
                        @NotNull final List<Attribute<String>> t_lFields =
                            (List<Attribute<String>>)
                                t_mKeys.get(
                                    buildTableFieldsKey(
                                        t_Table.getName()));

                        final Iterator<Attribute<String>> t_itFields = t_lFields.iterator();

                        while  (t_itFields.hasNext())
                        {
                            @Nullable final Attribute<String> t_Field = t_itFields.next();

                            @NotNull final List<AntFieldFkElement> t_lFieldFks =
                                (List<AntFieldFkElement>)
                                    t_mKeys.get(
                                        buildFkKey(
                                            t_Table.getName(),
                                            t_Field.getName()));

                            final Iterator<AntFieldFkElement> t_itFieldFks = t_lFieldFks.iterator();

                            @NotNull final List<Attribute<String>> t_lFk = new ArrayList<Attribute<String>>(1);

                            while  (t_itFieldFks.hasNext())
                            {
                                @Nullable final AntFieldFkElement t_FieldFk = t_itFieldFks.next();

                                if  (t_FieldFk != null)
                                {
                                    t_lFk.add(
                                        metadataManager.getColumnDAO().findColumn(
                                            t_FieldFk.getTable(),
                                            t_FieldFk.getField()));
                                }
                            }

                            // TODO: use targetAttributes
                            metadataManager.getForeignKeyDAO().insert(
                                t_Table.getName(),
                                t_lFk,
                                t_lFk,
                                null,
                                null);
                        }
                    }
                }
            }
        }

        storeAlreadyDoneFlag(parameters);

        return result;
    }

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @param metaData the database metadata.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected boolean handle(
        @NotNull final QueryJCommand parameters,
        @NotNull final DatabaseMetaData metaData)
      throws  QueryJBuildException
    {
        final boolean result = false;

        storeMetadata(metaData, parameters);

        @Nullable final AntTablesElement t_TablesElement;

        t_TablesElement = retrieveTablesElement(parameters);

        @NotNull final List<Table<String, Attribute<String>>> t_lTables =
            extractTables(parameters, t_TablesElement);

        storeTables(t_lTables, parameters);

        @Nullable final MetadataManager t_MetadataManager =
            buildMetadataManager(parameters, metaData);

        if (t_MetadataManager != null)
        {
            storeMetadataManager(t_MetadataManager, parameters);
        }

        return result;
    }

    /**
     * Retrieves the names of the user-defined tables.
     * @param parameters the command parameters.
     * @param tablesElement the &lt;tables&gt; element.
     * @return such table names.
     */
    @NotNull
    @SuppressWarnings("unused")
    protected List<Table<String, Attribute<String>>> extractTables(
        @NotNull final QueryJCommand parameters, @Nullable final AntTablesElement tablesElement)
    {
        List<Table<String, Attribute<String>>> result = null;

        if (tablesElement != null)
        {
            result = extractTables(tablesElement.getTables());
        }

        if (result == null)
        {
            result = new ArrayList<Table<String, Attribute<String>>>(0);
        }

        return result;
    }

    /**
     * Retrieves the fields of the user-defined tables.
     * @param tables the table definitions.
     * @return such fields.
     */
    @NotNull
    protected List<Table<String, Attribute<String>>> extractTables(@Nullable final List<AntTableElement> tables)
    {
        @Nullable final List<Table<String, Attribute<String>>> result;

        final int t_iLength = (tables != null) ? tables.size() : 0;

        result = new ArrayList<Table<String, Attribute<String>>>(t_iLength);

        if (tables != null)
        {
            for (@Nullable final AntTableElement t_Table : tables)
            {
                if  (t_Table != null)
                {
                    result.add(convertToTable(t_Table));
                }
            }
        }

        return result;
    }

    /**
     * Retrieves whether the tables should be extracted on demand.
     * @param tablesElement the tables element.
     * @return the result of such analysis..
     */
    @SuppressWarnings("unused")
    protected boolean lazyTableExtraction(@Nullable final AntTablesElement tablesElement)
    {
        boolean result = false;

        if  (tablesElement != null)
        {
            result = lazyTableExtraction(tablesElement.getTables());
        }

        return result;
    }

    /**
     * Retrieves whether the tables should be extracted on demand.
     * @param tables the table definitions.
     * @return the result of such analysis.
     */
    protected boolean lazyTableExtraction(@Nullable final Collection<AntTableElement> tables)
    {
        boolean result = false;

        if (tables != null)
        {
            for (@Nullable final AntTableElement t_Table : tables)
            {
                if  (t_Table != null)
                {
                    @Nullable final Collection<AntFieldElement> t_cFields = t_Table.getFields();

                    if  (   (t_cFields != null)
                         && (t_cFields.size()> 0))
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
     * Extracts the procedures..
     * @param extractedMap the already-extracted information.
     * @param metadataManager the database metadata manager.
     * @param tableKey the key to store the tables.
     */
    @SuppressWarnings("unused")
    protected void extractForeignKeys(
        @NotNull final Collection<String> tables,
        @NotNull final Map<String, Collection<String>> extractedMap,
        @NotNull final MetadataManager metadataManager,
        @NotNull final Object tableKey)
    {
        final Iterator<String> t_itTables = tables.iterator();

        while  (t_itTables.hasNext())
        {
            @NotNull final String t_strTableName = t_itTables.next();

            @NotNull final Collection<String> t_cFields =
                extractedMap.get(buildTableFieldsKey(t_strTableName));

            final Iterator<String> t_itFields = t_cFields.iterator();

            while  (t_itFields.hasNext())
            {
                @NotNull final String t_strFieldName = t_itFields.next();

                @NotNull final Collection<String> t_cFieldFks =
                    extractedMap.get(buildFkKey(t_strTableName, t_strFieldName));

                @Nullable final Iterator<String> t_itFieldFks = null;

//                        if  (t_cFieldFks != null)
//                        {
//                            t_itFieldFks =
//                                t_cFieldFks.iterator();
//                        }
//
//                        if  (t_itFieldFks != null)
//                        {
//                            while  (t_itFieldFks.hasNext())
//                            {
//                                  @NotNull AntFieldFkElement t_FieldFk =
//                                      (AntFieldFkElement)
//                                          t_itFieldFks.next();
//
//                                  if  (t_FieldFk != null)
//                                  {
                                        // TODO
//                                        metadataManager.addForeignKey(
//                                            t_strTableName,
//                                            new String[] {t_strFieldName},
//                                            t_FieldFk.getTable(),
//                                            new String[]
//                                            {
//                                                t_FieldFk.getField()
//                                            });
//                                    }
//                                }
//                            }
            }
        }
    }

    /**
     * Retrieves the tables XML element stored in the
     * attribute map.
     * @param parameters the parameter map.
     * @return the table information.
     */
    @Nullable
    protected AntTablesElement retrieveTablesElement(@NotNull final QueryJCommand parameters)
    {
        return
            new QueryJCommandWrapper<AntTablesElement>(parameters)
                .getSetting(ParameterValidationHandler.EXPLICIT_TABLES);
    }

    /**
     * Annotates the database product name.
     * @param productName the product name.
     * @param parameters the parameters.
     */
    public void storeDatabaseProductName(
        @NotNull final String productName, @NotNull final QueryJCommand parameters)
    {
        parameters.setSetting(DATABASE_PRODUCT_NAME, productName);
    }

    /**
     * Retrieves the database product name.
     * @return such name.
     */
    @Nullable
    public String retrieveDatabaseProductName(@NotNull final QueryJCommand parameters)
    {
        return parameters.getSetting(DATABASE_PRODUCT_NAME);
    }

    /**
     * Annotates the database product version.
     * @param productVersion the product version.
     * @param parameters the parameters.
     */
    public void storeDatabaseProductVersion(
        @NotNull final String productVersion, @NotNull final QueryJCommand parameters)
    {
        parameters.setSetting(DATABASE_PRODUCT_VERSION, productVersion);
    }

    /**
     * Retrieves the database product version.
     * @return such version.
     */
    @Nullable
    public String retrieveDatabaseProductVersion(@NotNull final QueryJCommand parameters)
    {
        return parameters.getSetting(DATABASE_PRODUCT_VERSION);
    }

    /**
     * Annotates the database major version.
     * @param majorVersion the major version.
     * @param parameters the parameters.
     */
    public void storeDatabaseMajorVersion(final int majorVersion, @NotNull final QueryJCommand parameters)
    {
        new QueryJCommandWrapper<Integer>(parameters).setSetting(DATABASE_MAJOR_VERSION, majorVersion);
    }

    /**
     * Retrieves the database major version.
     * @return such version.
     */
    public int retrieveDatabaseMajorVersion(@NotNull final QueryJCommand parameters)
    {
        return parameters.getIntSetting(DATABASE_MAJOR_VERSION, -1);
    }

    /**
     * Annotates the database minor version.
     * @param minorVersion the minor version.
     * @param parameters the parameters.
     */
    public void storeDatabaseMinorVersion(final int minorVersion, @NotNull final QueryJCommand parameters)
    {
        new QueryJCommandWrapper<Integer>(parameters).setSetting(DATABASE_MINOR_VERSION, minorVersion);
    }

    /**
     * Retrieves the database minor version.
     * @return such version.
     */
    public int retrieveDatabaseMinorVersion(@NotNull final QueryJCommand parameters)
    {
        return parameters.getIntSetting(DATABASE_MINOR_VERSION, -1);
    }

    /**
     * Retrieves the database metadata manager using the database metadata and
     * parameters stored in the attribute map.
     * @param metaData the {@link DatabaseMetaData} instance.
     * @param parameters the parameter map.
     * @return the metadata manager instance.
     * @throws QueryJBuildException if the retrieval process cannot be
     * performed.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    protected MetadataManager buildMetadataManager(
        @NotNull final QueryJCommand parameters,
        @NotNull final DatabaseMetaData metaData)
      throws  QueryJBuildException
    {
        @Nullable MetadataManager result =
            retrieveCachedMetadataManager(parameters);

        @Nullable List<Table<String, Attribute<String>>> t_lTables = retrieveTables(parameters);

        if (t_lTables == null)
        {
            t_lTables = new ArrayList<Table<String, Attribute<String>>>(0);
        }

        if  (result == null)
        {
            result =
                buildMetadataManager(
                    parameters,
                    t_lTables,
                    retrieveDatabaseMetaData(parameters),
                    retrieveCatalog(parameters),
                    retrieveSchema(parameters),
                    retrieveCaseSensitive(parameters),
                    retrieveProductName(metaData),
                    retrieveProductVersion(metaData),
                    retrieveProductQuote(metaData));


        }

        try
        {
            result.eagerlyFetchMetadata();

            if (t_lTables.size() == 0)
            {
                storeTables(result.getTableDAO().findAllTables(), parameters);
            }
        }
        catch  (@NotNull final SQLException sqlException)
        {
            throw
                new CannotRetrieveDatabaseMetadataException(sqlException);
        }
        catch  (@NotNull final QueryJException queryjException)
        {
            throw
                new CannotRetrieveDatabaseMetadataException(queryjException);
        }

        return result;
    }

    /**
     * Retrieves the database metadata manager from the attribute map.
     * @param parameters the parameter map.
     * @return the manager.
     */
    @Nullable
    protected MetadataManager retrieveCachedMetadataManager(@NotNull final QueryJCommand parameters)
    {
        return
            new QueryJCommandWrapper<MetadataManager>(parameters)
                .getSetting(DatabaseMetaDataRetrievalHandler.METADATA_MANAGER);
    }

    /**
     * Retrieves whether the database engine is case sensitive or not.
     * @param parameters the parameters.
     * @return <code>true</code> in such case.
     */
    public boolean retrieveCaseSensitive(@NotNull final QueryJCommand parameters)
    {
        return parameters.getBooleanSetting(CASE_SENSITIVE, false);
    }

    /**
     * Retrieves whether the database engine is case sensitive or not.
     * @param parameters the parameters.
     */
    @SuppressWarnings("unchecked")
    public void storeCaseSensitive(final boolean caseSensitive, @NotNull final QueryJCommand parameters)
    {
        new QueryJCommandWrapper<Boolean>(parameters).setSetting(CASE_SENSITIVE, caseSensitive);
    }

    /**
     * Builds a database metadata manager.
     * @param tables the tables.
     * @param metaData the database metadata.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param caseSensitive whether the engine is case sensitive or not.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @return the metadata manager instance.
     * @throws QueryJBuildException whenever the required
     * parameters are not present or valid.
     */
    @NotNull
    protected MetadataManager buildMetadataManager(
        @SuppressWarnings("unused") @NotNull final QueryJCommand parameters,
        @NotNull final List<Table<String, Attribute<String>>> tables,
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema,
        final boolean caseSensitive,
        @NotNull final String engineName,
        @NotNull final String engineVersion,
        @NotNull final String quote)
      throws  QueryJBuildException
    {
        @NotNull final MetadataManager result;

        try 
        {
            result =
                new JdbcMetadataManager(
                    "jdbc",
                    metaData,
                    new MetadataExtractionLogger(),
                    catalog,
                    schema,
                    new ArrayList<String>(0),
                    tables,
                    false,
                    false,
                    caseSensitive,
                    engineName,
                    engineVersion,
                    quote);
        }
        catch  (@NotNull final RuntimeException exception)
        {
            throw exception;
        }
        catch  (@NotNull final Exception exception)
        {
            @Nullable final Log t_Log =
                UniqueLogFactory.getLog(
                    DatabaseMetaDataRetrievalHandler.class);

            if  (t_Log != null)
            {
                t_Log.error(
                    "Cannot build a database metadata manager.",
                    exception);
            }

            throw
                new CannotRetrieveDatabaseMetadataException(exception);
        }

        return result;
    }

    /**
     * Stores the database metadata in the attribute map.
     * @param metaData the database metadata.
     * @param parameters the parameter map.
     */
    protected void storeMetadata(
        @NotNull final DatabaseMetaData metaData, @NotNull final QueryJCommand parameters)
    {
        new QueryJCommandWrapper<DatabaseMetaData>(parameters).setSetting(DATABASE_METADATA, metaData);
    }

    /**
     * Stores a flag indicating the metadata extraction has already been done.
     * @param parameters the parameter map.
     */
    protected void storeAlreadyDoneFlag(@NotNull final QueryJCommand parameters)
    {
        new QueryJCommandWrapper<Boolean>(parameters).setSetting(METADATA_EXTRACTION_ALREADY_DONE, Boolean.TRUE);
    }

    /**
     * Retrieves the flag which indicates whether the metadata extraction
     * has been done already.
     * @param parameters the parameter map.
     */
    protected boolean retrieveAlreadyDoneFlag(@NotNull final QueryJCommand parameters)
    {
        return parameters.getBooleanSetting(METADATA_EXTRACTION_ALREADY_DONE, false);
    }

    /**
     * Stores the table names in the attribute map.
     * @param tables the tables.
     * @param parameters the parameter map.
     */
    protected void storeTables(
        @NotNull final List<Table<String, Attribute<String>>> tables, @NotNull final QueryJCommand parameters)
    {
        new QueryJCommandWrapper<List<Table<String, Attribute<String>>>>(parameters).setSetting(TABLES, tables);
    }

    /**
     * Retrieves the table names from the attribute map.
     * @param parameters the parameter map.
     * @return the table names.
     */
    @Nullable
    protected List<Table<String, Attribute<String>>> retrieveTables(@NotNull final QueryJCommand parameters)
    {
        return new QueryJCommandWrapper<List<Table<String, Attribute<String>>>>(parameters).getSetting(TABLES);
    }

    /**
     * Stores the database metadata manager in the attribute map.
     * @param metadataManager the metadata manager.
     * @param parameters the parameter map.
     */
    protected void storeMetadataManager(
        @NotNull final MetadataManager metadataManager, @NotNull final QueryJCommand parameters)
    {
        new QueryJCommandWrapper<MetadataManager>(parameters).setSetting(METADATA_MANAGER, metadataManager);
    }

    /**
     * Retrieves the JDBC catalog from the attribute map.
     * @param parameters the parameter map.
     * @return the catalog.
     */
    @Nullable
    protected String retrieveCatalog(@NotNull final QueryJCommand parameters)
    {
        return parameters.getSetting(ParameterValidationHandler.JDBC_CATALOG);
    }

    /**
     * Retrieves the JDBC schema from the attribute map.
     * @param parameters the parameter map.
     * @return the schema.
     */
    @Nullable
    protected String retrieveSchema(@NotNull final QueryJCommand parameters)
    {
        return parameters.getSetting(ParameterValidationHandler.JDBC_SCHEMA);
    }

    /**
     * Builds the table key.
     * @return the map key.
     */
    @NotNull
    protected String buildTableKey()
    {
        return "'@'@'table";
    }

    /**
     * Builds the table fields key.
     * @return the map key.
     */
    @NotNull
    protected String buildTableFieldsKey(@NotNull final Object key)
    {
        return ".98.table'@'@'fields`p" + key;
    }

    /**
     * Builds a pk key using given object.
     * @param firstKey the first object key.
     * @return the map key.
     */
    @NotNull
    protected String buildPkKey(@NotNull final Object firstKey)
    {
        return ".|\\|.pk" + firstKey;
    }

    /**
     * Builds a pk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    @NotNull
    protected String buildPkKey(
        @NotNull final String firstKey, @NotNull final String secondKey)
    {
        return buildPkKey(firstKey) + "-.,.,-" + secondKey;
    }

    /**
     * Builds a fk key using given object.
     * @param firstKey the first object key.
     * @return the map key.
     */
    @NotNull
    protected String buildFkKey(@NotNull final String firstKey)
    {
        return "==fk''" + firstKey;
    }

    /**
     * Builds a fk key using given object.
     * @param firstKey the first object key.
     * @param secondKey the second object key.
     * @return the map key.
     */
    @NotNull
    protected String buildFkKey(
        @NotNull final String firstKey, @NotNull final String secondKey)
    {
        return buildFkKey(firstKey) + ".,.," + secondKey;
    }

    /**
     * Retrieves the product name.
     * @param metaData the database metadata.
     * @return the product name.
     * @throws QueryJBuildException if the check fails.
     */
    @NotNull
    protected String retrieveProductName(@NotNull final DatabaseMetaData metaData)
        throws  QueryJBuildException
    {
        @NotNull String result = "";

        @Nullable QueryJBuildException t_ExceptionToThrow = null;

        try
        {
            result = metaData.getDatabaseProductName();
        }
        catch  (@NotNull final SQLException sqlException)
        {
            @Nullable final Log t_Log =
                UniqueLogFactory.getLog(
                    DatabaseMetaDataRetrievalHandler.class);

            if  (t_Log != null)
            {
                t_Log.error(
                    "Cannot retrieve database vendor's product name.",
                    sqlException);
            }

            t_ExceptionToThrow =
                new CannotRetrieveDatabaseInformationException(sqlException);
        }

        if (t_ExceptionToThrow != null)
        {
            throw t_ExceptionToThrow;
        }

        return result;
    }

    /**
     * Retrieves the product version.
     * @param metaData the database metadata.
     * @return the product version.
     */
    @NotNull
    protected String retrieveProductVersion(@NotNull final DatabaseMetaData metaData)
    {
        @NotNull String result = "";

        try
        {
            result = metaData.getDatabaseProductVersion();
        }
        catch  (@NotNull final SQLException sqlException)
        {
            @Nullable final Log t_Log =
                UniqueLogFactory.getLog(
                    DatabaseMetaDataRetrievalHandler.class);

            if  (t_Log != null)
            {
                t_Log.error(
                    "Cannot retrieve database vendor's product version.",
                    sqlException);
            }
        }

        return result;
    }

    /**
     * Retrieves the product's identifier quote string.
     * @param metaData the database metadata.
     * @return the quote string.
     * @throws QueryJBuildException if the check fails.
     */
    protected String retrieveProductQuote(@NotNull final DatabaseMetaData metaData)
        throws  QueryJBuildException
    {
        @NotNull String result = "";

        @Nullable QueryJBuildException t_ExceptionToThrow = null;

        try
        {
            result = metaData.getIdentifierQuoteString();
        }
        catch  (@NotNull final SQLException sqlException)
        {
            @Nullable final Log t_Log =
                UniqueLogFactory.getLog(
                    DatabaseMetaDataRetrievalHandler.class);

            if  (t_Log != null)
            {
                t_Log.error(
                    "Cannot retrieve database vendor's quote string.",
                    sqlException);
            }

            t_ExceptionToThrow =
                new CannotRetrieveDatabaseInformationException(sqlException);
        }

        if (t_ExceptionToThrow != null)
        {
            throw t_ExceptionToThrow;
        }

        return result;
    }

    /**
     * Retrieves the database major version.
     * @param metaData the database metadata.
     * @return the database major version.
     */
    protected int retrieveDatabaseMajorVersion(@NotNull final DatabaseMetaData metaData)
    {
        int result = -1;

        try
        {
            result = metaData.getDatabaseMajorVersion();
        }
        catch  (@NotNull final SQLException sqlException)
        {
            @Nullable final Log t_Log =
                UniqueLogFactory.getLog(
                    DatabaseMetaDataRetrievalHandler.class);

            if  (t_Log != null)
            {
                t_Log.debug(
                    "Cannot retrieve database vendor's major version.",
                    sqlException);
            }
        }

        return result;
    }

    /**
     * Retrieves the database minor version.
     * @param metaData the database metadata.
     * @return the database minor version.
     */
    protected int retrieveDatabaseMinorVersion(@NotNull final DatabaseMetaData metaData)
    {
        int result = -1;

        try
        {
            result = metaData.getDatabaseMinorVersion();
        }
        catch  (@NotNull final SQLException sqlException)
        {
            @Nullable final Log t_Log =
                UniqueLogFactory.getLog(
                    DatabaseMetaDataRetrievalHandler.class);

            if  (t_Log != null)
            {
                t_Log.debug(
                    "Cannot retrieve database vendor's minor version.",
                    sqlException);
            }
        }

        return result;
    }

    /**
     * Checks whether the database vendor matches this handler.
     * @param metaData the database metadata.
     * @param parameters the command parameters.
     * @return <code>true</code> in case it matches.
     * @throws QueryJBuildException if the check fails.
     */
    protected boolean checkVendor(
        @NotNull final DatabaseMetaData metaData, @NotNull final QueryJCommand parameters)
        throws  QueryJBuildException
    {
        final boolean result;

        @Nullable QueryJBuildException t_ExceptionToThrow = null;

        @Nullable String t_strProduct = retrieveDatabaseProductName(parameters);
        @Nullable String t_strVersion = retrieveDatabaseProductVersion(parameters);
        int t_iMajorVersion = retrieveDatabaseMajorVersion(parameters);
        int t_iMinorVersion = retrieveDatabaseMinorVersion(parameters);

        if (t_strProduct == null)
        {
            try
            {
                t_strProduct = retrieveProductName(metaData);
                storeDatabaseProductName(t_strProduct, parameters);
            }
            catch  (@NotNull final QueryJBuildException cannotRetrieveProductName)
            {
                t_ExceptionToThrow = cannotRetrieveProductName;
            }
        }

        if  (t_strVersion == null)
        {
            t_strVersion = retrieveProductVersion(metaData);
            storeDatabaseProductVersion(t_strVersion, parameters);
        }

        if (t_iMajorVersion < 0)
        {
            t_iMajorVersion = retrieveDatabaseMajorVersion(metaData);
            storeDatabaseMajorVersion(t_iMajorVersion, parameters);
        }

        if (t_iMinorVersion < 0)
        {
            t_iMinorVersion = retrieveDatabaseMinorVersion(metaData);
            storeDatabaseMinorVersion(t_iMinorVersion, parameters);
        }

        if (t_ExceptionToThrow == null)
        {
            result =
                checkVendor(
                    t_strProduct,
                    t_strVersion,
                    t_iMajorVersion,
                    t_iMinorVersion);
        }
        else
        {
            throw t_ExceptionToThrow;
        }

        return result;
    }

    /**
     * Checks whether the database vendor matches this handler.
     * @param productName the product name.
     * @param productVersion the product version.
     * @param majorVersion the major version number.
     * @param minorVersion the minor version number.
     * @return <code>true</code> in case it matches.
     */
    protected abstract boolean checkVendor(
        @NotNull final String productName,
        @NotNull final String productVersion,
        final int majorVersion,
        final int minorVersion);
}
