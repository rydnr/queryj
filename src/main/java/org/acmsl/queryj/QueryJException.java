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
 * Filename: QueryJException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents abnormal situations regarding QueryJ processing.
 *
 */
package org.acmsl.queryj;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents abnormal situations regarding QueryJ processing.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class QueryJException
    extends  Exception
{
    /**
     * Builds a QueryJ exception with a certain message.
     * @param message the message.
     */
    public QueryJException(final String message)
    {
        super(message);
    }

    /**
     * Builds a QueryJ exception to wrap given one.
     * @param message the message.
     * @param cause the exception to wrap.
     */
    public QueryJException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Outputs a text representation of this exception.
     * @return the error description.
     */
    public String toString()
    {
        return toString(getMessage(), getCause());
    }

    /**
     * Outputs a text representation of this exception.
     * @param message the message.
     * @param cause the cause.
     * @return the error description.
     */
    public String toString(final String message, @Nullable final Throwable cause)
    {
        @NotNull StringBuffer t_sbResult = new StringBuffer();

        t_sbResult.append(message);

        if  (cause != null) 
        {
            t_sbResult.append(" (");
            t_sbResult.append(cause.getMessage());
            t_sbResult.append(")");
        }

        return t_sbResult.toString();
    }
}
