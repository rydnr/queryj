/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Validates any custom sql queries.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
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
import org.acmsl.queryj.tools.MetaDataUtils;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;
import org.acmsl.commons.utils.ConversionUtils;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Validates any custom sql queries.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
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
    protected static final Class[] CLASS_ARRAY_OF_ONE_STRING =
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
                Project t_Project = t_AntCommand.getProject();

                if  (t_Project != null)
                {
                    t_Project.log(
                        t_AntCommand.getTask(),
                        buildException.getMessage(),
                        Project.MSG_ERR);
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
            command.getProject(),
            command.getTask());

        return result;
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected void handle(
        final Map parameters,
        final Project project,
        final Task task)
      throws  BuildException
    {
        handle(
            retrieveCustomSqlProvider(parameters),
            retrieveConnection(parameters),
            project,
            task);
    }


    /**
     * Handles given information.
     * @param customSqlProvider the custom sql provider.
     * @param connection the connection.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws BuildException if the build process cannot be performed.
     * @precondition customSqlProvider != null
     * @precondition connection != null
     */
    protected void handle(
        final CustomSqlProvider customSqlProvider,
        final Connection connection,
        final Project project,
        final Task task)
      throws  BuildException
    {
        Collection t_cElements = customSqlProvider.getCollection();

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
                                project,
                                task);
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
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws BuildException if the sql is not valid.
     * @precondition sqlElement != null
     * @precondition customSqlProvider != null
     * @precondition connection != null
     */
    protected void validate(
        final SqlElement sqlElement,
        final CustomSqlProvider customSqlProvider,
        final Connection connection,
        final Project project,
        final Task task)
      throws  BuildException
    {
        String t_strSql = sqlElement.getValue();

        SQLException t_ExceptionToWrap = null;

        BuildException t_ExceptionToThrow = null;

        project.log(
            task,
            "Validating [" + t_strSql + "]",
            Project.MSG_VERBOSE);

        PreparedStatement t_PreparedStatement = null;

        // The standard is true, but we assume false.
        boolean t_bLastAutoCommit = false;

        try
        {
            t_bLastAutoCommit = connection.getAutoCommit();
        }
        catch  (final SQLException sqlException)
        {
            project.log(
                task,
                  "Cannot retrieve auto-commit flag: "
                + sqlException.getMessage(),
                Project.MSG_WARN);
        }

        try
        {
            connection.setAutoCommit(false);
        }
        catch  (final SQLException sqlException)
        {
            project.log(
                task,
                  "Cannot set auto-commit flag to false: "
                + sqlException.getMessage(),
                Project.MSG_WARN);
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
                    ConversionUtils.getInstance(),
                    MetaDataUtils.getInstance(),
                    project,
                    task);

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
                project.log(
                    task,
                      "Cannot close prepared statement: "
                    + anotherSqlException.getMessage(),
                    Project.MSG_WARN);
            }

            try
            {
                connection.setAutoCommit(t_bLastAutoCommit);
            }
            catch  (final SQLException sqlException)
            {
                project.log(
                    task,
                      "Cannot restore auto-commit flag: "
                    + sqlException.getMessage(),
                    Project.MSG_WARN);
            }

            try
            {
                connection.rollback();
            }
            catch  (final SQLException sqlException)
            {
                if  (t_ExceptionToWrap == null)
                {
                    t_ExceptionToWrap = sqlException;
                }
                else
                {
                    project.log(
                        task,
                          "Cannot close prepared statement: "
                        + sqlException.getMessage(),
                        Project.MSG_WARN);
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
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @param conversionUtils the <code>ConversionUtils</code> instance.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws SQLException if the binding process fails.
     * @throws BuildException if some problem occurs.
     * @precondition sqlElement != null
     * @precondition statement != null
     * @precondition customSqlProvider != null
     * @precondition conversionUtils != null
     * @precondition metaDataUtils != null
     * @precondition project != null
     */
    protected void bindParameters(
        final SqlElement sqlElement,
        final PreparedStatement statement,
        final CustomSqlProvider customSqlProvider,
        final ConversionUtils conversionUtils,
        final MetaDataUtils metaDataUtils,
        final Project project,
        final Task task)
     throws  SQLException,
             BuildException
    {
        ParameterElement[] t_aParameters =
            retrieveParameterElements(sqlElement, customSqlProvider);

        ParameterElement t_Parameter = null;

        Method t_Method = null;

        Collection t_cSetterParams = new ArrayList();

        Class t_Type = null;

        String t_strType = null;

        for  (int t_iParameterIndex = 0;
                  t_iParameterIndex < t_aParameters.length;
                  t_iParameterIndex++)
        {
            t_Parameter = t_aParameters[t_iParameterIndex];

            t_cSetterParams = new ArrayList();

            t_cSetterParams.add(Integer.TYPE);

            t_strType =
                metaDataUtils.getObjectType(
                    metaDataUtils.getJavaType(
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
                        t_Type = Class.forName(t_strType);
                    }
                    catch  (final ClassNotFoundException thirdClassNotFoundException)
                    {
                        project.log(
                            task,
                            "Cannot find parameter class: " + t_strType,
                            Project.MSG_WARN);
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

                t_cSetterParams.add(t_Type);

                Object t_ParameterValue = null;

                try
                {
                    t_Method =
                        statement.getClass().getDeclaredMethod(
                            "set" + t_strType,
                            (Class[]) t_cSetterParams.toArray(EMPTY_CLASS_ARRAY));

                }
                catch  (final NoSuchMethodException noSuchMethodException)
                {
                    throw new BuildException(
                        "Cannot bind parameter whose type is " + t_strType);
                }

                try
                {
                    Method t_ParameterMethod =
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
                        t_ParameterValue =
                            DATE_FORMAT.parse(
                                t_Parameter.getValidationValue());
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
                            // no way
                        }
                        catch  (final SecurityException securityMethod)
                        {
                            // no way
                        }
                        catch  (final IllegalAccessException illegalAccessException)
                        {
                            // no way
                        }
                        catch  (final InstantiationException instantiationException)
                        {
                            // no way
                        }
                        catch  (final InvocationTargetException invocationTargetException)
                        {
                            // no way
                        }
                    }
                }

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
                    project.log(
                        task,
                          "Could not bind parameter via PreparedStatement.set"
                        + t_strType + "(int, " + t_Type.getName() + ")",
                        Project.MSG_WARN);
                }
                catch  (final InvocationTargetException invocationTargetException)
                {
                    project.log(
                        task,
                          "Could not bind parameter via PreparedStatement.set"
                        + t_strType + "(int, " + t_Type.getName() + ")",
                        Project.MSG_WARN);
                }
            }
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
     * Retrieves the <code>CustomSqlProvider</code> instance.
     * @param parameters the parameter map.
     * @return such instance.
     * @throws BuildException if the provider cannot be stored for any reason.
     * @precondition parameters != null
     */
    protected CustomSqlProvider retrieveCustomSqlProvider(
        final Map parameters)
      throws  BuildException
    {
        return
            (CustomSqlProvider)
                parameters.get(
                    CustomSqlProviderRetrievalHandler.CUSTOM_SQL_PROVIDER);
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

}
