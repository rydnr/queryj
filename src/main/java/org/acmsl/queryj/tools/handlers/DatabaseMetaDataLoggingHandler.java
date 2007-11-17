//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: DatabaseMetaDataLoggingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Prints trace messages with some database metadata information.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.SqlTypeResolver;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.utils.StringUtils;
import org.acmsl.commons.utils.StringValidator;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

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
import java.sql.Types;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Prints trace messages with some database metadata information.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class DatabaseMetaDataLoggingHandler
    extends  AbstractAntCommandHandler
{
    /**
     * Creates a DatabaseMetaDataLoggingHandler.
     */
    public DatabaseMetaDataLoggingHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        return handle(command.getAttributeMap());
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition project != null
     * @precondition task != null
     */
    protected boolean handle(final Map parameters)
        throws  BuildException
    {
        return
            handle(
                retrieveDatabaseMetaData(parameters),
                retrieveMetadataManager(parameters));
    }

    /**
     * Handles given information.
     * @param metaData the database metadata.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition metaData != null
     * @precondition metadataManager != null
     */
    protected boolean handle(
        final DatabaseMetaData metaData, final MetadataManager metadataManager)
        throws  BuildException
    {
        boolean result = false;

        Log t_Log = null;
        
        try 
        {
            t_Log =
                UniqueLogFactory.getLog(DatabaseMetaDataLoggingHandler.class);
            
            if  (t_Log != null)
            {
                traceSqlTypes(t_Log, SqlTypeResolver.getInstance());

                t_Log.debug(
                      "Numeric functions:"
                    + metaData.getNumericFunctions());

                t_Log.debug(
                      "String functions:"
                    + metaData.getStringFunctions());

                t_Log.debug(
                      "System functions:"
                    + metaData.getSystemFunctions());

                t_Log.debug(
                      "Time functions:"
                    + metaData.getTimeDateFunctions());

                t_Log.debug(
                      "insertsAreDetected("
                    +     "TYPE_FORWARD_ONLY):"
                    + metaData.insertsAreDetected(
                          ResultSet.TYPE_FORWARD_ONLY));

                t_Log.debug(
                     "insertsAreDetected("
                    +     "TYPE_SCROLL_INSENSITIVE):"
                    + metaData.insertsAreDetected(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.debug(
                     "insertsAreDetected("
                    +     "TYPE_SCROLL_SENS):"
                    + metaData.insertsAreDetected(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                t_Log.debug(
                    "isCatalogAtStart():"
                    + metaData.isCatalogAtStart());

                t_Log.debug(
                      "isReadOnly():"
                    + metaData.isReadOnly());

                /*
                 * Fails for MySQL with a java.lang.AbstractMethodError 
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.locatorsUpdateCopy()
                 t_Log.debug(
                       "locatorsUpdateCopy():"
                     + metaData.locatorsUpdateCopy());
                 */

                t_Log.debug(
                      "nullPlusNonNullIsNull():"
                    + metaData.nullPlusNonNullIsNull());

                t_Log.debug(
                      "nullsAreSortedAtEnd():"
                    + metaData.nullsAreSortedAtEnd());
                
                t_Log.debug(
                      "nullsAreSortedAtStart():"
                    + metaData.nullsAreSortedAtStart());
                
                t_Log.debug(
                      "nullsAreSortedHigh():"
                    + metaData.nullsAreSortedHigh());
                
                t_Log.debug(
                      "nullsAreSortedLow():"
                    + metaData.nullsAreSortedLow());
                
                t_Log.debug(
                      "othersDeletesAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.othersDeletesAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                t_Log.debug(
                      "othersDeletesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.othersDeletesAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.debug(
                      "othersDeletesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.othersDeletesAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                t_Log.debug(
                      "othersInsertsAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.othersInsertsAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                t_Log.debug(
                      "othersInsertsAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.othersInsertsAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.debug(
                      "othersInsertsAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.othersInsertsAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                t_Log.debug(
                      "othersUpdatesAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.othersUpdatesAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                t_Log.debug(
                      "othersUpdatesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.othersUpdatesAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.debug(
                      "othersUpdatesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.othersUpdatesAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                t_Log.debug(
                      "ownDeletesAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.ownDeletesAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                t_Log.debug(
                      "ownDeletesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.ownDeletesAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.debug(
                      "ownDeletesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.ownDeletesAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                t_Log.debug(
                      "ownInsertsAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.ownInsertsAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                t_Log.debug(
                      "ownInsertsAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.ownInsertsAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.debug(
                      "ownInsertsAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.ownInsertsAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                t_Log.debug(
                      "ownUpdatesAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.ownUpdatesAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                t_Log.debug(
                      "ownUpdatesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.ownUpdatesAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.debug(
                      "ownUpdatesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.ownUpdatesAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                t_Log.debug(
                      "storesLowerCaseIdentifiers():"
                    + metaData.storesLowerCaseIdentifiers());
            
                t_Log.debug(
                      "storesLowerCaseQuotedIdentifiers():"
                    + metaData.storesLowerCaseQuotedIdentifiers());
            
                t_Log.debug(
                      "storesMixedCaseIdentifiers():"
                    + metaData.storesMixedCaseIdentifiers());
            
                t_Log.debug(
                      "storesMixedCaseQuotedIdentifiers():"
                    + metaData.storesMixedCaseQuotedIdentifiers());
            
                t_Log.debug(
                      "storesUpperCaseIdentifiers():"
                    + metaData.storesUpperCaseIdentifiers());
            
                t_Log.debug(
                      "storesUpperCaseQuotedIdentifiers():"
                    + metaData.storesUpperCaseQuotedIdentifiers());
            
                t_Log.debug(
                      "supportsAlterTableWithAddColumn():"
                    + metaData.supportsAlterTableWithAddColumn());
            
                t_Log.debug(
                      "supportsAlterTableWithDropColumn():"
                    + metaData.supportsAlterTableWithDropColumn());
            
                t_Log.debug(
                      "supportsANSI92EntryLevelSQL():"
                    + metaData.supportsANSI92EntryLevelSQL());
            
                t_Log.debug(
                      "supportsANSI92FullSQL():"
                    + metaData.supportsANSI92FullSQL());
            
                t_Log.debug(
                      "supportsANSI92IntermediateSQL():"
                    + metaData.supportsANSI92IntermediateSQL());
            
                t_Log.debug(
                      "supportsBatchUpdates():"
                    + metaData.supportsBatchUpdates());
            
                t_Log.debug(
                      "supportsCatalogsInDataManipulation():"
                    + metaData.supportsCatalogsInDataManipulation());
            
                t_Log.debug(
                      "supportsCatalogsInIndexDefinitions():"
                    + metaData.supportsCatalogsInIndexDefinitions());
            
                t_Log.debug(
                      "supportsCatalogsInPrivilegeDefinitions():"
                    + metaData.supportsCatalogsInPrivilegeDefinitions());
            
                t_Log.debug(
                      "supportsCatalogsInProcedureCalls():"
                    + metaData.supportsCatalogsInProcedureCalls());
            
                t_Log.debug(
                      "supportsCatalogsInTableDefinitions():"
                    + metaData.supportsCatalogsInTableDefinitions());
            
                t_Log.debug(
                      "supportsColumnAliasing():"
                    + metaData.supportsColumnAliasing());
            
                t_Log.debug(
                      "supportsConvert():"
                    + metaData.supportsConvert());
            
                t_Log.debug(
                      "supportsCoreSQLGrammar():"
                    + metaData.supportsCoreSQLGrammar());
            
                t_Log.debug(
                      "supportsCorrelatedSubqueries():"
                    + metaData.supportsCorrelatedSubqueries());
            
                t_Log.debug(
                      "supportsDataDefinitionAndDataManipulationTransactions():"
                    + metaData.supportsDataDefinitionAndDataManipulationTransactions());
            
                t_Log.debug(
                      "supportsDataManipulationTransactionsOnly():"
                    + metaData.supportsDataManipulationTransactionsOnly());
            
                t_Log.debug(
                      "supportsDifferentTableCorrelationNames():"
                    + metaData.supportsDifferentTableCorrelationNames());
            
                t_Log.debug(
                      "supportsExpressionsInOrderBy():"
                    + metaData.supportsExpressionsInOrderBy());
            
                t_Log.debug(
                      "supportsExtendedSQLGrammar():"
                    + metaData.supportsExtendedSQLGrammar());
            
                t_Log.debug(
                      "supportsFullOuterJoins():"
                    + metaData.supportsFullOuterJoins());
            
                String t_strSupportsGetGeneratedKeys = "false";

                try
                {
                    t_strSupportsGetGeneratedKeys =
                            "" + metaData.supportsGetGeneratedKeys();
                }
                catch  (final SQLException sqlException)
                {
                    t_strSupportsGetGeneratedKeys +=
                        sqlException.getMessage();
                }

                t_Log.debug(
                      "supportsGetGeneratedKeys():"
                    + t_strSupportsGetGeneratedKeys);
            
                t_Log.debug(
                      "supportsGroupBy():"
                    + metaData.supportsGroupBy());
            
                t_Log.debug(
                      "supportsGroupByBeyondSelect():"
                    + metaData.supportsGroupByBeyondSelect());
            
                t_Log.debug(
                      "supportsGroupByUnrelated():"
                    + metaData.supportsGroupByUnrelated());
            
                t_Log.debug(
                      "supportsIntegrityEnhancementFacility():"
                    + metaData.supportsIntegrityEnhancementFacility());
            
                t_Log.debug(
                      "supportsLikeEscapeClause():"
                    + metaData.supportsLikeEscapeClause());
            
                t_Log.debug(
                      "supportsLimitedOuterJoins():"
                    + metaData.supportsLimitedOuterJoins());
            
                t_Log.debug(
                      "supportsMinimumSQLGrammar():"
                    + metaData.supportsMinimumSQLGrammar());
            
                t_Log.debug(
                      "supportsMixedCaseIdentifiers():"
                    + metaData.supportsMixedCaseIdentifiers());
            
                t_Log.debug(
                      "supportsMixedCaseQuotedIdentifiers():"
                    + metaData.supportsMixedCaseQuotedIdentifiers());
            
                /*
                 * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsMultipleOpenResults()
                 t_Log.debug(
                       "supportsMultipleOpenResults():"
                     + metaData.supportsMultipleOpenResults());
                 */

                t_Log.debug(
                      "supportsMultipleResultSets():"
                    + metaData.supportsMultipleResultSets());
            
                t_Log.debug(
                      "supportsMultipleTransactions():"
                    + metaData.supportsMultipleTransactions());
            
                /*
                 * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsNamedParameters()
                 t_Log.debug(
                       "supportsNamedParameters():"
                     + metaData.supportsNamedParameters());
                 */

                t_Log.debug(
                      "supportsNonNullableColumns():"
                    + metaData.supportsNonNullableColumns());
            
                t_Log.debug(
                      "supportsOpenCursorsAcrossCommit():"
                    + metaData.supportsOpenCursorsAcrossCommit());
            
                t_Log.debug(
                      "supportsOpenCursorsAcrossRollback():"
                    + metaData.supportsOpenCursorsAcrossRollback());
            
                t_Log.debug(
                      "supportsOpenStatementsAcrossCommit():"
                    + metaData.supportsOpenStatementsAcrossCommit());
            
                t_Log.debug(
                      "supportsOpenStatementsAcrossRollback():"
                    + metaData.supportsOpenStatementsAcrossRollback());
            
                t_Log.debug(
                      "supportsOrderByUnrelated():"
                    + metaData.supportsOrderByUnrelated());
            
                t_Log.debug(
                      "supportsOuterJoins():"
                    + metaData.supportsOuterJoins());
            
                t_Log.debug(
                      "supportsPositionedDelete():"
                    + metaData.supportsPositionedDelete());
            
                t_Log.debug(
                      "supportsPositionedUpdate():"
                    + metaData.supportsPositionedUpdate());
            
                t_Log.debug(
                      "supportsResultSetConcurrency("
                    +     "TYPE_FORWARD_ONLY,CONCUR_READ_ONLY):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_READ_ONLY));

                t_Log.debug(
                      "supportsResultSetConcurrency("
                    +     "TYPE_FORWARD_ONLY,CONCUR_UPDATABLE):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE));

                t_Log.debug(
                      "supportsResultSetConcurrency("
                    +     "TYPE_SCROLL_INSENSITIVE,CONCUR_READ_ONLY):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY));

                t_Log.debug(
                      "supportsResultSetConcurrency("
                    +     "TYPE_SCROLL_INSENSITIVE,CONCUR_UPDATABLE):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE));

                t_Log.debug(
                      "supportsResultSetConcurrency("
                    +     "TYPE_SCROLL_SENSITIVE,CONCUR_READ_ONLY):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_READ_ONLY));

                t_Log.debug(
                      "supportsResultSetConcurrency("
                    +     "TYPE_SCROLL_SENSITIVE,CONCUR_UPDATABLE):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE));

                /*
                 * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsResultSetHoldability()
                 t_Log.debug(
                       "supportsResultSetHoldability("
                 +     "HOLD_CURSORS_OVER_COMMIT):"
                 + metaData.supportsResultSetHoldability(
                     ResultSet.HOLD_CURSORS_OVER_COMMIT));

                 t_Log.debug(
                       "supportsResultSetHoldability("
                 +     "CLOSE_CURSORS_AT_COMMIT):"
                 + metaData.supportsResultSetHoldability(
                     ResultSet.CLOSE_CURSORS_AT_COMMIT));
                 */

                t_Log.debug(
                      "supportsResultSetType("
                    +     "TYPE_FORWARD_ONLY):"
                    + metaData.supportsResultSetType(
                        ResultSet.TYPE_FORWARD_ONLY));

                t_Log.debug(
                      "supportsResultSetType("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.supportsResultSetType(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.debug(
                      "supportsResultSetType("
                    +     "TYPE_SCROLL_SENSITIVE):"
                    + metaData.supportsResultSetType(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                /*
                 * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsSavePoints()
                 t_Log.debug(
                       "supportsSavepoints():"
                     + metaData.supportsSavepoints());
                 */

                t_Log.debug(
                      "supportsSchemasInDataManipulation():"
                    + metaData.supportsSchemasInDataManipulation());
            
                t_Log.debug(
                      "supportsSchemasInIndexDefinitions():"
                    + metaData.supportsSchemasInIndexDefinitions());
            
                t_Log.debug(
                      "supportsSchemasInPrivilegeDefinitions():"
                    + metaData.supportsSchemasInPrivilegeDefinitions());
            
                t_Log.debug(
                      "supportsSchemasInProcedureCalls():"
                    + metaData.supportsSchemasInProcedureCalls());
            
                t_Log.debug(
                      "supportsSchemasInTableDefinitions():"
                    + metaData.supportsSchemasInTableDefinitions());
            
                t_Log.debug(
                      "supportsSelectForUpdate():"
                    + metaData.supportsSelectForUpdate());

                /*
                 * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsStatementPooling()
                 t_Log.debug(
                       "supportsStatementPooling():"
                     + metaData.supportsStatementPooling());
                */

                t_Log.debug(
                      "supportsStoredProcedures():"
                    + metaData.supportsStoredProcedures());
            
                t_Log.debug(
                      "supportsSubqueriesInComparisons():"
                    + metaData.supportsSubqueriesInComparisons());
            
                t_Log.debug(
                      "supportsSubqueriesInExists():"
                    + metaData.supportsSubqueriesInExists());
            
                t_Log.debug(
                      "supportsSubqueriesInIns():"
                    + metaData.supportsSubqueriesInIns());
            
                t_Log.debug(
                      "supportsSubqueriesInQuantifieds():"
                    + metaData.supportsSubqueriesInQuantifieds());
            
                t_Log.debug(
                      "supportsTableCorrelationNames():"
                    + metaData.supportsTableCorrelationNames());
            
                t_Log.debug(
                      "supportsTransactionIsolationLevel("
                    +     "TRANSACTION_NONE):"
                    + metaData.supportsTransactionIsolationLevel(
                        Connection.TRANSACTION_NONE));

                t_Log.debug(
                      "supportsTransactionIsolationLevel("
                    +     "TRANSACTION_READ_COMMITTED):"
                    + metaData.supportsTransactionIsolationLevel(
                        Connection.TRANSACTION_READ_COMMITTED));

                t_Log.debug(
                      "supportsTransactionIsolationLevel("
                    +     "TRANSACTION_READ_UNCOMMITTED):"
                    + metaData.supportsTransactionIsolationLevel(
                        Connection.TRANSACTION_READ_UNCOMMITTED));

                t_Log.debug(
                      "supportsTransactionIsolationLevel("
                    +     "TRANSACTION_REPEATABLE_READ):"
                    + metaData.supportsTransactionIsolationLevel(
                        Connection.TRANSACTION_REPEATABLE_READ));

                t_Log.debug(
                      "supportsTransactionIsolationLevel("
                    +     "TRANSACTION_SERIALIZABLE):"
                    + metaData.supportsTransactionIsolationLevel(
                        Connection.TRANSACTION_SERIALIZABLE));

                t_Log.debug(
                      "supportsTransactions():"
                    + metaData.supportsTransactions());
            
                t_Log.debug(
                      "supportsUnion():"
                    + metaData.supportsUnion());
            
                t_Log.debug(
                      "supportsUnionAll():"
                    + metaData.supportsUnionAll());

                t_Log.debug(
                      "updatesAreDetected("
                    +     "TYPE_FORWARD_ONLY):"
                    + metaData.updatesAreDetected(
                        ResultSet.TYPE_FORWARD_ONLY));

                t_Log.debug(
                      "updatesAreDetected("
                    +     "TYPE_SCROLL_INSENSITIVE):"
                    + metaData.updatesAreDetected(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.debug(
                      "updatesAreDetected("
                    +     "TYPE_SCROLL_SENS):"
                    + metaData.updatesAreDetected(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                t_Log.debug(
                      "usesLocalFilePerTable():"
                    + metaData.usesLocalFilePerTable());
            
                t_Log.debug(
                      "usesLocalFiles():"
                    + metaData.usesLocalFiles());

                logModel(t_Log, metadataManager);
            }
        }
        catch  (final SQLException sqlException)
        {
            if  (t_Log != null)
            {
                t_Log.error(
                    "Database metadata request failed.",
                    sqlException);
            }
        }

        return result;
    }

    /**
     * Retrieves the database metadata from the attribute map.
     * @param parameters the parameter map.
     * @return the metadata.
     * @throws BuildException if the metadata retrieval process if faulty.
     * @precondition parameters != null
     */
    protected DatabaseMetaData retrieveDatabaseMetaData(
        final Map parameters)
      throws  BuildException
    {
        return
            (DatabaseMetaData)
                parameters.get(
                    DatabaseMetaDataRetrievalHandler.DATABASE_METADATA);
    }

    /**
     * Traces the sql type values.
     * @param log the <code>Log</code> instance.
     * @precondition log != null
     */
    protected void traceSqlTypes(
        final Log log, final SqlTypeResolver resolver)
    {
        log.debug("SQL Type map:");

        log.debug(
            resolver.resolve(Types.BIT) + "->" + Types.BIT);
        log.debug(
            resolver.resolve(Types.TINYINT) + "->" + Types.TINYINT);
        log.debug(
            resolver.resolve(Types.SMALLINT) + "->" + Types.SMALLINT);
        log.debug(
            resolver.resolve(Types.INTEGER) + "->" + Types.INTEGER);
        log.debug(
            resolver.resolve(Types.BIGINT) + "->" + Types.BIGINT);
        log.debug(
            resolver.resolve(Types.FLOAT) + "->" + Types.FLOAT);
        log.debug(
            resolver.resolve(Types.REAL) + "->" + Types.REAL);
        log.debug(
            resolver.resolve(Types.DOUBLE) + "->" + Types.DOUBLE);
        log.debug(
            resolver.resolve(Types.NUMERIC) + "->" + Types.NUMERIC);
        log.debug(
            resolver.resolve(Types.DECIMAL) + "->" + Types.DECIMAL);
        log.debug(
            resolver.resolve(Types.CHAR) + "->" + Types.CHAR);
        log.debug(
            resolver.resolve(Types.VARCHAR) + "->" + Types.VARCHAR);
        log.debug(
            resolver.resolve(Types.LONGVARCHAR) + "->" + Types.LONGVARCHAR);
        log.debug(
            resolver.resolve(Types.DATE) + "->" + Types.DATE);
        log.debug(
            resolver.resolve(Types.TIME) + "->" + Types.TIME);
        log.debug(
            resolver.resolve(Types.TIMESTAMP) + "->" + Types.TIMESTAMP);
        log.debug(
            resolver.resolve(Types.BINARY) + "->" + Types.BINARY);
        log.debug(
            resolver.resolve(Types.VARBINARY) + "->" + Types.VARBINARY);
        log.debug(
            resolver.resolve(Types.LONGVARBINARY) + "->" + Types.LONGVARBINARY);
        log.debug(
            resolver.resolve(Types.NULL) + "->" + Types.NULL);
        log.debug(
            resolver.resolve(Types.OTHER) + "->" + Types.OTHER);
        log.debug(
            resolver.resolve(Types.JAVA_OBJECT) + "->" + Types.JAVA_OBJECT);
        log.debug(
            resolver.resolve(Types.DISTINCT) + "->" + Types.DISTINCT);
        log.debug(
            resolver.resolve(Types.STRUCT) + "->" + Types.STRUCT);
        log.debug(
            resolver.resolve(Types.ARRAY) + "->" + Types.ARRAY);
        log.debug(
            resolver.resolve(Types.BLOB) + "->" + Types.BLOB);
        log.debug(
            resolver.resolve(Types.CLOB) + "->" + Types.CLOB);
        log.debug(
            resolver.resolve(Types.REF) + "->" + Types.REF);
        log.debug(
            resolver.resolve(Types.DATALINK) + "->" + Types.DATALINK);
        log.debug(
            resolver.resolve(Types.BOOLEAN) + "->" + Types.BOOLEAN);

    //------------------------- JDBC 4.0 -----------------------------------
    
        log.debug(
            resolver.resolve(-8) + "->" + -8); //Types.ROWID
        log.debug(
            resolver.resolve(-15) + "->" + -15); //Types.NCHAR
        log.debug(
            resolver.resolve(-9) + "->" + -9); //Types.NVARCHAR
        log.debug(
            resolver.resolve(-16) + "->" + -16); //Types.LONGNVARCHAR
        log.debug(
            resolver.resolve(2011) + "->" + 2011); //Types.NCLOB
        log.debug(
            resolver.resolve(2009) + "->" + 2009); //Types.SQLXML
    }

    /**
     * Traces the model.
     * @param log the <code>Log</code> instance.
     * @param metadataManager the metadata manager.
     * @precondition log != null
     * @precondition metadataManager != null
     */
    protected void logModel(
        final Log log, final MetadataManager metadataManager)
    {
        logModel(
            log,
            metadataManager.getTableNames(),
            metadataManager,
            SqlTypeResolver.getInstance(),
            StringValidator.getInstance(),
            StringUtils.getInstance());
    }

    /**
     * Traces the model.
     * @param log the <code>Log</code> instance.
     * @param tables the tables.
     * @param metadataManager the metadata manager.
     * @param sqlTypeResolver the <code>SqlTypeResolver</code> instance.
     * @param stringValidator the <code>StringValidator</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @precondition log != null
     * @precondition metadataManager != null
     * @precondition sqlTypeResolver != null
     * @precondition stringValidator != null
     * @precondition stringUtils != null
     */
    protected void logModel(
        final Log log,
        final String[] tables,
        final MetadataManager metadataManager,
        final SqlTypeResolver sqlTypeResolver,
        final StringValidator stringValidator,
        final StringUtils stringUtils)
    {
        int t_iTableCount = (tables != null) ? tables.length : 0;

        String t_strTable;

        for  (int t_iIndex = 0; t_iIndex < t_iTableCount; t_iIndex++)
        {
            logTable(
                log,
                tables[t_iIndex],
                metadataManager,
                sqlTypeResolver,
                stringValidator,
                stringUtils);
        }
    }

    /**
     * Traces a concrete table.
     * @param log the <code>Log</code> instance.
     * @param table the table.
     * @param metadataManager the metadata manager.
     * @param sqlTypeResolver the <code>SqlTypeResolver</code> instance.
     * @param stringValidator the <code>StringValidator</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @precondition log != null
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition sqlTypeResolver != null
     * @precondition stringValidator != null
     * @precondition stringUtils != null
     */
    protected void logTable(
        final Log log,
        final String table,
        final MetadataManager metadataManager,
        final SqlTypeResolver sqlTypeResolver,
        final StringValidator stringValidator,
        final StringUtils stringUtils)
    {
        String t_strTableComment = metadataManager.getTableComment(table);

        if  (stringValidator.isEmpty(t_strTableComment))
        {
            log.warn("Table " + table);

            log.warn("  Comment: (Undocumented!)");
        }
        else
        {
            log.debug("Table " + table);

            log.debug("  Comment: " + t_strTableComment);
        }

        String[] t_astrColumnNames = metadataManager.getColumnNames(table);

        int t_iColumnCount =
            (t_astrColumnNames != null) ? t_astrColumnNames.length : 0;

        String[] t_astrExternallyManagedFields =
            metadataManager.getExternallyManagedFields(table);

        for  (int t_iIndex = 0; t_iIndex < t_iColumnCount; t_iIndex++)
        {
            logColumn(
                log,
                t_astrColumnNames[t_iIndex],
                table,
                t_astrExternallyManagedFields,
                metadataManager,
                sqlTypeResolver,
                stringValidator);
        }

        logForeignKeys(
            log,
            table,
            metadataManager,
            stringUtils);
    }

    /**
     * Traces a concrete column.
     * @param log the <code>Log</code> instance.
     * @param columnName the column name.
     * @param table the table.
     * @param externallyManagedFields the externally managed fields for
     * the table.
     * @param metadataManager the metadata manager.
     * @param sqlTypeResolver the <code>SqlTypeResolver</code> instance.
     * @param stringValidator the <code>StringValidator</code> instance.
     * @precondition log != null
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition sqlTypeResolver != null
     * @precondition stringValidator != null
     */
    protected void logColumn(
        final Log log,
        final String columnName,
        final String table,
        final String[] externallyManagedFields,
        final MetadataManager metadataManager,
        final SqlTypeResolver sqlTypeResolver,
        final StringValidator stringValidator)
    {
        String t_strColumnComment =
            metadataManager.getColumnComment(table, columnName);

        boolean t_bPartOfPrimaryKey =
            metadataManager.isPartOfPrimaryKey(table, columnName);
        
        if  (stringValidator.isEmpty(t_strColumnComment))
        {
            if  (t_bPartOfPrimaryKey)
            {
                log.warn("  " + table + "." + columnName + " *");
            }
            else
            {
                log.warn("  " + table + "." + columnName);
            }

            log.warn("    Comment: (Undocumented!)");
        }
        else
        {
            if  (t_bPartOfPrimaryKey)
            {
                log.trace("  " + table + "." + columnName + " *");

                log.trace("    Comment: " + t_strColumnComment);
            }
            else
            {
                log.trace("  " + table + "." + columnName);
            }

        }

        int t_iColumnType = metadataManager.getColumnType(table, columnName);

        log.trace(
              "    Type: "
            + t_iColumnType + "/" + sqlTypeResolver.resolve(t_iColumnType));

        boolean t_bAllowsNull =
            metadataManager.getAllowNull(table, columnName);

        log.trace("    Nullable: " + t_bAllowsNull);

        if  (contains(
                 externallyManagedFields,
                 columnName,
                 metadataManager.isCaseSensitive()))
        {
            log.trace("    [Managed externally]");
        }
    }

    /**
     * Logs the foreign keys.
     * @param log the <code>Log</code> instance.
     * @param table the table.
     * @param metadataManager the metadata manager.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @precondition log != null
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition stringUtils != null
     */
    protected void logForeignKeys(
        final Log log,
        final String table,
        final MetadataManager metadataManager,
        final StringUtils stringUtils)
    {
        String[][] t_aastrForeignKeys =
            metadataManager.getForeignKeys(table);

        int t_iTableCount =
            (t_aastrForeignKeys != null) ? t_aastrForeignKeys.length : 0;

        for  (int t_iTableIndex = 0;
                  t_iTableIndex < t_iTableCount;
                  t_iTableIndex++)
        {
            logForeignKey(
                log,
                table,
                t_aastrForeignKeys[t_iTableIndex],
                metadataManager,
                stringUtils);
        }
    }

    /**
     * Logs the foreign keys.
     * @param log the <code>Log</code> instance.
     * @param table the table.
     * @param foreignKey the foreign key.
     * @param metadataManager the metadata manager.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @precondition log != null
     * @precondition table != null
     * @precondition foreignKey != null
     * @precondition metadataManager != null
     * @precondition stringUtils != null
     */
    protected void logForeignKey(
        final Log log,
        final String table,
        final String[] foreignKey,
        final MetadataManager metadataManager,
        final StringUtils stringUtils)
    {
        if  (log.isInfoEnabled())
        {
            String t_strTargetTable =
                metadataManager.getReferredTable(table, foreignKey);

            Collection t_cFk = Arrays.asList(foreignKey);

            Collection t_cPk =
                Arrays.asList(
                    metadataManager.getPrimaryKey(t_strTargetTable));

            log.trace(
                  "  [" + stringUtils.concatenate(t_cFk, ",")
                + "] -> [" + stringUtils.concatenate(t_cPk, ",") + "]");
        }
    }

    /**
     * Checks whether given array contains a concrete item.
     * @param array the array.
     * @param item the item.
     * @param caseSensitiveness whether case matters.
     * @return <code>true</code> in such case.
     * @precondition array != null
     */
    protected boolean contains(
        final String[] array,
        final String item,
        final boolean caseSensitiveness)
    {
        boolean result = false;

        int t_iCount = (array != null) ? array.length : 0;

        boolean t_bCheckingForNull = (item == null);

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            if  (t_bCheckingForNull)
            {
                result = (array[t_iIndex] == null);
            }
            else
            {
                if  (caseSensitiveness)
                {
                    result = (item.equals(array[t_iIndex]));
                }
                else
                {
                    result = (item.equalsIgnoreCase(array[t_iIndex]));
                }
            }

            if  (result)
            {
                break;
            }
        }

        return result;
    }

    /**
     * Retrieves the relative weight of this handler.
     * @param parameters the parameters.
     * @return a value between <code>MIN_WEIGHT</code>
     * and <code>MAX_WEIGHT</code>.
     */
    public double getRelativeWeight(final Map parameters)
    {
        return DEFAULT_WEIGHT;
    }
}
