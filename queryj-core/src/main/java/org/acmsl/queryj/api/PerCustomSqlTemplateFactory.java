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

 ******************************************************************************
 *
 * Filename: PerCustomSqlTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents entities able to create per-custom sql templates.
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.customsql.Sql;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Factory;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Represents entities able to create per-<i>custom sql</i> templates.
 * @param <T> the template type.
 * @param <C> the template context.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 2.0
 */
public interface PerCustomSqlTemplateFactory<T extends PerCustomSqlTemplate<C>, C extends PerCustomSqlTemplateContext>
    extends Factory
{
    /**
     * Creates a per-<i>custom sql</i> template.
     * @param sql the sql.
     * @param context the context.
     * @param command the {@link QueryJCommand}.
     * @return the template.
     */
    @NotNull
    public T createTemplate(
        @NotNull final Sql<String> sql,
        @NotNull final C context,
        @NotNull final QueryJCommand command);
}
