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
 * Filename: InvalidTemplateException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Triggered whenever an invalid template is generated.
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
 * Triggered whenever an invalid template is generated.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public abstract class InvalidTemplateException
    extends  QueryJNonCheckedException
{
    /**
     * Creates a InvalidTemplateException with given message.
     * @param messageKey the key to build the exception message.
     * @param params the parameters to build the exception message.
     */
    @SuppressWarnings("unused")
    public InvalidTemplateException(
        @NotNull final String messageKey,
        @NotNull final Object[] params)
    {
        super(messageKey, params);
    }

    /**
     * Creates a InvalidTemplateException with given cause.
     * @param messageKey the key to build the exception message.
     * @param params the parameters to build the exception message.
     * @param cause the error cause.
     */
    public InvalidTemplateException(
        @NotNull final String messageKey,
        @NotNull final Object[] params,
        @NotNull final Throwable cause)
    {
        super(messageKey, params, cause);
    }
}
