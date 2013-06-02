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
 * Filename: SerialVersionUIDHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Resolves "serialVersionUID" placeholders.
 *
 * Date: 6/20/12
 * Time: 4:15 PM
 *
 */
package org.acmsl.queryj.placeholders;

import org.acmsl.queryj.templates.TemplateContext;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Resolves "serialVersionUID" placeholders.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2012/06/20
 */
@ThreadSafe
@SuppressWarnings("unused")
public class SerialVersionUIDHandler
    extends AbstractTemplateContextFillHandler<TemplateContext,Long>
{
    public SerialVersionUIDHandler(@NotNull final TemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "serialVersionUID".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "serialVersionUID";
    }

    /**
     * Retrieves the template value for that placeholder.
     * @param context the {@link TemplateContext context}.
     * @return the dynamic value.
     */
    @Override
    public Long getValue(@NotNull final TemplateContext context)
    {
        return (long) context.getTemplateName().hashCode();
    }
}
