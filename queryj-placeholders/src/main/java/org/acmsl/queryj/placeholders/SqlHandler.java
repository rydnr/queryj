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
 * Filename: SqlHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 5/23/12
 * Time: 9:28 PM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.templates.BasePerCustomSqlTemplateContext;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to resolve "sql" placeholders using {@link BasePerCustomSqlTemplateContext contexts}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/23
 */
@SuppressWarnings("unused")
@ThreadSafe
public class SqlHandler
    extends AbstractTemplateContextFillHandler<BasePerCustomSqlTemplateContext,Sql>
{
    /**
     * Creates a {@link SqlHandler} using given {@link BasePerCustomSqlTemplateContext context}.
     * @param context the {@link BasePerCustomSqlTemplateContext context}.
     */
    @SuppressWarnings("unused")
    public SqlHandler(@NotNull final BasePerCustomSqlTemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "sql".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "sql";
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected Sql getValue(@NotNull final BasePerCustomSqlTemplateContext context)
    {
        return context.getSql();
    }
}
