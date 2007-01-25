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
 * Filename: VariableCondition.java
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
import org.acmsl.queryj.ConditionOperator;
import org.acmsl.queryj.Field;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents conditions in SQL statements.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class VariableCondition
    extends  AtomicCondition
{
    /**
     * Creates a variable condition using given information.
     * @param field the left-side field.
     * @param operator the operator.
     * @precondition field != null
     * @precondition operator != null
     */
    public VariableCondition(final Field field, final ConditionOperator operator)
    {
        super(field, operator, "?");
    }

    /**
     * Retrieves the variable conditions.
     * @return such collection.
     */
    public Collection getVariableConditions()
    {
        Collection result = super.getVariableConditions();

        if  (result == null)
        {
            result = new ArrayList();
        }

        result.add(this);

        return result;
    }
}
