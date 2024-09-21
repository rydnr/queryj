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
 * Filename: OracleUserTablesTable.java
 *
 * Author: QueryJ
 *
 * Description: Represents the USER_TABLES table in the persistence domain.
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
import org.acmsl.queryj.sql.StringField;
import org.acmsl.queryj.sql.Table;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents the USER_TABLES table in the persistence domain.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class OracleUserTablesTable
    extends Table
    implements  Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class OracleUserTablesTableSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final OracleUserTablesTable SINGLETON =
            new OracleUserTablesTable();
    }

    /**
     * The user_tables table table_name field.
     */
    @NotNull
    public StringField TABLE_NAME =
        new StringField(Literals.TABLE_NAME_U, this);

    /**
     * Protected constructor to avoid accidental instantiation.
     * @param alias the table alias.
     */
    protected OracleUserTablesTable(final String alias)
    {
        super("USER_TABLES", alias);
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected OracleUserTablesTable()
    {
        this("USER_TABLES");
    }

    /**
     * Retrieves a OracleUserTablesTable instance.
     * @param alias the desired table alias.
     * @return such instance.
     */
    @Nullable
    public static OracleUserTablesTable getInstance(@Nullable final String alias)
    {
        @Nullable OracleUserTablesTable result;

        if  (alias != null)
        {
            result = new OracleUserTablesTable(alias);
        }
        else
        {
            result = getInstance();
        }

        return result;
    }

    /**
     * Retrieves a OracleUserTablesTable instance.
     * @return such instance.
     */
    @NotNull
    public static OracleUserTablesTable getInstance()
    {
        return OracleUserTablesTableSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the table name.
     * @return such name.
     */
    @NotNull
    public String getTableName()
    {
        return "USER_TABLES";
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
                TABLE_NAME
            };
    }
}
