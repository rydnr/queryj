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
 * Filename: MultipleConditionOperator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents condition operators involving multiple bindings.
 *
 */
package org.acmsl.queryj;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.Condition;
import org.acmsl.queryj.ConditionOperator;
import org.acmsl.queryj.Field;

/**
 * Represents condition operators involving multiple bindings.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class MultipleConditionOperator
    extends  ConditionOperator
{
    /**
     * The binding count.
     */
    private int m__iBindingCount;

    /**
     * Creates a multiple-condition operator using given information.
     * @param symbol the symbol.
     * @param count the binding count.
     * @precondition symbol != null
     * @precondition count > 0
     */
    public MultipleConditionOperator(final String symbol, final int count)
    {
        super(symbol);
        immutableSetBindingCount(count);
    }

    /**
     * Specifies the binding count.
     * @param count the count.
     */
    private void immutableSetBindingCount(final int count)
    {
        m__iBindingCount = count;
    }

    /**
     * Specifies the binding count.
     * @param count the count.
     */
    protected void setBindingCount(final int count)
    {
        immutableSetBindingCount(count);
    }

    /**
     * Retrieves the binding count.
     * @return such count.
     */
    public int getBindingCount()
    {
        return m__iBindingCount;
    }

    /**
     * Checks if given object is logically equal to this one.
     * @param candidate the object to check.
     * @return <code>true</code> if both objects are logically equal.
     */
    public boolean equals(final Object candidate)
    {
        return equals(candidate, getBindingCount());
    }

    /**
     * Checks if given object is logically equal to this one.
     * @param candidate the object to check.
     * @param count the count.
     * @return <code>true</code> if both objects are logically equal.
     */
    public boolean equals(final Object candidate, final int count)
    {
        boolean result = super.equals(candidate);

        if  (!result) 
        {
            result = !(candidate instanceof MultipleConditionOperator);
        }

        if  (!result)
        {
            MultipleConditionOperator t_Candidate =
                (MultipleConditionOperator) candidate;

            result = (t_Candidate.getBindingCount() == count);
        }

        return result;
    }

    /**
     * Retrieves the hash code.
     * @return such information.
     */
    public int hashCode()
    {
        return hashCode(getBindingCount());
    }

    /**
     * Retrieves the hash code.
     * @param count the binding count.
     * @return such information.
     */
    protected int hashCode(final int count)
    {
        return (MultipleConditionOperator.class + "[" + count + "]").hashCode();
    }
}

