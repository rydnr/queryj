
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
 * Description: Manages the information metadata stored in an Oracle database.
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
package org.acmsl.queryj.tools.oracle;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.MetaDataUtils;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.Condition;
import org.acmsl.queryj.Field;
import org.acmsl.queryj.Query;
import org.acmsl.queryj.QueryFactory;
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.QueryResultSet;
import org.acmsl.queryj.SelectQuery;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.oracle.OracleTableRepository;
import org.acmsl.queryj.tools.ProcedureMetaData;
import org.acmsl.queryj.tools.ProcedureParameterMetaData;

/*
 * Importing Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the information metadata stored in an Oracle database.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public class OracleMetaDataManager
    extends  DatabaseMetaDataManager
{
    /**
     * Creates an empty OracleMetaDataManager.
     */
    protected OracleMetaDataManager()
    {
        super();
    }

    /**
     * Creates an OracleMetaDataManager using given information.
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
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    public OracleMetaDataManager(
        final String[] tableNames,
        final String[] procedureNames,
        final boolean disableTableExtraction,
        final boolean lazyTableExtraction,
        final boolean disableProcedureExtraction,
        final boolean lazyProcedureExtraction,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final Project project,
        final Task task)
        throws  SQLException,
                QueryJException
    {
        super(
            tableNames,
            procedureNames,
            disableTableExtraction,
            lazyTableExtraction,
            disableProcedureExtraction,
            lazyProcedureExtraction,
            metaData,
            catalog,
            schema,
            project,
            task);
    }

    /**
     * Creates an OracleMetaDataManager using given information.
     * @param tableNames explicitly specified table names.
     * @param procedureNames explicitly specified procedure names.
     * @param metaData the database meta data.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    public OracleMetaDataManager(
        final String[] tableNames,
        final String[] procedureNames,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final Project project,
        final Task task)
        throws  SQLException,
                QueryJException
    {
        super(
            tableNames,
            procedureNames,
            metaData,
            catalog,
            schema,
            project,
            task);
    }

    /**
     * Extracts the primary keys.
     * @param metaData the database metadata.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     * @precondition metaData != null
     */
    protected void extractPrimaryKeys(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        extractPrimaryKeys(
            metaData.getConnection(),
            catalog,
            schema,
            getTableNames(metaData, catalog, schema, project, task),
            QueryFactory.getInstance(),
            project,
            task);
    }

    /**
     * Extracts the primary keys.
     * @param connection the database connection.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param tableNames the table names.
     * @param queryFactory the <code>QueryFactory</code> instance.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     * @precondition connection != null
     * @precondition tableNames != null
     * @precndition queryFactory != null
     */
    protected void extractPrimaryKeys(
        final Connection connection,
        final String catalog,
        final String schema,
        final String[] tableNames,
        final QueryFactory queryFactory,
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        ResultSet t_rsResults = null;

        PreparedStatement t_PreparedStatement = null;

        try
        {
            for  (int t_iTableIndex = 0;
                      t_iTableIndex < tableNames.length;
                      t_iTableIndex++) 
            {
                try
                {
                    SelectQuery t_Query = queryFactory.createSelectQuery();

                    t_Query.select(OracleTableRepository.USER_CONS_COLUMNS.COLUMN_NAME);

                    t_Query.from(OracleTableRepository.USER_CONS_COLUMNS);
                    t_Query.from(OracleTableRepository.USER_CONSTRAINTS);

                    Condition t_Condition =
                        OracleTableRepository.USER_CONS_COLUMNS.CONSTRAINT_NAME.equals(
                            OracleTableRepository.USER_CONSTRAINTS.CONSTRAINT_NAME);

                    // USER_CONS_COLUMNS.CONSTRAINT_NAME = USER_CONSTRAINTS.CONSTRAINT_NAME

                    t_Condition =
                        t_Condition.and(
                            OracleTableRepository.USER_CONS_COLUMNS.TABLE_NAME.equals());

                    //(USER_CONS_COLUMNS.CONSTRAINT_NAME = USER_CONSTRAINTS.CONSTRAINT_NAME)
                    // AND (USER_CONS_COLUMNS.TABLE_NAME = ?)

                    t_Condition =
                        t_Condition.and(
                            OracleTableRepository.USER_CONSTRAINTS.CONSTRAINT_TYPE.equals("P"));

                    //((USER_CONS_COLUMNS.CONSTRAINT_NAME = USER_CONSTRAINTS.CONSTRAINT_NAME)
                    // AND (USER_CONS_COLUMNS.TABLE_NAME = ?)) AND (USER_CONSTRAINTS.CONSTRAINT_TYPE = 'P')

                    t_Query.where(t_Condition);

                    t_PreparedStatement = t_Query.prepareStatement(connection);

                    t_Query.setString(
                        OracleTableRepository.USER_CONS_COLUMNS.TABLE_NAME.equals(),
                        tableNames[t_iTableIndex]);

                    //SELECT USER_CONS_COLUMNS.COLUMN_NAME
                    //FROM USER_CONS_COLUMNS, USER_CONSTRAINTS
                    //WHERE ((USER_CONS_COLUMNS.CONSTRAINT_NAME = USER_CONSTRAINTS.CONSTRAINT_NAME)
                    // AND (USER_CONS_COLUMNS.TABLE_NAME = ?)) AND (USER_CONSTRAINTS.CONSTRAINT_TYPE = 'P')

                    project.log(
                        task,
                        "query:" + t_Query,
                        Project.MSG_DEBUG);

                    t_rsResults = t_Query.executeQuery();

                    while  (   (t_rsResults != null)
                            && (t_rsResults.next()))
                    {
                        addPrimaryKey(
                            tableNames[t_iTableIndex],
                            t_rsResults.getString(
                                OracleTableRepository.USER_CONS_COLUMNS.COLUMN_NAME.toSimplifiedString()));
                    }
                }
                catch  (final SQLException sqlException)
                {
                    throw
                        new QueryJException(
                            "cannot.retrieve.primary.keys",
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
                project.log(
                    task,
                    "Cannot close the result set " + sqlException,
                    Project.MSG_ERR);
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
                project.log(
                    task,
                    "Cannot close the statement " + sqlException,
                    Project.MSG_ERR);
            }
        }
    }

    /**
     * Extracts the foreign keys.
     * @param metaData the database metadata.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     * @precondition metaData != null
     * @precondition project != null
     * @precondition task != null
     */
    protected void extractForeignKeys(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        extractForeignKeys(
            metaData.getConnection(),
            catalog,
            schema,
            getTableNames(metaData, catalog, schema, project, task),
            QueryFactory.getInstance(),
            OracleTextFunctions.getInstance(),
            project,
            task);
    }

    /**
     * Extracts the foreign keys.
     * @param connection the database connection.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param tableNames the table names.
     * @param queryFactory the query factory.
     * @param textFunctions the <code>OracleTextFunctions</code> instance.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     * @precondition connection != null
     * @precondition tableNames != null
     * @precondition queryFactory != null
     * @precondition textFunctions != null
     * @precondition project != null
     * @precondition task != null
     */
    protected void extractForeignKeys(
        final Connection connection,
        final String catalog,
        final String schema,
        final String[] tableNames,
        final QueryFactory queryFactory,
        final OracleTextFunctions textFunctions,
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        QueryResultSet t_Results = null;

        PreparedStatement t_PreparedStatement = null;

        try
        {
            for  (int t_iTableIndex = 0;
                      t_iTableIndex < tableNames.length;
                      t_iTableIndex++) 
            {
                try
                {
                    SelectQuery t_Query = queryFactory.createSelectQuery();

                    OracleUserConstraintsTable CON =
                        OracleUserConstraintsTable.getInstance("con");
                    OracleUserConsColumnsTable COL =
                        OracleUserConsColumnsTable.getInstance("col");
                    OracleUserConstraintsTable RCON =
                        OracleUserConstraintsTable.getInstance("rcon");
                    OracleUserConsColumnsTable RCOL =
                        OracleUserConsColumnsTable.getInstance("rcol");

                    t_Query.select(CON.CONSTRAINT_NAME);
                    t_Query.select(RCOL.TABLE_NAME);
                    t_Query.select(RCOL.COLUMN_NAME);
                    t_Query.select(CON.TABLE_NAME);
                    t_Query.select(COL.COLUMN_NAME);

                    t_Query.from(CON);
                    t_Query.from(COL);
                    t_Query.from(RCON);
                    t_Query.from(RCOL);

                    t_Query.where(
                             RCON.CONSTRAINT_TYPE.equals("R")
                        .and(RCON.R_CONSTRAINT_NAME.equals(CON.CONSTRAINT_NAME))
                        .and(COL.TABLE_NAME.equals(CON.TABLE_NAME))
                        .and(COL.CONSTRAINT_NAME.equals(CON.CONSTRAINT_NAME))
                        .and(RCOL.TABLE_NAME.equals(RCON.TABLE_NAME))
                        .and(RCOL.CONSTRAINT_NAME.equals(RCON.CONSTRAINT_NAME))
                        .and(RCOL.POSITION.equals(COL.POSITION))
                        .and(textFunctions.upper(COL.TABLE_NAME).equals()));

                    t_PreparedStatement = t_Query.prepareStatement(connection);

                    t_Query.setString(
                        textFunctions.upper(COL.TABLE_NAME).equals(),
                        tableNames[t_iTableIndex].toUpperCase());

                    t_Results = (QueryResultSet) t_Query.executeQuery();

                    if  (t_Results != null)
                    {
                        while  (t_Results.next())
                        {
                            addForeignKey(
                                t_Results.getString(
                                    RCOL.TABLE_NAME),
                                t_Results.getString(
                                    RCOL.COLUMN_NAME),
                                tableNames[t_iTableIndex],
                                t_Results.getString(
                                    COL.COLUMN_NAME));
                        }
                    }
                }
                catch  (final SQLException sqlException)
                {
                    throw
                        new QueryJException(
                            "cannot.retrieve.foreign.keys",
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
                project.log(
                    task,
                    "Cannot close the result set " + sqlException,
                    Project.MSG_ERR);
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
                project.log(
                    task,
                    "Cannot close the statement " + sqlException,
                    Project.MSG_ERR);
            }
        }
    }

    /**
     * Retrieves the table names.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the list of all table names.
     * @throws SQLException if the database operation fails.
     * @precondition metaData != null
     */
    protected String[] getTableNames(
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema,
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        return
            getTableNames(
                metaData.getConnection(),
                catalog,
                schema,
                QueryFactory.getInstance(),
                project,
                task);
    }
                
    /**
     * Retrieves the table names.
     * @param connection the connection.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param queryFactory the <code>QueryFactory</code> instance.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
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
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        String[] result = EMPTY_STRING_ARRAY;

        QueryResultSet t_Results = null;

        PreparedStatement t_PreparedStatement = null;

        try
        {
            try
            {
                SelectQuery t_Query = queryFactory.createSelectQuery();

                t_Query.select(OracleTableRepository.USER_TABLES.TABLE_NAME);

                t_Query.from(OracleTableRepository.USER_TABLES);

                t_Query.where(OracleTableRepository.USER_TABLES.TABLE_NAME.notEquals("PLAN_TABLE"));

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

            result = extractTableNames(t_Results);
        }
        catch  (final SQLException sqlException)
        {
            throw sqlException;
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
                project.log(
                    task,
                    "Cannot close the result set " + sqlException,
                    Project.MSG_ERR);
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
                project.log(
                    task,
                    "Cannot close the statement " + sqlException,
                    Project.MSG_ERR);
            }
        }

        return result;
    }

    /**
     * Extracts the table names from given result set.
     * @param resultSet the result set with the table information.
     * @return the list of table names.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected String[] extractTableNames(final QueryResultSet resultSet)
        throws  SQLException
    {
        return
            extractStringFields(
                resultSet,
                OracleTableRepository.USER_TABLES.TABLE_NAME);
    }

    /**
     * Retrieves the column names from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
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
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        return
            getColumnNames(
                metaData.getConnection(),
                catalog,
                schema,
                tableName,
                QueryFactory.getInstance(),
                project,
                task);
    }

    /**
     * Retrieves the column names from given table name.
     * @param connection the connection.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param queryFactory the <code>QueryFactory</code> instance.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the list of all column names.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if the any other error occurs.
     * @precondition connection != null
     * @precondition queryFactory != null
     */
    protected String[] getColumnNames(
        final Connection connection,
        final String catalog,
        final String schema,
        final String tableName,
        final QueryFactory queryFactory,
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        String[] result = EMPTY_STRING_ARRAY;

        ResultSet t_rsResults = null;

        PreparedStatement t_PreparedStatement = null;

        try
        {
            try
            {
                SelectQuery t_Query = queryFactory.createSelectQuery();

                t_Query.select(OracleTableRepository.USER_COL_COMMENTS.COLUMN_NAME);

                // t_Query.select(OracleTableRepository.USER_COL_COMMENTS.COMMENTS);

                t_Query.from(OracleTableRepository.USER_COL_COMMENTS);

                t_Query.where(OracleTableRepository.USER_COL_COMMENTS.TABLE_NAME.equals());

                t_PreparedStatement = t_Query.prepareStatement(connection);

                t_Query.setString(
                    OracleTableRepository.USER_COL_COMMENTS.TABLE_NAME.equals(),
                    tableName);

                /*
                t_PreparedStatement = t_Connection.prepareStatement(t_Query.toString());

                t_PreparedStatement.setString(
                    1,
                    tableName);
                */

                t_rsResults = t_Query.executeQuery();
                //t_rsResults = t_PreparedStatement.executeQuery();
            }
            catch  (final SQLException sqlException)
            {
                throw
                    new QueryJException(
                        "cannot.retrieve.database.column.names",
                        sqlException);
            }

            result = extractColumnNames(t_rsResults);
        }
        catch  (final SQLException sqlException)
        {
            throw sqlException;
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
            catch  (SQLException sqlException)
            {
                project.log(
                    task,
                    "Cannot close the result set " + sqlException,
                    Project.MSG_ERR);
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
                project.log(
                    task,
                    "Cannot close the statement " + sqlException,
                    Project.MSG_ERR);
            }
        }

        return result;
    }

    /**
     * Extracts the column names from given result set.
     * @param resultSet the result set with the table information.
     * @return the list of column names.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected String[] extractColumnNames(final ResultSet resultSet)
        throws  SQLException
    {
        return
            extractStringFields(
                resultSet,
                OracleTableRepository.USER_COL_COMMENTS.COLUMN_NAME);
    }

    /**
     * Retrieves the column types from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param size the number of fields to extract.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
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
        final int size,
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        return
            getColumnTypes(
                metaData.getConnection(),
                catalog,
                schema,
                tableName,
                size,
                QueryFactory.getInstance(),
                project,
                task);
    }

    /**
     * Retrieves the column types from given table name.
     * @param connection the connection.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param size the number of fields to extract.
     * @param queryFactory the <code>QueryFactory</code> instance.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the list of all column types.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if any other error occurs.
     * @precondition connection != null
     * @precondition queryFactory != null
     */
    protected int[] getColumnTypes(
        final Connection connection,
        final String catalog,
        final String schema,
        final String tableName,
        final int size,
        final QueryFactory queryFactory,
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        int[] result = EMPTY_INT_ARRAY;

        ResultSet t_rsResults = null;

        PreparedStatement t_PreparedStatement = null;

        try
        {
            try
            {
                SelectQuery t_Query = queryFactory.createSelectQuery();

                t_Query.select(OracleTableRepository.USER_TAB_COLUMNS.DATA_TYPE);

                t_Query.from(OracleTableRepository.USER_TAB_COLUMNS);

                t_Query.where(OracleTableRepository.USER_TAB_COLUMNS.TABLE_NAME.equals());

                t_PreparedStatement = t_Query.prepareStatement(connection);

                t_Query.setString(
                    OracleTableRepository.USER_TAB_COLUMNS.TABLE_NAME.equals(),
                    tableName);

                t_rsResults = t_Query.executeQuery();
            }
            catch  (final SQLException sqlException)
            {
                throw
                    new QueryJException(
                        "cannot.retrieve.database.column.types",
                        sqlException);
            }

            result = extractColumnTypes(t_rsResults, OracleTableRepository.USER_TAB_COLUMNS.DATA_TYPE);
        }
        catch  (final SQLException sqlException)
        {
            throw sqlException;
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
                project.log(
                    task,
                    "Cannot close the result set " + sqlException,
                    Project.MSG_ERR);
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
                project.log(
                    task,
                    "Cannot close the statement " + sqlException,
                    Project.MSG_ERR);
            }
        }

        return result;
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param fieldName the field name.
     * @return the list of column types.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected int[] extractColumnTypes(
        final ResultSet resultSet, final String fieldName)
      throws  SQLException
    {
        return
            extractColumnTypes(
                resultSet, fieldName, MetaDataUtils.getInstance());
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param fieldName the field name.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @return the list of column types.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     * @precondition metaDataUtils != null
     */
    protected int[] extractColumnTypes(
        final ResultSet resultSet,
        final String fieldName,
        final MetaDataUtils metaDataUtils)
      throws  SQLException
    {
        int[] result = EMPTY_INT_ARRAY;

        String[] t_astrTypes = extractStringFields(resultSet, fieldName);

        if  (t_astrTypes != null)
        {
            result = new int[t_astrTypes.length];

            for  (int t_iIndex = 0;
                      t_iIndex < t_astrTypes.length;
                      t_iIndex++) 
            {
                result[t_iIndex] =
                    metaDataUtils.getJavaType(t_astrTypes[t_iIndex]);
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
     * @param size the number of fields to extract.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
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
        final int size,
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        return
            getAllowNulls(
                metaData.getConnection(),
                catalog,
                schema,
                tableName,
                size,
                QueryFactory.getInstance(),
                project,
                task);
    }

    /**
     * Retrieves whether the columns allow null values.
     * @param connection the connection.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param size the number of fields to extract.
     * @param queryFactory the <code>QueryFactory</code> instance.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the list of all column types.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if any other error occurs.
     * @precondition connection != null
     * @precondition queryFactory != null
     */
    protected boolean[] getAllowNulls(
        final Connection connection,
        final String catalog,
        final String schema,
        final String tableName,
        final int size,
        final QueryFactory queryFactory,
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        boolean[] result = EMPTY_BOOLEAN_ARRAY;

        ResultSet t_rsResults = null;

        PreparedStatement t_PreparedStatement = null;

        try
        {
            try
            {
                SelectQuery t_Query = queryFactory.createSelectQuery();

                t_Query.select(OracleTableRepository.USER_TAB_COLUMNS.NULLABLE);

                t_Query.from(OracleTableRepository.USER_TAB_COLUMNS);

                t_Query.where(
                    OracleTableRepository.USER_TAB_COLUMNS.TABLE_NAME.equals());

                t_PreparedStatement = t_Query.prepareStatement(connection);

                t_Query.setString(
                    OracleTableRepository.USER_TAB_COLUMNS.TABLE_NAME.equals(),
                    tableName);

                t_rsResults = t_Query.executeQuery();
            }
            catch  (final SQLException sqlException)
            {
                throw
                    new QueryJException(
                        "cannot.retrieve.database.column.types",
                        sqlException);
            }

            result =
                extractAllowNull(
                    t_rsResults,
                    OracleTableRepository.USER_TAB_COLUMNS.NULLABLE);
        }
        catch  (final SQLException sqlException)
        {
            throw sqlException;
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
                project.log(
                    task,
                    "Cannot close the result set " + sqlException,
                    Project.MSG_ERR);
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
                project.log(
                    task,
                    "Cannot close the statement " + sqlException,
                    Project.MSG_ERR);
            }
        }

        return result;
    }

    /**
     * Extracts the column types from given result set.
     * @param resultSet the result set with the column information.
     * @param fieldName the field name.
     * @return the list of column types.
     * @throws SQLException if the database operation fails.
     * @precondition resultSet != null
     */
    protected boolean[] extractAllowNull(
        final ResultSet resultSet, final String fieldName)
      throws  SQLException
    {
        boolean[] result = EMPTY_BOOLEAN_ARRAY;

        String[] t_astrTypes = extractStringFields(resultSet, fieldName);

        if  (t_astrTypes != null)
        {
            result = new boolean[t_astrTypes.length];

            for  (int t_iIndex = 0;
                      t_iIndex < t_astrTypes.length;
                      t_iIndex++) 
            {
                result[t_iIndex] =
                    !("N".equalsIgnoreCase(t_astrTypes[t_iIndex]));
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
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
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
        final Project project,
        final Task task)
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
                project,
                task);
    }

    /**
     * Extracts the table comments.
     * @param connection the database connection.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param tableName the table name.
     * @param queryFactory the <code>QueryFactory</code> instance.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the table comments.
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     * @precondition connection != null
     * @precondition tableNames != null
     * @precndition queryFactory != null
     */
    protected String getTableComment(
        final Connection connection,
        final String catalog,
        final String schema,
        final String tableName,
        final QueryFactory queryFactory,
        final Project project,
        final Task task)
      throws  SQLException,
              QueryJException
    {
        String result = "";

        ResultSet t_rsResults = null;

        PreparedStatement t_PreparedStatement = null;

        try
        {
            try
            {
                SelectQuery t_Query = queryFactory.createSelectQuery();

                t_Query.select(OracleTableRepository.USER_TAB_COMMENTS.COMMENTS);

                t_Query.from(OracleTableRepository.USER_TAB_COMMENTS);

                t_Query.where(
                    OracleTableRepository.USER_TAB_COMMENTS.TABLE_NAME.equals());

                t_PreparedStatement = t_Query.prepareStatement(connection);

                t_Query.setString(
                    OracleTableRepository.USER_TAB_COMMENTS.TABLE_NAME.equals(),
                    tableName);

                project.log(
                    task,
                    "query:" + t_Query,
                    Project.MSG_DEBUG);

                t_rsResults = t_Query.executeQuery();

                String t_strComment = null;

                if  (   (t_rsResults != null)
                        && (t_rsResults.next()))
                {
                    t_strComment =
                        t_rsResults.getString(
                            OracleTableRepository.USER_TAB_COMMENTS.COMMENTS.toSimplifiedString());

                    if  (t_strComment != null)
                    {
                        project.log(
                            task,
                            "Comments for table " + tableName + " = " + t_strComment,
                            Project.MSG_VERBOSE);
                    }
                    else
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
                        "cannot.retrieve.table.comments",
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
                project.log(
                    task,
                    "Cannot close the result set " + sqlException,
                    Project.MSG_ERR);
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
                project.log(
                    task,
                    "Cannot close the statement " + sqlException,
                    Project.MSG_ERR);
            }
        }

        return result;
    }
}
