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
 * Filename: TemplateDefHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Handler to resolve "templateDef" placeholder in templates.
 *
 * Date: 2013/09/02
 * Time: 07:20
 *
 */
package org.acmsl.queryj.templates.packaging.placeholders;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.TemplateContext;
import org.acmsl.queryj.metadata.DecoratedString;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.PerTemplateDefTemplateContext;
import org.acmsl.queryj.templates.packaging.TemplateDef;
import org.acmsl.queryj.templates.packaging.TemplateDefTemplateContext;

/*
 * Importing QueryJ-Placeholder classes.
 */
import org.acmsl.queryj.placeholders.AbstractTemplateContextFillHandler;
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
 * Handler to resolve "templateDef" placeholder in templates.
 * @param <C> the context type.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/09/02 07:20
 */
@ThreadSafe
public class TemplateDefHandler<C extends TemplateContext>
    extends AbstractTemplateContextFillHandler<C, TemplateDef<DecoratedString>>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 6682126485145582404L;

    /**
     * Creates a new instance.
     * @param context the context.
     */
    public TemplateDefHandler(@NotNull final C context)
    {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "templateDef";
    }

    /**
     * Retrieves the {@link TemplateDef}, to be available in templates.
     * @return such instance.
     */
    @Nullable
    @Override
    protected TemplateDef<DecoratedString> getValue(@NotNull final C context)
    {
        @Nullable final TemplateDef<DecoratedString> result;

        if (context instanceof TemplateDefTemplateContext)
        {
            result = new DecoratedTemplateDefWrapper(((TemplateDefTemplateContext) context).getTemplateDef());
        }
        else if (context instanceof PerTemplateDefTemplateContext)
        {
            result = new DecoratedTemplateDefWrapper(((PerTemplateDefTemplateContext) context).getTemplateDef());
        }
        else
        {
            result = null;
        }

        return result;
    }
}
