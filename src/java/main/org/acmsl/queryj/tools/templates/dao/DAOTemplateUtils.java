//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Filename: $RCSfile: $
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
import org.acmsl.queryj.tools.customsql.ResultRefElement;
import org.acmsl.queryj.tools.customsql.ResultSetFlagsElement;
import org.acmsl.queryj.tools.customsql.SqlElement;
import org.acmsl.queryj.tools.customsql.StatementFlagsElement;
import org.acmsl.queryj.tools.metadata.CachingRowDecorator;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.MetadataUtils;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.vo.Attribute;
import org.acmsl.queryj.tools.metadata.vo.Row;
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

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/**
 * Provides some useful methods when generating DAO classes
 * via DAO template instances.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
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
     * An empty PropertyElement array.
     */
    public static final PropertyElement[] EMPTY_PROPERTYELEMENT_ARRAY =
        new PropertyElement[0];

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
    protected static final int FETCHSIZE_INDEX = AUTOGENERATEDKEYS_INDEX + 1;

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
    protected static final int CONCURRENCY_INDEX = TYPE_INDEX + 1;

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
    protected DAOTemplateUtils() {};

    /**
     * Retrieves a DAOTemplateUtils instance.
     * @return such instance.
     */
    public static DAOTemplateUtils getInstance()
    {
        return DAOTemplateUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the connection flags for find-by-primary-key operation.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @return the connection flags for given operation.
     * @precondition customSqlProvider != null
     */
    public String[] retrieveConnectionFlagsForFindByPrimaryKeyOperation(
        final CustomSqlProvider customSqlProvider)
    {
        String result[] = EMPTY_STRING_ARRAY;

        Collection t_cResult = null;

        if  (customSqlProvider != null)
        {
            Collection t_cConnectionFlags =
                filterConnectionFlags(
                    customSqlProvider.getCollection(),
                    FIND_BY_PRIMARY_KEY_CONNECTION_FLAGS_FILTER);

            if  (   (t_cConnectionFlags == null)
                 || (t_cConnectionFlags.size() == 0))
            {
                t_cConnectionFlags =
                    filterConnectionFlags(
                        customSqlProvider.getCollection(),
                        DEFAULT_CONNECTION_FLAGS_FILTER);
            }

            if  (   (t_cConnectionFlags != null)
                 && (t_cConnectionFlags.size() > 0))
            {
                Iterator t_itConnectionFlags = t_cConnectionFlags.iterator();

                t_cResult = new ArrayList();

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
            result = (String[]) t_cResult.toArray(result);
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
    protected Collection filterConnectionFlags(
        final Collection contents, final String idFilter)
    {
        return filterItems(contents, ConnectionFlagsElement.class, idFilter);
    }

    /**
     * Retrieves the statement flags for find-by-primary-key operation.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @return the statement flags for given operation.
     * @precondition customSqlProvider != null
     */
    public String[] retrieveStatementFlagsForFindByPrimaryKeyOperation(
        final CustomSqlProvider customSqlProvider)
    {
        String result[] = EMPTY_9_STRING_ARRAY;

        if  (customSqlProvider != null)
        {
            Collection t_cStatementFlags =
                filterStatementFlags(
                    customSqlProvider.getCollection(),
                    FIND_BY_PRIMARY_KEY_STATEMENT_FLAGS_FILTER);

            if  (   (t_cStatementFlags == null)
                 || (t_cStatementFlags.size() == 0))
            {
                t_cStatementFlags =
                    filterStatementFlags(
                        customSqlProvider.getCollection(),
                        DEFAULT_STATEMENT_FLAGS_FILTER);
            }

            if  (   (t_cStatementFlags != null)
                 && (t_cStatementFlags.size() > 0))
            {
                Iterator t_itStatementFlags = t_cStatementFlags.iterator();

                // There should be only one.
                StatementFlagsElement t_Statement =
                    (StatementFlagsElement) t_itStatementFlags.next();

                result[AUTOGENERATEDKEYS_INDEX] =
                    t_Statement.getAutogeneratedKeys();

                String t_strFetchSize = null;

                if  (t_Statement.getFetchSize() != null)
                {
                    t_strFetchSize = "" + t_Statement.getFetchSize();
                }

                result[FETCHSIZE_INDEX] = t_strFetchSize;

                String t_strMaxFieldSize = null;

                if  (t_Statement.getMaxFieldSize() != null)
                {
                    t_strMaxFieldSize = "" + t_Statement.getMaxFieldSize();
                }

                result[MAXFIELDSIZE_INDEX] = t_strMaxFieldSize;

                String t_strMaxRows = null;

                if  (t_Statement.getMaxRows() != null)
                {
                    t_strMaxRows = "" + t_Statement.getMaxRows();
                }

                result[MAXROWS_INDEX] = t_strMaxRows;

                String t_strQueryTimeout = null;

                if  (t_Statement.getQueryTimeout() != null)
                {
                    t_strQueryTimeout = "" + t_Statement.getQueryTimeout();
                }

                result[QUERYTIMEOUT_INDEX] = t_strQueryTimeout;

                result[FETCHDIRECTION_INDEX] =
                    t_Statement.getFetchDirection();

                String t_strEscapeProcessing = null;

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
    protected final String[] immutableRetrieveStatementFlagsSetters()
    {
        return STATEMENT_FLAGS_SETTERS;
    }

    /**
     * Retrieves the setter methods.
     * @return the setter methods.
     */
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
    protected Collection filterStatementFlags(
        final Collection contents, final String idFilter)
    {
        return filterItems(contents, StatementFlagsElement.class, idFilter);
    }

    /**
     * Retrieves the resultset flags for find-by-primary-key operation.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @return the resultset flags for given operation.
     * @precondition customSqlProvider != null
     */
    public String[] retrieveResultSetFlagsForFindByPrimaryKeyOperation(
        final CustomSqlProvider customSqlProvider)
    {
        String result[] = EMPTY_3_STRING_ARRAY;

        Collection t_cResult = null;

        if  (customSqlProvider != null)
        {
            Collection t_cResultSetFlags =
                filterResultSetFlags(
                    customSqlProvider.getCollection(),
                    FIND_BY_PRIMARY_KEY_RESULTSET_FLAGS_FILTER);

            if  (   (t_cResultSetFlags == null)
                 || (t_cResultSetFlags.size() == 0))
            {
                t_cResultSetFlags =
                    filterResultSetFlags(
                        customSqlProvider.getCollection(),
                        DEFAULT_RESULTSET_FLAGS_FILTER);
            }

            if  (   (t_cResultSetFlags != null)
                 && (t_cResultSetFlags.size() > 0))
            {
                Iterator t_itResultSetFlags = t_cResultSetFlags.iterator();

                // There should be only one.
                ResultSetFlagsElement t_ResultSet =
                    (ResultSetFlagsElement) t_itResultSetFlags.next();

                String t_strType = null;

                if  (t_ResultSet.getType() != null)
                {
                    t_strType = t_ResultSet.getType();
                }

                result[TYPE_INDEX] = t_strType;

                String t_strConcurrency = null;

                if  (t_ResultSet.getConcurrency() != null)
                {
                    t_strConcurrency = t_ResultSet.getConcurrency();
                }

                result[CONCURRENCY_INDEX] = t_strConcurrency;

                String t_strHoldability = null;

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
    protected Collection filterResultSetFlags(
        final Collection contents, final String idFilter)
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
     * @precondition contents != null
     * @precondition itemClass != null
     */
    protected Collection filterItems(
        final Collection contents,
        final Class itemClass,
        final String idFilter)
    {
        Collection result = new ArrayList();

        Iterator t_itContents = contents.iterator();

        while  (t_itContents.hasNext())
        {
            Object t_CurrentItem = t_itContents.next();

            if  (   (t_CurrentItem != null)
                 && (t_CurrentItem.getClass().isAssignableFrom(itemClass)))
            {
                if  (   (idFilter == null)
                     || (filterById(
                             (IdentifiableElement) t_CurrentItem, idFilter)))
                {
                    result.add(t_CurrentItem);
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
        final IdentifiableElement element, final String idFilter)
    {
        return idFilter.equalsIgnoreCase(element.getId());
    }

    /**
     * Finds all <code>SqlElement</code> instances associated to given
     * result element.
     * @param resultId such id.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @return all such entities.
     * @precondition resultId != null
     * @precondition customSqlProvider != null
     */
    public SqlElement[] findSqlElementsByResultId(
        final String resultId,
        final CustomSqlProvider customSqlProvider)
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
     * @precondition resultId != null
     * @precondition customSqlProvider != null
     * @precondition customResultUtils != null
     */
    protected SqlElement[] findSqlElementsByResultId(
        final String resultId,
        final CustomSqlProvider customSqlProvider,
        final CustomResultUtils customResultUtils)
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
        final String tableName, final String daoId)
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
        final String tableName,
        final String daoId,
        final CustomResultUtils customResultUtils)
    {
        return customResultUtils.matches(tableName, daoId);
    }

    /**
     * Retrieves all <code>SqlElement</code> instances of given type.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param type the type.
     * @return such elements.
     * @precondition type != null
     */
    public SqlElement[] retrieveSqlElementsByType(
        final CustomSqlProvider customSqlProvider,
        final String type)
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
     * @precondition customSqlProvider != null
     * @precondition type != null
     * @precondition customResultUtils != null
     */
    protected SqlElement[] retrieveSqlElementsByType(
        final CustomSqlProvider customSqlProvider,
        final String type,
        final CustomResultUtils customResultUtils)
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
     * @precondition sqlProvider != null
     * @precondition resultId != null
     */
    public SqlElement[] retrieveSqlElementsByResultId(
        final CustomSqlProvider customSqlProvider,
        final String resultId)
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
     * @precondition sqlProvider != null
     * @precondition resultId != null
     * @precondition customResultUtils != null
     */
    protected SqlElement[] retrieveSqlElementsByResultId(
        final CustomSqlProvider customSqlProvider,
        final String resultId,
        final CustomResultUtils customResultUtils)
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
     * @precondition sqlProvider != null
     * @precondition type != null
     */
    public Result[] retrieveResultsByType(
        final CustomSqlProvider customSqlProvider, final String type)
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
     * @precondition sqlProvider != null
     * @precondition type != null
     * @precondition customResultUtils != null
     */
    protected Result[] retrieveResultsByType(
        final CustomSqlProvider customSqlProvider,
        final String type,
        final CustomResultUtils customResultUtils)
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
     * @precondition sqlProvider != null
     * @precondition resultElement != null
     */
    public PropertyElement[] retrievePropertyElementsByResultId(
        final CustomSqlProvider customSqlProvider,
        final Result resultElement)
    {
        Collection t_cResult = new ArrayList();

        Collection t_cPropertyRefs = resultElement.getPropertyRefs();

        if  (t_cPropertyRefs != null)
        {
            Iterator t_itPropertyRefs = t_cPropertyRefs.iterator();

            if  (t_itPropertyRefs != null)
            {
                while  (t_itPropertyRefs.hasNext())
                {
                    t_cResult.add(
                        customSqlProvider.resolveReference(
                            (PropertyRefElement) t_itPropertyRefs.next()));
                }
            }
        }

        return
            (PropertyElement[])
                t_cResult.toArray(EMPTY_PROPERTYELEMENT_ARRAY);
    }

    /**
     * Clones given String array.
     * @param array the array to clone.
     * @return the cloned array.
     * @precondition array != null
     */
    protected String[] clone(final String[] array)
    {
        String[] result = EMPTY_STRING_ARRAY;

        int t_iCount = (array != null) ? array.length : 0;

        if  (t_iCount > 0)
        {
            result = new String[t_iCount];

            for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                result[t_iIndex] = array[t_iIndex];
            }
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
    public Collection queryContents(
        final String tableName,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
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
    public Collection queryContents(
        final String tableName,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory,
        final MetaLanguageUtils metaLanguageUtils,
        final MetadataUtils metadataUtils)
      throws  SQLException
    {
        return
            queryContents(
                tableName,
                metaLanguageUtils.retrieveStaticAttribute(
                    tableName, metadataManager),
                metadataUtils.retrieveAttributes(
                    tableName,
                    metadataManager,
                    metadataManager.getMetadataTypeManager(),
                    decoratorFactory),
                metadataManager,
                decoratorFactory);
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
     * @precondition tableName != null
     * @precondition staticAttributeName != null
     * @precondition attributes != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    public Collection queryContents(
        final String tableName,
        final String staticAttributeName,
        final Collection attributes,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
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
     * @param input the input.
     * @param tableName the table name.
     * @param staticAttributeName the name of the static attribute.
     * @param attributes the attributes.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param metaData the metadata.
     * @return the retrieved rows.
     * @throws SQLException if the operation fails.
     * @precondition tableName != null
     * @precondition staticAttributeName != null
     * @precondition attributes != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition metaData != null
     */
    protected Collection queryContents(
        final String tableName,
        final String staticAttributeName,
        final Collection attributes,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final DatabaseMetaData metaData)
      throws  SQLException
    {
        Collection result = null;

        try
        {
            result =
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
        catch  (final SQLException sqlException)
        {
//             throw
//                 new InvalidTemplateException(
//                     "cannot.retrieve.connection",
//                     new Object[0],
//                     sqlException);
            throw sqlException;
        }

        if  (result == null)
        {
            result = new ArrayList();
        }

        return result;
    }

    /**
     * Queries the contents of given table.
     * @param input the input.
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
     * @precondition tableName != null
     * @precondition staticAttributeName != null
     * @precondition attributes != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition metadataUtils != null
     * @precondition connection != null
     */
    protected Collection queryContents(
        final String tableName,
        final String staticAttributeName,
        final Collection attributes,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final MetadataUtils metadataUtils,
        final Connection connection)
      throws  SQLException
    {
        Collection result = new ArrayList();

        Log t_Log = UniqueLogFactory.getLog(DAOTemplateUtils.class);
        
        int t_iColumnCount = (attributes != null) ? attributes.size() : 0;

        ResultSet t_rsResults = null;

        PreparedStatement t_PreparedStatement = null;

        try
        {
            t_PreparedStatement =
                connection.prepareStatement(
                    "select * from " + tableName);

            t_rsResults = t_PreparedStatement.executeQuery();

            String[] t_astrColumnNames = new String[t_iColumnCount];

            String[] t_astrColumnValues = new String[t_iColumnCount];

            String t_strRowName = null;

            if  (t_rsResults != null)
            {
                while  (t_rsResults.next())
                {
                    t_strRowName = null;

                    ResultSetMetaData t_rsMetaData =
                        t_rsResults.getMetaData();

                    int t_iArrayIndex = 0;

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

                    result.add(
                        buildRow(
                            t_strRowName,
                            tableName,
                            metadataUtils.buildAttributes(
                                t_astrColumnNames,
                                t_astrColumnValues,
                                tableName,
                                metadataManager,
                                metadataTypeManager,
                                decoratorFactory),
                            metadataTypeManager));
                }
            }
        }
        catch  (final SQLException sqlException)
        {
//             throw
//                 new InvalidTemplateException(
//                     "cannot.retrieve.fixed.table.contents",
//                     new Object[] { tableName },
//                     sqlException);
            throw sqlException;
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
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the statement.",
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
     * @param metadataTypeManager the metadata type manager.
     * @return the row.
     * @precondition rowName != null
     * @precondition tableName != null
     * @precondition attributes != null
     * @precondition metadatTypeManager != null
     */
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
     * @precondition attributes != null
     * @precondition names != null
     * @precondition values != null
     * @precondition attributes.size() == names.length
     * @precondition names.length == values.length
     */
    protected void reorderAttributes(
        final Collection attributes,
        final String[] names,
        final String[] values)
    {
        Iterator t_itAttributeIterator =
            (attributes != null) ? attributes.iterator() : null;

        if  (t_itAttributeIterator != null)
        {
            Object t_Item;
            Attribute t_CurrentAttribute;
            int t_iIndex = 0;
            int t_iPosition;
            int t_iCount = (attributes != null) ? attributes.size() : 0;

            String[] t_astrAuxNames = new String[t_iCount];
            String[] t_astrAuxValues = new String[t_iCount];

            while  (t_itAttributeIterator.hasNext())
            {
                t_Item = t_itAttributeIterator.next();

                if  (t_Item instanceof Attribute)
                {
                    t_CurrentAttribute = (Attribute) t_Item;

                    t_iPosition =
                        retrieveAttributeIndex(
                            t_CurrentAttribute.getName(), names);

                    if  (   (t_iPosition >= 0)
                         && (t_iPosition < t_iCount))
                    {
                        t_astrAuxNames[t_iIndex] = names[t_iPosition];
                        t_astrAuxValues[t_iIndex] = values[t_iPosition];
                    }

                    t_iIndex++;
                }
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
     * @precondition name != null
     * @precondition attributes != null
     */
    protected int retrieveAttributeIndex(
        final String name, final String[] attributes)
    {
        int result = -1;

        int t_iCount = (attributes != null) ? attributes.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            if  (name.equals(attributes[t_iIndex]))
            {
                result = t_iIndex;
                break;
            }
        }

        return result;
    }

    /**
     * Copies given array.
     * @param source the source.
     * @param target the target.
     * @precondition source != null
     * @precondition target != null
     * @precondition source.length <= target.length
     */
    protected void copyArray(final String[] source, final String[] target)
    {
        int t_iCount = (source != null) ? source.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            target[t_iIndex] = source[t_iIndex];
        }
    }
}
