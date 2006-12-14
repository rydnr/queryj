/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
                        chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

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
 * Filename: ThreadLocalBag.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides per-thread data containers.
 *
 *
 */
package org.acmsl.queryj.dao;

/*
 * Importing some JDK classes.
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Provides per-thread data containers.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @version $Revision: 39425 $
 */
public class ThreadLocalBag
{
    /**
     * The key to access the principal associated to the thread.
     */
    public static final String PRINCIPAL = "user";

    /**
     * The key to access the remote IP associated to the thread.
     */
    public static final String REMOTE_IP = "ip";

    /**
     * The key to access the accessed url associated the thread.
     */
    public static final String URL = "url";

    /**
     * The Map associated to this thread.
     */
    private static final ThreadLocal THREAD_BAG =
        new ThreadLocal()
        {
            /**
             * Retrieves the thread bag.
             * @return such data.
             */
            protected synchronized Object initialValue()
            {
                return new HashMap();
            }
        };

    /**
     * Retrieves the current threads' bag.
     * @return such bag.
     */
    public static Map getThreadBag()
    {
        return (Map) THREAD_BAG.get();
    }

    /**
     * Resets the current thread's bag.
     */
    public static void resetThreadBag()
    {
        THREAD_BAG.set(null);
    }
}
