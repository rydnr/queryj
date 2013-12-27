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
 * Filename: AreTimestampsAllowedHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Is able to resolve "timestamps?" placeholders in
 *              templates.
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing project classes.
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
 * Is able to resolve "timestamps?" placeholders in templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2013/03/18
 */
@SuppressWarnings("unused")
@ThreadSafe
public class AreTimestampsAllowedHandler
    extends AbstractTemplateContextFillHandler<QueryJTemplateContext, Boolean>
{
    private static final long serialVersionUID = -3522961683638627590L;

    /**
     * Creates a {@link AreTimestampsAllowedHandler} to resolve placeholders
     * using given {@link org.acmsl.queryj.api.QueryJTemplateContext context}.
     * @param context the {@link org.acmsl.queryj.api.QueryJTemplateContext context}.
     */
    @SuppressWarnings("unused")
    public AreTimestampsAllowedHandler(@NotNull final QueryJTemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "generation_timestamps_enabled".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "generation_timestamps_enabled";
    }

    /**
     * Retrieves whether to use generation timestamps in templates.
     * @param context the {@link org.acmsl.queryj.api.QueryJTemplateContext}.
     * @return such information.
     */
    @NotNull
    @Override
    protected Boolean getValue(@NotNull final QueryJTemplateContext context)
    {
        return !context.getDisableGenerationTimestamps();
    }
}
