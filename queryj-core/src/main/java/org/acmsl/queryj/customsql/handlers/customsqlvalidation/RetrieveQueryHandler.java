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
 * Filename: RetrieveQueryHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Iterates through the list of Sql queries.
 *
 * Date: 2014/03/14
 * Time: 20:36
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Iterates through the list of {@link Sql queries}
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/14 20:36
 */
@ThreadSafe
public class RetrieveQueryHandler
    extends AbstractQueryJCommandHandler
{
    /**
     * The key to access the SQL list in the command.
     */
    public static final String SQL_LIST = "sql-list";

    /**
     * The key to access the index of the current sql.
     */
    public static final String CURRENT_SQL_INDEX = "index-of-current-sql";

    /**
     * The key to access the current sql.
     */
    public static final String CURRENT_SQL = "current-sql";

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand command)
        throws QueryJBuildException
    {
        final boolean result;

        final int index = retrieveCurrentSqlIndex(command);

        @NotNull final List<Sql<String>> t_lSql = retrieveSqlList(command);

        if (   (index > -1)
            && (index < t_lSql.size()))
        {
            setCurrentSql(t_lSql.get(index), command);
            result = false;
        }
        else
        {
            result = true;
        }

        return result;
    }

    /**
     * Annotates the current sql in the command.
     * @param sql the {@link Sql query}.
     * @param command the {@link QueryJCommand command}.
     */
    protected void setCurrentSql(@NotNull final Sql<String> sql, @NotNull final QueryJCommand command)
    {
        new QueryJCommandWrapper<Sql<String>>(command).setSetting(CURRENT_SQL, sql);
    }

    /**
     * Retrieves the current sql in the command.
     * @param command the {@link QueryJCommand command}.
     * @return such {@link Sql}.
     */
    public Sql<String> retrieveCurrentSql(@NotNull final QueryJCommand command)
    {
        return new QueryJCommandWrapper<Sql<String>>(command).getSetting(CURRENT_SQL);
    }

    /**
     * Retrieves the index of the current SQL, or {@code 0} if the iteration
     * has not started yet.
     * @param parameters the parameters.
     * @return the index of the current SQL.
     */
    protected int retrieveCurrentSqlIndex(final QueryJCommand parameters)
    {
        final int result;

        @Nullable final Integer aux =
            new QueryJCommandWrapper<Integer>(parameters).getSetting(CURRENT_SQL_INDEX);

        if (aux == null)
        {
            result = 0;
        }
        else
        {
            result = aux;
        }

        return result;
    }

    /**
     * Retrieves the list of queries.
     * @param parameters the parameters.
     * @return the list of {@link Sql queries}.
     */
    public List<Sql<String>> retrieveSqlList(final QueryJCommand parameters)
    {
        @NotNull final List<Sql<String>> result;

        @Nullable final List<Sql<String>> aux =
            new QueryJCommandWrapper<Sql<String>>(parameters).getListSetting(SQL_LIST);

        if (aux == null)
        {
            result = new ArrayList<>(0);
        }
        else
        {
            result = aux;
        }

        return result;
    }
}
