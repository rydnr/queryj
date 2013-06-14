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
 * Filename: NoTableMatchingCustomResultException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Triggered when there's no table for a given result.
 *
 * Date: 6/12/13
 * Time: 10:17 PM
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.customsql.Result;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Triggered when there's no table for a given result.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/12
 */
@ThreadSafe
public class NoTableMatchingCustomResultException
    extends QueryJBuildException
{
    private static final long serialVersionUID = -989139460480205584L;

    /**
     * Creates an instance with valid context.
     * @param result the {@link Result} instance.
     */
    public NoTableMatchingCustomResultException(@NotNull final Result result)
    {
        super("no.table.matching.custom-result", new Object[] { result.getId() });
    }
}
