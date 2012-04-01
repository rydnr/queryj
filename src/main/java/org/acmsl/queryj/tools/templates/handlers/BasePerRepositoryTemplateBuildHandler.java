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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: BasePerRepositoryTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a per-repository templates using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.QueryJCommand;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.DefaultBasePerRepositoryTemplateFactory;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplate;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateFactory;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Builds a per-repository template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class BasePerRepositoryTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a {@link BasePerRepositoryTemplateBuildHandler} instance.
     */
    public BasePerRepositoryTemplateBuildHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the process fails unexpectedly.
     * @precondition command != null
     */
    @Override
    public boolean handle(final QueryJCommand command)
        throws  QueryJBuildException
    {
        return handle(command.getAttributeMap());
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
        throws  QueryJBuildException
    {
        return
            createTemplate(
                    parameters,
                    retrieveMetadataManager(parameters),
                    retrieveCustomSqlProvider(parameters),
                    retrieveTemplateFactory(),
                    retrieveProjectPackage(parameters),
                    retrieveTableRepositoryName(parameters),
                    retrieveHeader(parameters),
                    retrieveJmx(parameters),
                    retrieveTableTemplates(parameters));
    }

    /**
     * Retrieves the per-repository template factory.
     * @return such instance.
     */
    protected abstract BasePerRepositoryTemplateFactory retrieveTemplateFactory();

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom sql provider.
     * @param templateFactory the template factory.
     * @param projectPackage the project package.
     * @param repository the repository.
     * @param header the header.
     * @param jmx whether to support JMX or not.
     * @param tableTemplates the table templates.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition templateFactory != null
     * @precondition projectPackage != null
     * @precondition packageName != null
     * @precondition repository != null
     * @precondition tableTemplates != null
     */
    protected boolean createTemplate(
        final Map parameters,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final BasePerRepositoryTemplateFactory templateFactory,
        final String projectPackage,
        final String repository,
        final String header,
        final boolean jmx,
        final TableTemplate[] tableTemplates)
      throws  QueryJBuildException
    {
        return
           createTemplate(
                   parameters,
                   retrieveDatabaseProductName(metadataManager.getMetaData()),
                   retrieveDatabaseProductVersion(metadataManager.getMetaData()),
                   retrieveDatabaseIdentifierQuoteString(metadataManager.getMetaData()),
                   metadataManager,
                   customSqlProvider,
                   templateFactory,
                   projectPackage,
                   repository,
                   header,
                   jmx,
                   tableTemplates);

    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom sql provider.
     * @param templateFactory the template factory.
     * @param projectPackage the project package.
     * @param repository the repository.
     * @param header the header.
     * @param jmx whether to support JMX or not.
     * @param tableTemplates the table templates.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition templateFactory != null
     * @precondition projectPackage != null
     * @precondition packageName != null
     * @precondition repository != null
     * @precondition tableTemplates != null
     */
    protected boolean createTemplate(
        final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final BasePerRepositoryTemplateFactory templateFactory,
        final String projectPackage,
        final String repository,
        final String header,
        final boolean jmx,
        final TableTemplate[] tableTemplates)
      throws  QueryJBuildException
    {
        return
            createTemplate(
                    parameters,
                    engineName,
                    engineVersion,
                    quote,
                    metadataManager,
                    customSqlProvider,
                    templateFactory,
                    projectPackage,
                    retrievePackage(engineName, parameters),
                    repository,
                    header,
                    jmx,
                    tableTemplates);
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom sql provider.
     * @param templateFactory the template factory.
     * @param projectPackage the project package.
     * @param packageName the package name.
     * @param repository the repository.
     * @param header the header.
     * @param jmx whether to support JMX or not.
     * @param tableTemplates the table templates.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition templateFactory != null
     * @precondition projectPackage != null
     * @precondition packageName != null
     * @precondition repository != null
     * @precondition tableTemplates != null
     */
    protected boolean createTemplate(
        final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final BasePerRepositoryTemplateFactory templateFactory,
        final String projectPackage,
        final String packageName,
        final String repository,
        final String header,
        final boolean jmx,
        final TableTemplate[] tableTemplates)
      throws  QueryJBuildException
    {
        boolean result = false;

        Collection t_cTableNames = new ArrayList();

        int t_iLength = (tableTemplates != null) ? tableTemplates.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            t_cTableNames.add(tableTemplates[t_iIndex].getTableName());
        }

        BasePerRepositoryTemplate t_Template =
            createTemplate(
                metadataManager,
                metadataManager.getMetadataTypeManager(),
                customSqlProvider,
                templateFactory,
                packageName,
                projectPackage,
                repository,
                engineName,
                header,
                jmx,
                t_cTableNames,
                parameters);

        storeTemplate(t_Template, parameters);

        return result;
    }

    /**
     * Uses the factory to create the template.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager}
     * instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param templateFactory the template factory.
     * @param projectPackage the base package.
     * @param packageName the package name.
     * @param repository the repository.
     * @param engineName the engine name.
     * @param header the header.
     * @param jmx whether to support JMX or not.
     * @param tableNames the table names.
     * @param parameters the parameters.
     * @return the template.
     * @throws QueryJBuildException on invalid input.
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition customSqlProvider != null
     * @precondition packageName != null
     * @precondition projectPackage != null
     * @precondition repository != null
     * @precondition engineName != null
     * @precondition tableNames != null
     * @precondition factory != null
     */
    protected BasePerRepositoryTemplate createTemplate(
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        final BasePerRepositoryTemplateFactory templateFactory,
        final String projectPackage,
        final String packageName,
        final String repository,
        final String engineName,
        final String header,
        final boolean jmx,
        final Collection tableNames,
        final Map parameters)
      throws  QueryJBuildException
    {
        BasePerRepositoryTemplate result = null;

        if  (templateFactory instanceof DefaultBasePerRepositoryTemplateFactory)
        {
            result =
                ((DefaultBasePerRepositoryTemplateFactory) templateFactory)
                    .createTemplate(
                        metadataManager,
                        metadataTypeManager,
                        customSqlProvider,
                        packageName,
                        projectPackage,
                        repository,
                        engineName,
                        tableNames,
                        header,
                        jmx);
        }
        else
        {
            LogFactory.getLog(BasePerRepositoryTemplateBuildHandler.class).warn(
                  "Unexpected BasePerRepository factory. Forgot overriding build handler's "
                + "createTemplate(..) ?");
        }

        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition parameters != null
     */
    protected String retrievePackage(
        final String engineName, final Map parameters)
    {
        return
            retrievePackage(
                engineName,
                retrieveProjectPackage(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected abstract String retrievePackage(
        final String engineName,
        final String projectPackage,
        final PackageUtils packageUtils);

    /**
     * Stores the template in given attribute map.
     * @param template the template.
     * @param parameters the parameter map.
     * @precondition template != null
     * @precondition parameters != null
     */
    protected abstract void storeTemplate(
        final BasePerRepositoryTemplate template, final Map parameters);

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @precondition parameters != null
     */
    protected TableTemplate[] retrieveTableTemplates(final Map parameters)
    {
        return
            (TableTemplate[])
                parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
    }
}
