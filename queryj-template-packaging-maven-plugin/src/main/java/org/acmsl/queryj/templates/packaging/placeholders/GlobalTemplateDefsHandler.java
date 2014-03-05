/*
                        QueryJ Template Packaging

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
 * Filename: GlobalTemplateDefsHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Resolves "templateDefs" placeholders, for global templates.
 *
 * Date: 2013/11/08
 * Time: 06:22
 *
 */
package org.acmsl.queryj.templates.packaging.placeholders;

/*
 * Importing QueryJ Template Packaging classes..
 */
import org.acmsl.queryj.placeholders.AbstractTemplateContextFillHandler;
import org.acmsl.queryj.metadata.DecoratedString;
import org.acmsl.queryj.templates.packaging.GlobalTemplateContext;
import org.acmsl.queryj.templates.packaging.TemplateDef;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Resolves "templateDefs" placeholders, for global templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/11/08 06:22
 */
@ThreadSafe
public class GlobalTemplateDefsHandler<C extends GlobalTemplateContext>
    extends AbstractTemplateContextFillHandler<C, List<TemplateDef<DecoratedString>>>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 6074422493902086923L;

    /**
     * Creates a new instance to resolve "templateDefs" placeholders in global templates.
     * @param context the {@link GlobalTemplateContext context}.
     */
    public GlobalTemplateDefsHandler(final C context)
    {
        super(context);
    }

    /**
     * Retrieves "templateDefs".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "templateDefs";
    }

    /**
     * Resolves "templateDefs" values.
     * @param context the {@link GlobalTemplateContext context}.
     * @return such value.
     */
    @Nullable
    @Override
    protected List<TemplateDef<DecoratedString>> getValue(@NotNull final C context)
    {
        @NotNull final List<TemplateDef<DecoratedString>> result;

        @NotNull final List<TemplateDef<String>> templateDefs = context.getTemplateDefs();

        result = new ArrayList<>(templateDefs.size());

        for (@NotNull final TemplateDef<String> templateDef : templateDefs)
        {
            result.add(new DecoratedTemplateDefWrapper(templateDef));
        }

        return result;
    }

}
