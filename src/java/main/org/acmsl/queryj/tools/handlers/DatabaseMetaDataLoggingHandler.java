/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile$
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
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

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
           >Jose San Leandro</a>
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
                Project project = t_AntCommand.getProject();

                if  (project != null)
                {
                    project.log(
                        t_AntCommand.getTask(),
                        buildException.getMessage(),
                        Project.MSG_ERR);
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
        return
            handle(
                command.getAttributeMap(),
                command.getProject(),
                command.getTask());
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition project != null
     * @precondition task != null
     */
    protected boolean handle(
        final Map parameters, final Project project, final Task task)
        throws  BuildException
    {
        return
            handle(
                retrieveDatabaseMetaData(parameters),
                project,
                task);
    }

    /**
     * Handles given information.
     * @param metaData the database metadata.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition metaData != null
     * @precondition project != null
     * @precondition task != null
     */
    protected boolean handle(
        final DatabaseMetaData metaData,
        final Project project,
        final Task task)
      throws  BuildException
    {
        boolean result = false;

        try 
        {
            project.log(
                task,
                "Numeric functions:"
                + metaData.getNumericFunctions(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "String functions:"
                + metaData.getStringFunctions(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "System functions:"
                + metaData.getSystemFunctions(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "Time functions:" + metaData.getTimeDateFunctions(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "insertsAreDetected("
                +     "TYPE_FORWARD_ONLY):"
                + metaData.insertsAreDetected(
                    ResultSet.TYPE_FORWARD_ONLY),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "insertsAreDetected("
                +     "TYPE_SCROLL_INSENSITIVE):"
                + metaData.insertsAreDetected(
                    ResultSet.TYPE_SCROLL_INSENSITIVE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "insertsAreDetected("
                +     "TYPE_SCROLL_SENS):"
                + metaData.insertsAreDetected(
                    ResultSet.TYPE_SCROLL_SENSITIVE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "isCatalogAtStart():"
                + metaData.isCatalogAtStart(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "isReadOnly():"
                + metaData.isReadOnly(),
                Project.MSG_VERBOSE);

            /*
             * Fails for MySQL with a java.lang.AbstractMethodError 
             * com.mysql.jdbc.jdbc2.DatabaseMetaData.locatorsUpdateCopy()
             project.log(
             task,
             "locatorsUpdateCopy():"
             + metaData.locatorsUpdateCopy(),
             Project.MSG_VERBOSE);
            */

            project.log(
                task,
                "nullPlusNonNullIsNull():"
                + metaData.nullPlusNonNullIsNull(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "nullsAreSortedAtEnd():"
                + metaData.nullsAreSortedAtEnd(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "nullsAreSortedAtStart():"
                + metaData.nullsAreSortedAtStart(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "nullsAreSortedHigh():"
                + metaData.nullsAreSortedHigh(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "nullsAreSortedLow():"
                + metaData.nullsAreSortedLow(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "othersDeletesAreVisible("
                +     "ResultSet.TYPE_FORWARD_ONLY):"
                + metaData.othersDeletesAreVisible(
                    ResultSet.TYPE_FORWARD_ONLY),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "othersDeletesAreVisible("
                +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                + metaData.othersDeletesAreVisible(
                    ResultSet.TYPE_SCROLL_INSENSITIVE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "othersDeletesAreVisible("
                +     "ResultSet.TYPE_SCROLL_SENS):"
                + metaData.othersDeletesAreVisible(
                    ResultSet.TYPE_SCROLL_SENSITIVE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "othersInsertsAreVisible("
                +     "ResultSet.TYPE_FORWARD_ONLY):"
                + metaData.othersInsertsAreVisible(
                    ResultSet.TYPE_FORWARD_ONLY),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "othersInsertsAreVisible("
                +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                + metaData.othersInsertsAreVisible(
                    ResultSet.TYPE_SCROLL_INSENSITIVE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "othersInsertsAreVisible("
                +     "ResultSet.TYPE_SCROLL_SENS):"
                + metaData.othersInsertsAreVisible(
                    ResultSet.TYPE_SCROLL_SENSITIVE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "othersUpdatesAreVisible("
                +     "ResultSet.TYPE_FORWARD_ONLY):"
                + metaData.othersUpdatesAreVisible(
                    ResultSet.TYPE_FORWARD_ONLY),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "othersUpdatesAreVisible("
                +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                + metaData.othersUpdatesAreVisible(
                    ResultSet.TYPE_SCROLL_INSENSITIVE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "othersUpdatesAreVisible("
                +     "ResultSet.TYPE_SCROLL_SENS):"
                + metaData.othersUpdatesAreVisible(
                    ResultSet.TYPE_SCROLL_SENSITIVE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "ownDeletesAreVisible("
                +     "ResultSet.TYPE_FORWARD_ONLY):"
                + metaData.ownDeletesAreVisible(
                    ResultSet.TYPE_FORWARD_ONLY),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "ownDeletesAreVisible("
                +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                + metaData.ownDeletesAreVisible(
                    ResultSet.TYPE_SCROLL_INSENSITIVE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "ownDeletesAreVisible("
                +     "ResultSet.TYPE_SCROLL_SENS):"
                + metaData.ownDeletesAreVisible(
                    ResultSet.TYPE_SCROLL_SENSITIVE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "ownInsertsAreVisible("
                +     "ResultSet.TYPE_FORWARD_ONLY):"
                + metaData.ownInsertsAreVisible(
                    ResultSet.TYPE_FORWARD_ONLY),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "ownInsertsAreVisible("
                +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                + metaData.ownInsertsAreVisible(
                    ResultSet.TYPE_SCROLL_INSENSITIVE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "ownInsertsAreVisible("
                +     "ResultSet.TYPE_SCROLL_SENS):"
                + metaData.ownInsertsAreVisible(
                    ResultSet.TYPE_SCROLL_SENSITIVE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "ownUpdatesAreVisible("
                +     "ResultSet.TYPE_FORWARD_ONLY):"
                + metaData.ownUpdatesAreVisible(
                    ResultSet.TYPE_FORWARD_ONLY),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "ownUpdatesAreVisible("
                +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                + metaData.ownUpdatesAreVisible(
                    ResultSet.TYPE_SCROLL_INSENSITIVE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "ownUpdatesAreVisible("
                +     "ResultSet.TYPE_SCROLL_SENS):"
                + metaData.ownUpdatesAreVisible(
                    ResultSet.TYPE_SCROLL_SENSITIVE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "storesLowerCaseIdentifiers():"
                + metaData.storesLowerCaseIdentifiers(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "storesLowerCaseQuotedIdentifiers():"
                + metaData.storesLowerCaseQuotedIdentifiers(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "storesMixedCaseIdentifiers():"
                + metaData.storesMixedCaseIdentifiers(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "storesMixedCaseQuotedIdentifiers():"
                + metaData.storesMixedCaseQuotedIdentifiers(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "storesUpperCaseIdentifiers():"
                + metaData.storesUpperCaseIdentifiers(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "storesUpperCaseQuotedIdentifiers():"
                + metaData.storesUpperCaseQuotedIdentifiers(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsAlterTableWithAddColumn():"
                + metaData.supportsAlterTableWithAddColumn(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsAlterTableWithDropColumn():"
                + metaData.supportsAlterTableWithDropColumn(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsANSI92EntryLevelSQL():"
                + metaData.supportsANSI92EntryLevelSQL(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsANSI92FullSQL():"
                + metaData.supportsANSI92FullSQL(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsANSI92IntermediateSQL():"
                + metaData.supportsANSI92IntermediateSQL(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsBatchUpdates():"
                + metaData.supportsBatchUpdates(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsCatalogsInDataManipulation():"
                + metaData.supportsCatalogsInDataManipulation(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsCatalogsInIndexDefinitions():"
                + metaData.supportsCatalogsInIndexDefinitions(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsCatalogsInPrivilegeDefinitions():"
                + metaData.supportsCatalogsInPrivilegeDefinitions(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsCatalogsInProcedureCalls():"
                + metaData.supportsCatalogsInProcedureCalls(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsCatalogsInTableDefinitions():"
                + metaData.supportsCatalogsInTableDefinitions(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsColumnAliasing():"
                + metaData.supportsColumnAliasing(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsConvert():"
                + metaData.supportsConvert(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsCoreSQLGrammar():"
                + metaData.supportsCoreSQLGrammar(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsCorrelatedSubqueries():"
                + metaData.supportsCorrelatedSubqueries(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsDataDefinitionAndDataManipulationTransactions():"
                + metaData.supportsDataDefinitionAndDataManipulationTransactions(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsDataManipulationTransactionsOnly():"
                + metaData.supportsDataManipulationTransactionsOnly(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsDifferentTableCorrelationNames():"
                + metaData.supportsDifferentTableCorrelationNames(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsExpressionsInOrderBy():"
                + metaData.supportsExpressionsInOrderBy(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsExtendedSQLGrammar():"
                + metaData.supportsExtendedSQLGrammar(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsFullOuterJoins():"
                + metaData.supportsFullOuterJoins(),
                Project.MSG_VERBOSE);

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

            project.log(
                task,
                "supportsGetGeneratedKeys():"
                + t_strSupportsGetGeneratedKeys,
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsGroupBy():"
                + metaData.supportsGroupBy(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsGroupByBeyondSelect():"
                + metaData.supportsGroupByBeyondSelect(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsGroupByUnrelated():"
                + metaData.supportsGroupByUnrelated(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsIntegrityEnhancementFacility():"
                + metaData.supportsIntegrityEnhancementFacility(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsLikeEscapeClause():"
                + metaData.supportsLikeEscapeClause(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsLimitedOuterJoins():"
                + metaData.supportsLimitedOuterJoins(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsMinimumSQLGrammar():"
                + metaData.supportsMinimumSQLGrammar(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsMixedCaseIdentifiers():"
                + metaData.supportsMixedCaseIdentifiers(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsMixedCaseQuotedIdentifiers():"
                + metaData.supportsMixedCaseQuotedIdentifiers(),
                Project.MSG_VERBOSE);

            /*
             * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
             * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsMultipleOpenResults()
             project.log(
             task,
             "supportsMultipleOpenResults():"
             + metaData.supportsMultipleOpenResults(),
             Project.MSG_VERBOSE);
            */

            project.log(
                task,
                "supportsMultipleResultSets():"
                + metaData.supportsMultipleResultSets(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsMultipleTransactions():"
                + metaData.supportsMultipleTransactions(),
                Project.MSG_VERBOSE);

            /*
             * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
             * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsNamedParameters()
             project.log(
             task,
             "supportsNamedParameters():"
             + metaData.supportsNamedParameters(),
             Project.MSG_VERBOSE);
            */

            project.log(
                task,
                "supportsNonNullableColumns():"
                + metaData.supportsNonNullableColumns(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsOpenCursorsAcrossCommit():"
                + metaData.supportsOpenCursorsAcrossCommit(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsOpenCursorsAcrossRollback():"
                + metaData.supportsOpenCursorsAcrossRollback(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsOpenStatementsAcrossCommit():"
                + metaData.supportsOpenStatementsAcrossCommit(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsOpenStatementsAcrossRollback():"
                + metaData.supportsOpenStatementsAcrossRollback(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsOrderByUnrelated():"
                + metaData.supportsOrderByUnrelated(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsOuterJoins():"
                + metaData.supportsOuterJoins(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsPositionedDelete():"
                + metaData.supportsPositionedDelete(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsPositionedUpdate():"
                + metaData.supportsPositionedUpdate(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsResultSetConcurrency("
                +     "TYPE_FORWARD_ONLY,CONCUR_READ_ONLY):"
                + metaData.supportsResultSetConcurrency(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsResultSetConcurrency("
                +     "TYPE_FORWARD_ONLY,CONCUR_UPDATABLE):"
                + metaData.supportsResultSetConcurrency(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsResultSetConcurrency("
                +     "TYPE_SCROLL_INSENSITIVE,CONCUR_READ_ONLY):"
                + metaData.supportsResultSetConcurrency(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsResultSetConcurrency("
                +     "TYPE_SCROLL_INSENSITIVE,CONCUR_UPDATABLE):"
                + metaData.supportsResultSetConcurrency(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsResultSetConcurrency("
                +     "TYPE_SCROLL_SENSITIVE,CONCUR_READ_ONLY):"
                + metaData.supportsResultSetConcurrency(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsResultSetConcurrency("
                +     "TYPE_SCROLL_SENSITIVE,CONCUR_UPDATABLE):"
                + metaData.supportsResultSetConcurrency(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE),
                Project.MSG_VERBOSE);

            /*
             * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
             * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsResultSetHoldability()
             project.log(
             task,
             "supportsResultSetHoldability("
             +     "HOLD_CURSORS_OVER_COMMIT):"
             + metaData.supportsResultSetHoldability(
             ResultSet.HOLD_CURSORS_OVER_COMMIT),
             Project.MSG_VERBOSE);

             project.log(
             task,
             "supportsResultSetHoldability("
             +     "CLOSE_CURSORS_AT_COMMIT):"
             + metaData.supportsResultSetHoldability(
             ResultSet.CLOSE_CURSORS_AT_COMMIT),
             Project.MSG_VERBOSE);
            */

            project.log(
                task,
                "supportsResultSetType("
                +     "TYPE_FORWARD_ONLY):"
                + metaData.supportsResultSetType(
                    ResultSet.TYPE_FORWARD_ONLY),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsResultSetType("
                +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                + metaData.supportsResultSetType(
                    ResultSet.TYPE_SCROLL_INSENSITIVE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsResultSetType("
                +     "TYPE_SCROLL_SENSITIVE):"
                + metaData.supportsResultSetType(
                    ResultSet.TYPE_SCROLL_SENSITIVE),
                Project.MSG_VERBOSE);

            /*
             * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
             * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsSavePoints()
             project.log(
             task,
             "supportsSavepoints():"
             + metaData.supportsSavepoints(),
             Project.MSG_VERBOSE);
            */

            project.log(
                task,
                "supportsSchemasInDataManipulation():"
                + metaData.supportsSchemasInDataManipulation(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsSchemasInIndexDefinitions():"
                + metaData.supportsSchemasInIndexDefinitions(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsSchemasInPrivilegeDefinitions():"
                + metaData.supportsSchemasInPrivilegeDefinitions(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsSchemasInProcedureCalls():"
                + metaData.supportsSchemasInProcedureCalls(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsSchemasInTableDefinitions():"
                + metaData.supportsSchemasInTableDefinitions(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsSelectForUpdate():"
                + metaData.supportsSelectForUpdate(),
                Project.MSG_VERBOSE);

            /*
             * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
             * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsStatementPooling()
             project.log(
             task,
             "supportsStatementPooling():"
             + metaData.supportsStatementPooling(),
             Project.MSG_VERBOSE);
            */

            project.log(
                task,
                "supportsStoredProcedures():"
                + metaData.supportsStoredProcedures(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsSubqueriesInComparisons():"
                + metaData.supportsSubqueriesInComparisons(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsSubqueriesInExists():"
                + metaData.supportsSubqueriesInExists(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsSubqueriesInIns():"
                + metaData.supportsSubqueriesInIns(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsSubqueriesInQuantifieds():"
                + metaData.supportsSubqueriesInQuantifieds(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsTableCorrelationNames():"
                + metaData.supportsTableCorrelationNames(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsTransactionIsolationLevel("
                +     "TRANSACTION_NONE):"
                + metaData.supportsTransactionIsolationLevel(
                    Connection.TRANSACTION_NONE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsTransactionIsolationLevel("
                +     "TRANSACTION_READ_COMMITTED):"
                + metaData.supportsTransactionIsolationLevel(
                    Connection.TRANSACTION_READ_COMMITTED),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsTransactionIsolationLevel("
                +     "TRANSACTION_READ_UNCOMMITTED):"
                + metaData.supportsTransactionIsolationLevel(
                    Connection.TRANSACTION_READ_UNCOMMITTED),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsTransactionIsolationLevel("
                +     "TRANSACTION_REPEATABLE_READ):"
                + metaData.supportsTransactionIsolationLevel(
                    Connection.TRANSACTION_REPEATABLE_READ),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsTransactionIsolationLevel("
                +     "TRANSACTION_SERIALIZABLE):"
                + metaData.supportsTransactionIsolationLevel(
                    Connection.TRANSACTION_SERIALIZABLE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsTransactions():"
                + metaData.supportsTransactions(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsUnion():"
                + metaData.supportsUnion(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "supportsUnionAll():"
                + metaData.supportsUnionAll(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "updatesAreDetected("
                +     "TYPE_FORWARD_ONLY):"
                + metaData.updatesAreDetected(
                    ResultSet.TYPE_FORWARD_ONLY),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "updatesAreDetected("
                +     "TYPE_SCROLL_INSENSITIVE):"
                + metaData.updatesAreDetected(
                    ResultSet.TYPE_SCROLL_INSENSITIVE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "updatesAreDetected("
                +     "TYPE_SCROLL_SENS):"
                + metaData.updatesAreDetected(
                    ResultSet.TYPE_SCROLL_SENSITIVE),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "usesLocalFilePerTable():"
                + metaData.usesLocalFilePerTable(),
                Project.MSG_VERBOSE);

            project.log(
                task,
                "usesLocalFiles():"
                + metaData.usesLocalFiles(),
                Project.MSG_VERBOSE);
        }
        catch  (final SQLException sqlException)
        {
            project.log(
                task,
                "Database metadata request failed ("
                + sqlException
                + ")",
                Project.MSG_ERR);

            sqlException.printStackTrace(System.out);
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
