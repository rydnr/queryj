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
 * Description: Has the responsiblity of knowing how to create conditions.
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
 * Importing ACM-SL classes.
 */
import org.acmsl.queryj.Query;
import org.acmsl.queryj.SelectQuery;

/*
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Has the responsiblity of knowing how to create conditions.
 * @author <a href="mailto:jsanleandro@yahoo.es">Jose San Leandro</a>
 * @version $Revision$
 */
public class ConditionFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected ConditionFactory() {};

    /**
     * Specifies a new weak reference.
     * @param factory the factory instance to use.
     */
    protected static void setReference(ConditionFactory factory)
    {
        singleton = new WeakReference(factory);
    }

    /**
     * Retrieves the weak reference.
     * @return such reference.
     */
    protected static WeakReference getReference()
    {
        return singleton;
    }

    /**
     * Retrieves a ConditionFactory instance.
     * @return such instance.
     */
    public static ConditionFactory getInstance()
    {
        ConditionFactory result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (ConditionFactory) reference.get();
        }

        if  (result == null) 
        {
            result = new ConditionFactory() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Creates a condition
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param rightSideField the right-side field.
     * @return such type of instance.
     */
    public Condition createCondition(
        Field              leftSideField,
        ConditionOperator  operator,
        Field              rightSideField)
    {
        Condition result = null;

        if  (   (leftSideField  != null)
             && (operator       != null))
            // && (rightSideField != null)) // Removed due to isNull()
        {
            result =
                new AtomicCondition(
                    leftSideField, operator, rightSideField) {};
        }
        
        return result;
    }

    /**
     * Creates a condition
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param value the fixed value.
     * @return such type of instance.
     */
    public Condition createCondition(
        Field              leftSideField,
        ConditionOperator  operator,
        int                value)
    {
        Condition result = null;

        if  (   (leftSideField  != null)
             && (operator       != null))
        {
            result = new AtomicCondition(leftSideField, operator, value) {};
        }
        
        return result;
    }

    /**
     * Creates a condition
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param value the fixed value.
     * @return such type of instance.
     */
    public Condition createCondition(
        Field              leftSideField,
        ConditionOperator  operator,
        long                value)
    {
        Condition result = null;

        if  (   (leftSideField  != null)
             && (operator       != null))
        {
            result = new AtomicCondition(leftSideField, operator, value) {};
        }
        
        return result;
    }

    /**
     * Creates a condition
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param value the fixed value.
     * @return such type of instance.
     */
    public Condition createCondition(
        Field              leftSideField,
        ConditionOperator  operator,
        double             value)
    {
        Condition result = null;

        if  (   (leftSideField  != null)
             && (operator       != null))
        {
            result = new AtomicCondition(leftSideField, operator, value) {};
        }
        
        return result;
    }

    /**
     * Creates a condition
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param value the fixed value.
     * @return such type of instance.
     */
    public Condition createCondition(
        Field              leftSideField,
        ConditionOperator  operator,
        BigDecimal         value)
    {
        Condition result = null;

        if  (   (leftSideField  != null)
             && (operator       != null))
        {
            result = new AtomicCondition(leftSideField, operator, value) {};
        }
        
        return result;
    }

    /**
     * Creates a condition
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param value the fixed value.
     * @return such type of instance.
     */
    public Condition createCondition(
        Field              leftSideField,
        ConditionOperator  operator,
        String             value)
    {
        Condition result = null;

        if  (   (leftSideField  != null)
             && (operator       != null))
        {
            result = new AtomicCondition(leftSideField, operator, value) {};
        }
        
        return result;
    }

    /**
     * Creates a condition
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param value the fixed value.
     * @return such type of instance.
     */
    public Condition createCondition(
        Field              leftSideField,
        ConditionOperator  operator,
        Calendar           value)
    {
        Condition result = null;

        if  (   (leftSideField  != null)
             && (operator       != null))
        {
            result = new AtomicCondition(leftSideField, operator, value) {};
        }
        
        return result;
    }

    /**
     * Creates a condition
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param value the fixed value.
     * @return such type of instance.
     */
    public Condition createCondition(
        Field              leftSideField,
        ConditionOperator  operator,
        Date               value)
    {
        Condition result = null;

        if  (   (leftSideField  != null)
             && (operator       != null))
        {
            result = new AtomicCondition(leftSideField, operator, value) {};
        }
        
        return result;
    }

    /**
     * Creates a variable condition.
     * @param field the field.
     * @param operator the operator.
     * @return such type of instance.
     */
    public VariableCondition createVariableCondition(
        Field              field,
        ConditionOperator  operator)
    {
        VariableCondition result = null;

        if  (   (field    != null)
             && (operator != null))
        {
            result = new VariableCondition(field, operator) {};
        }
        
        return result;
    }

    /**
     * Creates a wrapper condition.
     * @param condition the condition to wrap.
     * @param prefix the prefix.
     * @param suffix the suffix.
     * @return the wrapped condition.
     */
    public Condition wrap(Condition condition, String prefix, String suffix)
    {
        Condition result = condition;

        if  (result != null) 
        {
            result = new _ConditionWrapper(condition, prefix, suffix) {};
        }
        
        return result;
    }

    /**
     * Creates a wrapper condition.
     * @param condition the condition to wrap.
     * @param prefix the prefix.
     * @param suffix the suffix.
     * @return the wrapped condition.
     */
    public Condition wrap(
        AtomicCondition condition, String prefix, String suffix)
    {
        Condition result = condition;

        if  (result != null) 
        {
            result = new _AtomicConditionWrapper(condition, prefix, suffix) {};
        }
        
        return result;
    }

    /**
     * Creates a wrapper condition.
     * @param condition the condition to wrap.
     * @param prefix the prefix.
     * @param suffix the suffix.
     * @return the wrapped condition.
     */
    public VariableCondition wrap(
        VariableCondition condition, String prefix, String suffix)
    {
        VariableCondition result = condition;

        if  (result != null) 
        {
            result =
                new _VariableConditionWrapper(condition, prefix, suffix) {};
        }
        
        return result;
    }

    /**
     * Envelopes a condition surrounding it with appropiate prefix and suffix.
     * @author <a href="mailto:jsanleandro@yahoo.es">Jose San Leandro</a>
     * @version $Revision$
     */
    protected static class _ConditionWrapper
        extends  Condition
    {
        /**
         * The condition to wrap.
         */
        private Condition m__Condition;

        /**
         * The prefix.
         */
        private String m__strPrefix;

        /**
         * The suffix.
         */
        private String m__strSuffix;

        /**
         * Creates a condition wrapper with given information.
         * @param condition the condition to wrap.
         * @param prefix the prefix.
         * @param suffix the suffix.
         */
        public _ConditionWrapper(
            Condition condition, String prefix, String suffix)
        {
            super();

            inmutableSetCondition(condition);
            inmutableSetPrefix(prefix);
            inmutableSetSuffix(suffix);
        }
        /**
         * Specifies the condition to wrap.
         * @param condition such condition.
         */
        private void inmutableSetCondition(Condition condition)
        {
            m__Condition = condition;
        }

        /**
         * Specifies the condition to wrap.
         * @param condition such condition.
         */
        protected void setCondition(Condition condition)
        {
            inmutableSetCondition(condition);
        }

        /**
         * Retrieves the wrapped condition.
         * @return such condition.
         */
        public Condition getCondition()
        {
            return m__Condition;
        }

        /**
         * Specifies the prefix.
         * @param prefix such prefix.
         */
        private void inmutableSetPrefix(String prefix)
        {
            m__strPrefix = prefix;
        }

        /**
         * Specifies the prefix.
         * @param prefix such prefix.
         */
        protected void setPrefix(String prefix)
        {
            inmutableSetPrefix(prefix);
        }

        /**
         * Retrieves the prefix.
         * @return such prefix.
         */
        public String getPrefix()
        {
            return m__strPrefix;
        }

        /**
         * Specifies the suffix.
         * @param suffix such suffix.
         */
        private void inmutableSetSuffix(String suffix)
        {
            m__strSuffix = suffix;
        }

        /**
         * Specifies the suffix.
         * @param suffix such suffix.
         */
        protected void setSuffix(String suffix)
        {
            inmutableSetSuffix(suffix);
        }

        /**
         * Retrieves the suffix.
         * @return such suffix.
         */
        public String getSuffix()
        {
            return m__strSuffix;
        }

        // Delegate methods //


        /**
         * Retrieves the variable conditions.
         * @return such collection.
         */
        public Collection getVariableConditions()
        {
            Collection result = super.getVariableConditions();

            Condition t_Condition = getCondition();

            if  (t_Condition != null) 
            {
                Collection t_cVariableConditions =
                    t_Condition.getVariableConditions();

                if  (   (t_cVariableConditions != null)
                     && (t_cVariableConditions.size() > 0))
                {
                    if  (result == null)
                    {
                        result = new ArrayList();
                    }

                    result.addAll(t_cVariableConditions);
                }
            }

            return result;
        }

        /**
         * Outputs a brief text version of the condition.
         * @return such text.
         */
        public String toSimplifiedString()
        {
            String result = "";

            Condition t_Condition = getCondition();

            if  (t_Condition != null)
            {
                result =
                      getPrefix()
                    + getCondition().toSimplifiedString()
                    + getSuffix();
            }

            return result;
        }

        /**
         * Outputs a text version of the condition.
         * @return such text.
         */
        public String toString()
        {
            return getPrefix() + getCondition() + getSuffix();
        }
    }

    /**
     * Envelopes a condition surrounding it with appropiate prefix and suffix.
     * @author <a href="mailto:jsanleandro@yahoo.es">Jose San Leandro</a>
     * @version $Revision$
     */
    protected static class _AtomicConditionWrapper
        extends  AtomicCondition
    {
        /**
         * The condition to wrap.
         */
        private AtomicCondition m__Condition;

        /**
         * The prefix.
         */
        private String m__strPrefix;

        /**
         * The suffix.
         */
        private String m__strSuffix;

        /**
         * Creates a condition wrapper with given information.
         * @param condition the condition to wrap.
         * @param prefix the prefix.
         * @param suffix the suffix.
         */
        public _AtomicConditionWrapper(
            AtomicCondition condition, String prefix, String suffix)
        {
            super(
                condition.getLeftSideField(),
                condition.getOperator(),
                condition.getRightSideField());

            inmutableSetCondition(condition);
            inmutableSetPrefix(prefix);
            inmutableSetSuffix(suffix);
        }

        /**
         * Specifies the condition to wrap.
         * @param condition such condition.
         */
        private void inmutableSetCondition(AtomicCondition condition)
        {
            m__Condition = condition;
        }

        /**
         * Specifies the condition to wrap.
         * @param condition such condition.
         */
        protected void setCondition(AtomicCondition condition)
        {
            inmutableSetCondition(condition);
        }

        /**
         * Retrieves the wrapped condition.
         * @return such condition.
         */
        public AtomicCondition getCondition()
        {
            return m__Condition;
        }

        /**
         * Specifies the prefix.
         * @param prefix such prefix.
         */
        private void inmutableSetPrefix(String prefix)
        {
            m__strPrefix = prefix;
        }

        /**
         * Specifies the prefix.
         * @param prefix such prefix.
         */
        protected void setPrefix(String prefix)
        {
            inmutableSetPrefix(prefix);
        }

        /**
         * Retrieves the prefix.
         * @return such prefix.
         */
        public String getPrefix()
        {
            return m__strPrefix;
        }

        /**
         * Specifies the suffix.
         * @param suffix such suffix.
         */
        private void inmutableSetSuffix(String suffix)
        {
            m__strSuffix = suffix;
        }

        /**
         * Specifies the suffix.
         * @param suffix such suffix.
         */
        protected void setSuffix(String suffix)
        {
            inmutableSetSuffix(suffix);
        }

        /**
         * Retrieves the suffix.
         * @return such suffix.
         */
        public String getSuffix()
        {
            return m__strSuffix;
        }

        // Delegated methods //

        /**
         * Retrieves the left-side field.
         * @return such reference.
         */
        public Field getLeftSideField()
        {
            Field result = null;

            AtomicCondition t_Condition = getCondition();

            if  (t_Condition != null) 
            {
                result = t_Condition.getLeftSideField();
            }
            
            return result;
        }

        /**
         * Retrieves the condition operator.
         * @return such reference.
         */
        public ConditionOperator getOperator()
        {
            ConditionOperator result = null;

            AtomicCondition t_Condition = getCondition();

            if  (t_Condition != null) 
            {
                result = t_Condition.getOperator();
            }
            
            return result;
        }

        /**
         * Retrieves the right-side field.
         * @return such reference.
         */
        public Field getRightSideField()
        {
            Field result = null;

            AtomicCondition t_Condition = getCondition();

            if  (t_Condition != null) 
            {
                result = t_Condition.getRightSideField();
            }
            
            return result;
        }

        /**
         * Retrieves the right-side value.
         * @return such reference.
         */
        public String getRightSideValue()
        {
            String result = null;

            AtomicCondition t_Condition = getCondition();

            if  (t_Condition != null) 
            {
                result = t_Condition.getRightSideValue();
            }
            
            return result;
        }

        /**
         * Retrieves the variable conditions.
         * @return such collection.
         */
        public Collection getVariableConditions()
        {
            Collection result = super.getVariableConditions();

            Condition t_Condition = getCondition();

            if  (t_Condition != null) 
            {
                Collection t_cVariableConditions =
                    t_Condition.getVariableConditions();

                if  (   (t_cVariableConditions != null)
                     && (t_cVariableConditions.size() > 0))
                {
                    if  (result == null)
                    {
                        result = new ArrayList();
                    }

                    result.addAll(t_cVariableConditions);
                }
            }

            return result;
        }

        /**
         * Outputs a brief text version of the condition.
         * @return such text.
         */
        public String toSimplifiedString()
        {
            String result = "";

            Condition t_Condition = getCondition();

            if  (t_Condition != null)
            {
                result =
                      getPrefix()
                    + getCondition().toSimplifiedString()
                    + getSuffix();
            }

            return result;
        }

        /**
         * Outputs a text version of the condition.
         * @return such text.
         */
        public String toString()
        {
            return getPrefix() + getCondition() + getSuffix();
        }
    }


    /**
     * Envelopes a variable condition surrounding it with appropiate
     * prefix and suffix.
     * @author <a href="mailto:jsanleandro@yahoo.es">Jose San Leandro</a>
     * @version $Revision$
     */
    protected static class _VariableConditionWrapper
        extends  VariableCondition
    {
        /**
         * The condition to wrap.
         */
        private VariableCondition m__Condition;

        /**
         * The prefix.
         */
        private String m__strPrefix;

        /**
         * The suffix.
         */
        private String m__strSuffix;

        /**
         * Creates a condition wrapper with given information.
         * @param condition the condition to wrap.
         * @param prefix the prefix.
         * @param suffix the suffix.
         */
        public _VariableConditionWrapper(
            VariableCondition condition, String prefix, String suffix)
        {
            super(
                condition.getLeftSideField(),
                condition.getOperator());

            inmutableSetCondition(condition);
            inmutableSetPrefix(prefix);
            inmutableSetSuffix(suffix);
        }

        /**
         * Specifies the condition to wrap.
         * @param condition such condition.
         */
        private void inmutableSetCondition(VariableCondition condition)
        {
            m__Condition = condition;
        }

        /**
         * Specifies the condition to wrap.
         * @param condition such condition.
         */
        protected void setCondition(VariableCondition condition)
        {
            inmutableSetCondition(condition);
        }

        /**
         * Retrieves the wrapped condition.
         * @return such condition.
         */
        public VariableCondition getCondition()
        {
            return m__Condition;
        }

        /**
         * Specifies the prefix.
         * @param prefix such prefix.
         */
        private void inmutableSetPrefix(String prefix)
        {
            m__strPrefix = prefix;
        }

        /**
         * Specifies the prefix.
         * @param prefix such prefix.
         */
        protected void setPrefix(String prefix)
        {
            inmutableSetPrefix(prefix);
        }

        /**
         * Retrieves the prefix.
         * @return such prefix.
         */
        public String getPrefix()
        {
            return m__strPrefix;
        }

        /**
         * Specifies the suffix.
         * @param suffix such suffix.
         */
        private void inmutableSetSuffix(String suffix)
        {
            m__strSuffix = suffix;
        }

        /**
         * Specifies the suffix.
         * @param suffix such suffix.
         */
        protected void setSuffix(String suffix)
        {
            inmutableSetSuffix(suffix);
        }

        /**
         * Retrieves the suffix.
         * @return such suffix.
         */
        public String getSuffix()
        {
            return m__strSuffix;
        }


        // Delegated methods //

        /**
         * Retrieves the left-side field.
         * @return such reference.
         */
        public Field getLeftSideField()
        {
            Field result = null;

            VariableCondition t_Condition = getCondition();

            if  (t_Condition != null) 
            {
                result = t_Condition.getLeftSideField();
            }
            
            return result;
        }

        /**
         * Retrieves the condition operator.
         * @return such reference.
         */
        public ConditionOperator getOperator()
        {
            ConditionOperator result = null;

            VariableCondition t_Condition = getCondition();

            if  (t_Condition != null) 
            {
                result = t_Condition.getOperator();
            }
            
            return result;
        }

        /**
         * Retrieves the right-side field.
         * @return such reference.
         */
        public Field getRightSideField()
        {
            Field result = null;

            VariableCondition t_Condition = getCondition();

            if  (t_Condition != null) 
            {
                result = t_Condition.getRightSideField();
            }
            
            return result;
        }

        /**
         * Retrieves the right-side value.
         * @return such reference.
         */
        public String getRightSideValue()
        {
            String result = null;

            VariableCondition t_Condition = getCondition();

            if  (t_Condition != null) 
            {
                result = t_Condition.getRightSideValue();
            }
            
            return result;
        }

        /**
         * Outputs a brief text version of the condition.
         * @return such text.
         */
        public String toSimplifiedString()
        {
            String result = "";

            VariableCondition t_Condition = getCondition();

            if  (t_Condition != null)
            {
                result =
                      getPrefix()
                    + getCondition().toSimplifiedString()
                    + getSuffix();
            }

            return result;
        }

        /**
         * Outputs a text version of the condition.
         * @return such text.
         */
        public String toString()
        {
            return getPrefix() + getCondition() + getSuffix();
        }
    }
}
