/*
                        QueryJ Test

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
 * Filename: ForeignKeyTestHelper.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Helper class for per-foreign key Cucumber tests.
 *
 * Date: 2014/04/15
 * Time: 21:11
 *
 */
package org.acmsl.queryj.test;

/*
 * Importing JetBrains annotations.
 */
import cucumber.api.DataTable;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

import java.util.List;

/**
 * Helper class for per-foreign key Cucumber tests.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/15 21:11
 */
@ThreadSafe
public class ForeignKeyTestHelper
{
    /**
     * Singleton implementation to avoid double-locking check.
     */
    protected static final class ForeignKeyTestHelperSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final ForeignKeyTestHelper SINGLETON = new ForeignKeyTestHelper();
    }

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    @NotNull
    public static ForeignKeyTestHelper getInstance()
    {
        return ForeignKeyTestHelperSingletonContainer.SINGLETON;
    }

    /**
     * Defines the foreign keys based on the Cucumber information given.
     * @param tableInfo the Cucumber table.
     * @param foreignKeys the list to fill with the foreign keys.
     */
    public void defineInputForeignKeys(
        @NotNull final DataTable tableInfo, @NotNull final List<ForeignKey<String>> foreignKeys)
    {
        // TODO
    }

    /**
     * Defines the input columns, based on the Cucumber table given.
     * @param columnInfo the Cucumber table.
     * @param foreignKeys the foreign keys.
     * @return the foreign keys.
     */
    @NotNull
    public List<ForeignKey<String>> defineInputColumns(
        @NotNull final DataTable columnInfo, @NotNull final List<ForeignKey<String>> foreignKeys)
    {
        // TODO
        return foreignKeys;
    }

}
