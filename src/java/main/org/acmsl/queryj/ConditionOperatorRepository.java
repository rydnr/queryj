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
 * Description: Contains references to declared operators.
 *
 */
package org.acmsl.queryj;

/*
 * Importing ACM-SL classes.
 */
import org.acmsl.queryj.ConditionOperator;
import org.acmsl.queryj.NestedConditionOperator;

/*
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;

/**
 * Contains references to declared operators.
 * @author <a href="mailto:jsanleandro@yahoo.es">Jose San Leandro</a>
 */
public class ConditionOperatorRepository
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected ConditionOperatorRepository() {};

    /**
     * Specifies a new weak reference.
     * @param repository the repository instance to use.
     */
    protected static void setReference(
        final ConditionOperatorRepository repository)
    {
        singleton = new WeakReference(repository);
    }

    /**
     * Retrieves the weak reference.
     * @return such reference.
     */
    protected static WeakReference getReference()
    {
        return singleton;
    }

    /**
     * Retrieves a ConditionOperatorRepository instance.
     * @return such instance.
     */
    public static ConditionOperatorRepository getInstance()
    {
        ConditionOperatorRepository result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (ConditionOperatorRepository) reference.get();
        }

        if  (result == null) 
        {
            result = new ConditionOperatorRepository();

            setReference(result);
        }

        return result;
    }

    /**
     * Retrieves the equality operator.
     * @return such operator
     */
    public ConditionOperator getEquals()
    {
        return new ConditionOperator("=")  {};
    }

    /**
     * Retrieves the unequality operator.
     * @return such operator.
     */
    public ConditionOperator getNotEquals()
    {
        return new ConditionOperator("!=")  {};
    }

    /**
     * Retrieves the greater-than operator.
     * @return such operator.
     */
    public ConditionOperator getGreaterThan()
    {
        return new ConditionOperator(">")  {};
    }

    /**
     * Retrieves the less-than operator.
     * @return such operator.
     */
    public ConditionOperator getLessThan()
    {
        return new ConditionOperator("<")  {};
    }

    /**
     * Retrieves the null operator.
     * @return such operator.
     */
    public ConditionOperator getIsNull()
    {
        return new ConditionOperator("is null")  {};
    }

    /**
     * Retrieves the belongs-to operator.
     * @param query the query.
     * @return such operator.
     */
    public ConditionOperator getBelongsTo(final SelectQuery query)
    {
        return new NestedConditionOperator("in", query)  {};
    }

    /**
     * Retrieves the not-belongs-to operator.
     * @param query the query.
     * @return such operator.
     */
    public ConditionOperator getNotBelongsTo(final SelectQuery query)
    {
        return new NestedConditionOperator("not in", query)  {};
    }

    /**
     * Retrieves the <code>like</code> operator.
     * @return such operator.
     */
    public ConditionOperator getLike()
    {
        return new ConditionOperator("like")  {};
    }

    /**
     * Retrieves the <code>not like</code> operator.
     * @return such operator.
     */
    public ConditionOperator getNotLike()
    {
        return new ConditionOperator("not like")  {};
    }
}
