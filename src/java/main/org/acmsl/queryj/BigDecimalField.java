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
 *         >Jose San Leandro</a>
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
    public BigDecimalField(final String name, final Table table)
    {
        super(name, table);
    }

    /**
     * Retrieves the condition to be able to filter for equality.
     * @param value the value.
     * @return such kind of condition.
     */
    public Condition equals(final BigDecimal value)
    {
        return
            equals(
                value,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }
                
    /**
     * Retrieves the condition to be able to filter for equality.
     * @param value the value.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected Condition equals(
        final BigDecimal value,
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createCondition(
                this,
                conditionOperatorRepository.getEquals(),
                value);
    }

    /**
     * Retrieves the condition to be able to filter for non-equality.
     * @param value the value.
     * @return such kind of condition.
     */
    public Condition notEquals(final BigDecimal value)
    {
        return
            notEquals(
                value,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }
                
    /**
     * Retrieves the condition to be able to filter for non-equality.
     * @param value the value.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected Condition notEquals(
        final BigDecimal value,
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createCondition(
                this,
                conditionOperatorRepository.getNotEquals(),
                value);
    }

    /**
     * Retrieves the condition to be able to filter for lower values.
     * @param value the value.
     * @return such kind of condition.
     */
    public Condition greaterThan(final BigDecimal value)
    {
        return
            greaterThan(
                value,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }
                
    /**
     * Retrieves the condition to be able to filter for lower values.
     * @param value the value.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected Condition greaterThan(
        final BigDecimal value,
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createCondition(
                this,
                conditionOperatorRepository.getGreaterThan(),
                value);
    }

    /**
     * Retrieves the condition to be able to filter for higher values.
     * @param value the value.
     * @return such kind of condition.
     */
    public Condition lessThan(final BigDecimal value)
    {
        return
            lessThan(
                value,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }
                
    /**
     * Retrieves the condition to be able to filter for higher values.
     * @param value the value.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected Condition lessThan(
        final BigDecimal value,
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createCondition(
                this,
                conditionOperatorRepository.getLessThan(),
                value);
    }
}
