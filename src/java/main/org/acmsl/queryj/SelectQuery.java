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
 * Description: Standard SQL select query.
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
import org.acmsl.queryj.Condition;
import org.acmsl.queryj.Field;
import org.acmsl.queryj.Query;
import org.acmsl.queryj.Table;

/*
 * Importing some JDK classes.
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents standard SQL select queries.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class SelectQuery
    extends  Query
{
    /**
     * The ordering fields.
     */
    private List m__lOrderingFields;

    /**
     * The grouping fields.
     */
    private List m__lGroupingFields;

    /**
     * Constructs a query.
     */
    public SelectQuery()
    {
        super();
        inmutableSetOrderingFields(new ArrayList());
        inmutableSetGroupingFields(new ArrayList());
    }

    /**
     * Specifies new ordering field collection.
     * @param list the new list.
     */
    private void inmutableSetOrderingFields(List list)
    {
        m__lOrderingFields = list;
    }

    /**
     * Specifies new ordering field collection.
     * @param list the new list.
     */
    protected void setOrderingFields(List list)
    {
        inmutableSetOrderingFields(list);
    }

    /**
     * Retrieves the ordering field collection.
     * @return such list.
     */
    protected List getOrderingFields()
    {
        return m__lOrderingFields;
    }

    /**
     * Adds a new ordering field.
     * @param orderingField the ordering field to add.
     */
    protected void addOrderingField(Field orderingField)
    {
        if  (orderingField != null) 
        {
            List t_OrderingFields = getOrderingFields();

            if  (t_OrderingFields != null) 
            {
                t_OrderingFields.add(orderingField);
            }
        }
    }

    /**
     * Retrieves the position of given ordering field on the query.
     * @param field the field to find.
     * @return its position, or -1 if such field doesn't belong to
     * this query.
     */
    protected int getOrderingFieldIndex(Field field)
    {
        return getIndex(getOrderingFields(), field);
    }

    /**
     * Specifies new grouping field collection.
     * @param list the new list.
     */
    private void inmutableSetGroupingFields(List list)
    {
        m__lGroupingFields = list;
    }

    /**
     * Specifies new grouping field collection.
     * @param list the new list.
     */
    protected void setGroupingFields(List list)
    {
        inmutableSetGroupingFields(list);
    }

    /**
     * Retrieves the grouping field collection.
     * @return such list.
     */
    protected List getGroupingFields()
    {
        return m__lGroupingFields;
    }

    /**
     * Adds a new grouping field.
     * @param groupingField the grouping field to add.
     */
    protected void addGroupingField(Field groupingField)
    {
        if  (groupingField != null) 
        {
            List t_GroupingFields = getGroupingFields();

            if  (t_GroupingFields != null) 
            {
                t_GroupingFields.add(groupingField);
            }
        }
    }

    /**
     * Retrieves the position of given grouping field on the query.
     * @param field the field to find.
     * @return its position, or -1 if such field doesn't belong to
     * this query.
     */
    protected int getGroupingFieldIndex(Field field)
    {
        return getIndex(getGroupingFields(), field);
    }

    /**
     * Selects a field.
     * @param field the field to select.
     */
    public void select(Field field)
    {
        if  (field != null) 
        {
            addField(field);
            //m__Factory.add(this, field);
        }
    }

    /**
     * Indicates which table participates in the query.
     * @param table the table.
     */
    public void from(Table table)
    {
        if  (table != null) 
        {
            addTable(table);
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

    /**
     * Indicates a field to be used to group the results.
     * @param groupingField such field.
     */
    public void groupBy(Field groupingField)
    {
        if  (groupingField != null) 
        {
            addGroupingField(groupingField);
        }
    }

    /**
     * Indicates a field to be used to order the results.
     * @param orderingField such field.
     */
    public void orderBy(Field orderingField)
    {
        if  (orderingField != null) 
        {
            addOrderingField(orderingField);
        }
    }

    /**
     * Looks for the records that match the filter.
     * @return the result set.
     * @exception SQLException if an error occurs.
     */
    public QueryResultSet retrieveMatchingResults()
        throws  SQLException
    {
        QueryResultSet result = null;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null)
        {
            result =
                new QueryResultSet(
                    this,
                    t_Statement.executeQuery(toString())) {};
        }

        return result;
    }

    /**
     * Executes given update operation using field references.
     * @param sql (Taken from Sun's Javadoc) must be an SQL INSERT, UPDATE
     * or DELETE statement or an SQL statement that returns nothing.
     * @param fields the fields.
     * @return (Taken from Sun's Javadoc) either the row count for INSERT,
     * UPDATE or DELETE statements, or 0 for SQL statements that return
     * nothing.
     * @exception SQLException if an error occurs.
     */
    public int executeUpdate(String sql, Field[] fields)
        throws  SQLException
    {
        int result = 0;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            int[] t_aiIndexes = new int[fields.length];

            for  (int t_iIndex = 0; t_iIndex < fields.length; t_iIndex++) 
            {
                t_aiIndexes[t_iIndex] = getFieldIndex(fields[t_iIndex]);
            }

            result = t_Statement.executeUpdate(sql, t_aiIndexes);
        }

        return result;
    }

    /**
     * Executes given update operation using field references.
     * @param sql (Taken from Sun's Javadoc) must be an SQL INSERT, UPDATE
     * or DELETE statement or an SQL statement that returns nothing.
     * @param fields the fields.
     * @return (Taken from Sun's Javadoc) true if the first result is a
     * ResultSet object; false if it is an update count or there are no
     * results.
     * @exception SQLException if an error occurs.
     */
    public boolean execute(String sql, Field[] fields)
        throws  SQLException
    {
        boolean result = false;

        Statement t_Statement = getPreparedStatement();

        if  (t_Statement != null) 
        {
            int[] t_aiIndexes = new int[fields.length];

            for  (int t_iIndex = 0; t_iIndex < fields.length; t_iIndex++) 
            {
                t_aiIndexes[t_iIndex] = getFieldIndex(fields[t_iIndex]);
            }

            result = t_Statement.execute(sql, t_aiIndexes);
        }

        return result;
    }

    /**
     * Processes given tables to appear in "FROM" clauses.
     * @param tables the tables to process.
     * @param separator the separator.
     * @return the processed tables.
     */
    protected List processTables(List tables, String separator)
    {
        List result = tables;

        if  (result != null)
        {
            result = new ArrayList();

            Iterator t_itTables = tables.iterator();

            Table t_CurrentTable = null;

            TableAlias t_CurrentTableAlias = null;

            StringBuffer t_sbCurrentTable = null;

            while  (   (t_itTables != null)
                    && (t_itTables.hasNext()))
            {
                t_CurrentTable = (Table) t_itTables.next();

                if  (t_CurrentTable != null)
                {
                    t_sbCurrentTable = new StringBuffer();

                    t_sbCurrentTable.append(t_CurrentTable.getName());

                    t_CurrentTableAlias = t_CurrentTable.getTableAlias();

                    if  (t_CurrentTableAlias != null)
                    {
                        t_sbCurrentTable.append(separator);
                        t_sbCurrentTable.append(t_CurrentTableAlias);
                    }

                    result.add(t_sbCurrentTable);
                }
            }
        }
        
        return result;
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

        if  (t_QueryUtils != null) 
        {
            t_sbResult.append("SELECT ");

            t_sbResult.append(
                t_QueryUtils.concatenate(getFields(), ", "));

            List t_lTables = getTables();

            if  (t_lTables != null)
            {
                t_sbResult.append(" FROM ");

                t_sbResult.append(
                    t_QueryUtils.concatenate(processTables(t_lTables, " "), ", "));

                List t_lConditions = getConditions();

                if  (   (t_lConditions != null)
                     && (t_lConditions.size() > 0))
                {
                    t_sbResult.append(" WHERE ");

                    t_sbResult.append(
                        t_QueryUtils.concatenate(t_lConditions, " AND "));
                }

                List t_lAdditionalFields = getGroupingFields();

                if  (   (t_lAdditionalFields != null)
                     && (t_lAdditionalFields.size() > 0))
                {
                    t_sbResult.append(" GROUP BY ");

                    t_sbResult.append(
                        t_QueryUtils.concatenate(t_lAdditionalFields, ", "));
                }
            
                t_lAdditionalFields = getOrderingFields();

                if  (   (t_lAdditionalFields != null)
                     && (t_lAdditionalFields.size() > 0))
                {
                    t_sbResult.append(" ORDER BY ");

                    t_sbResult.append(
                        t_QueryUtils.concatenate(t_lAdditionalFields, ", "));
                }
            }
        }

        return t_sbResult.toString();
    }
}
