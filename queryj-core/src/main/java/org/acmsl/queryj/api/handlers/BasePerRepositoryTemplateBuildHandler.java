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
package org.acmsl.queryj.api.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.SqlDAO;
import org.acmsl.queryj.api.AbstractBasePerRepositoryTemplate;
import org.acmsl.queryj.api.PerRepositoryTemplateContext;
import org.acmsl.queryj.api.PerRepositoryTemplateFactory;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.List;
import java.util.Map;

/**
 * Builds a per-repository template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class BasePerRepositoryTemplateBuildHandler
    <T extends AbstractBasePerRepositoryTemplate<C>,
     TF extends PerRepositoryTemplateFactory<T>,
     C extends PerRepositoryTemplateContext>
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
    @Override
    @SuppressWarnings("unchecked")
    protected boolean handle(@NotNull final Map<String, ?> parameters)
        throws  QueryJBuildException
    {
        return
            handle(parameters, retrieveProjectPackage((Map<String, String>) parameters));
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param projectPackage the project package.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @SuppressWarnings("unchecked")
    protected boolean handle(@NotNull final Map<String, ?> parameters, @NotNull final String projectPackage)
        throws  QueryJBuildException
    {
        final boolean result;

        @Nullable final MetadataManager t_MetadataManager =
            retrieveMetadataManager((Map<String, MetadataManager>) parameters);

        if (t_MetadataManager != null)
        {
            result = false;

            buildTemplate(
                parameters,
                t_MetadataManager,
                retrieveCustomSqlProvider((Map<String, CustomSqlProvider>) parameters),
                retrieveTemplateFactory(),
                retrievePackage(t_MetadataManager.getEngineName(), projectPackage, PackageUtils.getInstance()),
                projectPackage,
                retrieveTableRepositoryName((Map<String, String>) parameters),
                retrieveHeader((Map <String, String>) parameters),
                retrieveImplementMarkerInterfaces((Map <String, Boolean>) parameters),
                retrieveJmx((Map<String, Boolean>) parameters),
                retrieveJNDILocation((Map <String, String>) parameters),
                CachingDecoratorFactory.getInstance());
        }
        else
        {
            result = true;
        }

        return result;
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
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @SuppressWarnings("unchecked")
    protected void buildTemplate(
        @NotNull final Map<String, ?>  parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final TF templateFactory,
        @NotNull final String packageName,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @Nullable final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        @NotNull final DecoratorFactory decoratorFactory)
      throws  QueryJBuildException
    {
        if (isGenerationEnabled(customSqlProvider, parameters))
        {
            @NotNull final List<String> t_lTableNames = metadataManager.getTableDAO().findAllTableNames();

            @Nullable final T t_Template =
                createTemplate(
                    metadataManager,
                    customSqlProvider,
                    decoratorFactory,
                    templateFactory,
                    packageName,
                    projectPackage,
                    repository,
                    header,
                    implementMarkerInterfaces,
                    jmx,
                    t_lTableNames,
                    retrieveJNDILocation((Map <String, String>) parameters),
                    retrieveDisableGenerationTimestamps((Map<String, Boolean>) parameters),
                    retrieveDisableNotNullAnnotations((Map <String, Boolean>) parameters),
                    retrieveDisableCheckthreadAnnotations((Map <String, Boolean>) parameters),
                    parameters);

            if (t_Template != null)
            {
                storeTemplate(t_Template, (Map<String, List<T>>) parameters);
            }
        }
    }

    /**
     * Checks whether template generation is enabled for this kind of template.
     * @return <code>true</code> in such case.
     */
    @SuppressWarnings("unchecked")
    protected boolean isGenerationEnabled(
        @NotNull final CustomSqlProvider customSqlProvider, @NotNull final Map<String, ?> parameters)
    {
        return
            definesRepositoryScopedSql(
                customSqlProvider, getAllowEmptyRepositoryDAOSetting((Map <String, Boolean >) parameters));
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
     * @param jndiLocation the JNDI path of the {@link javax.sql.DataSource}.
     * @param disableGenerationTimestamps whether to disable generation timestamps.
     * @param disableNotNullAnnotations whether to disable NotNull annotations.
     * @param disableCheckthreadAnnotations whether to disable checkthread.org annotations or not.
     * @return the template.
     * @throws QueryJBuildException on invalid input.
     */
    @SuppressWarnings("unused")
    @Nullable
    protected T createTemplate(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final TF templateFactory,
        @NotNull final String packageName,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @Nullable final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final List<String> tableNames,
        @NotNull final String jndiLocation,
        final boolean disableGenerationTimestamps,
        final boolean disableNotNullAnnotations,
        final boolean disableCheckthreadAnnotations,
        @NotNull final Map parameters)
      throws  QueryJBuildException
    {
        return
            templateFactory.createTemplate(
                metadataManager,
                customSqlProvider,
                decoratorFactory,
                packageName,
                projectPackage,
                repository,
                header,
                implementMarkerInterfaces,
                jmx,
                tableNames,
                jndiLocation,
                disableGenerationTimestamps,
                disableNotNullAnnotations,
                disableCheckthreadAnnotations);
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
     */
    protected abstract void storeTemplate(
        @NotNull final T template, @NotNull final Map<String, List<T>> parameters);

    /**
     * Checks whether there's any custom SQL for the whole repository.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param allowEmptyRepositoryDAO whether to generate the repository DAO
     * in any case.
     * @return <code>true</code> in such case.
     */
    protected boolean definesRepositoryScopedSql(
        @NotNull final CustomSqlProvider customSqlProvider,
        final boolean allowEmptyRepositoryDAO)
    {
        return definesRepositoryScopedSql(customSqlProvider.getSqlDAO(), allowEmptyRepositoryDAO);
    }

    /**
     * Checks whether there's any custom SQL for the whole repository.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @param allowEmptyRepositoryDAO whether to generate the repository DAO
     * in any case.
     * @return <code>true</code> in such case.
     */
    protected boolean definesRepositoryScopedSql(
        @NotNull final SqlDAO sqlDAO,
        final boolean allowEmptyRepositoryDAO)
    {
        boolean result = allowEmptyRepositoryDAO;

        if  (!result)
        {
            for (@Nullable final Sql t_Sql : sqlDAO.findAll())
            {
                if (   (t_Sql != null)
                    && (t_Sql.getRepositoryScope() != null))
                {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Checks whether empty repository DAO is allowed explicitly.
     * @param parameters the parameters.
     * @return <code>true</code> in such case.
     */
    protected boolean getAllowEmptyRepositoryDAOSetting(@NotNull final Map<String, Boolean> parameters)
    {
        final boolean result;

        @Nullable final Boolean t_Result =
            parameters.get(ParameterValidationHandler.ALLOW_EMPTY_REPOSITORY_DAO);

        result = (t_Result != null) && t_Result;

        return result;
    }

    /**
     * Displays useful information about this handler.
     * @return such information.
     */
    @NotNull
    @Override
    public String toString()
    {
        return "Builder:" + getClass().getSimpleName();
    }
}