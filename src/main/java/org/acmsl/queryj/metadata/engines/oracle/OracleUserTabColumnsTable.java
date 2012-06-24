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
 * Filename: OracleUserTabColumnsTable.java
 *
 * Author: QueryJ
 *
 * Description: Represents the USER_TAB_COLUMNS table in the persistence
 *              domain.
 *
 */
package org.acmsl.queryj.metadata.engines.oracle;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some QueryJ-SQL classes.
 */
import org.acmsl.queryj.Field;
import org.acmsl.queryj.LongField;
import org.acmsl.queryj.StringField;
import org.acmsl.queryj.Table;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the USER_TAB_COLUMNS table in the persistence domain.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */

public class OracleUserTabColumnsTable
    extends Table
    implements  Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class OracleUserTabColumnsTableSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final OracleUserTabColumnsTable SINGLETON =
            new OracleUserTabColumnsTable();
    }

    /**
     * The user_tab_columns table table_name field.
     */
    @NotNull
    public StringField TABLE_NAME =
        new StringField("TABLE_NAME", this);

    /**
     * The user_tab_columns table column_name field.
     */
    @NotNull
    public StringField COLUMN_NAME =
        new StringField("COLUMN_NAME", this);

    /**
     * The user_tab_columns table data_type field.
     */
    @NotNull
    public StringField DATA_TYPE =
        new StringField("DATA_TYPE", this);

    /**
     * The user_tab_columns table nullable field.
     */
    @NotNull
    public StringField NULLABLE =
        new StringField("NULLABLE", this);

    /**
     * The user_tab_columns table column_id field.
     */
    @NotNull
    public LongField COLUMN_ID =
        new LongField("COLUMN_ID", this);

    /**
     * Protected constructor to avoid accidental instantiation.
     * @param alias the table alias.
     */
    protected OracleUserTabColumnsTable(final String alias)
    {
        super("USER_TAB_COLUMNS", alias);
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected OracleUserTabColumnsTable()
    {
        this("USER_TAB_COLUMNS");
    }

    /**
     * Retrieves a OracleUserTabColumnsTable instance.
     * @param alias the desired table alias.
     * @return such instance.
     */
    @Nullable
    public static OracleUserTabColumnsTable getInstance(@Nullable final String alias)
    {
        @Nullable OracleUserTabColumnsTable result;

        if  (alias != null)
        {
            result = new OracleUserTabColumnsTable(alias);
        }
        else
        {
            result = getInstance();
        }

        return result;
    }

    /**
     * Retrieves a OracleUserTabColumnsTable instance.
     * @return such instance.
     */
    @NotNull
    public static OracleUserTabColumnsTable getInstance()
    {
        return OracleUserTabColumnsTableSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the table name.
     * @return such name.
     */
    @NotNull
    public String getTableName()
    {
        return "USER_TAB_COLUMNS";
    }

    /**
     * Retrieves <code>all</code> fields. It's equivalent to a star in a query.
     * @return such fields.
     */
    @NotNull
    public Field[] getAll()
    {
        return
            new Field[]
            {
                TABLE_NAME,
                COLUMN_NAME,
                DATA_TYPE,
                NULLABLE
            };
    }
}
