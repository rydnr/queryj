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
 * Filename: JdbcTableDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: MetadataManager-backed TableDAO instance.
 *
 * Date: 6/6/12
 * Time: 6:26 AM
 *
 */
package org.acmsl.queryj.metadata.engines;

/*
 * Importing ACM SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.MetaLanguageUtils;
import org.acmsl.queryj.api.dao.DAOTemplateUtils;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.MetadataUtils;
import org.acmsl.queryj.metadata.TableDAO;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.AttributeValueObject;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.metadata.vo.RowValueObject;
import org.acmsl.queryj.metadata.vo.Table;

/*
 * Importing Apache Commons Logging classes.
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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * {@link MetadataManager}-backed {@link TableDAO} instance.
 * @author <a href="mailto:queryj@acm-sl.org">chous</a>
 * @since 3.0
 * Created: 2012/06/06
 * @param <M> the metadata manager type.
 */
public abstract class MetadataManagerTableDAO<M extends MetadataManager>
    implements TableDAO
{
    /**
     * The {@link MetadataManager} instance.
     */
    private M m__MetadataManager;

    /**
     * The cache of DAO -&gt; Table.
     */
    private static final Map<String, Table<String, Attribute<String>, List<Attribute<String>>>> FIND_BY_DAO_CACHE =
        new HashMap<>();

    /**
     * The cache miss.
     */
    private static final Map<String, Boolean> FIND_BY_DAO_CACHE_MISS = new HashMap<>();

    /**
     * Creates a new {@link MetadataManagerTableDAO} instance using given {@link MetadataManager}.
     * @param manager the {@link MetadataManager} instance.
     */
    public MetadataManagerTableDAO(@NotNull final M manager)
    {
        immutableSetMetadataManager(manager);
    }

    /**
     * Specifies the {@link MetadataManager} instance.
     * @param manager the {@link MetadataManager manager}.
     */
    protected final void immutableSetMetadataManager(@NotNull final M manager)
    {
        m__MetadataManager = manager;
    }

    /**
     * Specifies the {@link MetadataManager} instance.
     * @param manager the {@link MetadataManager manager}.
     */
    @SuppressWarnings("unused")
    protected void setMetadataManager(@NotNull final M manager)
    {
        immutableSetMetadataManager(manager);
    }

    /**
     * Retrieves the {@link MetadataManager} instance.
     * @return such {@link MetadataManager instance}.
     */
    @NotNull
    protected MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Retrieves the table matching given name.
     * @param name the table name.
     * @return the associated {@link Table} instance, if the table is found.
     */
    @Override
    @Nullable
    public Table<String, Attribute<String>, List<Attribute<String>>> findByName(
        @NotNull final String name)
    {
        return findByName(name, getMetadataManager().isCaseSensitive());
    }

    /**
     * Retrieves the table matching given name.
     * @param name the table name.
     * @param caseSensitiveness whether the engine is case sensitive or not.
     * @return the associated {@link Table} instance, if the table is found.
     */
    @Nullable
    protected Table<String, Attribute<String>, List<Attribute<String>>> findByName(
        @NotNull final String name, final boolean caseSensitiveness)
    {
        @Nullable Table<String, Attribute<String>, List<Attribute<String>>> result = null;

        @NotNull final List<Table<String, Attribute<String>, List<Attribute<String>>>> t_Tables =
            findAllTables();

        for (@Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table : t_Tables)
        {
            if (t_Table != null)
            {
                if (   (caseSensitiveness)
                    && (t_Table.getName().equals(name)))
                {
                    result = t_Table;
                    break;
                }
                else if (   (!caseSensitiveness)
                         && (t_Table.getName().equalsIgnoreCase(name)))
                {
                    result = t_Table;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the list of tables with foreign keys to given table.
     * @param target the target table.
     * @return the list of referring tables.
     */
    @NotNull
    public List<Table<String, Attribute<String>, List<Attribute<String>>>> findReferringTables(
        @NotNull final String target)
    {
        @NotNull final List<Table<String, Attribute<String>, List<Attribute<String>>>> result =
            new ArrayList<Table<String, Attribute<String>, List<Attribute<String>>>>(1);

        @Nullable final List<Table<String, Attribute<String>, List<Attribute<String>>>> t_Tables = findAllTables();

        @NotNull List<ForeignKey<String>> t_lForeignKeys;

        for (@Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table : t_Tables)
        {
            if (t_Table != null)
            {
                t_lForeignKeys = t_Table.getForeignKeys();

                for (@Nullable final ForeignKey<String> t_ForeignKey : t_lForeignKeys)
                {
                    if (   (t_ForeignKey != null)
                        && (t_ForeignKey.getTargetTableName().equalsIgnoreCase(target)))
                    {
                        result.add(t_Table);
                        break;

                    }
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the table associated to given DAO.
     * @param dao the DAO name.
     * @return the table.
     */
    @Override
    @Nullable
    public Table<String, Attribute<String>, List<Attribute<String>>> findByDAO(
        @NotNull final String dao)
    {
        return findByDAO(dao, MetadataUtils.getInstance());
    }

    /**
     * Retrieves the {@link Table} associated to given DAO, from local cache.
     * @param dao the DAO.
     * @return such {@link Table}, or <code>null</code> if not found.
     */
    protected static Table<String, Attribute<String>, List<Attribute<String>>> getCachedByDAO(
        @NotNull final String dao)
    {
        return FIND_BY_DAO_CACHE.get(dao.toLowerCase(Locale.US));
    }

    /**
     * Caches given association between DAO and {@link Table}.
     * @param dao the DAO.
     * @param table the associated table.
     */
    protected synchronized static void cacheByDAO(
        @NotNull final String dao,
        @NotNull final Table<String, Attribute<String>, List<Attribute<String>>> table)
    {
        FIND_BY_DAO_CACHE.put(dao, table);
    }

    /**
     * Annotates a miss for given DAO.
     * @param dao the dao.
     */
    protected synchronized static void cacheByDAOMiss(@NotNull final String dao)
    {
        FIND_BY_DAO_CACHE_MISS.put(dao, Boolean.TRUE);
    }

    /**
     * Checks whether given DAO is known to have no associated {@link Table}.
     * @param dao the DAO to check.
     * @return <code>true</code> in such case.
     */
    protected synchronized boolean isDaoAlreadyProcessed(@NotNull final String dao)
    {
        boolean result = false;

        final Boolean miss = FIND_BY_DAO_CACHE_MISS.get(dao);

        if (miss != null)
        {
            result = miss;
        }

        return result;
    }

    /**
     * Retrieves the table associated to given DAO.
     * @param dao the DAO name.
     * @param metadataUtils the {@link MetadataUtils} instance.
     * @return the table.
     */
    @Nullable
    protected Table<String, Attribute<String>, List<Attribute<String>>> findByDAO(
        @NotNull final String dao, @NotNull final MetadataUtils metadataUtils)
    {
        @Nullable Table<String, Attribute<String>, List<Attribute<String>>> result =
            getCachedByDAO(dao);

        if (   (result == null)
            && (!isDaoAlreadyProcessed(dao)))
        {
            for (@Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table : findAllTables())
            {
                if  (   (t_Table != null)
                     && (metadataUtils.matches(t_Table.getName(), dao)))
                {
                    result = t_Table;
                    break;
                }
            }

            if (result != null)
            {
                cacheByDAO(dao, result);
            }
            else
            {
                cacheByDAOMiss(dao);
            }
        }

        return result;
    }

    /**
     * Retrieves the actual contents of given table.
     * @param tableName the table name.
     * @return the retrieved rows.
     */
    @Override
    @NotNull
    public List<Row<String>> queryContents(@NotNull final String tableName)
        throws SQLException
    {
        return queryContents(tableName, getMetadataManager());
    }

    /**
     * Queries the contents of given table.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @return the retrieved rows.
     * @throws SQLException if the contents cannot be retrieved.
     */
    @NotNull
    protected List<Row<String>> queryContents(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager)
        throws SQLException
    {
        return
            queryContents(
                tableName,
                metadataManager,
                MetaLanguageUtils.getInstance(),
                MetadataUtils.getInstance());
    }

    /**
     * Queries the contents of given table.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param metaLanguageUtils the {@link MetaLanguageUtils}  instance.
     * @param metadataUtils the {@link MetadataUtils} instance.
     * @return the retrieved rows.
     * @throws SQLException if the contents cannot be retrieved.
     */
    @NotNull
    protected List<Row<String>> queryContents(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetaLanguageUtils metaLanguageUtils,
        @NotNull final MetadataUtils metadataUtils)
        throws  SQLException
    {
        List<Row<String>> result = null;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(tableName);

        if (t_Table != null)
        {
            @Nullable final Attribute<String> t_StaticAttribute = t_Table.getStaticAttribute();

            @Nullable final String t_strStaticAttribute;

            if (t_StaticAttribute == null)
            {
                t_strStaticAttribute = metaLanguageUtils.retrieveStaticAttribute(t_Table.getComment());
            }
            else
            {
                t_strStaticAttribute = t_StaticAttribute.getName();
            }

            if (t_strStaticAttribute != null)
            {
                result =
                    queryContents(
                        tableName,
                        t_strStaticAttribute,
                        metadataUtils.retrieveAttributes(tableName, metadataManager),
                        metadataManager);
            }
        }

        if (result == null)
        {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * Queries the contents of given table.
     * @param tableName the table name.
     * @param staticAttributeName the name of the static attribute.
     * @param attributes the attributes.
     * @param metadataManager the metadata manager.
     * @return the retrieved rows.
     * @throws SQLException if the contents cannot be retrieved.
     */
    @NotNull
    public List<Row<String>> queryContents(
        @NotNull final String tableName,
        @Nullable final String staticAttributeName,
        @NotNull final List<Attribute<String>> attributes,
        @NotNull final MetadataManager metadataManager)
        throws  SQLException
    {
        return
            queryContents(
                tableName,
                staticAttributeName,
                attributes,
                metadataManager.getMetaData());
    }

    /**
     * Queries the contents of given table.
     * @param tableName the table name.
     * @param staticAttributeName the name of the static attribute.
     * @param attributes the attributes.
     * @param metaData the metadata.
     * @return the retrieved rows.
     * @throws SQLException if the contents cannot be retrieved.
     */
    @NotNull
    protected List<Row<String>> queryContents(
        @NotNull final String tableName,
        @Nullable final String staticAttributeName,
        @NotNull final List<Attribute<String>> attributes,
        @NotNull final DatabaseMetaData metaData)
        throws  SQLException
    {
        return
            queryContents(
                tableName,
                staticAttributeName,
                attributes,
                metaData.getConnection());
    }

    /**
     * Queries the contents of given table.
     * @param tableName the table name.
     * @param staticAttributeName the static attribute name.
     * @param attributes the attributes.
     * @param connection the connection.
     * @return the retrieved rows.
     * @throws SQLException if the contents cannot be retrieved.
     */
    @NotNull
    protected List<Row<String>> queryContents(
        @NotNull final String tableName,
        @Nullable final String staticAttributeName,
        @NotNull final List<Attribute<String>> attributes,
        @NotNull final Connection connection)
        throws  SQLException
    {
        // TODO: Move this to TableDAO
        @NotNull final List<Row<String>> result = new ArrayList<>();

        @Nullable final Log t_Log = UniqueLogFactory.getLog(DAOTemplateUtils.class);

        final int t_iColumnCount = attributes.size();

        @Nullable ResultSet t_rsResults = null;

        @Nullable PreparedStatement t_PreparedStatement = null;

        try
        {
            t_PreparedStatement =
                connection.prepareStatement(
                    "select * from " + tableName);

            t_rsResults = t_PreparedStatement.executeQuery();

            @NotNull final String[] t_astrColumnNames = new String[t_iColumnCount];

            @NotNull final String[] t_astrColumnValues = new String[t_iColumnCount];

            @Nullable String t_strRowName;

            if  (t_rsResults != null)
            {
                while  (t_rsResults.next())
                {
                    t_strRowName = null;

                    @NotNull final ResultSetMetaData t_rsMetaData =
                        t_rsResults.getMetaData();

                    int t_iArrayIndex;

                    for  (int t_iIndex = 1;
                          t_iIndex <= t_iColumnCount;
                          t_iIndex++)
                    {
                        t_iArrayIndex = t_iIndex - 1;

                        t_astrColumnNames[t_iArrayIndex] =
                            t_rsMetaData.getColumnName(t_iIndex);

                        t_astrColumnValues[t_iArrayIndex] =
                            t_rsResults.getString(t_iIndex);

                        if  (staticAttributeName.equalsIgnoreCase(
                            t_astrColumnNames[t_iArrayIndex]))
                        {
                            t_strRowName = t_astrColumnValues[t_iArrayIndex];
                        }
                    }

                    reorderAttributes(
                        attributes, t_astrColumnNames, t_astrColumnValues);

                    @NotNull final List<Attribute<String>> t_lAttributes = new ArrayList<>(t_astrColumnValues.length);

                    Attribute<String> t_NewAttribute;

                    for (int t_iIndex = 0; t_iIndex < t_astrColumnValues.length; t_iIndex++)
                    {
                        @Nullable final Attribute<String> t_Attribute = attributes.get(t_iIndex);

                        if (t_Attribute != null)
                        {
                            t_NewAttribute =
                                new AttributeValueObject(
                                    t_Attribute.getName(),
                                    t_Attribute.getTypeId(),
                                    t_Attribute.getType(),
                                    t_Attribute.getTableName(),
                                    t_Attribute.getComment(),
                                    t_Attribute.getOrdinalPosition(),
                                    t_Attribute.getLength(),
                                    t_Attribute.getPrecision(),
                                    t_Attribute.getKeyword(),
                                    t_Attribute.getRetrievalQuery(),
                                    t_Attribute.getSequence(),
                                    t_Attribute.isNullable(),
                                    t_astrColumnValues[t_iIndex],
                                    t_Attribute.isReadOnly(),
                                    t_Attribute.isBoolean(),
                                    t_Attribute.getBooleanTrue(),
                                    t_Attribute.getBooleanFalse(),
                                    t_Attribute.getBooleanNull());

                            t_lAttributes.add(t_NewAttribute);
                        }
                    }

                    result.add(
                        buildRow(
                            t_strRowName,
                            tableName,
                            t_lAttributes));
                }
            }
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
            catch  (@NotNull final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the ResultSet.",
                        sqlException);
                }
            }

            try
            {
                if  (t_PreparedStatement != null)
                {
                    t_PreparedStatement.close();
                }
            }
            catch  (@NotNull final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the PreparedStatement.",
                        sqlException);
                }
            }
        }

        return result;
    }

    /**
     * Builds a row with given information.
     * @param rowName the row name.
     * @param tableName the table name.
     * @param attributes the attributes.
     * @return the row.
     */
    @NotNull
    protected Row<String> buildRow(
        @NotNull final String rowName,
        @NotNull final String tableName,
        @NotNull final List<Attribute<String>> attributes)
    {
        return new RowValueObject(rowName, tableName, attributes);
    }

    /**
     * Reorders the attributes in the same order as the original ones.
     * @param attributes the original attributes.
     * @param names the retrieved attribute names.
     * @param values the retrieved attribute values.
     */
    protected void reorderAttributes(
        @Nullable final Collection<Attribute<String>> attributes,
        @NotNull final String[] names,
        @NotNull final String[] values)
    {
        @Nullable final Iterator<Attribute<String>> t_itAttributeIterator =
            (attributes != null) ? attributes.iterator() : null;

        if  (t_itAttributeIterator != null)
        {
            Attribute<String> t_CurrentAttribute;
            int t_iIndex = 0;
            int t_iPosition;
            final int t_iCount = attributes.size();

            @NotNull final String[] t_astrAuxNames = new String[t_iCount];
            @NotNull final String[] t_astrAuxValues = new String[t_iCount];

            while  (t_itAttributeIterator.hasNext())
            {
                t_CurrentAttribute = t_itAttributeIterator.next();

                t_iPosition =
                    retrieveAttributeIndex(t_CurrentAttribute.getName(), names);

                if  (   (t_iPosition >= 0)
                        && (t_iPosition < t_iCount))
                {
                    t_astrAuxNames[t_iIndex] = names[t_iPosition];
                    t_astrAuxValues[t_iIndex] = values[t_iPosition];
                }

                t_iIndex++;
            }

            copyArray(t_astrAuxNames, names);
            copyArray(t_astrAuxValues, values);
        }
    }

    /**
     * Retrieves the index of the attribute in given collection.
     * @param name the attribute name.
     * @param attributes the attributes.
     * @return such index.
     */
    protected int retrieveAttributeIndex(
        @NotNull final String name, @Nullable final String[] attributes)
    {
        int result = -1;

        final int t_iCount = (attributes != null) ? attributes.length : 0;

        String t_strAttribute;

        if (attributes != null)
        {
            for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                t_strAttribute = attributes[t_iIndex];

                if (name.equals(t_strAttribute))
                {
                    result = t_iIndex;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Copies given array.
     * @param source the source.
     * @param target the target.
     */
    protected void copyArray(@NotNull final String[] source, @NotNull final String[] target)
    {
        System.arraycopy(source, 0, target, 0, source.length);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"metadataManager\": \"" + m__MetadataManager.hashCode() + '"'
            + ", \"class\": \"MetadataManagerTableDAO\""
            + ", \"package\": \"org.acmsl.queryj.metadata.engines\" }";
    }
}
