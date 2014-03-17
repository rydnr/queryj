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
 * Filename: SetupPreparedStatementHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Creates the PreparedStatement for the current Sql and
 *              "publishes" it into the command.
 *
 * Date: 2014/03/15
 * Time: 10:02
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

/*
 * Importing ACM-SL Java Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.InvalidCustomSqlException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.exceptions.PreparedStatementNotAvailableForValidationException;
import org.acmsl.queryj.customsql.handlers.CustomSqlValidationHandler;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Creates the {@link PreparedStatement} for the current {@link Sql} and "publishes" it
 * into the command.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/15 10:02
 */
@ThreadSafe
public class SetupPreparedStatementHandler
    extends AbstractQueryJCommandHandler
{
    /**
     * The current prepared statement.
     */
    @NotNull
    public static String CURRENT_PREPARED_STATEMENT = "current-prepared-statement";

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
        @NotNull final Sql<String> sql = new RetrieveQueryHandler().retrieveCurrentSql(command);
        @NotNull final Connection connection = retrieveConnection(command);

        try
        {
            @Nullable final PreparedStatement t_Statement = setupStatement(sql, connection);

            if (t_Statement != null)
            {
                setCurrentPreparedStatement(t_Statement, command);
            }
        }
        catch (@NotNull final SQLException invalidSql)
        {
            throw new InvalidCustomSqlException(sql, invalidSql);
        }

        return false;
    }

    /**
     * Annotates the prepared statement into the command.
     * @param statement the statement.
     * @param command the command.
     */
    protected void setCurrentPreparedStatement(final PreparedStatement statement, final QueryJCommand command)
    {
        new QueryJCommandWrapper<PreparedStatement>(command).setSetting(CURRENT_PREPARED_STATEMENT, statement);
    }

    /**
     * Retrieves the prepared statement from the command.
     * @param command the command.
     * @return the statement.
     */
    @NotNull
    protected PreparedStatement retrieveCurrentPreparedStatement(final QueryJCommand command)
    {
        @Nullable final PreparedStatement result =
            new QueryJCommandWrapper<PreparedStatement>(command).getSetting(CURRENT_PREPARED_STATEMENT);

        if (result == null)
        {
            throw
                new PreparedStatementNotAvailableForValidationException(
                    new RetrieveQueryHandler().retrieveCurrentSql(command));
        }

        return result;
    }

    /**
     * Validates given sql element.
     * @param sql such element.
     * @param connection the connection.
     * @throws SQLException if the sql is not valid.
     */
    @Nullable
    public PreparedStatement setupStatement(
        @NotNull final Sql<String> sql,
        @NotNull final Connection connection)
        throws  SQLException
    {
        @Nullable final PreparedStatement result;

        @Nullable final String t_strValue = sql.getValue();

        if (t_strValue != null)
        {
            @NotNull final String t_strSql = t_strValue.trim();

            result = connection.prepareStatement(t_strSql);
        }
        else
        {
            @Nullable final Log t_Log = UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

            if (t_Log != null)
            {
                t_Log.warn("Non-select/empty query with validate=\"true\": " + sql.getId());
            }
            result = null;
        }

        return result;
    }
}
