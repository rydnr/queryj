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
 * Filename: DebugUtils.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Provides simple stuff to help with debugging.
 *
 * Date: 7/29/12
 * Time: 7:55 AM
 *
 */
package org.acmsl.queryj.tools;

/*
 * Importing some ACM-SL Java Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Provides simple stuff to help with debugging.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2012/07/29
 */
@ThreadSafe
public class DebugUtils
    implements Utils,
               Singleton
{
    /**
     * Protected constructor.
     */
    protected DebugUtils() {}

    /**
     * Singleton implemented to avoid double-locking check.
     */
    protected static class DebugUtilsSingletonContainer
    {
        /**
         * The actual singleton.
         */
        protected static final DebugUtils SINGLETON = new DebugUtils();
    }

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    @NotNull
    public static DebugUtils getInstance()
    {
        return DebugUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Checks whether to intercept the flow while debugging table logic.
     * @param table the table name.
     * @return <code>true</code> in such case.
     */
    public boolean debugEnabledForTable(@NotNull final String table)
    {
        return table.equalsIgnoreCase(System.getProperty("queryj.table.debug"));
    }

    /**
     * Checks whether to intercept the flow while debugging custom-result logic.
     * @param resultId the id of the result.
     * @return <code>true</code> in such case.
     * @param <T> the id type.
     */
    public <T> boolean debugEnabledForResultId(@NotNull final T resultId)
    {
        return ("" + resultId).equalsIgnoreCase(System.getProperty("queryj.customresult.debug"));
    }
}
