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

                t_Log.trace(
                      "Numeric functions:"
                    + metaData.getNumericFunctions());

                t_Log.trace(
                      "String functions:"
                    + metaData.getStringFunctions());

                t_Log.trace(
                      "System functions:"
                    + metaData.getSystemFunctions());

                t_Log.trace(
                      "Time functions:"
                    + metaData.getTimeDateFunctions());

                t_Log.trace(
                      "insertsAreDetected("
                    +     "TYPE_FORWARD_ONLY):"
                    + metaData.insertsAreDetected(
                          ResultSet.TYPE_FORWARD_ONLY));

                t_Log.trace(
                     "insertsAreDetected("
                    +     "TYPE_SCROLL_INSENSITIVE):"
                    + metaData.insertsAreDetected(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.trace(
                     "insertsAreDetected("
                    +     "TYPE_SCROLL_SENS):"
                    + metaData.insertsAreDetected(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                t_Log.trace(
                    "isCatalogAtStart():"
                    + metaData.isCatalogAtStart());

                t_Log.trace(
                      "isReadOnly():"
                    + metaData.isReadOnly());

                /*
                 * Fails for MySQL with a java.lang.AbstractMethodError 
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.locatorsUpdateCopy()
                 t_Log.trace(
                       "locatorsUpdateCopy():"
                     + metaData.locatorsUpdateCopy());
                 */

                t_Log.trace(
                      "nullPlusNonNullIsNull():"
                    + metaData.nullPlusNonNullIsNull());

                t_Log.trace(
                      "nullsAreSortedAtEnd():"
                    + metaData.nullsAreSortedAtEnd());
                
                t_Log.trace(
                      "nullsAreSortedAtStart():"
                    + metaData.nullsAreSortedAtStart());
                
                t_Log.trace(
                      "nullsAreSortedHigh():"
                    + metaData.nullsAreSortedHigh());
                
                t_Log.trace(
                      "nullsAreSortedLow():"
                    + metaData.nullsAreSortedLow());
                
                t_Log.trace(
                      "othersDeletesAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.othersDeletesAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                t_Log.trace(
                      "othersDeletesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.othersDeletesAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.trace(
                      "othersDeletesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.othersDeletesAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                t_Log.trace(
                      "othersInsertsAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.othersInsertsAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                t_Log.trace(
                      "othersInsertsAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.othersInsertsAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.trace(
                      "othersInsertsAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.othersInsertsAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                t_Log.trace(
                      "othersUpdatesAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.othersUpdatesAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                t_Log.trace(
                      "othersUpdatesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.othersUpdatesAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.trace(
                      "othersUpdatesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.othersUpdatesAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                t_Log.trace(
                      "ownDeletesAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.ownDeletesAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                t_Log.trace(
                      "ownDeletesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.ownDeletesAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.trace(
                      "ownDeletesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.ownDeletesAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                t_Log.trace(
                      "ownInsertsAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.ownInsertsAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                t_Log.trace(
                      "ownInsertsAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.ownInsertsAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.trace(
                      "ownInsertsAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.ownInsertsAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                t_Log.trace(
                      "ownUpdatesAreVisible("
                    +     "ResultSet.TYPE_FORWARD_ONLY):"
                    + metaData.ownUpdatesAreVisible(
                        ResultSet.TYPE_FORWARD_ONLY));

                t_Log.trace(
                      "ownUpdatesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.ownUpdatesAreVisible(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.trace(
                      "ownUpdatesAreVisible("
                    +     "ResultSet.TYPE_SCROLL_SENS):"
                    + metaData.ownUpdatesAreVisible(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                t_Log.trace(
                      "storesLowerCaseIdentifiers():"
                    + metaData.storesLowerCaseIdentifiers());
            
                t_Log.trace(
                      "storesLowerCaseQuotedIdentifiers():"
                    + metaData.storesLowerCaseQuotedIdentifiers());
            
                t_Log.trace(
                      "storesMixedCaseIdentifiers():"
                    + metaData.storesMixedCaseIdentifiers());
            
                t_Log.trace(
                      "storesMixedCaseQuotedIdentifiers():"
                    + metaData.storesMixedCaseQuotedIdentifiers());
            
                t_Log.trace(
                      "storesUpperCaseIdentifiers():"
                    + metaData.storesUpperCaseIdentifiers());
            
                t_Log.trace(
                      "storesUpperCaseQuotedIdentifiers():"
                    + metaData.storesUpperCaseQuotedIdentifiers());
            
                t_Log.trace(
                      "supportsAlterTableWithAddColumn():"
                    + metaData.supportsAlterTableWithAddColumn());
            
                t_Log.trace(
                      "supportsAlterTableWithDropColumn():"
                    + metaData.supportsAlterTableWithDropColumn());
            
                t_Log.trace(
                      "supportsANSI92EntryLevelSQL():"
                    + metaData.supportsANSI92EntryLevelSQL());
            
                t_Log.trace(
                      "supportsANSI92FullSQL():"
                    + metaData.supportsANSI92FullSQL());
            
                t_Log.trace(
                      "supportsANSI92IntermediateSQL():"
                    + metaData.supportsANSI92IntermediateSQL());
            
                t_Log.trace(
                      "supportsBatchUpdates():"
                    + metaData.supportsBatchUpdates());
            
                t_Log.trace(
                      "supportsCatalogsInDataManipulation():"
                    + metaData.supportsCatalogsInDataManipulation());
            
                t_Log.trace(
                      "supportsCatalogsInIndexDefinitions():"
                    + metaData.supportsCatalogsInIndexDefinitions());
            
                t_Log.trace(
                      "supportsCatalogsInPrivilegeDefinitions():"
                    + metaData.supportsCatalogsInPrivilegeDefinitions());
            
                t_Log.trace(
                      "supportsCatalogsInProcedureCalls():"
                    + metaData.supportsCatalogsInProcedureCalls());
            
                t_Log.trace(
                      "supportsCatalogsInTableDefinitions():"
                    + metaData.supportsCatalogsInTableDefinitions());
            
                t_Log.trace(
                      "supportsColumnAliasing():"
                    + metaData.supportsColumnAliasing());
            
                t_Log.trace(
                      "supportsConvert():"
                    + metaData.supportsConvert());
            
                t_Log.trace(
                      "supportsCoreSQLGrammar():"
                    + metaData.supportsCoreSQLGrammar());
            
                t_Log.trace(
                      "supportsCorrelatedSubqueries():"
                    + metaData.supportsCorrelatedSubqueries());
            
                t_Log.trace(
                      "supportsDataDefinitionAndDataManipulationTransactions():"
                    + metaData.supportsDataDefinitionAndDataManipulationTransactions());
            
                t_Log.trace(
                      "supportsDataManipulationTransactionsOnly():"
                    + metaData.supportsDataManipulationTransactionsOnly());
            
                t_Log.trace(
                      "supportsDifferentTableCorrelationNames():"
                    + metaData.supportsDifferentTableCorrelationNames());
            
                t_Log.trace(
                      "supportsExpressionsInOrderBy():"
                    + metaData.supportsExpressionsInOrderBy());
            
                t_Log.trace(
                      "supportsExtendedSQLGrammar():"
                    + metaData.supportsExtendedSQLGrammar());
            
                t_Log.trace(
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

                t_Log.trace(
                      "supportsGetGeneratedKeys():"
                    + t_strSupportsGetGeneratedKeys);
            
                t_Log.trace(
                      "supportsGroupBy():"
                    + metaData.supportsGroupBy());
            
                t_Log.trace(
                      "supportsGroupByBeyondSelect():"
                    + metaData.supportsGroupByBeyondSelect());
            
                t_Log.trace(
                      "supportsGroupByUnrelated():"
                    + metaData.supportsGroupByUnrelated());
            
                t_Log.trace(
                      "supportsIntegrityEnhancementFacility():"
                    + metaData.supportsIntegrityEnhancementFacility());
            
                t_Log.trace(
                      "supportsLikeEscapeClause():"
                    + metaData.supportsLikeEscapeClause());
            
                t_Log.trace(
                      "supportsLimitedOuterJoins():"
                    + metaData.supportsLimitedOuterJoins());
            
                t_Log.trace(
                      "supportsMinimumSQLGrammar():"
                    + metaData.supportsMinimumSQLGrammar());
            
                t_Log.trace(
                      "supportsMixedCaseIdentifiers():"
                    + metaData.supportsMixedCaseIdentifiers());
            
                t_Log.trace(
                      "supportsMixedCaseQuotedIdentifiers():"
                    + metaData.supportsMixedCaseQuotedIdentifiers());
            
                /*
                 * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsMultipleOpenResults()
                 t_Log.trace(
                       "supportsMultipleOpenResults():"
                     + metaData.supportsMultipleOpenResults());
                 */

                t_Log.trace(
                      "supportsMultipleResultSets():"
                    + metaData.supportsMultipleResultSets());
            
                t_Log.trace(
                      "supportsMultipleTransactions():"
                    + metaData.supportsMultipleTransactions());
            
                /*
                 * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsNamedParameters()
                 t_Log.trace(
                       "supportsNamedParameters():"
                     + metaData.supportsNamedParameters());
                 */

                t_Log.trace(
                      "supportsNonNullableColumns():"
                    + metaData.supportsNonNullableColumns());
            
                t_Log.trace(
                      "supportsOpenCursorsAcrossCommit():"
                    + metaData.supportsOpenCursorsAcrossCommit());
            
                t_Log.trace(
                      "supportsOpenCursorsAcrossRollback():"
                    + metaData.supportsOpenCursorsAcrossRollback());
            
                t_Log.trace(
                      "supportsOpenStatementsAcrossCommit():"
                    + metaData.supportsOpenStatementsAcrossCommit());
            
                t_Log.trace(
                      "supportsOpenStatementsAcrossRollback():"
                    + metaData.supportsOpenStatementsAcrossRollback());
            
                t_Log.trace(
                      "supportsOrderByUnrelated():"
                    + metaData.supportsOrderByUnrelated());
            
                t_Log.trace(
                      "supportsOuterJoins():"
                    + metaData.supportsOuterJoins());
            
                t_Log.trace(
                      "supportsPositionedDelete():"
                    + metaData.supportsPositionedDelete());
            
                t_Log.trace(
                      "supportsPositionedUpdate():"
                    + metaData.supportsPositionedUpdate());
            
                t_Log.trace(
                      "supportsResultSetConcurrency("
                    +     "TYPE_FORWARD_ONLY,CONCUR_READ_ONLY):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_READ_ONLY));

                t_Log.trace(
                      "supportsResultSetConcurrency("
                    +     "TYPE_FORWARD_ONLY,CONCUR_UPDATABLE):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE));

                t_Log.trace(
                      "supportsResultSetConcurrency("
                    +     "TYPE_SCROLL_INSENSITIVE,CONCUR_READ_ONLY):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY));

                t_Log.trace(
                      "supportsResultSetConcurrency("
                    +     "TYPE_SCROLL_INSENSITIVE,CONCUR_UPDATABLE):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE));

                t_Log.trace(
                      "supportsResultSetConcurrency("
                    +     "TYPE_SCROLL_SENSITIVE,CONCUR_READ_ONLY):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_READ_ONLY));

                t_Log.trace(
                      "supportsResultSetConcurrency("
                    +     "TYPE_SCROLL_SENSITIVE,CONCUR_UPDATABLE):"
                    + metaData.supportsResultSetConcurrency(
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE));

                /*
                 * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsResultSetHoldability()
                 t_Log.trace(
                       "supportsResultSetHoldability("
                 +     "HOLD_CURSORS_OVER_COMMIT):"
                 + metaData.supportsResultSetHoldability(
                     ResultSet.HOLD_CURSORS_OVER_COMMIT));

                 t_Log.trace(
                       "supportsResultSetHoldability("
                 +     "CLOSE_CURSORS_AT_COMMIT):"
                 + metaData.supportsResultSetHoldability(
                     ResultSet.CLOSE_CURSORS_AT_COMMIT));
                 */

                t_Log.trace(
                      "supportsResultSetType("
                    +     "TYPE_FORWARD_ONLY):"
                    + metaData.supportsResultSetType(
                        ResultSet.TYPE_FORWARD_ONLY));

                t_Log.trace(
                      "supportsResultSetType("
                    +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                    + metaData.supportsResultSetType(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.trace(
                      "supportsResultSetType("
                    +     "TYPE_SCROLL_SENSITIVE):"
                    + metaData.supportsResultSetType(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                /*
                 * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsSavePoints()
                 t_Log.trace(
                       "supportsSavepoints():"
                     + metaData.supportsSavepoints());
                 */

                t_Log.trace(
                      "supportsSchemasInDataManipulation():"
                    + metaData.supportsSchemasInDataManipulation());
            
                t_Log.trace(
                      "supportsSchemasInIndexDefinitions():"
                    + metaData.supportsSchemasInIndexDefinitions());
            
                t_Log.trace(
                      "supportsSchemasInPrivilegeDefinitions():"
                    + metaData.supportsSchemasInPrivilegeDefinitions());
            
                t_Log.trace(
                      "supportsSchemasInProcedureCalls():"
                    + metaData.supportsSchemasInProcedureCalls());
            
                t_Log.trace(
                      "supportsSchemasInTableDefinitions():"
                    + metaData.supportsSchemasInTableDefinitions());
            
                t_Log.trace(
                      "supportsSelectForUpdate():"
                    + metaData.supportsSelectForUpdate());

                /*
                 * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                 * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsStatementPooling()
                 t_Log.trace(
                       "supportsStatementPooling():"
                     + metaData.supportsStatementPooling());
                */

                t_Log.trace(
                      "supportsStoredProcedures():"
                    + metaData.supportsStoredProcedures());
            
                t_Log.trace(
                      "supportsSubqueriesInComparisons():"
                    + metaData.supportsSubqueriesInComparisons());
            
                t_Log.trace(
                      "supportsSubqueriesInExists():"
                    + metaData.supportsSubqueriesInExists());
            
                t_Log.trace(
                      "supportsSubqueriesInIns():"
                    + metaData.supportsSubqueriesInIns());
            
                t_Log.trace(
                      "supportsSubqueriesInQuantifieds():"
                    + metaData.supportsSubqueriesInQuantifieds());
            
                t_Log.trace(
                      "supportsTableCorrelationNames():"
                    + metaData.supportsTableCorrelationNames());
            
                t_Log.trace(
                      "supportsTransactionIsolationLevel("
                    +     "TRANSACTION_NONE):"
                    + metaData.supportsTransactionIsolationLevel(
                        Connection.TRANSACTION_NONE));

                t_Log.trace(
                      "supportsTransactionIsolationLevel("
                    +     "TRANSACTION_READ_COMMITTED):"
                    + metaData.supportsTransactionIsolationLevel(
                        Connection.TRANSACTION_READ_COMMITTED));

                t_Log.trace(
                      "supportsTransactionIsolationLevel("
                    +     "TRANSACTION_READ_UNCOMMITTED):"
                    + metaData.supportsTransactionIsolationLevel(
                        Connection.TRANSACTION_READ_UNCOMMITTED));

                t_Log.trace(
                      "supportsTransactionIsolationLevel("
                    +     "TRANSACTION_REPEATABLE_READ):"
                    + metaData.supportsTransactionIsolationLevel(
                        Connection.TRANSACTION_REPEATABLE_READ));

                t_Log.trace(
                      "supportsTransactionIsolationLevel("
                    +     "TRANSACTION_SERIALIZABLE):"
                    + metaData.supportsTransactionIsolationLevel(
                        Connection.TRANSACTION_SERIALIZABLE));

                t_Log.trace(
                      "supportsTransactions():"
                    + metaData.supportsTransactions());
            
                t_Log.trace(
                      "supportsUnion():"
                    + metaData.supportsUnion());
            
                t_Log.trace(
                      "supportsUnionAll():"
                    + metaData.supportsUnionAll());

                t_Log.trace(
                      "updatesAreDetected("
                    +     "TYPE_FORWARD_ONLY):"
                    + metaData.updatesAreDetected(
                        ResultSet.TYPE_FORWARD_ONLY));

                t_Log.trace(
                      "updatesAreDetected("
                    +     "TYPE_SCROLL_INSENSITIVE):"
                    + metaData.updatesAreDetected(
                        ResultSet.TYPE_SCROLL_INSENSITIVE));

                t_Log.trace(
                      "updatesAreDetected("
                    +     "TYPE_SCROLL_SENS):"
                    + metaData.updatesAreDetected(
                        ResultSet.TYPE_SCROLL_SENSITIVE));

                t_Log.trace(
                      "usesLocalFilePerTable():"
                    + metaData.usesLocalFilePerTable());
            
                t_Log.trace(
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
        log.trace("SQL Type map:");

        log.trace(
            resolver.resolve(Types.BIT) + "->" + Types.BIT);
        log.trace(
            resolver.resolve(Types.TINYINT) + "->" + Types.TINYINT);
        log.trace(
            resolver.resolve(Types.SMALLINT) + "->" + Types.SMALLINT);
        log.trace(
            resolver.resolve(Types.INTEGER) + "->" + Types.INTEGER);
        log.trace(
            resolver.resolve(Types.BIGINT) + "->" + Types.BIGINT);
        log.trace(
            resolver.resolve(Types.FLOAT) + "->" + Types.FLOAT);
        log.trace(
            resolver.resolve(Types.REAL) + "->" + Types.REAL);
        log.trace(
            resolver.resolve(Types.DOUBLE) + "->" + Types.DOUBLE);
        log.trace(
            resolver.resolve(Types.NUMERIC) + "->" + Types.NUMERIC);
        log.trace(
            resolver.resolve(Types.DECIMAL) + "->" + Types.DECIMAL);
        log.trace(
            resolver.resolve(Types.CHAR) + "->" + Types.CHAR);
        log.trace(
            resolver.resolve(Types.VARCHAR) + "->" + Types.VARCHAR);
        log.trace(
            resolver.resolve(Types.LONGVARCHAR) + "->" + Types.LONGVARCHAR);
        log.trace(
            resolver.resolve(Types.DATE) + "->" + Types.DATE);
        log.trace(
            resolver.resolve(Types.TIME) + "->" + Types.TIME);
        log.trace(
            resolver.resolve(Types.TIMESTAMP) + "->" + Types.TIMESTAMP);
        log.trace(
            resolver.resolve(Types.BINARY) + "->" + Types.BINARY);
        log.trace(
            resolver.resolve(Types.VARBINARY) + "->" + Types.VARBINARY);
        log.trace(
            resolver.resolve(Types.LONGVARBINARY) + "->" + Types.LONGVARBINARY);
        log.trace(
            resolver.resolve(Types.NULL) + "->" + Types.NULL);
        log.trace(
            resolver.resolve(Types.OTHER) + "->" + Types.OTHER);
        log.trace(
            resolver.resolve(Types.JAVA_OBJECT) + "->" + Types.JAVA_OBJECT);
        log.trace(
            resolver.resolve(Types.DISTINCT) + "->" + Types.DISTINCT);
        log.trace(
            resolver.resolve(Types.STRUCT) + "->" + Types.STRUCT);
        log.trace(
            resolver.resolve(Types.ARRAY) + "->" + Types.ARRAY);
        log.trace(
            resolver.resolve(Types.BLOB) + "->" + Types.BLOB);
        log.trace(
            resolver.resolve(Types.CLOB) + "->" + Types.CLOB);
        log.trace(
            resolver.resolve(Types.REF) + "->" + Types.REF);
        log.trace(
            resolver.resolve(Types.DATALINK) + "->" + Types.DATALINK);
        log.trace(
            resolver.resolve(Types.BOOLEAN) + "->" + Types.BOOLEAN);

    //------------------------- JDBC 4.0 -----------------------------------
    
        log.trace(
            resolver.resolve(-8) + "->" + -8); //Types.ROWID
        log.trace(
            resolver.resolve(-15) + "->" + -15); //Types.NCHAR
        log.trace(
            resolver.resolve(-9) + "->" + -9); //Types.NVARCHAR
        log.trace(
            resolver.resolve(-16) + "->" + -16); //Types.LONGNVARCHAR
        log.trace(
            resolver.resolve(2011) + "->" + 2011); //Types.NCLOB
        log.trace(
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

        log.info("Table " + table);

        if  (stringValidator.isEmpty(t_strTableComment))
        {

            log.warn("  Comment: (Undocumented!)");
        }
        else
        {
            log.info("  Comment: " + t_strTableComment);
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

        if  (metadataManager.isPartOfPrimaryKey(table, columnName))
        {
            log.info("  " + table + "." + columnName + " *");
        }
        else
        {
            log.info("  " + table + "." + columnName);
        }

        if  (stringValidator.isEmpty(t_strColumnComment))
        {
            log.warn("    Comment: (Undocumented!)");
        }
        else
        {
            log.info("    Comment: " + t_strColumnComment);
        }

        int t_iColumnType = metadataManager.getColumnType(table, columnName);

        log.info(
              "    Type: "
            + t_iColumnType + "/" + sqlTypeResolver.resolve(t_iColumnType));

        boolean t_bAllowsNull =
            metadataManager.getAllowNull(table, columnName);

        log.info("    Nullable: " + t_bAllowsNull);

        if  (contains(
                 externallyManagedFields,
                 columnName,
                 metadataManager.isCaseSensitive()))
        {
            log.info("    [Managed externally]");
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

            log.info(
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
}
