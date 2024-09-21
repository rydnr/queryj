/*
                        QueryJ Core

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
 * Description: Overrides JdbcMetadataManager in order to retrieve Oracle
 * dictionary information when using the standard DatabaseMetaData
 * instance does provide no information.
 *
 * Date: 6/8/12
 * Time: 5:08 PM
 *
 */
package org.acmsl.queryj.metadata.engines.oracle;

/*
 * Importing some project classes.
 */
import org.acmsl.commons.utils.Chronometer;
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.api.exceptions.QueryJException;
import org.acmsl.queryj.metadata.MetadataExtractionListener;
import org.acmsl.queryj.metadata.MetadataTypeManager;
import org.acmsl.queryj.metadata.engines.Engine;
import org.acmsl.queryj.metadata.engines.JdbcMetadataManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.AttributeIncompleteValueObject;
import org.acmsl.queryj.metadata.vo.ForeignKeyIncompleteValueObject;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.metadata.vo.TableIncompleteValueObject;
import org.acmsl.queryj.api.MetaLanguageUtils;

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
import java.io.Serializable;
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
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Overrides {@link org.acmsl.queryj.metadata.engines.JdbcMetadataManager}
 * in order to retrieve Oracle dictionary information when using the
 * standard {@link java.sql.DatabaseMetaData} instance does provide no information.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/08
 */
