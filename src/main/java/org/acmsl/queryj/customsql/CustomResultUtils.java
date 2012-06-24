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
import org.acmsl.queryj.metadata.MetadataManager;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;
import org.acmsl.commons.utils.EnglishGrammarUtils;

/*
 * Importing some JetBrains classes.
 */
import org.acmsl.queryj.metadata.vo.Table;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Provides some useful methods when working with custom result elements.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class CustomResultUtils
    implements  Singleton,
                Utils
{
    /**
     * An empty SqlElement array.
     */
    public static final SqlElement[] EMPTY_SQLELEMENT_ARRAY = new SqlElement[0];

    /**
     * An empty Result array.
     */
    public static final Result[] EMPTY_RESULT_ARRAY = new Result[0];

    /**
     * An empty String array.
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

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
    private static final Map<String,String> CACHE = new HashMap<String,String>();

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
    protected String[] retrieveExplicitlyEnabledResults()
    {
        return parseExplicitResults(System.getProperty(RESULTS_ENABLED));
    }

    /**
     * Retrieves the explicitly disabled custom results, via environment property. This means
     * no other custom results should be processed.
     */
    protected String[] retrieveExplicitlyDisabledResults()
    {
        return parseExplicitResults(System.getProperty(RESULTS_DISABLED));
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
        @NotNull String[] result = null;

        if (environmentProperty != null)
        {
            result = environmentProperty.split(",");
        }

        if (result == null)
        {
            result = EMPTY_STRING_ARRAY;
        }

        return result;
    }

    /**
     * Checks whether the generation phase is enabled for given table.
     * @param resultsEnabled the explicitly-enabled tables.
     * @param resultsDisabled the explicitly-disabled tables.
     * @param resultId the id of the custom result.
     * @return <code>true</code> in such case.
     */
    protected final boolean isGenerationAllowedForResult(
        @Nullable final String[] resultsEnabled,
        @Nullable final String[] resultsDisabled,
        @NotNull final String resultId)
    {
        boolean result = true;

        boolean t_bExplicitlyEnabled = false;

        if (resultsEnabled != null)
        {
            t_bExplicitlyEnabled = Arrays.asList(resultsEnabled).contains(resultId);

            result = t_bExplicitlyEnabled;
        }

        if (   (!t_bExplicitlyEnabled)
            && (resultsDisabled != null))
        {
            List<String> t_lResultsDisabled = Arrays.asList(resultsDisabled);

            if (   (t_lResultsDisabled.contains("*"))
                || (resultsEnabled.length > 0)) // explicitly-enabled results imply
            // the others are disabled implicitly.
            {
                result = false;
            }
            else if (resultsDisabled != null)
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
    public String retrieveTable(
        @NotNull final Result resultElement,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable String result = retrieveCachedEntry(resultElement.getId());

        boolean t_bBreakLoop = false;

        if (result == null)
        {
            if (   (resultElement.getId().equals("draws.multiple.draw.result"))
                || (resultElement.getId().equals("active_lottery_numbers.multiple.total_avaibles.result")))
            {
                System.out.println("caught");
            }
            List<Table> t_lTables = metadataManager.getTableDAO().findAllTables();

            int t_iTableCount = t_lTables.size();

            @Nullable SqlElement[] t_aSqlElements;

            int t_iSqlCount;

            String t_strDao;

            for  (int t_iTableIndex = 0;
                     (t_iTableIndex < t_iTableCount) && (!t_bBreakLoop);
                      t_iTableIndex++)
            {
                t_aSqlElements =
                    retrieveSqlElementsByResultId(
                        customSqlProvider, resultElement.getId());

                t_iSqlCount = t_aSqlElements.length;

                for  (int t_iSqlIndex = 0;
                          t_iSqlIndex < t_iSqlCount;
                          t_iSqlIndex++)
                {
                    t_strDao = t_aSqlElements[t_iSqlIndex].getDao();

                    if  (   (t_strDao != null)
                         && (matches(t_lTables.get(t_iTableIndex).getName(), t_strDao)))
                    {
                        result = t_lTables.get(t_iTableIndex).getName();
                        cacheEntry(resultElement.getId(), result);
                        t_bBreakLoop = true;
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
     * Checks whether given result element is suitable of being
     * included in the DAO layer associated to a concrete table.
     * @param resultElement the result.
     * @param tableName the table name.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return <code>true</code> if it should be included.
     * @precondition resultElement != null
     * @precondition tableName != null
     * @precondition customSqlProvider != null
     */
    public boolean matches(
        @NotNull final Result resultElement,
        @NotNull final String tableName,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        boolean result = false;

        @NotNull SqlElement[] t_aSqlElements =
            findSqlElementsByResultId(
                resultElement.getId(), customSqlProvider);

        int t_iCount = (t_aSqlElements != null) ? t_aSqlElements.length : 0;

        EnglishGrammarUtils t_EnglishGrammarUtils =
            EnglishGrammarUtils.getInstance();

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            if  (matches(
                     tableName,
                     t_aSqlElements[t_iIndex].getDao(),
                     t_EnglishGrammarUtils))
            {
                result = true;
                break;
            }
        }

        return result;
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
        return matches(tableName, daoId, EnglishGrammarUtils.getInstance());
    }

    /**
     * Checks whether given table name matches the DAO id.
     * @param tableName the table name.
     * @param daoId the DAO id.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @return <code>true</code> if they match.
     */
    protected boolean matches(
        @NotNull final String tableName,
        @NotNull final String daoId,
        @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        boolean result = false;

        String t_strTableInLowerCase = tableName.trim().toLowerCase();

        result = daoId.equalsIgnoreCase(t_strTableInLowerCase);

        synchronized  (t_strTableInLowerCase)
        {
            if  (!result)
            {
                String t_strSingularName =
                    retrieveCachedSingularTableName(t_strTableInLowerCase);

                if  (t_strSingularName == null)
                {
                    t_strSingularName =
                        englishGrammarUtils.getSingular(t_strTableInLowerCase);

                    cacheSingularTableName(
                        t_strTableInLowerCase, t_strSingularName);
                }

                result = daoId.equalsIgnoreCase(t_strSingularName);
            }

            if  (!result)
            {
                String t_strPluralName =
                    retrieveCachedPluralTableName(t_strTableInLowerCase);

                if  (t_strPluralName == null)
                {
                    t_strPluralName =
                        englishGrammarUtils.getPlural(t_strTableInLowerCase);

                    cachePluralTableName(
                        t_strTableInLowerCase, t_strPluralName);
                }

                result = daoId.equalsIgnoreCase(t_strPluralName);
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
     * @precondition sqlProvider != null
     * @precondition resultId != null
     */
    @NotNull
    public SqlElement[] retrieveSqlElementsByResultId(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String resultId)
    {
        @NotNull Collection t_cResult = new ArrayList();

        Collection t_cElements = customSqlProvider.getCollection();

        if  (t_cElements != null)
        {
            Iterator t_itElements = t_cElements.iterator();

            if  (t_itElements != null)
            {
                @Nullable Object t_CurrentItem = null;

                @Nullable ResultRefElement t_ResultRef = null;

                while  (t_itElements.hasNext())
                {
                    t_CurrentItem = t_itElements.next();

                    if  (t_CurrentItem instanceof SqlElement)
                    {
                        t_ResultRef = ((SqlElement) t_CurrentItem).getResultRef();

                        if  (   (t_ResultRef != null)
                             && (resultId.equals(t_ResultRef.getId())))
                        {
                            t_cResult.add(t_CurrentItem);
                        }
                    }
                }
            }
        }

        return
            (SqlElement[]) t_cResult.toArray(EMPTY_SQLELEMENT_ARRAY);
    }

    /**
     * Retrieves all {@link Result} instances of given type.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param type the type.
     * @return such elements.
     */
    @NotNull
    public Result[] retrieveResultsByType(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String type)
    {
        @NotNull Collection t_cResult = new ArrayList();

        @NotNull SqlElement[] t_aSqlElements =
            retrieveSqlElementsByType(customSqlProvider, type);

        @Nullable Result t_CurrentElement = null;

        for  (int t_iIndex = 0; t_iIndex < t_aSqlElements.length; t_iIndex++)
        {
            t_CurrentElement =
                customSqlProvider.resolveReference(
                    t_aSqlElements[t_iIndex].getResultRef());

            if  (t_CurrentElement != null)
            {
                t_cResult.add(t_CurrentElement);
            }
        }

        return
            (Result[]) t_cResult.toArray(EMPTY_RESULT_ARRAY);
    }

    /**
     * Retrieves all {@link SqlElement} instances of given type.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param type the type.
     * @return such elements.
     */
    @NotNull
    public SqlElement[] retrieveSqlElementsByType(
        @Nullable final CustomSqlProvider customSqlProvider,
        @NotNull final String type)
    {
        @NotNull Collection t_cResult = new ArrayList();

        @Nullable Collection t_cElements = null;

        if  (customSqlProvider != null)
        {
            t_cElements = customSqlProvider.getCollection();
        }

        if  (t_cElements != null)
        {
            Iterator t_itElements = t_cElements.iterator();

            if  (t_itElements != null)
            {
                @Nullable Object t_CurrentItem = null;

                while  (t_itElements.hasNext())
                {
                    t_CurrentItem = t_itElements.next();

                    if  (   (t_CurrentItem instanceof SqlElement)
                         && (type.equals(((SqlElement) t_CurrentItem).getType())))
                    {
                        t_cResult.add(t_CurrentItem);
                    }
                }
            }
        }

        return
            (SqlElement[]) t_cResult.toArray(EMPTY_SQLELEMENT_ARRAY);
    }

    /**
     * Finds all {@link SqlElement} instances associated to given
     * result element.
     * @param resultId such id.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return all such entities.
     * @precondition resultId != null
     * @precondition customSqlProvider != null
     */
    @NotNull
    public SqlElement[] findSqlElementsByResultId(
        @NotNull final String resultId,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        @NotNull Collection t_cResult = new ArrayList();

        Collection t_cElements = customSqlProvider.getCollection();

        if  (t_cElements != null)
        {
            Iterator t_itElements = t_cElements.iterator();

            if  (t_itElements != null)
            {
                @Nullable Object t_CurrentItem = null;

                while  (t_itElements.hasNext())
                {
                    t_CurrentItem = t_itElements.next();

                    if  (t_CurrentItem instanceof SqlElement)
                    {
                        ResultRefElement t_ResultRefElement =
                            ((SqlElement) t_CurrentItem).getResultRef();

                        if  (   (t_ResultRefElement != null)
                             && (resultId.equals(t_ResultRefElement.getId())))
                        {
                            t_cResult.add(t_CurrentItem);
                        }
                    }
                }
            }
        }

        return (SqlElement[]) t_cResult.toArray(EMPTY_SQLELEMENT_ARRAY);
    }

    /**
     * Caches the singular table name.
     * @param tableName the table name.
     * @param singularForm the singular form.
     * @precondition tableName != null
     */
    protected void cacheSingularTableName(
        final String tableName, final String singularForm)
    {
        cacheEntry(buildSingularKey(tableName), singularForm);
    }

    /**
     * Caches the plural table name.
     * @param tableName the table name.
     * @param pluralForm the plural form.
     * @precondition tableName != null
     */
    protected void cachePluralTableName(
        final String tableName, final String pluralForm)
    {
        cacheEntry(buildPluralKey(tableName), pluralForm);
    }

    /**
     * Caches given singular or plural form of a table name.
     * @param key the key.
     * @param value the value.
     * @precondition key != null
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
     * Retrieves the cached singular form for given table.
     * @param tableName the table name.
     * @return such value.
     * @precondition tableName != null
     */
    @NotNull
    protected String retrieveCachedSingularTableName(final String tableName)
    {
        return retrieveCachedEntry(buildSingularKey(tableName));
    }

    /**
     * Retrieves the cached plural form for given table.
     * @param tableName the table name.
     * @return such value.
     * @precondition tableName != null
     */
    @NotNull
    protected String retrieveCachedPluralTableName(final String tableName)
    {
        return retrieveCachedEntry(buildPluralKey(tableName));
    }

    /**
     * Retrieves the cached entry.
     * @param key the key.
     * @return the cached entry, or <code>null</code> if it's not cached.
     * @precondition key != null
     */
    @Nullable
    protected String retrieveCachedEntry(final String key)
    {
        return retrieveCachedEntry(CACHE, key);
    }

    /**
     * Caches given entry.
     * @param map the cache.
     * @param key the key.
     * @param value the value.
     * @precondition map != null
     * @precondition key != null
     * @precondition value != null
     */
    protected void cache(@NotNull final Map<String,String> map, final String key, final String value)
    {
        map.put(key, value);
    }

    /**
     * Retrieves the cached entry.
     * @param cache the cache.
     * @param key the key.
     * @return the cached entry, or <code>null</code> if it's not cached.
     * @precondition cache != null
     * @precondition key != null
     */
    @NotNull
    protected String retrieveCachedEntry(@NotNull final Map<String,String> cache, final String key)
    {
        return cache.get(key);
    }

    /**
     * Removes an entry from the cache.
     * @param cache the cache.
     * @param key the key.
     * @precondition cache != null
     * @precondition key != null
     */
    protected void removeEntryFromCache(@NotNull final Map<String,String> cache, final String key)
    {
        cache.remove(key);
    }

    /**
     * Builds the singular key for given table.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    @NotNull
    protected String buildSingularKey(final String tableName)
    {
        return "~singular--" + tableName;
    }

    /**
     * Builds the plural key for given table.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    @NotNull
    protected String buildPluralKey(final String tableName)
    {
        return "~plural--" + tableName;
    }

    /**
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
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
    public boolean equals(final Object object)
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
