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
 * Filename: CustomResultHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: FillHandler to resolve "result" placeholders, for
 *              per-custom-result templates.
 *
 * Date: 7/5/12
 * Time: 9:04 PM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.metadata.CachingResultDecorator;
import org.acmsl.queryj.metadata.ResultDecorator;
import org.acmsl.queryj.api.PerCustomResultTemplateContext;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * FillHandler to resolve "result" placeholders, for per-custom-result templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2012/07/05
 */
@ThreadSafe
@SuppressWarnings("unused")
public class CustomResultHandler
    extends AbstractTemplateContextFillHandler<PerCustomResultTemplateContext, ResultDecorator<?>>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -1257476640731147692L;

    /**
     * Creates a new instance.
     * @param context the {@link PerCustomResultTemplateContext context}.
     */
    public CustomResultHandler(@NotNull final PerCustomResultTemplateContext context)
    {
        super(context);
    }

    /**
     * Retrieves the template value for this placeholder.
     *
     * @return such value.
     */
    @Override
    protected ResultDecorator<?> getValue(@NotNull final PerCustomResultTemplateContext context)
    {
        return
            new CachingResultDecorator(
                context.getResult(),
                context.getCustomSqlProvider(),
                context.getMetadataManager(),
                context.getDecoratorFactory());
    }

    /**
     * Retrieves "result".
     *
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return Literals.RESULT;
    }
}