@ThreadSafe
public class OracleMetadataManager
    extends JdbcMetadataManager
    implements  OracleTableRepository,
                Serializable
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -914170490377897216L;

    /**
     * String literal: "left outer join ( ";
     */
    public static final String LEFT_OUTER_JOIN = "left outer join ( ";

    /**
     * Creates a {@link org.acmsl.queryj.metadata.engines.AbstractJdbcMetadataManager} with given information.
     * @param metadata the {@link java.sql.DatabaseMetaData} instance.
     * @param metadataExtractionListener the {@link org.acmsl.queryj.metadata.MetadataExtractionListener} instance.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @param tableNames the table names.
     * @param tables the list of tables.
     * @param disableTableExtraction whether to disable table extraction or not.
     * @param lazyTableExtraction whether to retrieve table information on demand.
     * @param caseSensitive whether it's case sensitive.
     * @param engine the engine.
     */
    public OracleMetadataManager(
        @NotNull final DatabaseMetaData metadata,
        @NotNull final MetadataExtractionListener metadataExtractionListener,
        @Nullable final String catalog,
        @Nullable final String schema,
        @NotNull final List<String> tableNames,
        @NotNull final List<Table<String, Attribute<String>, List<Attribute<String>>>> tables,
        final boolean disableTableExtraction,
        final boolean lazyTableExtraction,
        final boolean caseSensitive,
        @NotNull final Engine<String> engine)
    {
        super(
            Literals.ORACLE,
            metadata,
            metadataExtractionListener,
            catalog,
            schema,
            tableNames,
            tables,
            disableTableExtraction,
            lazyTableExtraction,
            caseSensitive,
            engine);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    @SuppressWarnings("unused")
    protected List<Table<String, Attribute<String>, List<Attribute<String>>>> extractTableMetadata(
        @Nullable final List<String> tableNames,
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema,
        final boolean caseSensitiveness,
        @NotNull final MetadataExtractionListener metadataExtractionListener,
        @NotNull final MetaLanguageUtils metaLanguageUtils)
      throws SQLException,
             QueryJException
    {
        return
            extractTableMetadata(
                tableNames,
                metaData.getConnection(),
                caseSensitiveness,
                metadataExtractionListener,
                metaLanguageUtils,
                getMetadataTypeManager());
    }

    /**
     * Processes the schema.
     * @param tableNames the table names.
     * @param connection the database connection.
     * @param caseSensitiveness whether the checks are case sensitive or not.
     * @param metadataExtractionListener the metadata extraction listener.
     * @param metaLanguageUtils the {@link MetaLanguageUtils} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return the list of all table names.
     * @throws SQLException if the extraction fails.
     * @throws QueryJException if any other error occurs.
     */
    @NotNull
    @SuppressWarnings("unused")
    protected List<Table<String, Attribute<String>, List<Attribute<String>>>> extractTableMetadata(
        @Nullable final List<String> tableNames,
        @NotNull final Connection connection,
        final boolean caseSensitiveness,
        @NotNull final MetadataExtractionListener metadataExtractionListener,
        @NotNull final MetaLanguageUtils metaLanguageUtils,
        @NotNull final MetadataTypeManager metadataTypeManager)
      throws SQLException,
             QueryJException
    {
        @NotNull final List<Table<String, Attribute<String>, List<Attribute<String>>>> result;

        @Nullable final Log t_Log = UniqueLogFactory.getLog(OracleMetadataManager.class);

        @Nullable SQLException sqlExceptionToThrow = null;

        @Nullable ResultSet t_rsResults = null;

        @Nullable PreparedStatement t_PreparedStatement = null;

        @Nullable TableIncompleteValueObject t_Table;

        @NotNull final Map<String, TableIncompleteValueObject> t_mTableMap =
            new HashMap<>();

        @NotNull final Map<String ,List<AttributeIncompleteValueObject>> t_mColumnMap =
            new HashMap<>();

        @NotNull final Map<String, List<AttributeIncompleteValueObject>> t_mPrimaryKeyMap =
            new HashMap<>();

        @NotNull final Map<String, List<ForeignKeyIncompleteValueObject>> t_mForeignKeyMap =
            new HashMap<>();

        @NotNull final Map<String, List<AttributeIncompleteValueObject>> t_mForeignKeyAttributeMap =
            new HashMap<>();

        try
        {
            @NotNull final String t_strQuery =
                  "select c.table_name, "
                +        "tc.comments table_comment, "
                +        "c.column_name, "
                +        "uc.comments column_comment, "
                +        "c.data_type, "
                +        "c.data_length, "
                +        "c.data_precision, "
                +        "c.data_scale, "
                +        "c.nullable, "
                +        "c.column_id, "
                +        "cons.position pk_position, "
                +        "fks.constraint_name fk_name, "
                +        "fks.target_table, "
                +        "fks.position fk_position "
                +   "from user_tab_comments tc, user_col_comments uc, "
                +         "user_tab_columns c "
                + LEFT_OUTER_JOIN
                +              "select ucc.* "
                +                "from user_cons_columns ucc, user_constraints uc "
                +               "where uc.constraint_type = 'P' and uc.status = 'ENABLED' "
                +                 "and uc.constraint_name = ucc.constraint_name) cons "
                +           "on c.table_name = cons.table_name and c.column_name = cons.column_name "
                + LEFT_OUTER_JOIN
                +              "select rcon.constraint_name, "
                +                     "col.position, "
                +                     "rcol.table_name source_table, "
                +                     "con.table_name target_table, "
                +                     "rcol.column_name "
                +                "from user_constraints con, "
                +                     "user_cons_columns col, "
                +                     "user_constraints rcon, "
                +                     "user_cons_columns rcol "
                +               "where rcon.constraint_type = 'R' "
                +                 "and rcon.r_constraint_name = con.constraint_name "
                +                 "and col.table_name = con.table_name "
                +                 "and col.constraint_name = con.constraint_name "
                +                 "and rcol.table_name = rcon.table_name "
                +                 "and rcol.constraint_name = rcon.constraint_name "
                +                 "and rcol.position = col.position) fks "
                +           "on c.table_name = fks.source_table and c.column_name = fks.column_name "
                +  "where instr(tc.table_name, '$') = 0 "
                +    "and tc.table_name = c.table_name "
                +    "and tc.table_name = uc.table_name "
                +    "and c.column_name = uc.column_name ";
            
            if  (   (t_Log != null)
                 && (t_Log.isDebugEnabled()))
            {
                t_Log.debug("query:" + t_strQuery);
            }

            t_PreparedStatement = connection.prepareStatement(t_strQuery);
        }
        catch (@NotNull final SQLException invalidQuery)
        {
            sqlExceptionToThrow = invalidQuery;
        }

        if (t_PreparedStatement != null)
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

        if (t_rsResults != null)
        {
            try
            {
                while  (t_rsResults.next())
                {
                    processRow(
                        t_rsResults,
                        t_mTableMap,
                        t_mColumnMap,
                        t_mPrimaryKeyMap,
                        t_mForeignKeyMap,
                        t_mForeignKeyAttributeMap,
                        caseSensitiveness,
                        metaLanguageUtils,
                        metadataTypeManager);
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

        buildUpTables(
            t_mTableMap,
            t_mColumnMap,
            t_mPrimaryKeyMap,
            t_mForeignKeyMap,
            t_mForeignKeyAttributeMap,
            caseSensitiveness,
            metaLanguageUtils);

        result = cloneTables(t_mTableMap.values());

        return result;
    }

    /**
     * Builds the table structures.
     * @param tableMap the table map.
     * @param columnMap the column map.
     * @param primaryKeyMap the primary key map.
     * @param foreignKeyMap the foreign key map.
     * @param foreignKeyAttributeMap the foreign key attribute map.
     * @param caseSensitiveness whether it's case sensitive.
     * @param metaLanguageUtils the {@link MetaLanguageUtils} instance.
     */
    protected void buildUpTables(
        @NotNull final Map<String,TableIncompleteValueObject> tableMap,
        @NotNull final Map<String,List<AttributeIncompleteValueObject>> columnMap,
        @NotNull final Map<String,List<AttributeIncompleteValueObject>> primaryKeyMap,
        @NotNull final Map<String,List<ForeignKeyIncompleteValueObject>> foreignKeyMap,
        @NotNull final Map<String,List<AttributeIncompleteValueObject>> foreignKeyAttributeMap,
        final boolean caseSensitiveness,
        @NotNull final MetaLanguageUtils metaLanguageUtils)
    {
        @Nullable final Log t_Log = UniqueLogFactory.getLog(OracleMetadataManager.class);
        @Nullable Chronometer t_Chronometer = null;

        if (   (t_Log != null)
            && (t_Log.isInfoEnabled()))
        {
            t_Chronometer = new Chronometer();
            t_Log.info("Building up " + tableMap.size() + " tables ...");
        }

        for (@Nullable final TableIncompleteValueObject t_Table : tableMap.values())
        {
            if (t_Table != null)
            {
                @Nullable final List<AttributeIncompleteValueObject> t_lColumns = columnMap.get(t_Table.getName());

                if (t_lColumns != null)
                {
                    t_Table.setAttributes(toAttributeList(t_lColumns));
                }

                @Nullable final List<AttributeIncompleteValueObject> t_lPrimaryKeys = primaryKeyMap.get(t_Table.getName());

                if (t_lPrimaryKeys != null)
                {
                    t_Table.setPrimaryKey(toAttributeList(t_lPrimaryKeys));
                }

                @Nullable final List<ForeignKeyIncompleteValueObject> t_lForeignKeys = foreignKeyMap.get(t_Table
                                                                                                             .getName());

                if (t_lForeignKeys != null)
                {
                    for (@Nullable final ForeignKeyIncompleteValueObject t_ForeignKey : t_lForeignKeys)
                    {
                        if (t_ForeignKey != null)
                        {
                            final List<AttributeIncompleteValueObject> t_lForeignKeyAttributes =
                                foreignKeyAttributeMap.get(t_ForeignKey.getFkName());

                            if (t_lForeignKeyAttributes != null)
                            {
                                t_ForeignKey.setAttributes(toAttributeList(t_lForeignKeyAttributes));
                            }
                        }
                    }
                    t_Table.setForeignKeys(toForeignKeyList(t_lForeignKeys));
                }
            }
        }

        if (   (t_Log != null)
            && (t_Log.isInfoEnabled()))
        {
            t_Log.info("Table build up phase took " + t_Chronometer.now());

            t_Log.info("Processing table comments ...");
            t_Chronometer = new Chronometer();
        }

        // second round: fix table properties based on the table comments.
        processTableComments(tableMap.values(), metaLanguageUtils);

        if (   (t_Log != null)
            && (t_Log.isInfoEnabled()))
        {
            t_Log.info("Processing table comments took " + t_Chronometer.now());
            t_Log.info("Building hierarchy relationships ...");
            t_Chronometer = new Chronometer();
        }

        // third round: parent tables
        bindParentChildRelationships(tableMap.values(), caseSensitiveness, metaLanguageUtils);

        if (   (t_Log != null)
            && (t_Log.isInfoEnabled()))
        {
            t_Log.info("Hierarchy relationships took " + t_Chronometer.now());
            t_Log.info("Binding attributes ...");
            t_Chronometer = new Chronometer();
        }

        bindAttributes(tableMap.values(), columnMap);

        if (   (t_Log != null)
            && (t_Log.isInfoEnabled()))
        {
            t_Log.info("Attribute binding took " + t_Chronometer.now());
        }
    }

    /**
     * Processes the tables' comments.
     * @param tables the list of {@link Table tables}.
     * @param metaLanguageUtils the {@link MetaLanguageUtils} instance.
     */
    protected void processTableComments(
        @NotNull final Collection<TableIncompleteValueObject> tables,
        @NotNull final MetaLanguageUtils metaLanguageUtils)
    {
        for (@Nullable final TableIncompleteValueObject t_Table : tables)
        {
            if (t_Table != null)
            {
                @Nullable final String t_strComment = t_Table.getComment();

                if (t_strComment != null)
                {
                    if (metaLanguageUtils.isStatic(t_strComment))
                    {
                        @Nullable final Attribute<String> attribute =
                            findAttribute(
                                metaLanguageUtils.retrieveStaticAttribute(t_strComment),
                                t_Table.getAttributes(),
                                false);

                        if (attribute != null)
                        {
                            t_Table.setStaticAttribute(attribute);
                        }
                    }
                    if (metaLanguageUtils.retrieveTableDecorator(t_strComment))
                    {
                        t_Table.setVoDecorated(true);
                    }
                }
            }
        }
    }

    /**
     * Process given row.
     * @param resultSet the result set.
     * @param tableMap the map with the temporary table results.
     * @param columnMap the map with the temporary column results.
     * @param primaryKeyMap the map with the temporary primary keys.
     * @param foreignKeyMap the map with the temporary foreign keys.
     * @param foreignKeyAttributeMap the map with the temporary foreign key attributes.
     * @param caseSensitiveness whether the engine is case sensitive or not.
     * @param metaLanguageUtils the {@link MetaLanguageUtils} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @throws SQLException if the process fails.
     */
    @SuppressWarnings("unused,unchecked")
    protected void processRow(
        @NotNull final ResultSet resultSet,
        @NotNull final Map<String,TableIncompleteValueObject> tableMap,
        @NotNull final Map<String,List<AttributeIncompleteValueObject>> columnMap,
        @NotNull final Map<String,List<AttributeIncompleteValueObject>> primaryKeyMap,
        @NotNull final Map<String,List<ForeignKeyIncompleteValueObject>> foreignKeyMap,
        @NotNull final Map<String,List<AttributeIncompleteValueObject>> foreignKeyAttributeMap,
        final boolean caseSensitiveness,
        @SuppressWarnings("unused") @NotNull final MetaLanguageUtils metaLanguageUtils,
        @NotNull final MetadataTypeManager metadataTypeManager)
      throws SQLException
    {
        @NotNull final String t_strTableName = resultSet.getString(Literals.TABLE_NAME_U);
        @Nullable final String t_strTableComment = resultSet.getString("TABLE_COMMENT");
        @NotNull final String t_strColumnName = resultSet.getString(Literals.COLUMN_NAME_U);
        @Nullable final String t_strColumnComment = resultSet.getString("COLUMN_COMMENT");
        @NotNull final String t_strType = resultSet.getString(Literals.DATA_TYPE1);
        final int t_iLength = resultSet.getInt("DATA_LENGTH");
        @Nullable final Integer t_iPrecision = resultSet.getInt("DATA_PRECISION");
        //Integer t_iScale = resultSet.getInt("DATA_SCALE");
        final boolean t_bNullable = "Y".equalsIgnoreCase(resultSet.getString(Literals.NULLABLE));
        final int t_iOrdinalPosition = resultSet.getInt(Literals.COLUMN_ID1);
        @Nullable final Integer t_iPkPosition = resultSet.getInt("PK_POSITION");
        @Nullable final String t_strFkName = resultSet.getString("FK_NAME");
        @Nullable final String t_strTargetTable = resultSet.getString("TARGET_TABLE");
        //Integer t_iFkPosition = resultSet.getInt("FK_POSITION");

        @Nullable TableIncompleteValueObject t_Table = tableMap.get(t_strTableName);

        if (t_Table == null)
        {
            t_Table = new TableIncompleteValueObject(t_strTableName, t_strTableComment);
            tableMap.put(t_strTableName, t_Table);
        }

        @Nullable List<AttributeIncompleteValueObject> t_lColumns = columnMap.get(t_strTableName);

        if (t_lColumns == null)
        {
            t_lColumns = new ArrayList<>();
            columnMap.put(t_strTableName, t_lColumns);
        }
        @NotNull final AttributeIncompleteValueObject t_CurrentAttribute =
            new AttributeIncompleteValueObject(
                t_strColumnName,
                metadataTypeManager.getJavaType(t_strType, t_iPrecision),
                t_strType,
                t_strTableName,
                t_strColumnComment,
                t_iOrdinalPosition,
                t_iLength,
                t_iPrecision,
                t_bNullable,
                null);

        t_lColumns.add(t_CurrentAttribute);

        if (t_iPkPosition != 0)
        {
            @Nullable List<AttributeIncompleteValueObject> t_lPrimaryKey = primaryKeyMap.get(t_strTableName);

            if (t_lPrimaryKey == null)
            {
                t_lPrimaryKey = new ArrayList<>(1);
                primaryKeyMap.put(t_strTableName, t_lPrimaryKey);
            }
            t_lPrimaryKey.add(t_CurrentAttribute);
        }

        if (t_strFkName != null)
        {
            @Nullable List<ForeignKeyIncompleteValueObject> t_lForeignKeys =
                foreignKeyMap.get(t_strTableName);

            if (t_lForeignKeys == null)
            {
                t_lForeignKeys = new ArrayList<>(1);
                foreignKeyMap.put(t_strTableName, t_lForeignKeys);
            }

            @Nullable List<AttributeIncompleteValueObject> t_lFkAttributes =
                foreignKeyAttributeMap.get(t_strFkName);

            if (t_lFkAttributes == null)
            {
                t_lFkAttributes = new ArrayList<>(1);
                foreignKeyAttributeMap.put(t_strFkName, t_lFkAttributes);
            }

            t_lFkAttributes.add(t_CurrentAttribute);

            @Nullable ForeignKeyIncompleteValueObject t_ForeignKey =
                findForeignKeyByName(t_strFkName, t_lForeignKeys, caseSensitiveness);

            if (t_ForeignKey == null)
            {
                t_ForeignKey =
                    new ForeignKeyIncompleteValueObject(
                        t_strFkName, t_strTableName, t_strTargetTable);

                t_lForeignKeys.add(t_ForeignKey);
            }
        }
    }

    /**
     * Finds a foreign key using the name.
     * @param name the foreign key name.
     * @param foreignKeys the list of {@link ForeignKeyIncompleteValueObject foreign keys}.
     * @param caseSensitiveness whether the match is case sensitive or not.
     * @return the foreign key with given name, or <code>null</code> if not found.
     */
    @Nullable
    protected ForeignKeyIncompleteValueObject findForeignKeyByName(
        @NotNull final String name,
        @NotNull final List<ForeignKeyIncompleteValueObject> foreignKeys,
        final boolean caseSensitiveness)
    {
        @Nullable ForeignKeyIncompleteValueObject result = null;

        for (@Nullable final ForeignKeyIncompleteValueObject t_ForeignKey : foreignKeys)
        {
            if (t_ForeignKey != null)
            {
                if (   (caseSensitiveness)
                    && (name.equals(t_ForeignKey.getFkName())))
                {
                    result = t_ForeignKey;
                    break;
                }
                else if (   (!caseSensitiveness)
                         && (name.equalsIgnoreCase(t_ForeignKey.getFkName())))
                {
                    result = t_ForeignKey;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInvalidColumnNameException(@NotNull final SQLException sqlException)
    {
        return sqlException.getErrorCode() == 17006;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInvalidColumnTypeException(@NotNull final SQLException sqlException)
    {
        return sqlException.getErrorCode() == 17004;
    }
}
