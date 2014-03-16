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
 * Filename: RetrieveResultSetColumnsHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Retrieves the columns (as a list of properties) from the
 *              current ResultSet.
 *
 * Date: 2014/03/16
 * Time: 11:11
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.exceptions.ColumnsNotAvailableForValidationException;
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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrieves the columns (as a list of {@link Property properties})
 * from the current {@link ResultSet}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/16 11:11
 */
@ThreadSafe
public class RetrieveResultSetColumnsHandler
    extends AbstractQueryJCommandHandler
{
    /**
     * The key to store the current columns.
     */
    protected static final String CURRENT_COLUMNS = "current-columns";

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
        @NotNull final ResultSet t_ResultSet = new ExecuteQueryHandler().retrieveCurrentResultSet(command);

        try
        {
            @NotNull final List<Property<String>> t_lColumns =
                retrieveColumns(t_ResultSet.getMetaData(), new RetrieveResultPropertiesHandler());

            setColumns(t_lColumns, command);
        }
        catch (@NotNull final SQLException sqlException)
        {
            throw
                new CannotAnalyzeResultSetForValidationException(
                    new RetrieveQueryHandler().retrieveCurrentSql(command));
        }

        return false;
    }

    /**
     * Annotates the columns into the command.
     * @param columns the {@link Property properties} to store.
     * @param command the {@link QueryJCommand command}.
     */
    protected void setColumns(@NotNull final List<Property<String>> columns, @NotNull final QueryJCommand command)
    {
        new QueryJCommandWrapper<List<Property<String>>>(command).setSetting(CURRENT_COLUMNS, columns);
    }

    /**
     * Retrieves the columns from the command.
     * @param command the {@link QueryJCommand command}.
     * @return the {@link Property columns}.
     */
    @NotNull
    protected List<Property<String>> retrieveCurrentColumns(@NotNull final QueryJCommand command)
    {
        @Nullable final List<Property<String>> result =
            new QueryJCommandWrapper<Property<String>>(command).getListSetting(CURRENT_COLUMNS);

        if (result == null)
        {
            @NotNull final Sql<String> t_Sql = new RetrieveQueryHandler().retrieveCurrentSql(command);

            throw new ColumnsNotAvailableForValidationException(t_Sql);
        }

        return result;
    }

    /**
     * Retrieves the columns from given {@link ResultSet}.
     * @param resultSetMetadata the {@link ResultSetMetaData}.
     * @return the list of {@link Property} columns.
     * @throws SQLException if any operation on the {@link ResultSetMetaData} fails.
     */
    protected List<Property<String>> retrieveColumns(
        @NotNull final ResultSetMetaData resultSetMetadata, @NotNull final RetrieveResultPropertiesHandler handler)
        throws SQLException
    {
        @NotNull final List<Property<String>> result = new ArrayList<>();

        final int t_iColumnCount = resultSetMetadata.getColumnCount();

        for  (int t_iIndex = 1; t_iIndex <= t_iColumnCount; t_iIndex++)
        {
            result.add(handler.createPropertyFrom(resultSetMetadata, t_iIndex));
        }

        return result;
    }
}
