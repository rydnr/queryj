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
 * Filename: OracleMetadataManager.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 6/8/12
 * Time: 5:08 PM
 *
 */
package org.acmsl.queryj.tools.metadata.engines.oracle;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.metadata.MetadataExtractionListener;
import org.acmsl.queryj.tools.metadata.engines.JdbcMetadataManager;
import org.acmsl.queryj.tools.metadata.vo.AttributeIncompleteValueObject;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.metadata.vo.Table;
import org.acmsl.queryj.tools.metadata.vo.TableIncompleteValueObject;
import org.acmsl.queryj.tools.templates.MetaLanguageUtils;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Apache Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Overrides {@link org.acmsl.queryj.tools.metadata.engines.JdbcMetadataManager}
 * in order to retrieve Oracle dictionary information when using the
 * standard {@link java.sql.DatabaseMetaData} instance does provide no information.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/08
 */
public class OracleMetadataManager
    extends JdbcMetadataManager
    implements  OracleTableRepository
{
    /**
     * Creates a {@link org.acmsl.queryj.tools.metadata.engines.AbstractJdbcMetadataManager} with given information.
     * @param name                       the manager name.
     * @param metadata                   the {@link java.sql.DatabaseMetaData} instance.
     * @param metadataExtractionListener the {@link org.acmsl.queryj.tools.metadata.MetadataExtractionListener}
     *                                   instance.
     * @param catalog                    the database catalog.
     * @param schema                     the database schema.
     * @param tableNames                 the table names.
     * @param tables                     the list of tables.
     * @param disableTableExtraction     whether to disable table extraction or not.
     * @param lazyTableExtraction        whether to retrieve table information on demand.
     * @param caseSensitive              whether it's case sensitive.
     * @param engineName                 the engine name.
     * @param engineVersion              the engine version.
     * @param quote                      the identifier quote string.
     */
    public OracleMetadataManager(
        @NotNull final String name,
        @NotNull final DatabaseMetaData metadata,
        @NotNull final MetadataExtractionListener metadataExtractionListener,
        @Nullable final String catalog,
        @Nullable final String schema,
        @NotNull final String[] tableNames,
        @NotNull final List<Table> tables,
        final boolean disableTableExtraction,
        final boolean lazyTableExtraction,
        final boolean caseSensitive,
        @NotNull final String engineName,
        @NotNull final String engineVersion,
        @NotNull final String quote)
    {
        super(
            name,
            metadata,
            metadataExtractionListener,
            catalog,
            schema,
            tableNames,
            tables,
            disableTableExtraction,
            lazyTableExtraction,
            caseSensitive,
            engineName,
            engineVersion,
            quote);
    }

    /**
     * Retrieves the table names.
     *
     * @param tableNames                 optionally specified table names.
     * @param metaData                   the metadata.
     * @param catalog                    the catalog.
     * @param schema                     the schema.
     * @param caseSensitiveness          whether it's case sensitive or not.
     * @param metadataExtractionListener the
     *                                   <code>MetadataExtractionListener</code> instance.
     * @param metaLanguageUtils          the {@link org.acmsl.queryj.tools.templates.MetaLanguageUtils} instance.
     * @return the list of tables.
     * @throws java.sql.SQLException if the database operation fails.
     * @throws org.acmsl.queryj.QueryJException
     *                               if an error, which is identified by QueryJ,
     *                               occurs.
     */
    @NotNull
    @Override
    protected List<Table> extractTableMetadata(
        @Nullable final String[] tableNames,
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema,
        final boolean caseSensitiveness,
        @NotNull final MetadataExtractionListener metadataExtractionListener,
        @NotNull final MetaLanguageUtils metaLanguageUtils)
      throws SQLException, QueryJException
    {
        return
            extractTableMetadata(
                tableNames,
                metaData.getConnection(),
                caseSensitiveness,
                metadataExtractionListener,
                metaLanguageUtils);
    }

    /**
     * Retrieves the table names.
     * @param tableNames the table names.
     * @param connection the database connection.
     * @param caseSensitiveness whether the checks are case sensitive or not.
     * @param metadataExtractionListener the metadata extraction listener.
     * @param metaLanguageUtils the {@link MetaLanguageUtils} instance.
     * @return the list of all table names.
     * @throws java.sql.SQLException if the database operation fails.
     */
    @NotNull
    @SuppressWarnings("unused")
    protected List<Table> extractTableMetadata(
        @Nullable final String[] tableNames,
        @NotNull final Connection connection,
        final boolean caseSensitiveness,
        @NotNull final MetadataExtractionListener metadataExtractionListener,
        @NotNull final MetaLanguageUtils metaLanguageUtils)
        throws SQLException, QueryJException
    {
        @NotNull final List<TableIncompleteValueObject> result = new ArrayList<TableIncompleteValueObject>();

        Log t_Log = UniqueLogFactory.getLog(OracleMetadataManager.class);

        @Nullable SQLException sqlExceptionToThrow = null;

        @Nullable ResultSet t_rsResults = null;

        @Nullable PreparedStatement t_PreparedStatement = null;

        @Nullable TableIncompleteValueObject t_Table;

        @NotNull final Map<String,TableIncompleteValueObject> t_mTableMap =
            new HashMap<String, TableIncompleteValueObject>();

        @NotNull final Map<String,List<AttributeIncompleteValueObject>> t_mColumnMap =
            new HashMap<String, List<AttributeIncompleteValueObject>>();

        @NotNull final Map<String,List<AttributeIncompleteValueObject>> t_mPrimaryKeyMap =
            new HashMap<String, List<AttributeIncompleteValueObject>>();

        @NotNull final Map<String,List<ForeignKey>> t_mForeignKeyMap =
            new HashMap<String, List<ForeignKey>>();

        try
        {
            @NotNull final String t_strQuery =
                  "select c.table_name,"
                +        "tc.comments table_comment,"
                +        "c.column_name,"
                +        "uc.comments column_comment,"
                +        "c.data_type,"
                +        "c.data_length,"
                +        "c.data_precision,"
                +        "c.data_scale,"
                +        "c.nullable,"
                +        "c.column_id,"
                +        "cons.position pk_position,"
                +        "fks.constraint_name fk_name,"
                +        "fks.target_table,"
                +        "fks.position fk_position"
                +   "from user_tab_comments tc, user_col_comments uc,"
                +         "user_tab_columns c "
                +         "left outer join ("
                +              "select ucc.* "
                +                "from user_cons_columns ucc, user_constraints uc"
                +               "where uc.constraint_type = 'P' and uc.status = 'ENABLED'"
                +                 "and uc.constraint_name = ucc.constraint_name) cons"
                +           "on c.table_name = cons.table_name and c.column_name = cons.column_name"
                +         "left outer join ("
                +              "select rcon.constraint_name,"
                +                     "col.position,"
                +                     "rcol.table_name source_table,"
                +                     "con.table_name target_table,"
                +                     "rcol.column_name"
                +                "from user_constraints con,"
                +                     "user_cons_columns col,"
                +                     "user_constraints rcon,"
                +                     "user_cons_columns rcol"
                +               "where rcon.constraint_type = 'R'"
                +                 "and rcon.r_constraint_name = con.constraint_name"
                +                 "and col.table_name = con.table_name"
                +                 "and col.constraint_name = con.constraint_name"
                +                 "and rcol.table_name = rcon.table_name"
                +                 "and rcol.constraint_name = rcon.constraint_name"
                +                 "and rcol.position = col.position) fks"
                +           "on c.table_name = fks.source_table and c.column_name = fks.column_name"
                +  "where tc.table_name = c.table_name"
                +    "and tc.table_name = uc.table_name"
                +    "and c.column_name = uc.column_name";
            
            if  (t_Log != null)
            {
                t_Log.debug("query:" + t_strQuery);
            }

            t_PreparedStatement = connection.prepareStatement(t_strQuery);
        }
        catch (@NotNull final SQLException invalidQuery)
        {
            sqlExceptionToThrow = invalidQuery;
        }

        if (   (t_PreparedStatement != null)
               && (sqlExceptionToThrow != null))
        {
            try
            {
                t_rsResults = t_PreparedStatement.executeQuery();
            }
            catch (@NotNull final SQLException queryFailed)
            {
                sqlExceptionToThrow = queryFailed;
            }
        }

        if (   (t_rsResults != null)
               && (sqlExceptionToThrow != null))
        {
            try
            {
                while  (t_rsResults.next())
                {
                    processRow(t_rsResults, t_mTableMap, t_mColumnMap, t_mPrimaryKeyMap, t_mForeignKeyMap);
                }
            }
            catch (@NotNull final SQLException errorIteratingResults)
            {
                sqlExceptionToThrow = errorIteratingResults;
            }
        }

        if (t_rsResults != null)
        {
            try
            {
                t_rsResults.close();
            }
            catch  (@NotNull final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the result set.",
                        sqlException);
                }
            }
        }

        if (t_PreparedStatement != null)
        {
            try
            {
                t_PreparedStatement.close();
            }
            catch  (@NotNull final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the statement.",
                        sqlException);
                }
            }
        }

        if (sqlExceptionToThrow != null)
        {
            throw sqlExceptionToThrow;
        }

        return cloneTables(result);
    }

    /**
     * Checks whether the engine requires specific CLOB handling.
     * @return <code>true</code> in such case.
     */
    @Override
    public boolean requiresCustomClobHandling()
    {
        return true;
    }

    /**
     * Process given row.
     * @param resultSet the result set.
     * @param tableMap the map with the temporary table results.
     * @param columnMap the map with the temporary column results.
     * @param primaryKeyMap the map with the temporary primary keys.
     * @param foreignKeyMap the map with the temporary foreign keys.
     * @throws SQLException if the {@link ResultSet} cannot be processed.
     */
    @Nullable
    protected void processRow(
        @NotNull final ResultSet resultSet,
        @NotNull final Map<String,TableIncompleteValueObject> tableMap,
        @NotNull final Map<String,List<AttributeIncompleteValueObject>> columnMap,
        @NotNull final Map<String,List<AttributeIncompleteValueObject>> primaryKeyMap,
        @NotNull final Map<String,List<ForeignKey>> foreignKeyMap)
      throws SQLException
    {
        String t_strTableName = resultSet.getString("TABLE_NAME");
        String t_strTableComment = resultSet.getString("TABLE_COMMENT");
        String t_strColumnName = resultSet.getString("COLUMN_NAME");
        String t_strColumnComment = resultSet.getString("COLUMN_COMMENT");
        int t_iTypeId = resultSet.getInt("DATA_TYPE");
        int t_iLength = resultSet.getInt("DATA_LENGTH");
        Integer t_iPrecision = resultSet.getInt("DATA_PRECISION");
        Integer t_iScale = resultSet.getInt("DATA_SCALE");
        boolean t_bNullable = "Y".equalsIgnoreCase(resultSet.getString("NULLABLE"));
        int t_iOrdinalPosition = resultSet.getInt("COLUMN_ID");
        Integer t_iPkPosition = resultSet.getInt("PK_POSITION");
        String t_strFkName = resultSet.getString("FK_NAME");
        String t_strTargetTable = resultSet.getString("TARGET_TABLE");
        Integer t_iFkPosition = resultSet.getInt("FK_POSITION");
    }
}
