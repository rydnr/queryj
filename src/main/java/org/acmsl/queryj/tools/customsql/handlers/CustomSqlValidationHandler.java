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
package org.acmsl.queryj.tools.customsql.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.customsql.CustomResultUtils;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.ParameterElement;
import org.acmsl.queryj.tools.customsql.ParameterRefElement;
import org.acmsl.queryj.tools.customsql.Property;
import org.acmsl.queryj.tools.customsql.PropertyElement;
import org.acmsl.queryj.tools.customsql.PropertyRefElement;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.customsql.ResultRefElement;
import org.acmsl.queryj.tools.customsql.Sql;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;

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
import java.util.Iterator;
import java.util.Map;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Validates any custom sql queries.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class CustomSqlValidationHandler
    extends  AbstractQueryJCommandHandler
{
    /**
     * A cached empty class array.
     */
    protected static final Class[] EMPTY_CLASS_ARRAY = new Class[0];

    /**
     * A cached class array.
     */
    private static final Class[] CLASS_ARRAY_OF_ONE_STRING =
        new Class[] { String.class };

    /**
     * A cached empty parameter element array.
     */
    protected static final ParameterElement[] EMPTY_PARAMETERELEMENT_ARRAY =
        new ParameterElement[0];

    /**
     * The date format.
     */
    public final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/DD/yyyy");

    /**
     * Creates a <code>CustomSqlValidationHandler</code> instance.
     */
    public CustomSqlValidationHandler() {};

    /**
     * Handles given information.
     *
     *
     * @param parameters the parameters.
     * @return <code>true</code> in case the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(@NotNull final Map parameters)
      throws  QueryJBuildException
    {
        if  (!retrieveDisableCustomSqlValidation(parameters))
        {
            validate(
                retrieveCustomSqlProvider(parameters),
                retrieveConnection(parameters),
                retrieveMetadataManager(parameters));
        }

        return false;
    }

    /**
     * Validates the SQL queries.
     * @param customSqlProvider the custom sql provider.
     * @param connection the connection.
     * @param metadataManager the metadata manager.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition connection != null
     * @precondition metadataManager != null
     */
    protected void validate(
        @Nullable final CustomSqlProvider customSqlProvider,
        @NotNull final Connection connection,
        @NotNull final MetadataManager metadataManager)
      throws  QueryJBuildException
    {
        @Nullable Collection t_cElements = null;

        if  (customSqlProvider != null)
        {
            t_cElements = customSqlProvider.getCollection();
        }

        @Nullable Iterator t_itElements =
            (t_cElements != null)
            ?  t_cElements.iterator()
            :  null;

        if  (t_itElements != null)
        {
            Object t_CurrentItem;

            Sql t_Sql;

            while  (t_itElements.hasNext())
            {
                t_CurrentItem = t_itElements.next();

                if  (t_CurrentItem instanceof Sql)
                {
                    t_Sql = (Sql) t_CurrentItem;

                    if  (t_Sql.isValidate())
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
     * @precondition sql != null
     * @precondition customSqlProvider != null
     * @precondition connection != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
    protected void validate(
        @NotNull final Sql sql,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final Connection connection,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager)
      throws  QueryJBuildException
    {
        String t_strSql = sql.getValue().trim();

        @Nullable SQLException t_ExceptionToWrap = null;

        @Nullable QueryJBuildException t_ExceptionToThrow = null;

        Log t_Log = UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

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

                    ResultRefElement t_ResultRef =
                        sql.getResultRef();
                    
                    if  (t_ResultRef != null)
                    {
                        validateResultSet(
                            t_ResultSet,
                            sql,
                            customSqlProvider.resolveReference(t_ResultRef),
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
            throw
                new QueryJBuildException(
                    "Invalid SQL (" + sql.getId() + "):\n"+ t_strSql,
                    t_ExceptionToWrap);
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
     * @precondition sql != null
     * @precondition statement != null
     * @precondition customSqlProvider != null
     * @precondition metadataTypeManager != null
     * @precondition conversionUtils != null
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

        ParameterElement[] t_aParameters =
            retrieveParameterElements(sql, customSqlProvider);

        Log t_Log = UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

        @Nullable ParameterElement t_Parameter = null;

        @Nullable Method t_Method = null;

        @Nullable Collection<Class> t_cSetterParams = null;

        @Nullable Class<?> t_Type = null;

        @Nullable String t_strType = null;

        int t_iLength = (t_aParameters != null) ? t_aParameters.length : 0;
        
        for  (int t_iParameterIndex = 0;
                  t_iParameterIndex < t_iLength;
                  t_iParameterIndex++)
        {
            t_Method = null;

            t_Parameter = t_aParameters[t_iParameterIndex];

            t_cSetterParams = new ArrayList<Class>();

            t_cSetterParams.add(Integer.TYPE);

            if  (t_Parameter == null)
            {
                exceptionToThrow =
                    new QueryJBuildException(
                          "Invalid parameter at position " + t_iParameterIndex
                        + " in sql element whose id is " + sql.getId());

                break;
            }
            else
            {
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
                            t_cSetterParams.toArray(EMPTY_CLASS_ARRAY));
                }
                catch  (@NotNull final NoSuchMethodException noSuchMethodException)
                {
                    exceptionToThrow =
                        new QueryJBuildException(
                            "Cannot bind parameter whose type is "
                            + t_strType,
                            noSuchMethodException);
                }

                try
                {
                    @Nullable Method t_ParameterMethod = null;

                    if  (   (   ("Date".equals(t_strType))
                             || ("Timestamp".equals(t_strType)))
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
                                    new Object[]
                                    {
                                        t_Parameter.getValidationValue()
                                    });

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
                                new QueryJBuildException(
                                    "No validation value specified for "
                                    + "date parameter [" + t_Parameter.getId() + "]");
                        }
                    }
                    catch  (@NotNull final ParseException parseException)
                    {
                        // We have only once chance: constructor call.
                        try
                        {
                            Constructor t_Constructor =
                                t_Type.getConstructor(CLASS_ARRAY_OF_ONE_STRING);

                            if  (t_Constructor != null)
                            {
                                t_ParameterValue =
                                    t_Constructor.newInstance(
                                        new Object[]
                                        {
                                            t_Parameter.getValidationValue()
                                        });
                            }
                        }
                        catch  (@NotNull final NoSuchMethodException noSuchMethod)
                        {
                            exceptionToThrow =
                                new QueryJBuildException(
                                      "Cannot bind parameter whose type is "
                                    + t_strType,
                                    noSuchMethod);
                        }
                        catch  (@NotNull final SecurityException securityException)
                        {
                            exceptionToThrow =
                                new QueryJBuildException(
                                      "Cannot bind parameter whose type is "
                                    + t_strType,
                                    securityException);
                        }
                        catch  (@NotNull final IllegalAccessException illegalAccessException)
                        {
                            exceptionToThrow =
                                new QueryJBuildException(
                                      "Cannot bind parameter whose type is "
                                    + t_strType,
                                    illegalAccessException);
                        }
                        catch  (@NotNull final InstantiationException instantiationException)
                        {
                            exceptionToThrow =
                                new QueryJBuildException(
                                      "Cannot bind parameter whose type is "
                                    + t_strType,
                                    instantiationException);
                        }
                        catch  (@NotNull final InvocationTargetException invocationTargetException)
                        {
                            exceptionToThrow =
                                new QueryJBuildException(
                                      "Cannot bind parameter whose type is "
                                    + t_strType,
                                    invocationTargetException);
                        }
                    }
                }

                if  (   (exceptionToThrow == null)
                     && (t_Method == null))
                {
                    exceptionToThrow =
                        new QueryJBuildException(
                              "Cannot bind parameter [" + t_Parameter.getId()
                            + "] in sql [" + sql.getId() + "]");
                }

                if  (exceptionToThrow == null)
                {
                    try
                    {
                        t_Method.invoke(
                            statement,
                            new Object[]
                            {
                                Integer.valueOf(t_iParameterIndex + 1),
                                t_ParameterValue
                            });
                        
                    }
                    catch  (@NotNull final IllegalAccessException illegalAccessException)
                    {
                        if  (t_Log != null)
                        {
                            t_Log.warn(
                                  "Could not bind parameter via "
                                + "PreparedStatement.set" + t_strType
                                + "(int, " + t_Type.getName() + ")",
                                illegalAccessException);
                        }

                        exceptionToThrow =
                            new QueryJBuildException(
                                "Cannot bind parameter whose type is "
                                + t_strType,
                                illegalAccessException);
                    }
                    catch  (@NotNull final InvocationTargetException invocationTargetException)
                    {
                        if  (t_Log != null)
                        {
                            t_Log.warn(
                                  "Could not bind parameter via "
                                + "PreparedStatement.set" + t_strType
                                + "(int, " + t_Type.getName() + ")",
                                invocationTargetException);
                        }

                        exceptionToThrow =
                            new QueryJBuildException(
                                "Cannot bind parameter whose type is "
                                + t_strType,
                                invocationTargetException);
                    }
                }
                else
                {
                    break;
                }
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
     * @param customSqlProvider the custom sql provider.
     * @return the parameter elements.
     * @precondition sql != null
     * @precondition customSqlProvider != null
     */
    protected ParameterElement[] retrieveParameterElements(
        @NotNull final Sql sql, @NotNull final CustomSqlProvider customSqlProvider)
    {
        @NotNull Collection<ParameterElement> t_cResult = new ArrayList<ParameterElement>();

        Collection t_cParameterRefs = sql.getParameterRefs();

        if  (t_cParameterRefs != null)
        {
            Iterator t_itParameterRefs = t_cParameterRefs.iterator();

            if  (t_itParameterRefs != null)
            {
                while  (t_itParameterRefs.hasNext())
                {
                    t_cResult.add(
                        customSqlProvider.resolveReference(
                            (ParameterRefElement) t_itParameterRefs.next()));
                }
            }
        }

        return t_cResult.toArray(EMPTY_PARAMETERELEMENT_ARRAY);
    }

    /**
     * Retrieves the setter method name.
     * @param type the data type.
     * @param metadataTypeManager the metadata type manager.
     * @return the associated setter method.
     * @precondition type != null
     * @precondition metadataTypeManager the metadata type manager.
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
     * @precondition type != null
     * @precondition metadataTypeManager the metadata type manager.
     */
    protected String getGetterMethod(
        final String type, @NotNull final MetadataTypeManager metadataTypeManager)
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
     * @precondition type != null
     * @precondition metaDataUtils != null
     * @precondition stringUtils != null
     */
    protected String getAccessorMethod(
        final String prefix,
        final String type,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final StringUtils stringUtils)
    {
        String result = prefix;

        if  (   ("Date".equals(type))
             || ("Timestamp".equals(type)))
        {
            result += "Timestamp";
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
     * @precondition resultSet != null
     * @precondition sql != null
     * @precondition sqlResult != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
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
        Log t_Log = UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

        @NotNull Collection t_cProperties =
            retrieveProperties(
                sql,
                sqlResult,
                customSqlProvider,
                metadataManager,
                metadataTypeManager);
        
        if  (   (t_cProperties == null)
             || (t_cProperties.size() == 0))
        {
            throw
                new QueryJBuildException(
                    "No return properties for " + sql.getId());
        }
        else
        {
            if  (resultSet.next())
            {
                Iterator t_Iterator = t_cProperties.iterator();

                if  (t_Iterator != null)
                {
                    Method t_Method;

                    Property t_Property;

                    while  (t_Iterator.hasNext())
                    {
                        t_Property = (Property) t_Iterator.next();

                        try
                        {
                            t_Method =
                                retrieveMethod(
                                    ResultSet.class,
                                    getGetterMethod(
                                        t_Property.getType(),
                                        metadataTypeManager),
                                    new Class[]
                                    {
                                        (t_Property.getIndex() > 0)
                                        ?  Integer.TYPE
                                        :  String.class
                                    });
                        }
                        catch  (@NotNull final NoSuchMethodException noSuchMethod)
                        {
                            throw
                                new QueryJBuildException(
                                      "Cannot retrieve result for "
                                    + "property type "
                                    + t_Property.getType()
                                    + " (" + t_Property.getId() + ")",
                                    noSuchMethod);
                        }

                        invokeResultSetGetter(
                            t_Method, resultSet, t_Property, sql);
                    }
                }
            }
            else if  (t_cProperties != null)
            {
                ResultSetMetaData t_Metadata = resultSet.getMetaData();

                int t_iColumnCount = t_Metadata.getColumnCount();
                
                if  (t_iColumnCount < t_cProperties.size())
                {
                    throw
                        new QueryJBuildException(
                              "Invalid number of columns ("
                            + t_iColumnCount + "): "
                            + "expecting at least " + t_cProperties.size());
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
     * @param resultSet the result set to validate.
     * @param sql the sql.
     * @param sqlResult the custom sql result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return such properties.
     * @throws QueryJBuildException if the properties cannot be retrieved..
     * @precondition sql != null
     * @precondition sqlResult != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
    @NotNull
    protected Collection<Property> retrieveProperties(
        final Sql sql,
        @Nullable final Result sqlResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager)
      throws  QueryJBuildException
    {
        @NotNull Collection<Property> result = new ArrayList<Property>();
        
        @Nullable Collection<PropertyRefElement> t_cPropertyRefs =
            (sqlResult != null) ? sqlResult.getPropertyRefs() : null;

        if  (t_cPropertyRefs == null)
        {
            result =
                retrieveImplicitProperties(
                    sqlResult,
                    customSqlProvider,
                    metadataManager,
                    metadataTypeManager);
        }
        else
        {
            Iterator<PropertyRefElement> t_Iterator = t_cPropertyRefs.iterator();

            Object t_Item;
            @Nullable Property t_Property;

            if  (t_Iterator != null)
            {
                while  (t_Iterator.hasNext())
                {
                    t_Property =
                        customSqlProvider.resolveReference(t_Iterator.next());

                    if  (t_Property != null)
                    {
                        result.add(t_Property);
                    }
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
     * @precondition sql != null
     * @precondition sqlResult != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
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
     * @precondition sql != null
     * @precondition sqlResult != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition customResultUtils != null
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
        @NotNull Collection<Property> result = new ArrayList<Property>();

        @Nullable String t_strTable =
            customResultUtils.retrieveTable(
                sqlResult,
                customSqlProvider,
                metadataManager);

        if  (t_strTable != null)
        {
            String[] t_astrColumnNames =
                metadataManager.getColumnNames(t_strTable);

            int t_iCount =
                (t_astrColumnNames != null)
                ? t_astrColumnNames.length : 0;

            String t_strId;
            String t_strColumnName;
            String t_strName;
            @Nullable String t_strType;
            boolean t_bNullable;

            for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                t_strColumnName = t_astrColumnNames[t_iIndex];

                t_strId = t_strTable + "." + t_strColumnName + ".property";
                
                t_strName = t_strColumnName;
                t_strType =
                    metadataTypeManager.getNativeType(
                        metadataManager.getColumnType(
                            t_strTable, t_strColumnName));
                
                t_bNullable =
                    metadataManager.allowsNull(t_strTable, t_strColumnName);

                result.add(
                    new PropertyElement(
                        t_strId,
                        t_strColumnName,
                        t_iIndex + 1,
                        t_strName,
                        t_strType,
                        t_bNullable));
            }
        }
        else
        {
            @NotNull String t_strErrorMessage =
                "Cannot retrieve table associated to SQL result " + sqlResult.getId();

            Log t_Log =
                UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

            if  (t_Log != null)
            {
                t_Log.warn(t_strErrorMessage);
            }

            throw new QueryJBuildException(t_strErrorMessage);
        }

        return result;
    }
    
    /**
     * Executes the <code>ResultSet.getXXX</code> method.
     * @param method the <code>ResultSet</code> getter method for given property.
     * @param resultSet the <code>ResultSet</code> instance.
     * @param property the property.
     * @param sql the SQL element.
     * @throws QueryJBuildException if the validation fails.
     * @precondition method != null
     * @precondition resultSet != null
     * @precondition property != null
     * @precondition sql != null
     */
    protected void invokeResultSetGetter(
        @NotNull final Method method,
        final ResultSet resultSet,
        @NotNull final Property property,
        @NotNull final Sql sql)
      throws QueryJBuildException
    {
        Log t_Log = UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

        try
        {
            @NotNull Object[] t_aParameters = new Object[1];

            if  (property.getIndex() > 0)
            {
                t_aParameters[0] =
                    Integer.valueOf(property.getIndex());
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
                      "Validation failed for " + sql.getId() + ":\n"
                    + "Could not retrieve result via "
                    + "ResultSet." + method.getName()
                    + "("
                    + (   (property.getIndex() > 0)
                       ?  "" + property.getIndex()
                       :  property.getColumnName())
                    + ")",
                    illegalAccessException);
            }

            throw
                new QueryJBuildException(
                      "Could not retrieve result property "
                    + (   (property.getIndex() > 0)
                       ?  "" + property.getIndex()
                       :  property.getColumnName()),
                    illegalAccessException);
        }
        catch  (@NotNull final InvocationTargetException invocationTargetException)
        {
            if  (t_Log != null)
            {
                t_Log.warn(
                      "Validation failed for " + sql.getId() + ":\n"
                    + "Could not retrieve result via "
                    + "ResultSet." + method.getName()
                    + "("
                    + (   (property.getIndex() > 0)
                       ?  "" + property.getIndex()
                       :  property.getColumnName())
                    + ")",
                    invocationTargetException);
            }

            throw
                new QueryJBuildException(
                      "Validation failed for " + sql.getId() + ":\n"
                    + "Could not retrieve result property "
                    + (   (property.getIndex() > 0)
                       ?  "" + property.getIndex()
                       :  property.getColumnName()),
                    invocationTargetException);
        }
    }

    /**
     * Retrieves the object type.
     * @param type the type name.
     * @param parameterType the parameter type.
     * @return such class.
     * @precondition type != null
     * @precondition parameterType != null
     */
    @Nullable
    protected Class retrieveType(
        @NotNull final String type, final String parameterType)
    {
        @Nullable Class result = null;

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
                        Log t_Log =
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
     * @precondition instanceClass != null
     * @precondition methodName != null
     */
    protected Method retrieveMethod(
        @NotNull final Class<?> instanceClass, final String methodName, final Class[] parameterClasses)
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
     * @precondition properties != null
     * @precondition metadataTypeManager != null
     */
    protected boolean matches(
        final String name,
        final int type,
        final int index,
        @Nullable final Collection properties,
        final MetadataTypeManager metadataTypeManager)
    {
        boolean result = false;

        @Nullable Iterator t_Iterator =
            (properties != null) ? properties.iterator() : null;
        
        if  (t_Iterator != null)
        {
            Object t_Item;
            int t_iPropertyIndex = 1;

            while  (t_Iterator.hasNext())
            {
                t_Item = t_Iterator.next();
               
                if  (t_Item instanceof Property)
                {
                    if  (matches(
                             name,
                             type,
                             index,
                             (Property) t_Item,
                             t_iPropertyIndex,
                             metadataTypeManager))
                    {
                        result = true;
                        break;
                    }
                    t_iPropertyIndex++;
                }
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
     * @precondition property != null
     * @precondition metadataTypeManager != null
     */
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
             && (!name.equalsIgnoreCase(property.getName()))
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
     * @precondition settings != null
     */
    protected boolean retrieveDisableCustomSqlValidation(@NotNull final Map settings)
    {
        boolean result = false;

        @NotNull Boolean flag =
            (Boolean)
                settings.get(
                    ParameterValidationHandler.DISABLE_CUSTOM_SQL_VALIDATION);

        if  (flag != null)
        {
            result = flag.booleanValue();
        }

        return result;
    }
}
