//;-*- mode: java -*-
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
import org.acmsl.queryj.metadata.engines.JdbcMetadataTypeManager;
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
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.lang.reflect.Method;
import java.lang.NoSuchFieldException;
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
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
     * @param sqlDAO the {@link SqlDAO} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param connection the connection.
     * @param metadataManager the metadata manager.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void validate(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final SqlDAO sqlDAO,
        @NotNull final Connection connection,
        @NotNull final MetadataManager metadataManager)
      throws  QueryJBuildException
    {
        for (@Nullable final Sql t_Sql : sqlDAO.findAll())
        {
            if (   (t_Sql != null)
                && (t_Sql.isValidate()))
            {
                validate(
                    t_Sql,
                    customSqlProvider,
                    connection,
                    metadataManager,
                    metadataManager.getMetadataTypeManager());
            }
        }
    }

    /**
     * Validates given sql element.
     * @param sql such element.
     * @param customSqlProvider the custom sql provider.
     * @param connection the connection.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @throws QueryJBuildException if the sql is not valid.
     */
    protected void validate(
        @NotNull final Sql sql,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final Connection connection,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager)
      throws  QueryJBuildException
    {
        @NotNull final String t_strSql = sql.getValue().trim();

        @Nullable SQLException t_ExceptionToWrap = null;

        @Nullable QueryJBuildException t_ExceptionToThrow = null;

        @Nullable final Log t_Log = UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

        if  (t_Log != null)
        {
            t_Log.trace(
                "Validating " + sql.getId() + " [\n" + t_strSql + "\n]");
        }
        
        @Nullable PreparedStatement t_PreparedStatement = null;
        @Nullable ResultSet t_ResultSet = null;

        // The standard is true, but we assume false.
        boolean t_bLastAutoCommit = false;

        try
        {
            t_bLastAutoCommit = connection.getAutoCommit();
        }
        catch  (@NotNull final SQLException sqlException)
        {
            if  (t_Log != null)
            {
                t_Log.warn(
                    "Cannot retrieve auto-commit flag.",
                    sqlException);
            }
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
        }

        try
        {
            t_PreparedStatement =
                connection.prepareStatement(t_strSql);
        }
        catch  (@NotNull final SQLException sqlException)
        {
            t_ExceptionToWrap = sqlException;
        }

        if  (t_PreparedStatement != null)
        {
            try
            {
                bindParameters(
                    sql,
                    t_PreparedStatement,
                    customSqlProvider,
                    metadataTypeManager,
                    ConversionUtils.getInstance());

                if  (   (Sql.INSERT.equals(sql.getType()))
                     || (Sql.DELETE.equals(sql.getType())))
                {
                    t_PreparedStatement.executeUpdate();
                }
                else
                {
                    t_ResultSet = t_PreparedStatement.executeQuery();

                    @Nullable final ResultRef t_ResultRef = sql.getResultRef();
                    
                    if  (t_ResultRef != null)
                    {
                        validateResultSet(
                            t_ResultSet,
                            sql,
                            customSqlProvider.getSqlResultDAO().findByPrimaryKey(t_ResultRef.getId()),
                            customSqlProvider,
                            metadataManager,
                            metadataTypeManager);
                    }
                }
            }
            catch  (@NotNull final SQLException sqlException)
            {
                t_ExceptionToWrap = sqlException;
            }
            catch  (@NotNull final QueryJBuildException buildException)
            {
                t_ExceptionToThrow = buildException;
            }

            if  (t_ResultSet != null)
            {
                try
                {
                    t_ResultSet.close();
                }
                catch  (@NotNull final SQLException sqlException)
                {
                    if  (t_Log != null)
                    {
                        t_Log.warn(
                            "Cannot close result set.",
                            sqlException);
                    }
                }
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
            }

            try
            {
                connection.setAutoCommit(t_bLastAutoCommit);
            }
            catch  (@NotNull final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.warn(
                        "Cannot restore auto-commit flag.",
                        sqlException);
                }
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
            }                
        }

        if  (t_ExceptionToThrow != null)
        {
            throw t_ExceptionToThrow;
        }
        else if  (t_ExceptionToWrap != null)
        {
            throw new InvalidCustomSqlException(sql, t_ExceptionToWrap);
        }
    }

    /**
     * Binds the parameters to given statement.
     * @param sql the sql.
     * @param statement the prepared statement.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataTypeManager the metadata type manager.
     * @param conversionUtils the <code>ConversionUtils</code> instance.
     * @throws SQLException if the binding process fails.
     * @throws QueryJBuildException if some problem occurs.
     */
    protected void bindParameters(
        @NotNull final Sql sql,
        @NotNull final PreparedStatement statement,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final ConversionUtils conversionUtils)
     throws  SQLException,
             QueryJBuildException
    {
        @Nullable QueryJBuildException exceptionToThrow = null;

        @Nullable final Log t_Log = UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

        @Nullable Method t_Method;

        @Nullable Collection<Class<?>> t_cSetterParams;

        @Nullable Class<?> t_Type;

        @Nullable String t_strType;

        int t_iParameterIndex = 0;

        for (@Nullable final Parameter t_Parameter : retrieveParameterElements(sql, customSqlProvider.getSqlParameterDAO()))
        {
            if  (t_Parameter == null)
            {
                exceptionToThrow = new InvalidCustomSqlParameterException(t_iParameterIndex, sql);

                break;
            }
            else
            {
                t_Method = null;

                t_cSetterParams = new ArrayList<Class<?>>();

                t_cSetterParams.add(Integer.TYPE);

                // TODO: support boolean properties/parameters
                t_strType =
                    metadataTypeManager.getObjectType(
                        metadataTypeManager.getJavaType(
                            t_Parameter.getType()), false);

                t_Type =
                    retrieveType(t_strType, t_Parameter.getType());
            }

            if  (t_Type != null)
            {
                t_cSetterParams.add(t_Type);

                @Nullable Object t_ParameterValue = null;

                try
                {
                    t_Method =
                        retrieveMethod(
                            statement.getClass(),
                            getSetterMethod(t_strType, metadataTypeManager),
                            t_cSetterParams.toArray(new Class<?>[t_cSetterParams.size()]));
                }
                catch  (@NotNull final NoSuchMethodException noSuchMethodException)
                {
                    exceptionToThrow =
                        new UnsupportedCustomSqlParameterTypeException(
                            t_strType,
                            t_iParameterIndex,
                            sql,
                            noSuchMethodException);
                }

                try
                {
                    @Nullable final Method t_ParameterMethod;

                    if  (   (   ("Date".equals(t_strType))
                             || (JdbcMetadataTypeManager.TIMESTAMP.equals(t_strType)))
                         && (t_Parameter.getValidationValue() != null))
                    {
                        t_ParameterValue = new Timestamp(new Date().getTime());
                    }
                    else
                    {
                        t_ParameterMethod =
                            conversionUtils.getClass().getMethod(
                                "to" + t_strType,
                                CLASS_ARRAY_OF_ONE_STRING);

                        if  (t_ParameterMethod != null)
                        {
                            t_ParameterValue =
                                t_ParameterMethod.invoke(
                                    conversionUtils,
                                    t_Parameter.getValidationValue());

                            exceptionToThrow = null;
                        }
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

                if  (t_ParameterValue == null)
                {
                    // let's try if it's a date.
                    try
                    {
                        boolean t_bInvalidValidationValue = false;

                        String t_strValidationValue = 
                            t_Parameter.getValidationValue();

                        if  (t_strValidationValue == null)
                        {
                            t_strValidationValue = DATE_FORMAT.format(new Date());
                            t_bInvalidValidationValue = true;
                        }

                        t_ParameterValue =
                            DATE_FORMAT.parse(
                                t_strValidationValue);

                        if  (t_bInvalidValidationValue)
                        {
                            exceptionToThrow =
                                new NoValidationValueForCustomSqlDateParameterException(
                                    t_Parameter, sql);
                        }
                    }
                    catch  (@NotNull final ParseException parseException)
                    {
                        // We have only once chance: constructor call.
                        try
                        {
                            @Nullable final Constructor<?> t_Constructor =
                                t_Type.getConstructor(CLASS_ARRAY_OF_ONE_STRING);

                            if  (t_Constructor != null)
                            {
                                t_ParameterValue =
                                    t_Constructor.newInstance(
                                        t_Parameter.getValidationValue());
                            }
                        }
                        catch  (@NotNull final NoSuchMethodException noSuchMethod)
                        {
                            exceptionToThrow =
                                new UnsupportedCustomSqlParameterTypeException(
                                    t_strType,
                                    t_iParameterIndex,
                                    sql,
                                    noSuchMethod);
                        }
                        catch  (@NotNull final SecurityException securityException)
                        {
                            exceptionToThrow =
                                new UnsupportedCustomSqlParameterTypeException(
                                    t_strType,
                                    t_iParameterIndex,
                                    sql,
                                    securityException);
                        }
                        catch  (@NotNull final IllegalAccessException illegalAccessException)
                        {
                            exceptionToThrow =
                                new UnsupportedCustomSqlParameterTypeException(
                                    t_strType,
                                    t_iParameterIndex,
                                    sql,
                                    illegalAccessException);
                        }
                        catch  (@NotNull final InstantiationException instantiationException)
                        {
                            exceptionToThrow =
                                new UnsupportedCustomSqlParameterTypeException(
                                    t_strType,
                                    t_iParameterIndex,
                                    sql,
                                    instantiationException);
                        }
                        catch  (@NotNull final InvocationTargetException invocationTargetException)
                        {
                            exceptionToThrow =
                                new UnsupportedCustomSqlParameterTypeException(
                                    t_strType,
                                    t_iParameterIndex,
                                    sql,
                                    invocationTargetException);
                        }
                    }
                }

                if  (   (exceptionToThrow == null)
                     && (t_Method == null))
                {
                    exceptionToThrow =
                        new UnsupportedCustomSqlParameterTypeException(
                            t_strType,
                            t_iParameterIndex,
                            sql);
                }

                if  (exceptionToThrow == null)
                {
                    try
                    {
                        t_Method.invoke(
                            statement,
                            t_iParameterIndex + 1,
                            t_ParameterValue);

                    }
                    catch  (@NotNull final IllegalAccessException illegalAccessException)
                    {
                        if  (t_Log != null)
                        {
                            t_Log.warn(
                                  COULD_NOT_BIND_PARAMETER_VIA
                                + PREPARED_STATEMENT_SET + t_strType
                                + "(int, " + t_Type.getName() + ")",
                                illegalAccessException);
                        }

                        exceptionToThrow =
                            new UnsupportedCustomSqlParameterTypeException(
                                t_strType,
                                t_iParameterIndex,
                                sql,
                                illegalAccessException);
                    }
                    catch  (@NotNull final InvocationTargetException invocationTargetException)
                    {
                        if  (t_Log != null)
                        {
                            t_Log.warn(
                                  COULD_NOT_BIND_PARAMETER_VIA
                                + PREPARED_STATEMENT_SET + t_strType
                                + "(int, " + t_Type.getName() + ")",
                                invocationTargetException);
                        }

                        exceptionToThrow =
                            new UnsupportedCustomSqlParameterTypeException(
                                t_strType,
                                t_iParameterIndex,
                                sql,
                                invocationTargetException);
                    }
                }
                else
                {
                    break;
                }
                t_iParameterIndex++;
            }
        }

        if  (exceptionToThrow != null)
        {
            throw exceptionToThrow;
        }
    }

    /**
     * Retrieves the parameters for given sql element.
     * @param sql such element.
     * @param parameterDAO the {@link SqlParameterDAO} instance.
     * @return the parameter elements.
     */
    protected List<Parameter> retrieveParameterElements(
        @NotNull final Sql sql, @NotNull final SqlParameterDAO parameterDAO)
    {
        @NotNull final List<Parameter> result = new ArrayList<Parameter>();

        Parameter t_Parameter;

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
     * @param metadataTypeManager the metadata type manager.
     * @return the associated setter method.
     */
    protected String getSetterMethod(
        final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            getAccessorMethod(
                "set",  type, metadataTypeManager, StringUtils.getInstance());
    }

    /**
     * Retrieves the getter method name.
     * @param type the data type.
     * @param metadataTypeManager the metadata type manager.
     * @return the associated getter method.
     */
    @NotNull
    protected String getGetterMethod(
        @NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            getAccessorMethod(
                "get", type, metadataTypeManager, StringUtils.getInstance());
    }

    /**
     * Retrieves the accessor method name.
     * @param prefix the prefix (set/get).
     * @param type the data type.
     * @param metadataTypeManager the metadata type manager.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the associated getter method.
     */
    protected String getAccessorMethod(
        @NotNull final String prefix,
        @NotNull final String type,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final StringUtils stringUtils)
    {
        @NotNull String result = prefix;

        if  (   ("Date".equals(type))
             || (JdbcMetadataTypeManager.TIMESTAMP.equals(type)))
        {
            result += JdbcMetadataTypeManager.TIMESTAMP;
        }
        else
        {
            result +=
                stringUtils.capitalize(
                    metadataTypeManager.getNativeType(
                        metadataTypeManager.getJavaType(type)), '|');
        }

        return result;
    }

    /**
     * Validates the result set.
     * @param resultSet the result set to validate.
     * @param sql the sql.
     * @param sqlResult the custom sql result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @throws SQLException if the SQL operation fails.
     * @throws QueryJBuildException if the expected result cannot be extracted.
     */
    protected void validateResultSet(
        @NotNull final ResultSet resultSet,
        @NotNull final Sql sql,
        final Result sqlResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager)
      throws SQLException,
             QueryJBuildException
    {
        @Nullable final Log t_Log = UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

        @NotNull final Collection<Property> t_cProperties =
            retrieveProperties(
                sql,
                sqlResult,
                customSqlProvider.getSqlPropertyDAO(),
                metadataManager,
                metadataTypeManager);
        
        if  (t_cProperties.size() == 0)
        {
            throw new CustomResultWithNoPropertiesException(sqlResult, sql);
        }
        else
        {
            if  (resultSet.next())
            {
                Method t_Method;

                for (@Nullable final Property t_Property : t_cProperties)
                {
                    if (t_Property != null)
                    {
                        try
                        {
                            t_Method =
                                retrieveMethod(
                                    ResultSet.class,
                                    getGetterMethod(
                                        t_Property.getType(),
                                        metadataTypeManager),
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
                             metadataTypeManager))
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
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return such properties.
     * @throws QueryJBuildException if the properties cannot be retrieved..
     */
    @SuppressWarnings("unused")
    @NotNull
    protected List<Property> retrieveProperties(
        @NotNull final Sql sql,
        @NotNull final Result sqlResult,
        @NotNull final SqlPropertyDAO propertyDAO,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager)
      throws  QueryJBuildException
    {
        @NotNull final List<Property> result = new ArrayList<Property>();

        Property t_Property;

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
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return such properties.
     * @throws QueryJBuildException if the properties cannot be retrieved..
     */
    @SuppressWarnings("unused")
    @NotNull
    protected Collection<Property> retrieveImplicitProperties(
        @NotNull final Result sqlResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager)
      throws  QueryJBuildException
    {
        return
            retrieveImplicitProperties(
                sqlResult,
                customSqlProvider,
                metadataManager,
                metadataTypeManager,
                CustomResultUtils.getInstance());
    }

    /**
     * Retrieves the implicit properties declared for given result.
     * @param sqlResult the custom sql result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param customResultUtils the <code>CustomResultUtils</code> instance.
     * @return such properties.
     * @throws QueryJBuildException if the properties cannot be retrieved..
     */
    @NotNull
    protected Collection<Property> retrieveImplicitProperties(
        @NotNull final Result sqlResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final CustomResultUtils customResultUtils)
      throws  QueryJBuildException
    {
        @NotNull final Collection<Property> result = new ArrayList<Property>();

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
            @Nullable String t_strType;

            for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                t_Column = t_lColumns.get(t_iIndex);

                if (t_Column != null)
                {
                    t_strId = t_strTable + "." + t_Column.getName() + ".property";
                
                    t_strType =
                        metadataTypeManager.getNativeType(t_Column.getTypeId());

                    result.add(
                        new PropertyElement(
                            t_strId,
                            t_Column.getName(),
                            t_iIndex + 1,
                            t_strType,
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
        @NotNull final Property property,
        @NotNull final Result sqlResult,
        @NotNull final Sql sql)
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
     * Retrieves the object type.
     * @param type the type name.
     * @param parameterType the parameter type.
     * @return such class.
     */
    @Nullable
    protected Class<?> retrieveType(
        @NotNull final String type, @NotNull final String parameterType)
    {
        @Nullable Class<?> result = null;

        try
        {
            result = Class.forName("java.lang." + type);
        }
        catch  (@NotNull final ClassNotFoundException firstClassNotFoundException)
        {
            // Second try
            try
            {
                result = Class.forName("java.util." + type);
            }
            catch  (@NotNull final ClassNotFoundException secondClassNotFoundException)
            {
                // third try
                try
                {
                    result = Class.forName("java.sql." + type);
                }
                catch  (@NotNull final ClassNotFoundException
                              thirddClassNotFoundException)
                {
                    // fourth try
                    try
                    {
                        result = Class.forName(type);
                    }
                    catch  (@NotNull final ClassNotFoundException
                                  fourthClassNotFoundException)
                    {
                        @Nullable final Log t_Log =
                            UniqueLogFactory.getLog(
                                CustomSqlValidationHandler.class);
                        
                        if  (t_Log != null)
                        {
                            t_Log.warn(
                                "Cannot find class: " + type,
                                fourthClassNotFoundException);
                        }
                    }
                }
            }
        }

        if  (result != null)
        {
            if  (!type.equals(parameterType))
            {
                try
                {
                    result =
                        (Class) result.getDeclaredField("TYPE").get(result);
                }
                catch  (@NotNull final NoSuchFieldException noSuchFieldException)
                {
                    // Nothing to do.
                }
                catch  (@NotNull final IllegalAccessException illegalAccessException)
                {
                    // Nothing to do.
                }
            }

            if  (result.equals(java.util.Date.class))
            {
                result = Timestamp.class;
            }
        }

        return result;
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
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return <code>true</code> in such case.
     */
    protected boolean matches(
        @NotNull final String name,
        final int type,
        final int index,
        @NotNull final Collection<Property> properties,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        boolean result = false;

        int t_iPropertyIndex = 0;

        for (@Nullable final Property t_Property : properties)
        {
            if (t_Property != null)
            {
                final boolean t_bMatches =
                    matches(
                        name, type, index, t_Property, t_iPropertyIndex, metadataTypeManager);

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
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return <code>true</code> in such case.
     */
    @SuppressWarnings("unused")
    protected boolean matches(
        @Nullable final String name,
        final int type,
        final int index,
        @NotNull final Property property,
        final int propertyIndex,
        final MetadataTypeManager metadataTypeManager)
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
        return "{ 'class': 'CustomSqlValidationHandler', 'DATE_FORMAT': '" + DATE_FORMAT + "' }";
    }
}
