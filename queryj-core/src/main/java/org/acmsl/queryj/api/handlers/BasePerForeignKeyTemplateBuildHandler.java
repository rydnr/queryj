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
 * Filename: BasePerForeignKeyTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds all templates to generate sources for each
 *              foreign key.
 *
 */
package org.acmsl.queryj.api.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.api.PerForeignKeyTemplate;
import org.acmsl.queryj.api.PerForeignKeyTemplateContext;
import org.acmsl.queryj.api.PerForeignKeyTemplateFactory;
import org.acmsl.queryj.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.metadata.vo.Table;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Builds all templates to generate sources for each foreign key.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerForeignKeyTemplateBuildHandler
    <T extends PerForeignKeyTemplate<C>,
        TF extends PerForeignKeyTemplateFactory<T>, C extends PerForeignKeyTemplateContext>
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The key for storing the foreign keys in the parameter map.
     */
    public static final String FOREIGN_KEYS = "..FOreignKeY:::";

    /**
     * Creates a <code>BasePerForeignKeyTemplateBuildHandler</code> instance.
     */
    public BasePerForeignKeyTemplateBuildHandler() {}

    /**
     * Handles given information.
     *
     *
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @Override
    @SuppressWarnings("unchecked, cast")
    protected boolean handle(@NotNull final Map<String, ?> parameters)
        throws  QueryJBuildException
    {
        boolean result = true;

        @Nullable final MetadataManager t_MetadataManager =
            retrieveMetadataManager((Map<String, MetadataManager>) parameters);

        if (t_MetadataManager != null)
        {
            buildTemplates(
                parameters,
                t_MetadataManager,
                retrieveCustomSqlProvider((Map<String, CustomSqlProvider>) parameters),
                retrieveTemplateFactory());

            result = false;
        }

        return result;
    }

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param templateFactory the template factory.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @SuppressWarnings("unchecked,cast")
    protected void buildTemplates(
        @NotNull final Map<String, ?> parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final TF templateFactory)
      throws  QueryJBuildException
    {
        buildTemplates(
            parameters,
            metadataManager,
            customSqlProvider,
            templateFactory,
            retrieveProjectPackage((Map<String, String>) parameters),
            retrieveTableRepositoryName((Map<String, String>) parameters),
            retrieveHeader((Map<String, String>) parameters),
            retrieveImplementMarkerInterfaces((Map<String, Boolean>) parameters),
            retrieveJmx((Map<String, Boolean>) parameters),
            retrieveJNDILocation((Map<String, String>) parameters),
            retrieveDisableGenerationTimestamps((Map<String, Boolean>) parameters),
            retrieveDisableNotNullAnnotations((Map<String, Boolean>) parameters),
            retrieveDisableCheckthreadAnnotations((Map<String, Boolean>) parameters),
            retrieveForeignKeys((Map<String, List<ForeignKey>>) parameters, metadataManager),
            CachingDecoratorFactory.getInstance());
    }
    
    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @NotNull
    protected abstract TF retrieveTemplateFactory();

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom sql provider.
     * @param templateFactory the template factory.
     * @param projectPackage the project package.
     * @param repository the repository.
     * @param header the header.
     * @param implementMarkerInterfaces whether to implement marker interfaces.
     * @param jmx whether to include JMX support.
     * @param jndiLocation the JNDI location.
     * @param disableGenerationTimestamps whether to disable generation timestamps.
     * @param disableNotNullAnnotations whether to disable NotNull annotations.
     * @param disableCheckthreadAnnotations whether to disable checkthread.org annotations or not.
     * @param foreignKeys the foreign keys.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @SuppressWarnings("unchecked,cast")
    protected void buildTemplates(
        @NotNull final Map<String, ?> parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final TF templateFactory,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @Nullable final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        final boolean disableGenerationTimestamps,
        final boolean disableNotNullAnnotations,
        final boolean disableCheckthreadAnnotations,
        @NotNull final List<ForeignKey> foreignKeys,
        @NotNull final DecoratorFactory decoratorFactory)
      throws  QueryJBuildException
    {
        @NotNull final List<T> t_lTemplates = new ArrayList<T>();

        for  (@Nullable final ForeignKey t_ForeignKey : foreignKeys)
        {
            if (   (t_ForeignKey != null)
                && (metadataManager.isGenerationAllowedForForeignKey(t_ForeignKey)))
            {
                // TODO
                @NotNull final String t_strFileName = "";

                t_lTemplates.add(
                    templateFactory.createTemplate(
                        metadataManager,
                        customSqlProvider,
                        decoratorFactory,
                        retrievePackage(
                            t_ForeignKey.getSourceTableName(),
                            metadataManager.getEngineName(),
                            (Map<String, String>) parameters),
                        projectPackage,
                        repository,
                        header,
                        implementMarkerInterfaces,
                        jmx,
                        jndiLocation,
                        disableGenerationTimestamps,
                        disableNotNullAnnotations,
                        disableCheckthreadAnnotations,
                        t_strFileName,
                        t_ForeignKey));
            }
        }

        storeTemplates(t_lTemplates, (Map<String, List<T>>) parameters);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     */
    @NotNull
    protected String retrievePackage(
        @NotNull final String tableName,
        @NotNull final String engineName,
        @NotNull final Map<String, String> parameters)
    {
        return
            retrievePackage(
                tableName,
                engineName,
                retrieveProjectPackage(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     */
    @NotNull
    protected abstract String retrievePackage(
        @NotNull final String tableName,
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils);

    /**
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     */
    protected abstract void storeTemplates(
        @NotNull final List<T> templates, @NotNull final Map<String, List<T>> parameters);

    /**
     * Retrieves the foreign keys.
     * @param parameters the parameter map.
     * @param metadataManager the database metadata manager.
     * @return such templates.
     */
    @NotNull
    @SuppressWarnings("unchecked")
    protected List<ForeignKey> retrieveForeignKeys(
        @NotNull final Map<String, List<ForeignKey>> parameters,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<ForeignKey> result = parameters.get(FOREIGN_KEYS);

        if  (result == null)
        {
            result = retrieveForeignKeys(metadataManager);
        }

        return result;
    }

    /**
     * Retrieves the foreign keys.
     * @param metadataManager the database metadata manager.
     * @return such foreign keys.
     */
    @NotNull
    protected List<ForeignKey> retrieveForeignKeys(
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<ForeignKey> result = null;

        @NotNull final List<Table> t_lTables = metadataManager.getTableDAO().findAllTables();

        for (@Nullable final Table t_Table : t_lTables)
        {
            if (t_Table != null)
            {
                if (result == null)
                {
                    result = new ArrayList<ForeignKey>();
                }

                result.addAll(t_Table.getForeignKeys());
            }
        }

        if (result == null)
        {
            result = new ArrayList<ForeignKey>(0);
        }

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
        return PREFIX + getClass().getSimpleName();
    }
}
