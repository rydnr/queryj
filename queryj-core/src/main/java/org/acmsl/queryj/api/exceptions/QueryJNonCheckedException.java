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
 * Filename: QueryJNonCheckedException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Non-checked QueryJ exceptions.
 *
 * Date: 2013/08/03
 * Time: 18:25
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.NonCheckedException;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;


/**
 * Non-checked QueryJ exceptions.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/03 18:25
 */
@ThreadSafe
public class QueryJNonCheckedException
    extends NonCheckedException
{
    private static final long serialVersionUID = -8100092770309110856L;

    /**
     * The name of the bundle.
     */
    public static final String QUERYJ_EXCEPTIONS_BUNDLE_NAME = "queryj-exceptions";

    /**
     * The system property of the bundle.
     */
    public static final String QUERYJ_EXCEPTIONS_BUNDLE_PROPERTY = "org.acmsl.queryj.exceptions";

    /**
     * Creates a QueryJNonCheckedException with given message.
     * @param messageKey the key to build the exception message.
     * @param params the parameters to build the exception message.
     */
    public QueryJNonCheckedException(
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
    public QueryJNonCheckedException(
        @NotNull final String messageKey,
        @NotNull final Object[] params,
        @NotNull final Throwable cause)
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
        return QUERYJ_EXCEPTIONS_BUNDLE_NAME;
    }

    /**
     * Retrieves the exceptions system property.
     * @return such bundle name.
     */
    @NotNull
    protected String retrieveExceptionsBundleProperty()
    {
        return QUERYJ_EXCEPTIONS_BUNDLE_PROPERTY;
    }

}
