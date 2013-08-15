/*
                        queryj

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
 * Filename: AbstractTemplatePackagingTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Abstract template for Template Packaging.
 *
 * Date: 2013/08/15
 * Time: 08:34
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.api.AbstractTemplate;
import org.acmsl.queryj.api.DefaultThemeConstants;
import org.acmsl.queryj.api.QueryJTemplateContext;
import org.acmsl.queryj.api.STTemplate;
import org.acmsl.queryj.api.exceptions.InvalidTemplateException;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;
import org.stringtemplate.v4.STGroup;

import java.io.Serializable;

/**
 * Abstract template for Template Packaging.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/15 08/34
 */
@ThreadSafe
public abstract class AbstractTemplatePackagingTemplate<C extends TemplatePackagingContext>
    extends AbstractTemplate<C>
    implements STTemplate<C>,
               TemplatePackagingSettings
{
    /**
     * Creates a new instance.
     * @param context the {@link TemplatePackagingContext context}.
     */
    protected AbstractTemplatePackagingTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Retrieves the header.
     *
     * @param context the template context.
     * @return such information.
     */
    @Nullable
    @Override
    protected String getHeader(@NotNull final C context)
    {
        return TEMPLATE_PACKAGING_HEADER;
    }

    /**
     * Generates the source code.
     *
     * @param context      the {@link org.acmsl.queryj.api.QueryJTemplateContext} instance.
     * @param relevantOnly whether to include only relevant placeholders.
     * @return such output.
     * @throws org.acmsl.queryj.api.exceptions.InvalidTemplateException
     *          if the template cannot be generated.
     */
    @Nullable
    @Override
    protected String generate(@NotNull final C context, final boolean relevantOnly) throws InvalidTemplateException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the source code generated by this template.
     *
     * @param header       the header.
     * @param context      the context.
     * @param relevantOnly whether to include only relevant placeholders.
     * @return such code.
     * @throws org.acmsl.queryj.api.exceptions.InvalidTemplateException
     *          if the template cannot be processed.
     */
    @Nullable
    @Override
    protected String generateOutput(@Nullable final String header, @NotNull final C context, final boolean relevantOnly)
        throws InvalidTemplateException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
