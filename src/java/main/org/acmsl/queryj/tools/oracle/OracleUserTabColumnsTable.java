/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armend?riz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecaba?as
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: QueryJ
 *
 * Description: Represents the USER_TAB_COLUMNS table in the persistence domain.
 *
 */
package org.acmsl.queryj.tools.oracle;

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
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;

/**
 * Represents the USER_TAB_COLUMNS table in the persistence domain.
 * @author <a href="http://maven.acm-sl.org/queryj">QueryJ</a>
 */

public class OracleUserTabColumnsTable
    extends  Table
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * The user_tab_columns table table_name field.
     */
    public StringField TABLE_NAME =
        new StringField("TABLE_NAME", this);

    /**
     * The user_tab_columns table column_name field.
     */
    public StringField COLUMN_NAME =
        new StringField("COLUMN_NAME", this);

    /**
     * The user_tab_columns table data_type field.
     */
    public StringField DATA_TYPE =
        new StringField("DATA_TYPE", this);

    /**
     * The user_tab_columns table nullable field.
     */
    public StringField NULLABLE =
        new StringField("NULLABLE", this);

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
        this(null);
    }

    /**
     * Specifies a new weak reference.
     * @param table the table instance to use.
     */
    protected static void setReference(
        final OracleUserTabColumnsTable table)
    {
        singleton = new WeakReference(table);
    }

    /**
     * Retrieves the weak reference.
     * @return such reference.
     */
    protected static WeakReference getReference()
    {
        return singleton;
    }

    /**
     * Retrieves a OracleUserTabColumnsTable instance.
     * @param alias the desired table alias.
     * @return such instance.
     */
    public static OracleUserTabColumnsTable getInstance(final String alias)
    {
        OracleUserTabColumnsTable result = null;

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
    public static OracleUserTabColumnsTable getInstance()
    {
        OracleUserTabColumnsTable result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (OracleUserTabColumnsTable) reference.get();
        }

        if  (result == null) 
        {
            result = new OracleUserTabColumnsTable();

            setReference(result);
        }

        return result;
    }

    /**
     * Retrieves the table name.
     * @return such name.
     */
    public String getTableName()
    {
        return "USER_TAB_COLUMNS";
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
                TABLE_NAME,
                COLUMN_NAME,
                DATA_TYPE
            };
    }
}
