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
 * Description: Represents fields.
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
import org.acmsl.queryj.Table;

/**
 * Represents fields.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class Field
{
    /**
     * The field name.
     */
    private String m__strName;

    /**
     * The table this field belongs to.
     */
    private Table m__Table;

    /**
     * Creates a field using given information.
     * @param name the field name.
     * @param table the table.
     */
    public Field(String name, Table table)
    {
        unmodifiableSetName(name);
        unmodifiableSetTable(table);
    }

    /**
     * Specifies the field name.
     * @param name the name.
     */
    private void unmodifiableSetName(String name)
    {
        m__strName = name;
    }

    /**
     * Specifies the field name.
     * @param name the name.
     */
    protected void setName(String name)
    {
        unmodifiableSetName(name);
    }

    /**
     * Retrieves the field name.
     * @return such reference.
     */
    public String getName()
    {
        return m__strName;
    }

    /**
     * Specifies the field table.
     * @param table the table.
     */
    private void unmodifiableSetTable(Table table)
    {
        m__Table = table;
    }

    /**
     * Specifies the field table.
     * @param table the table.
     */
    protected void setTable(Table table)
    {
        unmodifiableSetTable(table);
    }

    /**
     * Retrieves the field table.
     * @return such reference.
     */
    public Table getTable()
    {
        return m__Table;
    }

    /**
     * Retrieves the condition to be able to filter for equality.
     * @param field the field to filter with.
     * @return such kind of condition.
     */
    public Condition equals(Field field)
    {
        Condition result = null;

        ConditionFactory t_ConditionFactory =
            ConditionFactory.getInstance();

        ConditionOperatorRepository t_ConditionOperatorRepository =
            ConditionOperatorRepository.getInstance();

        if  (   (t_ConditionFactory            != null) 
             && (t_ConditionOperatorRepository != null))
        {
            result =
                t_ConditionFactory.createCondition(
                    this, t_ConditionOperatorRepository.getEquals(), field);
        }

        return result;
    }

    /**
     * Retrieves the variable condition to be able to filter for equality.
     * @return such kind of condition.
     */
    public VariableCondition equals()
    {
        VariableCondition result = null;

        ConditionFactory t_ConditionFactory =
            ConditionFactory.getInstance();

        ConditionOperatorRepository t_ConditionOperatorRepository =
            ConditionOperatorRepository.getInstance();

        if  (   (t_ConditionFactory            != null) 
             && (t_ConditionOperatorRepository != null))
        {
            result =
                t_ConditionFactory.createVariableCondition(
                    this, t_ConditionOperatorRepository.getEquals());
        }

        return result;
    }

    /**
     * Retrieves the variable condition to be able to filter for non-equality.
     * @return such kind of condition.
     */
    public VariableCondition notEquals()
    {
        VariableCondition result = null;

        ConditionFactory t_ConditionFactory =
            ConditionFactory.getInstance();

        ConditionOperatorRepository t_ConditionOperatorRepository =
            ConditionOperatorRepository.getInstance();

        if  (   (t_ConditionFactory            != null) 
             && (t_ConditionOperatorRepository != null))
        {
            result =
                t_ConditionFactory.createVariableCondition(
                    this, t_ConditionOperatorRepository.getNotEquals());
        }

        return result;
    }

    /**
     * Retrieves the variable condition to be able to filter for lower values.
     * @return such kind of condition.
     */
    public VariableCondition greaterThan()
    {
        VariableCondition result = null;

        ConditionFactory t_ConditionFactory =
            ConditionFactory.getInstance();

        ConditionOperatorRepository t_ConditionOperatorRepository =
            ConditionOperatorRepository.getInstance();

        if  (   (t_ConditionFactory            != null) 
             && (t_ConditionOperatorRepository != null))
        {
            result =
                t_ConditionFactory.createVariableCondition(
                    this, t_ConditionOperatorRepository.getGreaterThan());
        }

        return result;
    }

    /**
     * Retrieves the variable condition to be able to filter for greater
     * values.
     * @return such kind of condition.
     */
    public VariableCondition lessThan()
    {
        VariableCondition result = null;

        ConditionFactory t_ConditionFactory =
            ConditionFactory.getInstance();

        ConditionOperatorRepository t_ConditionOperatorRepository =
            ConditionOperatorRepository.getInstance();

        if  (   (t_ConditionFactory            != null) 
             && (t_ConditionOperatorRepository != null))
        {
            result =
                t_ConditionFactory.createVariableCondition(
                    this, t_ConditionOperatorRepository.getLessThan());
        }

        return result;
    }

    /**
     * Retrieves the variable condition to be able to filter for values using
     * belongs-to relationships.
     * @return such kind of condition.
     */
    public VariableCondition in(SelectQuery query)
    {
        VariableCondition result = null;

        ConditionFactory t_ConditionFactory =
            ConditionFactory.getInstance();

        ConditionOperatorRepository t_ConditionOperatorRepository =
            ConditionOperatorRepository.getInstance();

        if  (   (query                         != null) 
             && (t_ConditionFactory            != null) 
             && (t_ConditionOperatorRepository != null))
        {
            result =
                t_ConditionFactory.createVariableCondition(
                    this, t_ConditionOperatorRepository.getBelongsTo(query));
        }

        return result;
    }

    /**
     * Retrieves the variable condition to be able to filter for values using
     * not-belongs-to relationships.
     * @return such kind of condition.
     */
    public VariableCondition notIn(SelectQuery query)
    {
        VariableCondition result = null;

        ConditionFactory t_ConditionFactory =
            ConditionFactory.getInstance();

        ConditionOperatorRepository t_ConditionOperatorRepository =
            ConditionOperatorRepository.getInstance();

        if  (   (query                         != null) 
             && (t_ConditionFactory            != null) 
             && (t_ConditionOperatorRepository != null))
        {
            result =
                t_ConditionFactory.createVariableCondition(
                    this,
                    t_ConditionOperatorRepository.getNotBelongsTo(query));
        }

        return result;
    }

    /**
     * Retrieves the variable condition to be able to filter for null values.
     * @return such kind of condition.
     */
    public Condition isNull()
    {
        Condition result = null;

        ConditionFactory t_ConditionFactory =
            ConditionFactory.getInstance();

        ConditionOperatorRepository t_ConditionOperatorRepository =
            ConditionOperatorRepository.getInstance();

        if  (   (t_ConditionFactory            != null) 
             && (t_ConditionOperatorRepository != null))
        {
            result =
                t_ConditionFactory.createCondition(
                    this,
                    t_ConditionOperatorRepository.getIsNull(),
                    (Field) null);
        }

        return result;
    }

    // Serialization methods //

    /**
     * Outputs a text version of the field.
     * @return the field.
     */
    public String toString()
    {
        StringBuffer result = new StringBuffer();

        Table t_Table = getTable();

        if  (t_Table != null) 
        {
            result.append(t_Table);
            result.append(".");
        }

        result.append(getName());

        return result.toString();
    }

    /**
     * Outputs a simplified text version of the field.
     * @return the field.
     */
    public String toSimplifiedString()
    {
        return getName();
    }
}
