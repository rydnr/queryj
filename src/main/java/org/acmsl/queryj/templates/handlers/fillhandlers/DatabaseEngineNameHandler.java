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
 * Filename: DatabaseEngineNameHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Resolves the "engine_name" placeholder in templates.
 *
 * Date: 5/19/12
 * Time: 7:04 PM
 *
 */
package org.acmsl.queryj.templates.handlers.fillhandlers;

/*
 * Importing some project-specific classes.
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
 * Resolves the "engine_name" placeholder in templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/19
 */
@ThreadSafe
public class DatabaseEngineNameHandler
    extends AbstractTemplateContextFillHandler<TemplateContext, DecoratedString>
{
    /**
     * Creates a {@link DatabaseEngineNameHandler} for given {@link TemplateContext}.
     * @param context the template context.
     */
    public DatabaseEngineNameHandler(@NotNull final TemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "engine_name".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "engine_name";
    }

    /**
     * Retrieves the engine name.
     * @param context the {@link TemplateContext context}.
     * @return such information.
     */
    @NotNull
    protected DecoratedString getValue(@NotNull final TemplateContext context)
    {
        return new DecoratedString(getEngineName(context.getMetadataManager()));
    }

    /**
     * Retrieves the engine name.
     * @param metadataManager the {@link MetadataManager} instance.
     */
    @NotNull
    protected String getEngineName(@NotNull final MetadataManager metadataManager)
    {
        return metadataManager.getEngineName();
    }
}
