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
 * Filename: DatabaseEngineVersionHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Resolves the "engine_version" placeholder in templates.
 *
 * Date: 5/19/12
 * Time: 7:04 PM
 *
 */
package org.acmsl.queryj.tools.templates.handlers.fillhandlers;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.TemplateContext;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Resolves the "engine_version" placeholder in templates.
 * @param <C> the {@link TemplateContext}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/19
 */
@SuppressWarnings("unused")
public class DatabaseEngineVersionHandler<C extends TemplateContext>
    extends AbstractTemplateContextFillHandler<C,String>
{
    /**
     * Creates a {@link DatabaseEngineNameHandler} for given {@link TemplateContext}.
     * @param context the context.
     */
    public DatabaseEngineVersionHandler(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Returns "engine_version".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "engine_version";
    }

    /**
     * Retrieves the engine name.
     * @return such information.
     */
    @NotNull
    @Override
    public String getValue(@NotNull final TemplateContext context)
    {
        return getEngineVersion(context.getMetadataManager());
    }

    /**
     * Retrieves the engine version.
     * @param metadataManager the {@link MetadataManager} instance.
     */
    @NotNull
    protected String getEngineVersion(@NotNull final MetadataManager metadataManager)
    {
        return metadataManager.getEngineVersion();
    }
}
