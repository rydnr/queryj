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
 * Filename: ConditionOperator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents operators used inside function-based conditions
 *              in SQL statements.
 *
 */
package org.acmsl.queryj;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.Condition;
import org.acmsl.queryj.Field;

/**
 * Represents operators used inside function-based conditions.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class FunctionConditionOperator
    extends  ConditionOperator
{
    /**
     * The function.
     */
    private Function m__Function;

    /**
     * Creates a operator using given information.
     * @param function the function.
     * @param symbol the operator symbol.
     * @precondition function != null
     * @precondition symbol != null
     */
    public FunctionConditionOperator(final Function function, final String symbol)
    {
        super(symbol);
        immutableSetFunction(function);
    }

    /**
     * Specifies the operator function.
     * @param function the function.
     */
    private void immutableSetFunction(final Function function)
    {
        m__Function = function;
    }

    /**
     * Specifies the operator function.
     * @param function the function.
     */
    protected void setFunction(final Function function)
    {
        immutableSetFunction(function);
    }

    /**
     * Retrieves the operator function.
     * @return such function.
     */
    public Function getFunction()
    {
        return m__Function;
    }

    /**
     * Retrieves a text version of the object.
     * @return such representation.
     */
    public String toString()
    {
        return toString(getFunction(), getSymbol());
    }

    /**
     * Retrieves a text version of the object.
     * @param function the function.
     * @param symbol the symbol.
     * @return such representation.
     * @precondition function != null
     * @precondition symbol != null
     */
    protected String toString(final Function function, final String symbol)
    {
        return function.buildExpression(symbol);
    }

    /**
     * Checks if given object is logically equal to this one.
     * @param candidate the object to check.
     * @return <code>true</code> if both objects are logically equal.
     */
    public boolean equals(final Object candidate)
    {
        return equals(candidate, getSymbol());
    }

    /**
     * Checks if given object is logically equal to this one.
     * @param candidate the object to check.
     * @param function the function.
     * @return <code>true</code> if both objects are logically equal.
     * @precondition function != null
     */
    protected boolean equals(final Object candidate, final Function function)
    {
        boolean result = false;

        if  (candidate instanceof FunctionConditionOperator)
        {
            FunctionConditionOperator t_Candidate =
                (FunctionConditionOperator) candidate;

            Function t_Function = t_Candidate.getFunction();
            
            result = (t_Function == function);

            if  (!result)
            {
                result = (t_Function != null);

                if  (result) 
                {
                    result = (t_Function.equals(function));
                }
                else
                {
                    result = (function == null);
                }
            }
        }

        if  (result)
        {
            result = super.equals(candidate);
        }

        return result;
    }

    /**
     * Retrieves the hash code.
     * @return such information.
     */
    public int hashCode()
    {
        return hashCode(getSymbol(), getFunction());
    }

    /**
     * Retrieves the hash code.
     * @param symbol the symbol.
     * @return such information.
     */
    protected int hashCode(final String symbol, final Function function)
    {
        return
            (  FunctionConditionOperator.class
                     + function.buildExpression(symbol)).hashCode();
    }
}

