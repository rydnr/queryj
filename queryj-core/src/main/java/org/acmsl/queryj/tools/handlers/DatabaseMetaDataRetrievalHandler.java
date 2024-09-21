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
 * Filename: DatabaseMetaDataRetrievalHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Retrieves the database metadata instance.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJException;
import org.acmsl.queryj.api.exceptions.CannotRetrieveDatabaseMetadataException;
import org.acmsl.queryj.api.exceptions.CannotRetrieveDatabaseInformationException;
import org.acmsl.queryj.metadata.engines.Engine;
import org.acmsl.queryj.metadata.engines.UndefinedJdbcEngine;
import org.acmsl.queryj.metadata.engines.oracle.OracleEngine;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.metadata.MetadataExtractionLogger;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.engines.JdbcMetadataManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Table;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing NotNull annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand parameters)
        throws  QueryJBuildException
    {
        final boolean result;

        @NotNull final DatabaseMetaData t_Metadata = retrieveDatabaseMetaData(parameters);

        result = handle(parameters, retrieveAlreadyDoneFlag(parameters), t_Metadata);

        return result;
    }

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @param alreadyDone the flag indicating whether the metadata
     * extraction has already been done.
     * @param metaData the database metadata.
     * @return {@code true} if the chain should be stopped.
     * @throws QueryJBuildException if the process fails.
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
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return {@code true} if the chain should be stopped.
     * @throws QueryJBuildException if the process fails.
     */
    @SuppressWarnings("unchecked")
    protected boolean handle(
        @NotNull final QueryJCommand parameters, @Nullable final MetadataManager metadataManager)
        throws  QueryJBuildException
    {
        final boolean result = false;

        @NotNull final DatabaseMetaData t_Metadata = retrieveDatabaseMetaData(parameters);

        storeMetadata(t_Metadata, parameters);

        if  (metadataManager != null)
        {
            storeMetadataManager(metadataManager, parameters);

            /*
            @NotNull final Map<String, ?> t_mKeys = new HashMap<String, Object>();

            @Nullable final MetadataTypeManager t_MetadataTypeManager;

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

            @Nullable final List<Table<String, Attribute<String>, List<Attribute<String>>>> t_lTables =
                (List<Table<String, Attribute<String>, List<Attribute<String>>>>) t_mKeys.get(buildTableKey());

            if  (t_lTables != null)
            {
                for (@Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table : t_lTables)
                {
                    if (t_Table != null)
                    {
                        @NotNull final List<Attribute<String>> t_lFields =
                            (List<Attribute<String>>)
                                t_mKeys.get(
                                    buildTableFieldsKey(
                                        t_Table.getName()));

                        for (@Nullable final Attribute<String> t_Field : t_lFields)
                        {
                            if (t_Field != null)
                            {
                                @NotNull final List<AntFieldFkElement> t_lFieldFks =
                                    (List<AntFieldFkElement>)
                                        t_mKeys.get(
                                            buildFkKey(
                                                t_Table.getName(),
                                                t_Field.getName()));

                                final Iterator<AntFieldFkElement> t_itFieldFks = t_lFieldFks.iterator();

                                @NotNull final List<Attribute<String>> t_lFk = new ArrayList<>(1);

                                while (t_itFieldFks.hasNext())
                                {
                                    @Nullable final AntFieldFkElement t_FieldFk = t_itFieldFks.next();

                                    if (t_FieldFk != null)
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
            */
        }

        storeAlreadyDoneFlag(parameters);

        return result;
    }

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @param metaData the database metadata.
     * @return {@code true} if the chain should be stopped.
     * @throws QueryJBuildException if the process fails.
     */
    protected boolean handle(
        @NotNull final QueryJCommand parameters,
        @NotNull final DatabaseMetaData metaData)
      throws  QueryJBuildException
    {
        final boolean result = false;

        storeMetadata(metaData, parameters);

        /*
        @Nullable final AntTablesElement t_TablesElement;

        t_TablesElement = retrieveTablesElement(parameters);

        @NotNull final List<Table<String, Attribute<String>, List<Attribute<String>>>> t_lTables =
            extractTables(parameters, t_TablesElement);

        storeTables(t_lTables, parameters);
        */
        @Nullable final MetadataManager t_MetadataManager =
            buildMetadataManager(parameters, metaData);

        if (t_MetadataManager != null)
        {
            storeMetadataManager(t_MetadataManager, parameters);
        }

        return result;
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
     * @param parameters the parameters.
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
     * @param parameters the parameters.
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
     * @param parameters the parameters.
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
     * @param parameters the parameters.
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
     * @throws QueryJBuildException if the metadata manager cannot be built.
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

        @Nullable List<Table<String, Attribute<String>, List<Attribute<String>>>> t_lTables =
            retrieveTables(parameters);

        if (t_lTables == null)
        {
            t_lTables = new ArrayList<>(0);
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
                    buildEngine(
                        retrieveProductName(metaData),
                        retrieveProductVersion(metaData)));


        }

        try
        {
            result.eagerlyFetchMetadata();

            if (t_lTables.size() == 0)
            {
                storeTables(result.getTableDAO().findAllTables(), parameters);
            }
        }
        catch  (@NotNull final SQLException | QueryJException sqlException)
        {
            throw
                new CannotRetrieveDatabaseMetadataException(sqlException);
        }

        return result;
    }

    /**
     * Creates the {@link Engine} with given information.
     * @param name the name.
     * @param version the version.
     * @return the engine.
     */
    @NotNull
    protected Engine<String> buildEngine(final String name, final String version)
    {
        @NotNull final Engine<String> result;

        if (name.toLowerCase(Locale.US).equalsIgnoreCase(Literals.ORACLE.toLowerCase(Locale.US)))
        {
            result = new OracleEngine(version);
        }
        else
        {
            result = new UndefinedJdbcEngine(name, version);
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
     * @param caseSensitive the case sensitiveness.
     * @param parameters the parameters.
     */
    @SuppressWarnings("unchecked")
    public void storeCaseSensitive(final boolean caseSensitive, @NotNull final QueryJCommand parameters)
    {
        new QueryJCommandWrapper<Boolean>(parameters).setSetting(CASE_SENSITIVE, caseSensitive);
    }

    /**
     * Builds a database metadata manager.
     * @param parameters the parameters.
     * @param tables the tables.
     * @param metaData the database metadata.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param caseSensitive whether the engine is case sensitive or not.
     * @param engine the engine.
     * @return the metadata manager instance.
     * @throws QueryJBuildException if the metadata manager cannot be built.
     */
    @NotNull
    protected MetadataManager buildMetadataManager(
        @SuppressWarnings("unused") @NotNull final QueryJCommand parameters,
        @NotNull final List<Table<String, Attribute<String>, List<Attribute<String>>>> tables,
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema,
        final boolean caseSensitive,
        @NotNull final Engine<String> engine)
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
                    engine);
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
     * @return such condition.
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
        @NotNull final List<Table<String, Attribute<String>, List<Attribute<String>>>> tables,
        @NotNull final QueryJCommand parameters)
    {
        new QueryJCommandWrapper<List<Table<String, Attribute<String>, List<Attribute<String>>>>>(parameters)
            .setSetting(TABLES, tables);
    }

    /**
     * Retrieves the table names from the attribute map.
     * @param parameters the parameter map.
     * @return the table names.
     */
    @Nullable
    protected List<Table<String, Attribute<String>, List<Attribute<String>>>> retrieveTables(
        @NotNull final QueryJCommand parameters)
    {
        return new QueryJCommandWrapper<List<Table<String, Attribute<String>, List<Attribute<String>>>>>(parameters)
            .getSetting(TABLES);
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
     * Retrieves the product name.
     * @param metaData the database metadata.
     * @return the product name.
     * @throws QueryJBuildException if the product name information is not available.
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
     * @throws QueryJBuildException if the product quote information is not available.
     */
    @SuppressWarnings("unused")
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
     * @throws QueryJBuildException if the vendor information is not available.
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
