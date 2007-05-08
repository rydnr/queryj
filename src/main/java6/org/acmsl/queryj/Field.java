/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: Field.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents fields.
 *
 */
package org.acmsl.queryj;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.Table;

/**
 * Represents fields.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
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
     * @precondition name != null
     * @precondition table != null
     */
    public Field(final String name, final Table table)
    {
        immutableSetName(name);
        immutableSetTable(table);
    }

    /**
     * Specifies the field name.
     * @param name the name.
     */
    private void immutableSetName(final String name)
    {
        m__strName = name;
    }

    /**
     * Specifies the field name.
     * @param name the name.
     */
    protected void setName(final String name)
    {
        immutableSetName(name);
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
    private void immutableSetTable(final Table table)
    {
        m__Table = table;
    }

    /**
     * Specifies the field table.
     * @param table the table.
     */
    protected void setTable(final Table table)
    {
        immutableSetTable(table);
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
    public Condition equals(final Field field)
    {
        return
            equals(
                field,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }

    /**
     * Retrieves the condition to be able to filter for equality.
     * @param field the field.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected Condition equals(
        final Field field,
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createCondition(
                this, conditionOperatorRepository.getEquals(), field);
    }

    /**
     * Retrieves the variable condition to be able to filter for equality.
     * @return such kind of condition.
     */
    public VariableCondition equals()
    {
        return
            equals(
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }

    /**
     * Retrieves the variable condition to be able to filter for non-equality.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected VariableCondition equals(
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createVariableCondition(
                this, conditionOperatorRepository.getEquals());
    }

    /**
     * Retrieves the condition to be able to filter for equality.
     * @param function the field to filter with.
     * @return such kind of condition.
     */
    public VariableCondition equals(final Function function)
    {
        return
            equals(
                function,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }

    /**
     * Retrieves the variable condition to be able to filter for non-equality.
     * @param function the function.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition function != null
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected VariableCondition equals(
        final Function function,
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createVariableCondition(
                this, conditionOperatorRepository.getEquals(), function);
    }

    /**
     * Retrieves the variable condition to be able to filter for non-equality.
     * @return such kind of condition.
     */
    public VariableCondition notEquals()
    {
        return
            notEquals(
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }

    /**
     * Retrieves the variable condition to be able to filter for non-equality.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected VariableCondition notEquals(
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createVariableCondition(
                this, conditionOperatorRepository.getNotEquals());
    }

    /**
     * Retrieves the variable condition to be able to filter for non-equality.
     * @param function the function.
     * @return such kind of condition.
     * @precondition function != null
     */
    public VariableCondition notEquals(final Function function)
    {
        return
            notEquals(
                function,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }

    /**
     * Retrieves the variable condition to be able to filter for non-equality.
     * @param function the function.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition function != null
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected VariableCondition notEquals(
        final Function function,
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createVariableCondition(
                this, conditionOperatorRepository.getNotEquals(), function);
    }

    /**
     * Retrieves the variable condition to be able to filter for lower values.
     * @return such kind of condition.
     */
    public VariableCondition greaterThan()
    {
        return
            greaterThan(
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }

    /**
     * Retrieves the condition to be able to filter for lower values.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected VariableCondition greaterThan(
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createVariableCondition(
                this, conditionOperatorRepository.getGreaterThan());
    }

    /**
     * Retrieves the variable condition to be able to filter for lower values.
     * @param function the function.
     * @return such kind of condition.
     * @precondition function != null
     */
    public VariableCondition greaterThan(final Function function)
    {
        return
            greaterThan(
                function,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }

    /**
     * Retrieves the condition to be able to filter for lower values.
     * @param function the function.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition function != null
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected VariableCondition greaterThan(
        final Function function,
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createVariableCondition(
                this, conditionOperatorRepository.getGreaterThan(), function);
    }

    /**
     * Retrieves the variable condition to be able to filter for greater
     * values.
     * @return such kind of condition.
     */
    public VariableCondition lessThan()
    {
        return
            lessThan(
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }

    /**
     * Retrieves the condition to be able to filter for higher values.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected VariableCondition lessThan(
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createVariableCondition(
                this, conditionOperatorRepository.getLessThan());
    }

    /**
     * Retrieves the variable condition to be able to filter for greater
     * values.
     * @param function the function.
     * @return such kind of condition.
     * @precondition function != null
     */
    public VariableCondition lessThan(final Function function)
    {
        return
            lessThan(
                function,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }

    /**
     * Retrieves the condition to be able to filter for higher values.
     * @param function the function.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition function != null
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected VariableCondition lessThan(
        final Function function,
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createVariableCondition(
                this, conditionOperatorRepository.getLessThan(), function);
    }

    /**
     * Retrieves the variable condition to be able to filter for values using
     * belongs-to relationships.
     * @return such kind of condition.
     * @precondition query != null
     */
    public VariableCondition in(final SelectQuery query)
    {
        return
            in(
                query,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }

    /**
     * Retrieves the variable condition to be able to filter for values using
     * belongs-to relationships.
     * @param query the query.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition query != null
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected VariableCondition in(
        final SelectQuery query,
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createVariableCondition(
                this, conditionOperatorRepository.getBelongsTo(query));
    }

    /**
     * Retrieves the variable condition to be able to filter for values using
     * not-belongs-to relationships.
     * @return such kind of condition.
     */
    public VariableCondition notIn(final SelectQuery query)
    {
        return
            notIn(
                query,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }

    /**
     * Retrieves the variable condition to be able to filter for values using
     * not-belongs-to relationships.
     * @param query the query.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition query != null
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected VariableCondition notIn(
        final SelectQuery query,
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createVariableCondition(
                this,
                conditionOperatorRepository.getNotBelongsTo(query));
    }

    /**
     * Retrieves the variable condition to be able to filter for null values.
     * @return such kind of condition.
     */
    public Condition isNull()
    {
        return
            isNull(
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }

    /**
     * Retrieves the variable condition to be able to filter for null values.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected Condition isNull(
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createCondition(
                this,
                conditionOperatorRepository.getIsNull(),
                (Field) null);
    }

    // Serialization methods //

    /**
     * Outputs a text version of the field.
     * @return the field.
     */
    public String toString()
    {
        return toString(this);
    }
    
    /**
     * Outputs a text version of the field.
     * @param field the field.
     * @return the field.
     * @precondition field != null
     */
    public static String toString(final Field field)
    {
        String result = "";

        if  (field != null)
        {
            result = toString(field.getTable(), field.getName());
        }
        
        return result;
    }

    /**
     * Outputs a text version of the field.
     * @param table the table.
     * @param name the name.
     * @return the field.
     */
    protected static String toString(final Table table, final String name)
    {
        StringBuffer result = new StringBuffer();

        if  (table != null) 
        {
            result.append(Table.toString(table));
            result.append(".");
        }

        result.append(name);

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
