//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armend&aacute;riz
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
                    Urb. Valdecaba&ntilde;as
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armend&aacute;riz
 *
 * Description: Provides some useful methods when working with templates for
 *              the default theme..
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;

/**
 * Provides some useful methods when working with templates for the default
 * theme..
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro Armend&aacute;riz</a>
 */
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
    protected DefaultThemeUtils() {};

    /**
     * Retrieves a <code>DefaultThemeUtils</code> instance.
     * @return such instance.
     */
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
    public String buildDAOImplementationClassName(
        final String engineName, final String tableName)
    {
        return engineName + buildDAOClassName(tableName);
    }
}
