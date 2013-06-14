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
 * Filename: FkStatementSetterTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create FkStatementSetter implementation for each
 *              table in the persistence model.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.api.AbstractBasePerForeignKeyTemplate;
import org.acmsl.queryj.api.PerForeignKeyTemplateContext;
import org.acmsl.queryj.api.FillTemplateChain;

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
 * Generates <code>ForeignKeyStatementSetter</code> sources for a concrete
 * entity in the persistence model.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class FkStatementSetterTemplate
    extends AbstractBasePerForeignKeyTemplate<PerForeignKeyTemplateContext>
{
    private static final long serialVersionUID = 7268173474744893352L;

    /**
     * Builds a <code>FkStatementSetterTemplate</code> using given
     * information.
     * @param context the {@link org.acmsl.queryj.api.PerForeignKeyTemplateContext} instance.
     */
    public FkStatementSetterTemplate(@NotNull final PerForeignKeyTemplateContext context)
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
            retrieveGroup(
                "/org/acmsl/queryj/dao/" + getTemplateName() + ".stg");
    }

    /**
     * Returns "ForeignKeyStatementSetter".
     * @return such information.
     */
    @NotNull
    public String getTemplateName()
    {
        return "FkStatementSetter";
    }

    /**
     * Builds the correct chain.
     * @param context the context.
     * @param relevantOnly whether to include relevant-only placeholders.
     * @return the specific {@link org.acmsl.queryj.api.FillTemplateChain}.
     */
    @NotNull
    @Override
    public FillTemplateChain<PerForeignKeyTemplateContext> buildFillTemplateChain(
        @NotNull final PerForeignKeyTemplateContext context, final boolean relevantOnly)
    {
        // TODO
        return null;
    }
}
