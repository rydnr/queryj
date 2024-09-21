/*
                        QueryJ Core

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
 * Filename: BasePerTableTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents entities able to create per-table templates.
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.metadata.vo.Row;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * Represents entities able to create per-table templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @param <T> the template type.
 * @param <C> the template context.
 */
public interface PerTableTemplateFactory<T extends PerTableTemplate<C>, C extends PerTableTemplateContext>
    extends  TemplateFactory
{
    /**
     * Generates a <i>per-table</i> template.
     * @param tableName the table name.
     * @param staticContents the table's static contents (optional).
     * @param context the context.
     * @param command the command.
     * @return a template.
     */
    @Nullable
    public T createTemplate(
        @NotNull final String tableName,
        @NotNull final List<Row<String>> staticContents,
        @NotNull final C context,
        @NotNull final QueryJCommand command);
}
