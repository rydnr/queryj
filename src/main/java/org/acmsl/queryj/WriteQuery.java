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
 * Filename: WriteQuery.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents SQL write operations.
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
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class WriteQuery
    extends  Query
{
    /**
     * The values.
     */
    private Map<Field,Object> m__mValues;

    /**
     * The table.
     */
    private Table m__Table;

    /**
     * The escaping flags.
     */
    private Map<String,Boolean> m__mEscapingFlags;

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
    private void immutableSetValues(final Map map)
    {
        m__mValues = map;
    }

    /**
     * Specifies new value collection.
     * @param map the new map.
     */
    protected void setValues(final Map<Field,Object> map)
    {
        immutableSetValues(map);
    }

    /**
     * Retrieves the value collection.
     * @return such map.
     */
    protected Map<Field,Object> getValues()
    {
        return m__mValues;
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
    protected void setEscapingFlags(final Map<String,Boolean> map)
    {
        immutableSetEscapingFlags(map);
    }

    /**
     * Retrieves the escaping flags.
     * @return such map.
     */
    protected Map<String,Boolean> getEscapingFlags()
    {
        return m__mEscapingFlags;
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
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
     */
    protected void putValue(
        final StringField field, final String value, final boolean escape)
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
        final StringField field,
        final String value,
        final boolean escape,
        final Map<Field,Object> values,
        final Map<String,Boolean> escapingFlags)
    {
        String t_strValue = value;

        if  (t_strValue == null)
        {
            t_strValue = "null";
        }

        if  (!escape)
        {
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
        values.put(field, Integer.valueOf(value));
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
        }
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
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
     */
    protected void putValue(
        final LongField field, final long value, final Map values)
    {
        values.put(field, Long.valueOf(value));
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

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final DoubleField field, final double value)
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
        final DoubleField field, final double value, final Map values)
    {
        values.put(field, Double.valueOf(value));
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final DoubleField field, final String value)
    {
        putValue(field, value, true);
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @precondition field != null
     */
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
    {
        values.put(field, value);

        if  (!escape)
        {
            escapingFlags.put(buildEscapingFlagKey(value), Boolean.FALSE);
        }
    }

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
     */
    protected void putValue(final DateField field, final String value)
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
        final BigDecimalField field, final BigDecimal value, final Map values)
    {
        values.put(field, value);
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void putValue(final Field field, final Object value)
    {
        putValue(field, value, true);
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     * @param escape to force value escaping.
     * @precondition field != null
     */
    protected void putValue(
        final Field field, final Object value, final boolean escape)
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
     */
    protected long getValue(final LongField field, final Map values)
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
     */
    protected Calendar getValue(final CalendarField field, final Map values)
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
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
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
     */
    protected void addValue(
        final LongField field, final String value, final boolean escape)
    {
        addField(field);
        putValue(field, value, escape);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
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
     */
    protected void addValue(
        final DoubleField field, final String value, final boolean escape)
    {
        addField(field);
        putValue(field, value, escape);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
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
     */
    protected void addValue(final CalendarField field, final String value)
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
        final CalendarField field, final String value, final boolean escape)
    {
        addField(field);
        putValue(field, value, escape);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void addValue(final DateField field, final Date value)
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
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void addValue(
        final BigDecimalField field, final BigDecimal value)
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
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     * @precondition field != null
     */
    protected void addValue(final Field field, final Object value)
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
        final Field field, final Object value, final boolean escape)
    {
        addField(field);
        putValue(field, value, escape);
    }

    /**
     * Checks whether given value should be escaped or not.
     * @param value the value to check.
     * @return <code>true</code> if given value should be escaped.
     * @precondition value != null
     */
    protected boolean shouldBeEscaped(final Object value)
    {
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
        final Object value,
        final Map escapingFlags,
        final QueryUtils queryUtils)
    {
        boolean result = true;

        Object t_Flag = escapingFlags.get(buildEscapingFlagKey(value));

        if  (t_Flag instanceof Boolean)
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
    protected String buildEscapingFlagKey(final Object value)
    {
        return "||queryj||escaping-flag||" + value;
    }
}
