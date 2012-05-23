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
 * Filename: SubPackageNameHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 5/23/12
 * Time: 8:29 PM
 *
 */
package org.acmsl.queryj.tools.templates.handlers.fillhandlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.templates.TemplateContext;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
* Is able to resolve "sub_package_name" placeholders
* @author <a href="mailto:chous@acm-sl.org">chous</a>
* @since 2012/05/23
*/
@SuppressWarnings("unused")
public class SubPackageNameHandler
    extends AbstractTemplateContextFillHandler<TemplateContext,String>
{
    /**
     * Creates a handler able to resolve "sub_package_name" using given {@link TemplateContext}.
     * @param context the {@link TemplateContext context}.
     */
    @SuppressWarnings("unused")
    public SubPackageNameHandler(@NotNull final TemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "sub_package_name".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "sub_package_name";
    }

    /**
     * Retrieves the template value for this placeholder.
     * @return such value.
     */
    @NotNull
    @Override
    protected String getValue(@NotNull final TemplateContext context)
    {
        return context.getPackageName();
    }
}
