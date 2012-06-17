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
 * Filename: DAOTemplateUtils.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides some useful methods when generating DAO classes
 *              via DAO template instances.
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.IdentifiableElement;
import org.acmsl.queryj.tools.customsql.ConnectionFlagsElement;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.CustomResultUtils;
import org.acmsl.queryj.tools.customsql.PropertyElement;
import org.acmsl.queryj.tools.customsql.PropertyRefElement;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.customsql.ResultSetFlagsElement;
import org.acmsl.queryj.tools.customsql.SqlElement;
import org.acmsl.queryj.tools.customsql.StatementFlagsElement;
import org.acmsl.queryj.tools.metadata.CachingRowDecorator;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.MetadataUtils;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.vo.Attribute;
import org.acmsl.queryj.tools.metadata.vo.AttributeValueObject;
import org.acmsl.queryj.tools.metadata.vo.Row;
import org.acmsl.queryj.tools.metadata.vo.Table;
import org.acmsl.queryj.tools.templates.MetaLanguageUtils;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;

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
import java.util.Iterator;
import java.util.List;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Provides some useful methods when generating DAO classes
 * via DAO template instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class DAOTemplateUtils
    implements  Singleton,
                Utils
{
    /**
     * An empty String array.
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * The default connection-flags filter.
     */
    public static final String DEFAULT_CONNECTION_FLAGS_FILTER =
        "connection-flags.default";

    /**
     * The connection-flags filter for <i>find-by-primary-key</i> operation.
     */
    public static final String FIND_BY_PRIMARY_KEY_CONNECTION_FLAGS_FILTER =
        "connection-flags.find-by-primary-key";

    /**
     * The default statement-flags filter.
     */
    public static final String DEFAULT_STATEMENT_FLAGS_FILTER =
        "statement-flags.default";

    /**
     * The statement-flags filter for <i>find-by-primary-key</i> operation.
     */
    public static final String FIND_BY_PRIMARY_KEY_STATEMENT_FLAGS_FILTER =
        "statement-flags.find-by-primary-key";

    /**
     * The default resultset-flags filter.
     */
    public static final String DEFAULT_RESULTSET_FLAGS_FILTER =
        "resultset-flags.default";

    /**
     * The resultset-flags filter for <i>find-by-primary-key</i> operation.
     */
    public static final String FIND_BY_PRIMARY_KEY_RESULTSET_FLAGS_FILTER =
        "resultset-flags.find-by-primary-key";

    /**
     * An empty, 9-element String array.
     */
    private static final String[] EMPTY_9_STRING_ARRAY = new String[9];

    /**
     * The statement flags setter array.
     */
    private static final String[] STATEMENT_FLAGS_SETTERS = new String[9];

    /**
     * The autogeneratedkeys index.
     */
    protected static final int AUTOGENERATEDKEYS_INDEX = 0;

    /**
     * The fetchsize index.
     */
    protected static final int FETCHSIZE_INDEX = 1;

    /**
     * The maxfieldsize index.
     */
    protected static final int MAXFIELDSIZE_INDEX = FETCHSIZE_INDEX + 1;

    /**
     * The maxrows index.
     */
    protected static final int MAXROWS_INDEX = MAXFIELDSIZE_INDEX + 1;

    /**
     * The querytimeout index.
     */
    protected static final int QUERYTIMEOUT_INDEX = MAXROWS_INDEX + 1;

    /**
     * The fetchdirection index.
     */
    protected static final int FETCHDIRECTION_INDEX = QUERYTIMEOUT_INDEX + 1;

    /**
     * The escapeprocessing index.
     */
    protected static final int ESCAPEPROCESSING_INDEX = FETCHDIRECTION_INDEX + 1;

    /**
     * The moreresults index.
     */
    protected static final int MORERESULTS_INDEX = ESCAPEPROCESSING_INDEX + 1;

    /**
     * The cursorname index.
     */
    protected static final int CURSORNAME_INDEX = MORERESULTS_INDEX + 1;

    /**
     * An empty, 3-element String array.
     */
    private static final String[] EMPTY_3_STRING_ARRAY = new String[3];

    /**
     * The ResultSet type index.
     */
    protected static final int TYPE_INDEX = 0;

    /**
     * The ResultSet concurrency index.
     */
    protected static final int CONCURRENCY_INDEX = 1;

    /**
     * The ResultSet holdability index.
     */
    protected static final int HOLDABILITY_INDEX = CONCURRENCY_INDEX + 1;

    static
    {
        STATEMENT_FLAGS_SETTERS[AUTOGENERATEDKEYS_INDEX] = null;
        STATEMENT_FLAGS_SETTERS[FETCHSIZE_INDEX] = "setFetchSize({0})";
        STATEMENT_FLAGS_SETTERS[MAXFIELDSIZE_INDEX] = "setMaxFieldSize({0})";
        STATEMENT_FLAGS_SETTERS[MAXROWS_INDEX] = "setMaxRows({0})";
        STATEMENT_FLAGS_SETTERS[QUERYTIMEOUT_INDEX] = "setQueryTimeout({0})";
        STATEMENT_FLAGS_SETTERS[FETCHDIRECTION_INDEX] = "setFetchDirection({0})";
        STATEMENT_FLAGS_SETTERS[ESCAPEPROCESSING_INDEX] = "setEscapeProcessing({0})";
        STATEMENT_FLAGS_SETTERS[MORERESULTS_INDEX] = null;
        STATEMENT_FLAGS_SETTERS[CURSORNAME_INDEX] = "setCursorName(\"{0}\")";
    }

    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class DAOTemplateUtilsSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final DAOTemplateUtils SINGLETON =
            new DAOTemplateUtils();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected DAOTemplateUtils() {}

    /**
     * Retrieves a DAOTemplateUtils instance.
     * @return such instance.
     */
    @NotNull
    public static DAOTemplateUtils getInstance()
    {
        return DAOTemplateUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the connection flags for find-by-primary-key operation.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @return the connection flags for given operation.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String[] retrieveConnectionFlagsForFindByPrimaryKeyOperation(
        @Nullable final CustomSqlProvider customSqlProvider)
    {
        @NotNull String result[] = EMPTY_STRING_ARRAY;

        @Nullable Collection<String> t_cResult = null;

        if  (customSqlProvider != null)
        {
            @NotNull Collection t_cConnectionFlags =
                filterConnectionFlags(
                    customSqlProvider.getCollection(),
                    FIND_BY_PRIMARY_KEY_CONNECTION_FLAGS_FILTER);

            if  (t_cConnectionFlags.size() == 0)
            {
                t_cConnectionFlags =
                    filterConnectionFlags(
                        customSqlProvider.getCollection(),
                        DEFAULT_CONNECTION_FLAGS_FILTER);
            }

            if  (t_cConnectionFlags.size() > 0)
            {
                Iterator t_itConnectionFlags = t_cConnectionFlags.iterator();

                t_cResult = new ArrayList<String>(t_cConnectionFlags.size());

                while  (t_itConnectionFlags.hasNext())
                {
                    t_cResult.add(
                        ((ConnectionFlagsElement) t_itConnectionFlags.next())
                            .getTransactionIsolation());
                }
            }
        }

        if  (t_cResult != null)
        {
            result = t_cResult.toArray(result);
        }

        return result;
    }

    /**
     * Filters the connection flags from given CustomSqlProvider contents.
     * @param contents such contents.
     * @param idFilter the id filter.
     * @return the connection flags.
     * @precondition contents != null
     */
    @NotNull
    protected Collection filterConnectionFlags(
        @NotNull final Collection<? extends IdentifiableElement> contents, final String idFilter)
    {
        return filterItems(contents, ConnectionFlagsElement.class, idFilter);
    }

    /**
     * Retrieves the statement flags for find-by-primary-key operation.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @return the statement flags for given operation.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String[] retrieveStatementFlagsForFindByPrimaryKeyOperation(
        @Nullable final CustomSqlProvider customSqlProvider)
    {
        @NotNull String result[] = EMPTY_9_STRING_ARRAY;

        if  (customSqlProvider != null)
        {
            @NotNull Collection t_cStatementFlags =
                filterStatementFlags(
                    customSqlProvider.getCollection(),
                    FIND_BY_PRIMARY_KEY_STATEMENT_FLAGS_FILTER);

            if  (t_cStatementFlags.size() == 0)
            {
                t_cStatementFlags =
                    filterStatementFlags(
                        customSqlProvider.getCollection(),
                        DEFAULT_STATEMENT_FLAGS_FILTER);
            }

            if  (t_cStatementFlags.size() > 0)
            {
                Iterator t_itStatementFlags = t_cStatementFlags.iterator();

                // There should be only one.
                @NotNull StatementFlagsElement t_Statement =
                    (StatementFlagsElement) t_itStatementFlags.next();

                result[AUTOGENERATEDKEYS_INDEX] =
                    t_Statement.getAutogeneratedKeys();

                @Nullable String t_strFetchSize = null;

                if  (t_Statement.getFetchSize() != null)
                {
                    t_strFetchSize = "" + t_Statement.getFetchSize();
                }

                result[FETCHSIZE_INDEX] = t_strFetchSize;

                @Nullable String t_strMaxFieldSize = null;

                if  (t_Statement.getMaxFieldSize() != null)
                {
                    t_strMaxFieldSize = "" + t_Statement.getMaxFieldSize();
                }

                result[MAXFIELDSIZE_INDEX] = t_strMaxFieldSize;

                @Nullable String t_strMaxRows = null;

                if  (t_Statement.getMaxRows() != null)
                {
                    t_strMaxRows = "" + t_Statement.getMaxRows();
                }

                result[MAXROWS_INDEX] = t_strMaxRows;

                @Nullable String t_strQueryTimeout = null;

                if  (t_Statement.getQueryTimeout() != null)
                {
                    t_strQueryTimeout = "" + t_Statement.getQueryTimeout();
                }

                result[QUERYTIMEOUT_INDEX] = t_strQueryTimeout;

                result[FETCHDIRECTION_INDEX] =
                    t_Statement.getFetchDirection();

                @Nullable String t_strEscapeProcessing = null;

                if  (t_Statement.getEscapeProcessing() != null)
                {
                    t_strEscapeProcessing =
                        "" + t_Statement.getEscapeProcessing();
                }

                result[ESCAPEPROCESSING_INDEX] = t_strEscapeProcessing;

                result[MORERESULTS_INDEX] = t_Statement.getMoreResults();

                result[CURSORNAME_INDEX] = t_Statement.getCursorName();
            }
        }

        return result;
    }

    /**
     * Retrieves the setter methods.
     * @return the setter methods.
     */
    @NotNull
    protected final String[] immutableRetrieveStatementFlagsSetters()
    {
        return STATEMENT_FLAGS_SETTERS;
    }

    /**
     * Retrieves the setter methods.
     * @return the setter methods.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String[] retrieveStatementFlagsSetters()
    {
        return clone(immutableRetrieveStatementFlagsSetters());
    }

    /**
     * Filters the statement flags from given CustomSqlProvider contents.
     * @param contents such contents.
     * @param idFilter the id filter.
     * @return the statement flags.
     * @precondition contents != null
     */
    @NotNull
    protected Collection filterStatementFlags(
        @NotNull final Collection contents, final String idFilter)
    {
        return filterItems(contents, StatementFlagsElement.class, idFilter);
    }

    /**
     * Retrieves the resultset flags for find-by-primary-key operation.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @return the resultset flags for given operation.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String[] retrieveResultSetFlagsForFindByPrimaryKeyOperation(
        @Nullable final CustomSqlProvider customSqlProvider)
    {
        @NotNull String result[] = EMPTY_3_STRING_ARRAY;

        if  (customSqlProvider != null)
        {
            @NotNull Collection t_cResultSetFlags =
                filterResultSetFlags(
                    customSqlProvider.getCollection(),
                    FIND_BY_PRIMARY_KEY_RESULTSET_FLAGS_FILTER);

            if  (t_cResultSetFlags.size() == 0)
            {
                t_cResultSetFlags =
                    filterResultSetFlags(
                        customSqlProvider.getCollection(),
                        DEFAULT_RESULTSET_FLAGS_FILTER);
            }

            if  (t_cResultSetFlags.size() > 0)
            {
                Iterator t_itResultSetFlags = t_cResultSetFlags.iterator();

                // There should be only one.
                @NotNull ResultSetFlagsElement t_ResultSet =
                    (ResultSetFlagsElement) t_itResultSetFlags.next();

                @Nullable String t_strType = null;

                if  (t_ResultSet.getType() != null)
                {
                    t_strType = t_ResultSet.getType();
                }

                result[TYPE_INDEX] = t_strType;

                @Nullable String t_strConcurrency = null;

                if  (t_ResultSet.getConcurrency() != null)
                {
                    t_strConcurrency = t_ResultSet.getConcurrency();
                }

                result[CONCURRENCY_INDEX] = t_strConcurrency;

                @Nullable String t_strHoldability = null;

                if  (t_ResultSet.getHoldability() != null)
                {
                    t_strHoldability = t_ResultSet.getHoldability();
                }

                result[HOLDABILITY_INDEX] = t_strHoldability;
            }
        }

        return result;
    }

    /**
     * Filters the resultset flags from given CustomSqlProvider contents.
     * @param contents such contents.
     * @param idFilter the id filter.
     * @return the resultset flags.
     * @precondition contents != null
     */
    @NotNull
    protected Collection filterResultSetFlags(
        @NotNull final Collection contents, final String idFilter)
    {
        return filterItems(contents, ResultSetFlagsElement.class, idFilter);
    }

    /**
     * Filters given CustomSqlProvider contents according to given class name
     * and filter (optional).
     * @param contents such contents.
     * @param itemClass the class to filter.
     * @param idFilter the id filter (optional).
     * @return the connection flags.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected <I extends IdentifiableElement> Collection<I> filterItems(
        @NotNull final Collection<? extends IdentifiableElement> contents,
        final Class itemClass,
        @Nullable final String idFilter)
    {
        @NotNull Collection<I> result = new ArrayList<I>(contents.size());

        for (final IdentifiableElement t_CurrentItem : contents)
        {
            if (   (t_CurrentItem != null)
                && (t_CurrentItem.getClass().isAssignableFrom(itemClass)))
            {
                if (   (idFilter == null)
                    || (filterById(t_CurrentItem, idFilter)))
                {
                    result.add((I) t_CurrentItem);
                }
            }
        }

        return result;
    }

    /**
     * Filters given element by its id.
     * @param element the element.
     * @param idFilter the filter.
     * @return <code>true</code> if both identifiers match.
     * @precondition element != null
     * @precondition idFilters != null
     */
    protected boolean filterById(
        @NotNull final IdentifiableElement element, @NotNull final String idFilter)
    {
        return idFilter.equalsIgnoreCase(element.getId());
    }

    /**
     * Finds all <code>SqlElement</code> instances associated to given
     * result element.
     * @param resultId such id.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @return all such entities.
     */
    @SuppressWarnings("unused")
    @NotNull
    public SqlElement[] findSqlElementsByResultId(
        @NotNull final String resultId,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return
            findSqlElementsByResultId(
                resultId,
                customSqlProvider,
                CustomResultUtils.getInstance());
    }

    /**
     * Finds all <code>SqlElement</code> instances associated to given
     * result element.
     * @param resultId such id.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param customResultUtils the <code>CustomResultUtils</code> instance.
     * @return all such entities.
     */
    @NotNull
    protected SqlElement[] findSqlElementsByResultId(
        @NotNull final String resultId,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final CustomResultUtils customResultUtils)
    {
        return
            customResultUtils.findSqlElementsByResultId(
                resultId, customSqlProvider);
    }

    /**
     * Checks whether given table name matches the DAO id.
     * @param tableName the table name.
     * @param daoId the DAO id.
     * @return <code>true</code> if they match.
     * @precondition tableName != null
     * @precondition daoId != null
     */
    public boolean matches(
        @NotNull final String tableName, @NotNull final String daoId)
    {
        return matches(tableName, daoId, CustomResultUtils.getInstance());
    }

    /**
     * Checks whether given table name matches the DAO id.
     * @param tableName the table name.
     * @param daoId the DAO id.
     * @param customResultUtils the <code>CustomResultUtils</code>
     * instance.
     * @return <code>true</code> if they match.
     * @precondition tableName != null
     * @precondition daoId != null
     * @precondition customResultUtils != null
     */
    protected boolean matches(
        @NotNull final String tableName,
        @NotNull final String daoId,
        @NotNull final CustomResultUtils customResultUtils)
    {
        return customResultUtils.matches(tableName, daoId);
    }

    /**
     * Retrieves all <code>SqlElement</code> instances of given type.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param type the type.
     * @return such elements.
     */
    @SuppressWarnings("unused")
    @NotNull
    public SqlElement[] retrieveSqlElementsByType(
        final CustomSqlProvider customSqlProvider,
        @NotNull final String type)
    {
        return
            retrieveSqlElementsByType(
                customSqlProvider, type, CustomResultUtils.getInstance());
    }

    /**
     * Retrieves all <code>SqlElement</code> instances of given type.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param customResultUtils the <code>CustomResultUtils</code> instance.
     * @param type the type.
     * @return such elements.
     */
    @NotNull
    protected SqlElement[] retrieveSqlElementsByType(
        final CustomSqlProvider customSqlProvider,
        @NotNull final String type,
        @NotNull final CustomResultUtils customResultUtils)
    {
        return
            customResultUtils.retrieveSqlElementsByType(
                customSqlProvider, type);
    }

    /**
     * Retrieves all <code>SqlElement</code> instances associated to
     * given result id.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param resultId the result id.
     * @return such elements.
     */
    @SuppressWarnings("unused")
    @NotNull
    public SqlElement[] retrieveSqlElementsByResultId(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String resultId)
    {
        return
            retrieveSqlElementsByResultId(
                customSqlProvider,
                resultId,
                CustomResultUtils.getInstance());
    }

    /**
     * Retrieves all <code>SqlElement</code> instances associated to
     * given result id.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param resultId the result id.
     * @param customResultUtils the <code>CustomResultUtils</code> instance.
     * @return such elements.
     */
    @NotNull
    protected SqlElement[] retrieveSqlElementsByResultId(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String resultId,
        @NotNull final CustomResultUtils customResultUtils)
    {
        return
            customResultUtils.retrieveSqlElementsByResultId(
                customSqlProvider, resultId);
    }

    /**
     * Retrieves all <code>Result</code> instances of given type.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param type the type.
     * @return such elements.
     */
    @SuppressWarnings("unused")
    @NotNull
    public Result[] retrieveResultsByType(
        @NotNull final CustomSqlProvider customSqlProvider, @NotNull final String type)
    {
        return
            retrieveResultsByType(
                customSqlProvider, type, CustomResultUtils.getInstance());
    }

    /**
     * Retrieves all <code>Result</code> instances of given type.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param type the type.
     * @param customResultUtils the <code>CustomResultUtils</code> instance.
     * @return such elements.
     */
    @NotNull
    protected Result[] retrieveResultsByType(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String type,
        @NotNull final CustomResultUtils customResultUtils)
    {
        return
            customResultUtils.retrieveResultsByType(
                customSqlProvider, type);
    }

    /**
     * Retrieves all <code>PropertyElement</code> instances associated to
     * given result element.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param resultElement the result element.
     * @return such elements.
     */
    @SuppressWarnings("unused")
    @NotNull
    public PropertyElement[] retrievePropertyElementsByResultId(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final Result resultElement)
    {
        @NotNull Collection<PropertyElement> t_cResult = new ArrayList<PropertyElement>();

        @Nullable PropertyElement t_Property;

        for (@Nullable PropertyRefElement t_PropertyRef : resultElement.getPropertyRefs())
        {
            if (t_PropertyRef != null)
            {
                t_Property = customSqlProvider.resolveReference(t_PropertyRef);

                if (t_Property != null)
                {
                    t_cResult.add(t_Property);
                }
            }
        }

        return t_cResult.toArray(new PropertyElement[t_cResult.size()]);
    }

    /**
     * Clones given String array.
     * @param array the array to clone.
     * @return the cloned array.
     * @precondition array != null
     */
    @NotNull
    protected String[] clone(@Nullable final String[] array)
    {
        @NotNull String[] result = EMPTY_STRING_ARRAY;

        int t_iCount = (array != null) ? array.length : 0;

        if  (t_iCount > 0)
        {
            result = new String[t_iCount];

            System.arraycopy(array, 0, result, 0, t_iCount);
        }

        return result;
    }

    /**
     * Queries the contents of given table.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the retrieved rows.
     * @throws SQLException if the operation fails.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    @Nullable
    public List<Row> queryContents(
        final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
      throws  SQLException
    {
        return
            queryContents(
                tableName,
                metadataManager,
                decoratorFactory,
                MetaLanguageUtils.getInstance(),
                MetadataUtils.getInstance());
    }

    /**
     * Queries the contents of given table.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param metaLanguageUtils the <code>MetaLanguageUtils</code> instance.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @return the retrieved rows.
     * @throws SQLException if the operation fails.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     * @precondition metaLanguageUtils != null
     * @precondition metadataUtils != null
     */
    @NotNull
    public List<Row> queryContents(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final MetaLanguageUtils metaLanguageUtils,
        @NotNull final MetadataUtils metadataUtils)
      throws  SQLException
    {
        List<Row> result = null;

        Table t_Table = metadataManager.getTableDAO().findByName(tableName);

        if (t_Table != null)
        {
            @Nullable String t_strStaticAttribute =
                metaLanguageUtils.retrieveStaticAttribute(t_Table.getComment());

            if (t_strStaticAttribute != null)
            {
                result =
                    queryContents(
                        tableName,
                        t_strStaticAttribute,
                        metadataUtils.retrieveAttributes(tableName, metadataManager),
                        metadataManager,
                        decoratorFactory);
            }
        }

        if (result == null)
        {
            result = new ArrayList<Row>(0);
        }

        return result;
    }

    /**
     * Queries the contents of given table.
     * @param tableName the table name.
     * @param staticAttributeName the name of the static attribute.
     * @param attributes the attributes.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the retrieved rows.
     * @throws SQLException if the operation fails.
     */
    @NotNull
    public List<Row> queryContents(
        @NotNull final String tableName,
        @Nullable final String staticAttributeName,
        @NotNull final List<Attribute> attributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
      throws  SQLException
    {
        return
            queryContents(
                tableName,
                staticAttributeName,
                attributes,
                metadataManager,
                metadataManager.getMetadataTypeManager(),
                decoratorFactory,
                metadataManager.getMetaData());
    }

    /**
     * Queries the contents of given table.
     * @param tableName the table name.
     * @param staticAttributeName the name of the static attribute.
     * @param attributes the attributes.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param metaData the metadata.
     * @return the retrieved rows.
     * @throws SQLException if the operation fails.
     */
    @NotNull
    protected List<Row> queryContents(
        @NotNull final String tableName,
        @Nullable final String staticAttributeName,
        @NotNull final List<Attribute> attributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DatabaseMetaData metaData)
      throws  SQLException
    {
        return
            queryContents(
                tableName,
                staticAttributeName,
                attributes,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                MetadataUtils.getInstance(),
                metaData.getConnection());
    }

    /**
     * Queries the contents of given table.
     * @param tableName the table name.
     * @param staticAttributeName the static attribute name.
     * @param attributes the attributes.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @param connection the connection.
     * @return the retrieved rows.
     * @throws SQLException if the operation fails.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected List<Row> queryContents(
        @NotNull final String tableName,
        @Nullable final String staticAttributeName,
        @NotNull final List<Attribute> attributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final MetadataUtils metadataUtils,
        @NotNull final Connection connection)
      throws  SQLException
    {
        @NotNull List<Row> result = new ArrayList<Row>();

        Log t_Log = UniqueLogFactory.getLog(DAOTemplateUtils.class);
        
        int t_iColumnCount = (attributes != null) ? attributes.size() : 0;

        @Nullable ResultSet t_rsResults = null;

        @Nullable PreparedStatement t_PreparedStatement = null;

        try
        {
            t_PreparedStatement =
                connection.prepareStatement(
                    "select * from " + tableName);

            t_rsResults = t_PreparedStatement.executeQuery();

            @NotNull String[] t_astrColumnNames = new String[t_iColumnCount];

            @NotNull String[] t_astrColumnValues = new String[t_iColumnCount];

            @Nullable String t_strRowName;

            if  (t_rsResults != null)
            {
                while  (t_rsResults.next())
                {
                    t_strRowName = null;

                    ResultSetMetaData t_rsMetaData =
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

                    List<Attribute> t_lAttributes = new ArrayList<Attribute>(t_astrColumnValues.length);

                    Attribute t_NewAttribute;

                    for (int t_iIndex = 0; t_iIndex < t_astrColumnValues.length; t_iIndex++)
                    {
                        Attribute t_Attribute = attributes.get(t_iIndex);

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
                            t_lAttributes,
                            metadataTypeManager));
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
                        "Cannot close the result set.",
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
                        "Cannot close the statement.",
                        sqlException);
                }
            }
        }

        if (result == null)
        {
            result = new ArrayList<Row>(0);
        }

        return result;
    }

    /**
     * Builds a row with given information.
     * @param rowName the row name.
     * @param tableName the table name.
     * @param attributes the attributes.
     * @param metadataTypeManager the metadata type manager.
     * @return the row.
     * @precondition rowName != null
     * @precondition tableName != null
     * @precondition attributes != null
     * @precondition metadatTypeManager != null
     */
    @NotNull
    protected Row buildRow(
        final String rowName,
        final String tableName,
        final Collection attributes,
        final MetadataTypeManager metadataTypeManager)
    {
        return
            new CachingRowDecorator(
                rowName, tableName, attributes, metadataTypeManager);
    }

    /**
     * Reorders the attributes in the same order as the original ones.
     * @param attributes the original attributes.
     * @param names the retrieved attribute names.
     * @param values the retrieved attribute values.
     */
    protected void reorderAttributes(
        @Nullable final Collection<Attribute> attributes,
        @NotNull final String[] names,
        @NotNull final String[] values)
    {
        @Nullable Iterator<Attribute> t_itAttributeIterator =
            (attributes != null) ? attributes.iterator() : null;

        if  (t_itAttributeIterator != null)
        {
            Attribute t_CurrentAttribute;
            int t_iIndex = 0;
            int t_iPosition;
            int t_iCount = attributes.size();

            @NotNull String[] t_astrAuxNames = new String[t_iCount];
            @NotNull String[] t_astrAuxValues = new String[t_iCount];

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

        int t_iCount = (attributes != null) ? attributes.length : 0;

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
}
