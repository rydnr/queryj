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
 * Filename: ConditionFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Has the responsiblity of knowing how to create conditions.
 *
 */
package org.acmsl.queryj;

/*
 * Importing ACM-SL classes.
 */
import org.acmsl.queryj.Query;
import org.acmsl.queryj.SelectQuery;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Factory;
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JDK classes.
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Has the responsiblity of knowing how to create conditions.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 */
public class ConditionFactory
    implements  Factory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class ConditionFactorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final ConditionFactory SINGLETON =
            new ConditionFactory();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected ConditionFactory() {};

    /**
     * Retrieves a <code>ConditionFactory</code> instance.
     * @return such instance.
     */
    public static ConditionFactory getInstance()
    {
        return ConditionFactorySingletonContainer.SINGLETON;
    }

    /**
     * Creates a condition.
     * Note: Removed precondition rightSideField != null due
     * to <code>isNull</code>.
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param rightSideField the right-side field.
     * @return such type of instance.
     * @precondition leftSideField != null
     * @precondition operator != null
     */
    public Condition createCondition(
        final Field leftSideField,
        final ConditionOperator operator,
        final Field rightSideField)
    {
        return
            new AtomicCondition(
                leftSideField, operator, rightSideField);
    }

    /**
     * Creates a condition
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param value the fixed value.
     * @return such type of instance.
     * @precondition leftSideField != null
     * @precondition operator != null
     */
    public Condition createCondition(
        final Field leftSideField,
        final ConditionOperator operator,
        final int value)
    {
        return new AtomicCondition(leftSideField, operator, value);
    }

    /**
     * Creates a condition
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param value the fixed value.
     * @return such type of instance.
     * @precondition leftSideField != null
     * @precondition operator != null
     */
    public Condition createCondition(
        final Field leftSideField,
        final ConditionOperator operator,
        final long value)
    {
        return new AtomicCondition(leftSideField, operator, value);
    }

    /**
     * Creates a condition
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param value the fixed value.
     * @return such type of instance.
     * @precondition leftSideField != null
     * @precondition operator != null
     */
    public Condition createCondition(
        final Field leftSideField,
        final ConditionOperator operator,
        final boolean value)
    {
        return new AtomicCondition(leftSideField, operator, value);
    }

    /**
     * Creates a condition
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param value the fixed value.
     * @return such type of instance.
     * @precondition leftSideField != null
     * @precondition operator != null
     */
    public Condition createCondition(
        final Field leftSideField,
        final ConditionOperator operator,
        final double value)
    {
        return new AtomicCondition(leftSideField, operator, value);
    }

    /**
     * Creates a condition
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param value the fixed value.
     * @return such type of instance.
     * @precondition leftSideField != null
     * @precondition operator != null
     */
    public Condition createCondition(
        final Field leftSideField,
        final ConditionOperator operator,
        final BigDecimal value)
    {
        return new AtomicCondition(leftSideField, operator, value);
    }

    /**
     * Creates a condition
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param value the fixed value.
     * @return such type of instance.
     * @precondition leftSideField != null
     * @precondition operator != null
     */
    public Condition createCondition(
        final Field leftSideField,
        final ConditionOperator operator,
        final String value)
    {
        return new AtomicCondition(leftSideField, operator, value);
    }

    /**
     * Creates a condition
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param value the fixed value.
     * @return such type of instance.
     * @precondition leftSideField != null
     * @precondition operator != null
     */
    public Condition createCondition(
        final Field leftSideField,
        final ConditionOperator operator,
        final Calendar value)
    {
        return new AtomicCondition(leftSideField, operator, value);
    }

    /**
     * Creates a condition
     * @param leftSideField the left-side field.
     * @param operator the operator.
     * @param value the fixed value.
     * @return such type of instance.
     * @precondition leftSideField != null
     * @precondition operator != null
     */
    public Condition createCondition(
        final Field leftSideField,
        final ConditionOperator operator,
        final Date value)
    {
        return new AtomicCondition(leftSideField, operator, value);
    }

    /**
     * Creates a variable condition.
     * @param field the field.
     * @param operator the operator.
     * @return such type of instance.
     * @precondition field != null
     * @precondition operator != null
     */
    public VariableCondition createVariableCondition(
        final Field field, final ConditionOperator operator)
    {
        return new VariableCondition(field, operator);
    }

    /**
     * Creates a variable condition.
     * @param field the field.
     * @param operator the operator.
     * @param function the function.
     * @return such type of instance.
     * @precondition field != null
     * @precondition operator != null
     * @precondition function != null
     */
    public VariableCondition createVariableCondition(
        final Field field, final ConditionOperator operator, final Function function)
    {
        return new FunctionVariableCondition(field, operator, function);
    }

    /**
     * Creates a wrapper condition.
     * @param condition the condition to wrap.
     * @param prefix the prefix.
     * @param suffix the suffix.
     * @return the wrapped condition.
     * @precondition condition != null
     */
    public Condition wrap(
        final Condition condition, final String prefix, final String suffix)
    {
        return new _ConditionWrapper(condition, prefix, suffix);
    }

    /**
     * Creates a wrapper condition.
     * @param condition the condition to wrap.
     * @param prefix the prefix.
     * @param suffix the suffix.
     * @return the wrapped condition.
     * @precondition condition != null
     */
    public Condition wrap(
        final AtomicCondition condition,
        final String prefix,
        final String suffix)
    {
        return new _AtomicConditionWrapper(condition, prefix, suffix);
    }

    /**
     * Creates a wrapper condition.
     * @param condition the condition to wrap.
     * @param prefix the prefix.
     * @param suffix the suffix.
     * @return the wrapped condition.
     * @precondition condition != null
     */
    public VariableCondition wrap(
        final VariableCondition condition,
        final String prefix,
        final String suffix)
    {
        return new _VariableConditionWrapper(condition, prefix, suffix);
    }

    /**
     * Envelopes a condition surrounding it with appropiate prefix and suffix.
     * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
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
            final Condition condition,
            final String prefix,
            final String suffix)
        {
            super();

            immutableSetCondition(condition);
            immutableSetPrefix(prefix);
            immutableSetSuffix(suffix);
        }
        /**
         * Specifies the condition to wrap.
         * @param condition such condition.
         */
        private void immutableSetCondition(final Condition condition)
        {
            m__Condition = condition;
        }

        /**
         * Specifies the condition to wrap.
         * @param condition such condition.
         */
        protected void setCondition(final Condition condition)
        {
            immutableSetCondition(condition);
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
        private void immutableSetPrefix(final String prefix)
        {
            m__strPrefix = prefix;
        }

        /**
         * Specifies the prefix.
         * @param prefix such prefix.
         */
        protected void setPrefix(final String prefix)
        {
            immutableSetPrefix(prefix);
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
        private void immutableSetSuffix(final String suffix)
        {
            m__strSuffix = suffix;
        }

        /**
         * Specifies the suffix.
         * @param suffix such suffix.
         */
        protected void setSuffix(final String suffix)
        {
            immutableSetSuffix(suffix);
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
            return
                getVariableConditions(
                    super.getVariableConditions(),
                    getCondition());
        }

        /**
         * Retrieves the variable conditions.
         * @return such collection.
         */
        protected Collection getVariableConditions(
            final Collection parentVariableConditions,
            final Condition condition)
        {
            Collection result = parentVariableConditions;

            if  (condition != null) 
            {
                Collection t_cVariableConditions =
                    condition.getVariableConditions();

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
            return
                toSimplifiedString(
                    getPrefix(),
                    getCondition(),
                    getSuffix());
        }

        /**
         * Outputs a brief text version of the condition.
         * @param prefix the prefix.
         * @param condition the condition.
         * @param suffix the suffix.
         * @return such text.
         */
        protected String toSimplifiedString(
            final String prefix,
            final Condition condition,
            final String suffix)
        {
            String result = "";

            if  (condition != null)
            {
                result =
                      prefix
                    + condition.toSimplifiedString()
                    + suffix;
            }

            return result;
        }

        /**
         * Outputs a text version of the condition.
         * @return such text.
         */
        public String toString()
        {
            return toString(getPrefix(), getCondition(), getSuffix());
        }

        /**
         * Outputs a text version of the condition.
         * @param prefix the prefix.
         * @param condition the condition.
         * @param suffix the suffix.
         * @return such text.
         */
        public String toString(
            final String prefix,
            final Condition condition,
            final String suffix)
        {
            return prefix + condition + suffix;
        }
    }

    /**
     * Envelopes a condition surrounding it with appropiate prefix and suffix.
     * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
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
            final AtomicCondition condition,
            final String prefix,
            final String suffix)
        {
            super(
                condition.getLeftSideField(),
                condition.getOperator(),
                condition.getRightSideField());

            immutableSetCondition(condition);
            immutableSetPrefix(prefix);
            immutableSetSuffix(suffix);
        }

        /**
         * Specifies the condition to wrap.
         * @param condition such condition.
         */
        private void immutableSetCondition(final AtomicCondition condition)
        {
            m__Condition = condition;
        }

        /**
         * Specifies the condition to wrap.
         * @param condition such condition.
         */
        protected void setCondition(final AtomicCondition condition)
        {
            immutableSetCondition(condition);
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
        private void immutableSetPrefix(final String prefix)
        {
            m__strPrefix = prefix;
        }

        /**
         * Specifies the prefix.
         * @param prefix such prefix.
         */
        protected void setPrefix(final String prefix)
        {
            immutableSetPrefix(prefix);
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
        private void immutableSetSuffix(final String suffix)
        {
            m__strSuffix = suffix;
        }

        /**
         * Specifies the suffix.
         * @param suffix such suffix.
         */
        protected void setSuffix(final String suffix)
        {
            immutableSetSuffix(suffix);
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
            return getLeftSideField(getCondition());
        }

        /**
         * Retrieves the left-side field.
         * @param condition the condition.
         * @return such reference.
         */
        protected Field getLeftSideField(final AtomicCondition condition)
        {
            Field result = null;

            if  (condition != null) 
            {
                result = condition.getLeftSideField();
            }
            
            return result;
        }

        /**
         * Retrieves the condition operator.
         * @return such reference.
         */
        public ConditionOperator getOperator()
        {
            return getOperator(getCondition());
        }

        /**
         * Retrieves the condition operator.
         * @param condition the condition.
         * @return such reference.
         */
        protected ConditionOperator getOperator(final AtomicCondition condition)
        {
            ConditionOperator result = null;

            if  (condition != null) 
            {
                result = condition.getOperator();
            }
            
            return result;
        }

        /**
         * Retrieves the right-side field.
         * @return such reference.
         */
        public Field getRightSideField()
        {
            return getRightSideField(getCondition());
        }

        /**
         * Retrieves the right-side field.
         * @param condition the condition.
         * @return such reference.
         */
        protected Field getRightSideField(final AtomicCondition condition)
        {
            Field result = null;

            if  (condition != null) 
            {
                result = condition.getRightSideField();
            }
            
            return result;
        }

        /**
         * Retrieves the right-side value.
         * @return such reference.
         */
        public String getRightSideValue()
        {
            return getRightSideValue(getCondition());
        }

        /**
         * Retrieves the right-side value.
         * @param condition the condition.
         * @return such reference.
         */
        protected String getRightSideValue(final AtomicCondition condition)
        {
            String result = null;

            if  (condition != null) 
            {
                result = condition.getRightSideValue();
            }
            
            return result;
        }

        /**
         * Retrieves the variable conditions.
         * @return such collection.
         */
        public Collection getVariableConditions()
        {
            return
                getVariableConditions(
                    super.getVariableConditions(),
                    getCondition());
        }

        /**
         * Retrieves the variable conditions.
         * @param parentVariableConditions the parent's variable conditions.
         * @param condition the condition.
         * @return such collection.
         */
        public Collection getVariableConditions(
            final Collection parentVariableConditions,
            final Condition condition)
        {
            Collection result = parentVariableConditions;

            if  (condition != null) 
            {
                Collection t_cVariableConditions =
                    condition.getVariableConditions();

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
            return
                toSimplifiedString(getPrefix(), getCondition(), getSuffix());
        }

        /**
         * Outputs a brief text version of the condition.
         * @param prefix the prefix.
         * @param condition the condition.
         * @param suffix the suffix.
         * @return such text.
         */
        protected String toSimplifiedString(
            final String prefix,
            final Condition condition,
            final String suffix)
        {
            String result = "";

            if  (condition != null)
            {
                result =
                      prefix
                    + condition.toSimplifiedString()
                    + suffix;
            }

            return result;
        }

        /**
         * Outputs a text version of the condition.
         * @return such text.
         */
        public String toString()
        {
            return toString(getPrefix(), getCondition(), getSuffix());
        }

        /**
         * Outputs a text version of the condition.
         * @param prefix the prefix.
         * @param condition the condition.
         * @param suffix the suffix.
         * @return such text.
         */
        protected String toString(
            final String prefix,
            final Condition condition,
            final String suffix)
        {
            return prefix + condition + suffix;
        }
    }


    /**
     * Envelopes a variable condition surrounding it with appropiate
     * prefix and suffix.
     * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
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
            final VariableCondition condition,
            final String prefix,
            final String suffix)
        {
            super(
                condition.getLeftSideField(),
                condition.getOperator());

            immutableSetCondition(condition);
            immutableSetPrefix(prefix);
            immutableSetSuffix(suffix);
        }

        /**
         * Specifies the condition to wrap.
         * @param condition such condition.
         */
        private void immutableSetCondition(final VariableCondition condition)
        {
            m__Condition = condition;
        }

        /**
         * Specifies the condition to wrap.
         * @param condition such condition.
         */
        protected void setCondition(final VariableCondition condition)
        {
            immutableSetCondition(condition);
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
        private void immutableSetPrefix(final String prefix)
        {
            m__strPrefix = prefix;
        }

        /**
         * Specifies the prefix.
         * @param prefix such prefix.
         */
        protected void setPrefix(final String prefix)
        {
            immutableSetPrefix(prefix);
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
        private void immutableSetSuffix(final String suffix)
        {
            m__strSuffix = suffix;
        }

        /**
         * Specifies the suffix.
         * @param suffix such suffix.
         */
        protected void setSuffix(final String suffix)
        {
            immutableSetSuffix(suffix);
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
            return getLeftSideField(getCondition());
        }

        /**
         * Retrieves the left-side field.
         * @param condition the condition.
         * @return such reference.
         */
        protected Field getLeftSideField(final VariableCondition condition)
        {
            Field result = null;

            if  (condition != null) 
            {
                result = condition.getLeftSideField();
            }
            
            return result;
        }

        /**
         * Retrieves the condition operator.
         * @return such reference.
         */
        public ConditionOperator getOperator()
        {
            return getOperator(getCondition());
        }

        /**
         * Retrieves the condition operator.
         * @param condition the condition.
         * @return such reference.
         */
        protected ConditionOperator getOperator(
            final VariableCondition condition)
        {
            ConditionOperator result = null;

            if  (condition != null) 
            {
                result = condition.getOperator();
            }
            
            return result;
        }

        /**
         * Retrieves the right-side field.
         * @return such reference.
         */
        public Field getRightSideField()
        {
            return getRightSideField(getCondition());
        }

        /**
         * Retrieves the right-side field.
         * @param condition the condition.
         * @return such reference.
         */
        protected Field getRightSideField(final VariableCondition condition)
        {
            Field result = null;

            if  (condition != null) 
            {
                result = condition.getRightSideField();
            }
            
            return result;
        }

        /**
         * Retrieves the right-side value.
         * @return such reference.
         */
        public String getRightSideValue()
        {
            return getRightSideValue(getCondition());
        }

        /**
         * Retrieves the right-side value.
         * @param condition the condition.
         * @return such reference.
         */
        protected String getRightSideValue(final VariableCondition condition)
        {
            String result = null;

            if  (condition != null) 
            {
                result = condition.getRightSideValue();
            }
            
            return result;
        }

        /**
         * Outputs a brief text version of the condition.
         * @return such text.
         */
        public String toSimplifiedString()
        {
            return
                toSimplifiedString(
                    getPrefix(),
                    getCondition(),
                    getSuffix());
        }

        /**
         * Outputs a brief text version of the condition.
         * @param prefix the prefix.
         * @param condition the condition.
         * @param suffix the suffix.
         * @return such text.
         */
        protected String toSimplifiedString(
            final String prefix,
            final VariableCondition condition,
            final String suffix)
        {
            String result = "";

            if  (condition != null)
            {
                result =
                      prefix
                    + condition.toSimplifiedString()
                    + suffix;
            }

            return result;
        }

        /**
         * Outputs a text version of the condition.
         * @return such text.
         */
        public String toString()
        {
            return toString(getPrefix(), getCondition(), getSuffix());
        }

        /**
         * Outputs a text version of the condition.
         * @param prefix the prefix.
         * @param condition the condition.
         * @param suffix the suffix.
         * @return such text.
         */
        protected String toString(
            final String prefix,
            final Condition condition,
            final String suffix)
        {
            return prefix + condition + suffix;
        }
    }
}
