/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

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
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendáriz
 *
 * Description: Represents standard SQL delete queries.
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Represents standard SQL delete queries.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class DeleteQuery
    extends  WriteQuery
{
    /**
     * Constructs a query.
     */
    public DeleteQuery()
    {
        super();
    }

    /**
     * Indicates which table the delete applies to.
     * @param table the table.
     */
    public void deleteFrom(Table table)
    {
        if  (table != null) 
        {
            setTable(table);
        }
    }

    /**
     * Indicates a query condition.
     * @param condition such condition.
     */
    public void where(Condition condition)
    {
        if  (condition != null) 
        {
            addCondition(condition);
        }
    }

    /**
     * Indicates a query variable condition.
     * @param variableCondition such variable condition.
     */
    public void where(VariableCondition variableCondition)
    {
        if  (variableCondition != null) 
        {
            addCondition(variableCondition);
            addVariableCondition(variableCondition);
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
            t_sbResult.append("DELETE FROM ");

            t_sbResult.append(t_Table);

            List t_lConditions = getConditions();

            if  (   (t_lConditions != null)
                 && (t_lConditions.size() > 0))
            {
                t_sbResult.append(" WHERE ");

                t_sbResult.append(
                    t_QueryUtils.concatenate(t_lConditions, " AND ", true));
            }
        }

        return t_sbResult.toString();
    }
}
