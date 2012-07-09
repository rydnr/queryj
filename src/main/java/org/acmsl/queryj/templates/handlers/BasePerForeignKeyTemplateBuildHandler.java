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
package org.acmsl.queryj.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.templates.BasePerForeignKeyTemplate;
import org.acmsl.queryj.templates.BasePerForeignKeyTemplateContext;
import org.acmsl.queryj.templates.BasePerForeignKeyTemplateFactory;

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
    <T extends BasePerForeignKeyTemplate<C>,
        TF extends BasePerForeignKeyTemplateFactory<T>, C extends BasePerForeignKeyTemplateContext>
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
    protected boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        @Nullable MetadataManager t_MetadataManager = retrieveMetadataManager(parameters);

        if (t_MetadataManager == null)
        {
            throw new QueryJBuildException("Error accessing database metadata");
        }

        buildTemplates(
            parameters,
            t_MetadataManager,
            retrieveCustomSqlProvider(parameters),
            retrieveTemplateFactory());

        return false;
    }

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param templateFactory the template factory.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
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
            retrieveProjectPackage(parameters),
            retrieveTableRepositoryName(parameters),
            retrieveHeader(parameters),
            retrieveImplementMarkerInterfaces(parameters),
            retrieveJmx(parameters),
            retrieveJNDILocation(parameters),
            retrieveForeignKeys(
                parameters, metadataManager),
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
     * @param foreignKeys the foreign keys.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final TF templateFactory,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        @NotNull final List<ForeignKey> foreignKeys,
        @NotNull final DecoratorFactory decoratorFactory)
      throws  QueryJBuildException
    {
        @NotNull List<T> t_lTemplates = new ArrayList<T>();

        for  (@Nullable ForeignKey t_ForeignKey : foreignKeys)
        {
            if (   (t_ForeignKey != null)
                && (metadataManager.isGenerationAllowedForForeignKey(t_ForeignKey)))
            {
                t_lTemplates.add(
                    templateFactory.createTemplate(
                        metadataManager,
                        customSqlProvider,
                        decoratorFactory,
                        retrievePackage(
                            t_ForeignKey.getSourceTableName(),
                            metadataManager.getEngineName(),
                            parameters),
                        projectPackage,
                        repository,
                        header,
                        implementMarkerInterfaces,
                        jmx,
                        jndiLocation,
                        t_ForeignKey));
            }
        }

        storeTemplates(t_lTemplates, parameters);
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
        @NotNull final String tableName, @NotNull final String engineName, @NotNull final Map parameters)
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
        @NotNull final List<T> templates, @NotNull final Map parameters);

    /**
     * Retrieves the foreign keys.
     * @param parameters the parameter map.
     * @param metadataManager the database metadata manager.
     * @return such templates.
     */
    @NotNull
    @SuppressWarnings("unchecked")
    protected List<ForeignKey> retrieveForeignKeys(
        @NotNull final Map parameters,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<ForeignKey> result = (List<ForeignKey>) parameters.get(FOREIGN_KEYS);

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

        List<Table> t_lTables = metadataManager.getTableDAO().findAllTables();

        for (@Nullable Table t_Table : t_lTables)
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
}
