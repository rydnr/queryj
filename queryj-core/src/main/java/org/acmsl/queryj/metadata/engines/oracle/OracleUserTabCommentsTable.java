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
package org.acmsl.queryj.metadata.engines.oracle;

/*
 * Importing some QueryJ-Core classes.
 */
import org.acmsl.queryj.Literals;

/*
 * Importing some ACM-SL QueryJ-SQL classes.
 */
import org.acmsl.queryj.sql.Field;
import org.acmsl.queryj.sql.StringField;
import org.acmsl.queryj.sql.Table;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents the USER_TAB_TABUMNS table in the persistence domain.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class OracleUserTabCommentsTable
    extends Table
    implements Singleton
{
    public static final String USER_TAB_COMMENTS = "USER_TAB_COMMENTS";

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
        new StringField(Literals.TABLE_NAME_U, this);

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
        super(USER_TAB_COMMENTS, alias);
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected OracleUserTabCommentsTable()
    {
        this(USER_TAB_COMMENTS);
    }

    /**
     * Retrieves a OracleUserTabCommentsTable instance.
     * @param alias the desired table alias.
     * @return such instance.
     */
    @Nullable
    public static OracleUserTabCommentsTable getInstance(@Nullable final String alias)
    {
        @Nullable final OracleUserTabCommentsTable result;

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
        return USER_TAB_COMMENTS;
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
