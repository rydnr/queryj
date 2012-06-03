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
package org.acmsl.queryj.tools.templates.handlers.fillhandlers;

/*
 * Importing project classes.
 */

import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Sql;
import org.acmsl.queryj.tools.templates.TemplateContext;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Is able to resolve "is_repository_DAO" placeholders in templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/24
 */
@SuppressWarnings("unused")
public class IsRepositoryDAOHandler
    extends AbstractTemplateContextFillHandler<TemplateContext, Boolean>
{
    /**
     * Creates a {@link IsRepositoryDAOHandler} to resolve placeholders
     * using given {@link TemplateContext context}.
     * @param context the {@link TemplateContext context}.
     */
    @SuppressWarnings("unused")
    public IsRepositoryDAOHandler(@NotNull final TemplateContext context)
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
     * Retrieves whether the {@link TemplateContext context} is associated to a
     * repository-wide DAO.
     * @return such value.
     */
    @NotNull
    @Override
    protected Boolean getValue(@NotNull final TemplateContext context)
    {
        return definesRepositoryScopedSql(context.getCustomSqlProvider());
    }

    /**
     * Checks whether given custom SQL provider defines any repository-scope
     * SQL or not.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @return <code>true</code> in such case.
     */
    protected boolean definesRepositoryScopedSql(
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        boolean result = false;

        @Nullable Collection t_cContents = customSqlProvider.getCollection();

        if  (t_cContents != null)
        {
            @Nullable Sql t_Sql;
            String t_strDao;

            for (Object t_Content : t_cContents)
            {
                if  (t_Content instanceof Sql)
                {
                    t_Sql = (Sql) t_Content;

                    t_strDao = t_Sql.getDao();

                    if  (t_strDao == null)
                    {
                        result = (t_Sql.getRepositoryScope() != null);

                        if  (result)
                        {
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }

}