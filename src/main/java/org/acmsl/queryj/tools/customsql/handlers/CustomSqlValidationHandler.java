/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

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
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.customsql.CustomResultUtils;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.handlers.CustomSqlProviderRetrievalHandler;
import org.acmsl.queryj.tools.customsql.ParameterElement;
import org.acmsl.queryj.tools.customsql.ParameterRefElement;
import org.acmsl.queryj.tools.customsql.Property;
import org.acmsl.queryj.tools.customsql.PropertyElement;
import org.acmsl.queryj.tools.customsql.PropertyRefElement;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.customsql.ResultRefElement;
import org.acmsl.queryj.tools.customsql.Sql;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.handlers.JdbcConnectionOpeningHandler;
import org.acmsl.queryj.tools.logging.QueryJLog;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;
import org.acmsl.commons.utils.ConversionUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

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

/**
 * Validates any custom sql queries.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class CustomSqlValidationHandler
    extends  AbstractAntCommandHandler
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
     * Creates a CustomSqlValidationHandler.
     */
    public CustomSqlValidationHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     */
    public boolean handle(final Command command)
    {
        boolean result = false;

        if  (command instanceof AntCommand) 
        {
            AntCommand t_AntCommand = (AntCommand) command;

            try 
            {
                result = handle(t_AntCommand);
            }
            catch  (final BuildException buildException)
            {
                QueryJLog t_Log = t_AntCommand.getLog();

                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot validate custom SQL information.",
                        buildException);
                }
            }
        }
        
        return result;
    }

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition command != null
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        boolean result = false;

        handle(
            command.getAttributeMap(),
            command.getLog());

        return result;
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param log the log instance.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected void handle(
        final Map parameters,
        final QueryJLog log)
      throws  BuildException
    {
        handle(
            retrieveCustomSqlProvider(parameters),
            retrieveConnection(parameters),
            retrieveMetadataManager(parameters),
            CustomResultUtils.getInstance(),
            ConversionUtils.getInstance(),
            log);
    }


    /**
     * Handles given information.
     * @param customSqlProvider the custom sql provider.
     * @param connection the connection.
     * @param metadataManager the metadata manager.
     * @param customResultUtils the <code>CustomResultUtils</code> instance.
     * @param conversionUtils the <code>ConversionUtils</code> instance.
     * @param log the log instance.
     * @throws BuildException if the build process cannot be performed.
     * @precondition connection != null
     * @precondition metadataManager != null
     */
    protected void handle(
        final CustomSqlProvider customSqlProvider,
        final Connection connection,
        final MetadataManager metadataManager,
        final CustomResultUtils customResultUtils,
        final ConversionUtils conversionUtils,
        final QueryJLog log)
      throws  BuildException
    {
        Collection t_cElements = null;

        if  (customSqlProvider != null)
        {
            t_cElements = customSqlProvider.getCollection();
        }

        if  (t_cElements != null)
        {
            Iterator t_itElements = t_cElements.iterator();

            if  (t_itElements != null)
            {
                Object t_CurrentItem = null;

                Sql t_Sql = null;

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
                                metadataManager.getMetadataTypeManager(),
                                customResultUtils,
                                conversionUtils,
                                log);
                        }
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
     * @param customResultUtils the <code>CustomResultUtils</code> instance.
     * @param conversionUtils the <code>ConversionUtils</code> instance.
     * @param log the log instance.
     * @throws BuildException if the sql is not valid.
     * @precondition sql != null
     * @precondition customSqlProvider != null
     * @precondition connection != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition customResultUtils != null
     * @precondition conversionUtils != null
     */
    protected void validate(
        final Sql sql,
        final CustomSqlProvider customSqlProvider,
        final Connection connection,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomResultUtils customResultUtils,
        final ConversionUtils conversionUtils,
        final QueryJLog log)
      throws  BuildException
    {
        String t_strSql = sql.getValue().trim();

        SQLException t_ExceptionToWrap = null;

        BuildException t_ExceptionToThrow = null;

        if  (log != null)
        {
            log.trace(
                "Validating " + sql.getId() + " [\n" + t_strSql + "\n]");
        }
        
        PreparedStatement t_PreparedStatement = null;
        ResultSet t_ResultSet = null;

        // The standard is true, but we assume false.
        boolean t_bLastAutoCommit = false;

        try
        {
            t_bLastAutoCommit = connection.getAutoCommit();
        }
        catch  (final SQLException sqlException)
        {
            if  (log != null)
            {
                log.warn(
                    "Cannot retrieve auto-commit flag.",
                    sqlException);
            }
        }

        try
        {
            connection.setAutoCommit(false);
        }
        catch  (final SQLException sqlException)
        {
            if  (log != null)
            {
                log.warn(
                    "Cannot set auto-commit flag to false.",
                    sqlException);
            }
        }

        try
        {
            t_PreparedStatement =
                connection.prepareStatement(t_strSql);
        }
        catch  (final SQLException sqlException)
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
                    ConversionUtils.getInstance(),
                    log);

                if  (   (Sql.INSERT.equals(sql.getType()))
                     || (Sql.UPDATE.equals(sql.getType()))
                     || (Sql.DELETE.equals(sql.getType())))
                {
                    t_PreparedStatement.executeUpdate();
                }
                else
                {
                    t_ResultSet = t_PreparedStatement.executeQuery();

                    ResultRefElement t_ResultRef =
                        sql.getResultRef();
                    
                    Result t_Result = null;
                    
                    if  (t_ResultRef != null)
                    {
                        t_Result =
                            customSqlProvider.resolveReference(t_ResultRef);
                    }
                    else
                    {
                        throw new BuildException("No <result-ref> specified.");
                    }
                    
                    if  (t_Result != null)
                    {
                        validateResultSet(
                            t_ResultSet,
                            sql,
                            t_Result,
                            customSqlProvider,
                            metadataManager,
                            metadataTypeManager,
                            customResultUtils,
                            log);
                    }
                    else
                    {
                        throw
                            new BuildException(
                                  "Invalid <result-ref> specified. No <result id=\""
                                + t_ResultRef.getId() + "\"> found.");
                    }
                }
            }
            catch  (final SQLException sqlException)
            {
                t_ExceptionToWrap = sqlException;
            }
            catch  (final BuildException buildException)
            {
                t_ExceptionToThrow = buildException;
            }

            if  (t_ResultSet != null)
            {
                try
                {
                    t_ResultSet.close();
                }
                catch  (final SQLException sqlException)
                {
                    if  (log != null)
                    {
                        log.warn(
                            "Cannot close result set.",
                            sqlException);
                    }
                }
            }
            try
            {
                t_PreparedStatement.close();
            }
            catch  (final SQLException anotherSqlException)
            {
                if  (log != null)
                {
                    log.warn(
                        "Cannot close prepared statement.",
                        anotherSqlException);
                }
            }

            try
            {
                connection.setAutoCommit(t_bLastAutoCommit);
            }
            catch  (final SQLException sqlException)
            {
                if  (log != null)
                {
                    log.warn(
                        "Cannot restore auto-commit flag.",
                        sqlException);
                }
            }

            try
            {
                connection.rollback();
            }
            catch  (final SQLException sqlException)
            {
                if  (log != null)
                {
                    log.warn(
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
            throw new BuildException(
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
     * @param log the log instance.
     * @throws SQLException if the binding process fails.
     * @throws BuildException if some problem occurs.
     * @precondition sql != null
     * @precondition statement != null
     * @precondition customSqlProvider != null
     * @precondition metadataTypeManager != null
     * @precondition conversionUtils != null
     */
    protected void bindParameters(
        final Sql sql,
        final PreparedStatement statement,
        final CustomSqlProvider customSqlProvider,
        final MetadataTypeManager metadataTypeManager,
        final ConversionUtils conversionUtils,
        final QueryJLog log)
     throws  SQLException,
             BuildException
    {
        BuildException exceptionToThrow = null;

        ParameterElement[] t_aParameters =
            retrieveParameterElements(sql, customSqlProvider);

        ParameterElement t_Parameter = null;

        Method t_Method = null;

        Collection t_cSetterParams = null;

        Class t_Type = null;

        String t_strType = null;

        int t_iLength = (t_aParameters != null) ? t_aParameters.length : 0;
        
        for  (int t_iParameterIndex = 0;
                  t_iParameterIndex < t_iLength;
                  t_iParameterIndex++)
        {
            t_Method = null;

            t_Parameter = t_aParameters[t_iParameterIndex];

            t_cSetterParams = new ArrayList();

            t_cSetterParams.add(Integer.TYPE);

            if  (t_Parameter == null)
            {
                exceptionToThrow =
                    new BuildException(
                          "Invalid parameter at position " + t_iParameterIndex
                        + " in sql element whose id is " + sql.getId());

                break;
            }
            else
            {
                t_strType =
                    metadataTypeManager.getObjectType(
                        metadataTypeManager.getJavaType(
                            t_Parameter.getType()), false);

                t_Type = retrieveType(t_strType, t_Parameter.getType(), log);
            }

            if  (t_Type != null)
            {
                t_cSetterParams.add(t_Type);

                Object t_ParameterValue = null;

                try
                {
                    t_Method =
                        retrieveMethod(
                            statement.getClass(),
                            getSetterMethod(t_strType, metadataTypeManager),
                            (Class[]) t_cSetterParams.toArray(EMPTY_CLASS_ARRAY));
                }
                catch  (final NoSuchMethodException noSuchMethodException)
                {
                    exceptionToThrow =
                        new BuildException(
                              "Cannot bind parameter whose type is "
                            + t_strType,
                            noSuchMethodException);
                }

                if  (exceptionToThrow == null)
                {
                    try
                    {
                        Method t_ParameterMethod = null;

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
                    catch  (final NoSuchMethodException noSuchMethod)
                    {
                        // it's not a plain type.
                    }
                    catch  (final SecurityException securityMethod)
                    {
                        // can do little
                    }
                    catch  (final IllegalAccessException illegalAccessException)
                    {
                        // can do little
                    }
                    catch  (final InvocationTargetException invocationTargetException)
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
                                DATE_FORMAT.parse(t_strValidationValue);

                            if  (t_bInvalidValidationValue)
                            {
                                exceptionToThrow =
                                    new BuildException(
                                          "No validation value specified for "
                                        + "date parameter [" + t_Parameter.getId() + "]");
                            }
                        }
                        catch  (final ParseException parseException)
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
                            catch  (final NoSuchMethodException noSuchMethod)
                            {
                                exceptionToThrow =
                                    new BuildException(
                                          "Cannot bind parameter whose type is "
                                        + t_strType,
                                        noSuchMethod);
                            }
                            catch  (final SecurityException securityException)
                            {
                                exceptionToThrow =
                                    new BuildException(
                                          "Cannot bind parameter whose type is "
                                        + t_strType,
                                        securityException);
                            }
                            catch  (final IllegalAccessException illegalAccessException)
                            {
                                exceptionToThrow =
                                    new BuildException(
                                          "Cannot bind parameter whose type is "
                                        + t_strType,
                                        illegalAccessException);
                            }
                            catch  (final InstantiationException instantiationException)
                            {
                                exceptionToThrow =
                                    new BuildException(
                                          "Cannot bind parameter whose type is "
                                        + t_strType,
                                        instantiationException);
                            }
                            catch  (final InvocationTargetException invocationTargetException)
                            {
                                exceptionToThrow =
                                    new BuildException(
                                          "Cannot bind parameter whose type is "
                                        + t_strType,
                                        invocationTargetException);
                            }
                        }
                    }
                }

                if  (   (exceptionToThrow == null)
                     && (t_Method == null))
                {
                    exceptionToThrow =
                        new BuildException(
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
                                new Integer(t_iParameterIndex + 1),
                                t_ParameterValue
                            });
                        
                    }
                    catch  (final IllegalAccessException illegalAccessException)
                    {
                        if  (log != null)
                        {
                            log.warn(
                                  "Could not bind parameter via "
                                + "PreparedStatement.set" + t_strType
                                + "(int, " + t_Type.getName() + ")",
                                illegalAccessException);
                        }

                        exceptionToThrow =
                            new BuildException(
                                "Cannot bind parameter whose type is "
                                + t_strType,
                                illegalAccessException);
                    }
                    catch  (final InvocationTargetException invocationTargetException)
                    {
                        if  (log != null)
                        {
                            log.warn(
                                  "Could not bind parameter via "
                                + "PreparedStatement.set" + t_strType
                                + "(int, " + t_Type.getName() + ")",
                                invocationTargetException);
                        }

                        exceptionToThrow =
                            new BuildException(
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
        final Sql sql, final CustomSqlProvider customSqlProvider)
    {
        Collection t_cResult = new ArrayList();

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

        return
            (ParameterElement[])
                t_cResult.toArray(EMPTY_PARAMETERELEMENT_ARRAY);
    }

    /**
     * Retrieves the <code>Connection</code> instance.
     * @param parameters the parameter map.
     * @return such instance.
     * @throws BuildException if the provider cannot be stored for any reason.
     * @precondition parameters != null
     */
    protected Connection retrieveConnection(final Map parameters)
      throws  BuildException
    {
        return
            (Connection)
                parameters.get(
                    JdbcConnectionOpeningHandler.JDBC_CONNECTION);
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
        final String type, final MetadataTypeManager metadataTypeManager)
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
        final String type, final MetadataTypeManager metadataTypeManager)
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
        final MetadataTypeManager metadataTypeManager,
        final StringUtils stringUtils)
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
     * @param customResultUtils the <code>CustomResultUtils</code> instance.
     * @param log the log.
     * @throws SQLException if the SQL operation fails.
     * @throws BuildException if the expected result cannot be extracted.
     * @precondition resultSet != null
     * @precondition sql != null
     * @precondition sqlResult != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition customResultUtils != null
     */
    protected void validateResultSet(
        final ResultSet resultSet,
        final Sql sql,
        final Result sqlResult,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomResultUtils customResultUtils,
        final QueryJLog log)
      throws SQLException,
             BuildException
    {
        Collection t_cProperties =
            retrieveProperties(
                sql,
                sqlResult,
                customSqlProvider,
                metadataManager,
                metadataTypeManager,
                customResultUtils,
                log);
        
        if  (   (t_cProperties == null)
             || (t_cProperties.size() == 0))
        {
            throw new BuildException("No return properties for " + sql.getId());
        }
        else
        {
            if  (resultSet.next())
            {
                Iterator t_Iterator =
                    (t_cProperties != null) ? t_cProperties.iterator() : null;

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
                        catch  (final NoSuchMethodException noSuchMethod)
                        {
                            throw
                                new BuildException(
                                      "Cannot retrieve result for property type "
                                    + t_Property.getType()
                                    + " (" + t_Property.getId() + ")",
                                    noSuchMethod);
                        }

                        invokeResultSetGetter(
                            t_Method, resultSet, t_Property, sql, log);
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
                        new BuildException(
                              "Invalid number of columns (" + t_iColumnCount + "): "
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
                        log.warn(
                              "Column not mapped ("
                            + t_iIndex + ", "
                            + t_strColumnName
                            + ", " + t_iColumnType + ")");
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
     * @param customResultUtils the <code>CustomResultUtils</code> instance.
     * @param log the log.
     * @return such properties.
     * @precondition sql != null
     * @precondition sqlResult != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition log != null
     */
    protected Collection retrieveProperties(
        final Sql sql,
        final Result sqlResult,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomResultUtils customResultUtils,
        final QueryJLog log)
    {
        Collection result = new ArrayList();
        
        Collection t_cPropertyRefs =
            (sqlResult != null) ? sqlResult.getPropertyRefs() : null;

        if  (t_cPropertyRefs == null)
        {
            result =
                retrieveImplicitProperties(
                    sqlResult,
                    customSqlProvider,
                    metadataManager,
                    metadataTypeManager,
                    customResultUtils,
                    log);
        }
        else
        {
            Iterator t_Iterator = t_cPropertyRefs.iterator();

            Object t_Item;
            Property t_Property;

            if  (t_Iterator != null)
            {
                while  (t_Iterator.hasNext())
                {
                    t_Item = t_Iterator.next();

                    if  (t_Item instanceof PropertyRefElement)
                    {
                        t_Property =
                            customSqlProvider.resolveReference(
                                (PropertyRefElement) t_Item);

                        if  (t_Property != null)
                        {
                            result.add(t_Property);
                        }
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
     * @param customResultUtils the <code>CustomResultUtils</code> instance.
     * @param log the log.
     * @return such properties.
     * @precondition sqlResult != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition customResultUtils != null
     * @precondition log != null
     */
    protected Collection retrieveImplicitProperties(
        final Result sqlResult,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomResultUtils customResultUtils,
        final QueryJLog log)
    {
        Collection result = new ArrayList();
        
        Property implicitProperty = sqlResult.getImplicitProperty();
        
        if  (implicitProperty != null)
        {
            result.add(implicitProperty);
        }
        else
        {
            String t_strTable =
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
                String t_strType;
                boolean t_bNullable;
                boolean t_bReadOnly;
                boolean t_bIsBool;
                String t_strBooleanTrue;
                String t_strBooleanFalse;
                String t_strBooleanNull;

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

                    t_bReadOnly = 
                        metadataManager.isReadOnly(
                            t_strTable, t_strColumnName);

                    t_bIsBool =
                        metadataManager.isBoolean(
                            t_strTable, t_strColumnName);

                    t_strBooleanTrue =
                        metadataManager.getBooleanTrue(
                            t_strTable, t_strColumnName);
                    
                    t_strBooleanFalse =
                        metadataManager.getBooleanFalse(
                            t_strTable, t_strColumnName);
                    
                    t_strBooleanNull =
                        metadataManager.getBooleanNull(
                            t_strTable, t_strColumnName);
                    
                    result.add(
                        new PropertyElement(
                            t_strId,
                            t_strColumnName,
                            t_iIndex + 1,
                            t_strName,
                            t_strType,
                            t_bNullable,
                            t_bReadOnly,
                            t_bIsBool,
                            t_strBooleanTrue,
                            t_strBooleanFalse,
                            t_strBooleanNull));
                }
            }
            else
            {
                String t_strErrorMessage =
                    "Cannot retrieve table associated to SQL result " + sqlResult.getId();

                if  (log != null)
                {
                    log.warn(t_strErrorMessage);
                }

                throw new BuildException(t_strErrorMessage);
            }
        }
        
        return result;
    }
    
    /**
     * Executes the <code>ResultSet.getXXX</code> method.
     * @param method the <code>ResultSet</code> getter method for given property.
     * @param resultSet the <code>ResultSet</code> instance.
     * @param property the property.
     * @param sql the SQL element.
     * @param log the log.
     * @precondition method != null
     * @precondition resultSet != null
     * @precondition property != null
     * @precondition sql != null
     * @precondition log != null
     */
    protected void invokeResultSetGetter(
        final Method method,
        final ResultSet resultSet,
        final Property property,
        final Sql sql,
        final QueryJLog log)
    {
        try
        {
            Object[] t_aParameters = new Object[1];

            if  (property.getIndex() > 0)
            {
                t_aParameters[0] =
                    new Integer(property.getIndex());
            }
            else
            {
                t_aParameters[0] =
                    property.getColumnName();
            }

            method.invoke(resultSet, t_aParameters);
        }
        catch  (final IllegalAccessException illegalAccessException)
        {
            throw
                new BuildException(
                      "Could not retrieve result property "
                    + (   (property.getIndex() > 0)
                       ?  "" + property.getIndex()
                       :  property.getColumnName()),
                    illegalAccessException);
        }
        catch  (final InvocationTargetException invocationTargetException)
        {
            throw
                new BuildException(
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
     * @param log the log.
     * @return such class.
     * @precondition type != null
     * @precondition parameterType != null
     */
    protected Class retrieveType(
        final String type, final String parameterType, final QueryJLog log)
    {
        Class result = null;

        try
        {
            result = Class.forName("java.lang." + type);
        }
        catch  (final ClassNotFoundException firstClassNotFoundException)
        {
            // Second try
            try
            {
                result = Class.forName("java.util." + type);
            }
            catch  (final ClassNotFoundException secondClassNotFoundException)
            {
                // third try
                try
                {
                    result = Class.forName("java.sql." + type);
                }
                catch  (final ClassNotFoundException thirddClassNotFoundException)
                {
                    // fourth try
                    try
                    {
                        result = Class.forName(type);
                    }
                    catch  (final ClassNotFoundException fourthClassNotFoundException)
                    {
                        if  (log != null)
                        {
                            log.warn(
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
                catch  (final NoSuchFieldException noSuchFieldException)
                {
                    // Nothing to do.
                }
                catch  (final IllegalAccessException illegalAccessException)
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
        final Class instanceClass, final String methodName, final Class[] parameterClasses)
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
        final Collection properties,
        final MetadataTypeManager metadataTypeManager)
    {
        boolean result = false;

        Iterator t_Iterator =
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
        final String name,
        final int type,
        final int index,
        final Property property,
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
}
