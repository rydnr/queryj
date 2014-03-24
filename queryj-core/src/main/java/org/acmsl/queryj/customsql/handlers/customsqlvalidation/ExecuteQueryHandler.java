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
 * Filename: ExecuteQueryHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Executes the current PreparedStatement.
 *
 * Date: 2014/03/15
 * Time: 11:33
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.InvalidCustomSqlException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.exceptions.ResultSetNotAvailableForValidationException;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Executes the current {@link PreparedStatement}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/15 11:33
 */
@ThreadSafe
public class ExecuteQueryHandler
    extends AbstractQueryJCommandHandler
{
    /**
     * The current {@link ResultSet}.
     */
    protected static final String CURRENT_RESULTSET = "current-resultset";

    /**
     * Asks the handler to process the command. The idea is that each
     * command handler decides if such command is suitable of being
     * processed, and if so perform the concrete actions the command
     * represents.
     *
     * @param command the command to process (or not).
     * @return <code>true</code> if the handler actually process the command,
     *         or maybe because it's not desirable to continue the chain.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand command)
        throws QueryJBuildException
    {
        @NotNull final PreparedStatement t_Statement =
            new SetupPreparedStatementHandler().retrieveCurrentPreparedStatement(command);

        @NotNull final Sql<String> t_Sql = new RetrieveQueryHandler().retrieveCurrentSql(command);

        try
        {
            @Nullable final ResultSet t_ResultSet = validateStatement(t_Sql, t_Statement);

            if (t_ResultSet != null)
            {
                setCurrentResultSet(t_ResultSet, command);
            }
        }
        catch (@NotNull final SQLException invalidSql)
        {
            throw new InvalidCustomSqlException(t_Sql, invalidSql);
        }

        return false;
    }

    /**
     * Annotates the current {@link ResultSet} in the command.
     * @param resultSet the result set.
     * @param command the command.
     */
    protected void setCurrentResultSet(@NotNull final ResultSet resultSet, final QueryJCommand command)
    {
        new QueryJCommandWrapper<ResultSet>(command).setSetting(CURRENT_RESULTSET, resultSet);
    }

    /**
     * Retrieves the current {@link ResultSet} from the command.
     * @param command the command.
     * @return the result set.
     */
    protected ResultSet retrieveCurrentResultSet(final QueryJCommand command)
    {
        @Nullable final ResultSet result =
            new QueryJCommandWrapper<ResultSet>(command).getSetting(CURRENT_RESULTSET);

        if (result == null)
        {
            @NotNull final Sql<String> t_Sql = new RetrieveQueryHandler().retrieveCurrentSql(command);

            throw new ResultSetNotAvailableForValidationException(t_Sql);
        }

        return result;
    }

    /**
     * Validates given statement.
     * @param sql the {@link Sql}.
     * @param preparedStatement the {@link PreparedStatement}.
     * @return the {@link ResultSet}.
     */
    @Nullable
    protected ResultSet validateStatement(
        @NotNull final Sql<String> sql,
        @NotNull final PreparedStatement preparedStatement)
        throws SQLException,
              QueryJBuildException
    {
        @Nullable final ResultSet result;

        if  (   (Sql.INSERT.equals(sql.getType()))
             || (Sql.DELETE.equals(sql.getType())))
        {
            preparedStatement.executeUpdate();

            result = null;
        }
        else
        {
            result = preparedStatement.executeQuery();
        }

        return result;
    }
}
