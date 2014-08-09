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
 * Filename: CheckResultSetGettersWorkForDefinedPropertiesHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Performs some checks in the ResultSet, for the expected
 *              Properties.
 *
 * Date: 2014/03/16
 * Time: 08:44
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.CustomResultWithInvalidNumberOfColumnsException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.exceptions.ReferencedResultNotFoundException;
import org.acmsl.queryj.api.exceptions.UnsupportedCustomResultPropertyTypeException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultRef;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.exceptions.ResultSetGettersValidationNotAvailableException;
import org.acmsl.queryj.customsql.exceptions.ResultSetMetadataOperationFailedException;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.SqlResultDAO;
import org.acmsl.queryj.metadata.TypeManager;
import org.acmsl.queryj.metadata.engines.JdbcTypeManager;
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
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Performs some checks in the {@link ResultSet}, for the expected {@link Property properties}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/16 08:44
 */
@ThreadSafe
public class CheckResultSetGettersWorkForDefinedPropertiesHandler
    extends AbstractQueryJCommandHandler
{
    /**
     * The key to store the validation outcomes per SQL.
     */
    protected static final String VALIDATION = "resultset-getters-validation";

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
        @NotNull final Sql<String> t_Sql = new RetrieveQueryHandler().retrieveCurrentSql(command);
        @Nullable final ResultRef t_ResultRef = t_Sql.getResultRef();

        if (t_ResultRef != null)
        {
            @Nullable final Result<String> t_Result = retrieveResult(t_ResultRef, retrieveCustomSqlProvider(command));

            if (t_Result == null)
            {
                throw new ReferencedResultNotFoundException(t_ResultRef, t_Sql);
            }
            else
            {
                @NotNull final ResultSet t_ResultSet = new ExecuteQueryHandler().retrieveCurrentResultSet(command);
                @NotNull final RetrieveResultPropertiesHandler t_Handler = new RetrieveResultPropertiesHandler();
                @NotNull final List<Property<String>> t_lProperties = t_Handler.retrieveCurrentProperties(command);
                @NotNull final MetadataManager t_MetadataManager = retrieveMetadataManager(command);

                try
                {
                    validateProperties(
                        t_ResultSet, t_lProperties, t_Sql, t_Result, new JdbcTypeManager(), t_Handler, t_MetadataManager);

                    setValidationOutcome(true, t_Sql, command);
                }
                catch (@NotNull final SQLException errorDealingWithResultSetMetadata)
                {
                    setValidationOutcome(false, t_Sql, command);

                    throw
                        new ResultSetMetadataOperationFailedException(
                            t_Sql, t_ResultRef, errorDealingWithResultSetMetadata);
                }
            }
        }
        else
        {
            setValidationOutcome(true, t_Sql, command);
        }
        return false;
    }

    /**
     * Retrieves the {@link Result} associated to given {@link ResultRef}.
     * @param resultRef the result reference.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return the result.
     */
    @Nullable
    protected Result<String> retrieveResult(
        @NotNull final ResultRef resultRef, @NotNull final CustomSqlProvider customSqlProvider)
    {
        @NotNull final SqlResultDAO t_ResultDAO = customSqlProvider.getSqlResultDAO();

        return t_ResultDAO.findByPrimaryKey(resultRef.getId());
    }

    /**
     * Validates the properties using given {@link ResultSet}.
     * @param resultSet the result set.
     * @param properties the properties.
     * @param sql the sql.
     * @param sqlResult the custom sql result.
     * @param typeManager the {@link TypeManager} instance.
     * @param handler a {@link RetrieveResultPropertiesHandler} for dealing with reflection.
     * @param metadataManager the {@link MetadataManager} instance.
     * @throws SQLException if the properties cannot be validated.
     * @throws QueryJBuildException if the operation fails for some other reason.
     */
    protected void validateProperties(
        @NotNull final ResultSet resultSet,
        @NotNull final List<Property<String>> properties,
        @NotNull final Sql<String> sql,
        @NotNull final Result<String> sqlResult,
        @NotNull final TypeManager typeManager,
        @NotNull final RetrieveResultPropertiesHandler handler,
        @NotNull final MetadataManager metadataManager)
        throws SQLException,
               QueryJBuildException
    {
        if  (resultSet.next())
        {
            @NotNull Method t_Method;

            for (@Nullable final Property<String> t_Property : properties)
            {
                if (t_Property != null)
                {
                    try
                    {
                        t_Method =
                            handler.retrieveMethod(
                                ResultSet.class,
                                handler.getGetterMethod(
                                    typeManager.getClass(t_Property.getType())),
                                new Class<?>[]
                                    {
                                        String.class
                                    });
                    }
                    catch  (@NotNull final NoSuchMethodException noSuchMethod)
                    {
                        throw
                            new UnsupportedCustomResultPropertyTypeException(
                                t_Property, sql, sqlResult, noSuchMethod);
                    }

                    handler.invokeResultSetGetter(
                        t_Method, resultSet, t_Property, sqlResult, sql, metadataManager);
                }
            }
        }
        else
        {
            @NotNull final ResultSetMetaData t_Metadata = resultSet.getMetaData();

            final int t_iColumnCount = t_Metadata.getColumnCount();

            if  (t_iColumnCount < properties.size())
            {
                throw
                    new CustomResultWithInvalidNumberOfColumnsException(
                        t_iColumnCount, properties.size());
            }

            @NotNull final List<Property<String>> t_lColumns = new ArrayList<>();

            for  (int t_iIndex = 1; t_iIndex <= t_iColumnCount; t_iIndex++)
            {
                t_lColumns.add(handler.createPropertyFrom(t_Metadata, t_iIndex));
            }
        }
    }

    /**
     * Retrieves the validation outcome for given {@link Sql}.
     * @param sql the SQL.
     * @param command the command.
     * @return the outcome of the validation.
     */
    public boolean getValidationOutcome(@NotNull final Sql<String> sql, @NotNull final QueryJCommand command)
    {
        final boolean result;

        @NotNull final QueryJCommandWrapper<Map<Sql<?>, Boolean>> wrapper =
            new QueryJCommandWrapper<>(command);

        @Nullable Map<Sql<?>, Boolean> outcomes = wrapper.getSetting(VALIDATION);

        if (outcomes == null)
        {
            outcomes = new HashMap<>();
            wrapper.setSetting(VALIDATION, outcomes);
        }

        if (outcomes.containsKey(sql))
        {
            result = outcomes.get(sql);
        }
        else
        {
            throw new ResultSetGettersValidationNotAvailableException(sql);
        }

        return result;
    }

    /**
     * Annotates the validation outcome for given {@link Sql}.
     * @param outcome the outcome.
     * @param sql the SQL.
     * @param command the command.
     */
    protected void setValidationOutcome(
        final boolean outcome, @NotNull final Sql<String> sql, @NotNull final QueryJCommand command)
    {
        @NotNull final QueryJCommandWrapper<Map<Sql<?>, Boolean>> wrapper =
            new QueryJCommandWrapper<>(command);

        @Nullable Map<Sql<?>, Boolean> outcomes = wrapper.getSetting(VALIDATION);

        if (outcomes == null)
        {
            outcomes = new HashMap<>();
            wrapper.setSetting(VALIDATION, outcomes);
        }

        outcomes.put(sql, outcome);
    }
}
