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
 * Filename: GlobalClassNameHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Resolves "class_name" placeholders, for global templates.
 *
 * Date: 2013/09/16
 * Time: 17:01
 *
 */
package org.acmsl.queryj.templates.packaging.placeholders;

/*
 * Importing QueryJ Placeholders classes.
 */
import org.acmsl.queryj.placeholders.AbstractDecoratedStringHandler;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.placeholders.Literals;
import org.acmsl.queryj.templates.packaging.GlobalTemplateContext;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Resolves "class_name" placeholders, for global templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/09/16 17:01
 */
@ThreadSafe
public class GlobalClassNameHandler
    extends AbstractDecoratedStringHandler<GlobalTemplateContext>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 5318336642738009644L;

    /**
     * Creates a new instance to resolve "class_name" placeholders in global templates.
     * @param context the {@link GlobalTemplateContext context}.
     */
    public GlobalClassNameHandler(@NotNull final GlobalTemplateContext context)
    {
        super(context);
    }

    /**
     * Resolves the actual value using given {@link GlobalTemplateContext context}.
     * @param context the {@link GlobalTemplateContext context}.
     * @return such value.
     */
    @NotNull
    @Override
    protected String resolveContextValue(@NotNull final GlobalTemplateContext context)
    {
        return context.getTemplateName();
    }

    /**
     * Retrieves "class_name".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return Literals.CLASS_NAME;
    }
}
