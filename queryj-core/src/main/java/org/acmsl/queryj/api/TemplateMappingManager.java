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
 * Filename: TemplateMappingManager.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Manages the association between database engines and template
 *              factory classes.
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Manager;
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Manages the association between database engines and template
 * factory classes.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class TemplateMappingManager
    implements  Manager,
                Singleton
{
    /**
     * The DAO templates attribute name.
     */
    public static final String DAO_TEMPLATES = "DAO.templates";

    /**
     * The JDBC DAO template type.
     */
    @SuppressWarnings("unused")
    public static final String JDBC_DAO_TEMPLATE = "jdbc.dao.template";

    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class TemplateMappingManagerSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final TemplateMappingManager SINGLETON =
            new TemplateMappingManager();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected TemplateMappingManager() {}

    /**
     * Retrieves a <code>TemplateMappingManager</code> instance.
     * @return such instance.
     */
    @NotNull
    public static TemplateMappingManager getInstance()
    {
        return TemplateMappingManagerSingletonContainer.SINGLETON;
    }
}
