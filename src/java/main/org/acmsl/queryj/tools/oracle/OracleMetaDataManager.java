
/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

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
                    Urb. Valdecabañas
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

/*
 * Importing Jakarta Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Manages the information metadata stored in an Oracle database.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
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
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    public OracleMetaDataManager(
            String[]         tableNames,
            String[]         procedureNames,
            boolean          disableTableExtraction,
            boolean          lazyTableExtraction,
            boolean          disableProcedureExtraction,
            boolean          lazyProcedureExtraction,
            DatabaseMetaData metaData,
            String           catalog,
            String           schema)
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
            schema);
    }

    /**
     * Creates an OracleMetaDataManager using given information.
     * @param tableNames explicitly specified table names.
     * @param procedureNames explicitly specified procedure names.
     * @param metaData the database meta data.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    public OracleMetaDataManager(
            String[]         tableNames,
            String[]         procedureNames,
            DatabaseMetaData metaData,
            String           catalog,
            String           schema)
        throws  SQLException,
                QueryJException
    {
        super(
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
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     */
    protected void extractPrimaryKeys(
            DatabaseMetaData metaData,
            String           catalog,
            String           schema)
        throws  SQLException,
                QueryJException
    {
        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        ResultSet t_rsResults = null;

        PreparedStatement t_PreparedStatement = null;

        String[] t_astrTableNames =
            getTableNames(metaData, catalog, schema);

        if  (   (t_astrTableNames != null)
             && (metaData         != null)
             && (t_QueryFactory   != null))
        {
            try
            {
                for  (int t_iTableIndex = 0;
                          t_iTableIndex < t_astrTableNames.length;
                          t_iTableIndex++) 
                {
                    try
                    {
                        SelectQuery t_Query = t_QueryFactory.createSelectQuery();

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

                        t_PreparedStatement = t_Query.prepareStatement(metaData.getConnection());

                        t_Query.setString(
                            OracleTableRepository.USER_CONS_COLUMNS.TABLE_NAME.equals(),
                            t_astrTableNames[t_iTableIndex]);

                        //SELECT USER_CONS_COLUMNS.COLUMN_NAME
                        //FROM USER_CONS_COLUMNS, USER_CONSTRAINTS
                        //WHERE ((USER_CONS_COLUMNS.CONSTRAINT_NAME = USER_CONSTRAINTS.CONSTRAINT_NAME)
                        // AND (USER_CONS_COLUMNS.TABLE_NAME = ?)) AND (USER_CONSTRAINTS.CONSTRAINT_TYPE = 'P')

                        org.apache.commons.logging.LogFactory.getLog(getClass()).info("query:" + t_Query);

                        t_rsResults = t_Query.executeQuery();

                        while  (   (t_rsResults != null)
                                && (t_rsResults.next()))
                        {
                            addPrimaryKey(
                                t_astrTableNames[t_iTableIndex],
                                t_rsResults.getString(
                                    OracleTableRepository.USER_CONS_COLUMNS.COLUMN_NAME.toSimplifiedString()));
                        }
                    }
                    catch  (SQLException sqlException)
                    {
                        throw
                            new QueryJException(
                                "cannot.retrieve.primary.keys",
                                sqlException);
                    }
                }
            }
            catch  (QueryJException queryjException)
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
                    LogFactory.getLog(getClass()).error(
                        "cannot.close.result.set",
                        sqlException);
                }

                try 
                {
                    if  (t_PreparedStatement != null)
                    {
                        t_PreparedStatement.close();
                    }
                }
                catch  (SQLException sqlException)
                {
                    LogFactory.getLog(getClass()).error(
                        "cannot.close.statement",
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
     * @throws SQLException if any kind of SQL exception occurs.
     * @throws QueryJException if any other error occurs.
     */
    protected void extractForeignKeys(
            DatabaseMetaData metaData,
            String           catalog,
            String           schema)
        throws  SQLException,
                QueryJException
    {
        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        QueryResultSet t_Results = null;

        PreparedStatement t_PreparedStatement = null;

        String[] t_astrTableNames =
            getTableNames(metaData, catalog, schema);

        if  (   (t_astrTableNames != null)
             && (metaData         != null)
             && (t_QueryFactory   != null))
        {
            try
            {
                for  (int t_iTableIndex = 0;
                          t_iTableIndex < t_astrTableNames.length;
                          t_iTableIndex++) 
                {
                    try
                    {
                        SelectQuery t_Query = t_QueryFactory.createSelectQuery();

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
                            .and(COL.TABLE_NAME.equals()));

                        t_PreparedStatement = t_Query.prepareStatement(metaData.getConnection());

                        t_Query.setString(
                            COL.TABLE_NAME.equals(),
                            t_astrTableNames[t_iTableIndex]);

                        t_Results = (QueryResultSet) t_Query.executeQuery();

                        while  (   (t_Results != null)
                                && (t_Results.next()))
                        {
                            addForeignKey(
                                t_astrTableNames[t_iTableIndex],
                                t_Results.getString(
                                    RCOL.COLUMN_NAME),
                                t_Results.getString(
                                    CON.TABLE_NAME),
                                t_Results.getString(
                                    COL.COLUMN_NAME));
                        }
                    }
                    catch  (SQLException sqlException)
                    {
                        throw
                            new QueryJException(
                                "cannot.retrieve.foreign.keys",
                                sqlException);
                    }
                }
            }
            catch  (QueryJException queryjException)
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
                catch  (SQLException sqlException)
                {
                    LogFactory.getLog(getClass()).error(
                        "cannot.close.result.set",
                        sqlException);
                }

                try 
                {
                    if  (t_PreparedStatement != null)
                    {
                        t_PreparedStatement.close();
                    }
                }
                catch  (SQLException sqlException)
                {
                    LogFactory.getLog(getClass()).error(
                        "cannot.close.statement",
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
     * @return the list of all table names.
     * @throws SQLException if the database operation fails.
     */
    protected String[] getTableNames(
            DatabaseMetaData metaData,
            String           catalog,
            String           schema)
        throws  SQLException,
                QueryJException
    {
        String[] result = EMPTY_STRING_ARRAY;

        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        Connection t_Connection = null;

        if  (metaData != null)
        {
            t_Connection = metaData.getConnection();
        }

        if  (   (t_Connection   != null)
             && (t_QueryFactory != null))
        {
            t_QueryFactory = QueryFactory.getInstance();

            QueryResultSet t_Results = null;

            PreparedStatement t_PreparedStatement = null;

            try
            {
                try
                {
                    SelectQuery t_Query = t_QueryFactory.createSelectQuery();

                    t_Query.select(OracleTableRepository.USER_TABLES.TABLE_NAME);

                    t_Query.from(OracleTableRepository.USER_TABLES);

                    t_PreparedStatement = t_Query.prepareStatement(t_Connection);

                    t_Results = t_Query.retrieveMatchingResults();
                }
                catch  (SQLException sqlException)
                {
                    throw
                        new QueryJException(
                            "cannot.retrieve.database.table.names",
                            sqlException);
                }

                result = extractTableNames(t_Results);
            }
            catch  (SQLException sqlException)
            {
                throw sqlException;
            }
            catch  (QueryJException queryjException)
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
                catch  (SQLException sqlException)
                {
                    LogFactory.getLog(getClass()).error(
                        "cannot.close.result.set",
                        sqlException);
                }

                try 
                {
                    if  (t_PreparedStatement != null)
                    {
                        t_PreparedStatement.close();
                    }
                }
                catch  (SQLException sqlException)
                {
                    LogFactory.getLog(getClass()).error(
                        "cannot.close.statement",
                        sqlException);
                }
            }
        }

        return result;
    }

    /**
     * Extracts the table names from given result set.
     * @param resultSet the result set with the table information.
     * @return the list of table names.
     * @throws SQLException if the database operation fails.
     */
    protected String[] extractTableNames(QueryResultSet resultSet)
        throws  SQLException
    {
        String[] result = EMPTY_STRING_ARRAY;

        if  (resultSet != null)
        {
            result =
                extractStringFields(
                    resultSet,
                    OracleTableRepository.USER_TABLES.TABLE_NAME);
        }

        return result;
    }

    /**
     * Retrieves the column names from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @return the list of all column names.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if the any other error occurs.
     */
    protected String[] getColumnNames(
            DatabaseMetaData metaData,
            String           catalog,
            String           schema,
            String           tableName)
        throws  SQLException,
                QueryJException
    {
        String[] result = EMPTY_STRING_ARRAY;

        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        Connection t_Connection = null;

        if  (metaData != null)
        {
            t_Connection = metaData.getConnection();
        }

        if  (   (t_Connection   != null)
             && (t_QueryFactory != null))
        {
            t_QueryFactory = QueryFactory.getInstance();

            ResultSet t_rsResults = null;

            PreparedStatement t_PreparedStatement = null;

            try
            {
                try
                {
                    SelectQuery t_Query = t_QueryFactory.createSelectQuery();

                    t_Query.select(OracleTableRepository.USER_COL_COMMENTS.COLUMN_NAME);

                    // t_Query.select(OracleTableRepository.USER_COL_COMMENTS.COMMENTS);

                    t_Query.from(OracleTableRepository.USER_COL_COMMENTS);

                    t_Query.where(OracleTableRepository.USER_COL_COMMENTS.TABLE_NAME.equals());

                    t_PreparedStatement = t_Query.prepareStatement(t_Connection);

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
                catch  (SQLException sqlException)
                {
                    throw
                        new QueryJException(
                            "cannot.retrieve.database.column.names",
                            sqlException);
                }

               result = extractColumnNames(t_rsResults);
            }
            catch  (SQLException sqlException)
            {
                throw sqlException;
            }
            catch  (QueryJException queryjException)
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
                    LogFactory.getLog(getClass()).error(
                        "cannot.close.result.set",
                        sqlException);
                }

                try 
                {
                    if  (t_PreparedStatement != null)
                    {
                        t_PreparedStatement.close();
                    }
                }
                catch  (SQLException sqlException)
                {
                    LogFactory.getLog(getClass()).error(
                        "cannot.close.statement",
                        sqlException);
                }
            }
        }

        return result;
    }

    /**
     * Extracts the column names from given result set.
     * @param resultSet the result set with the table information.
     * @return the list of column names.
     * @throws SQLException if the database operation fails.
     */
    protected String[] extractColumnNames(ResultSet resultSet)
        throws  SQLException
    {
        String[] result = EMPTY_STRING_ARRAY;

        if  (resultSet != null)
        {
            result =
                extractStringFields(
                    resultSet,
                    OracleTableRepository.USER_COL_COMMENTS.COLUMN_NAME);
        }

        return result;
    }

    /**
     * Retrieves the column types from given table name.
     * @param metaData the metadata.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param tableName the table name.
     * @param size the number of fields to extract.
     * @return the list of all column types.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if any other error occurs.
     */
    protected int[] getColumnTypes(
            DatabaseMetaData metaData,
            String           catalog,
            String           schema,
            String           tableName,
            int              size)
        throws  SQLException,
                QueryJException
    {
        int[] result = EMPTY_INT_ARRAY;

        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        Connection t_Connection = null;

        if  (metaData != null)
        {
            t_Connection = metaData.getConnection();
        }

        if  (   (t_Connection   != null)
             && (t_QueryFactory != null))
        {
            t_QueryFactory = QueryFactory.getInstance();

            ResultSet t_rsResults = null;

            PreparedStatement t_PreparedStatement = null;

            try
            {
                try
                {
                    SelectQuery t_Query = t_QueryFactory.createSelectQuery();

                    t_Query.select(OracleTableRepository.USER_TAB_COLUMNS.DATA_TYPE);

                    t_Query.from(OracleTableRepository.USER_TAB_COLUMNS);

                    t_Query.where(OracleTableRepository.USER_TAB_COLUMNS.TABLE_NAME.equals());

                    t_PreparedStatement = t_Query.prepareStatement(t_Connection);

                    t_Query.setString(
                        OracleTableRepository.USER_TAB_COLUMNS.TABLE_NAME.equals(),
                        tableName);

                    t_rsResults = t_Query.executeQuery();
                }
                catch  (SQLException sqlException)
                {
                    throw
                        new QueryJException(
                            "cannot.retrieve.database.column.types",
                            sqlException);
                }

                result = extractColumnTypes(t_rsResults, OracleTableRepository.USER_TAB_COLUMNS.DATA_TYPE);
            }
            catch  (SQLException sqlException)
            {
                throw sqlException;
            }
            catch  (QueryJException queryjException)
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
                    LogFactory.getLog(getClass()).error(
                        "cannot.close.result.set",
                        sqlException);
                }

                try 
                {
                    if  (t_PreparedStatement != null)
                    {
                        t_PreparedStatement.close();
                    }
                }
                catch  (SQLException sqlException)
                {
                    LogFactory.getLog(getClass()).error(
                        "cannot.close.statement",
                        sqlException);
                }
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
     */
    protected int[] extractColumnTypes(ResultSet resultSet, String fieldName)
        throws  SQLException
    {
        int[] result = EMPTY_INT_ARRAY;

        if  (resultSet != null) 
        {
            String[] t_astrTypes = extractStringFields(resultSet, fieldName);

            MetaDataUtils t_MetaDataUtils = MetaDataUtils.getInstance();

            if  (   (t_astrTypes     != null)
                 && (t_MetaDataUtils != null))
            {
                result = new int[t_astrTypes.length];

                for  (int t_iIndex = 0;
                          t_iIndex < t_astrTypes.length;
                          t_iIndex++) 
                {
                    result[t_iIndex] = t_MetaDataUtils.getJavaType(t_astrTypes[t_iIndex]);
                }
            }
        }

        return result;
    }
}
