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
 * Filename: ConnectionFlagsElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <connection-flags> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.Literals;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Models &lt;connection-flags&gt; elements in <i>custom-sql</i> models, which
 * satisfy the following DTD extract (to describe the model even in
 * non-xml implementations):<br/>
 * <!ELEMENT connection-flags EMPTY><br/>
 * <!ATTLIST connection-flags<br/>
 *   id ID #REQUIRED<br/>
 *   transactionisolation (  TRANSACTION_NONE<br/>
 *                         | TRANSACTION_READ_COMMITTED<br/>
 *                         | TRANSACTION_READ_UNCOMMITTED<br/>
 *                         | TRANSACTION_REPEATABLE_READ<br/>
 *                         | TRANSACTION_SERIALIZABLE ) #REQUIRED><br/>
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class ConnectionFlagsElement
    extends  AbstractIdElement
    implements  ConnectionFlags
{
    private static final long serialVersionUID = 7483005190203528643L;
    /**
     * The <i>transactionisolation</i> attribute.
     */
    public String m__strTransactionIsolation;

    /**
     * Creates a ConnectionFlagsElement with given information.
     * @param id the <i>id</i> attribute.
     * @param transactionIsolation the <i>transactionosilation</i> attribute.
     */
    public ConnectionFlagsElement(
        @NotNull final String id, @NotNull final String transactionIsolation)
    {
        super(id);
        immutableSetTransactionIsolation(transactionIsolation);
    }

    /**
     * Specifies the <i>transactionisolation</i> attribute.
     * @param value such value.
     */
    protected final void immutableSetTransactionIsolation(@NotNull final String value)
    {
        m__strTransactionIsolation = value;
    }

    /**
     * Specifies the <i>transactionisolation</i> attribute.
     * @param value such value.
     */
    @SuppressWarnings("unused")
    protected void setTransactionIsolation(@NotNull final String value)
    {
        immutableSetTransactionIsolation(value);
    }

    /**
     * Retrieves the <i>transactionisolation</i> attribute.
     * @return such value.
     */
    @NotNull
    public String getTransactionIsolation()
    {
        return m__strTransactionIsolation;
    }

    /**
     * Provides a text information about this instance.
     * @return such information.
     */
    @NotNull
    public String toString()
    {
        return
            toString(
                getId(),
                getTransactionIsolation());
    }

    /**
     * Provides a text information about this instance.
     * @param id the <i>id</i> attribute.
     * @param transactionIsolation the <i>transactionisolation</i> attribute.
     * @return such information.
     */
    @NotNull
    protected String toString(
        final String id,
        final String transactionIsolation)
    {
        return
              getClass().getName()
            + "[" + "id=" + id + "]"
            + "[" + "transactionIsolation=" + transactionIsolation + "]";
    }


    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    public int hashCode()
    {
        return
            new org.apache.commons.lang.builder.HashCodeBuilder(-1682907421, 626306293)
                .appendSuper(super.hashCode())
                .append(getTransactionIsolation())
                .toHashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    public boolean equals(final Object object)
    {
        boolean result = false;

        if  (object instanceof ConnectionFlags)
        {
            @NotNull final ConnectionFlags t_OtherInstance = (ConnectionFlags) object;

            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .appendSuper(super.equals(t_OtherInstance))
                    .append(
                        getTransactionIsolation(),
                        t_OtherInstance.getTransactionIsolation())
                .isEquals();
        }

        return result;
    }

    /**
     * Compares given object with this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     */
    public int compareTo(final ConnectionFlags object)
        throws  ClassCastException
    {
        int result = 1;

        @Nullable ClassCastException exceptionToThrow = null;

        if  (object != null)
        {
            result =
                new org.apache.commons.lang.builder.CompareToBuilder()
                .append(
                    getTransactionIsolation(),
                    object.getTransactionIsolation())
                .toComparison();
        }
        else
        {
            exceptionToThrow =
                new ClassCastException(
                      Literals.CANNOT_COMPARE
                    + object
                    + " with "
                    + toString());
        }

        if  (exceptionToThrow != null)
        {
            throw  exceptionToThrow;
        }

        return result;
    }


}
