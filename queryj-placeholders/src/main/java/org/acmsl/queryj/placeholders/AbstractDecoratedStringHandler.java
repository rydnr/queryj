/*
                        QueryJ Placeholders

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
 * Filename: AbstractDecoratedStringHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Generic class to decorate simple String placeholders with
 *              "normalized", "capitalized", etc. versions.
 *
 * Date: 5/24/12
 * Time: 4:25 AM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.api.TemplateContext;

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.metadata.DecoratedString;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Generic class to decorate simple String placeholders with "normalized",
 * "capitalized", etc. versions.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 3.0
 * Created: 2012/05/24
 */
@ThreadSafe
public abstract class AbstractDecoratedStringHandler<C extends TemplateContext>
    extends AbstractTemplateContextFillHandler<C, DecoratedString>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -2303275887774081493L;

    /**
     * Creates {@link AbstractDecoratedStringHandler} to enhance String
     * placeholders with 'decorated' versions.
     * @param context the {@link org.acmsl.queryj.api.QueryJTemplateContext context}.
     */
    @SuppressWarnings("unused")
    public AbstractDecoratedStringHandler(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Retrieves the template value for this placeholder.
     *
     * @return such value.
     */
    @NotNull
    @Override
    protected final DecoratedString getValue(@NotNull final C context)
    {
        return new DecoratedString(resolveContextValue(context));
    }

    /**
     * Resolves the actual value using given {@link org.acmsl.queryj.api.QueryJTemplateContext context}.
     * @param context the {@link org.acmsl.queryj.api.QueryJTemplateContext context}.
     * @return such value.
     */
    @NotNull
    protected abstract String resolveContextValue(@NotNull final C context);
}
