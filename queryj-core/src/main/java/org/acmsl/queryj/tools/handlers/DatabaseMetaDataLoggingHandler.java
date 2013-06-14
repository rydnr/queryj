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
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

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
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Prints trace messages with some database metadata information.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class DatabaseMetaDataLoggingHandler
    extends  AbstractQueryJCommandHandler
{
    /**
     * Creates a {@link DatabaseMetaDataLoggingHandler} instance.
     */
    public DatabaseMetaDataLoggingHandler() {}

    /**
     * Handles given information.
     *
     *
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected boolean handle(@NotNull final Map<String, ?> parameters)
        throws  QueryJBuildException
    {
        return handle(retrieveDatabaseMetaData(parameters));
    }

    /**
     * Handles given information.
     * @param metaData the database metadata.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected boolean handle(@NotNull final DatabaseMetaData metaData)
        throws  QueryJBuildException
    {
        final boolean result = false;

        @Nullable Log t_Log = null;
        
        try 
        {
            t_Log =
                UniqueLogFactory.getLog(DatabaseMetaDataLoggingHandler.class);
            
            if  (t_Log != null)
            {
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
                catch  (@NotNull final SQLException sqlException)
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
            }
        }
        catch  (@NotNull final SQLException sqlException)
        {
            t_Log.error("Database metadata request failed.", sqlException);
        }

        return result;
    }
}
