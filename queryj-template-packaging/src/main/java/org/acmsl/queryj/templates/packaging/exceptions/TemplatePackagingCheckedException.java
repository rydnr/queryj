/*
                        QueryJ Template Packaging

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
 * Filename: TemplatePackagingCheckedException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Checked exceptions for Template Packaging.
 *
 * Date: 2013/08/11
 * Time: 08:53
 *
 */
package org.acmsl.queryj.templates.packaging.exceptions;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

/**
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Checked exceptions for Template Packaging.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created 2013/08/11 08:53
 */
@ThreadSafe
public class TemplatePackagingCheckedException
    extends QueryJBuildException
{
    private static final long serialVersionUID = -4378305755075677629L;

    /**
     * The name of the bundle.
     */
    public static final String TEMPLATE_PACKAGING_EXCEPTIONS_BUNDLE_NAME = "template-packaging-exceptions";

    /**
     * The system property of the bundle.
     */
    public static final String TEMPLATE_PACKAGING_EXCEPTIONS_PROPERTY =
        "org.acmsl.queryj.templates.packaging.exceptions";

    /**
     * Builds a QueryJ exception with a certain message.
     * @param message the message.
     * @param params the parameters.
     */
    public TemplatePackagingCheckedException(@NotNull final String message, @NotNull final Object[] params)
    {
        super(message, params);
    }

    /**
     * Builds a QueryJ exception to wrap given one.
     * @param message the message.
     * @param params the parameters.
     * @param cause the exception to wrap.
     */
    public TemplatePackagingCheckedException(
        @NotNull final String message, @NotNull final Object[] params, @NotNull final Throwable cause)
    {
        super(message, params, cause);
    }

    /**
     * Retrieves the exceptions bundle.
     * @return such bundle name.
     */
    @NotNull
    @Override
    protected String retrieveExceptionsBundleName()
    {
        return TEMPLATE_PACKAGING_EXCEPTIONS_BUNDLE_NAME;
    }

    /**
     * Retrieves the exceptions bundle property.
     * @return such property.
     */
    @NotNull
    @Override
    protected String retrieveExceptionsBundleProperty()
    {
        return TEMPLATE_PACKAGING_EXCEPTIONS_PROPERTY;
    }

}
