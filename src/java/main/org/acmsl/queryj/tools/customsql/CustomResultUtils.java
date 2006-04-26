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
import org.acmsl.commons.utils.EnglishGrammarUtils;

/*
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Provides some useful methods when working with custom result elements.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class CustomResultUtils
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
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected CustomResultUtils() {};

    /**
     * Specifies a new weak reference.
     * @param utils the utils instance to use.
     */
    protected static void setReference(final CustomResultUtils utils)
    {
        singleton = new WeakReference(utils);
    }

    /**
     * Retrieves the weak reference.
     * @return such reference.
     */
    protected static WeakReference getReference()
    {
        return singleton;
    }

    /**
     * Retrieves a <code>CustomResultUtils</code> instance.
     * @return such instance.
     */
    public static CustomResultUtils getInstance()
    {
        CustomResultUtils result = null;

        WeakReference reference = getReference();

        if  (reference != null)
        {
            result = (CustomResultUtils) reference.get();
        }

        if  (result == null)
        {
            result = new CustomResultUtils();

            setReference(result);
        }

        return result;
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
                if  (matches(
                         t_astrTableNames[t_iTableIndex],
                         t_aSqlElements[t_iSqlIndex].getDao()))
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

        if  (t_aSqlElements != null)
        {
            for  (int t_iIndex = 0;
                      t_iIndex < t_aSqlElements.length;
                      t_iIndex++)
            {
                if  (matches(
                         tableName,
                         t_aSqlElements[t_iIndex].getDao(),
                         EnglishGrammarUtils.getInstance()))
                {
                    result = true;
                    break;
                }
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

        if  (!result)
        {
            String t_strSingularName =
                englishGrammarUtils.getSingular(t_strTableInLowerCase);

            result = daoId.equalsIgnoreCase(t_strSingularName);
        }

        if  (!result)
        {
            String t_strPluralName =
                englishGrammarUtils.getPlural(t_strTableInLowerCase);

            result = daoId.equalsIgnoreCase(t_strPluralName);
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
}
