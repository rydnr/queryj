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
 * Filename: DefaultThemeUtils.java
 *
 * Author: Jose San Leandro Armend&aacute;riz
 *
 * Description: Provides some useful methods when working with templates for
 *              the default theme..
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Provides some useful methods when working with templates for the default
 * theme..
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class DefaultThemeUtils
    implements  Singleton,
                Utils
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class DefaultThemeUtilsSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final DefaultThemeUtils SINGLETON = new DefaultThemeUtils();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected DefaultThemeUtils() {}

    /**
     * Retrieves a <code>DefaultThemeUtils</code> instance.
     * @return such instance.
     */
    @NotNull
    public static DefaultThemeUtils getInstance()
    {
        return DefaultThemeUtilsSingletonContainer.SINGLETON;
    }
    
    /**
     * Builds the base DAO name.
     * @param tableName the table name, in singular form.
     * @return such name.
     * @precondition tableName != null
     */
    @NotNull
    public String buildDAOClassName(final String tableName)
    {
        return tableName + "DAO";
    }

    /**
     * Builds the base DAO factory name.
     * @param tableName the table name, in singular form.
     * @return such name.
     * @precondition tableName != null
     */
    @NotNull
    public String buildDAOFactoryClassName(
        final String tableName)
    {
        return buildDAOClassName(tableName) + "Factory";
    }

    /**
     * Builds the base DAO factory implementation name.
     * @param engineName the engine name.
     * @param tableName the table name, in singular form.
     * @return such name.
     * @precondition engineName != null
     * @precondition tableName != null
     */
    @NotNull
    public String buildDAOFactoryImplementationClassName(
        final String engineName, final String tableName)
    {
        return engineName + buildDAOFactoryClassName(tableName);
    }

    /**
     * Builds the base DAO factory implementation name.
     * @param engineName the engine name.
     * @param tableName the table name, in singular form.
     * @return such name.
     * @precondition engineName != null
     * @precondition tableName != null
     */
    @NotNull
    public String buildDAOImplementationClassName(
        final String engineName, final String tableName)
    {
        return engineName + buildDAOClassName(tableName);
    }
}
