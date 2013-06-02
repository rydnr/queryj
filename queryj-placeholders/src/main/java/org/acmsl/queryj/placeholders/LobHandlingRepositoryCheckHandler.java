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
 * Filename: LobHandlingCheckHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Resolves "lob_handling_required" placeholders.
 *
 * Date: 7/19/12
 * Time: 10:50 AM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.metadata.MetadataManager;
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
 * Resolves "lob_handling_required" placeholders.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/07/19
 */
@ThreadSafe
@SuppressWarnings("unused")
public class LobHandlingRepositoryCheckHandler
    extends AbstractTemplateContextFillHandler<TemplateContext, Boolean>
{
    /**
     * Creates a {@link LobHandlingRepositoryCheckHandler} instance.
     * @param context the context.
     */
    public LobHandlingRepositoryCheckHandler(@NotNull final TemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "lob_handling_required".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "lob_handling_required";
    }

    /**
     * Retrieves the template value for this placeholder.
     * @return such value.
     */
    @NotNull
    @Override
    protected Boolean getValue(@NotNull final TemplateContext context)
    {
        return isLobHandlingRequired(context.getMetadataManager());
    }

    /**
     * Checks whether processing current table requires taking care of clobs/blobs.
     * @param metadataManager the {@link MetadataManager} instance.
     */
    protected boolean isLobHandlingRequired(@NotNull final MetadataManager metadataManager)
    {
        return metadataManager.requiresCustomClobHandling();
    }
}
