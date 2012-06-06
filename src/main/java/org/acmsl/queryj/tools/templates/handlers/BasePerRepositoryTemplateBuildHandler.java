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
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplate;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateContext;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateFactory;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateContext;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Builds a per-repository template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class BasePerRepositoryTemplateBuildHandler
    <T extends BasePerRepositoryTemplate<C>, TF extends BasePerRepositoryTemplateFactory<T>, C extends BasePerRepositoryTemplateContext>
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a {@link BasePerRepositoryTemplateBuildHandler} instance.
     */
    public BasePerRepositoryTemplateBuildHandler() {}

    /**
     * Retrieves the per-repository template factory.
     * @return such instance.
     */
    protected abstract TF retrieveTemplateFactory();

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the process fails unexpectedly.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand command)
        throws  QueryJBuildException
    {
        return handle(command.getAttributeMap());
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        return
            handle(parameters, retrieveProjectPackage(parameters));
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param projectPackage the project package.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected boolean handle(@NotNull final Map parameters, @NotNull final String projectPackage)
        throws  QueryJBuildException
    {
        @Nullable MetadataManager t_MetadataManager = retrieveMetadataManager(parameters);

        if (t_MetadataManager == null)
        {
            throw new QueryJBuildException("Cannot access database metadata");
        }

        buildTemplate(
            parameters,
            t_MetadataManager,
            retrieveCustomSqlProvider(parameters),
            retrieveTemplateFactory(),
            retrievePackage(t_MetadataManager.getEngineName(), projectPackage, PackageUtils.getInstance()),
            retrieveProjectPackage(parameters),
            retrieveTableRepositoryName(parameters),
            retrieveHeader(parameters),
            retrieveImplementMarkerInterfaces(parameters),
            retrieveJmx(parameters),
            retrieveJNDILocation(parameters),
            retrieveTableTemplates(parameters));

        return false;
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom sql provider.
     * @param templateFactory the template factory.
     * @param packageName the package name.
     * @param projectPackage the project package.
     * @param repository the repository.
     * @param header the header.
     * @param implementMarkerInterfaces whether to implement marker interfaces.
     * @param jmx whether to support JMX or not.
     * @param jndiLocation the JNDI path of the {@link javax.sql.DataSource}.
     * @param tableTemplates the table templates.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @SuppressWarnings("unused")
    protected void buildTemplate(
        @NotNull final Map parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final TF templateFactory,
        @NotNull final String packageName,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        @NotNull final List<TableTemplate> tableTemplates)
      throws  QueryJBuildException
    {
        @NotNull List<String> t_lTableNames = new ArrayList<String>();

        String t_strTableName;
        BasePerTableTemplateContext t_Context;

        for (TableTemplate t_TableTemplate : tableTemplates)
        {
            if (t_TableTemplate != null)
            {
                t_Context = t_TableTemplate.getTemplateContext();

                t_strTableName = t_Context.getTableName();

                t_lTableNames.add(t_strTableName);
            }
        }

        @Nullable T t_Template =
            createTemplate(
                metadataManager,
                customSqlProvider,
                templateFactory,
                packageName,
                projectPackage,
                repository,
                header,
                implementMarkerInterfaces,
                jmx,
                t_lTableNames,
                retrieveJNDILocation(parameters),
                parameters);

        if (t_Template != null)
        {
            storeTemplate(t_Template, parameters);
        }
    }

    /**
     * Uses the factory to create the template.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param templateFactory the template factory.
     * @param projectPackage the base package.
     * @param packageName the package name.
     * @param repository the repository.
     * @param header the header.
     * @param implementMarkerInterfaces whether to implement marker interfaces.
     * @param jmx whether to support JMX or not.
     * @param tableNames the table names.
     * @return the template.
     * @throws QueryJBuildException on invalid input.
     */
    @Nullable
    @SuppressWarnings("unused")
    protected T createTemplate(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final TF templateFactory,
        @NotNull final String projectPackage,
        @NotNull final String packageName,
        @NotNull final String repository,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final List<String> tableNames,
        @NotNull String jndiLocation,
        @NotNull final Map parameters)
      throws  QueryJBuildException
    {
        return
            templateFactory.createTemplate(
                metadataManager,
                customSqlProvider,
                projectPackage,
                packageName,
                repository,
                header,
                implementMarkerInterfaces,
                jmx,
                tableNames,
                jndiLocation);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected String retrievePackage(
        @NotNull final String engineName, @NotNull final Map parameters)
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
    @NotNull
    protected abstract String retrievePackage(
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils);

    /**
     * Stores the template in given attribute map.
     * @param template the template.
     * @param parameters the parameter map.
     * @precondition template != null
     * @precondition parameters != null
     */
    protected abstract void storeTemplate(
        @NotNull final T template, @NotNull final Map parameters);

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @precondition parameters != null
     */
    @NotNull
    @SuppressWarnings("unchecked")
    protected List<TableTemplate> retrieveTableTemplates(@NotNull final Map parameters)
    {
        List<TableTemplate> result =
            (List<TableTemplate>)
                parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);

        if (result == null)
        {
            result = new ArrayList<TableTemplate>(0);
        }

        return result;
    }
}
