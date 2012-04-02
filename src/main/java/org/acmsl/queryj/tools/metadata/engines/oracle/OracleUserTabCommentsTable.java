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
 * Filename: OracleUserTabCommentsTable.java
 *
 * Author: QueryJ
 *
 * Description: Represents the USER_TAB_COMMENTS table in the persistence
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
 * Represents the USER_TAB_TABUMNS table in the persistence domain.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class OracleUserTabCommentsTable
    extends  Table
    implements  Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class OracleUserTabCommentsTableSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final OracleUserTabCommentsTable SINGLETON =
            new OracleUserTabCommentsTable();
    }

    /**
     * The user_tab_comments table table_name field.
     */
    @NotNull
    public StringField TABLE_NAME =
        new StringField("TABLE_NAME", this);

    /**
     * The user_tab_comments table table_type field.
     */
    @NotNull
    public StringField TABLE_TYPE =
        new StringField("TABLE_TYPE", this);

    /**
     * The user_tab_comments table comments field.
     */
    @NotNull
    public StringField COMMENTS =
        new StringField("COMMENTS", this);

    /**
     * Protected constructor to avoid accidental instantiation.
     * @param alias the table alias.
     */
    protected OracleUserTabCommentsTable(final String alias)
    {
        super("USER_TAB_COMMENTS", alias);
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected OracleUserTabCommentsTable()
    {
        this(null);
    }

    /**
     * Retrieves a OracleUserTabCommentsTable instance.
     * @param alias the desired table alias.
     * @return such instance.
     */
    @Nullable
    public static OracleUserTabCommentsTable getInstance(@Nullable final String alias)
    {
        @Nullable OracleUserTabCommentsTable result = null;

        if  (alias != null)
        {
            result = new OracleUserTabCommentsTable(alias);
        }
        else
        {
            result = getInstance();
        }

        return result;
    }

    /**
     * Retrieves a OracleUserTabCommentsTable instance.
     * @return such instance.
     */
    @NotNull
    public static OracleUserTabCommentsTable getInstance()
    {
        return OracleUserTabCommentsTableSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the table name.
     * @return such name.
     */
    @NotNull
    public String getTableName()
    {
        return "USER_TAB_COMMENTS";
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
                TABLE_TYPE,
                COMMENTS
            };
    }
}
