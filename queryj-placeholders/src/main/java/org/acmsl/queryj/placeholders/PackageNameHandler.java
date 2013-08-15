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
 * Filename: PackageNameHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Is able to resolve "package" placeholders.
 *
 * Date: 2013/05/05
 * Time: 16:57
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.api.QueryJTemplateContext;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to resolve "package" placeholders.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2013/05/05
 */
@SuppressWarnings("unused")
@ThreadSafe
public class PackageNameHandler
    extends AbstractTemplateContextFillHandler<QueryJTemplateContext, DecoratedString>
{
    private static final long serialVersionUID = 6902259512128669602L;

    /**
     * Creates a handler to resolve "project_package" placeholders.
     * @param context the {@link org.acmsl.queryj.api.QueryJTemplateContext context}.
     */
    @SuppressWarnings("unused")
    public PackageNameHandler(@NotNull final QueryJTemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "project_package".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "package";
    }

    /**
     * Retrieves the template value for this placeholder.
     * @return such value.
     */
    @NotNull
    @Override
    protected DecoratedString getValue(@NotNull final QueryJTemplateContext context)
    {
        return new DecoratedString(context.getPackageName());
    }
}
