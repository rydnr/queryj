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
 * Filename: ConnectionFlags.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <connection-flags> elements in custom-sql models.
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
public interface ConnectionFlags
    extends  IdentifiableElement,
             java.lang.Comparable
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
     * Retrieves the <i>transactionisolation</i> attribute.
     * @return such value.
     */
    public String getTransactionIsolation();
}
