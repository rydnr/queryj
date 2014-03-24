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
 * Filename: CustomResultUtils.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides some useful methods when working with custom result
 *              elements.
 */
package org.acmsl.queryj.customsql;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.metadata.CachingResultDecorator;
import org.acmsl.queryj.metadata.DecorationUtils;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.MetadataUtils;
import org.acmsl.queryj.metadata.ResultDecorator;
import org.acmsl.queryj.metadata.SqlDAO;
import org.acmsl.queryj.metadata.TableDAO;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.tools.DebugUtils;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;

/*
 * Importing some JetBrains classes.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Provides some useful methods when working with custom result elements.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CustomResultUtils
    implements  Singleton,
                Utils
{
    /**
     * The system property prefix to disable generation for concrete (or all, with *) custom results.
     */
    public static final String RESULTS_DISABLED = "queryj.customresults.disabled";

    /**
     * The system property to enable generation for concrete (or all, with * or missing property) custom results.
     */
    public static final String RESULTS_ENABLED = "queryj.customresults.enabled";

    /**
     * A map-based cache to improve performance when retrieving
     * singular and plural forms for tables, and to cache other
     * other data if needed.
     */
    private static final Map<String,String> CACHE = new HashMap<>();

    /**
     * Checks whether given {@link Result} is implicit or not.
     * @param customResult the {@link Result} to process.
     * @return <code>true</code> in such case.
     */
    public boolean isImplicit(
        @NotNull final Result<String> customResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        boolean result = false;

        @Nullable final String t_strTable = retrieveTable(customResult, customSqlProvider, metadataManager);

        if (t_strTable != null)
        {
            result =
                matches(customResult, t_strTable, customSqlProvider, MetadataUtils.getInstance());
        }

        return result;
    }

    /**
     * Decorates given custom result.
     * @param customResult the {@link Result} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the decorated result.
     */
    @NotNull
    public ResultDecorator decorate(
        @NotNull final Result<String> customResult,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return new CachingResultDecorator(customResult, customSqlProvider, metadataManager, decoratorFactory);

    }

    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class CustomResultUtilsSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final CustomResultUtils SINGLETON =
            new CustomResultUtils();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected CustomResultUtils() {}

    /**
     * Retrieves a <code>CustomResultUtils</code> instance.
     * @return such instance.
     */
    @NotNull
    public static CustomResultUtils getInstance()
    {
        return CustomResultUtilsSingletonContainer.SINGLETON;
    }
    /**
     * Checks whether the generation phase is enabled for given custom result.
     * @param resultId the id of the custom result.
     * @return <code>true</code> in such case.
     */
    public boolean isGenerationAllowedForResult(@NotNull final String resultId)
    {
          return
              isGenerationAllowedForResult(
                  retrieveExplicitlyEnabledResults(),
                  retrieveExplicitlyDisabledResults(),
                  resultId);
    }

    /**
     * Retrieves the explicitly enabled table names, via environment property. This means
     * no other tables should be processed.
     */
    @NotNull
    protected String[] retrieveExplicitlyEnabledResults()
    {
        @NotNull final String[] result;

        @Nullable final String property = System.getProperty(RESULTS_ENABLED);

        if (property != null)
        {
            result = parseExplicitResults(property);
        }
        else
        {
            result = new String[0];
        }

        return result;
    }

    /**
     * Retrieves the explicitly disabled custom results, via environment property. This means
     * no other custom results should be processed.
     */
    @NotNull
    protected String[] retrieveExplicitlyDisabledResults()
    {
        @NotNull final String[] result;

        @Nullable final String property = System.getProperty(RESULTS_DISABLED);

        if (property != null)
        {
            result = parseExplicitResults(property);
        }
        else
        {
            result = new String[0];
        }

        return result;
    }

    /**
     * Parses given environment property to find out whether some tables are
     * explicitly specified.
     * @param environmentProperty the environment property.
     * @return the tables specified in given environment property.
     */
    @NotNull
    protected String[] parseExplicitResults(@NotNull final String environmentProperty)
    {
        return environmentProperty.split(",");
    }

    /**
     * Checks whether the generation phase is enabled for given table.
     * @param resultsEnabled the explicitly-enabled tables.
     * @param resultsDisabled the explicitly-disabled tables.
     * @param resultId the id of the custom result.
     * @return <code>true</code> in such case.
     */
    protected final boolean isGenerationAllowedForResult(
        @NotNull final String[] resultsEnabled,
        @NotNull final String[] resultsDisabled,
        @NotNull final String resultId)
    {
        boolean result;

        final boolean t_bExplicitlyEnabled = Arrays.asList(resultsEnabled).contains(resultId);

        result = t_bExplicitlyEnabled;
        boolean t_bCheckDisabled = false;

        if (!t_bExplicitlyEnabled)
        {
            final List<String> t_lResultsDisabled = Arrays.asList(resultsDisabled);

            if (   (resultsEnabled.length > 0)
                || (t_lResultsDisabled.contains("*"))) // explicitly-enabled results imply
                // the others are disabled implicitly.
            {
                result = false;
            }
            else
            {
                t_bCheckDisabled = true;
            }

            if (t_bCheckDisabled)
            {
                result = !t_lResultsDisabled.contains(resultId);
            }
        }

        return result;
    }

    /**
     * Retrieves the table associated to the result.
     * @param resultElement the result element.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the database metadata manager.
     * @return the table name.
     */
    @Nullable
    public <T> String retrieveTable(
        @NotNull final Result<T> resultElement,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable final String result;

        result = retrieveTable(resultElement.getId(), customSqlProvider, metadataManager);

        return result;
    }

    /**
     * Retrieves the table associated to the result.
     * @param resultId the result id.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the database metadata manager.
     * @return the table name.
     */
    @Nullable
    public <T> String retrieveTable(
        @NotNull final T resultId,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable String result = retrieveCachedEntry("" + resultId);

        if (result == null)
        {
            if (DebugUtils.getInstance().debugEnabledForResultId(resultId))
            {
                @SuppressWarnings("unused") final int a = 1;
            }

            for (@Nullable final Sql<String> t_Sql : retrieveSqlElementsByResultId(customSqlProvider, "" + resultId))
            {
                if (t_Sql != null)
                {
                    result = retrieveTable(t_Sql, metadataManager);

                    if (result != null)
                    {
                        cacheEntry("" + resultId, result);
                        break;
                    }
                }
            }
        }

//        if (   (result == null)
//            && (!t_bBreakLoop))
//        {
//            throw new IllegalArgumentException("Result " + resultElement.getId() + " does not match any table");
//        }

        return result;
    }

    /**
     * Retrieves the table associated to the {@link Sql}.
     * @param sql the {@link Sql}.
     * @param metadataManager the database metadata manager.
     * @return the table name.
     */
    @Nullable
    public <T> String retrieveTable(
        @NotNull final Sql<T> sql,
        @NotNull final MetadataManager metadataManager)
    {
        return retrieveTable(sql, metadataManager.getTableDAO());
    }

    /**
     * Retrieves the table associated to the {@link Sql}.
     * @param sql the {@link Sql}.
     * @param tableDAO the {@link TableDAO}.
     * @param <T> the type.
     * @return the table name.
     */
    @Nullable
    protected <T> String retrieveTable(@NotNull final Sql<T> sql, @NotNull final TableDAO tableDAO)
    {
        @Nullable String result = null;
        @Nullable final T t_strDao = sql.getDao();

        if (t_strDao != null)
        {
            @NotNull final List<String> t_lTableNameVariants =
                Arrays.asList(
                    "" + t_strDao,
                    ("" + t_strDao).toLowerCase(QueryJSettings.DEFAULT_LOCALE),
                    ("" + t_strDao).toUpperCase(QueryJSettings.DEFAULT_LOCALE));

            for (@NotNull final String t_strTableName : t_lTableNameVariants)
            {
                @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
                    tableDAO.findByDAO(t_strTableName);

                if  (t_Table != null)
                {
                    result = t_Table.getName();
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Checks whether given result element is suitable of being
     * included in the DAO layer associated to a concrete table.
     * @param resultElement the result.
     * @param tableName the table name.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return <code>true</code> if it should be included.
     */
    public boolean matches(
        @NotNull final Result<String> resultElement,
        @NotNull final String tableName,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return matches(resultElement, tableName, customSqlProvider, MetadataUtils.getInstance());
    }

    /**
     * Checks whether given result element is suitable of being
     * included in the DAO layer associated to a concrete table.
     * @param resultElement the result.
     * @param tableName the table name.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataUtils the {@link MetadataUtils} instance.
     * @return <code>true</code> if it should be included.
     */
    protected <T> boolean matches(
        @NotNull final Result<T> resultElement,
        @NotNull final String tableName,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataUtils metadataUtils)
    {
        boolean result = false;

        @Nullable String t_strDao = null;

        for (@Nullable final Sql<String> t_Sql : findSqlElementsByResultId("" + resultElement.getId(), customSqlProvider))
        {
            if (t_Sql != null)
            {
                t_strDao = t_Sql.getDao();
            }

            if (   (t_strDao != null)
                && (metadataUtils.matches(tableName, t_strDao)))
            {
                result = true;
                break;
            }
        }

        if (result)
        {
            result = false;

            @Nullable final String t_strResultVoClass = extractVoName(resultElement);
            @Nullable final String t_strDaoVoClass = extractVoName(t_strDao);

            if (t_strResultVoClass != null)
            {
                result = t_strResultVoClass.equalsIgnoreCase(t_strDaoVoClass);
            }
        }

        return result;
    }

    /**
     * Retrieves all {@link SqlElement} instances associated to
     * given result id.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param resultId the result id.
     * @return such elements.
     */
    @NotNull
    public List<Sql<String>> retrieveSqlElementsByResultId(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String resultId)
    {
        return retrieveSqlElementsByResultId(customSqlProvider.getSqlDAO(), resultId);
    }

    /**
     * Retrieves all {@link SqlElement} instances associated to
     * given result id.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @param resultId the result id.
     * @return such elements.
     */
    @NotNull
    protected List<Sql<String>> retrieveSqlElementsByResultId(
        @NotNull final SqlDAO sqlDAO,
        @NotNull final String resultId)
    {
        return sqlDAO.findByResultId(resultId);
    }

    /**
     * Retrieves all {@link SqlElement} instances of given type.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param type the type.
     * @return such elements.
     */
    @NotNull
    public List<Sql<String>> retrieveSqlElementsByType(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String type)
    {
        return retrieveSqlElementsByType(customSqlProvider.getSqlDAO(), type);
    }

    /**
     * Retrieves all {@link SqlElement} instances of given type.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @param type the type.
     * @return such elements.
     */
    @NotNull
    public List<Sql<String>> retrieveSqlElementsByType(
        @NotNull final SqlDAO sqlDAO,
        @NotNull final String type)
    {
        return sqlDAO.findByType(type);
    }

    /**
     * Finds all {@link SqlElement} instances associated to given
     * result element.
     * @param resultId such id.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return all such entities.
     */
    @NotNull
    public List<Sql<String>> findSqlElementsByResultId(
        @NotNull final String resultId,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return findSqlElementsByResultId(resultId, customSqlProvider.getSqlDAO());
    }

    /**
     * Finds all {@link SqlElement} instances associated to given
     * result element.
     * @param resultId such id.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @return all such entities.
     */
    @NotNull
    public List<Sql<String>> findSqlElementsByResultId(
        @NotNull final String resultId,
        @NotNull final SqlDAO sqlDAO)
    {
        return sqlDAO.findByResultId(resultId);
    }

    /**
     * Caches given singular or plural form of a table name.
     * @param key the key.
     * @param value the value.
     */
    protected void cacheEntry(
        final String key, @Nullable final String value)
    {
        if  (value == null)
        {
            removeEntryFromCache(CACHE, key);
        }
        else
        {
            cache(CACHE, key, value);
        }
    }

    /**
     * Retrieves the cached entry.
     * @param key the key.
     * @return the cached entry, or <code>null</code> if it's not cached.
     */
    @Nullable
    protected String retrieveCachedEntry(@NotNull final String key)
    {
        return retrieveCachedEntry(CACHE, key);
    }

    /**
     * Caches given entry.
     * @param map the cache.
     * @param key the key.
     * @param value the value.
     */
    protected void cache(@NotNull final Map<String,String> map, @NotNull final String key, @NotNull final String value)
    {
        map.put(key, value);
    }

    /**
     * Retrieves the cached entry.
     * @param cache the cache.
     * @param key the key.
     * @return the cached entry, or <code>null</code> if it's not cached.
     */
    @Nullable
    protected String retrieveCachedEntry(@NotNull final Map<String,String> cache, @NotNull final String key)
    {
        return cache.get(key);
    }

    /**
     * Removes an entry from the cache.
     * @param cache the cache.
     * @param key the key.
     */
    protected void removeEntryFromCache(@NotNull final Map<String,String> cache, @NotNull final String key)
    {
        cache.remove(key);
    }

    /**
     * Extracts the ValueObject name of given result.
     * @param customResult the {@link Result} instance.
     * @return such class name, or <code>null</code> if it has no declared value.
     */
    @Nullable
    protected <T> String extractVoName(@NotNull final Result<T> customResult)
    {
        @Nullable final String result;

        @Nullable final T classValue = customResult.getClassValue();

        if (classValue != null)
        {
            @NotNull String aux = "" + classValue;

            @NotNull final String[] t_astrParts = aux.split("\\.");

            if (t_astrParts.length > 0)
            {
                aux = t_astrParts[t_astrParts.length - 1];
            }

            result = extractVoName(aux);
        }
        else
        {
            result = null;
        }

        return result;
    }

    /**
     * Extracts the ValueObject name of given name.
     * @param name the name.
     * @return such class name, or <code>null</code> if it has no declared value.
     */
    @Nullable
    protected String extractVoName(@NotNull final String name)
    {
        return extractVoName(name, DecorationUtils.getInstance());
    }

    /**
     * Extracts the ValueObject name of given result class.
     * @param name the class name.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return such class name, or <code>null</code> if it has no declared value.
     */
    @Nullable
    protected String extractVoName(@NotNull final String name, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(decorationUtils.getSingular(name));
    }

   /**
    * Provides a text representation of the information
    * contained in this instance.
    * @return such information.
    */
    @Override
    @NotNull
    public String toString()
    {
        return
            new org.apache.commons.lang.builder.ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    @Override
    public int hashCode()
    {
        return
            new org.apache.commons.lang.builder.HashCodeBuilder(-1682907411, 790802603)
                .appendSuper(super.hashCode())
                .toHashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    public boolean equals(@Nullable final Object object)
    {
        boolean result = false;

        if  (object instanceof CustomResultUtils)
        {
            @NotNull final CustomResultUtils t_OtherInstance =
                (CustomResultUtils) object;

            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .appendSuper(super.equals(t_OtherInstance))
                    .isEquals();
        }

        return result;
    }
}

