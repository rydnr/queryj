//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armend?riz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecaba?as
                    Boadilla del monte
                    28660 Madrid
                    Spain

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
package org.acmsl.queryj.tools.metadata.engines.oracle;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.BigDecimalField;
import org.acmsl.queryj.CalendarField;
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

/**
 * Represents the USER_CONS_COLUMNS table in the persistence domain.
 * @author <a href="http://maven.acm-sl.org/queryj">QueryJ</a>
 */
public class OracleUserConsColumnsTable
    extends  Table
    implements  Singleton
{
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
    public StringField CONSTRAINT_NAME =
        new StringField("CONSTRAINT_NAME", this);

    /**
     * The user_cons_columns table table_name field.
     */
    public StringField TABLE_NAME =
        new StringField("TABLE_NAME", this);

    /**
     * The user_cons_columns table column_name field.
     */
    public StringField COLUMN_NAME =
        new StringField("COLUMN_NAME", this);

    /**
     * The user_constraints table position field.
     */
    public IntField POSITION =
        new IntField("POSITION", this);

    /**
     * Protected constructor to avoid accidental instantiation.
     * @param alias the table alias.
     */
    protected OracleUserConsColumnsTable(final String alias)
    {
        super("USER_CONS_COLUMNS", alias);
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected OracleUserConsColumnsTable()
    {
        this(null);
    }

    /**
     * Retrieves a OracleUserConsColumnsTable instance.
     * @param alias the desired table alias.
     * @return such instance.
     */
    public static OracleUserConsColumnsTable getInstance(final String alias)
    {
        OracleUserConsColumnsTable result = null;

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
    public static OracleUserConsColumnsTable getInstance()
    {
        return OracleUserConsColumnsTableSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the table name.
     * @return such name.
     */
    public String getTableName()
    {
        return "USER_CONS_COLUMNS";
    }

    /**
     * Retrieves <code>all</code> fields. It's equivalent to a star in a query.
     * @return such fields.
     */
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
