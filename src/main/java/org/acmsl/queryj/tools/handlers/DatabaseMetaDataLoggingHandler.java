//;-*- mode: java -*-
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
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.QueryJCommand;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Command;
import org.acmsl.commons.logging.UniqueLogFactory;

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
import java.util.Map;

/**
 * Prints trace messages with some database metadata information.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class DatabaseMetaDataLoggingHandler
    extends  AbstractQueryJCommandHandler
{
    /**
     * Creates a <code>DatabaseMetaDataLoggingHandler</code> instance.
     */
    public DatabaseMetaDataLoggingHandler() {};

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition project != null
     * @precondition task != null
     */
    protected boolean handle(final Map parameters)
        throws  QueryJBuildException
    {
        return handle(retrieveDatabaseMetaData(parameters));
    }

    /**
     * Handles given information.
     * @param metaData the database metadata.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition metaData != null
     */
    protected boolean handle(final DatabaseMetaData metaData)
        throws  QueryJBuildException
    {
        boolean result = false;

        Log t_Log = null;
        
        try 
        {
            t_Log =
                UniqueLogFactory.getLog(DatabaseMetaDataLoggingHandler.class);
            
            if  (t_Log != null)
            {
                System.out.println(
                      "Numeric functions:"
                    + metaData.getNumericFunctions());

                System.out.println(
                      "String functions:"
                    + metaData.getStringFunctions());

                System.out.println(
                      "System functions:"
                    + metaData.getSystemFunctions());

                System.out.println(
                      "Time functions:"
                    + metaData.getTimeDateFunctions());

                System.out.println(
                      "insertsAreDetected("
                    +     "TYPE_FORWARD_ONLY):"
                    + metaData.insertsAreDetected(
                          ResultSet.TYPE_FORWARD_ONLY));

                System.out.println(
                     "insertsAreDetected("
                    +     "TYPE_SCROLL_INSENSITIVE):"
                    + metaData.insertsAreDetected(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                System.out.println(
                     "insertsAreDetected("
                    +     "TYPE_SCROLL_SENS):"
                    + metaData.insertsAreDetected(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                System.out.println(
                    "isCatalogAtStart():"
                    + metaData.isCatalogAtStart());

                System.out.println(
                      "isReadOnly():"
                    + metaData.isReadOnly());

                /*
                 * Fails for MySQL with a java.lang.AbstractMethodError 
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.locatorsUpdateCopy()
                 System.out.println(
                       "locatorsUpdateCopy():"
                     + metaData.locatorsUpdateCopy());
                 */

                System.out.println(
                      "nullPlusNonNullIsNull():"
                    + metaData.nullPlusNonNullIsNull());

                System.out.println(
                      "nullsAreSortedAtEnd():"
                    + metaData.nullsAreSortedAtEnd());
                
                System.out.println(
                      "nullsAreSortedAtStart():"
                    + metaData.nullsAreSortedAtStart());
                
                System.out.println(
                      "nullsAreSortedHigh():"
                    + metaData.nullsAreSortedHigh());
                
                System.out.println(
                      "nullsAreSortedLow():"
                    + metaData.nullsAreSortedLow());
                
                System.out.println(
                      "othersDeletesAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.othersDeletesAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                System.out.println(
                      "othersDeletesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.othersDeletesAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                System.out.println(
                      "othersDeletesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.othersDeletesAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                System.out.println(
                      "othersInsertsAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.othersInsertsAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                System.out.println(
                      "othersInsertsAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.othersInsertsAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                System.out.println(
                      "othersInsertsAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.othersInsertsAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                System.out.println(
                      "othersUpdatesAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.othersUpdatesAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                System.out.println(
                      "othersUpdatesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.othersUpdatesAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                System.out.println(
                      "othersUpdatesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.othersUpdatesAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                System.out.println(
                      "ownDeletesAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.ownDeletesAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                System.out.println(
                      "ownDeletesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.ownDeletesAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                System.out.println(
                      "ownDeletesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.ownDeletesAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                System.out.println(
                      "ownInsertsAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.ownInsertsAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                System.out.println(
                      "ownInsertsAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.ownInsertsAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                System.out.println(
                      "ownInsertsAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.ownInsertsAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                System.out.println(
                      "ownUpdatesAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.ownUpdatesAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                System.out.println(
                      "ownUpdatesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.ownUpdatesAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                System.out.println(
                      "ownUpdatesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.ownUpdatesAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                System.out.println(
                      "storesLowerCaseIdentifiers():"
                    + metaData.storesLowerCaseIdentifiers());
            
                System.out.println(
                      "storesLowerCaseQuotedIdentifiers():"
                    + metaData.storesLowerCaseQuotedIdentifiers());
            
                System.out.println(
                      "storesMixedCaseIdentifiers():"
                    + metaData.storesMixedCaseIdentifiers());
            
                System.out.println(
                      "storesMixedCaseQuotedIdentifiers():"
                    + metaData.storesMixedCaseQuotedIdentifiers());
            
                System.out.println(
                      "storesUpperCaseIdentifiers():"
                    + metaData.storesUpperCaseIdentifiers());
            
                System.out.println(
                      "storesUpperCaseQuotedIdentifiers():"
                    + metaData.storesUpperCaseQuotedIdentifiers());
            
                System.out.println(
                      "supportsAlterTableWithAddColumn():"
                    + metaData.supportsAlterTableWithAddColumn());
            
                System.out.println(
                      "supportsAlterTableWithDropColumn():"
                    + metaData.supportsAlterTableWithDropColumn());
            
                System.out.println(
                      "supportsANSI92EntryLevelSQL():"
                    + metaData.supportsANSI92EntryLevelSQL());
            
                System.out.println(
                      "supportsANSI92FullSQL():"
                    + metaData.supportsANSI92FullSQL());
            
                System.out.println(
                      "supportsANSI92IntermediateSQL():"
                    + metaData.supportsANSI92IntermediateSQL());
            
                System.out.println(
                      "supportsBatchUpdates():"
                    + metaData.supportsBatchUpdates());
            
                System.out.println(
                      "supportsCatalogsInDataManipulation():"
                    + metaData.supportsCatalogsInDataManipulation());
            
                System.out.println(
                      "supportsCatalogsInIndexDefinitions():"
                    + metaData.supportsCatalogsInIndexDefinitions());
            
                System.out.println(
                      "supportsCatalogsInPrivilegeDefinitions():"
                    + metaData.supportsCatalogsInPrivilegeDefinitions());
            
                System.out.println(
                      "supportsCatalogsInProcedureCalls():"
                    + metaData.supportsCatalogsInProcedureCalls());
            
                System.out.println(
                      "supportsCatalogsInTableDefinitions():"
                    + metaData.supportsCatalogsInTableDefinitions());
            
                System.out.println(
                      "supportsColumnAliasing():"
                    + metaData.supportsColumnAliasing());
            
                System.out.println(
                      "supportsConvert():"
                    + metaData.supportsConvert());
            
                System.out.println(
                      "supportsCoreSQLGrammar():"
                    + metaData.supportsCoreSQLGrammar());
            
                System.out.println(
                      "supportsCorrelatedSubqueries():"
                    + metaData.supportsCorrelatedSubqueries());
            
                System.out.println(
                      "supportsDataDefinitionAndDataManipulationTransactions():"
                    + metaData.supportsDataDefinitionAndDataManipulationTransactions());
            
                System.out.println(
                      "supportsDataManipulationTransactionsOnly():"
                    + metaData.supportsDataManipulationTransactionsOnly());
            
                System.out.println(
                      "supportsDifferentTableCorrelationNames():"
                    + metaData.supportsDifferentTableCorrelationNames());
            
                System.out.println(
                      "supportsExpressionsInOrderBy():"
                    + metaData.supportsExpressionsInOrderBy());
            
                System.out.println(
                      "supportsExtendedSQLGrammar():"
                    + metaData.supportsExtendedSQLGrammar());
            
                System.out.println(
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

                System.out.println(
                      "supportsGetGeneratedKeys():"
                    + t_strSupportsGetGeneratedKeys);
            
                System.out.println(
                      "supportsGroupBy():"
                    + metaData.supportsGroupBy());
            
                System.out.println(
                      "supportsGroupByBeyondSelect():"
                    + metaData.supportsGroupByBeyondSelect());
            
                System.out.println(
                      "supportsGroupByUnrelated():"
                    + metaData.supportsGroupByUnrelated());
            
                System.out.println(
                      "supportsIntegrityEnhancementFacility():"
                    + metaData.supportsIntegrityEnhancementFacility());
            
                System.out.println(
                      "supportsLikeEscapeClause():"
                    + metaData.supportsLikeEscapeClause());
            
                System.out.println(
                      "supportsLimitedOuterJoins():"
                    + metaData.supportsLimitedOuterJoins());
            
                System.out.println(
                      "supportsMinimumSQLGrammar():"
                    + metaData.supportsMinimumSQLGrammar());
            
                System.out.println(
                      "supportsMixedCaseIdentifiers():"
                    + metaData.supportsMixedCaseIdentifiers());
            
                System.out.println(
                      "supportsMixedCaseQuotedIdentifiers():"
                    + metaData.supportsMixedCaseQuotedIdentifiers());
            
                /*
                 * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsMultipleOpenResults()
                 System.out.println(
                       "supportsMultipleOpenResults():"
                     + metaData.supportsMultipleOpenResults());
                 */

                System.out.println(
                      "supportsMultipleResultSets():"
                    + metaData.supportsMultipleResultSets());
            
                System.out.println(
                      "supportsMultipleTransactions():"
                    + metaData.supportsMultipleTransactions());
            
                /*
                 * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsNamedParameters()
                 System.out.println(
                       "supportsNamedParameters():"
                     + metaData.supportsNamedParameters());
                 */

                System.out.println(
                      "supportsNonNullableColumns():"
                    + metaData.supportsNonNullableColumns());
            
                System.out.println(
                      "supportsOpenCursorsAcrossCommit():"
                    + metaData.supportsOpenCursorsAcrossCommit());
            
                System.out.println(
                      "supportsOpenCursorsAcrossRollback():"
                    + metaData.supportsOpenCursorsAcrossRollback());
            
                System.out.println(
                      "supportsOpenStatementsAcrossCommit():"
                    + metaData.supportsOpenStatementsAcrossCommit());
            
                System.out.println(
                      "supportsOpenStatementsAcrossRollback():"
                    + metaData.supportsOpenStatementsAcrossRollback());
            
                System.out.println(
                      "supportsOrderByUnrelated():"
                    + metaData.supportsOrderByUnrelated());
            
                System.out.println(
                      "supportsOuterJoins():"
                    + metaData.supportsOuterJoins());
            
                System.out.println(
                      "supportsPositionedDelete():"
                    + metaData.supportsPositionedDelete());
            
                System.out.println(
                      "supportsPositionedUpdate():"
                    + metaData.supportsPositionedUpdate());
            
                System.out.println(
                      "supportsResultSetConcurrency("
                    +     "TYPE_FORWARD_ONLY,CONCUR_READ_ONLY):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_READ_ONLY));

                System.out.println(
                      "supportsResultSetConcurrency("
                    +     "TYPE_FORWARD_ONLY,CONCUR_UPDATABLE):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE));

                System.out.println(
                      "supportsResultSetConcurrency("
                    +     "TYPE_SCROLL_INSENSITIVE,CONCUR_READ_ONLY):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY));

                System.out.println(
                      "supportsResultSetConcurrency("
                    +     "TYPE_SCROLL_INSENSITIVE,CONCUR_UPDATABLE):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE));

                System.out.println(
                      "supportsResultSetConcurrency("
                    +     "TYPE_SCROLL_SENSITIVE,CONCUR_READ_ONLY):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_READ_ONLY));

                System.out.println(
                      "supportsResultSetConcurrency("
                    +     "TYPE_SCROLL_SENSITIVE,CONCUR_UPDATABLE):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE));

                /*
                 * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsResultSetHoldability()
                 System.out.println(
                       "supportsResultSetHoldability("
                 +     "HOLD_CURSORS_OVER_COMMIT):"
                 + metaData.supportsResultSetHoldability(
                     ResultSet.HOLD_CURSORS_OVER_COMMIT));

                 System.out.println(
                       "supportsResultSetHoldability("
                 +     "CLOSE_CURSORS_AT_COMMIT):"
                 + metaData.supportsResultSetHoldability(
                     ResultSet.CLOSE_CURSORS_AT_COMMIT));
                 */

                System.out.println(
                      "supportsResultSetType("
                    +     "TYPE_FORWARD_ONLY):"
                    + metaData.supportsResultSetType(
                        ResultSet.TYPE_FORWARD_ONLY));

                System.out.println(
                      "supportsResultSetType("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.supportsResultSetType(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                System.out.println(
                      "supportsResultSetType("
                    +     "TYPE_SCROLL_SENSITIVE):"
                    + metaData.supportsResultSetType(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                /*
                 * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsSavePoints()
                 System.out.println(
                       "supportsSavepoints():"
                     + metaData.supportsSavepoints());
                 */

                System.out.println(
                      "supportsSchemasInDataManipulation():"
                    + metaData.supportsSchemasInDataManipulation());
            
                System.out.println(
                      "supportsSchemasInIndexDefinitions():"
                    + metaData.supportsSchemasInIndexDefinitions());
            
                System.out.println(
                      "supportsSchemasInPrivilegeDefinitions():"
                    + metaData.supportsSchemasInPrivilegeDefinitions());
            
                System.out.println(
                      "supportsSchemasInProcedureCalls():"
                    + metaData.supportsSchemasInProcedureCalls());
            
                System.out.println(
                      "supportsSchemasInTableDefinitions():"
                    + metaData.supportsSchemasInTableDefinitions());
            
                System.out.println(
                      "supportsSelectForUpdate():"
                    + metaData.supportsSelectForUpdate());

                /*
                 * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsStatementPooling()
                 System.out.println(
                       "supportsStatementPooling():"
                     + metaData.supportsStatementPooling());
                */

                System.out.println(
                      "supportsStoredProcedures():"
                    + metaData.supportsStoredProcedures());
            
                System.out.println(
                      "supportsSubqueriesInComparisons():"
                    + metaData.supportsSubqueriesInComparisons());
            
                System.out.println(
                      "supportsSubqueriesInExists():"
                    + metaData.supportsSubqueriesInExists());
            
                System.out.println(
                      "supportsSubqueriesInIns():"
                    + metaData.supportsSubqueriesInIns());
            
                System.out.println(
                      "supportsSubqueriesInQuantifieds():"
                    + metaData.supportsSubqueriesInQuantifieds());
            
                System.out.println(
                      "supportsTableCorrelationNames():"
                    + metaData.supportsTableCorrelationNames());
            
                System.out.println(
                      "supportsTransactionIsolationLevel("
                    +     "TRANSACTION_NONE):"
                    + metaData.supportsTransactionIsolationLevel(
                        Connection.TRANSACTION_NONE));

                System.out.println(
                      "supportsTransactionIsolationLevel("
                    +     "TRANSACTION_READ_COMMITTED):"
                    + metaData.supportsTransactionIsolationLevel(
                        Connection.TRANSACTION_READ_COMMITTED));

                System.out.println(
                      "supportsTransactionIsolationLevel("
                    +     "TRANSACTION_READ_UNCOMMITTED):"
                    + metaData.supportsTransactionIsolationLevel(
                        Connection.TRANSACTION_READ_UNCOMMITTED));

                System.out.println(
                      "supportsTransactionIsolationLevel("
                    +     "TRANSACTION_REPEATABLE_READ):"
                    + metaData.supportsTransactionIsolationLevel(
                        Connection.TRANSACTION_REPEATABLE_READ));

                System.out.println(
                      "supportsTransactionIsolationLevel("
                    +     "TRANSACTION_SERIALIZABLE):"
                    + metaData.supportsTransactionIsolationLevel(
                        Connection.TRANSACTION_SERIALIZABLE));

                System.out.println(
                      "supportsTransactions():"
                    + metaData.supportsTransactions());
            
                System.out.println(
                      "supportsUnion():"
                    + metaData.supportsUnion());
            
                System.out.println(
                      "supportsUnionAll():"
                    + metaData.supportsUnionAll());

                System.out.println(
                      "updatesAreDetected("
                    +     "TYPE_FORWARD_ONLY):"
                    + metaData.updatesAreDetected(
                        ResultSet.TYPE_FORWARD_ONLY));

                System.out.println(
                      "updatesAreDetected("
                    +     "TYPE_SCROLL_INSENSITIVE):"
                    + metaData.updatesAreDetected(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                System.out.println(
                      "updatesAreDetected("
                    +     "TYPE_SCROLL_SENS):"
                    + metaData.updatesAreDetected(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                System.out.println(
                      "usesLocalFilePerTable():"
                    + metaData.usesLocalFilePerTable());
            
                System.out.println(
                      "usesLocalFiles():"
                    + metaData.usesLocalFiles());
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
}
