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
 * Filename: TableTemplateUtils.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides some useful methods when generating
 *              Table class.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecorationUtils;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;

/**
 * Provides some useful methods when generating Table class.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TableTemplateUtils
    implements  Singleton,
                Utils
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class TableTemplateUtilsSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final TableTemplateUtils SINGLETON = new TableTemplateUtils();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected TableTemplateUtils() {};

    /**
     * Retrieves a <code>TableTemplateUtils</code> instance.
     * @return such instance.
     */
    public static TableTemplateUtils getInstance()
    {
        return TableTemplateUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the name of the <code>Table</code> instance.
     * @param table the table name.
     * @return such name.
     * @precondition table != null
     */
    public String retrieveTableClassName(final String table)
    {
        return
            retrieveTableClassName(table, DecorationUtils.getInstance());
    }

    /**
     * Retrieves the name of the <code>Table</code> instance.
     * @param table the  table name.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such name.
     * @precondition table != null
     * @precondition decorationUtils != null
     */
    protected String retrieveTableClassName(
        final String table, final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(table) + "Table";
    }
}
