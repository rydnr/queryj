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
 * Description: Represents conditions.
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
import org.acmsl.queryj.Field;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents conditions.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class Condition
{
    /**
     * The inner condition.
     */
    private Condition m__InnerCondition;

    /**
     * The possible list of variable conditions.
     */
    private Collection m__cVariableConditions;

    /**
     * Creates a condition.
     */
    public Condition() {};

    /**
     * Specifies a new condition collection.
     * @param conditions the new conditions.
     */
    protected void setInnerCondition(Condition condition)
    {
        m__InnerCondition = condition;
    }

    /**
     * Retrieves the inner condition.
     * @return such instance.
     */
    protected Condition getInnerCondition()
    {
        return m__InnerCondition;
    }

    /**
     * Specifies the variable conditions.
     * @param variableConditions the variable conditions.
     */
    protected void setVariableConditionCollection(Collection collection)
    {
        m__cVariableConditions = collection;
    }

    /**
     * Retrieves the variable condition colleciton.
     * @return such collection.
     */
    protected Collection getVariableConditionCollection()
    {
        return m__cVariableConditions;
    }

    /**
     * Adds new variable conditions.
     * @param conditions the new conditions.
     */
    protected void addVariableConditions(Collection conditions)
    {
        if  (   (conditions != null)
             && (conditions.size() > 0))
        {
            Collection t_cVariableConditions = getVariableConditionCollection();

            if  (t_cVariableConditions == null)
            {
                t_cVariableConditions = new ArrayList();
                setVariableConditionCollection(t_cVariableConditions);
            }

            t_cVariableConditions.addAll(conditions);
        }
    }

    /**
     * Retrieves the variable conditions.
     * @return such collection.
     */
    public Collection getVariableConditions()
    {
        Collection result = new ArrayList();

        Condition t_InnerCondition = getInnerCondition();

        if  (t_InnerCondition != null)
        {
            Collection t_cInnerVariableConditions = t_InnerCondition.getVariableConditions();

            if  (   (t_cInnerVariableConditions != null)
                 && (t_cInnerVariableConditions.size() > 0))
            {
                result.addAll(t_cInnerVariableConditions);
            }
        }

        if  (m__cVariableConditions != null)
        {
            result.addAll(m__cVariableConditions);
        }

        return result;
    }

    /**
     * Requests an operation with given condition.
     * @param condition the condition to evaluate.
     * @param operator the operator.
     * @return the resulting condition.
     */
    public Condition operate(Condition condition, String operator)
    {
        ConditionFactory t_ConditionFactory = ConditionFactory.getInstance();

        if  (   (condition != null)
             && (t_ConditionFactory != null))
        {
            Condition t_InnerCondition = getInnerCondition();

            if  (t_InnerCondition != null)
            {
                addVariableConditions(t_InnerCondition.getVariableConditions());
            }
            else 
            {
                t_InnerCondition = this; // Subclasses override toString()
            }                            //            |
                                         //            v
            String t_strPrefix = "(" + t_InnerCondition.toString() + ") " + operator + " (";
            String t_strSuffix = ")";

            setInnerCondition(
                t_ConditionFactory.wrap(condition, t_strPrefix, t_strSuffix));
        }

        return this;
    }

    /**
     * Requests AND evaluation with given condition.
     * @param condition the condition to evaluate.
     * @return the resulting condition.
     */
    public Condition and(Condition condition)
    {
        return operate(condition, "AND");
    }

    /**
     * Requests OR evaluation with given condition.
     * @param condition the condition to evaluate.
     * @return the resulting condition.
     */
    public Condition or(Condition condition)
    {
        return operate(condition, "OR");
    }

    /**
     * Retrieves the complete or simplified text associated with
     * given field.
     * @param field the field to serialize.
     * @param simplify if fields should appear
     * without explicit table information.
     * @return such text.
     */
    protected String toString(Field field, boolean simplify)
    {
        String result = "null";

        if  (field != null)
        {
            if  (simplify)
            {
                result = field.toSimplifiedString();
            }
            else 
            {
                result = field.toString();
            }
        }
        
        return result;
    }

    /**
     * Outputs a brief text version of the condition.
     * @return the condition.
     */
    public String toSimplifiedString()
    {
        String result = "";

        Condition t_InnerCondition = getInnerCondition();

        if  (t_InnerCondition != null)
        {
            result = t_InnerCondition.toSimplifiedString();
        }

        return result;
    }

    /**
     * Outputs a text version of the condition.
     * @return the condition.
     */
    public String toString()
    {
        return "" + getInnerCondition();
    }
}
