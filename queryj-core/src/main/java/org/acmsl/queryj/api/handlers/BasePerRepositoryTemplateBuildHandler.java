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
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.SqlDAO;
import org.acmsl.queryj.api.AbstractBasePerRepositoryTemplate;
import org.acmsl.queryj.api.PerRepositoryTemplateContext;
import org.acmsl.queryj.api.PerRepositoryTemplateFactory;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.metadata.engines.Engine;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;

/*
 * Importing some JetBrains annotations.
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
import java.util.List;

/**
 * Builds a per-repository template using database metadata.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 2.0
 * @param <T> the template class.
 * @param <C> the template context class.
 * @param <TF> the template factory class.
 */
@ThreadSafe
public abstract class BasePerRepositoryTemplateBuildHandler
    <T extends AbstractBasePerRepositoryTemplate<C>,
     C extends PerRepositoryTemplateContext,
     TF extends PerRepositoryTemplateFactory<T, C>>
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
     * @throws QueryJBuildException if the template cannot be built.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand command)
        throws  QueryJBuildException
    {
        buildTemplate(
            command,
            retrieveCustomSqlProvider(command),
            retrieveTemplateFactory(),
            retrieveTableRepositoryName(command),
            retrieveMetadataManager(command));

        return false;
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param customSqlProvider the custom sql provider.
     * @param templateFactory the template factory.
     * @param repository the repository.
     * @param metadataManager the {@link MetadataManager} instance.
     * @throws QueryJBuildException if the template cannot be built.
     */
    @SuppressWarnings("unchecked")
    protected void buildTemplate(
        @NotNull final QueryJCommand parameters,
        @SuppressWarnings("unused") @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final TF templateFactory,
        @NotNull final String repository,
        @NotNull final MetadataManager metadataManager)
      throws  QueryJBuildException
    {
//        if (isGenerationEnabled(customSqlProvider, parameters))
//        {
            @Nullable final T t_Template =
                createTemplate(
                    templateFactory,
                    repository,
                    metadataManager.getTableDAO().findAllTableNames(),
                    parameters);

            if (t_Template != null)
            {
                storeTemplate(t_Template, parameters);
            }
        }
//    }

    /**
     * Checks whether template generation is enabled for this kind of template.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param parameters the command.
     * @return <code>true</code> in such case.
     */
    @SuppressWarnings("unchecked, unused")
    protected boolean isGenerationEnabled(
        @NotNull final CustomSqlProvider customSqlProvider, @NotNull final QueryJCommand parameters)
    {
        return
            definesRepositoryScopedSql(
                customSqlProvider, getAllowEmptyRepositoryDAOSetting(parameters));
    }

    /**
     * Uses the factory to create the template.
     * @param templateFactory the template factory.
     * @param repository the repository.
     * @param tableNames the table names.
     * @param parameters the command.
     * @return the template.
     * @throws QueryJBuildException if the template cannot be built.
     */
    @Nullable
    protected abstract T createTemplate(
        @NotNull final TF templateFactory,
        @NotNull final String repository,
        @NotNull final List<String> tableNames,
        @NotNull final QueryJCommand parameters)
      throws  QueryJBuildException;

    /**
     * Retrieves the package name.
     * @param repository the repository.
     * @param engine the engine.
     * @param parameters the parameters.
     * @return the package name.
     */
    @NotNull
    protected abstract String retrievePackage(
        @NotNull final String repository,
        @NotNull final Engine<String> engine,
        @NotNull final QueryJCommand parameters);

    /**
     * Stores the template in given attribute map.
     * @param template the template.
     * @param parameters the parameter map.
     */
    protected abstract void storeTemplate(
        @NotNull final T template, @NotNull final QueryJCommand parameters);

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
            for (@Nullable final Sql<String> t_Sql : sqlDAO.findAll())
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
    protected boolean getAllowEmptyRepositoryDAOSetting(@NotNull final QueryJCommand parameters)
    {
        return parameters.getBooleanSetting(ParameterValidationHandler.ALLOW_EMPTY_REPOSITORY_DAO, false);
    }
}
