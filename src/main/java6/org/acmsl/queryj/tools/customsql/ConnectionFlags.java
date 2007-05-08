/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: ConnectionFlagsElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models connection flags in custom-sql models.
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
 * Models connection flags in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface ConnectionFlags
    extends  IdentifiableElement
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
