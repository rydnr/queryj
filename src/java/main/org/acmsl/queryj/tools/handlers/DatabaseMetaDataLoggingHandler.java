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

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Command;
import org.acmsl.commons.logging.UniqueLogFactory;

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
     */
    public boolean handle(final Command command)
    {
        boolean result = false;

        if  (command instanceof AntCommand) 
        {
            AntCommand t_AntCommand = (AntCommand) command;

            try 
            {
                result = handle(t_AntCommand);
            }
            catch  (final BuildException buildException)
            {
                Log t_Log =
                    UniqueLogFactory.getLog(
                        DatabaseMetaDataLoggingHandler.class);
                
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot log database metadata.",
                        buildException);
                }
            }
        }
        
        return result;
    }

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
                retrieveDatabaseMetaData(parameters));
    }

    /**
     * Handles given information.
     * @param metaData the database metadata.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition metaData != null
     */
    protected boolean handle(final DatabaseMetaData metaData)
        throws  BuildException
    {
        boolean result = false;

        Log t_Log = null;
        
        try 
        {
            t_Log = UniqueLogFactory.getLog(getClass());
            
            if  (t_Log != null)
            {
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
}
