/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides some useful methods when generating DAO classes
 *              via DAO template instances.
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
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.IdentifiableElement;
import org.acmsl.queryj.tools.customsql.ConnectionFlagsElement;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;

/*
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Provides some useful methods when generating DAO classes
 * via DAO template instances.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class DAOTemplateUtils
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
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected DAOTemplateUtils() {};

    /**
     * Specifies a new weak reference.
     * @param utils the utils instance to use.
     */
    protected static void setReference(final DAOTemplateUtils utils)
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
     * Retrieves a DAOTemplateUtils instance.
     * @return such instance.
     */
    public static DAOTemplateUtils getInstance()
    {
        DAOTemplateUtils result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (DAOTemplateUtils) reference.get();
        }

        if  (result == null) 
        {
            result = new DAOTemplateUtils();

            setReference(result);
        }

        return result;
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
}
