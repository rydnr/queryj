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
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
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
 * @version $Revision$
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
     * Constructs a query.
     */
    public WriteQuery()
    {
        super();
        inmutableSetValues(new HashMap());
    }

    /**
     * Specifies the table.
     * @param table the table.
     */
    protected void setTable(Table table)
    {
        m__Table = table;
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
    private void inmutableSetValues(Map map)
    {
        m__mValues = map;
    }

    /**
     * Specifies new value collection.
     * @param map the new map.
     */
    protected void setValues(Map map)
    {
        inmutableSetValues(map);
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
     */
    protected void putValue(StringField field, String value)
    {
        if  (field != null)
        {
            if  (value == null)
            {
                value = "null";
            }

            Map t_mValues = getValues();

            if  (t_mValues != null)
            {
                t_mValues.put(field, value);
            }
        }
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     */
    protected void putValue(IntField field, int value)
    {
        if  (field != null)
        {
            Map t_mValues = getValues();

            if  (t_mValues != null)
            {
                t_mValues.put(field, new Integer(value));
            }
        }
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     */
    protected void putValue(LongField field, long value)
    {
        if  (field != null)
        {
            Map t_mValues = getValues();

            if  (t_mValues != null)
            {
                t_mValues.put(field, new Long(value));
            }
        }
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     */
    protected void putValue(DoubleField field, double value)
    {
        if  (field != null)
        {
            Map t_mValues = getValues();

            if  (t_mValues != null)
            {
                t_mValues.put(field, new Double(value));
            }
        }
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     */
    protected void putValue(CalendarField field, Calendar value)
    {
        if  (field != null)
        {
            Map t_mValues = getValues();

            if  (t_mValues != null)
            {
                t_mValues.put(field, value);
            }
        }
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     */
    protected void putValue(DateField field, Date value)
    {
        if  (field != null)
        {
            Map t_mValues = getValues();

            if  (t_mValues != null)
            {
                t_mValues.put(field, value);
            }
        }
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     */
    protected void putValue(BigDecimalField field, BigDecimal value)
    {
        if  (field != null)
        {
            Map t_mValues = getValues();

            if  (t_mValues != null)
            {
                t_mValues.put(field, value);
            }
        }
    }

    /**
     * Puts a new value.
     * @param field the field.
     * @param value the value.
     */
    protected void putValue(Field field, Object value)
    {
        if  (field != null)
        {
            Map t_mValues = getValues();

            if  (t_mValues != null)
            {
                t_mValues.put(field, value);
            }
        }
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @return the value.
     */
    protected Object getValue(Field field)
    {
        Object result = null;

        if  (field != null)
        {
            Map t_mValues = getValues();

            if  (t_mValues != null)
            {
                result = t_mValues.get(field);
            }
        }

        return result;
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @return the value.
     */
    protected String getValue(StringField field)
    {
        String result = "null";

        if  (field != null)
        {
            Map t_mValues = getValues();

            if  (t_mValues != null)
            {
                result = (String) t_mValues.get(field);
            }
        }

        return result;
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @return the value.
     */
    protected int getValue(IntField field)
    {
        int result = -1;

        if  (field != null)
        {
            Map t_mValues = getValues();

            if  (t_mValues != null)
            {
                result = ((Integer) t_mValues.get(field)).intValue();
            }
        }

        return result;
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @return the value.
     */
    protected long getValue(LongField field)
    {
        long result = -1;

        if  (field != null)
        {
            Map t_mValues = getValues();

            if  (t_mValues != null)
            {
                result = ((Long) t_mValues.get(field)).longValue();
            }
        }

        return result;
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @return the value.
     */
    protected double getValue(DoubleField field)
    {
        double result = -1.0;

        if  (field != null)
        {
            Map t_mValues = getValues();

            if  (t_mValues != null)
            {
                result = ((Double) t_mValues.get(field)).doubleValue();
            }
        }

        return result;
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @return the value.
     */
    protected Calendar getValue(CalendarField field)
    {
        Calendar result = null;

        if  (field != null)
        {
            Map t_mValues = getValues();

            if  (t_mValues != null)
            {
                result = (Calendar) t_mValues.get(field);
            }
        }

        return result;
    }

    /**
     * Retrieves the value of given field.
     * @param field the field.
     * @return the value.
     */
    protected BigDecimal getValue(BigDecimalField field)
    {
        BigDecimal result = null;

        if  (field != null)
        {
            Map t_mValues = getValues();

            if  (t_mValues != null)
            {
                result = (BigDecimal) t_mValues.get(field);
            }
        }

        return result;
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    protected void addValue(StringField field, String value)
    {
        if  (field != null)
        {
            addField(field);
            putValue(field, value);
        }
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    protected void addValue(IntField field, int value)
    {
        if  (field != null)
        {
            addField(field);
            putValue(field, value);
        }
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    protected void addValue(LongField field, long value)
    {
        if  (field != null)
        {
            addField(field);
            putValue(field, value);
        }
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    protected void addValue(DoubleField field, double value)
    {
        if  (field != null)
        {
            addField(field);
            putValue(field, value);
        }
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    protected void addValue(CalendarField field, Calendar value)
    {
        if  (field != null)
        {
            addField(field);
            putValue(field, value);
        }
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    protected void addValue(DateField field, Date value)
    {
        if  (field != null)
        {
            addField(field);
            putValue(field, value);
        }
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    protected void addValue(BigDecimalField field, BigDecimal value)
    {
        if  (field != null)
        {
            addField(field);
            putValue(field, value);
        }
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    protected void addValue(Field field, Object value)
    {
        if  (field != null)
        {
            addField(field);
            putValue(field, value);
        }
    }
}
