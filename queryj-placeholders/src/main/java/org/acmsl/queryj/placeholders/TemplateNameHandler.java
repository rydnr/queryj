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
 * Filename: TemplateNameHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Handler to resolve "templateName" placeholder in templates.
 *
 * Date: 2013/08/06
 * Time: 6:54
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.api.TemplateContext;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Handler to resolve "templateName" placeholder in templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/08/06
 */
@ThreadSafe
public class TemplateNameHandler
    extends AbstractTemplateContextFillHandler<TemplateContext, DecoratedString>
{
    private static final long serialVersionUID = -6175019064662147096L;

    /**
     * Creates a new handler associated to given {@link TemplateContext}.
     * @param context the template.
     */
    protected TemplateNameHandler(@NotNull final TemplateContext context)
    {
        super(context);
    }

    /**
     * Retrieves the template name.
     * @return such value.
     */
    @Nullable
    @Override
    protected DecoratedString getValue(@NotNull final TemplateContext context)
    {
        return new DecoratedString(context.getTemplateName());
    }

    /**
     * Retrieves the placeholder "templateName".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "templateName";
    }
}
