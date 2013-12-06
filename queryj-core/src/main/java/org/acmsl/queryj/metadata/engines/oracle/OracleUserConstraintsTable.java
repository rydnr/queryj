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
    RESPONSIBILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: OracleUserConstraintsTable.java
 *
 * Author: QueryJ
 *
 * Description: Represents the USER_CONSTRAINTS table in the persistence
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
import org.acmsl.queryj.IntField;
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.StringField;
import org.acmsl.queryj.Table;

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
 * Represents the USER_CONSTRAINTS table in the persistence domain.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class OracleUserConstraintsTable
    extends Table
    implements  Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class OracleUserConstraintsTableSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final OracleUserConstraintsTable SINGLETON =
            new OracleUserConstraintsTable();
    }

    /**
     * The user_constraints table constraint_name field.
     */
    @NotNull
    public StringField CONSTRAINT_NAME =
        new StringField(Literals.CONSTRAINT_NAME_U, this);

    /**
     * The user_constraints table r_constraint_name field.
     */
    @NotNull
    public StringField R_CONSTRAINT_NAME =
        new StringField("R_CONSTRAINT_NAME", this);

    /**
     * The user_constraints table constraint_type field.
     */
    @NotNull
    @SuppressWarnings("unused")
    public StringField CONSTRAINT_TYPE =
        new StringField("CONSTRAINT_TYPE", this);

    /**
     * The user_constraints table table_name field.
     */
    @NotNull
    public StringField TABLE_NAME =
        new StringField(Literals.TABLE_NAME_U, this);

    /**
     * The user_constraints table column_name field.
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
    protected OracleUserConstraintsTable(final String alias)
    {
        super(Literals.USER_CONSTRAINTS_U, alias);
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected OracleUserConstraintsTable()
    {
        this(Literals.USER_CONSTRAINTS_U);
    }

    /**
     * Retrieves a OracleUserConstraintsTable instance.
     * @param alias the desired table alias.
     * @return such instance.
     */
    @Nullable
    public static OracleUserConstraintsTable getInstance(@Nullable final String alias)
    {
        @Nullable final OracleUserConstraintsTable result;

        if  (alias != null)
        {
            result = new OracleUserConstraintsTable(alias);
        }
        else
        {
            result = getInstance();
        }

        return result;
    }

    /**
     * Retrieves a OracleUserConstraintsTable instance.
     * @return such instance.
     */
    @NotNull
    public static OracleUserConstraintsTable getInstance()
    {
        return OracleUserConstraintsTableSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the table name.
     * @return such name.
     */
    @NotNull
    public String getTableName()
    {
        return Literals.USER_CONSTRAINTS_U;
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
                R_CONSTRAINT_NAME,
                TABLE_NAME,
                COLUMN_NAME,
                POSITION
            };
    }

    @Override
    public String toString()
    {
        return "OracleUserConstraintsTable{" +
               "COLUMN_NAME=" + COLUMN_NAME +
               ", CONSTRAINT_NAME=" + CONSTRAINT_NAME +
               ", R_CONSTRAINT_NAME=" + R_CONSTRAINT_NAME +
               ", CONSTRAINT_TYPE=" + CONSTRAINT_TYPE +
               ", TABLE_NAME=" + TABLE_NAME +
               ", POSITION=" + POSITION +
               '}';
    }
}
