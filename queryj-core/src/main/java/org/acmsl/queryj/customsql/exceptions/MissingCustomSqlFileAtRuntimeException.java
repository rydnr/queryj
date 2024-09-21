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
 * Filename: MissingCustomSqlFileAtRuntimeException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: The custom SQL file is missing at runtime.
 *
 * Date: 2013/12/08
 * Time: 09:55
 *
 */
package org.acmsl.queryj.customsql.exceptions;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.api.exceptions.QueryJNonCheckedException;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * The custom SQL file is missing at runtime.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/12/08 09:55
 */
@ThreadSafe
public class MissingCustomSqlFileAtRuntimeException
    extends QueryJNonCheckedException
{
    private static final long serialVersionUID = 5569874470208650380L;

    /**
     * Creates an instance with given context.
     */
    public MissingCustomSqlFileAtRuntimeException()
    {
        super("missing.custom.sql.file.at.runtime");
    }
}

