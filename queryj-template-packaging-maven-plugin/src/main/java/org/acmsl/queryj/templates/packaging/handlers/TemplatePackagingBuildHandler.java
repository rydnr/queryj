/*
                        queryj

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
 * Filename: TemplatePackagingBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base build handler for Template Packaging templates.
 *
 * Date: 2013/08/17
 * Time: 11:00
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.templates.packaging.TemplatePackagingSettings;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Base build handler for Template Packaging templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/17 11:00
 */
@ThreadSafe
public abstract class TemplatePackagingBuildHandler
    implements QueryJCommandHandler<QueryJCommand>,
               TemplatePackagingSettings
{
    /**
     * Retrieves the header.
     * @param command the command.
     * @return the header.
     */
    @NotNull
    public String retrieveHeader(@SuppressWarnings("unused") @NotNull final QueryJCommand command)
    {
        return TEMPLATE_PACKAGING_HEADER;
    }

    /**
     * Retrieves whether the generated content will be cached.
     * @param command the command.
     * @return always {@code false}.
     */
    public boolean retrieveCaching(@SuppressWarnings("unused") @NotNull final QueryJCommand command)
    {
        return false;
    }

    /**
     * Retrieves the number of threads to use in the generation process.
     * @param command the command.
     * @return the number of available processors.
     */
    public int retrieveThreadCount(@SuppressWarnings("unused") @NotNull final QueryJCommand command)
    {
        return Runtime.getRuntime().availableProcessors();
    }
}
