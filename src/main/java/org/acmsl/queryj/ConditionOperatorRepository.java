//;-*- mode: java -*-
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
 * Filename: ConditionOperatorRepository.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Contains references to declared operators.
 *
 */
package org.acmsl.queryj;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.ConditionOperator;
import org.acmsl.queryj.NestedConditionOperator;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Repository;
import org.acmsl.commons.patterns.Singleton;
import org.jetbrains.annotations.NotNull;

/**
 * Contains references to declared operators.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class ConditionOperatorRepository
    implements  Repository,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class ConditionOperatorRepositorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final ConditionOperatorRepository SINGLETON =
            new ConditionOperatorRepository();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected ConditionOperatorRepository() {};

    /**
     * Retrieves a <code>ConditionOperatorRepository</code> instance.
     * @return such instance.
     */
    @NotNull
    public static ConditionOperatorRepository getInstance()
    {
        return ConditionOperatorRepositorySingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the equality operator.
     * @return such operator
     */
    @NotNull
    public ConditionOperator getEquals()
    {
        return new ConditionOperator("=");
    }

    /**
     * Retrieves the unequality operator.
     * @return such operator.
     */
    @NotNull
    public ConditionOperator getNotEquals()
    {
        return new ConditionOperator("!=");
    }

    /**
     * Retrieves the greater-than operator.
     * @return such operator.
     */
    @NotNull
    public ConditionOperator getGreaterThan()
    {
        return new ConditionOperator(">");
    }

    /**
     * Retrieves the less-than operator.
     * @return such operator.
     */
    @NotNull
    public ConditionOperator getLessThan()
    {
        return new ConditionOperator("<");
    }

    /**
     * Retrieves the null operator.
     * @return such operator.
     */
    @NotNull
    public ConditionOperator getIsNull()
    {
        return new ConditionOperator("is null");
    }

    /**
     * Retrieves the belongs-to operator.
     * @param query the query.
     * @return such operator.
     */
    @NotNull
    public ConditionOperator getBelongsTo(final SelectQuery query)
    {
        return new NestedConditionOperator("in", query);
    }

    /**
     * Retrieves the not-belongs-to operator.
     * @param query the query.
     * @return such operator.
     */
    @NotNull
    public ConditionOperator getNotBelongsTo(final SelectQuery query)
    {
        return new NestedConditionOperator("not in", query);
    }

    /**
     * Retrieves the belongs-to operator.
     * @param count the number of parameters within the clause.
     * @return such operator.
     */
    @NotNull
    public ConditionOperator getIn(final int count)
    {
        return new MultipleConditionOperator("in", count);
    }

    /**
     * Retrieves the not-belongs-to operator.
     * @param count the number of parameters within the clause.
     * @return such operator.
     */
    @NotNull
    public ConditionOperator getNotIn(final int count)
    {
        return new MultipleConditionOperator("not in", count);
    }

    /**
     * Retrieves the <code>like</code> operator.
     * @return such operator.
     */
    @NotNull
    public ConditionOperator getLike()
    {
        return new ConditionOperator("like");
    }

    /**
     * Retrieves the <code>not like</code> operator.
     * @return such operator.
     */
    @NotNull
    public ConditionOperator getNotLike()
    {
        return new ConditionOperator("not like");
    }
}
