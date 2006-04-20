/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents conditions in SQL statements.
 *
 */
package org.acmsl.queryj;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.Condition;
import org.acmsl.queryj.Field;

/**
 * Represents operators used inside conditions.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class ConditionOperator
{
    /**
     * The symbol.
     */
    private String m__strSymbol;

    /**
     * Creates a operator using given information.
     * @param symbol the operator symbol.
     */
    public ConditionOperator(final String symbol)
    {
        unmodifiableSetSymbol(symbol);
    }

    /**
     * Specifies the operator symbol.
     * @param symbol the symbol.
     */
    private void unmodifiableSetSymbol(final String symbol)
    {
        m__strSymbol = symbol;
    }

    /**
     * Specifies the operator symbol.
     * @param symbol the symbol.
     */
    protected void setSymbol(final String symbol)
    {
        unmodifiableSetSymbol(symbol);
    }

    /**
     * Retrieves the operator symbol.
     * @return such symbol.
     */
    public String getSymbol()
    {
        return m__strSymbol;
    }

    /**
     * Retrieves a text version of the object.
     * @return such representation.
     */
    public String toString()
    {
        return getSymbol();
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
     * @param symbol the symbol.
     * @return <code>true</code> if both objects are logically equal.
     */
    protected boolean equals(final Object candidate, final String symbol)
    {
        boolean result = (candidate == null);

        if  (!result) 
        {
            result = !(candidate instanceof ConditionOperator);
        }

        if  (!result)
        {
            ConditionOperator t_Candidate = (ConditionOperator) candidate;

            result = (t_Candidate.getSymbol() == symbol);

            if  (!result)
            {
                result = (t_Candidate.getSymbol() != null);

                if  (result) 
                {
                    result = (t_Candidate.getSymbol().equals(symbol));
                }
                else
                {
                    result = (symbol == null);
                }
            }
        }

        return result;
    }
}

