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

/**
 * Contains references to declared operators.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
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
    public static ConditionOperatorRepository getInstance()
    {
        return ConditionOperatorRepositorySingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the equality operator.
     * @return such operator
     */
    public ConditionOperator getEquals()
    {
        return new ConditionOperator("=");
    }

    /**
     * Retrieves the unequality operator.
     * @return such operator.
     */
    public ConditionOperator getNotEquals()
    {
        return new ConditionOperator("!=");
    }

    /**
     * Retrieves the greater-than operator.
     * @return such operator.
     */
    public ConditionOperator getGreaterThan()
    {
        return new ConditionOperator(">");
    }

    /**
     * Retrieves the less-than operator.
     * @return such operator.
     */
    public ConditionOperator getLessThan()
    {
        return new ConditionOperator("<");
    }

    /**
     * Retrieves the null operator.
     * @return such operator.
     */
    public ConditionOperator getIsNull()
    {
        return new ConditionOperator("is null");
    }

    /**
     * Retrieves the belongs-to operator.
     * @param query the query.
     * @return such operator.
     */
    public ConditionOperator getBelongsTo(final SelectQuery query)
    {
        return new NestedConditionOperator("in", query);
    }

    /**
     * Retrieves the not-belongs-to operator.
     * @param query the query.
     * @return such operator.
     */
    public ConditionOperator getNotBelongsTo(final SelectQuery query)
    {
        return new NestedConditionOperator("not in", query);
    }

    /**
     * Retrieves the <code>like</code> operator.
     * @return such operator.
     */
    public ConditionOperator getLike()
    {
        return new ConditionOperator("like");
    }

    /**
     * Retrieves the <code>not like</code> operator.
     * @return such operator.
     */
    public ConditionOperator getNotLike()
    {
        return new ConditionOperator("not like");
    }
}
