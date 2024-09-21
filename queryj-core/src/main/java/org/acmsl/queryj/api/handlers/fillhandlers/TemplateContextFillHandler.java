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
 * Filename: TemplateContextFillHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Allows FillHandlers to access the TemplateContext.
 *
 * Date: 5/23/12
 * Time: 7:59 PM
 *
 */
package org.acmsl.queryj.api.handlers.fillhandlers;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.api.TemplateContext;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Allows {@link FillHandler FillHandlers} to access the {@link org.acmsl.queryj.api.QueryJTemplateContext}.
 * @param <C> the {@link org.acmsl.queryj.api.QueryJTemplateContext}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 3.0
 * Created: 2012/05/23
 */
@ThreadSafe
public interface TemplateContextFillHandler<C extends TemplateContext,P>
    extends FillHandler<P>
{
    /**
     * Retrieves the {@link org.acmsl.queryj.api.QueryJTemplateContext context}.
     * @return such context.
     */
    @NotNull
    C getTemplateContext();
}
