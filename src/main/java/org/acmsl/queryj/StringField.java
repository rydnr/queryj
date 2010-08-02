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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents text fields.
 *
 */
package org.acmsl.queryj;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.Field;

/**
 * Represents text fields.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class StringField
    extends  Field
{
    /**
     * Creates a String field using given information.
     * @param name the field name.
     * @param table the table.
     * @precondition name != null
     * @precondition table != null
     */
    public StringField(final String name, final Table table)
    {
        super(name, table);
    }

    /**
     * Retrieves the condition to be able to filter for equality.
     * @param value the value.
     * @return such kind of condition.
     */
    public Condition equals(final String value)
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
        final String value,
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createCondition(
                this,
                conditionOperatorRepository.getEquals(),
                "'" + value + "'");
    }

    /**
     * Retrieves the condition to be able to filter for non-equality.
     * @param value the value.
     * @return such kind of condition.
     */
    public Condition notEquals(final String value)
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
        final String value,
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createCondition(
                this,
                conditionOperatorRepository.getNotEquals(),
                "'" + value + "'");
    }

    /**
     * Retrieves the condition to be able to filter for text patterns.
     * @param value the value.
     * @return such kind of condition.
     */
    public Condition like(final String value)
    {
        return
            like(
                value,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }

    /**
     * Retrieves the condition to be able to filter for text patterns.
     * @param value the value.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected Condition like(
        final String value,
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createCondition(
                this,
                conditionOperatorRepository.getLike(),
                "'" + value + "'");
    }

    /**
     * Retrieves the condition to be able to filter for not containing
     * text patterns.
     * @param value the value.
     * @return such kind of condition.
     */
    public Condition notLike(final String value)
    {
        return
            like(
                value,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }

    /**
     * Retrieves the condition to be able to filter for not containing
     * text patterns.
     * @param value the value.
     * @param conditionFactory the <code>ConditionFactory</code> instance.
     * @param conditionOperatorRepository the
     * <code>ConditionOperatorRepository</code> instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    protected Condition notLike(
        final String value,
        final ConditionFactory conditionFactory,
        final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createCondition(
                this,
                conditionOperatorRepository.getNotLike(),
                "'" + value + "'");
    }
}
