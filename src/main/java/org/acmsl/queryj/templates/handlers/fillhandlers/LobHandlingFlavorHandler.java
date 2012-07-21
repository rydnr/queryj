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
 * Filename: LobHandlingFlavorHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Resolves 'lob_handling_flavor' placeholders.
 *
 * Date: 7/19/12
 * Time: 2:17 PM
 *
 */
package org.acmsl.queryj.templates.handlers.fillhandlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.templates.TemplateContext;
import org.acmsl.queryj.tools.QueryJBuildException;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Resolves 'lob_handling_flavor' placeholders.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2012/07/19
 */
@ThreadSafe
public class LobHandlingFlavorHandler
    extends AbstractTemplateContextFillHandler<TemplateContext, DecoratedString>
{
    /**
     * Creates a new {@link LobHandlingFlavorHandler} with given context.
     * @param context the {@link TemplateContext} instance.
     */
    public LobHandlingFlavorHandler(@NotNull final TemplateContext context)
    {
        super(context);
    }

    /**
     * Retrieves 'lob_handling_flavor'.
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "lob_handling_flavor";
    }

    /**
     * Retrieves the template value for this placeholder.
     * @return such value.
     * @throws org.acmsl.queryj.tools.QueryJBuildException
     *          if there inconsistencies in the custom SQL
     *          model.
     */
    @Override
    protected DecoratedString getValue(@NotNull final TemplateContext context) throws QueryJBuildException
    {
        return new DecoratedString(getLobFlavor(context.getMetadataManager()));
    }

    /**
     * Retrieves the Lob-handling flavor.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return such flavor.
     */
    @NotNull
    protected String getLobFlavor(@NotNull final MetadataManager metadataManager)
    {
        return metadataManager.getEngineName();
    }
}
