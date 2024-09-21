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
 * Filename: MissingJdbcDriverAtRuntimeException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: The JDBC driver information is missing at runtime.
 *
 * Date: 2013/12/08
 * Time: 11:02
 *
 */
package org.acmsl.queryj.tools.exceptions;

/*
 * Importing QueryJ-API classes.
 */
import org.acmsl.queryj.api.exceptions.QueryJNonCheckedException;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * The JDBC driver information is missing at runtime.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/12/08 11:02
 */
@ThreadSafe
public class MissingJdbcDriverAtRuntimeException
    extends QueryJNonCheckedException
{
    private static final long serialVersionUID = 94270582279560215L;

    /**
     * Creates a new instance.
     */
    public MissingJdbcDriverAtRuntimeException()
    {
        super("missing.jdbc.driver.at.runtime");
    }
}
