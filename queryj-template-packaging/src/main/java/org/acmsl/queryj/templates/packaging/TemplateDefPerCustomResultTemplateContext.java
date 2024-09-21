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
 * Filename: TemplateDefPerCustomResultTemplateContext.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Enhances PerCustomResultTemplateContext with the TemplateDef
 *              reference.
 *
 * Date: 2014/05/27
 * Time: 20:44
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.PerCustomResultTemplateContext;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.Result;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.List;

/**
 * Enhances {@link PerCustomResultTemplateContext} with the {@link TemplateDef} reference.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/27 20:44
 */
@ThreadSafe
@SuppressWarnings("unused")
public class TemplateDefPerCustomResultTemplateContext
    extends PerCustomResultTemplateContext
    implements TemplateDefTemplateContext
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -6499667603360482807L;

    /**
     * The template def.
     */
    private TemplateDef<String> m__TemplateDef;

    /**
     * Creates a new context.
     * @param templateDef the {@link TemplateDef}.
     * @param result the {@link Result result}.
     * @param properties the list of {@link Property properties}.
     * @param debug whether we're debugging or not.
     * @param command the {@link QueryJCommand command}.
     */
    public TemplateDefPerCustomResultTemplateContext(
        @NotNull final TemplateDef<String> templateDef,
        @NotNull final Result<String> result,
        @NotNull final List<Property<String>> properties,
        final boolean debug,
        @NotNull final QueryJCommand command)
    {
        super(result, properties, debug, command);
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
    @Override
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
            + ", \"class\": \"TemplateDefPerCustomResultTemplateContext\""
            + ", \"package\": \"org.acmsl.queryj.templates.packaging\" }";
    }
}
