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
 * Description: Models <connection-flags> elements in custom-sql models.
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
package org.acmsl.queryj.tools.customsql;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.AbstractIdElement;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;

/**
 * Models &lt;connection-flags&gt; elements in <i>custom-sql</i> models, which
 * satisfy the following DTD extract (to describe the model even in
 * non-xml implementations):
 * <!ELEMENT connection-flags EMPTY>
 * <!ATTLIST connection-flags
 *   id ID #REQUIRED
 *   transactionisolation (  TRANSACTION_NONE
 *                         | TRANSACTION_READ_COMMITTED
 *                         | TRANSACTION_READ_UNCOMMITTED
 *                         | TRANSACTION_REPEATABLE_READ
 *                         | TRANSACTION_SERIALIZABLE ) #REQUIRED>
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public class ConnectionFlagsElement
    extends  AbstractIdElement
{
    /**
     * The <b>TRANSACTION_NONE</b> value for
     * <i>transactionisolation</i> attribute.
     */
    public static final String TRANSACTION_NONE = "TRANSACTION_NONE";

    /**
     * The <b>TRANSACTION_READ_COMMITTED</b> value for
     * <i>transactionisolation</i> attribute.
     */
    public static final String TRANSACTION_READ_COMMITTED =
        "TRANSACTION_READ_COMMITTED";

    /**
     * The <b>TRANSACTION_READ_UNCOMMITTED</b> value for
     * <i>transactionisolation</i> attribute.
     */
    public static final String TRANSACTION_READ_UNCOMMITTED =
        "TRANSACTION_READ_UNCOMMITTED";

    /**
     * The <b>TRANSACTION_REPEATABLE_READ</b> value for
     * <i>transactionisolation</i> attribute.
     */
    public static final String TRANSACTION_REPEATABLE_READ =
        "TRANSACTION_REPEATABLE_READ";

    /**
     * The <b>TRANSACTION_SERIALIZABLE</b> value for
     * <i>transactionisolation</i> attribute.
     */
    public static final String TRANSACTION_SERIALIZABLE =
        "TRANSACTION_SERIALIZABLE";

    /**
     * The <i>transactionisolation</i> attribute.
     */
    public String m__strTransactionIsolation;

    /**
     * Creates a ConnectionFlagsElement with given information.
     * @param id the <i>id</i> attribute.
     * @param transactionIsolation the <i>transactionosilation</i> attribute.
     * @precondition id != null
     * @precondition transactionIsolation != null
     */
    public ConnectionFlagsElement(
        final String id, final String transactionIsolation)
    {
        super(id);
        immutableSetTransactionIsolation(transactionIsolation);
    }

    /**
     * Specifies the <i>transactionisolation</i> attribute.
     * @param value such value.
     */
    protected final void immutableSetTransactionIsolation(final String value)
    {
        m__strTransactionIsolation = value;
    }

    /**
     * Specifies the <i>transactionisolation</i> attribute.
     * @param value such value.
     */
    protected void setTransactionIsolation(final String value)
    {
        immutableSetTransactionIsolation(value);
    }

    /**
     * Retrieves the <i>transactionisolation</i> attribute.
     * @return such value.
     */
    public String getTransactionIsolation()
    {
        return m__strTransactionIsolation;
    }

    /**
     * Provides a text information about this instance.
     * @return such information.
     */
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
    protected String toString(
        final String id,
        final String transactionIsolation)
    {
        return
              getClass().getName()
            + "[" + "id=" + id + "]"
            + "[" + "transactionIsolation=" + transactionIsolation + "]";
    }
}
