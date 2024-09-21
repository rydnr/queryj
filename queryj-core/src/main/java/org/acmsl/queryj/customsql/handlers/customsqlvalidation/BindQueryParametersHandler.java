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
 * Filename: BindQueryParametersHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Binds the query parameters to the PreparedStatement.
 *
 * Date: 2014/03/15
 * Time: 08:06
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

/*
 * Importing ACM-SL Java Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.utils.ConversionUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing
 */
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.api.exceptions.InvalidCustomSqlParameterException;
import org.acmsl.queryj.api.exceptions.NoValidationValueForCustomSqlDateParameterException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.exceptions.UnsupportedCustomSqlParameterTypeException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.ParameterRef;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.handlers.CustomSqlValidationHandler;
import org.acmsl.queryj.metadata.SqlParameterDAO;
import org.acmsl.queryj.metadata.TypeManager;
import org.acmsl.queryj.metadata.engines.JdbcTypeManager;
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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Binds the query parameters to the PreparedStatement.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/15 08:06
 */
@ThreadSafe
public class BindQueryParametersHandler
    extends AbstractQueryJCommandHandler
{
    /**
     * String literal: "Could not bind parameter via "
     */
    protected static final String COULD_NOT_BIND_PARAMETER_VIA = "Could not bind parameter via ";

    /**
     * String literal: "PreparedStatement.set"
     */
    protected static final String PREPARED_STATEMENT_SET = "PreparedStatement.set";

    /**
     * A cached class array.
     */
    @SuppressWarnings("unchecked")
    private static final Class<String>[] CLASS_ARRAY_OF_ONE_STRING =
        (Class<String>[]) new Class<?>[] { String.class };

    /**
     * The date format.
     */
    public final String DATE_FORMAT = "MM/DD/yyyy";

    /**
     * The date format, in english notation.
     */
    public final String DATE_FORMAT_EN = "yyyy/DD/MM/DD";

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
        @NotNull final CustomSqlProvider customSqlProvider = retrieveCustomSqlProvider(command);
        @NotNull final TypeManager typeManager = new JdbcTypeManager();

        if ("share.bookings.close-shares-bookings-by-parent-club-definition-id-and-cycle-id".equals(sql.getId()))
        {
            int a = 0;
        }
        @NotNull final PreparedStatement t_PreparedStatement =
            new SetupPreparedStatementHandler().retrieveCurrentPreparedStatement(command);

        try
        {
            bindParameters(
                sql,
                t_PreparedStatement,
                customSqlProvider,
                typeManager,
                ConversionUtils.getInstance());
        }
        catch  (@NotNull final QueryJBuildException buildException)
        {
            throw buildException;
        }

        return false;
    }

    /**
     * Binds the query parameters to the {@link PreparedStatement}.
     * @param sql the query.
     * @param preparedStatement the PreparedStatement.
     * @param customSqlProvider the {@link CustomSqlProvider}.
     * @param typeManager the {@link TypeManager}.
     * @param conversionUtils the {@link ConversionUtils}.
     * @throws QueryJBuildException if the binding fails.
     */
    protected void bindParameters(
        @NotNull final Sql<String> sql,
        @NotNull final PreparedStatement preparedStatement,
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
                    preparedStatement,
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
     * @param parameterIndex the parameter index.
     * @param sql the sql.
     * @param statement the prepared statement.
     * @param typeManager the metadata type manager.
     * @param conversionUtils the <code>ConversionUtils</code> instance.
     * @param <T> the type.
     * @throws QueryJBuildException if the binding fails.
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
     * @param typeManager the {@link org.acmsl.queryj.metadata.MetadataTypeManager}.
     * @param <T> the type.
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
     * @param parameterClasses the parameter classes.
     * @return the method.
     * @throws QueryJBuildException if the binding fails.
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
                    parameterClasses.toArray(new Class[parameterClasses.size()]));
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
     * @param <T> the type.
     * @return the validation value.
     * @throws QueryJBuildException if the binding fails.
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
     * @param <T> the type.
     * @return the validation value.
     * @throws QueryJBuildException if the binding fails.
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
                    "to" + stringUtils.capitalize(type, QueryJSettings.DEFAULT_LOCALE),
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
     * @throws QueryJBuildException if the binding fails.
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

            @NotNull final DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            if  (t_strValidationValue == null)
            {
                t_strValidationValue = formatter.format(new Date());
                t_bInvalidValidationValue = true;
            }

            try
            {
                result = formatter.parse("" + t_strValidationValue);
            }
            catch (@NotNull final NumberFormatException invalidDate)
            {
                try
                {
                    result = new SimpleDateFormat(DATE_FORMAT_EN).parse("" + t_strValidationValue);
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
     * @param <T> the type.
     * @return the parameter value.
     * @throws QueryJBuildException if the binding fails.
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
     * @param parameterDAO the {@link org.acmsl.queryj.metadata.SqlParameterDAO} instance.
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
            t_strSimpleName = stringUtils.capitalize(type.getSimpleName(), QueryJSettings.DEFAULT_LOCALE);
        }

        result.append(stringUtils.capitalize(t_strSimpleName, QueryJSettings.DEFAULT_LOCALE));

        return result.toString();
    }

    /**
     * Retrieves the method to call.
     * @param instanceClass the instance class.
     * @param methodName the method name.
     * @param parameterClasses the parameter classes.
     * @return the <code>Method</code> instance.
     * @throws NoSuchMethodException if the method is not found.
     */
    @NotNull
    protected Method retrieveMethod(
        @NotNull final Class<?> instanceClass,
        @NotNull final String methodName,
        @NotNull final Class<?>[] parameterClasses)
        throws  NoSuchMethodException
    {
        return instanceClass.getDeclaredMethod(methodName, parameterClasses);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"DATE_FORMAT\": \"" + DATE_FORMAT + '"'
            + ", \"DATE_FORMAT_EN\": \"" + DATE_FORMAT_EN + '"'
            + ", \"class\": \"BindQueryParametersHandler\""
            + ", \"package\": \"org.acmsl.queryj.customsql.handlers.customsqlvalidation\""
            + " }";
    }
}
