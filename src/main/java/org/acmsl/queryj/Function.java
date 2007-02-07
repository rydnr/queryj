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
 * Filename: Function.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents text/numeric/time/system functions in SQL statements.
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
 * Represents text/numeric/time/system in SQL statements.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public abstract class Function
{
    /**
     * The function name.
     */
    private String m__strName;
    
    /**
     * Creates a function condition using given information.
     * @param name the function name.
     * @precondition name != null
     */
    public Function(final String name)
    {
        immutableSetName(name);
        
    }

    /**
     * Specifies the name.
     * @param name such name.
     */
    protected final void immutableSetName(final String name)
    {
        m__strName = name;
    }

    /**
     * Specifies the name.
     * @param name such name.
     */
    protected void setName(final String name)
    {
        immutableSetName(name);
    }

    /**
     * Retrieves the name.
     * @param name such name.
     */
    public String getName()
    {
        return m__strName ;
    }

    /**
     * Builds an expression.
     * @param value the expression value.
     * @return the function call in text format.
     * @precondition value != null
     */
    public abstract String buildExpression(final String value);
}
