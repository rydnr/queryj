/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents SQL write operations.
 *
<<<<<<< WriteQuery.java
=======
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
>>>>>>> 1.4
 */
package org.acmsl.queryj;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.BigDecimalField;
import org.acmsl.queryj.CalendarField;
import org.acmsl.queryj.Condition;
import org.acmsl.queryj.DateField;
import org.acmsl.queryj.DoubleField;
import org.acmsl.queryj.Field;
import org.acmsl.queryj.IntField;
import org.acmsl.queryj.LongField;
import org.acmsl.queryj.Query;
import org.acmsl.queryj.Table;

/*
 * Importing some JDK classes.
 */
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Represents standard SQL insert queries.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
<<<<<<< WriteQuery.java
=======
 * @version $Revision$
>>>>>>> 1.4
 */
public abstract class WriteQuery
    extends  Query
{
    /**
     * The values.
     */
    private Map m__mValues;

    /**
     * The table.
     */
    private Table m__Table;

    /**
     * The escaping flags.
     */
    private Map m__mEscapingFlags;

    /**
     * Constructs a query.
     */
    public WriteQuery()
    {
        super();

        Map uniqueMap = new HashMap();
        immutableSetValues(uniqueMap);
        immutableSetEscapingFlags(uniqueMap);
    }

    /**
     * Specifies the table.
     * @param table the table.
     */
    protected final void immutableSetTable(final Table table)
    {
        m__Table = table;
    }

    /**
     * Specifies the table.
     * @param table the table.
     */
    protected void setTable(final Table table)
    {
        immutableSetTable(table);
    }

    /**
     * Retrieves the table.
     * @return such table.
     */
    protected Table getTable()
    {
        return m__Table;
    }

    /**
     * Specifies new value collection.
     * @param map the new map.
     */
<<<<<<< WriteQuery.java
    private void immutableSetValues(final Map map)
=======
    private void inmutableSetValues(final Map map)
>>>>>>> 1.4
    {
        m__mValues = map;
    }

    /**
     * Specifies new value collection.
     * @param map the new map.
     */
    protected void setValues(final Map map)
    {
        immutableSetValues(map);
    }

    /**
     * Retrieves the value collection.
     * @return such map.
     */
    protected Map getValues()
    {
        return m__mValues;
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
<<<<<<< WriteQuery.java
     * @precondition field != null
     */
    protected void putValue(final StringField field, final String value)
    {
        putValue(field, value, true);
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @precondition field != null
=======
     * @precondition field != null
>>>>>>> 1.4
     */
<<<<<<< WriteQuery.java
    protected void putValue(
        final StringField field, final String value, final boolean escape)
=======
    protected void putValue(final StringField field, final String value)
>>>>>>> 1.4
    {
<<<<<<< WriteQuery.java
        putValue(field, value, escape, getValues(), getEscapingFlags());
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @param values the values.
     * @param escapingFlags the escaping flags.
     * @precondition field != null
     * @precondition values != null
     * @precondition escapingFlags != null
     */
    protected void putValue(
        final StringField field,
        final String value,
        final boolean escape,
        final Map values,
        final Map escapingFlags)
    {
        String t_strValue = value;

        if  (t_strValue == null)
        {
            t_strValue = "null";
        }

        if  (!escape)
=======
        putValue(field, value, getValues());
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @param values the values.
     * @precondition field != null
     * @precondition values != null
     */
    protected void putValue(
        final StringField field, final String value, final Map values)
    {
        String t_strValue = value;

        if  (t_strValue == null)
>>>>>>> 1.4
        {
<<<<<<< WriteQuery.java
            escapingFlags.put(buildEscapingFlagKey(value), Boolean.FALSE);
        }

        values.put(field, t_strValue);
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final IntField field, final int value)
    {
        putValue(field, value, getValues());
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @param values the values.
     * @precondition field != null
     * @precondition values != null
     */
    protected void putValue(
        final IntField field, final int value, final Map values)
    {
        values.put(field, new Integer(value));
    }

    /**
     * Puts a new keyword-based value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final IntField field, final String value)
    {
        putValue(field, value, true);
    }

    /**
     * Puts a new keyword-based value.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @precondition field != null
     */
    protected void putValue(
        final IntField field, final String value, final boolean escape)
    {
        putValue(field, value, escape, getValues(), getEscapingFlags());
    }

    /**
     * Puts a new keyword-based value.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @param values the values.
     * @precondition field != null
     * @precondition values != null
     * @precondition escapingFlags != null
     */
    protected void putValue(
        final IntField field,
        final String value,
        final boolean escape,
        final Map values,
        final Map escapingFlags)
    {
        values.put(field, value);

        if  (!escape)
        {
            escapingFlags.put(buildEscapingFlagKey(value), Boolean.FALSE);
=======
            t_strValue = "null";
>>>>>>> 1.4
        }

        values.put(field, t_strValue);
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
<<<<<<< WriteQuery.java
     * @precondition field != null
     */
    protected void putValue(final LongField field, final long value)
    {
        putValue(field, value, getValues());
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     * @precondition values != null
=======
     * @precondition field != null
     */
    protected void putValue(final IntField field, final int value)
    {
        putValue(field, value, getValues());
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @param values the values.
     * @precondition field != null
     * @precondition values != null
     */
    protected void putValue(
        final IntField field, final int value, final Map values)
    {
        values.put(field, new Integer(value));
    }

    /**
     * Puts a new keyword-based value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final IntField field, final String value)
    {
        putValue(field, value, getValues());
    }

    /**
     * Puts a new keyword-based value.
     * @param field the field.
     * @param value the value.
     * @param values the values.
     * @precondition field != null
     * @precondition values != null
>>>>>>> 1.4
     */
<<<<<<< WriteQuery.java
    protected void putValue(final LongField field, final long value, final Map values)
=======
    protected void putValue(
        final IntField field, final String value, final Map values)
>>>>>>> 1.4
    {
<<<<<<< WriteQuery.java
        values.put(field, new Long(value));
    }

    /**
     * Puts a new keyword-based value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final LongField field, final String value)
    {
        putValue(field, value, true);
    }

    /**
     * Puts a new keyword-based value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(
        final LongField field, final String value, final boolean escape)
    {
        putValue(field, value, escape, getValues(), getEscapingFlags());
    }

    /**
     * Puts a new keyword-based value.
     * @param field the field.
     * @param value the value.
     * @param values the values.
     * @precondition field != null
     * @precondition values != null
     */
    protected void putValue(
        final LongField field,
        final String value,
        final boolean escape,
        final Map values,
        final Map escapingFlags)
    {
        values.put(field, value);

        if  (!escape)
        {
            escapingFlags.put(buildEscapingFlagKey(value), Boolean.FALSE);
        }
    }
=======
        values.put(field, value);
    }
>>>>>>> 1.4

<<<<<<< WriteQuery.java
    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final DoubleField field, final double value)
    {
        putValue(field, value, getValues());
=======
    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final LongField field, final long value)
    {
        putValue(field, value, getValues());
>>>>>>> 1.4
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
<<<<<<< WriteQuery.java
     * @param values the values.
     * @precondition field != null
     * @precondition values != null
=======
     * @precondition field != null
     * @precondition values != null
>>>>>>> 1.4
     */
<<<<<<< WriteQuery.java
    protected void putValue(
        final DoubleField field, final double value, final Map values)
=======
    protected void putValue(final LongField field, final long value, final Map values)
>>>>>>> 1.4
    {
<<<<<<< WriteQuery.java
        values.put(field, new Double(value));
    }
=======
        values.put(field, new Long(value));
    }

    /**
     * Puts a new keyword-based value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final LongField field, final String value)
    {
        putValue(field, value, getValues());
    }

    /**
     * Puts a new keyword-based value.
     * @param field the field.
     * @param value the value.
     * @param values the values.
     * @precondition field != null
     * @precondition values != null
     */
    protected void putValue(
        final LongField field, final String value, final Map values)
    {
        values.put(field, value);
    }
>>>>>>> 1.4

<<<<<<< WriteQuery.java
    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final DoubleField field, final String value)
    {
        putValue(field, value, true);
=======
    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final DoubleField field, final double value)
    {
        putValue(field, value, getValues());
>>>>>>> 1.4
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
<<<<<<< WriteQuery.java
     * @param escape to force value escaping.
     * @precondition field != null
=======
     * @param values the values.
     * @precondition field != null
     * @precondition values != null
>>>>>>> 1.4
     */
<<<<<<< WriteQuery.java
    protected void putValue(
        final DoubleField field, final String value, final boolean escape)
    {
        putValue(field, value, escape, getValues(), getEscapingFlags());
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @param values the values.
     * @param escapingFlags the escaping flags.
     * @precondition field != null
     * @precondition values != null
     * @precondition escapingFlags != null
     */
    protected void putValue(
        final DoubleField field,
        final String value,
        final boolean escape,
        final Map values,
        final Map escapingFlags)
=======
    protected void putValue(
        final DoubleField field, final double value, final Map values)
>>>>>>> 1.4
    {
<<<<<<< WriteQuery.java
        values.put(field, value);

        if  (!escape)
        {
            escapingFlags.put(buildEscapingFlagKey(value), Boolean.FALSE);
        }
    }
=======
        values.put(field, new Double(value));
    }
>>>>>>> 1.4

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final CalendarField field, final Calendar value)
    {
        putValue(field, value, getValues());
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @param values the values.
     * @precondition field != null
     * @precondition values != null
     */
    protected void putValue(
        final CalendarField field, final Calendar value, final Map values)
    {
<<<<<<< WriteQuery.java
        values.put(field, value);
    }

    /**
     * Puts a new keyword.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final CalendarField field, final String value)
    {
        putValue(field, value, true);
    }

    /**
     * Puts a new keyword.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @precondition field != null
     */
    protected void putValue(
        final CalendarField field, final String value, final boolean escape)
    {
        putValue(field, value, escape, getValues(), getEscapingFlags());
    }

    /**
     * Puts a new keyword.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @param values the values.
     * @param escapingFlags the escaping flags.
     * @precondition field != null
     * @precondition values != null
     * @precondition escapingFlags != null
     */
    protected void putValue(
        final CalendarField field,
        final String value,
        final boolean escape,
        final Map values,
        final Map escapingFlags)
    {
        values.put(field, value);

        if  (!escape)
        {
            escapingFlags.put(buildEscapingFlagKey(value), Boolean.FALSE);
        }
    }
=======
        values.put(field, value);
    }

    /**
     * Puts a new keyword.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final CalendarField field, final String value)
    {
        putValue(field, value, getValues());
    }

    /**
     * Puts a new keyword.
     * @param field the field.
     * @param value the value.
     * @param values the values.
     * @precondition field != null
     * @precondition values != null
     */
    protected void putValue(
        final CalendarField field, final String value, final Map values)
    {
        values.put(field, value);
    }
>>>>>>> 1.4

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final DateField field, final Date value)
    {
        putValue(field, value, getValues());
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
<<<<<<< WriteQuery.java
     * @param values the values.
     * @precondition field != null
     * @precondition values != null
     */
    protected void putValue(
        final DateField field, final Date value, final Map values)
    {
        values.put(field, value);
    }

    /**
     * Puts a new keyword.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
=======
     * @param values the values.
     * @precondition field != null
     * @precondition values != null
>>>>>>> 1.4
     */
<<<<<<< WriteQuery.java
    protected void putValue(final DateField field, final String value)
=======
    protected void putValue(
        final DateField field, final Date value, final Map values)
>>>>>>> 1.4
    {
<<<<<<< WriteQuery.java
        putValue(field, value, true);
    }

    /**
     * Puts a new keyword.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @precondition field != null
     */
    protected void putValue(
        final DateField field, final String value, final boolean escape)
    {
        putValue(field, value, escape, getValues(), getEscapingFlags());
    }

    /**
     * Puts a new keyword.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @param values the values.
     * @param escapingFlags the escaping flags.
     * @precondition field != null
     * @precondition values != null
     * @precondition escapingFlags != null
     */
    protected void putValue(
        final DateField field,
        final String value,
        final boolean escape,
        final Map values,
        final Map escapingFlags)
    {
        values.put(field, value);

        if  (!escape)
        {
            escapingFlags.put(buildEscapingFlagKey(value), Boolean.FALSE);
        }
    }
=======
        values.put(field, value);
    }

    /**
     * Puts a new keyword.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final DateField field, final String value)
    {
        putValue(field, value, getValues());
    }
>>>>>>> 1.4

<<<<<<< WriteQuery.java
    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(
        final BigDecimalField field, final BigDecimal value)
    {
        putValue(field, value, getValues());
=======
    /**
     * Puts a new keyword.
     * @param field the field.
     * @param value the value.
     * @param values the values.
     * @precondition field != null
     * @precondition values != null
     */
    protected void putValue(
        final DateField field, final String value, final Map values)
    {
        values.put(field, value);
>>>>>>> 1.4
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
<<<<<<< WriteQuery.java
     * @param values the values.
     * @precondition field != null
     * @precondition values != null
=======
     * @precondition field != null
>>>>>>> 1.4
     */
<<<<<<< WriteQuery.java
    protected void putValue(
        final BigDecimalField field, final BigDecimal value, final Map values)
=======
    protected void putValue(
        final BigDecimalField field, final BigDecimal value)
>>>>>>> 1.4
    {
<<<<<<< WriteQuery.java
        values.put(field, value);
    }
=======
        putValue(field, value, getValues());
    }
>>>>>>> 1.4

<<<<<<< WriteQuery.java
    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final Field field, final Object value)
    {
        putValue(field, value, true);
=======
    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @param values the values.
     * @precondition field != null
     * @precondition values != null
     */
    protected void putValue(
        final BigDecimalField field, final BigDecimal value, final Map values)
    {
        values.put(field, value);
>>>>>>> 1.4
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
<<<<<<< WriteQuery.java
     * @param escape to force value escaping.
     * @precondition field != null
=======
     * @precondition field != null
>>>>>>> 1.4
     */
<<<<<<< WriteQuery.java
    protected void putValue(final Field field, final Object value, final boolean escape)
=======
    protected void putValue(final Field field, final Object value)
>>>>>>> 1.4
    {
<<<<<<< WriteQuery.java
        putValue(field, value, escape, getValues(), getEscapingFlags());
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @param values the values.
     * @param escapingFlags the escaping flags.
     * @precondition field != null
     * @precondition values != null
     * @precondition escapingFlags != null
     */
    protected void putValue(
        final Field field,
        final Object value,
        final boolean escape,
        final Map values,
        final Map escapingFlags)
    {
        values.put(field, value);

        if  (!escape)
        {
            escapingFlags.put(buildEscapingFlagKey(value), Boolean.FALSE);
        }
=======
        putValue(field, value, getValues());
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @param values the values.
     * @precondition field != null
     * @precondition values != null
     */
    protected void putValue(
        final Field field, final Object value, final Map values)
    {
        values.put(field, value);
>>>>>>> 1.4
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @return the value.
     * @precondition field != null
     */
    protected Object getValue(final Field field)
    {
        return getValue(field, getValues());
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @param values the values.
     * @return the value.
     * @precondition field != null
     * @precondition values != null
     */
    protected Object getValue(final Field field, final Map values)
    {
        return values.get(field);
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @return the value.
     * @precondition field != null
     */
    protected String getValue(final StringField field)
    {
        return getValue(field, getValues());
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @param values the values.
     * @return the value.
     * @precondition field != null
     * @precondition values != null
     */
    protected String getValue(final StringField field, final Map values)
    {
        String result = "null";

        Object t_Result = values.get(field);

        if  (t_Result instanceof String)
        {
            result = (String) t_Result;
        }

        return result;
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @return the value.
     * @precondition field != null
     */
    protected int getValue(final IntField field)
    {
        return getValue(field, getValues());
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @param values the values.
     * @return the value.
     * @precondition field != null
     * @precondition values != null
     */
    protected int getValue(final IntField field, final Map values)
    {
        int result = -1;

        Object t_Result = values.get(field);

        if  (t_Result instanceof Integer)
        {
            result = ((Integer) t_Result).intValue();
        }

        return result;
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @return the value.
<<<<<<< WriteQuery.java
     * @precondition field != null
=======
     * @precondition field != null
     */
    protected long getValue(final LongField field)
    {
        return getValue(field, getValues());
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @param values the values.
     * @return the value.
     * @precondition field != null
     * @precondition values != null
>>>>>>> 1.4
     */
<<<<<<< WriteQuery.java
    protected long getValue(final LongField field)
    {
        return getValue(field, getValues());
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @param values the values.
     * @return the value.
     * @precondition field != null
     * @precondition values != null
     */
    protected long getValue(final LongField field, final Map values)
=======
    protected long getValue(final LongField field, final Map values)
>>>>>>> 1.4
    {
        long result = -1;

        Object t_Result = values.get(field);

        if  (t_Result instanceof Long)
        {
            result = ((Long) t_Result).longValue();
        }

        return result;
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @return the value.
     * @precondition field != null
     */
    protected double getValue(final DoubleField field)
    {
        return getValue(field, getValues());
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @param values the values.
     * @return the value.
     * @precondition field != null
     * @precondition values != null
     */
    protected double getValue(final DoubleField field, final Map values)
    {
        double result = -1.0;

        Object t_Result = values.get(field);

        if  (t_Result instanceof Double)
        {
            result = ((Double) t_Result).doubleValue();
        }

        return result;
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @return the value.
<<<<<<< WriteQuery.java
     * @precondition field != null
=======
     * @precondition field != null
     */
    protected Calendar getValue(final CalendarField field)
    {
        return getValue(field, getValues());
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @param values the values.
     * @return the value.
     * @precondition field != null
     * @precondition values != null
>>>>>>> 1.4
     */
<<<<<<< WriteQuery.java
    protected Calendar getValue(final CalendarField field)
    {
        return getValue(field, getValues());
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @param values the values.
     * @return the value.
     * @precondition field != null
     * @precondition values != null
     */
    protected Calendar getValue(final CalendarField field, final Map values)
=======
    protected Calendar getValue(final CalendarField field, final Map values)
>>>>>>> 1.4
    {
        Calendar result = null;

        Object t_Result = values.get(field);

        if  (t_Result instanceof Calendar)
        {
            result = (Calendar) t_Result;
        }

        return result;
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @return the value.
     * @precondition field != null
     */
    protected BigDecimal getValue(final BigDecimalField field)
    {
        return getValue(field, getValues());
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @param values the values.
     * @return the value.
     * @precondition field != null
     * @precondition values != null
     */
    protected BigDecimal getValue(
        final BigDecimalField field, final Map values)
    {
        BigDecimal result = null;

        Object t_Result = values.get(field);

        if  (t_Result instanceof BigDecimal)
        {
            result = (BigDecimal) t_Result;
        }

        return result;
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
<<<<<<< WriteQuery.java
     * @precondition field != null
     */
    protected void addValue(final StringField field, final String value)
    {
        addValue(field, value, true);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @precondition field != null
     */
    protected void addValue(
        final StringField field, final String value, final boolean escape)
    {
        addField(field);
        putValue(field, value, escape);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
=======
     * @precondition field != null
>>>>>>> 1.4
     */
<<<<<<< WriteQuery.java
    protected void addValue(final IntField field, final int value)
=======
    protected void addValue(final StringField field, final String value)
>>>>>>> 1.4
    {
<<<<<<< WriteQuery.java
        addField(field);
        putValue(field, value);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void addValue(final IntField field, final String value)
    {
        addValue(field, value, true);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @precondition field != null
     */
    protected void addValue(
        final IntField field, final String value, final boolean escape)
    {
        addField(field);
        putValue(field, value, escape);
=======
        addField(field);
        putValue(field, value);
>>>>>>> 1.4
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
<<<<<<< WriteQuery.java
     * @precondition field != null
     */
    protected void addValue(final LongField field, final long value)
    {
        addField(field);
        putValue(field, value);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void addValue(final LongField field, final String value)
    {
        addValue(field, value, true);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @precondition field != null
=======
     * @precondition field != null
     */
    protected void addValue(final IntField field, final int value)
    {
        addField(field);
        putValue(field, value);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
>>>>>>> 1.4
     */
<<<<<<< WriteQuery.java
    protected void addValue(
        final LongField field, final String value, final boolean escape)
=======
    protected void addValue(final IntField field, final String value)
>>>>>>> 1.4
    {
<<<<<<< WriteQuery.java
        addField(field);
        putValue(field, value, escape);
=======
        addField(field);
        putValue(field, value);
>>>>>>> 1.4
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
<<<<<<< WriteQuery.java
     * @precondition field != null
     */
    protected void addValue(final DoubleField field, final double value)
    {
        addField(field);
        putValue(field, value);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void addValue(final DoubleField field, final String value)
    {
        addValue(field, value, true);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @precondition field != null
=======
     * @precondition field != null
     */
    protected void addValue(final LongField field, final long value)
    {
        addField(field);
        putValue(field, value);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
>>>>>>> 1.4
     */
<<<<<<< WriteQuery.java
    protected void addValue(
        final DoubleField field, final String value, final boolean escape)
=======
    protected void addValue(final LongField field, final String value)
>>>>>>> 1.4
    {
<<<<<<< WriteQuery.java
        addField(field);
        putValue(field, value, escape);
=======
        addField(field);
        putValue(field, value);
>>>>>>> 1.4
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
<<<<<<< WriteQuery.java
     * @precondition field != null
     */
    protected void addValue(final CalendarField field, final Calendar value)
    {
        addField(field);
        putValue(field, value);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
=======
     * @precondition field != null
     */
    protected void addValue(final DoubleField field, final double value)
    {
        addField(field);
        putValue(field, value);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
>>>>>>> 1.4
     */
<<<<<<< WriteQuery.java
    protected void addValue(final CalendarField field, final String value)
=======
    protected void addValue(final DoubleField field, final String value)
>>>>>>> 1.4
    {
<<<<<<< WriteQuery.java
        addValue(field, value, true);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @precondition field != null
     */
    protected void addValue(
        final CalendarField field, final String value, final boolean escape)
    {
        addField(field);
        putValue(field, value, escape);
=======
        addField(field);
        putValue(field, value);
>>>>>>> 1.4
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
<<<<<<< WriteQuery.java
    protected void addValue(final DateField field, final Date value)
=======
    protected void addValue(final CalendarField field, final Calendar value)
>>>>>>> 1.4
    {
<<<<<<< WriteQuery.java
        addField(field);
        putValue(field, value);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void addValue(final DateField field, final String value)
    {
        addValue(field, value, true);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @precondition field != null
     */
    protected void addValue(
        final DateField field, final String value, final boolean escape)
    {
        addField(field);
        putValue(field, value, escape);
=======
        addField(field);
        putValue(field, value);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void addValue(final CalendarField field, final String value)
    {
        addField(field);
        putValue(field, value);
>>>>>>> 1.4
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
<<<<<<< WriteQuery.java
    protected void addValue(final BigDecimalField field, final BigDecimal value)
=======
    protected void addValue(final DateField field, final Date value)
>>>>>>> 1.4
    {
<<<<<<< WriteQuery.java
        addField(field);
        putValue(field, value);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void addValue(final BigDecimalField field, final String value)
    {
        addValue(field, value, true);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @precondition field != null
     */
    protected void addValue(
        final BigDecimalField field, final String value, final boolean escape)
    {
        addField(field);
        putValue(field, value, escape);
=======
        addField(field);
        putValue(field, value);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void addValue(final DateField field, final String value)
    {
        addField(field);
        putValue(field, value);
>>>>>>> 1.4
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
<<<<<<< WriteQuery.java
     * @precondition field != null
=======
     * @precondition field != null
     */
    protected void addValue(final BigDecimalField field, final BigDecimal value)
    {
        addField(field);
        putValue(field, value);
    }

    /**
     * Specifies the keyword of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
>>>>>>> 1.4
     */
<<<<<<< WriteQuery.java
    protected void addValue(final Field field, final Object value)
=======
    protected void addValue(final BigDecimalField field, final String value)
>>>>>>> 1.4
    {
<<<<<<< WriteQuery.java
        addValue(field, value, true);
=======
        addField(field);
        putValue(field, value);
>>>>>>> 1.4
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
<<<<<<< WriteQuery.java
     * @param escape to force value escaping.
     * @precondition field != null
     */
    protected void addValue(
        final Field field, final Object value, final boolean escape)
    {
        addField(field);
        putValue(field, value, escape);
    }

    /**
     * Specifies the escaping flags.
     * @param map such map.
     */
    protected final void immutableSetEscapingFlags(final Map map)
    {
        m__mEscapingFlags = map;
    }

    /**
     * Specifies the escaping flags.
     * @param map such map.
     */
    protected void setEscapingFlags(final Map map)
    {
        immutableSetEscapingFlags(map);
    }

    /**
     * Retrieves the escaping flags.
     * @return such map.
     */
    protected Map getEscapingFlags()
    {
        return m__mEscapingFlags;
    }

    /**
     * Checks whether given value should be escaped or not.
     * @param value the value to check.
     * @return <code>true</code> if given value should be escaped.
     * @precondition value != null
=======
     * @precondition field != null
>>>>>>> 1.4
     */
<<<<<<< WriteQuery.java
    protected boolean shouldBeEscaped(final Object value)
=======
    protected void addValue(final Field field, final Object value)
>>>>>>> 1.4
    {
<<<<<<< WriteQuery.java
        return
            shouldBeEscaped(
                value, getEscapingFlags(), QueryUtils.getInstance());
    }

    /**
     * Checks whether given value should be escaped or not.
     * @param value the value to check.
     * @param escapingFlags the flags.
     * @param queryUtils the <code>QueryUtils</code> instance.
     * @return <code>true</code> if given value should be escaped.
     * @precondition value != null
     * @precondition values != null
     * @precondition queryUtils != null
     */
    protected boolean shouldBeEscaped(
        final Object value, final Map escapingFlags, final QueryUtils queryUtils)
    {
        boolean result = true;

        Object t_Flag = escapingFlags.get(buildEscapingFlagKey(value));

        if  (   (t_Flag != null)
             && (t_Flag instanceof Boolean))
        {
            result = Boolean.TRUE.equals(t_Flag);
        }
        else
        {
            result = queryUtils.shouldBeEscaped(value);
        }

        return result;
    }

    /**
     * Builds the escaping flag key for given value.
     * @param value the value.
     * @return the escaping flag key.
     * @precondition value != null
     */
    protected Object buildEscapingFlagKey(final Object value)
    {
        return "||queryj||escaping-flag||" + value;
=======
        addField(field);
        putValue(field, value);
>>>>>>> 1.4
    }
}
