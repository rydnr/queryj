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
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base logic for all per-custom sql templates.
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.customsql.Sql;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Logicless container for all templates to be processed once per custom SQL.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractBasePerCustomSqlTemplate<C extends BasePerCustomSqlTemplateContext>
    extends  AbstractTemplate<C>
{
    /**
     * Builds a <code>AbstractBasePerCustomSqlTemplate</code> using
     * given context.
     * @param context the {@link BasePerCustomSqlTemplateContext} instance.
     */
    public AbstractBasePerCustomSqlTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    @NotNull
    protected String buildHeader()
    {
        return buildHeader(getTemplateName(), getTemplateContext());
    }

    /**
     * Builds the header for logging purposes.
     * @param templateName the template name.
     * @param context the {@link BasePerCustomSqlTemplateContext} instance.
     * @return such header.
     */
    @NotNull
    protected String buildHeader(@NotNull final String templateName, @NotNull final C context)
    {
        return buildHeader(templateName, context.getSql());
    }

    /**
     * Builds the header for logging purposes.
     * @param templateName the template name.
     * @param sql the sql.
     * @return such header.
     */
    @NotNull
    protected String buildHeader(
        final String templateName, @NotNull final Sql sql)
    {
        return "Generating " + templateName + " for " + sql.getName();
    }
}
