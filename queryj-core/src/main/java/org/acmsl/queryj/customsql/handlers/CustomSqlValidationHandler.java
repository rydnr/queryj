//;-*- mode: java -*-
/*
                        QueryJ-Core

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
 * Filename: CustomSqlValidationHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Validates any custom sql queries.
 *
 */
package org.acmsl.queryj.customsql.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.commons.utils.StringUtils;
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.exceptions.CustomResultWithInvalidNumberOfColumnsException;
import org.acmsl.queryj.api.exceptions.CustomResultWithNoPropertiesException;
import org.acmsl.queryj.api.exceptions.InvalidCustomSqlException;
import org.acmsl.queryj.api.exceptions.InvalidCustomSqlParameterException;
import org.acmsl.queryj.api.exceptions.NoTableMatchingCustomResultException;
import org.acmsl.queryj.api.exceptions.NoValidationValueForCustomSqlDateParameterException;
import org.acmsl.queryj.api.exceptions.UnsupportedCustomResultPropertyTypeException;
import org.acmsl.queryj.api.exceptions.UnsupportedCustomSqlParameterTypeException;
import org.acmsl.queryj.customsql.*;
import org.acmsl.queryj.metadata.SqlDAO;
import org.acmsl.queryj.metadata.SqlParameterDAO;
import org.acmsl.queryj.metadata.SqlPropertyDAO;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.metadata.TypeManager;
import org.acmsl.queryj.metadata.engines.JdbcTypeManager;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.MetadataTypeManager;
import org.acmsl.queryj.metadata.vo.Attribute;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.utils.ConversionUtils;

/*
 * Importing some JDK classes.
 */
import java.lang.reflect.Method;
import java.lang.NoSuchMethodException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*
 * Importing some Apache Commons Logging classes.
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

