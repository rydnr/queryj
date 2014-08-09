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
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.PerForeignKeyTemplate;
import org.acmsl.queryj.api.PerForeignKeyTemplateContext;
import org.acmsl.queryj.api.PerForeignKeyTemplateFactory;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.ForeignKeyDecorator;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.engines.Engine;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;

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

/**
 * Builds all templates to generate sources for each foreign key.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 2.0
 * @param <T> the template class.
 * @param <C> the template context class.
 * @param <TF> the template factory class.
 */
public abstract class BasePerForeignKeyTemplateBuildHandler
    <T extends PerForeignKeyTemplate<C>,
     C extends PerForeignKeyTemplateContext,
     TF extends PerForeignKeyTemplateFactory<T, C>>
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The key for storing the foreign keys in the parameter map.
     */
    public static final String FOREIGN_KEYS = "..FOreignKeY:::";

    /**
     * Creates a {@code BasePerForeignKeyTemplateBuildHandler} instance.
     */
    public BasePerForeignKeyTemplateBuildHandler() {}

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return {@code true} if the chain should be stopped.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand parameters)
        throws  QueryJBuildException
    {
        final boolean result;

        @NotNull final MetadataManager t_MetadataManager =
            retrieveMetadataManager(parameters);

        buildTemplates(
            parameters,
            t_MetadataManager,
            retrieveTemplateFactory());

        result = false;

        return result;
    }

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param templateFactory the template factory.
     * throws QueryJBuildException if the templates cannot be built.
     * @throws QueryJBuildException if the templates cannot be built.
     */
    protected void buildTemplates(
        @NotNull final QueryJCommand parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final TF templateFactory)
      throws  QueryJBuildException
    {
        buildTemplates(
            parameters,
            metadataManager,
            templateFactory,
            retrieveForeignKeys(parameters, metadataManager));
    }

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @NotNull
    protected abstract TF retrieveTemplateFactory();

    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    @NotNull
    protected DecoratorFactory retrieveDecoratorFactory()
    {
        return CachingDecoratorFactory.getInstance();
    }

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param templateFactory the template factory.
     * @param foreignKeys the foreign keys.
     * @throws QueryJBuildException if the templates cannot be built.
     */
    protected void buildTemplates(
        @NotNull final QueryJCommand parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final TF templateFactory,
        @NotNull final List<ForeignKey<String>> foreignKeys)
      throws  QueryJBuildException
    {
        @NotNull final List<T> t_lTemplates = new ArrayList<>();

        for  (@Nullable final ForeignKey<String> t_ForeignKey : foreignKeys)
        {
            if (   (t_ForeignKey != null)
                && (metadataManager.isGenerationAllowedForForeignKey(t_ForeignKey)))
            {
                t_lTemplates.add(
                    createTemplate(
                        templateFactory,
                        t_ForeignKey,
                        parameters));
            }
        }

        storeTemplates(t_lTemplates, parameters);
    }

    /**
     * Creates a template with required information.
     * @param templateFactory the {@link org.acmsl.queryj.api.PerTableTemplateFactory} instance.
     * @param foreignKey the foreign key.
     * @param parameters the parameter map.
     * @return the template.
     * @throws QueryJBuildException if the template cannot be created.
     */
    @Nullable
    protected abstract T createTemplate(
        @NotNull final TF templateFactory,
        @NotNull final ForeignKey<String> foreignKey,
        @NotNull final QueryJCommand parameters)
        throws  QueryJBuildException;

    /**
     * Retrieves the package name from the attribute map.
     * @param foreignKey the foreign key.
     * @param engine the engine.
     * @param parameters the parameter map.
     * @return the package name.
     */
    @NotNull
    protected String retrievePackage(
        @NotNull final ForeignKeyDecorator foreignKey,
        @NotNull final Engine<String> engine,
        @NotNull final QueryJCommand parameters)
    {
        return
            retrievePackage(
                foreignKey,
                engine,
                retrieveProjectPackage(parameters));
    }

    /**
     * Retrieves the package name.
     * @param foreignKey the foreign key.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @return the package name.
     */
    @NotNull
    protected abstract String retrievePackage(
        @NotNull final ForeignKeyDecorator foreignKey,
        @NotNull final Engine<String> engineName,
        @NotNull final String projectPackage);

    /**
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     */
    protected abstract void storeTemplates(
        @NotNull final List<T> templates, @NotNull final QueryJCommand parameters);

    /**
     * Retrieves the foreign keys.
     * @param parameters the parameter map.
     * @param metadataManager the database metadata manager.
     * @return such templates.
     */
    @NotNull
    protected List<ForeignKey<String>> retrieveForeignKeys(
        @NotNull final QueryJCommand parameters,
        @NotNull final MetadataManager metadataManager)
    {
        @NotNull final List<ForeignKey<String>> result;

        @Nullable final List<ForeignKey<String>> aux =
            new QueryJCommandWrapper<List<ForeignKey<String>>>(parameters).getSetting(FOREIGN_KEYS);

        if  (aux == null)
        {
            result = retrieveForeignKeys(metadataManager);
        }
        else
        {
            result = aux;
        }

        return result;
    }

    /**
     * Retrieves the foreign keys.
     * @param metadataManager the database metadata manager.
     * @return such foreign keys.
     */
    @NotNull
    protected List<ForeignKey<String>> retrieveForeignKeys(
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<ForeignKey<String>> result = null;

        @NotNull final List<Table<String, Attribute<String>, List<Attribute<String>>>> t_lTables =
            metadataManager.getTableDAO().findAllTables();

        for (@Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table : t_lTables)
        {
            if (t_Table != null)
            {
                if (result == null)
                {
                    result = new ArrayList<>();
                }

                result.addAll(t_Table.getForeignKeys());
            }
        }

        if (result == null)
        {
            result = new ArrayList<>(0);
        }

        return result;
    }
}
