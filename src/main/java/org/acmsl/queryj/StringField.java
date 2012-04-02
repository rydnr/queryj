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
 * Filename: StringField.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents text fields.
 *
 */
package org.acmsl.queryj;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.Field;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents text fields.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
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
    @NotNull
    public Condition equals(final String value)
    {
        return
            equals(
                value,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance(),
                StringUtils.getInstance());
    }

    /**
     * Retrieves the condition to be able to filter for equality.
     * @param value the value.
     * @param conditionFactory the {@link ConditionFactory} instance.
     * @param conditionOperatorRepository the
     * {@link ConditionOperatorRepository} instance.
     * @param stringUtils the {@link StringUtils} instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     * @precondition stringUtils != null
     */
    @NotNull
    protected Condition equals(
        final String value,
        @NotNull final ConditionFactory conditionFactory,
        @NotNull final ConditionOperatorRepository conditionOperatorRepository,
        @NotNull final StringUtils stringUtils)
    {
        return
            conditionFactory.createCondition(
                this,
                conditionOperatorRepository.getEquals(),
                stringUtils.quote(value, '\''));
    }

    /**
     * Retrieves the condition to be able to filter for non-equality.
     * @param value the value.
     * @return such kind of condition.
     */
    @NotNull
    public Condition notEquals(final String value)
    {
        return
            notEquals(
                value,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance(),
                StringUtils.getInstance());
    }

    /**
     * Retrieves the condition to be able to filter for non-equality.
     * @param value the value.
     * @param conditionFactory the {@link ConditionFactory} instance.
     * @param conditionOperatorRepository the
     * {@link ConditionOperatorRepository} instance.
     * @param stringUtils the {@link StringUtils} instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     * @precondition stringUtils != null
     */
    @NotNull
    protected Condition notEquals(
        final String value,
        @NotNull final ConditionFactory conditionFactory,
        @NotNull final ConditionOperatorRepository conditionOperatorRepository,
        @NotNull final StringUtils stringUtils)
    {
        return
            conditionFactory.createCondition(
                this,
                conditionOperatorRepository.getNotEquals(),
                stringUtils.quote(value, '\''));
    }

    /**
     * Retrieves the condition to be able to filter for text patterns.
     * @param value the value.
     * @return such kind of condition.
     */
    @NotNull
    public Condition like(final String value)
    {
        return
            like(
                value,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance(),
                StringUtils.getInstance());
    }

    /**
     * Retrieves the condition to be able to filter for text patterns.
     * @param value the value.
     * @param conditionFactory the {@link ConditionFactory} instance.
     * @param conditionOperatorRepository the
     * {@link ConditionOperatorRepository} instance.
     * @param stringUtils the {@link StringUtils} instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     * @precondition stringUtils != null
     */
    @NotNull
    protected Condition like(
        final String value,
        @NotNull final ConditionFactory conditionFactory,
        @NotNull final ConditionOperatorRepository conditionOperatorRepository,
        @NotNull final StringUtils stringUtils)
    {
        return
            conditionFactory.createCondition(
                this,
                conditionOperatorRepository.getLike(),
                stringUtils.quote(value, '\''));
    }

    /**
     * Retrieves the condition to be able to filter for not containing
     * text patterns.
     * @param value the value.
     * @return such kind of condition.
     */
    @NotNull
    public Condition notLike(final String value)
    {
        return
            like(
                value,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance(),
                StringUtils.getInstance());
    }

    /**
     * Retrieves the condition to be able to filter for not containing
     * text patterns.
     * @param value the value.
     * @param conditionFactory the {@link ConditionFactory} instance.
     * @param conditionOperatorRepository the
     * {@link ConditionOperatorRepository} instance.
     * @param stringUtils the {@link StringUtils} instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     * @precondition stringUtils != null
     */
    @NotNull
    protected Condition notLike(
        final String value,
        @NotNull final ConditionFactory conditionFactory,
        @NotNull final ConditionOperatorRepository conditionOperatorRepository,
        @NotNull final StringUtils stringUtils)
    {
        return
            conditionFactory.createCondition(
                this,
                conditionOperatorRepository.getNotLike(),
                stringUtils.quote(value, '\''));
    }

    /**
     * Retrieves the condition to be able to filter within a list of
     * predefined values.
     * @param values the values.
     * @return such kind of condition.
     */
    @NotNull
    public Condition in(final String[] values)
    {
        return
            in(
                values,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance(),
                StringUtils.getInstance());
    }

    /**
     * Retrieves the condition to be able to filter within a list of
     * predefined values.
     * @param values the values.
     * @param conditionFactory the {@link ConditionFactory} instance.
     * @param conditionOperatorRepository the
     * {@link ConditionOperatorRepository} instance.
     * @param stringUtils the {@link StringUtils} instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     * @precondition stringUtils != null
     */
    @NotNull
    protected Condition in(
        @Nullable final String[] values,
        @NotNull final ConditionFactory conditionFactory,
        @NotNull final ConditionOperatorRepository conditionOperatorRepository,
        @NotNull final StringUtils stringUtils)
    {
        return
            conditionFactory.createCondition(
                this,
                conditionOperatorRepository.getIn((values != null) ? values.length : 0),
                toQuotedCsv(values, stringUtils));
    }

    /**
     * Creates a list of comma-separated, quoted values.
     * @param values the values.
     * @param stringUtils the {@link StringUtils} instance.
     * @return such list.
     * @precondition values != null
     * @precondition stringUtils != null
     */
    protected String toQuotedCsv(@Nullable final String[] values, @NotNull final StringUtils stringUtils)
    {
        @NotNull StringBuilder t_sbResult = new StringBuilder();

        int t_iCount = (values != null) ? values.length : 0;

        @Nullable String t_strValue;

        for (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_strValue = values[t_iIndex];

            if (t_strValue != null)
            {
                t_sbResult.append(stringUtils.quote(values[t_iIndex], '\''));

                if (t_iIndex < t_iCount - 1)
                {
                    t_sbResult.append(",");
                }
            }
        }

        return t_sbResult.toString();
    }

    /**
     * Retrieves the condition to be able to filter excluding a list of
     * predefined values.
     * @param values the values.
     * @return such kind of condition.
     */
    @NotNull
    public Condition notIn(final String[] values)
    {
        return
            notIn(
                values,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance(),
                StringUtils.getInstance());
    }

    /**
     * Retrieves the condition to be able to filter excluding a list of
     * predefined values.
     * @param values the values.
     * @param conditionFactory the {@link ConditionFactory} instance.
     * @param conditionOperatorRepository the
     * {@link ConditionOperatorRepository} instance.
     * @param stringUtils the {@link StringUtils} instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     * @precondition stringUtils != null
     */
    @NotNull
    protected Condition notIn(
        @Nullable final String[] values,
        @NotNull final ConditionFactory conditionFactory,
        @NotNull final ConditionOperatorRepository conditionOperatorRepository,
        @NotNull final StringUtils stringUtils)
    {
        return
            conditionFactory.createCondition(
                this,
                conditionOperatorRepository.getNotIn((values != null) ? values.length : 0),
                toQuotedCsv(values, stringUtils));
    }

    /**
     * Retrieves the variable condition to be able to filter within a list of
     * predefined values.
     * @param count the parameter count.
     * @return such kind of condition.
     */
    @NotNull
    public VariableCondition in(final int count)
    {
        return
            in(
                count,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }

    /**
     * Retrieves the variable condition to be able to filter within a list of
     * predefined values.
     * @param count the number of parameters.
     * @param conditionFactory the {@link ConditionFactory} instance.
     * @param conditionOperatorRepository the
     * {@link ConditionOperatorRepository} instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    @NotNull
    protected VariableCondition in(
        final int count,
        @NotNull final ConditionFactory conditionFactory,
        @NotNull final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createVariableCondition(
                this,
                conditionOperatorRepository.getIn(count));
    }

    /**
     * Retrieves the variable condition to be able to filter excluding a list of
     * predefined values.
     * @param count the parameter count.
     * @return such kind of condition.
     */
    @NotNull
    public VariableCondition notIn(final int count)
    {
        return
            notIn(
                count,
                ConditionFactory.getInstance(),
                ConditionOperatorRepository.getInstance());
    }

    /**
     * Retrieves the variable condition to be able to filter excluding a list of
     * predefined values.
     * @param count the number of parameters.
     * @param conditionFactory the {@link ConditionFactory} instance.
     * @param conditionOperatorRepository the
     * {@link ConditionOperatorRepository} instance.
     * @return such kind of condition.
     * @precondition conditionFactory != null
     * @precondition conditionOperatorRepository != null
     */
    @NotNull
    protected VariableCondition notIn(
        final int count,
        @NotNull final ConditionFactory conditionFactory,
        @NotNull final ConditionOperatorRepository conditionOperatorRepository)
    {
        return
            conditionFactory.createVariableCondition(
                this,
                conditionOperatorRepository.getNotIn(count));
    }
}
