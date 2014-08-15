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
 * Filename: TemplateDefPerForeignKeyTemplateContext.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Enhances PerForeignKeyTemplateContext with the TemplateDef
 *              reference.
 *
 * Date: 2014/05/27
 * Time: 20:59
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.PerForeignKeyTemplateContext;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Enhances {@link PerForeignKeyTemplateContext} with the {@link TemplateDef} reference.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/27 20:59
 */
@ThreadSafe
@SuppressWarnings("unused")
public class TemplateDefPerForeignKeyTemplateContext
    extends PerForeignKeyTemplateContext
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -8375197881601002390L;

    /**
     * The template def.
     */
    private TemplateDef<String> m__TemplateDef;

    /**
     * Creates a new context.
     * @param templateDef the {@link TemplateDef}.
     * @param foreignKey the {@link ForeignKey}.
     * @param debug whether we're debugging or not.
     * @param command the {@link QueryJCommand}.
     */
    public TemplateDefPerForeignKeyTemplateContext(
        @NotNull final TemplateDef<String> templateDef,
        @NotNull final ForeignKey<String> foreignKey,
        final boolean debug,
        @NotNull final QueryJCommand command)
    {
        super(foreignKey, debug, command);
        immutableSetTemplateDef(templateDef);
    }

    /**
     * Specifies the template def.
     * @param templateDef the template def.
     */
    protected final void immutableSetTemplateDef(@NotNull final TemplateDef<String> templateDef)
    {
        m__TemplateDef = templateDef;
    }

    /**
     * Specifies the template def.
     * @param templateDef the template def.
     */
    @SuppressWarnings("unused")
    protected void setTemplateDef(@NotNull final TemplateDef<String> templateDef)
    {
        immutableSetTemplateDef(templateDef);
    }

    /**
     * Retrieves the template def.
     * @return such information.
     */
    @NotNull
    public TemplateDef<String> getTemplateDef()
    {
        return this.m__TemplateDef;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String toString()
    {
        return
            "{ \"templateDef\": " + this.m__TemplateDef
            + ", \"super\": " + super.toString()
            + ", \"class\": \"TemplateDefPerForeignKeyTemplateContext\""
            + ", \"package\": \"org.acmsl.queryj.templates.packaging\" }";
    }
}