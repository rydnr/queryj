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
 * Filename: InvalidTemplateTemplateException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Identifies errors when trying to generate Template-related
 *              classes using ST templates.
 *
 * Date: 2013/08/15
 * Time: 07:52
 *
 */
package org.acmsl.queryj.templates.packaging.exceptions;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.exceptions.InvalidTemplateException;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Identifies errors when trying to generate
 * {@link org.acmsl.queryj.api.Template}-related classes using ST templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/15 07:52
 */
@ThreadSafe
public class InvalidTemplatePackagingTemplateException
    extends InvalidTemplateException
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -6950491764603246762L;

    /**
     * Creates an instance.
     * @param group the group.
     * @param cause the cause.
     */
    public InvalidTemplatePackagingTemplateException(@NotNull final String group, @NotNull final Throwable cause)
    {
        super("invalid.template", new Object[] { group }, cause);
    }

    /**
     * Retrieves the exceptions bundle.
     * @return such bundle name.
     */
    @NotNull
    @Override
    protected String retrieveExceptionsBundleName()
    {
        return TemplatePackagingCheckedException.TEMPLATE_PACKAGING_EXCEPTIONS_BUNDLE_NAME;
    }

    /**
     * Retrieves the exceptions bundle property.
     * @return such property.
     */
    @NotNull
    @Override
    protected String retrieveExceptionsBundleProperty()
    {
        return TemplatePackagingCheckedException.TEMPLATE_PACKAGING_EXCEPTIONS_PROPERTY;
    }
}