/**
 * Validates any custom sql queries.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CustomSqlValidationHandler
    extends  AbstractQueryJCommandHandler
{
    /**
     * A cached class array.
     */
    @SuppressWarnings("unchecked")
    private static final Class<String>[] CLASS_ARRAY_OF_ONE_STRING =
        (Class<String>[]) new Class<?>[] { String.class };
    protected static final String COULD_NOT_BIND_PARAMETER_VIA = "Could not bind parameter via ";
    protected static final String PREPARED_STATEMENT_SET = "PreparedStatement.set";
    protected static final String VALIDATION_FAILED_FOR = "Validation failed for ";
    protected static final String COULD_NOT_RETRIEVE_RESULT_VIA = "Could not retrieve result via ";
    protected static final String RESULT_SET = "ResultSet.";

    /**
     * The date format.
     */
    public final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/DD/yyyy");
    public final DateFormat DATE_FORMAT_EN = new SimpleDateFormat("yyyy/DD/MM/DD");

    /**
     * Creates a <code>CustomSqlValidationHandler</code> instance.
     */
    public CustomSqlValidationHandler() {}

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> in case the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand parameters)
      throws  QueryJBuildException
    {
        boolean result = false;

        @Nullable final MetadataManager t_MetadataManager =
            retrieveMetadataManager(parameters);

        @NotNull final CustomSqlProvider t_CustomSqlProvider =
            retrieveCustomSqlProvider(parameters);

        if (t_MetadataManager == null)
        {
            result = true;
        }
        else if  (!retrieveDisableCustomSqlValidation(parameters))
        {
            validate(
                t_CustomSqlProvider,
                t_CustomSqlProvider.getSqlDAO(),
                retrieveConnection(parameters),
                t_MetadataManager);
        }

        return result;
    }

    /**
     * Validates the SQL queries.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @param connection the connection.
     * @param metadataManager the metadata manager.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    public void validate(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final SqlDAO sqlDAO,
        @NotNull final Connection connection,
        @NotNull final MetadataManager metadataManager)
      throws  QueryJBuildException
    {
        for (@Nullable final Sql<String> t_Sql : sqlDAO.findAll())
        {
            if (   (t_Sql != null)
                && (t_Sql.isValidate()))
            {
                validate(
                    t_Sql,
                    customSqlProvider,
                    connection,
                    metadataManager,
                    new JdbcTypeManager());
            }
        }
    }

    /**
     * Validates given sql element.
     * @param sql such element.
     * @param customSqlProvider the custom sql provider.
     * @param connection the connection.
     * @param metadataManager the metadata manager.
     * @param typeManager the type manager.
     * @throws QueryJBuildException if the sql is not valid.
     */
    public void validate(
        @NotNull final Sql<String> sql,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final Connection connection,
        @NotNull final MetadataManager metadataManager,
        @NotNull final TypeManager typeManager)
      throws  QueryJBuildException
    {
        @NotNull final String t_strSql = sql.getValue().trim();

        @Nullable SQLException t_ExceptionToWrap = null;

        @Nullable QueryJBuildException t_ExceptionToThrow = null;

        @Nullable final Log t_Log = UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

        if  (t_Log != null)
        {
            t_Log.trace("Validating " + sql.getId() + " [\n" + t_strSql + "\n]");
        }
        
        @Nullable PreparedStatement t_PreparedStatement = null;

        boolean t_bLastAutoCommit = false;
        try
        {
            t_bLastAutoCommit = setupConnection(connection);
        }
        catch  (@NotNull final SQLException sqlException)
        {
            t_ExceptionToWrap = sqlException;
        }

        try
        {
            t_PreparedStatement = connection.prepareStatement(t_strSql);
        }
        catch  (@NotNull final SQLException sqlException)
        {
            if (t_ExceptionToWrap == null)
            {
                t_ExceptionToWrap = sqlException;
            }
        }

        if  (t_PreparedStatement != null)
        {
            try
            {
                bindParameters(
                    sql,
                    t_PreparedStatement,
                    customSqlProvider,
                    typeManager,
                    ConversionUtils.getInstance());

                validateStatement(sql, t_PreparedStatement, customSqlProvider, metadataManager, typeManager);
            }
            catch  (@NotNull final SQLException sqlException)
            {
                if (t_ExceptionToWrap == null)
                {
                    t_ExceptionToWrap = sqlException;
                }
            }
            catch  (@NotNull final QueryJBuildException buildException)
            {
                t_ExceptionToThrow = buildException;
            }

            try
            {
                t_PreparedStatement.close();
            }
            catch  (@NotNull final SQLException anotherSqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.warn(
                        "Cannot close prepared statement.",
                        anotherSqlException);
                }

                if (t_ExceptionToWrap == null)
                {
                    t_ExceptionToWrap = anotherSqlException;
                }
            }

            try
            {
                tearDownConnection(connection, t_bLastAutoCommit);
            }
            catch  (@NotNull final SQLException anotherSqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.warn(
                        "Cannot restore the connection.",
                        anotherSqlException);
                }

                if (t_ExceptionToWrap == null)
                {
                    t_ExceptionToWrap = anotherSqlException;
                }
            }
        }

        if  (t_ExceptionToWrap != null)
        {
            throw new InvalidCustomSqlException(sql, t_ExceptionToWrap);
        }
        else if  (t_ExceptionToThrow != null)
        {
            throw t_ExceptionToThrow;
        }
    }

    /**
     * Sets up the connection.
     * @param connection the {@link Connection} instance.
     * @return the current auto-commit flag, to be restored afterwards.
     * @throws SQLException if the auto-commit behaviour cannot be changed.
     */
    protected boolean setupConnection(@NotNull final Connection connection)
        throws SQLException
    {
        @Nullable SQLException t_ExceptionToThrow = null;

        @Nullable final Log t_Log = UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

        // The standard is true, but we assume false.
        boolean result = false;

        try
        {
            result = connection.getAutoCommit();
        }
        catch  (@NotNull final SQLException sqlException)
        {
            if  (t_Log != null)
            {
                t_Log.warn(
                    "Cannot retrieve auto-commit flag.",
                    sqlException);
            }

            t_ExceptionToThrow = sqlException;
        }

        try
        {
            connection.setAutoCommit(false);
        }
        catch  (@NotNull final SQLException sqlException)
        {
            if  (t_Log != null)
            {
                t_Log.warn(
                    "Cannot set auto-commit flag to false.",
                    sqlException);
            }

            if (t_ExceptionToThrow == null)
            {
                t_ExceptionToThrow = sqlException;
            }
        }

        if (t_ExceptionToThrow != null)
        {
            throw t_ExceptionToThrow;
        }

        return result;
    }

    /**
     * Performs any necessary actions once the connection is not needed anymore.
     * @param connection the {@link Connection}.
     * @param autoCommit the auto-commit behavior to restore.
     * @throws SQLException if the connection cannot be restored successfully.
     */
    protected void tearDownConnection(@NotNull final Connection connection, final boolean autoCommit)
        throws SQLException
    {
        @Nullable SQLException t_ExceptionToThrow = null;

        @Nullable final Log t_Log = UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

        try
        {
            connection.setAutoCommit(autoCommit);
        }
        catch  (@NotNull final SQLException sqlException)
        {
            if  (t_Log != null)
            {
                t_Log.warn(
                    "Cannot restore auto-commit flag.",
                    sqlException);
            }

            t_ExceptionToThrow = sqlException;
        }

        try
        {
            connection.rollback();
        }
        catch  (@NotNull final SQLException sqlException)
        {
            if  (t_Log != null)
            {
                t_Log.warn(
                    "Cannot rollback connection.",
                    sqlException);
            }

            if (t_ExceptionToThrow == null)
            {
                t_ExceptionToThrow = sqlException;
            }
        }

        if (t_ExceptionToThrow != null)
        {
            throw t_ExceptionToThrow;
        }
    }

    /**
     * Validates given statement.
     * @param sql the {@link Sql}.
     * @param preparedStatement the {@link PreparedStatement}.
     * @param customSqlProvider the {@link CustomSqlProvider}.
     * @param metadataManager the {@link MetadataManager}.
     * @param typeManager the {@link TypeManager}.
     * @return the {@link ResultSet}.
     * @throws SQLException if the ResultSet cannot be closed.
     * @throws QueryJBuildException if the validation fails.
     */
    protected ResultSet validateStatement(
        @NotNull final Sql<String> sql,
        @NotNull final PreparedStatement preparedStatement,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final TypeManager typeManager)
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

            @Nullable final ResultRef t_ResultRef = sql.getResultRef();

            if  (t_ResultRef != null)
            {
                validateResultSet(
                    result,
                    sql,
                    customSqlProvider.getSqlResultDAO().findByPrimaryKey(t_ResultRef.getId()),
                    customSqlProvider,
                    metadataManager,
                    typeManager);
            }
        }

        if  (result != null)
        {
            try
            {
                result.close();
            }
            catch  (@NotNull final SQLException sqlException)
            {
                @Nullable final Log t_Log = UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

                if  (t_Log != null)
                {
                    t_Log.warn(
                        "Cannot close result set.",
                        sqlException);
                }

                throw sqlException;
            }
        }

        return result;
    }

    /**
     * Binds the parameters to given statement.
     * @param sql the sql.
     * @param statement the prepared statement.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param typeManager the metadata type manager.
     * @param conversionUtils the <code>ConversionUtils</code> instance.
     * @throws QueryJBuildException if some problem occurs.
     */
    protected void bindParameters(
        @NotNull final Sql<String> sql,
        @NotNull final PreparedStatement statement,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final TypeManager typeManager,
        @NotNull final ConversionUtils conversionUtils)
     throws  QueryJBuildException
    {
        @Nullable QueryJBuildException exceptionToThrow = null;

        int t_iParameterIndex = 0;

        for (@Nullable final Parameter<String, ?> t_Parameter :
                retrieveParameterElements(sql, customSqlProvider.getSqlParameterDAO()))
        {
            if  (t_Parameter == null)
            {
                exceptionToThrow = new InvalidCustomSqlParameterException(t_iParameterIndex, sql);

                break;
            }
            else
            {
                bindParameter(
                    t_Parameter,
                    t_iParameterIndex,
                    sql,
                    statement,
                    typeManager,
                    conversionUtils);

                t_iParameterIndex++;
            }
        }

        if  (exceptionToThrow != null)
        {
            throw exceptionToThrow;
        }
    }

    /**
     * Binds the parameters to given statement.
     * @param parameter the {@link Parameter}.
     * @param sql the sql.
     * @param statement the prepared statement.
     * @param typeManager the metadata type manager.
     * @param conversionUtils the <code>ConversionUtils</code> instance.
     * @throws QueryJBuildException if some problem occurs.
     */
    @SuppressWarnings("unchecked")
    protected <T> void bindParameter(
        @NotNull final Parameter<String, T> parameter,
        final int parameterIndex,
        @NotNull final Sql<String> sql,
        @NotNull final PreparedStatement statement,
        @NotNull final TypeManager typeManager,
        @NotNull final ConversionUtils conversionUtils)
        throws QueryJBuildException
    {
        @Nullable QueryJBuildException exceptionToThrow = null;

        @Nullable final Log t_Log = UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

        @Nullable final Method t_Method;

        @Nullable final Collection<Class<?>> t_cParameterClasses;

        @Nullable final Class<T> t_Type = retrieveType(parameter, typeManager);

        if  (t_Type != null)
        {
            if (typeManager.isPrimitiveWrapper(t_Type))
            {
                t_cParameterClasses = Arrays.asList(Integer.TYPE, typeManager.toPrimitive(t_Type));
            }
            else
            {
                t_cParameterClasses = Arrays.asList(Integer.TYPE, t_Type);
            }

            t_Method =
                retrievePreparedStatementMethod(
                    parameter,
                    parameterIndex,
                    t_Type,
                    sql,
                    t_cParameterClasses);

            @Nullable final Object t_ParameterValue =
                retrieveParameterValue(
                    parameter, parameterIndex, t_Type.getSimpleName(), t_Type, sql, conversionUtils);

            try
            {
                t_Method.invoke(statement, parameterIndex + 1, t_ParameterValue);
            }
            catch  (@NotNull final IllegalAccessException illegalAccessException)
            {
                if  (t_Log != null)
                {
                    t_Log.warn(
                        COULD_NOT_BIND_PARAMETER_VIA
                        + PREPARED_STATEMENT_SET + t_Type.getSimpleName()
                        + "(int, " + t_Type.getName() + ")",
                        illegalAccessException);
                }

                exceptionToThrow =
                    new UnsupportedCustomSqlParameterTypeException(
                        t_Type.getSimpleName(),
                        parameterIndex + 1,
                        parameter.getName(),
                        sql,
                        illegalAccessException);
            }
            catch  (@NotNull final InvocationTargetException invocationTargetException)
            {
                if  (t_Log != null)
                {
                    t_Log.warn(
                        COULD_NOT_BIND_PARAMETER_VIA
                        + PREPARED_STATEMENT_SET + t_Type.getSimpleName()
                        + "(int, " + t_Type.getName() + ")",
                        invocationTargetException);
                }

                exceptionToThrow =
                    new UnsupportedCustomSqlParameterTypeException(
                        t_Type.getSimpleName(),
                        parameterIndex + 1,
                        parameter.getName(),
                        sql,
                        invocationTargetException);
            }
        }

        if  (exceptionToThrow != null)
        {
            throw exceptionToThrow;
        }
    }

    /**
     * Retrieves the type of the parameter.
     * @param parameter the {@link Parameter}.
     * @param typeManager the {@link MetadataTypeManager}.
     * @return the parameter type.
     */
    @SuppressWarnings("unchecked")
    protected <T> Class<T> retrieveType(
        @NotNull final Parameter<String, T> parameter, @NotNull final TypeManager typeManager)
    {
        return (Class<T>) typeManager.getClass(parameter.getType());
    }

    /**
     * Retrieves the method to invoke on {@link PreparedStatement} class to bind the parameter value.
     * @param parameter the {@link Parameter}.
     * @param parameterIndex the index of the parameter.
     * @param type the parameter type.
     * @param sql the sql.
     * @throws QueryJBuildException if some problem occurs.
     */
    @NotNull
    protected Method retrievePreparedStatementMethod(
        @NotNull final Parameter<String, ?> parameter,
        final int parameterIndex,
        @NotNull final Class<?> type,
        @NotNull final Sql<String> sql,
        @NotNull final Collection<Class<?>> parameterClasses)
        throws  QueryJBuildException
    {
        @Nullable QueryJBuildException exceptionToThrow = null;

        @Nullable Method result = null;

        try
        {
            result =
                retrieveMethod(
                    PreparedStatement.class,
                    getSetterMethod(type),
                    parameterClasses.toArray(new Class<?>[parameterClasses.size()]));
        }
        catch  (@NotNull final NoSuchMethodException noSuchMethodException)
        {
            exceptionToThrow =
                new UnsupportedCustomSqlParameterTypeException(
                    type.getSimpleName(),
                    parameterIndex + 1,
                    parameter.getName(),
                    sql,
                    noSuchMethodException);
        }

        if  (exceptionToThrow != null)
        {
            throw exceptionToThrow;
        }

        return result;
    }

    /**
     * Retrieves the validation value for given parameter.
     * @param parameter the {@link Parameter}.
     * @param parameterIndex the index of the parameter.
     * @param type the parameter type.
     * @param typeClass the class of the parameter type.
     * @param sql the {@link Sql}.
     * @param conversionUtils the {@link ConversionUtils} instance.
     * @return the validation value.
     * @throws QueryJBuildException if some problem occurs.
     */
    @SuppressWarnings("unchecked")
    protected <T> T retrieveParameterValue(
        @NotNull final Parameter<String, T> parameter,
        final int parameterIndex,
        @NotNull final String type,
        @NotNull final Class<T> typeClass,
        @NotNull final Sql<String> sql,
        @NotNull final ConversionUtils conversionUtils)
        throws  QueryJBuildException
    {
        @Nullable T result;

        if  (   ("Date".equals(type))
             && (parameter.getValidationValue() != null))
        {
            result = (T) new java.sql.Date(new Date().getTime());
        }
        else if (   (Literals.TIMESTAMP_U.equals(type.toUpperCase(new Locale("US"))))
                 && (parameter.getValidationValue() != null))
        {
            result = (T) new Timestamp(new Date().getTime());
        }
        else
        {
            result =
                retrieveParameterValue(
                    parameter, type, conversionUtils, StringUtils.getInstance());
        }

        if (result == null)
        {
            result = (T) assumeIsADate(parameter, sql);
        }

        if (result == null)
        {
            // We have only once chance: constructor call.
            result = createViaConstructor(parameter, parameterIndex, type, typeClass, sql);
        }

        return result;
    }

    /**
     * Retrieves the validation value for given parameter.
     * @param parameter the {@link Parameter}.
     * @param type the parameter type.
     * @param conversionUtils the {@link ConversionUtils} instance.
     * @param stringUtils the {@link StringUtils} instance.
     * @return the validation value.
     * @throws QueryJBuildException if some problem occurs.
     */
    @SuppressWarnings("unchecked")
    protected <T> T retrieveParameterValue(
        @NotNull final Parameter<String, T> parameter,
        @NotNull final String type,
        @NotNull final ConversionUtils conversionUtils,
        @NotNull final StringUtils stringUtils)
        throws  QueryJBuildException
    {
        @Nullable T result = null;

        try
        {
            @Nullable final Method t_ParameterMethod;

            t_ParameterMethod =
                conversionUtils.getClass().getMethod(
                    "to" + stringUtils.capitalize(type),
                    CLASS_ARRAY_OF_ONE_STRING);

            if  (t_ParameterMethod != null)
            {
                result =
                    (T) t_ParameterMethod.invoke(
                        conversionUtils,
                        parameter.getValidationValue());
            }
        }
        catch  (@NotNull final NoSuchMethodException noSuchMethod)
        {
            // it's not a plain type.
        }
        catch  (@NotNull final SecurityException securityMethod)
        {
            // can do little
        }
        catch  (@NotNull final IllegalAccessException illegalAccessException)
        {
            // can do little
        }
        catch  (@NotNull final InvocationTargetException invocationTargetException)
        {
            // can do little
        }

        return result;
    }

    /**
     * Tries to retrieve a Date value from given parameter.
     * @param parameter the {@link Parameter}.
     * @param sql the {@link Sql}.
     * @return the {@link Date} value if it's a Date.
     * @throws QueryJBuildException if some problem occurs.
     */
    protected Date assumeIsADate(
        @NotNull final Parameter<String, ?> parameter,
        @NotNull final Sql<String> sql)
        throws  QueryJBuildException
    {
        @Nullable Date result = null;

        @Nullable QueryJBuildException exceptionToThrow = null;

        // let's try if it's a date.
        try
        {
            boolean t_bInvalidValidationValue = false;

            Object t_strValidationValue =
                parameter.getValidationValue();

            if  (t_strValidationValue == null)
            {
                t_strValidationValue = DATE_FORMAT.format(new Date());
                t_bInvalidValidationValue = true;
            }

            try
            {
                result = DATE_FORMAT.parse("" + t_strValidationValue);
            }
            catch (@NotNull final NumberFormatException invalidDate)
            {
                try
                {
                    result = DATE_FORMAT_EN.parse("" + t_strValidationValue);
                }
                catch (@NotNull final NumberFormatException invalidEnglishDate)
                {
                    // It doesn't need to be a date.
                }
            }

            if  (t_bInvalidValidationValue)
            {
                exceptionToThrow =
                    new NoValidationValueForCustomSqlDateParameterException(
                        parameter, sql);
            }
        }
        catch  (@NotNull final ParseException parseException)
        {
            // It doesn't need to be a date.
        }

        if  (exceptionToThrow != null)
        {
            throw exceptionToThrow;
        }

        return result;
    }

    /**
     * Creates the validation value via constructor.
     * @param parameter the {@link Parameter}.
     * @param parameterIndex the index of the parameter.
     * @param type the parameter type.
     * @param typeClass the class of the parameter type.
     * @param sql the {@link Sql}.
     * @return the parameter value.
     * @throws QueryJBuildException if some problem occurs.
     */
    protected <T> T createViaConstructor(
        @NotNull final Parameter<String, T> parameter,
        final int parameterIndex,
        @NotNull final String type,
        @NotNull final Class<T> typeClass,
        @NotNull final Sql<String> sql)
        throws  QueryJBuildException
    {
        @Nullable T result = null;

        @Nullable QueryJBuildException exceptionToThrow = null;

        // We have only once chance: constructor call.
        try
        {
            @Nullable final Constructor<T> t_Constructor =
                typeClass.getConstructor(CLASS_ARRAY_OF_ONE_STRING);

            if  (t_Constructor != null)
            {
                result =
                    t_Constructor.newInstance(
                        parameter.getValidationValue());
            }
        }
        catch  (@NotNull final NoSuchMethodException noSuchMethod)
        {
            exceptionToThrow =
                new UnsupportedCustomSqlParameterTypeException(
                    type,
                    parameterIndex + 1,
                    parameter.getName(),
                    sql,
                    noSuchMethod);
        }
        catch  (@NotNull final SecurityException securityException)
        {
            exceptionToThrow =
                new UnsupportedCustomSqlParameterTypeException(
                    type,
                    parameterIndex + 1,
                    parameter.getName(),
                    sql,
                    securityException);
        }
        catch  (@NotNull final IllegalAccessException illegalAccessException)
        {
            exceptionToThrow =
                new UnsupportedCustomSqlParameterTypeException(
                    type,
                    parameterIndex + 1,
                    parameter.getName(),
                    sql,
                    illegalAccessException);
        }
        catch  (@NotNull final InstantiationException instantiationException)
        {
            exceptionToThrow =
                new UnsupportedCustomSqlParameterTypeException(
                    type,
                    parameterIndex + 1,
                    parameter.getName(),
                    sql,
                    instantiationException);
        }
        catch  (@NotNull final InvocationTargetException invocationTargetException)
        {
            exceptionToThrow =
                new UnsupportedCustomSqlParameterTypeException(
                    type,
                    parameterIndex + 1,
                    parameter.getName(),
                    sql,
                    invocationTargetException);
        }

        if  (exceptionToThrow != null)
        {
            throw exceptionToThrow;
        }

        return result;
    }

    /**
     * Retrieves the parameters for given sql element.
     * @param sql such element.
     * @param parameterDAO the {@link SqlParameterDAO} instance.
     * @return the parameter elements.
     */
    protected List<Parameter<String, ?>> retrieveParameterElements(
        @NotNull final Sql<String> sql, @NotNull final SqlParameterDAO parameterDAO)
    {
        @NotNull final List<Parameter<String, ?>> result = new ArrayList<>();

        Parameter<String, ?> t_Parameter;

        for (@Nullable final ParameterRef t_ParameterRef : sql.getParameterRefs())
        {
            if (t_ParameterRef != null)
            {
                t_Parameter = parameterDAO.findByPrimaryKey(t_ParameterRef.getId());

                if (t_Parameter != null)
                {
                    result.add(t_Parameter);
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the setter method name.
     * @param type the data type.
     * @return the associated setter method.
     */
    protected String getSetterMethod(final Class<?> type)
    {
        return getAccessorMethod("set",  type, StringUtils.getInstance());
    }

    /**
     * Retrieves the getter method name.
     * @param type the data type.
     * @return the associated getter method.
     */
    @NotNull
    protected String getGetterMethod(@NotNull final Class<?> type)
    {
        return getAccessorMethod("get", type, StringUtils.getInstance());
    }

    /**
     * Retrieves the accessor method name.
     * @param prefix the prefix (set/get).
     * @param type the data type.
     * @param stringUtils the {@link StringUtils} instance.
     * @return the associated getter method.
     */
    protected String getAccessorMethod(
        @NotNull final String prefix,
        @NotNull final Class<?> type,
        @NotNull final StringUtils stringUtils)
    {
        @NotNull final StringBuilder result = new StringBuilder(prefix);

        @NotNull final String t_strSimpleName;

        if (type.equals(Integer.class))
        {
            t_strSimpleName = "Int";
        }
        else
        {
            t_strSimpleName = stringUtils.capitalize(type.getSimpleName());
        }

        result.append(stringUtils.capitalize(t_strSimpleName));

        return result.toString();
    }

    /**
     * Validates the result set.
     * @param resultSet the result set to validate.
     * @param sql the sql.
     * @param sqlResult the custom sql result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param typeManager the <code>MetadataTypeManager</code> instance.
     * @throws SQLException if the SQL operation fails.
     * @throws QueryJBuildException if the expected result cannot be extracted.
     */
    protected void validateResultSet(
        @NotNull final ResultSet resultSet,
        @NotNull final Sql<String> sql,
        final Result<String> sqlResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final TypeManager typeManager)
      throws SQLException,
             QueryJBuildException
    {
        @Nullable final Log t_Log = UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

        @NotNull Collection<Property<String>> t_cProperties =
            retrieveExplicitProperties(
                sql,
                sqlResult,
                customSqlProvider.getSqlPropertyDAO(),
                metadataManager,
                typeManager);

        if  (t_cProperties.size() == 0)
        {
            t_cProperties =
                retrieveImplicitProperties(sqlResult, customSqlProvider, metadataManager, typeManager);
        }

        if  (t_cProperties.size() == 0)
        {
            throw new CustomResultWithNoPropertiesException(sqlResult, sql);
        }
        else
        {
            if  (resultSet.next())
            {
                @NotNull Method t_Method;

                for (@Nullable final Property<String> t_Property : t_cProperties)
                {
                    if (t_Property != null)
                    {
                        try
                        {
                            t_Method =
                                retrieveMethod(
                                    ResultSet.class,
                                    getGetterMethod(typeManager.getClass(t_Property.getType())),
                                    new Class<?>[]
                                    {
                                        (t_Property.getIndex() > 0)
                                        ?  Integer.TYPE
                                        :  String.class
                                    });
                        }
                        catch  (@NotNull final NoSuchMethodException noSuchMethod)
                        {
                            throw
                                new UnsupportedCustomResultPropertyTypeException(
                                    t_Property, sqlResult, sql, noSuchMethod);
                        }

                        invokeResultSetGetter(
                            t_Method, resultSet, t_Property, sqlResult, sql);
                    }
                }
            }
            else
            {
                @NotNull final ResultSetMetaData t_Metadata = resultSet.getMetaData();

                final int t_iColumnCount = t_Metadata.getColumnCount();
                
                if  (t_iColumnCount < t_cProperties.size())
                {
                    throw
                        new CustomResultWithInvalidNumberOfColumnsException(
                            t_iColumnCount, t_cProperties.size());
                }

                String t_strColumnName;
                int t_iColumnType;
                
                for  (int t_iIndex = 1; t_iIndex <= t_iColumnCount; t_iIndex++)
                {
                    t_strColumnName = t_Metadata.getColumnName(t_iIndex);
                    t_iColumnType = t_Metadata.getColumnType(t_iIndex);

                    if  (!matches(
                             t_strColumnName,
                             t_iColumnType,
                             t_iIndex,
                             t_cProperties,
                             typeManager))
                    {
                        if  (t_Log != null)
                        {
                            t_Log.warn(
                                  "Column not mapped ("
                                + t_iIndex + ", "
                                + t_strColumnName
                                + ", " + t_iColumnType + ")");
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Retrieves the properties declared for given result.
     * @param sql the sql.
     * @param sqlResult the custom sql result.
     * @param propertyDAO the {@link SqlPropertyDAO} instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param typeManager the <code>MetadataTypeManager</code> instance.
     * @return such properties.
     * @throws QueryJBuildException if the properties cannot be retrieved..
     */
    @SuppressWarnings("unused")
    @NotNull
    protected List<Property<String>> retrieveExplicitProperties(
        @NotNull final Sql<String> sql,
        @NotNull final Result<String> sqlResult,
        @NotNull final SqlPropertyDAO propertyDAO,
        @NotNull final MetadataManager metadataManager,
        @NotNull final TypeManager typeManager)
      throws  QueryJBuildException
    {
        @NotNull final List<Property<String>> result = new ArrayList<>();

        Property<String> t_Property;

        for (@Nullable final PropertyRef t_PropertyRef : sqlResult.getPropertyRefs())
        {
            if (t_PropertyRef != null)
            {
                t_Property = propertyDAO.findByPrimaryKey(t_PropertyRef.getId());

                if  (t_Property != null)
                {
                    result.add(t_Property);
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the implicit properties declared for given result.
     * @param sqlResult the custom sql result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param typeManager the <code>MetadataTypeManager</code> instance.
     * @return such properties.
     * @throws QueryJBuildException if the properties cannot be retrieved..
     */
    @SuppressWarnings("unused")
    @NotNull
    protected Collection<Property<String>> retrieveImplicitProperties(
        @NotNull final Result<String> sqlResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final TypeManager typeManager)
      throws  QueryJBuildException
    {
        return
            retrieveImplicitProperties(
                sqlResult,
                customSqlProvider,
                metadataManager,
                typeManager,
                CustomResultUtils.getInstance());
    }

    /**
     * Retrieves the implicit properties declared for given result.
     * @param sqlResult the custom sql result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param typeManager the <code>MetadataTypeManager</code> instance.
     * @param customResultUtils the <code>CustomResultUtils</code> instance.
     * @return such properties.
     * @throws QueryJBuildException if the properties cannot be retrieved..
     */
    @NotNull
    protected Collection<Property<String>> retrieveImplicitProperties(
        @NotNull final Result<String> sqlResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final TypeManager typeManager,
        @NotNull final CustomResultUtils customResultUtils)
      throws  QueryJBuildException
    {
        @NotNull final Collection<Property<String>> result = new ArrayList<>();

        @Nullable final String t_strTable =
            customResultUtils.retrieveTable(
                sqlResult,
                customSqlProvider,
                metadataManager);

        if  (t_strTable != null)
        {
            @Nullable final List<Attribute<String>> t_lColumns =
                metadataManager.getColumnDAO().findAllColumns(t_strTable);

            final int t_iCount = t_lColumns.size();

            String t_strId;
            Attribute<String> t_Column;
            @Nullable Class<?> t_Type;

            for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                t_Column = t_lColumns.get(t_iIndex);

                if (t_Column != null)
                {
                    t_strId = t_strTable + "." + t_Column.getName() + ".property";
                
                    t_Type = typeManager.getClass(t_Column.getType());

                    result.add(
                        new PropertyElement<>(
                            t_strId,
                            t_Column.getName(),
                            t_iIndex + 1,
                            t_Type.getSimpleName(),
                            t_Column.isNullable()));
                }
            }
        }
        else
        {
            @NotNull final String t_strErrorMessage =
                "Cannot retrieve table associated to SQL result " + sqlResult.getId();

            @Nullable final Log t_Log =
                UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

            if  (t_Log != null)
            {
                t_Log.warn(t_strErrorMessage);
            }

            throw new NoTableMatchingCustomResultException(sqlResult);
        }

        return result;
    }
    
    /**
     * Executes the <code>ResultSet.getXXX</code> method.
     * @param method the <code>ResultSet</code> getter method for given property.
     * @param resultSet the {@link ResultSet} instance.
     * @param property the property.
     * @param sqlResult the {@link Result} instance.
     * @param sql the SQL element.
     * @throws QueryJBuildException if the validation fails.
     */
    protected void invokeResultSetGetter(
        @NotNull final Method method,
        @NotNull final ResultSet resultSet,
        @NotNull final Property<String> property,
        @NotNull final Result<String> sqlResult,
        @NotNull final Sql<String> sql)
      throws QueryJBuildException
    {
        @Nullable final Log t_Log = UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

        try
        {
            @NotNull final Object[] t_aParameters = new Object[1];

            if  (property.getIndex() > 0)
            {
                t_aParameters[0] = property.getIndex();
            }
            else
            {
                t_aParameters[0] = property.getColumnName();
            }

            method.invoke(resultSet, t_aParameters);
        }
        catch  (@NotNull final IllegalAccessException illegalAccessException)
        {
            if  (t_Log != null)
            {
                t_Log.warn(
                      VALIDATION_FAILED_FOR + sql.getId() + ":\n"
                    + COULD_NOT_RETRIEVE_RESULT_VIA
                    + RESULT_SET + method.getName()
                    + "("
                    + (   (property.getIndex() > 0)
                       ?  "" + property.getIndex()
                       :  property.getColumnName())
                    + ")",
                    illegalAccessException);
            }

            throw
                new UnsupportedCustomResultPropertyTypeException(
                    property, sqlResult, sql, illegalAccessException);
        }
        catch  (@NotNull final InvocationTargetException invocationTargetException)
        {
            if  (t_Log != null)
            {
                t_Log.warn(
                      VALIDATION_FAILED_FOR + sql.getId() + ":\n"
                    + COULD_NOT_RETRIEVE_RESULT_VIA
                    + RESULT_SET + method.getName()
                    + "("
                    + (   (property.getIndex() > 0)
                       ?  "" + property.getIndex()
                       :  property.getColumnName())
                    + ")",
                    invocationTargetException);
            }

            throw
                new UnsupportedCustomResultPropertyTypeException(
                    property, sqlResult, sql, invocationTargetException);
        }
    }

    /**
     * Retrieves the method to call.
     * @param instanceClass the instance class.
     * @param methodName the method name.
     * @return the <code>Method</code> instance.
     * @throws NoSuchMethodException if the desired method is not available.
     */
    @NotNull
    protected Method retrieveMethod(
        @NotNull final Class<?> instanceClass,
        @NotNull final String methodName,
        @NotNull final Class[] parameterClasses)
      throws  NoSuchMethodException
    {       
        return instanceClass.getDeclaredMethod(methodName, parameterClasses);
    }

    /**
     * Checks whether given column information is represented by any of the
     * defined properties.
     * @param name the column name.
     * @param type the column type.
     * @param index the column index.
     * @param properties the properties.
     * @param typeManager the <code>MetadataTypeManager</code> instance.
     * @return <code>true</code> in such case.
     */
    protected boolean matches(
        @NotNull final String name,
        final int type,
        final int index,
        @NotNull final Collection<Property<String>> properties,
        @NotNull final TypeManager typeManager)
    {
        boolean result = false;

        int t_iPropertyIndex = 0;

        for (@Nullable final Property<String> t_Property : properties)
        {
            if (t_Property != null)
            {
                final boolean t_bMatches =
                    matches(
                        name, type, index, t_Property, t_iPropertyIndex, typeManager);

                if (t_bMatches)
                {
                    result = true;
                    break;
                }
                t_iPropertyIndex++;
            }
        }
        
        return result;
    }

    /**
     * Checks whether given column information is represented by the
     * defined property.
     * @param name the column name.
     * @param type the column type.
     * @param index the column index.
     * @param property the property.
     * @param propertyIndex the property index.
     * @param typeManager the <code>MetadataTypeManager</code> instance.
     * @return <code>true</code> in such case.
     */
    @SuppressWarnings("unused")
    protected boolean matches(
        @Nullable final String name,
        final int type,
        final int index,
        @NotNull final Property<String> property,
        final int propertyIndex,
        final TypeManager typeManager)
    {
        boolean result = true;

        if  (   (name != null)
             && (!name.equalsIgnoreCase(property.getColumnName())))
        {
            result = false;
        }

        // This doesn't seem to work well for Oracle (2 != -5)
//         if  (   (result)
//              && (type != metadataTypeManager.getJavaType(property.getType())))
//         {
//             result = false;
//         }

        if  (   (result)
             && (index != propertyIndex))
        {
            result = false;
        }

        return result;
    }

    /**
     * Retrieves whether to disable custom sql validation at all or not.
     * @param settings the settings.
     * @return <code>true</code> in such case.
     */
    protected boolean retrieveDisableCustomSqlValidation(@NotNull final QueryJCommand settings)
    {
        return settings.getBooleanSetting(ParameterValidationHandler.DISABLE_CUSTOM_SQL_VALIDATION, false);
    }

    @Override
    @NotNull
    public String toString()
    {
        return
              "{ 'class': 'CustomSqlValidationHandler', 'DATE_FORMAT': '" + DATE_FORMAT + "'"
            + ", 'DATE_FORMAT_EN': '" + DATE_FORMAT_EN + "' }";
    }
}
