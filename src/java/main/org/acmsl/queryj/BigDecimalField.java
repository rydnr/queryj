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
 * Description: Represents big decimal fields.
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
import org.acmsl.queryj.DoubleField;

/*
 * Importing some JDK classes.
 */
import java.math.BigDecimal;

/**
 * Represents big decimal fields.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class BigDecimalField
    extends  DoubleField
{
    /**
     * Creates a big decimal field using given information.
     * @param name the field name.
     * @param table the table.
     */
    public BigDecimalField(String name, Table table)
    {
        super(name, table);
    }

    /**
     * Retrieves the condition to be able to filter for equality.
     * @param value the value.
     * @return such kind of condition.
     */
    public Condition equals(BigDecimal value)
    {
        Condition result = null;

        ConditionFactory t_ConditionFactory =
            ConditionFactory.getInstance();

        ConditionOperatorRepository t_ConditionOperatorRepository =
            ConditionOperatorRepository.getInstance();

        if  (   (t_ConditionFactory            != null) 
             && (t_ConditionOperatorRepository != null))
        {
            result =
                t_ConditionFactory.createCondition(
                    this,
                    t_ConditionOperatorRepository.getEquals(),
                    value);
        }

        return result;
    }

    /**
     * Retrieves the condition to be able to filter for non-equality.
     * @param value the value.
     * @return such kind of condition.
     */
    public Condition notEquals(BigDecimal value)
    {
        Condition result = null;

        ConditionFactory t_ConditionFactory =
            ConditionFactory.getInstance();

        ConditionOperatorRepository t_ConditionOperatorRepository =
            ConditionOperatorRepository.getInstance();

        if  (   (t_ConditionFactory            != null) 
             && (t_ConditionOperatorRepository != null))
        {
            result =
                t_ConditionFactory.createCondition(
                    this,
                    t_ConditionOperatorRepository.getNotEquals(),
                    value);
        }

        return result;
    }

    /**
     * Retrieves the condition to be able to filter for lower values.
     * @param value the value.
     * @return such kind of condition.
     */
    public Condition greaterThan(BigDecimal value)
    {
        Condition result = null;

        ConditionFactory t_ConditionFactory =
            ConditionFactory.getInstance();

        ConditionOperatorRepository t_ConditionOperatorRepository =
            ConditionOperatorRepository.getInstance();

        if  (   (t_ConditionFactory            != null) 
             && (t_ConditionOperatorRepository != null))
        {
            result =
                t_ConditionFactory.createCondition(
                    this,
                    t_ConditionOperatorRepository.getGreaterThan(),
                    value);
        }

        return result;
    }

    /**
     * Retrieves the condition to be able to filter for higher values.
     * @param value the value.
     * @return such kind of condition.
     */
    public Condition lessThan(BigDecimal value)
    {
        Condition result = null;

        ConditionFactory t_ConditionFactory =
            ConditionFactory.getInstance();

        ConditionOperatorRepository t_ConditionOperatorRepository =
            ConditionOperatorRepository.getInstance();

        if  (   (t_ConditionFactory            != null) 
             && (t_ConditionOperatorRepository != null))
        {
            result =
                t_ConditionFactory.createCondition(
                    this,
                    t_ConditionOperatorRepository.getLessThan(),
                    value);
        }

        return result;
    }
}
