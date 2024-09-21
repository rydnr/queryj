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
 * Filename: TemplateFillAdapterHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Adapts fill handlers to be used as regular handlers in a
 *              chain-of-responsibility process flow.
 *
 * Date: 6/3/12
 * Time: 11:58 AM
 *
 */
package org.acmsl.queryj.api.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.api.QueryJTemplateContext;
import org.acmsl.queryj.api.handlers.fillhandlers.TemplateContextFillHandler;


/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Adapts fill handlers to be used as regular handlers in a chain-of-responsibility
 * process flow.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/03
 */
@ThreadSafe
public class TemplateContextFillAdapterHandler
    <C extends QueryJTemplateContext, F extends TemplateContextFillHandler<C,P>, P>
    extends FillAdapterHandler<F, P>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 7171024612422933270L;

    /**
     * Creates a {@link TemplateContextFillAdapterHandler instance} to adapt given
     * {@link TemplateContextFillHandler fill handler}.
     * @param fillHandler the fill handler to adapt.
     */
    public TemplateContextFillAdapterHandler(@NotNull final F fillHandler)
    {
        super(fillHandler);
    }
}
