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
package org.acmsl.queryj.templates;

/*
 * Importing ACM-SL classes.
 */
import org.acmsl.commons.NonCheckedException;
import org.jetbrains.annotations.NotNull;

/**
 * Triggered whenever an invalid template is generated.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class InvalidTemplateException
    extends  NonCheckedException
{
    /**
     * Creates a InvalidTemplateException with given message.
     * @param messageKey the key to build the exception message.
     * @param params the parameters to build the exception message.
     * @precondition messageKey != null
     * @precondition params != null
     */
    public InvalidTemplateException(
        final String messageKey,
        final Object[] params)
    {
        super(messageKey, params);
    }

    /**
     * Creates a InvalidTemplateException with given cause.
     * @param messageKey the key to build the exception message.
     * @param params the parameters to build the exception message.
     * @param cause the error cause.
     * @precondition messageKey != null
     * @precondition params != null
     * @precondition cause != null
     */
    public InvalidTemplateException(
        final String messageKey,
        final Object[] params,
        final Throwable cause)
    {
        super(messageKey, params, cause);
    }

    /**
     * Retrieves the exceptions bundle.
     * @return such bundle name.
     */
    @NotNull
    protected String retrieveExceptionsBundleName()
    {
        return "queryj-exceptions";
    }

    /*
     * Retrieves the exceptions system property.
     * @return such bundle name.
     */
    @NotNull
    protected String retrieveExceptionsBundleProperty()
    {
        return "org.acmsl.queryj.exceptions";
    }
}
