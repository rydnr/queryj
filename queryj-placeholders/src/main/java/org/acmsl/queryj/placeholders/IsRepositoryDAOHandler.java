/*
                        QueryJ Placeholders

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
 * Filename: IsRepositoryDAOHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Is able to resolve "is_repository_DAO" placeholders in
 *              templates.
 *
 * Date: 5/24/12
 * Time: 4:11 AM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing project classes.
 */

import org.acmsl.queryj.api.QueryJTemplateContext;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.SqlDAO;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to resolve "is_repository_DAO" placeholders in templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 3.0
 * Created: 2012/05/24
 */
@SuppressWarnings("unused")
@ThreadSafe
public class IsRepositoryDAOHandler
    extends AbstractTemplateContextFillHandler<QueryJTemplateContext, Boolean>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 5018265597468636823L;

    /**
     * Creates a {@link IsRepositoryDAOHandler} to resolve placeholders
     * using given {@link org.acmsl.queryj.api.QueryJTemplateContext context}.
     * @param context the {@link org.acmsl.queryj.api.QueryJTemplateContext context}.
     */
    @SuppressWarnings("unused")
    public IsRepositoryDAOHandler(@NotNull final QueryJTemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "is_repository_dao".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "is_repository_dao";
    }

    /**
     * Retrieves whether the {@link org.acmsl.queryj.api.QueryJTemplateContext context} is associated to a
     * repository-wide DAO.
     * @param context the {@link org.acmsl.queryj.api.QueryJTemplateContext}.
     * @return such value.
     */
    @NotNull
    @Override
    protected Boolean getValue(@NotNull final QueryJTemplateContext context)
    {
        return definesRepositoryScopedSql(context.getCustomSqlProvider());
    }

    /**
     * Checks whether given custom SQL provider defines any repository-scope
     * SQL or not.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return <code>true</code> in such case.
     */
    protected boolean definesRepositoryScopedSql(
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return definesRepositoryScopedSql(customSqlProvider.getSqlDAO());
    }

    /**
     * Checks whether given custom SQL provider defines any repository-scope
     * SQL or not.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @return <code>true</code> in such case.
     */
    protected boolean definesRepositoryScopedSql(@NotNull final SqlDAO sqlDAO)
    {
        return sqlDAO.containsRepositoryScopedSql();
    }
}
