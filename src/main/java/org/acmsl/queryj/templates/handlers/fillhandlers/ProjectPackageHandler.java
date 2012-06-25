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
 * Filename: ProjectPackageHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Is able to resolve "project_package" placeholders.
 *
 * Date: 5/23/12
 * Time: 8:23 PM
 *
 */
package org.acmsl.queryj.templates.handlers.fillhandlers;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.templates.TemplateContext;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Is able to resolve "project_package" placeholders.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/23
 */
@SuppressWarnings("unused")
public class ProjectPackageHandler
    extends AbstractTemplateContextFillHandler<TemplateContext, DecoratedString>
{
    /**
     * Creates a handler to resolve "project_package" placeholders.
     * @param context the {@link TemplateContext context}.
     */
    @SuppressWarnings("unused")
    public ProjectPackageHandler(@NotNull final TemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "project_package".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "project_package";
    }

    /**
     * Retrieves the template value for this placeholder.
     * @return such value.
     */
    @NotNull
    @Override
    protected DecoratedString getValue(@NotNull final TemplateContext context)
    {
        return new DecoratedString(context.getBasePackageName());
    }

}
