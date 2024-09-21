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
 * Filename: JndiLocationFillHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Resolves "jndi_location" placeholders.
 *
 * Date: 6/25/12
 * Time: 5:05 AM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.api.QueryJTemplateContext;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Resolves "jndi_location" placeholders.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2012/06/25
 */
@ThreadSafe
@SuppressWarnings("unused")
public class JndiLocationFillHandler
    extends AbstractDecoratedStringHandler<QueryJTemplateContext>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -4111639313234855152L;

    /**
     * Creates a {@link JndiLocationFillHandler} with given context.
     * @param context the {@link org.acmsl.queryj.api.QueryJTemplateContext context}.
     */
    public JndiLocationFillHandler(@NotNull final QueryJTemplateContext context)
    {
        super(context);
    }

    /**
     * Resolves the actual value using given {@link org.acmsl.queryj.api.QueryJTemplateContext context}.
     * @param context the {@link org.acmsl.queryj.api.QueryJTemplateContext context}.
     * @return such value.
     */
    @NotNull
    @Override
    protected String resolveContextValue(@NotNull final QueryJTemplateContext context)
    {
        return context.getJndiLocation();
    }

    /**
     * Retrieves the placeholder.
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "jndi_location";
    }
}
