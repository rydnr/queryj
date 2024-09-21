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
    MERCHANCONSILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: OracleUserConsColumnsTable.java
 *
 * Author: QueryJ
 *
 * Description: Represents the USER_CONS_COLUMNS table in the persistence
 *              domain.
 *
 */
package org.acmsl.queryj.metadata.engines.oracle;

/*
 * Importing some QueryJ-Core classes.
 */
import org.acmsl.queryj.Literals;

/*
 * Importing some QueryJ-SQL classes.
 */
import org.acmsl.queryj.sql.Field;
import org.acmsl.queryj.sql.IntField;
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
 * Represents the USER_CONS_COLUMNS table in the persistence domain.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class OracleUserConsColumnsTable
    extends Table
    implements Singleton
{
    public static final String USER_CONS_COLUMNS = "USER_CONS_COLUMNS";

    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class OracleUserConsColumnsTableSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final OracleUserConsColumnsTable SINGLETON =
            new OracleUserConsColumnsTable();
    }

    /**
     * The user_cons_columns table constraint_name field.
     */
    @NotNull
    public StringField CONSTRAINT_NAME =
        new StringField(Literals.CONSTRAINT_NAME_U, this);

    /**
     * The user_cons_columns table table_name field.
     */
    @NotNull
    public StringField TABLE_NAME =
        new StringField(Literals.TABLE_NAME_U, this);

    /**
     * The user_cons_columns table column_name field.
     */
    @NotNull
    public StringField COLUMN_NAME =
        new StringField(Literals.COLUMN_NAME_U, this);

    /**
     * The user_constraints table position field.
     */
    @NotNull
    public IntField POSITION =
        new IntField(Literals.POSITION_U, this);

    /**
     * Protected constructor to avoid accidental instantiation.
     * @param alias the table alias.
     */
    protected OracleUserConsColumnsTable(final String alias)
    {
        super(USER_CONS_COLUMNS, alias);
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected OracleUserConsColumnsTable()
    {
        this(USER_CONS_COLUMNS);
    }

    /**
     * Retrieves a OracleUserConsColumnsTable instance.
     * @param alias the desired table alias.
     * @return such instance.
     */
    @Nullable
    public static OracleUserConsColumnsTable getInstance(@Nullable final String alias)
    {
        @Nullable final OracleUserConsColumnsTable result;

        if  (alias != null)
        {
            result = new OracleUserConsColumnsTable(alias) { };
        }
        else
        {
            result = getInstance();
        }

        return result;
    }

    /**
     * Retrieves a OracleUserConsColumnsTable instance.
     * @return such instance.
     */
    @NotNull
    public static OracleUserConsColumnsTable getInstance()
    {
        return OracleUserConsColumnsTableSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the table name.
     * @return such name.
     */
    @NotNull
    public String getTableName()
    {
        return USER_CONS_COLUMNS;
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
                CONSTRAINT_NAME,
                TABLE_NAME,
                COLUMN_NAME,
                POSITION
            };
    }
}
