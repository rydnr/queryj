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
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds the configuration properties.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.templates.dao.ConfigurationPropertiesTemplate;
import org.acmsl.queryj.tools.templates.dao.ConfigurationPropertiesTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.ConfigurationPropertiesTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Builds the configuration properties.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class ConfigurationPropertiesTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a ConfigurationPropertiesTemplateBuildHandler.
     */
    public ConfigurationPropertiesTemplateBuildHandler() {};

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
        return
            handle(
                command.getAttributeMap(),
                command.getProject(),
                command.getTask());
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(
        final Map parameters, final Project project, final Task task)
        throws  BuildException
    {
        return
            handle(
                parameters,
                buildConfigurationPropertiesTemplate(
                    parameters, project, task));
    }
                
    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param template the template.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition template != null
     */
    protected boolean handle(
        final Map parameters, final ConfigurationPropertiesTemplate template)
      throws  BuildException
    {
        storeConfigurationPropertiesTemplate(template, parameters);

        return false;
    }

    /**
     * Builds a ConfigurationProperties template using the information
     * stored in the attribute map.
     * @param parameters the parameter map.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the template instance.
     * @throws BuildException if the template cannot be created.
     * @precondition parameters != null
     */
    protected ConfigurationPropertiesTemplate buildConfigurationPropertiesTemplate(
        final Map parameters, final Project project, final Task task)
      throws  BuildException
    {
        return
            buildConfigurationPropertiesTemplate(
                parameters,
                retrieveDatabaseMetaData(parameters),
                project,
                task);
    }

    /**
     * Builds a ConfigurationProperties template using the information
     * stored in the attribute map.
     * @param parameters the parameter map.
     * @param metaData the database meta data.
     * @param repository the repository.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the template instance.
     * @throws BuildException if the template cannot be created.
     * @precondition parameters != null
     * @precondition metaData != null
     * @preconditrion repository != null
     */
    protected ConfigurationPropertiesTemplate buildConfigurationPropertiesTemplate(
        final Map parameters,
        final DatabaseMetaData metaData,
        final Project project,
        final Task task)
      throws  BuildException
    {
        ConfigurationPropertiesTemplate result = null;

        try
        {
            result =
                buildConfigurationPropertiesTemplate(
                    parameters,
                    retrieveRepository(parameters),
                    metaData.getDatabaseProductName(),
                    metaData.getDatabaseProductVersion(),
                    retrieveProjectPackage(parameters),
                    retrieveTableNames(parameters),
                    project,
                    task);
        }
        catch  (final SQLException sqlException)
        {
            throw new BuildException(sqlException);
        }

        return result;
    }

    /**
     * Builds a ConfigurationProperties template using the information
     * stored in the attribute map.
     * @param parameters the parameter map.
     * @param repository the repository.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param projectPackage the project package.
     * @param tableNames the table names.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the template instance.
     * @throws BuildException if the template cannot be created.
     * @precondition parameters != null
     * @preconditrion repository != null
     * @precondition engineName != null
     * @precondition projectPackage != null
     * @precondition tableNames != null
     */
    protected ConfigurationPropertiesTemplate buildConfigurationPropertiesTemplate(
        final Map parameters,
        final String repository,
        final String engineName,
        final String engineVersion,
        final String projectPackage,
        final String[] tableNames,
        final Project project,
        final Task task)
      throws  BuildException
    {
        ConfigurationPropertiesTemplate result =
            buildConfigurationPropertiesTemplate(
                repository,
                engineName,
                engineVersion,
                projectPackage,
                project,
                task);

        for  (int t_iTableIndex = 0;
                  t_iTableIndex < tableNames.length;
                  t_iTableIndex++)
        {
            result.addTable(tableNames[t_iTableIndex]);
        }

        return result;
    }

    /**
     * Builds a ConfigurationProperties template using given information.
     * @param repository the repository.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws BuildException whenever the template
     * information is not valid.
     * @precondition repository != null
     * @precondition engineName != null
     * @precondition basePackageName != null
     */
    protected ConfigurationPropertiesTemplate buildConfigurationPropertiesTemplate(
        final String repository,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final Project project,
        final Task task)
      throws  BuildException
    {
        return
            buildConfigurationPropertiesTemplate(
                repository,
                engineName,
                engineVersion,
                basePackageName,
                ConfigurationPropertiesTemplateGenerator.getInstance(),
                project,
                task);
    }

    /**
     * Builds a ConfigurationProperties template using given information.
     * @param repository the repository.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param templateFactory the template factory.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws BuildException whenever the template
     * information is not valid.
     * @precondition repository != null
     * @precondition engineName != null
     * @precondition basePackageName != null
     * @precondition templateFactory != null
     */
    protected ConfigurationPropertiesTemplate buildConfigurationPropertiesTemplate(
        final String repository,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final ConfigurationPropertiesTemplateFactory templateFactory,
        final Project project,
        final Task task)
      throws  BuildException
    {
        ConfigurationPropertiesTemplate result = null;

        try
        {
            result =
                templateFactory.createConfigurationPropertiesTemplate(
                    repository,
                    engineName,
                    engineVersion,
                    basePackageName,
                    project,
                    task);
        }
        catch  (final QueryJException queryjException)
        {
            throw new BuildException(queryjException);
        }

        return result;
    }

    /**
     * Retrieves the repository from the attribute map.
     * @param parameters the parameter map.
     * @return the repository name.
     * @precondition parameters != null
     */
    protected String retrieveRepository(final Map parameters)
    {
        return
            (String) parameters.get(ParameterValidationHandler.REPOSITORY);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition parameters != null
     */
    protected String retrieveProjectPackage(final Map parameters)
    {
        return
            (String) parameters.get(ParameterValidationHandler.PACKAGE);
    }

    /**
     * Retrieves the database metadata from the attribute map.
     * @param parameters the parameter map.
     * @return the metadata.
     * @precondition parameters != null
     */
    protected DatabaseMetaData retrieveDatabaseMetaData(final Map parameters)
    {
        return
            (DatabaseMetaData)
                parameters.get(
                    DatabaseMetaDataRetrievalHandler.DATABASE_METADATA);
    }

    /**
     * Retrieves the table names.
     * @param parameters the parameters.
     * @return the table names.
     * @precondition parameters != null
     */
    protected String[] retrieveTableNames(final Map parameters)
    {
        return
            (String[]) parameters.get(TableTemplateBuildHandler.TABLE_NAMES);
    }

    /**
     * Stores the ConfigurationProperties template in given attribute map.
     * @param configurationPropertiesTemplate the ConfigurationProperties template.
     * @param parameters the parameter map.
     * @precondition configurationPropertiesTemplate != null
     * @precondition parameters != null
     */
    protected void storeConfigurationPropertiesTemplate(
        final ConfigurationPropertiesTemplate configurationPropertiesTemplate,
        final Map parameters)
        throws  BuildException
    {
        parameters.put(
            TemplateMappingManager.CONFIGURATION_PROPERTIES_TEMPLATE,
            configurationPropertiesTemplate);
    }
}
