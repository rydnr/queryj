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
 * Description: Represents conditions in SQL statements.
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

/**
 * Represents operators used inside conditions.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
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
    public ConditionOperator(String symbol)
    {
        unmodifiableSetSymbol(symbol);
    }

    /**
     * Specifies the operator symbol.
     * @param symbol the symbol.
     */
    private void unmodifiableSetSymbol(String symbol)
    {
        m__strSymbol = symbol;
    }

    /**
     * Specifies the operator symbol.
     * @param symbol the symbol.
     */
    protected void setSymbol(String symbol)
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
    public boolean equals(Object candidate)
    {
        boolean result = (candidate == null);

        if  (!result) 
        {
            result = !(candidate instanceof ConditionOperator);
        }

        if  (!result)
        {
            ConditionOperator t_Candidate = (ConditionOperator) candidate;

            result = (t_Candidate.getSymbol() == getSymbol());

            if  (!result)
            {
                result = (t_Candidate.getSymbol() != null);

                if  (result) 
                {
                    result = (t_Candidate.getSymbol().equals(getSymbol()));
                }
                else
                {
                    result = (getSymbol() == null);
                }
            }
        }

        return result;
    }
}

