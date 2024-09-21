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
 * Filename: QueryJBuildException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents abnormal situations regarding QueryJ generation
 *              process.
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents abnormal situations regarding QueryJ generation process.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public abstract class QueryJBuildException
    extends QueryJException
{
    private static final long serialVersionUID = 3970924778495193437L;

    /**
     * Builds a QueryJ build exception with a certain message.
     * @param message the message.
     * @param params the parameters.
     */
    public QueryJBuildException(@NotNull final String message, @NotNull final Object[] params)
    {
        super(message, params);
    }

    /**
     * Builds a QueryJ exception to wrap given one.
     * @param message the message.
     * @param params the parameters.
     * @param cause the exception to wrap.
     */
    public QueryJBuildException(
        @NotNull final String message,  @NotNull final Object[] params, @NotNull final Throwable cause)
    {
        super(message, params, cause);
    }
}
