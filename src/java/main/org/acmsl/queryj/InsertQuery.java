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
 * Description: Represents standard SQL insert queries.
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
public abstract class InsertQuery
    extends  WriteQuery
{
    /**
     * Constructs a query.
     */
    public InsertQuery()
    {
        super();
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    public void value(StringField field, String value)
    {
        addValue(field, value);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     */
    public void value(StringField field)
    {
        addField(field);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    public void value(IntField field, int value)
    {
        addValue(field, value);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     */
    public void value(IntField field)
    {
        addField(field);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    public void value(LongField field, long value)
    {
        addValue(field, value);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     */
    public void value(LongField field)
    {
        addField(field);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    public void value(DoubleField field, double value)
    {
        addValue(field, value);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     */
    public void value(DoubleField field)
    {
        addField(field);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    public void value(CalendarField field, Calendar value)
    {
        addValue(field, value);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     */
    public void value(CalendarField field)
    {
        addField(field);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    public void value(DateField field, Date value)
    {
        addValue(field, value);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     */
    public void value(DateField field)
    {
        addField(field);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    public void value(BigDecimalField field, BigDecimal value)
    {
        addValue(field, value);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     */
    public void value(BigDecimalField field)
    {
        addField(field);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    public void value(Field field, Object value)
    {
        addValue(field, value);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     */
    public void value(Field field)
    {
        addField(field);
    }

    /**
     * Indicates which table the insert applies to.
     * @param table the table.
     */
    public void insertInto(Table table)
    {
        if  (table != null) 
        {
            setTable(table);
        }
    }

    // Serialization methods //

    /**
     * Outputs a text version of the query, in SQL format.
     * @return the SQL query.
     */
    public String toString()
    {
        StringBuffer t_sbResult = new StringBuffer();

        QueryUtils t_QueryUtils = QueryUtils.getInstance();

        Table t_Table =  getTable();
        List t_lFields = getFields();

        if  (   (t_QueryUtils != null)
             && (t_lFields    != null)
             && (t_Table      != null))
        {
            t_sbResult.append("INSERT INTO ");

            t_sbResult.append(t_Table);

            t_sbResult.append(" ( ");

            List t_alBriefFields = new ArrayList();
            List t_alValues = new ArrayList();

            Iterator t_FieldIterator = t_lFields.iterator();

            while  (   (t_FieldIterator != null)
                    && (t_FieldIterator.hasNext()))
            {
                Field t_Field = (Field) t_FieldIterator.next();

                if  (t_Field != null)
                {
                    t_alBriefFields.add(t_Field.toSimplifiedString());

                    String t_strValue = "";

                    Object t_Value = getValue(t_Field);

                    if  (t_Value == null)
                    {
                        t_strValue = "?";
                    }
                    else 
                    {
                        t_strValue = "" + t_Value;

                        if  (t_QueryUtils.shouldBeEscaped(t_Value))
                        {
                            t_strValue = "'" + t_strValue + "'";
                        }
                    }

                    t_alValues.add(t_strValue);
                }
            }

            t_sbResult.append(
                t_QueryUtils.concatenate(t_alBriefFields, ", "));

            t_sbResult.append(" ) VALUES ( ");

            t_sbResult.append(
                t_QueryUtils.concatenate(t_alValues, ", "));

            t_sbResult.append(" )");
        }

        return t_sbResult.toString();
    }
}
