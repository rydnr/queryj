/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
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
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
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
import org.acmsl.commons.version.Version;
import org.acmsl.commons.version.VersionFactory;

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
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
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
    public boolean handle(Command command)
    {
        boolean result = false;

        if  (command instanceof AntCommand) 
        {
            AntCommand t_AntCommand = (AntCommand) command;

            try 
            {
                result = handle(t_AntCommand);
            }
            catch  (BuildException buildException)
            {
                Project t_Project = t_AntCommand.getProject();

                if  (t_Project != null)
                {
                    t_Project.log(
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
    public boolean handle(AntCommand command)
        throws  BuildException
    {
        boolean result = false;

        if  (command != null) 
        {
            Project t_Project = command.getProject();

            if  (t_Project != null)
            {
                Task t_Task = command.getTask();

                Map t_mAttributes = command.getAttributeMap();

                DatabaseMetaData t_MetaData =
                    retrieveDatabaseMetaData(t_mAttributes);

                if  (t_MetaData != null)
                {
                    try 
                    {
                        t_Project.log(
                            t_Task,
                              "Numeric functions:"
                            + t_MetaData.getNumericFunctions(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "String functions:"
                            + t_MetaData.getStringFunctions(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "System functions:"
                            + t_MetaData.getSystemFunctions(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                            "Time functions:" + t_MetaData.getTimeDateFunctions(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "insertsAreDetected("
                            +     "TYPE_FORWARD_ONLY):"
                            + t_MetaData.insertsAreDetected(
                                  ResultSet.TYPE_FORWARD_ONLY),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "insertsAreDetected("
                            +     "TYPE_SCROLL_INSENSITIVE):"
                            + t_MetaData.insertsAreDetected(
                                  ResultSet.TYPE_SCROLL_INSENSITIVE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "insertsAreDetected("
                            +     "TYPE_SCROLL_SENS):"
                            + t_MetaData.insertsAreDetected(
                                  ResultSet.TYPE_SCROLL_SENSITIVE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "isCatalogAtStart():"
                            + t_MetaData.isCatalogAtStart(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "isReadOnly():"
                            + t_MetaData.isReadOnly(),
                            Project.MSG_VERBOSE);

                        /*
                         * Fails for MySQL with a java.lang.AbstractMethodError 
                         * com.mysql.jdbc.jdbc2.DatabaseMetaData.locatorsUpdateCopy()
                         t_Project.log(
                            t_Task,
                                "locatorsUpdateCopy():"
                              + t_MetaData.locatorsUpdateCopy(),
                            Project.MSG_VERBOSE);
                        */

                        t_Project.log(
                            t_Task,
                              "nullPlusNonNullIsNull():"
                            + t_MetaData.nullPlusNonNullIsNull(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "nullsAreSortedAtEnd():"
                            + t_MetaData.nullsAreSortedAtEnd(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "nullsAreSortedAtStart():"
                            + t_MetaData.nullsAreSortedAtStart(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "nullsAreSortedHigh():"
                            + t_MetaData.nullsAreSortedHigh(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "nullsAreSortedLow():"
                            + t_MetaData.nullsAreSortedLow(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                            "othersDeletesAreVisible("
                            +     "ResultSet.TYPE_FORWARD_ONLY):"
                            + t_MetaData.othersDeletesAreVisible(
                                  ResultSet.TYPE_FORWARD_ONLY),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "othersDeletesAreVisible("
                            +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                            + t_MetaData.othersDeletesAreVisible(
                                  ResultSet.TYPE_SCROLL_INSENSITIVE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "othersDeletesAreVisible("
                            +     "ResultSet.TYPE_SCROLL_SENS):"
                            + t_MetaData.othersDeletesAreVisible(
                                  ResultSet.TYPE_SCROLL_SENSITIVE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "othersInsertsAreVisible("
                            +     "ResultSet.TYPE_FORWARD_ONLY):"
                            + t_MetaData.othersInsertsAreVisible(
                                  ResultSet.TYPE_FORWARD_ONLY),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "othersInsertsAreVisible("
                            +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                            + t_MetaData.othersInsertsAreVisible(
                                  ResultSet.TYPE_SCROLL_INSENSITIVE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "othersInsertsAreVisible("
                            +     "ResultSet.TYPE_SCROLL_SENS):"
                            + t_MetaData.othersInsertsAreVisible(
                                  ResultSet.TYPE_SCROLL_SENSITIVE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "othersUpdatesAreVisible("
                            +     "ResultSet.TYPE_FORWARD_ONLY):"
                            + t_MetaData.othersUpdatesAreVisible(
                                  ResultSet.TYPE_FORWARD_ONLY),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "othersUpdatesAreVisible("
                            +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                            + t_MetaData.othersUpdatesAreVisible(
                                  ResultSet.TYPE_SCROLL_INSENSITIVE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "othersUpdatesAreVisible("
                            +     "ResultSet.TYPE_SCROLL_SENS):"
                            + t_MetaData.othersUpdatesAreVisible(
                                  ResultSet.TYPE_SCROLL_SENSITIVE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "ownDeletesAreVisible("
                            +     "ResultSet.TYPE_FORWARD_ONLY):"
                            + t_MetaData.ownDeletesAreVisible(
                                  ResultSet.TYPE_FORWARD_ONLY),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "ownDeletesAreVisible("
                            +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                            + t_MetaData.ownDeletesAreVisible(
                                  ResultSet.TYPE_SCROLL_INSENSITIVE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "ownDeletesAreVisible("
                            +     "ResultSet.TYPE_SCROLL_SENS):"
                            + t_MetaData.ownDeletesAreVisible(
                                  ResultSet.TYPE_SCROLL_SENSITIVE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "ownInsertsAreVisible("
                            +     "ResultSet.TYPE_FORWARD_ONLY):"
                            + t_MetaData.ownInsertsAreVisible(
                                  ResultSet.TYPE_FORWARD_ONLY),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "ownInsertsAreVisible("
                            +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                            + t_MetaData.ownInsertsAreVisible(
                                  ResultSet.TYPE_SCROLL_INSENSITIVE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "ownInsertsAreVisible("
                            +     "ResultSet.TYPE_SCROLL_SENS):"
                            + t_MetaData.ownInsertsAreVisible(
                                  ResultSet.TYPE_SCROLL_SENSITIVE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "ownUpdatesAreVisible("
                            +     "ResultSet.TYPE_FORWARD_ONLY):"
                            + t_MetaData.ownUpdatesAreVisible(
                                  ResultSet.TYPE_FORWARD_ONLY),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "ownUpdatesAreVisible("
                            +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                            + t_MetaData.ownUpdatesAreVisible(
                                  ResultSet.TYPE_SCROLL_INSENSITIVE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "ownUpdatesAreVisible("
                            +     "ResultSet.TYPE_SCROLL_SENS):"
                            + t_MetaData.ownUpdatesAreVisible(
                                  ResultSet.TYPE_SCROLL_SENSITIVE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "storesLowerCaseIdentifiers():"
                            + t_MetaData.storesLowerCaseIdentifiers(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "storesLowerCaseQuotedIdentifiers():"
                            + t_MetaData.storesLowerCaseQuotedIdentifiers(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "storesMixedCaseIdentifiers():"
                            + t_MetaData.storesMixedCaseIdentifiers(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "storesMixedCaseQuotedIdentifiers():"
                            + t_MetaData.storesMixedCaseQuotedIdentifiers(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "storesUpperCaseIdentifiers():"
                            + t_MetaData.storesUpperCaseIdentifiers(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "storesUpperCaseQuotedIdentifiers():"
                            + t_MetaData.storesUpperCaseQuotedIdentifiers(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsAlterTableWithAddColumn():"
                            + t_MetaData.supportsAlterTableWithAddColumn(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsAlterTableWithDropColumn():"
                            + t_MetaData.supportsAlterTableWithDropColumn(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsANSI92EntryLevelSQL():"
                            + t_MetaData.supportsANSI92EntryLevelSQL(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsANSI92FullSQL():"
                            + t_MetaData.supportsANSI92FullSQL(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsANSI92IntermediateSQL():"
                            + t_MetaData.supportsANSI92IntermediateSQL(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsBatchUpdates():"
                            + t_MetaData.supportsBatchUpdates(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsCatalogsInDataManipulation():"
                            + t_MetaData.supportsCatalogsInDataManipulation(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsCatalogsInIndexDefinitions():"
                            + t_MetaData.supportsCatalogsInIndexDefinitions(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsCatalogsInPrivilegeDefinitions():"
                            + t_MetaData.supportsCatalogsInPrivilegeDefinitions(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsCatalogsInProcedureCalls():"
                            + t_MetaData.supportsCatalogsInProcedureCalls(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsCatalogsInTableDefinitions():"
                            + t_MetaData.supportsCatalogsInTableDefinitions(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsColumnAliasing():"
                            + t_MetaData.supportsColumnAliasing(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsConvert():"
                            + t_MetaData.supportsConvert(),
                            Project.MSG_VERBOSE);

                        /*
                          t_Project.log(
                            t_Task,
                              "supportsConvert(int fromType, int toType):"
                            + supportsConvert(int fromType, int toType),
                            Project.MSG_VERBOSE);
                        */

                        t_Project.log(
                            t_Task,
                              "supportsCoreSQLGrammar():"
                            + t_MetaData.supportsCoreSQLGrammar(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsCorrelatedSubqueries():"
                            + t_MetaData.supportsCorrelatedSubqueries(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsDataDefinitionAndDataManipulationTransactions():"
                            + t_MetaData.supportsDataDefinitionAndDataManipulationTransactions(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsDataManipulationTransactionsOnly():"
                            + t_MetaData.supportsDataManipulationTransactionsOnly(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsDifferentTableCorrelationNames():"
                            + t_MetaData.supportsDifferentTableCorrelationNames(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsExpressionsInOrderBy():"
                            + t_MetaData.supportsExpressionsInOrderBy(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsExtendedSQLGrammar():"
                            + t_MetaData.supportsExtendedSQLGrammar(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsFullOuterJoins():"
                            + t_MetaData.supportsFullOuterJoins(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsGetGeneratedKeys():"
                            + t_MetaData.supportsGetGeneratedKeys(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsGroupBy():"
                            + t_MetaData.supportsGroupBy(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsGroupByBeyondSelect():"
                            + t_MetaData.supportsGroupByBeyondSelect(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsGroupByUnrelated():"
                            + t_MetaData.supportsGroupByUnrelated(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsIntegrityEnhancementFacility():"
                            + t_MetaData.supportsIntegrityEnhancementFacility(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsLikeEscapeClause():"
                            + t_MetaData.supportsLikeEscapeClause(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsLimitedOuterJoins():"
                            + t_MetaData.supportsLimitedOuterJoins(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsMinimumSQLGrammar():"
                            + t_MetaData.supportsMinimumSQLGrammar(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsMixedCaseIdentifiers():"
                            + t_MetaData.supportsMixedCaseIdentifiers(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsMixedCaseQuotedIdentifiers():"
                            + t_MetaData.supportsMixedCaseQuotedIdentifiers(),
                            Project.MSG_VERBOSE);

                        /*
                         * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                         * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsMultipleOpenResults()
                         t_Project.log(
                            t_Task,
                              "supportsMultipleOpenResults():"
                            + t_MetaData.supportsMultipleOpenResults(),
                            Project.MSG_VERBOSE);
                        */

                        t_Project.log(
                            t_Task,
                              "supportsMultipleResultSets():"
                            + t_MetaData.supportsMultipleResultSets(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsMultipleTransactions():"
                            + t_MetaData.supportsMultipleTransactions(),
                            Project.MSG_VERBOSE);

                        /*
                         * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                         * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsNamedParameters()
                         t_Project.log(
                            t_Task,
                              "supportsNamedParameters():"
                            + t_MetaData.supportsNamedParameters(),
                            Project.MSG_VERBOSE);
                        */

                        t_Project.log(
                            t_Task,
                              "supportsNonNullableColumns():"
                            + t_MetaData.supportsNonNullableColumns(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsOpenCursorsAcrossCommit():"
                            + t_MetaData.supportsOpenCursorsAcrossCommit(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsOpenCursorsAcrossRollback():"
                            + t_MetaData.supportsOpenCursorsAcrossRollback(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsOpenStatementsAcrossCommit():"
                            + t_MetaData.supportsOpenStatementsAcrossCommit(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsOpenStatementsAcrossRollback():"
                            + t_MetaData.supportsOpenStatementsAcrossRollback(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsOrderByUnrelated():"
                            + t_MetaData.supportsOrderByUnrelated(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsOuterJoins():"
                            + t_MetaData.supportsOuterJoins(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsPositionedDelete():"
                            + t_MetaData.supportsPositionedDelete(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsPositionedUpdate():"
                            + t_MetaData.supportsPositionedUpdate(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsResultSetConcurrency("
                            +     "TYPE_FORWARD_ONLY,CONCUR_READ_ONLY):"
                            + t_MetaData.supportsResultSetConcurrency(
                                  ResultSet.TYPE_FORWARD_ONLY,
                                  ResultSet.CONCUR_READ_ONLY),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsResultSetConcurrency("
                            +     "TYPE_FORWARD_ONLY,CONCUR_UPDATABLE):"
                            + t_MetaData.supportsResultSetConcurrency(
                                  ResultSet.TYPE_FORWARD_ONLY,
                                  ResultSet.CONCUR_UPDATABLE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsResultSetConcurrency("
                            +     "TYPE_SCROLL_INSENSITIVE,CONCUR_READ_ONLY):"
                            + t_MetaData.supportsResultSetConcurrency(
                                  ResultSet.TYPE_SCROLL_INSENSITIVE,
                                  ResultSet.CONCUR_READ_ONLY),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsResultSetConcurrency("
                            +     "TYPE_SCROLL_INSENSITIVE,CONCUR_UPDATABLE):"
                            + t_MetaData.supportsResultSetConcurrency(
                                  ResultSet.TYPE_SCROLL_INSENSITIVE,
                                  ResultSet.CONCUR_UPDATABLE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsResultSetConcurrency("
                            +     "TYPE_SCROLL_SENSITIVE,CONCUR_READ_ONLY):"
                            + t_MetaData.supportsResultSetConcurrency(
                                  ResultSet.TYPE_SCROLL_SENSITIVE,
                                  ResultSet.CONCUR_READ_ONLY),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsResultSetConcurrency("
                            +     "TYPE_SCROLL_SENSITIVE,CONCUR_UPDATABLE):"
                            + t_MetaData.supportsResultSetConcurrency(
                                   ResultSet.TYPE_SCROLL_SENSITIVE,
                                   ResultSet.CONCUR_UPDATABLE),
                            Project.MSG_VERBOSE);

                        /*
                         * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                         * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsResultSetHoldability()
                         t_Project.log(
                            t_Task,
                              "supportsResultSetHoldability("
                            +     "HOLD_CURSORS_OVER_COMMIT):"
                            + t_MetaData.supportsResultSetHoldability(
                                  ResultSet.HOLD_CURSORS_OVER_COMMIT),
                            Project.MSG_VERBOSE);

                         t_Project.log(
                            t_Task,
                              "supportsResultSetHoldability("
                            +     "CLOSE_CURSORS_AT_COMMIT):"
                            + t_MetaData.supportsResultSetHoldability(
                                  ResultSet.CLOSE_CURSORS_AT_COMMIT),
                            Project.MSG_VERBOSE);
                        */

                        t_Project.log(
                            t_Task,
                              "supportsResultSetType("
                            +     "TYPE_FORWARD_ONLY):"
                            + t_MetaData.supportsResultSetType(
                                  ResultSet.TYPE_FORWARD_ONLY),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsResultSetType("
                            +     "ResultSet.TYPE_SCROLL_INSENSITIVE):"
                            + t_MetaData.supportsResultSetType(
                                  ResultSet.TYPE_SCROLL_INSENSITIVE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsResultSetType("
                            +     "TYPE_SCROLL_SENSITIVE):"
                            + t_MetaData.supportsResultSetType(
                                  ResultSet.TYPE_SCROLL_SENSITIVE),
                            Project.MSG_VERBOSE);

                        /*
                         * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                         * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsSavePoints()
                         t_Project.log(
                            t_Task,
                              "supportsSavepoints():"
                            + t_MetaData.supportsSavepoints(),
                            Project.MSG_VERBOSE);
                        */

                        t_Project.log(
                            t_Task,
                              "supportsSchemasInDataManipulation():"
                            + t_MetaData.supportsSchemasInDataManipulation(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsSchemasInIndexDefinitions():"
                            + t_MetaData.supportsSchemasInIndexDefinitions(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsSchemasInPrivilegeDefinitions():"
                            + t_MetaData.supportsSchemasInPrivilegeDefinitions(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsSchemasInProcedureCalls():"
                            + t_MetaData.supportsSchemasInProcedureCalls(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsSchemasInTableDefinitions():"
                            + t_MetaData.supportsSchemasInTableDefinitions(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsSelectForUpdate():"
                            + t_MetaData.supportsSelectForUpdate(),
                            Project.MSG_VERBOSE);

                        /*
                         * Fails in MySQL 3.23.53 with a java.lang.AbstractMethodError
                         * com.mysql.jdbc.jdbc2.DatabaseMetaData.supportsStatementPooling()
                         t_Project.log(
                            t_Task,
                              "supportsStatementPooling():"
                            + t_MetaData.supportsStatementPooling(),
                            Project.MSG_VERBOSE);
                        */

                        t_Project.log(
                            t_Task,
                              "supportsStoredProcedures():"
                            + t_MetaData.supportsStoredProcedures(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsSubqueriesInComparisons():"
                            + t_MetaData.supportsSubqueriesInComparisons(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsSubqueriesInExists():"
                            + t_MetaData.supportsSubqueriesInExists(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsSubqueriesInIns():"
                            + t_MetaData.supportsSubqueriesInIns(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsSubqueriesInQuantifieds():"
                            + t_MetaData.supportsSubqueriesInQuantifieds(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsTableCorrelationNames():"
                            + t_MetaData.supportsTableCorrelationNames(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsTransactionIsolationLevel("
                            +     "TRANSACTION_NONE):"
                            + t_MetaData.supportsTransactionIsolationLevel(
                                  Connection.TRANSACTION_NONE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsTransactionIsolationLevel("
                            +     "TRANSACTION_READ_COMMITTED):"
                            + t_MetaData.supportsTransactionIsolationLevel(
                                  Connection.TRANSACTION_READ_COMMITTED),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsTransactionIsolationLevel("
                            +     "TRANSACTION_READ_UNCOMMITTED):"
                            + t_MetaData.supportsTransactionIsolationLevel(
                                  Connection.TRANSACTION_READ_UNCOMMITTED),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsTransactionIsolationLevel("
                            +     "TRANSACTION_REPEATABLE_READ):"
                            + t_MetaData.supportsTransactionIsolationLevel(
                                  Connection.TRANSACTION_REPEATABLE_READ),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsTransactionIsolationLevel("
                            +     "TRANSACTION_SERIALIZABLE):"
                            + t_MetaData.supportsTransactionIsolationLevel(
                                  Connection.TRANSACTION_SERIALIZABLE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsTransactions():"
                            + t_MetaData.supportsTransactions(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsUnion():"
                            + t_MetaData.supportsUnion(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "supportsUnionAll():"
                            + t_MetaData.supportsUnionAll(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "updatesAreDetected("
                            +     "TYPE_FORWARD_ONLY):"
                            + t_MetaData.updatesAreDetected(
                                  ResultSet.TYPE_FORWARD_ONLY),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "updatesAreDetected("
                            +     "TYPE_SCROLL_INSENSITIVE):"
                            + t_MetaData.updatesAreDetected(
                                  ResultSet.TYPE_SCROLL_INSENSITIVE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "updatesAreDetected("
                            +     "TYPE_SCROLL_SENS):"
                            + t_MetaData.updatesAreDetected(
                                  ResultSet.TYPE_SCROLL_SENSITIVE),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "usesLocalFilePerTable():"
                            + t_MetaData.usesLocalFilePerTable(),
                            Project.MSG_VERBOSE);

                        t_Project.log(
                            t_Task,
                              "usesLocalFiles():"
                            + t_MetaData.usesLocalFiles(),
                            Project.MSG_VERBOSE);
                    }
                    catch  (SQLException sqlException)
                    {
                        t_Project.log(
                            t_Task,
                            "Database metadata request failed ("
                            + sqlException
                            + ")",
                            Project.MSG_ERR);
                    }
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the database metadata from the attribute map.
     * @param parameters the parameter map.
     * @return the metadata.
     * @throws BuildException if the metadata retrieval process if faulty.
     */
    protected DatabaseMetaData retrieveDatabaseMetaData(
            Map parameters)
        throws  BuildException
    {
        DatabaseMetaData result = null;

        if  (parameters != null)
        {
            result =
                (DatabaseMetaData)
                    parameters.get(
                        DatabaseMetaDataRetrievalHandler.DATABASE_METADATA);
        }
        
        return result;
    }

    /**
     * Concrete version object updated everytime it's checked-in in a
     * CVS repository.
     */
    public static final Version VERSION =
        VersionFactory.createVersion("$Revision$");

    /**
     * Retrieves the current version of this object.
     * @return the version object with such information.
     */
    public Version getVersion()
    {
        return VERSION;
    }

    /**
     * Retrieves the current version of this class. It's defined because
     * this is a utility class that cannot be instantiated.
     * @return the object with class version information.
     */
    public static Version getClassVersion()
    {
        return VERSION;
    }
}
