/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile: $
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
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.handlers.CustomSqlProviderRetrievalHandler;
import org.acmsl.queryj.tools.customsql.ParameterElement;
import org.acmsl.queryj.tools.customsql.ParameterRefElement;
import org.acmsl.queryj.tools.customsql.SqlElement;
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
            log);
    }


    /**
     * Handles given information.
     * @param customSqlProvider the custom sql provider.
     * @param connection the connection.
     * @param metadataManager the metadata manager.
     * @param log the log instance.
     * @throws BuildException if the build process cannot be performed.
     * @precondition connection != null
     * @precondition metadataManager != null
     */
    protected void handle(
        final CustomSqlProvider customSqlProvider,
        final Connection connection,
        final MetadataManager metadataManager,
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

                SqlElement t_SqlElement = null;

                while  (t_itElements.hasNext())
                {
                    t_CurrentItem = t_itElements.next();

                    if  (   (t_CurrentItem != null)
                         && (t_CurrentItem instanceof SqlElement))
                    {
                        t_SqlElement = (SqlElement) t_CurrentItem;

                        if  (t_SqlElement.isValidate())
                        {
                            validate(
                                t_SqlElement,
                                customSqlProvider,
                                connection,
                                metadataManager.getMetadataTypeManager(),
                                log);
                        }
                    }
                }
            }
        }
    }

    /**
     * Validates given sql element.
     * @param sqlElement such element.
     * @param customSqlProvider the custom sql provider.
     * @param connection the connection.
     * @param metadataTypeManager the metadata type manager.
     * @param log the log instance.
     * @throws BuildException if the sql is not valid.
     * @precondition sqlElement != null
     * @precondition customSqlProvider != null
     * @precondition connection != null
     * @precondition metadataTypeManager != null
     */
    protected void validate(
        final SqlElement sqlElement,
        final CustomSqlProvider customSqlProvider,
        final Connection connection,
        final MetadataTypeManager metadataTypeManager,
        final QueryJLog log)
      throws  BuildException
    {
        String t_strSql = sqlElement.getValue();

        SQLException t_ExceptionToWrap = null;

        BuildException t_ExceptionToThrow = null;

        if  (log != null)
        {
            log.trace(
                "Validating [" + t_strSql + "]");
        }
        
        PreparedStatement t_PreparedStatement = null;

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
                    sqlElement,
                    t_PreparedStatement,
                    customSqlProvider,
                    metadataTypeManager,
                    ConversionUtils.getInstance(),
                    log);

                if  (   (SqlElement.INSERT.equals(sqlElement.getType()))
                     || (SqlElement.DELETE.equals(sqlElement.getType())))
                {
                    t_PreparedStatement.executeUpdate();
                }
                else
                {
                    t_PreparedStatement.executeQuery();
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
                "Invalid SQL: " + t_strSql,
                t_ExceptionToWrap);
        }
    }

    /**
     * Binds the parameters to given statement.
     * @param sqlElement the sql element.
     * @param statement the prepared statement.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataTypeManager the metadata type manager.
     * @param conversionUtils the <code>ConversionUtils</code> instance.
     * @param log the log instance.
     * @throws SQLException if the binding process fails.
     * @throws BuildException if some problem occurs.
     * @precondition sqlElement != null
     * @precondition statement != null
     * @precondition customSqlProvider != null
     * @precondition metadataTypeManager != null
     * @precondition conversionUtils != null
     */
    protected void bindParameters(
        final SqlElement sqlElement,
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
            retrieveParameterElements(sqlElement, customSqlProvider);

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

            t_Type = null;

            t_strType = null;

            t_Parameter = t_aParameters[t_iParameterIndex];

            t_cSetterParams = new ArrayList();

            t_cSetterParams.add(Integer.TYPE);

            if  (t_Parameter == null)
            {
                exceptionToThrow =
                    new BuildException(
                          "Invalid parameter at position " + t_iParameterIndex
                        + " in sql element whose id is " + sqlElement.getId());

                break;
            }
            else
            {
                t_strType =
                    metadataTypeManager.getObjectType(
                        metadataTypeManager.getJavaType(
                            t_Parameter.getType()));

                try
                {
                    t_Type = Class.forName("java.lang." + t_strType);
                }
                catch  (final ClassNotFoundException firstClassNotFoundException)
                {
                    // Second try
                    try
                    {
                        t_Type = Class.forName("java.util." + t_strType);
                    }
                    catch  (final ClassNotFoundException secondClassNotFoundException)
                    {
                        // third try
                        try
                        {
                            t_Type = Class.forName("java.sql." + t_strType);
                        }
                        catch  (final ClassNotFoundException thirddClassNotFoundException)
                        {
                            // fourth try
                            try
                            {
                                t_Type = Class.forName(t_strType);
                            }
                            catch  (final ClassNotFoundException fourthClassNotFoundException)
                            {
                                if  (log != null)
                                {
                                    log.warn(
                                        "Cannot find parameter class: " + t_strType,
                                        fourthClassNotFoundException);
                                }
                            }
                        }
                    }
                }
            }

            if  (t_Type != null)
            {
                if  (!t_strType.equals(t_Parameter.getType()))
                {
                    try
                    {
                        t_Type =
                            (Class) t_Type.getDeclaredField("TYPE").get(t_Type);
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

                if  (t_Type.equals(java.util.Date.class))
                {
                    t_Type = Timestamp.class;
                }

                t_cSetterParams.add(t_Type);

                Object t_ParameterValue = null;

                try
                {
                    t_Method =
                        statement.getClass().getDeclaredMethod(
                            getSetterMethod(t_strType, metadataTypeManager),
                            (Class[])
                                t_cSetterParams.toArray(EMPTY_CLASS_ARRAY));

                }
                catch  (final NoSuchMethodException noSuchMethodException)
                {
                    exceptionToThrow =
                        new BuildException(
                            "Cannot bind parameter whose type is "
                            + t_strType,
                            noSuchMethodException);
                }

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
                            DATE_FORMAT.parse(
                                t_strValidationValue);

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

                if  (   (exceptionToThrow == null)
                     && (t_Method == null))
                {
                    exceptionToThrow =
                        new BuildException(
                              "Cannot bind parameter [" + t_Parameter.getId()
                            + "] in sql [" + sqlElement.getId() + "]");
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
     * @param sqlElement such element.
     * @param customSqlProvider the custom sql provider.
     * @return the parameter elements.
     * @precondition sqlElement != null
     * @precondition customSqlProvider != null
     */
    protected ParameterElement[] retrieveParameterElements(
        final SqlElement sqlElement,
        final CustomSqlProvider customSqlProvider)
    {
        Collection t_cResult = new ArrayList();

        Collection t_cParameterRefs = sqlElement.getParameterRefs();

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
    protected Connection retrieveConnection(
        final Map parameters)
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
            getSetterMethod(
                type, metadataTypeManager, StringUtils.getInstance());
    }

    /**
     * Retrieves the setter method name.
     * @param type the data type.
     * @param metadataTypeManager the metadata type manager.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the associated setter method.
     * @precondition type != null
     * @precondition metaDataUtils != null
     * @precondition stringUtils != null
     */
    protected String getSetterMethod(
        final String type,
        final MetadataTypeManager metadataTypeManager,
        final StringUtils stringUtils)
    {
        String result = "set";

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
}
