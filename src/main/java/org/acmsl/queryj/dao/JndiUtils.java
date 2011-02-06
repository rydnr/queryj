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
 * Filename: JndiUtils.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Accesses DataSources in JNDI.
 */
package org.acmsl.queryj.dao;

/*
 * Importing Spring classes.
 */
import org.springframework.jdbc.datasource.DataSourceUtils;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;

/*
 * Importing Java extension classes.
 */
import javax.sql.DataSource;

/**
 * Accesses DataSources in JNDI.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class JndiUtils
    implements  Singleton,
                Utils
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class JndiUtilsSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final JndiUtils SINGLETON = new JndiUtils();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     * @param alias the table alias.
     */
    protected JndiUtils() {};

    /**
     * Retrieves a <code>JndiUtils</code> instance.
     * @return such instance.
     */
    public static JndiUtils getInstance()
    {
        return JndiUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the <code>DataSource</code> from given
     * JNDI location.
     * @param location the location.
     * @return such datasource, or <code>null</code> if it's
     * not found.
     * @throws CannotGetJdbcConnectionException if the data source cannot
     * be retrieved from JNDI.
     */
    public DataSource getDataSourceFromJndi(final String location)
    {
        return DataSourceUtils.getDataSourceFromJndi(location);
    }
}
