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
 * Filename: TemplatePackagingNonCheckedException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base class for all template-packaging-related non-checked
 *              exceptions.
 *
 * Date: 2014/03/30
 * Time: 20:03
 *
 */
package org.acmsl.queryj.templates.packaging.exceptions;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.api.exceptions.QueryJNonCheckedException;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Base class for all template-packaging-related non-checked exceptions.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/30 20:03
 */
@ThreadSafe
public abstract class TemplatePackagingNonCheckedException
    extends QueryJNonCheckedException
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -3197807444017761355L;

    /**
     * Creates a {@code TemplatePackagingNonCheckedException} with given message.
     * @param messageKey the key to build the exception message.
     */
    protected TemplatePackagingNonCheckedException(@NotNull final String messageKey)
    {
        super(messageKey);
    }

    /**
     * Creates a {@code TemplatePackagingNonCheckedException} with given message.
     * @param messageKey the key to build the exception message.
     * @param params     the parameters to build the exception message.
     */
    protected TemplatePackagingNonCheckedException(
        @NotNull final String messageKey, @NotNull final Object[] params)
    {
        super(messageKey, params);
    }

    /**
     * Creates a {@code TemplatePackagingNonCheckedException} with given cause.
     * @param messageKey the key to build the exception message.
     * @param params     the parameters to build the exception message.
     * @param cause      the error cause.
     */
    protected TemplatePackagingNonCheckedException(
        @NotNull final String messageKey, @NotNull final Object[] params, @NotNull final Throwable cause)
    {
        super(messageKey, params, cause);
    }

    /**
     * Retrieves the exceptions bundle.
     * @return such bundle name.
     */
    @NotNull
    @Override
    protected String retrieveExceptionsBundleName()
    {
        return "template-packaging-exceptions";
    }

    /**
     * Retrieves the exceptions system property.
     * @return such bundle name.
     */
    @NotNull
    @Override
    protected String retrieveExceptionsBundleProperty()
    {
        return "org.acmsl.queryj.templates.packaging.exceptions";
    }
}
