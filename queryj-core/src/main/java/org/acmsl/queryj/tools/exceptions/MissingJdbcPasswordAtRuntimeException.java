/*
                        QueryJ Core

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
 * Filename: MissingJdbcPasswordAtRuntimeException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: The runtime information for the JDBC password is missing.
 *
 * Date: 2014/02/15
 * Time: 21:12
 *
 */
package org.acmsl.queryj.tools.exceptions;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.exceptions.QueryJNonCheckedException;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * The runtime information for the JDBC password is missing.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/02/15 21:12
 */
@ThreadSafe
public class MissingJdbcPasswordAtRuntimeException
    extends QueryJNonCheckedException
{
    private static final long serialVersionUID = -7059977794778627323L;

    /**
     * Creates a new instance.
     */
    public MissingJdbcPasswordAtRuntimeException()
    {
        super("missing.jdbc.password.at.runtime");
    }
}
