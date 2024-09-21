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
 * Filename: BasePerForeignKeyTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes per-fk templates.
 *
 */
package org.acmsl.queryj.api.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.PerForeignKeyTemplate;
import org.acmsl.queryj.api.PerForeignKeyTemplateContext;
import org.acmsl.queryj.api.PerForeignKeyTemplateGenerator;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.CachingForeignKeyDecorator;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.ForeignKeyDecorator;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.engines.Engine;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;

/**
 * Writes <i>per-fk</i> templates.
 * @param <T> the template type.
 * @param <C> the template context type.
 * @param <TG> the template generator type.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@SuppressWarnings("unused")
public abstract class BasePerForeignKeyTemplateWritingHandler
    <T extends PerForeignKeyTemplate<C>,
     C extends PerForeignKeyTemplateContext,
     TG extends PerForeignKeyTemplateGenerator<T, C>>
    extends    AbstractQueryJTemplateWritingHandler<T, C, TG>
    implements TemplateWritingHandler
{
    /**
     * Creates a <code>BasePerForeignKeyTemplateWritingHandler</code> instance.
     */
    public BasePerForeignKeyTemplateWritingHandler() {}

    /**
     * Retrieves the output dir from the attribute map.
     * @param context the context.
     * @param rootDir the root dir.
     * @param parameters the parameter map.
     * @return such folder.
     */
    @Override
    @NotNull
    public File retrieveOutputDir(
        @NotNull final C context,
        @NotNull final File rootDir,
        @NotNull final QueryJCommand parameters)
      throws QueryJBuildException
    {
        @NotNull final MetadataManager t_MetadataManager = retrieveMetadataManager(parameters);

        @NotNull final DecoratorFactory t_DecoratorFactory = retrieveDecoratorFactory(parameters);

        @NotNull final CustomSqlProvider t_CustomSqlProvider = retrieveCustomSqlProvider(parameters);

        return
            retrieveOutputDir(
                t_MetadataManager.getEngine(),
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                new CachingForeignKeyDecorator(
                    context.getForeignKey(), t_MetadataManager, t_DecoratorFactory, t_CustomSqlProvider),
                retrieveUseSubfoldersFlag(parameters));
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param engine the engine.
     * @param projectOutputDir the project output dir.
     * @param projectPackage the project package.
     * @param foreignKey the foreign key.
     * @param subFolders whether to use sub folders or not.
     * @return such folder.
     */
    @NotNull
    protected abstract File retrieveOutputDir(
        @NotNull final Engine<String> engine,
        @NotNull final File projectOutputDir,
        final String projectPackage,
        @NotNull final ForeignKeyDecorator foreignKey,
        final boolean subFolders);
}
