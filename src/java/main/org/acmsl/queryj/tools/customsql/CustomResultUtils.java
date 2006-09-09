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
 * Description: Provides some useful methods when working with custom result
 *              elements.
 */
package org.acmsl.queryj.tools.customsql;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.customsql.SqlElement;
import org.acmsl.queryj.tools.metadata.MetadataManager;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;
import org.acmsl.commons.utils.EnglishGrammarUtils;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Provides some useful methods when working with custom result elements.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class CustomResultUtils
    implements  Singleton,
                Utils
{
    /**
     * An empty SqlElement array.
     */
    public static final SqlElement[] EMPTY_SQLELEMENT_ARRAY =
        new SqlElement[0];

    /**
     * An empty Result array.
     */
    public static final Result[] EMPTY_RESULT_ARRAY =
        new Result[0];

    /**
     * A map-based cache to improve performance when retrieving
     * singular and plural forms for tables, and to cache other
     * other data if needed.
     */
    private static final Map CACHE = new HashMap();

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
    protected CustomResultUtils() {};

    /**
     * Retrieves a <code>CustomResultUtils</code> instance.
     * @return such instance.
     */
    public static CustomResultUtils getInstance()
    {
        return CustomResultUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the table associated to the result.
     * @param resultElement the result element.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the database metadata manager.
     * @return the table name.
     * @precondition resultElement != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     */
    public String retrieveTable(
        final Result resultElement,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager)
    {
        String result = null;

        String[] t_astrTableNames = metadataManager.getTableNames();

        int t_iTableCount =
            (t_astrTableNames != null) ? t_astrTableNames.length : 0;

        SqlElement[] t_aSqlElements = null;

        int t_iSqlCount = 0;

        boolean t_bBreakLoop = false;
        String t_strDao;

        for  (int t_iTableIndex = 0;
                 (t_iTableIndex < t_iTableCount) && (!t_bBreakLoop);
                  t_iTableIndex++)
        {
            t_aSqlElements =
                retrieveSqlElementsByResultId(
                    customSqlProvider, resultElement.getId());

            t_iSqlCount = (t_aSqlElements != null) ? t_aSqlElements.length : 0;

            for  (int t_iSqlIndex = 0;
                      t_iSqlIndex < t_iSqlCount;
                      t_iSqlIndex++)
            {
                t_strDao = t_aSqlElements[t_iSqlIndex].getDao();

                if  (   (t_strDao != null)
                     && (matches(
                             t_astrTableNames[t_iTableIndex], t_strDao)))
                {
                    result = t_astrTableNames[t_iTableIndex];
                    t_bBreakLoop = true;
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
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @return <code>true</code> if it should be included.
     * @precondition resultElement != null
     * @precondition tableName != null
     * @precondition customSqlProvider != null
     */
    public boolean matches(
        final Result resultElement,
        final String tableName,
        final CustomSqlProvider customSqlProvider)
    {
        boolean result = false;

        SqlElement[] t_aSqlElements =
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
        final String tableName, final String daoId)
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
     * @precondition tableName != null
     * @precondition daoId != null
     * @precondition englishGrammarUtils != null
     */
    protected boolean matches(
        final String tableName,
        final String daoId,
        final EnglishGrammarUtils englishGrammarUtils)
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
        Collection t_cResult = new ArrayList();

        Collection t_cElements = customSqlProvider.getCollection();

        if  (t_cElements != null)
        {
            Iterator t_itElements = t_cElements.iterator();

            if  (t_itElements != null)
            {
                Object t_CurrentItem = null;

                ResultRefElement t_ResultRef = null;

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
     * Retrieves all <code>Result</code> instances of given type.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param type the type.
     * @return such elements.
     * @precondition sqlProvider != null
     * @precondition type != null
     */
    public Result[] retrieveResultsByType(
        final CustomSqlProvider customSqlProvider,
        final String type)
    {
        Collection t_cResult = new ArrayList();

        SqlElement[] t_aSqlElements =
            retrieveSqlElementsByType(customSqlProvider, type);

        Result t_CurrentElement = null;

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
        Collection t_cResult = new ArrayList();

        Collection t_cElements = null;

        if  (customSqlProvider != null)
        {
            t_cElements = customSqlProvider.getCollection();
        }

        if  (t_cElements != null)
        {
            Iterator t_itElements = t_cElements.iterator();

            if  (t_itElements != null)
            {
                Object t_CurrentItem = null;

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
        Collection t_cResult = new ArrayList();

        Collection t_cElements = customSqlProvider.getCollection();

        if  (t_cElements != null)
        {
            Iterator t_itElements = t_cElements.iterator();

            if  (t_itElements != null)
            {
                Object t_CurrentItem = null;

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
        final String key, final String value)
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
    protected void cache(final Map map, final String key, final String value)
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
    protected String retrieveCachedEntry(final Map cache, final String key)
    {
        return (String) cache.get(key);
    }

    /**
     * Removes an entry from the cache.
     * @param cache the cache.
     * @param key the key.
     * @precondition cache != null
     * @precondition key != null
     */
    protected void removeEntryFromCache(final Map cache, final String key)
    {
        cache.remove(key);
    }

    /**
     * Builds the singular key for given table.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
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
            final CustomResultUtils t_OtherInstance =
                (CustomResultUtils) object;

            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .appendSuper(super.equals(t_OtherInstance))
                .isEquals();
        }

        return result;
    }

}
