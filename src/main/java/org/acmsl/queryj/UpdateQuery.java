//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents standard SQL update queries.
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
 * Represents standard SQL update queries.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class UpdateQuery
    extends  WriteQuery
{
    /**
     * Constructs a query.
     */
    public UpdateQuery()
    {
        super();
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    public void set(final StringField field, final String value)
    {
        addValue(field, value);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    public void set(final IntField field, final int value)
    {
        addValue(field, value);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    public void set(final LongField field, final long value)
    {
        addValue(field, value);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    public void set(final DoubleField field, final double value)
    {
        addValue(field, value);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    public void set(final CalendarField field, final Calendar value)
    {
        addValue(field, value);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    public void set(final DateField field, final Date value)
    {
        addValue(field, value);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    public void set(final BigDecimalField field, final BigDecimal value)
    {
        addValue(field, value);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     * @param value the value.
     */
    public void set(final Field field, final Object value)
    {
        addValue(field, value);
    }

    /**
     * Specifies the value of a field.
     * @param field the field.
     */
    public void set(final Field field)
    {
        addField(field);
        addVariableCondition(field.equals());
    }

    /**
     * Indicates which table the update applies to.
     * @param table the table.
     * @precondition table != null
     */
    public void update(final Table table)
    {
        setTable(table);
    }

    /**
     * Indicates a query condition.
     * @param condition such condition.
     * @precondition condition != null
     */
    public void where(final Condition condition)
    {
        addCondition(condition);
    }

    /**
     * Indicates a query variable condition.
     * @param variableCondition such variable condition.
     * @precondition variableCondition != null
     */
    public void where(final VariableCondition variableCondition)
    {
        addCondition(variableCondition);
        addVariableCondition(variableCondition);
    }

    // Serialization methods //

    /**
     * Outputs a text version of the query, in SQL format.
     * @return the SQL query.
     */
    public String toString()
    {
        return
            toString(
                getTable(),
                getFields(),
                getConditions(),
                QueryUtils.getInstance());
    }

    /**
     * Outputs a text version of the query, in SQL format.
     * @param table the table.
     * @param fields the fields.
     * @param conditions the conditions.
     * @param queryUtils the <code>QueryUtils</code> instance.
     * @return the SQL query.
     * @precondition tabhle != null
     * @precondition fields != null
     * @precondition queryUtils != null
     */
    protected String toString(
        final Table table,
        final List fields,
        final List conditions,
        final QueryUtils queryUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        t_sbResult.append("UPDATE ");

        t_sbResult.append(table);

        t_sbResult.append(" SET ");

        List t_alValues = new ArrayList();

        Iterator t_FieldIterator = fields.iterator();

        if  (t_FieldIterator != null)
        {
            while  (t_FieldIterator.hasNext())
            {
                Field t_Field = (Field) t_FieldIterator.next();

                String t_strValue = "";

                Object t_Value = getValue(t_Field);

                if  (t_Value == null)
                {
                    t_strValue = "?";
                }
                else 
                {
                    t_strValue = "" + t_Value;

                    if  (queryUtils.shouldBeEscaped(t_Value))
                    {
                        t_strValue = "'" + t_strValue + "'";
                    }
                }

                t_alValues.add(
                    t_Field.toSimplifiedString() + " = " + t_strValue);
            }
        }

        t_sbResult.append(
            queryUtils.concatenate(t_alValues, ", "));

        if  (   (conditions != null)
             && (conditions.size() > 0))
        {
            t_sbResult.append(" WHERE ");

            t_sbResult.append(
                queryUtils.concatenate(conditions, " AND ", true));
        }

        return t_sbResult.toString();
    }
}
