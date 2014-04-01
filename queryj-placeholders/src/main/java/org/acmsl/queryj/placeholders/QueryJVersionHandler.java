/*
                        QueryJ Core

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
 * Filename: QueryJVersionHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
 *
 * Date: 2014/03/30
 * Time: 18:41
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.QueryJTemplateContext;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.api.TemplateContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 *
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/30 18:41
 */
@ThreadSafe
public class QueryJVersionHandler<C extends TemplateContext>
    extends AbstractDecoratedStringHandler<C>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -7009997981120209866L;

    /**
     * Creates a new instance wrapping given context.
     * @param context the context.
     */
    public QueryJVersionHandler(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Retrieves "version".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "version";
    }


    /**
     * Retrieves the QueryJ version.
     * @return such value.
     */
    @Nullable
    @Override
    protected String resolveContextValue(@NotNull final C context)
    {
        return context.getVersion();
    }
}
