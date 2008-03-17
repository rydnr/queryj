//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Filename: OracleMetadataManager.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Manages the information metadata stored in an Oracle database.
 *
 */
package org.acmsl.queryj.tools.metadata.engines.oracle;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.Condition;
import org.acmsl.queryj.Field;
import org.acmsl.queryj.QueryFactory;
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.QueryResultSet;
import org.acmsl.queryj.SelectQuery;
import org.acmsl.queryj.tools.metadata.engines.JdbcMetadataManager;
import org.acmsl.queryj.tools.metadata.engines.oracle.OracleTableRepository;
import org.acmsl.queryj.tools.metadata.engines.oracle.OracleUserConsColumnsTable;
import org.acmsl.queryj.tools.metadata.engines.oracle.OracleUserConstraintsTable;
import org.acmsl.queryj.tools.metadata.MetadataExtractionListener;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.SqlTypeResolver;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Manages the information metadata stored in an Oracle database.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class OracleMetadataManager
    extends  JdbcMetadataManager
    implements  OracleTableRepository
{
    /**
     * Creates an empty <code>OracleMetadataManager</code>.
     * @param metadataExtractionListener the <code>MetadataExtractionListener</code> instance.
     */
    protected OracleMetadataManager(
        final MetadataExtractionListener metadataExtractionListener)
    {
        super(metadataExtractionListener);
    }

    /**
     * Creates an <code>OracleMetadataManager</code>. using given information.
     * @param metadataExtractionListener the metadata extraction listener.
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
    public OracleMetadataManager(
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
        super(
            metadataExtractionListener,
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
     * Creates an <code>OracleMetadataManager</code> using given information.
     * @param metadataExtractionListener the metadata extraction listener.
     * @param tableNames explicitly specified table names.
     * @param procedureNames explicitly specified procedure names.
     * @param metaData the database meta data.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    public OracleMetadataManager(
        final MetadataExtractionListener metadataExtractionListener,
        final String[] tableNames,
        final String[] procedureNames,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema)
        throws  SQLException,
                QueryJException
    {
        super(
            metadataExtractionListener,
            tableNames,
            procedureNames,
            metaData,
            catalog,
            schema);
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
        extractPrimaryKeys(
            metaData.getConnection(),
            catalog,
            schema,
            getTableNames(
                metaData, catalog, schema, metadataExtractionListener),
            QueryFactory.getInstance(),
            OracleTextFunctions.getInstance(),
            metadataExtractionListener);
    }

    /**
     * Extracts the primary keys.
     * @param connection the database connection.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param tableNames the table names.
     * @param queryFactory the <code>QueryFactory</code> instance.
     * @param oracleTextFunctions the <code>OracleTextFunctions</code> instance.
     * @param metadataExtractionListener the metadata extraction listener.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     * @precondition connection != null
     * @precondition tableNames != null
     * @precondition queryFactory != null
     * @precondition oracleTextFunctions != null
     */
    protected void extractPrimaryKeys(
        final Connection connection,
        final String catalog,
        final String schema,
        final String[] tableNames,
        final QueryFactory queryFactory,
        final OracleTextFunctions oracleTextFunctions,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        ResultSet t_rsResults = null;

        Locale t_Locale = Locale.getDefault();

        PreparedStatement t_PreparedStatement = null;

        Log t_Log = UniqueLogFactory.getLog(OracleMetadataManager.class);

        int t_iLength = (tableNames != null) ? tableNames.length : 0;
            
        String t_strTableName = null;

        try
        {
            for  (int t_iTableIndex = 0;
                      t_iTableIndex < t_iLength;
                      t_iTableIndex++) 
            {
                try
                {
                    t_strTableName = tableNames[t_iTableIndex];

                    SelectQuery t_Query = queryFactory.createSelectQuery();

                    t_Query.select(USER_CONS_COLUMNS.COLUMN_NAME);

                    t_Query.from(USER_CONS_COLUMNS);
                    t_Query.from(USER_CONSTRAINTS);

                    Condition t_Condition =
                        USER_CONS_COLUMNS.CONSTRAINT_NAME.equals(
                            USER_CONSTRAINTS.CONSTRAINT_NAME);

                    t_Condition =
                        t_Condition.and(
                            oracleTextFunctions.upper(USER_CONS_COLUMNS.TABLE_NAME).equals(
                                oracleTextFunctions.upper()));

                    t_Condition =
                        t_Condition.and(
                            USER_CONSTRAINTS.CONSTRAINT_TYPE.equals("P"));

                    t_Query.where(t_Condition);

                    t_PreparedStatement = t_Query.prepareStatement(connection);

                    if  (   (t_Log != null)
                         && (t_Log.isInfoEnabled()))
                    {
                        t_Log.info("  Extracting PK for "
                            + t_strTableName.toUpperCase(t_Locale) + "...");
                    }

                    t_Query.setString(
                        oracleTextFunctions.upper(USER_CONS_COLUMNS.TABLE_NAME)
                            .equals(oracleTextFunctions.upper()),
                        t_strTableName.toUpperCase(t_Locale));

                    if  (   (t_Log != null)
                         && (t_Log.isDebugEnabled()))
                    {
                        t_Log.debug("query:" + t_Query);
                    }
                    
                    t_rsResults = t_Query.executeQuery();

                    if  (t_rsResults != null)
                    {
                        while  (t_rsResults.next())
                        {

                            final String t_rsResult_PK = 
                                t_rsResults.getString(
                                    USER_CONS_COLUMNS.COLUMN_NAME.toSimplifiedString());

                            if  (   (t_Log != null)
                                 && (t_Log.isInfoEnabled()))
                            {
                                t_Log.info("    PK="+t_rsResult_PK);
                            }

                            addPrimaryKey(
                                t_strTableName,
                                t_rsResult_PK);
                        }

                        if  (metadataExtractionListener != null)
                        {
                            metadataExtractionListener.primaryKeyExtracted(
                                t_strTableName,
                                getPrimaryKey(t_strTableName));
                        }
                    }

                    if  (   (t_Log != null)
                         && (t_Log.isInfoEnabled()))
                    {
                        t_Log.info("  Done.");
                    }

                }
                catch  (final SQLException sqlException)
                {
                    throw
                        new QueryJException(
                            "cannot.retrieve.primary.keys",
                            new Object[]
                            {
                                t_strTableName
                            },
                            sqlException);
                }
            }
        }
        catch  (final QueryJException queryjException)
        {
            throw queryjException;
        }
        finally 
        {
            try 
            {
                if  (t_rsResults != null)
                {
                    t_rsResults.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the result set.",
                        sqlException);
                }
            }

            try 
            {
                if  (t_PreparedStatement != null)
                {
                    t_PreparedStatement.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the statement.",
                        sqlException);
                }
            }
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
        extractForeignKeys(
            metaData.getConnection(),
            catalog,
            schema,
            getTableNames(
                metaData, catalog, schema, metadataExtractionListener),
            QueryFactory.getInstance(),
            OracleTextFunctions.getInstance(),
            metadataExtractionListener);
    }

    /**
     * Extracts the foreign keys.
     * @param connection the database connection.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param tableNames the table names.
     * @param queryFactory the query factory.
     * @param oracleTextFunctions the <code>OracleTextFunctions</code> instance.
     * @param metadataExtractionListener the metadata extraction listener.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     * @precondition connection != null
     * @precondition tableNames != null
     * @precondition queryFactory != null
     * @precondition oracleTextFunctions != null
     */
    protected void extractForeignKeys(
        final Connection connection,
        final String catalog,
        final String schema,
        final String[] tableNames,
        final QueryFactory queryFactory,
        final OracleTextFunctions oracleTextFunctions,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        QueryResultSet t_Results = null;

        PreparedStatement t_PreparedStatement = null;

        Locale t_Locale = Locale.getDefault();

        Log t_Log = UniqueLogFactory.getLog(OracleMetadataManager.class);
        
        int t_iLength = (tableNames != null) ? tableNames.length : 0;

        String t_strTableName = null;

        try
        {
            for  (int t_iTableIndex = 0;
                      t_iTableIndex < t_iLength;
                      t_iTableIndex++) 
            {
                try
                {
                    t_strTableName = tableNames[t_iTableIndex];

                    SelectQuery t_Query = queryFactory.createSelectQuery();

                    OracleUserConstraintsTable tableConstraints =
                        OracleUserConstraintsTable.getInstance("tableConstraints");
                    OracleUserConsColumnsTable tableConstraintColumns =
                        OracleUserConsColumnsTable.getInstance("tableConstraintColumns");
                    OracleUserConstraintsTable foreignTableConstraints =
                        OracleUserConstraintsTable.getInstance("foreignTableConstraints");
                    OracleUserConsColumnsTable foreignTableConstraintColumns =
                        OracleUserConsColumnsTable.getInstance("foreignTableConstraintColumns");

                    t_Query.select(tableConstraints.CONSTRAINT_NAME);
                    t_Query.select(tableConstraintColumns.COLUMN_NAME);
                    t_Query.select(foreignTableConstraints.TABLE_NAME);
                    t_Query.select(foreignTableConstraintColumns.COLUMN_NAME);

                    t_Query.from(tableConstraints);
                    t_Query.from(tableConstraintColumns);
                    t_Query.from(foreignTableConstraints);
                    t_Query.from(foreignTableConstraintColumns);

                    t_Query.where(
                        tableConstraints.CONSTRAINT_TYPE
                            .equals("P")
                        .and(foreignTableConstraints.R_CONSTRAINT_NAME
                            .equals(tableConstraints.CONSTRAINT_NAME))
                        .and(foreignTableConstraints.OWNER
                            .equals(tableConstraints.OWNER))
                        .and(tableConstraints.OWNER
                            .equals(tableConstraintColumns.OWNER))
                        .and(tableConstraints.CONSTRAINT_NAME
                            .equals(tableConstraintColumns.CONSTRAINT_NAME))
                        .and(tableConstraints.TABLE_NAME
                            .equals(tableConstraintColumns.TABLE_NAME))
                        .and(foreignTableConstraints.OWNER
                            .equals(foreignTableConstraintColumns.OWNER))
                        .and(foreignTableConstraints.CONSTRAINT_NAME
                            .equals(foreignTableConstraintColumns.CONSTRAINT_NAME))
                        .and(foreignTableConstraints.TABLE_NAME
                            .equals(foreignTableConstraintColumns.TABLE_NAME))
                        .and(foreignTableConstraintColumns.POSITION
                            .equals(tableConstraintColumns.POSITION))
                        .and(oracleTextFunctions.upper(tableConstraints.TABLE_NAME)
                            .equals(oracleTextFunctions.upper())));

                    t_Query.groupBy(tableConstraints.CONSTRAINT_NAME);
                    t_Query.groupBy(tableConstraintColumns.COLUMN_NAME);
                    t_Query.groupBy(foreignTableConstraints.TABLE_NAME);
                    t_Query.groupBy(foreignTableConstraintColumns.COLUMN_NAME);

                    t_PreparedStatement = t_Query.prepareStatement(connection);

                    if  (   (t_Log != null)
                         && (t_Log.isInfoEnabled()))
                    {
                        t_Log.info(
                           "  Extracting FK for " 
                            + t_strTableName.toUpperCase(t_Locale) + "...");
                    }

                    t_Query.setString(
                        oracleTextFunctions.upper(tableConstraints.TABLE_NAME)
                            .equals(oracleTextFunctions.upper()),
                        t_strTableName.toUpperCase(t_Locale));

                    if  (   (t_Log != null)
                         && (t_Log.isDebugEnabled()))
                    {
                        t_Log.debug("query:" + t_Query);
                    }

                    t_Results = (QueryResultSet) t_Query.executeQuery();

                    if  (t_Results != null)
                    {

                        String t_strForeignKeyName;
                        String t_strTableColumnName;
                        String t_strForeignTableName;
                        String t_strForeignColumnName;

                        String t_strPreviousForeignTableName = null;
                        Collection t_cFks = new ArrayList();

                        while  (t_Results.next())
                        {

                            t_strForeignKeyName = t_Results.getString(
                                tableConstraints.CONSTRAINT_NAME);
                            
                            t_strTableColumnName = t_Results.getString(
                                tableConstraintColumns.COLUMN_NAME);
                            
                            t_strForeignTableName = t_Results.getString(
                                foreignTableConstraints.TABLE_NAME);
                            
                            t_strForeignColumnName = t_Results.getString(
                                foreignTableConstraintColumns.COLUMN_NAME);

                            if  (metadataExtractionListener != null)
                            {
                                t_cFks.add(t_strForeignColumnName);
                            }

                            if  (   (t_Log != null)
                                 && (t_Log.isInfoEnabled()))
                            {
                                t_Log.info("    FK=["
                                    +t_strForeignKeyName+", "
                                    +t_strTableColumnName+", "
                                    +t_strForeignTableName+", "
                                    +t_strForeignColumnName+"]");
                            }

                            addForeignKey(
                                t_strForeignTableName,
                                new String[] {t_strForeignColumnName},
                                t_strTableName,
                                new String[] {t_strTableColumnName});

                            if  (   (metadataExtractionListener != null)
                                 && (t_strPreviousForeignTableName != null)
                                 && (!t_strPreviousForeignTableName.equals(t_strForeignTableName)))
                            {
                                metadataExtractionListener.foreignKeyExtracted(
                                    t_strTableName,
                                    t_strPreviousForeignTableName,
                                    (String[]) t_cFks.toArray(EMPTY_STRING_ARRAY));

                                t_cFks = new ArrayList();
                            }

                            t_strPreviousForeignTableName = t_strForeignTableName;
                        }

                    }

                    if  (   (t_Log != null)
                         && (t_Log.isInfoEnabled()))
                    {
                        t_Log.info("  Done.");
                    }
                }
                catch  (final SQLException sqlException)
                {
                    throw
                        new QueryJException(
                            "cannot.retrieve.foreign.keys",
                            new Object[]
                            {
                                t_strTableName
                            },
                            sqlException);
                }
            }
        }
        catch  (final QueryJException queryjException)
        {
            throw queryjException;
        }
        finally 
        {
            try 
            {
                if  (t_Results != null)
                {
                    t_Results.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the result set.",
                        sqlException);
                }
            }

            try 
            {
                if  (t_PreparedStatement != null)
                {
                    t_PreparedStatement.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the statement.",
                        sqlException);
                }
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
        return
            getTableNames(
                metaData.getConnection(),
                catalog,
                schema,
                QueryFactory.getInstance(),
                metadataExtractionListener);
    }

    /**
     * Retrieves the table names.
     * @param connection the connection.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param queryFactory the <code>QueryFactory</code> instance.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of all table names.
     * @throws SQLException if the database operation fails.
     * @precondition connection != null
     * @precondition queryFactory != null
     */
    protected String[] getTableNames(
        final Connection connection,
        final String catalog,
        final String schema,
        final QueryFactory queryFactory,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        String[] result;

        Log t_Log = UniqueLogFactory.getLog(OracleMetadataManager.class);
        
        QueryResultSet t_Results = null;

        PreparedStatement t_PreparedStatement = null;

        try
        {
            try
            {
                SelectQuery t_Query = queryFactory.createSelectQuery();

                t_Query.select(USER_TABLES.TABLE_NAME);

                t_Query.from(USER_TABLES);

                t_Query.where(USER_TABLES.TABLE_NAME.notEquals("PLAN_TABLE"));

                t_PreparedStatement = t_Query.prepareStatement(connection);

                t_Results = t_Query.retrieveMatchingResults();
            }
            catch  (final SQLException sqlException)
            {
                throw
                    new QueryJException(
                        "cannot.retrieve.database.table.names",
                        sqlException);
            }

            result = extractTableNames(t_Results, metadataExtractionListener);
        }
        catch  (final SQLException sqlException)
        {
            if  (t_Log != null)
            {
                t_Log.error(
                    "Cannot retrieve the table names.",
                    sqlException);
            }

            throw sqlException;
        }
        catch  (final QueryJException queryjException)
        {
            if  (t_Log != null)
            {
                t_Log.error(
                    "Cannot retrieve the table names.",
                    queryjException);
            }

            throw queryjException;
        }
        finally 
        {
            try 
            {
                if  (t_Results != null)
                {
                    t_Results.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the result set.",
                        sqlException);
                }
            }

            try 
            {
                if  (t_PreparedStatement != null)
                {
                    t_PreparedStatement.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the statement.",
                        sqlException);
                }
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
     * @param table the table.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of table names.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected String[] extractTableNames(
        final QueryResultSet resultSet,
        final String table,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        return
            extractStringFields(
                resultSet,
                USER_TABLES.TABLE_NAME,
                null,
                (Field) null)[0];
                // TODO metadataExtractionListener)[0];
    }

    /**
     * Retrieves the column names from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param parentTable the parent table.
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
        return
            getColumnNames(
                metaData.getConnection(),
                catalog,
                schema,
                tableName,
                parentTable,
                QueryFactory.getInstance(),
                OracleTextFunctions.getInstance(),
                metadataExtractionListener);
    }

    /**
     * Retrieves the column names from given table name.
     * @param connection the connection.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param parentTable the parent table.
     * @param queryFactory the <code>QueryFactory</code> instance.
     * @param oracleTextFunctions the <code>OracleTextFunctions</code> instance.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of all column names.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if the any other error occurs.
     * @precondition connection != null
     * @precondition queryFactory != null
     * @precondition oracleTextFunctions != null
     */
    protected String[] getColumnNames(
        final Connection connection,
        final String catalog,
        final String schema,
        final String tableName,
        final String parentTable,
        final QueryFactory queryFactory,
        final OracleTextFunctions oracleTextFunctions,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        Collection result = new ArrayList();

        Locale t_Locale = Locale.getDefault();

        Log t_Log = UniqueLogFactory.getLog(OracleMetadataManager.class);
        
        ResultSet t_rsResults = null;

        PreparedStatement t_PreparedStatement = null;

        String t_strQuery = null;

        SelectQuery t_Query;

        try
        {
            try
            {
                t_Query = queryFactory.createSelectQuery();

                t_Query.select(USER_TAB_COLUMNS.COLUMN_NAME);

                t_Query.from(USER_TAB_COLUMNS);

                t_Query.where(
                    oracleTextFunctions.upper(USER_TAB_COLUMNS.TABLE_NAME).equals(
                        oracleTextFunctions.upper()));

                t_Query.orderBy(USER_TAB_COLUMNS.COLUMN_ID);

                t_PreparedStatement = t_Query.prepareStatement(connection);

                t_Query.setString(
                    oracleTextFunctions.upper(USER_TAB_COLUMNS.TABLE_NAME).equals(
                        oracleTextFunctions.upper()),
                    tableName.toUpperCase(t_Locale));

                /*
                t_PreparedStatement = t_Connection.prepareStatement(t_Query.toString());

                t_PreparedStatement.setString(
                    1,
                    tableName);
                */

                t_strQuery = t_Query.toString();

                t_rsResults = t_Query.executeQuery();
                //t_rsResults = t_PreparedStatement.executeQuery();
            }
            catch  (final SQLException sqlException)
            {
                throw
                    new QueryJException(
                        "cannot.retrieve.database.column.names",
                        new Object[]
                        {
                            tableName
                        },
                        sqlException);
            }

            result.addAll(
                Arrays.asList(
                    extractColumnNames(
                        t_rsResults, tableName, metadataExtractionListener)));
        }
        catch  (final SQLException sqlException)
        {
            if  (t_Log != null)
            {
                t_Log.error(
                    "Cannot retrieve the column names.",
                    sqlException);
            }

            throw sqlException;
        }
        catch  (final QueryJException queryjException)
        {
            if  (t_Log != null)
            {
                t_Log.error(
                    "Cannot retrieve the column names.",
                    queryjException);
            }

            throw queryjException;
        }
        finally 
        {
            try 
            {
                if  (t_rsResults != null)
                {
                    t_rsResults.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the result set.",
                        sqlException);
                }
            }

            try 
            {
                if  (t_PreparedStatement != null)
                {
                    t_PreparedStatement.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the statement.",
                        sqlException);
                }
            }
        }

        if  (result.size() == 0)
        {
            t_Log.error(
                  "'" + t_strQuery + "' returned zero results for table "
                + tableName);
        }

        return (String[]) result.toArray(EMPTY_STRING_ARRAY);
    }

    /**
     * Extracts the column names from given result set.
     * @param resultSet the result set with the table information.
     * @param table the table.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of column names.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected String[] extractColumnNames(
        final ResultSet resultSet,
        final String table,
        final MetadataExtractionListener metadataExtractionListener)
        throws  SQLException
    {
        return
            extractStringFields(
                resultSet,
                USER_TAB_COLUMNS.COLUMN_NAME,
                table,
                (Field) null)[0];
                // TODO metadataExtractionListener)[0];
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
     * @return the list of all column types.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if any other error occurs.
     * @precondition metaData != null
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
        return
            getColumnTypes(
                metaData.getConnection(),
                catalog,
                schema,
                tableName,
                parentTable,
                size,
                QueryFactory.getInstance(),
                OracleTextFunctions.getInstance(),
                metadataExtractionListener);
    }

    /**
     * Retrieves the column types from given table name.
     * @param connection the connection.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param parentTable the parent table.
     * @param size the number of fields to extract.
     * @param queryFactory the <code>QueryFactory</code> instance.
     * @param oracleTextFunctions the <code>OracleTextFunctions</code> instance.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of all column types.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if any other error occurs.
     * @precondition connection != null
     * @precondition queryFactory != null
     * @precondition oracleTextFunctions != null
     */
    protected int[] getColumnTypes(
        final Connection connection,
        final String catalog,
        final String schema,
        final String tableName,
        final String parentTable,
        final int size,
        final QueryFactory queryFactory,
        final OracleTextFunctions oracleTextFunctions,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        Collection result = new ArrayList();
        
        Locale t_Locale = Locale.getDefault();

        Log t_Log = UniqueLogFactory.getLog(OracleMetadataManager.class);
        
        ResultSet t_rsResults = null;

        PreparedStatement t_PreparedStatement = null;

        try
        {
            try
            {
                SelectQuery t_Query = queryFactory.createSelectQuery();

                t_Query.select(USER_TAB_COLUMNS.DATA_TYPE);
                t_Query.select(USER_TAB_COLUMNS.COLUMN_NAME);
                t_Query.select(USER_TAB_COLUMNS.DATA_SCALE);

                t_Query.from(USER_TAB_COLUMNS);

                t_Query.where(
                    oracleTextFunctions.upper(USER_TAB_COLUMNS.TABLE_NAME).equals(
                        oracleTextFunctions.upper()));

                t_Query.orderBy(USER_TAB_COLUMNS.COLUMN_ID);

                t_PreparedStatement = t_Query.prepareStatement(connection);

                t_Query.setString(
                    oracleTextFunctions.upper(USER_TAB_COLUMNS.TABLE_NAME).equals(
                        oracleTextFunctions.upper()),
                    tableName.toUpperCase(t_Locale));

                t_rsResults = t_Query.executeQuery();
            }
            catch  (final SQLException sqlException)
            {
                throw
                    new QueryJException(
                        "cannot.retrieve.database.column.types",
                        new Object[]
                        {
                            tableName
                        },
                        sqlException);
            }

            result.addAll(
                Arrays.asList(
                    (Object[])
                        toIntegerArray(
                            extractColumnTypes(
                                t_rsResults,
                                USER_TAB_COLUMNS.DATA_TYPE,
                                tableName,
                                metadataExtractionListener))));
        }
        catch  (final SQLException sqlException)
        {
            if  (t_Log != null)
            {
                t_Log.error(
                    "Cannot retrieve the column types.",
                    sqlException);
            }

            throw sqlException;
        }
        catch  (final QueryJException queryjException)
        {
            if  (t_Log != null)
            {
                t_Log.error(
                    "Cannot retrieve the column types.",
                    queryjException);
            }

            throw queryjException;
        }
        finally 
        {
            try 
            {
                if  (t_rsResults != null)
                {
                    t_rsResults.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the result set.",
                        sqlException);
                }
            }

            try 
            {
                if  (t_PreparedStatement != null)
                {
                    t_PreparedStatement.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the statement.",
                        sqlException);
                }
            }
        }

        return toIntArray((Integer[]) result.toArray(EMPTY_INTEGER_ARRAY));
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param fieldName the field name.
     * @param table the table.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of column types.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
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
                resultSet,
                fieldName,
                table,
                USER_TAB_COLUMNS.COLUMN_NAME.toSimplifiedString(),
                getMetadataTypeManager(),
                SqlTypeResolver.getInstance(),
                metadataExtractionListener);
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param fieldName the field name.
     * @param table the table.
     * @param metadataTypeManager the metadata type manager.
     * @param sqlTypeResolver the <code>SqlTypeResolver</code> instance.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of column types.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     * @precondition metadataTypeManager != null
     * @precondition sqlTypeResolver != null
     */
    protected int[] extractColumnTypes(
        final ResultSet resultSet,
        final String fieldName,
        final String table,
        final String columnNameFieldName,
        final MetadataTypeManager metadataTypeManager,
        final SqlTypeResolver sqlTypeResolver,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        int[] result = EMPTY_INT_ARRAY;

//         String[][] t_astrExtractedValues =
//             extractStringFields(
//                 resultSet,
//                 fieldName,
//                 table,
//                 columnNameFieldName,
//                 );
//         // TODO metadataExtractionListener);

        boolean t_bExtractNames = (columnNameFieldName != null);

        List t_cTypes = new ArrayList();
        List t_cNames = new ArrayList();
        List t_cPrecisions = new ArrayList();

        if  (resultSet != null)
        {
            while  (resultSet.next()) 
            {
                t_cTypes.add(resultSet.getString(fieldName));

                if  (t_bExtractNames)
                {
                    t_cNames.add(
                        resultSet.getString(columnNameFieldName));
                }
                t_cPrecisions.add(
                    new Integer(resultSet.getInt("DATA_SCALE")));
            }
        }

        String[] t_astrTypes = new String[t_cTypes.size()];
        t_cTypes.toArray(t_astrTypes);

        String[] t_astrNames = new String[t_cNames.size()];
        t_cNames.toArray(t_astrNames);

        Integer[] t_aiPrecisions = new Integer[t_cPrecisions.size()];
        t_cPrecisions.toArray(t_aiPrecisions);

        int[] t_iPrecisions = toIntArray(t_aiPrecisions);

        int t_iCount = (t_astrTypes != null) ? t_astrTypes.length : 0;

        if  (t_iCount > 0)
        {
            result = new int[t_iCount];

            int t_iType;

            for  (int t_iIndex = 0;
                      t_iIndex < t_iCount;
                      t_iIndex++) 
            {
                t_iType = 
                    metadataTypeManager.getJavaType(
                        t_astrTypes[t_iIndex], t_iPrecisions[t_iIndex]);

                result[t_iIndex] = t_iType;
            }
        }

        return result;
    }

    /**
     * Retrieves whether the columns allow null values.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param parentTable the parent table, if any.
     * @param size the number of fields to extract.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of all column types.
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
        return
            getAllowNulls(
                metaData.getConnection(),
                catalog,
                schema,
                tableName,
                parentTable,
                size,
                QueryFactory.getInstance(),
                OracleTextFunctions.getInstance(),
                metadataExtractionListener);
    }

    /**
     * Retrieves whether the columns allow null values.
     * @param connection the connection.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param parentTable the parent table.
     * @param size the number of fields to extract.
     * @param queryFactory the <code>QueryFactory</code> instance.
     * @param oracleTextFunctions the <code>OracleTextFunctions</code> instance.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the list of all column types.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if any other error occurs.
     * @precondition connection != null
     * @precondition queryFactory != null
     * @precondition oracleTextFunctions != null
     */
    protected boolean[] getAllowNulls(
        final Connection connection,
        final String catalog,
        final String schema,
        final String tableName,
        final String parentTable,
        final int size,
        final QueryFactory queryFactory,
        final OracleTextFunctions oracleTextFunctions,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        Collection result = new ArrayList();

        Locale t_Locale = Locale.getDefault();

        Log t_Log = UniqueLogFactory.getLog(OracleMetadataManager.class);
        
        ResultSet t_rsResults = null;

        PreparedStatement t_PreparedStatement = null;

        try
        {
            try
            {
                SelectQuery t_Query = queryFactory.createSelectQuery();

                t_Query.select(USER_TAB_COLUMNS.NULLABLE);
                t_Query.select(USER_TAB_COLUMNS.COLUMN_NAME);

                t_Query.from(USER_TAB_COLUMNS);

                t_Query.where(
                    oracleTextFunctions.upper(USER_TAB_COLUMNS.TABLE_NAME).equals(
                        oracleTextFunctions.upper()));

                t_Query.orderBy(USER_TAB_COLUMNS.COLUMN_ID);

                t_PreparedStatement = t_Query.prepareStatement(connection);

                t_Query.setString(
                    oracleTextFunctions.upper(USER_TAB_COLUMNS.TABLE_NAME).equals(
                        oracleTextFunctions.upper()),
                    tableName.toUpperCase(t_Locale));

                t_rsResults = t_Query.executeQuery();
            }
            catch  (final SQLException sqlException)
            {
                throw
                    new QueryJException(
                        "cannot.retrieve.database.column.types",
                        new Object[]
                        {
                            tableName
                        },
                        sqlException);
            }

            result.addAll(
                Arrays.asList(
                    (Object[])
                        toBooleanArray(
                            extractAllowNull(
                                t_rsResults,
                                USER_TAB_COLUMNS.NULLABLE,
                                tableName,
                                USER_TAB_COLUMNS.COLUMN_NAME,
                                metadataExtractionListener))));
        }
        catch  (final SQLException sqlException)
        {
            if  (t_Log != null)
            {
                t_Log.error(
                    "Cannot retrieve the column types.",
                    sqlException);
            }

            throw sqlException;
        }
        catch  (final QueryJException queryjException)
        {
            if  (t_Log != null)
            {
                t_Log.error(
                    "Cannot retrieve the column types.",
                    queryjException);
            }

            throw queryjException;
        }
        finally 
        {
            try 
            {
                if  (t_rsResults != null)
                {
                    t_rsResults.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the result set.",
                        sqlException);
                }
            }

            try 
            {
                if  (t_PreparedStatement != null)
                {
                    t_PreparedStatement.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the statement.",
                        sqlException);
                }
            }
        }

        return toBoolArray((Boolean[]) result.toArray(EMPTY_BOOLEAN_ARRAY));
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param fieldName the field name.
     * @param table the table name.
     * @param nameField the name field.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the information about the nullable condition of each column.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     * @overrides
     */
    protected boolean[] extractAllowNull(
        final ResultSet resultSet,
        final String fieldName,
        final String table,
        final String nameField,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException
    {
        boolean[] result = EMPTY_BOOL_ARRAY;

        String[][] t_astrExtractedValues =
            extractStringFields(
                resultSet,
                fieldName,
                table,
                nameField);
                // TODO metadataExtractionListener);

        String[] t_astrTypes = t_astrExtractedValues[0];
        String[] t_astrNames = t_astrExtractedValues[1];

        int t_iCount = (t_astrTypes != null) ? t_astrTypes.length : 0;

        if  (t_iCount > 0)
        {
            result = new boolean[t_iCount];

            boolean t_bItem;

            for  (int t_iIndex = 0;
                      t_iIndex < t_iCount;
                      t_iIndex++) 
            {
                t_bItem = 
                    !("N".equalsIgnoreCase(t_astrTypes[t_iIndex]));

                result[t_iIndex] = t_bItem;
            }
        }

        return result;
    }

    /**
     * Extracts the table comments.
     * @param metaData the database metadata.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param tableName the table name.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the table comments.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     * @precondition metaData != null
     * @precondition tableName != null
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
        return
            getTableComment (
                metaData.getConnection(),
                catalog,
                schema,
                tableName,
                QueryFactory.getInstance(),
                OracleTextFunctions.getInstance(),
                metadataExtractionListener);
    }

    /**
     * Extracts the table comments.
     * @param connection the database connection.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param tableName the table name.
     * @param queryFactory the <code>QueryFactory</code> instance.
     * @param oracleTextFunctions the <code>OracleTextFunctions</code> instance.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the table comments.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     * @precondition connection != null
     * @precondition tableNames != null
     * @precndition queryFactory != null
     * @precondition oracleTextFunctions != null
     */
    protected String getTableComment(
        final Connection connection,
        final String catalog,
        final String schema,
        final String tableName,
        final QueryFactory queryFactory,
        final OracleTextFunctions oracleTextFunctions,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        String result = "";

        Locale t_Locale = Locale.getDefault();

        Log t_Log = UniqueLogFactory.getLog(OracleMetadataManager.class);
        
        ResultSet t_rsResults = null;

        PreparedStatement t_PreparedStatement = null;

        try
        {
            try
            {
                SelectQuery t_Query = queryFactory.createSelectQuery();

                t_Query.select(USER_TAB_COMMENTS.COMMENTS);

                t_Query.from(USER_TAB_COMMENTS);

                t_Query.where(
                    oracleTextFunctions.upper(USER_TAB_COMMENTS.TABLE_NAME).equals(
                        oracleTextFunctions.upper()));

                t_PreparedStatement = t_Query.prepareStatement(connection);

                t_Query.setString(
                    oracleTextFunctions.upper(USER_TAB_COMMENTS.TABLE_NAME).equals(
                        oracleTextFunctions.upper()),
                    tableName.toUpperCase(t_Locale));

                if  (t_Log != null)
                {
                    t_Log.debug("query:" + t_Query);
                }
                
                t_rsResults = t_Query.executeQuery();

                String t_strComment = null;

                if  (   (t_rsResults != null)
                     && (t_rsResults.next()))
                {
                    t_strComment =
                        t_rsResults.getString(
                            USER_TAB_COMMENTS.COMMENTS.toSimplifiedString());

                    if  (metadataExtractionListener != null)
                    {
                        metadataExtractionListener.tableCommentExtracted(
                            tableName, t_strComment);
                    }

                    if  (t_strComment == null)
                    {
                        t_strComment = "";
                    }

                    result = t_strComment;
                }
            }
            catch  (final SQLException sqlException)
            {
                throw
                    new QueryJException(
                        "cannot.retrieve.table.comment",
                        new Object[]
                        {
                            tableName
                        },
                        sqlException);
            }
        }
        catch  (final QueryJException queryjException)
        {
            throw queryjException;
        }
        finally 
        {
            try 
            {
                if  (t_rsResults != null)
                {
                    t_rsResults.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the result set.",
                        sqlException);
                }
            }

            try 
            {
                if  (t_PreparedStatement != null)
                {
                    t_PreparedStatement.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the statement.",
                        sqlException);
                }
            }
        }

        return result;
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
        return
            getColumnComments(
                metaData.getConnection(),
                catalog,
                schema,
                tableName,
                columnNames,
                QueryFactory.getInstance(),
                OracleTextFunctions.getInstance(),
                metadataExtractionListener);
    }

    /**
     * Extracts the column comments.
     * @param connection the database connection.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param tableName the table name.
     * @param columnNames the column names.
     * @param queryFactory the <code>QueryFactory</code> instance.
     * @param oracleTextFunctions the <code>OracleTextFunctions</code>
     * instance.
     * @param metadataExtractionListener the metadata extraction listener.
     * @return the table comments.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     * @precondition connection != null
     * @precondition tableName != null
     * @precondition queryFactory != null
     * @precondition oracleTextFunctions != null
     */
    protected String[] getColumnComments(
        final Connection connection,
        final String catalog,
        final String schema,
        final String tableName,
        final String[] columnNames,
        final QueryFactory queryFactory,
        final OracleTextFunctions oracleTextFunctions,
        final MetadataExtractionListener metadataExtractionListener)
      throws  SQLException,
              QueryJException
    {
        String[] result;

        int t_iCount = (columnNames != null) ? columnNames.length : 0;

        result = (t_iCount == 0) ? EMPTY_STRING_ARRAY : new String[t_iCount];

        Locale t_Locale = Locale.getDefault();

        Log t_Log = UniqueLogFactory.getLog(OracleMetadataManager.class);
        
        ResultSet t_rsResults = null;

        PreparedStatement t_PreparedStatement = null;

        try
        {
            String t_strColumnName = null;
            String t_strComment = null;

            try
            {
                SelectQuery t_Query;

                for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
                {
                    t_strColumnName = columnNames[t_iIndex];

                    t_Query = queryFactory.createSelectQuery();

                    t_Query.select(USER_COL_COMMENTS.COMMENTS);

                    t_Query.from(USER_COL_COMMENTS);

                    t_Query.where(
                             oracleTextFunctions.upper(USER_COL_COMMENTS.TABLE_NAME).equals(
                                 oracleTextFunctions.upper())
                        .and(oracleTextFunctions.upper(USER_COL_COMMENTS.COLUMN_NAME).equals(
                                 oracleTextFunctions.upper())));

                    t_PreparedStatement = t_Query.prepareStatement(connection);

                    t_Query.setString(
                        oracleTextFunctions.upper(USER_COL_COMMENTS.TABLE_NAME).equals(
                            oracleTextFunctions.upper()),
                        tableName.toUpperCase(t_Locale));

                    t_Query.setString(
                        oracleTextFunctions.upper(USER_COL_COMMENTS.COLUMN_NAME).equals(
                            oracleTextFunctions.upper()),
                        t_strColumnName.toUpperCase(t_Locale));

                    String t_strQuery = t_Query.toString();

                    if  (   (t_Log != null)
                         && (t_Log.isDebugEnabled()))
                    {
                        t_Log.debug("query:" + t_strQuery);
                    }
                
                    t_rsResults = t_Query.executeQuery();

                    if  (   (t_rsResults != null)
                         && (t_rsResults.next()))
                    {
                        t_strComment =
                            t_rsResults.getString(
                                USER_COL_COMMENTS.COMMENTS
                                    .toSimplifiedString());

                        if  (metadataExtractionListener != null)
                        {
                            metadataExtractionListener.columnCommentExtracted(
                                tableName, t_strColumnName, t_strComment);
                        }

                        if  (t_strComment == null)
                        {
                            t_strComment = "";
                        }

                        result[t_iIndex] = t_strComment;
                    }
                }
            }
            catch  (final SQLException sqlException)
            {
                throw
                    new QueryJException(
                        "cannot.retrieve.column.comment",
                        new Object[]
                        {
                            t_strColumnName,
                            tableName
                        },
                        sqlException);
            }
        }
        catch  (final QueryJException queryjException)
        {
            throw queryjException;
        }
        finally 
        {
            try 
            {
                if  (t_rsResults != null)
                {
                    t_rsResults.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the result set.",
                        sqlException);
                }
            }

            try 
            {
                if  (t_PreparedStatement != null)
                {
                    t_PreparedStatement.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the statement.",
                        sqlException);
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the type manager.
     * @return such instance.
     */
    public MetadataTypeManager getMetadataTypeManager()
    {
        return OracleMetadataTypeManager.getInstance();
    }

    /**
     * Retrieves the name identifying the manager instance.
     * @return such name.
     */
    public String getName()
    {
        return "oracle";
    }

    /**
     * Checks whether the engine requires specific BLOB handling.
     * @return <code>true</code> in such case.
     */
    public boolean requiresCustomBlobHandling()
    {
        // TODO: Test whether this is needed.
        return true;
    }

    /**
     * Checks whether the engine requires specific CLOB handling.
     * @return <code>true</code> in such case.
     */
    public boolean requiresCustomClobHandling()
    {
        // TODO: Test whether this is needed.
        return true;
    }
}
