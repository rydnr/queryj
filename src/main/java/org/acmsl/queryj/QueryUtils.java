//;-*- mode: java -*-
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
 * Filename: QueryUtils.java
 *
 * Author: Jose San Leandro Armend&aacute;riz
 *
 * Description: Provides some useful methods when working with queries.
 *
 */
package org.acmsl.queryj;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.Query;
import org.acmsl.queryj.SelectQuery;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;

/*
 * Importing some JDK classes.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Provides some useful methods when working with queries.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class QueryUtils
    implements  Singleton,
                Utils
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class QueryUtilsSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final QueryUtils SINGLETON = new QueryUtils();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected QueryUtils() {};

    /**
     * Retrieves a <code>QueryUtils</code> instance.
     * @return such instance.
     */
    public static QueryUtils getInstance()
    {
        return QueryUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Concatenates all elements of given collection using a separator.
     * @param items the collection.
     * @param separator the separator.
     * @return such concatenation.
     */
    public String concatenate(final Collection items, final String separator)
    {
        return concatenate(items, separator, false);
    }

    /**
     * Concatenates all elements of given collection using a separator.
     * @param items the collection.
     * @param separator the separator.
     * @param simplify if any fields in given collection should appear
     * without explicit table information.
     * @return such concatenation.
     */
    public String concatenate(
        final Collection items, final String separator, final boolean simplify)
    {
        StringBuffer t_sbResult = new StringBuffer();

        if  (items != null) 
        {
            Iterator t_itItems = items.iterator();

            if  (t_itItems.hasNext()) 
            {
                t_sbResult.append(concatenate(t_itItems.next(), simplify));
            }
            
            while  (t_itItems.hasNext())
            {
                t_sbResult.append(separator);
                t_sbResult.append(concatenate(t_itItems.next(), simplify));
            }
        }

        return t_sbResult.toString();
    }

    /**
     * Manages the concatenation of a concrete item.
     * @param simplify if any fields in given collection should appear
     * without explicit table information.
     * @return such text.
     */
    protected String concatenate(final Object item, final boolean simplify)
    {
        String result = "null";

        if  (item != null)
        {
            if  (simplify)
            {
                if  (item instanceof Field)
                {
                    result = ((Field) item).toSimplifiedString();
                }
                else if  (item instanceof Condition)
                {
                    result = ((Condition) item).toSimplifiedString();
                }
                else 
                {
                    result = item.toString();
                }
            }
            else 
            {
                result = item.toString();
            }
        }

        return result;
    }

    /**
     * Checks if given object should be escaped inside a query or not.
     * @param object the element.
     * @return <code>true</code> if such object should be escaped.
     */
    public boolean shouldBeEscaped(final Object object)
    {
        boolean result = false;

        if  (object != null) 
        {
            result = true;

            if  (   (object instanceof Field)
                 || (object instanceof Integer) 
                 || (object instanceof Long)
                 || (object instanceof Double))
            {
                result = false;
            }
        }

        return result;
    }

    /**
     * Converts a clob to a string.
     * @param clob the clob to convert.
     * @return the clob contents.
     * @throws SQLException if the CLOB cannot be processed.
     * @see <a href="http://www.opengroup.org/bookstore/catalog/c449.htm">SQL 2</a>.
     * @precondition clob != null
     */
    public String clobToString(final Clob clob)
        throws  SQLException
    {
        StringBuffer t_sbResult = new StringBuffer();

        SQLException t_ExceptionToThrow = null;

        BufferedReader t_Reader =
            new BufferedReader(clob.getCharacterStream());

        try 
        {
            String t_strLine = t_Reader.readLine();

            while  (t_strLine != null)
            {
                t_sbResult.append(t_strLine);

                t_strLine = t_Reader.readLine();
            } // end of while  ()
            
        }
        catch  (final IOException ioException)
        {
            t_ExceptionToThrow =
                new SQLException(
                    "cannot.read.clob",
                    "22021"); // "Translation result not in target repertoire"

            t_ExceptionToThrow.initCause(ioException);
        }
        finally
        {
            try
            {
                t_Reader.close();
            }
            catch  (final IOException ioException)
            {
                LogFactory.getLog(QueryUtils.class).warn(
                    "Cannot close CLOB stream.",
                    ioException);
            }
        }

        if  (t_ExceptionToThrow != null)
        {
            throw t_ExceptionToThrow;
        }

        return t_sbResult.toString();
    }
}
