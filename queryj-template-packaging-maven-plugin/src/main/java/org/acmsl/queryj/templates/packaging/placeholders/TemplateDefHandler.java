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
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.metadata.DecoratedString;
import org.acmsl.queryj.templates.packaging.DefaultTemplatePackagingContext;
import org.acmsl.queryj.templates.packaging.TemplateDef;

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
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/09/02 07:20
 */
@ThreadSafe
public class TemplateDefHandler
    extends AbstractTemplateContextFillHandler<DefaultTemplatePackagingContext, TemplateDef<DecoratedString>>
{
    private static final long serialVersionUID = 6682126485145582404L;

    /**
     * Creates a new instance.
     * @param context the context.
     */
    public TemplateDefHandler(@NotNull final DefaultTemplatePackagingContext context)
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
    protected TemplateDef<DecoratedString> getValue(@NotNull final DefaultTemplatePackagingContext context)
    {
        return new DecoratedTemplateDefWrapper(context.getTemplateDef());
    }
}
