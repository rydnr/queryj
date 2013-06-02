//;-*- mode: java -*-
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

 *****************************************************************************
 *
 * Filename: CustomValueObjectFactoryTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create CustomValueObjectFactory implementation
 *              for each custom query requiring so.
 *
 * $Id$
 */
package org.acmsl.queryj.templates.valueobject;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.templates.AbstractBasePerCustomResultTemplate;
import org.acmsl.queryj.templates.BasePerCustomResultTemplateContext;
import org.acmsl.queryj.templates.FillTemplateChain;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to create CustomValueObjectFactory implementations for each
 * custom query requiring so.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CustomValueObjectFactoryTemplate
    extends AbstractBasePerCustomResultTemplate<BasePerCustomResultTemplateContext>
{
    private static final long serialVersionUID = -1424240157388443640L;

    /**
     * Builds a <code>CustomValueObjectTemplate</code> using
     * information.
     * @param context the {@link BasePerCustomResultTemplateContext context}.
     */
    public CustomValueObjectFactoryTemplate(@NotNull final BasePerCustomResultTemplateContext context)
    {
        super(context);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Nullable
    @Override
    public StringTemplateGroup retrieveGroup()
    {
        return
            retrieveGroup("/org/acmsl/queryj/vo/" + getTemplateName() + ".stg");
    }

    /**
     * Returns "CustomValueObjectFactory".
     * @return such information.
     */
    @Override
    @NotNull
    public String getTemplateName()
    {
        return "CustomValueObjectFactory";
    }

    /**
     * Builds the correct chain.
     * @param context the context.
     * @param relevantOnly whether to include relevant-only placeholders.
     * @return the specific {@link FillTemplateChain}.
     */
    @NotNull
    @Override
    public FillTemplateChain<BasePerCustomResultTemplateContext> buildFillTemplateChain(
        @NotNull final BasePerCustomResultTemplateContext context, final boolean relevantOnly)
    {
        // TODO
        return null;
    }
}
