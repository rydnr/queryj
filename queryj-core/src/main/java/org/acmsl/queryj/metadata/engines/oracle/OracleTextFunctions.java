//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
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
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: OracleTextFunctions.java
 *
 * Author: QueryJ
 *
 * Description: Contains the text functions supported by Oracle Oracle8i
 *              Enterprise Edition Release 8.1.7.0.1 - Production
 *              JServer Release 8.1.7.0.1 - Production.
 *
 */
package org.acmsl.queryj.metadata.engines.oracle;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.sql.Field;
import org.acmsl.queryj.sql.QueryUtils;
import org.acmsl.queryj.sql.StringField;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Contains the text functions supported by Oracle Oracle8i Enterprise Edition
 * Release 8.1.7.0.1 - Production JServer Release 8.1.7.0.1 - Production.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@SuppressWarnings("unused")
@ThreadSafe
public class OracleTextFunctions
    implements  Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class OracleTextFunctionsSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final OracleTextFunctions SINGLETON =
            new OracleTextFunctions();
    }

    /**
     * Retrieves a TextFunctions instance.
     * @return such instance.
     */
    @NotNull
    public static OracleTextFunctions getInstance()
    {
        return OracleTextFunctionsSingletonContainer.SINGLETON;
    }

    /**
     * Creates the field represented by the usage of Oracle's LTRIM function.
     * @param field the text field.
     * @return the corresponding function call.
     */
    @NotNull
    public StringField upper(final StringField field)
    {
        return new _StringFieldWrapper(field, "UPPER");
    }

    /**
     * Wraps a field to call a function.
     * @author <a href="http://www.acm-sl.org/projects/queryj">QueryJ</a>
     */
    private static final class _StringFieldWrapper
        extends  StringField
    {
        /**
         * The field to wrap.
         */
        private Field m__WrappedField;

        /**
         * The function.
         */
        private String m__strFunction;

        /**
         * The additional arguments.
         */
        private Object[] m__aParameters;

        /**
         * No function flag.
         */
        private boolean m__bNoFunction;

        /**
         * Builds a FieldWrapper for given field.
         * @param field the field to wrap.
         * @param function the function.
         */
        private _StringFieldWrapper(@Nullable final Field field, final String function)
        {
            // Example of when ? operator cannot be substituted by if-else.
            super(
                ((field == null) ? "" : field.getName()),
                ((field == null) ? null : field.getTable()));
            immutableSetWrappedField(field);
            immutableSetFunction(function);
            immutableSetNoFunction(false);
        }

        /**
         * Builds a FieldWrapper for given field and parameters.
         * @param field the field to wrap.
         * @param function the function.
         * @param parameters the additional parameters.
         */
        @SuppressWarnings("unused")
        private _StringFieldWrapper(
            final Field field, final String function, final Object[] parameters)
        {
            this(field, function);
            immutableSetParameters(parameters);
        }

        /**
         * Builds a FieldWrapper for given persistence function or variable.
         * @param function the variable or function.
         * @param variable <code>true</code> if it is a variable.
         */
        @SuppressWarnings("unused")
        private _StringFieldWrapper(
            final String function, @SuppressWarnings("unused") final boolean variable)
        {
            this((Field) null, function);
            immutableSetNoFunction(true);
        }

        /**
         * Builds a FieldWrapper for given field and parameters.
         * @param function the function.
         */
        @SuppressWarnings("unused")
        private _StringFieldWrapper(final String function)
        {
            this((Field) null, function);
        }

        /**
         * Builds a FieldWrapper for given value.
         * @param value the value to wrap.
         * @param function the function.
         */
        @SuppressWarnings("unused")
        private _StringFieldWrapper(final String value, final String function)
        {
            this((Field) null, function);
            immutableSetParameters(new Object[]{value});
        }

        /**
         * Specifies the wrapped field.
         * @param field the field to wrap.
         */
        private void immutableSetWrappedField(final Field field)
        {
            m__WrappedField = field;
        }

        /**
         * Specifies the wrapped field.
         * @param field the field to wrap.
         */
        @SuppressWarnings("unused")
        public void setWrappedField(final Field field)
        {
            immutableSetWrappedField(field);
        }

        /**
         * Retrieves the wrapped field.
         * @return such field.
         */
        public Field getWrappedField()
        {
            return m__WrappedField;
        }

        /**
         * Specifies the function.
         * @param function such function.
         */
        private void immutableSetFunction(final String function)
        {
            m__strFunction = function;
        }

        /**
         * Specifies the function.
         * @param function such function.
         */
        @SuppressWarnings("unused")
        public void setFunction(final String function)
        {
            immutableSetFunction(function);
        }

        /**
         * Retrieves the function.
         * @return such information.
         */
        public String getFunction()
        {
            return m__strFunction;
        }

        /**
         * Specifies the parameter list.
         * @param parameters the parameters.
         */
        private void immutableSetParameters(final Object[] parameters)
        {
            m__aParameters = parameters;
        }

        /**
         * Specifies the parameter list.
         * @param parameters the parameters.
         */
        public void setParameters(final Object[] parameters)
        {
            immutableSetParameters(parameters);
        }

        /**
         * Retrieves the parameter list.
         * @return the parameters.
         */
        public Object[] getParameters()
        {
            return m__aParameters;
        }

        /**
         * Specifies whether the operator is a function or a variable.
         * @param function such flag.
         */
        private void immutableSetNoFunction(final boolean function)
        {
            m__bNoFunction = function;
        }

        /**
         * Specifies whether the operator is a function or a variable.
         * @param variable such flag.
         */
        @SuppressWarnings("unused")
        public void setNoFunction(final boolean variable)
        {
            immutableSetNoFunction(variable);
        }

        /**
         * Retrieves whether the operator is a function or a variable.
         * @return such information.
         */
        public boolean isNotAFunction()
        {
            return m__bNoFunction;
        }

        /**
         * Builds a text representation of this object.
         * @return this object in text format.
         */
        @NotNull
        public String toString()
        {
            @NotNull final StringBuilder t_sbResult = new StringBuilder();

            @NotNull final QueryUtils t_QueryUtils = QueryUtils.getInstance();
            final boolean t_bVariable = isNotAFunction();
            @Nullable final String t_strFunction = getFunction();
            if  (t_strFunction != null)
            {
                t_sbResult.append(t_strFunction);
            }

            if  (!t_bVariable)
            {
                t_sbResult.append("(");
                @Nullable final Field t_strWrappedField = getWrappedField();
                if  (t_strWrappedField != null)
                {
                    t_sbResult.append(t_strWrappedField.toString());

                }

                @Nullable final Object[] t_aArgs = getParameters();

                if  (   (t_aArgs != null)
                     && (t_aArgs.length > 0))
                {
                    if  (t_strWrappedField != null)
                    {
                        t_sbResult.append(",");

                    }
                    if  (t_QueryUtils.shouldBeEscaped(t_aArgs[0]))
                    {
                        t_sbResult.append("\"");
                    }
                    t_sbResult.append(t_aArgs[0]);

                    if  (t_QueryUtils.shouldBeEscaped(t_aArgs[0]))
                    {
                        t_sbResult.append("\"");
                    }
                    boolean t_bQuoted;
                    for  (int t_iIndex = 1; t_iIndex < t_aArgs.length; t_iIndex++)
                    {
                        t_sbResult.append(",");
                        t_bQuoted = (t_QueryUtils.shouldBeEscaped(t_aArgs[t_iIndex]));
                        if  (t_bQuoted)
                        {
                            t_sbResult.append("\"");
                        }
                        t_sbResult.append(t_aArgs[t_iIndex]);
                        if  (   (t_bQuoted)
                             && (t_iIndex < t_aArgs.length - 1))
                        {
                            t_sbResult.append("\"");
                        }
                    }
                }

                if  (   (t_strFunction != null)
                     && (t_aArgs != null)
                     && (t_aArgs.length > 1)
                     && (t_QueryUtils.shouldBeEscaped(t_aArgs[t_aArgs.length - 1])))
                {
                    t_sbResult.append("\"");

                }
                t_sbResult.append(")");
            }

            return t_sbResult.toString();
        }

        /**
         * Checks whether this instance is equal to given one.
         * @param other the other instance to compare to.
         * @return <code>true</code> if both instances represent
         * the same entity.
         */
        @Override
        public boolean equals(@Nullable final Object other)
        {
            boolean result = false;

            if  (other != null)
            {
                final String t_strThisToString = toString();

                final String t_strOtherToString = other.toString();

                result =
                    (   (other instanceof OracleTextFunctions)
                     && (t_strThisToString.equals(t_strOtherToString)));
            }

            return result;
        }

        /**
         * Retrieves the hash code.
         * @return such information.
         */
        public int hashCode()
        {
            return hashCode(getWrappedField(), getFunction(), getParameters());
        }

        /**
         * Retrieves the hash code.
         * @param field the wrapped field.
         * @param function the function name.
         * @param parameters the parameters.
         * @return such information.
         */
        protected int hashCode(
            final Field field,
            final String function,
            @Nullable final Object[] parameters)
        {
            int result = (field + function).hashCode();
            
            final int t_iCount = (parameters != null) ? parameters.length : 0;

            @Nullable Object t_CurrentParameter;

            for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                t_CurrentParameter = parameters[t_iIndex];

                if  (t_CurrentParameter != null)
                {
                    result += t_CurrentParameter.hashCode();
                }
            }

            return result;
        }
    }
}

