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
 * Description: Represents undivisible conditions.
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

/*
 * Importing some JDK classes.
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Represents indivisible conditions in statements.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class AtomicCondition
    extends  Condition
{
    /**
     * The left-side field.
     */
    private Field m__LeftSideField;

    /**
     * The operator.
     */
    private ConditionOperator m__Operator;

    /**
     * The right-side field.
     */
    private Field m__RightSideField;

    /**
     * The right-side value.
     */
    private String m__strRightSideValue;

    /**
     * Creates a condition using given information.
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param rightSideField the right-side field.
     */
    public AtomicCondition(
        Field              leftSideField,
        ConditionOperator  operator,
        Field              rightSideField)
    {
        unmodifiableSetLeftSideField(leftSideField);
        unmodifiableSetOperator(operator);
        unmodifiableSetRightSideField(rightSideField);
    }

    /**
     * Creates a condition using given information.
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param rightSideValue the right-side value.
     */
    protected AtomicCondition(
        Field              leftSideField,
        ConditionOperator  operator,
        String             rightSideValue)
    {
        this(leftSideField, operator, (Field) null);

        unmodifiableSetRightSideValue(rightSideValue);
    }

    /**
     * Creates a condition using given information.
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param rightSideValue the right-side value.
     */
    public AtomicCondition(
        Field              leftSideField,
        ConditionOperator  operator,
        int                rightSideValue)
    {
        this(leftSideField, operator, (Field) null);
        unmodifiableSetRightSideValue("" + rightSideValue);
    }

    /**
     * Creates a condition using given information.
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param rightSideValue the right-side value.
     */
    public AtomicCondition(
        Field              leftSideField,
        ConditionOperator  operator,
        long               rightSideValue)
    {
        this(leftSideField, operator, (Field) null);
        unmodifiableSetRightSideValue("" + rightSideValue);
    }

    /**
     * Creates a condition using given information.
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param rightSideValue the right-side value.
     */
    public AtomicCondition(
        Field              leftSideField,
        ConditionOperator  operator,
        double             rightSideValue)
    {
        this(leftSideField, operator, (Field) null);
        unmodifiableSetRightSideValue("" + rightSideValue);
    }

    /**
     * Creates a condition using given information.
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param rightSideValue the right-side value.
     */
    public AtomicCondition(
        Field              leftSideField,
        ConditionOperator  operator,
        BigDecimal         rightSideValue)
    {
        this(leftSideField, operator, (Field) null);
        unmodifiableSetRightSideValue("" + rightSideValue);
    }

    /**
     * Creates a condition using given information.
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param rightSideValue the right-side value.
     */
    public AtomicCondition(
        Field              leftSideField,
        ConditionOperator  operator,
        Calendar           rightSideValue)
    {
        this(leftSideField, operator, (Field) null);
        // This should use Functions.toDate or something...
        unmodifiableSetRightSideValue("" + rightSideValue);
    }

    /**
     * Creates a condition using given information.
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param rightSideValue the right-side value.
     */
    public AtomicCondition(
        Field              leftSideField,
        ConditionOperator  operator,
        Date               rightSideValue)
    {
        this(leftSideField, operator, (Field) null);
        // This should use Functions.toDate or something...
        unmodifiableSetRightSideValue("" + rightSideValue);
    }

    /**
     * Specifies the left-side field.
     * @param leftSideField such field.
     */
    private void unmodifiableSetLeftSideField(Field leftSideField)
    {
        m__LeftSideField = leftSideField;
    }

    /**
     * Specifies the left side field.
     * @param leftSideField such field.
     */
    protected void setLeftSideField(Field leftSideField)
    {
        unmodifiableSetLeftSideField(leftSideField);
    }

    /**
     * Retrieves the left-side field.
     * @return such reference.
     */
    public Field getLeftSideField()
    {
        return m__LeftSideField;
    }

    /**
     * Specifies the operator.
     * @param operator such operator.
     */
    private void unmodifiableSetOperator(ConditionOperator operator)
    {
        m__Operator = operator;
    }

    /**
     * Specifies the operator.
     * @param operator such operator.
     */
    protected void setOperator(ConditionOperator operator)
    {
        unmodifiableSetOperator(operator);
    }

    /**
     * Retrieves the operator.
     * @return such reference.
     */
    public ConditionOperator getOperator()
    {
        return m__Operator;
    }

    /**
     * Specifies the right-side field.
     * @param rightSideField such field.
     */
    private void unmodifiableSetRightSideField(Field rightSideField)
    {
        m__RightSideField = rightSideField;
    }

    /**
     * Specifies the right side field.
     * @param rightSideField such field.
     */
    protected void setRightSideField(Field rightSideField)
    {
        unmodifiableSetRightSideField(rightSideField);
    }

    /**
     * Retrieves the right-side field.
     * @return such reference.
     */
    public Field getRightSideField()
    {
        return m__RightSideField;
    }

    /**
     * Specifies the right-side value.
     * @param rightSideValue such value.
     */
    private void unmodifiableSetRightSideValue(String rightSideValue)
    {
        m__strRightSideValue = rightSideValue;
    }

    /**
     * Specifies the right side value.
     * @param rightSideValue such value.
     */
    protected void setRightSideValue(String rightSideValue)
    {
        unmodifiableSetRightSideValue(rightSideValue);
    }

    /**
     * Retrieves the right-side value.
     * @return such reference.
     */
    public String getRightSideValue()
    {
        return m__strRightSideValue;
    }

    /**
     * Outputs a text version of the condition.
     * @return the condition.
     */
    public String toString()
    {
        return toString(false);
    }

    /**
     * Outputs a brief version of the condition.
     * @return such text.
     */
    public String toSimplifiedString()
    {
        return toString(true);
    }

    /**
     * Outputs a text version of the condition.
     * @param simplify if fields should appear
     * without explicit table information.
     * @return the condition.
     */
    protected String toString(boolean simplify)
    {
        StringBuffer t_sbResult = new StringBuffer();

        if  (getInnerCondition() != null)
        {
            t_sbResult.append(super.toString());
        }
        else 
        {
            t_sbResult.append(toString(getLeftSideField(), simplify));
            t_sbResult.append(" ");
            t_sbResult.append(getOperator());

            Field t_RightSideField = getRightSideField();

            String t_strRightSide = "";

            if  (t_RightSideField != null) 
            {
                t_strRightSide = toString(t_RightSideField, simplify);
            }
            else
            {
                t_strRightSide = getRightSideValue();
            }

            if  (t_strRightSide == null) 
            {
                t_strRightSide = "";
            }
            else
            {
                t_sbResult.append(" ");
            }

            t_sbResult.append(t_strRightSide);
        }

        return t_sbResult.toString();
    }

    /**
     * Performs the equality check.
     * @param object the object to check.
     * @return <code>true</code> if both objects are semantically equal.
     */
    public boolean equals(Object object)
    {
        boolean result = false;

        if  (   (object != null)
             && (object instanceof VariableCondition))
        {
            VariableCondition t_Object = (VariableCondition) object;

            if  (t_Object != null)
            {
                Field t_LeftSideField = t_Object.getLeftSideField();

                if  (t_LeftSideField == null)
                {
                    result = (getLeftSideField() == null);
                }
                else
                {
                    result = t_LeftSideField.equals((Object) getLeftSideField());
                }

                if  (result) 
                {
                    ConditionOperator t_Operator = t_Object.getOperator();

                    if  (t_Operator == null)
                    {
                        result = (getOperator() == null);
                    }
                    else
                    {
                        result = t_Operator.equals(getOperator());
                    }

                    if  (result) 
                    {
                        Field t_RightSideField = t_Object.getRightSideField();

                        if  (t_RightSideField == null)
                        {
                            result = (getRightSideField() == null);
                        }
                        else
                        {
                            result = t_RightSideField.equals((Object) getRightSideField());
                        }
                    }
                }
            }
        }
        
        return result;
    }

    /**
     * Retrieves the hash code of the object.
     * @return such code.
     */
    public int hashCode()
    {
        int result = toString().hashCode();

        Field t_LeftSideField = getLeftSideField();

        if  (t_LeftSideField != null)
        {
            result += t_LeftSideField.hashCode();
        }

        ConditionOperator t_Operator = getOperator();

        if  (t_Operator != null)
        {
            result += t_Operator.hashCode();
        }

        Field t_RightSideField = getRightSideField();

        if  (t_RightSideField != null)
        {
            result += t_RightSideField.hashCode();
        }

        return result;
    }
}
