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
 * Filename: HeaderHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 5/23/12
 * Time: 8:15 PM
 *
 */
package org.acmsl.queryj.templates.handlers.fillhandlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.templates.NonRelevantFillHandler;
import org.acmsl.queryj.templates.TemplateContext;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Fills "header" placeholder in templates.
 * @author <a href="mailto:jose.sanleandro@ventura24.es">Jose San Leandro</a>
 * @since 2012/05/23
 */
@SuppressWarnings("unused")
@ThreadSafe
public class HeaderHandler
    extends AbstractTemplateContextFillHandler<TemplateContext, DecoratedString>
    implements NonRelevantFillHandler
{
    /**
     * Creates a handler able to resolve "header" placeholders.
     * @param context the {@link TemplateContext context}.
     */
    @SuppressWarnings("unused")
    public HeaderHandler(@NotNull final TemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "header".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "header";
    }

    /**
     * Retrieves the template value for this placeholder.
     * @param context the {@link TemplateContext context}.
     * @return the dynamic value.
     */
    @Override
    @NotNull
    protected DecoratedString getValue(@NotNull final TemplateContext context)
    {
        return new DecoratedString(context.getHeader());
    }
}
