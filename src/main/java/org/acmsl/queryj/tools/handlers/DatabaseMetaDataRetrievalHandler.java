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
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.ant.AntFieldElement;
import org.acmsl.queryj.tools.ant.AntFieldFkElement;
import org.acmsl.queryj.tools.ant.AntTableElement;
import org.acmsl.queryj.tools.ant.AntTablesElement;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.metadata.engines.JdbcMetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;

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
    public static final String TABLE_NAMES = "table.names";

    /**
     * The procedure names attribute name.
     */
    public static final String PROCEDURE_NAMES = "procedure.names";

    /**
     * The table fields attribute name.
     */
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
     * @precondition parameters != null
     */
    protected boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        return
            handle(
                parameters,
                retrieveAlreadyDoneFlag(parameters),
                retrieveDatabaseMetaData(parameters));
    }

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @param alreadyDone the flag indicating whether the metadata
     * extraction has already been done.
     * @param metaData the database metadata.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     */
    protected boolean handle(
        @NotNull final Map parameters,
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
                handle(parameters, buildMetadataManager(parameters));
        }

        return result;
    }

    protected void analyzeMetaData(final DatabaseMetaData metaData, final Map parameters)
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
            Log t_Log =
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
     * Builds the metadata manager.
     * @param parameters the command parameters.
     * @return the {@link MetadataManager} instance.
     * @throws QueryJBuildException if the manager instance cannot be created.
     */
    @Nullable
    protected MetadataManager buildMetadataManager(@NotNull final Map parameters)
      throws QueryJBuildException
    {
        boolean t_bDisableTableExtraction =
            !retrieveExtractTables(parameters);

        boolean t_bDisableProcedureExtraction =
            !retrieveExtractProcedures(parameters);

        return
            buildMetadataManager(
                parameters, t_bDisableTableExtraction, t_bDisableProcedureExtraction);
    }

    /**
     * Retrieves the explicitly-defined table names.
     * @param parameters the parameters.
     * @return the array of table names.
     */
    @NotNull
    protected String[] retrieveExplicitTableNames(@NotNull final Map parameters)
    {
        String[] result = null;

        @NotNull AntTablesElement t_TablesElement =
            retrieveTablesElement(parameters);

        @Nullable Iterator t_itTableElements = null;

        @Nullable Collection t_cTableElements = null;

        int t_iTableIndex = 0;

        @Nullable Collection t_cFieldElements = null;

        if  (t_TablesElement != null)
        {
            t_cTableElements = t_TablesElement.getTables();

            if  (   (t_cTableElements != null)
                 && (t_cTableElements.size() > 0))
            {
                result = new String[t_cTableElements.size()];

                t_itTableElements = t_cTableElements.iterator();

                while  (   (t_itTableElements != null)
                        && (t_itTableElements.hasNext()))
                {
                    @NotNull AntTableElement t_Table =
                        (AntTableElement) t_itTableElements.next();

                    if  (t_Table != null)
                    {
                        result[t_iTableIndex] = t_Table.getName();
                    }

                    t_iTableIndex++;
                }

                storeTableNames(result, parameters);
            }
        }

        if (result == null)
        {
            result = new String[0];
        }

        return result;
    }

    /**
     * Checks whether there are at least one field in any explicitly-defined table.
     * @param explicitTables the explicitly-defined table information.
     * @return <code>true</code> in such case.
     */
    protected boolean areExplicitTableFieldsEmpty(@Nullable final AntTablesElement explicitTables)
    {
        boolean result = true;

        if (explicitTables != null)
        {
            @Nullable Iterator t_itTableElements = null;

            @Nullable Collection t_cTableElements = explicitTables.getTables();

            @Nullable Collection t_cFieldElements = null;

            t_itTableElements = t_cTableElements.iterator();

            while  (   (t_itTableElements != null)
                    && (t_itTableElements.hasNext()))
            {
                @NotNull AntTableElement t_Table =
                    (AntTableElement) t_itTableElements.next();

                if  (t_Table != null)
                {
                    t_cFieldElements = t_Table.getFields();

                    if  (   (t_cFieldElements != null)
                        && (t_cFieldElements.size() > 0))
                    {
                        result = false;
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Builds the metadata manager.
     * @param parameters the command parameters.
     * @param disableTableExtraction whether to extract tables or not.
     * @param disableProcedureExtraction whether to extract procedures or not.
     * @return the {@link MetadataManager} instance.
     * @throws QueryJBuildException if the manager instance cannot be created.
     */
    protected MetadataManager buildMetadataManager(
        @NotNull final Map parameters,
        final boolean disableTableExtraction,
        final boolean disableProcedureExtraction)
      throws QueryJBuildException
    {
        @Nullable MetadataManager result = null;

        if  (!disableTableExtraction)
        {
            result =
                buildMetadataManager(
                    disableTableExtraction,
                    lazyTableExtraction(retrieveTablesElement(parameters)),
                    disableProcedureExtraction,
                    false,
                    parameters);
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
        @NotNull final Map parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @Nullable final AntTablesElement explicitTables,
        @NotNull final Map fieldMap)
    {
        @Nullable Collection t_cTableElements = null;

        if (explicitTables != null)
        {
            t_cTableElements = explicitTables.getTables();
        }

        @Nullable Iterator t_itTableElements = null;

        @Nullable Collection t_cFieldElements = null;

        int t_iTableIndex = 0;

        Collection t_cTables;

        if  (t_cTableElements != null)
        {
            t_itTableElements = t_cTableElements.iterator();
        }

        if  (   (t_itTableElements != null)
             && (metadataTypeManager != null))
        {
            while  (t_itTableElements.hasNext())
            {
                @NotNull AntTableElement t_Table =
                    (AntTableElement) t_itTableElements.next();

                if  (t_Table != null)
                {
                    t_cTables = (Collection) fieldMap.get(buildTableKey());

                    if  (t_cTables == null)
                    {
                        t_cTables = new ArrayList();
                        fieldMap.put(buildTableKey(), t_cTables);
                    }

                    t_cTables.add(t_Table.getName());

                    t_cFieldElements = t_Table.getFields();

                    if  (   (t_cFieldElements  != null)
                        && (t_cFieldElements.size() > 0))
                    {
                        @NotNull String[] t_astrTableFieldNames =
                            new String[t_cFieldElements.size()];

                        Iterator t_itFieldElements = t_cFieldElements.iterator();

                        int t_iFieldIndex = 0;

                        while  (   (t_itFieldElements != null)
                            && (t_itFieldElements.hasNext()))
                        {
                            @NotNull AntFieldElement t_Field =
                                (AntFieldElement) t_itFieldElements.next();

                            if  (t_Field != null)
                            {
                                t_astrTableFieldNames[t_iFieldIndex] =
                                    t_Field.getName();

                                metadataManager.addColumnType(
                                    t_Table.getName(),
                                    t_Field.getName(),
                                    metadataTypeManager.getJavaType(
                                        t_Field.getType()));

                                @NotNull Collection t_cFields =
                                    (Collection)
                                        fieldMap.get(
                                            buildTableFieldsKey(
                                                t_Table.getName()));

                                if  (t_cFields == null)
                                {
                                    t_cFields = new ArrayList();
                                    fieldMap.put(
                                        buildTableFieldsKey(
                                            t_Table.getName()), t_cFields);
                                }

                                t_cFields.add(t_Field.getName());

                                if  (t_Field.isPk())
                                {
                                    metadataManager.addPrimaryKey(
                                        t_Table.getName(),
                                        t_Field.getName());

                                    @NotNull Collection t_cPks =
                                        (Collection)
                                            fieldMap.get(
                                                buildPkKey(t_Table.getName()));

                                    if  (t_cPks == null)
                                    {
                                        t_cPks = new ArrayList();
                                        fieldMap.put(
                                            buildPkKey(
                                                t_Table.getName()), t_cPks);
                                    }

                                    t_cPks.add(t_Field.getName());

                                    fieldMap.put(
                                        buildPkKey(
                                            t_Table.getName(),
                                            t_Field.getName()),
                                        t_Field.getName());
                                }

                                Collection t_cFieldFks =
                                    t_Field.getFieldFks();

                                if  (t_cFieldFks != null)
                                {
                                    fieldMap.put(
                                        buildFkKey(
                                            t_Table.getName(),
                                            t_Field.getName()),
                                        t_cFieldFks);
                                }
                            }

                            t_iFieldIndex++;
                        }

                        metadataManager.addColumnNames(
                            t_Table.getName(),
                            t_astrTableFieldNames);
                    }
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
    protected boolean handle(
        @NotNull final Map parameters, @Nullable final MetadataManager metadataManager)
        throws  QueryJBuildException
    {
        boolean result = false;

        storeMetadata(retrieveDatabaseMetaData(parameters), parameters);

        @Nullable MetadataManager t_MetadataManager;

        @Nullable MetadataTypeManager t_MetadataTypeManager = null;

        if  (metadataManager != null)
        {
            @NotNull Map t_mKeys = new HashMap();

            storeMetadataManager(metadataManager, parameters);

            t_MetadataTypeManager =
                metadataManager.getMetadataTypeManager();

            processExplicitSchema(
                parameters,
                metadataManager,
                t_MetadataTypeManager,
                retrieveTablesElement(parameters),
                t_mKeys);

            @Nullable Collection<String> t_cTables = (Collection<String>) t_mKeys.get(buildTableKey());

            if  (t_cTables != null)
            {
                Iterator t_itTables = t_cTables.iterator();

                while  (   (t_itTables != null)
                         && (t_itTables.hasNext()))
                {
                    @NotNull String t_strTableName = (String) t_itTables.next();

                    if  (t_strTableName != null)
                    {
                        @NotNull Collection t_cFields =
                            (Collection)
                            t_mKeys.get(
                                buildTableFieldsKey(
                                    t_strTableName));

                        if  (t_cFields != null)
                        {
                            Iterator t_itFields = t_cFields.iterator();

                            while  (   (t_itFields != null)
                                       && (t_itFields.hasNext()))
                            {
                                @NotNull String t_strFieldName =
                                    (String) t_itFields.next();

                                @NotNull Collection t_cFieldFks =
                                    (Collection)
                                    t_mKeys.get(
                                        buildFkKey(
                                            t_strTableName,
                                            t_strFieldName));

                                if  (t_cFieldFks != null)
                                {
                                    Iterator t_itFieldFks = t_cFieldFks.iterator();

                                    while  (   (t_itFieldFks != null)
                                               && (t_itFieldFks.hasNext()))
                                    {
                                        @NotNull AntFieldFkElement t_FieldFk =
                                            (AntFieldFkElement)
                                            t_itFieldFks.next();

                                        if  (t_FieldFk != null)
                                        {
                                            metadataManager.addForeignKey(
                                                t_strTableName,
                                                new String[] {t_strFieldName},
                                                t_FieldFk.getTable(),
                                                new String[] {t_FieldFk.getField()});
                                        }
                                    }
                                }
                            }
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
     * @param enableTableExtraction whether to extract tables.
     * @param enableProcedureExtraction whether to extract procedures.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     */
    protected boolean handle(
        @NotNull final Map parameters,
        final DatabaseMetaData metaData,
        final boolean enableTableExtraction,
        final boolean enableProcedureExtraction)
      throws  QueryJBuildException
    {
        boolean result = false;

        storeMetadata(metaData, parameters);

        @Nullable AntTablesElement t_TablesElement = null;

        if  (enableTableExtraction)
        {
            t_TablesElement = retrieveTablesElement(parameters);

            String[] t_astrTableNames =
                extractTableNames(parameters, t_TablesElement);

            storeTableNames(t_astrTableNames, parameters);
        }

        @Nullable MetadataManager t_MetadataManager =
            buildMetadataManager(
                !enableTableExtraction,
                lazyTableExtraction(t_TablesElement),
                !enableProcedureExtraction,
                false,
                parameters);

        storeMetadataManager(t_MetadataManager, parameters);

        if  (enableProcedureExtraction)
        {
            if  (t_TablesElement == null)
            {
                t_TablesElement = retrieveTablesElement(parameters);
            }

            extractProcedures(
                parameters,
                metaData,
                t_MetadataManager,
                t_TablesElement,
                t_MetadataManager.getMetadataTypeManager());
        }

        return result;
    }


    /**
     * Retrieves the names of the user-defined tables.
     * @param parameters the command parameters.
     * @param tablesElement the &lt;tables&gt; element.
     * @return such table names.
     * @precondition parameters != null
     */
    protected String[] extractTableNames(
        final Map parameters, @Nullable final AntTablesElement tablesElement)
    {
        String[] result = new String[0];

        if  (tablesElement != null)
        {
            result = extractTableNames(tablesElement.getTables());
        }

        return result;
    }

    /**
     * Retrieves the fields of the user-defined tables.
     * @param tables the table definitions.
     * @return such fields.
     * @precondition tables != null
     */
    protected String[] extractTableNames(@Nullable final Collection tables)
    {
        String[] result;

        int t_iLength = (tables != null) ? tables.size() : 0;

        result = new String[t_iLength];

        int t_iTableIndex = 0;

        if  (t_iLength > 0)
        {
            Iterator t_itTableElements = tables.iterator();

            if  (t_itTableElements != null)
            {
                while  (t_itTableElements.hasNext())
                {
                    @NotNull AntTableElement t_Table =
                        (AntTableElement) t_itTableElements.next();

                    if  (t_Table != null)
                    {
                        result[t_iTableIndex] = t_Table.getName();
                    }

                    t_iTableIndex++;
                }
            }
        }

        return result;
    }

    /**
     * Retrieves whether the tables should be extracted on demand.
     * @param tablesElement the tables element.
     * @return the result of such analysis..
     * @precondition tablesElement != null
     */
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
     * @precondition tables != null
     */
    protected boolean lazyTableExtraction(@Nullable final Collection tables)
    {
        boolean result = false;

        int t_iLength = (tables != null) ? tables.size() : 0;

        if  (t_iLength > 0)
        {
            Iterator t_itTableElements = tables.iterator();

            if  (t_itTableElements != null)
            {
                while  (t_itTableElements.hasNext())
                {
                    @NotNull AntTableElement t_Table =
                        (AntTableElement) t_itTableElements.next();

                    if  (t_Table != null)
                    {
                        Collection t_cFields = t_Table.getFields();

                        if  (   (t_cFields != null)
                             && (t_cFields.size()> 0))
                        {
                            result = true;

                            break;
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Extracts the procedures.
     * @param parameters the parameters to handle.
     * @param metaData the database metadata.
     * @param metadataManager the metadata manager.
     * @param tablesElement the tables element.
     * @param metadataTypeManager the metadata type manager.
     * @return <code>true</code> if the chain should be stopped.
     * @precondition parameters != null
     * @precondition metaData != null
     * @precondition metadataManager != null
     * @precondition tableElements != null
     * @precondition metadataTypeManager != null
     */
    protected boolean extractProcedures(
        final Map parameters,
        final DatabaseMetaData metaData,
        @NotNull final MetadataManager metadataManager,
        @NotNull final AntTablesElement tablesElement,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            extractProcedures(
                parameters,
                metaData,
                metadataManager,
                tablesElement.getTables(),
                buildTableKey(),
                metadataTypeManager);
    }

    /**
     * Extracts the procedures..
     * @param parameters the parameters to handle.
     * @param metaData the database metadata.
     * @param metadataManager the metadata manager.
     * @param tableElements the table elements.
     * @param tableKey the key to store the tables.
     * @param metadataTypeManager the metadata type manager.
     * @return <code>true</code> if the chain should be stopped.
     * @precondition parameters != null
     * @precondition metaData != null
     * @precondition metadataManager != null
     * @precondition tableElements != null
     * @precondition tableKey != null
     * @precondition metadataTypeManager != null
     */
    protected boolean extractProcedures(
        final Map parameters,
        final DatabaseMetaData metaData,
        @NotNull final MetadataManager metadataManager,
        @Nullable final Collection tableElements,
        final Object tableKey,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        boolean result = false;

        @Nullable Iterator t_itTableElements = null;

        @Nullable String[] t_astrTableNames = null;

        @NotNull Map t_mKeys = new HashMap();

        @Nullable Collection t_cTables = null;

        Collection t_cFields;

        Object t_TableFieldsKey;

        Object t_TablePkKey;

        Collection t_cPks;

        int t_iLength = 0;

        AntTableElement t_Table;

        Collection t_cFieldElements;

        AntFieldElement t_Field;

        String[] t_astrTableFieldNames;

        Collection t_cFieldFks;

        Iterator t_itFieldElements;

        int t_iFieldIndex = 0;

        if  (tableElements != null)
        {
            t_itTableElements = tableElements.iterator();
        }

        if  (t_itTableElements != null)
        {
            while  (t_itTableElements.hasNext())
            {
                t_Table = (AntTableElement) t_itTableElements.next();

                if  (t_Table != null)
                {
                    t_cTables = (Collection) t_mKeys.get(tableKey);

                    if  (t_cTables == null)
                    {
                        t_cTables = new ArrayList();
                        t_mKeys.put(tableKey, t_cTables);
                    }

                    t_cTables.add(t_Table.getName());

                    t_cFieldElements = t_Table.getFields();

                    t_iLength =
                        (t_cFieldElements != null)
                        ?  t_cFieldElements.size()
                        :  0;

                    if  (t_iLength > 0)
                    {
                        t_astrTableFieldNames = new String[t_iLength];

                        t_itFieldElements = t_cFieldElements.iterator();

                        t_iFieldIndex = 0;

                        if  (t_itFieldElements != null)
                        {
                            while  (t_itFieldElements.hasNext())
                            {
                                t_Field =
                                    (AntFieldElement) t_itFieldElements.next();

                                if  (t_Field != null)
                                {
                                    t_astrTableFieldNames[t_iFieldIndex] =
                                        t_Field.getName();

                                    metadataManager.addColumnType(
                                        t_Table.getName(),
                                        t_Field.getName(),
                                        metadataTypeManager.getJavaType(
                                            t_Field.getType()));

                                    t_TableFieldsKey =
                                        buildTableFieldsKey(t_Table.getName());

                                    t_cFields =
                                        (Collection) t_mKeys.get(t_TableFieldsKey);

                                    if  (t_cFields == null)
                                    {
                                        t_cFields = new ArrayList();
                                        t_mKeys.put(t_TableFieldsKey, t_cFields);
                                    }

                                    t_cFields.add(t_Field.getName());

                                    if  (t_Field.isPk())
                                    {
                                        metadataManager.addPrimaryKey(
                                            t_Table.getName(),
                                            t_Field.getName());

                                        t_TablePkKey =
                                            buildPkKey(t_Table.getName());

                                        t_cPks =
                                            (Collection) t_mKeys.get(t_TablePkKey);

                                        if  (t_cPks == null)
                                        {
                                            t_cPks = new ArrayList();
                                            t_mKeys.put(t_TablePkKey, t_cPks);
                                        }

                                        t_cPks.add(t_Field.getName());

                                        t_mKeys.put(
                                            buildPkKey(
                                                t_Table.getName(),
                                                t_Field.getName()),
                                            t_Field.getName());
                                    }

                                    t_cFieldFks = t_Field.getFieldFks();

                                    if  (t_cFieldFks != null)
                                    {
                                        t_mKeys.put(
                                            buildFkKey(
                                                t_Table.getName(),
                                                t_Field.getName()),
                                            t_cFieldFks);
                                    }
                                }

                                t_iFieldIndex++;
                            }

                            metadataManager.addColumnNames(
                                t_Table.getName(),
                                t_astrTableFieldNames);
                        }
                    }
                }
            }
        }

        if  (t_cTables != null)
        {
            extractForeignKeys(
                t_cTables, t_mKeys, metadataManager, tableKey);
        }

        return result;
    }

    /**
     * Extracts the procedures..
     * @param extractedMap the already-extracted information.
     * @param metadataManager the database metadata manager.
     * @param tableKey the key to store the tables.
     * @precondition tables != null
     * @precondition extractedMap != null
     * @precondition metadataManager != null
     * @precondition tableKey != null
     */
    protected void extractForeignKeys(
        @NotNull final Collection tables,
        @NotNull final Map extractedMap,
        @NotNull final MetadataManager metadataManager,
        final Object tableKey)
    {
        Iterator t_itTables = tables.iterator();

        if  (t_itTables != null)
        {
            while  (t_itTables.hasNext())
            {
                @NotNull String t_strTableName = (String) t_itTables.next();

                if  (t_strTableName != null)
                {
                    @NotNull Collection t_cFields =
                        (Collection)
                            extractedMap.get(
                                buildTableFieldsKey(t_strTableName));

                    if  (t_cFields != null)
                    {
                        Iterator t_itFields = t_cFields.iterator();

                        if  (t_itFields != null)
                        {
                            while  (t_itFields.hasNext())
                            {
                                @NotNull String t_strFieldName =
                                    (String) t_itFields.next();

                                @NotNull Collection t_cFieldFks =
                                    (Collection)
                                        extractedMap.get(
                                            buildFkKey(
                                                t_strTableName,
                                                t_strFieldName));

                                @Nullable Iterator t_itFieldFks = null;

                                if  (t_cFieldFks != null)
                                {
                                    t_itFieldFks =
                                        t_cFieldFks.iterator();
                                }

                                if  (t_itFieldFks != null)
                                {
                                    while  (t_itFieldFks.hasNext())
                                    {
                                        @NotNull AntFieldFkElement t_FieldFk =
                                            (AntFieldFkElement)
                                                t_itFieldFks.next();

                                        if  (t_FieldFk != null)
                                        {
                                            metadataManager.addForeignKey(
                                                t_strTableName,
                                                new String[] {t_strFieldName},
                                                t_FieldFk.getTable(),
                                                new String[]
                                                {
                                                    t_FieldFk.getField()
                                                });
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Retrieves the tables XML element stored in the
     * attribute map.
     * @param parameters the parameter map.
     * @return the table information.
     * @precondition parameters != null
     */
    @Nullable
    protected AntTablesElement retrieveTablesElement(@NotNull final Map parameters)
    {
        return
            (AntTablesElement)
                parameters.get(ParameterValidationHandler.TABLES);
    }

    /**
     * Retrieves whether the table extraction is disabled or not.
     * @param parameters the parameter map.
     * @return such setting.
     */
    protected boolean retrieveExtractTables(@NotNull final Map parameters)
    {
        return
            retrieveBoolean(
                ParameterValidationHandler.EXTRACT_TABLES, parameters);
    }

    /**
     * Retrieves whether the procedure extraction is disabled or not.
     * @param parameters the parameter map.
     * @return such setting.
     */
    protected boolean retrieveExtractProcedures(@NotNull final Map parameters)
    {
        return
            retrieveBoolean(
                ParameterValidationHandler.EXTRACT_PROCEDURES, parameters);
    }

    /**
     * Annotates the database product name.
     * @param productName the product name.
     * @param parameters the parameters.
     * @return such name.
     * @precondition parameters != null
     */
    public void storeDatabaseProductName(@NotNull String productName, @NotNull final Map parameters)
    {
        parameters.put(DATABASE_PRODUCT_NAME, productName);
    }

    /**
     * Retrieves the database product name.
     * @return such name.
     */
    @Nullable
    public String retrieveDatabaseProductName(@NotNull final Map parameters)
    {
        return (String) parameters.get(DATABASE_PRODUCT_NAME);
    }

    /**
     * Annotates the database product version.
     * @param productVersion the product version.
     * @param parameters the parameters.
     * @return such name.
     * @precondition parameters != null
     */
    public void storeDatabaseProductVersion(@NotNull String productVersion, @NotNull final Map parameters)
    {
        parameters.put(DATABASE_PRODUCT_VERSION, productVersion);
    }

    /**
     * Retrieves the database product version.
     * @return such version.
     */
    @Nullable
    public String retrieveDatabaseProductVersion(@NotNull final Map parameters)
    {
        return (String) parameters.get(DATABASE_PRODUCT_VERSION);
    }

    /**
     * Annotates the database major version.
     * @param majorVersion the major version.
     * @param parameters the parameters.
     * @return such name.
     * @precondition parameters != null
     */
    public void storeDatabaseMajorVersion(final int majorVersion, @NotNull final Map parameters)
    {
        parameters.put(DATABASE_MAJOR_VERSION, majorVersion);
    }

    /**
     * Retrieves the database major version.
     * @return such version.
     */
    public int retrieveDatabaseMajorVersion(@NotNull final Map parameters)
    {
        int result = -1;

        Integer t_iMajorVersion = (Integer) parameters.get(DATABASE_MAJOR_VERSION);

        if (t_iMajorVersion != null)
        {
            result = t_iMajorVersion;
        }

        return result;
    }

    /**
     * Annotates the database minor version.
     * @param minorVersion the minor version.
     * @param parameters the parameters.
     * @return such name.
     * @precondition parameters != null
     */
    public void storeDatabaseMinorVersion(final int minorVersion, @NotNull final Map parameters)
    {
        parameters.put(DATABASE_MINOR_VERSION, minorVersion);
    }

    /**
     * Retrieves the database minor version.
     * @return such version.
     */
    public int retrieveDatabaseMinorVersion(@NotNull final Map parameters)
    {
        int result = -1;

        Integer t_iMinorVersion = (Integer) parameters.get(DATABASE_MINOR_VERSION);

        if (t_iMinorVersion != null)
        {
            result = t_iMinorVersion;
        }

        return result;
    }

    /**
     * Retrieves a boolean value stored in the attribute map.
     * @param name the key name.
     * @param parameters the parameter map.
     * @return the boolean value.
     * @precondition parameters != null
     */
    protected boolean retrieveBoolean(final String name, @NotNull final Map parameters)
    {
        boolean result = false;

        Object t_Flag = parameters.get(name);

        if  (   (t_Flag != null)
             && (t_Flag instanceof Boolean))
        {
            result = (Boolean) t_Flag;
        }

        return result;
    }

    /**
     * Retrieves the database metadata manager using the database metadata and
     * parameters stored in the attribute map.
     * @param disableTableExtraction if the table metadata should not be
     * extracted.
     * @param lazyTableExtraction if the table metadata should not be
     * extracted immediately.
     * @param disableProcedureExtraction if the procedure metadata should not be
     * extracted.
     * @param lazyProcedureExtraction if the procedure metadata should not be
     * extracted immediately.
     * @param parameters the parameter map.
     * @return the metadata manager instance.
     * @throws QueryJBuildException if the retrieval process cannot be
     * performed.
     * @precondition parameters != null
     */
    @Nullable
    protected MetadataManager buildMetadataManager(
        final boolean disableTableExtraction,
        final boolean lazyTableExtraction,
        final boolean disableProcedureExtraction,
        final boolean lazyProcedureExtraction,
        @NotNull final Map parameters)
      throws  QueryJBuildException
    {
        @Nullable MetadataManager result =
            retrieveMetadataManager(parameters);

        String[] t_astrTableNames = retrieveTableNames(parameters);

        if  (result == null)
        {
            result =
                buildMetadataManager(
                    parameters,
                    t_astrTableNames,
                    retrieveProcedureNames(parameters),
                    disableTableExtraction,
                    lazyTableExtraction,
                    disableProcedureExtraction,
                    lazyProcedureExtraction,
                    retrieveDatabaseMetaData(parameters),
                    retrieveCatalog(parameters),
                    retrieveSchema(parameters),
                    retrieveCaseSensitive(parameters));


        }

        if  (result != null)
        {
            try
            {
                result.retrieveMetadata();

                if (   (t_astrTableNames == null)
                    || (t_astrTableNames.length == 0))
                {
                    storeTableNames(result.getTableNames(), parameters);
                }
            }
            catch  (@NotNull final SQLException sqlException)
            {
                throw
                    new QueryJBuildException(
                        "Cannot retrieve metadata.", sqlException);
            }
            catch  (@NotNull final QueryJException queryjException)
            {
                throw
                    new QueryJBuildException(
                        "Cannot retrieve metadata.", queryjException);
            }
        }

        return result;
    }

    /**
     * Retrieves whether the database engine is case sensitive or not.
     * @param parameters the parameters.
     * @return <code>true</code> in such case.
     */
    public boolean retrieveCaseSensitive(@NotNull final Map parameters)
    {
        boolean result = false;

        Boolean t_bCaseSensitive = (Boolean) parameters.get(CASE_SENSITIVE);

        if (t_bCaseSensitive != null)
        {
            result = t_bCaseSensitive;
        }

        return result;
    }

    /**
     * Retrieves whether the database engine is case sensitive or not.
     * @param parameters the parameters.
     * @return <code>true</code> in such case.
     */
    public void storeCaseSensitive(final boolean caseSensitive, @NotNull final Map parameters)
    {
        parameters.put(CASE_SENSITIVE, caseSensitive);
    }

    /**
     * Builds a database metadata manager.
     * @param tableNames the table names.
     * @param procedureNames the procedure names.
     * @param disableTableExtraction if the table metadata should not
     * be extracted.
     * @param lazyTableExtraction if the table metadata should not
     * be extracted immediately.
     * @param disableProcedureExtraction if the procedure metadata should not
     * be extracted.
     * @param lazyProcedureExtraction if the procedure metadata should not
     * be extracted immediately.
     * @param metaData the database metadata.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param caseSensitive whether the engine is case sensitive or not.
     * @return the metadata manager instance.
     * @throws QueryJBuildException whenever the required
     * parameters are not present or valid.
     * @precondition metaData != null
     */
    @Nullable
    protected MetadataManager buildMetadataManager(
        @NotNull final Map parameters,
        @NotNull final String[] tableNames,
        @NotNull final String[] procedureNames,
        final boolean disableTableExtraction,
        final boolean lazyTableExtraction,
        final boolean disableProcedureExtraction,
        final boolean lazyProcedureExtraction,
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @NotNull final String schema,
        final boolean caseSensitive)
      throws  QueryJBuildException
    {
        MetadataManager result;

        try 
        {
            result =
                new JdbcMetadataManager(
                    tableNames,
                    procedureNames,
                    disableTableExtraction,
                    lazyTableExtraction,
                    disableProcedureExtraction,
                    lazyProcedureExtraction,
                    metaData,
                    catalog,
                    schema,
                    caseSensitive);
        }
        catch  (@NotNull final RuntimeException exception)
        {
            throw exception;
        }
        catch  (@NotNull final Exception exception)
        {
            Log t_Log =
                UniqueLogFactory.getLog(
                    DatabaseMetaDataRetrievalHandler.class);

            if  (t_Log != null)
            {
                t_Log.error(
                    "Cannot build a database metadata manager.",
                    exception);
            }

            throw
                new QueryJBuildException(
                    "Cannot retrieve database metadata", exception);
        }

        return result;
    }

    /**
     * Stores the database metadata in the attribute map.
     * @param metaData the database metadata.
     * @param parameters the parameter map.
     * @precondition metaData != null
     * @precondition parameters != null
     */
    protected void storeMetadata(
        final DatabaseMetaData metaData, @NotNull final Map parameters)
    {
        parameters.put(DATABASE_METADATA, metaData);
    }

    /**
     * Stores a flag indicating the metadata extraction has already been done.
     * @param parameters the parameter map.
     * @precondition parameters != null
     */
    protected void storeAlreadyDoneFlag(@NotNull final Map parameters)
    {
        parameters.put(METADATA_EXTRACTION_ALREADY_DONE, Boolean.TRUE);
    }

    /**
     * Retrieves the flag which indicates whether the metadata extraction
     * has been done already.
     * @param parameters the parameter map.
     * @precondition metaData != null
     */
    protected boolean retrieveAlreadyDoneFlag(@NotNull final Map parameters)
    {
        boolean result = false;

        Object t_Flag = parameters.get(METADATA_EXTRACTION_ALREADY_DONE);

        if  (   (t_Flag  != null)
             && (t_Flag instanceof Boolean))
        {
            result = (Boolean) t_Flag;
        }

        return result;
    }

    /**
     * Stores the table names in the attribute map.
     * @param tableNames the table names.
     * @param parameters the parameter map.
     * @precondition tableNames != null
     * @precondition parameters != null
     */
    protected void storeTableNames(
        final String[] tableNames, @NotNull final Map parameters)
    {
        parameters.put(TABLE_NAMES, tableNames);
    }

    /**
     * Retrieves the table names from the attribute map.
     * @param parameters the parameter map.
     * @return the table names.
     * @precondition parameters != null
     */
    @Nullable
    protected String[] retrieveTableNames(@NotNull final Map parameters)
    {
        return (String[]) parameters.get(TABLE_NAMES);
    }

    /**
     * Retrieves the procedure names from the attribute map.
     * @param parameters the parameter map.
     * @return the procedure names.
     * @precondition parameters != null
     */
    @Nullable
    protected String[] retrieveProcedureNames(@NotNull final Map parameters)
    {
        return (String[]) parameters.get(PROCEDURE_NAMES);
    }

    /**
     * Stores the database metadata manager in the attribute map.
     * @param metadataManager the metadata manager.
     * @param parameters the parameter map.
     * @precondition metadataManager != null
     * @precondition parameters != null
     */
    protected void storeMetadataManager(
        final MetadataManager metadataManager, @NotNull final Map parameters)
    {
        parameters.put(METADATA_MANAGER, metadataManager);
    }

    /**
     * Retrieves the JDBC catalog from the attribute map.
     * @param parameters the parameter map.
     * @return the catalog.
     * @precondition parameters != null
     */
    @Nullable
    protected String retrieveCatalog(@NotNull final Map parameters)
    {
        return (String) parameters.get(ParameterValidationHandler.JDBC_CATALOG);
    }

    /**
     * Retrieves the JDBC schema from the attribute map.
     * @param parameters the parameter map.
     * @return the schema.
     * @precondition parameters != null
     */
    @Nullable
    protected String retrieveSchema(@NotNull final Map parameters)
    {
        return (String) parameters.get(ParameterValidationHandler.JDBC_SCHEMA);
    }

    /**
     * Builds the table key.
     * @return the map key.
     */
    @NotNull
    protected Object buildTableKey()
    {
        return "'@'@'table";
    }

    /**
     * Builds the table fields key.
     * @return the map key.
     */
    @NotNull
    protected Object buildTableFieldsKey(final Object key)
    {
        return ".98.table'@'@'fields`p" + key;
    }

    /**
     * Builds a pk key using given object.
     * @param firstKey the first object key.
     * @return the map key.
     */
    @NotNull
    protected Object buildPkKey(final Object firstKey)
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
    protected Object buildPkKey(
        final Object firstKey, final Object secondKey)
    {
        return buildPkKey(firstKey) + "-.,.,-" + secondKey;
    }

    /**
     * Builds a fk key using given object.
     * @param firstKey the first object key.
     * @return the map key.
     */
    @NotNull
    protected Object buildFkKey(final Object firstKey)
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
    protected Object buildFkKey(
        final Object firstKey, final Object secondKey)
    {
        return buildFkKey(firstKey) + ".,.," + secondKey;
    }

    /**
     * Retrieves the product name.
     * @param metaData the database metadata.
     * @return the product name.
     * @throws QueryJBuildException if the check fails.
     * @precondition metaData != null
     */
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
            Log t_Log =
                UniqueLogFactory.getLog(
                    DatabaseMetaDataRetrievalHandler.class);

            if  (t_Log != null)
            {
                t_Log.error(
                    "Cannot retrieve database vendor's product name.",
                    sqlException);
            }

            t_ExceptionToThrow =
                new QueryJBuildException(
                    "cannot.retrieve.vendor.product.name",
                    sqlException);
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
     * @precondition metaData != null
     */
    protected String retrieveProductVersion(@NotNull final DatabaseMetaData metaData)
    {
        @NotNull String result = "";

        try
        {
            result = metaData.getDatabaseProductVersion();
        }
        catch  (@NotNull final SQLException sqlException)
        {
            Log t_Log =
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
     * Retrieves the database major version.
     * @param metaData the database metadata.
     * @return the database major version.
     * @precondition metaData != null
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
            Log t_Log =
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
     * @precondition metaData != null
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
            Log t_Log =
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
     * @precondition metaData != null
     * @precondition parameters != null
     */
    protected boolean checkVendor(@NotNull final DatabaseMetaData metaData, @NotNull Map parameters)
        throws  QueryJBuildException
    {
        boolean result = false;

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
     * @precondition product != null
     */
    protected abstract boolean checkVendor(
        final String productName,
        final String productVersion,
        final int majorVersion,
        final int minorVersion);
}
