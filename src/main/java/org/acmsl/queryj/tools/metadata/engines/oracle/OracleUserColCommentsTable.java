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
 * Filename: OracleUserColCommentsTable.java
 *
 * Author: QueryJ
 *
 * Description: Represents the USER_COL_COMMENTS table in the persistence
 *              domain.
 *
 */
package org.acmsl.queryj.tools.metadata.engines.oracle;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.CalendarField;
import org.acmsl.queryj.BigDecimalField;
import org.acmsl.queryj.DoubleField;
import org.acmsl.queryj.Field;
import org.acmsl.queryj.IntField;
import org.acmsl.queryj.LongField;
import org.acmsl.queryj.StringField;
import org.acmsl.queryj.Table;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the USER_TAB_COLUMNS table in the persistence domain.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class OracleUserColCommentsTable
    extends  Table
    implements  Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class OracleUserColCommentsTableSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final OracleUserColCommentsTable SINGLETON =
            new OracleUserColCommentsTable();
    }

    /**
     * The user_col_comments table table_name field.
     */
    @NotNull
    public StringField TABLE_NAME =
        new StringField("TABLE_NAME", this);

    /**
     * The user_col_comments table column_name field.
     */
    @NotNull
    public StringField COLUMN_NAME =
        new StringField("COLUMN_NAME", this);

    /**
     * The user_col_comments table comments field.
     */
    @NotNull
    public StringField COMMENTS =
        new StringField("COMMENTS", this);

    /**
     * Protected constructor to avoid accidental instantiation.
     * @param alias the table alias.
     */
    protected OracleUserColCommentsTable(final String alias)
    {
        super("USER_COL_COMMENTS", alias);
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected OracleUserColCommentsTable()
    {
        this(null);
    }

    /**
     * Retrieves a OracleUserColCommentsTable instance.
     * @param alias the desired table alias.
     * @return such instance.
     */
    @Nullable
    public static OracleUserColCommentsTable getInstance(@Nullable final String alias)
    {
        @Nullable OracleUserColCommentsTable result = null;

        if  (alias != null)
        {
            result = new OracleUserColCommentsTable(alias);
        }
        else
        {
            result = getInstance();
        }

        return result;
    }

    /**
     * Retrieves a OracleUserColCommentsTable instance.
     * @return such instance.
     */
    @NotNull
    public static OracleUserColCommentsTable getInstance()
    {
        return OracleUserColCommentsTableSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the table name.
     * @return such name.
     */
    @NotNull
    public String getTableName()
    {
        return "USER_COL_COMMENTS";
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
                COMMENTS
            };
    }
}
